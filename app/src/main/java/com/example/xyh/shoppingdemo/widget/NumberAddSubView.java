package com.example.xyh.shoppingdemo.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.xyh.shoppingdemo.R;

/**
 * Created by xyh on 2016/9/16.
 */
public class NumberAddSubView extends LinearLayout implements View.OnClickListener {
    private Context mContext;
    private View mView;
    private LayoutInflater mLayoutInflater;
    private Button reduce, add;
    private TextView numValue;
    private int maxValue, minValue, value;
    private OnButtonClickListener mOnButtonClickListener;
    private static final String TAG = "NumberAddSubView";
    public void setOnButtonClickListener(OnButtonClickListener mOnButtonClickListener) {
        this.mOnButtonClickListener = mOnButtonClickListener;
    }

    public NumberAddSubView(Context context) {
        this(context, null);
        this.mContext = context;
    }

    public NumberAddSubView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NumberAddSubView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mLayoutInflater = LayoutInflater.from(getContext());
        initView();
        final TintTypedArray ta = TintTypedArray.obtainStyledAttributes(getContext(), attrs,
                R.styleable.NumberAddSubView, defStyleAttr, 0);
        int value = ta.getInt(R.styleable.NumberAddSubView_value, 1);
        int maxValue = ta.getInt(R.styleable.NumberAddSubView_maxValue, 777);
        int minValue = ta.getInt(R.styleable.NumberAddSubView_minValue, 1);

        Drawable editBackground = ta.getDrawable(R.styleable.NumberAddSubView_numBackground);
        Drawable addBackground = ta.getDrawable(R.styleable.NumberAddSubView_addBackground);
        Drawable reduceBackground = ta.getDrawable(R.styleable.NumberAddSubView_reduceBackground);
        setValue(value);
        if (maxValue > 0) {
            setMaxValue(maxValue);
        }

        if (minValue >= 1) {
            setMinValue(minValue);
        }

        if (editBackground != null) {
            setEditBackground(editBackground);
        }

        if (reduceBackground != null) {
            setReduceBackground(reduceBackground);
        }

        if (reduceBackground != null) {
            setAddBackground(addBackground);
        }


        reduce.setOnClickListener(this);
        add.setOnClickListener(this);

        ta.recycle();
    }

    public void setValue(int value) {
        this.value = value;
        numValue.setText(value + "");
    }

    private void setAddBackground(Drawable addBackground) {
        add.setBackground(addBackground);
    }

    private void setReduceBackground(Drawable reduceBackground) {
        reduce.setBackground(reduceBackground);
    }

    private void setEditBackground(Drawable editBackground) {
        numValue.setBackground(editBackground);
    }

    private void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    private void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    private void initView() {
        if (mView == null) {
            mView = mLayoutInflater.inflate(R.layout.cart_num_layout, this,true);
            reduce = (Button) mView.findViewById(R.id.cart_reduce);
            numValue = (TextView) mView.findViewById(R.id.cart_numValue);
            add = (Button) mView.findViewById(R.id.cart_add);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cart_reduce:
                reduceValue();
                mOnButtonClickListener.onButtonSubClick(v, value);
                break;
            case R.id.cart_add:
                addValue();
                mOnButtonClickListener.onButtonAddClick(v, value);
                break;
            default:
                break;
        }
    }

    private void addValue() {
         value = getValue();
        if (value < maxValue) {
            value++;
        }
        Log.i(TAG, "addValue: ");
        numValue.setText(value + "");
    }

    private void reduceValue() {
        value = getValue();
        if (value > minValue) {
            value--;
        }
        numValue.setText(value + "");
    }

    public int getValue() {

        String value = numValue.getText().toString();

        if (value != null && !"".equals(value))
            this.value = Integer.parseInt(value);

        return this.value;
    }

    public interface OnButtonClickListener {

        public void onButtonAddClick(View view, int value);

        public void onButtonSubClick(View view, int value);

    }


}
