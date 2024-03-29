package com.study.biz.db.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.study.biz.db.bean.BankBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "BANK_BEAN".
*/
public class BankBeanDao extends AbstractDao<BankBean, Long> {

    public static final String TABLENAME = "BANK_BEAN";

    /**
     * Properties of entity BankBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property BankId = new Property(0, Long.class, "bankId", true, "_id");
        public final static Property LogoId = new Property(1, int.class, "logoId", false, "logoId");
        public final static Property BankTypeName = new Property(2, String.class, "bankTypeName", false, "bankTypeName");
        public final static Property BankType = new Property(3, int.class, "bankType", false, "bankType");
        public final static Property UserName = new Property(4, String.class, "userName", false, "userName");
        public final static Property Identity = new Property(5, String.class, "identity", false, "identity");
        public final static Property Mobile = new Property(6, String.class, "mobile", false, "mobile");
        public final static Property Account = new Property(7, String.class, "account", false, "account");
        public final static Property Password = new Property(8, String.class, "password", false, "password");
        public final static Property ItemType = new Property(9, int.class, "itemType", false, "ITEM_TYPE");
    }


    public BankBeanDao(DaoConfig config) {
        super(config);
    }
    
    public BankBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"BANK_BEAN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: bankId
                "\"logoId\" INTEGER NOT NULL ," + // 1: logoId
                "\"bankTypeName\" TEXT," + // 2: bankTypeName
                "\"bankType\" INTEGER NOT NULL ," + // 3: bankType
                "\"userName\" TEXT," + // 4: userName
                "\"identity\" TEXT," + // 5: identity
                "\"mobile\" TEXT," + // 6: mobile
                "\"account\" TEXT," + // 7: account
                "\"password\" TEXT," + // 8: password
                "\"ITEM_TYPE\" INTEGER NOT NULL );"); // 9: itemType
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"BANK_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, BankBean entity) {
        stmt.clearBindings();
 
        Long bankId = entity.getBankId();
        if (bankId != null) {
            stmt.bindLong(1, bankId);
        }
        stmt.bindLong(2, entity.getLogoId());
 
        String bankTypeName = entity.getBankTypeName();
        if (bankTypeName != null) {
            stmt.bindString(3, bankTypeName);
        }
        stmt.bindLong(4, entity.getBankType());
 
        String userName = entity.getUserName();
        if (userName != null) {
            stmt.bindString(5, userName);
        }
 
        String identity = entity.getIdentity();
        if (identity != null) {
            stmt.bindString(6, identity);
        }
 
        String mobile = entity.getMobile();
        if (mobile != null) {
            stmt.bindString(7, mobile);
        }
 
        String account = entity.getAccount();
        if (account != null) {
            stmt.bindString(8, account);
        }
 
        String password = entity.getPassword();
        if (password != null) {
            stmt.bindString(9, password);
        }
        stmt.bindLong(10, entity.getItemType());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, BankBean entity) {
        stmt.clearBindings();
 
        Long bankId = entity.getBankId();
        if (bankId != null) {
            stmt.bindLong(1, bankId);
        }
        stmt.bindLong(2, entity.getLogoId());
 
        String bankTypeName = entity.getBankTypeName();
        if (bankTypeName != null) {
            stmt.bindString(3, bankTypeName);
        }
        stmt.bindLong(4, entity.getBankType());
 
        String userName = entity.getUserName();
        if (userName != null) {
            stmt.bindString(5, userName);
        }
 
        String identity = entity.getIdentity();
        if (identity != null) {
            stmt.bindString(6, identity);
        }
 
        String mobile = entity.getMobile();
        if (mobile != null) {
            stmt.bindString(7, mobile);
        }
 
        String account = entity.getAccount();
        if (account != null) {
            stmt.bindString(8, account);
        }
 
        String password = entity.getPassword();
        if (password != null) {
            stmt.bindString(9, password);
        }
        stmt.bindLong(10, entity.getItemType());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public BankBean readEntity(Cursor cursor, int offset) {
        BankBean entity = new BankBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // bankId
            cursor.getInt(offset + 1), // logoId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // bankTypeName
            cursor.getInt(offset + 3), // bankType
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // userName
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // identity
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // mobile
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // account
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // password
            cursor.getInt(offset + 9) // itemType
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, BankBean entity, int offset) {
        entity.setBankId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setLogoId(cursor.getInt(offset + 1));
        entity.setBankTypeName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setBankType(cursor.getInt(offset + 3));
        entity.setUserName(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setIdentity(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setMobile(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setAccount(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setPassword(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setItemType(cursor.getInt(offset + 9));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(BankBean entity, long rowId) {
        entity.setBankId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(BankBean entity) {
        if(entity != null) {
            return entity.getBankId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(BankBean entity) {
        return entity.getBankId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
