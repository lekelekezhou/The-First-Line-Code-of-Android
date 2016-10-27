package com.endergeek.rookie.abroadcasttestdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by wangsenhui on 10/26/16.
 * 不再使用内部类方式定义广播接收器，因需要在AndroidManifest.xml中注册类名
 */
public class BootCompleteReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Boot complete", Toast.LENGTH_SHORT).show();
    }
}
