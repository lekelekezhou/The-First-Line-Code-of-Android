package com.endergeek.rookie.anactivitylifecycledemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

/**
 * Created by sennhviwang on 10/10/16.
 * Namely the ThirdActivity
 */
public class DialogActivity extends Activity{
    private static final String TAG = "com.endergeek.rookie.anactivitylifecycledemo.dialogactivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Task id is " + getTaskId());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dialog);
    }
}
