package com.example.rajesh.mifeelingsapp;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position ==0) {
            return new Fragmentone();
        }
        if (position == 1) {
            return new Fragmenttwo();

        } if (position==2){
            return new Fragmentthree();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
