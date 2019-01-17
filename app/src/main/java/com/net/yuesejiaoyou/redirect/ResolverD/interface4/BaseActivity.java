package com.net.yuesejiaoyou.redirect.ResolverD.interface4;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.net.yuesejiaoyou.R;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.zhy.http.okhttp.OkHttpUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import butterknife.ButterKnife;

/**
 * Created by admin on 2018/12/21.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected SharedPreferences sp;
    protected YhApplicationA app;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        ButterKnife.bind(this);
        //强制竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        sp = getSharedPreferences("Acitivity", Activity.MODE_PRIVATE);
        ;
        app = (YhApplicationA) getApplication();


        ImmersionBar.with(this)
                .statusBarColor(statusBarColor())
                .statusBarDarkFont(statusBarFont())
                .init();
    }

    @ColorRes
    public int statusBarColor() {
        return R.color.white;
    }

    public boolean statusBarFont() {
        return true;
    }

    protected abstract int getContentView();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(this);
        ImmersionBar.with(this).destroy();
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0-1.0
        getWindow().setAttributes(lp);
    }


    protected void startActivity(Class<?> cls) {
        startActivity(new Intent(this, cls));
    }

    protected void startActivity(Class<?> cls, boolean flag) {
        startActivity(cls);
        if (flag) {
            finish();
        }
    }

    protected void startActivityForResult(Class<?> cls, int resultCode) {
        startActivityForResult(new Intent(this, cls), resultCode);
    }

    protected void showToast(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    protected void showToast(int msg) {
        showToast(getString(msg));
    }
}
