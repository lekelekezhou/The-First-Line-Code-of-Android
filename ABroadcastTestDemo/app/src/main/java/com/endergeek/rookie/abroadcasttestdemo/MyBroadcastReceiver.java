package com.endergeek.rookie.abroadcasttestdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by wangsenhui on 10/27/16.
 */
public class MyBroadcastReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast toast = Toast.makeText(context, "Standard Broadcast received in Custom BroadcastReceiver", Toast.LENGTH_SHORT);
        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
        v.setTextColor(Color.RED);
        v.setBackgroundColor(Color.LTGRAY);
        toast.show();
        abortBroadcast(); // 有序广播，优先级较高，可以截断广播
    }
}
