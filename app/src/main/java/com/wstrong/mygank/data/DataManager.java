package com.wstrong.mygank.data;

import android.content.Context;

import com.wstrong.mygank.data.local.PreferencesHelper;
import com.wstrong.mygank.data.remote.DataRestApiImpl;

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
    }


    public void setPreferencesHelper(Context context) {
        mPreferencesHelper = new PreferencesHelper(context.getApplicationContext());
    }

    public PreferencesHelper getPreferencesHelper() {
        return mPreferencesHelper;
    }


    public DataRestApiImpl getDataRestApi() {
        return mDataRestApi;
    }
}
