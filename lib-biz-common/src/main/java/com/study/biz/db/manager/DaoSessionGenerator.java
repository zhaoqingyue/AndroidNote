package com.study.biz.db.manager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.study.biz.db.dao.DaoMaster;
import com.study.biz.db.dao.DaoSession;
import com.study.biz.db.help.NegOpenHelper;
import com.study.commonlib.base.app.BaseApplication;


/**
 * Created by chen.tuxi on 2018/12/12.
 * 数据库管理
 */
public class DaoSessionGenerator {

    private static final String DB_NAME = "AndroidNote.db";
    private static final boolean UPDATE_DB = false;
    private static DaoSessionGenerator mManager;
    private DaoMaster.DevOpenHelper mDevOpenHelper;
    private DaoSession mDaoSession;
    private DaoMaster mDaoMaster;
    private Context mContext;

    private DaoSessionGenerator() {
        mContext = BaseApplication.getInstance();
        // 初始化数据库信息
        mDevOpenHelper = new DaoMaster.DevOpenHelper(mContext, DB_NAME, null);
//        SQLiteDatabase db = helper.getWritableDatabase();
//        DaoMaster daoMaster = new DaoMaster(db);
//        mDaoSession = daoMaster.newSession();
    }

    public static DaoSessionGenerator getInstance() {
        if (mManager == null) {
            synchronized (DaoSessionGenerator.class) {
                if (mManager == null) {
                    mManager = new DaoSessionGenerator();
                }
            }
        }
        return mManager;
    }

//    public DaoSession getDaoSession() {
//        return mDaoSession;
//    }

    /**
     * 获取可写数据库
     */
    public SQLiteDatabase getWritableDatabase() {
        if (mContext == null) {
            mContext = BaseApplication.getInstance();
        }

        if (mDevOpenHelper == null) {
            getInstance();
        }

        return mDevOpenHelper.getWritableDatabase();
    }

    /**
     * 获取DaoMaster
     *
     * 判断是否存在数据库，如果没有则创建数据库
     */
    public DaoMaster getDaoMaster() {
        if (mContext == null) {
            mContext = BaseApplication.getInstance();
        }

        if (mDaoMaster == null) {
            synchronized (DaoSessionGenerator.class) {
                if (mDaoMaster == null) {
                    if (UPDATE_DB) {
                        // 数据库版本升级时用
                        NegOpenHelper helper = new NegOpenHelper(mContext, DB_NAME, null);
                        mDaoMaster = new DaoMaster(helper.getWritableDatabase());
                    } else {
                        mDaoMaster = new DaoMaster(getWritableDatabase());
                    }
                }
            }
        }
        return mDaoMaster;
    }

    /**
     * 获取DaoSession
     */
    public DaoSession getDaoSession() {
        if (mContext == null) {
            mContext = BaseApplication.getInstance();
        }

        if (mDaoSession == null) {
            synchronized (DaoSessionGenerator.class) {
                mDaoSession = getDaoMaster().newSession();
            }
        }
        return mDaoSession;
    }
}
