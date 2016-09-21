package com.wstrong.mygank.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.wstrong.mygank.Constants;
import com.wstrong.mygank.adapter.MultiDataItem;
import com.wstrong.mygank.base.mvp.BasePresenter;
import com.wstrong.mygank.config.DataType;
import com.wstrong.mygank.data.model.GankDailyData;
import com.wstrong.mygank.data.model.GankData;
import com.wstrong.mygank.presenter.iview.HomeView;
import com.wstrong.mygank.utils.DateUtils;
import com.wstrong.mygank.utils.RxUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Created by pengl on 2016/9/12.
 */
public class HomePresenter extends BasePresenter<HomeView> {
    public static final int PAGE_SIZE = 10;

    private int mCurPage = 1;

    private String mCategory;

    private boolean hasMoreData = true;

    private int year,mouth,day;

    public HomePresenter(String category){
        //mPageMap = new HashMap<>();
        if(!TextUtils.isEmpty(category)){
            mCategory = DataType.ALL.getName();
        }

        mCategory = category;

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        mouth = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
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
                            getMvpView().onGetDailyDataFail(Log.getStackTraceString(e));
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

    /**
     * 先获取到数据，然后过滤重复数据，再进行时间排序
     * @param itemList
     */
    public void refreshGankData(final List<MultiDataItem> itemList){
        if (itemList == null)
            return;

        mCompositeSubscription.add(mDataManager.getCategoryData(mCategory,PAGE_SIZE,1)
                .flatMap(new Func1<List<GankData>, Observable<GankData>>() {
                    @Override
                    public Observable<GankData> call(List<GankData> dataList) {
                        return Observable.from(dataList);
                    }
                })
                .filter(new Func1<GankData, Boolean>() {
                    @Override
                    public Boolean call(GankData data) {
                        for (int i = 0; i < itemList.size(); i++) {
                            if(itemList.get(i).getGankData().equals(data)){
                                return false;
                            }
                        }

                        return true;
                    }
                })
                .toSortedList(new Func2<GankData, GankData, Integer>() {
                    @Override
                    public Integer call(GankData data, GankData data2) {
                        return data2.getPublishedAt().compareTo(data.getPublishedAt());
                    }
                })
                .compose(RxUtils.<List<GankData>>applyIOToMainThreadSchedulers())
                .subscribe(new Subscriber<List<GankData>>() {
                    @Override
                    public void onCompleted() {
                        if (mCompositeSubscription != null)
                            mCompositeSubscription.remove(this);

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e != null) {
                            getMvpView().onGetDailyDataFail(Log.getStackTraceString(e));
                        }else{
                            getMvpView().onRefreshDataFail(Constants.UNKNOWN_ERROR);
                        }
                    }

                    @Override
                    public void onNext(List<GankData> dataList) {
                        getMvpView().onRefreshDataSuccess(dataList);
                    }
                }));
    }


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



    public void getGankDailyData(){
        if(DataType.WELFARE.getCategory().equals(mCategory)) {
            throw new IllegalArgumentException("非法的数据类型");
        }

        Observable<List<GankData>> welfareObservable = mDataManager.
                getCategoryData(DataType.WELFARE.getCategory(),1,mCurPage);

        Observable<GankDailyData>  dataObservable = mDataManager
                .getDailyData(year, mouth, day);

        mCompositeSubscription
                .add(dataObservable.zipWith(welfareObservable, new Func2<GankDailyData, List<GankData>, GankDailyData>() {
                            @Override
                            public GankDailyData call(GankDailyData dataList, List<GankData> welfareDataList) {
                                for (int i = 0; i < welfareDataList.size(); i++) {
                                    dataList.imageUrl = welfareDataList.get(i).getUrl();
                                }

                                return dataList;
                            }
                        })
                .compose(RxUtils.<GankDailyData>applyIOToMainThreadSchedulers())
                .subscribe(new Subscriber<GankDailyData>() {
                    @Override
                    public void onCompleted() {
                        mCurPage++;
                        if (mCompositeSubscription != null)
                            mCompositeSubscription.remove(this);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e != null) {
                            getMvpView().onGetDailyDataFail(Log.getStackTraceString(e));
                        }else{
                            getMvpView().onGetDataFail(Constants.UNKNOWN_ERROR);
                        }
                    }

                    @Override
                    public void onNext(GankDailyData dataList) {
                        getMvpView().onGetDailyDataSuccess(dataList);
                    }
                }));


    }

    /**
     * 过去多少天的日期集合
     */
    public class EasyDate implements Serializable {
        private Calendar calendar;

        public EasyDate(Calendar calendar) {
            this.calendar = calendar;
        }

        public int getYear() {
            return calendar.get(Calendar.YEAR);
        }

        public int getMonth() {
            return calendar.get(Calendar.MONTH) + 1;
        }

        public int getDay() {
            return calendar.get(Calendar.DAY_OF_MONTH);
        }

        public List<EasyDate> getPastTime(int pageSize,int pageNum) {
            List<EasyDate> easyDates = new ArrayList<>();
            for (int i = 0; i < pageSize; i++) {
                /*
                 * - (page * DateUtils.ONE_DAY) 翻到哪页再找 一页有DEFAULT_DAILY_SIZE这么长
                 * - i * DateUtils.ONE_DAY 往前一天一天 找呀找
                 */
                long time = this.calendar.getTimeInMillis() - ((pageNum - 1) * pageSize * DateUtils.ONE_DAY) - i * DateUtils.ONE_DAY;
                Calendar c = Calendar.getInstance();
                c.setTimeInMillis(time);
                EasyDate date = new EasyDate(c);
                easyDates.add(date);
            }
            return easyDates;
        }

    }

    public int getCurPage() {
        return mCurPage;
    }

    public boolean isHasMoreData() {
        return hasMoreData;
    }
}
