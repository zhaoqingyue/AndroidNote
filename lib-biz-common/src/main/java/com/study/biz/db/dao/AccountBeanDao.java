package com.study.biz.db.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.study.biz.db.bean.AccountBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "ACCOUNT_BEAN".
*/
public class AccountBeanDao extends AbstractDao<AccountBean, Long> {

    public static final String TABLENAME = "ACCOUNT_BEAN";

    /**
     * Properties of entity AccountBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property AccountId = new Property(0, Long.class, "accountId", true, "_id");
        public final static Property LogoId = new Property(1, int.class, "logoId", false, "logoId");
        public final static Property UserName = new Property(2, String.class, "userName", false, "userName");
        public final static Property NickName = new Property(3, String.class, "nickName", false, "nickName");
        public final static Property AccountType = new Property(4, String.class, "accountType", false, "accountType");
        public final static Property Account = new Property(5, String.class, "account", false, "account");
        public final static Property Password = new Property(6, String.class, "password", false, "password");
        public final static Property Identity = new Property(7, String.class, "identity", false, "identity");
        public final static Property Mobile = new Property(8, String.class, "mobile", false, "mobile");
        public final static Property Email = new Property(9, String.class, "email", false, "email");
        public final static Property Url = new Property(10, String.class, "url", false, "url");
        public final static Property ItemType = new Property(11, int.class, "itemType", false, "ITEM_TYPE");
    }


    public AccountBeanDao(DaoConfig config) {
        super(config);
    }
    
    public AccountBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"ACCOUNT_BEAN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: accountId
                "\"logoId\" INTEGER NOT NULL ," + // 1: logoId
                "\"userName\" TEXT," + // 2: userName
                "\"nickName\" TEXT," + // 3: nickName
                "\"accountType\" TEXT," + // 4: accountType
                "\"account\" TEXT," + // 5: account
                "\"password\" TEXT," + // 6: password
                "\"identity\" TEXT," + // 7: identity
                "\"mobile\" TEXT," + // 8: mobile
                "\"email\" TEXT," + // 9: email
                "\"url\" TEXT," + // 10: url
                "\"ITEM_TYPE\" INTEGER NOT NULL );"); // 11: itemType
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"ACCOUNT_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, AccountBean entity) {
        stmt.clearBindings();
 
        Long accountId = entity.getAccountId();
        if (accountId != null) {
            stmt.bindLong(1, accountId);
        }
        stmt.bindLong(2, entity.getLogoId());
 
        String userName = entity.getUserName();
        if (userName != null) {
            stmt.bindString(3, userName);
        }
 
        String nickName = entity.getNickName();
        if (nickName != null) {
            stmt.bindString(4, nickName);
        }
 
        String accountType = entity.getAccountType();
        if (accountType != null) {
            stmt.bindString(5, accountType);
        }
 
        String account = entity.getAccount();
        if (account != null) {
            stmt.bindString(6, account);
        }
 
        String password = entity.getPassword();
        if (password != null) {
            stmt.bindString(7, password);
        }
 
        String identity = entity.getIdentity();
        if (identity != null) {
            stmt.bindString(8, identity);
        }
 
        String mobile = entity.getMobile();
        if (mobile != null) {
            stmt.bindString(9, mobile);
        }
 
        String email = entity.getEmail();
        if (email != null) {
            stmt.bindString(10, email);
        }
 
        String url = entity.getUrl();
        if (url != null) {
            stmt.bindString(11, url);
        }
        stmt.bindLong(12, entity.getItemType());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, AccountBean entity) {
        stmt.clearBindings();
 
        Long accountId = entity.getAccountId();
        if (accountId != null) {
            stmt.bindLong(1, accountId);
        }
        stmt.bindLong(2, entity.getLogoId());
 
        String userName = entity.getUserName();
        if (userName != null) {
            stmt.bindString(3, userName);
        }
 
        String nickName = entity.getNickName();
        if (nickName != null) {
            stmt.bindString(4, nickName);
        }
 
        String accountType = entity.getAccountType();
        if (accountType != null) {
            stmt.bindString(5, accountType);
        }
 
        String account = entity.getAccount();
        if (account != null) {
            stmt.bindString(6, account);
        }
 
        String password = entity.getPassword();
        if (password != null) {
            stmt.bindString(7, password);
        }
 
        String identity = entity.getIdentity();
        if (identity != null) {
            stmt.bindString(8, identity);
        }
 
        String mobile = entity.getMobile();
        if (mobile != null) {
            stmt.bindString(9, mobile);
        }
 
        String email = entity.getEmail();
        if (email != null) {
            stmt.bindString(10, email);
        }
 
        String url = entity.getUrl();
        if (url != null) {
            stmt.bindString(11, url);
        }
        stmt.bindLong(12, entity.getItemType());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public AccountBean readEntity(Cursor cursor, int offset) {
        AccountBean entity = new AccountBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // accountId
            cursor.getInt(offset + 1), // logoId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // userName
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // nickName
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // accountType
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // account
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // password
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // identity
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // mobile
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // email
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // url
            cursor.getInt(offset + 11) // itemType
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, AccountBean entity, int offset) {
        entity.setAccountId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setLogoId(cursor.getInt(offset + 1));
        entity.setUserName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setNickName(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setAccountType(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setAccount(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setPassword(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setIdentity(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setMobile(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setEmail(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setUrl(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setItemType(cursor.getInt(offset + 11));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(AccountBean entity, long rowId) {
        entity.setAccountId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(AccountBean entity) {
        if(entity != null) {
            return entity.getAccountId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(AccountBean entity) {
        return entity.getAccountId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
