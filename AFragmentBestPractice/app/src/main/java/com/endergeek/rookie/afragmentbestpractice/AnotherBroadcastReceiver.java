package com.endergeek.rookie.afragmentbestpractice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by wangsenhui on 10/27/16.
 */
public class AnotherBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Standard Broadcast received in Another Broadcast Receiver", Toast.LENGTH_SHORT).show();
    }
}
