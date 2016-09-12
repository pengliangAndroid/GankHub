package com.wstrong.mygank.views;

import android.os.Bundle;
import android.view.View;

import com.wstrong.mygank.R;
import com.wstrong.mygank.base.BaseFragment;

/**
 * Created by pengl on 2016/9/12.
 */
public class CommonFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_common;
    }

    @Override
    protected void initViews(View self, Bundle savedInstanceState) {

    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initData() {

    }

    public static CommonFragment newInstance(){
        return new CommonFragment();
    }
}
