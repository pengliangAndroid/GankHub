package com.wstrong.mygank.base;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;

import com.wstrong.mygank.R;
import com.wstrong.mygank.widget.MultiSwipeRefreshLayout;

import butterknife.Bind;

/**
 * Description：BaseSwipeRefreshLayoutActivity
 * Time：2016-01-05 11:39
 */
public abstract class BaseSwipeRefreshLayoutActivity extends BaseToolbarActivity {

    @Bind(R.id.swipe_refresh_layout) protected MultiSwipeRefreshLayout
            mSwipeRefreshLayout;

    private boolean refreshStatus = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        this.initSwipeRefreshLayout();
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
            this.mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    onSwipeRefresh();
                }
            });
        }
    }


    /**
     * 刷新的时候
     */
    public abstract void onSwipeRefresh();


    /**
     * 设置刷新状态
     *
     * @param status status
     */
    public void setRefreshStatus(boolean status) {
        this.refreshStatus = status;
    }


    /**
     * 获取当前刷新状态
     *
     * @return boolean
     */
    public boolean isRefreshStatus() {
        return refreshStatus;
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
         * 所以用了短路&&，则直接关掉
         */
        if (!refresh && this.refreshStatus) {
            // 到这了 refresh==false && refreshStatus==true
            this.mSwipeRefreshLayout.postDelayed(new Runnable(){
                @Override
                public void run() {
                    BaseSwipeRefreshLayoutActivity.this.mSwipeRefreshLayout.setRefreshing(false);
                    BaseSwipeRefreshLayoutActivity.this.refreshStatus = false;
                }
            }, 1500);
        } else if (!this.refreshStatus) {
            /*
             * 到这了，refresh==true，refreshStatus==false
             * 排除了refreshStatus==true的情况
             */
            this.mSwipeRefreshLayout.post(new Runnable(){
                @Override
                public void run() {
                    BaseSwipeRefreshLayoutActivity.this.mSwipeRefreshLayout.setRefreshing(true);
                }
            });
            this.refreshStatus = true;
        }
    }
}
