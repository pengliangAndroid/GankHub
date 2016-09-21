package com.wstrong.mygank.data.local;

import android.content.Context;

import com.wstrong.mygank.data.dao.CollectionDao;
import com.wstrong.mygank.data.dao.DaoMaster;
import com.wstrong.mygank.data.dao.DaoSession;
import com.wstrong.mygank.data.dao.UpgradeHelper;

/**
 * Created by pengl on 2016/9/21.
 */
public class DataBaseHelper {
    private static final String DB_NAME = "data.db";
    private DaoMaster mDaoMaster;

    private UpgradeHelper mUpgradeHelper;

    private DaoSession mDaoSession;

    public DataBaseHelper(Context context){
        mUpgradeHelper = new UpgradeHelper(context,DB_NAME,null);

        mDaoMaster = new DaoMaster(mUpgradeHelper.getWritableDatabase());
        mDaoSession = mDaoMaster.newSession();
    }

    public DaoMaster getDaoMaster() {
        return mDaoMaster;
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public CollectionDao getCollectionDao(){
        return getDaoSession().getCollectionDao();
    }
}
