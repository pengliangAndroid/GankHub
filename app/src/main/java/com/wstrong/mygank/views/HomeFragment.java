package com.wstrong.mygank.views;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wstrong.mygank.R;
import com.wstrong.mygank.base.BaseFragment;
import com.wstrong.mygank.widget.MultiSwipeRefreshLayout;


/**
 * Created by pengl on 2016/9/10.
 */
public class HomeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{
    private boolean refreshStatus = false;

    //@Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    //@Bind(R.id.swipe_refresh_layout)
    MultiSwipeRefreshLayout mSwipeRefreshLayout;

    public static HomeFragment newInstance(){
        return new HomeFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initViews(View self, Bundle savedInstanceState) {
        mRecyclerView = findView(R.id.recycler_view);
        mSwipeRefreshLayout = findView(R.id.swipe_refresh_layout);

        initSwipeRefreshLayout();
    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onRefresh() {
        refreshStatus = true;

        refresh(false);
    }

    /**
     * 初始化MultiSwipeRefreshLayout
     */
    private void initSwipeRefreshLayout() {
        // 下拉刷新的颜色
        if (this.mSwipeRefreshLayout != null) {
            this.mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        }

        // 在刷新时，关闭刷新开关
        if (this.mSwipeRefreshLayout != null) {
            this.mSwipeRefreshLayout.setOnRefreshListener(this);
        }
    }

    /**
     * 刷新 true false
     *
     * @param refresh refresh
     */
    public void refresh(final boolean refresh) {
        if (this.mSwipeRefreshLayout == null) return;
        /*
         * refresh 只要进来是false 就不考虑 refreshStatus
         */
        if (!refresh && this.refreshStatus) {
            this.mSwipeRefreshLayout.postDelayed(new Runnable(){
                @Override
                public void run() {
                    HomeFragment.this.mSwipeRefreshLayout.setRefreshing(false);
                    HomeFragment.this.refreshStatus = false;
                }
            }, 1500);
        } else if (!this.refreshStatus) {
            /*
             * 排除了refreshStatus==true的情况
             */
            this.mSwipeRefreshLayout.post(new Runnable(){
                @Override
                public void run() {
                    HomeFragment.this.mSwipeRefreshLayout.setRefreshing(true);
                }
            });
            this.refreshStatus = true;
        }
    }
}
