package com.zhy.http.okhttp.request;



import com.net.yuesejiaoyou.redirect.ResolverD.interface4.utils.LogUtil;

import org.json.JSONObject;

import java.util.Map;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

public class PostJsonRequest extends OkHttpRequest {
    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

    public PostJsonRequest(String url, Object tag, Map<String, String> params, Map<String, String> headers, int id) {
        super(url, tag, params, headers, id);
        LogUtil.i("okhttp", "headers:" + headers.toString());
        LogUtil.i("okhttp", "params:" + params.toString());
    }

    @Override
    protected RequestBody buildRequestBody() {

        //String json = JSON.toJSON(params).toString();
        String json = new JSONObject(params).toString();
        return RequestBody.create(MEDIA_TYPE_JSON, json);
    }

    @Override
    protected Request buildRequest(RequestBody requestBody) {
        return builder.post(requestBody).build();
    }
}
