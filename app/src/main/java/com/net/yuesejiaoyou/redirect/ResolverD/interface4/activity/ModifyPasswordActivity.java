package com.net.yuesejiaoyou.redirect.ResolverD.interface4.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.BaseActivity;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.URL;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.DialogCallback;

import butterknife.OnClick;
import okhttp3.Call;


/**
 * Created by Administrator on 2018/3/22.
 */

public class ModifyPasswordActivity extends BaseActivity {
    private EditText new_phonenum, edit1;
    String zhanghao = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        edit1 = (EditText) findViewById(R.id.phonenum);
        new_phonenum = (EditText) findViewById(R.id.new_phonenum);

        zhanghao = getIntent().getStringExtra("zhanghao");

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_modifypassword;
    }


    @OnClick(R.id.back1)
    public void backClick() {
        finish();
    }

    @OnClick(R.id.sign)
    public void commitClick() {
        String str = new_phonenum.getText().toString();
        String str1 = edit1.getText().toString();
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str1)) {
            Toast.makeText(ModifyPasswordActivity.this, "密码不能为空", Toast.LENGTH_LONG).show();
            return;
        }
        if (!str.equals(str1)) {
            Toast.makeText(ModifyPasswordActivity.this, "两个密码不一致", Toast.LENGTH_LONG).show();
            return;
        }


        OkHttpUtils.post(this)
                .url(URL.URL_RESETPSD)
                .addParams("param1", "1")
                .addParams("param2", str)
                .addParams("param3", zhanghao)
                .build()
                .execute(new DialogCallback(this) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        showToast("网络异常,请重试！");
                    }

                    @Override
                    public void onResponse(String resultBean, int id) {
                        if (resultBean.contains("success")) {
                            showToast("更改密码成功");
                            sp.edit().putString("password", new_phonenum.getText().toString().trim()).apply();
                            finish();
                        } else {
                            showToast("更改密码失败");
                        }
                    }
                });
    }

}
