package com.net.yuesejiaoyou.classroot.interface4.openfire.uiface;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Editable;
import android.text.Html;
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
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

//import com.aliyun.struct.common.CropKey;
//import com.aliyun.struct.common.ScaleMode;
//import com.aliyun.struct.common.VideoQuality;
//import com.aliyun.struct.encoder.VideoCodecs;
//import com.aliyun.struct.recorder.CameraType;
//import com.aliyun.struct.recorder.FlashType;
//import com.aliyun.struct.snap.AliyunSnapVideoParam;
import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect.DataType;
import com.net.yuesejiaoyou.classroot.interface4.openfire.core.Utils;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.bean.Msg;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.bean.Session;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.db.ChatMsgDao;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.db.Const;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.db.SessionDao;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa1.smack.Base64;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa1.smack.XMPPException;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa1.smack.XMPPTCPConnection;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack.Chat;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack.ChatManager;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack.SmackException.NotConnectedException;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack.util.FileInOut;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.view.DropdownListView;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.view.DropdownListView.OnRefreshListenerHeader;
import com.net.yuesejiaoyou.classroot.interface4.openfire.interface4.ChatAdapter_KF;
import com.net.yuesejiaoyou.classroot.interface4.openfire.interface4.ExpressionUtil;
import com.net.yuesejiaoyou.classroot.interface4.openfire.interface4.FaceVPAdapter;
import com.net.yuesejiaoyou.classroot.interface4.openfire.interface4.Util;
import com.net.yuesejiaoyou.classroot.interface4.util.AudioRecoderUtils;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.im.IMManager;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.BaseActivity;
//import com.net.yuesejiaoyou.redirect.ResolverB.interface4.videoeditor.Common;
//import com.net.yuesejiaoyou.redirect.ResolverB.interface4.videoimport.MediaActivity_01160;
//import com.net.yuesejiaoyou.redirect.ResolverB.interface4.videorecorder.AliyunVideoRecorder_01160;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

//import com.net.yuesejiaoyou.redirect.ResolverB.uiface.MediaActivity_01160;
//import com.lazysellers.sellers.infocenter.hengexa2.smackx.filetransfer.OutgoingFileTransfer;


/**
 * 聊天界面
 * 
 * @author 白玉梁
 * @blog http://blog.csdn.net/baiyuliang2013
 * @weibo http://weibo.com/274433520
 * 
 * */
