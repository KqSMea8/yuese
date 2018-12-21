package com.net.yuesejiaoyou.redirect.ResolverB.interface4.util;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.alibaba.sdk.android.vod.upload.VODUploadCallback;
import com.alibaba.sdk.android.vod.upload.VODUploadClient;
import com.alibaba.sdk.android.vod.upload.VODUploadClientImpl;
import com.alibaba.sdk.android.vod.upload.model.UploadFileInfo;
import com.alibaba.sdk.android.vod.upload.model.VodInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/14.
 */

public class VideoUploadUtil {

    private static VideoUploadUtil instance;
    private VODUploadClient uploader;
    private String uploadAddress;
    private String uploadAuth;
    private Handler handler;
    private Context mContext;
    public static void init(Context ctxt) {
        if(instance == null) {
            instance = new VideoUploadUtil(ctxt);
            instance.mContext = ctxt;
        }
    }
    private VideoUploadUtil(Context ctxt) {
        uploader = new VODUploadClientImpl(ctxt);
        VODUploadCallback callback = new VODUploadCallback() {

            @Override
            public void onUploadSucceed(UploadFileInfo uploadFileInfo) {
                Log.e("YT","onUploadSucceed()");
                if(handler != null) {
                    Message.obtain(handler,3).sendToTarget();
                }
                uploader.clearFiles();
            }

            @Override
            public void onUploadFailed(UploadFileInfo uploadFileInfo, String s, String s1) {
                Log.e("YT","onUploadFailed()");
                if(handler != null) {
                    Message.obtain(handler,4).sendToTarget();
                }
                uploader.clearFiles();
            }

            @Override
            public void onUploadProgress(UploadFileInfo uploadFileInfo, long uploadSize, long totalSize) {
                Log.e("YT","onUploadProgress():"+((int)(uploadSize*100/totalSize)));
                if(handler != null) {
                    Message.obtain(handler,5,((int)(uploadSize*100/totalSize))).sendToTarget();
                }
            }

            @Override
            public void onUploadTokenExpired() {

            }

            @Override
            public void onUploadRetry(String s, String s1) {

            }

            @Override
            public void onUploadRetryResume() {

            }

            @Override
            public void onUploadStarted(UploadFileInfo uploadFileInfo) {
                Log.e("YT","onUploadStarted()");
                uploader.setUploadAuthAndAddress(uploadFileInfo, uploadAuth, uploadAddress);
            }
        };
        //uploader.init(callback);
        uploader.init("LTAIMw9n5RwSGy7V","ttaQTSsdubE5ydnA0YsjfdQGhDt1vU",callback);
    }

    public static void uploadVideo(Handler handler, String file, String uploadAddress, String uploadAuth) {
        Context tmpCtxt = instance.mContext;
        instance = new VideoUploadUtil(instance.mContext); // 重新创建uploader
        instance.mContext = tmpCtxt;
        instance.handler = handler;
        instance.uploadAddress = uploadAddress;
        instance.uploadAuth = uploadAuth;
        instance.uploader.addFile(file,getVodInfo());
        instance.uploader.start();
    }

    private static VodInfo getVodInfo() {
        VodInfo vodInfo = new VodInfo();
        vodInfo.setTitle("标题");
        vodInfo.setDesc("描述");
        vodInfo.setCateId(0);
        vodInfo.setIsProcess(true);
        vodInfo.setCoverUrl("");
        List<String> tags = new ArrayList<>();
        tags.add("标签");
        vodInfo.setTags(tags);
        vodInfo.setIsShowWaterMark(false);
        vodInfo.setPriority(7);

        return vodInfo;
    }
}
