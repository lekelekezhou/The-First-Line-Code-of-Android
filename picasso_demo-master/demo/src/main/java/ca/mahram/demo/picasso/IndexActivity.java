package ca.mahram.demo.picasso;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import butterknife.ButterKnife;
import butterknife.Bind;
import ca.mahram.demo.picasso.activity.ContactsListActivity;
import ca.mahram.demo.picasso.activity.PhotoListActivity;
import ca.mahram.demo.picasso.activity.PhotoSliderActivity;
import ca.mahram.demo.picasso.activity.PicassoOnceActivity;
import ca.mahram.demo.picasso.misc.ListItemRow;

public class IndexActivity
  extends ActionBarActivity
  implements AdapterView.OnItemClickListener {

    @Bind (android.R.id.list) ListView    list;
    private                         DemoAdapter adapter;

    private enum DemoActivity {
        USE_ONCE (R.string.picasso_once, PicassoOnceActivity.class),
        CONTACT_LIST (R.string.contacts, ContactsListActivity.class),
        PHOTO_SLIDER (R.string.photo_slider, PhotoSliderActivity.class),
        LIST (R.string.photo_list, PhotoListActivity.class);

        @StringRes final int                       title;
        final            Class<? extends Activity> activity;

        DemoActivity (final int titleRes, final Class<? extends Activity> activityClass) {
            title = titleRes;
            activity = activityClass;
        }
    }

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_index);
        ButterKnife.bind (this);

        adapter = new DemoAdapter ();
        list.setAdapter (adapter);
        list.setOnItemClickListener (this);
    }

    @Override public void onItemClick (final AdapterView<?> parent,
                                       final View view,
                                       final int position,
                                       final long id) {
        startActivity (new Intent (this, adapter.getItem (position).activity));
    }

    private class DemoAdapter
      extends BaseAdapter {
        private final DemoActivity[] activities = DemoActivity.values ();
        private final LayoutInflater inflater   = LayoutInflater.from (IndexActivity.this);

        @Override public int getCount () {
            return activities.length;
        }

        @Override public DemoActivity getItem (final int position) {
            return activities[position];
        }

        @Override public long getItemId (final int position) {
            return position;
        }

        private View newView (final ViewGroup parent) {
            final View v = inflater.inflate (R.layout.item_list, parent, false);
            v.setTag (new ListItemRow (v));
            return v;
        }

        @Override public View getView (final int position, final View convertView, final ViewGroup parent) {
            final View v = null == convertView ? newView (parent) : convertView;
            final DemoActivity activity = getItem (position);

            final ListItemRow row = (ListItemRow) v.getTag ();

            row.title.setText (activity.title);
            row.icon.setVisibility (View.GONE);

            return v;
        }
    }
}
