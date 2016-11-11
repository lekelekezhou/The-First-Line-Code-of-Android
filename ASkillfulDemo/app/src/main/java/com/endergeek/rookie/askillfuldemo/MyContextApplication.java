package com.endergeek.rookie.askillfuldemo;

import android.app.Application;
import android.content.Context;

/**
 * Created by wangsenhui on 11/10/16.
 * 全局获取Context技巧:
 * 告知系统初始化程序时应初始化此类，在AndroidManifest.xml中指定**完整包名**
 * <application android:name="com.endergeek.rookie.askillfuldemo.MyContextApplication"
 * 之后在需要调用的地方，使用MyApplication.getApplicationContext()即可
 */
public class MyContextApplication extends Application{

    private static Context context;

    /**
     * 得到应用级别的Context
     */
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    /**
     * @return
     */
    public static Context getContext() {
        return context;
    }
}
