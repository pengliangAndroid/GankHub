package com.wstrong.mygank.presenter;

import android.text.TextUtils;

import com.wstrong.mygank.base.mvp.BasePresenter;
import com.wstrong.mygank.config.DataType;
import com.wstrong.mygank.data.model.GankData;
import com.wstrong.mygank.data.model.GankDataWrapper;
import com.wstrong.mygank.presenter.iview.HomeView;
import com.wstrong.mygank.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func2;

/**
 * Created by pengl on 2016/9/12.
 */
public class HomePresenter extends BasePresenter<HomeView> {
    public static final int PAGE_SIZE = 10;

    private int mCurPage = 1;

    private String mCategory;

    //private HashMap<String,Integer> mPageMap;

    private boolean hasMoreData = true;

    public HomePresenter(String category){
        //mPageMap = new HashMap<>();
        if(!TextUtils.isEmpty(category)){
            mCategory = DataType.ALL.getName();
        }

        mCategory = category;
    }

    public void getGankData(){
        //根据不同类型的数据类型加载不同的数据
        /*if(!mPageMap.containsKey(category)){
            mCurPage = 1;
            mPageMap.put(category,mCurPage);
        }else{
            mCurPage = mPageMap.get(category);
        }*/

        mCompositeSubscription.add(mDataManager.getDataRestApi().getDailyData(mCategory,PAGE_SIZE,mCurPage)
        .subscribe(new Subscriber<List<GankData>>() {
            @Override
            public void onCompleted() {
                mCurPage++;
                if (mCompositeSubscription != null)
                    mCompositeSubscription.remove(this);
            }

            @Override
            public void onError(Throwable e) {
                if (e != null)
                    LogUtil.d(e.getMessage());
                getMvpView().onGetDataFail(""+e.toString());
            }

            @Override
            public void onNext(List<GankData> dataList) {
                getMvpView().onGetDataSuccess(dataList);
            }
        }));
    }

    public void getGankDataWrapper(){
        if(DataType.WELFARE.getName().equals(mCategory)) {
            throw new IllegalArgumentException("非法的数据类型");
        }

        Observable welfareObservable = mDataManager.getDataRestApi().
                getDailyData(DataType.WELFARE.getName(),PAGE_SIZE,mCurPage);

        Observable dataObservable = mDataManager.getDataRestApi()
                .getDailyData(mCategory, PAGE_SIZE, mCurPage);

        mCompositeSubscription
                .add(dataObservable.zipWith(welfareObservable,
                        new Func2<List<GankData>, List<GankData>, List<GankDataWrapper>>() {
                            @Override
                            public List<GankDataWrapper> call(List<GankData> dataList, List<GankData> welfareDataList) {
                                List<GankDataWrapper> newDataList = new ArrayList<>();
                                for (int i = 0; i < dataList.size(); i++) {
                                    GankData object = dataList.get(i);
                                    GankDataWrapper data = transform(object);
                                    data.setImageUrl(welfareDataList.get(i).getUrl());

                                    newDataList.add(data);
                                }
                                return newDataList;
                            }
                        })
                .subscribe(new Subscriber<List<GankDataWrapper>>() {
                    @Override
                    public void onCompleted() {
                        mCurPage++;
                        if (mCompositeSubscription != null)
                            mCompositeSubscription.remove(this);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e != null)
                            LogUtil.d(e.getMessage());
                        getMvpView().onGetDataWrapperFail(""+e.toString());
                    }

                    @Override
                    public void onNext(List<GankDataWrapper> dataList) {
                        getMvpView().onGetDataWrapperSuccess(dataList);
                    }
                }));
    }

    public GankDataWrapper transform(GankData data){
        GankDataWrapper object = new GankDataWrapper();
        object.setType(data.getType());
        object.setDesc(data.getDesc());
        object.setGanhuo_id(data.getGanhuo_id());
        object.setPublishedAt(data.getPublishedAt());
        object.setReadability(data.getReadability());
        object.setUrl(data.getUrl());
        object.setWho(data.getWho());

        return object;
    }


    public int getCurPage() {
        return mCurPage;
    }

    public boolean isHasMoreData() {
        return hasMoreData;
    }
}
