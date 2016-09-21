package com.wstrong.mygank.data.remote;

import com.wstrong.mygank.data.model.GankDailyData;
import com.wstrong.mygank.data.model.GankDataResp;
import com.wstrong.mygank.data.model.GankSearchDataResp;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by pengl on 2016/9/11.
 */
public interface DataRestService {

    /**
     * 获取分类数据
     * category 后面可接受参数 all | Android | iOS | 休息视频 | 福利 | 拓展资源 | 前端 | 瞎推荐 | App
     *
     * @param category 数据类型
     * @param size 数据个数
     * @param page 第几页
     * @return Observable<GankSearchDataResp>
     */
    @GET("data/{category}/{size}/{page}")
    Observable<GankDataResp> getCategoryData(@Path("category") String category,
                                             @Path("size") int size, @Path("page") int page);

    /**
     * 获取每日数据
     * @param year
     * @param month
     * @param day
     * @return
     */
    @GET("day/{year}/{month}/{day}")
    Observable<GankDailyData> getDailyData(@Path("year") int year,
                                           @Path("month") int month, @Path("day") int day);

    /**
     * 搜索分类数据
     * category 后面可接受参数 all | Android | iOS | 休息视频 | 福利 | 拓展资源 | 前端 | 瞎推荐 | App
     *
     * @param category 数据类型
     * @param size 数据个数
     * @param page 第几页
     * @return Observable<GankSearchDataResp>
     */
    @GET("search/query/{keyword}/category/{category}/count/{size}/page/{page}")
    Observable<GankSearchDataResp> searchData(@Path("keyword")String keyword, @Path("category") String category,
                                              @Path("size") int size, @Path("page") int page);

    /**
     * 获取历史数据
     * @param size 数据个数
     * @param page 第几页
     * @return Observable<GankSearchDataResp>
     */
    @GET("history/content/{size}/{page}")
    Observable<GankDataResp> getHistroyData(@Path("size") int size, @Path("page") int page);


    /**
     * 获取特定日期历史数据
     * @param year
     * @param month
     * @param day
     * @return Observable<GankSearchDataResp>
     */
    @GET("history/content/day/{year}/{month}/{day}")
    Observable<GankDataResp> getHistroyDataByDate(@Path("year") int year,
                                                        @Path("month") int month, @Path("day") int day);


}