@SuppressLint("SimpleDateFormat")
public class CoustomerActivity extends BaseActivity implements OnClickListener,
		OnRefreshListenerHeader {
	private ViewPager mViewPager;
	private LinearLayout mDotsLayout;
	private EditText input;
	private TextView send;		//,sendFile;
	private DropdownListView mListView;
	private ChatAdapter_KF mLvAdapter;
	private ChatMsgDao msgDao;
	private SessionDao sessionDao;
	private float y1 = 0;
	private float y2 = 0;
	private int Timing,Timing1;
	private Timer timer = new Timer();  
	private Timer timer1 = new Timer();  
	private TimerTask task;
	private TimerTask task1;
	
	private Uri photoUri;
	private String photoFile;
	private String cameraFile;

	private TextView shuohua,tv_voc1;
	//private Date endDate,curDate;
	private LinearLayout chat_face_container, chat_add_container,bottom,l1;
	private ImageView image_face;// 表情图标
	private ImageView image_add;// 更多图标

	private int sj = 0,jishu = 0;
	private TextView tv_title, tv_pic,// 图片
			tv_camera,// 拍照
		//	tv_loc,// 位置
			tv_voc;	// 语音
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
	private String I, YOU,name,logo,headpicture,username,gender;// 为了好区分，I就是自己，YOU就是对方
	private SimpleDateFormat sdName;
//	private Button fanhui;
	private TextView yuyin,yuyin1,shuru;
	
	//private TextView jishi,jishi1;
	
	private Button button_more_moremodify;
	
	private Chronometer yy_js;
	
	private TextView yy_tips;
	
	private AnimationDrawable animationDrawable_left;
	private AnimationDrawable animationDrawable_right;
	
	private Drawable nav_up,nav_up1,nav_up2,js_up,js_up1,js_up2;
	
	private boolean recordState = false;
	
	private RelativeLayout chat_main;
	
	private StringBuffer st1,st;

	private MyReceiver_Home receiver;

	private String[] permissions = {Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.RECORD_AUDIO
	};
	
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			/*case 1:
				mLvAdapter.notifyDataSetChanged();
				break;*/
			 case 0 :  
				 yuyin.setCompoundDrawables(null, null, nav_up, null);
                 break;  
             case 1 :  
				 yuyin.setCompoundDrawables(null, null, nav_up1, null);
                 break;  
             case 2 :  
				 yuyin.setCompoundDrawables(null, null, nav_up2, null);
            	 break;
             case 3 :  
            	 yuyin1.setCompoundDrawables(js_up, null, null, null);  
            	 break;  
	         case 4 :  
	        	 yuyin1.setCompoundDrawables(js_up1, null, null, null);  
	             break;  
	         case 5 :  
	        	 yuyin1.setCompoundDrawables(js_up2, null, null, null);  
	        	 break;
	         case 50 :
	        	 int time = (int)msg.obj;
	        	 LogDetect.send(DataType.nonbasicType, "01160 mes time",time);
	        	 Timing = time;
	        	
	        	 StringBuffer sf = new StringBuffer();
					for(int i=0;i<=Timing;i++){
						sf.insert(0 , " " ); 
					}
				 st = sf;	
					mLvAdapter.enableItemChoser();
	        	 //yuyin.setEnabled(false);
	        	 if (timer == null) {  
	        		 timer = new Timer();  
	             }  
	        	 
	        	 if (task == null){
	        			task  = new TimerTask() {  
	        				  
	        		        @Override  
	        		        public void run() {  
	        		            // 需要做的事:发送消息  
	        		            Message message = new Message();  
	        		            message.what = 70;
								mHandler.sendMessage(message);
	        		        }  
	        		    }; 
	        		
	        	 }
	        	 if(timer != null && task != null ){ 
	        		 timer.schedule(task, 1000, 1000);
	        	 }
	        	 break;
	         case 60 :
	        	 int time1 = (int)msg.obj;
	        	 
	        	 Timing1 = time1 ;
	        	 StringBuffer sf1 = new StringBuffer();
					for(int i=0;i<=Timing1;i++){
						sf1.insert(0 , " " ); 
					}
					st1 = sf1;
					mLvAdapter.enableItemChoser();
	        	// yuyin1.setEnabled(false);
	        	 if (timer1 == null) {  
	        		 timer1 = new Timer();  
	             }  
	        	 if (task1 == null){
	        	     task1  = new TimerTask() {  
	        			  
	        	        @Override  
	        	        public void run() {  
	        	            // 需要做的事:发送消息  
	        	            Message message = new Message();  
	        	            message.what = 80;
							mHandler.sendMessage(message);
	        	        }  
	        	    }; 
	        	 }
	        	 if(timer1 != null && task1 != null ){ 
	        		 timer1.schedule(task1, 1000, 1000);
	        	 }
	        	
	        	 break;
	        /* case 20:
	        	 shuru.setVisibility(View.VISIBLE);
	        	 break;
	         case 30:
	        	 shuru.setVisibility(View.GONE);
	        	 break;*/
				case 70 :

					if((Integer.toString(Timing).equals(Integer.toString(sj)))){
						if (timer != null) {
							timer.cancel();
							timer = null;
						}

						if (task != null) {
							task.cancel();
							task = null;
						}


						yuyin.setText(String.valueOf(Timing)+"'' "+st);
						mLvAdapter.notifyDataSetChanged();
						sj = 0;
						Timing = 0;
						mLvAdapter.disableAllItemChoser();
						//yuyin.setEnabled(true);
					}else{
						yuyin.setText(Integer.toString(sj++)+"'' "+st);
					}
					break;
				case 80 :

					if((Integer.toString(Timing1).equals(Integer.toString(jishu)))){
						if (timer1 != null) {
							timer1.cancel();
							timer1 = null;
						}

						if (task1 != null) {
							task1.cancel();
							task1 = null;
						}
						yuyin1.setText(st1+Integer.toString(Timing1)+"'' ");
						jishu = 0;
						Timing1=0;
						mLvAdapter.notifyDataSetChanged();
						mLvAdapter.disableAllItemChoser();
						//yuyin1.setEnabled(true);
					}else{
						yuyin1.setText(st1+Integer.toString(jishu++)+"'' ");

					}
					break;

			}
		}
	};

	/*
	private Handler handler = new Handler(new Handler.Callback() {
		
		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
		     case 70 :
		    	 yuyin.setText(Integer.toString(sj++)+"'' "+st);
	        	 if((Integer.toString(Timing).equals(Integer.toString(sj)))){
	        		 if (timer != null) {  
	        			 timer.cancel();  
	        			 timer = null;  
	        	        }  
	        	  
        	        if (task != null) {  
        	        	task.cancel();  
        	        	task = null;  
        	        }     
        	       
				
					 yuyin.setText(String.valueOf(sj++)+"'' "+st);
					 mLvAdapter.notifyDataSetChanged();
	        		 sj = 0;
	        		 mLvAdapter.disableAllItemChoser();
	        		 //yuyin.setEnabled(true);
	        	 }
	        	 break;
	         case 80 :
	        	 yuyin1.setText(st1+Integer.toString(jishu++)+"'' ");
				
	        	 if((Integer.toString(Timing1).equals(Integer.toString(jishu)))){
	        		 if (timer1 != null) {  
	        			 timer1.cancel();  
	        			 timer1 = null;  
	        	        }  
	        	  
        	        if (task1 != null) {  
        	        	task1.cancel();  
        	        	task1 = null;  
        	        }     
        	        yuyin1.setText(st1+Integer.toString(jishu++)+"'' ");
        	        jishu = 0;
					 mLvAdapter.notifyDataSetChanged();
        	        mLvAdapter.disableAllItemChoser();
	        		//yuyin1.setEnabled(true);
	        	 }
	        	 break;
			}
			return false;
		}
	}); */
	
	private AudioRecoderUtils recorder = new AudioRecoderUtils();


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SharedPreferences sharedPreferences = getSharedPreferences("Acitivity", Context.MODE_PRIVATE); //私有数据
		
		nav_up=getResources().getDrawable(R.drawable.r_audio_1);  
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
		js_up2.setBounds(0, 0, js_up2.getMinimumWidth(), js_up2.getMinimumHeight());  
		
		
		I = sharedPreferences.getString("userid","");
		
		username = sharedPreferences.getString("nickname","");
		headpicture= sharedPreferences.getString("headpic","");

		gender = sharedPreferences.getString("sex","");
		
		//I = "147852";/* PreferencesUtils.getSharePreStr(this, "username"); */
		YOU = getIntent().getStringExtra("id");
        name= getIntent().getStringExtra("name");
		logo=getIntent().getStringExtra("headpic");

		
		inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText(Html.fromHtml("小客服<font color=\"#FF5C5C\">(官方)</font>"));
		sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sdName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
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

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

			for(String permission: permissions) {
				// 检查该权限是否已经获取
				int i = ContextCompat.checkSelfPermission(CoustomerActivity.this, permission);
				// 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
				if (i != PackageManager.PERMISSION_GRANTED) {
					// 如果没有授予该权限，就去提示用户请求
					ActivityCompat.requestPermissions(CoustomerActivity.this, permissions, 321);
					return;
				}
			}
		}
		
	}

	@Override
	protected int getContentView() {
		return R.layout.activity_coustomer;
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

		
		l1 = (LinearLayout)findViewById(R.id.l1);
		yy_js = (Chronometer)findViewById(R.id.yy_js);
		yy_js.setFormat("%s");
		yy_tips = (TextView)findViewById(R.id.yy_tips);
		
		chat_main = (RelativeLayout) findViewById(R.id.chat_main);
		/*mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				
				Toast.makeText(CoustomerActivity.this,"item: "+i,Toast.LENGTH_SHORT).show();
			}
		});*/
		button_more_moremodify = (Button)findViewById(R.id.button_more_moremodify);
		button_more_moremodify.setOnClickListener(this);
		
		shuru = (TextView)findViewById(R.id.shuru);
		
		// 表情图标
		image_face = (ImageView) findViewById(R.id.image_face);
		// 更多图标
		image_add = (ImageView) findViewById(R.id.image_add);
		// 表情布局
		chat_face_container = (LinearLayout) findViewById(R.id.chat_face_container);
		// 更多
		chat_add_container = (LinearLayout) findViewById(R.id.chat_add_container);

		mViewPager = (ViewPager) findViewById(R.id.face_viewpager);
		mViewPager.setOnPageChangeListener(new PageChange());
		// 表情下小圆点
		mDotsLayout = (LinearLayout) findViewById(R.id.face_dots_container);
		input = (EditText) findViewById(R.id.input_sms);
		send = (TextView) findViewById(R.id.send_sms);
		//sendFile = (TextView) findViewById(R.id.send_file);
		input.setOnClickListener(this);

		input.addTextChangedListener(watcher);

		input.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View view, boolean b) {
				chat_face_container.setVisibility(View.GONE);
			}
		});
		// 表情按钮
		image_face.setOnClickListener(this);
		// 更多按钮
		image_add.setOnClickListener(this);
		// 发送
		send.setOnClickListener(this);
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
					if (chat_add_container.getVisibility() == View.VISIBLE) {
						chat_add_container.setVisibility(View.GONE);
					}
					hideSoftInputView();
				}
				return false;
			}
		});
	}

	@SuppressLint("ClickableViewAccessibility")
	public void initAdd() {
		tv_pic = (TextView) findViewById(R.id.tv_pic);
		tv_camera = (TextView) findViewById(R.id.tv_camera);
		/*tv_loc = (TextView) findViewById(R.id.tv_loc);*/
		tv_voc = (TextView) findViewById(R.id.tv_voc);

		tv_voc1 = (TextView)findViewById(R.id.tv_voc1);
		shuohua = (TextView)findViewById(R.id.shuohua);
		
		tv_pic.setOnClickListener(this);
		tv_camera.setOnClickListener(this);
		/*tv_loc.setOnClickListener(this);*/
		tv_voc.setOnClickListener(this);
		tv_voc1.setOnClickListener(this);
		


			shuohua.setOnTouchListener(new OnTouchListener() {
				@Override
				public boolean onTouch(View view, MotionEvent motionEvent) {

					int action = motionEvent.getAction();
					if(action == MotionEvent.ACTION_DOWN) {

				         y1 = motionEvent.getY();  
						
						shuohua.setText("松开 结束");
						recordState = true;
						
						mHandler.postDelayed(a, 20000);
						
			/*			curDate = new Date(System.currentTimeMillis());	*/		
						l1.setVisibility(View.VISIBLE);
						
						//复位计时器
						yy_js.setBase(SystemClock.elapsedRealtime());
						yy_js.start();
						yy_tips.setText("手指上滑，取消发送");
						yy_tips.setBackgroundColor(Color.parseColor("#00000000"));
					
						recorder.startRecord();
					}else if(action == MotionEvent.ACTION_UP){

						if(Integer.parseInt(yy_js.getText().toString().split(":")[1]) < 1){
							recordState = false;
							mHandler.removeCallbacks(a);
							l1.setVisibility(View.GONE);
							yy_js.stop();
							//向上滑
							recorder.stopRecord();
							Toast.makeText(CoustomerActivity.this,"录音时间太短",Toast.LENGTH_SHORT).show();
						}else{
							 y2 = motionEvent.getY();

							shuohua.setText("按住 说话");
							/*endDate = new Date(System.currentTimeMillis());*/
							 if(y1 - y2 > 50) {
								yy_tips.setText("松开手指，取消发送");
								yy_tips.setBackgroundColor(Color.parseColor("#e0EA3636"));

								recordState = false;
								mHandler.removeCallbacks(a);
								l1.setVisibility(View.GONE);
								yy_js.stop();
								 //向上滑
								 recorder.stopRecord();
							 } else	if(recordState) {
								 recordState = false;
								 mHandler.removeCallbacks(a);
								 AudioRecoderUtils.RecordInfo vocInfo = recorder.stopRecord();
								 sendMsgVoc(vocInfo.filePath + ":" + vocInfo.time);
								/*	sj = endDate.getTime() - curDate.getTime();
								Timing = (int) (sj/1000);
								LogDetect.send(LogDetect.DataType.specialType,"01160 sj:",Timing);*/
								 l1.setVisibility(View.GONE);
								 yy_js.stop();
										/*	if(Timing <=1){
												Toast.makeText(ChatActivity.this, "录音时间过短", Toast.LENGTH_SHORT).show();
											}else if(Timing >= 20){
												RecordInfo vocInfo = recorder.stopRecord();
												sendMsgVoc(vocInfo.filePath+":"+vocInfo.time);

											}else{*/
								 // 语音
								 //if(Timing <= 20){


								 //}/*else{

								 //}*/

									/*
											}*/
							 }
						}
					
					
					}
					
					return true;
				}
			});
		
		
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
		mLvAdapter = new ChatAdapter_KF(this, listMsg,logo,CoustomerActivity.this,mListView,mHandler/*,Timing*/);
		mListView.setAdapter(mLvAdapter);
		mListView.setSelection(listMsg.size());

		mLvAdapter.setmItemOnClickListener(new ChatAdapter_KF.ItemOnClickListener() {  
		  
         /** 
          *  点击的view子控件 
          * @param view view子控件 
          */
         @Override  
         public void itemOnClickListener(View view) {  
        	 yuyin = (TextView)view;  
             yuyin1 = (TextView)view;
           //  jishi = (TextView)view;
           //  jishi1 = (TextView)view;
         }  
     });  
		
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
			sendMsgText(content);
			/*if(YOU.equals("40")){
				sendMsgText(content);
			}else{
				if(com.example.aiba.util.Util.vip.equals("1")){
					sendMsgText(content);
				}else{*/
					//不是vip的给他扣2个币
/*					String mode ="knot_bi";
					String[] paramsMap = {com.example.vliao.util.Util.userid,YOU};
					UsersThread_01160 a = new UsersThread_01160(mode, paramsMap, mHandler);
					Thread t = new Thread(a.runnable);
					t.start();
					LogDetect.send(LogDetect.DataType.specialType,"01160 开线程:","1");*/
			/*	} 
			}*/
			break;
		case R.id.input_sms:
			if (chat_face_container.getVisibility() == View.VISIBLE) {
				chat_face_container.setVisibility(View.GONE);
			}
			if (chat_add_container.getVisibility() == View.VISIBLE) {
				chat_add_container.setVisibility(View.GONE);
			}
			break;
		case R.id.image_face:
			hideSoftInputView();
			tv_voc.setVisibility(View.VISIBLE);
			input.setVisibility(View.VISIBLE);
			tv_voc1.setVisibility(View.GONE);
			shuohua.setVisibility(View.GONE);
			if (chat_add_container.getVisibility() == View.VISIBLE) {
				chat_add_container.setVisibility(View.GONE);
			}
			if (chat_face_container.getVisibility() == View.GONE) {
				chat_face_container.setVisibility(View.VISIBLE);
			} else {
				chat_face_container.setVisibility(View.GONE);
			}
			break;
		case R.id.image_add:
			hideSoftInputView();// 隐藏软键盘
			
			if (chat_face_container.getVisibility() == View.VISIBLE) {
				chat_face_container.setVisibility(View.GONE);
			}
			if (chat_add_container.getVisibility() == View.GONE) {
				chat_add_container.setVisibility(View.VISIBLE);
			} else {
				chat_add_container.setVisibility(View.GONE);
			}
			break;
		case R.id.tv_pic:// 照片
			
			showPopupspWindow_xiangce(chat_main);
			
			break;
		case R.id.tv_camera:// 视频
			showPopupspWindow_shipin(chat_main);
			break;
	/*	case R.id.tv_loc:// 位置，正常情况下是需要定位的，可以用百度或者高德地图，现设置为北京坐标
			sendMsgLocation(Utils.longtitude+","+Utils.latitude);
			//LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"sendLocation: ("+Utils.longtitude+","+Utils.latitude+")");
			break;*/
		case R.id.tv_voc:	
			hideSoftInputView();// 隐藏软键盘
			tv_voc.setVisibility(View.GONE);
			input.setVisibility(View.GONE);
			chat_face_container.setVisibility(View.GONE);
			chat_add_container.setVisibility(View.GONE);
			tv_voc1.setVisibility(View.VISIBLE);
			shuohua.setVisibility(View.VISIBLE);
		break;
				
		case R.id.tv_voc1:
			hideSoftInputView();// 隐藏软键盘
			tv_voc.setVisibility(View.VISIBLE);
			input.setVisibility(View.VISIBLE);
			tv_voc1.setVisibility(View.GONE);
			shuohua.setVisibility(View.GONE);
			break;
		case R.id.button_more_moremodify:
			if(logo.equals("000000")){
				Util.currentfrom="";
					AudioRecoderUtils.stopMusic();
				finish();
			}else{
				hideSoftInputView();
				if (chat_face_container.getVisibility() == View.VISIBLE) {
					chat_face_container.setVisibility(View.GONE);
				} else if (chat_add_container.getVisibility() == View.VISIBLE) {
					chat_add_container.setVisibility(View.GONE);
				} else {
					Util.currentfrom="";
						AudioRecoderUtils.stopMusic();
					finish();
				}
			}
			break;
//			case R.id.send_file:
//				Log.v("TT",Environment.getExternalStorageDirectory().getAbsolutePath());
//				OutgoingFileTransfer outFileTransfer = com.example.tongchengqo.util.Util.imManager.fileTransManager.createOutgoingFileTransfer("13@120.27.98.128/asmack");
//				Log.v("TT","before sendFile");
//				try {
//					outFileTransfer.sendFile(new File(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"hello.txt"), "send");
//				}catch(SmackException e) {
//					Log.e("TT","SmackException: "+e.toString());
//				}
				//Toast.makeText(ChatActivity.this,"发送文件",Toast.LENGTH_SHORT).show();
//				String vocFile = recorder.stopRecord();
//				sendFile.setText("录 音");
//				sendMsgVoc(vocFile);
//				break;
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
	void sendMsgImg(String photoPath) {
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
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
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
	}

	/**
	 * 执行发送消息 语音类型
	 *

	 */
	void sendMsgVoc(String vocPath) {

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

		//LogDetect.send(LogDetect.DataType.basicType,"orignal msg:",message);
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
	}
	
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
		//LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"message: "+message);
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					//LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"before sendMessage()");
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
		updateSession1(Const.MSG_TYPE_TEXT, content,name,logo);
	}

	/**
	 * 执行发送消息 文本类型
	 * 
	 * @param content
	 */
	void sendMsgLocation(String content) {
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
	}

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
		// int type = intent.getIntExtra("type", 0);
		// final int position = intent.getIntExtra("position", 0);
		// if (listMsg.size() <= 0) {
		// return;
		// }
		// final Msg msg = listMsg.get(position);
		// switch (type) {
		// case 1:// 聊天记录操作
		// Builder bd = new AlertDialog.Builder(ChatActivity.this);
		// String[] items = null;
		// if (msg.getType().equals(Const.MSG_TYPE_TEXT)) {
		// items = new String[] { "删除记录", "删除全部记录", "复制文字" };
		// } else {
		// items = new String[] { "删除记录", "删除全部记录" };
		// }
		// bd.setItems(items, new DialogInterface.OnClickListener() {
		// @Override
		// public void onClick(DialogInterface arg0, int arg1) {
		// switch (arg1) {
		// case 0:// 删除
		// listMsg.remove(position);
		// offset = listMsg.size();
		// mLvAdapter.notifyDataSetChanged();
		// msgDao.deleteMsgById(msg.getMsgId());
		// break;
		// case 1:// 删除全部
		// listMsg.removeAll(listMsg);
		// offset = listMsg.size();
		// mLvAdapter.notifyDataSetChanged();
		// msgDao.deleteTableData();
		// break;
		// case 2:// 复制
		// ClipboardManager cmb = (ClipboardManager) ChatActivity.this
		// .getSystemService(ChatActivity.CLIPBOARD_SERVICE);
		// cmb.setText(msg.getContent());
		// Toast.makeText(getApplicationContext(), "已复制到剪切板",
		// Toast.LENGTH_SHORT).show();
		// break;
		// }
		// }
		// });
		// bd.show();
		// break;
		// }
		//
		// }
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
		/*new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				// 让输入框获取焦点
				if(!logo.equals("000000")){
				input.requestFocus();
				}
			}
		}, 100);*/
		super.onResume();
