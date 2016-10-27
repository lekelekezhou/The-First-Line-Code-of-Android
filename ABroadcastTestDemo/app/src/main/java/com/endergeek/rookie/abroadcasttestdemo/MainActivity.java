package com.endergeek.rookie.abroadcasttestdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private IntentFilter intentFilter;

//    private NetworkChangeReceiver networkChangeReceiver;

    private LocalReceiver localReceiver;
    private LocalBroadcastManager localBroadcastManager;

    /**
     * 当网络状态发生变化时，系统发出的广播值为 android.net.conn.CONNECTIVITY_CHANGE
     * addAction 以过滤出需要监听的广播
     * 通过 registerReceiver注册接收器和过滤器监听网络变化
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        localBroadcastManager = LocalBroadcastManager.getInstance(this); // 获取实例，与系统广播不同
        Button btnSendBroadcast = (Button) findViewById(R.id.btnSendBroadcast);
        btnSendBroadcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.endergeek.rookie.abroadcasttestdemo.LOCAL_BROADCAST");
                localBroadcastManager.sendBroadcast(intent); // 发送本地广播
//                Intent intent = new Intent("com.endergeek.rookie.abroadcasttestdemo.MY_BROADCAST");
//                sendOrderedBroadcast(intent, null); // 发送有序广播，需注册顺序: priority
////                sendBroadcast(intent); // 发送标准广播，context方法，监听此广播值的接收器会收到
            }
        });
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.endergeek.rookie.abroadcasttestdemo.LOCAL_BROADCAST");
        localReceiver = new LocalReceiver();
        localBroadcastManager.registerReceiver(localReceiver, intentFilter); // 注册本地广播监听器
//        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
//        networkChangeReceiver = new NetworkChangeReceiver();
//        registerReceiver(networkChangeReceiver, intentFilter);
    }

    /**
     * 动态注册的广播接收器需要通过 unregisterReceiver取消注册
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(localReceiver);
//        unregisterReceiver(networkChangeReceiver);
    }

    /**
     * NOTE: 需要设置 uses-permission: ACCESS_NETWORK_STATE
     * 不要在 onReceive中添加过多逻辑和耗时操作，因为在 BroadcastReceiver中是不允许开启线程的
     * 每当网络状态发生变化时，onReceive会执行
     * ConnectivityManager
     */
    class NetworkChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable()) {
                Toast.makeText(context, "Network available", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Network unavailable", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class LocalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "Local Broadcast received, propagates in local", Toast.LENGTH_SHORT).show();
        }
    }
}
