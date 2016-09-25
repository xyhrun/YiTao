package com.example.xyh.shoppingdemo.cart.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.xyh.shoppingdemo.R;
import com.example.xyh.shoppingdemo.base.BaseAdapter;
import com.example.xyh.shoppingdemo.base.BaseViewHolder;
import com.example.xyh.shoppingdemo.cart.CartProvider;
import com.example.xyh.shoppingdemo.cart.model.ShoppingCart;
import com.example.xyh.shoppingdemo.widget.NumberAddSubView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by xyh on 2016/9/16.
 */
public class CartAdapter extends BaseAdapter<ShoppingCart, BaseViewHolder> implements BaseAdapter.OnItemClickListener, View.OnClickListener {
    private static final String TAG = "CartAdapter";
    private CartProvider mCartProvider;
    private CheckBox mSelectedAll;
    private TextView mTotoalPrice;
    private CheckBox checkBox;

    public CartAdapter(List<ShoppingCart> mDatas, Context mContext, CheckBox checkBox, TextView totoalPrice) {
        super(mDatas, mContext, R.layout.cart_recycler_item);
        mCartProvider = new CartProvider(mContext);
        this.mSelectedAll = checkBox;
        this.mTotoalPrice = totoalPrice;
        setOnItemClickListener(this);
        mSelectedAll.setOnClickListener(this);
    }

    @Override
    public void bindData(BaseViewHolder holder, final ShoppingCart shoppingCart) {
        holder.setText(R.id.cart_item_truckPrice, shoppingCart.getPrice() + "");
        holder.setText(R.id.cart_item_truckName, shoppingCart.getName());
        holder.setImageView(R.id.cart_item_truckImage, shoppingCart.getImgUrl());
        checkBox = holder.getCheckBox(R.id.cart_item_cb);
        checkBox.setChecked(shoppingCart.isChecked());
        NumberAddSubView numberAddSubView = holder.getNumberAddSubView(R.id.cart_numberAddSubView);
        numberAddSubView.setValue(shoppingCart.getCount());
        numberAddSubView.setOnButtonClickListener(new NumberAddSubView.OnButtonClickListener() {
            @Override
            public void onButtonAddClick(View view, int value) {
                Log.i(TAG, "onButtonAddClick++: value = " + value);
                shoppingCart.setCount(value);
                mCartProvider.update(shoppingCart);
                setTotalPrice();
            }

            @Override
            public void onButtonSubClick(View view, int value) {
                Log.i(TAG, "onButtonSubClick--: value = " + value);
                shoppingCart.setCount(value);
                mCartProvider.update(shoppingCart);
                setTotalPrice();
            }
        });

    }

    public void setTotalPrice() {
        mTotoalPrice.setText(getTotalPrice() + "");
    }

    private float getTotalPrice() {
        float sum = 0;
        if (getItemCount() == 0) {
            Log.i(TAG, "getTotalPrice: 等于0？？？？");
            return 0;
        }
        for (ShoppingCart shoppingCart : mDatas) {
            if (shoppingCart.isChecked()) {
                sum += shoppingCart.getPrice() * shoppingCart.getCount();
            }
        }
        return sum;
    }

    public void clearData() {
        if (notEmpty()) {
            mDatas.clear();
            notifyDataSetChanged();
        }
    }


    //刷新数据
    public void refreshData(List<ShoppingCart> shoppingCartList) {
        clearData();
        Log.i(TAG, "refreshData: 刷新后还有几条" + shoppingCartList.size());
        if (shoppingCartList.size() > 0) {
            for (ShoppingCart shoppingCart : shoppingCartList) {
                if (mDatas == null) {
                    mDatas = new ArrayList<>();
                }
                mDatas.add(0, shoppingCart);
            }
        }
        notifyItemRangeChanged(0, shoppingCartList.size());
    }

    @Override
    public void onClick(View v, int position) {
        ShoppingCart shoppingCart = getItem(position);
        shoppingCart.setChecked(!shoppingCart.isChecked());
        mCartProvider.update(shoppingCart);
        notifyItemChanged(position);
        checkListen();
        setTotalPrice();
    }

    private void checkListen() {
        int count = 0;
        int checkNum = 0;
        if (mDatas != null) {
            count = mDatas.size();

            for (ShoppingCart cart : mDatas) {
                if (!cart.isChecked()) {
                    mSelectedAll.setChecked(false);
                    break;
                } else {
                    checkNum++;
                }
            }

            if (count == checkNum) {
                mSelectedAll.setChecked(true);
            }
        }
    }


    public void checkAll_None(boolean isChecked) {
        if (!notEmpty()) {
            return;
        }

        for (int i = 0; i < mDatas.size(); i++) {
            ShoppingCart cart = mDatas.get(i);
            cart.setChecked(isChecked);
            mCartProvider.update(cart);
        }
        notifyDataSetChanged();
        setTotalPrice();
    }

    private boolean notEmpty() {
        return (mDatas != null && mDatas.size() > 0);
    }

    public void delCart() {
        if (!notEmpty())
            return;

        for (Iterator iterator = mDatas.iterator(); iterator.hasNext(); ) {

            ShoppingCart cart = (ShoppingCart) iterator.next();
            if (cart.isChecked()) {
                int position = mDatas.indexOf(cart);
                mCartProvider.delete(cart);
                iterator.remove();
                notifyItemRemoved(position);
            }

        }

        setTotalPrice();
    }

    @Override
    public void onClick(View v) {
        checkAll_None(mSelectedAll.isChecked());
    }
}
