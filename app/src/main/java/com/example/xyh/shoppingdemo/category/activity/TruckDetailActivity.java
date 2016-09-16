package com.example.xyh.shoppingdemo.category.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.xyh.shoppingdemo.R;
import com.example.xyh.shoppingdemo.category.model.CategoryTruckBean;
import com.example.xyh.shoppingdemo.util.Api;
import com.example.xyh.shoppingdemo.util.MyToast;
import com.example.xyh.shoppingdemo.widget.MyToolBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by xyh on 2016/9/15.
 */
public class TruckDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "TruckDetailActivity";

    @Bind(R.id.truckDetail_mytoolbar)
    MyToolBar mMytoolbar;
    @Bind(R.id.truckDetail_webview)
    WebView mWebView;

    private WebAppInterface mAppInterfce;
    private CategoryTruckBean mCategoryTruckBean;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truckdetail);
        ButterKnife.bind(this);
        mMytoolbar.setLeftIconClickListener(this);
        mMytoolbar.setRightIconClickListener(this);
        mCategoryTruckBean = (CategoryTruckBean) getIntent().getSerializableExtra("truck");
        if (mCategoryTruckBean == null) {
            MyToast.showToast("没有数据");
            finish();
        }
        initWebView();
    }

    private void initWebView(){

        WebSettings settings = mWebView.getSettings();

        settings.setJavaScriptEnabled(true);
        settings.setBlockNetworkImage(false);
        settings.setAppCacheEnabled(true);


        mWebView.loadUrl(Api.WARES_DETAIL);

        mAppInterfce = new WebAppInterface(this);
        mWebView.addJavascriptInterface(mAppInterfce,"appInterface");
        mWebView.setWebViewClient(new WC());


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.homepage_leftIcon:
                finish();
                break;
            case R.id.homepage_rightIcon:
                showShare();
                break;
            default:
                break;
            
        }
    }

    private void showShare() {
        ShareSDK.initSDK(this);


        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(getString(R.string.shape_title));

        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://www.cniao5.com");

        // text是分享文本，所有平台都需要这个字段
        oks.setText(mCategoryTruckBean.getName());

        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        oks.setImageUrl(mCategoryTruckBean.getImgUrl());

        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://www.cniao5.com");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment(mCategoryTruckBean.getName());

        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));

        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://www.cniao5.com");

// 启动分享GUI
        oks.show(this);
    }

    class WebAppInterface{


        private Context mContext;
        public WebAppInterface(Context context){
            mContext = context;
        }

        @JavascriptInterface
        public  void showDetail(){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    mWebView.loadUrl("javascript:showDetail("+mCategoryTruckBean.getId()+")");

                }
            });
        }


        @JavascriptInterface
        public void buy(long id){

//            cartProvider.put(mWare);
//            ToastUtils.show(mContext,"已添加到购物车");

        }

        @JavascriptInterface
        public void addFavorites(long id){


        }

    }

    class  WC extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            mAppInterfce.showDetail();
        }
    }

    //关闭分享
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ShareSDK.stopSDK(this);
    }
}
