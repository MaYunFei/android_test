package com.yunfei.downloaddemo;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.yunfei.download.BaseTask;
import com.yunfei.download.BundleBean;
import com.yunfei.download.DaoMaster;
import com.yunfei.download.DownEvent;
import com.yunfei.download.DownloadManager;

import org.greenrobot.greendao.query.QueryBuilder;

public class MainActivity extends AppCompatActivity {

    private DownloadManager downloadManager;
    BaseTask.OnEventListener onEventListener = new BaseTask.OnEventListener(){

        @Override
        protected void onEvent(DownEvent event) {
            Log.i("Main",event.toString());
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;

        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(getApplicationContext(), "download.db");
        SQLiteDatabase writableDatabase = devOpenHelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(writableDatabase);



        downloadManager = new DownloadManager(daoMaster.newSession());

        for (int i = 0; i < 10; i++) {
            BundleBean bundleBean = new BundleBean();
            bundleBean.setKey("123"+i);
            downloadManager.addBundle(bundleBean,onEventListener);
        }


    }
}
