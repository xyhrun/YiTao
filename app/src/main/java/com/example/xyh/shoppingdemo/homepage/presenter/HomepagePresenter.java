package com.example.xyh.shoppingdemo.homepage.presenter;

import android.content.Context;
import android.view.View;

import com.example.xyh.shoppingdemo.homepage.adapter.CampaignItemClickListener;
import com.example.xyh.shoppingdemo.homepage.fragment.HomePageFragment;
import com.example.xyh.shoppingdemo.homepage.model.CampaignBean;

/**
 * Created by xyh on 2016/9/12.
 */
public class HomepagePresenter implements CampaignItemClickListener {
    private Context mContext;
    private HomePageFragment homePageFragment;
    public HomepagePresenter(Context mContext) {
        this.mContext = mContext;
        homePageFragment = new HomePageFragment();
    }

    @Override
    public void onClickListener(View view, int position, CampaignBean.Bean bean) {
        homePageFragment.onCampaignClickListener(view, position, bean);
    }

}
