package com.ttdb.ttdbsdk.mvp.newarea;

import android.os.Handler;

import com.google.gson.Gson;
import com.ttdb.ttdbsdk.bean.IosIndexEntity;
import com.ttdb.ttdbsdk.common._Toast;
import com.ttdb.ttdbsdk.mvp.BaseModel;

import java.io.IOException;

/**
 * Created by Administrator on 2017/3/9 0009.
 */

public class NewAreaPresenter {
    private NewAreaModel newAreaModel;
    private NewAreaView newAreaView;
    private Gson gson;
    private Handler handler;

    public NewAreaPresenter(NewAreaView newAreaView) {
        this.newAreaView = newAreaView;
        newAreaModel = new NewAreaModelImp();
        gson = new Gson();
        handler = new Handler();
    }

    public void present() {
        newAreaModel.getIosIndex(new BaseModel.Listener() {
            @Override
            public void onSuccees(String json) {
                final IosIndexEntity iosIndexEntity = gson.fromJson(json, IosIndexEntity.class);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        newAreaView.showNewArea(iosIndexEntity);
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
