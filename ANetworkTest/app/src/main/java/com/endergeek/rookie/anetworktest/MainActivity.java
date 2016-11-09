package com.endergeek.rookie.anetworktest;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ListMenuItemView;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;

/**
 * 发送Http请求两种方式: HttpClient和HttpURLConnection
 * 从 Android 6.0开始 HttpClient被移除，若要使用需要在build.gradle引入
 * android {
 *     useLibrary 'org.apache.http.legacy'
 * }
 * 解析XML: Pull 与 SAX 方式
 * 解析JSON: JSONObject 与 GSON，需要在app的build.gradle中引入 compile 'com.google.code.gson:gson:2.8.0'
 * HttpURLConnection请求方式: 子线程处理(MainActivity) 与 回调接口处理(HttpCallbackListener + HttpUtil)
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int SHOW_RESPONSE = 0;
    private static final String TAG = MainActivity.class.getSimpleName();

    private Button btnSendRequest;

    private EditText etInput;

    private TextView tvResponseText;

    private String inputString;

    private String address;

    /**
     * 相当于匿名内部类，使用的是外部类实例，若执行活动跳转，则不会释放，容易造成内存泄露
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_RESPONSE:
                    String response = (String) msg.obj;
                    // 在此进行UI操作，显示结果到界面
                    tvResponseText.setText(response);
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvResponseText = (TextView) findViewById(R.id.tv_response_text);
        btnSendRequest = (Button) findViewById(R.id.btn_send_request);
        etInput = (EditText) findViewById(R.id.et_send_request);
        btnSendRequest.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_send_request) {
            inputString = etInput.getText().toString();
            if (inputString.startsWith("http")) {
                address = inputString;
            } else {
                address = "http://" + inputString;
            }
            Log.d(TAG,address);
            /**
             * 请求方式二选一：多线程与回调接口
             * 注:此处onFinish与onError仍然是在子线程中执行，更新UI操作需要使用异步消息处理在主线程中更新
             */
//            sendRendRequestWithHttpURLConnection();
            HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
                @Override
                public void onFinish(String response) {
                    Log.d(TAG, "onfinish" + response);
                    parseJSONWithGSON(response);
                }

                @Override
                public void onError(Exception e) {
                    Log.e(TAG, String.valueOf(e));
                }
            });
        }
    }

    private void sendRendRequestWithHttpURLConnection() {
        /**
         * 开启线程来发起网络请求，耗时操作，依然类似内部类，可能造成内存泄露
         * 消息队列MessageQueue应在使用后**清空释放**
         * 一般将此处网络操作提取到一个公共类的静态方法中，详见HttpUtil与HttpCallbackListener
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    // "java.net.MalformedURLException: Protocol not found" 需要指定http://
//                    URL url = new URL("http://cn.bing.com");
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in = connection.getInputStream();
                    // 下面对获取到的输入流进行读取
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    Log.d(TAG, String.valueOf(response));
                    /**
                     * XML:Pull 或 SAX 解析方式二选一
                     */
//                    parseXMLWithPull(String.valueOf(response));
//                    parseXMLWithSAX(String.valueOf(response));
                    /**
                     * JSON: JSONObject 或 GSON 解析方式二选一
                     */
//                    parseJSONWithJSONObject(String.valueOf(response));
                    parseJSONWithGSON(String.valueOf(response));

                    Message message = new Message();
                    message.what = SHOW_RESPONSE;
                    // 将服务器返回的结果存放到Message中
                    message.obj = response.toString();
                    handler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    /**
     * GSON方式解析Json数据
     * @param jsonData
     */
    private void parseJSONWithGSON(String jsonData) {
        Gson gson = new Gson();
        List<App> appList = gson.fromJson(jsonData, new TypeToken<List<App>>(){}.getType());
        for (App app: appList) {
            Log.d(TAG, "id is " + app.getId());
            Log.d(TAG, "name is " + app.getName());
            Log.d(TAG, "version is" + app.getVersion());
        }
    }

    /**
     * 数据传入到JSONArray(格式：[{},{}])，接着循环遍历，从中取出的每个元素为JSONObject，其中包含id, name, version，使用getString取出即可
     * @param jsonData
     */
    private void parseJSONWithJSONObject(String jsonData) {
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("id");
                String name = jsonObject.getString("name");
                String version = jsonObject.getString("version");
                Log.d(TAG, "id is " + id);
                Log.d(TAG, "name is " + name);
                Log.d(TAG, "version is" + version);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建SAXParserFactory实例，借助其得到XMLReader对象
     * @param xmlData
     */
    private void parseXMLWithSAX(String xmlData) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            XMLReader xmlReader = factory.newSAXParser().getXMLReader();
            ContentHandler handler = new ContentHandler();
            // 将ContentHandler的实例设置到XMLReader中
            xmlReader.setContentHandler(handler);
            // 开始解析执行
            xmlReader.parse(new InputSource(new StringReader(xmlData)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * PULL解析方式
     *
     * 获取XmlPullParserFactory实例，借助其得到XmlPullParser对象，使用setInput设置StringReader类型xml数据
     * 解析：getEventType得到当前解析事件，END_DOCUMENT标记文档结尾，START_TAG，END_TAG标记头尾
     * @param xmlData
     */
    private void parseXMLWithPull(String xmlData) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(new StringReader(xmlData));
            int eventType = xmlPullParser.getEventType();
            String id = "";
            String name = "";
            String version = "";
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String nodeName = xmlPullParser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG: {
                        if ("id".equals(nodeName)) {
                            /**
                             * nextText:
                             * 若当前事件为START_TAG，下一元素为文本，则返回元素内容。
                             * 若下一事件为END_TAG，则返回空字符串。
                             * 否则将抛出异常，成功调用此函数后将定位到END_TAG
                             */
                            id = xmlPullParser.nextText();
                            Log.d(TAG, "ID:" +id); // 说明在此时获得id，下同
                        } else if ("name".equals(nodeName)) {
                            name = xmlPullParser.nextText();
                        } else if ("version".equals(nodeName)) {
                            version = xmlPullParser.nextText();
                        }
                        break;
                    }
                    case XmlPullParser.END_TAG:
                        if ("app".equals(nodeName)) {
                            Log.d(TAG, "id is " + id);
                            Log.d(TAG, "name is " + name);
                            Log.d(TAG, "version is " + version);
                        }
                        break;
                    default:
                        break;
                }
                eventType = xmlPullParser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
