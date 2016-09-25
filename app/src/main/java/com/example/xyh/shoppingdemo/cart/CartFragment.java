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
    private static final int CART_EDIT = 1;
    private static final int CART_COMPLETE = 2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.from(container.getContext()).inflate(R.layout.fragment_cart, container, false);
        ButterKnife.bind(this, view);
        mCartProvider = new CartProvider(getActivity());
        initToolBar();
        showData();
        Log.i(TAG, "-------------onCreateView: 执行了");
        mDel.setOnClickListener(this);
        return view;
    }

    private void showData() {
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
    }

    public void refreshData() {
        if (mCartProvider == null) {
            mCartProvider = new CartProvider(getActivity());
        }
        shoppingCarts = mCartProvider.getAll();

        if (shoppingCarts == null) {
            shoppingCarts = new ArrayList<>();
            Log.i(TAG, "refreshData: 删除后还有"+shoppingCarts.size());
        }
        Log.i(TAG, "refreshData: 删除后还有"+shoppingCarts.size());
        mCartAdapter.refreshData(shoppingCarts);
        mCartAdapter.setTotalPrice();
    }

    public void initToolBar() {
        Drawable drawable = this.getResources().getDrawable(R.mipmap.edit);
        mMyToolBar.setRightIcon(drawable);
        mMyToolBar.getRightBtn().setTag(CART_EDIT);
        mMyToolBar.getRightBtn().setOnClickListener(this);
    }

    public void showCompleteControl() {
        mPay.setVisibility(View.GONE);
        mDel.setVisibility(View.VISIBLE);

        Drawable drawable = this.getResources().getDrawable(R.mipmap.complete);
        mMyToolBar.setRightIcon(drawable);
        mMyToolBar.getRightBtn().setTag(CART_COMPLETE);
        mMyToolBar.getRightBtn().setOnClickListener(this);
        mCheckBox.setChecked(false);
        mCartAdapter.checkAll_None(false);
    }

    public void hideCompleteControl() {
        mDel.setVisibility(View.GONE);
        mPay.setVisibility(View.VISIBLE);

        initToolBar();
        mCheckBox.setChecked(true);
        mCartAdapter.checkAll_None(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cart_del:
                mCartAdapter.delCart();
                Log.i(TAG, "onClick: 当前的商品个数为="+shoppingCarts.size());
                break;
            default:
                int action = (int) v.getTag();
                if(CART_EDIT == action){
                    showCompleteControl();
                }
                else if(CART_COMPLETE == action){
                    hideCompleteControl();
                }
                break;
        }

    }
}
