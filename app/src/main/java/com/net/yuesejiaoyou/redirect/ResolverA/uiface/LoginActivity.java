package com.net.yuesejiaoyou.redirect.ResolverA.uiface;

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
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.LogUtil;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.URL;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.DialogCallback;

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


public class LoginActivity extends BaseActivity implements PlatformActionListener {


    @BindView(R.id.xieyi)
    TextView xieyi;

    String shareCode = "";
    String channelCode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        SpannableStringBuilder style = new SpannableStringBuilder(xieyi.getText().toString());
        style.setSpan(new ForegroundColorSpan(Color.YELLOW), 11, style.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        xieyi.setText(style);

        String username = sp.getString("username", "");
        String password = sp.getString("password", "");
        String logintype = sp.getString("logintype", "");
        String openid = sp.getString("openid", "1");
        LogUtil.i("ttt", "---username:" + username + "--password:" + password + "---openid:" + openid + "---logintype:" + logintype);
        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
            login(username, password);
        } else if (!TextUtils.isEmpty(openid) && !openid.equals("1")) {
            weixinLogin(openid, "", "", "");
        }

        OpenInstall.getInstall(new AppInstallAdapter() {
            @Override
            public void onInstall(AppData appData) {
                LoginActivity.this.channelCode = appData.getChannel();
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
    }

    @TargetApi(19)
    protected void initWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setNavigationBarTintEnabled(false);
            tintManager.setStatusBarTintColor(Color.TRANSPARENT);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
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
//                        if (!resultBean.equals("")) {
//                            try {
//                                JSONObject jsonObject = new JSONObject(resultBean);
//                                if (jsonObject.getString("0").equals("已封号")) {
//                                    JPushInterface.setAlias(getApplicationContext(), 1, "0");    // 设置极光别名
//                                    showToast("您的账号出现违规操作已被冻结，有问题请联系客服");
//                                    return;
//                                }
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                            if (resultBean.contains("id")) {
//                                try {
//                                    JSONArray jsonArray = new JSONArray(resultBean);
//                                    JSONObject jsonObject = jsonArray.getJSONObject(0);
//                                    String a = jsonObject.getString("id");
//                                    if (!a.equals("")) {
//                                        String name = jsonObject.getString("nickname");
//                                        String headpic = jsonObject.getString("photo");
//                                        String gender = jsonObject.getString("gender");
//                                        String zhubo = jsonObject.getString("is_v");
//                                        String zh = jsonObject.getString("username");
//                                        String pwd = jsonObject.getString("password");
//                                        String invite_num = jsonObject.getString("invite_num");
//                                        Util.invite_num = invite_num;
//                                        //share = getSharedPreferences("Acitivity", Activity.MODE_PRIVATE);
//                                        sp.edit()
//                                                .putString("logintype", "phonenum")
//                                                .putString("username", zh)
//                                                .putString("password", pwd)
//                                                .putString("userid", a)
//                                                .putString("nickname", name)
//                                                .putString("headpic", headpic)
//                                                .putString("sex", gender)
//                                                .putString("zhubo_bk", zhubo)
//                                                .putBoolean("FIRST", false).apply();
//                                        Util.userid = a;
//                                        Util.headpic = headpic;
//                                        Util.nickname = name;
//                                        Util.is_agent = jsonObject.getString("is_agent");
//                                        Util.iszhubo = zhubo.equals("0") ? "0" : "1";
//
//
//                                        /////////////////////给openfire通入信息，连接B区的opnefire实时通道。
//                                        if (Util.imManager == null) {
//                                            Util.imManager = new com.net.yuesejiaoyou.redirect.ResolverB.interface4.im.IMManager();
//                                            Util.imManager.initIMManager(jsonObject, getApplicationContext());
//                                        }
//
//                                        LogDetect.send(LogDetect.DataType.basicType, "01107", "before 绑定极光别名： " + Util.userid);
//                                        JPushInterface.setAlias(getApplicationContext(), 1, Util.userid);    // 设置极光别名
//                                        LogDetect.send(LogDetect.DataType.basicType, "01107", "after 绑定极光别名： " + Util.userid);
//
//                                        // 检查有没有一对一视频请求
//                                        Log.v("TT", "userid: " + jsonObject.get("gukeid"));
//                                        Log.v("TT", "name: " + jsonObject.getString("gukename"));
//                                        Log.v("TT", "pic: " + jsonObject.getString("gukepic"));
//                                        Log.v("TT", "roomid: " + jsonObject.getString("roomid"));
//
//                                        final String gukeid = jsonObject.get("gukeid").toString();
//
//                                        if (!"null".equals(gukeid)) {
//                                            if (Util.iszhubo.equals("0")) {
//                                                Log.v("TT", "触发登录后的一对一视频回拨");
//                                                if ("0".equals(zhubo) == false) {
//
//                                                }
//                                            } else {
//                                                Log.v("TT", "触发登录后的一对一视频回拨");
//                                            }
//                                        }
//
//                                        Intent intent = new Intent();
//                                        intent.setClass(LoginActivity.this, MainActivity.class);
//                                        startActivity(intent);
//                                        finish();
//                                    } else {
//                                        showToast("请检查手机号或密码");
//                                    }
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            } else {
//                                JPushInterface.setAlias(getApplicationContext(), 1, "0");    // 设置极光别名
//                                showToast("请检查手机号或密码");
//                            }
//                        } else {
//                            JPushInterface.setAlias(getApplicationContext(), 1, "0");    // 设置极光别名
//                            showToast("请检查手机号或密码");
//                        }
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
//                        if (resultBean.contains("nickname")) {
//                            try {
//                                JSONArray jsonArray = new JSONArray(resultBean);
//                                JSONObject jsonObject = jsonArray.getJSONObject(0);
//                                String a = jsonObject.getString("id");
//                                if (!a.equals("")) {
//                                    if (jsonObject.getString("is_banned").equals("1")) {
//                                        JPushInterface.setAlias(getApplicationContext(), 1, "0");    // 设置极光别名
//                                        showToast("您的账号出现违规操作已被冻结，有问题请联系客服.");
//                                        return;
//                                    }
//                                    String name = jsonObject.getString("nickname");
//                                    String headpic = jsonObject.getString("photo");
//                                    String gender = jsonObject.getString("gender");
//                                    String zhubo = jsonObject.getString("is_v");
//                                    String zh = jsonObject.getString("username");
//                                    String pwd = jsonObject.getString("password");
//                                    String wxopenid = jsonObject.getString("openid");
//                                    String invite_num = jsonObject.getString("invite_num");
//
//                                    String isreged = jsonObject.getString("isreged");
//                                    if (isreged.equals("1")) {
//                                        OpenInstall.reportRegister();
//                                    }
//
//                                    sp.edit()
//                                            .putString("logintype", "phonenum")
//                                            .putString("username", zh)
//                                            .putString("password", pwd)
//                                            .putString("userid", a)
//                                            .putString("nickname", name)
//                                            .putString("headpic", headpic)
//                                            .putString("sex", gender)
//                                            .putString("zhubo_bk", zhubo)
//                                            .putBoolean("FIRST", false).apply();
//
//                                    Util.userid = a;
//                                    Util.headpic = headpic;
//                                    Util.nickname = name;
//                                    Util.is_agent = jsonObject.getString("is_agent");
//                                    Util.iszhubo = zhubo.equals("0") ? "0" : "1";
//                                    if (Util.imManager == null) {
//                                        Util.imManager = new com.net.yuesejiaoyou.redirect.ResolverB.interface4.im.IMManager();
//                                        Util.imManager.initIMManager(jsonObject, getApplicationContext());
//                                    }
//                                    Util.invite_num = invite_num;
//                                    JPushInterface.setAlias(getApplicationContext(), 1, Util.userid);    // 设置极光别名
//
//                                    String gukeid = jsonObject.get("gukeid").toString();
//                                    String gukename = jsonObject.getString("gukename");
//                                    String gukepic = jsonObject.getString("gukepic");
//                                    String roomid = jsonObject.getString("roomid");
//
//                                    if (!"null".equals(gukeid)) {
//                                        if (Util.iszhubo.equals("0")) {
//                                            Log.v("TT", "触发登录后的一对一视频回拨");
//                                            String msgBody = "邀0请1视2频卍com.android.one.invite卍" + roomid + "卍" + gukename + "卍" + gukepic;
//                                            AgoraVideoManager.startVideo(getApplicationContext(), roomid, true, gukeid, msgBody, true);
//                                        } else {
//                                            Log.v("TT", "触发登录后的一对一视频回拨");
//                                            String msgBody = "邀0请1视2频卍com.android.one.invite卍" + roomid + "卍" + gukename + "卍" + gukepic;
//                                            AgoraVideoManager.startVideo(getApplicationContext(), roomid, true, gukeid, msgBody, false);
//                                        }
//                                    }
//
//                                    Intent intent = new Intent();
//                                    intent.setClass(LoginActivity.this, MainActivity.class);
//                                    startActivity(intent);
//                                    finish();
//                                }
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        } else {
//                            showToast("邀请码不正确");
//                        }

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
                        .putBoolean("FIRST", false).apply();
                Util.userid = a;
                Util.headpic = headpic;
                Util.nickname = name;
                Util.is_agent = jsonObject.getString("is_agent");
                Util.iszhubo = zhubo.equals("0") ? "0" : "1";


                /////////////////////给openfire通入信息，连接B区的opnefire实时通道。
                if (Util.imManager == null) {
                    Util.imManager = new com.net.yuesejiaoyou.redirect.ResolverB.interface4.im.IMManager();
                    Util.imManager.initIMManager(jsonObject, getApplicationContext());
                }

                LogDetect.send(LogDetect.DataType.basicType, "01107", "before 绑定极光别名： " + Util.userid);
                JPushInterface.setAlias(getApplicationContext(), 1, Util.userid);    // 设置极光别名
                LogDetect.send(LogDetect.DataType.basicType, "01107", "after 绑定极光别名： " + Util.userid);

                // 检查有没有一对一视频请求
                Log.v("TT", "userid: " + jsonObject.get("gukeid"));
                Log.v("TT", "name: " + jsonObject.getString("gukename"));
                Log.v("TT", "pic: " + jsonObject.getString("gukepic"));
                Log.v("TT", "roomid: " + jsonObject.getString("roomid"));

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
        LogUtil.i("ttt", "---" + hashMap);

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
}