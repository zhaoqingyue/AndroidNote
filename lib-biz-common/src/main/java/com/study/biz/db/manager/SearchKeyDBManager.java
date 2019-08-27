package com.study.biz.db.manager;

import com.study.biz.db.bean.SearchKey;
import com.study.biz.db.dao.SearchKeyDao;
import org.greenrobot.greendao.AbstractDao;
import java.util.List;

/**
 * Created by zhao.qingyue on 2019/7/15.
 * 搜索关键字——数据库管理器
 */
public class SearchKeyDBManager<T, V extends AbstractDao<T, Long>> extends DBManager<T,V>  {

    public SearchKeyDBManager(V dao) {
        super(dao);
    }

    /**
     * 根据关键字查询
     */
    public SearchKey querySearchKey(String keyWord) {
        return  (SearchKey) mDao.queryBuilder()
                .where(SearchKeyDao.Properties.KeyWod.eq(keyWord))
                .build()
                .unique();
    }

    /**
     * 升序查询
     */
    public List<SearchKey> queryByOrderAsc() {
        return ((SearchKeyDao)mDao).queryBuilder()
                .orderAsc(SearchKeyDao.Properties.HistoryId)
                .list();
    }
}
