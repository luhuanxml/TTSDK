package com.ttdb.ttdbsdk.bean;

/**
 * Created by Administrator on 2017/3/18 0018.
 */

public class IndexEvent {
    public static final int HOME = 0;
    public final static int DETAIL = 1;
    public final static int CLASSIFY = 2;
    public final static int PRIZES = 3;
    public final static int MY = 4;
    private Integer index;

    public IndexEvent(Integer index) {
        this.index = index;
    }

    public Integer getIndex(){
        return index;
    }

}
