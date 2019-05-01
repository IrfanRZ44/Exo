package com.exomatik.irfanrz.exomatik.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IrfanRZ on 6/2/2018.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> fragmentList = new ArrayList<>();
//    private final List<String> fragmenListTitle = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

//    @Override
//    public CharSequence getPageTitle(int posisiImgAnggota) {
//        return fragmenListTitle.get(posisiImgAnggota);
//    }
    public void AddFragment(Fragment fragment){
        fragmentList.add(fragment);
//        fragmenListTitle.add(Title);
    }
}