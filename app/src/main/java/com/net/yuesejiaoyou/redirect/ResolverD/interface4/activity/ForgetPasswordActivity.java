package com.net.yuesejiaoyou.redirect.ResolverD.interface4.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.widget.CountDownTimerUtils;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.BaseActivity;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.URL;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.DialogCallback;

import org.json.JSONException;
import org.json.JSONObject;


import butterknife.OnClick;
import okhttp3.Call;


/**
 * Created by Administrator on 2018/3/22.
 */

public class ForgetPasswordActivity extends BaseActivity {
    private TextView get_code;
    private EditText code2, edit1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        get_code = (TextView) findViewById(R.id.get_code);
        code2 = (EditText) findViewById(R.id.code);
        edit1 = (EditText) findViewById(R.id.phonenum);
    }

    @Override
    protected int getContentView() {
        return R.layout.vchat_forgetpassword;
    }

    @OnClick(R.id.get_code)
    public void codeClick() {
        String phoneStr = edit1.getText().toString().trim();
        if (TextUtils.isEmpty(phoneStr) || phoneStr.length() != 11 || !phoneStr.startsWith("1")) {
            showToast("请输入有效的手机号码");
            return;
        }

        OkHttpUtils.post(this)
                .url(URL.URL_GETCODE)
                .addParams("param1", "1")
                .addParams("param2", phoneStr)
                .build()
                .execute(new DialogCallback(this) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        showToast("网络异常,请重试！");
                        code2.setClickable(true);
                    }

                    @Override
                    public void onResponse(String resultBean, int id) {
                        if (TextUtils.isEmpty(resultBean)) {
                            code2.setClickable(true);
                            return;
                        }
                        try {
                            JSONObject jsonObject1 = new JSONObject(resultBean);
                            code = jsonObject1.getString("random");
                            if (TextUtils.isEmpty(code)) {
                                showToast("获取验证码失败");
                            } else if (code.equals("-1")) {
                                showToast("此手机号码尚未注册");
                            } else {
                                showToast("发送成功");
                                CountDownTimerUtils countDownTimer = new CountDownTimerUtils(get_code, 60000, 1000);
                                countDownTimer.start();
                            }

                        } catch (JSONException e) {
                            showToast("获取验证码失败");
                            code2.setClickable(true);
                        }
                    }
                });

        code2.setClickable(false);
    }

    @OnClick(R.id.back1)
    public void backClick(){
        finish();
    }

    @OnClick(R.id.sign)
    public void commitClick(){
        String phoneStr = edit1.getText().toString().trim();
        if (TextUtils.isEmpty(phoneStr) || phoneStr.length() != 11 || !phoneStr.startsWith("1")) {
            showToast("请输入有效的手机号码");
            return;
        }

        if (code.equals("0")) {
            return;
        }
        if (!code.equals(code2.getText().toString())) {
            showToast("请输入验证码");
            return;
        }
        Intent intent = new Intent();
        intent.setClass(ForgetPasswordActivity.this, ModifyPasswordActivity.class);
        intent.putExtra("zhanghao", phoneStr);
        startActivity(intent);
        finish();
    }

    String code = "0";

}
