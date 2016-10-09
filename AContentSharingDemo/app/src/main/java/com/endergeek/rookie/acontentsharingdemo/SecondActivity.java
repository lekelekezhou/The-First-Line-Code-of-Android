package com.endergeek.rookie.acontentsharingdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by wangsenhui on 10/8/16.
 */
public class SecondActivity extends AppCompatActivity {

    /**
     * Activity 需要在AndroidManifest.xml中注册
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondary);
        Button buttonGoBack = (Button) findViewById(R.id.btn_secondary);
        buttonGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("data_return", "Hello FirstActivity");
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }

    /**
     * 重写Back按钮行为，使SecondActivity Button与Back行为一致
     */
    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("data_return", "Hello FirstActivity");
        setResult(RESULT_OK, intent);
        finish();
    }
}
