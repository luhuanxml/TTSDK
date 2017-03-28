package com.ttdb.ttdbsdk.utils;

import com.ttdb.ttdbsdk.myapp.TTApplication;

public interface Constants {
	
	//appid
	String APPID = "0";
	String PAPPID = "99";
	//平台
	String PT = "1";

	//域名
	String DOMAIN = TTApplication.pUserInfo.getDomain_name();

	//活动域名
	String HUODONG_URL = TTApplication.pUserInfo.getHuodong_url();
	

	//1.初始化及登陆注册接口
	String LOADING="http://db.wanyige.com/index.php?tp=api/ini_loading_sdk";
	
	//2.首页
	String MAIN_PAGE= DOMAIN+"/index.php?tp=front/sdk_index";
	
	//3.最新揭晓
	String ACTIVITY_NEW= DOMAIN+"/index.php?tp=front/activity_new";
	
	//4.他人夺宝记录
	String USER_RECORD= DOMAIN+"/index.php?tp=front/user_record";

	//5.商品详情
	String ACTIVITY_DETAIL= DOMAIN+"/index.php?tp=front/activity_detail";
	
	//6.往期揭晓
	String ACTIVITY_RECORD= DOMAIN+"/index.php?tp=front/activity_record";
	
	//7.清单
	String CAR= DOMAIN+"/index.php?tp=front/car";
	
	//8.结算
	String PAY= DOMAIN+"/index.php?tp=front/pay";

	//9.结果
	String PAY_HANDLER= DOMAIN+"/index.php?tp=front/pay_handler";
	
	//10.我的夺宝记录
	String MY_ACT_RECORD= DOMAIN+"/index.php?tp=front/my_act_record";
	
	//11.用户中心
	String UCENTER= DOMAIN+"/index.php?tp=front/ucenter";
	
	//12.我的消息
	String NEWS= DOMAIN+"/index.php?tp=front/news";
	
	//13.中奖记录
	String AWARD_RECORD= DOMAIN+"/index.php?tp=front/award_record";
	
	//15.地址管理
	String MANAGEADD= DOMAIN+"/index.php?tp=api/manageadd";
	
	//16.个人信息
	String UINFO= DOMAIN+"/index.php?tp=front/uinfo";
	
	//17.绑定手机
	String UPDATE_PHONE= DOMAIN+"/index.php?tp=api/updatephone";

	//19.显示全部号码
	String RESULT_MORENUM= DOMAIN+"/index.php?tp=front/result_morenum";

	//20.修改昵称
	String UPDATE_NAME= DOMAIN+"/index.php?tp=api/updatename";

	//21.计算详情
	String ACTIVITY_DETAIL_CALC= DOMAIN+"/index.php?tp=front/activity_detail_calc";

	//22.图文详情
	String ACTIVITY_DETAIL_INTRO= DOMAIN+"/index.php?tp=front/activity_detail_intro";
	String SHARETOQQ="mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D";

	//23 签到
	String DAYSIGN_NEW=DOMAIN+"/index.php?tp=front/daysign_new";

	//23 热门活动
	String ACT_0BUY=HUODONG_URL+"/index.php?tp=acts/act_0buy";

	//招募徒弟
	String PROM=DOMAIN+"/index.php?tp=front/prom";
	//晒单
	String SHAIDAN_NEW = DOMAIN + "/index.php?tp=front/shaidan_new";


	String SIGN_KEY = "#ccAv#896@ttdb";
	String SIGN_KEY_TTDB="x2cvty@Eaoqw";

	String SDK_VER="1.0";

	//支付宝SDK flag
	 int SDK_PAY_FLAG = 1;
	int SDK_AUTH_FLAG = 2;

	//充值平台
	String  ALI_PAY_PT="1";
	String WECHART_PAY_PT="2";
}
