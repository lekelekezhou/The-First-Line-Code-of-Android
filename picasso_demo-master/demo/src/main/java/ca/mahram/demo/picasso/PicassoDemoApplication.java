package ca.mahram.demo.picasso;

import android.app.Application;

/**
 Created by mahram on 15-03-02.
 */
public class PicassoDemoApplication extends Application {

    @Override public void onCreate () {
        super.onCreate ();
        ImageManager.init (this);
    }

}
