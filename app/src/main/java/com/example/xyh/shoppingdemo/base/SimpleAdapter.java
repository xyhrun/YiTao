package com.example.xyh.shoppingdemo.base;

import android.content.Context;

import java.util.List;

/**
 * Created by xyh on 2016/9/13.
 */
public abstract class SimpleAdapter<T> extends BaseAdapter<T, BaseViewHolder> {
    public SimpleAdapter(List<T> mDatas,  Context mContext, int mLayoutResId) {
        super(mDatas, mContext, mLayoutResId);
    }
}
