package com.ttdb.ttdbsdk.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;

import com.ttdb.ttdbsdk.alipay.AuthResult;
import com.ttdb.ttdbsdk.alipay.PayResult;
import com.ttdb.ttdbsdk.common.ParamsUtil;
import com.ttdb.ttdbsdk.common.Sign;
import com.ttdb.ttdbsdk.common._Toast;
import com.ttdb.ttdbsdk.myapp.TTApplication;
import com.ttdb.ttdbsdk.utils.LoadingImage;
import com.ttdb.ttdbsdk.utils.SP;

import java.util.HashMap;
import java.util.Map;

import static com.ttdb.ttdbsdk.utils.Constants.APPID;
import static com.ttdb.ttdbsdk.utils.Constants.PAPPID;
import static com.ttdb.ttdbsdk.utils.Constants.PT;
import static com.ttdb.ttdbsdk.utils.Constants.SDK_AUTH_FLAG;
import static com.ttdb.ttdbsdk.utils.Constants.SDK_PAY_FLAG;
import static com.ttdb.ttdbsdk.utils.Constants.SDK_VER;
import static com.ttdb.ttdbsdk.utils.Constants.SIGN_KEY_TTDB;

public abstract  class TTBaseActivity extends FragmentActivity  {
	protected Context mContext;
	protected String ver;
	protected String uid;
	LoadingImage loading;
	//商品详情的链接参数和sign计算方法
	public String getParameter(String act_id){
		HashMap<String,String> params=new HashMap<>();
		params.put("pt",PT);
		params.put("appid",APPID);
		params.put("pappid",PAPPID);
		params.put("uid",uid);
		params.put("imei", SP.get("imei","0"));
		params.put("ver",ver);
		params.put("sign",Sign.getMD5(uid+act_id+ SIGN_KEY_TTDB));
		return ParamsUtil.provide(params);
	}

	//设置链接参数方法
	public String getParameter() {
		HashMap<String,String> params=new HashMap<>();
		params.put("pt",PT);
		params.put("appid",APPID);
		params.put("pappid",PAPPID);
		params.put("uid",uid);
		params.put("imei", SP.get("imei","0"));
		params.put("ver",ver);
		params.put("sign",Sign.getMD5(uid+ SIGN_KEY_TTDB));
		return ParamsUtil.provide(params);
	}

	//跳转到他人夺宝记录
	public String getParams(String cid){
		HashMap<String,String> params=new HashMap<>();
		params.put("pt",PT);
		params.put("appid",APPID);
		params.put("pappid",PAPPID);
		params.put("cid",cid);
		params.put("uid",uid);
		params.put("imei",  SP.get("imei","0"));
		params.put("ver",ver);
		params.put("sign",Sign.getMD5(uid+cid+ SIGN_KEY_TTDB));
		return ParamsUtil.provide(params);
	}
 
	@SuppressLint("HardwareIds")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		loading=new LoadingImage(this);
		uid = TTApplication.pUserInfo.getUid();
		ver = SDK_VER;
	}

	@SuppressLint("HandlerLeak")
	public Handler mHandler = new Handler() {
		@SuppressWarnings("unused")
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case SDK_PAY_FLAG: {
					@SuppressWarnings("unchecked")
					PayResult payResult = new PayResult((Map<String, String>) msg.obj);
					/**
					 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
					 */
					String resultInfo = payResult.getResult();// 同步返回需要验证的信息
					String resultStatus = payResult.getResultStatus();
					// 判断resultStatus 为9000则代表支付成功
					if (TextUtils.equals(resultStatus, "9000")) {
						// 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
						_Toast.show("支付成功");
					}else {
						// 判断resultStatus 为非“9000”则代表可能支付失败
						// “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
						if (TextUtils.equals(resultStatus, "8000")) {
							_Toast.show("支付结果确认中");

						} else if (TextUtils.equals(resultStatus, "6001")) {
							// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
							_Toast.show("支付取消");

						} else {
							// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
							_Toast.show("支付失败");
						}
					}
					break;
				}
				case SDK_AUTH_FLAG: {
					@SuppressWarnings("unchecked")
					AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
					String resultStatus = authResult.getResultStatus();

					// 判断resultStatus 为“9000”且result_code
					// 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
					if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
						// 获取alipay_open_id，调支付时作为参数extern_token 的value
						// 传入，则支付账户为该授权账户
						_Toast.show( "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()));
					} else {
						// 其他状态值则为授权失败
						_Toast.show("授权失败" + String.format("authCode:%s", authResult.getAuthCode()));
					}
					break;
				}
				default:
					break;
			}
		};
	};
}
