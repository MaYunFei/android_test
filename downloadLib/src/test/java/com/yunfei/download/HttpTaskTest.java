package com.yunfei.download;

import org.junit.Test;

import okhttp3.OkHttpClient;

import static org.junit.Assert.*;

public class HttpTaskTest {
    public static final String url = "https://dldir1.qq.com/weixin/mac/Wechat_2.3.13.dmg";

    @Test
    public void start() {
        OkHttpClient okHttpClient = new OkHttpClient();
        HttpTask task = new HttpTask("123",okHttpClient,url,"/Users/yunfei/Documents/Codes/github/DownloadDemo/douyin.apk");
        task.addObserver(new BaseTask.OnEventListener() {
            @Override
            protected void onEvent(DownEvent event) {
                System.out.println(event);
            }
        });

        task.start();
    }
}