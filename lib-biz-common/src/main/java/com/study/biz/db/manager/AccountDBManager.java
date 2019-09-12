package com.study.biz.db.manager;

import com.study.biz.db.bean.AccountBean;
import com.study.biz.db.dao.AccountBeanDao;

import org.greenrobot.greendao.AbstractDao;

import java.util.List;

/**
 * Created by zhao.qingyue on 2019/9/4.
 * 账户-数据库
 */
public class AccountDBManager<T, V extends AbstractDao<T, Long>> extends DBManager<T,V> {

    public AccountDBManager(V dao) {
        super(dao);
    }

    /**
     * 根据account查询
     */
    public AccountBean queryAccountByAccount(String account) {
        return  (AccountBean) mDao.queryBuilder()
                .where(AccountBeanDao.Properties.Account.eq(account))
                .build()
                .unique();
    }

    /**
     * 升序查询
     */
    public List<AccountBean> queryByOrderAsc() {
        return ((AccountBeanDao)mDao).queryBuilder()
                .orderAsc(AccountBeanDao.Properties.AccountId)
                .list();
    }
}
