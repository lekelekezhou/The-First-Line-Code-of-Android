package ca.mahram.demo.picasso.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import ca.mahram.demo.picasso.ImageManager;
import ca.mahram.demo.picasso.R;
import ca.mahram.demo.picasso.activity.base.BaseDemoActivity;

/**
 Created by mahram on 15-03-02.
 */
public class PicassoOnceActivity
  extends BaseDemoActivity {

    private static final int ICON2_PIXEL_WIDTH  = 800;
    private static final int ICON2_PIXEL_HEIGHT = 600;

    @Bind (android.R.id.icon)  ImageView icon;
    @Bind (android.R.id.icon1) ImageView icon1;
    @Bind (android.R.id.icon2) ImageView icon2;

    @Override protected void onCreate (final Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_one_off);
        ButterKnife.bind (this);
        loadRandomImages ();
    }

    private void loadRandomImages () {
        Picasso.with (this)
               .load (ImageManager.randomUri ())
               .placeholder (R.drawable.ic_file_cloud_download)
               .into (icon);

        // 50% chance of bad picture uris
        Picasso.with (this)
               .load (ImageManager.randomUri (0.5f))
               .placeholder (R.drawable.ic_file_cloud_download)
               .error (R.drawable.ic_alert_error)
               .resizeDimen (R.dimen.hundred_dips, R.dimen.sixty_dips)
               .centerCrop ()
               .into (icon1);

        Picasso.with (this)
               .load (ImageManager.randomUri ())
               .resize (ICON2_PIXEL_WIDTH, ICON2_PIXEL_HEIGHT)
               .centerInside ()
               .placeholder (R.drawable.ic_file_cloud_download)
               .into (icon2);
    }

    @Override public boolean onOptionsItemSelected (final MenuItem item) {
        super.onOptionsItemSelected (item);

        if (item.getItemId () != R.id.action_refresh) {
            return false;
        }

        loadRandomImages ();

        return true;
    }

    @Override public boolean onCreateOptionsMenu (final Menu menu) {
        getMenuInflater ().inflate (R.menu.menu_singleton, menu);
        return menu.hasVisibleItems ();
    }
}
