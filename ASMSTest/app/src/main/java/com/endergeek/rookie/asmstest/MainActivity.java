package com.endergeek.rookie.asmstest;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

// 接收短信广播测试成功
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int REQUEST_PERMISSION = 1;

    private static final String[] PERMISSION_SMS = {
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.SEND_SMS
    } ;

    private static final String TAG = MainActivity.class.getSimpleName();

    private TextView tvSend;

    private TextView tvContent;

    private EditText etTo;

    private EditText etMsgInput;

    private Button btnSend;

    // 短信接收广播
    private IntentFilter receiveFilter;
    private MessageReceiver messageReceiver;

    // 查看短信是否发送成功
    private IntentFilter sendFilter;
    private SendStatusReceiver sendStatusReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkStoragePermissions(this);
        tvSend = (TextView) findViewById(R.id.tvSend);
        tvContent = (TextView) findViewById(R.id.tvContent);
        etTo = (EditText) findViewById(R.id.etTo);
        etMsgInput = (EditText) findViewById(R.id.etMsgInput);
        btnSend = (Button) findViewById(R.id.btnSend);
        btnSend.setOnClickListener(this);

        // 查看短信是否发送成功
        sendFilter = new IntentFilter();
        sendFilter.addAction("SENT_SMS_ACTION");
        sendStatusReceiver = new SendStatusReceiver();
        registerReceiver(sendStatusReceiver, sendFilter);

        // 短信接收广播
        receiveFilter = new IntentFilter();
        receiveFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        messageReceiver = new MessageReceiver();
        registerReceiver(messageReceiver, receiveFilter);
    }

    @Override
    public void onClick(View view) {
        try {
            SmsManager smsManager = SmsManager.getDefault(); // 获取SmsManager实例
            Intent sentIntent = new Intent("SENT_SMS_ACTION");
            /**
             * 慎重测试！！！延迟高
             *
             * sendIntent 获取PendingIntent对象，为发送后接收的广播信息，
             * deliveryIntent 获取PendingIntent对象，为送达后接收的广播信息，
             * 参数说明sendTextMessage(destinationAddress, sourceAddress, textMessage, (PendingIntent)sendIntent, (PendingIntent)deliveryIntent)
             * 由于160字符限制，发送多条短信使用:sendMultipartTextMessage()
             */
            PendingIntent pi = PendingIntent.getBroadcast(this, 0, sentIntent, 0);
            Log.w(TAG, etTo.getText().toString() + etMsgInput.getText().toString());
            smsManager.sendTextMessage(etTo.getText().toString(), null, etMsgInput.getText().toString(), pi, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class MessageReceiver extends BroadcastReceiver {
        /**
         * 从intent中取出Bundle对象，使用pdu迷药提取pdus数组，每个pdu都表示一条短信
         * createFromPdu方法将每一个pdu字节数组转换为SmsMessage对象
         * Address为号码，MessageBody为短信内容，将每个SmsMessage对象中的短信内容拼接起来组成完整短信
         * @param context
         * @param intent
         */
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            Object[] pdus = (Object[]) bundle.get("pdus");
            SmsMessage[] messages = new SmsMessage[pdus.length];
            for (int i = 0; i < messages.length; i++) {
                messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                Log.w(TAG, String.valueOf(messages[i]) +  " " + pdus[i]);
            }
            String address = messages[0].getOriginatingAddress();
            String fullMessage = "";
            for (SmsMessage message: messages) {
                fullMessage += message.getMessageBody();
            }
            tvSend.setText(address);
            tvContent.setText(fullMessage);
        }
    }

    private class SendStatusReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            // getResultCode() 获取返回状态值
            if (getResultCode() == RESULT_OK) {
                Toast.makeText(context, "send Succeeded", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Send Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(messageReceiver);
        unregisterReceiver(sendStatusReceiver);
    }

    public static void checkStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permissionR = ActivityCompat.checkSelfPermission(activity, Manifest.permission.RECEIVE_SMS);
        int permissionS = ActivityCompat.checkSelfPermission(activity, Manifest.permission.SEND_SMS);

        if (permissionR != PackageManager.PERMISSION_GRANTED || permissionS != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSION_SMS,
                    REQUEST_PERMISSION
            );
        }
    }
}
