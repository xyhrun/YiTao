package com.example.xyh.shoppingdemo.homepage.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.xyh.shoppingdemo.R;
import com.example.xyh.shoppingdemo.homepage.adapter.ViewPagerAdapter;
import com.example.xyh.shoppingdemo.fragment.AccountFragment;
import com.example.xyh.shoppingdemo.fragment.CartFragment;
import com.example.xyh.shoppingdemo.fragment.DiscoverFragment;
import com.example.xyh.shoppingdemo.homepage.fragment.HomePageFragment;
import com.example.xyh.shoppingdemo.fragment.TfaccountFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.main_viewPager)
    ViewPager mViewPager;
    @Bind(R.id.main_fragmentTabHost)
    FragmentTabHost mFragmentTabHost;

    private int[] images = {R.drawable.selector_guide_homepage, R.drawable.selector_guide_tfaccount,
            R.drawable.selector_guide_discover, R.drawable.selector_guide_cart, R.drawable.selector_guide_account};

    private String[] titles = {"首页", "微淘", "社区", "购物车", "我的淘宝"};
    private List<Fragment> fragmentList;
    private ViewPagerAdapter mViewPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
        setRelate();

    }



    private void setRelate() {
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mFragmentTabHost.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mFragmentTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                mViewPager.setCurrentItem(mFragmentTabHost.getCurrentTab());
            }
        });
    }

    private void initData() {
        fragmentList = new ArrayList<>();
        HomePageFragment homePageFragment = new HomePageFragment();
        TfaccountFragment tfaccountFragment = new TfaccountFragment();
        DiscoverFragment discoverFragment = new DiscoverFragment();
        CartFragment cartFragment = new CartFragment();
        AccountFragment accountFragment = new AccountFragment();
        fragmentList.add(homePageFragment);
        fragmentList.add(tfaccountFragment);
        fragmentList.add(discoverFragment);
        fragmentList.add(cartFragment);
        fragmentList.add(accountFragment);

        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),fragmentList);

        mFragmentTabHost.setup(this, getSupportFragmentManager(), R.id.main_viewPager);
        for (int i = 0; i < titles.length; i++) {
            TabHost.TabSpec tabSpec = mFragmentTabHost.newTabSpec(titles[i])
                    .setIndicator(getTabItemView(i));
            mFragmentTabHost.addTab(tabSpec, fragmentList.get(i).getClass(), null);
        }
        mViewPager.setAdapter(mViewPagerAdapter);
        mFragmentTabHost.setCurrentTab(0);
        mFragmentTabHost.getTabWidget().setDividerDrawable(R.color.transparent);
    }

    private View getTabItemView(int position) {
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.tab_item_view, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.tab_item_imageView);
        TextView textView = (TextView) view.findViewById(R.id.tab_item_textView);
        imageView.setBackgroundResource(images[position]);
        textView.setText(titles[position]);
        return view;
    }
}
