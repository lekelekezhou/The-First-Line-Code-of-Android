package com.endergeek.rookie.aweatherfinaldemo.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.endergeek.rookie.aweatherfinaldemo.service.AutoUpdateService;

/**
 * Created by wangsenhui on 11/15/16.
 */
public class AutoUpdateReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, AutoUpdateService.class);
        context.startService(i);
    }
}
