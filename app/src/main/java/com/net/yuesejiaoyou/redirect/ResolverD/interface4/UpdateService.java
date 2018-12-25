/* 
 * Copyright 2012 Share.Ltd  All rights reserved.
 * Share.Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * @UpdateService.java - 2012-7-23 4:19:56 - Anonymous
 */

package com.net.yuesejiaoyou.redirect.ResolverD.interface4;


import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.utils.LogUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;


public class UpdateService extends Service {

    public static String fileName;
    private NotificationCompat.Builder builder;
    private NotificationManager notificationManager;
    private int NOTIFY_ID = 1000;
    private String baseDir;

    public void onCreate() {
        super.onCreate();
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) {
            return super.onStartCommand(intent, flags, startId);
        }

        String url = intent.getStringExtra("url");
        String appVersion = intent.getStringExtra("version");

        if (TextUtils.isEmpty(url) || TextUtils.isEmpty(appVersion)) {
            return super.onStartCommand(intent, flags, startId);
        }
        loadFile(url, appVersion);

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void loadFile(String url, String version) {
        fileName = "yuese_" + version + ".apk";
        baseDir = Environment.getExternalStorageDirectory().getAbsolutePath();
        File file = new File(baseDir, fileName);
        if (file.exists()) {
            installApk();
            return;
        }

        updateNotification(0);

        OkHttpUtils.get(this)
                .url(url)
                .build()
                .execute(new FileCallBack(baseDir, fileName + ".d") {

                    int lastProgress = 0;

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtil.i("ttt", "----onError:" + Thread.currentThread().getName());
                        notificationManager.cancel(NOTIFY_ID);
                        stopSelf();
                    }

                    @Override
                    public void inProgress(float progress, long total, int id) {
                        int pro = (int) (progress * 100);
                        if (pro > lastProgress) {
                            updateNotification(pro);
                            lastProgress = pro;
                        }
                    }

                    @Override
                    public void onResponse(File response, int id) {
                        installApk();
                    }
                });
    }

    public void updateNotification(int progress) {
        if (builder == null) {
            builder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("正在下载...");
        }

        builder.setContentText(progress + "%");
        builder.setProgress(100, progress, false);
        notificationManager.notify(NOTIFY_ID, builder.build());
    }

    private void installApk() {
        File file = new File(baseDir, fileName + ".d");
        File newFile = new File(baseDir, fileName);

        if (file.exists()) {
            file.renameTo(newFile);
        }

        chmod(newFile.getAbsolutePath());
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri parse = Uri.parse("file://" + newFile.getAbsolutePath());
        intent.setDataAndType(parse, "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        notificationManager.cancel(NOTIFY_ID);
        stopSelf();
    }


    public static void chmod(String path) {
        try {
            String command = "chmod 777 " + path;
            Log.i("update", command);
            Runtime runtime = Runtime.getRuntime();
            runtime.exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}