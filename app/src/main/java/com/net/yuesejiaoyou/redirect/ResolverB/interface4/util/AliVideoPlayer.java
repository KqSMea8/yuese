package com.net.yuesejiaoyou.redirect.ResolverB.interface4.util;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import net.sf.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/3/11.
 */

public class AliVideoPlayer {


    public static void playVideoToActivity(final String videoid, final Context ctxt, final String jumpName) {
        basePlayVideo(videoid, 1, ctxt, jumpName, null);
    }

    public static void playVideoToListener(String videoid, final VideoPlayListener listener) {
        basePlayVideo(videoid, 2, null, null, listener);
    }

    private static void basePlayVideo(final String videoid, final int type, final Context ctxt, final String jumpName, final VideoPlayListener listener) {
        {
            // 生成URL
            Map<String, String> privateParams = AliVideoUtil.generatePrivateParamters(videoid);
            Map<String, String> publicParams = AliVideoUtil.generatePublicParamters();
            String URL = AliVideoUtil.generateOpenAPIURL(publicParams, privateParams);
            Log.v("TCQO","URL:"+URL);
            OkHttpClient client = new OkHttpClient();
            RequestBody body = new FormBody.Builder().build();
            Request request = new Request.Builder().url(URL).post(body).build();
            Call call = client.newCall(request);

            call.enqueue(new Callback() {

                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String json = response.body().string();
                    JSONObject jsonObj = JSONObject.fromObject(json);
                    Log.v("TCQO","json:"+json);
                    if(type == 1) {     // type==1  跳转到播放页面
                        Intent intent = new Intent();
                        intent.setClassName(ctxt, jumpName);
                        intent.putExtra("type", "authInfo");
                        intent.putExtra("vid", videoid);
                        intent.putExtra("authinfo", jsonObj.getString("PlayAuth"));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                        ctxt.startActivity(intent);
                    } else if(type == 2) {      // type==2 返回播放信息
                        if(listener != null) {
                            listener.vpOnSuccess("authInfo",videoid,jsonObj.getString("PlayAuth"));
                        }
                    }
                }
            });
        }
    }

    //===========================================================================
    // 私有内部方法
}
