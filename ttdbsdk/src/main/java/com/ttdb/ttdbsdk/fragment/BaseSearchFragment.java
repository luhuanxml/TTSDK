package com.ttdb.ttdbsdk.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ttdb.ttdbsdk.activity.SearchActivity;

import static com.ttdb.ttdbsdk.utils.Constants.SDK_VER;

/**
 * Created by Administrator on 2017/3/15 0015.
 */

public abstract class BaseSearchFragment extends Fragment {
    SearchActivity mActivity;
    protected String ver;
    protected String imei;
    protected GridLayoutManager gridLayoutManager;
    LayoutInflater inflater;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity= (SearchActivity) getActivity();
        imei =((TelephonyManager) mActivity.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        ver=SDK_VER;
        gridLayoutManager = new GridLayoutManager(mActivity,3);
        inflater=LayoutInflater.from(mActivity);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view=inflater.inflate(getLayoutId(),container,false);
        initView(view);
        return view;
    }

    protected abstract void initView(View view);

    protected abstract int getLayoutId();

    //添加列表的方法
    protected abstract void addView(String searchContent);
}
