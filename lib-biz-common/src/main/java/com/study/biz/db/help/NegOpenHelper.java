package com.study.biz.db.help;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.study.biz.db.dao.DaoMaster;
import com.study.biz.db.dao.UserInfoDao;

import org.greenrobot.greendao.database.Database;

/**
 * Created by zhao.qingyue on 2019/4/1.
 * 数据库升级自定义helper类
 *
 * 操作方式：
 * 1. 更改实体类（增加、删减字段）
 * 2. 在onUpgrade方法里调用MigrationHelper.getInstance().migrate(db, XXDao.class);
 * 3. 改变build.gradle里面schemaVersion（最好递增）
 */
public class NegOpenHelper extends DaoMaster.OpenHelper {

    public NegOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    /**
     * 数据库升级
     */
    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        // 操作数据库的更新，有几个表升级都可以传入到下面
        if (oldVersion < newVersion) {
            // 更改过的实体类(新增的不用加)，更新UserDao文件（可以添加多个XXDao.class 文件）
            MigrationHelper.getInstance().migrate(db, UserInfoDao.class);



        }
    }
}
