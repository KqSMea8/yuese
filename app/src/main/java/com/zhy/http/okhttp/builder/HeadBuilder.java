package com.zhy.http.okhttp.builder;

import android.text.TextUtils;
import android.util.Log;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.request.OtherRequest;
import com.zhy.http.okhttp.request.RequestCall;

import java.util.HashMap;

/**
 * Created by zhy on 16/3/2.
 */
public class HeadBuilder extends GetBuilder {
    @Override
    public RequestCall build() {
//        if (!TextUtils.isEmpty(OkHttpUtils.HEADER)) {
//            if(headers == null){
//                headers = new HashMap<>();
//            }
//            headers.put("Authorization", "Basic " + OkHttpUtils.HEADER);
//        }

        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers, id).build();
    }
}
