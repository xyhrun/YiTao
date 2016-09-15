package com.example.xyh.shoppingdemo.homepage.fragment;

import android.view.View;

import com.example.xyh.shoppingdemo.homepage.model.CampaignBean;

/**
 * Created by xyh on 2016/9/12.
 */
interface IFragmentView {
    void onCampaignClickListener(View view, int position, CampaignBean.Bean bean);
}
