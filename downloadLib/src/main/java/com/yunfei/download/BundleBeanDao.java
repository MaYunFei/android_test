package com.yunfei.download;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.yunfei.download.Status.StatusConverter;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "BUNDLE_BEAN".
*/
public class BundleBeanDao extends AbstractDao<BundleBean, Long> {

    public static final String TABLENAME = "BUNDLE_BEAN";

    /**
     * Properties of entity BundleBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Key = new Property(1, String.class, "key", false, "KEY");
        public final static Property TotalSize = new Property(2, long.class, "totalSize", false, "TOTAL_SIZE");
        public final static Property CompletedSize = new Property(3, long.class, "completedSize", false, "COMPLETED_SIZE");
        public final static Property Path = new Property(4, String.class, "path", false, "PATH");
        public final static Property Status = new Property(5, Integer.class, "status", false, "STATUS");
        public final static Property Type = new Property(6, int.class, "type", false, "TYPE");
        public final static Property Url = new Property(7, String.class, "url", false, "URL");
        public final static Property Data = new Property(8, String.class, "data", false, "DATA");
    }

    private DaoSession daoSession;

    private final StatusConverter statusConverter = new StatusConverter();

    public BundleBeanDao(DaoConfig config) {
        super(config);
    }
    
    public BundleBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"BUNDLE_BEAN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"KEY\" TEXT NOT NULL ," + // 1: key
                "\"TOTAL_SIZE\" INTEGER NOT NULL ," + // 2: totalSize
                "\"COMPLETED_SIZE\" INTEGER NOT NULL ," + // 3: completedSize
                "\"PATH\" TEXT," + // 4: path
                "\"STATUS\" INTEGER," + // 5: status
                "\"TYPE\" INTEGER NOT NULL ," + // 6: type
                "\"URL\" TEXT," + // 7: url
                "\"DATA\" TEXT);"); // 8: data
        // Add Indexes
        db.execSQL("CREATE UNIQUE INDEX " + constraint + "IDX_BUNDLE_BEAN_KEY ON \"BUNDLE_BEAN\"" +
                " (\"KEY\" ASC);");
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"BUNDLE_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, BundleBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getKey());
        stmt.bindLong(3, entity.getTotalSize());
        stmt.bindLong(4, entity.getCompletedSize());
 
        String path = entity.getPath();
        if (path != null) {
            stmt.bindString(5, path);
        }
 
        Status status = entity.getStatus();
        if (status != null) {
            stmt.bindLong(6, statusConverter.convertToDatabaseValue(status));
        }
        stmt.bindLong(7, entity.getType());
 
        String url = entity.getUrl();
        if (url != null) {
            stmt.bindString(8, url);
        }
 
        String data = entity.getData();
        if (data != null) {
            stmt.bindString(9, data);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, BundleBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getKey());
        stmt.bindLong(3, entity.getTotalSize());
        stmt.bindLong(4, entity.getCompletedSize());
 
        String path = entity.getPath();
        if (path != null) {
            stmt.bindString(5, path);
        }
 
        Status status = entity.getStatus();
        if (status != null) {
            stmt.bindLong(6, statusConverter.convertToDatabaseValue(status));
        }
        stmt.bindLong(7, entity.getType());
 
        String url = entity.getUrl();
        if (url != null) {
            stmt.bindString(8, url);
        }
 
        String data = entity.getData();
        if (data != null) {
            stmt.bindString(9, data);
        }
    }

    @Override
    protected final void attachEntity(BundleBean entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public BundleBean readEntity(Cursor cursor, int offset) {
        BundleBean entity = new BundleBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // key
            cursor.getLong(offset + 2), // totalSize
            cursor.getLong(offset + 3), // completedSize
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // path
            cursor.isNull(offset + 5) ? null : statusConverter.convertToEntityProperty(cursor.getInt(offset + 5)), // status
            cursor.getInt(offset + 6), // type
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // url
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8) // data
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, BundleBean entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setKey(cursor.getString(offset + 1));
        entity.setTotalSize(cursor.getLong(offset + 2));
        entity.setCompletedSize(cursor.getLong(offset + 3));
        entity.setPath(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setStatus(cursor.isNull(offset + 5) ? null : statusConverter.convertToEntityProperty(cursor.getInt(offset + 5)));
        entity.setType(cursor.getInt(offset + 6));
        entity.setUrl(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setData(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(BundleBean entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(BundleBean entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(BundleBean entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
