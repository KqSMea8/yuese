package com.net.yuesejiaoyou.classroot.interface4.openfire.uiface;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect.DataType;
import com.net.yuesejiaoyou.classroot.interface4.openfire.core.Utils;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.bean.Msg;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.bean.Session;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.db.ChatMsgDao;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.db.Const;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.db.SessionDao;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa1.smack.XMPPException;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa1.smack.XMPPTCPConnection;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack.Chat;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack.ChatManager;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack.SmackException.NotConnectedException;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.view.DropdownListView;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.view.DropdownListView.OnRefreshListenerHeader;
import com.net.yuesejiaoyou.classroot.interface4.openfire.interface4.ChatAdapter;
import com.net.yuesejiaoyou.classroot.interface4.openfire.interface4.ExpressionUtil;
import com.net.yuesejiaoyou.classroot.interface4.openfire.interface4.FaceVPAdapter;
import com.net.yuesejiaoyou.classroot.interface4.openfire.interface4.Util;
import com.net.yuesejiaoyou.redirect.ResolverA.interface3.UsersThread_01160A;
import com.net.yuesejiaoyou.redirect.ResolverB.interface3.UsersThread_01158B;
import com.net.yuesejiaoyou.redirect.ResolverB.interface3.UsersThread_01160B;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.agora.P2PVideoConst;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.agora.guke.GukeActivity;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.agora.guke.ZhuboInfo;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.agora.zhubo.GukeInfo;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.agora.zhubo.ZhuboActivity;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.im.IMManager;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.util.AudioRecoderUtils;
import com.net.yuesejiaoyou.redirect.ResolverD.uiface.Chongzhi_01178;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

//import com.lazysellers.sellers.infocenter.hengexa2.smackx.filetransfer.OutgoingFileTransfer;


/**
 * 聊天界面
 * 
 * @author 白玉梁
 * @blog http://blog.csdn.net/baiyuliang2013
 * @weibo http://weibo.com/274433520
 * 
 * */
