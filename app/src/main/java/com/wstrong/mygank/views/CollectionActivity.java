package com.wstrong.mygank.views;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.wstrong.mygank.R;
import com.wstrong.mygank.adapter.CollectionAdapter;
import com.wstrong.mygank.base.BaseToolbarActivity;
import com.wstrong.mygank.data.model.Collection;
import com.wstrong.mygank.presenter.CollectionPresenter;
import com.wstrong.mygank.presenter.iview.CollectionView;
import com.wstrong.mygank.utils.LogUtil;
import com.wstrong.mygank.widget.CustomLinearLayoutManager;
import com.wstrong.mygank.widget.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class CollectionActivity extends BaseToolbarActivity implements CollectionView{
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;

    List<Collection> mDataList;

    CollectionAdapter mAdapter;

    CollectionPresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_collection;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        showBack();
        setTitle("收藏");
        mRecyclerView.setLayoutManager(new CustomLinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
    }

    @Override
    protected void initData() {
        mPresenter = new CollectionPresenter();
        mPresenter.attachView(this);

        mDataList = new ArrayList<>();
        mAdapter = new CollectionAdapter(R.layout.item_site,mDataList);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter adapter, View view, int position) {
                Collection collection = mAdapter.getData().get(position);
                WebViewActivity.toUrl(CollectionActivity.this,collection.getUrl(),collection.getTitle());
            }
        });

        mPresenter.getCollectionList();

    }


    @Override
    protected void initListener() {
    }


    @Override
    public void onGetCollectionListSuccess(List<Collection> dataList) {
        LogUtil.d("size:"+dataList.size());
        mAdapter.addData(dataList);
    }

    @Override
    public void onGetCollectionListFail(String error) {
        showToast(error);
    }
}
