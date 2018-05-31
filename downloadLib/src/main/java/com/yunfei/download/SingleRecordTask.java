package com.yunfei.download;

import java.util.List;

public abstract class SingleRecordTask extends BaseTask {
    private final BundleBean bundleBean;
    private final BundleBeanDao bundleBeanDao;
    private final ItemBeanDao itemBeanDao;
    private BaseTask task;
    private ItemBean itemBean;

    public SingleRecordTask(BundleBean bundleBean, BundleBeanDao bundleBeanDao, ItemBeanDao itemBeanDao) {
        super(bundleBean.getKey());
        this.bundleBean = bundleBean;
        this.bundleBeanDao = bundleBeanDao;
        this.itemBeanDao = itemBeanDao;
    }

    @Override
    protected DownEvent createEvent(String key) {
        //Dao 数据库获取 真实状态
        return new DownEvent(key);
    }


    @Override
    public void run() {
        final List<ItemBean> itemBeans = bundleBean.getItemBeans();
        if (itemBeans.isEmpty()) {
            itemBean = new ItemBean();
            itemBean.setBundleId(bundleBean.getId());
            itemBean.setPath(bundleBean.getPath());
            itemBean.setStatus(Status.WATTING);
            itemBeanDao.insert(itemBean);
        } else {
            itemBean = itemBeans.get(0);
        }
        if (isPause()){
            return;
        }
        task = getTask();
        //
        task.addObserver(new OnEventListener() {
            @Override
            protected void onEvent(DownEvent event) {
                dbUpdate(event);
                switch (event.getStatus()) {
                    case DOWLOADING:
                        onUpdate(event.getTotalSize(),event.getCompletedSize(),event.getSpeed());
                        break;
                    case WATTING:
                        break;
                    case FINISH:
                        onFinish(event.getTotalSize(),event.getCompletedSize());
                        break;
                    case PAUSE:
                        onPause();
                        break;
                    case ERROR:
                        onError(event.getError());
                        break;
                    default:

                        break;
                }
            }
        });
        task.run();

    }

    protected abstract BaseTask getTask();


    @Override
    public void pause() {
        super.pause();
        onPause();
        if (task != null) {
            task.pause();
        }
    }

    //异步操作 增加效率
    private void dbUpdate(DownEvent event) {
        itemBean.setTotalSize(event.getTotalSize());
        itemBean.setCompletedSize(event.getCompletedSize());
        itemBean.setStatus(event.getStatus());
        bundleBean.setTotalSize(event.getTotalSize());
        bundleBean.setCompletedSize(event.getCompletedSize());
        bundleBean.setStatus(event.getStatus());
        itemBeanDao.update(itemBean);
        bundleBeanDao.update(bundleBean);
    }


}
