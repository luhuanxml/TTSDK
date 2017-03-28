package com.ttdb.ttdbsdk.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/9 0009.
 */

public class IosIndexEntity {

    /**
     * msg :
     * status : 1
     * data : {"icon":[{"id":"8","pic":"/templates/images/front/new_user_hb.png","title":"新手红包","type":"2","appid":"0","url":"http://a.ttdb.com/index.php?tp=control/newer_task","spic":""},{"id":"9","pic":"/templates/images/front/db_ketang.png","title":"夺宝课堂","type":"2","appid":"0","url":"http://huodong.3z.cc/index.php?tp=acts/act_newerclass","spic":""},{"id":"935","pic":"/templates/images/front/new_db.png","title":"新手夺宝","type":"3","appid":"0","url":"http://a.ttdb.com/index.php?tp=front/activity_detail&info_id=","spic":""},{"id":"11","pic":"/templates/images/front/new_0buy.png","title":"0元就购","type":"2","appid":"0","url":"http://huodong.3z.cc/index.php?tp=acts/act_0buy","spic":""}],"eye":[{"id":"19","pic":"http://db.3z.cc/templates/images/front/btn_double.jpg","title":"新用户首充双倍","type":"1","appid":"0","url":"http://huodong.3z.cc/index.php?tp=acts/act_double","spic":""},{"id":"20","pic":"http://db.3z.cc/templates/images/front/btn_sukai.png?1","title":"速开专区","type":"2","appid":"0","url":"http://huodong.3z.cc/index.php?tp=acts/act_zero","spic":""}]}
     */

    private String msg;
    private String status;
    private DataBean data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<IconBean> icon;
        private List<EyeBean> eye;

        public List<IconBean> getIcon() {
            return icon;
        }

        public void setIcon(List<IconBean> icon) {
            this.icon = icon;
        }

        public List<EyeBean> getEye() {
            return eye;
        }

        public void setEye(List<EyeBean> eye) {
            this.eye = eye;
        }

        public static class IconBean {
            /**
             * id : 8
             * pic : /templates/images/front/new_user_hb.png
             * title : 新手红包
             * type : 2
             * appid : 0
             * url : http://a.ttdb.com/index.php?tp=control/newer_task
             * spic :
             */

            private String id;
            private String pic;
            private String title;
            private String type;
            private String appid;
            private String url;
            private String spic;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getAppid() {
                return appid;
            }

            public void setAppid(String appid) {
                this.appid = appid;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getSpic() {
                return spic;
            }

            public void setSpic(String spic) {
                this.spic = spic;
            }
        }

        public static class EyeBean {
            /**
             * id : 19
             * pic : http://db.3z.cc/templates/images/front/btn_double.jpg
             * title : 新用户首充双倍
             * type : 1
             * appid : 0
             * url : http://huodong.3z.cc/index.php?tp=acts/act_double
             * spic :
             */

            private String id;
            private String pic;
            private String title;
            private String type;
            private String appid;
            private String url;
            private String spic;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getAppid() {
                return appid;
            }

            public void setAppid(String appid) {
                this.appid = appid;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getSpic() {
                return spic;
            }

            public void setSpic(String spic) {
                this.spic = spic;
            }
        }
    }
}
