package com.wstrong.mygank;

/**
 * Created by pengl on 2016/9/10.
 */
public class Constants {

    public static final int[] NAV_MENU_ITEMS = {R.id.nav_home,R.id.nav_site,R.id.nav_favorite};


    public static final int CONNECT_TIME_OUT = 10;
    public static final String BASE_URL = "http://gank.io/api/";
    public static final String BASE_URL_SITE = "http://gank.io/";
    public static final String BASE_API_URL = "http://gank.io/api/";
    public static final String BASE_SEARCH_URL = "http://gank.io/api/search/query/listview/category/";
    public static final String GITHUB_TRENDING = "https://github.com/trending?l=java";

    public static final String UNKNOWN_ERROR = "未知异常，请检查网络重试";
    public static final String UNKNOWN_GET_ERROR = "获取数据失败，请刷新重试";
    public static final String UNKNOWN_DOWNLOAD_ERROR = "下载失败，请重新下载";
}
