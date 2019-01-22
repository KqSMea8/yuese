package com.net.yuesejiaoyou.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.fm.openinstall.OpenInstall;
import com.fm.openinstall.listener.AppInstallAdapter;
import com.fm.openinstall.model.AppData;
import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.BaseActivity;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.URL;
import com.umeng.analytics.MobclickAgent;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.DialogCallback;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;
import okhttp3.Call;

import static com.net.yuesejiaoyou.activity.RegisterActivity.isMobileNO;


/**
 * Created by Administrator on 2018/3/22.
 */

public class PhoneLoginActivity extends BaseActivity {


    @BindView(R.id.phonenum)
    EditText etPhone;
    @BindView(R.id.password)
    EditText etPassword;
    @BindView(R.id.sign)
    TextView sign;

    @BindView(R.id.xieyi)
    TextView tvXieyi;

    String shareCode = "";
    String channelCode = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        etPhone.addTextChangedListener(mTextWatcher);
        etPassword.addTextChangedListener(mTextWatcher);

        SpannableStringBuilder style = new SpannableStringBuilder(tvXieyi.getText().toString());
        style.setSpan(new ForegroundColorSpan(Color.parseColor("#FD2F7A")), 7, style.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvXieyi.setText(style);

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
        return R.layout.vchat_login_01162;
    }


    @OnClick(R.id.back1)
    public void backClick(){
        finish();
    }

    @OnClick(R.id.xieyi)
    public void xieyiClick() {
        Intent intent2 = new Intent();
        intent2.setClass(PhoneLoginActivity.this, AgreementActivity.class);
        startActivity(intent2);
    }

    @OnClick(R.id.wx)
    public void weixinClick() {
        Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
        wechat.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                String openid = (String) hashMap.get("openid");
                String nickname = (String) hashMap.get("nickname");
                String headimgurl = (String) hashMap.get("headimgurl");
                String gender = "男";
                String sex = Integer.parseInt(hashMap.get("sex").toString()) + "";
                if (sex.equals("2")) {
                    gender = "女";
                } else {
                    gender = "男";
                }

                weixinLogin(openid, nickname, headimgurl, gender);
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                throwable.printStackTrace();
                showToast("登录失败");
            }

            @Override
            public void onCancel(Platform platform, int i) {
                showToast("登录取消");
            }
        });
        wechat.authorize();
        wechat.showUser(null);
    }

    @OnClick(R.id.sign)
    public void loginClick() {
        String phoneStr = etPhone.getText().toString().trim();
        if (phoneStr.length() != 11 || !phoneStr.startsWith("1")) {
            showToast("请输入有效的手机号");
            return;
        }

        String passwordStr = etPassword.getText().toString().trim();
        if (TextUtils.isEmpty(passwordStr)) {
            showToast("请输入密码");
            return;
        }

        OkHttpUtils.post(this)
                .url(URL.URL_LOGIN)
                .addParams("param1", 1)
                .addParams("param2", phoneStr)
                .addParams("param3", passwordStr)
                .build()
                .execute(new DialogCallback(this) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        showToast("网络异常,请重试！");
                    }

                    @Override
                    public void onResponse(String resultBean, int id) {
                        dealResult(resultBean);
                    }
                });
    }

    @OnClick(R.id.forget)
    public void forgetClick(){
        startActivity(ForgetPasswordActivity.class);
    }

    private void weixinLogin(String openid, String nickname, String head, String gender) {
        OkHttpUtils.post(this)
                .url(URL.URL_WXLOGIN)
                .addParams("param1", "")
                .addParams("param2", openid)
                .addParams("param3", nickname)
                .addParams("param4", head)
                .addParams("param5", gender)
                .build()
                .execute(new DialogCallback(this) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        showToast("网络异常");
                    }

                    @Override
                    public void onResponse(String resultBean, int id) {
                        dealResult(resultBean);
                    }
                });
    }

    private void dealResult(String result) {
        if (TextUtils.isEmpty(result)) {
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(result);
            if (jsonObject.getString("0").equals("已封号")) {
                JPushInterface.setAlias(getApplicationContext(), 1, "0");    // 设置极光别名
                showToast("您的账号出现违规操作已被冻结，有问题请联系客服");
                return;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            JSONArray jsonArray = new JSONArray(result);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            String a = jsonObject.getString("id");
            if (!a.equals("")) {
                String name = jsonObject.getString("nickname");
                String headpic = jsonObject.getString("photo");
                String gender = jsonObject.getString("gender");
                String zhubo = jsonObject.getString("is_v");
                String zh = jsonObject.getString("username");
                String pwd = jsonObject.getString("password");
                String wxopenid = jsonObject.getString("openid");
                String invite_num = jsonObject.getString("invite_num");
                Util.invite_num = invite_num;
                sp.edit()
                        .putString("logintype", "phonenum")
                        .putString("username", zh)
                        .putString("password", pwd)
                        .putString("userid", a)
                        .putString("nickname", name)
                        .putString("headpic", headpic)
                        .putString("sex", gender)
                        .putString("zhubo_bk", zhubo)
                        .putString("openid", wxopenid)
                        .putBoolean("FIRST", false).apply();
                Util.userid = a;
                Util.headpic = headpic;
                Util.nickname = name;
                Util.is_agent = jsonObject.getString("is_agent");
                Util.iszhubo = zhubo.equals("0") ? "0" : "1";

                MobclickAgent.onProfileSignIn(a);


                if (Util.imManager == null) {
                    Util.imManager = new com.net.yuesejiaoyou.redirect.ResolverB.interface4.im.IMManager();
                    Util.imManager.initIMManager(jsonObject, getApplicationContext());
                }

                JPushInterface.setAlias(getApplicationContext(), 1, Util.userid);    // 设置极光别名

                final String gukeid = jsonObject.get("gukeid").toString();

                if (!"null".equals(gukeid)) {
                    if (Util.iszhubo.equals("0")) {
                        Log.v("TT", "触发登录后的一对一视频回拨");
                        if ("0".equals(zhubo) == false) {

                        }
                    } else {
                        Log.v("TT", "触发登录后的一对一视频回拨");
                    }
                }

                updatePlatform();

                Intent intent = new Intent();
                intent.setClass(PhoneLoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                showToast("请检查手机号或密码");
            }
        } catch (JSONException e) {
            e.printStackTrace();
            showToast("请检查手机号或密码");
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

    @OnClick(R.id.register)
    public void registerClick(){
        startActivity(RegisterActivity.class);
    }


    TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int arg1, int arg2, int arg3) {

        }

        @Override
        public void onTextChanged(CharSequence s, int arg1, int arg2,
                                  int arg3) {
            if (TextUtils.isEmpty(s)) {
                sign.setBackgroundResource(R.drawable.shape_login1_01160);
            } else {
                sign.setBackgroundResource(R.drawable.shape_login2_01160);
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

}
