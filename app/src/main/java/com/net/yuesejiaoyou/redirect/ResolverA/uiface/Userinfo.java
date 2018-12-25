package com.net.yuesejiaoyou.redirect.ResolverA.uiface;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
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
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack.SmackException;
import com.net.yuesejiaoyou.classroot.interface4.openfire.uiface.ChatActivity;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.redirect.ResolverA.getset.User_data;
import com.net.yuesejiaoyou.redirect.ResolverA.interface3.UsersThread_01066A;
import com.net.yuesejiaoyou.redirect.ResolverA.interface3.UsersThread_01160A;
import com.net.yuesejiaoyou.redirect.ResolverA.interface3.UsersThread_01162A;
import com.net.yuesejiaoyou.redirect.ResolverA.interface4.VMyAdapterqm_01066;
import com.net.yuesejiaoyou.redirect.ResolverB.interface3.UsersThread_01158B;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.agora.P2PVideoConst;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.agora.guke.GukeActivity;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.agora.guke.ZhuboInfo;
import com.net.yuesejiaoyou.redirect.ResolverC.uiface.Vliao_hisqinmibang_01178;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.ShareHelp;
import com.net.yuesejiaoyou.redirect.ResolverD.uiface.Chongzhi_01178;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

//import com.facebook.share.widget.ShareButton;
//import com.facebook.share.widget.ShareDialog;
//import com.net.yuesejiaoyou.redirect.ResolverB.uiface.guke_bk;
//import com.net.yuesejiaoyou.redirect.ResolverB.interface4.agora.guke_bk;

/***************************
 * 本类是
 **************************/
public class Userinfo extends AppCompatActivity implements View.OnClickListener{
	private String msgBody;
	public String yid,msgbody;
	private int yue;//用户V币余额
	private ArrayList mListImage;
	private boolean   guanzhuable=true;
	private int lastVisibleItem = 0,isguanzhu=0,fannum=0;
	private String nicheng,headpic,userid="";

	private GridLayoutManager mLayoutManager;
	private RelativeLayout layout;
	private LinearLayout exit_del,exit_queding;
	private RelativeLayout ll_vhome_avchat;
	private SimpleDateFormat sd  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");;
	private TextView user_exit;
	private ImageView photo,fanhui,erweima,iv_zt_img,ib_focus;
	private TextView nickname,wodeqianbao,vhome_tv_status,text_view_price,tv_dv,flow_img,tv_focus_count;
	private DisplayImageOptions options=null;
	private RatingBar ratingbar;
	private PopupWindow mPopWindow;
	private  LinearLayout send_red;
	private PopupWindow popupWindow;
	private Context mContext;
	MsgOperReciver1 msgOperReciver1;
	reciever reviever;
	/**
	 * 通用的ToolBar标题
	 */
	private TextView commonTitleTv;
	/**
	 * 通用的ToolBar
	 */
	private Toolbar commonTitleTb;
	/**
	 * 内容区域
	 */
	private RelativeLayout content;
	private RelativeLayout relative_layout_intimacy;
	private View.OnClickListener relative_layout_intimacy_listener;

	//ShareDialog shareDialog;
	//CallbackManager callbackManager;
	reciever1	reviever1;
	//ShareButton share_btn=null;

	private User_data userInfo;

	/********************************
	 *
	 * @param savedInstanceState
	 *******************************/

	//送红包
	private ChatMsgDao msgDao;
	private List<Msg> listMsg = new ArrayList<Msg>();
	private SessionDao sessionDao;
	private String I,price;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		I = Util.userid;
		msgDao = new ChatMsgDao(this);
		sessionDao = new SessionDao(this);