@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("SimpleDateFormat")
public class ChatActivity extends Activity implements OnClickListener,
		OnRefreshListenerHeader {
	private ViewPager mViewPager;
	private LinearLayout mDotsLayout;
	private EditText input;
	private TextView send,send_sms1;		//,sendFile;
	private DropdownListView mListView;
	private ChatAdapter mLvAdapter;
	private ChatMsgDao msgDao;
	private SessionDao sessionDao;
/*	private float y1 = 0;
	private float y2 = 0;
	private int Timing;
	private Time t;*/
	/*private Uri photoUri;
	private String photoFile;
	private String cameraFile;*/

	/*private TextView shuohua,tv_voc1*/;
/*	private Date endDate,curDate;*/
	private LinearLayout chat_face_container, /*chat_add_container,*/bottom,l1;
	private ImageView image_face,dv,jb;// 表情图标
	/*private ImageView image_add;// 更多图标
*/
/*	private long sj = 0,jishu = 0;*/
	private TextView tv_title, /*tv_pic,// 图片
			tv_camera,// 拍照
*/	/*		tv_loc,// 位置
*/			tv_voc;	// 语音
/*	private Chat chat;*/
	// 表情图标每页6列4行
	private int columns = 6;
	private int rows = 4;
	// 每页显示的表情view
	private List<View> views = new ArrayList<View>();
	// 表情列表
	private List<String> staticFacesList;
	private PopupWindow popupWindow;
	// 消息
	private List<Msg> listMsg;
	private SimpleDateFormat sd;
	private MsgOperReciver msgOperReciver;
/*	private MsgOperReciver1 msgOperReciver1;*/
	private LayoutInflater inflater;
	private int offset;
	private String I, YOU,name,logo,headpicture,username,gender,is_v;// 为了好区分，I就是自己，YOU就是对方
	private Button fanhui;
	/*private TextView yuyin,yuyin1,shuru;*/
	
	private Button button_more_moremodify;
	
	private RelativeLayout chat_main,base_header;
	
	private CheckedTextView c1,c2,c3,c4,c5,c6;

	private MyReceiver_Home receiver;

	private RelativeLayout layBottom;
/*	private Drawable nav_up,nav_up1,nav_up2,js_up,js_up1,js_up2;
	*/
	//private boolean recordState = false;
	
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 1000:
                    tv_voc.performClick();
					break;
             case 201:
				 String json1 = (String)msg.obj;
				 ////////////////////------------------>>>>>>>>
		    	 LogDetect.send(DataType.specialType,"01160:",msg);
				 ////////////////////------------------>>>>>>>>
		    	 if(!json1.isEmpty()){
				  try {
					JSONObject jsonObject1 = new JSONObject(json1);
					  ////////////////////------------------>>>>>>>>
					LogDetect.send(DataType.specialType,"01160:",jsonObject1);
					  ////////////////////------------------>>>>>>>>
					//如果返回0，他钱不够，弹窗让他充值
					String success_ornot=jsonObject1.getString("success");
					  ////////////////////------------------>>>>>>>>
					LogDetect.send(DataType.specialType,"01160 success_ornot:",success_ornot);
					  ////////////////////------------------>>>>>>>>
							if(success_ornot.equals("0")){
								hideSoftInputView();
								showPopupspWindow(chat_main);
							}else if(success_ornot.equals("1")){
								sendMsgText(input.getText().toString());
							//	Toast.makeText(ChatActivity.this, "您花费了2悦币", Toast.LENGTH_SHORT).show();
							}else if(success_ornot.equals("4")){
								Toast.makeText(ChatActivity.this, "您被主播拉黑了", Toast.LENGTH_SHORT).show();
							}/*else if(success_ornot.equals("2")){
								Toast.makeText(ChatActivity.this, "聊天失败，请查看网络连接", Toast.LENGTH_SHORT).show();
							}else if(success_ornot.equals("3")){
								sendMsgText(input.getText().toString());
							}else if(success_ornot.equals("5")){
								Toast.makeText(ChatActivity.this, "您已被封禁。", Toast.LENGTH_SHORT).show();
							}*/
						}catch(JSONException e){
						e.printStackTrace();
					}
		    	 }else{
		    		 Toast.makeText(ChatActivity.this, "聊天失败，请检查网络连接", Toast.LENGTH_SHORT).show();
		    	 }
				break;
             case 230:
					String json = (String)msg.obj;
					LogDetect.send(DataType.specialType,"01160:",msg);
					if(!json.isEmpty()){
						try {
							JSONObject jsonObject = new JSONObject(json);
							LogDetect.send(DataType.specialType,"01160:",jsonObject);
							//如果返回1，说明成功了
							String success_ornot=jsonObject.getString("success");
							LogDetect.send(DataType.specialType,"01160:",success_ornot);
							if(success_ornot.equals("1")){

								SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								final String timestamp = new Date().getTime()+"";
//								final String message = "邀0请1视2频" + Const.SPLIT + Const.ACTION_MSG_ONEINVITE
//										+ Const.SPLIT + timestamp+ Const.SPLIT+com.net.yuesejiaoyou.classroot.interface4.util.Util.nickname+Const.SPLIT+com.net.yuesejiaoyou.classroot.interface4.util.Util.headpic;
//								new Thread(new Runnable() {
//									@Override
//									public void run() {
//										try {
//											LogDetect.send(DataType.noType,"这是什么YouId11",YOU);

//											Utils.sendmessage(Utils.xmppConnection, message, YOU);
//											//AgoraVideoManager.startVideo(getApplication(), timestamp, false);	// 发出邀请后立即进入房间
//											Intent intent = new Intent();
//											intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//											intent.setClass(getApplicationContext(),
//													guke.class);
//											Bundle bundle = new Bundle();
//											bundle.putString("yid_guke", YOU);
//											LogDetect.send(LogDetect.DataType.specialType, "01160 主播id跳转到顾客页面 yid:", YOU);
//											bundle.putString("msgbody", "" + Const.SPLIT + "" + Const.SPLIT + "" + Const.SPLIT +name+Const.SPLIT+logo);
//											bundle.putString("roomid", timestamp);
//											intent.putExtras(bundle);
//
//											startActivity(intent);
											// 插入一对一请求记录
//											String mode2 = "pushp2pvideo";
//											String[] paramsMap2 = {"", com.net.yuesejiaoyou.classroot.interface4.util.Util.userid, com.net.yuesejiaoyou.classroot.interface4.util.Util.nickname, com.net.yuesejiaoyou.classroot.interface4.util.Util.headpic,YOU,timestamp};
//											UsersThread_01158B a2 = new UsersThread_01158B(mode2,paramsMap2,mHandler);
//											Thread c2 = new Thread(a2.runnable);
//											c2.start();

											GukeActivity.startCallZhubo(ChatActivity.this, new ZhuboInfo(YOU,name,logo,timestamp, P2PVideoConst.GUKE_CALL_ZHUBO, P2PVideoConst.NONE_YUYUE));
//										} catch (XMPPException | NotConnectedException e) {
//											e.printStackTrace();
//											//LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"chatmanager: "+e.toString());
//											Looper.prepare();
//											// ToastUtil.showShortToast(ChatActivity.this, "发送失败");
//											Looper.loop();
//										}
//									}
//								}).start();
							}//否则失败了
							/*else if(success_ornot.equals("0")){
								showPopupspWindow_cz(chat_main);
							}else if(success_ornot.equals("3")){
								Toast.makeText(ChatActivity.this, "Big V is busy。", Toast.LENGTH_SHORT).show();
							}else if(success_ornot.equals("4")){
								Toast.makeText(ChatActivity.this, "Big V is not online。", Toast.LENGTH_SHORT).show();
							}*/
							else if(success_ornot.equals("2")){
								showPopupspWindow_reservation(mListView,2);
								Toast.makeText(ChatActivity.this, "主播忙碌，请稍后再试", Toast.LENGTH_SHORT).show();
							}/*else if(success_ornot.equals("0")){

							}*/else if(success_ornot.equals("3")){
								showPopupspWindow_reservation(mListView,3);
								Toast.makeText(ChatActivity.this, "主播设置勿打扰，请稍后再试", Toast.LENGTH_SHORT).show();
							}else if(success_ornot.equals("4")){
								showPopupspWindow_reservation(mListView,4);
								Toast.makeText(ChatActivity.this, "主播不在线", Toast.LENGTH_SHORT).show();
							}else if(success_ornot.equals("0")){
								Toast.makeText(ChatActivity.this, "您的余额不足", Toast.LENGTH_SHORT).show();
							}else if(success_ornot.equals("5")){
								Toast.makeText(ChatActivity.this, "主播被封禁", Toast.LENGTH_SHORT).show();
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}else{
						Toast.makeText(ChatActivity.this, "请检查网络连接", Toast.LENGTH_SHORT).show();
					}
				 tv_voc.setClickable(true);
					break;
				//举报
             case 203:
            	 String json_report = (String) (msg).obj;
            	 LogDetect.send(DataType.specialType,"01160:",msg);
		    	 if(!json_report.isEmpty()){
				  try {
					JSONObject jsonObject1 = new JSONObject(json_report);
					LogDetect.send(DataType.specialType,"01160:",jsonObject1);
					//拉黑
					String success_ornot=jsonObject1.getString("success");
					LogDetect.send(DataType.specialType,"01160 success_ornot:",success_ornot);
							if(success_ornot.equals("1")){
								popupWindow.dismiss();
								 Toast.makeText(ChatActivity.this, "举报成功", Toast.LENGTH_SHORT).show();
							}
						}catch(JSONException e){
						e.printStackTrace();
					}
		    	 }else{
		    		 Toast.makeText(ChatActivity.this, "举报失败，请检查网络连接", Toast.LENGTH_SHORT).show();
		    	 }
		      	 break;
            	 //拉黑
             case 204:
            	 String json_hei = (String) (msg).obj;
            	 LogDetect.send(DataType.specialType,"01160:",msg);
		    	 if(!json_hei.isEmpty()){
				  try {
					JSONObject jsonObject1 = new JSONObject(json_hei);
					LogDetect.send(DataType.specialType,"01160:",jsonObject1);
					//拉黑
					String success_ornot=jsonObject1.getString("success");
					LogDetect.send(DataType.specialType,"01160 success_ornot:",success_ornot);
							if(success_ornot.equals("1")){
								popupWindow.dismiss();
								 Toast.makeText(ChatActivity.this, "拉黑成功", Toast.LENGTH_SHORT).show();
							}
						}catch(JSONException e){
						e.printStackTrace();
					}
		    	 }else{
		    		 Toast.makeText(ChatActivity.this, "拉黑失败，请检查网络连接", Toast.LENGTH_SHORT).show();
		    	 }
		      	 break;
		    case 210:
					String json_reservation = (String) (msg).obj;
					LogDetect.send(DataType.specialType,"01160:",msg);
					if(!json_reservation.isEmpty()){
						try {
							JSONObject jsonObject1 = new JSONObject(json_reservation);
							LogDetect.send(DataType.specialType,"01160:",jsonObject1);
							//预约
							String success_ornot=jsonObject1.getString("success");
							LogDetect.send(DataType.specialType,"01160 success_ornot:",success_ornot);
							if(success_ornot.equals("-2")){
								Toast.makeText(ChatActivity.this, "余额不足，无法预约", Toast.LENGTH_SHORT).show();
							}else if(success_ornot.equals("-1")){
								com.net.yuesejiaoyou.classroot.interface4.util.Util.sendMsgText("『"+ com.net.yuesejiaoyou.classroot.interface4.util.Util.nickname+"』 Appointment is successful",YOU);
								Toast.makeText(ChatActivity.this, "已预约成功，无法再次预约", Toast.LENGTH_SHORT).show();
							}else{
								Toast.makeText(ChatActivity.this, "预约成功,消费"+success_ornot+"悦币", Toast.LENGTH_SHORT).show();
							}
						}catch(JSONException e){
							e.printStackTrace();
						}
					}else{
						Toast.makeText(ChatActivity.this, "预约失败，请检查网络连接", Toast.LENGTH_SHORT).show();
					}
					break;
			}
		    	 
		}
	};

	private AudioRecoderUtils recorder = new AudioRecoderUtils();

	@SuppressLint("ShowToast")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_chat_01160);

		layBottom = (RelativeLayout)findViewById(R.id.lay_bottom);

		SharedPreferences sharedPreferences = getSharedPreferences("Acitivity", Context.MODE_PRIVATE); //私有数据
		
	/*	nav_up=getResources().getDrawable(R.drawable.r_audio_1);  
		nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());  
		nav_up1=getResources().getDrawable(R.drawable.r_audio_2);  
		nav_up1.setBounds(0, 0, nav_up1.getMinimumWidth(), nav_up1.getMinimumHeight());  
		nav_up2=getResources().getDrawable(R.drawable.yuyin);  
		nav_up2.setBounds(0, 0, nav_up2.getMinimumWidth(), nav_up2.getMinimumHeight());  
		
		js_up=getResources().getDrawable(R.drawable.audio_1);  
		js_up.setBounds(0, 0, js_up.getMinimumWidth(), js_up.getMinimumHeight());  
		js_up1=getResources().getDrawable(R.drawable.audio_2);  
		js_up1.setBounds(0, 0, js_up1.getMinimumWidth(), js_up1.getMinimumHeight());  
		js_up2=getResources().getDrawable(R.drawable.audio_3);  
		js_up2.setBounds(0, 0, js_up2.getMinimumWidth(), js_up2.getMinimumHeight());  */
		
		
		I = sharedPreferences.getString("userid","");
		
		username = sharedPreferences.getString("nickname","");
		headpicture= sharedPreferences.getString("headpic","");

		gender = sharedPreferences.getString("sex","");
		
		//I = "147852";/* PreferencesUtils.getSharePreStr(this, "username"); */
		YOU = getIntent().getStringExtra("id");


		
        name= getIntent().getStringExtra("name");
		
		logo=getIntent().getStringExtra("headpic");
		
	/*	fanhui = (Button) findViewById(R.id.button_more_moremodify);
		fanhui.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				Intent intent = new Intent();
				setResult(100, intent);
				finish();
	        }
		});*/
		
		l1 = (LinearLayout)findViewById(R.id.l1);
		
		
		
		inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText(name);
		sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		/*sdName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");*/
		msgDao = new ChatMsgDao(this);
		sessionDao = new SessionDao(this);
		msgOperReciver = new MsgOperReciver();
		IntentFilter intentFilter = new IntentFilter(Const.ACTION_MSG_OPER);
		registerReceiver(msgOperReciver, intentFilter);

		receiver = new MyReceiver_Home();
		IntentFilter homeFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
		registerReceiver(receiver, homeFilter);
		/*msgOperReciver1 = new MsgOperReciver1();
		IntentFilter intentFilter1 = new IntentFilter(Const.ACTION_INPUT);
		registerReceiver(msgOperReciver1, intentFilter1);*/
		
		if(logo.equals("000000")){
			mListView = (DropdownListView) findViewById(R.id.message_chat_listview);
			mListView.setOnRefreshListenerHead(this);
			
			bottom = (LinearLayout) findViewById(R.id.bottom);
			bottom.setVisibility(View.GONE);
			
		}else{
			staticFacesList = ExpressionUtil.initStaticFaces(this);
			// 初始化控件
			initViews();
			// 初始化表情
			initViewPager();
			// 初始化更多选项（即表情图标右侧"+"号内容）
			initAdd();
		}
		init_Data(YOU,I);
		// 初始化数据
		initData();
		Util.currentfrom=YOU;
		//Intent intent = new Intent();

		// 如果打开了“系统消息”页面则隐藏回复输入框
		// 和举报按钮
		if("80".equals(YOU)) {
			layBottom.setVisibility(View.GONE);
			jb.setVisibility(View.GONE);
		}
	}

    private void init_Data(String from,String to) {
		
		msgDao.toberead(from,to);
		Intent intent = new Intent(Const.ACTION_MSG_OPER);// 发送广播，通知消息界面更新
		sendBroadcast(intent);
	}
	
	
	/**
	 * 初始化控件
	 */
	private void initViews() {
		mListView = (DropdownListView) findViewById(R.id.message_chat_listview);

//		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//				Toast.makeText(ChatActivity.this,"item: "+i,Toast.LENGTH_SHORT).show();
//			}
//		});
		chat_main = (RelativeLayout) findViewById(R.id.chat_main);
		base_header = (RelativeLayout) findViewById(R.id.base_header);
		
		button_more_moremodify = (Button)findViewById(R.id.button_more_moremodify);
		button_more_moremodify.setOnClickListener(this);
		
/*		shuru = (TextView)findViewById(R.id.shuru);*/
		
		// 表情图标
		image_face = (ImageView) findViewById(R.id.image_face);
		/*// 更多图标
		image_add = (ImageView) findViewById(R.id.image_add);*/
		// 表情布局
		chat_face_container = (LinearLayout) findViewById(R.id.chat_face_container);
		// 更多
		//chat_add_container = (LinearLayout) findViewById(R.id.chat_add_container);
		dv = (ImageView)findViewById(R.id.dv);
		jb = (ImageView)findViewById(R.id.jb);

		if(com.net.yuesejiaoyou.classroot.interface4.util.Util.iszhubo.equals("0")){
			dv.setVisibility(View.VISIBLE);
			jb.setVisibility(View.VISIBLE);
			jb.setOnClickListener(this);
		}else{
			dv.setVisibility(View.VISIBLE);
			jb.setVisibility(View.VISIBLE);
			jb.setOnClickListener(this);
		}
		
		mViewPager = (ViewPager) findViewById(R.id.face_viewpager);
		mViewPager.setOnPageChangeListener(new PageChange());
		// 表情下小圆点
		mDotsLayout = (LinearLayout) findViewById(R.id.face_dots_container);
		input = (EditText) findViewById(R.id.input_sms);

		input.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View view, boolean b) {
				chat_face_container.setVisibility(View.GONE);
			}
		});






		send = (TextView) findViewById(R.id.send_sms);
		send.setOnClickListener(this);
		
		//Typeface typeFace =Typeface.createFromAsset(getAssets(),"fonts/arialbd.ttf");
		
		send_sms1 = (TextView) findViewById(R.id.send_sms1);
		//sendFile = (TextView) findViewById(R.id.send_file);
		input.setOnClickListener(this);

		input.addTextChangedListener(watcher);
		
		// 表情按钮
		image_face.setOnClickListener(this);
		/*// 更多按钮
		image_add.setOnClickListener(this);*/
		// 发送
	//	send.setTypeface(typeFace);
	//	send_sms1.setTypeface(typeFace);
	//	tv_title.setTypeface(typeFace);
