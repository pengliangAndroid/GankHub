package com.wstrong.mygank.views.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wstrong.mygank.base.BaseFragment;

/**
 * Fragment懒加载问题
 * Created by pengl on 2016/9/14.
 */
public abstract class LazyFragment extends BaseFragment {
    //是否用户可见
    protected boolean isVisible = false;

    //是否加载过数据
    protected boolean isLoadData = false;

    private boolean isPrepared;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(getUserVisibleHint()){
            isVisible = true;
            //只有用户可见时进行加载
            lazyLoad();
        }else{
            isVisible = false;
            onInVisible();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isPrepared = true;
        lazyLoad();
    }


    protected abstract void loadData();

    public void onInVisible(){}

    protected void lazyLoad(){
        if (!isPrepared || !isVisible || isLoadData) {
            return;
        }

        loadData();
        isLoadData = true;
    }

}
