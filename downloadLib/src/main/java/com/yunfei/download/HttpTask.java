package com.yunfei.download;

import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class HttpTask extends BaseTask {
    private static final int SPEED_CHECK = 200*1024; //每下载 200kb 才通知
    private final String url;
    private final String path;
    private long completedSize;
    private long totalSize;
    private OkHttpClient httpClient;
    private Call requestCall;

    public HttpTask(String key,OkHttpClient client,String url,String path,long totalSize,long completedSize) {
        super(key);
        this.httpClient = client;
        this.url = url;
        this.path = path;
        this.totalSize = totalSize;
        this.completedSize = completedSize;
    }

    public HttpTask(String key,OkHttpClient client,String url,String path) {
        this(key,client,url,path,0,0);
    }

    public HttpTask(String key,OkHttpClient client,String url,String path,OnEventListener onEventListener) {
        this(key,client,url,path,0,0);
        this.addObserver(onEventListener);
    }



    @Override
    protected DownEvent createEvent(String key) {
        return new DownEvent(key);
    }

    @Override
    public void start() {
        if (isPause()){
            onPause();
            return;
        }
        Request request = new Request.Builder().url(url).addHeader("RANGE", "bytes=" + completedSize + "-").build();
        requestCall = httpClient.newCall(request);
        try {
            Response response = requestCall.execute();
            if (response.isSuccessful()) {
                ResponseBody body = response.body();
                writeCaches(body, new File(path));
            }


        } catch (Exception e) {
            if (!isPause())
                onError(e);
        }
    }


    /**
     * 写入文件
     *
     * @param file
     * @throws IOException
     */
    public void writeCaches(ResponseBody responseBody, File file) throws IOException {
        try {
            RandomAccessFile randomAccessFile = null;
            FileChannel channelOut = null;
            InputStream inputStream = null;
            try {
                if (!file.getParentFile().exists())
                    file.getParentFile().mkdirs();
                //断点续传
                long contentLength = responseBody.contentLength();

                totalSize = 0 == totalSize ? responseBody.contentLength() : completedSize + contentLength;
                //大小
                //开始
                onStart(totalSize,completedSize);

                inputStream = responseBody.byteStream();
                randomAccessFile = new RandomAccessFile(file, "rwd");
                channelOut = randomAccessFile.getChannel();
                MappedByteBuffer mappedBuffer = channelOut.map(FileChannel.MapMode.READ_WRITE,
                        completedSize, totalSize - completedSize);
                byte[] buffer = new byte[1024 * 4];
                int len;
                int speedLength = 0;
                long startTime = System.currentTimeMillis();
                while ((len = inputStream.read(buffer)) != -1 && !isPause()) {
                    mappedBuffer.put(buffer, 0, len);

                    //累计下载长度
                    speedLength+=len;
                    completedSize+=len;

                    long endTIme = System.currentTimeMillis();
                    if (speedLength>=SPEED_CHECK||(endTIme-startTime)>1500) {
                        if (endTIme>startTime) {
//                            Log.i("速度","speedLength/(endTIme-startTime) "+speedLength+"/"+"("+endTIme+"-"+startTime+")" + " "+ speedLength/(endTIme-startTime) );
                            onUpdate(totalSize,completedSize,speedLength/(endTIme-startTime));
                            speedLength = 0;
                            startTime = endTIme;
                        }
                    }
                }

                if (speedLength<SPEED_CHECK){
                    long endTIme = System.currentTimeMillis();
                    if (endTIme>startTime) {
                        onUpdate(totalSize,completedSize,speedLength/(endTIme-startTime));
//                        speedLength = 0;
//                        startTime = endTIme;
                    }
                }

                if (!isPause()){
                    onFinish(totalSize,completedSize);
                }else {
                    onPause();
                }

            } catch (IOException e) {
                throw e;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (channelOut != null) {
                    channelOut.close();
                }
                if (randomAccessFile != null) {
                    randomAccessFile.close();
                }
            }
        } catch (IOException e) {
            throw e;
        }
    }

    @Override
    public void run() {
        start();
    }
}
