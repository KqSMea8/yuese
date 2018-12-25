package com.net.yuesejiaoyou.redirect.ResolverD.interface4.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.redirect.ResolverA.uiface.DownloadActivityApk;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.URL;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.utils.Tools;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.DialogCallback;


import okhttp3.Call;

/**
 * Created by tl on 2018/7/19.
 */

public class UpdateManager {

    private Activity mContext;

    public UpdateManager(Activity context) {
        this.mContext = context;
    }

    /**
     * 检测软件更新
     */
    public void checkUpdate() {
        OkHttpUtils.post(this)
                .url(URL.URL_UPDATE)
                .build()
                .execute(new DialogCallback(mContext, false) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String resultBean, int id) {
                        if (TextUtils.isEmpty(resultBean)) {
                            return;
                        }
                        JSONObject jsonObject = JSON.parseArray(resultBean).getJSONObject(0);
                        String apkversion = jsonObject.getString("vsion");

                        String currentVersion = Tools.currentVersion(mContext);
                        if (TextUtils.isEmpty(currentVersion)) {
                            return;
                        }
                        if (Tools.checkVersion(apkversion, currentVersion)) {
                            showDialog(jsonObject);
                        }

                    }
                });

    }

    private void showDialog(final JSONObject jsonObject) {

        View view = View.inflate(mContext, R.layout.zp_banbengengxintanchukuang, null);


        final Dialog dialog = new Dialog(mContext, R.style.Dialog);
        dialog.setContentView(view, new ViewGroup.LayoutParams(-2, -2));
        dialog.setCancelable(false);
        dialog.show();

        TextView gengxinc = view.findViewById(R.id.gengxinc);
        gengxinc.setText(jsonObject.getString("update_what"));
        view.findViewById(R.id.updateon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,DownloadActivityApk.class);
                Bundle bundle = new Bundle();
                bundle.putString("url", jsonObject.getString("downurl"));
                bundle.putString("name", jsonObject.getString("update_what"));
                intent.putExtras(bundle);
                mContext.startActivity(intent);
                dialog.dismiss();
            }
        });

    }

}
