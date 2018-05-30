package com.yunfei.download;

import java.util.List;

 class DbUtil {
    public static BundleBean getBundleByKey(BundleBeanDao dao,String key){

        return dao.queryBuilder().where(BundleBeanDao.Properties.Key.eq(key))
                .build().uniqueOrThrow();

    }
    public static List<ItemBean> getFinishItemBean(ItemBeanDao dao, long bundleId){
        return dao.queryBuilder().where(ItemBeanDao.Properties.BundleId.eq(bundleId))
                .where(ItemBeanDao.Properties.Status.eq(Status.FINISH))
                .build().list();
    }
}
