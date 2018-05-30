package com.yunfei.download;

import java.util.Observable;
import java.util.Observer;

public abstract class BaseTask extends Observable implements ITask {
    private final String key;
    private volatile boolean isPause;
    //event 初始化
    private DownEvent downEvent;

    public BaseTask(String key) {
        this.key = key;
        downEvent = createEvent(key);
        downEvent.setStatus(Status.WATTING);
    }

    public String getKey() {
        return key;
    }

    protected abstract DownEvent createEvent(String key);


    public boolean isPause() {
        return isPause;
    }

    @Override
    public void start() {

    }

    @Override
    public void pause() {
        isPause = true;
    }

    protected void onStart(long totalSize,long completedSize){
        downEvent.setTotalSize(totalSize);
        downEvent.setCompletedSize(completedSize);
        downEvent.setStatus(Status.DOWLOADING);
        updateEvent();
    }


    protected void onUpdate(long totalSize,long completedSize,long speed){
        downEvent.setTotalSize(totalSize);
        downEvent.setCompletedSize(completedSize);
        downEvent.setStatus(Status.DOWLOADING);
        downEvent.setSpeed(speed);
        updateEvent();
    }

    protected void onPause(){
        downEvent.setStatus(Status.PAUSE);
        updateEvent();
        deleteObservers();
    }

    protected void onError(Exception e){
        downEvent.setStatus(Status.ERROR);
        downEvent.setError(e);
        updateEvent();
        deleteObservers();
    }

    protected void onFinish(long totalSize,long completedSize){
        downEvent.setTotalSize(totalSize);
        downEvent.setCompletedSize(completedSize);
        downEvent.setStatus(Status.FINISH);
        updateEvent();
        deleteObservers();
    }




    /**
     * 通知观察者
     */
    public void updateEvent(){
        setChanged();
        notifyObservers(downEvent);
    }

    /**
     * 观察者
     */
    public static abstract class OnEventListener implements Observer {

        @Override
        public void update(Observable o, Object event) {
            if (event instanceof DownEvent) {
                onEvent((DownEvent) event);
            }
        }

        protected abstract void onEvent(DownEvent event);
    }
}
