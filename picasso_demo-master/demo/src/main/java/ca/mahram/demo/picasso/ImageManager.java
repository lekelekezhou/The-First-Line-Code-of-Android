package ca.mahram.demo.picasso;

import android.content.Context;
import android.net.Uri;

import com.squareup.picasso.Picasso;

import java.util.Random;

/**
 Created by mahram on 15-03-02.
 */
public final class ImageManager {

    private static ImageManager instance;

    public final Picasso picasso;

    private static final Random RND = new Random (System.currentTimeMillis ());

    private static final Uri BASE_BAD_URI = Uri.parse ("http://mahram.ca/nonexistent/images");

    public static final Uri[] IMAGE_URIS =
      {
        Uri.parse ("https://farm4.staticflickr.com/3700/10165441826_8612d74683_k_d.jpg"),
        Uri.parse ("https://farm8.staticflickr.com/7365/9291665316_2affe13d8b_k_d.jpg"),
        Uri.parse ("https://farm4.staticflickr.com/3791/9288885061_67c5884fc2_k_d.jpg"),
        Uri.parse ("https://farm4.staticflickr.com/3751/9291680816_1c636a3c7e_k_d.jpg"),
        Uri.parse ("https://farm6.staticflickr.com/5502/9291682276_827dd4a483_k_d.jpg"),
        Uri.parse ("https://farm4.staticflickr.com/3674/9291726824_49b1640378_k_d.jpg"),
        Uri.parse ("https://farm3.staticflickr.com/2875/9288955003_a28cf0a3cf_k_d.jpg"),
        Uri.parse ("https://farm4.staticflickr.com/3735/9561989032_5a754f6319_k_d.jpg"),
        Uri.parse ("https://farm8.staticflickr.com/7194/14096628066_f5dc0da114_k_d.jpg"),
        Uri.parse ("https://lh6.googleusercontent" +
                   ".com/-vu3-H142_R8/U8Sz81jdWmI/AAAAAAAAm8U/qI3PVfpyEL4/w1302-h868-no/_DSC3244.jpg"),
        Uri.parse ("https://lh6.googleusercontent" +
                   ".com/-1CNuae2Sjg4/U2yDjEzsI-I/AAAAAAAAkjs/bp66xSVZS2c/w1302-h868-no/_DSC1523.jpg"),
        Uri.parse ("https://lh6.googleusercontent" +
                   ".com/-pCSNJqj16y4/U2yDCFLzR1I/AAAAAAAAknw/jo07-88TX5g/w579-h868-no/_DSC1503.jpg")
      };

    public ImageManager (final Context context) {
        picasso = new Picasso.Builder (context.getApplicationContext ())
                    .indicatorsEnabled (BuildConfig.DEBUG)
                    .loggingEnabled (BuildConfig.DEBUG)
                    .build ();

        Picasso.setSingletonInstance (picasso);
    }

    public static ImageManager get () {
        if (null == instance) {
            throw new NullPointerException ("instance");
        }
        return instance;
    }

    static void init (final Context context) {
        instance = new ImageManager (context);
    }

    /**
     @return a random uri from known valid image uris
     */
    public static Uri randomUri () {
        return IMAGE_URIS[RND.nextInt (IMAGE_URIS.length)];
    }

    /**
     Return an image uri with a possibility of an invalid image uri
     @param errorProbability the probablility of getting an invalid uri
     @return a valid or invalid image uri
     */
    public static Uri randomUri (final float errorProbability) {
        return RND.nextFloat () < errorProbability
               ? badUri ()
               : IMAGE_URIS[RND.nextInt (IMAGE_URIS.length)];
    }

    /**
     @return a known invalid image uri
     */
    public static Uri badUri () {
        return BASE_BAD_URI.buildUpon ().appendPath (String.valueOf (System.currentTimeMillis ())).build ();
    }
}
