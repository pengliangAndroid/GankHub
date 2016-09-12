package com.wstrong.mygank.data.remote;

/**
 * Created by pengl on 2016/9/11.
 */
public class BaseRestApi {
    public static final String HOME_PAGE_NAME = "干货集中营";
    public static final String HOME_PAGE_URL = "http://gank.io/";

    public static final String DATA_TYPE_WELFARE = "福利";
    public static final String DATA_TYPE_ANDROID = "Android";
    public static final String DATA_TYPE_IOS = "iOS";
    public static final String DATA_TYPE_REST_VIDEO = "休息视频";
    public static final String DATA_TYPE_EXTEND_RESOURCES = "拓展资源";
    public static final String DATA_TYPE_JS = "前端";
    public static final String DATA_TYPE_APP = "App";
    public static final String DATA_TYPE_RECOMMEND = "瞎推荐";
    public static final String DATA_TYPE_ALL = "all";

    public static final int DEFAULT_DATA_SIZE = 10;
    public static final int DEFAULT_DAILY_SIZE = 15;

    public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";


    /**
     * 错误公共处理方法,上传请求错误log到服务器
     * @param response
     */
    public void onError(Object response) {
    }

}
