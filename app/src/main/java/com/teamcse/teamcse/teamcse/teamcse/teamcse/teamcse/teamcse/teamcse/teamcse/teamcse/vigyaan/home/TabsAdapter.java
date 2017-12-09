package com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by samveg on 6/10/17.
 */

public class TabsAdapter extends FragmentPagerAdapter {

    List<Fragment> fragmentList = new ArrayList<>();

    public TabsAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(Fragment fragment){


        fragmentList.add(fragment);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
