package com.example.xyh.shoppingdemo.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.TintTypedArray;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.xyh.shoppingdemo.R;

/**
 * Created by xyh on 2016/9/10.
 */
public class MyToolBar extends Toolbar {
    private static final String TAG = "MyToolBar";

    private View mView;
    private LayoutInflater mLayoutInflater;
    private ImageButton mErWeiMa;
    private ImageButton mMessage;
    private ImageButton mBack;
    private ImageButton mAdd;
    private TextView mTitle;
    private EditText mSearchInput;

    public MyToolBar(Context context) {
        this(context, null);
    }

    public MyToolBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyToolBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        if (attrs != null) {
            final TintTypedArray ta = TintTypedArray.obtainStyledAttributes(getContext(), attrs,
                    R.styleable.MyToolBar, defStyleAttr, 0);
            //设置左按钮
            Drawable leftButton = ta.getDrawable(R.styleable.MyToolBar_leftButton);
            Drawable rightButton = ta.getDrawable(R.styleable.MyToolBar_rightButton);
            boolean isShowSearchView = ta.getBoolean(R.styleable.MyToolBar_isShowSearchView, false);
            String editTextHint = ta.getString(R.styleable.MyToolBar_editTextHint);
            Drawable editTextBackground = ta.getDrawable(R.styleable.MyToolBar_editBackground);
            setLeftIcon(leftButton);
            setRightIcon(rightButton);
            setEditTextBackground(isShowSearchView, editTextBackground);
            setEditTextHint(editTextHint);
            if (isShowSearchView) {
                showSearchView();
                hideTitleView();
            }
            ta.recycle();
        }
    }

    private void setEditTextHint(String editTextHint) {
        if (editTextHint != null) {
            mSearchInput.setHint(editTextHint);
        }
    }

    private void setEditTextBackground(boolean isShowSearchView, Drawable editTextBackground) {
        if (isShowSearchView && editTextBackground != null) {
            mSearchInput.setBackground(editTextBackground);
        }
    }

    public void setLeftIconClickListener(OnClickListener listener) {
        mBack.setOnClickListener(listener);
    }

    public void setRightIconClickListener(OnClickListener listener) {
        mAdd.setOnClickListener(listener);
    }

    private void setRightIcon(Drawable rightButton) {
        if (rightButton != null) {
            mAdd.setBackground(rightButton);
            mAdd.setVisibility(VISIBLE);
        }
    }

    private void setLeftIcon(Drawable leftButton) {
        if (leftButton != null) {
            //设置setImageDrawable图片后面会有灰色的块
            mBack.setBackground(leftButton);
            mBack.setVisibility(VISIBLE);
        }
    }

    private void initView() {
        Log.i(TAG, "initView: 初始化了吗");
        if (mView == null) {
            mLayoutInflater = LayoutInflater.from(getContext());
            Log.i(TAG, "initView: mLayoutInflater = " + mLayoutInflater);
            mView = mLayoutInflater.inflate(R.layout.homepage_toolbar, null);
            mErWeiMa = (ImageButton) mView.findViewById(R.id.homepage_erweima);
            mSearchInput = (EditText) mView.findViewById(R.id.homepage_search);
            mMessage = (ImageButton) mView.findViewById(R.id.homepage_message);
            mTitle = (TextView) mView.findViewById(R.id.toolbar_title);
            mBack = (ImageButton) mView.findViewById(R.id.homepage_back);
            mAdd = (ImageButton) mView.findViewById(R.id.homepage_add);
            LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            addView(mView, lp);
        }
    }

    @Override
    public void setTitle(@StringRes int resId) {
        setTitle(getContext().getText(resId));
    }

    @Override
    public void setTitle(CharSequence title) {
        Log.i(TAG, "setTitle: 执行了吗");
        initView();
        mTitle.setText(title);
        showTitleView();

    }

    public void showSearchView() {
        mSearchInput.setVisibility(VISIBLE);
    }

    public void hideSearchView() {

        mSearchInput.setVisibility(GONE);

    }

    public void showTitleView() {
        mTitle.setVisibility(VISIBLE);
    }

    public void hideTitleView() {
        mTitle.setVisibility(GONE);
    }
}
