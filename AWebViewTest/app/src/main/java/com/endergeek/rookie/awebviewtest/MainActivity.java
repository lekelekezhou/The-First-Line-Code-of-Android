package com.endergeek.rookie.awebviewtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true); // 设置浏览器属性，支持js。
        webView.setWebViewClient(new WebViewClient()); // 当需要跳转时，仍然在webview显示
        webView.loadUrl("http://cn.bing.com");

    }
}
