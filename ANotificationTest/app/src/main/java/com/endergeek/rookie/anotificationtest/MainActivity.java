package com.endergeek.rookie.anotificationtest;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnSendNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSendNotification = (Button) findViewById(R.id.btnSendNotification);
        btnSendNotification.setOnClickListener(this);
    }

    /**
     * 点击：添加通知同时振动、亮灯、铃声，点击通知：进入SecondActivity，点击返回，进入Home
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSendNotification:
                /**
                 * NotificationManager 对通知进行管理，使用Context的getSystemService获取指定服务。
                 * 通知被点击后应当取消setAutoCancel，比在其他活动中cancel(id)管用
                 * vibrates[2k]:手机静止时长，vibrates[2k+1]:手机振动时长,k=0,1,2,3... 需要use-permission:VIBRATE
                 * setLights(ledARGB, ledOnMS, ledOffMS)
                 */
                Uri soundUri = Uri.fromFile(new File("/system/media/audio/ringtones/Andromeda.ogg"));
                long[] vibrates = {0, 1000, 1000, 1000};
                NotificationCompat.Builder notification = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("My Notification")
                        .setContentText("Content")
                        .setAutoCancel(true)
                        .setSound(soundUri)
                        .setVibrate(vibrates)
                        .setLights(Color.GREEN, 1000, 1000);

                Intent resultIntent = new Intent(this, ResultActivity.class);

                /**
                 * TaskStackBuilder对象将包含启动活动的返回栈，以确保正确返回到Home
                 */
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
                // 给 Intent添加返回栈（不是Intent自身）
                stackBuilder.addParentStack(ResultActivity.class);
                // 将Intent添加到返回栈栈顶
                stackBuilder.addNextIntent(resultIntent);

                /**
                 * 另一种PendingIntent实现方式
                 * PendingIntent pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                 */
                PendingIntent resultPendingIntent =
                        stackBuilder.getPendingIntent(
                                0,
                                PendingIntent.FLAG_UPDATE_CURRENT
                        );

                notification.setContentIntent(resultPendingIntent);
                NotificationManager mNotificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                // 9 为 id，允许稍后更新通知
                mNotificationManager.notify("Tag", 9, notification.build());
                // 另一种通知实现方式
//                Notification notification = builder.build();
//                NotificationManager notificationManager = (NotificationManager) context
//                        .getSystemService(Context.NOTIFICATION_SERVICE);
//                notificationManager.notify(New_PEOPLE_GUIDE_ID, notification);
                break;
            default:
                break;
        }
    }
}
