package com.net.yuesejiaoyou.redirect.ResolverA.interface4.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Administrator on 2017/7/10.
 * 监听屏幕状态的广播
 */
//1个像素保活
public class OnePixelReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

//            if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {    //屏幕关闭启动1像素Activity
//                Intent it = new Intent(context, HooliganActivity.class);
//                it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(it);
//                LogDetect.send(LogDetect.DataType.basicType,"不休眠测试不休眠测试","-------------屏幕关闭启动1像素Activity");
//
//                /*Intent it1 = new Intent(context, zhubo_bk.class);
//                it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(it1);
//                LogDetect.send(LogDetect.DataType.basicType,"不休眠测试不休眠测试","-------------屏幕关闭启动1像素主播页面");*/
//
//
//            } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {   //屏幕打开 结束1像素
//                context.sendBroadcast(new Intent("finish123"));
//                Intent MainActivity = new Intent(Intent.ACTION_MAIN);
//                MainActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                MainActivity.addCategory(Intent.CATEGORY_HOME);
//                context.startActivity(MainActivity);
//                LogDetect.send(LogDetect.DataType.basicType,"不休眠测试不休眠测试","-------------//屏幕打开 结束1像素");
//            }else{
//
//            }
    }
}