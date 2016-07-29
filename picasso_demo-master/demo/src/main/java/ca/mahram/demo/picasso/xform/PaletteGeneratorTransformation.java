package ca.mahram.demo.picasso.xform;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.graphics.Palette;
import android.widget.ImageView;

import com.squareup.picasso.Transformation;

import java.util.Map;
import java.util.WeakHashMap;

/**
 Created by mahram on 15-03-02.
 */
public final class PaletteGeneratorTransformation
  implements Transformation {

    private static final Map<Bitmap, Palette> CACHE = new WeakHashMap<> ();
    private final int numColors;

    @Override public Bitmap transform (final Bitmap source) {
        if (!CACHE.containsKey (source)) {
            final Palette palette = numColors > 0
                                    ? Palette.generate (source, numColors)
                                    : Palette.generate (source);
            CACHE.put (source, palette);
        }

        return source;
    }

    @Override public String key () {
        return getClass ().getCanonicalName () + ":" + numColors;
    }

    public PaletteGeneratorTransformation () {
        this (0);
    }

    public PaletteGeneratorTransformation (final int c) {
        numColors = c;
    }

    public static abstract class Callback
      implements com.squareup.picasso.Callback {
        private final ImageView target;

        public Callback (final ImageView t) {
            target = t;
        }

        @Override public void onSuccess () {
            final Drawable targetDrawable = target.getDrawable ();

            if (null == targetDrawable || !(targetDrawable instanceof BitmapDrawable)) {
                onPalette (null);
                return;
            }

            onPalette (CACHE.get (((BitmapDrawable) target.getDrawable ()).getBitmap ()));
        }

        @Override public void onError () {
            onPalette (null);
        }

        protected abstract void onPalette (final Palette palette);
    }
}
