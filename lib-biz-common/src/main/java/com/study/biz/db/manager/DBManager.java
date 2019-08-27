package com.study.biz.db.manager;

import org.greenrobot.greendao.AbstractDao;

import java.util.List;

/**
 * 增删改查公共方法封装，这个类不能修改
 * 根据特定条件删除，继承该类进行具体的拓展
 * Created by zhao.qingyue on 2018/12/12.
 */

public class DBManager<T, V extends AbstractDao<T, Long>> {
    protected AbstractDao mDao;

    public DBManager(V dao) {
        this.mDao = dao;
    }

    /**
     * 增：增加一条
     */
    public void insertEntity(T entity) {
        mDao.insert(entity);
    }

    /**
     * 增：增加一条，主键相同的替换更新
     */
    public void insertOrReplace(T entity) {
        mDao.insertOrReplace(entity);
    }

    /**
     * 增：增加一个集合，主键相同的替换更新
     */
    public void insertList(List<T> entities) {
        mDao.insertOrReplaceInTx(entities);
    }

    /**
     * 删：删除一条
     */
    public void deleteEntity(T entity) {
        mDao.delete(entity);
    }

    /**
     * 删：根据主键ID删除
     */
    public void deleteById(Long id) {
        mDao.deleteByKey(id);
    }

    /**
     * 删：删除一个集合
     */
    public void deleteList(List<T> entities) {
        mDao.deleteInTx(entities);
    }

    /**
     * 删：删除所有
     */
    public void deleteAll() {
        mDao.deleteAll();
    }

    /**
     * 改：修改一个集合
     */
    public void updateEntity(T entity) {
        mDao.update(entity);
    }

    /**
     * 改：修改一个集合
     */
    public void updateList(List<T> entities) {
        mDao.updateInTx(entities);
    }

    //查具体某条，根据ID查，查询所有
    public T queryById(Long id) {
        return (T) mDao.loadByRowId(id);
    }

    public List<T> queryAll() {
        return mDao.loadAll();
    }
}
