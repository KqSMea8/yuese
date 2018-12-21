package com.zhy.http.okhttp.builder;

import android.text.TextUtils;
import android.util.Log;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.request.PostFileRequest;
import com.zhy.http.okhttp.request.RequestCall;

import java.io.File;
import java.util.HashMap;

import okhttp3.MediaType;

/**
 * Created by zhy on 15/12/14.
 */
public class PostFileBuilder extends OkHttpRequestBuilder<PostFileBuilder> {
    private File file;
    private MediaType mediaType;


    public OkHttpRequestBuilder file(File file) {
        this.file = file;
        return this;
    }

    public OkHttpRequestBuilder mediaType(MediaType mediaType) {
        this.mediaType = mediaType;
        return this;
    }

    @Override
    public RequestCall build() {
//        if (!TextUtils.isEmpty(OkHttpUtils.HEADER)) {
//            if(headers == null){
//                headers = new HashMap<>();
//            }
//            headers.put("Authorization", "Basic " + OkHttpUtils.HEADER);
//        }

        return new PostFileRequest(url, tag, params, headers, file, mediaType, id).build();
    }


}
