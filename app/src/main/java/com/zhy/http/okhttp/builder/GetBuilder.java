package com.zhy.http.okhttp.builder;

import android.net.Uri;

import com.net.yuesejiaoyou.redirect.ResolverD.interface4.utils.LogUtil;
import com.zhy.http.okhttp.request.GetRequest;
import com.zhy.http.okhttp.request.RequestCall;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by zhy on 15/12/14.
 */
public class GetBuilder extends OkHttpRequestBuilder<GetBuilder> implements HasParamsable {

    public GetBuilder(){
        super();
    }

    public GetBuilder(Object tag){
        super();
        tag(tag);
    }

    @Override
    public RequestCall build() {

//        if (!TextUtils.isEmpty(OkHttpUtils.HEADER)) {
//            if(headers == null){
//                headers = new HashMap<>();
//            }
//            headers.put("Authorization", "Basic " + OkHttpUtils.HEADER);
//        }
        if (params != null) {
            url = appendParams(url, params);
            LogUtil.i("okhttp", "params: " + params.toString());
        }

        LogUtil.i("okhttp", "heards: " + headers.toString());
        LogUtil.i("okhttp", "url: " + url);
        return new GetRequest(url, tag, params, headers, id).build();
    }

    protected String appendParams(String url, Map<String, String> params) {
        if (url == null || params == null || params.isEmpty()) {
            return url;
        }
        Uri.Builder builder = Uri.parse(url).buildUpon();
        Set<String> keys = params.keySet();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            builder.appendQueryParameter(key, params.get(key));
        }
        return builder.build().toString();
    }


    @Override
    public GetBuilder params(Map<String, String> params) {
        this.params = params;
        return this;
    }

    @Override
    public GetBuilder addParams(String key, String val) {
        if (this.params == null) {
            params = new LinkedHashMap<>();
        }
        params.put(key, val);
        return this;
    }

    @Override
    public GetBuilder addParams(String key, int val) {
        if (this.params == null) {
            params = new LinkedHashMap<>();
        }
        params.put(key, val + "");
        return this;
    }

    @Override
    public GetBuilder addParams(String key, boolean val) {
        if (this.params == null) {
            params = new LinkedHashMap<>();
        }
        params.put(key, val + "");
        return this;
    }

    @Override
    public GetBuilder addParams(String key, float val) {
        if (this.params == null) {
            params = new LinkedHashMap<>();
        }
        params.put(key, val + "");
        return this;
    }


}
