package com.example.xyh.shoppingdemo.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by xyh on 2016/9/13.
 */
public abstract class BaseAdapter<T, H extends BaseViewHolder> extends RecyclerView.Adapter<BaseViewHolder> {
//    子类可以访问除了private修饰的属性和方法，
//    int defaultT;
//    public int publicT;
//    private int privateT;
//    protected int protectT;
private static final String TAG = "BaseAdapter";

    protected List<T> mDatas;
    protected LayoutInflater mLayoutInflater;
    protected Context mContext;
    protected int mLayoutResId;
    protected OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public BaseAdapter(List<T> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mLayoutInflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
    }

    public BaseAdapter(List<T> mDatas, Context mContext, int mLayoutResId) {
        this.mDatas = mDatas;
        this.mLayoutInflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.mLayoutResId = mLayoutResId;
    }

    public interface OnItemClickListener {
        void onClick(View v, int position);
    }


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(mLayoutResId, parent, false);
        return new BaseViewHolder(mContext, view, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        T t  = getItem(position);
        bindData(holder, t);
    }

    public abstract void bindData(BaseViewHolder holder, T t);

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public T getItem(int position) {
        return mDatas.get(position);
    }
}
