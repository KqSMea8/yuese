package com.zhy.http.okhttp.builder;

import android.text.TextUtils;
import android.util.Log;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.request.OtherRequest;
import com.zhy.http.okhttp.request.RequestCall;

import java.util.HashMap;

import okhttp3.RequestBody;

/**
 * DELETE、PUT、PATCH等其他方法
 */
public class OtherRequestBuilder extends OkHttpRequestBuilder<OtherRequestBuilder> {
    private RequestBody requestBody;
    private String method;
    private String content;

    public OtherRequestBuilder(String method) {
        this.method = method;
    }

    @Override
    public RequestCall build() {
//        if (!TextUtils.isEmpty(OkHttpUtils.HEADER)) {
//            if(headers == null){
//                headers = new HashMap<>();
//            }
//            headers.put("Authorization", "Basic " + OkHttpUtils.HEADER);
//        }

        return new OtherRequest(requestBody, content, method, url, tag, params, headers, id).build();
    }

    public OtherRequestBuilder requestBody(RequestBody requestBody) {
        this.requestBody = requestBody;
        return this;
    }

    public OtherRequestBuilder requestBody(String content) {
        this.content = content;
        return this;
    }

}
