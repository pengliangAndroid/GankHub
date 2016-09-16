package com.wstrong.mygank.presenter;

import android.text.TextUtils;

import com.wstrong.mygank.Constants;
import com.wstrong.mygank.base.mvp.BasePresenter;
import com.wstrong.mygank.config.DataType;
import com.wstrong.mygank.data.model.GankData;
import com.wstrong.mygank.presenter.iview.HomeView;
import com.wstrong.mygank.utils.RxUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by pengl on 2016/9/12.
 */
public class HomePresenter extends BasePresenter<HomeView> {
    public static final int PAGE_SIZE = 10;

    private int mCurPage = 1;

    private String mCategory;

    private boolean hasMoreData = true;

    public HomePresenter(String category){
        //mPageMap = new HashMap<>();
        if(!TextUtils.isEmpty(category)){
            mCategory = DataType.ALL.getName();
        }

        mCategory = category;
    }


    public void getGankData(){
        mCompositeSubscription.add(mDataManager.getCategoryData(mCategory,PAGE_SIZE,mCurPage)
                .map(new Func1<List<GankData>, List<GankData>>() {
                    @Override
                    public List<GankData> call(List<GankData> dataList) {
                        Collections.sort(dataList, new Comparator<GankData>() {
                            @Override
                            public int compare(GankData o1, GankData o2) {
                                return o2.getPublishedAt().compareTo(o1.getPublishedAt());
                            }
                        });
                        return dataList;
                    }
                })
                .compose(RxUtils.<List<GankData>>applyIOToMainThreadSchedulers())
                .subscribe(new Subscriber<List<GankData>>() {
                    @Override
                    public void onCompleted() {
                        mCurPage++;
                        if (mCompositeSubscription != null)
                            mCompositeSubscription.remove(this);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e != null) {
                            getMvpView().onGetDataFail(Constants.UNKNOWN_GET_ERROR);
                        }else{
                            getMvpView().onGetDataFail(Constants.UNKNOWN_ERROR);
                        }
                    }

                    @Override
                    public void onNext(List<GankData> dataList) {

                        getMvpView().onGetDataSuccess(dataList);
                    }
                }));
    }

    /*
    public void getGankDataWithImage(){
        if(DataType.WELFARE.getCategory().equals(mCategory)) {
            throw new IllegalArgumentException("非法的数据类型");
        }

        Observable<List<GankData>> welfareObservable = mDataManager.
                getCategoryData(DataType.WELFARE.getCategory(),PAGE_SIZE,mCurPage);

        Observable<List<GankData>>  dataObservable = mDataManager
                .getCategoryData(mCategory, PAGE_SIZE, mCurPage);

        mCompositeSubscription
                .add(dataObservable.zipWith(welfareObservable, new Func2<List<GankData>, List<GankData>, List<GankData>>() {
                            @Override
                            public List<GankData> call(List<GankData> dataList, List<GankData> welfareDataList) {
                                for (int i = 0; i < dataList.size(); i++) {
                                    dataList.get(i).setImageUrl(welfareDataList.get(i).getUrl());
                                }

                                Collections.sort(dataList, new Comparator<GankData>() {
                                    @Override
                                    public int compare(GankData o1, GankData o2) {
                                        return o2.getPublishedAt().compareTo(o1.getPublishedAt());
                                    }
                                });
                                return dataList;
                            }
                        })
                .compose(RxUtils.<List<GankData>>applyIOToMainThreadSchedulers())
                .subscribe(new Subscriber<List<GankData>>() {
                    @Override
                    public void onCompleted() {
                        mCurPage++;
                        if (mCompositeSubscription != null)
                            mCompositeSubscription.remove(this);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e != null) {
                            getMvpView().onGetDataFail(Constants.UNKNOWN_GET_ERROR);
                        }else{
                            getMvpView().onGetDataFail(Constants.UNKNOWN_ERROR);
                        }
                    }

                    @Override
                    public void onNext(List<GankData> dataList) {
                        getMvpView().onGetDataSuccess(dataList);
                    }
                }));


    }

*/
    public int getCurPage() {
        return mCurPage;
    }

    public boolean isHasMoreData() {
        return hasMoreData;
    }
}
