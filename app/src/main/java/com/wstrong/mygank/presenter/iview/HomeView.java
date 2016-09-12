package com.wstrong.mygank.presenter.iview;

import com.wstrong.mygank.base.mvp.MvpView;
import com.wstrong.mygank.data.model.GankData;
import com.wstrong.mygank.data.model.GankDataWrapper;

import java.util.List;

/**
 * Created by pengl on 2016/9/12.
 */
public interface HomeView extends MvpView {

    void onGetDataWrapperSuccess(List<GankDataWrapper> dataList);

    void onGetDataWrapperFail(String errorMsg);

    void onLoadNoMoreData();

    void onGetDataSuccess(List<GankData> dataList);

    void onGetDataFail(String errorMsg);
}
