package ca.mahram.demo.picasso.activity;

import android.annotation.TargetApi;
import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ca.mahram.demo.picasso.ImageManager;
import ca.mahram.demo.picasso.R;
import ca.mahram.demo.picasso.activity.base.BaseDemoActivity;
import ca.mahram.demo.picasso.misc.Contact;
import ca.mahram.demo.picasso.misc.Utils;
import ca.mahram.demo.picasso.xform.GaussianBlurTransformation;
import ca.mahram.demo.picasso.xform.PaletteGeneratorTransformation;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import static android.provider.ContactsContract.Contacts.DISPLAY_NAME;
import static ca.mahram.demo.picasso.misc.Utils.firstNonNull;

/**
 Demos Picasso transformations
 Created by mahram on 15-03-02.
 */
public class ContactActivity
  extends BaseDemoActivity
  implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int LOADER_ID_CONTACT = 1;
    private static final int LOADER_ID_NUMBERS = 2;
    private static final int LOADER_ID_EMAILS  = 3;

    private static final float  PHOTO_BLUR_RADIUS      = 10f;
    private static final int    DEFAULT_PALETTE_COLORS = 24;
    private static final String LOGTAG                 = "CONTACT";

    private static final float BG_ALPHA = 0.5f;

    private Picasso picasso = ImageManager.get ().picasso;

    private Header header;

    private long    contactRawId;
    private Uri     contactUri;
    private Adapter adapter;

    @Bind (android.R.id.list) ListView list;
    @Bind (R.id.toolbar)      Toolbar  toolbar;

    @Override protected void onCreate (final Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_contact);
        ButterKnife.bind (this);

        setSupportActionBar (toolbar);
        initActionbar ();

        final LayoutInflater inflater = LayoutInflater.from (this);
        header = new Header (inflater.inflate (R.layout.contact_header, list, false));

        list.addHeaderView (header.root, null, false);
        adapter = new Adapter (this);
        list.setAdapter (adapter);

        final Intent intent = getIntent ();
        contactUri = intent.getData ();
        contactRawId = ContentUris.parseId (contactUri);

        initHeaderPhotos ();

        final LoaderManager lm = getLoaderManager ();
        lm.initLoader (LOADER_ID_CONTACT, null, this);
        lm.initLoader (LOADER_ID_EMAILS, null, this);
        lm.initLoader (LOADER_ID_NUMBERS, null, this);
    }

    private void initHeaderPhotos () {
        final Uri photoUri = Contact.getPhotoUri (contactRawId);

        Log.d (LOGTAG, "PhotoUri: " + String.valueOf (photoUri));

        final Context context = this;

        picasso.load (photoUri)
               .transform (new GaussianBlurTransformation (context, PHOTO_BLUR_RADIUS))
               .placeholder (R.drawable.ic_default_2)
               .into (header.blurr, new Callback () {
                   @Override public void onSuccess () {

                   }

                   @Override public void onError () {
                       picasso.load (R.drawable.ic_default_2)
                              .placeholder (R.drawable.ic_default_2)
                              .transform (new GaussianBlurTransformation (context, PHOTO_BLUR_RADIUS))
                              .into (header.blurr);
                   }
               });

        picasso.load (photoUri)
               .placeholder (R.drawable.ic_default_1)
               .transform (new PaletteGeneratorTransformation (DEFAULT_PALETTE_COLORS))
               .resizeDimen (R.dimen.profile_photo_size, R.dimen.profile_photo_size)
               .centerCrop ()
               .into (header.photo, new Palettier (header.photo));
    }

    // used to darken the palette color for status bar
    private static int darken (final int color, final float ratio) {
        final float[] hsv = new float[3];
        Color.colorToHSV (color, hsv);
        hsv[2] *= (1f - ratio);
        return Color.HSVToColor (hsv);
    }

    @Override
    public Loader<Cursor> onCreateLoader (final int id, final Bundle args) {
        switch (id) {
            case LOADER_ID_CONTACT:
                Log.d (LOGTAG, "Loading " + String.valueOf (contactUri));
                return new CursorLoader (this,
                                         contactUri,
                                         new String[] {DISPLAY_NAME },
                                         null, null, null);
            case LOADER_ID_EMAILS:
                return new CursorLoader (this,
                                         ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                                         new String[] {ContactsContract.CommonDataKinds.Email.DATA},
                                         ContactsContract.CommonDataKinds.Email.RAW_CONTACT_ID + "=?",
                                         new String[] {String.valueOf (contactRawId)},
                                         null);
            case LOADER_ID_NUMBERS:
                return new CursorLoader (this,
                                         ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                         new String[] {ContactsContract.CommonDataKinds.Phone.NUMBER},
                                         ContactsContract.CommonDataKinds.Phone.RAW_CONTACT_ID + "=?",
                                         new String[] {String.valueOf (contactRawId)},
                                         null);
            default:
                throw new IllegalArgumentException ("Unknown loader id");
        }
    }

    @Override public void onLoadFinished (final Loader<Cursor> loader, final Cursor data) {
        switch (loader.getId ()) {
            case LOADER_ID_CONTACT:
                onContactLoaded (data);
                break;
            case LOADER_ID_EMAILS:
                onEmailsLoaded (data);
                break;
            case LOADER_ID_NUMBERS:
                onPhoneNumbersLoaded (data);
                break;
            default:
                throw new IllegalArgumentException ("Unknown loader id");
        }
    }

    private void onPhoneNumbersLoaded (final Cursor data) {
        adapter.refreshEntries (data, ContactMethodType.PHONE);
    }

    private void onEmailsLoaded (final Cursor emails) {
        adapter.refreshEntries (emails, ContactMethodType.EMAIL);
    }

    private void onContactLoaded (final Cursor contact) {
        if (!contact.moveToFirst ())
            return;

        header.title.setText (contact.getString (0));
    }

    @Override public void onLoaderReset (final Loader<Cursor> loader) {
        adapter.notifyDataSetInvalidated ();
    }

    private void assignMutedColors (final Palette.Swatch muted) {
        if (null == muted)
            return;

        header.title.setTextColor (muted.getBodyTextColor ());
        header.title.setBackgroundColor (Utils.applyAlpha (muted.getRgb (), BG_ALPHA));
    }

    private void assignVibrantColors (final Palette.Swatch vibrant) {
        if (null == vibrant)
            return;

        final int toolbarColor = vibrant.getRgb ();
        final int statusbarColor = darken (toolbarColor, 0.2f);

        toolbar.setBackgroundColor (toolbarColor);
        toolbar.setTitleTextColor (vibrant.getTitleTextColor ());
        header.separator.setBackgroundColor (toolbarColor);
        setMaterialColors (toolbarColor, statusbarColor);
    }

    @TargetApi (Build.VERSION_CODES.LOLLIPOP)
    private void setMaterialColors (final int primary, final int primaryDark) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) return;

        final Window window = getWindow ();
        window.setStatusBarColor (primaryDark);
        window.setNavigationBarColor (primary);
    }

    private class Palettier
      extends PaletteGeneratorTransformation.Callback {

        public Palettier (final ImageView t) {
            super (t);
        }

        @Override protected void onPalette (final Palette palette) {
            if (null == palette)
                return;

            assignVibrantColors (firstNonNull (palette.getVibrantSwatch (),
                                               palette.getDarkVibrantSwatch (),
                                               palette.getLightVibrantSwatch ()));
            assignMutedColors (firstNonNull (palette.getMutedSwatch (),
                                             palette.getDarkMutedSwatch (),
                                             palette.getLightMutedSwatch ()));
        }
    }

    private static class Adapter
      extends BaseAdapter {

        private final List<ContactMethod> entries = new ArrayList<> ();
        private final LayoutInflater inflater;

        Adapter (final Context context) {
            inflater = LayoutInflater.from (context);
        }

        private void clear (final ContactMethodType type) {
            int i = 0;
            int size = entries.size ();

            while (i < size) {
                final ContactMethod next = entries.get (i);

                if (type != next.type) {
                    i++;
                } else {
                    entries.remove (i);
                    size = entries.size ();
                }
            }
        }

        void refreshEntries (final Cursor cursor, final ContactMethodType type) {
            clear (type);

            Log.d (LOGTAG, type.name () + " x" + cursor.getCount ());

            if (!cursor.moveToNext ())
                return;

            do {
                entries.add (new ContactMethod (type, cursor.getString (0)));
            } while (cursor.moveToNext ());

            Collections.sort (entries, COMPARATOR);

            notifyDataSetChanged ();
        }

        @Override public int getCount () {
            return entries.size ();
        }

        @Override public ContactMethod getItem (final int position) {
            return entries.get (position);
        }

        @Override public long getItemId (final int position) {
            return position;
        }

        @Override public View getView (final int position, final View convertView, final ViewGroup parent) {
            final View view = convertView == null ? newView (parent) : convertView;
            final ContactMethodRow row = (ContactMethodRow) view.getTag ();

            final ContactMethod contactMethod = getItem (position);

            switch (contactMethod.type) {
                case EMAIL:
                    row.text.setCompoundDrawablesWithIntrinsicBounds (R.drawable.ic_communication_email, 0, 0, 0);
                    break;
                case PHONE:
                    row.text.setCompoundDrawablesWithIntrinsicBounds (R.drawable.ic_communication_phone, 0, 0, 0);
                    break;
                default:
                    row.text.setCompoundDrawablesWithIntrinsicBounds (0, 0, 0, 0);
                    break;
            }

            row.text.setText (contactMethod.data);

            return view;
        }

        private View newView (final ViewGroup parent) {
            final View view = inflater.inflate (R.layout.item_contact_method, parent, false);
            view.setTag (new ContactMethodRow (view));
            return view;
        }
    }

    static class Header {
        private final                    View      root;
        @Bind (R.id.blurred_photo) ImageView blurr;
        @Bind (R.id.photo)         ImageView photo;
        @Bind (android.R.id.title) TextView  title;
        @Bind (R.id.separator)     View      separator;

        Header (final View view) {
            root = view;
            ButterKnife.bind (this, root);
        }
    }

    private enum ContactMethodType {
        PHONE,
        EMAIL
    }

    static class ContactMethodRow {
        @Bind (android.R.id.text1) TextView text;

        ContactMethodRow (final View view) {
            ButterKnife.bind (this, view);
        }
    }

    @EqualsAndHashCode @ToString
    static class ContactMethod {

        private final ContactMethodType type;
        private final String            data;

        ContactMethod (final ContactMethodType t, final String d) {
            data = d;
            type = t;
        }
    }

    private static final Comparator<ContactMethod> COMPARATOR = new Comparator<ContactMethod> () {
        @Override public int compare (final ContactMethod lhs, final ContactMethod rhs) {
            // if same type, compara data
            if (lhs.type == rhs.type) {
                // same string or both null
                //noinspection StringEquality
                if (lhs.data == rhs.data)
                    return 0;

                if (lhs.data == null)
                    return -1;

                if (rhs.data == null)
                    return 1;

                return lhs.data.compareToIgnoreCase (rhs.data);
            }

            // if they're not the same type, phone comes before
            return lhs.type == ContactMethodType.PHONE ? -1 : 1;
        }
    };
}
