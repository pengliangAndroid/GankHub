package com.wstrong.mygank.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.wstrong.mygank.config.DataType;
import com.wstrong.mygank.views.fragment.HomeFragment;

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

        mFragmentList.add(HomeFragment.newInstance(DataType.ALL.getCategory(), DataType.getMultiType(DataType.ALL)));
        mFragmentList.add(HomeFragment.newInstance(DataType.ANDROID.getCategory(), DataType.getMultiType(DataType.ANDROID)));
        mFragmentList.add(HomeFragment.newInstance(DataType.IOS.getCategory(), DataType.getMultiType(DataType.IOS)));
        mFragmentList.add(HomeFragment.newInstance(DataType.WELFARE.getCategory(), DataType.getMultiType(DataType.WELFARE)));
        mFragmentList.add(HomeFragment.newInstance(DataType.VIDEOS.getCategory(), DataType.getMultiType(DataType.VIDEOS)));
        mFragmentList.add(HomeFragment.newInstance(DataType.RESOURCES.getCategory(), DataType.getMultiType(DataType.RESOURCES)));
        mFragmentList.add(HomeFragment.newInstance(DataType.RECOMMEND.getCategory(), DataType.getMultiType(DataType.RECOMMEND)));
        mFragmentList.add(HomeFragment.newInstance(DataType.JS.getCategory(), DataType.getMultiType(DataType.JS)));
        mFragmentList.add(HomeFragment.newInstance(DataType.APP.getCategory(), DataType.getMultiType(DataType.APP)));
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
