package com.net.yuesejiaoyou.redirect.ResolverD.interface4.activity;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;


import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;


import com.net.yuesejiaoyou.classroot.interface4.openfire.uiface.ChatActivity;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.redirect.ResolverB.interface3.UsersThread_01158B;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.agora.P2PVideoConst;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.bean.ZhuboInfo;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.Page;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.Phone_01162;
import com.net.yuesejiaoyou.redirect.ResolverC.interface3.UsersThread_01162C;
import com.net.yuesejiaoyou.redirect.ResolverC.interface4.MyAdapter_01162;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.BaseActivity;
import com.wang.avi.AVLoadingIndicatorView;







/*************************************
 * Created by Administrator on 2018/3/5.
 * 我的通话明细
 ************************************/
public class CallHistoryActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
	private int pageno = 1, totlepage = 0;
	private boolean canPull = true;
	private int lastVisibleItem = 0;
	private String userid="";
	private String username = "";
	private String userPhoto = "";
	private List<Phone_01162> list1;

	SwipeRefreshLayout refreshLayout;
	private RecyclerView gridView;
	private GridLayoutManager layoutManager;
	private ImageView back;
	private TextView norecord;
	private AVLoadingIndicatorView avi;
	private PopupWindow popupWindow;

	private MyAdapter_01162 adapter;


	/**************************************
	 *
	 * @param savedInstanceState
	 *************************************/
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		back = (ImageView) findViewById(R.id.back);
		back.setOnClickListener(this);
		avi = (AVLoadingIndicatorView) findViewById(R.id.avi);
		avi.smoothToShow();
		//////////////////////////
		LogDetect.send(LogDetect.DataType.basicType, "01162", "加载页面出现");
		refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
		refreshLayout.setOnRefreshListener(this);
		refreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
				android.R.color.holo_orange_light, android.R.color.holo_green_light);
		gridView = (RecyclerView) findViewById(R.id.rv);
		refreshLayout.setVisibility(View.INVISIBLE);
		norecord= (TextView) findViewById(R.id.no_record);
		norecord.setVisibility(View.GONE);
		pageno = 1;
		init();
	}

	@Override
	protected int getContentView() {
		return R.layout.activity_phonerecord;
	}

	/************************
	 *
	 ************************/
	@Override
	public void onRefresh() {
		refreshLayout.setRefreshing(true);
		pageno = 1;
		init();
	}

	/************************
	 *发送线程
	 ************************/
	public void init() {

			String mode = "my_phone_record";
			String params[] = {Util.userid, pageno + ""};
			UsersThread_01162C b = new UsersThread_01162C(mode, params, handler);
			Thread thread = new Thread(b.runnable);
			thread.start();

	}
	/************************
	 *返回数据
	 ************************/
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
				////////////////////////////
				case 200:
					if (pageno == 1) {
						avi.smoothToHide();
					}
					Page page = (Page) msg.obj;
					pageno = page.getPageNo();
					totlepage = page.getTotlePage();

					refreshLayout.setVisibility(View.VISIBLE);
					if (totlepage == 0) {
						//list1 = page.getList();
						//adapter = new MyAdapter_01162(null, CallHistoryActivity.this, false, list1);
						//adapter.setFadeTips(true);
						norecord.setVisibility(View.VISIBLE);
						return;

					}
					if (pageno == 1) {
						list1 = page.getList();
						adapter = new MyAdapter_01162(null, CallHistoryActivity.this, false, list1);
						if (totlepage == 1) {
							adapter.setFadeTips(true);
						} else {
							adapter.setFadeTips(false);
						}
						////////////////////
						LogDetect.send(LogDetect.DataType.basicType, "01162---t通话记录", list1.size());
						refreshLayout.setRefreshing(false);
						layoutManager = new GridLayoutManager(CallHistoryActivity.this, 1);
						gridView.setLayoutManager(layoutManager);
						gridView.setAdapter(adapter);
						gridView.setItemAnimator(new DefaultItemAnimator());
						/////////////////////////////////////
						gridView.setOnScrollListener(new RecyclerView.OnScrollListener() {
							@Override
							public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
								super.onScrollStateChanged(recyclerView, newState);
								if (newState == RecyclerView.SCROLL_STATE_IDLE) {
									if (lastVisibleItem + 1 == adapter.getItemCount()) {
										if (pageno == totlepage) {

										} else {
											if (canPull) {
												canPull = false;
												pageno++;
												init();
											}
										}
									}
								}
							}
							////////////////////////////////////
							@Override
							public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
								super.onScrolled(recyclerView, dx, dy);
								lastVisibleItem = layoutManager.findLastVisibleItemPosition();
							}
						});
					} else {
						List<Phone_01162> list2 = new ArrayList<Phone_01162>();
						list2 = page.getList();
						for (int i = 0; i < list2.size(); i++) {
							list1.add(list2.get(i));
						}

						adapter.notifyDataSetChanged();
						canPull = true;
						if (totlepage == pageno) {
							adapter.setFadeTips(true);
						} else {
							adapter.setFadeTips(false);
						}
					}
				adapter.setOnItemClickLitsener(new MyAdapter_01162.onItemClickListener() {
						@Override
						public void onItemClick(View view, int position) {
							if(Util.iszhubo.equals("1")){
								userid=list1.get(position).getUser_id()+"";
								Intent intent = new Intent(CallHistoryActivity.this, ChatActivity.class);
								intent.putExtra("id", userid);
								intent.putExtra("name", list1.get(position).getNickname());
								intent.putExtra("headpic", list1.get(position).getPhoto());
								startActivity(intent);
							}else {
								userid=list1.get(position).getZhuboid()+"";
								username = list1.get(position).getNickname();
								userPhoto = list1.get(position).getPhoto();

								String mode1 = "zhubo_online";
								String[] paramsMap1 = {"",Util.userid,userid};
								UsersThread_01158B a = new UsersThread_01158B(mode1,paramsMap1,handler);
								Thread c = new Thread(a.runnable);
								c.start();
								LogDetect.send(LogDetect.DataType.specialType, "准备通话---对方ID",list1.get(position).getUser_id()+"");
							}

						}

						@Override
						public void onItemLongClick(View view, int position) {

						}
					});
					break;
				case 230:
					String json = (String)msg.obj;
					//LogDetect.send(LogDetect.DataType.specialType,"01160:",msg);
					if(!json.isEmpty()){
						try {
							JSONObject jsonObject = new JSONObject(json);
							/////////////////////////
							LogDetect.send(LogDetect.DataType.specialType,"01160:",jsonObject);
							//如果返回1，说明成功了
							String success_ornot=jsonObject.getString("success");
							////////////////////////
							LogDetect.send(LogDetect.DataType.specialType,"01160:",success_ornot);

							/*************************************************************
							 * openfire相关暂时注释
							 ************************************************************/
							if(success_ornot.equals("1")){
								SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								final String timestamp = new Date().getTime()+"";
								GukeActivity.startCallZhubo(CallHistoryActivity.this, new ZhuboInfo(userid,username,userPhoto,timestamp, P2PVideoConst.GUKE_CALL_ZHUBO, P2PVideoConst.NONE_YUYUE));
//								final String message = "邀0请1视2频" + Const.SPLIT + Const.ACTION_MSG_ONEINVITE
//										+ Const.SPLIT + timestamp+ Const.SPLIT+ Util.nickname+Const.SPLIT+ Util.headpic;
//								if(userid.equals(Util.userid)){
//									//you can't call yourself---抱歉，你与你自己无法通话
//									Toast.makeText(CallHistoryActivity.this,"抱歉，你与你自己无法通话",Toast.LENGTH_LONG).show();
//								}else{
//									new Thread(new Runnable() {
//										@Override
//										public void run() {
//											try {
//												///////////////////////
//												LogDetect.send(LogDetect.DataType.noType,"这是什么YouId",userid);
//												Utils.sendmessage(Utils.xmppConnection, message,userid);
//												//AgoraVideoManager.startVideo(getApplication(), timestamp, false);	// 发出邀请后立即进入房间
//												Intent intent = new Intent();
//												intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//												intent.setClass(getApplicationContext(),
//														guke.class);
//												Bundle bundle = new Bundle();
//												bundle.putString("yid_guke",  userid);
//												LogDetect.send(LogDetect.DataType.specialType, "01160 主播id跳转到顾客页面 yid:", userid);
//												bundle.putString("msgbody", "" + Const.SPLIT + "" + Const.SPLIT + "" + Const.SPLIT +username+Const.SPLIT+userPhoto);
//												bundle.putString("roomid", timestamp);
//												intent.putExtras(bundle);
//
//												startActivity(intent);
//											} catch (XMPPException | SmackException.NotConnectedException e) {
//												e.printStackTrace();
//												Looper.prepare();
//												Looper.loop();
//											}
//										}
//									}).start();
//								}
							}//否则失败了		/////////////////////////////////注释结束地方else后边
							else if(success_ornot.equals("2")){
							/*showPopupspWindow4(mListView);*/
							//The anchor is busy now,please try again later----主播现在很忙，请稍后再试。
								Toast.makeText(CallHistoryActivity.this, "主播现在很忙，请稍后再试。", Toast.LENGTH_SHORT).show();

							}/*else if(success_ornot.equals("0")){

							}*/else if(success_ornot.equals("3")){
								/*showPopupspWindow4(mListView);*/
								//The anchor won't be disturbed now,please try again later--主播现在不会被打扰，请稍后再试。
								Toast.makeText(CallHistoryActivity.this, "主播暂时不在，请稍后再试。", Toast.LENGTH_SHORT).show();
							}else if(success_ornot.equals("4")){
								/*showPopupspWindow4(mListView);*/
								Toast.makeText(CallHistoryActivity.this, "主播离线中", Toast.LENGTH_SHORT).show();
							}else if(success_ornot.equals("0")){
								//You don't have enough money to pay for this call--你的悦币余额不足以进行本次通话
								Toast.makeText(CallHistoryActivity.this, "你的悦币余额不足以进行本次通话", Toast.LENGTH_SHORT).show();
								showPopupspWindow_chongzhi(norecord);
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}else{
						Toast.makeText(CallHistoryActivity.this, "请检查网络", Toast.LENGTH_SHORT).show();
					}
					break;
			}
		}
	};

	/******************************
	 * 单击事件
	 * @param view
	 ******************************/
	@Override
	public void onClick(View view) {
		int id = view.getId();
		switch (id) {
			///////////////////////
			case R.id.back:
				finish();
				break;
		}
	}

	/***************************************
	 *
	 * @param parent
	 **************************************/
	public void showPopupspWindow_chongzhi(View parent) {
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.is_chongzhi_01165, null);
		TextView cancel = (TextView)layout.findViewById(R.id.cancel);
		/////////////////////////////////
		cancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				popupWindow.dismiss();
			}
		});
		TextView confirm = (TextView) layout.findViewById(R.id.confirm);//获取小窗口上的TextView，以便显示现在操作的功能。
		////////////////////////////////
		confirm.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent1=new Intent();
				/***********************************
				 * 充值页面需要V信api，暂时注释掉跳转充值页面
				 *
				 **********************************/
				//intent1.setClass(CallHistoryActivity.this,RechargeActivity.class );//充值页面
				startActivity(intent1);
				popupWindow.dismiss();
				finish();
			}
		});
		popupWindow = new PopupWindow(layout, ViewPager.LayoutParams.MATCH_PARENT,//？？？？？？？？？？？？？？
				ViewPager.LayoutParams.WRAP_CONTENT, true);
		// 控制键盘是否可以获得焦点
		popupWindow.setFocusable(true);
		// 设置popupWindow弹出窗体的背景
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.alpha = 0.4f;
		getWindow().setAttributes(lp);
		popupWindow.setBackgroundDrawable(new BitmapDrawable(null, ""));
		WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		@SuppressWarnings("deprecation")
		// 获取xoff
				int xpos = manager.getDefaultDisplay().getWidth() / 2
				- popupWindow.getWidth() / 2;
		//xoff,yoff基于anchor的左下角进行偏移。
		//popupWindow.showAsDropDown(parent, 0, 0);
		popupWindow.showAtLocation(parent, Gravity.CENTER | Gravity.CENTER,252, 0);
		//监听
		/////////////////////////////////////
		popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
			// 在dismiss中恢复透明度
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