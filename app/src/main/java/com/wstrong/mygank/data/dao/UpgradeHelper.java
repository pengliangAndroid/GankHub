package com.wstrong.mygank.data.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.greenrobot.greendao.database.Database;

/**
 * Created by pengl on 2016/9/21.
 */
public class UpgradeHelper extends DaoMaster.OpenHelper {

    public UpgradeHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    /**
     * Here is where the calls to upgrade are executed
     */
    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        Log.i("greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by migrating all tables data");

        MigrationHelper.getInstance().migrate(db,
                CollectionDao.class);
    }
}
