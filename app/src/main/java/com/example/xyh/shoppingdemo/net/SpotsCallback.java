package com.example.xyh.shoppingdemo.net;

import android.content.Context;

import dmax.dialog.SpotsDialog;
import okhttp3.Request;

/**
 * Created by xyh on 2016/9/19.
 */
public abstract class SpotsCallback<T> extends ResultCallback<T> {

    private SpotsDialog mSpotsDialog;

    private Context mContext;

    public SpotsCallback(Context mContext) {
        this.mContext = mContext;
        mSpotsDialog = new SpotsDialog(mContext, "已经在加油了...");
    }

    public void setLoadMessage(String content) {
        mSpotsDialog.setMessage(content);
    }

    public void SpotsCallback(Context mContext, int style) {
        this.mContext = mContext;
        mSpotsDialog = new SpotsDialog(mContext, style);
    }

    @Override
    public void onBeforeRequest(Request request) {
        mSpotsDialog.show();
    }

    public void onFailure(Request request, Exception e) {
        mSpotsDialog.dismiss();
    }

    public void onSuccess(T response) {
        mSpotsDialog.dismiss();
    }
}
