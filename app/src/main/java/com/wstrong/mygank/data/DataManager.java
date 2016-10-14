package com.wstrong.mygank.data;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wstrong.mygank.config.DataType;
import com.wstrong.mygank.data.dao.CollectionDao;
import com.wstrong.mygank.data.local.DataBaseHelper;
import com.wstrong.mygank.data.local.PreferencesHelper;
import com.wstrong.mygank.data.model.Collection;
import com.wstrong.mygank.data.model.GankDailyData;
import com.wstrong.mygank.data.model.GankData;
import com.wstrong.mygank.data.remote.ApiConnection;
import com.wstrong.mygank.data.remote.DataRestApiImpl;
import com.wstrong.mygank.utils.LogUtil;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by pengl on 2016/9/11.
 */
public class DataManager {

    private static DataManager sInstance;

    private DataRestApiImpl mDataRestApi;

    private PreferencesHelper mPreferencesHelper;

    private DataBaseHelper mDataBaseHelper;

    private static final String KEY_GANK_DATA = "GANK_DATA";

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
        mDataBaseHelper = new DataBaseHelper(context);

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


   /*public DataRestApiImpl getDataRestApi() {
        return mDataRestApi;
    }*/

    public Observable<List<GankData>> getCategoryData(String category, int pageSize, int page){
        return  mDataRestApi
                .getCategoryData(category,pageSize,page);
        /*Observable<List<GankData>> remoteDataList = getAndSaveDataList(category,pageSize,page);

        if(pageSize != 1){
            return remoteDataList;
        }

        Observable<List<GankData>> localDataList = getLocalDataList();
        LogUtil.d("localDataList:" + localDataList);
        if(localDataList == null){
            return remoteDataList;
        }

        return Observable.concat(localDataList,remoteDataList)
                .filter(new Func1<List<GankData>, Boolean>() {
                    @Override
                    public Boolean call(List<GankData> dataList) {
                        return !dataList.isEmpty();
                    }
                })
                .first();*/

    }

    private Observable<List<GankData>> getAndSaveDataList(final String category, int pageSize, final int page) {
        return  mDataRestApi
                .getCategoryData(category,pageSize,page)
                .doOnNext(new Action1<List<GankData>>() {
                    @Override
                    public void call(List<GankData> dataList) {
                        if(category.equals(DataType.ALL.getCategory()) && (page == 1)) {
                            saveLocalDataList(dataList);
                            LogUtil.d("call saveLocalDataList...");
                        }
                    }
                });
    }


    public Observable<GankDailyData> getDailyData(int year,int month,int day){
        return mDataRestApi.getDailyData(year,month,day);
    }

    public Observable<Collection> saveCollection(Collection collection){
        return mDataBaseHelper.getCollectionDao().rx().save(collection);
    }


    public Observable<Void> deleteCollection(Long id){
        return mDataBaseHelper.getCollectionDao().rx().deleteByKey(id);
    }

    public Observable<Collection> getLocalCollection(String url){
        return mDataBaseHelper.getCollectionDao().queryBuilder()
                .where(CollectionDao.Properties.Url.eq(url)).rx().unique();
    }

    public Observable<List<Collection>> getAllCollection(){
        return mDataBaseHelper.getCollectionDao().queryBuilder().rx().list();
    }


    public Observable<List<Collection>> searchLocalCollection(String keyword){
        return mDataBaseHelper.getCollectionDao().queryBuilder()
                .whereOr(CollectionDao.Properties.Title.like("%"+keyword),
                        CollectionDao.Properties.Url.like("%"+keyword))
                .rx().list();
    }

    public void saveLocalDataList(List<GankData> dataList){
        if(dataList != null) {
            String jsonString = getGson().toJson(dataList, ArrayList.class);
            LogUtil.d("saveLocalDataList:"+jsonString);
            mPreferencesHelper.put(KEY_GANK_DATA, jsonString);
        }
    }

    public Observable<List<GankData>> getLocalDataList(){
        List<GankData> dataList = getLocalGankDataList();

        return Observable.just(dataList)
                .flatMap(new Func1<List<GankData>, Observable<List<GankData>>>() {
                    @Override
                    public Observable<List<GankData>> call(List<GankData> dataList) {
                        return Observable.from(dataList).toList();
                    }
                });

    }

    public List<GankData> getLocalGankDataList(){
        String dataJson = (String) mPreferencesHelper.get(KEY_GANK_DATA,"");
        if(dataJson.equals(""))
            return Collections.EMPTY_LIST;

        Type type = new TypeToken<List<GankData>>(){}.getType();
        return getGson().fromJson(dataJson,type);
    }


    public Gson getGson(){
        return ApiConnection.getInstance().getGson();
    }
}
