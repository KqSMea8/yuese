package com.zhy.http.okhttp.builder;

import android.net.Uri;

import com.net.yuesejiaoyou.redirect.ResolverD.interface4.utils.LogUtil;
import com.zhy.http.okhttp.request.DeleteRequest;
import com.zhy.http.okhttp.request.RequestCall;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class DeteleBuilder extends OkHttpRequestBuilder<DeteleBuilder> implements HasParamsable {
    @Override
    public RequestCall build() {
        if (params != null) {
            url = appendParams(url, params);
        }
//        if (!TextUtils.isEmpty(OkHttpUtils.HEADER)) {
//            if (headers == null) {
//                headers = new HashMap<>();
//            }
//
//            long ctm = System.currentTimeMillis();
//            String s_clientId = StringUtills.stringToBase64(StringUtills.stringToMD5(OkHttpUtils.CLIENT_ID + OkHttpUtils.CLIENT_SECRET + ctm) + "ctm");
//            headers.put("Authorization", "Basic " + s_clientId);
//
//            //headers.put("Authorization", "Basic " + OkHttpUtils.HEADER);
//
//        }
        return new DeleteRequest(url, tag, params, headers, id).build();
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
        LogUtil.i("okhttp", "url: " + url);
        LogUtil.i("okhttp", "params: " + params.toString());
        return builder.build().toString();
    }


    @Override
    public DeteleBuilder params(Map<String, String> params) {
        this.params = params;
        return this;
    }

    @Override
    public DeteleBuilder addParams(String key, String val) {
        if (this.params == null) {
            params = new LinkedHashMap<>();
        }
        params.put(key, val);
        return this;
    }

    @Override
    public DeteleBuilder addParams(String key, int val) {
        if (this.params == null) {
            params = new LinkedHashMap<>();
        }
        params.put(key, val + "");
        return this;
    }

    @Override
    public DeteleBuilder addParams(String key, boolean val) {
        if (this.params == null) {
            params = new LinkedHashMap<>();
        }
        params.put(key, val + "");
        return this;
    }

    @Override
    public DeteleBuilder addParams(String key, float val) {
        if (this.params == null) {
            params = new LinkedHashMap<>();
        }
        params.put(key, val + "");
        return this;
    }


}
