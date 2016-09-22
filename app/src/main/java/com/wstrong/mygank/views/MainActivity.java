package com.wstrong.mygank.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.umeng.analytics.MobclickAgent;
import com.wstrong.mygank.Constants;
import com.wstrong.mygank.R;
import com.wstrong.mygank.adapter.MainPagerAdapter;
import com.wstrong.mygank.base.BaseDrawerLayoutActivity;
import com.wstrong.mygank.config.Config;
import com.wstrong.mygank.utils.LogUtil;
import com.wstrong.mygank.utils.rx.RxBus;

import butterknife.Bind;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class MainActivity extends BaseDrawerLayoutActivity {


    @Bind(R.id.tab_layout)
    TabLayout mTabLayout;
    @Bind(R.id.view_pager)
    ViewPager mViewPager;

    MainPagerAdapter mPagerAdapter;

    Observable mRxErrorMsg,mRxShowView;

    @Override
    protected int getLayoutId() {
        //setStatusCompat(false);
        return R.layout.activity_main;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
    }

    @Override
    protected void initData() {
        mPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());

        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mViewPager.setOffscreenPageLimit(mPagerAdapter.getCount() - 1);
        mViewPager.setAdapter(mPagerAdapter);

        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected void initListener() {
        mRxErrorMsg = RxBus.get().register(Config.TAG_SERVER_ERROR, String.class);
        mRxErrorMsg.observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        LogUtil.d("mRxErrorMsg:"+s);
                    }
                });


        mRxShowView = RxBus.get().register(Config.TAG_SHOW_VIEW, String.class);
        mRxShowView.observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        LogUtil.d("mRxShowView:"+s);
                        showSnack(s);
                    }
                });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister(Config.TAG_SERVER_ERROR,mRxErrorMsg);
        RxBus.get().unregister(Config.TAG_SHOW_VIEW,mRxShowView);

        LogUtil.d("MainActivity onDestroy.");
    }

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
                } else if(item.getItemId() == R.id.nav_site){
                    //menuItemChecked(R.id.nav_home);
                    startActivity(new Intent(MainActivity.this,SiteActivity.class));
                    return true;
                }  else if(item.getItemId() == R.id.nav_favorite){
                    startActivity(new Intent(MainActivity.this,CollectionActivity.class));
                    return true;
                }else {
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
            case R.id.action_gank:
                WebViewActivity.toUrl(this, Constants.BASE_URL_SITE,"干货集中营");
                return true;
            case R.id.action_github:
                WebViewActivity.toUrl(this, Constants.GITHUB_TRENDING,"热门Github仓库");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showSnack(String message) {

    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }


    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

}