//		sendFile.setOnClickListener(this);
//		sendFile.setOnTouchListener(new OnTouchListener() {
//
//			@Override
//			public boolean onTouch(View view, MotionEvent motionEvent) {
//				int action = motionEvent.getAction();
//
//				if(action != MotionEvent.ACTION_DOWN) {
//					return false;
//				}
//				sendFile.setText("...");
//				recorder.startRecord();
//				return false;
//			}
//		});

		mListView.setOnRefreshListenerHead(this);
		mListView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
					if (chat_face_container.getVisibility() == View.VISIBLE) {
						chat_face_container.setVisibility(View.GONE);
					}
					/*if (chat_add_container.getVisibility() == View.VISIBLE) {
						chat_add_container.setVisibility(View.GONE);
					}*/
					hideSoftInputView();
				}
				return false;
			}
		});
	}

	public void initAdd() {
		/*tv_pic = (TextView) findViewById(R.id.tv_pic);
		tv_camera = (TextView) findViewById(R.id.tv_camera);*/
		/*tv_loc = (TextView) findViewById(R.id.tv_loc);*/
		tv_voc = (TextView) findViewById(R.id.tv_voc);

		//tv_voc1 = (TextView)findViewById(R.id.tv_voc1);
		//shuohua = (TextView)findViewById(R.id.shuohua);
		
	/*	tv_pic.setOnClickListener(this);
		tv_camera.setOnClickListener(this);*/
		/*tv_loc.setOnClickListener(this);*/
		tv_voc.setOnClickListener(this);
		//tv_voc1.setOnClickListener(this);
		


		/*	shuohua.setOnTouchListener(new OnTouchListener() {
				@Override
				public boolean onTouch(View view, MotionEvent motionEvent) {
					int action = motionEvent.getAction();
					if(action == MotionEvent.ACTION_DOWN) {



				         y1 = motionEvent.getY();  
						
						shuohua.setText("松开 结束");
						recordState = true;
						
						mHandler.postDelayed(a, 20000);
						
						curDate = new Date(System.currentTimeMillis());			
						l1.setVisibility(View.VISIBLE);
						
						recorder.startRecord();
					
					}else if(action == MotionEvent.ACTION_UP){
						

					     y2 = motionEvent.getY();
					    
						shuohua.setText("按住 说话");
						endDate = new Date(System.currentTimeMillis());
						 if(y1 - y2 > 50) {
							 Toast.makeText(ChatActivity.this, "取消发送", Toast.LENGTH_SHORT).show();
							recordState = false;
							mHandler.removeCallbacks(a);
							l1.setVisibility(View.GONE);
					    	 //向上滑
							 recorder.stopRecord(); 
						 } else	if(recordState) {
							recordState = false;
							AudioRecoderUtils.RecordInfo vocInfo = recorder.stopRecord();
							sendMsgVoc(vocInfo.filePath+":"+vocInfo.time);
						sj = endDate.getTime() - curDate.getTime();
						Timing = (int) (sj/1000);
						LogDetect.send(LogDetect.DataType.specialType,"01160 sj:",Timing);
						l1.setVisibility(View.GONE);
										if(Timing <=1){
											Toast.makeText(ChatActivity.this, "录音时间过短", Toast.LENGTH_SHORT).show();
										}else if(Timing >= 20){
											RecordInfo vocInfo = recorder.stopRecord();
											sendMsgVoc(vocInfo.filePath+":"+vocInfo.time);
											
										}else{
											// 语音
											//if(Timing <= 20){
											
										
											//}else{
												
											//}
										
											
										//}
				
						}
					
					
					}
					
					return true;
				}
			});
		*/
	
		/*tv_voc.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent motionEvent) {
				int action = motionEvent.getAction();
				if(action != MotionEvent.ACTION_DOWN) {
					return false;
				}
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				curDate = new Date(System.currentTimeMillis());			
				l1.setVisibility(View.VISIBLE);
				recorder.startRecord();
				return false;
			}
		});*/
	}

	public void initData() {
		offset = 0;
		listMsg = msgDao.queryMsg(YOU, I, offset);
		offset = listMsg.size();
		mLvAdapter = new ChatAdapter(this, listMsg,logo,ChatActivity.this,mListView,mHandler/*,Timing*/);
		mListView.setAdapter(mLvAdapter);
		mListView.setSelection(listMsg.size());

		/*mLvAdapter.setmItemOnClickListener(new ChatAdapter.ItemOnClickListener() {  
		  
         *//** 
          *  点击的view子控件 
          * @param view view子控件 
          *//*
         @Override  
         public void itemOnClickListener(View view) {  
             yuyin = (TextView)view;  
             yuyin1 = (TextView)view;
         }  
     });  */
		
	}

	/**
	 * 初始化表情
	 */
	private void initViewPager() {
		int pagesize = ExpressionUtil.getPagerCount(staticFacesList.size(),
				columns, rows);
		
		Log.v("PAOPAO","pagesize="+pagesize);
		// 获取页数
		for (int i = 0; i < pagesize; i++) {
			views.add(ExpressionUtil.viewPagerItem(this, i, staticFacesList,
					columns, rows, input));
			LayoutParams params = new LayoutParams(16, 16);
			mDotsLayout.addView(dotsItem(i), params);
		}
		FaceVPAdapter mVpAdapter = new FaceVPAdapter(views);
		mViewPager.setAdapter(mVpAdapter);
		Log.v("PAOPAO","before setSelected-"+mDotsLayout.getChildCount());
		mDotsLayout.getChildAt(0).setSelected(true);
		Log.v("PAOPAO","after setSelected");
	}

	/**
	 * 表情页切换时，底部小圆点
	 * 
	 * @param position
	 * @return
	 */
	private ImageView dotsItem(int position) {
		View layout = inflater.inflate(R.layout.dot_image_01160, null);
		ImageView iv = (ImageView) layout.findViewById(R.id.face_dot);
		iv.setId(position);
		return iv;
	}

	/**
	 */
	@Override
	protected void onStart() {
		super.onStart();
	}

	
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.send_sms:
			String content = input.getText().toString();
			if (TextUtils.isEmpty(content)) {
				return;
			}
			////////////////////------------------>>>>>>>>
			LogDetect.send(DataType.specialType,"发送消息","情况5");
			////////////////////------------------>>>>>>>>
			if(YOU.equals("40")){
				sendMsgText(content);
			}else if(!com.net.yuesejiaoyou.classroot.interface4.util.Util.iszhubo.equals("0")){
				sendMsgText(content);
			}else{
					//看余额是否够看5分钟的钱
					String mode ="see_five_money";
					String[] paramsMap = {com.net.yuesejiaoyou.classroot.interface4.util.Util.userid,YOU};
					UsersThread_01160B a = new UsersThread_01160B(mode, paramsMap, mHandler);
					Thread t = new Thread(a.runnable);
					t.start();
				//	LogDetect.send(DataType.specialType,"01160 开线程看余额是否够看5分钟的钱:","1");
			}
			//sendMsgText(content);
			break;
		case R.id.input_sms:
			if (chat_face_container.getVisibility() == View.VISIBLE) {
				chat_face_container.setVisibility(View.GONE);
			}
			break;
		case R.id.image_face:
			hideSoftInputView();
			tv_voc.setVisibility(View.VISIBLE);
			input.setVisibility(View.VISIBLE);
			//tv_voc1.setVisibility(View.GONE);
			//shuohua.setVisibility(View.GONE);
			/*if (chat_add_container.getVisibility() == View.VISIBLE) {
				chat_add_container.setVisibility(View.GONE);
			}*/
			if (chat_face_container.getVisibility() == View.GONE) {
				chat_face_container.setVisibility(View.VISIBLE);
			} else {
				chat_face_container.setVisibility(View.GONE);
			}
			break;

		case R.id.tv_voc:
			//点击通话,主播不能点

			// 主播不能发起一对一视频
			if(!"0".equals(com.net.yuesejiaoyou.classroot.interface4.util.Util.iszhubo)) {
//				Toast.makeText(this, "主播不能主动发起视频，请从预约列表发起视频", Toast.LENGTH_SHORT).show();
				ZhuboActivity.startCallGuke(this, new GukeInfo(YOU,name, logo, System.currentTimeMillis()+"",
						P2PVideoConst.ZHUBO_CALL_GUKE, P2PVideoConst.HAVE_NO_YUYUE));
				break;
			}

				String mode1 = "zhubo_online";
				String[] paramsMap1 = {"",I,YOU};
				UsersThread_01158B a = new UsersThread_01158B(mode1,paramsMap1,mHandler);
				Thread c = new Thread(a.runnable);
				c.start();

			break;
				
		/*case R.id.tv_voc1:
			hideSoftInputView();// 隐藏软键盘
			tv_voc.setVisibility(View.VISIBLE);
			input.setVisibility(View.VISIBLE);
			tv_voc1.setVisibility(View.GONE);
			//shuohua.setVisibility(View.GONE);
			break;*/
		case R.id.button_more_moremodify:
			if(logo.equals("000000")){
				Util.currentfrom="";
					AudioRecoderUtils.stopMusic();
				finish();
			}else{
				hideSoftInputView();
				if (chat_face_container.getVisibility() == View.VISIBLE) {
					chat_face_container.setVisibility(View.GONE);
				} /*else if (chat_add_container.getVisibility() == View.VISIBLE) {
					chat_add_container.setVisibility(View.GONE);
				}*/ else {
					Util.currentfrom="";
						AudioRecoderUtils.stopMusic();
					finish();
				}
			}
			break;
		case R.id.jb:
			showPopupspWindow_jb(base_header);
			break;
		case R.id.c1:
			String mode ="report";
			String[] paramsMap = {com.net.yuesejiaoyou.classroot.interface4.util.Util.userid,YOU,c1.getText().toString()};
			UsersThread_01160B az = new UsersThread_01160B(mode, paramsMap, mHandler);
			Thread t = new Thread(az.runnable);
			t.start();
			////////////////////------------------>>>>>>>>
			LogDetect.send(DataType.specialType,"01160 开线程举报:","1");
			////////////////////------------------>>>>>>>>
			break;
		case R.id.c2:
			String mode112 ="report";
			String[] paramsMap112 = {com.net.yuesejiaoyou.classroot.interface4.util.Util.userid,YOU,c2.getText().toString()};
			UsersThread_01160B a1 = new UsersThread_01160B(mode112, paramsMap112, mHandler);
			Thread t1 = new Thread(a1.runnable);
			t1.start();
			////////////////////------------------>>>>>>>>
			LogDetect.send(DataType.specialType,"01160 开线程举报:","2");
			////////////////////------------------>>>>>>>>
			break;
		case R.id.c3:
			String mode2 ="report";
			String[] paramsMap2 = {com.net.yuesejiaoyou.classroot.interface4.util.Util.userid,YOU,c3.getText().toString()};
			UsersThread_01160B a2 = new UsersThread_01160B(mode2, paramsMap2, mHandler);
			Thread t2 = new Thread(a2.runnable);
			t2.start();
			////////////////////------------------>>>>>>>>
			LogDetect.send(DataType.specialType,"01160 开线程举报:","3");
			////////////////////------------------>>>>>>>>
			break;
		case R.id.c4:
			String mode3 ="report";
			String[] paramsMap3 = {com.net.yuesejiaoyou.classroot.interface4.util.Util.userid,YOU,c4.getText().toString()};
			UsersThread_01160B a3 = new UsersThread_01160B(mode3, paramsMap3, mHandler);
			Thread t3 = new Thread(a3.runnable);
			t3.start();
			////////////////////------------------>>>>>>>>
			LogDetect.send(DataType.specialType,"01160 开线程举报:","4");
			////////////////////------------------>>>>>>>>
			break;
		case R.id.c5:
			String mode4 ="report";
			String[] paramsMap4 = {com.net.yuesejiaoyou.classroot.interface4.util.Util.userid,YOU,c5.getText().toString()};
			UsersThread_01160B a4 = new UsersThread_01160B(mode4, paramsMap4, mHandler);
			Thread t4 = new Thread(a4.runnable);
			t4.start();
			////////////////////------------------>>>>>>>>
			LogDetect.send(DataType.specialType,"01160 开线程举报:","5");
			////////////////////------------------>>>>>>>>
			break;
		case R.id.c6:
			String mode5 ="report";
			String[] paramsMap5 = {com.net.yuesejiaoyou.classroot.interface4.util.Util.userid,YOU,c6.getText().toString()};
			UsersThread_01160B a5 = new UsersThread_01160B(mode5, paramsMap5, mHandler);
			Thread t5 = new Thread(a5.runnable);
			t5.start();
			////////////////////------------------>>>>>>>>
			LogDetect.send(DataType.specialType,"01160 开线程举报:","6");
			////////////////////------------------>>>>>>>>
			break;
		}
	}


	/**
	 * 执行发送消息 图片类型
	 * 
	 * @param content
	 */
	/*void sendMsgImg(String photoPath) {
		Msg msg = getChatInfoTo(photoPath, Const.MSG_TYPE_IMG);
		msg.setMsgId(msgDao.insert(msg));
		listMsg.add(msg);
		offset = listMsg.size();
		mLvAdapter.notifyDataSetChanged();
		String[] sep = photoPath.split("\\.");
		String imgpath = Base64.encodeBytes(FileInOut.readFile(new File(
				photoPath)));

		final String message = imgpath + Const.SPLIT + Const.MSG_TYPE_IMG
				+ Const.SPLIT + sd.format(new Date()) + Const.SPLIT + sep[1];

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					sendMessage(Utils.xmppConnection, message, YOU);
				} catch (XMPPException | NotConnectedException e) {
					e.printStackTrace();
					Looper.prepare();
					// ToastUtil.showShortToast(ChatActivity.this, "发送失败");
					Looper.loop();
				}
			}
		}).start();
		updateSession(Const.MSG_TYPE_TEXT, "[图片]");
	}*/
	/**
	 * 执行发送消息 图片类型
	 * 

	 */
	void sendMsgImg(String photoPath) {/*
		//01107m add start
		// 判断要发送的图片容量是否过大，如果太大就进行1/4按比例缩小
		
		boolean needcompress=true;
		while(needcompress){
			File fileTemp = new File(photoPath);
			//String name = fileTemp.getName();
			long length = fileTemp.length();
			if(length > 1024*200) {	//文件大于1MB就把图片长宽各压缩1/4，图片质量降低为原来的80%
				FileOutputStream fos;
				BitmapFactory.Options newOpts = new BitmapFactory.Options();
				newOpts.inJustDecodeBounds = true;
				Bitmap bitmap = BitmapFactory.decodeFile(photoPath,newOpts);

				newOpts.inJustDecodeBounds = false;

		        newOpts.inSampleSize = 2;
		        bitmap = BitmapFactory.decodeFile(photoPath, newOpts);

		        try {
		        	File newFile = new File(Environment.getExternalStorageDirectory()+"/"+fileTemp.getName());
		        	if(newFile.exists()) {
		        		fos = new FileOutputStream(fileTemp);
		        	} else {	//如果是从相册发送的图片且体积过大，压缩后存放入SD根目录
		        		photoPath = Environment.getExternalStorageDirectory()+"/"+fileTemp.getName();
		        		fos = new FileOutputStream(new File(photoPath));
		        	}

					bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos);
					try {
						fos.flush();
						fos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}else{
				needcompress=false;
			}
		}
		
		//01107m add end
		Msg msg = getChatInfoTo(photoPath, Const.MSG_TYPE_IMG);
		msg.setMsgId(msgDao.insert(msg));
		listMsg.add(msg);
		offset = listMsg.size();
		mLvAdapter.notifyDataSetChanged();

		String[] sep = photoPath.split("\\.");
		String imgpath = Base64.encodeBytes(FileInOut.readFile(new File(
				photoPath)));
		//String imgpath=GetImageStr(photoPath);

		final String message = imgpath + Const.SPLIT + Const.MSG_TYPE_IMG
				+ Const.SPLIT + sd.format(new Date()) + Const.SPLIT + sdName.format(new Date()) + "." + sep[1]+ Const.SPLIT+username+Const.SPLIT+headpicture;
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					sendMessage(Utils.xmppConnection, message, YOU);
				} catch (XMPPException | NotConnectedException e) {
					e.printStackTrace();
					//LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"chatmanager: "+e.toString());
					Looper.prepare();
					// ToastUtil.showShortToast(ChatActivity.this, "发送失败");
					Looper.loop();
				}
			}
		}).start();
		//updateSession(Const.MSG_TYPE_TEXT, "[图片]");
		updateSession1(Const.MSG_TYPE_TEXT, "[图片]",name,logo);
	*/}

	/**
	 * 执行发送消息 语音类型
	 *
	 *
	 */
	void sendMsgVoc(String vocPath) {/*
		//01107m add start
		// 判断要发送的图片容量是否过大，如果太大就进行1/4按比例缩小

//		boolean needcompress=true;
//		while(needcompress){
//			File fileTemp = new File(photoPath);
//			//String name = fileTemp.getName();
//			long length = fileTemp.length();
//			if(length > 1024*200) {	//文件大于1MB就把图片长宽各压缩1/4，图片质量降低为原来的80%
//				FileOutputStream fos;
//				BitmapFactory.Options newOpts = new BitmapFactory.Options();
//				newOpts.inJustDecodeBounds = true;
//				Bitmap bitmap = BitmapFactory.decodeFile(photoPath,newOpts);
//
//				newOpts.inJustDecodeBounds = false;
//
//				newOpts.inSampleSize = 2;
//				bitmap = BitmapFactory.decodeFile(photoPath, newOpts);
//
//				try {
//					File newFile = new File(Environment.getExternalStorageDirectory()+"/"+fileTemp.getName());
//					if(newFile.exists()) {
//						fos = new FileOutputStream(fileTemp);
//					} else {	//如果是从相册发送的图片且体积过大，压缩后存放入SD根目录
//						photoPath = Environment.getExternalStorageDirectory()+"/"+fileTemp.getName();
//						fos = new FileOutputStream(new File(photoPath));
//					}
//
//					bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos);
//					try {
//						fos.flush();
//						fos.close();
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//				} catch (FileNotFoundException e) {
//					e.printStackTrace();
//				}
//			}else{
//				needcompress=false;
//			}
//		}

		//01107m add end
		Msg msg = getChatInfoTo(vocPath, Const.MSG_TYPE_VOICE);
		msg.setMsgId(msgDao.insert(msg));
		listMsg.add(msg);
		offset = listMsg.size();
		mLvAdapter.notifyDataSetChanged();

		//--------------
		String[] subStrs = vocPath.split(":");
		//--------------

		String[] sep = subStrs[0].split("\\.");
		String imgpath = Base64.encodeBytes(FileInOut.readFile(new File(subStrs[0])));
		//String imgpath=GetImageStr(photoPath);

		final String message = imgpath + Const.SPLIT + Const.MSG_TYPE_VOICE
				+ Const.SPLIT + sd.format(new Date()) + Const.SPLIT + new File(subStrs[0]).getName()+":"+subStrs[1]+ Const.SPLIT+username+Const.SPLIT+headpicture;

		LogDetect.send(LogDetect.DataType.basicType,"orignal msg:",message);
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					sendMessage(Utils.xmppConnection, message, YOU);
				} catch (XMPPException | NotConnectedException e) {
					e.printStackTrace();
					//LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"chatmanager: "+e.toString());
					Looper.prepare();
					// ToastUtil.showShortToast(ChatActivity.this, "发送失败");
					Looper.loop();
				}
			}
		}).start();
		//updateSession(Const.MSG_TYPE_TEXT, "[图片]");
		updateSession1(Const.MSG_TYPE_TEXT, "[语音]",name,logo);
	*/}
	
	/*public static String GetImageStr(String imgFilePath) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
	    byte[] data = null;
	     
	    // 读取图片字节数组
	    try {
	      InputStream in = new FileInputStream(imgFilePath);
	      data = new byte[in.available()];
	      in.read(data);
	      in.close();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	    return Base64.encode(data); 
	    // 对字节数组Base64编码
	    //BASE64Encoder encoder = new BASE64Encoder();
	    //return encoder.encode(data);// 返回Base64编码过的字节数组字符串
	  }*/
	
	/**
	 * 执行发送消息 文本类型
	 * 
	 * @param content
	 */
	void sendMsgText(String content) {
		Msg msg = getChatInfoTo(content, Const.MSG_TYPE_TEXT);
		msg.setMsgId(msgDao.insert(msg));
		listMsg.add(msg);
		offset = listMsg.size();
		mLvAdapter.notifyDataSetChanged();
		input.setText("");
		final String message = content + Const.SPLIT + Const.MSG_TYPE_TEXT
				+ Const.SPLIT + sd.format(new Date())+ Const.SPLIT+username+Const.SPLIT+headpicture;
		////////////////////------------------>>>>>>>>
		LogDetect.send(DataType.specialType,"发送消息","情况6");
		////////////////////------------------>>>>>>>>
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					////////////////////------------------>>>>>>>>
					LogDetect.send(DataType.specialType,"发送消息","情况7");
					////////////////////------------------>>>>>>>>
					sendMessage(Utils.xmppConnection, message, YOU);
					////////////////////------------------>>>>>>>>
					LogDetect.send(DataType.specialType,"发送消息","情况");
					////////////////////------------------>>>>>>>>
				} catch (XMPPException | NotConnectedException e) {
					e.printStackTrace();
					//LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"chatmanager: "+e.toString());
					Looper.prepare();
					// ToastUtil.showShortToast(ChatActivity.this, "发送失败");
					Looper.loop();
				}
			}
		}).start();
		updateSession1(Const.MSG_TYPE_TEXT, content,name,logo);
	}

	/**
	 * 执行发送消息 文本类型
	 * 
	 * @param content
	 */
	void sendMsgLocation(String content) {/*
		Msg msg = getChatInfoTo(content, Const.MSG_TYPE_LOCATION);
		msg.setMsgId(msgDao.insert(msg));
		listMsg.add(msg);
		offset = listMsg.size();
		mLvAdapter.notifyDataSetChanged();
		final String message = content + Const.SPLIT + Const.MSG_TYPE_LOCATION
				+ Const.SPLIT + sd.format(new Date())+ Const.SPLIT+username+Const.SPLIT+headpicture;
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					sendMessage(Utils.xmppConnection, message, YOU);
				} catch (XMPPException | NotConnectedException e) {
					e.printStackTrace();
					//LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"chatmanager: "+e.toString());
					Looper.prepare();
					// ToastUtil.showShortToast(ChatActivity.this, "发送失败");
					Looper.loop();
				}
			}
		}).start();
		//updateSession(Const.MSG_TYPE_TEXT, "[位置]");
		updateSession1(Const.MSG_TYPE_TEXT, "[位置]",name,logo);
	*/}

	/**
	 * 发送的信息 from为收到的消息，to为自己发送的消息
	 * 
	 * @param message
	 *            => 接收者卍发送者卍消息类型卍消息内容卍发送时间
	 * @return
	 */
	private Msg getChatInfoTo(String message, String msgtype) {
		String time = sd.format(new Date());
		Msg msg = new Msg();
		msg.setFromUser(YOU);
		msg.setToUser(I);
		msg.setType(msgtype);
		msg.setIsComing(1);
		msg.setContent(message);
		msg.setDate(time);
		LogDetect.send(DataType.nonbasicType, "01160 time:", time);
		return msg;
	}
	
