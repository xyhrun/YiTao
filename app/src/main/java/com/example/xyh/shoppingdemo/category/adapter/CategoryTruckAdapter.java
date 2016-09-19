package com.example.xyh.shoppingdemo.category.adapter;

import android.content.Context;
import android.util.Log;

import com.example.xyh.shoppingdemo.R;
import com.example.xyh.shoppingdemo.base.BaseAdapter;
import com.example.xyh.shoppingdemo.base.BaseViewHolder;
import com.example.xyh.shoppingdemo.tfaccount.model.TruckBean;

import java.util.List;

/**
 * Created by xyh on 2016/9/14.
 */
public class CategoryTruckAdapter extends BaseAdapter<TruckBean, BaseViewHolder> {
    private static final String TAG = "CategoryTruckAdapter";
    public CategoryTruckAdapter(List<TruckBean> mDatas, Context mContext) {
        super(mDatas, mContext, R.layout.category_right_item);
    }

    @Override
    public void bindData(BaseViewHolder holder, TruckBean TruckBean) {
        holder.setText(R.id.category_truckName, TruckBean.getName());
        holder.setText(R.id.category_truckPrice, TruckBean.getPrice()+"");
        holder.setImageView(R.id.category_truckImage, TruckBean.getImgUrl());
    }

    public void clearData() {
        int count = getItemCount();
        mDatas.clear();
        notifyItemRangeRemoved(0, count);
    }

    //刷新数据
    public void refreshData(List<TruckBean> TruckBeanList) {
        //刷新前把数据清除
        clearData();
        Log.i(TAG, "refreshData: 刷新的数据 = " + TruckBeanList.size());
        mDatas.addAll(TruckBeanList);
//        notifyDataSetChanged();
        notifyItemRangeChanged(0, getItemCount());
    }


    //增加数据
    public void addData(List<TruckBean> TruckBeanList) {
        if (TruckBeanList != null && TruckBeanList.size() > 0) {
            mDatas.addAll(TruckBeanList);
            notifyItemRangeChanged(getItemCount(), TruckBeanList.size());
        }

    }
}
