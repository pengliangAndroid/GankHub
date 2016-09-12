package com.wstrong.mygank.views;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.wstrong.mygank.R;
import com.wstrong.mygank.adapter.HomeAdapter;
import com.wstrong.mygank.base.BaseFragment;
import com.wstrong.mygank.data.model.GankData;
import com.wstrong.mygank.data.model.GankDataWrapper;
import com.wstrong.mygank.presenter.HomePresenter;
import com.wstrong.mygank.presenter.iview.HomeView;
import com.wstrong.mygank.utils.DensityUtils;
import com.wstrong.mygank.utils.LogUtil;
import com.wstrong.mygank.widget.BorderDividerItemDecoration;
import com.wstrong.mygank.widget.CustomLinearLayoutManager;
import com.wstrong.mygank.widget.MultiSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by pengl on 2016/9/10.
 */
public class HomeFragment extends BaseFragment implements HomeView,SwipeRefreshLayout.OnRefreshListener{
    private static final String ARGUMENT_TYPE = "type";

    //@Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    //@Bind(R.id.swipe_refresh_layout)
    MultiSwipeRefreshLayout mSwipeRefreshLayout;

    HomePresenter mHomePresenter;

    HomeAdapter mAdapter;

    List<GankDataWrapper> mDataList;

    Handler handler = new Handler();

    private View noMoreView,errorView,emptyView;

    public static HomeFragment newInstance(String type){
        HomeFragment homeFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT_TYPE,type);
        homeFragment.setArguments(bundle);
        return homeFragment;
    }


    @Override
    protected int getLayoutId() {
        LogUtil.d("HomeFragment getLayoutId().");
        return R.layout.fragment_home;
    }

    @Override
    protected void initViews(View self, Bundle savedInstanceState) {
        initSwipeRefreshLayout();

        mRecyclerView = findView(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new CustomLinearLayoutManager(getActivity()));

        mRecyclerView.addItemDecoration(new BorderDividerItemDecoration(
                DensityUtils.dp2px(getActivity(),8), DensityUtils.dp2px(getActivity(),8)));

        LayoutInflater inflater = getActivity().getLayoutInflater();
        noMoreView = inflater.inflate(R.layout.no_more_layout,(ViewGroup) mRecyclerView.getParent(), false);
        errorView = inflater.inflate(R.layout.error_view,(ViewGroup) mRecyclerView.getParent(), false);
        emptyView = inflater.inflate(R.layout.empty_view,(ViewGroup) mRecyclerView.getParent(), false);

        initAdapter();
    }

    private void initAdapter() {
        mDataList = new ArrayList<>();
        mAdapter = new HomeAdapter(getActivity(),R.layout.item_daily,mDataList);

        mAdapter.openLoadAnimation();
        mAdapter.isFirstOnly(true);
        mAdapter.openLoadMore(HomePresenter.PAGE_SIZE);

        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initListeners() {
        mRecyclerView.addOnItemTouchListener(new SimpleClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, final int position) {
                //Toast.makeText(SwipeRefreshActivity.this,"onItemClick position:"+position,Toast.LENGTH_SHORT).show();
                System.out.println(position);
                System.out.println("headerCount:"+adapter.getHeaderLayoutCount());
                adapter.remove(position);
            }

            @Override
            public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

            }

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
            }

            @Override
            public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {
            }
        });

        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if(!mHomePresenter.isHasMoreData()){
                    mAdapter.loadComplete();
                    mAdapter.addFooterView(noMoreView);
                }else{
                    loadNextPageData();
                }
            }
        });
    }

    @Override
    protected void initData() {
        mHomePresenter = new HomePresenter(getArguments().getString(ARGUMENT_TYPE));
        mHomePresenter.attachView(this);

        loadNextPageData();
    }

    /**
     * 加载下一页数据
     */
    private void loadNextPageData() {
        mSwipeRefreshLayout.setRefreshing(true);
        mHomePresenter.getGankDataWrapper();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        mHomePresenter.detachView();
    }

    @Override
    public void onRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        },1500);
    }

    /**
     * 初始化MultiSwipeRefreshLayout
     */
    private void initSwipeRefreshLayout() {
        mSwipeRefreshLayout = findView(R.id.swipe_refresh_layout);
        // 下拉刷新的颜色
        if (this.mSwipeRefreshLayout != null) {
            this.mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        }

        // 在刷新时，关闭刷新开关
        if (this.mSwipeRefreshLayout != null) {
            this.mSwipeRefreshLayout.setOnRefreshListener(this);
        }
    }


    @Override
    public void onGetDataWrapperSuccess(List<GankDataWrapper> dataList) {
        //showToast("加载数据成功");
        mAdapter.addData(dataList);
        mSwipeRefreshLayout.setRefreshing(false);
        System.out.println(dataList.size());
    }

    @Override
    public void onGetDataWrapperFail(String errorMsg) {
        showToast(errorMsg);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoadNoMoreData() {
        showToast("网络异常，请刷新重试");
        mAdapter.showLoadMoreFailedView();
    }

    @Override
    public void onGetDataSuccess(List<GankData> dataList) {

    }

    @Override
    public void onGetDataFail(String errorMsg) {

    }
}
