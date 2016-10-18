package com.endergeek.rookie.anuibestpractice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lvMessageList;

    private EditText etMessageInput;

    private Button btnSend;

    private MessageAdapter adapter;

    private List<Message> messageList = new ArrayList<Message>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initMessages(); // 初始化消息
        adapter = new MessageAdapter(MainActivity.this, R.layout.item_list_message, messageList);

        etMessageInput = (EditText) findViewById(R.id.etMessageInputArea);
        btnSend = (Button) findViewById(R.id.btnMessageSend);
        lvMessageList = (ListView) findViewById(R.id.lvMessageList);

        lvMessageList.setAdapter(adapter);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = etMessageInput.getText().toString();
                if (!"".equals(content)) {
                    Message msg = new Message(content, Message.TYPE_SENT);
                    messageList.add(msg);
                    /**
                     * 当有新消息时，刷新ListView中的显示
                     * notifyDataSetChanged方法通过一个外部的方法控制如果适配器的内容改变时
                     * 需要强制调用getView来刷新每个Item的内容。
                     */
                    adapter.notifyDataSetChanged();
                    lvMessageList.setSelection(messageList.size()); // 将ListView定位到最后一行
                    etMessageInput.setText(""); // 清空输入框中的内容
                }
            }
        });
    }

    private void initMessages() {
        Message msg1 = new Message("Hello ", Message.TYPE_RECEIVED);
        Message msg2 = new Message("Hi ", Message.TYPE_SENT);
        Message msg3 = new Message("How r u ", Message.TYPE_RECEIVED);
        messageList.add(msg1);
        messageList.add(msg2);
        messageList.add(msg3);
    }
}
