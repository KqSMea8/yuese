package com.net.yuesejiaoyou.redirect.ResolverB.uiface;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.vliao.R;
//import com.example.vliao.interface3.UsersThread_01158;
//import com.example.vliao.interface4.LogDetect;
//import com.example.vliao.util.AgoraVideoManager;
//import com.example.vliao.util.Util;
//import com.lazysellers.sellers.core.Utils;
//import com.lazysellers.sellers.infocenter.db.Const;
//import com.lazysellers.sellers.infocenter.hengexa1.smack.XMPPException;
//import com.lazysellers.sellers.infocenter.hengexa2.smack.SmackException;
import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.classroot.interface4.openfire.core.Utils;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.db.Const;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa1.smack.XMPPException;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack.SmackException;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.redirect.ResolverB.interface3.UsersThread_01158B;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.util.AgoraVideoManager;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.SimpleDateFormat;
import java.util.Date;


public class zhubo extends Activity {
	private RelativeLayout layout;
	private LinearLayout exit_del,exit_queding;
	private SimpleDateFormat sd;
	private TextView user_exit;
	private String msgBody;
	public String yid,msgbody;
	private ImageView photo;
	private TextView nickname;
	private DisplayImageOptions options=null;
	private String headpic;

	private boolean isAccept = false;	// 是否已经接通
	private boolean isThreadStop = false;	// 是否停止计时线程
	private Thread timerThread;




	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		startCallingTimer();
		setContentView(R.layout.tonghuabohao_01146);
		//--------------10000-------------------

		getWindow().addFlags(
				//WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|               //这个在锁屏状态下
						WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
						| WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
						| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);



	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
				case 260:
					Toast.makeText(zhubo.this, "对方已挂断", Toast.LENGTH_SHORT).show();
					//--------------------158---------------------
					//关闭音乐
					MusicUtil.stopPlay();
					//--------------------158---------------------
					finish();
					break;
				case 200:

