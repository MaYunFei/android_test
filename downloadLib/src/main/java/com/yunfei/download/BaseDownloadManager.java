package com.yunfei.download;

import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;


public abstract class BaseDownloadManager extends BaseTask.OnEventListener {
    static final int MAX_PART_COUNT = 2;
    private static final String TAG = "BaseDownloadManager";
    /**
     * 线程控制器
     */
    private ExecutorService executor;

    /**
     * 下载队列
     */
    private BlockingQueue<BaseTask> downloadQueue;  //必须是全局监听
    /**
     * 下载任务
     */
    private Map<String, BaseTask> downloadIngTasks; // 为什么用 map


    public BaseDownloadManager() {
        executor = Executors.newCachedThreadPool(new ThreadFactory() {
            private AtomicInteger count = new AtomicInteger();

            @Override
            public Thread newThread(final Runnable r) {
                final int number = count.incrementAndGet();
                return new Thread(new Runnable() {
                    @Override
                    public void run() {
                        r.run();
                    }
                }, "download   " + number);
            }
        });
        downloadIngTasks = new ConcurrentHashMap<>();
        downloadQueue = new LinkedBlockingQueue<>();
    }

    public void addBundle(BundleBean bundleBean, BaseTask.OnEventListener onEventListener){
        // 添加 Task 等待队列判断
        for (BaseTask downloadTask : downloadQueue) {
            if (downloadTask.getKey().equals(bundleBean.getKey())){
                return;
            }
        }
        if (downloadIngTasks.get(bundleBean.getKey()) != null) {
            return;
        }


        //获取任务
        BaseTask downloadTask = getTask(bundleBean);
        downloadTask.addObserver(onEventListener);

        if (downloadIngTasks.size() >= MAX_PART_COUNT) {
            downloadQueue.add(downloadTask);

            //TODO 等待
            DownEvent downEvent = new DownEvent(bundleBean.getKey());
            downEvent.setStatus(Status.WATTING);
            onEventListener.onEvent(downEvent);


        } else {
            startDownload(downloadTask);
        }

    }

    protected abstract BaseTask getTask(BundleBean bundleBean);

    /**
     * 正在下载的 event
     * @param event
     */
    @Override
    protected void onEvent(DownEvent event) {
        switch (event.getStatus()){
            case ERROR:
                removeFinishTask(event);
                break;
            case PAUSE:
                removeFinishTask(event);
                break;
            case FINISH:
                removeFinishTask(event);
                break;
            case WATTING:
                break;
            case DOWLOADING:
                removeFinishTask(event);
                break;
                default:
                    break;
        }
    }

    /**
     * 移除已经运行完毕的task
     * @param event
     */
    private void removeFinishTask(DownEvent event) {
        downloadIngTasks.remove(event.getKey());
        checkAllFinish();
    }

    private void checkAllFinish() {
        if (!checkNext()&& downloadIngTasks.size() == 0) {
            //AllFinish
        }
    }

    private void startDownload(BaseTask entity) {
        downloadIngTasks.put(entity.getKey(), entity);
        //加入 正在下载 监听   不用手动删除每次都自动删除
        entity.addObserver(this);
        executor.submit(entity);
    }

    /**
     * 暂停
     * @param key
     */
    public  void pause(String key){
        for (BaseTask task : downloadQueue) {
            if (task.getKey().equals(key)){
                downloadQueue.remove(task);
                task.pause();
                return;
            }

        }
        BaseTask downloadTask = downloadIngTasks.get(key);
        if (downloadTask != null) {
            downloadTask.pause();
        }
    }


    public  void pause(List<String> keys) {
        for (String key : keys) {
            pause(key);
        }
    }


    /**
     * 有没有下一个
     * @return
     */
    private boolean checkNext() {
        if (downloadIngTasks.size() >=MAX_PART_COUNT){
            return false;
        }
        BaseTask nextEntity = downloadQueue.poll();

        if (nextEntity != null) {

            startDownload(nextEntity);
            return true;
        }
        return false;
    }
}
