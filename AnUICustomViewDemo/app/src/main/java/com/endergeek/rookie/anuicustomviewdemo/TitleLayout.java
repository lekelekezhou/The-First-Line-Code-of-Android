package com.endergeek.rookie.anuicustomviewdemo;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by wangsenhui on 10/14/16.
 * 标题栏控件
 */
public class TitleLayout extends LinearLayout{
    /**
     * 重写LinearLayout构造函数，在布局中引入TitleLayout就会调用此构造函数
     * 使用LayoutInflater对标题栏布局进行动态加载
     * @param context
     * @param attrs
     */
    public TitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.activity_title_layout, this);

        Button btnTitleBack = (Button) findViewById(R.id.btnTitleBack);
        Button btnTitleEdit = (Button) findViewById(R.id.btnTitleEdit);
        btnTitleBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Activity) getContext()).finish();
            }
        });
        btnTitleEdit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Edit", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
