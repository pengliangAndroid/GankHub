package com.wstrong.mygank.data.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by pengl on 2016/9/11.
 */
public class BaseApi {
    public static final String HOME_PAGE_NAME = "干货集中营";
    public static final String HOME_PAGE_URL = "http://gank.io/";

    public static final String BASE_URL = "http://gank.io/api/";

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

    private static final int CONNECT_TIME_OUT = 10;

    private static BaseApi sInstance;

    private OkHttpClient mOkHttpClient;

    private Retrofit mRetrofit;

    private Gson mGson;

    public static BaseApi getInstance() {
        if (sInstance == null){
            synchronized (BaseApi.class){
                if(sInstance == null){
                    return new BaseApi();
                }
            }
        }
        return sInstance;
    }


    public BaseApi(){
        mGson = new GsonBuilder().create();

        mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .build();

        //查看网络请求发送状况
        if (true) {
            mOkHttpClient.interceptors().add(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Response response = chain.proceed(chain.request());
                    Logger.d(chain.request().toString());
                    return response;
                }
            });
        }

        mRetrofit = new Retrofit.Builder().client(mOkHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(mGson))
                .baseUrl(BASE_URL)
                .build();

    }

    public Gson getGson() {
        return mGson;
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }
}