		/////////////////////////////
		LogDetect.send(LogDetect.DataType.specialType,"Userinfo:","444444444444");
		//////////////////////////////
		mContext = Userinfo.this;
		//无title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//全屏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
				WindowManager.LayoutParams. FLAG_FULLSCREEN);
		setContentView(R.layout.activity_new_v_homepage);
		options = new DisplayImageOptions.Builder().cacheInMemory(true)
				.cacheOnDisc(true).bitmapConfig(Bitmap.Config.RGB_565).build();
		wodeqianbao= (TextView) findViewById(R.id.wodeqianbao);//昵称
		fanhui= (ImageView) findViewById(R.id.fanhui);//返回
		fanhui.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});
		erweima= (ImageView) findViewById(R.id.erweima);//二维码

		//分享功能
		erweima.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				ShareHelp shareHelp1=new ShareHelp();
				shareHelp1.showShare(Userinfo.this,Util.invite_num);
			}
		});

		/////////////////////////////
		LogDetect.send(LogDetect.DataType.specialType,"Userinfo:","55555555555");
		//////////////////////////////
		iv_zt_img= (ImageView) findViewById(R.id.iv_zt_img);//状态图片
		vhome_tv_status= (TextView) findViewById(R.id.vhome_tv_status);//状态文字
		text_view_price= (TextView) findViewById(R.id.text_view_price);//价格
		tv_dv= (TextView) findViewById(R.id.tv_dv);//昵称
		flow_img= (TextView) findViewById(R.id.flow_img);//介绍
		ib_focus= (ImageView) findViewById(R.id.ib_focus);//关注图片
		/////////////////////////////////////////////

		ib_focus.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (guanzhuable){
					guanzhuable=false;
					String mode = "guanzhu";
					//userid，页数，男女
					String[] params = {"13",userid,isguanzhu+""};
					UsersThread_01066A b = new UsersThread_01066A(mode, params, handler);
					Thread thread = new Thread(b.runnable);
					thread.start();
					/////////////////////////////
					LogDetect.send(LogDetect.DataType.specialType,"Userinfo:A区 转2调 B区","关注图片");
					//////////////////////////////
				}
			}
		});
		/////////////////////////////
		LogDetect.send(LogDetect.DataType.specialType,"Userinfo:","6666666");
		//////////////////////////////

		tv_focus_count= (TextView) findViewById(R.id.tv_focus_count);//粉丝数
		ratingbar= (RatingBar) findViewById(R.id.ratingbar);//星星
		relative_layout_intimacy = (RelativeLayout) findViewById(R.id.relative_layout_intimacy);
		relative_layout_intimacy_listener = new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				/////////////////////////////
				LogDetect.send(LogDetect.DataType.specialType,"Userinfo:A区 转2调 C区","他的亲密榜");
				//////////////////////////////
				Intent intent = new Intent();
				intent.setClass(mContext, Vliao_hisqinmibang_01178.class);
				Bundle bundle = new Bundle();
				bundle.putString("id", userid);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		};
		relative_layout_intimacy.setOnClickListener(relative_layout_intimacy_listener);

		/////////////////////////////
		LogDetect.send(LogDetect.DataType.specialType,"Userinfo:","777777777");
		//////////////////////////////

		ll_vhome_avchat = (RelativeLayout)findViewById(R.id.ll_vhome_avchat);
		ll_vhome_avchat.setOnClickListener(this);

		//01165-----打赏红包////////////////////////////////////////////////////
		send_red = (LinearLayout)findViewById(R.id.send_red);
		send_red.setOnClickListener(this);
		/////////////////////////////////////////////////////////////////
		LinearLayout ll_vhome_imchat  = (LinearLayout) findViewById(R.id.ll_vhome_imchat);
		ll_vhome_imchat.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/////////////////////////////
				LogDetect.send(LogDetect.DataType.specialType,"Userinfo:","私信 转1调 聊天");
				//////////////////////////////
				Intent intentthis = new Intent(Userinfo.this, ChatActivity.class);
				intentthis.putExtra("id", userid);
				intentthis.putExtra("name",  nicheng);
				intentthis.putExtra("headpic", headpic);
				startActivity(intentthis);

			}
		});
		//RecyclerView gridView=(RecyclerView)findViewById(R.id.recycler_intimacy);

		commonTitleTb = (Toolbar) findViewById(R.id.toolbar);
		AppBarLayout mAppBarLayout= (AppBarLayout) findViewById(R.id.appbar);
		/////////////////////////////
		LogDetect.send(LogDetect.DataType.specialType,"Userinfo:","8888888");
		//////////////////////////////
		//////////////////////////////////////////////
		mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
			@Override
			public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
				int scrollRangle = appBarLayout.getTotalScrollRange();
				//初始verticalOffset为0，不能参与计算。
				Log.d("1111", "scrollRangle: " + scrollRangle);
				if (verticalOffset == 0) {
					wodeqianbao.setAlpha(0.0f);
				} else {
					//保留一位小数
					Log.d("1111", "alpha: " + verticalOffset);
					if (verticalOffset<-700){
						Resources resources = getBaseContext().getResources();
//						Drawable imageDrawable = resources.getDrawable(R.drawable.arrow_left_gray); //图片在drawable目录下
//						fanhui.setBackground(imageDrawable);
						fanhui.setImageResource(R.drawable.arrow_left_gray);
						erweima.setImageResource(R.drawable.big_v_home_business_card_gray);
						Drawable imageDrawable1 = resources.getDrawable(R.drawable.big_v_home_mask_yb); //图片在drawable目录下
						commonTitleTb.setBackground(imageDrawable1);
					}else{
						Resources resources = getBaseContext().getResources();
//						Drawable imageDrawable = resources.getDrawable(R.drawable.arrow_left_white); //图片在drawable目录下
//						fanhui.setBackground(imageDrawable);
						fanhui.setImageResource(R.drawable.arrow_left_white);
						//Drawable imageDrawable1 = resources.getDrawable(R.drawable.big_v_home_business_card); //图片在drawable目录下
						erweima.setImageResource(R.drawable.big_v_home_business_card);
						Drawable imageDrawable1 = resources.getDrawable(R.drawable.big_v_home_mask_s); //图片在drawable目录下
						commonTitleTb.setBackground(imageDrawable1);
					}
					DecimalFormat df=new DecimalFormat("0.00");
					//Log.d("1111", "Math: " + Math.abs(Float.parseFloat(df.format((float)verticalOffset/scrollRangle))));
					float alpha = Math.abs(Float.parseFloat(df.format((float)verticalOffset/scrollRangle)));
					//Log.d("1111", "alpha: " + alpha);
					wodeqianbao.setAlpha(alpha);
				}
			}
		});
		/////////////////////////////
		LogDetect.send(LogDetect.DataType.specialType,"Userinfo:","9999999");
		//////////////////////////////
		reviever = new reciever();
		IntentFilter filter1 = new IntentFilter("5");
		registerReceiver(reviever, filter1);


		reviever1 = new reciever1();
		IntentFilter filter2 = new IntentFilter("99");
		registerReceiver(reviever1, filter2);

       	Intent intent9=getIntent();
      	Bundle bundle=intent9.getExtras();
      	userid=bundle.getString("id");
		String mode = "userinfo";
		String[] params = {"13",userid};
		UsersThread_01066A b = new UsersThread_01066A(mode, params, handler);
		Thread thread = new Thread(b.runnable);
		thread.start();

		//01165--------------------------------------------开线程，请求用户V币余额，-----------------------01165
	/*	String mode1 = "yue_search";
		//userid，页数，男女
		String[] params1 = {Util.userid};
		UsersThread_01165 b1 = new UsersThread_01165(mode1, params1, handler);
		Thread thread1 = new Thread(b1.runnable);
		thread1.start();*/
		//////////////////////////////////////////////////////////////////////////////////////
		 msgOperReciver1 = new MsgOperReciver1();
		IntentFilter intentFilter = new IntentFilter("userguanzhu");
		registerReceiver(msgOperReciver1, intentFilter);
		/////////////////////////////
		LogDetect.send(LogDetect.DataType.specialType,"Userinfo:","666666666");
		//////////////////////////////
		
	}

	/*************************************
	 *
	 ************************************/
	private class MsgOperReciver1 extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			String msgBody=intent.getStringExtra("guanzhu");
			///////////////////////////////
			LogDetect.send(LogDetect.DataType.specialType,"downorup:",msgBody);
			if (msgBody.equals(userid)) {
				handler.sendMessage(handler.obtainMessage(1000, (Object)msgBody));
			}
		}
	}

	/*************************************
	 *
	 ************************************/
	public   Bitmap returnBitMap(String url) {
		URL myFileUrl = null;
		Bitmap bitmap = null;

		try {
			myFileUrl = new URL(url);
			HttpURLConnection conn;

			conn = (HttpURLConnection) myFileUrl.openConnection();
			conn.setDoInput(true);
			conn.connect();
			InputStream is = conn.getInputStream();
			bitmap = BitmapFactory.decodeStream(is);

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bitmap;
	}

	/*************************************
	 *
	 ************************************/
	public void saveFile(Bitmap bm, String fileName) throws IOException {
		String path = Environment.getExternalStorageDirectory() +"/revoeye/";
		File dirFile = new File(path);
		if(!dirFile.exists()){
			dirFile.mkdir();
		}
		File myCaptureFile = new File(path + fileName);
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
		bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
		bos.flush();
		bos.close();
	}


	/*************************************
	 *
	 ************************************/
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
				///////////////////////////
				case 1000:
					isguanzhu=1;
					ib_focus.setBackgroundResource(R.drawable.guanzhu_on);
					fannum=fannum+1;
					tv_focus_count.setText(fannum+" 粉丝");
					break;
				case 0:
					break;
				/////////////////////////////
				case 500:
					String json11 = (String)msg.obj;
					try { //如果服务端返回1，说明个人信息修改成功了
						JSONObject jsonObject = new JSONObject(json11);
						isguanzhu= Integer.parseInt( jsonObject.getString("success"));
						///////////////////////////////
						LogDetect.send(LogDetect.DataType.specialType, "yue_____： ",isguanzhu);
						if (isguanzhu==0){
							ib_focus.setBackgroundResource(R.drawable.guanzhu);
							fannum=fannum-1;
							tv_focus_count.setText(fannum+" 粉丝");
						}else{
							ib_focus.setBackgroundResource(R.drawable.guanzhu_on);
							fannum=fannum+1;
							tv_focus_count.setText(fannum+" 粉丝");
						}

						Intent intent = new Intent("userinfoguanzhu");//
						Bundle bundle = new Bundle();
						bundle.putString("guanzhu", userid);
						bundle.putInt("isguanzhu", isguanzhu);
						intent.putExtras(bundle);
						Userinfo.this.sendBroadcast(intent);

						guanzhuable=true;
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}


					break;
				/////////////////////////////////////////
				case 201:
					User_data userinfo = (User_data) msg.obj;
					userInfo = userinfo;
					/////////////////////////////
					LogDetect.send(LogDetect.DataType.specialType,"Userinfo:","121212212"+userinfo.getAddress());
					//////////////////////////////
					wodeqianbao= (TextView) findViewById(R.id.wodeqianbao);//昵称
					nicheng=userinfo.getNickname();
					headpic=userinfo.getPhoto();
					userid=String.valueOf(userinfo.getId());
					wodeqianbao.setText(userinfo.getNickname());
					tv_dv.setText(userinfo.getNickname());
					fanhui= (ImageView) findViewById(R.id.fanhui);//返回
					erweima= (ImageView) findViewById(R.id.erweima);//二维码
                    //得到AssetManager
					AssetManager mgr=Userinfo.this.getAssets();

					//根据路径得到Typeface
					Typeface tf= Typeface.createFromAsset(mgr, "fonts/arialbd.ttf");


					vhome_tv_status.setTypeface(tf);
					if (userinfo.getOnline()==1){
						iv_zt_img.setBackgroundResource(R.drawable.zt_zaixian);
						vhome_tv_status.setText("在线");

					}else if (userinfo.getOnline()==2){
						iv_zt_img.setBackgroundResource(R.drawable.zt_zailiao);
						vhome_tv_status.setText("在聊");
					}else if (userinfo.getOnline()==3){
						iv_zt_img.setBackgroundResource(R.drawable.zt_wurao);
						vhome_tv_status.setText("勿扰");
					}else {
						iv_zt_img.setBackgroundResource(R.drawable.zt_lixian);
						vhome_tv_status.setText("离线");
					}

					ratingbar.setRating((float) userinfo.getStar());

					if (userinfo.getIslike()==0){
						ib_focus.setBackgroundResource(R.drawable.guanzhu);
					}else{
						ib_focus.setBackgroundResource(R.drawable.guanzhu_on);
					}
					isguanzhu=userinfo.getIslike();
					flow_img.setText(userinfo.getLab_tab());
					text_view_price.setText(userinfo.getPrice());
					tv_focus_count.setText(userinfo.getFans_num()+" 粉丝");
					fannum=Integer.parseInt(userinfo.getFans_num());
					//实例化Banner
					Banner banner = (Banner) findViewById(R.id.header);
					//设置Banner样式
					banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
					//设置图片加载器

					//实例化图片集合
					mListImage = new ArrayList<>();
					//实例化Title集合
					ArrayList mListTitle = new ArrayList<>();
					String[] a=userinfo.getPicture().split(",");
					for (int i=0;i<a.length;i++){
						mListImage.add(a[i]);
						mListTitle.add("");
					}

					//设置Banner图片集合
					banner.setImages(mListImage);
					banner.setImageLoader(new GlideImageLaoder());
					//设置Banner动画效果
					banner.setBannerAnimation(Transformer.Tablet);

					//设置Banner标题集合（当banner样式有显示title时）
					banner.setBannerTitles(mListTitle);
					//设置轮播时间
					banner.setDelayTime(3000);
					banner.setBackgroundColor(Userinfo.this.getResources().getColor(R.color.white));
					//设置指示器位置（当banner模式中有指示器时）
					banner.setIndicatorGravity(BannerConfig.CIRCLE_INDICATOR);
					//Banner设置方法全部调用完毕时最后调用
					banner.start();
					ViewPager viewpager= (ViewPager) findViewById(R.id.viewpager);
					//构造适配器
					List<Fragment> fragments=new ArrayList<Fragment>();
					fragments.add(new VFragmentzl_1_01066(userinfo));
					fragments.add(new VideoFragment_1_01066(userinfo));
					FragAdapter adapter = new FragAdapter(getSupportFragmentManager(), fragments);
					viewpager.setAdapter(adapter);

					TabLayout mTabLayout= (TabLayout) findViewById(R.id.tabs);
					mTabLayout.setupWithViewPager(viewpager);

					LinearLayout linearLayout = (LinearLayout) mTabLayout.getChildAt(0);
					linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);

					linearLayout.setDividerDrawable(ContextCompat.getDrawable(Userinfo.this,
							R.drawable.shuxian));
					linearLayout.setDividerPadding(30);
					setIndicator(Userinfo.this, mTabLayout,40, 40);
					RecyclerView gridView=(RecyclerView)findViewById(R.id.recycler_intimacy);


					if (userinfo.getLikep().equals("")){

					}else{
						String[] pic=userinfo.getLikep().split(",");
						VMyAdapterqm_01066 adapter1 = new VMyAdapterqm_01066(null, Userinfo.this, true,pic,userid,relative_layout_intimacy_listener);

						mLayoutManager = new GridLayoutManager(Userinfo.this, pic.length);
						gridView.setLayoutManager(mLayoutManager);
						gridView.setAdapter(adapter1);
					}

					break;
				/////////////////////////////////////////
				case 230:
					String json = (String)msg.obj;
					//LogDetect.send(LogDetect.DataType.specialType,"01160:",msg);
					if(!json.isEmpty()){
						try {
							JSONObject jsonObject = new JSONObject(json);
							///////////////////////////////
							LogDetect.send(LogDetect.DataType.specialType,"01160:",jsonObject);
							//如果返回1，说明成功了
							String success_ornot=jsonObject.getString("success");
							///////////////////////////////
							LogDetect.send(LogDetect.DataType.specialType,"01160:",success_ornot);
							if(success_ornot.equals("1")){

								///////////////////////////////A区进入B区 一对一视频
								/////////////////////////////////
								SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								final String timestamp = new Date().getTime()+"";
								final String message = "邀0请1视2频" + Const.SPLIT + Const.ACTION_MSG_ONEINVITE
										+ Const.SPLIT + timestamp+ Const.SPLIT+ com.net.yuesejiaoyou.classroot.interface4.util.Util.nickname+Const.SPLIT+ com.net.yuesejiaoyou.classroot.interface4.util.Util.headpic;
								LogDetect.send(LogDetect.DataType.noType,"这是-------1",message);
								if(userid.equals(Util.userid)){
									LogDetect.send(LogDetect.DataType.noType,"这是-------111",message);
								}else{
//									new Thread(new Runnable() {
//										@Override
//										public void run() {
//											try {
//												LogDetect.send(LogDetect.DataType.noType,"这是什么YouId",userid);
//												Utils.sendmessage(Utils.xmppConnection, message,userid);
												//AgoraVideoManager.startVideo(getApplication(), timestamp, false);	// 发出邀请后立即进入房间
												// 发出邀请直接跳转到音乐播放页面
												if(userInfo != null) {
//													Intent intent = new Intent();
//													intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//													intent.setClass(mContext.getApplicationContext(),
//															guke_bk.class);
													yid = userInfo.getId()+"";
//													Bundle bundle = new Bundle();
//													bundle.putString("yid_guke", yid);
//													LogDetect.send(LogDetect.DataType.specialType, "01160 主播id跳转到顾客页面 yid:", yid);
//													bundle.putString("msgbody", "" + Const.SPLIT + "" + Const.SPLIT + "" + Const.SPLIT +userInfo.getNickname()+Const.SPLIT+userInfo.getPhoto());
//													bundle.putString("roomid", timestamp);
//													intent.putExtras(bundle);

//													mContext.startActivity(intent);


													Log.v("TTT","before pushp2pvideo");

													// 插入一对一请求记录
//													String mode2 = "pushp2pvideo";
//													String[] paramsMap2 = {"",Util.userid, Util.nickname, Util.headpic,yid,timestamp};
//													UsersThread_01158B a2 = new UsersThread_01158B(mode2,paramsMap2,handler);
//													Thread c2 = new Thread(a2.runnable);
//													c2.start();
//													Log.v("TTT","after pushp2pvideo");

													//guke_bk.startGukeActivity(yid, userInfo.getNickname(), userInfo.getPhoto(), timestamp, Userinfo.this);
													GukeActivity.startCallZhubo(Userinfo.this, new ZhuboInfo(yid,userInfo.getNickname(),userInfo.getPhoto(),timestamp, P2PVideoConst.GUKE_CALL_ZHUBO, P2PVideoConst.NONE_YUYUE));
												}
//											} catch (XMPPException | SmackException.NotConnectedException e) {
//												e.printStackTrace();
//												Looper.prepare();
//												Looper.loop();
//											}
//										}
//									}).start();
								}/////////////////////////////A区进入B区 一对一视频
								 //////////////////////////////////////////////////
							}//否则失败了
							else if(success_ornot.equals("2")){
								showPopupspWindow_reservation(findViewById(R.id.linear_load_visible),2);
								Toast.makeText(Userinfo.this, "主播忙碌，请稍后再试", Toast.LENGTH_SHORT).show();

							}/*else if(success_ornot.equals("0")){

							}*/else if(success_ornot.equals("3")){
								showPopupspWindow_reservation(findViewById(R.id.linear_load_visible),3);
								Toast.makeText(Userinfo.this, "主播设置勿打扰，请稍后再试", Toast.LENGTH_SHORT).show();

							}else if(success_ornot.equals("4")){
								showPopupspWindow_reservation(findViewById(R.id.linear_load_visible),4);
								Toast.makeText(Userinfo.this, "主播不在线", Toast.LENGTH_SHORT).show();

							}else if(success_ornot.equals("0")){
								//Toast.makeText(Userinfo.this, "您的余额不足", Toast.LENGTH_SHORT).show();
								showPopupspWindow_chongzhi(wodeqianbao);
							}else if(success_ornot.equals("5")){
								Toast.makeText(Userinfo.this, "主播被封禁", Toast.LENGTH_SHORT).show();

							}else if(success_ornot.equals("6")){
								Toast.makeText(Userinfo.this, "您已被对方拉黑", Toast.LENGTH_SHORT).show();

							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}else{
						Toast.makeText(Userinfo.this, "网络出错", Toast.LENGTH_SHORT).show();
					}
					ll_vhome_avchat.setClickable(true);

					break;
					//------01165----------------------获得V币余额-------------------------01165
				//////////////////////////////////////////////
				case 100:
					String json1 = (String)msg.obj;
					try { //如果服务端返回1，说明个人信息修改成功了
						JSONObject jsonObject = new JSONObject(json1);
						String str =  jsonObject.getString("success");
						///////////////////////////////
						LogDetect.send(LogDetect.DataType.specialType, "yue_____： ",str);
						yue = Integer.parseInt(str);				
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				/////////////////////////////////////////////////
				case 101://红包打赏
					String json2 = (String)msg.obj;
					if(!json2.isEmpty()){
						try { //如果服务端返回1，说明个人信息修改成功了
						//Reward Success-----打赏成功 
							JSONObject jsonObject = new JSONObject(json2);
							///////////////////////////////
							LogDetect.send(LogDetect.DataType.specialType, "yue_____： ",jsonObject.getString("success"));
							if(jsonObject.getString("success").equals("1")){
								sendSongLi("["+"☆"+Util.nickname+"给"+nicheng+"赠送了"+price+"元红包☆"+"]");
								Toast.makeText(Userinfo.this, "打赏成功 ", Toast.LENGTH_SHORT).show();
							}else{
								showPopupspWindow_chongzhi(wodeqianbao);
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
					}
					}else{
						Toast.makeText(Userinfo.this, "打赏失败，请检查网络连接", Toast.LENGTH_SHORT).show();
					}
					
					break;
				/////////////////////////////////////////////////
				case 210:
					String json_reservation = (String) (msg).obj;
					///////////////////////////////
					LogDetect.send(LogDetect.DataType.specialType,"01160:",msg);
					if(!json_reservation.isEmpty()){
						try {
							JSONObject jsonObject1 = new JSONObject(json_reservation);
							///////////////////////////////
							LogDetect.send(LogDetect.DataType.specialType,"01160:",jsonObject1);
							//预约
							String success_ornot=jsonObject1.getString("success");
							///////////////////////////////
							LogDetect.send(LogDetect.DataType.specialType,"01160 success_ornot:",success_ornot);
							if(success_ornot.equals("-2")){
								Toast.makeText(mContext, "余额不足，无法预约", Toast.LENGTH_SHORT).show();
							}else if(success_ornot.equals("-1")){
								Toast.makeText(mContext, "已预约成功，无法再次预约", Toast.LENGTH_SHORT).show();
							}else{

								Util.sendMsgText("『"+Util.nickname+"』 Appointment is successful",userid);

								Toast.makeText(mContext, "预约成功,消费"+success_ornot+"悦币", Toast.LENGTH_SHORT).show();
							}
						}catch(JSONException e){
							e.printStackTrace();
						}
					}else{
						Toast.makeText(Userinfo.this, "预约失败，请检查网络连接", Toast.LENGTH_SHORT).show();
					}
					break;

			}
		}
	};



	/*************************************
	 *
	 ************************************/
	public static void setIndicator(Context context, TabLayout tabs, int leftDip, int rightDip) {
		Class<?> tabLayout = tabs.getClass();
		Field tabStrip = null;
		try {
			tabStrip = tabLayout.getDeclaredField("mTabStrip");
			tabStrip.setAccessible(true);
			LinearLayout ll_tab = null;
			try {
				ll_tab = (LinearLayout) tabStrip.get(tabs);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			int left = (int) (getDisplayMetrics(context).density * leftDip);
			int right = (int) (getDisplayMetrics(context).density * rightDip);
			for (int i = 0; i < ll_tab.getChildCount(); i++) {
				View child = ll_tab.getChildAt(i);
				child.setPadding(0, 0, 0, 0);
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
				params.leftMargin = left;
				params.rightMargin = right;
				child.setLayoutParams(params);
				child.invalidate();
			}
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
	}
	/*************************************
	 *
	 ************************************/
	public static DisplayMetrics getDisplayMetrics(Context context) {
		DisplayMetrics metric = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metric);
		return metric;
	}
	/*************************************
	 *
	 ************************************/
	public static float getPXfromDP(float value, Context context) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value,
				context.getResources().getDisplayMetrics());
	}
	/*************************************
	 *
	 ************************************/
	@Override
	public void onClick(View view) {
		switch (view.getId()){
			//////////////////////////////////
			case R.id.ll_vhome_avchat:
				//点击通话

				// 主播不能发起一对一视频
				if(!"0".equals(Util.iszhubo)) {
					Toast.makeText(this, "主播不能跟主播通话", Toast.LENGTH_SHORT).show();
					break;
				}

				String mode1 = "zhubo_online";
				String[] paramsMap1 = {"",Util.userid,userid};
				LogDetect.send(LogDetect.DataType.specialType, "查看信息： ","----------"+Util.userid+"---------"+userid);
				UsersThread_01158B a = new UsersThread_01158B(mode1,paramsMap1,handler);//230
				Thread c = new Thread(a.runnable);
				c.start();
				ll_vhome_avchat.setClickable(false);
				///////////////////////////////
				LogDetect.send(LogDetect.DataType.specialType, "准备通话","禁止点击");
				break;
				//红包打赏
			////////////////////////////////////
			case R.id.send_red:
				///////////////////////////////
		        LogDetect.send(LogDetect.DataType.specialType, "打赏红包： ","-------------------");
		        showPopupspWindow_sendred(send_red);
				break;
		}
	}
	/*************************************
	 *
	 ************************************/
	public class FragAdapter extends FragmentPagerAdapter {

		private List<Fragment> mFragments;

		/***
		 *
		 **/
		public FragAdapter(FragmentManager fm, List<Fragment> fragments) {
			super(fm);
			// TODO Auto-generated constructor stub
			mFragments=fragments;
		}
		/***
		 *
		 **/
		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			return mFragments.get(arg0);
		}
		/***
		 *
		 **/
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mFragments.size();
		}
		/***
		 *
		 **/
		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
				case 0:
					return "资料";
				case 1:
					return "视频";
				default:
					return "资料";
			}
		}


	}

	/*************************************
	 *
	 ************************************/
	public class GlideImageLaoder extends com.youth.banner.loader.ImageLoader {

		@Override
		public void displayImage(Context context, Object path, ImageView imageView) {
			/**
			 注意：
			 1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
			 2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
			 传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
			 切记不要胡乱强转！
			 */

			//第一种方法：Glide 加载图片简单用法
			//Glide.with(context).load(path).into(imageView);

			ImageLoader.getInstance().displayImage(
					(String) path, imageView,
					options);


			//第二种方法：Picasso 加载图片简单用法
			//Picasso.with(context).load(path).into(imageView);

			//第三种方法：fresco加载图片简单用法，记得要写下面的createImageView方法
			//Uri uri = Uri.parse((String) path);
			//imageView.setImageURI(uri);
		}

		//提供createImageView 方法，如果不用可以不重写这个方法，主要是方便自定义ImageView的创建
		//////////////////////////////////////////////
		@Override
		public ImageView createImageView(Context context) {

			return null;
		}
	}




	/*************************************
	 *红包打赏
	 ************************************/
	public void showPopupspWindow_sendred(View parent) {
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.red_choose_01165, null);
		TextView cancel = (TextView)layout.findViewById(R.id.cancel);
		///////////////////////////////////////
		cancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				popupWindow.dismiss();

			}
		});
		//开线程，添加V币
		TextView coin1 = (TextView)layout.findViewById(R.id.coin1);
		///////////////////////////////////////
		coin1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				songhongbao(9);
				popupWindow.dismiss();

			}
		});
		TextView coin2 = (TextView)layout.findViewById(R.id.coin2);
		///////////////////////////////////////
		coin2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				songhongbao(18);
				popupWindow.dismiss();
				//			songhongbao(18);
			}
		});		
		TextView coin3 = (TextView)layout.findViewById(R.id.coin3);
		///////////////////////////////////////
		coin3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				songhongbao(66);
				popupWindow.dismiss();
				//			songhongbao(66);
			}
		});
		TextView coin4 = (TextView)layout.findViewById(R.id.coin4);
		///////////////////////////////////////
		coin4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				songhongbao(99);
				popupWindow.dismiss();
				//			songhongbao(99);
			}
		});
		TextView coin5 = (TextView)layout.findViewById(R.id.coin5);
		///////////////////////////////////////
		coin5.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				songhongbao(188);
				popupWindow.dismiss();
				//			songhongbao(188);
			}
		});
		TextView coin6 = (TextView)layout.findViewById(R.id.coin6);
		///////////////////////////////////////
		coin6.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				songhongbao(520);
				popupWindow.dismiss();
				//			songhongbao(520);
			}
		});
		TextView coin7 = (TextView)layout.findViewById(R.id.coin7);
		///////////////////////////////////////
		coin7.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				songhongbao(888);
				popupWindow.dismiss();
				//			songhongbao(888);
			}
		});
		TextView coin8 = (TextView)layout.findViewById(R.id.coin8);
		///////////////////////////////////////
		coin8.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				songhongbao(1314);
				popupWindow.dismiss();
				//		songhongbao(1314);
			}
		});
		popupWindow = new PopupWindow(layout, ViewPager.LayoutParams.MATCH_PARENT,
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
		///////////////////////////////////////
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

	/*************************************
	 *是否充值
	 ************************************/
	public void showPopupspWindow_chongzhi(View parent) {
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.is_chongzhi_01165, null);

		TextView cancel = (TextView)layout.findViewById(R.id.cancel);
		//////////////////////////////////////
		cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				popupWindow.dismiss();
			}
		});


		TextView confirm = (TextView) layout.findViewById(R.id.confirm);//获取小窗口上的TextView，以便显示现在操作的功能。
		confirm.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent1=new Intent();
				intent1.setClass(Userinfo.this,Chongzhi_01178.class );//充值页面
				startActivity(intent1);
				popupWindow.dismiss();
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
		popupWindow.showAtLocation(parent, Gravity.CENTER | Gravity.CENTER,0, 0);
		// 监听
		/////////////////////////////////////////////
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

	/*************************************
	 *开线程，添加红包
	 ************************************/
	public void songhongbao(int coin){
		price = coin + "";
		///////////////////////////////
		LogDetect.send(LogDetect.DataType.specialType, "奖赏红包，开启线程_coin： ",coin);
		 String mode = "red_envelope";
		//userid,---用户id，“2”----主播id，coin----红包大小
		String[] params = {Util.userid,userid,Integer.toString(coin)};
		UsersThread_01162A b = new UsersThread_01162A(mode, params, handler);
		Thread thread = new Thread(b.runnable);
		thread.start();
	}

	/*************************************
	 *
	 ************************************/
	public void showPopupspWindow4(View parent) {
		// 加载布局
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.pop_01162, null);
		mPopWindow = new PopupWindow(layout, ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT, true);
		// 控制键盘是否可以获得焦点
		mPopWindow.setFocusable(true);
		// 设置popupWindow弹出窗体的背景
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.alpha = 0.4f;
		getWindow().setAttributes(lp);
		mPopWindow.setBackgroundDrawable(new BitmapDrawable(null, ""));
		mPopWindow.setOutsideTouchable(true);

		mPopWindow.showAtLocation(parent, Gravity.CENTER | Gravity.CENTER,0, 0);
		// 监听
		//////////////////////////////////////////////
		mPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
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

	//预约
	@SuppressLint({ "RtlHardcoded", "NewApi" })
	public void showPopupspWindow_reservation(View parent,int online) {
		// 加载布局
		LayoutInflater inflater = LayoutInflater.from(Userinfo.this);
		View layout = inflater.inflate(R.layout.pop_reservation_01160, null);
		//取消、确定、标题
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
		exit_tuichu.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				popupWindow.dismiss();
			}
		});

		exit_login = (TextView) layout.findViewById(R.id.exit_login) ;
		/////////////////////////////////////////
		exit_login.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Calendar now= Calendar.getInstance();
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String befortime = sdf.format(now.getTimeInMillis());

				now.add(Calendar.HOUR,12);
				String dateStr=sdf.format(now.getTimeInMillis());

				//开始时间
				String[] paramsMap = {Util.userid,userid,befortime,dateStr};
				new Thread(new UsersThread_01160A("insert_reservation",paramsMap,handler).runnable).start();

				popupWindow.dismiss();
			}
		});

		popupWindow = new PopupWindow(layout, ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT, true);
		// 控制键盘是否可以获得焦点
		popupWindow.setFocusable(true);
		WindowManager.LayoutParams lp = Userinfo.this.getWindow().getAttributes();
		lp.alpha = 0.5f;
		Userinfo.this.getWindow().setAttributes(lp);
		popupWindow.setBackgroundDrawable(new BitmapDrawable(null, ""));
		//popupWindow.showAsDropDown(parent, 0, 0,Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL);
		popupWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);
		////////////////////////////////////////
		popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
			public void onDismiss() {
				WindowManager.LayoutParams lp = Userinfo.this.getWindow().getAttributes();
				lp.alpha = 1f;
				Userinfo.this.getWindow().setAttributes(lp);
			}
		});
	}

	/**
	 * 执行发送送礼物 文本类型
	 *
	 * @param content
	 */
	void sendSongLi(String content) {
		LogDetect.send(LogDetect.DataType.specialType,"01160 openfire送礼:","1");
		Msg msg = getChatInfoTo(content, Const.MSG_TYPE_TEXT);
		msg.setMsgId(msgDao.insert(msg));
		listMsg.add(msg);

		final String message = content + Const.SPLIT + Const.MSG_TYPE_TEXT
				+ Const.SPLIT + sd.format(new Date())+ Const.SPLIT+Util.nickname+Const.SPLIT+Util.headpic;
		//LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"message: "+message);
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					//LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"before sendMessage()");
					sendMessage_hongbao(Utils.xmppConnection, message, userid);
					LogDetect.send(LogDetect.DataType.noType,"zhuboid",user_exit);
				} catch (XMPPException | SmackException.NotConnectedException e) {
					e.printStackTrace();
					//LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"chatmanager: "+e.toString());
					Looper.prepare();
					// ToastUtil.showShortToast(ChatActivity.this, "发送失败");
					Looper.loop();
				}
			}
		}).start();
		updateSession1(Const.MSG_TYPE_TEXT, content,nicheng,headpic);
	}

	/**
	 * 发送消息
	 *
	 * @param content
	 * @param touser
	 * @throws XMPPException
	 * @throws SmackException.NotConnectedException
	 */
	public void sendMessage_hongbao(XMPPTCPConnection mXMPPConnection, String content,
									String touser) throws XMPPException, SmackException.NotConnectedException {
		if (mXMPPConnection == null || !mXMPPConnection.isConnected()) {

		}

		ChatManager chatmanager = Utils.xmppchatmanager;

		Chat chat = chatmanager.createChat(touser + "@" + Const.XMPP_HOST, null);
		if (chat != null) {

			chat.sendMessage(content, Util.userid + "@" + Const.XMPP_HOST);
			Log.e("jj", "发送成功");
			LogDetect.send(LogDetect.DataType.noType,"01160顾客发消息" ,content);
		}else{
			//LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"send fail:chat is null");
		}
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
		msg.setFromUser(userid);
		msg.setToUser(I);
		msg.setType(msgtype);
		msg.setIsComing(1);
		msg.setContent(message);
		msg.setDate(time);
		return msg;
	}

	void updateSession1(String type, String content,String name,String logo) {
		Session session = new Session();
		session.setFrom(userid);
		session.setTo(I);
		session.setNotReadCount("");// 未读消息数量
		session.setContent(content);
		session.setTime(sd.format(new Date()));
		session.setType(type);
		session.setName(name);
		session.setHeadpic(logo);

		if (sessionDao.isContent(userid, I)) {
			sessionDao.updateSession(session);
		} else {
			sessionDao.insertSession(session);
		}
		Intent intent = new Intent(Const.ACTION_ADDFRIEND);// 发送广播，通知消息界面更新
		sendBroadcast(intent);
	}


	/*************************************
	 *
	 ************************************/
	private class reciever extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			showPopupspWindow4(wodeqianbao);
		}
	}
	/*************************************
	 *
	 ************************************/
	private class reciever1 extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			//showPopupspWindow4(wodeqianbao);
			ll_vhome_avchat.setClickable(true);
			///////////////////////////////
			LogDetect.send(LogDetect.DataType.specialType, "准备通话---发送openfire","允许点击");
		}
	}
	/*************************************
	 *
	 ************************************/
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(msgOperReciver1);
		unregisterReceiver(reviever);
		unregisterReceiver(reviever1);
	}
}
