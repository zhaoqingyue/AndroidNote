package com.study.biz.db.manager;

import com.study.biz.db.bean.PaperworkBean;
import com.study.biz.db.dao.PaperworkBeanDao;
import org.greenrobot.greendao.AbstractDao;
import java.util.List;

/**
 * Created by zhao.qingyue on 2019/8/30.
 * 银行卡-数据库
 */
public class PaperworkDBManager<T, V extends AbstractDao<T, Long>> extends DBManager<T,V> {

    public PaperworkDBManager(V dao) {
        super(dao);
    }

    /**
     * 根据account查询
     */
    public PaperworkBean queryPaperworkByAccount(String account) {
        return  (PaperworkBean) mDao.queryBuilder()
                .where(PaperworkBeanDao.Properties.Account.eq(account))
                .build()
                .unique();
    }

    /**
     * 升序查询
     */
    public List<PaperworkBean> queryByOrderAsc() {
        return ((PaperworkBeanDao)mDao).queryBuilder()
                .orderAsc(PaperworkBeanDao.Properties.PaperworkId)
                .list();
    }
}
