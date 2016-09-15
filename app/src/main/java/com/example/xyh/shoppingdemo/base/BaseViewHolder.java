package com.example.xyh.shoppingdemo.base;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * Created by xyh on 2016/9/13.
 */
public class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private SparseArray<View> mViews;
    protected BaseAdapter.OnItemClickListener mOnItemClickListener;
    protected Context mContext;

    public BaseViewHolder(Context context, View itemView, BaseAdapter.OnItemClickListener onItemClickListener) {
        super(itemView);
        mViews = new SparseArray<>();
        this.mContext = context;
        this.mOnItemClickListener = onItemClickListener;
        itemView.setOnClickListener(this);
    }

    public <V extends View> V getViewById(int resId) {
        View view = mViews.get(resId);
        if (view == null) {
            view = itemView.findViewById(resId);
        }
        return (V) view;
    }

    //设置文本内容
    public BaseViewHolder setText(int resId, String content) {
        TextView text = (TextView) getViewById(resId);
        text.setText(content);
        text.setOnClickListener(this);
        return this;
    }

    public View getTextView(int resId) {
        return getViewById(resId);
    }

    public View getImageView(int resId) {
        return getViewById(resId);
    }

    public View getButton(int resId) {
        return getViewById(resId);
    }

    //设置图片
    public BaseViewHolder setImageView(int resId, int imageId) {
        ImageView image =  getViewById(resId);
        image.setBackgroundResource(imageId);
        return this;
    }

    public BaseViewHolder setImageView(int resId, Drawable drawable) {
        ImageView image =  getViewById(resId);
        image.setOnClickListener(this);
        image.setBackground(drawable);
        return this;
    }

    public BaseViewHolder setImageView(int resId, String url) {
        ImageView image =  getViewById(resId);
        image.setOnClickListener(this);
        Glide.with(mContext).load(url).asBitmap().into(image);
        return this;
    }

    public BaseViewHolder setImageView(int resId, String url, int placeHolderId) {
        ImageView image =  getViewById(resId);
        image.setOnClickListener(this);
        Glide.with(mContext).load(url).asBitmap().placeholder(placeHolderId).into(image);
        return this;
    }


    public BaseViewHolder setButtonClickListener(int resId) {
        Button button = getViewById(resId);
        button.setOnClickListener(this);
        return this;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onClick(v, getLayoutPosition());
        }
    }
}
