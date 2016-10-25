package com.endergeek.rookie.afragmentbestpractice;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by wangsenhui on 10/24/16.
 */
public class NewsContentActivity extends AppCompatActivity {

    /**
     * 列出参数，方便其他调用指明参数
     * @param context
     * @param newsTitle
     * @param newsContent
     */
    public static void actionStart(Context context, String newsTitle, String newsContent) {
        Intent intent = new Intent(context, NewsContentActivity.class);
        intent.putExtra("news_title", newsTitle);
        intent.putExtra("news_content", newsContent);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_news);
        String newsTitle = getIntent().getStringExtra("news_title"); // 获取传入的新闻标题
        String newsContent = getIntent().getStringExtra("news_content"); // 获取传入的新闻内容
        NewsContentFragment newsContentFragment = (NewsContentFragment) getFragmentManager().findFragmentById(R.id.fragmentNewsContent);
        newsContentFragment.refresh(newsTitle, newsContent); // 刷新NewsContentFragment界面
    }
}
