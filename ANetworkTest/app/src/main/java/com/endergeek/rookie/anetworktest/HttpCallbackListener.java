package com.endergeek.rookie.anetworktest;

/**
 * Created by wangsenhui on 11/9/16.
 */
public interface HttpCallbackListener {
    void onFinish(String response);

    void onError(Exception e);
}
