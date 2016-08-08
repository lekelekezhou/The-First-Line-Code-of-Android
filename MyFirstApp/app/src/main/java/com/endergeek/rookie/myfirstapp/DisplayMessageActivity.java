package com.endergeek.rookie.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class DisplayMessageActivity extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        Intent intent = getIntent();
        // Extract the message delivered by MyActivity with the getStringExtra() method.
        String message = intent.getStringExtra(MyActivity.EXTRA_MESSAGE);

        // Display text message
        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText(message);

        // Add the TextView to the RelativeLayout identified by R.id.content.
        RelativeLayout layout = (RelativeLayout)findViewById(R.id.content);
        layout.addView(textView);
    }
}
