package com.endergeek.rookie.afragmenttestdemo;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnFragmentLeft = (Button) findViewById(R.id.btnFragmentLeft);
        btnFragmentLeft.setOnClickListener(this);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int w_screen = dm.widthPixels;
        int h_screen = dm.heightPixels;
        Log.d(TAG, "w_screen" + w_screen + " " + "h_screen" + h_screen);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnFragmentLeft:
                AnotherRightFragment fragment = new AnotherRightFragment(); // 创建待添加的fragment实例
                FragmentManager fragmentManager = getSupportFragmentManager(); // 获取FragmentManager
                FragmentTransaction transaction = fragmentManager.beginTransaction(); // 开始事务
                transaction.replace(R.id.frameLayoutRight, fragment); // 使用replace向容器中加入fragment，需要容器id和待添加的fragment实例
                transaction.addToBackStack(null); // 添加到返回栈，null为名称
                transaction.commit(); // 提交事务
                break;
            default:
                break;
        }
    }
}
