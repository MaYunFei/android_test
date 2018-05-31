package com.yunfei.download;

public class DownloadManager extends BaseDownloadManager {

    private DaoSession daoSession;

    public DownloadManager(DaoSession daoSession) {
        this.daoSession = daoSession;
    }


    @Override
    protected BaseTask getTask(final BundleBean bundleBean) {


        BundleBean bundleByKey = DbUtil.getBundleByKey(daoSession.getBundleBeanDao(), bundleBean.getKey());
        if (bundleByKey == null){
            daoSession.getBundleBeanDao().insert(bundleBean);
        }else {
            bundleBean.__setDaoSession(daoSession);
            bundleBean.refresh();
        }

        return new SingleRecordTask(bundleBean,daoSession.getBundleBeanDao(),daoSession.getItemBeanDao()) {
            @Override
            protected BaseTask getTask() {
                return new TestTask(bundleBean.getKey());
            }
        };
    }



    static class TestTask extends BaseTask{


        private static final long TOTAL = 30;

        public TestTask(String key) {
            super(key);
        }

        @Override
        protected DownEvent createEvent(String key) {
            return new DownEvent(key);
        }

        @Override
        public void run() {
            int completedSize = 0;
            onStart(TOTAL,completedSize);
            while (!isPause() && completedSize<TOTAL){
                try {
                    Thread.sleep(1000);
                    completedSize++;
                    onUpdate(TOTAL,completedSize,123);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    onError(e);
                }
            }

            if (isPause()){
                onPause();
            }else {
                onFinish(TOTAL,completedSize);
            }
        }
    }
}
