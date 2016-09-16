package com.wstrong.mygank.views.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wstrong.mygank.R;
import com.wstrong.mygank.utils.LogUtil;

import butterknife.Bind;

/**
 * Created by pengl on 2016/9/12.
 */
public class CommonFragment extends LazyFragment {
    @Bind(R.id.textView)
    TextView mTextView;
    @Bind(R.id.pb_loading)
    ProgressBar mPbLoading;

    @Override
    protected int getLayoutId() {
        LogUtil.d("CommonFragment getLayoutId()");
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

    public static CommonFragment newInstance() {
        return new CommonFragment();
    }

    @Override
    protected void loadData() {
        mPbLoading.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPbLoading.setVisibility(View.GONE);
            }
        },1500);
    }

}
