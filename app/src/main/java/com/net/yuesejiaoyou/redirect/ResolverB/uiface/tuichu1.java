package com.net.yuesejiaoyou.redirect.ResolverB.uiface;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.classroot.interface4.openfire.core.Utils;
import com.net.yuesejiaoyou.activity.LoginActivity;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.ShareHelp;

import java.text.SimpleDateFormat;
import cn.jpush.android.api.JPushInterface;


public class tuichu1 extends Activity {

	private TextView exit_del,exit_queding,user_exit;
	private SimpleDateFormat sd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.downnotice);
		JPushInterface.setAlias(getApplicationContext(), 1, "0");	// 设置极光别名

		String status = getIntent().getStringExtra("status");


		user_exit = (TextView) findViewById(R.id.exit_jieshao);

		if(status.equals("1")){
			//认证成功
//			user_exit.setText( Html.fromHtml("您的主播认证已经成功"+ Utils.downtime+" 请\n" +
//					"重新登陆APP"));
			user_exit.setText(Html.fromHtml("欢迎入驻悦色平台！您的认证已经通过平台初步审核，请加客服微信:qumao518, 继续完成审核！"));
		}else {
			user_exit.setText( Html.fromHtml("您已被封号"+ Utils.downtime+" 将无\n" +
					"法登陆APP"));
		}

		exit_queding = (TextView) findViewById(R.id.exit_login);
		exit_queding.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {

				SharedPreferences share =getSharedPreferences("Acitivity",
						Activity.MODE_PRIVATE);
				share.edit().putString("logintype","").commit();

				Intent intent1=new Intent();
				intent1.setClass(tuichu1.this,LoginActivity.class );//复用入口
				startActivity(intent1);
		       }
	        });
		exit_del = (TextView) findViewById(R.id.exit_tuichu);
		exit_del.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				/*finish();
				new Thread(new Runnable() {
					@Override
					public void run() {

						System.exit(0);
					}
				}).start();*/

				SharedPreferences share =getSharedPreferences("Acitivity",
						Activity.MODE_PRIVATE);
				share.edit().putString("logintype","").commit();

				Intent intent1=new Intent();
				intent1.setClass(tuichu1.this,LoginActivity.class );//复用入口
				startActivity(intent1);
				//android.os.Process.killProcess(android.os.Process.myPid());
		       }
	        });
		LogDetect.send(LogDetect.DataType.specialType, Util.userid
				+ "=phone=" + Utils.android, "onDestroy");

		/*SharedPreferences share =this.getSharedPreferences("Acitivity",
				Activity.MODE_PRIVATE);
	*//*	share.edit().putString("id", "").commit();
		share.edit().putString("username", "").commit();
		share.edit().putString("password","")
		share.edit().putString("photo","").commit();*//*
		share.edit().putString("username", "").commit();
		share.edit().putString("password", "").commit();
		share.edit().putString("userid", "0").commit();
		share.edit().putString("nickname", "0").commit();
		share.edit().putString("headpic", "0").commit();
		share.edit().putString("sex", "0").commit();
		share.edit().putString("zhubo_bk", "0").commit();
		Util.imManager.xmppDisconnect();
		LogDetect.send(LogDetect.DataType.specialType, "share: ", share.getString("userid", ""));
		Util.userid="0";
		Util.iszhubo="0";


		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					if (Utils.xmppConnection.isConnected()) {
						//MsgService.getInstance().stopSelf();
						try {
							if (Utils.xmppConnection != null) {
								Utils.xmppConnection.disconnect();
								Utils.xmppConnection = null;
							}
							Utils.xmppchatmanager=null;
							Utils.xmppConnection=null;
						} catch (Exception e) {
							e.printStackTrace();
						}

					}
					
					
				} catch (Exception e) {

				}
			}
		}).start();*/

		SharedPreferences share =tuichu1.this.getSharedPreferences("Acitivity",
				Activity.MODE_PRIVATE);
		share.edit().putString("logintype", "").commit();
		share.edit().putString("openid", "").commit();
		share.edit().putString("username", "").commit();
		share.edit().putString("password", "").commit();
//				share.edit().putString("userid", "0").commit();
//				share.edit().putString("nickname", "0").commit();
//				share.edit().putString("headpic", "0").commit();
//				share.edit().putString("sex", "0").commit();
//				share.edit().putString("zhubo_bk", "0").commit();
		//share.edit().putBoolean("FIRST", true).commit();
		// 状态改成离线
//		String mode2 = "statuschange";
//		String[] paramsMap2 = {Util.userid,"0"};
//		UsersThread_01158B a2 = new UsersThread_01158B(mode2,paramsMap2,handler);
//		Thread c2 = new Thread(a2.runnable);
//		c2.start();

		JPushInterface.setAlias(tuichu1.this.getApplicationContext(), 1, "0");	// 退出登录后，撤销极光别名，就收不到一对一视频推送了
		ShareHelp share1 = new ShareHelp();
		share1.wx_delete();
		LogDetect.send(LogDetect.DataType.specialType, "share: ", share.getString("userid", ""));
		Util.userid="0";

		Util.imManager.xmppDisconnect();
	}
	

	
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			
			return true;
		}
		return super.onKeyUp(keyCode, event);
	}
	
}