//		IMManager.getInstance().checkConnectStatusAndReconnect();
		IMManager.clientHeartbeat();
		input.setFocusable(false);
		input.setFocusableInTouchMode(true);
		chat_face_container.setVisibility(View.GONE);


	};

	/**
	 * 监听返回键
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
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
				} else if (chat_add_container.getVisibility() == View.VISIBLE) {
					chat_add_container.setVisibility(View.GONE);
				} else {
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
	 * 发送消息
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
		ChatManager chatmanager = Utils.xmppchatmanager;
		//LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"chatmanager: "+chatmanager);
		// Chat chat =chatmanager.createChat(touser + "@" + Const.XMPP_HOST,
		// null);
		Chat chat = chatmanager.createChat(YOU + "@" + Const.XMPP_HOST, null);
		if (chat != null) {
			// chat.sendMessage(content);
			chat.sendMessage(content, I + "@" + Const.XMPP_HOST);
			Log.e("jj", "发送成功");
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
	protected void xiangce() {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_PICK);
		intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);// 使用以上这种模式，并添加以上两句
		startActivityForResult(intent, 0);

	}

	// 得到相册返回data
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

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
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				sendMsgImg(cameraFile);
				//LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"sendCamer: "+cameraFile);
			}
		}
		
		
		if(requestCode == 1006){
			if(data != null){
				Bundle bundle = data.getExtras();
				if(bundle != null){
					//TODO	获取上个页面返回值
					String video_id = bundle.getString("video_id");
					LogDetect.send(DataType.specialType, "01160 video_id",video_id);
					sendMsgVideo(video_id);
				}
			}
		}
	/*	if(requestCode == 1006){
			if(data != null){
				Bundle bundle = data.getExtras();
				if(bundle != null){
					//TODO	获取上个页面返回值
					String video_id = bundle.getString("video_id");
					LogDetect.send(DataType.specialType, "01160 video_id",video_id);
					sendMsgVideo(video_id);
				}
			}
		}*/
		super.onActivityResult(requestCode, resultCode, data);
	}

	// 取得相册图片地址
	@SuppressLint("NewApi")
	protected String upimg(Intent data) {
		if (data == null) {
			Toast.makeText(this, "请选择图片", Toast.LENGTH_LONG).show();
			return "";
		}
		photoUri = data.getData();
		if (photoUri == null) {
			Toast.makeText(this, "选择图片文件时出错", Toast.LENGTH_LONG).show();
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

	}

	@SuppressWarnings("unused")
	public void showPopupspWindow_xiangce(View parent) {
		// 加载布局
		LayoutInflater inflater = LayoutInflater.from(CoustomerActivity.this);
		View layout = inflater.inflate(R.layout.pop_xiangce_01160, null);
	                      
		TextView xuanze,quxiao;
		
		xuanze = (TextView)layout.findViewById(R.id.xuanze);
		xuanze.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				//从手机相册选择
			
				xiangce();
				popupWindow.dismiss();
			}
		});
		
		quxiao = (TextView)layout.findViewById(R.id.quxiao);
		quxiao.setOnClickListener(new OnClickListener() {
			//拍摄
			@Override
			public void onClick(View arg0) {
				
				xiangji();
				popupWindow.dismiss();
			}
		});
		
		
		popupWindow = new PopupWindow(layout, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT, true);
		// 控制键盘是否可以获得焦点
		popupWindow.setFocusable(true);
		WindowManager.LayoutParams lp = CoustomerActivity.this.getWindow().getAttributes();
		lp.alpha = 0.5f;
		CoustomerActivity.this.getWindow().setAttributes(lp);
		popupWindow.setBackgroundDrawable(new BitmapDrawable(null, ""));
		//popupWindow.showAsDropDown(parent, 0, 0);
		popupWindow.showAtLocation(parent, Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
		popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
			public void onDismiss() {
				WindowManager.LayoutParams lp = CoustomerActivity.this.getWindow().getAttributes();
				lp.alpha = 1f;
				CoustomerActivity.this.getWindow().setAttributes(lp);
			}
		});
	}

	@SuppressWarnings("unused")
	public void showPopupspWindow_shipin(View parent) {
		// 加载布局
		LayoutInflater inflater = LayoutInflater.from(CoustomerActivity.this);
		View layout = inflater.inflate(R.layout.pop_video_01160, null);
	                      
		TextView xuanze,quxiao;
		
		xuanze = (TextView)layout.findViewById(R.id.xuanze);
		xuanze.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				//从手机相册选择
				//MediaActivity_01160.startImport(CoustomerActivity.this);
				videoImport();
				popupWindow.dismiss();
			}
		});
		
		quxiao = (TextView)layout.findViewById(R.id.quxiao);
		quxiao.setOnClickListener(new OnClickListener() {
			//拍摄
			@Override
			public void onClick(View arg0) {
				recordVideo();
				popupWindow.dismiss();
			}
		});
		
		
		popupWindow = new PopupWindow(layout, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT, true);
		// 控制键盘是否可以获得焦点
		popupWindow.setFocusable(true);
		WindowManager.LayoutParams lp = CoustomerActivity.this.getWindow().getAttributes();
		lp.alpha = 0.5f;
		CoustomerActivity.this.getWindow().setAttributes(lp);
		popupWindow.setBackgroundDrawable(new BitmapDrawable(null, ""));
		//popupWindow.showAsDropDown(parent, 0, 0);
		popupWindow.showAtLocation(parent, Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
		popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
			public void onDismiss() {
				WindowManager.LayoutParams lp = CoustomerActivity.this.getWindow().getAttributes();
				lp.alpha = 1f;
				CoustomerActivity.this.getWindow().setAttributes(lp);
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
    		   	image_add.setVisibility(View.GONE);
    		  }else {
    		/*	sendshuru("0");*/
    		    send.setVisibility(View.GONE);
    		   	image_add.setVisibility(View.VISIBLE);
    		   	
    		  }
       
        }          
    }; 
	
  
   
	/**
	 * 执行发送消息 文本类型
	 * 
	 * @param content
	 *//*
	void sendshuru(String content) {
		final String message = content + Const.SPLIT + Const.ACTION_INPUT;
		//LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"message: "+message);
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					//LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"before sendMessage()");
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
	}*/

  
   Runnable a =  new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			if(recordState) {

				recordState = false;
			
		/*	sj = endDate.getTime() - curDate.getTime();
			Timing = (int) (sj/1000);
			LogDetect.send(LogDetect.DataType.specialType,"01160 sj:",Timing);*/
			l1.setVisibility(View.GONE);
			/*if(Timing <=1){
				Toast.makeText(ChatActivity.this, "录音时间过短", Toast.LENGTH_SHORT).show();
			}else if(Timing >= 20){
				RecordInfo vocInfo = recorder.stopRecord();
				sendMsgVoc(vocInfo.filePath+":"+vocInfo.time);
				
			}else{
				// 语音
				//if(Timing <= 20){
*/				Toast.makeText(CoustomerActivity.this, "录音时间过长", Toast.LENGTH_SHORT).show();
				AudioRecoderUtils.RecordInfo vocInfo = recorder.stopRecord();
				sendMsgVoc(vocInfo.filePath+":"+vocInfo.time);
				//}/*else{
					
				//}*/
			
				
			//}
			
			}
		}
		
	};
	

	
	
	/**
	 * 播放视频
	 */
	private String[] permissions2 = {Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE};
	private void recordVideo() {
        // 动态申请摄像头权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            for(String permission: permissions2) {
                // 检查该权限是否已经获取
                int i = ContextCompat.checkSelfPermission(this, permission);
                // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
                if (i != PackageManager.PERMISSION_GRANTED) {
                    // 如果没有授予该权限，就去提示用户请求
                    ActivityCompat.requestPermissions(this, permissions2, 321);
                    return;
                }
            }
        }
