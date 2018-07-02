package com.shopping.lifeplay.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.shopping.lifeplay.fragment.AllService;
import com.shopping.lifeplay.fragment.BodyCare;
import com.shopping.lifeplay.fragment.FaceCare;
import com.shopping.lifeplay.fragment.More;
import com.shopping.lifeplay.fragment.SexualWellness;
import com.shopping.lifeplay.fragment.SkinHairCare;


/**
 * Created by user on 10/24/2017.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0) {
            fragment = new AllService();
        } else if (position == 1) {
            fragment = new FaceCare();
        } else if (position == 2) {
            fragment = new BodyCare();
        } else if (position == 3) {
            fragment = new SexualWellness();
        } else if (position == 4) {
            fragment = new SkinHairCare();
        } else if (position == 5) {
            fragment = new More();
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0) {
            title = "All Product";
        } else if (position == 1) {
            title = "Face Care";
        } else if (position == 2) {
            title = "Body Care";
        } else if (position == 3) {
            title = "Sexual Wellness";
        } else if (position == 4) {
            title = "Skin & Hair Care";
        } else if (position == 5) {
            title = "more";
        }
        return title;
    }
}