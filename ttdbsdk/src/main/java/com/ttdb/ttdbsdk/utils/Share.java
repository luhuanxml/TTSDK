package com.ttdb.ttdbsdk.utils;

import com.ttdb.ttdbsdk.activity.TTWebActivity;

import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by Administrator on 2017/3/13 0013.
 */

public class Share {

    // 分享 1表示qq空间,2表示微博,3表示qq,4表示微信,5表示微信朋友圈
    private  boolean hasQzone = false;
    private  boolean hasWeibo = false;
    private  boolean hasQQ = false;
    private  boolean hasWechat = false;
    private  boolean hasWechatmoment = false;
    TTWebActivity mActivity;

    private static Share daysShare;

    private Share(TTWebActivity activity) {
        mActivity = activity;
    }
    public static Share getInstance(TTWebActivity activity){
        if (daysShare == null) {
            synchronized (Share.class){
                if (daysShare == null) {
                    daysShare=new Share(activity);
                }
            }
        }
        return daysShare;
    }

    /**
     *
     * @param share_title 分享标题
     * @param share_content 分享内容
     * @param share_link 分享链接
     * @param share_icon 分享图标
     */
    public void showShare(String share_title,
                                 String share_content,
                                 String share_link,
                                 String share_icon) {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        hasQzone = true;
        hasWeibo = true;
        hasQQ = true;
        hasWechat = true;
        hasWechatmoment = false;
        oks.addHiddenPlatform("WechatFavorite");
        oks.addHiddenPlatform("ShortMessage");
        if (!hasQQ) {
            oks.addHiddenPlatform("QQ");
        }
        if (!hasQzone) {
            oks.addHiddenPlatform("QZone");
        }
        if (!hasWechat) {
            oks.addHiddenPlatform("Wechat");
        }
        if (!hasWechatmoment) {
            oks.addHiddenPlatform("WechatMoments");
        }
        if (!hasWeibo) {
            oks.addHiddenPlatform("SinaWeibo");
        }

// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle(share_title);
// titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl(share_link);
// text是分享文本，所有平台都需要这个字段
        oks.setText(share_content);
// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
// url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(share_icon);
// site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(share_title);
// siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(share_link);

        oks.setCallback(mActivity);
// 启动分享GUI
        oks.show(mActivity);
    }
}
