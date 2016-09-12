package com.example.xyh.shoppingdemo.util;

import android.widget.Toast;

/**
 * Created by xyh on 2016/9/10.
 */
public class MyToast {
    private static Toast mToast;
    public static void showToast(String content) {
        if (mToast == null) {
            mToast = Toast.makeText(MyApplication.getInstance(), content, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(content);
        }

        mToast.show();
    }
}
