package com.study.biz.db.manager;

import com.study.biz.db.bean.UserInfo;
import com.study.biz.db.dao.DaoSession;
import com.study.biz.db.dao.UserInfoDao;

import java.util.List;

/**
 * Created by zhao.qingyue on 2019/1/31.
 * 用户管理类
 */
public class UserInfoManager {
    private static UserInfo mUserInfo;
    private static DBManager<UserInfo, UserInfoDao> dbManager;

    private UserInfoManager() {
        initDbManager();
    }

    private static void initDbManager() {
        if (dbManager == null) {
            DaoSession daoSession = DaoSessionGenerator.getInstance().getDaoSession();
            dbManager = new DBManager<>(daoSession.getUserInfoDao());
        }
    }


    /**
     * 获取用户信息实例
     */
    public static UserInfo getInstance() {
        if (mUserInfo == null) {
            synchronized (UserInfoManager.class) {
                if (mUserInfo == null) {
                    if (dbManager == null) {
                        initDbManager();
                    }
                    List<UserInfo> lists = dbManager.queryAll();
                    if(lists == null || lists.isEmpty())
                        return null;

                    mUserInfo = lists.get(0);
                }
                return mUserInfo;
            }
        }
        return mUserInfo;
    }

    /**
     * 获取用户信息数据库对象
     */
    public static DBManager<UserInfo, UserInfoDao> getUserInfoDbManager() {
        if (dbManager == null) {
            DaoSession daoSession = DaoSessionGenerator.getInstance().getDaoSession();
            dbManager = new DBManager<>(daoSession.getUserInfoDao());
        }
        return dbManager;
    }

    public static void exit() {
        mUserInfo = null;
    }
}
