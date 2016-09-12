package com.wstrong.mygank.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.wstrong.mygank.config.DataType;
import com.wstrong.mygank.views.CommonFragment;
import com.wstrong.mygank.views.HomeFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pengl on 2016/9/10.
 */
public class MainPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList;


    private String[] mTabNames;

    public MainPagerAdapter(FragmentManager manager){
        super(manager);

        mTabNames = DataType.getAllNames();

        mFragmentList = new ArrayList<>();

        mFragmentList.add(HomeFragment.newInstance(DataType.ANDROID.getName()));

        for (int i = 1; i < mTabNames.length; i++) {
            mFragmentList.add(CommonFragment.newInstance());
        }
    }

    @Override
    public int getCount() {
        return mTabNames.length;
    }


    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    public String getPageTitle(int position){
       return mTabNames[position];
    }

}
