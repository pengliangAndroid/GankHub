package com.wstrong.mygank.data.remote;

import com.wstrong.mygank.data.model.DailyData;
import com.wstrong.mygank.data.model.GankData;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by pengl on 2016/9/11.
 */
public interface DataService {

    /**
     * @param year year
     * @param month month
     * @param day day
     * @return Observable<DailyData>
     */
    @GET("day/{year}/{month}/{day}")
    Observable<DailyData> getDaily(
            @Path("year") int year, @Path("month") int month, @Path("day") int day);

    /**
     * 找妹子、Android、iOS、前端、扩展资源、休息视频
     *
     * @param type 数据类型
     * @param size 数据个数
     * @param page 第几页
     * @return Observable<GankData>
     */
    @GET("data/{type}/{size}/{page}") Observable<GankData> getData(
            @Path("type") String type, @Path("size") int size, @Path("page") int page);
}
