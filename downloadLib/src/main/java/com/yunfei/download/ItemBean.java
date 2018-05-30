package com.yunfei.download;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class ItemBean {
    @Id
    private Long id;
    private long bundleId;
    private long totalSize;
    private long completedSize;
    private String path;
    private String url;
    @Convert(converter = Status.StatusConverter.class, columnType = Integer.class)
    private Status status;
    @Generated(hash = 307597651)
    public ItemBean(Long id, long bundleId, long totalSize, long completedSize,
            String path, String url, Status status) {
        this.id = id;
        this.bundleId = bundleId;
        this.totalSize = totalSize;
        this.completedSize = completedSize;
        this.path = path;
        this.url = url;
        this.status = status;
    }
    @Generated(hash = 95333960)
    public ItemBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public long getBundleId() {
        return this.bundleId;
    }
    public void setBundleId(long bundleId) {
        this.bundleId = bundleId;
    }
    public long getTotalSize() {
        return this.totalSize;
    }
    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }
    public long getCompletedSize() {
        return this.completedSize;
    }
    public void setCompletedSize(long completedSize) {
        this.completedSize = completedSize;
    }
    public String getPath() {
        return this.path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public Status getStatus() {
        return this.status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }


}
