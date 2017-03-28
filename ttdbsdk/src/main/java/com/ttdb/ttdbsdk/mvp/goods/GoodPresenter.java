package com.ttdb.ttdbsdk.mvp.goods;

import android.os.Handler;

import com.google.gson.Gson;
import com.ttdb.ttdbsdk.bean.GoodEntity;
import com.ttdb.ttdbsdk.common._Toast;
import com.ttdb.ttdbsdk.mvp.BaseModel;

import java.io.IOException;

/**
 * Created by Administrator on 2017/3/9 0009.
 */

public class GoodPresenter {
    private GoodModel goodModel;
    private GoodView goodView;
    private Gson gson;
    private Handler handler;

    public GoodPresenter(GoodView goodView) {
        this.goodView = goodView;
        goodModel=new GoodModelImp();
        gson=new Gson();
        handler=new Handler();
    }
    public void present(final int page, String type){
        goodModel.getGoodList(page + "", type, new BaseModel.Listener() {
            @Override
            public void onSuccees(String json) {
                final GoodEntity goodEntity= gson.fromJson(json, GoodEntity.class);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (page==1) {
                            goodView.showGoodList(goodEntity);
                        }else {
                            goodView.loadMoreGoodList(goodEntity);
                        }
                    }
                });

            }

            @Override
            public void onFailure(IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        _Toast.show("网络请求错误");
                    }
                });
            }
        });

    }
}
