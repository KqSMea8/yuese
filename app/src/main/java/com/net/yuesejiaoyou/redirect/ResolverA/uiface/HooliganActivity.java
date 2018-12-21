package com.net.yuesejiaoyou.redirect.ResolverA.uiface;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.redirect.ResolverB.uiface.MusicUtil;

//1个像素保活
public class HooliganActivity extends Activity {
    private String a1="";
    private String b1="";
    private String openid="";
    //private boolean isplay=false;
    private BroadcastReceiver endReceiver;
    private BroadcastReceiver startReceiver;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置1像素
        Window window = getWindow();
        window.setGravity(Gravity.LEFT | Gravity.TOP);
        WindowManager.LayoutParams params = window.getAttributes();
        params.x = 0;
        params.y = 0;
        params.height = 1;
        params.width = 1;
        window.setAttributes(params);
//////////////////////////////////////////////////////////////////////////////////
        //结束该页面的广播
        endReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
               /* if(isplay){
                    MusicUtil.stopPlay();
                    isplay=false;
                }*/
                LogDetect.send(LogDetect.DataType.basicType,"不休眠测试不休眠测试","-------------测试endReceiver");
                finish();
            }
        };
        registerReceiver(endReceiver, new IntentFilter("finish123"));
        //检查屏幕状态
        LogDetect.send(LogDetect.DataType.basicType,"不休眠测试不休眠测试","-------------测试checkScreen()");
        checkScreen();

////////////////////////////////////////////////////////////////////////////////
        //接收广播播放音乐 含有保活
        startReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //isplay=true;
                //MusicUtil.playSound(1,100);放在这里不播放
                mHandler.sendEmptyMessage(0);
                LogDetect.send(LogDetect.DataType.basicType,"不休眠测试不休眠测试","-------------测试MusicUtil.playSound1");

            }
        };
        registerReceiver(startReceiver, new IntentFilter("playmusic"));



        //cpuWakeLockThread();

    }

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    MusicUtil.playSound(1,100);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        checkScreen();
    }

    /**
     * 检查屏幕状态  isScreenOn为true  屏幕“亮”结束该Activity
     */
    private void checkScreen() {

        PowerManager pm = (PowerManager) HooliganActivity.this.getSystemService(Context.POWER_SERVICE);
        boolean isScreenOn = pm.isScreenOn();
        if (isScreenOn) {
            finish();
        }
    }

    //心跳登录
    private long heartbeat_bk = 0;
    private void remonitor() {
        sharedPreferences = getSharedPreferences("Acitivity",
                Context.MODE_PRIVATE);
        Log.v("TT","++remonitor(): heartbeat_bk="+heartbeat_bk);
        LogDetect.send(LogDetect.DataType.basicType,"HooliganActivity----原heartbeat_bk-----",heartbeat_bk);
        long heartbeat = sharedPreferences.getLong("heartbeat1",0);
        LogDetect.send(LogDetect.DataType.basicType,"HooliganActivity----新heartbeat_bk-----",heartbeat);
        Log.v("TT","++remonitor(): heartbeat="+heartbeat);
        // 第一次赋值
        if(heartbeat_bk == 0) {
            heartbeat_bk = heartbeat;
        } else {
            if(heartbeat <= heartbeat_bk) { // 判断断开连接
                Log.v("TT","nlogin: "+heartbeat+","+heartbeat_bk);
                LogDetect.send(LogDetect.DataType.basicType,"HooliganActivity----判断断开连接，重新登陆-----",heartbeat_bk);
                nlogin();   // 重新连接
            } else {    // 心跳值比上一次值大的话就更新
                LogDetect.send(LogDetect.DataType.basicType,"HooliganActivity----心跳值比上一次值大的话就更新-----",heartbeat_bk);
                heartbeat_bk = heartbeat;
            }
        }
        Log.v("TT","--remonitor()");
    }

    //心跳登录
    private void nlogin() {
        sharedPreferences = getSharedPreferences("Acitivity",
                Context.MODE_PRIVATE);
        // 自动登录
        String logintype = sharedPreferences.getString("logintype","");

        switch(logintype) {
            case "phonenum": {    // 手机号
                String username = sharedPreferences.getString("username","");
                String userid = sharedPreferences.getString("userid","");
                String headpic = sharedPreferences.getString("headpic","");
                String zhubo = sharedPreferences.getString("zhubo_bk","");

                Util.userid = userid;
                LogDetect.send(LogDetect.DataType.basicType,"HooliganActivity----屏保下保持联网phone-----",userid);
                Util.headpic = headpic;
                Util.nickname = username;
                Util.iszhubo = zhubo;

                ////////建立跟openfire的关系
                Util.imManager = new com.net.yuesejiaoyou.redirect.ResolverB.interface4.im.IMManager();
                Util.imManager.resetIMManager(userid,username,headpic, getApplicationContext());

                break;
            }
            case "wx": {            // 微信
                String username = sharedPreferences.getString("username","");
                String userid = sharedPreferences.getString("userid","");
                String headpic = sharedPreferences.getString("headpic","");
                String zhubo = sharedPreferences.getString("zhubo_bk","");

                Util.userid = userid;
                LogDetect.send(LogDetect.DataType.basicType,"HooliganActivity----屏保下保持联网wx-------",userid);
                Util.headpic = headpic;
                Util.nickname = username;
                Util.iszhubo = zhubo;

                ////////建立跟openfire的关系
                Util.imManager = new com.net.yuesejiaoyou.redirect.ResolverB.interface4.im.IMManager();
                Util.imManager.resetIMManager(userid,username,headpic, getApplicationContext());
                break;
            }
        }
    }



    // 延时函数
    static public void Sleep(int millisecond) {
        try {
            Thread.sleep(millisecond);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    //设置cpu永不休眠并写入cpu运行日志
    private void cpuWakeLockThread()
    {
        Thread mThread=new Thread(new Runnable() {

            @Override
            public void run() {
                //acquireWakeLock(MainActivity.this);
                while (true) {
                    LogDetect.send(LogDetect.DataType.basicType,"不休眠测试不休眠测试","-------------测试nlogin()nlogin()");
                    remonitor();
                    Sleep(50000);
                }

            }
        });
        mThread.start();
    }
}