package com.wstrong.mygank.data;

import android.content.Context;

import com.wstrong.mygank.data.local.PreferencesHelper;
import com.wstrong.mygank.data.remote.BaseApi;
import com.wstrong.mygank.data.remote.DataService;

/**
 * Created by pengl on 2016/9/11.
 */
public class DataManager {

    private static DataManager sInstance;

    private DataService mDataService;

    private PreferencesHelper mPreferencesHelper;

    public static DataManager getInstance(Context context){
        if(sInstance == null){
            sInstance = new DataManager(context.getApplicationContext());
        }

        return sInstance;
    }

    private DataManager(Context context){
        mDataService = BaseApi.getInstance().getRetrofit().create(DataService.class);

        mPreferencesHelper = new PreferencesHelper(context);
    }


}
