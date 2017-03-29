package com.ttdb.ttdbsdk.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class PagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragments;
    public PagerAdapter(FragmentManager fm,ArrayList<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }


    @Override
    public int getCount() {
        return fragments==null?0:fragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }
}
