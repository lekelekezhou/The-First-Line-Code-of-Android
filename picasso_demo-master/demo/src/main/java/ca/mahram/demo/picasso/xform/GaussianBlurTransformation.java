package ca.mahram.demo.picasso.xform;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.Element;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptIntrinsicBlur;

import com.squareup.picasso.Transformation;

/**
 Source: https://gist.github.com/Mariuxtheone/903c35b4927c0df18cf8
 Created by mahram on 15-03-02.
 */
public class GaussianBlurTransformation
  implements Transformation {

    private final float radius;
    private final Context context;

    public GaussianBlurTransformation (final Context c, final float r) {
        context = c;
        radius = r;
    }

    @Override public Bitmap transform (final Bitmap source) {
        final Bitmap blurred = Bitmap.createBitmap (source.getWidth (), source.getHeight (), source.getConfig ());

        final RenderScript rs = RenderScript.create (context);
        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create (rs, Element.U8_4 (rs));

        final Allocation in = Allocation.createFromBitmap (rs, source);
        final Allocation out = Allocation.createFromBitmap (rs, blurred);
        blur.setRadius (radius);
        blur.setInput (in);
        blur.forEach (out);
        out.copyTo (blurred);

        source.recycle ();
        rs.destroy ();

        return blurred;
    }

    @Override public String key () {
        return getClass ().getCanonicalName () + ":" + radius;
    }
}
