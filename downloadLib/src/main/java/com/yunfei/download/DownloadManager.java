package com.yunfei.download;

public class DownloadManager extends BaseDownloadManager {
    @Override
    protected BaseTask getTask(BundleBean bundleBean) {
        return new TestTask(bundleBean.getKey());
    }

    static class TestTask extends BaseTask{

        public TestTask(String key) {
            super(key);
        }

        @Override
        protected DownEvent createEvent(String key) {
            return new DownEvent(key);
        }

        @Override
        public void run() {
        }
    }
}
