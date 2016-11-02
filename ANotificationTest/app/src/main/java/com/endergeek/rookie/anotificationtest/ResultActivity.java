package com.endergeek.rookie.anotificationtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by wangsenhui on 11/2/16.
 */
public class ResultActivity extends AppCompatActivity{
    /**
     * Activity 需要在AndroidManifest.xml中注册
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondary);
//        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        notificationManager.cancel(9);

        Button buttonGoBack = (Button) findViewById(R.id.btnBack);
        buttonGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    /**
     * 重写Back按钮行为，使SecondActivity Button与Back行为一致
     */
    @Override
    public void onBackPressed() {
        finish();
    }
}
