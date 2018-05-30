package com.yunfei.download;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.yunfei.download.BundleBean;
import com.yunfei.download.ItemBean;

import com.yunfei.download.BundleBeanDao;
import com.yunfei.download.ItemBeanDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig bundleBeanDaoConfig;
    private final DaoConfig itemBeanDaoConfig;

    private final BundleBeanDao bundleBeanDao;
    private final ItemBeanDao itemBeanDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        bundleBeanDaoConfig = daoConfigMap.get(BundleBeanDao.class).clone();
        bundleBeanDaoConfig.initIdentityScope(type);

        itemBeanDaoConfig = daoConfigMap.get(ItemBeanDao.class).clone();
        itemBeanDaoConfig.initIdentityScope(type);

        bundleBeanDao = new BundleBeanDao(bundleBeanDaoConfig, this);
        itemBeanDao = new ItemBeanDao(itemBeanDaoConfig, this);

        registerDao(BundleBean.class, bundleBeanDao);
        registerDao(ItemBean.class, itemBeanDao);
    }
    
    public void clear() {
        bundleBeanDaoConfig.clearIdentityScope();
        itemBeanDaoConfig.clearIdentityScope();
    }

    public BundleBeanDao getBundleBeanDao() {
        return bundleBeanDao;
    }

    public ItemBeanDao getItemBeanDao() {
        return itemBeanDao;
    }

}