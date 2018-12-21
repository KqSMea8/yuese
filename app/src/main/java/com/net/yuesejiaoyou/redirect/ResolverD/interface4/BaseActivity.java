package com.net.yuesejiaoyou.redirect.ResolverD.interface4;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.net.yuesejiaoyou.classroot.core.YhApplicationA;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.zhy.http.okhttp.OkHttpUtils;

import butterknife.ButterKnife;

/**
 * Created by admin on 2018/12/21.
 */

public abstract class BaseActivity extends Activity {

    protected SharedPreferences sp;
    protected YhApplicationA app;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        ButterKnife.bind(this);
        initWindow();


        //强制竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        sp = getSharedPreferences("Acitivity", Activity.MODE_PRIVATE);;
        app = (YhApplicationA) getApplication();

    }

    protected abstract int getContentView();

    @TargetApi(19)
    protected void initWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setNavigationBarTintEnabled(false);
            tintManager.setStatusBarTintColor(Color.WHITE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(this);
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
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    protected void showToast(int msg) {
        showToast(getString(msg));
    }
}
