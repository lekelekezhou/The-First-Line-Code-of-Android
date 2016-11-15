package com.endergeek.rookie.aweatherfinaldemo.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;

import com.endergeek.rookie.aweatherfinaldemo.receiver.AutoUpdateReceiver;
import com.endergeek.rookie.aweatherfinaldemo.util.HttpCallbackListener;
import com.endergeek.rookie.aweatherfinaldemo.util.HttpUtil;
import com.endergeek.rookie.aweatherfinaldemo.util.NetworkUtility;

/**
 * Created by wangsenhui on 11/15/16.
 */
public class AutoUpdateService extends Service{
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                updateWeather();
            }
        }).start();
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int anHour = 8 * 60 * 60 * 1000; // 8小时更新一次
        long triggerAtTime = SystemClock.elapsedRealtime() + anHour;
        Intent i = new Intent(this, AutoUpdateReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
        return super.onStartCommand(intent, flags, startId);
    }

    private void updateWeather() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherCode = prefs.getString("weather_code", "");
        String address = "http://www.weather.com.cn/data/cityinfo/" + weatherCode + ".html";
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }

            @Override
            public void onFinish(String response) {
                NetworkUtility.handleWeatherResponse(AutoUpdateService.this, response);
            }
        });
    }
}