//        String path = StorageUtils.getCacheDirectory(this).getAbsolutePath() + File.separator + "AliyunEditorDemo"+File.separator;
//        Log.e("TT","path: "+path);
//        String[] eff_dirs = new String[]{
//                null,
//                path + "filter/chihuang",
//                path + "filter/fentao",
//                path + "filter/hailan",
//                path + "filter/hongrun",
//                path + "filter/huibai",
//                path + "filter/jingdian",
//                path + "filter/maicha",
//                path + "filter/nonglie",
//                path + "filter/rourou",
//                path + "filter/shanyao",
//                path + "filter/xianguo",
//                path + "filter/xueli",
//                path + "filter/yangguang",
//                path + "filter/youya",
//                path + "filter/zhaoyang"
//        };
//
//        AliyunSnapVideoParam recordParam = new AliyunSnapVideoParam.Builder()
//                .setResulutionMode(AliyunSnapVideoParam.RESOLUTION_720P)
//                .setRatioMode(AliyunSnapVideoParam.RATIO_MODE_9_16)
//                .setRecordMode(AliyunSnapVideoParam.RECORD_MODE_AUTO)
//                .setFilterList(eff_dirs)
//                .setBeautyLevel(80)
//                .setBeautyStatus(true)
//                .setCameraType(CameraType.FRONT)
//                .setFlashType(FlashType.ON)
//                .setNeedClip(true)
//                .setMaxDuration(30000)
//                .setMinDuration(2000)
//                .setVideQuality(VideoQuality.SSD)
//                .setGop(5)
//                .setVideoBitrate(0)
//                /**
//                 * 裁剪参数
//                 */
//                .setMinVideoDuration(4000)
//                .setMaxVideoDuration(29 * 1000)
//                .setMinCropDuration(3000)
//                .setFrameRate(25)
//                .setCropMode(ScaleMode.PS)
//                .build();
//        AliyunVideoRecorder_01160.startRecordForResult(this,1006,recordParam);
    }

	/**
	 * 执行发送消息 视频类型


	 */
	void sendMsgVideo(String photoPath) {
		Msg msg = getChatInfoTo(photoPath, Const.MSG_TYPE_VIDEO);
		msg.setMsgId(msgDao.insert(msg));
		listMsg.add(msg);
		offset = listMsg.size();
		mLvAdapter.notifyDataSetChanged();
		final String message = photoPath + Const.SPLIT + Const.MSG_TYPE_VIDEO
				+ Const.SPLIT + sd.format(new Date())+ Const.SPLIT+username+Const.SPLIT+headpicture;
		//LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"message: "+message);
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					//LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"before sendMessage()");
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
		updateSession1(Const.MSG_TYPE_VIDEO, "[视频 消息]",name,logo);
	}
	
	@Override
	public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
		switch (requestCode) {
			case 1: {
				if (grantResults.length > 0
						&& grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					// 权限被用户同意，可以去放肆了。
					LogDetect.send(DataType.noType,"01160","再次权限");
				} else {
					// 权限被用户拒绝了，洗洗睡吧。
					Toast.makeText(this,"您已关闭麦克风访问权限",Toast.LENGTH_SHORT).show();
				}
				return;
			}
			case 321:{
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
					if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
						// 判断用户是否 点击了不再提醒。(检测该权限是否还可以申请)
						LogDetect.send(DataType.noType,"01160","再次权限");
					} else {
						Toast.makeText(this, "权限获取成功", Toast.LENGTH_SHORT).show();
					}
					return;
				}
			}
		}
	}

	private void videoImport() {
//		Intent intent = new Intent(this,MediaActivity_01160.class);
//		intent.putExtra(CropKey.VIDEO_RATIO, CropKey.RATIO_MODE_9_16);  // 比例
//		intent.putExtra(CropKey.VIDEO_SCALE,CropKey.SCALE_FILL);        // 画面填充
//		intent.putExtra(CropKey.VIDEO_QUALITY , VideoQuality.SSD);       // 视频质量
//		//String f = frameRateEdit.getText().toString();
//		int frameRate = 25;
////        if(f == null || f.isEmpty()){
////            frameRate = DEFAULT_FRAMR_RATE;
////        }else{
////            frameRate = Integer.parseInt(frameRateEdit.getText().toString());
////        }
//
//		intent.putExtra(CropKey.VIDEO_FRAMERATE,frameRate);
//		//String g = gopEdit.getText().toString();
//		int gop = 125;
////        if(g == null || g.isEmpty()){
////            gop = DEFAULT_GOP;
////        }else{
////            gop = Integer.parseInt(gopEdit.getText().toString());
////        }
//		intent.putExtra(CropKey.VIDEO_GOP,gop);
//		int bitrate = 0;
////        if(!TextUtils.isEmpty(mBitrateEdit.getText())){
////            bitrate = Integer.parseInt(mBitrateEdit.getText().toString());
////        }
//		intent.putExtra(CropKey.VIDEO_BITRATE, bitrate);
//
////            intent.putExtra("width",etWidth.getText().toString());
////            intent.putExtra("height",etHeight.getText().toString());
//		startActivityForResult(intent,1006);
	}

	private String[] mEffDirs;
