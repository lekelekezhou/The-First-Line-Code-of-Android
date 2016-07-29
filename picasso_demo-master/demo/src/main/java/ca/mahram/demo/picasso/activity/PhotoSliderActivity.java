package ca.mahram.demo.picasso.activity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import ca.mahram.demo.picasso.ImageManager;
import ca.mahram.demo.picasso.R;
import ca.mahram.demo.picasso.activity.base.BaseDemoActivity;

/**
 A simple photo slider
 Created by mahram on 15-03-02.
 */
public class PhotoSliderActivity
  extends BaseDemoActivity {

    @Override protected void onCreate (final Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);

        final ViewPager pager = new ViewPager (this);
        pager.setId (R.id.pager);
        setContentView (pager);

        pager.post (new Runnable () {
            @Override public void run () {
                pager.setAdapter (new PhotosAdapter (PhotoSliderActivity.this,
                                                     pager.getWidth (),
                                                     pager.getHeight ()));
            }
        });
    }

    static class PhotoPage {
        private final                       View        root;
        @Bind (R.id.photo)            ImageView   photo;
        @Bind (android.R.id.progress) ProgressBar progress;

        PhotoPage (final View page) {
            root = page;
            ButterKnife.bind (this, page);
        }
    }

    private static class PhotosAdapter
      extends android.support.v4.view.PagerAdapter {

        private final LayoutInflater inflater;

        private final Uri[]   imageUris = ImageManager.IMAGE_URIS;
        private final Picasso picasso   = ImageManager.get ().picasso;
        private final int pageWidth;
        private final int pageHeight;

        PhotosAdapter (final Context context, final int w, final int h) {
            inflater = LayoutInflater.from (context);
            pageWidth = w;
            pageHeight = h;
        }

        @Override public int getCount () {
            return imageUris.length;
        }

        @Override public boolean isViewFromObject (final View view, final Object object) {
            return (object instanceof PhotoPage) && ((PhotoPage) object).root == view;
        }

        @Override public PhotoPage instantiateItem (final ViewGroup container, final int position) {
            final PhotoPage page = new PhotoPage (inflater.inflate (R.layout.slider_page, container, false));

            picasso.load (imageUris[position])
                   .error (R.drawable.ic_alert_error)
                   .resize (pageWidth, pageHeight)
                   .centerInside ()
                   .into (page.photo, new Callback () {
                       @Override public void onSuccess () {
                           page.progress.setVisibility (View.GONE);
                       }

                       @Override public void onError () {
                           page.progress.setVisibility (View.GONE);
                       }
                   });

            container.addView (page.root);
            return page;
        }

        @Override public void destroyItem (final ViewGroup container, final int position, final Object object) {
            final PhotoPage page = (PhotoPage) object;
            container.removeView (page.root);
        }
    }
}
