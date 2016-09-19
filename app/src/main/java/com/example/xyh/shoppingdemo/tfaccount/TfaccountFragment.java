package com.example.xyh.shoppingdemo.tfaccount;

import android.content.Intent;
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

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.example.xyh.shoppingdemo.R;
import com.example.xyh.shoppingdemo.base.BaseAdapter;
import com.example.xyh.shoppingdemo.cart.CartProvider;
import com.example.xyh.shoppingdemo.category.activity.TruckDetailActivity;
import com.example.xyh.shoppingdemo.net.OkHttpClientManager;
import com.example.xyh.shoppingdemo.tfaccount.adapter.TfaccountAdapter;
import com.example.xyh.shoppingdemo.tfaccount.model.BaseTruckBean;
import com.example.xyh.shoppingdemo.tfaccount.model.TruckBean;
import com.example.xyh.shoppingdemo.util.Api;
import com.example.xyh.shoppingdemo.util.DividerItemDecoration;
import com.example.xyh.shoppingdemo.util.MyToast;
import com.example.xyh.shoppingdemo.widget.MyToolBar;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Request;

/**
 * Created by xyh on 2016/9/8.
 */
public class TfaccountFragment extends Fragment implements BaseAdapter.OnItemClickListener {

    private static final String TAG = "TfaccountFragment";

    @Bind(R.id.tfaccount_recyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.tfaccount_refresh)
    MaterialRefreshLayout mMaterialRefreshLayout;
    @Bind(R.id.tfaccount_mytoolbar)
    MyToolBar mMyToolBar;

    private int curPage = 1;
    private int pageSize = 10;

    private TfaccountAdapter mTfaccountAdapter;
    private BaseTruckBean<TruckBean> mBaseTruckBean;
    private List<TruckBean> mTruckBeanList;
    private CartProvider mCartProvider;
    //下拉刷新的状态
    private static final int STATE_NORMAL = 0;
    private static final int STATE_REFRESH = 1;
    private static final int STATE_LOADMORE = 2;
    private int state = STATE_NORMAL;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.from(container.getContext()).inflate(R.layout.fragment_tfaccount, container, false);
        ButterKnife.bind(this, view);
        mCartProvider = new CartProvider(getActivity());
        getData();
        RefreshEvent();
        return view;
    }

    private void RefreshEvent() {
        mMaterialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
//                Log.i(TAG, "onRefresh: 刷新");
                curPage = 1;
                state = STATE_REFRESH;
                getData();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                curPage = mBaseTruckBean.getCurrentPage() + 1;
//                Log.i(TAG, "onRefreshLoadMore: curPage = " + curPage);
                if (curPage <= mBaseTruckBean.getTotalPage()) {
                    state = STATE_LOADMORE;
                    getData();
                } else {
                    MyToast.showToast("无更多数据");
                    mMaterialRefreshLayout.finishRefreshLoadMore();
                }
            }
        });
//       要关闭刷新动画
        mMaterialRefreshLayout.finishRefresh();
        mMaterialRefreshLayout.finishRefreshLoadMore();
    }


    private void getData() {
        String url = Api.HOT_TRUCK_URL + "?curPage=" + curPage + "&pageSize=" + pageSize;
        OkHttpClientManager.getAsyn(url, new OkHttpClientManager.ResultCallback<BaseTruckBean<TruckBean>>() {
            @Override
            public void onError(Request mRequest, Exception e) {
                Log.i(TAG, "onError: 热卖返回错误数据");
                e.printStackTrace();
            }

            @Override
            public void onResponse(BaseTruckBean<TruckBean> response) {
                mBaseTruckBean = response;
                mTruckBeanList = response.getList();
//                Log.i(TAG, "onResponse: 商品个数 " + mTruckBeanList.size());
                changeData();
            }
        });
    }

    private void changeData() {
        switch (state) {
            case STATE_NORMAL:
                mTfaccountAdapter = new TfaccountAdapter(mTruckBeanList, getActivity());
                mTfaccountAdapter.setOnItemClickListener(this);
                //不设置LayoutManager什么都显示不出来
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
                mRecyclerView.setAdapter(mTfaccountAdapter);
                break;
            case STATE_REFRESH:
                mTfaccountAdapter.refreshData(mTruckBeanList);
                mRecyclerView.scrollToPosition(0);
                mMaterialRefreshLayout.finishRefresh();
                break;
            case STATE_LOADMORE:
                mTfaccountAdapter.addData(mTruckBeanList);
//                mRecyclerView.scrollToPosition(mTfaccountAdapter.getItemCount());
                mMaterialRefreshLayout.finishRefreshLoadMore();
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v, int position) {
       List<TruckBean> truckBeanList =  mTfaccountAdapter.getDatas();
        TruckBean truckBean = truckBeanList.get(position);
        Log.i(TAG, "onClick: position = "+position);
        Log.i(TAG, "onClick: 商品个数~~~~~~~~~~~~~~"+truckBeanList.size());
        switch (v.getId()) {
            case R.id.tfaccount_truckBuy:
                Log.i(TAG, "onClick: truckBean = "+truckBean);
                mCartProvider.put(truckBean);
                MyToast.showToast("已加入购物车");
                break;
            default:
                Intent intent = new Intent(getActivity(), TruckDetailActivity.class);
                intent.putExtra("truck", truckBean);
                startActivity(intent);
        }
    }
}
