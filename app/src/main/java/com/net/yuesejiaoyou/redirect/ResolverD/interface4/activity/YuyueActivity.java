package com.net.yuesejiaoyou.redirect.ResolverD.interface4.activity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/*import com.net.yuesejiaoyou.R;
import com.example.vliao.getset.Bills_01165;
import com.example.vliao.getset.UserBean;
import com.example.vliao.interface3.UsersThread_01158;
import com.example.vliao.interface3.UsersThread_01160;
import com.example.vliao.interface3.UsersThread_01165;
import com.example.vliao.interface3.Yuyue_ListAdapter_01165;
import com.example.vliao.interface3.ZByuyueAdapter_01160;
import com.example.vliao.interface4.LogDetect;
import com.example.vliao.interface4.LogDetect.DataType;
import com.example.vliao.util.Util;
import com.lazysellers.sellers.core.Utils;
import com.lazysellers.sellers.infocenter.db.Const;
import com.lazysellers.sellers.infocenter.hengexa1.smack.XMPPException;
import com.lazysellers.sellers.infocenter.hengexa2.smack.SmackException.NotConnectedException;*/
import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.redirect.ResolverB.interface3.UsersThread_01158B;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.agora.P2PVideoConst;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.agora.zhubo.GukeInfo;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.agora.zhubo.ZhuboActivity;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.Bills_01165;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.ZBYuyueJB_01160;
import com.net.yuesejiaoyou.redirect.ResolverC.interface3.UsersThread_01158;
import com.net.yuesejiaoyou.redirect.ResolverC.interface3.UsersThread_01160;
import com.net.yuesejiaoyou.redirect.ResolverC.interface3.UsersThread_01165C;
import com.net.yuesejiaoyou.redirect.ResolverC.interface4.Yuyue_ListAdapter_01165;
import com.net.yuesejiaoyou.redirect.ResolverC.interface4.ZByuyueAdapter_01160;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.BaseActivity;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class YuyueActivity extends BaseActivity implements View.OnClickListener,SwipeRefreshLayout.OnRefreshListener {
	private ImageView back;
	private TextView no_record;
	private ListView listView;
	private List<Bills_01165> articels = new ArrayList<Bills_01165>();
	private List<ZBYuyueJB_01160> list1 = new ArrayList<ZBYuyueJB_01160>();
	private int s = 0;
	private AVLoadingIndicatorView avi;
	private boolean can_video = true;

	private SwipeRefreshLayout refreshLayout;

	private MyReceiver_Home receiver;
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		back = (ImageView) findViewById(R.id.back);
		back.setOnClickListener(this);
		no_record = (TextView) findViewById(R.id.no_record);
		listView = (ListView) findViewById(R.id.listview);
		listView.setVisibility(View.GONE);
		avi = (AVLoadingIndicatorView) findViewById(R.id.avi);
		avi.smoothToShow();

		refreshLayout= (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
		refreshLayout.setOnRefreshListener(this);
		refreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
				android.R.color.holo_orange_light, android.R.color.holo_green_light);
		refreshLayout.setVisibility(View.GONE);

		IntentFilter intentFilter=new IntentFilter("99");
		reciever1 reciever1=new reciever1();
		registerReceiver(reciever1,intentFilter);


		initData();

		receiver = new MyReceiver_Home();
		IntentFilter homeFilter = new IntentFilter("hang up");
		registerReceiver(receiver, homeFilter);
	}

	@Override
	protected int getContentView() {
		return R.layout.yuyue_01165;
	}


	private void initData() {
		if (Util.iszhubo.equals("1")) {
			//看主播的预约记录
			String mode = "zhubo_yuyue";
			String[] paramsMap = {Util.userid};
			UsersThread_01160 b = new UsersThread_01160(mode, paramsMap, requestHandler);
			Thread thread = new Thread(b.runnable);
			thread.start();
		} else {
			String mode = "yuyue_search";
			String[] paramsMap = {Util.userid};
			UsersThread_01165C b = new UsersThread_01165C(mode, paramsMap,
					requestHandler);
			Thread thread = new Thread(b.runnable);
			thread.start();
		}
	}

	private class reciever1 extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			//showPopupspWindow4(wodeqianbao);
		can_video=true;
			LogDetect.send(LogDetect.DataType.specialType, "准备通话---发送openfire","允许点击");
		}
	}



	@SuppressLint("HandlerLeak")
	private Handler requestHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 200:
					@SuppressWarnings("unchecked")
                    ArrayList<Bills_01165> list = (ArrayList<Bills_01165>) msg.obj;
					avi.smoothToHide();
					refreshLayout.setVisibility(View.VISIBLE);
					if (list.size() != 0) {
						refreshLayout.setRefreshing(false);
						listView.setVisibility(View.VISIBLE); // 本类名
						Yuyue_ListAdapter_01165 adapter = new Yuyue_ListAdapter_01165(
								YuyueActivity.this, listView, null, list,
								requestHandler);
						listView.setAdapter(adapter);
					} else {
						//如果集合内没有数据，将显示没有数据的页面 tu1.setVisibility(View.VISIBLE);
						no_record.setVisibility(View.VISIBLE);
					}
					break;
				case 207:
