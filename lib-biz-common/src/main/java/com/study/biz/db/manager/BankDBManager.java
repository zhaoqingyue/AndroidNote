package com.study.biz.db.manager;

import com.study.biz.db.bean.BankBean;
import com.study.biz.db.dao.BankBeanDao;

import org.greenrobot.greendao.AbstractDao;

import java.util.List;

/**
 * Created by zhao.qingyue on 2019/8/30.
 * 银行卡-数据库
 */
public class BankDBManager<T, V extends AbstractDao<T, Long>> extends DBManager<T,V> {

    public BankDBManager(V dao) {
        super(dao);
    }

    /**
     * 根据account查询
     */
    public BankBean queryBankByAccount(String account) {
        return  (BankBean) mDao.queryBuilder()
                .where(BankBeanDao.Properties.Account.eq(account))
                .build()
                .unique();
    }

    /**
     * 升序查询
     */
    public List<BankBean> queryByOrderAsc() {
        return ((BankBeanDao)mDao).queryBuilder()
                .orderAsc(BankBeanDao.Properties.BankId)
                .list();
    }
}
