package com.wstrong.mygank.data.remote;

import com.wstrong.mygank.data.model.GankDataResp;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by pengl on 2016/9/11.
 */
public interface DataRestService {


    /**
     * category 后面可接受参数 all | Android | iOS | 休息视频 | 福利 | 拓展资源 | 前端 | 瞎推荐 | App
     *
     * @param category 数据类型
     * @param size 数据个数
     * @param page 第几页
     * @return Observable<GankData>
     */
    @GET("{category}/count/{size}/page/{page}") Observable<GankDataResp> getData(
            @Path("category") String category, @Path("size") int size, @Path("page") int page);
}
