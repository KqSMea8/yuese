package com.zhy.http.okhttp.builder;

import android.util.Log;

import com.net.yuesejiaoyou.redirect.ResolverD.interface4.LogUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.request.PostJsonRequest;
import com.zhy.http.okhttp.request.RequestCall;

import java.util.LinkedHashMap;
import java.util.Map;

public class PostJsonBuilder extends OkHttpRequestBuilder<PostJsonBuilder> implements HasParamsable {

    public PostJsonBuilder() {
        super();
    }

    public PostJsonBuilder(Object tag) {
        super();
        tag(tag);
    }

    @Override
    public PostJsonBuilder params(Map<String, String> params) {
        this.params = params;
        return this;
    }

    @Override
    public PostJsonBuilder addParams(String key, String val) {
        if (this.params == null) {
            params = new LinkedHashMap<>();
        }
        params.put(key, val);
        return this;
    }

    @Override
    public PostJsonBuilder addParams(String key, int val) {
        if (this.params == null) {
            params = new LinkedHashMap<>();
        }
        params.put(key, val + "");
        return this;
    }

    @Override
    public PostJsonBuilder addParams(String key, boolean val) {
        if (this.params == null) {
            params = new LinkedHashMap<>();
        }
        params.put(key, val + "");
        return this;
    }

    @Override
    public PostJsonBuilder addParams(String key, float val) {
        if (this.params == null) {
            params = new LinkedHashMap<>();
        }
        params.put(key, val + "");
        return this;
    }

    @Override
    public RequestCall build() {

        //Log.i("okhttp", "params: " + params.toString());
//        if (!TextUtils.isEmpty(OkHttpUtils.HEADER)) {
//            if(headers == null){
//                headers = new HashMap<>();
//            }
//            headers.put("Authorization", "Basic " + OkHttpUtils.HEADER);
//        }

        LogUtil.i("okhttp", "url: " + url);
        return new PostJsonRequest(url, tag, params, headers, id).build();
    }
}
