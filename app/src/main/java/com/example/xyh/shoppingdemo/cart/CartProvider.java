package com.example.xyh.shoppingdemo.cart;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;

import com.example.xyh.shoppingdemo.cart.model.ShoppingCart;
import com.example.xyh.shoppingdemo.tfaccount.model.TruckBean;
import com.example.xyh.shoppingdemo.util.PreferenceUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xyh on 2016/9/16.
 */
public class CartProvider {
    private static final String TAG = "CartProvider";

    public static SparseArray<ShoppingCart> mDatas = null;
    public Context mContext;
    public Gson gson;
    public static final String CART_JSON = "cart_json";

    public CartProvider(Context mContext) {
        this.mContext = mContext;
        mDatas = new SparseArray<>(10);
        gson = new Gson();
        listToSparse();
    }

    //增删改
    public void put(TruckBean truckBean) {
        ShoppingCart shoppingCart = convertData(truckBean);
        put(shoppingCart);
    }

    //增删改
    public void put(ShoppingCart shoppingCart) {
        ShoppingCart temp = mDatas.get(shoppingCart.getId());

        if (temp != null) {
            temp.setCount(temp.getCount() + 1);
        } else {
            temp = shoppingCart;
            temp.setCount(1);
        }

        Log.i(TAG, "put: 添加数据前还有" + mDatas.size());
        mDatas.put(shoppingCart.getId(), temp);
        Log.i(TAG, "put: 添加数据后还有" + mDatas.size());
        commit();
    }

    public void delete(ShoppingCart shoppingCart) {
        Log.i(TAG, "delete: id = " + shoppingCart.getId());
        Log.i(TAG, "delete: 删除前还有" + mDatas.size());
        mDatas.delete(shoppingCart.getId());
//        mDatas.delete(1);
        Log.i(TAG, "delete: 删除后还有" + mDatas.size());
        commit();
    }

    public void update(ShoppingCart shoppingCart) {
        Log.i(TAG, "update: 更新前数据" + mDatas.size());
        mDatas.put(shoppingCart.getId(), shoppingCart);
        Log.i(TAG, "update: 更新后数据" + mDatas.size());
        commit();
    }

    public List<ShoppingCart> getAll() {
        return getDataFromLocal();
    }

    public void commit() {
        Log.i(TAG, "commit: ");
        PreferenceUtil.putString(mContext, CART_JSON, gson.toJson(sparseToList(), new TypeToken<List<ShoppingCart>>() {
        }.getType()));

    }

    public List<ShoppingCart> sparseToList() {
        Log.i(TAG, "sparseToList: " + mDatas.size());
        int size = mDatas.size();
        List<ShoppingCart> shoppingCarts = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            shoppingCarts.add(mDatas.valueAt(i));
        }
        Log.i(TAG, "sparseToList: shoppingCarts = " + shoppingCarts.size());
        return shoppingCarts;
    }

    public void listToSparse() {
        List<ShoppingCart> carts = getDataFromLocal();
        if (carts != null && carts.size() > 0) {
            for (ShoppingCart shoppingCart : carts) {
                mDatas.put(shoppingCart.getId(), shoppingCart);
            }
        }
        Log.i(TAG, "listToSparse: 从本地获取后mDatas.size"+mDatas.size());
    }

    public List<ShoppingCart> getDataFromLocal() {
        String json = PreferenceUtil.getString(mContext, CART_JSON);
        List<ShoppingCart> carts = null;
        if (json != null) {
            carts = gson.fromJson(json, new TypeToken<List<ShoppingCart>>() {
            }.getType());
        }
        return carts;
    }

    public ShoppingCart convertData(TruckBean truckBean) {
        Log.i(TAG, "convertData: truckBean.id" + truckBean.getId());
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(truckBean.getId());
        shoppingCart.setName(truckBean.getName());
        shoppingCart.setPrice(truckBean.getPrice());
        shoppingCart.setImgUrl(truckBean.getImgUrl());

        return shoppingCart;
    }

}
