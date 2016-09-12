package com.wstrong.mygank;

import android.app.Application;

import com.wstrong.mygank.data.DataManager;

/**
 * Created by pengl on 2016/9/11.
 */
public class KApplication extends Application {
    public static KApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

        //初始化第三方框架
        initThirdSDK();

        //初始化数据源管理
        DataManager.getInstance().init(this);
    }

    private void initThirdSDK() {

    }


    public static KApplication getsInstance() {
        return sInstance;
    }
}
