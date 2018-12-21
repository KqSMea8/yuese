package com.net.yuesejiaoyou.redirect.ResolverA.uiface;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.core.YhApplicationA;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.bean.Session;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.db.Const;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.db.SessionDao;
import com.net.yuesejiaoyou.classroot.interface4.util.PlayerMusicService;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.redirect.ResolverA.interface3.UsersThread_01066A;
import com.net.yuesejiaoyou.redirect.ResolverA.interface3.UsersThread_01162A;
import com.net.yuesejiaoyou.redirect.ResolverA.interface4.receiver.OnePixelReceiver;
import com.net.yuesejiaoyou.redirect.ResolverA.interface4.receiver.ScreenReceiverUtil;
import com.net.yuesejiaoyou.redirect.ResolverA.interface4.utils.AutoMessage;
import com.net.yuesejiaoyou.redirect.ResolverA.interface4.utils.JobSchedulerManager;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.im.IMManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import cn.jpush.android.api.JPushInterface;
import crossoverone.statuslib.StatusUtil;



@SuppressLint("ResourceAsColor")
public class MainActivity extends FragmentActivity implements OnClickListener {
	private String a1="";
	private String b1="";
	private String openid="";
	private  boolean is_permit=false;
	Boolean userfirst;
	private String[] permissions = {Manifest.permission.DISABLE_KEYGUARD,Manifest.permission.RECORD_AUDIO,Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE
	};

	private MsgOperReciver msgOperReciver;
	private LinearLayout shipinliaotian,guangchang,wode;
	private PopupWindow popupWindow;
	SharedPreferences sharedPreferences;
	RelativeLayout xiaoxi;
	private ImageView shipinimg,guangchangimg,xiaoxiimg,wodeimg,hd;
	private FrameLayout orgParent;