//				@SuppressWarnings("unchecked")
					list1 = (ArrayList<ZBYuyueJB_01160>) msg.obj;
					// LogDetect.send(DataType.specialType, "01160 主播的预约信息： ",list1.toString());
					avi.smoothToHide();
					refreshLayout.setVisibility(View.VISIBLE);
					if (list1.size() != 0) {
						refreshLayout.setRefreshing(false);
						listView.setVisibility(View.VISIBLE); // 本类名
						ZByuyueAdapter_01160 adapter = new ZByuyueAdapter_01160(
								YuyueActivity.this, listView, list1,
								requestHandler, s);
						listView.setAdapter(adapter);

						listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
							@Override
							public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
								if (list1.get(i).getIs_online().equals("1")) {
									if (can_video) {
										can_video = false;
										String mode1 = "guke_online";
										//用户id
										String[] paramsMap1 = {"", list1.get(i).getUser_id()};
										UsersThread_01158B a = new UsersThread_01158B(mode1, paramsMap1, requestHandler);
										Thread c = new Thread(a.runnable);
										c.start();
										s = i;
									}

								} else {
									Toast.makeText(YuyueActivity.this, "顾客不在线", Toast.LENGTH_SHORT).show();
								}
							}
						});
					} else {

						//如果集合内没有数据，将显示没有数据的页面 tu1.setVisibility(View.VISIBLE);
						no_record.setVisibility(View.VISIBLE);
					}
					;
					break;
				case 240:
					String json = (String) msg.obj;
					//LogDetect.send(DataType.specialType,"01160:",msg);
					if (!json.isEmpty()) {
						try {
							JSONObject jsonObject = new JSONObject(json);
							LogDetect.send(LogDetect.DataType.specialType, "01160:", jsonObject);
							//如果返回1，说明成功了
							String success_ornot = jsonObject.getString("success");
							LogDetect.send(LogDetect.DataType.specialType, "01160:", success_ornot);
							if (success_ornot.equals("1")) {

//								SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								final String timestamp = new Date().getTime()+"";
//								final String message = "邀0请1视2频"+ Util.zhuboRoomId + Const.SPLIT + Const.ACTION_MSG_ZB_RESERVE
//										+ Const.SPLIT + timestamp + Const.SPLIT + Util.nickname + Const.SPLIT + Util.headpic;
//								new Thread(new Runnable() {
//									@Override
//									public void run() {
//										try {

//											Utils.sendmessage(Utils.xmppConnection, message, list1.get(s).getUser_id());
//											//AgoraVideoManager.startVideo(getApplication(), timestamp, false);	// 发出邀请后立即进入房间
//											Intent intent = new Intent();
//											intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//											intent.setClass(getApplicationContext(),
//													zhubo_01160.class);
//											Bundle bundle = new Bundle();
//											bundle.putString("yid_guke",  list1.get(s).getUser_id());
//											LogDetect.send(LogDetect.DataType.specialType, "01160 主播id跳转到顾客页面 yid:", list1.get(s).getUser_id());
//											bundle.putString("msgbody", "" + Const.SPLIT + "" + Const.SPLIT + "" + Const.SPLIT +list1.get(s).getName()+Const.SPLIT+list1.get(s).getPhoto());
//											bundle.putString("roomid", timestamp);
//											intent.putExtras(bundle);
//
//											startActivity(intent);
											// 插入一对一请求记录
//											String mode2 = "pushp2pvideo";
//											String[] paramsMap2 = {"",Util.userid, Util.nickname, Util.headpic,list1.get(s).getUser_id(),timestamp};
//											UsersThread_01158B a2 = new UsersThread_01158B(mode2,paramsMap2,requestHandler);
//											Thread c2 = new Thread(a2.runnable);
//											c2.start();

											ZhuboActivity.startCallGuke(YuyueActivity.this, new GukeInfo(list1.get(s).getUser_id(), list1.get(s).getName(), list1.get(s).getPhoto(), timestamp, P2PVideoConst.ZHUBO_CALL_GUKE, P2PVideoConst.HAVE_ALREADY_YUYUE));
//										} catch (XMPPException | SmackException.NotConnectedException e) {
//											e.printStackTrace();
//											Looper.prepare();
//											Looper.loop();
//										}
//									}
//								}).start();

							} else if (success_ornot.equals("2")) {
								Toast.makeText(YuyueActivity.this, "顾客正忙，请稍后再试", Toast.LENGTH_SHORT).show();
							} /*else if (success_ornot.equals("3")) {
						/*		showPopupspWindow4(mListView);*//*
								Toast.makeText(YuyueActivity.this, "主播设置勿打扰，请稍后再试", Toast.LENGTH_SHORT).show();
							} else if (success_ornot.equals("4")) {
							*//*	showPopupspWindow4(mListView);*//*
								Toast.makeText(YuyueActivity.this, "主播不在线", Toast.LENGTH_SHORT).show();
							} else if (success_ornot.equals("0")) {
								Toast.makeText(YuyueActivity.this, "您的余额不足，无法视频通话", Toast.LENGTH_SHORT).show();
							}*/
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						Toast.makeText(YuyueActivity.this, "请检查网络连接", Toast.LENGTH_SHORT).show();
					}
					break;
				default:
					break;
			}
		}

		;
	};

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
			case R.id.back:
				finish();
				break;

			default:
				break;
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		initData();

		can_video = true;
	}

	private class MyReceiver_Home extends BroadcastReceiver {


		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();

			String mode1 = "mod_online";
			String[] paramsMap1 = {Util.userid, Util.userid, "1",  "2"};
			UsersThread_01158 a = new UsersThread_01158(mode1, paramsMap1, requestHandler);
			Thread t = new Thread(a.runnable);
			t.start();
		}
	}

	@Override
	public void onRefresh() {
		refreshLayout.setRefreshing(true);
		initData();
	}


}
