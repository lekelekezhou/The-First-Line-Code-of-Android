package ca.mahram.demo.picasso.activity;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import ca.mahram.demo.picasso.ImageManager;
import ca.mahram.demo.picasso.R;
import ca.mahram.demo.picasso.activity.base.BaseDemoActivity;
import ca.mahram.demo.picasso.misc.Utils;
import ca.mahram.demo.picasso.xform.PaletteGeneratorTransformation;

import static ca.mahram.demo.picasso.misc.Utils.firstNonNull;

/**
 A list style recycler view
 Created by mahram on 15-03-02.
 */
public class PhotoListActivity
  extends BaseDemoActivity {

    @Override protected void onCreate (final Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        final RecyclerView rv = new RecyclerView (this);
        rv.setId (android.R.id.list);
        setContentView (rv);

        rv.setLayoutManager (new LinearLayoutManager (this, LinearLayoutManager.VERTICAL, false));
        rv.setAdapter (new PhotosAdapter (this));
    }

    static class PhotoCard
      extends RecyclerView.ViewHolder {

        @Bind (android.R.id.title)    TextView    title;
        @Bind (R.id.photo)            ImageView   photo;
        @Bind (android.R.id.progress) ProgressBar progress;
        @Bind (R.id.text)             TextView    text;

        public PhotoCard (final View itemView) {
            super (itemView);
            ButterKnife.bind (this, itemView);
        }
    }

    static class PhotosAdapter
      extends RecyclerView.Adapter<PhotoCard> {

        final LayoutInflater inflater;
        private final Uri[]   imageUris = ImageManager.IMAGE_URIS;
        private final Picasso picasso   = ImageManager.get ().picasso;

        private final int defaultTextBackground;
        private final int defaultTextColor;

        private final int defaultTitleTextColor;
        private final int defaultTitleBackground;

        PhotosAdapter (final Context context) {
            inflater = LayoutInflater.from (context);

            final Resources resources = context.getResources ();
            defaultTextBackground = resources.getColor (R.color.default_text_bg);
            defaultTextColor = resources.getColor (R.color.default_text_fg);
            defaultTitleTextColor = resources.getColor (R.color.accent);
            defaultTitleBackground = resources.getColor (R.color.primary);
        }

        @Override public PhotoCard onCreateViewHolder (final ViewGroup parent, final int viewType) {
            return new PhotoCard (inflater.inflate (R.layout.photo_card, parent, false));
        }

        @Override public void onBindViewHolder (final PhotoCard holder, final int position) {
            final Uri imageUri = imageUris[position];

            holder.title.setBackgroundColor (defaultTitleBackground);
            holder.title.setTextColor (defaultTitleTextColor);

            holder.text.setBackgroundColor (defaultTextBackground);
            holder.text.setTextColor (defaultTextColor);

            picasso.load (imageUri)
                   .error (R.drawable.ic_alert_error)
                   .transform (new PaletteGeneratorTransformation ())
                   .into (holder.photo, new Palettier (holder));
        }

        @Override public int getItemCount () {
            return imageUris.length;
        }
    }

    private static void setTextColors (final TextView text, final int bgColor, final int fgColor) {
        text.setBackgroundColor (bgColor);
        text.setTextColor (fgColor);

        text.setText (text.getContext ().getString (R.string.swatch,
                                                    Integer.toHexString (bgColor),
                                                    Integer.toHexString (fgColor)));
    }

    private static void setNoSwatch (final TextView text) {
        text.setText (R.string.no_swatches);
    }

    static class Palettier
      extends PaletteGeneratorTransformation.Callback {
        private final PhotoCard card;

        private static final float ALPHA = 0.8f;

        Palettier (final PhotoCard c) {
            super (c.photo);
            card = c;
        }

        @Override public void onSuccess () {
            card.progress.setVisibility (View.GONE);
            super.onSuccess ();
        }

        @Override public void onError () {
            card.progress.setVisibility (View.GONE);
            super.onError ();
        }

        @Override protected void onPalette (final Palette palette) {
            if (null == palette)
                return;

            final Palette.Swatch bg = firstNonNull (palette.getLightMutedSwatch (),
                                                    palette.getMutedSwatch (),
                                                    palette.getDarkMutedSwatch ());
            if (null != bg) {
                final int bgColor = bg.getRgb ();

                setTextColors (card.text, bg.getBodyTextColor (), Utils.applyAlpha (bgColor, ALPHA));
            } else
                setNoSwatch (card.text);

            final Palette.Swatch fg = firstNonNull (palette.getDarkVibrantSwatch (),
                                                    palette.getVibrantSwatch (),
                                                    palette.getLightVibrantSwatch ());

            if (null != fg) {
                setTextColors (card.title, fg.getRgb (), fg.getTitleTextColor ());
            } else {
                setNoSwatch (card.title);
            }
        }
    }
}