//	private Msg getChatInfoTo3(String message, String msgtype) {
//		String time = sd.format(new Date());
//		Msg msg = new Msg();
//		msg.setFromUser(YOU);
//		msg.setToUser(I);
//		msg.setType(msgtype);
//		msg.setIsComing(1);
//		msg.setContent(message);
//		msg.setDate(time);
//		return msg;
//	}
	
	void updateSession1(String type, String content,String name,String logo) {
		Session session = new Session();
		session.setFrom(YOU);
		session.setTo(I);
		session.setNotReadCount("");// 未读消息数量
		session.setContent(content);
		session.setTime(sd.format(new Date()));
		session.setType(type);
		session.setName(name);
		session.setHeadpic(logo);
		
		if (sessionDao.isContent(YOU, I)) {
			sessionDao.updateSession(session);
		} else {
			sessionDao.insertSession(session);
		}
		Intent intent = new Intent(Const.ACTION_ADDFRIEND);// 发送广播，通知消息界面更新
		sendBroadcast(intent);
	}
	void updateSession(String type, String content) {
		Session session = new Session();
		session.setFrom(YOU);
		session.setTo(I);
		session.setNotReadCount("");// 未读消息数量
		session.setContent(content);
		session.setTime(sd.format(new Date()));
		session.setType(type);
		if (sessionDao.isContent(YOU, I)) {
			sessionDao.updateSession(session);
		} else {
			sessionDao.insertSession(session);
		}
		Intent intent = new Intent(Const.ACTION_ADDFRIEND);// 发送广播，通知消息界面更新
		sendBroadcast(intent);
	}

	/**
	 * 表情页改变时，dots效果也要跟着改变
	 * */
	class PageChange implements OnPageChangeListener {
		@Override
		public void onPageScrollStateChanged(int arg0) {
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageSelected(int arg0) {
			for (int i = 0; i < mDotsLayout.getChildCount(); i++) {
				mDotsLayout.getChildAt(i).setSelected(false);
			}
			mDotsLayout.getChildAt(arg0).setSelected(true);
		}
	}

	/**
	 * 下拉加载更多
	 */
	@Override
	public void onRefresh() {
		List<Msg> list = msgDao.queryMsg(YOU, I, offset);
		if (list.size() <= 0) {
			mListView.setSelection(0);
			mListView.onRefreshCompleteHeader();
			return;
		}
		listMsg.addAll(0, list);
		offset = listMsg.size();
		mListView.onRefreshCompleteHeader();
		mLvAdapter.notifyDataSetChanged();
		mListView.setSelection(list.size());
	}

	/**
	 * 弹出输入法窗口
	 */
	@SuppressWarnings("unused")
	private void showSoftInputView(final View v) {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				((InputMethodManager) v.getContext().getSystemService(
						Service.INPUT_METHOD_SERVICE)).toggleSoftInput(0,
						InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}, 0);
	}

	/**
	 * 隐藏软键盘
	 */
	public void hideSoftInputView() {
		InputMethodManager manager = ((InputMethodManager) this
				.getSystemService(Activity.INPUT_METHOD_SERVICE));
		if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
			if (getCurrentFocus() != null)
				manager.hideSoftInputFromWindow(getCurrentFocus()
						.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	/**
	 * 接收消息记录操作广播：删除复制
	 * 
	 * @author baiyuliang
	 */
	private class MsgOperReciver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			initData();

		}
	}

