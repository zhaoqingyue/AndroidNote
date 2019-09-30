package com.study.biz.db.manager;

import com.study.biz.db.bean.AddressBean;
import com.study.biz.db.bean.CardBean;
import com.study.biz.db.dao.AddressBeanDao;
import com.study.biz.db.dao.CardBeanDao;

import org.greenrobot.greendao.AbstractDao;

import java.util.List;

/**
 * Created by zhao.qingyue on 2019/9/29.
 * 地址-数据库
 */
public class AddressDBManager<T, V extends AbstractDao<T, Long>> extends DBManager<T,V> {

    public AddressDBManager(V dao) {
        super(dao);
    }

    /**
     * 根据phone查询
     */
    public AddressBean queryPhone(String phone) {
        return  (AddressBean) mDao.queryBuilder()
                .where(AddressBeanDao.Properties.Phone.eq(phone))
                .build()
                .unique();
    }

    /**
     * 升序查询
     */
    public List<AddressBean> queryByOrderAsc() {
        return ((AddressBeanDao)mDao).queryBuilder()
                .orderAsc(AddressBeanDao.Properties.AddressId)
                .list();
    }
}
