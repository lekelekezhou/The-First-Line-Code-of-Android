package com.endergeek.rookie.athreaddemo;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Android的UI线程不安全，更新UI元素需要在主线程中进行
 * 本例使用Handler处理异步消息，也可以使用继承AsyncTask<Params, Progress, Result>，并在doInBackground()中
 * 执行耗时任务，onProgressUpdate()中执行UI操作，onPostExecute()中执行任务结束工作
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int UPDATE_TEXT = 1; // 更新TextView动作

    private Button btnChangeText;

    private TextView tvText;

    /**
     * 重写父类handleMessage方法，在主线程中运行
     */
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_TEXT:
                    tvText.setText("Text Changed Using Handler");
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnChangeText = (Button) findViewById(R.id.btn_change_text);
        tvText = (TextView) findViewById(R.id.tv_text);
        btnChangeText.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_change_text:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
//                        // Only the original thread that created a view hierarchy can touch its views.
//                        tvText.setText("Text Changed");
                        Message message = new Message(); // 创建Message(android.os.Message)对象
                        message.what = UPDATE_TEXT;
                        handler.sendMessage(message); // 将Message对象发送出去
                    }
                }).start();
                break;
            default:
                break;
        }
    }
}
