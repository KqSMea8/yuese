package com.net.yuesejiaoyou.redirect.ResolverD.interface4.activity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.bean.Session;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.db.Const;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.db.SessionDao;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.redirect.ResolverA.interface4.utils.AutoMessage;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.fragment.DynamicFragment;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.fragment.MessageFragment;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.fragment.MineFragment;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.fragment.UserFragment;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.fragment.ZhuboFragment;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.im.IMManager;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.BaseActivity;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.URL;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.utils.UpdateManager;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.DialogCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import okhttp3.Call;


@SuppressLint("ResourceAsColor")
public class MainActivity extends BaseActivity {

    private MsgOperReciver msgOperReciver;

    private UserFragment userFragment;
    private DynamicFragment dynamicFragment;
    private MessageFragment messageFragment;
    private ZhuboFragment f4;
    private MineFragment f5;
    Fragment curFragment = null;


    @BindView(R.id.shipinimg)
    ImageView ivFirst;
    @BindView(R.id.guangchangimg)
    ImageView ivVideo;
    @BindView(R.id.xiaoxiimg)
    ImageView ivMessage;
    @BindView(R.id.wodeimg)
    ImageView ivMine;
    @BindView(R.id.hd)
    ImageView ivNew;
    @BindView(R.id.fl_content)
    FrameLayout flContent;
    private PopupWindow popupWindow;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!Util.iszhubo.equals("1") && !"40".equals(Util.userid) && !"0".equals(Util.userid)) {
            AutoMessage.startAutoMessage(this);
        }

