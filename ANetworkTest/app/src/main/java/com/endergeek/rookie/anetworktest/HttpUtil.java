package com.endergeek.rookie.anetworktest;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by wangsenhui on 11/9/16.
 */
public class HttpUtil {
    private static final String TAG = HttpUtil.class.getSimpleName();

    public static void sendHttpRequest(final String address, final HttpCallbackListener listener) {
         new Thread(new Runnable() {
             @Override
             public void run() {
                 HttpURLConnection connection = null;
                 try {
                     URL url = new URL(address);
                     connection = (HttpURLConnection) url.openConnection();
                     connection.setRequestMethod("GET");
                     connection.setConnectTimeout(8000);
                     connection.setReadTimeout(8000);
                     connection.setDoInput(true);
                     /**
                      * 警告：使用POST使才使用此方法，书上zz
                      * connection.setDoOutput(true);
                      * 使用GET时会返回错误码405：用来访问本页面的HTTP谓词不被允许(方法不被允许)
                      */
                     InputStream in = connection.getInputStream();
                     BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                     StringBuilder response = new StringBuilder();
                     String line;
                     while ((line = reader.readLine()) != null) {
                         response.append(line);
                         Log.d(TAG, "response:" + response);
                     }
                     // 回调onFinish方法
                     if (listener != null) {
                         listener.onFinish(response.toString());
                     }
                 } catch (Exception e) {
                     // 回调onError方法
                     if (listener != null) {
                         listener.onError(e);
                     }
                 } finally {
                     if (connection != null) {
                         connection.disconnect();
                     }
                 }
             }
         }).start();
    }
}
