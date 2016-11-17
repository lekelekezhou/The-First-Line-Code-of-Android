package com.endergeek.rookie.aservicetestdemo;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 * Created by wangsenhui on 11/7/16.
 */
public class MyService extends Service{

    private DownloadBinder mBinder = new DownloadBinder();

    private static final String TAG = MyService.class.getSimpleName();

    class DownloadBinder extends Binder{
        public void startDownload() {
            Log.w(TAG, "startDownload executed");
        }

        public int getProgress() {
            Log.w(TAG, "getProgress executed");
            return 0;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        /**
         * 提供前台通知，此处与 Notification Demo创建通知有所区别
         * startForeground 属于Service，需要通过调用Service才可使用
         */
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Notification Title")
                .setContentText("Notification Content")
                .setContentIntent(pi).build();
        startForeground(1, notification);
        Log.w(TAG, "onCreated");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.w(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.w(TAG, "onDestroy");
    }

}
