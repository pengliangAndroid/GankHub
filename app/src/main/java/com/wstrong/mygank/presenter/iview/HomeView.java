package com.wstrong.mygank.presenter.iview;

import com.wstrong.mygank.base.mvp.MvpView;
import com.wstrong.mygank.data.model.GankDailyData;
import com.wstrong.mygank.data.model.GankData;

import java.util.List;

/**
 * Created by pengl on 2016/9/12.
 */
public interface HomeView extends MvpView {

    void onLoadNoMoreData();

    void onGetDataSuccess(List<GankData> dataList);

    void onGetDataFail(String errorMsg);

    void onGetDailyDataSuccess(GankDailyData dataList);

    void onGetDailyDataFail(String errorMsg);

    void onRefreshDataSuccess(List<GankData> dataList);

    void onRefreshDataFail(String errorMsg);


}