/*	*//**
	 * 接收消息记录操作广播：删除复制
	 * 
	 * @author baiyuliang
	 *//*
	private class MsgOperReciver1 extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			String status = intent.getStringExtra("shuru");
			LogDetect.send(LogDetect.DataType.specialType,"01160 广播", status);
			if(status.equals("1")){
				mHandler.sendMessage(mHandler.obtainMessage(20, (Object)status));
			}else if(status.equals("0")){
				mHandler.sendMessage(mHandler.obtainMessage(30, (Object)status));
			}

		}
	}*/
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(msgOperReciver);
		unregisterReceiver(receiver);
	}

	@Override
	protected void onResume() {
		super.onResume();

//		IMManager.getInstance().checkConnectStatusAndReconnect();
		IMManager.clientHeartbeat();
		input.setFocusable(false);
		input.setFocusableInTouchMode(true);
		chat_face_container.setVisibility(View.GONE);
	};

	private class MyReceiver_Home extends BroadcastReceiver {

		private final String SYSTEM_DIALOG_REASON_KEY = "reason";
		private final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";
		//	private final String SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps";

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
				String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);

				if (reason == null)
					return;

				// Home键
				if (reason.equals(SYSTEM_DIALOG_REASON_HOME_KEY)) {
					chat_face_container.setVisibility(View.GONE);
				}

			/*	// 最近任务列表键
				if (reason.equals(SYSTEM_DIALOG_REASON_RECENT_APPS)) {
					Toast.makeText(getApplicationContext(), "按了最近任务列表", Toast.LENGTH_SHORT).show();
				}*/
			}
		}
	}


	/**
	 * 监听返回键
	 */
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Log.v("PAOPAO","back key pressed");
			if(logo.equals("000000")){
				Util.currentfrom="";
					AudioRecoderUtils.stopMusic();
				finish();
			}else{
				hideSoftInputView();
				if (chat_face_container.getVisibility() == View.VISIBLE) {
					chat_face_container.setVisibility(View.GONE);
				} /*else if (chat_add_container.getVisibility() == View.VISIBLE) {
					chat_add_container.setVisibility(View.GONE);
				} */else {
					AudioRecoderUtils.stopMusic();
					Util.currentfrom="";
					//recorder.stopRecord();
					finish();
				}
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 发送消息
	 * 
	 *
	 * @param content
	 * @param touser
	 * @throws XMPPException
	 * @throws NotConnectedException
	 */
	public void sendMessage(XMPPTCPConnection mXMPPConnection, String content,
			String touser) throws XMPPException, NotConnectedException {
		if (mXMPPConnection == null || !mXMPPConnection.isConnected()) {
			// throw new XMPPException();
		}
		// ChatManager chatmanager = mXMPPConnection.getChatManager();
		// hatManager chatmanager = new ChatManager(mXMPPConnection);
		////////////////////------------------>>>>>>>>
		LogDetect.send(DataType.specialType,"xmpp表面:","1");
		////////////////////------------------>>>>>>>>
		ChatManager chatmanager = Utils.xmppchatmanager;
		//LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"chatmanager: "+chatmanager);
		// Chat chat =chatmanager.createChat(touser + "@" + Const.XMPP_HOST,
		// null);
		////////////////////------------------>>>>>>>>
		LogDetect.send(DataType.specialType,"xmpp表面:","2");
		////////////////////------------------>>>>>>>>
		Chat chat = chatmanager.createChat(YOU + "@" + Const.XMPP_HOST, null);
		////////////////////------------------>>>>>>>>
		LogDetect.send(DataType.specialType,"xmpp表面:","4");
		////////////////////------------------>>>>>>>>
		if (chat != null) {
			// chat.sendMessage(content);
			////////////////////------------------>>>>>>>>
			LogDetect.send(DataType.specialType,"xmpp表面:","5");
			////////////////////------------------>>>>>>>>
			chat.sendMessage(content, I + "@" + Const.XMPP_HOST);
			Log.e("jj", "发送成功");
			////////////////////------------------>>>>>>>>
			LogDetect.send(DataType.specialType,"xmpp表面:","3");
			////////////////////------------------>>>>>>>>
			//LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"send success");
		}else{
			//LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"send fail:chat is null");
		}
	}

	// 打开相机{
		protected void xiangji() {
			Intent intent = new Intent();
			intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
			/*cameraFile = Environment.getExternalStorageDirectory() + "/"
					+ sdName.format(new Date()) + ".jpg";
			photoUri = Uri.fromFile(new File(cameraFile));// 得到一个File
			intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);*/
			startActivityForResult(intent, 1);
		}

	// 打开相册
	protected void xiangce() {/*
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_PICK);
		intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);// 使用以上这种模式，并添加以上两句
		startActivityForResult(intent, 0);

	*/}

	// 得到相册返回data
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {/*

		if (resultCode == -1) {
			if (requestCode == 0) {
				photoFile = upimg(data);
				sendMsgImg(photoFile);
				//LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"sendPic: "+photoFile);
			} else {
				//xiangce();
				Bitmap bm = (Bitmap) data.getExtras().get("data");
				cameraFile = Environment.getExternalStorageDirectory() + "/"+ sdName.format(new Date()) + ".jpg";
				File myCaptureFile = new File(cameraFile);
				try {
					BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
					bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
					bos.flush();
					bos.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

				sendMsgImg(cameraFile);
				//LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"sendCamer: "+cameraFile);
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	*/}

	// 取得相册图片地址
	/*protected String upimg(Intent data) {
		if (data == null) {
			Toast.makeText(this, "未选择图片", Toast.LENGTH_LONG).show();
			return "";
		}
		photoUri = data.getData();
		if (photoUri == null) {
			Toast.makeText(this, "选择图片文件出错", Toast.LENGTH_LONG).show();
			return "";
		}
		String[] pojo = { MediaStore.Images.Media.DATA };
		@SuppressWarnings("deprecation")
		String[] proj = { MediaStore.Images.Media.DATA };
		CursorLoader loader = new CursorLoader(this, photoUri, proj, null,
				null, null);
		Cursor cursor = loader.loadInBackground();
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);

	}*/

	public void showPopupspWindow(View parent) {
		// 加载布局
		LayoutInflater inflater = LayoutInflater.from(ChatActivity.this);  
		View layout = inflater.inflate(R.layout.pop_5_recharge_01160, null);
	                      
		TextView queding,quxiao;
		
		queding = (TextView)layout.findViewById(R.id.queding);
		queding.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				//点击确定，跳转到充币页面
				Intent intent = new Intent();
				intent.setClass(ChatActivity.this,Chongzhi_01178.class);
				startActivity(intent);
				
				LogDetect.send(DataType.specialType,"01160 跳转到充币页面:",intent);
			}
		});
		
		quxiao = (TextView)layout.findViewById(R.id.quxiao);
		quxiao.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				popupWindow.dismiss();
			}
		});
		
		
		popupWindow = new PopupWindow(layout, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT, true);
		// 控制键盘是否可以获得焦点
		popupWindow.setFocusable(true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable(null, ""));
		//popupWindow.showAsDropDown(parent, 0, 0);
		popupWindow.showAtLocation(parent, Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
		popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
			public void onDismiss() {
			}
		});
	}

	@SuppressLint({ "RtlHardcoded", "NewApi" })
	public void showPopupspWindow_jb(View parent) {
		Log.v("TT","showPopupspWindow_jb");
		// 加载布局
		LayoutInflater inflater = LayoutInflater.from(ChatActivity.this);  
		View layout = inflater.inflate(R.layout.pop_jb_01160, null);
				//拉黑      //举报
		TextView quxiao,eat;
		
		eat = (TextView)layout.findViewById(R.id.eat);
		eat.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				popupWindow.dismiss();
				showPopupspWindow_rp(chat_main);
			}
		});
		
		quxiao = (TextView)layout.findViewById(R.id.quxiao);
		quxiao.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String mode ="black";
				String[] paramsMap = {com.net.yuesejiaoyou.classroot.interface4.util.Util.userid,YOU};
				UsersThread_01160B a = new UsersThread_01160B(mode, paramsMap, mHandler);
				Thread t = new Thread(a.runnable);
				t.start();
				LogDetect.send(DataType.specialType,"01160 开线程拉黑:","4");
			}
		});
		
		
		popupWindow = new PopupWindow(layout, 260,
				LayoutParams.WRAP_CONTENT, true);
		// 控制键盘是否可以获得焦点
		popupWindow.setFocusable(true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable(null, ""));
		popupWindow.showAsDropDown(parent, 0, 0,Gravity.RIGHT|Gravity.TOP);
		//popupWindow.showAtLocation(parent, Gravity.RIGHT|Gravity.TOP, 0, 45);
		popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
			public void onDismiss() {
			}
		});
	}
	
	//举报
	@SuppressLint({ "RtlHardcoded", "NewApi" })
	public void showPopupspWindow_rp(View parent) {
		// 加载布局
		LayoutInflater inflater = LayoutInflater.from(ChatActivity.this);  
		View layout = inflater.inflate(R.layout.pop_report_01160, null);
				//取消      
		TextView quxiao;
		
		
		c1 = (CheckedTextView) layout.findViewById(R.id.c1);
		c2 = (CheckedTextView) layout.findViewById(R.id.c2);
		c3 = (CheckedTextView) layout.findViewById(R.id.c3);
		c4 = (CheckedTextView) layout.findViewById(R.id.c4);
		c5 = (CheckedTextView) layout.findViewById(R.id.c5);
		c6 = (CheckedTextView) layout.findViewById(R.id.c6);
		
		c1.setOnClickListener(this);
		c2.setOnClickListener(this);
		c3.setOnClickListener(this);
		c4.setOnClickListener(this);
		c5.setOnClickListener(this);
		c6.setOnClickListener(this);
		
		quxiao = (TextView)layout.findViewById(R.id.quxiao);
		quxiao.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				popupWindow.dismiss();
			}
		});
		
		
		popupWindow = new PopupWindow(layout, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT, true);
		// 控制键盘是否可以获得焦点
		popupWindow.setFocusable(true);
		WindowManager.LayoutParams lp = ChatActivity.this.getWindow().getAttributes();
		lp.alpha = 0.5f;
		ChatActivity.this.getWindow().setAttributes(lp);
		popupWindow.setBackgroundDrawable(new BitmapDrawable(null, ""));
		//popupWindow.showAsDropDown(parent, 0, 0,Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL);
		popupWindow.showAtLocation(parent, Gravity.CENTER_VERTICAL|Gravity.BOTTOM, 0, 0);
		popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
			public void onDismiss() {
				WindowManager.LayoutParams lp = ChatActivity.this.getWindow().getAttributes();
				lp.alpha = 1f;
				ChatActivity.this.getWindow().setAttributes(lp);
			}
		});
	}


	//预约
	@SuppressLint({ "RtlHardcoded", "NewApi" })
	public void showPopupspWindow_reservation(View parent,int online) {
		// 加载布局
		LayoutInflater inflater = LayoutInflater.from(ChatActivity.this);
		View layout = inflater.inflate(R.layout.pop_reservation_01160, null);
		//取消		 确定				标题
		TextView exit_tuichu,exit_login,user_exit;

		user_exit = (TextView) layout.findViewById(R.id.user_exit);
		if(online == 2){
			user_exit.setText("主播正忙，是否预约");
		}else if(online == 3){
			user_exit.setText("主播设置勿打扰，是否预约");
		}else if(online == 4){
			user_exit.setText("主播离线，是否预约");
		}

		exit_tuichu = (TextView)layout.findViewById(R.id.exit_tuichu);
		exit_tuichu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				popupWindow.dismiss();
			}
		});

		exit_login = (TextView) layout.findViewById(R.id.exit_login) ;
		exit_login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Calendar now= Calendar.getInstance();
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String befortime = sdf.format(now.getTimeInMillis());

				now.add(Calendar.HOUR,12);
				String dateStr=sdf.format(now.getTimeInMillis());

				//开始时间
				//String[] paramsMap = {com.net.yuesejiaoyou.classroot.interface4.util.Util.userid,YOU,befortime,dateStr};
				//new Thread(new UsersThread_01160B("insert_reservation",paramsMap,mHandler).runnable).start();

				String[] paramsMap = {com.net.yuesejiaoyou.classroot.interface4.util.Util.userid,YOU,befortime,dateStr};
				new Thread(new UsersThread_01160A("insert_reservation",paramsMap,mHandler).runnable).start();


				popupWindow.dismiss();
			}
		});

		popupWindow = new PopupWindow(layout, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT, true);
		// 控制键盘是否可以获得焦点
		popupWindow.setFocusable(true);
		WindowManager.LayoutParams lp = ChatActivity.this.getWindow().getAttributes();
		lp.alpha = 0.5f;
		ChatActivity.this.getWindow().setAttributes(lp);
		popupWindow.setBackgroundDrawable(new BitmapDrawable(null, ""));
		//popupWindow.showAsDropDown(parent, 0, 0,Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL);
		popupWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);
		popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
			public void onDismiss() {
				WindowManager.LayoutParams lp = ChatActivity.this.getWindow().getAttributes();
				lp.alpha = 1f;
				ChatActivity.this.getWindow().setAttributes(lp);
			}
		});
	}

	
	private TextWatcher watcher = new TextWatcher(){  
        @Override  
        public void afterTextChanged(Editable s) {
    			
        }  
        @Override  
        public void beforeTextChanged(CharSequence s, int start, int count,  
                int after) {  
			
        }  
        @Override  
        public void onTextChanged(CharSequence s, int start, int before,  
                int count) {  
        	   if (s.length() != 0) {
        	/*	sendshuru("1");*/
    		    send.setVisibility(View.VISIBLE);
    		   	send_sms1.setVisibility(View.GONE);
    		  }else {
    		/*	sendshuru("0");*/
    		    send.setVisibility(View.GONE);
    		    send_sms1.setVisibility(View.VISIBLE);
    		   	
    		  }
       
        }          
    }; 
	
  
    
	//钱不够弹窗
	/*@SuppressLint({ "RtlHardcoded", "NewApi" })
	public void showPopupspWindow_cz(View parent) {
		// 加载布局
		LayoutInflater inflater = LayoutInflater.from(ChatActivity.this);  
		View layout = inflater.inflate(R.layout.pop_recharge_01160, null);
				//取消      
		TextView quxiao,queding;
		
		quxiao = (TextView)layout.findViewById(R.id.quxiao);
		quxiao.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				popupWindow.dismiss();
			}
		});
		
		queding = (TextView)layout.findViewById(R.id.queding);
		queding.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//点击确定，跳转到充币页面
				Intent intent = new Intent();
				intent.setClass(ChatActivity.this,Chongzhi_01178.class);
				startActivity(intent);
				
				LogDetect.send(DataType.specialType,"01160 跳转到充币页面:",intent);
			}
		});
		
		popupWindow = new PopupWindow(layout, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT, true);
		// 控制键盘是否可以获得焦点
		popupWindow.setFocusable(true);
		WindowManager.LayoutParams lp = ChatActivity.this.getWindow().getAttributes();
		lp.alpha = 0.5f;
		ChatActivity.this.getWindow().setAttributes(lp);
		popupWindow.setBackgroundDrawable(new BitmapDrawable(null, ""));
		//popupWindow.showAsDropDown(parent, 0, 0,Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL);
		popupWindow.showAtLocation(parent, Gravity.CENTER_VERTICAL|Gravity.BOTTOM, 0, 0);
		popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
			public void onDismiss() {
				WindowManager.LayoutParams lp = ChatActivity.this.getWindow().getAttributes();
				lp.alpha = 1f;
				ChatActivity.this.getWindow().setAttributes(lp);
			}
		});
	}*/
	public void showPopupspWindow_chongzhi(View parent) {
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.is_chongzhi_01165, null);

		TextView cancel = (TextView)layout.findViewById(R.id.cancel);
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				popupWindow.dismiss();
			}
		});


		TextView confirm = (TextView) layout.findViewById(R.id.confirm);//获取小窗口上的TextView，以便显示现在操作的功能。
		confirm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent1=new Intent();
				intent1.setClass(ChatActivity.this,Chongzhi_01178.class );//充值页面
				startActivity(intent1);
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
		// xoff,yoff基于anchor的左下角进行偏移。
		// popupWindow.showAsDropDown(parent, 0, 0);
		popupWindow.showAtLocation(parent, Gravity.CENTER | Gravity.CENTER,252, 0);
		// 监听

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
