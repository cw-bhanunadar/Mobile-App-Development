package com.example.bhanu.controller;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.Fragments.BeenThere;
import com.Fragments.Images;
import com.Fragments.Reviews;

/**
 * Created by Bhanugoban Nadar on 1/21/2018.
 */

public class TabsPagerAdapter extends FragmentPagerAdapter {

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                // Top Rated fragment activity
                return new Reviews();
            case 1:
                // Games fragment activity
                return new BeenThere();
            case 2:
                // Movies fragment activity
                return new Images();
        }

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 3;
    }

}
