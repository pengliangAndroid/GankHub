package com.wstrong.mygank.views.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.wstrong.mygank.Constants;
import com.wstrong.mygank.R;
import com.wstrong.mygank.adapter.HomeAdapter;
import com.wstrong.mygank.adapter.MultiDataItem;
import com.wstrong.mygank.data.model.GankData;
import com.wstrong.mygank.presenter.HomePresenter;
import com.wstrong.mygank.presenter.iview.HomeView;
import com.wstrong.mygank.utils.DeviceUtils;
import com.wstrong.mygank.utils.LogUtil;
import com.wstrong.mygank.views.WebViewActivity;
import com.wstrong.mygank.widget.BorderDividerItemDecoration;
import com.wstrong.mygank.widget.CustomLinearLayoutManager;
import com.wstrong.mygank.widget.MultiSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;


/**
 * Created by pengl on 2016/9/10.
 */
public class HomeFragment extends LazyFragment implements HomeView, SwipeRefreshLayout.OnRefreshListener {
    private static final String ARGUMENT_CATEGORY = "ARGUMENT_CATEGORY";
    private static final String ARGUMENT_MULTITYPE = "ARGUMENT_MULTITYPE";

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.swipe_refresh_layout)
    MultiSwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.pb_loading)
    ProgressBar mPbLoading;

    HomePresenter mHomePresenter;

    HomeAdapter mAdapter;

    Handler handler = new Handler();

    private List<MultiDataItem> mDataList;

    private View noMoreView, errorView, emptyView;

    private int mMultiType;

    public static HomeFragment newInstance(String category,int multiType) {
        HomeFragment homeFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT_CATEGORY, category);
        bundle.putInt(ARGUMENT_MULTITYPE, multiType);
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

        mMultiType = getDataMultiType();
        LogUtil.d("mMultiType:"+mMultiType);

        if(mMultiType == 2){
            mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
            mRecyclerView.addItemDecoration(new BorderDividerItemDecoration(
                    DeviceUtils.dp2px(getActivity(), 6), DeviceUtils.dp2px(getActivity(), 3)));
        }else{
            mRecyclerView.setLayoutManager(new CustomLinearLayoutManager(getActivity()));
            mRecyclerView.addItemDecoration(new BorderDividerItemDecoration(
                    DeviceUtils.dp2px(getActivity(), 8), DeviceUtils.dp2px(getActivity(), 8)));
        }

        LayoutInflater inflater = getActivity().getLayoutInflater();
        noMoreView = inflater.inflate(R.layout.no_more_layout, (ViewGroup) mRecyclerView.getParent(), false);
        errorView = inflater.inflate(R.layout.error_view, (ViewGroup) mRecyclerView.getParent(), false);
        emptyView = inflater.inflate(R.layout.empty_view, (ViewGroup) mRecyclerView.getParent(), false);

        initAdapter();
    }

    private void initAdapter() {
        mDataList = new ArrayList<>();
        mAdapter = new HomeAdapter(getActivity(), mDataList);

        mAdapter.openLoadAnimation();
        mAdapter.isFirstOnly(true);
        mAdapter.openLoadMore(HomePresenter.PAGE_SIZE);

        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * 懒加载
     */
    @Override
    protected void loadData() {
        System.out.println("loadData");
        mHomePresenter = new HomePresenter(getDataCategory());
        mHomePresenter.attachView(this);

        loadNextPageData();
    }


    @Override
    protected void initListeners() {
        mRecyclerView.addOnItemTouchListener(new SimpleClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if(mMultiType == 3){
                    GankData dataItem = mAdapter.getItem(position).getGankData();
                    WebViewActivity.toUrl(getActivity(),dataItem.getUrl(),dataItem.getDesc());
                    //DataDetailActivity.toActivity(getActivity(),dataItem.getReadability());
                }
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
                if (!mHomePresenter.isHasMoreData()) {
                    mAdapter.loadComplete();
                    mAdapter.addFooterView(noMoreView);
                } else {
                    loadNextPageData();
                }
            }
        });
    }

    @Override
    protected void initData() {
    }

    /**
     * 加载下一页数据
     */
    private void loadNextPageData() {
        if(mMultiType == 1){
            //mHomePresenter.getGankDataWithImage();
            onGetDataFail(Constants.UNKNOWN_GET_ERROR);
        }else if(mMultiType == 2){
            mHomePresenter.getGankData();
        }else if(mMultiType == 3){
            mHomePresenter.getGankData();
        }
    }

    @Override
    public void onDestroyView() {
        if(mHomePresenter != null)
            mHomePresenter.detachView();
        super.onDestroyView();
    }

    @Override
    public void onRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 1500);
    }

    /**
     * 初始化MultiSwipeRefreshLayout
     */
    private void initSwipeRefreshLayout() {
        //mSwipeRefreshLayout = findView(R.id.swipe_refresh_layout);
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
    public void onLoadNoMoreData() {
        //showToast("网络异常，请刷新重试");
    }

    private String getDataCategory(){
        return getArguments().getString(ARGUMENT_CATEGORY);
    }

    private int getDataMultiType(){
        return getArguments().getInt(ARGUMENT_MULTITYPE);
    }

    @Override
    public void onGetDataSuccess(List<GankData> dataList) {
        mPbLoading.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);

        List<MultiDataItem> tmpList = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            tmpList.add(new MultiDataItem(mMultiType,dataList.get(i)));
        }

        mAdapter.addData(tmpList);
        mSwipeRefreshLayout.setRefreshing(false);
        System.out.println(tmpList.size());
    }

    @Override
    public void onGetDataFail(String errorMsg) {
        showToast(errorMsg);

        if(mPbLoading.getVisibility() == View.VISIBLE)
            mPbLoading.setVisibility(View.GONE);

        if(mRecyclerView.getVisibility() == View.GONE)
            mRecyclerView.setVisibility(View.VISIBLE);

        if(mDataList.size() == 0) {
            mAdapter.setEmptyView(errorView);
        }else{
            mAdapter.showLoadMoreFailedView();
        }

        mSwipeRefreshLayout.setRefreshing(false);
    }


}
