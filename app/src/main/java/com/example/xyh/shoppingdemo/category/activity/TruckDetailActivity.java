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
                MyToast.showToast("分享");
                break;
            default:
                break;
            
        }
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

}