	private VFragment1_01066 f1;
	private VFragment2_01066 f2;
	private VLFragment_News_01160 f3;
	private Fragment_zhubozhongxin_1152 f4;
	private vliao_Gerenzhongxin_1152 f5;


	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zp_dibudaohanglan);

		if(!Util.iszhubo.equals("1") && !"40".equals(Util.userid) && !"0".equals(Util.userid)){
			AutoMessage.startAutoMessage(this);
		}



		if(Util.userid.equals("0")){
			LogDetect.send(LogDetect.DataType.basicType,"01162","崩溃后的重启");
			sharedPreferences = getSharedPreferences("Acitivity",
					Context.MODE_PRIVATE);
			//thread.start();

			// 自动登录
			String logintype = sharedPreferences.getString("logintype","");

			switch(logintype) {
				case "phonenum": {    // 手机号码自动登录
					String username = sharedPreferences.getString("username","");
					String password = sharedPreferences.getString("password","");

					// 如果手机账号密码为空则清理自动登录功能，并跳转到登录页面
					if(username.isEmpty() || password.isEmpty()) {
						sharedPreferences.edit().putString("logintype", "").commit();
						Intent intent = new Intent(this, LoginActivity.class);
						startActivity(intent);
						finish();
						break;
					}

					String[] paramsMap = {"1", username, password};
					UsersThread_01066A b = new UsersThread_01066A("login", paramsMap, handler);
					Thread thread = new Thread(b.runnable);
					thread.start();
					break;
				}
				case "wx": {            // 微信自动登录
					String mode = "wxlogin_1";
					openid = sharedPreferences.getString("openid","");
					if("".equals(openid)) {
						Log.e("TT","openid is empty");
						break;
					}
					String[] params = {"", openid};
					UsersThread_01162A b = new UsersThread_01162A(mode, params, handler);
					Thread t = new Thread(b.runnable);
					t.start();
					break;
				}
			}
		}
		for(String permission: permissions) {
			// 检查该权限是否已经获取
			int i = ContextCompat.checkSelfPermission(MainActivity.this, permission);
			// 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
			if (i != PackageManager.PERMISSION_GRANTED) {
				// 如果没有授予该权限，就去提示用户请求
			//	showPopupspWindow_tuichu(guangchangimg);
				is_permit=true;
			}
		}
		orgParent = (FrameLayout)findViewById(R.id.fl_content);
		shipinliaotian = (LinearLayout) findViewById(R.id.shipinliaotian);
		shipinliaotian.setOnClickListener(this);
		shipinimg = (ImageView) findViewById(R.id.shipinliaotian).findViewById(R.id.shipinimg);
		guangchang = (LinearLayout) findViewById(R.id.guangchang);
		guangchang.setOnClickListener(this);
		guangchangimg = (ImageView) findViewById(R.id.guangchang).findViewById(R.id.guangchangimg);
		xiaoxi = (RelativeLayout) findViewById(R.id.xiaoxi);
		xiaoxi.setOnClickListener(this);

		xiaoxiimg = (ImageView) findViewById(R.id.xiaoxi).findViewById(R.id.xiaoxiimg);
		hd = (ImageView)findViewById(R.id.xiaoxi).findViewById(R.id.hd);
		wode = (LinearLayout) findViewById(R.id.wode);
		wode.setOnClickListener(this);
		wodeimg = (ImageView) findViewById(R.id.wode).findViewById(R.id.wodeimg);
		//LogDetect.send(LogDetect.DataType.basicType,"158","V聊探针开始了");
		//setMsgCnt();
		msgOperReciver = new MsgOperReciver();
		IntentFilter intentFilter1 = new IntentFilter(Const.ACTION_MSG_OPER);
		//IntentFilter intentFilter2 = new IntentFilter(Const.ACTION_ADDFRIEND);
		registerReceiver(msgOperReciver, intentFilter1);
		//registerReceiver(msgOperReciver, intentFilter2);
		shipinliaotian.performClick();

		new Handler().postDelayed(new Runnable() {
			   @Override
			   public void run() {
				if(is_permit){
					showPopupspWindow_tuichu(guangchangimg);
				}
			   }
		},500);

    ///////////////////////播放音乐保活
		//startPlayMusicService();
		//acquireWakeLock(this);
	////////////忽略电池优化
		//ignoreBatteryOptimization(this);
		//setStatusBarColor();
		//StatusBarLightMode(MainActivity.this);

		StatusUtil.setUseStatusBarColor(this, Color.TRANSPARENT, Color.parseColor("#7F7F7F"));
		StatusUtil.setSystemStatus(this, true, true);

		cpuWakeLockThread();
	}



	private void cpuWakeLockThread()
	{
		Thread mThread=new Thread(new Runnable() {

			@Override
			public void run() {
				//acquireWakeLock(MainActivity.this);
				while (true) {
					//LogDetect.send(LogDetect.DataType.basicType,"不休眠测试不休眠测试","-------------测试主播页面保活");
					Log.v("TTT","---- cpuWakeLockThread ----");
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					LogDetect.send("01107", "---------- Util.imManager= "+Util.imManager+" ----------");
					if(Util.imManager != null) {
						LogDetect.send("01107", "---------- before checkConnection ----------");
						Util.imManager.checkConnection();
						LogDetect.send("01107", "---------- after checkConnection ----------");
					}
				}

			}
		});
		mThread.start();
	}

