package ca.mahram.demo.picasso.misc;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.DrawableRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ca.mahram.demo.picasso.ImageManager;
import ca.mahram.demo.picasso.R;

/**
 Created by mahram on 15-03-03.
 */
public class ContactsAdapter
  extends BaseAdapter {

    @DrawableRes
    private static final int DEFAULT_PHOTOS[] = {
                                                  R.drawable.ic_default_1,
                                                  R.drawable.ic_default_2,
                                                  R.drawable.ic_default_3,
                                                  R.drawable.ic_default_4,
                                                  R.drawable.ic_default_5,
                                                  R.drawable.ic_default_6,
                                                  R.drawable.ic_default_7,
                                                  R.drawable.ic_default_8,
                                                  R.drawable.ic_default_9,
                                                  R.drawable.ic_default_10
    };

    private final LayoutInflater inflater;
    private final List<Contact>  contacts;
    private final Picasso        picasso;

    public ContactsAdapter (final Context context) {
        super ();
        inflater = LayoutInflater.from (context);
        contacts = new ArrayList<> ();
        picasso = ImageManager.get ().picasso;
    }

    public ContactsAdapter (final Context context, final Cursor cursor) {
        this (context);
        Collections.addAll (contacts, Contact.extractAll (cursor));
    }

    public void update (final Cursor cursor) {
        final Contact[] updated = Contact.extractAll (cursor);
        contacts.clear ();
        Collections.addAll (contacts, updated);
        notifyDataSetChanged ();
    }

    public void clear () {
        contacts.clear ();
        notifyDataSetChanged ();
    }

    @Override public int getCount () {
        return contacts.size ();
    }

    @Override public Contact getItem (final int position) {
        return contacts.get (position);
    }

    @Override public long getItemId (final int position) {
        return getItem (position).id;
    }

    @Override public View getView (final int position, final View convertView, final ViewGroup parent) {
        final View v = null == convertView ? newView (parent) : convertView;
        final Contact contact = getItem (position);

        final ListItemRow tag = (ListItemRow) v.getTag ();
        tag.title.setText (contact.getName() );
        picasso.load (contact.getUri ())
               .placeholder (DEFAULT_PHOTOS[position % DEFAULT_PHOTOS.length])
               .into (tag.icon);

        return v;
    }

    private View newView (final ViewGroup parent) {
        final View v = inflater.inflate (R.layout.item_list, parent, false);
        v.setTag (new ListItemRow (v));
        return v;
    }
}
