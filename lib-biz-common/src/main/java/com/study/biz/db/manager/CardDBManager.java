package com.study.biz.db.manager;

import com.study.biz.db.bean.CardBean;
import com.study.biz.db.dao.CardBeanDao;

import org.greenrobot.greendao.AbstractDao;
import java.util.List;

/**
 * Created by zhao.qingyue on 2019/8/30.
 * 卡-数据库
 */
public class CardDBManager<T, V extends AbstractDao<T, Long>> extends DBManager<T,V> {

    public CardDBManager(V dao) {
        super(dao);
    }

    /**
     * 根据account查询
     */
    public CardBean queryCardByCertificateNumber(String account) {
        return  (CardBean) mDao.queryBuilder()
                .where(CardBeanDao.Properties.CertificateNumber.eq(account))
                .build()
                .unique();
    }

    /**
     * 升序查询
     */
    public List<CardBean> queryByOrderAsc() {
        return ((CardBeanDao)mDao).queryBuilder()
                .orderAsc(CardBeanDao.Properties.CardId)
                .list();
    }
}
