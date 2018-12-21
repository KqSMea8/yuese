package com.zhy.http.okhttp.callback;

import android.app.Dialog;
import android.content.Context;


import com.net.yuesejiaoyou.redirect.ResolverD.interface4.Tools;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/8/26.
 */
public abstract class DialogCallback extends Callback<String> {

    Context context;
    private Dialog dialog;
    boolean flag;

    public DialogCallback(Context context) {
        this.context = context;
        flag = true;
    }

    public DialogCallback(Context context, boolean flag) {
        this.context = context;
        this.flag = flag;
    }

    @Override
    public void onBefore(Request request, int id) {
        super.onBefore(request, id);
        //
        if (flag) {
            try {
                dialog = Tools.showProgressDialog(context, "正在加载...");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onAfter(int id) {
        super.onAfter(id);
        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
            dialog = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResponse(String response, int id) {
    }



    @Override
    public String parseNetworkResponse(Response response, int id) throws Exception {
        return response.body().string();
    }

}
