package com.ttdb.ttdbsdk.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ttdb.ttdbsdk.activity.TTMainActivity;
import com.ttdb.ttdbsdk.bean.DetaileList;
import com.ttdb.ttdbsdk.bean.PUserInfo;
import com.ttdb.ttdbsdk.common.ParamsUtil;
import com.ttdb.ttdbsdk.common.Sign;

import java.util.HashMap;

import static com.ttdb.ttdbsdk.utils.Constants.APPID;
import static com.ttdb.ttdbsdk.utils.Constants.PAPPID;
import static com.ttdb.ttdbsdk.utils.Constants.PT;
import static com.ttdb.ttdbsdk.utils.Constants.SDK_VER;
import static com.ttdb.ttdbsdk.utils.Constants.SIGN_KEY;
import static com.ttdb.ttdbsdk.utils.Constants.SIGN_KEY_TTDB;

public abstract class TTBaseFragment extends Fragment {
    protected Context mContext;
    protected String ver;
    protected String imei;
    protected PUserInfo pdata;

    public static DetaileList detaileList;

    protected TTMainActivity mActivity;

    protected abstract void initView(View view, Bundle savedInstanceState);

    //获取布局文件ID 传入你fragment引用的布局ID
    protected abstract int getLayoutId();

    @SuppressLint("HardwareIds")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle=getArguments();
        mContext=getActivity();
        mActivity= (TTMainActivity) getActivity();
        pdata= (PUserInfo) bundle.getSerializable("datainfo");
        imei =((TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        ver=SDK_VER;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        initView(view, savedInstanceState);
        return view;
    }

    //商品详情的链接参数和sign计算方法
    public String getParameter(String act_id){
        HashMap<String,String> params=new HashMap<>();
        params.put("pt",PT);
        params.put("appid",APPID);
        params.put("pappid",PAPPID);
        params.put("uid",pdata.getUid());
        params.put("imei",imei);
        params.put("ver",ver);
        params.put("sign",Sign.getMD5(pdata.getUid()+act_id+ SIGN_KEY_TTDB));
        return ParamsUtil.provide(params);
    }

    //设置链接参数方法
    public String getParameter() {
        HashMap<String,String> params=new HashMap<>();
        params.put("pt",PT);
        params.put("appid",APPID);
        params.put("pappid",PAPPID);
        params.put("uid",pdata.getUid());
        params.put("imei",imei);
        params.put("ver",ver);
        params.put("sign",Sign.getMD5(pdata.getUid()+ SIGN_KEY_TTDB));
        return ParamsUtil.provide(params);
    }

    //设置首页参数方法
    public String getMainParameter() {
        HashMap<String,String> params=new HashMap<>();
        params.put("pt",PT);
        params.put("appid",APPID);
        params.put("pappid",PAPPID);
        params.put("uid",pdata.getUid());
        params.put("imei",imei);
        params.put("ver",ver);
        params.put("sign",Sign.getMD5(pdata.getUid() + imei + PAPPID + SIGN_KEY));
        return ParamsUtil.provide(params);
    }

}
