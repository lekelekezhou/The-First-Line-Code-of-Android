package com.endergeek.rookie.aservicetestdemo;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * Created by wangsenhui on 11/7/16.
 * 服务中的代码默认运行在主线程，如果在其中处理耗时逻辑，容易ANR。
 * 本应在Service的onStartCommand中开启子线程执行耗时逻辑，但服务一旦启动之后就会处于运行状态，必须调用stopService()或stopSelf()
 * 由于容易忘记在run()方法中调用stopSelf()，因此可以创建一个异步的、会**自动停止**的服务
 */
public class MyIntentService extends IntentService{

    public MyIntentService() {
        super("MyIntentService"); // 调用父类的有参构造函数
    }

    /**
     * 在此方法中处理**具体的逻辑**，已是在子线程中运行的
     * @param intent
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        // 打印当前线程id
        Log.d("MyIntentService", "Thread id is " + Thread.currentThread().getId());
    }

    /**
     * 根据IntentService特性，本服务在运行结束后会自动停止
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("MyIntentService", "onDestroy executed");
    }
}
