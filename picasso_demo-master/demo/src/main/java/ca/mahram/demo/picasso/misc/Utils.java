package ca.mahram.demo.picasso.misc;

import android.graphics.Color;

/**
 Created by Copolii on 3/11/2015.
 */
public class Utils {
    public static int applyAlpha (final int color, final float alpha) {
        return Color.argb ((int) (alpha * 255),
                           Color.red (color),
                           Color.green (color),
                           Color.blue (color));
    }

    public static <T> T firstNonNull (final T... ts) {
        // we only need one good swatch
        for (final T t : ts) {
            if (null != t)
                return t;
        }

        return null;
    }
}
