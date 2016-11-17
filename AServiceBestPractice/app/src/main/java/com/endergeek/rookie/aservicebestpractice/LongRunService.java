package com.endergeek.rookie.aservicebestpractice;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Date;

/**
 * Created by wangsenhui on 11/8/16.
 */
public class LongRunService extends Service{

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 一旦启动本服务，在此设立的定时任务执行，getBroadcast使得onReceive会执行，将再次启动本服务。
     * 服务具体操作在此执行
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("LongRunService", "executed at " + new Date().toString());
            }
        }).start();
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        /**
         * 设置间隔低于5s，都会默认5s
         * 从android 4.4开始出发时间不准，系统耗电优化
         */
        int anInterval = 6 * 1000;
        long triggerAtTime = SystemClock.elapsedRealtime() + anInterval;

        Intent i = new Intent(this, AlarmReceiver.class);
        /**
         * getBroadcast: Retrieve a PendingIntent that will perform a broadcast, like calling Context.sendBroadcast().
         */
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);

        return super.onStartCommand(intent, flags, startId);
    }
}
