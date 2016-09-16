package com.example.xyh.shoppingdemo.tfaccount.adapter;

import android.content.Context;
import android.util.Log;

import com.example.xyh.shoppingdemo.R;
import com.example.xyh.shoppingdemo.base.BaseAdapter;
import com.example.xyh.shoppingdemo.base.BaseViewHolder;
import com.example.xyh.shoppingdemo.tfaccount.model.TruckBean;

import java.util.List;

/**
 * Created by xyh on 2016/9/13.
 */
public class TfaccountAdapter extends BaseAdapter<TruckBean, BaseViewHolder> {
    private static final String TAG = "TfaccountAdapter";

    public List<TruckBean> getDatas() {
        return mDatas;
    }

    public TfaccountAdapter(List<TruckBean> mDatas, Context mContext) {
        super(mDatas, mContext, R.layout.tfaccount_recycler_item);
    }

    @Override
    public void bindData(BaseViewHolder holder, TruckBean truckBean) {
        holder.setText(R.id.tfaccount_truckName, truckBean.getName())
                .setImageView(R.id.tfaccount_truckImage, truckBean.getImgUrl())
                .setText(R.id.tfaccount_truckPrice, truckBean.getPrice() + "")
                .setButtonClickListener(R.id.tfaccount_truckBuy);
    }

    public void clearData() {
        int count = getItemCount();
        mDatas.clear();
        notifyItemRangeRemoved(0, count);
    }

    //刷新数据
    public void refreshData(List<TruckBean> truckBeanList) {
        //刷新前把数据清除
        clearData();
        Log.i(TAG, "refreshData: 刷新的数据 = " + truckBeanList.size());
        mDatas.addAll(truckBeanList);
        notifyItemRangeChanged(0, getItemCount());
    }


    //增加数据
    public void addData(List<TruckBean> truckBeanList) {
        if (truckBeanList != null && truckBeanList.size() > 0) {
            mDatas.addAll(truckBeanList);
            notifyItemRangeChanged(getItemCount(), truckBeanList.size());
        }

    }
}
