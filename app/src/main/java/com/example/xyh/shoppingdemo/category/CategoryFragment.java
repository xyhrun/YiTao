package com.example.xyh.shoppingdemo.category;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
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
import com.example.xyh.shoppingdemo.category.activity.TruckDetailActivity;
import com.example.xyh.shoppingdemo.category.adapter.CategoryAdapter;
import com.example.xyh.shoppingdemo.category.adapter.CategoryTruckAdapter;
import com.example.xyh.shoppingdemo.category.model.CategoryBean;
import com.example.xyh.shoppingdemo.category.model.ListCategory;
import com.example.xyh.shoppingdemo.net.OkHttpClientManager;
import com.example.xyh.shoppingdemo.net.ResultCallback;
import com.example.xyh.shoppingdemo.net.SpotsCallback;
import com.example.xyh.shoppingdemo.tfaccount.model.TruckBean;
import com.example.xyh.shoppingdemo.util.Api;
import com.example.xyh.shoppingdemo.util.DividerItemDecoration;
import com.example.xyh.shoppingdemo.util.MyToast;
import com.example.xyh.shoppingdemo.util.RecyclerViewDivider;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Request;

/**
 * Created by xyh on 2016/9/8.
 */
public class CategoryFragment extends Fragment implements BaseAdapter.OnItemClickListener {
    private static final String TAG = "CategoryFragment";
    private CategoryAdapter mCategoryAdapter;
    private CategoryTruckAdapter mCategoryTruckAdapter;
    private List<CategoryBean> mCategoryBeanList;
    private List<TruckBean> mTruckBeanList;
    private ListCategory<TruckBean> mListCategory;
    @Bind(R.id.category_leftlist)
    RecyclerView mLeftRecyclerView;

    @Bind(R.id.category_materialRefreshLayout)
    MaterialRefreshLayout mMaterialRefreshLayout;
    @Bind(R.id.category_rightlist)
    RecyclerView mRightRecyclerView;

    private int categoryId = 1;
    private int curPage = 1;
    private int pageSize = 10;

    //下拉刷新的状态
    private static final int STATE_NORMAL = 0;
    private static final int STATE_REFRESH = 1;
    private static final int STATE_LOADMORE = 2;
    private int state = STATE_NORMAL;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.from(container.getContext()).inflate(R.layout.fragment_category, container, false);
        ButterKnife.bind(this, view);
        initCategoryData();
        Log.i(TAG, "-------------onCreateView: 执行了");
        //重复设置分割线， 会导致Item越来越小
        mRightRecyclerView.addItemDecoration(new RecyclerViewDivider());
        RefreshEvent();
        return view;
    }

    private void RefreshEvent() {
        mMaterialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                curPage = 1;
                state = STATE_REFRESH;
                Log.i(TAG, "onRefresh: 刷新 category id = " + categoryId);
                requestWares(categoryId);
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                //获得当前id的当前页
                curPage = mListCategory.getCurrentPage() + 1;
                if (curPage <= mListCategory.getTotalPage()) {
                    state = STATE_LOADMORE;
                    Log.i(TAG, "onRefresh: 加载 category id = " + categoryId);
                    Log.i(TAG, "onRefreshLoadMore: 当前页= " + curPage);
                    requestWares(categoryId);
                } else {
                    MyToast.showToast("没有更多数据");
                    mMaterialRefreshLayout.finishRefreshLoadMore();
                }
            }
        });
        mMaterialRefreshLayout.finishRefresh();
        mMaterialRefreshLayout.finishRefreshLoadMore();
    }


    private void initCategoryData() {
        OkHttpClientManager.getAsyn(Api.CATEGORY_LIST, new SpotsCallback<List<CategoryBean>>(getActivity()) {
            @Override
            public void onError(Request mRequest, Exception e) {
                Log.i(TAG, "onError: 出错了");
                e.printStackTrace();
            }

            @Override
            public void onResponse(List<CategoryBean> response) {
                Log.i(TAG, "onResponse: 分类个数" + response.size());
                mCategoryBeanList = response;
                showCategoryData();

                //请求的是第一条的数据
                if (mCategoryBeanList != null && mCategoryBeanList.size() > 0) {
                    requestWares(mCategoryBeanList.get(0).getId());
                }

            }
        });
    }

    private void showCategoryData() {
        mCategoryAdapter = new CategoryAdapter(mCategoryBeanList, getActivity());
        mLeftRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mLeftRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mLeftRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        mLeftRecyclerView.setAdapter(mCategoryAdapter);

        mCategoryAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v, int position) {
        switch (v.getId()) {
            case R.id.category_leftList_item:
                CategoryBean categoryBean = mCategoryBeanList.get(position);
                categoryId = categoryBean.getId();
                curPage = 1;
                pageSize = 10;
                state = STATE_NORMAL;
                requestWares(categoryId);
                break;
            default:
                TruckBean truckBean = mCategoryTruckAdapter.getItem(position);
//                MyToast.showToast("点击第"+(position+1)+"项");
                Intent intent = new Intent(getActivity(), TruckDetailActivity.class);
                intent.putExtra("truck", truckBean);
                startActivity(intent);
        }
    }

    //请求数据有问题
    private void requestWares(int categoryId) {
        String url = Api.WARES_LIST + "?categoryId=" + categoryId +
                "&curPage=" + curPage + "&pageSize=" + pageSize;
        OkHttpClientManager.getAsyn(url, new ResultCallback<ListCategory<TruckBean>>() {
            @Override
            public void onError(Request mRequest, Exception e) {

            }

            @Override
            public void onResponse(ListCategory<TruckBean> response) {
                mListCategory = response;
                mTruckBeanList = response.getList();
                if (mTruckBeanList.size() == 0) {
                    MyToast.showToast("没有查询到商品");
                }
                Log.i(TAG, "onResponse: 增加的数据为" + mTruckBeanList.size());
                //?
                getData();
            }

            @Override
            public void onBeforeRequest(Request request) {

            }

            @Override
            public void onFailure(Request request, Exception e) {

            }

            @Override
            public void onSuccess(ListCategory<TruckBean> response) {

            }
        });
    }

    private void getData() {
        switch (state) {
            case STATE_NORMAL:
                mCategoryTruckAdapter = new CategoryTruckAdapter(mTruckBeanList, getActivity());
                mCategoryTruckAdapter.setOnItemClickListener(CategoryFragment.this);
                mRightRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false));
                mRightRecyclerView.setItemAnimator(new DefaultItemAnimator());
                mRightRecyclerView.setAdapter(mCategoryTruckAdapter);
                break;
            case STATE_REFRESH:
                mCategoryTruckAdapter.refreshData(mTruckBeanList);
                mRightRecyclerView.scrollToPosition(0);
                mMaterialRefreshLayout.finishRefresh();
                break;
            case STATE_LOADMORE:
                mCategoryTruckAdapter.addData(mTruckBeanList);
                mMaterialRefreshLayout.finishRefreshLoadMore();
                break;
        }
    }
}
