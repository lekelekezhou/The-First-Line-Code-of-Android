package com.endergeek.rookie.acontentsharingdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by wangsenhui on 10/8/16.
 */
public class SecondActivity extends AppCompatActivity {

    // Activity 需要在AndroidManifest.xml中注册
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondary);
        Button buttonGotoSecond = (Button) findViewById(R.id.btn_secondary);
        buttonGotoSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
