package com.wstrong.mygank.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.wstrong.mygank.views.HomeFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pengl on 2016/9/10.
 */
public class MainPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList;


    private final String[] mTabNames = new String[]{
            "热门","福利","Android","IOS",
            "前端","休息视频","拓展资源","App"
    };

    public MainPagerAdapter(FragmentManager manager){
        super(manager);

        mFragmentList = new ArrayList<>();
        for (int i = 0; i < mTabNames.length; i++) {
            mFragmentList.add(HomeFragment.newInstance());
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
