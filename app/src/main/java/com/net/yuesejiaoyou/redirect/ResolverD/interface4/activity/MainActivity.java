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