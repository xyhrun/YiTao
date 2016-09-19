package com.example.xyh.shoppingdemo.cart.model;

import com.example.xyh.shoppingdemo.tfaccount.model.TruckBean;

/**
 * Created by xyh on 2016/9/16.
 */
public class ShoppingCart extends TruckBean {

    //添加到购物篮默认点击为true
    private boolean isChecked = true;
    private int count;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
