package com.example.xyh.shoppingdemo.homepage.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.RadioGroup;

import com.example.xyh.shoppingdemo.R;
import com.example.xyh.shoppingdemo.account.AccountFragment;
import com.example.xyh.shoppingdemo.cart.CartFragment;
import com.example.xyh.shoppingdemo.category.CategoryFragment;
import com.example.xyh.shoppingdemo.homepage.adapter.ViewPagerAdapter;
import com.example.xyh.shoppingdemo.homepage.fragment.HomePageFragment;
import com.example.xyh.shoppingdemo.tfaccount.TfaccountFragment;
import com.example.xyh.shoppingdemo.util.MyToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.main_viewPager)
    ViewPager mViewPager;

    @Bind(R.id.radiogroup_id)
    RadioGroup mRadioGroup;

    private int[] ids = {R.id.guide_homepage, R.id.guide_tfaccount,
            R.id.guide_category, R.id.guide_cart, R.id.guide_account};

    private List<Fragment> fragmentList;
    private ViewPagerAdapter mViewPagerAdapter;
    private CartFragment cartFragment;
    private long firstExitime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
        setRelate();
        mViewPager.setOffscreenPageLimit(4);
    }

    private void setRelate() {
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mRadioGroup.check(ids[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.guide_homepage:
                        mViewPager.setCurrentItem(0);
                        break;
                    case R.id.guide_tfaccount:
                        mViewPager.setCurrentItem(1);
                        break;
                    case R.id.guide_category:
                        mViewPager.setCurrentItem(2);
                        break;
                    case R.id.guide_cart:
                        mViewPager.setCurrentItem(3);
                        cartFragment.refreshData();
                        break;
                    case R.id.guide_account:
                        mViewPager.setCurrentItem(4);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void initData() {
        fragmentList = new ArrayList<>();
        HomePageFragment homePageFragment = new HomePageFragment();
        TfaccountFragment tfaccountFragment = new TfaccountFragment();
        CategoryFragment categoryFragment = new CategoryFragment();
        cartFragment = new CartFragment();
        AccountFragment accountFragment = new AccountFragment();
        fragmentList.add(homePageFragment);
        fragmentList.add(tfaccountFragment);
        fragmentList.add(categoryFragment);
        fragmentList.add(cartFragment);
        fragmentList.add(accountFragment);

        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentList);

        mViewPager.setAdapter(mViewPagerAdapter);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            long time = System.currentTimeMillis();
            if (time - firstExitime >= 2000) {
                MyToast.showToast("再按一次退出");
                firstExitime = time;
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
