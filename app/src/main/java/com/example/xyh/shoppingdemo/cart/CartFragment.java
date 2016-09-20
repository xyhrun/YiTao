package com.example.xyh.shoppingdemo.cart;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.xyh.shoppingdemo.R;
import com.example.xyh.shoppingdemo.cart.adapter.CartAdapter;
import com.example.xyh.shoppingdemo.cart.model.ShoppingCart;
import com.example.xyh.shoppingdemo.util.RecyclerViewDivider;
import com.example.xyh.shoppingdemo.widget.MyToolBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xyh on 2016/9/8.
 * 关闭屏幕才显示数据， 数据删除时候删除完了
 */
public class CartFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "CartFragment";

    @Bind(R.id.cart_myToolbar)
    MyToolBar mMyToolBar;
    @Bind(R.id.cart_recyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.cart_cb)
    CheckBox mCheckBox;
    @Bind(R.id.cart_totalPrice)
    TextView mTotalPrice;
    @Bind(R.id.cart_pay)
    Button mPay;
    @Bind(R.id.cart_priceInto)
    LinearLayout mCartPayInfo;
    @Bind(R.id.cart_del)
    Button mDel;
    private CartProvider mCartProvider;
    private CartAdapter mCartAdapter;
    private List<ShoppingCart> shoppingCarts;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.from(container.getContext()).inflate(R.layout.fragment_cart, container, false);
        ButterKnife.bind(this, view);
        showData();
        Log.i(TAG, "-------------onCreateView: 执行了");
        mMyToolBar.setRightIconClickListener(this);
        mDel.setOnClickListener(this);
        return view;
    }

    private void showData() {
        mCartProvider = new CartProvider(getActivity());
        shoppingCarts = mCartProvider.getAll();
//        Log.i(TAG, "showData: 添加到购物车的数量为 = "+shoppingCarts.size());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new RecyclerViewDivider());
        mCartAdapter = new CartAdapter(shoppingCarts, getActivity(), mCheckBox, mTotalPrice);
        mRecyclerView.setAdapter(mCartAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        refreshData();
    }


    public void refreshData() {
        shoppingCarts = mCartProvider.getAll();

        if (shoppingCarts == null) {
            shoppingCarts = new ArrayList<>();
            Log.i(TAG, "refreshData: 删除后还有"+shoppingCarts.size());
        }
        Log.i(TAG, "refreshData: 删除后还有"+shoppingCarts.size());
        mCartAdapter.refreshData(shoppingCarts);
        mCartAdapter.setTotalPrice();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.homepage_rightIcon:
                if (mCartPayInfo.getVisibility() == View.VISIBLE) {
                    Drawable drawable = this.getResources().getDrawable(R.mipmap.complete);
                    mMyToolBar.setRightIcon(drawable);
                    mCartPayInfo.setVisibility(View.GONE);
                    mPay.setVisibility(View.GONE);
                    mDel.setVisibility(View.VISIBLE);

                    for (ShoppingCart shoppingCart : shoppingCarts) {
                        shoppingCart.setChecked(false);
                    }
                    mCartAdapter.refreshData(shoppingCarts);
                    mCartAdapter.setTotalPrice();
                } else {
                    Drawable drawable = this.getResources().getDrawable(R.mipmap.edit);
                    mMyToolBar.setRightIcon(drawable);
                    mDel.setVisibility(View.GONE);
                    mCartPayInfo.setVisibility(View.VISIBLE);
                    mPay.setVisibility(View.VISIBLE);

                    for (ShoppingCart shoppingCart : shoppingCarts) {
                        shoppingCart.setChecked(true);
                    }
                    mCartAdapter.refreshData(shoppingCarts);
                    mCartAdapter.setTotalPrice();
                }
                break;
            case R.id.cart_del:
                Log.i(TAG, "onClick: 当前的商品个数为="+shoppingCarts.size());
                for (ShoppingCart shoppingCart : shoppingCarts) {
                    Log.i(TAG, "onClick: 是否被选中删除"+shoppingCart.isChecked());
                    if (shoppingCart.isChecked()) {
                        mCartProvider.delete(shoppingCart);
                        Log.i(TAG, "onClick: 删除"+shoppingCart.isChecked());
                    }
                }
                refreshData();
                break;
            default:
                break;
        }

    }
}