/////////////////////////////////////////////////////////////////////
	/***************************************************************************
	 *重置所有文本的选中状态、底部导航栏图片、文字的选中状态
	 * @
	 *
	 ***************************************************************************/
    public void selected(){
		shipinliaotian.setSelected(false);
		shipinimg.setImageResource(R.drawable.home1);
		guangchang.setSelected(false);
		guangchangimg.setImageResource(R.drawable.discover1);
		xiaoxi.setSelected(false);
		xiaoxiimg.setImageResource(R.drawable.message1);
		wode.setSelected(false);
		wodeimg.setImageResource(R.drawable.myself1);
    }

	/***************************************************************************
	 *隐藏所有Fragment
	 * @
	 *
	 ***************************************************************************/
    public void hideAllFragment(FragmentTransaction transaction){
		if(f1!=null){
			transaction.hide(f1);
		}
		if(f2!=null){
			transaction.hide(f2);
		}
		if(f3!=null){
			transaction.hide(f3);
		}
		if(f4!=null){
			transaction.hide(f4);
		}
		if(f5!=null){
			transaction.hide(f5);
		}
    }

	///////////////	//隐藏所有Fragment
	/***************************************************************************
	 *
	 * @
	 *
	 ***************************************************************************/
    private Handler handler=new Handler(){
		@Override
		public void dispatchMessage(Message msg) {
			super.dispatchMessage(msg);
			switch (msg.what){
				case 200:
					String json = (String) msg.obj;
					if (json.contains("id")) {
						try {
							//id,nickname,photo,gender
							JSONArray jsonArray = new JSONArray(json);
							//JSONObject jsonObject = new JSONObject(json);
							JSONObject jsonObject = jsonArray.getJSONObject(0);
							String a = jsonObject.getString("id");
							LogDetect.send(LogDetect.DataType.basicType,"01162",a);
							if(!a.equals("")){
								String name = jsonObject.getString("nickname");
								String headpic = jsonObject.getString("photo");
								String gender = jsonObject.getString("gender");
								String zhubo = jsonObject.getString("is_v");
								SharedPreferences share = getSharedPreferences("Acitivity", Activity.MODE_PRIVATE);
								share.edit().putString("username",a1).commit();
								share.edit().putString("password", b1).commit();
								share.edit().putString("userid", a).commit();
								share.edit().putString("nickname", name).commit();
								share.edit().putString("headpic", headpic).commit();
								share.edit().putString("sex", gender).commit();
								share.edit().putString("zhubo_bk", zhubo).commit();
								Util.userid = a;
								LogDetect.send(LogDetect.DataType.basicType,"01162----启动页用户id",a);
								Util.headpic = headpic;
								Util.nickname = name;
								Util.iszhubo = zhubo;

								////////建立跟openfire的关系
								if(Util.imManager == null) {
									Util.imManager = new com.net.yuesejiaoyou.redirect.ResolverB.interface4.im.IMManager();
									Util.imManager.initIMManager(jsonObject, getApplicationContext());
								}

								JPushInterface.setAlias(getApplicationContext(), 1, Util.userid);	// 设置极光别名
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					break;
				case 205:		// 微信授权登录后插入数据库用户数据并返回用户数据
					String json2 = (String) msg.obj;
					LogDetect.send(LogDetect.DataType.specialType, "LoginActivity:--json2 ", json2);
					if (!json2.equals("")) {
						if (json2.contains("nickname")) {
							try {
								//id,nickname,photo,gender
								JSONArray jsonArray = new JSONArray(json2);
								//JSONObject jsonObject = new JSONObject(json);
								JSONObject jsonObject = jsonArray.getJSONObject(0);
								String a = jsonObject.getString("id");
								if (!a.equals("")) {
									if(jsonObject.getString("is_banned").equals("1")){
										Toast.makeText(MainActivity.this,"您已被封禁",Toast.LENGTH_SHORT).show();
										return;
									}
									String name = jsonObject.getString("nickname");
									String headpic = jsonObject.getString("photo");
									String gender = jsonObject.getString("gender");
									String zhubo = jsonObject.getString("is_v");
									String zh = jsonObject.getString("username");
									String pwd = jsonObject.getString("password");
									String wxopenid=jsonObject.getString("openid");
									//share = getSharedPreferences("Acitivity", Activity.MODE_PRIVATE);

									Log.v("TT","name:"+name+",headpic:"+headpic+",wxopenid:"+wxopenid);

									/*share.edit().putString("userid", a).commit();
									share.edit().putString("nickname", name).commit();
									share.edit().putString("headpic", headpic).commit();
									share.edit().putString("sex", gender).commit();
									share.edit().putString("zhubo_bk", zhubo_bk).commit();
									share.edit().putBoolean("FIRST",false).commit();
									share.edit().putString("openid",wxopenid).commit();
									share.edit().putString("logintype","wx").commit();*/
									Util.userid = a;
									Util.headpic = headpic;
									Util.nickname = name;
									Util.is_agent = jsonObject.getString("is_agent");
									Util.iszhubo = zhubo.equals("0")?"0":"1";

									////////建立跟openfire的关系
									if(Util.imManager == null) {
										Util.imManager = new com.net.yuesejiaoyou.redirect.ResolverB.interface4.im.IMManager();
										Util.imManager.initIMManager(jsonObject, getApplicationContext());
									}
									/*Intent intent = new Intent();
								    intent.setClass(MainActivity.this, MainActivity.class);
									startActivity(intent);
									finish();*/
									JPushInterface.setAlias(getApplicationContext(), 1, Util.userid);	// 设置极光别名
								}/* else {
									Toast.makeText(LoginActivity.this, "邀请码不正确", Toast.LENGTH_SHORT).show();
								}*/
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								Log.e("TT","205 wxlogin:"+e);
							}
						}else{
							//showPopupspWindow_tuichu(wc);
						}
					}
					break;
			}
		}
	};

	/***************************************************************************
	 *隐藏所有Fragment
	 * @
	 *
	 ***************************************************************************/
	@Override
	public void onClick(View v) {
//		IMManager.getInstance().checkConnectStatusAndReconnect();	// 有点击导航栏则触发一下
		IMManager.clientHeartbeat();
		 FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
	        hideAllFragment(transaction);
	        switch(v.getId()){
				///////////////
	            case R.id.shipinliaotian:
	            	selected();
	            	hideAllFragment();
					shipinliaotian.setSelected(true);
					shipinimg.setImageResource(R.drawable.home2);
					if(f1==null){
						f1 = new VFragment1_01066();
						transaction.add(R.id.fl_content,f1);
					}else{
						transaction.show(f1);
					}
					break;
				///////////////
	            case R.id.guangchang:
	            	selected();
	            	hideAllFragment();
					guangchang.setSelected(true);
					guangchangimg.setImageResource(R.drawable.discover2);
					if(f2==null){
						f2 = new VFragment2_01066();
						transaction.add(R.id.fl_content,f2);
					}else{
						transaction.show(f2);
					}
	                break;
				///////////////
	            case R.id.xiaoxi:
	            	selected();
	            	hideAllFragment();
					xiaoxi.setSelected(true);
					xiaoxiimg.setImageResource(R.drawable.message2);

					if(f3==null){
						f3 = new VLFragment_News_01160();
						transaction.add(R.id.fl_content,f3);
					}else{
						transaction.show(f3);
					}


	                break;
				///////////////
				case R.id.wode:
					selected();
					hideAllFragment();
					wode.setSelected(true);
					wodeimg.setImageResource(R.drawable.myself2);
					if (!Util.iszhubo.equals("0")){
						if(f4==null){
							f4 = new Fragment_zhubozhongxin_1152();
							transaction.add(R.id.fl_content,f4);
						}else{
							transaction.show(f4);
						}
					}else{
						if(f5==null){
							f5 = new vliao_Gerenzhongxin_1152();
							transaction.add(R.id.fl_content,f5);
						}else{
							transaction.show(f5);
						}
					}


					break;
		}
	    transaction.commit();
	}
	/***************************************************************************
	 *
	 * @
	 *
	 ***************************************************************************/
	private void hideAllFragment() {
		// TODO Auto-generated method stub

	}


	private long firstTime = 0;
	/***************************************************************************
	 * 	//连点两次退出
	 * @
	 *
	 ***************************************************************************/
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
			///////////////
			case KeyEvent.KEYCODE_BACK:
//				long secondTime = System.currentTimeMillis();
//				if (secondTime - firstTime > 2000) {
//					Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
//					firstTime = secondTime;// 更新firstTime
//				} else {// 两次按键小于2秒时，退出应用
//					//finish();
//
//					//releaseWakeLock();
//
//					////////结束播放保活
//					stopPlayMusicService();
//					////////结束播放保活
//					YhApplicationA application = (YhApplicationA)getApplication();
//					application.close();
//					/*if(Util.imManager != null) {
//						Util.imManager.xmppDisconnect();
//					}*/
//
//					System.exit(0);
//				}
				moveTaskToBack(true);
				return true;
		}
		return super.onKeyUp(keyCode, event);
	}
	
	/**
	 * 接收消息广播，更新未读消息条数
	 * @author Administrator
	 *
	 */
	private class MsgOperReciver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			setMsgCnt();
		}
	}

	@Override
	protected void onDestroy() {
		//releaseWakeLock();
		super.onDestroy();
	}
	/**
	 * 计算未读消息条数
	 */
	public void setMsgCnt() {
		if(!"0".equals(Util.userid)) {
			SessionDao sessionDao=new SessionDao(this);
			List<Session> sessionList=sessionDao.queryAllSessions(Util.userid);
			
			int total = 0;
			for(Session session : sessionList) {
				total += Integer.parseInt(session.getNotReadCount());
			}
			if(total <= 0) {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						hd.setVisibility(View.GONE);
					}
				});

			} else {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						hd.setVisibility(View.VISIBLE);
					}
				});
			}
		} else {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					hd.setVisibility(View.GONE);
				}
			});
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
//		IMManager.getInstance().checkConnectStatusAndReconnect();
		IMManager.clientHeartbeat();
	}

	/***************************************************************************
	 *
	 * @
	 *
	 ***************************************************************************/
	private void showPopupspWindow_tuichu(View arg0) {
		LogDetect.send(LogDetect.DataType.specialType, "Fragment_zhubozhongxin_1152:", "弹出框======");
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View  convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.quanxian_pop, null);
	    ImageView close= (ImageView) convertView.findViewById(R.id.close);
	    close.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View view) {
			popupWindow.dismiss();
		}
	});
		TextView confirm = (TextView) convertView.findViewById(R.id.besure1);//获取小窗口上的TextView，以便显示现在操作的功能。
		//////////////////////
		confirm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (Build.VERSION.SDK_INT >=23) {
					for(String permission: permissions) {
						// 检查该权限是否已经获取
						int i = ContextCompat.checkSelfPermission(MainActivity.this, permission);
						// 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
						if (i != PackageManager.PERMISSION_GRANTED) {
							// 如果没有授予该权限，就去提示用户请求
							ActivityCompat.requestPermissions(MainActivity.this, permissions, 3210);
							return;
						}
					}
					popupWindow.dismiss();
				} else {
					popupWindow.dismiss();
				}
			}
		});
		///////////////////////////////////////////////////////////////////////////////////////
		popupWindow = new PopupWindow(convertView, ActionBar.LayoutParams.MATCH_PARENT,
				ActionBar.LayoutParams.WRAP_CONTENT, true);
		popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
		popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.alpha = 0.2f;
		getWindow().setAttributes(lp);
		//是否响应touch事件
		// mPopWindow.setTouchable(false);
		//是否具有获取焦点的能力
		popupWindow.setFocusable(true);
		popupWindow.showAtLocation(arg0, Gravity.BOTTOM  | Gravity.CENTER,0, 0);
		//外部是否可以点击
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.setOutsideTouchable(true);
		//mPopWindow.showAsDropDown(tj2);
        //////////////////////////////////////////////////////////////////////////////////////////

		//////////////////////
		convertView.setOnTouchListener(new View.OnTouchListener()// 需要设置，点击之后取消popupview，即使点击外面，也可以捕获事件
		{
			@Override
			public boolean onTouch(View v, android.view.MotionEvent event) {
				// TODO Auto-generated method stub
				if (popupWindow.isShowing())
				{

					popupWindow.dismiss();
				}
				return false;
			}
		});
		//////////////////////
		popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
			@Override
			public void onDismiss() {
				WindowManager.LayoutParams lp = getWindow().getAttributes();
				lp.alpha = 1f;
				getWindow()
						.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
				getWindow().setAttributes(lp);
			}
		});

	}

}