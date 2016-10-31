package com.endergeek.rookie.adatapersistence4sharedpreferences;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by wangsenhui on 10/27/16.
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
