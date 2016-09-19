package com.example.xyh.shoppingdemo.homepage.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.xyh.shoppingdemo.R;
import com.example.xyh.shoppingdemo.homepage.adapter.HeaderAndFooterAdapter;
import com.example.xyh.shoppingdemo.homepage.adapter.MyRecyclerAdapter;
import com.example.xyh.shoppingdemo.homepage.model.BannerBean;
import com.example.xyh.shoppingdemo.homepage.model.CampaignBean;
import com.example.xyh.shoppingdemo.net.OkHttpClientManager;
import com.example.xyh.shoppingdemo.net.SpotsCallback;
import com.example.xyh.shoppingdemo.util.Api;
import com.example.xyh.shoppingdemo.util.MyToast;
import com.example.xyh.shoppingdemo.util.RecyclerViewDivider;
import com.example.xyh.shoppingdemo.widget.BannerLayout;
import com.example.xyh.shoppingdemo.widget.IndicatorLayout;
import com.example.xyh.shoppingdemo.widget.MyToolBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Request;

/**
 * Created by xyh on 2016/9/8.
 */
public class HomePageFragment extends Fragment implements IFragmentView, View.OnTouchListener, BannerLayout.OnBannerChangeListener {
    @Bind(R.id.homepage_toolbar)
    MyToolBar mMyToolbar;
    @Bind(R.id.homepage_recyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.homepage_search)
    EditText mSearch;

    private View bannerView;
    //广告轮播的图片链接和标题
    private List<String> imgUrls;
    private List<String> titles;
    private BannerLayout mBannerLayout;
    private IndicatorLayout mIndicatorLayout;
    private TextView mBannerTitle;
    private static final String TAG = "HomePageFragment";
    private MyRecyclerAdapter mMyRecyclerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.from(container.getContext()).inflate(R.layout.fragment_homepage, container, false);
        ButterKnife.bind(this, view);
        mSearch.setOnTouchListener(this);
        initView();
        initData();
        return view;
    }

    private void recyclerViewData() {
        OkHttpClientManager.getAsyn(Api.CAMPAIGN_URL, new SpotsCallback<List<CampaignBean>>(getActivity()) {
            @Override
            public void onResponse(List<CampaignBean> response) {
                Log.i(TAG, "onResponse: response.get(0).getTitle" + response.get(0).getTitle());
                mMyRecyclerAdapter = new MyRecyclerAdapter(response, getActivity());
                //不设置LayoutManager什么都显示不出来
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                mRecyclerView.addItemDecoration(new RecyclerViewDivider());
                HeaderAndFooterAdapter headerAndFooterAdapter = new HeaderAndFooterAdapter(mMyRecyclerAdapter, getActivity());
                //添加广告
                addBannerHeader(headerAndFooterAdapter);
            }

            @Override
            public void onError(Request mRequest, Exception e) {

            }
        });
    }


    private void initView() {
        bannerView = LayoutInflater.from(getActivity()).inflate(R.layout.homepage_banner_container, null);
        mBannerLayout = (BannerLayout) bannerView.findViewById(R.id.homepage_banner);
        mIndicatorLayout = (IndicatorLayout) bannerView.findViewById(R.id.homepage_indicator_layout);
        mBannerTitle = (TextView) bannerView.findViewById(R.id.homepage_banner_title);
        if (mIndicatorLayout == null) {
            return;
        }

        mIndicatorLayout.setupWithBanner(mBannerLayout);
    }

    private void initData() {
        OkHttpClientManager.getAsyn(Api.BANNER_URL, new SpotsCallback<List<BannerBean>>(getActivity()) {
            @Override
            public void onError(Request mRequest, Exception e) {

            }

            //获取广告的数据
            @Override
            public void onResponse(List<BannerBean> response) {
                initBannerData(response);
                //广告，防止两个网络数据不同步
                mBannerLayout.setViewUrls(imgUrls);
                mBannerLayout.onDataUpdated();
                mBannerLayout.setOnBannerChangeListener(HomePageFragment.this);
                recyclerViewData();
            }
        });
    }

    private void addBannerHeader(HeaderAndFooterAdapter headerAndFooterAdapter) {
        headerAndFooterAdapter.addHeaderView(bannerView);
        mRecyclerView.setAdapter(headerAndFooterAdapter);
    }

    private void initBannerData(List<BannerBean> mBannerBeanList) {
        imgUrls = new ArrayList<>();
        titles = new ArrayList<>();
//        Log.i(TAG, "initBannerData: bannerBeanList = " + mBannerBeanList.size());
        for (BannerBean bannerBean : mBannerBeanList) {
            imgUrls.add(bannerBean.getImgUrl());
            titles.add(bannerBean.getName());
        }
    }

    @Override
    public void onCampaignClickListener(View view, int position, CampaignBean.Bean bean) {
        MyToast.showToast(bean.getTitle());
    }


    //点击别处软键盘关闭
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mSearch.setFocusable(true);
        mSearch.setFocusableInTouchMode(true);
        mSearch.requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mSearch.getWindowToken(), 0);
        return false;
    }

    @Override
    public void onBannerScrolled(int position) {
        mBannerTitle.setText(titles.get(position));
    }

    @Override
    public void onItemClick(int position) {
        MyToast.showToast("点击第" + (position + 1) + "项");
    }
}
