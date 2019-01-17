package com.net.yuesejiaoyou.redirect.ResolverD.interface4.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.fm.openinstall.OpenInstall;
import com.fm.openinstall.listener.AppInstallAdapter;
import com.fm.openinstall.model.AppData;
import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.widget.CountDownTimerUtils;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.BaseActivity;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.URL;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.DialogCallback;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import okhttp3.Call;

/**
 * Created by Administrator on 2018/3/22.
 */

public class RegisterActivity extends BaseActivity {
    private TextView get_code, xieyi;
    private EditText code2, edit1, edit2;

    private RadioButton maleButton, femalButton;

    private CountDownTimerUtils countDownTimer;


    String shareCode = "";
    String channelCode = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        get_code = (TextView) findViewById(R.id.get_code);
        code2 = (EditText) findViewById(R.id.code);
        edit1 = (EditText) findViewById(R.id.phonenum);
        edit2 = (EditText) findViewById(R.id.password);

        maleButton = (RadioButton) findViewById(R.id.radioMale);
        femalButton = (RadioButton) findViewById(R.id.radioFemale);


        xieyi = (TextView) findViewById(R.id.xieyi);

        SpannableStringBuilder style = new SpannableStringBuilder(xieyi.getText().toString());
        style.setSpan(new ForegroundColorSpan(Color.parseColor("#FD2F7A")), 7, style.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        xieyi.setText(style);

        OpenInstall.getInstall(new AppInstallAdapter() {
            @Override
            public void onInstall(AppData appData) {
                channelCode = appData.getChannel();
                String bindData = appData.getData();
                if (!appData.isEmpty()) {
                    if (bindData.contains("code")) {
                        net.sf.json.JSONObject jsonObj = net.sf.json.JSONObject.fromObject(bindData);
                        shareCode = jsonObj.getString("code");
                    }
                }
            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_register;
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
                            if (jsonObject1.has("random")) {
                                code = jsonObject1.getString("random");
                                if (!TextUtils.isEmpty(code)) {
                                    countDownTimer = new CountDownTimerUtils(get_code, 60000, 1000);
                                    countDownTimer.start();
                                    showToast("发送成功");
                                } else {
                                    showToast("获取验证码失败");
                                    code2.setClickable(true);
                                }
                            } else if (jsonObject1.has("result")) {
                                String reason = jsonObject1.getString("reason");
                                if ("limit".equals(reason)) {
                                    showToast("您今天接收短信次数已用完");
                                    code2.setClickable(true);
                                }
                            }
                        } catch (JSONException e) {
                            Toast.makeText(RegisterActivity.this, "获取验证码失败！", Toast.LENGTH_SHORT).show();
                            code2.setClickable(true);
                        }
                    }
                });

        code2.setClickable(false);
    }

    @OnClick(R.id.back1)
    public void backClick() {
        finish();
    }

    @OnClick(R.id.xieyi)
    public void xieyiClick() {
        startActivity(AgreementActivity.class);
    }

    @OnClick(R.id.sign)
    public void loginClick() {
        final String phoneStr = edit1.getText().toString().trim();
        if (TextUtils.isEmpty(phoneStr) || phoneStr.length() != 11 || !phoneStr.startsWith("1")) {
            showToast("请输入有效的手机号码");
            return;
        }

        final String passwordStr = edit2.getText().toString();
        if (TextUtils.isEmpty(passwordStr)) {
            showToast("请输入密码");
            return;
        }

        String codeStr = code2.getText().toString();
        if (TextUtils.isEmpty(codeStr)) {
            showToast("请输入验证码");
            return;
        }

        if (!code.equals(codeStr)) {
            showToast("验证码错误");
            return;
        }

        if (maleButton.isChecked() || femalButton.isChecked()) {
            String gender = "男";
            if (maleButton.isChecked()) {
                gender = "男";
            } else if (femalButton.isChecked()) {
                gender = "女";
            }

            OkHttpUtils.post(this)
                    .url(URL.URL_REGISTER)
                    .addParams("param1", 1)
                    .addParams("param2", phoneStr)
                    .addParams("param3", passwordStr)
                    .addParams("param4", shareCode)
                    .addParams("param5", channelCode)
                    .addParams("param6", "")
                    .addParams("param7", "")
                    .addParams("param8", gender)
                    .build()
                    .execute(new DialogCallback(this) {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            showToast("网络异常,请重试！");
                        }

                        @Override
                        public void onResponse(String resultBean, int id) {
                            if (TextUtils.isEmpty(resultBean)) {
                                showToast("注册失败");
                                return;
                            }
                            if (resultBean.contains("id")) {
                                OpenInstall.reportRegister();
                                try {
                                    JSONArray jsonArray = new JSONArray(resultBean);
                                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                                    String a = jsonObject.getString("id");
                                    String name = jsonObject.getString("nickname");
                                    String headpic = jsonObject.getString("photo");
                                    String gender = jsonObject.getString("gender");
                                    String zhubo = jsonObject.getString("is_v");
                                    String invite_num = jsonObject.getString("invite_num");
                                    Util.invite_num = invite_num;
                                    sp.edit().putString("username",phoneStr).apply();
                                    sp.edit().putString("password", passwordStr).apply();
                                    sp.edit().putString("userid", a).apply();
                                    sp.edit().putString("nickname", name).apply();
                                    sp.edit().putString("headpic", headpic).apply();
                                    sp.edit().putString("sex", gender).apply();
                                    sp.edit().putString("zhubo_bk", zhubo).apply();

                                    Util.userid = a;
                                    Util.headpic = headpic;
                                    Util.nickname = name;
                                    Util.iszhubo = zhubo;
                                    Util.is_agent = jsonObject.getString("is_agent");
                                    if (Util.imManager == null) {
                                        Util.imManager = new com.net.yuesejiaoyou.redirect.ResolverB.interface4.im.IMManager();
                                        Util.imManager.initIMManager(jsonObject, getApplicationContext());
                                    }

                                    JPushInterface.setAlias(getApplicationContext(), 1, Util.userid);    // 设置极光别名

                                    updatePlatform();

                                    Intent intent = new Intent();
                                    intent.setClass(RegisterActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            } else {
                                showToast("电话号码已注册");
                            }

                        }
                    });
        } else {
            showToast("请选择性别");
        }

    }

    private void updatePlatform(){
        OkHttpUtils.post(this)
                .url(URL.URL_UPDATEPLATFORM)
                .addParams("param1", 1)
                .addParams("param2", "android")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        showToast("网络异常,请重试！");
                    }

                    @Override
                    public void onResponse(String resultBean, int id) {

                    }
                });
    }


    String code = "0";
    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {

        String telRegex = "[1][3456789]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles))
            return false;
        else
            return mobiles.matches(telRegex);
    }


}
