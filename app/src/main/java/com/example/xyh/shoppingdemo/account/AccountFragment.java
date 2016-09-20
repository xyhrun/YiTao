package com.example.xyh.shoppingdemo.account;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.xyh.shoppingdemo.R;

/**
 * Created by xyh on 2016/9/8.
 */
public class AccountFragment extends Fragment {
    private static final String TAG = "AccountFragment";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.from(container.getContext()).inflate(R.layout.fragment_account, container, false);
        Log.i(TAG, "-------------onCreateView: 执行了");
        return view;
    }
}
