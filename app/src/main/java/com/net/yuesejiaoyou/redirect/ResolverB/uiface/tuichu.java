package com.net.yuesejiaoyou.redirect.ResolverB.uiface;

import java.text.SimpleDateFormat;

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

import cn.jpush.android.api.JPushInterface;


public class tuichu extends Activity {

	private TextView exit_del,exit_queding,user_exit;
	private SimpleDateFormat sd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.downnotice);
		JPushInterface.setAlias(getApplicationContext(), 1, "0");	// 设置极光别名

		user_exit = (TextView) findViewById(R.id.exit_jieshao);
	/*	user_exit.setText( Html.fromHtml("您的账号于"+ Utils.downtime+" If\n" +
				"it is not operated by yourself, you’re suggested to reset password"));*/
		user_exit.setText( Html.fromHtml("您的账号于"+ Utils.downtime+"在别处登录！如非本人操作，建议您："+"<a href=\"\">重置密码</a>。 " ));

		
		exit_queding = (TextView) findViewById(R.id.exit_login);
		exit_queding.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {

				SharedPreferences share =getSharedPreferences("Acitivity",
						Activity.MODE_PRIVATE);
				share.edit().putString("logintype","").commit();

				Intent intent1=new Intent();
				intent1.setClass(tuichu.this,LoginActivity.class );//复用入口
				startActivity(intent1);
				finish();
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
				intent1.setClass(tuichu.this,LoginActivity.class );//复用入口
				startActivity(intent1);
				finish();
				//android.os.Process.killProcess(android.os.Process.myPid());
		       }
	        });
		LogDetect.send(LogDetect.DataType.specialType, Util.userid
				+ "=phone=" + Utils.android, "onDestroy");

		SharedPreferences share =this.getSharedPreferences("Acitivity",
				Activity.MODE_PRIVATE);
	/*	share.edit().putString("id", "").commit();
		share.edit().putString("username", "").commit();
		share.edit().putString("password","")
		share.edit().putString("photo","").commit();*/
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
		}).start();
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
