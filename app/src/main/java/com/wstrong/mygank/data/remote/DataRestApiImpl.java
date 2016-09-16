package com.wstrong.mygank.data.remote;

import com.wstrong.mygank.config.Config;
import com.wstrong.mygank.data.model.GankDailyDataResp;
import com.wstrong.mygank.data.model.GankData;
import com.wstrong.mygank.data.model.GankDataResp;
import com.wstrong.mygank.data.model.GankSearchDataResp;
import com.wstrong.mygank.utils.rx.RxBus;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by pengl on 2016/9/12.
 */
public class DataRestApiImpl extends BaseRestApi{

    DataRestService mDataRestService;

    public DataRestApiImpl(){
        mDataRestService = ApiConnection.getInstance().getRetrofit().create(DataRestService.class);
    }

    public Observable<List<GankData>> getCategoryData(String category, int pageSize, int page){
        return mDataRestService.getCategoryData(category,pageSize,page)
                .map(new Func1<GankDataResp, List<GankData>>() {
                    @Override
                    public List<GankData> call(GankDataResp resp) {
                        if (resp.isError()){
                            //rxbus error处理
                            RxBus.get().post(Config.SERVER_ERROR,"server error.");
                            return null;
                        }
                        return resp.getResults();
                    }
                });
    }

    public Observable<GankDailyDataResp> getDailyData(int year, int month, int day){
        return mDataRestService.getDailyData(year,month,day);
    }

    public Observable<GankSearchDataResp> searchData(String keyword,String category,int pageSize,int page){
        return mDataRestService.searchData(keyword,category,pageSize,page);
    }

}
