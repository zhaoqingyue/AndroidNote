package com.study.biz.db.dao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.study.biz.db.bean.SearchKey;
import com.study.biz.db.bean.UserInfo;

import com.study.biz.db.dao.SearchKeyDao;
import com.study.biz.db.dao.UserInfoDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig searchKeyDaoConfig;
    private final DaoConfig userInfoDaoConfig;

    private final SearchKeyDao searchKeyDao;
    private final UserInfoDao userInfoDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        searchKeyDaoConfig = daoConfigMap.get(SearchKeyDao.class).clone();
        searchKeyDaoConfig.initIdentityScope(type);

        userInfoDaoConfig = daoConfigMap.get(UserInfoDao.class).clone();
        userInfoDaoConfig.initIdentityScope(type);

        searchKeyDao = new SearchKeyDao(searchKeyDaoConfig, this);
        userInfoDao = new UserInfoDao(userInfoDaoConfig, this);

        registerDao(SearchKey.class, searchKeyDao);
        registerDao(UserInfo.class, userInfoDao);
    }
    
    public void clear() {
        searchKeyDaoConfig.clearIdentityScope();
        userInfoDaoConfig.clearIdentityScope();
    }

    public SearchKeyDao getSearchKeyDao() {
        return searchKeyDao;
    }

    public UserInfoDao getUserInfoDao() {
        return userInfoDao;
    }

}