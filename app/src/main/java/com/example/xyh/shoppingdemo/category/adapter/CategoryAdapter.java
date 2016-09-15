package com.example.xyh.shoppingdemo.category.adapter;

import android.content.Context;

import com.example.xyh.shoppingdemo.R;
import com.example.xyh.shoppingdemo.base.BaseAdapter;
import com.example.xyh.shoppingdemo.base.BaseViewHolder;
import com.example.xyh.shoppingdemo.category.model.CategoryBean;

import java.util.List;

/**
 * Created by xyh on 2016/9/14.
 */
public class CategoryAdapter extends BaseAdapter<CategoryBean, BaseViewHolder> {
    public CategoryAdapter(List<CategoryBean> mDatas, Context mContext) {
        super(mDatas, mContext, R.layout.category_left_item);
    }


    @Override
    public void bindData(BaseViewHolder holder, CategoryBean categoryBean) {
        holder.setText(R.id.category_leftList_item, categoryBean.getName());
    }
}
