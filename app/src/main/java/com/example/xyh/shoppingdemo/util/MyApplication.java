package com.example.xyh.shoppingdemo.util;

import android.app.Application;

/**
 * Created by xyh on 2016/9/12.
 */
public class MyApplication extends Application {
    private static MyApplication mMyApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mMyApplication = this;
    }

    public static MyApplication getInstance() {
        return mMyApplication;
    }
}
