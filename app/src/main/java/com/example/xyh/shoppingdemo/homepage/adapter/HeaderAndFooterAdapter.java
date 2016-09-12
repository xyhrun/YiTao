package com.example.xyh.shoppingdemo.homepage.adapter;

import android.content.Context;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.xyh.shoppingdemo.util.MyToast;

/**
 * Created by 向阳湖 on 2016/8/25.
 */
public class HeaderAndFooterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "HeaderAndFooterAdapter";
    private static final int BASE_ITEM_TYPE_HEADER = 10000;
    private static final int BASE_ITEM_TYPE_FOOTER = 20000;

    private RecyclerView.Adapter mInnerAdapter;
    private Context mContext;

    public HeaderAndFooterAdapter(RecyclerView.Adapter mInnerAdapter, Context context) {
        this.mInnerAdapter = mInnerAdapter;
        mContext = context;
    }


    //定义头尾视图
    private SparseArrayCompat<View> mHeaderViews = new SparseArrayCompat<>();
    private SparseArrayCompat<View> mFooterViews = new SparseArrayCompat<>();
    //判断位置是否头尾

    private boolean isHeaderViewPos(int position) {
        return position < getHeaderCount();
    }

    private boolean isFooterViewPos(int position) {
        return position >= (getHeaderCount() + getRealItemCount());
    }

    //在头尾添加布局
    public void addHeaderView(View view) {
        mHeaderViews.put(mHeaderViews.size() + BASE_ITEM_TYPE_HEADER, view);
//        Log.i(TAG, "addHeaderView: size = " + mHeaderViews.size());
    }

    public void addFooterView(View view) {
//        Log.i(TAG, "addFooterView: view = " + view);
        mFooterViews.put(mFooterViews.size() + BASE_ITEM_TYPE_FOOTER, view);
//        Log.i(TAG, "addFooterView: size = " + mFooterViews.size());
    }

    private int getRealItemCount() {
        return mInnerAdapter.getItemCount();
    }

    //获得头尾视图的个数
    public int getHeaderCount() {
        return mHeaderViews.size();
    }

    public int getFooterCount() {
        return mFooterViews.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderViews.get(viewType) != null) {
            ViewHolder viewHolder = ViewHolder.createViewHolder(parent.getContext(), mHeaderViews.get(viewType));
            return viewHolder;
        } else if (mFooterViews.get(viewType) != null) {
            ViewHolder viewHolder = ViewHolder.createViewHolder(parent.getContext(), mFooterViews.get(viewType));
            return viewHolder;
        }


        return mInnerAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == 0) {
                    return;
                }
                MyToast.showToast("点击第" + position + "项");
            }

        });

        if (isHeaderViewPos(position)) {
            return;
        }

        if (isFooterViewPos(position)) {
            return;
        }
        mInnerAdapter.onBindViewHolder(holder, position - getHeaderCount());
    }

    //设置类型
    @Override
    public int getItemViewType(int position) {
        if (isHeaderViewPos(position)) {
//            Log.i(TAG, "getItemViewType: position = " + position);
//            Log.i(TAG, "getItemViewType: 类型" + mHeaderViews.keyAt(position));
            return mHeaderViews.keyAt(position);
        } else if (isFooterViewPos(position)) {
            return mFooterViews.keyAt(position - getHeaderCount() - getRealItemCount());
        }
        return mInnerAdapter.getItemViewType(position - getHeaderCount());
    }

    @Override
    public int getItemCount() {
//        Log.i(TAG, "getItemCount: " + getRealItemCount() + getHeaderCount() + getFooterCount());
//        Log.i(TAG, "实际项目个数 " + getRealItemCount() + "头布局个数" + getHeaderCount() + "尾布局" + getFooterCount());
        return getRealItemCount() + getHeaderCount() + getFooterCount();
    }
}
