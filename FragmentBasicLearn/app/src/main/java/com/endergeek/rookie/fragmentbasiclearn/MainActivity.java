package com.endergeek.rookie.fragmentbasiclearn;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_articles);

        // Check that the activity is using the layout version with
        // the fragment_container FragmentLayout
        if (findViewById(R.id.fragment_container) != null) {
            // However,if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we can end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new fragment to be replaced in the activity layout
            HeadlinesFragment firstFragment = new HeadlinesFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as fragments.
            firstFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();

        }
    }
}
