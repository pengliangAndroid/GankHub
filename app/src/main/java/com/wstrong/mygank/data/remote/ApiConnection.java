package com.wstrong.mygank.data.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wstrong.mygank.Constants;
import com.wstrong.mygank.utils.LogUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by pengl on 2016/9/12.
 */
public class ApiConnection {

    private OkHttpClient mOkHttpClient;

    private Retrofit mRetrofit;

    private Gson mGson;

    private static ApiConnection sInstance = null;

    private ApiConnection(){
        init();
    }

    public static ApiConnection getInstance() {
        if (sInstance == null){
            sInstance = new ApiConnection();
        }
        return sInstance;
    }

    private void init(){
        mGson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();

        //查看网络请求发送状况
        mOkHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Response response = chain.proceed(chain.request());
                        LogUtil.d(chain.request().toString());
                        return response;
                    }
                })
                .connectTimeout(Constants.CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .build();



        mRetrofit = new Retrofit.Builder().client(mOkHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(mGson))
                .baseUrl(Constants.BASE_API_URL)
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
