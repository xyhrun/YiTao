package com.example.xyh.shoppingdemo.homepage.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.xyh.shoppingdemo.R;
import com.example.xyh.shoppingdemo.homepage.model.CampaignBean;

import java.util.List;

/**
 * Created by xyh on 2016/9/11.
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {

    //左布局
    private static final int LEFT_VIEW = 0;
    //右布局
    private static final int RIGHT_VIEW = 1;
    private List<CampaignBean> mCampaignBeanList;
    private Context mContext;
    private CampaignItemClickListener mCampaignItemClickListener;

    public void setCampaignItemClickListener(CampaignItemClickListener mNormalItemClickListener) {
        this.mCampaignItemClickListener = mNormalItemClickListener;
    }

    public MyRecyclerAdapter(List<CampaignBean> mCampaignBeanList, Context mContext) {
        this.mCampaignBeanList = mCampaignBeanList;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == LEFT_VIEW) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homepage_recycler_leftitem, null);
        } else if (viewType == RIGHT_VIEW) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homepage_recycler_rightitem, null);
        }
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        CampaignBean campaignBean = mCampaignBeanList.get(position);
        holder.title.setText(campaignBean.getTitle());
        //asBitmap()和 crossFade不能同时出现
        Glide.with(mContext).load(campaignBean.getCpOne().getImgUrl())
                .asBitmap().placeholder(R.mipmap.default_img).into(holder.bigImg);

        Glide.with(mContext).load(campaignBean.getCpTwo().getImgUrl())
                .asBitmap().placeholder(R.mipmap.default_img).into(holder.topSmallImg);

        Glide.with(mContext).load(campaignBean.getCpThree().getImgUrl())
                .asBitmap().placeholder(R.mipmap.default_img).into(holder.bottomSmallImg);

    }


    @Override
    public int getItemCount() {
        return mCampaignBeanList == null ? 0 : mCampaignBeanList.size();
    }

    @Override
    public int getItemViewType(int position) {
        //位置为偶数的返回大图在右边的布局
        if (position % 2 == 0) {
            return RIGHT_VIEW;
        } else if (position % 2 != 0) {
            return LEFT_VIEW;
        }
        return super.getItemViewType(position);
    }


    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView title;
        private ImageView bigImg, topSmallImg, bottomSmallImg;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.homepage_campaign_title);
            bigImg = (ImageView) itemView.findViewById(R.id.homepage_campaign_bigImg);
            topSmallImg = (ImageView) itemView.findViewById(R.id.homepage_campaign_top_smallImg);
            bottomSmallImg = (ImageView) itemView.findViewById(R.id.homepage_campaign_bottom_smallImg);

            bigImg.setOnClickListener(this);
            topSmallImg.setOnClickListener(this);
            bottomSmallImg.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int position = getLayoutPosition();
            //减去头部
            CampaignBean campaignBean = mCampaignBeanList.get(position - 1);
            if (mCampaignItemClickListener != null) {
                switch (v.getId()) {
                    case R.id.homepage_campaign_bigImg:
                        mCampaignItemClickListener.onClickListener(v, position, campaignBean.getCpOne());
                        break;
                    case R.id.homepage_campaign_top_smallImg:
                        mCampaignItemClickListener.onClickListener(v, position, campaignBean.getCpTwo());
                        break;
                    case R.id.homepage_campaign_bottom_smallImg:
                        mCampaignItemClickListener.onClickListener(v, position, campaignBean.getCpThree());
                        break;
                    default:
                        break;
                }
            }

        }
    }
}
