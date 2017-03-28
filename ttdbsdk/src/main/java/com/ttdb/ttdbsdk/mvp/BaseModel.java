package com.ttdb.ttdbsdk.mvp;

import java.io.IOException;

/**
 * Created by Administrator on 2017/3/9 0009.
 */

public interface BaseModel {
    interface Listener{
        void onSuccees(String json);
        void onFailure(IOException e);
    }
}
