package com.net.yuesejiaoyou.redirect.ResolverD.interface4.activity;



import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.net.yuesejiaoyou.R;

import com.net.yuesejiaoyou.redirect.ResolverD.interface4.BaseActivity;

import butterknife.OnClick;


public class Answer2Activity extends BaseActivity {

    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_answer2;
    }

    @OnClick(R.id.back)
    public void backClick() {
        finish();
    }

    @OnClick(R.id.tongdao)
    public void tongdaoClick() {
        showPopupspWindow_reservation(findViewById(R.id.scrollview));
    }


    //预约
    @SuppressLint({"RtlHardcoded", "NewApi"})
    public void showPopupspWindow_reservation(View parent) {
        // 加载布局
        LayoutInflater inflater = LayoutInflater.from(Answer2Activity.this);
        View layout = inflater.inflate(R.layout.pop_authority_01160, null);

        Button b1, b2, b3, b4, b5;
        TextView b6;

        b1 = (Button) layout.findViewById(R.id.b1);
        b2 = (Button) layout.findViewById(R.id.b2);
        b3 = (Button) layout.findViewById(R.id.b3);
        b4 = (Button) layout.findViewById(R.id.b4);
        b5 = (Button) layout.findViewById(R.id.b5);
        b6 = (TextView) layout.findViewById(R.id.b6);

        b1.setOnClickListener(new btnListener(b1));
        b2.setOnClickListener(new btnListener(b2));
        b3.setOnClickListener(new btnListener(b3));
        b4.setOnClickListener(new btnListener(b4));
        b5.setOnClickListener(new btnListener(b5));
        b6.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        WindowManager m = getWindowManager();
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        //int height = (int)(metrics.heightPixels*0.8);
        int width = (int) (metrics.widthPixels * 0.7);

        popupWindow = new PopupWindow(layout, width,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        // 控制键盘是否可以获得焦点

        popupWindow.setFocusable(true);
        WindowManager.LayoutParams lp = Answer2Activity.this.getWindow().getAttributes();
        lp.alpha = 0.5f;
        Answer2Activity.this.getWindow().setAttributes(lp);
        popupWindow.setBackgroundDrawable(new BitmapDrawable(null, ""));
        //popupWindow.showAsDropDown(parent, 0, 0,Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL);
        popupWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            public void onDismiss() {
                WindowManager.LayoutParams lp = Answer2Activity.this.getWindow().getAttributes();
                lp.alpha = 1f;
                Answer2Activity.this.getWindow().setAttributes(lp);
            }
        });
    }


    /*
            * 创建一个按钮监听器类, 作用是点击按钮后改变按钮的名字
    */
    class btnListener implements OnClickListener {
        //定义一个 Button 类型的变量
        private Button btn;

        /*
        * 一个构造方法, 将Button对象做为参数
        */
        private btnListener(Button btn) {
            this.btn = btn;//将引用变量传递给实体变量
        }

        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", getApplication().getPackageName(), null);
            intent.setData(uri);
            startActivity(intent);
        }
    }


}
