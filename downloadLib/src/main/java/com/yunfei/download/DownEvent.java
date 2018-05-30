package com.yunfei.download;

public class DownEvent {
    private String key;
    private long totalSize;
    private long completedSize;
    private Status status;
    private long speed;
    private Exception error;

    public DownEvent(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

    public long getCompletedSize() {
        return completedSize;
    }

    public void setCompletedSize(long completedSize) {
        this.completedSize = completedSize;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public long getSpeed() {
        return speed;
    }

    public void setSpeed(long speed) {
        this.speed = speed;
    }

    public Exception getError() {
        return error;
    }

    public void setError(Exception error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "DownEvent{" +
                "key='" + key + '\'' +
                ", totalSize=" + totalSize +
                ", completedSize=" + completedSize +
                ", status=" + status +
                ", speed=" + speed +
                ", error=" + error +
                '}';
    }
}
