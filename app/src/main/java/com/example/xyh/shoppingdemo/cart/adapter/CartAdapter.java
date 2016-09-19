package com.example.xyh.shoppingdemo.cart.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.xyh.shoppingdemo.R;
import com.example.xyh.shoppingdemo.base.BaseAdapter;
import com.example.xyh.shoppingdemo.base.BaseViewHolder;
import com.example.xyh.shoppingdemo.cart.CartProvider;
import com.example.xyh.shoppingdemo.cart.model.ShoppingCart;
import com.example.xyh.shoppingdemo.widget.NumberAddSubView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xyh on 2016/9/16.
 */
public class CartAdapter extends BaseAdapter<ShoppingCart, BaseViewHolder> implements BaseAdapter.OnItemClickListener, CompoundButton.OnCheckedChangeListener {
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

        mSelectedAll.setOnCheckedChangeListener(this);

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
        if (mDatas != null && mDatas.size() > 0) {
            mDatas.clear();
            notifyDataSetChanged();
        }
//        for (Iterator it = mDatas.iterator(); it.hasNext(); ) {
//
//            ShoppingCart t = (ShoppingCart) it.next();
//            int position = mDatas.indexOf(t);
//            it.remove();
//            notifyItemRemoved(position);
//        }
    }


    //刷新数据
    public void refreshData(List<ShoppingCart> shoppingCartList) {
        clearData();
        Log.i(TAG, "refreshData: 删除后还有几条" + shoppingCartList.size());
        if (shoppingCartList.size() > 0) {
            for (ShoppingCart shoppingCart : shoppingCartList) {
                if (mDatas == null) {
                    mDatas = new ArrayList<>();
                }
                mDatas.add(0, shoppingCart);
                Log.i(TAG, "refreshData: id = " + shoppingCart.getId() + shoppingCart.getName());

            }
        }
        notifyDataSetChanged();

    }

    @Override
    public void onClick(View v, int position) {
        ShoppingCart shoppingCart = getItem(position);
        shoppingCart.setChecked(!shoppingCart.isChecked());
        mCartProvider.update(shoppingCart);
        notifyItemChanged(position);
        setTotalPrice();
    }

    private void checkAllEvent(boolean isChecked) {
        if (getItemCount() == 0) {
            return;
        }

        if (isChecked) {
            for (ShoppingCart shoppingCart : mDatas) {
                if (!shoppingCart.isChecked()) {
                    shoppingCart.setChecked(true);
                    mCartProvider.update(shoppingCart);
                }

            }
        } else {
            for (ShoppingCart shoppingCart : mDatas) {
                if (shoppingCart.isChecked()) {
                    shoppingCart.setChecked(false);
                    mCartProvider.update(shoppingCart);
                }
            }
        }
        notifyDataSetChanged();
        setTotalPrice();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        checkAllEvent(isChecked);
    }
}
