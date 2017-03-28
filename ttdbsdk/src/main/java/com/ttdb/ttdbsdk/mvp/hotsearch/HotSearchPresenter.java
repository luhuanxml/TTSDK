package com.ttdb.ttdbsdk.mvp.hotsearch;

import android.os.Handler;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.ttdb.ttdbsdk.mvp.BaseModel;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2017/3/15 0015.
 */

public class HotSearchPresenter {
    private HotSearchModel hotSearchModel;
    private HotSearchView hotSearchView;
    private Handler handler;
    private Gson gson;

    public HotSearchPresenter(HotSearchView hotSearchView) {
        this.hotSearchView = hotSearchView;
        hotSearchModel=new HOtSearchModelImp();
        handler=new Handler();
        gson=new Gson();
    }

    public void present(){
        hotSearchModel.getHotSearchList(new BaseModel.Listener() {
            @Override
            public void onSuccees(String json) {
                final List<LinkedTreeMap<String,String>> list=gson.fromJson(json,List.class);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        hotSearchView.showHotSearchList(list);
                    }
                });

            }

            @Override
            public void onFailure(IOException e) {

            }
        });
    }
}