					LogDetect.send(LogDetect.DataType.specialType,"01160 改为在线:","");
					String mode1 = "mod_online1";
					String[] paramsMap1 = {Util.userid, yid,"0","1"};
					UsersThread_01158B a = new UsersThread_01158B(mode1,paramsMap1,handler);
					Thread c = new Thread(a.runnable);
					c.start();
					break;
				case 201:
					msgBody=(String) msg.obj;
					if(!msgBody.equals("")){
						isAccept = true;
						String[] roid=msgBody.split(Const.SPLIT);
						//Toast.makeText(zhubo_bk.this, "对方已挂断", Toast.LENGTH_SHORT).show();
						//-----------------------------------------------------------------------------------------------
						//Intent intent = new Intent(zhubo_bk.this, VideoChatViewActivity_zhubo.class);
						//Intent intent = new Intent(zhubo_bk.this, AgoraChatActivity_zhubo.class);
						Intent intent = new Intent(zhubo.this, AgoraRtcActivity_zhubo.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
						intent.putExtra("roomid",roid[2]);
						intent.putExtra("yid_zhubo",yid);
						intent.putExtra("guke_name",nickname.getText().toString());
						intent.putExtra("guke_pic",headpic);
						startActivity(intent);
						//-----------------------------------------------------------------------------------------------
//						Intent intent = new Intent(zhubo_bk.this, LiveRoomActivity_zhubo.class);
//						intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//						intent.putExtra("roomid",roid[2]);
//						intent.putExtra("yid_zhubo",yid);
//						intent.putExtra("guke_name",nickname.getText().toString());
//						intent.putExtra("headpic",headpic);
//						intent.putExtra("type","zhubo_bk");
//						startActivity(intent);
						//-----------------------------------------------------------------------------------------------
						finish();
						//--------------------158---------------------
						//关闭音乐
						MusicUtil.stopPlay();
						//--------------------158---------------------
						finish();
					}else{
//						Toast.makeText(zhubo_bk.this,"网络异常,请稍后重试",Toast.LENGTH_SHORT).show();
					}
					break;
			}
		}
	};

	//连点两次退出
	private long firstTime = 0;
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
			case KeyEvent.KEYCODE_BACK:
				long secondTime = System.currentTimeMillis();
				if (secondTime - firstTime > 2000) {
					Toast.makeText(this, "再按一次可以挂断", Toast.LENGTH_SHORT).show();
					firstTime = secondTime;// 更新firstTime
				} else {// 两次按键小于2秒时，退出应用
					SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					final String message1 = "拒0绝1邀2请" + Const.SPLIT + Const.ACTION_MSG_ONEINVITE
							+ Const.SPLIT + sd.format(new Date()) + Const.SPLIT + Util.nickname + Const.SPLIT + Util.headpic;
					//LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"message: "+message);
					new Thread(new Runnable() {
						@Override
						public void run() {
							try {
								//LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"before sendMessage()");
								Utils.sendmessage(Utils.xmppConnection, message1, yid);
							} catch (XMPPException | SmackException.NotConnectedException e) {
								e.printStackTrace();
								Looper.prepare();
								Looper.loop();
							}
						}
					}).start();
					stopCallingTimer();
					String mode1 = "modezhubostate";
					String[] paramsMap1 = {Util.userid,yid};
					LogDetect.send(LogDetect.DataType.specialType,"01160 主播改变在线状态 video:",Util.userid);
					UsersThread_01158B a = new UsersThread_01158B(mode1,paramsMap1,handler);
					Thread c = new Thread(a.runnable);
					c.start();
					String mode2 = "removep2pvideo";
					String[] paramsMap2 = {"",AgoraVideoManager.getCurRoomid()};
					UsersThread_01158B a2 = new UsersThread_01158B(mode2,paramsMap2,handler);
					Thread c2 = new Thread(a2.runnable);
					c2.start();

					AgoraVideoManager.close();
					handler.sendMessage(handler.obtainMessage(200, (Object)msgBody));
				}
				return true;
		}
		return super.onKeyUp(keyCode, event);
	}

	@Override
	protected void onStop() {
		super.onStop();

		//MusicUtil.stopPlay();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		//stopCallingTimer();
	}

	/**
	 * 待接通倒计时，如果超时则退出呼叫
	 * 关闭声网预接通，关闭页面，向对方发送挂断消息，提示对方也做挂断处理
	 *
	 * 为了防止顾客端超时的时候碰巧主播端做了接听操作，所以把顾客端超时时间设置为25秒，主播端超时时间设置为20秒
	 */
	private void startCallingTimer() {

		isThreadStop = false;
		timerThread = new Thread(new Runnable() {

			private int maxTime = 60;
			private int curTime = 0;
			@Override
			public void run() {

				while(isThreadStop == false && curTime < maxTime) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					curTime++;
					if(isAccept) {
						return;
					}
				}
				if(isThreadStop == false && isAccept == false) {
					MusicUtil.stopPlay();

					SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					final String message1 = "拒0绝1邀2请" + Const.SPLIT + Const.ACTION_MSG_ONEINVITE
							+ Const.SPLIT + sd.format(new Date()) + Const.SPLIT + Util.nickname + Const.SPLIT + Util.headpic;
					//LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"message: "+message);
					new Thread(new Runnable() {
						@Override
						public void run() {
							try {
								//LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"before sendMessage()");
								Utils.sendmessage(Utils.xmppConnection, message1, yid);
							} catch (XMPPException | SmackException.NotConnectedException e) {
								e.printStackTrace();
								Looper.prepare();
								Looper.loop();
							}
						}
					}).start();

					//-----------------------------------
					String mode1 = "modezhubostate";
					String[] paramsMap1 = {Util.userid,yid};
					LogDetect.send(LogDetect.DataType.specialType,"01160 主播改变在线状态 video:",Util.userid);
					UsersThread_01158B a = new UsersThread_01158B(mode1,paramsMap1,handler);
					Thread c = new Thread(a.runnable);
					c.start();
					//-----------------------------------

					AgoraVideoManager.close();
					finish();
				}
			}
		});
		timerThread.start();
	}

	private void stopCallingTimer() {
		isThreadStop = true;
	}
}
