package com.net.yuesejiaoyou.redirect.ResolverB.uiface;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.vliao.R;
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


public class zhubo_01160 extends Activity {
	private RelativeLayout layout;
	private TextView exit_queding,user_exit;
	private SimpleDateFormat sd;
	public String yid,msgbody;
	private String msgBody;
	private LinearLayout exit_del;
	private DisplayImageOptions options=null;
	private ImageView photo;
	private TextView nickname;
	private String headpic;
	private String zhuboRoomId = "";

	private String roomid;

	private boolean isAccept = false;	// 是否已经接通
	private boolean isThreadStop = false;	// 是否停止计时线程
	private Thread timerThread;

	//主播打电话
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		startCallingTimer();
		setContentView(R.layout.yuyue_man_01160);
		//--------------158-------------------
		//开始播放音乐
		//--------------158-------------------
		roomid = getIntent().getStringExtra("roomid");
		AgoraVideoManager.startVideo(getApplication(), roomid, false);

		yid = getIntent().getStringExtra("yid_guke");
		msgbody= getIntent().getStringExtra("msgbody");
		Log.e("jj",yid+"++++++++++"+ msgbody);
		LogDetect.send(LogDetect.DataType.basicType,"顾客页面信息",msgbody);
		LogDetect.send(LogDetect.DataType.basicType,"01162","发送广播,允许点击");
		Intent intent66=new Intent("99");
		sendBroadcast(intent66);
		options=new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true).bitmapConfig(Bitmap.Config.RGB_565).build();
		String[] z = msgbody.split("卍");
		photo = (ImageView) findViewById(R.id.photo);
		nickname = (TextView) findViewById(R.id.nickname);
		String strphoto = z[4];
		headpic = strphoto;
		String str = strphoto.substring(0,1);
		if(str.equals("h")){
			ImageLoader.getInstance().displayImage(
					strphoto, photo,
					options);
		}else{
			ImageLoader.getInstance().displayImage(
					strphoto, photo,
					options);
		}
		nickname = (TextView) findViewById(R.id.nickname);
		nickname.setText(z[3]);
		exit_del = (LinearLayout) findViewById(R.id.exit_tuichu);

		exit_del.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				stopCallingTimer();
				stopCalling();
			}
		});

		MsgOperReciver msgOperReciver = new MsgOperReciver();
		IntentFilter intentFilter = new IntentFilter(Const.ACTION_MSG_ZB_RESERVE);
		registerReceiver(msgOperReciver, intentFilter);

		String mode1 = "mod_mang";
		String[] paramsMap1 = {Util.userid};
		UsersThread_01158B a = new UsersThread_01158B(mode1,paramsMap1,handler);
		Thread c = new Thread(a.runnable);
		c.start();
		//1有空  2再聊  3勿扰 0离线


		Intent intent = new Intent("isopen");// 一对一视频邀请
		Bundle bundle = new Bundle();
		bundle.putString("isopen","0");
		intent.putExtras(bundle);
		sendBroadcast(intent);



		MusicUtil.playSound(1,100);
	}

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
					stopCallingTimer();
					stopCalling();
				}
				return true;
		}
		return super.onKeyUp(keyCode, event);
	}

	private void stopCalling() {
		MusicUtil.stopPlay();

		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		final String message1 = "挂0断1邀2请" + Const.SPLIT + Const.ACTION_MSG_ZB_RESERVE
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
					//LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"chatmanager: "+e.toString());
					Looper.prepare();
					// ToastUtil.showShortToast(ChatActivity.this, "发送失败");
					Looper.loop();
				}
			}
		}).start();
		//--------------------158---------------------
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
		//--------------------158---------------------
		AgoraVideoManager.close();
		finish();
	}

	private class MsgOperReciver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {

			msgBody=intent.getStringExtra("oneuponeinvite");
			Log.e("jj","---------"+ msgBody);
			if (msgBody.contains("挂0断1邀2请")) {

			} else if (msgBody.contains("拒0绝1邀2请")) {
				stopCallingTimer();
				AgoraVideoManager.close();
				LogDetect.send(LogDetect.DataType.specialType,"01160 顾客挂断 invite:",msgBody);
				handler.sendMessage(handler.obtainMessage(302, (Object) msgBody));
			} else if (msgBody.contains("接0通1视2频")) {
				handler.sendMessage(handler.obtainMessage(303, (Object) msgBody));
			} else if (msgBody.contains("开0始1视2频")) {

			} else if (msgBody.contains("结0束1视2频")){

			}
			//handler.sendMessage(handler.obtainMessage(200, (Object) msgBody));

		}
	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {

				case 260:
			/*		Toast.makeText(zhubo_01160.this, "对方拒绝了你的邀请", Toast.LENGTH_SHORT).show();
					//--------------------158---------------------
					//关闭音乐
					MusicUtil.stopPlay();
					//--------------------158---------------------
					finish();*/
				break;
				case 302:
				/*	String mode1 = "mod_online";
					String[] paramsMap1 = {Util.userid,yid,"0","1"};
					LogDetect.send(LogDetect.DataType.specialType,"01160 改为在线:",yid);
					UsersThread_01158 a = new UsersThread_01158(mode1,paramsMap1,handler);
					Thread c = new Thread(a.runnable);
					c.start();*/

					MusicUtil.stopPlay();
					Toast.makeText(zhubo_01160.this, "对方已挂断", Toast.LENGTH_SHORT).show();

					sendBroadcast(new Intent("hang up"));

					finish();
					break;
				case 303:
					isAccept = true;
					String timestamp = new Date().getTime() + "";
					//SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					final String message1 = "开0始1视2频" + Const.SPLIT + Const.ACTION_MSG_ZB_RESERVE
							+ Const.SPLIT + timestamp + Const.SPLIT + Util.nickname + Const.SPLIT + Util.headpic;
					//LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"message: "+message);
					new Thread(new Runnable() {
						@Override
						public void run() {
							try {
								//LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"before sendMessage()");
								Utils.sendmessage(Utils.xmppConnection, message1, yid);
							} catch (XMPPException | SmackException.NotConnectedException e) {
								e.printStackTrace();
								//LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"chatmanager: "+e.toString());
								Looper.prepare();
								// ToastUtil.showShortToast(ChatActivity.this, "发送失败");
								Looper.loop();
							}
						}
					}).start();
					MusicUtil.stopPlay();
					//-----------------------------------
					//Intent intent = new Intent(zhubo_01160.this,VideoChatViewActivity_zhubo.class);
					Intent intent = new Intent(zhubo_01160.this,AgoraRtcActivity_zhubo.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					intent.putExtra("roomid",timestamp);
					intent.putExtra("yid_zhubo",yid);
					intent.putExtra("guke_name",nickname.getText().toString());
					intent.putExtra("guke_pic",headpic);
					intent.putExtra("status","yuyue");
					startActivity(intent);
					//--------------------------------------
//					Intent intent = new Intent(zhubo_01160.this, LiveRoomActivity_zhubo.class);
//					intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//					intent.putExtra("roomid", zhuboRoomId);	//timestamp);
//					intent.putExtra("yid_zhubo",yid);
//					intent.putExtra("guke_name",nickname.getText().toString());
//					intent.putExtra("headpic",headpic);
//					intent.putExtra("type","zhubo_bk");
//					startActivity(intent);
                    finish();
					break;
			}
		}
	};

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
					stopCalling();
				}
			}
		});
		timerThread.start();
	}

	private void stopCallingTimer() {
		isThreadStop = true;
	}
}
