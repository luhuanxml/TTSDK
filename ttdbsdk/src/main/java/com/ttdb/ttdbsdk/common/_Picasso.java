package com.ttdb.ttdbsdk.common;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.ttdb.ttdbsdk.R;

/**
 * Created by Administrator on 2017/3/9 0009.
 */

public class _Picasso {
    private static Context mContext;
    public static void init(Context context){
        mContext=context;
    }

    public static void loadImg(String urlPic, ImageView imageView){
        Picasso.with(mContext).load(urlPic)
                .placeholder(R.mipmap.placeholde)
                .error(R.mipmap.placeholde)
                .into(imageView);
    }

    public static RequestCreator loadImg(String urlPic){
        return Picasso.with(mContext).load(urlPic);
    }
}
