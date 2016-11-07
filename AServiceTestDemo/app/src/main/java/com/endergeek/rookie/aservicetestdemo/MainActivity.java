package com.endergeek.rookie.aservicetestdemo;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.nfc.Tag;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnStartService;

    private Button btnStopService;

    private Button btnBindService;

    private Button btnUnbindService;

    private Button btnStartIntentService;

    private boolean mIsBond = false;

    private MyService.DownloadBinder downloadBinder;

    /**
     * 创建匿名类，重写方法。通过向下转型得到DownloadBinder实例
     */
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            downloadBinder = (MyService.DownloadBinder) iBinder;
            downloadBinder.startDownload();
            downloadBinder.getProgress();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            downloadBinder = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStartService = (Button) findViewById(R.id.btn_start_service);
        btnStopService = (Button) findViewById(R.id.btn_stop_service);
        btnBindService = (Button) findViewById(R.id.btn_bind_service);
        btnUnbindService = (Button) findViewById(R.id.btn_unbind_service);
        btnStartIntentService = (Button) findViewById(R.id.btn_start_intent_service);
        btnStartService.setOnClickListener(this);
        btnStopService.setOnClickListener(this);
        btnBindService.setOnClickListener(this);
        btnUnbindService.setOnClickListener(this);
        btnStartIntentService.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start_service:
                Intent startService = new Intent(this, MyService.class);
                startService(startService); // 启动服务
                break;
            case R.id.btn_stop_service:
                Intent stopService = new Intent(this, MyService.class);
                stopService(stopService); // 停止服务
                break;
            case R.id.btn_bind_service:
                Intent bindService = new Intent(this, MyService.class);
                // args: Intent Service, ServiceConnection conn, Int flags(绑定后自动创建服务onCreate，onStartCommand不执行)
                bindService(bindService, connection, BIND_AUTO_CREATE);
                mIsBond = true;
                break;
            case R.id.btn_unbind_service:
                // 增加flag解决问题: Android java.lang.IllegalArgumentException: Service not registered
                if (mIsBond) {
                    unbindService(connection);
                    mIsBond = false;
                }
                break;
            case R.id.btn_start_intent_service:
                // 打印主线程id
                Log.d("MainActivity", "Thread id is" + Thread.currentThread().getId());
                Intent intentService = new Intent(this, MyIntentService.class);
                startService(intentService);
                break;
            default:
                break;
        }
    }
}
