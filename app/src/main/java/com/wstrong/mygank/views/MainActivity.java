package com.wstrong.mygank.views;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.wstrong.mygank.Constants;
import com.wstrong.mygank.R;
import com.wstrong.mygank.adapter.MainPagerAdapter;
import com.wstrong.mygank.base.BaseDrawerLayoutActivity;

import butterknife.Bind;

public class MainActivity extends BaseDrawerLayoutActivity {


    @Bind(R.id.tab_layout)
    TabLayout mTabLayout;
    @Bind(R.id.view_pager)
    ViewPager mViewPager;

    MainPagerAdapter mPagerAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {
        mPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());

        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        mViewPager.setAdapter(mPagerAdapter);

        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected NavigationView.OnNavigationItemSelectedListener getNavigationItemSelectedListener() {
        return new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if (item.getItemId() == R.id.nav_settings) {

                    mDrawerLayout.closeDrawer(mNavigationView);
                    return true;
                } else if (item.getItemId() == R.id.nav_read_mode) {

                    mDrawerLayout.closeDrawer(mNavigationView);
                    return true;
                } else {
                    return menuItemChecked(item.getItemId());
                }
            }
        };
    }

    @Override
    protected int[] getMenuItemIds() {
        return Constants.NAV_MENU_ITEMS;
    }

    @Override
    protected void onMenuItemOnClick(MenuItem item) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
