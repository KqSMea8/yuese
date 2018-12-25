package com.net.yuesejiaoyou.redirect.ResolverD.interface4.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.redirect.ResolverA.getset.Page;
import com.net.yuesejiaoyou.redirect.ResolverA.getset.User_data;
import com.net.yuesejiaoyou.redirect.ResolverA.uiface.MainActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by admin on 2018/12/21.
 */

public class Tools {

    public static Dialog showProgressDialog(Context context, String msg) {
        return showProgressDialog(context, msg, true);
    }

    public static Dialog showProgressDialog(Context context, String msg, boolean isShow) {
        Dialog dialog = new Dialog(context, R.style.progress_requst_dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_just2, null);
        TextView textView = (TextView) view.findViewById(R.id.msg_tv);
        if (!TextUtils.isEmpty(msg)) {
            textView.setText(msg);
        }

        dialog.setContentView(view);
        Window window = dialog.getWindow();
        dialog.setCancelable(false);
        if (isShow) {
            dialog.show();
        }
        return dialog;
    }

    public static Page xunyuan(String json) {

        ArrayList<User_data> list = new ArrayList<User_data>();
        Page page = new Page();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                if(i==jsonArray.length()-1){
                    page.setTotlePage(item.getInt("totlePage"));
                    page.setCurrent(item.getInt("pagenum"));
                }else{
                    User_data bean=new User_data();
                    bean.setNickname(item.getString("nickname"));
                    bean.setId(item.getInt("id"));
                    bean.setIdentify_check(item.getInt("is_anchor"));
                    bean.setOnline(item.getInt("online"));
                    bean.setSignature(item.getString("signature"));
                    bean.setPhoto(item.getString("photo"));
                    bean.setPrice(item.getString("price"));
                    bean.setStar(item.getInt("star"));
                    bean.setPicture(item.getString("pictures"));
                    list.add(bean);
                }
            }
            page.setList(list);
        } catch (Exception e) {
            e.printStackTrace();
            LogDetect.send(LogDetect.DataType.specialType, "this0",e);
        }
        return page;
    }
    public static String currentVersion(Context context) {
        String versionName = "";
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionName = packageInfo.versionName;
            if (TextUtils.isEmpty(versionName)) {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }

    public static boolean checkVersion(String newVersionName,String oldVersionName) {
        String[] split = newVersionName.split("\\.");
        String[] split1 = oldVersionName.split("\\.");

        if (split.length > split1.length) {
            return true;
        }

        try {
            for (int i = 0; i < split.length; i++) {
                int a = Integer.parseInt(split[i]);
                int b = Integer.parseInt(split1[i]);
                if (a > b) {
                    return true;
                } else if (a < b) {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
