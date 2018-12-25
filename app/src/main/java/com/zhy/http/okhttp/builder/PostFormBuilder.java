package com.zhy.http.okhttp.builder;

import com.net.yuesejiaoyou.redirect.ResolverD.interface4.utils.LogUtil;
import com.zhy.http.okhttp.request.PostFormRequest;
import com.zhy.http.okhttp.request.RequestCall;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhy on 15/12/14.
 */
public class PostFormBuilder extends OkHttpRequestBuilder<PostFormBuilder> implements HasParamsable {
    private List<FileInput> files = new ArrayList<>();


    public PostFormBuilder(){
        super();
    }

    public PostFormBuilder(Object tag){
        super();
        tag(tag);
    }

    @Override
    public RequestCall build() {
        LogUtil.i("okhttp", "url: " + url);
        if (params != null) {
            LogUtil.i("okhttp", "params: " + params.toString());
        }
//        if (!TextUtils.isEmpty(OkHttpUtils.HEADER)) {
//            if(headers == null){
//                headers = new HashMap<>();
//            }
//            headers.put("Authorization", "Basic " + OkHttpUtils.HEADER);
//        }

        return new PostFormRequest(url, tag, params, headers, files, id).build();
    }

    public PostFormBuilder files(String key, Map<String, File> files) {
        for (String filename : files.keySet()) {
            this.files.add(new FileInput(key, filename, files.get(filename)));
        }
        return this;
    }

    public PostFormBuilder addFile(String name, String filename, File file) {
        files.add(new FileInput(name, filename, file));
        return this;
    }

    public static class FileInput {
        public String key;
        public String filename;
        public File file;

        public FileInput(String name, String filename, File file) {
            this.key = name;
            this.filename = filename;
            this.file = file;
        }

        @Override
        public String toString() {
            return "FileInput{" +
                    "key='" + key + '\'' +
                    ", filename='" + filename + '\'' +
                    ", file=" + file +
                    '}';
        }
    }


    @Override
    public PostFormBuilder params(Map<String, String> params) {
        this.params = params;
        return this;
    }

    @Override
    public PostFormBuilder addParams(String key, String val) {
        if (this.params == null) {
            params = new LinkedHashMap<>();
        }
        params.put(key, val);
        return this;
    }


    @Override
    public PostFormBuilder addParams(String key, int val) {
        if (this.params == null) {
            params = new LinkedHashMap<>();
        }
        params.put(key, val + "");
        return this;
    }

    @Override
    public PostFormBuilder addParams(String key, boolean val) {
        if (this.params == null) {
            params = new LinkedHashMap<>();
        }
        params.put(key, val + "");
        return this;
    }

    @Override
    public PostFormBuilder addParams(String key, float val) {
        if (this.params == null) {
            params = new LinkedHashMap<>();
        }
        params.put(key, val + "");
        return this;
    }

}