//	private void initAssetPath(){
//		String path = com.aliyun.common.utils.StorageUtils.getCacheDirectory(this).getAbsolutePath() + File.separator+ Common.QU_NAME + File.separator;
//		File filter = new File(new File(path), "filter");
//
//		String[] list = filter.list();
//		if(list == null || list.length == 0){
//			return ;
//		}
//		mEffDirs = new String[list.length + 1];
//		mEffDirs[0] = null;
//		for(int i = 0; i < list.length; i++){
//			mEffDirs[i + 1] = filter.getPath() + "/" + list[i];
//		}
////        mEffDirs = new String[]{
////                null,
////                path + "filter/chihuang",
////                path + "filter/fentao",
////                path + "filter/hailan",
////                path + "filter/hongrun",
////                path + "filter/huibai",
////                path + "filter/jingdian",
////                path + "filter/maicha",
////                path + "filter/nonglie",
////                path + "filter/rourou",
////                path + "filter/shanyao",
////                path + "filter/xianguo",
////                path + "filter/xueli",
////                path + "filter/yangguang",
////                path + "filter/youya",
////                path + "filter/zhaoyang",
////                path + "filter/mosaic",
////                path + "filter/blur",
////                path + "filter/bulge",
////                path + "filter/false",
////                path + "filter/gray",
////                path + "filter/haze",
////                path + "filter/invert",
////                path + "filter/miss",
////                path + "filter/pixellate",
////                path + "filter/rgb",
////                path + "filter/sepiatone",
////                path + "filter/threshold",
////                path + "filter/tone",
////                path + "filter/vignette"
////
////        };
//	}

	private void videoRecord() {

//		initAssetPath();
//
//		int min = 3000;
//		int max = 30000;
//		int gop = 5;
//		int bitrate = 0;
////        if(minDurationEt.getText() != null && !minDurationEt.getText().toString().isEmpty()){
////            try {
////                min = Integer.parseInt(minDurationEt.getText().toString()) * 1000;
////            }catch (Exception e){
////                Log.e("ERROR","input error");
////            }
////        }
////        if(maxDurationEt.getText() != null && !maxDurationEt.getText().toString().isEmpty()){
////            try {
////                max = Integer.parseInt(maxDurationEt.getText().toString()) * 1000;
////            }catch (Exception e){
////                Log.e("ERROR","input error");
////            }
////        }
////        if(gopEt.getText() != null && !gopEt.getText().toString().isEmpty()){
////            try {
////                gop = Integer.parseInt(gopEt.getText().toString());
////            }catch (Exception e){
////                Log.e("ERROR","input error");
////            }
////        }
////        if(!TextUtils.isEmpty(mBitrateEdit.getText())){
////            try{
////                bitrate = Integer.parseInt(mBitrateEdit.getText().toString());
////            }catch (Exception e){
////                e.printStackTrace();
////            }
////        }
//		AliyunSnapVideoParam recordParam = new AliyunSnapVideoParam.Builder()
//				.setResolutionMode(AliyunSnapVideoParam.RESOLUTION_720P)    // 分辨率
//				.setRatioMode(AliyunSnapVideoParam.RATIO_MODE_9_16)     // 比例
//				.setRecordMode(AliyunSnapVideoParam.RECORD_MODE_AUTO)
//				.setFilterList(mEffDirs)
//				.setBeautyLevel(80)
//				.setBeautyStatus(true)
//				.setCameraType(CameraType.FRONT)
//				.setFlashType(FlashType.ON)
//				.setNeedClip(true)
//				.setMaxDuration(max)
//				.setMinDuration(min)
//				.setVideoQuality(VideoQuality.SSD)   // 视频质量
//				.setGop(gop)
//				.setVideoBitrate(bitrate)
//				.setVideoCodec(VideoCodecs.H264_HARDWARE)   // 视频编码
//				/**
//				 * 裁剪参数
//				 */
//				.setMinVideoDuration(4000)
//				.setMaxVideoDuration(29 * 1000)
//				.setMinCropDuration(3000)
//				.setFrameRate(25)
//				.setCropMode(ScaleMode.PS)
//				.build();
//		AliyunVideoRecorder_01160.startRecordForResult(this,1006,recordParam);
	}
}
