package com.net.yuesejiaoyou.redirect.ResolverD.interface4.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.fm.openinstall.OpenInstall;
import com.fm.openinstall.listener.AppInstallAdapter;
import com.fm.openinstall.listener.AppWakeUpAdapter;
import com.fm.openinstall.model.AppData;
import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.BaseActivity;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.utils.LogUtil;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.URL;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.DialogCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;
import okhttp3.Call;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;


public class LoginActivity extends BaseActivity implements PlatformActionListener, EasyPermissions.PermissionCallbacks {

    private String[] permissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.RECORD_AUDIO
    };

    @BindView(R.id.xieyi)
    TextView xieyi;

    String shareCode = "";
    String channelCode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SpannableStringBuilder style = new SpannableStringBuilder(xieyi.getText().toString());
        style.setSpan(new ForegroundColorSpan(Color.YELLOW), 11, style.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
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

        OpenInstall.getWakeUp(getIntent(), wakeUpAdapter);

        if (EasyPermissions.hasPermissions(this, permissions)) {
            init();
        } else {
            EasyPermissions.requestPermissions(this, "需要使用如下权限", 101, permissions);
        }
    }

    private void init() {
        String username = sp.getString("username", "");
        String password = sp.getString("password", "");
        String openid = sp.getString("openid", "1");
        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
            login(username, password);
        } else if (!TextUtils.isEmpty(openid) && !openid.equals("1")) {
            weixinLogin(openid, "", "", "");
        }
    }


    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    public boolean statusBarFont() {
        return false;
    }

    @Override
    public int statusBarColor() {
        return R.color.transparent;
    }

    private void login(String usernem, String password) {
        OkHttpUtils.post(this)
                .url(URL.URL_LOGIN)
                .addParams("param1", 1)
                .addParams("param2", usernem)
                .addParams("param3", password)
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

    private void weixinLogin(String openid, String nickname, String head, String gender) {
        OkHttpUtils.post(this)
                .url(URL.URL_WXLOGIN)
                .addParams("param1", "")
                .addParams("param2", openid)
                .addParams("param3", nickname)
                .addParams("param4", head)
                .addParams("param5", gender)
                .addParams("param6", TextUtils.isEmpty(nickname) ? "" : shareCode)
                .addParams("param7", TextUtils.isEmpty(nickname) ? "" : channelCode)
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
                String wxopenid=jsonObject.getString("openid");
                String invite_num = jsonObject.getString("invite_num");
                Util.invite_num = invite_num;
                //share = getSharedPreferences("Acitivity", Activity.MODE_PRIVATE);
                sp.edit()
                        .putString("logintype", "phonenum")
                        .putString("username", zh)
                        .putString("password", pwd)
                        .putString("userid", a)
                        .putString("nickname", name)
                        .putString("headpic", headpic)
                        .putString("sex", gender)
                        .putString("zhubo_bk", zhubo)
                        .putString("openid",wxopenid)
                        .putBoolean("FIRST", false).apply();
                Util.userid = a;
                Util.headpic = headpic;
                Util.nickname = name;
                Util.is_agent = jsonObject.getString("is_agent");
                Util.iszhubo = zhubo.equals("0") ? "0" : "1";


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

                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, MainActivity.class);
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

    @OnClick(R.id.phonelogin)
    public void phoneClick() {
        Intent intent = new Intent();
        intent.setClass(LoginActivity.this, PhoneLoginActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.wc)
    public void wxClick() {
        Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
        wechat.setPlatformActionListener(this);
        wechat.authorize();
        wechat.showUser(null);
    }

    @OnClick(R.id.xieyi)
    public void xieyiClick() {
        Intent intent1 = new Intent();
        intent1.setClass(LoginActivity.this, AgreementActivity.class);
        startActivity(intent1);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // 此处要调用，否则App在后台运行时，会无法截获
        OpenInstall.getWakeUp(intent, wakeUpAdapter);
    }

    AppWakeUpAdapter wakeUpAdapter = new AppWakeUpAdapter() {
        @Override
        public void onWakeUp(AppData appData) {
            //获取渠道数据
            String channelCode = appData.getChannel();
            Util.channelcode = channelCode;
            //获取绑定数据
            String bindData = appData.getData();
            Log.d("OpenInstall", "getWakeUp : wakeupData = " + appData.toString());
            LogDetect.send(LogDetect.DataType.basicType, "wakeupData:", appData.toString());
            if (bindData.contains("code")) {
                net.sf.json.JSONObject jsonObj = net.sf.json.JSONObject.fromObject(bindData);
                Util.yqcode = jsonObj.getString("code");
            } else {
                Util.yqcode = "";
            }
            LogDetect.send(LogDetect.DataType.basicType, "OpenInstall:", Util.yqcode);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        wakeUpAdapter = null;
    }


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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if (perms.size() == permissions.length) {
            init();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        showToast("授权失败");
        init();
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }
}