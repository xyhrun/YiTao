package com.example.xyh.shoppingdemo.category.adapter;

import android.content.Context;
import android.util.Log;

import com.example.xyh.shoppingdemo.R;
import com.example.xyh.shoppingdemo.base.BaseAdapter;
import com.example.xyh.shoppingdemo.base.BaseViewHolder;
import com.example.xyh.shoppingdemo.category.model.CategoryTruckBean;

import java.util.List;

/**
 * Created by xyh on 2016/9/14.
 */
public class CategoryTruckAdapter extends BaseAdapter<CategoryTruckBean, BaseViewHolder> {
    private static final String TAG = "CategoryTruckAdapter";
    public CategoryTruckAdapter(List<CategoryTruckBean> mDatas, Context mContext) {
        super(mDatas, mContext, R.layout.category_right_item);
    }

    @Override
    public void bindData(BaseViewHolder holder, CategoryTruckBean categoryTruckBean) {
        holder.setText(R.id.category_truckName, categoryTruckBean.getName());
        holder.setText(R.id.category_truckPrice, categoryTruckBean.getPrice()+"");
        holder.setImageView(R.id.category_truckImage, categoryTruckBean.getImgUrl());
    }

    public void clearData() {
        mDatas.clear();
        notifyItemRangeChanged(0, getItemCount());
    }

    //刷新数据
    public void refreshData(List<CategoryTruckBean> categoryTruckBeanList) {
        //刷新前把数据清除
        clearData();
        Log.i(TAG, "refreshData: 刷新的数据 = " + categoryTruckBeanList.size());
        mDatas.addAll(categoryTruckBeanList);
        notifyItemRangeChanged(0, getItemCount());
    }


    //增加数据
    public void addData(List<CategoryTruckBean> categoryTruckBeanList) {
        if (categoryTruckBeanList != null && categoryTruckBeanList.size() > 0) {
            mDatas.addAll(categoryTruckBeanList);
            notifyItemRangeChanged(getItemCount(), categoryTruckBeanList.size());
        }

    }
}
