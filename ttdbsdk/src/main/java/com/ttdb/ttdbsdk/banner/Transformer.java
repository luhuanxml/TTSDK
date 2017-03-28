package com.ttdb.ttdbsdk.banner;

import android.support.v4.view.ViewPager.PageTransformer;

import com.ttdb.ttdbsdk.banner.transformer.DefaultTransformer;


public class Transformer {
    public static Class<? extends PageTransformer> Default = DefaultTransformer.class;
}
