package com.wstrong.mygank.data;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.wstrong.mygank.data.local.PreferencesHelper;
import com.wstrong.mygank.data.model.GankData;
import com.wstrong.mygank.data.remote.ApiConnection;
import com.wstrong.mygank.data.remote.DataRestApiImpl;

import java.io.InputStream;
import java.util.List;

import rx.Observable;

/**
 * Created by pengl on 2016/9/11.
 */
public class DataManager {

    private static DataManager sInstance;

    private DataRestApiImpl mDataRestApi;

    private PreferencesHelper mPreferencesHelper;

    public static DataManager getInstance(){
        if(sInstance == null){
            sInstance = new DataManager();
        }

        return sInstance;
    }

    private DataManager(){
    }

    public void init(Context context){
        mDataRestApi = new DataRestApiImpl();
        mPreferencesHelper = new PreferencesHelper(context);

        //Glide图片网络访问设置为OkHttp
        Glide.get(context).register(GlideUrl.class, InputStream.class,
                new OkHttpUrlLoader.Factory(ApiConnection.getInstance().getOkHttpClient()));
    }


    public void setPreferencesHelper(Context context) {
        mPreferencesHelper = new PreferencesHelper(context.getApplicationContext());
    }

    public PreferencesHelper getPreferencesHelper() {
        return mPreferencesHelper;
    }


   /* public DataRestApiImpl getDataRestApi() {
        return mDataRestApi;
    }*/

    public Observable<List<GankData>> getCategoryData(String category, int pageSize, int page){
        return mDataRestApi.getCategoryData(category,pageSize,page);
    }

}
