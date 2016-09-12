package com.example.xyh.shoppingdemo.homepage.presenter;

import android.view.View;

import com.example.xyh.shoppingdemo.homepage.model.CampaignBean;

/**
 * Created by xyh on 2016/9/12.
 */
public interface IHomepage {
    void onClickListener(View view, int position, CampaignBean.Bean bean);

}
