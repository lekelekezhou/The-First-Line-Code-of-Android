package com.endergeek.rookie.aweatherfinaldemo.util;

/**
 * Created by wangsenhui on 11/14/16.
 */
public interface HttpCallbackListener {
    void onError(Exception e);

    void onFinish(String s);
}
