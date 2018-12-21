package com.net.yuesejiaoyou.redirect.ResolverD.interface4;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.net.yuesejiaoyou.R;

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
}
