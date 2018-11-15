package com.example.akhilbatchu.iot;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();


    public Adapter(FragmentManager fm)
    {
        super(fm);
    }



    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                Fragmentone one = new Fragmentone();
                return one;
            case 1:
                Fragmenttwo two = new Fragmenttwo();
                return two;
            default:
                return null;

        }
    }

    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return  mFragmentTitleList.get(position);
    }
}
