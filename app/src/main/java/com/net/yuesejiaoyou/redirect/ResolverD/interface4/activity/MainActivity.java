package com.net.yuesejiaoyou.redirect.ResolverD.interface4.activity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.net.yuesejiaoyou.R;
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

    private Handler handler = new Handler();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!Util.iszhubo.equals("1") && !"40".equals(Util.userid) && !"0".equals(Util.userid)) {
            AutoMessage.startAutoMessage(this);
        }


        msgOperReciver = new MsgOperReciver();
        IntentFilter intentFilter1 = new IntentFilter(Const.ACTION_MSG_OPER);
        registerReceiver(msgOperReciver, intentFilter1);


        new UpdateManager(this).checkUpdate();

        if (Util.userid.equals("0") || TextUtils.isEmpty(Util.userid)) {
            String username = sp.getString("username", "");
            String password = sp.getString("password", "");
            String openid = sp.getString("openid", "1");
            if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
                login(username, password);
            } else if (!TextUtils.isEmpty(openid) && !openid.equals("1")) {
                weixinLogin(openid, "", "", "");
            }
        } else {
            changeSelect(0);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        IMManager.clientHeartbeat();

//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                showPopupspWindowEveryday();
//            }
//        }, 1000);

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
                        f4.getData();
                    }
                    curFragment = f4;
                } else {
                    if (f5 == null) {
                        f5 = new MineFragment();
                        transaction.add(R.id.fl_content, f5);
                    } else {
                        transaction.show(f5);
                        f5.getData();
                    }
                    curFragment = f5;
                }
                transaction.commit();
                break;
        }
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
                        finish();
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
                .addParams("param6", "")
                .addParams("param7", "")
                .build()
                .execute(new DialogCallback(this) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        showToast("网络异常");
                        finish();
                    }

                    @Override
                    public void onResponse(String resultBean, int id) {
                        dealResult(resultBean);
                    }
                });
    }

    private void dealResult(String result) {
        if (TextUtils.isEmpty(result)) {
            finish();
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(result);
            if (jsonObject.getString("0").equals("已封号")) {
                JPushInterface.setAlias(getApplicationContext(), 1, "0");    // 设置极光别名
                showToast("您的账号出现违规操作已被冻结，有问题请联系客服");
                finish();
                return;
            }
        } catch (JSONException e) {
            finish();
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
                        .putString("openid", wxopenid)
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

                changeSelect(0);
            } else {
                showToast("请检查手机号或密码");
                finish();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            showToast("请检查手机号或密码");
            finish();
        }

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