//        if (Util.userid.equals("0")) {
//            sharedPreferences = getSharedPreferences("Acitivity", Context.MODE_PRIVATE);
//
//            // 自动登录
//            String logintype = sharedPreferences.getString("logintype", "");
//
//            switch (logintype) {
//                case "phonenum": {    // 手机号码自动登录
//                    String username = sharedPreferences.getString("username", "");
//                    String password = sharedPreferences.getString("password", "");
//
//                    // 如果手机账号密码为空则清理自动登录功能，并跳转到登录页面
//                    if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
//
//                        gotoLogin();
//                        break;
//                    }
//
//                    login(username, password);
//                    break;
//                }
//                case "wx": {
//                    openid = sharedPreferences.getString("openid", "");
//                    if ("".equals(openid)) {
//                        Log.e("TT", "openid is empty");
//                        break;
//                    }
//                    wxLogin(openid);
//                    break;
//                }
//            }
//        }


        msgOperReciver = new MsgOperReciver();
        IntentFilter intentFilter1 = new IntentFilter(Const.ACTION_MSG_OPER);
        registerReceiver(msgOperReciver, intentFilter1);

        changeSelect(0);
        new UpdateManager(this).checkUpdate();
    }

    @Override
    protected void onResume() {
        super.onResume();
        IMManager.clientHeartbeat();
        showPopupspWindowEveryday();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    public void showPopupspWindowEveryday() {
        String getday = sp.getString("today", "");
        String curday = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        if (curday.equals(getday)) {
            return;
        }
        sp.edit().putString("today", curday).apply();
        View layout = LayoutInflater.from(this).inflate(R.layout.popup_everyday, null);

        layout.findViewById(R.id.img_ad).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ShareActivity.class);
                startActivity(intent);
                popupWindow.dismiss();
            }
        });

        popupWindow = new PopupWindow(layout, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        // 控制键盘是否可以获得焦点
        popupWindow.setFocusable(true);
        backgroundAlpha(0.5f);
        popupWindow.setBackgroundDrawable(new BitmapDrawable(null, ""));
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER_VERTICAL, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });

        ImageView imgClose = layout.findViewById(R.id.img_close);
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
    }

    public void changeSelect(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (position) {
            case 0:
                if (curFragment != null && curFragment == userFragment) {
                    return;
                }
                ivFirst.setImageResource(R.drawable.home2);
                ivVideo.setImageResource(R.drawable.discover1);
                ivMessage.setImageResource(R.drawable.message1);
                ivMine.setImageResource(R.drawable.myself1);

                if (curFragment != null) {
                    transaction.hide(curFragment);
                }

                if (userFragment == null) {
                    userFragment = new UserFragment();
                    transaction.add(R.id.fl_content, userFragment);
                } else {
                    transaction.show(userFragment);
                }
                transaction.commit();
                curFragment = userFragment;
                break;
            case 1:
                if (curFragment != null && curFragment == dynamicFragment) {
                    return;
                }
                ivFirst.setImageResource(R.drawable.home1);
                ivVideo.setImageResource(R.drawable.discover2);
                ivMessage.setImageResource(R.drawable.message1);
                ivMine.setImageResource(R.drawable.myself1);
                if (curFragment != null) {
                    transaction.hide(curFragment);
                }
                if (dynamicFragment == null) {
                    dynamicFragment = new DynamicFragment();
                    transaction.add(R.id.fl_content, dynamicFragment);
                } else {
                    transaction.show(dynamicFragment);
                }
                transaction.commit();
                curFragment = dynamicFragment;
                break;
            case 2:
                if (curFragment != null && curFragment == messageFragment) {
                    return;
                }
                ivFirst.setImageResource(R.drawable.home1);
                ivVideo.setImageResource(R.drawable.discover1);
                ivMessage.setImageResource(R.drawable.message2);
                ivMine.setImageResource(R.drawable.myself1);
                if (curFragment != null) {
                    transaction.hide(curFragment);
                }
                if (messageFragment == null) {
                    messageFragment = new MessageFragment();
                    transaction.add(R.id.fl_content, messageFragment);
                } else {
                    transaction.show(messageFragment);
                }
                transaction.commit();
                curFragment = messageFragment;
                break;
            case 3:
                if (curFragment != null && (curFragment == f4 || curFragment == f5)) {
                    return;
                }
                ivFirst.setImageResource(R.drawable.home1);
                ivVideo.setImageResource(R.drawable.discover1);
                ivMessage.setImageResource(R.drawable.message1);
                ivMine.setImageResource(R.drawable.myself2);
                if (curFragment != null) {
                    transaction.hide(curFragment);
                }
                if (!Util.iszhubo.equals("0")) {
                    if (f4 == null) {
                        f4 = new ZhuboFragment();
                        transaction.add(R.id.fl_content, f4);
                    } else {
                        transaction.show(f4);
                    }
                    curFragment = f4;
                } else {
                    if (f5 == null) {
                        f5 = new MineFragment();
                        transaction.add(R.id.fl_content, f5);
                    } else {
                        transaction.show(f5);
                    }
                    curFragment = f5;
                }
                transaction.commit();
                break;
        }
    }

    private void login(final String usernem, final String password) {
        OkHttpUtils.post(this)
                .url(URL.URL_LOGIN)
                .addParams("param1", 1)
                .addParams("param2", usernem)
                .addParams("param3", password)
                .build()
                .execute(new DialogCallback(this) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        gotoLogin();
                    }

                    @Override
                    public void onResponse(String resultBean, int id) {
                        if (resultBean.contains("id")) {
                            try {
                                JSONArray jsonArray = new JSONArray(resultBean);
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                String a = jsonObject.getString("id");
                                LogDetect.send(LogDetect.DataType.basicType, "01162", a);
                                if (!a.equals("")) {
                                    String name = jsonObject.getString("nickname");
                                    String headpic = jsonObject.getString("photo");
                                    String gender = jsonObject.getString("gender");
                                    String zhubo = jsonObject.getString("is_v");

                                    sp.edit()
                                            .putString("logintype", "phonenum")
                                            .putString("username", usernem)
                                            .putString("password", password)
                                            .putString("userid", a)
                                            .putString("nickname", name)
                                            .putString("headpic", headpic)
                                            .putString("sex", gender)
                                            .putString("zhubo_bk", zhubo)
                                            .putBoolean("FIRST", false)
                                            .apply();
                                    Util.userid = a;
                                    LogDetect.send(LogDetect.DataType.basicType, "01162----启动页用户id", a);
                                    Util.headpic = headpic;
                                    Util.nickname = name;
                                    Util.iszhubo = zhubo;

                                    if (Util.imManager == null) {
                                        Util.imManager = new com.net.yuesejiaoyou.redirect.ResolverB.interface4.im.IMManager();
                                        Util.imManager.initIMManager(jsonObject, getApplicationContext());
                                    }

                                    JPushInterface.setAlias(getApplicationContext(), 1, Util.userid);    // 设置极光别名
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                });
    }

    private void wxLogin(String openid) {
        OkHttpUtils.post(this)
                .url(URL.URL_WXLOGIN1)
                .addParams("param1", "")
                .addParams("param2", openid)
                .build()
                .execute(new DialogCallback(this) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        gotoLogin();
                    }

                    @Override
                    public void onResponse(String resultBean, int id) {
                        if (!resultBean.equals("")) {
                            if (resultBean.contains("nickname")) {
                                try {
                                    //id,nickname,photo,gender
                                    JSONArray jsonArray = new JSONArray(resultBean);
                                    //JSONObject jsonObject = new JSONObject(json);
                                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                                    String a = jsonObject.getString("id");
                                    if (!a.equals("")) {
                                        if (jsonObject.getString("is_banned").equals("1")) {
                                            Toast.makeText(MainActivity.this, "您已被封禁", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                        String name = jsonObject.getString("nickname");
                                        String headpic = jsonObject.getString("photo");
                                        String gender = jsonObject.getString("gender");
                                        String zhubo = jsonObject.getString("is_v");
                                        String zh = jsonObject.getString("username");
                                        String pwd = jsonObject.getString("password");
                                        String wxopenid = jsonObject.getString("openid");
                                        //share = getSharedPreferences("Acitivity", Activity.MODE_PRIVATE);


                                        Util.userid = a;
                                        Util.headpic = headpic;
                                        Util.nickname = name;
                                        Util.is_agent = jsonObject.getString("is_agent");
                                        Util.iszhubo = zhubo.equals("0") ? "0" : "1";

                                        ////////建立跟openfire的关系
                                        if (Util.imManager == null) {
                                            Util.imManager = new com.net.yuesejiaoyou.redirect.ResolverB.interface4.im.IMManager();
                                            Util.imManager.initIMManager(jsonObject, getApplicationContext());
                                        }
                                        JPushInterface.setAlias(getApplicationContext(), 1, Util.userid);    // 设置极光别名
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                    }
                });
    }

    private void gotoLogin() {
        sp.edit().putString("logintype", "").apply();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }


    @OnClick(R.id.shipinimg)
    public void firstClick() {
        changeSelect(0);

    }

    @OnClick(R.id.guangchangimg)
    public void videoClick() {
        changeSelect(1);
    }

    @OnClick(R.id.xiaoxiimg)
    public void messageClick() {
        changeSelect(2);
    }

    @OnClick(R.id.wodeimg)
    public void mineClick() {
        changeSelect(3);
    }


    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                moveTaskToBack(true);
                return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    /**
     * 接收消息广播，更新未读消息条数
     *
     * @author Administrator
     */
    private class MsgOperReciver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            setMsgCnt();
        }
    }

    /**
     * 计算未读消息条数
     */
    public void setMsgCnt() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!"0".equals(Util.userid)) {
                    SessionDao sessionDao = new SessionDao(MainActivity.this);
                    List<Session> sessionList = sessionDao.queryAllSessions(Util.userid);

                    int total = 0;
                    for (Session session : sessionList) {
                        total += Integer.parseInt(session.getNotReadCount());
                    }
                    if (total <= 0) {
                        ivNew.setVisibility(View.GONE);

                    } else {
                        ivNew.setVisibility(View.VISIBLE);
                    }
                } else {
                    ivNew.setVisibility(View.GONE);
                }
            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(msgOperReciver);
    }
}