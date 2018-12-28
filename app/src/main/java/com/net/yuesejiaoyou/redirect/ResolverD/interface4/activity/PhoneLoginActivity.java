package com.net.yuesejiaoyou.redirect.ResolverD.interface4.activity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.fm.openinstall.OpenInstall;
import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.redirect.ResolverA.interface3.UsersThread_01066A;
import com.net.yuesejiaoyou.redirect.ResolverA.interface3.UsersThread_01162A;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.BaseActivity;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.ShareHelp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import cn.jpush.android.api.JPushInterface;

import static com.net.yuesejiaoyou.redirect.ResolverD.interface4.activity.RegisterActivity.isMobileNO;



/**
 * Created by Administrator on 2018/3/22.
 */

public class PhoneLoginActivity extends BaseActivity implements View.OnClickListener {

	String openid="";
	String headimgurl="";
	String nickname="";
	String gender="";
	//CallbackManager callbackManager;

	private EditText edit1, edit2, guojianum;
	private TextView sign, register, forget, xieyi;
	private ImageView/* facebook,*/ back1;
	//private LoginButton loginButton;
	private LinearLayout selecta;
	private PopupWindow popupWindow;
	private ImageView qq,wc;
	SharedPreferences share;

	/************************************************************************************************
	 *初始化页面，加载控件
	 *
	 * @param savedInstanceState
	 ***********************************************************************************************/

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*guojianum = (EditText) findViewById(R.id.guojianum);
		guojianum.setOnClickListener(this);*/
		edit1 = (EditText) findViewById(R.id.phonenum);
		edit2 = (EditText) findViewById(R.id.password);
		edit1.addTextChangedListener(mTextWatcher);
		edit2.addTextChangedListener(mTextWatcher);
		sign = (TextView) findViewById(R.id.sign);
		back1 = (ImageView) findViewById(R.id.back1);
		back1.setOnClickListener(this);
		selecta = (LinearLayout) findViewById(R.id.selecta);
		selecta.setOnClickListener(this);
		register = (TextView) findViewById(R.id.register);
		forget = (TextView) findViewById(R.id.forget);
		forget.setOnClickListener(this);
		sign.setOnClickListener(this);
		register.setOnClickListener(this);
		qq = (ImageView) findViewById(R.id.qq);
		wc = (ImageView) findViewById(R.id.wx);
		qq.setOnClickListener(this);
		wc.setOnClickListener(this);
		xieyi = (TextView) findViewById(R.id.xieyi);
		SpannableStringBuilder style=new SpannableStringBuilder(xieyi.getText().toString());
		style.setSpan(new ForegroundColorSpan(Color.parseColor("#FD2F7A")),7,15, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		xieyi.setText(style);
		xieyi.setOnClickListener(this);
	}

	@Override
	protected int getContentView() {
		return R.layout.vchat_login_01162;
	}

	/************************************************************************************************
	 * 返回json字符串，并转化为JSONObject对象
	 * 解析JSONObject对象，获取相应的数据，保存到后台服务器。
	 ************************************************************************************************/

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		////////////////////
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
				////////////////////
				case 200:
					String json = (String) msg.obj;
					//LogDetect.send(LogDetect.DataType.specialType, "LoginActivity: ", json);
					if (!json.equals("")) {
						JSONObject jsonObject1 = null;
						try {
							jsonObject1 = new JSONObject(json);
							if(jsonObject1.getString("0").equals("已封号")){
								Toast.makeText(PhoneLoginActivity.this, "您的账号出现违规操作已被冻结，有问题请联系客服.", Toast.LENGTH_SHORT).show();
								return;
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}

						if (json.contains("id")) {
							try {
								 // id,nickname,photo,gender
								JSONArray jsonArray = new JSONArray(json);
								//JSONObject jsonObject = new JSONObject(json);
								JSONObject jsonObject = jsonArray.getJSONObject(0);
								String a = jsonObject.getString("id");
								if (!a.equals("")) {
									String name = jsonObject.getString("nickname");
									String headpic = jsonObject.getString("photo");
									String gender = jsonObject.getString("gender");
									String zhubo = jsonObject.getString("is_v");
									String invite_num=jsonObject.getString("invite_num");
									String zh = /*guojianum.getText().toString().substring(1) +*/ edit1.getText().toString();
									String pwd = edit2.getText().toString();
									share = getSharedPreferences("Acitivity", Activity.MODE_PRIVATE);
									share.edit().putString("logintype", "phonenum").commit();
									share.edit().putString("username", zh).commit();
									share.edit().putString("password", pwd).commit();
									share.edit().putString("userid", a).commit();
									share.edit().putString("nickname", name).commit();
									share.edit().putString("headpic", headpic).commit();
									share.edit().putString("sex", gender).commit();
									share.edit().putString("zhubo_bk", zhubo).commit();
									share.edit().putBoolean("FIRST",false).commit();
									Util.userid = a;
									Util.headpic = headpic;
									Util.nickname = name;
									Util.is_agent = jsonObject.getString("is_agent");
									Util.iszhubo = zhubo.equals("0")?"0":"1";
									Util.invite_num = invite_num;

									////////建立跟openfire的关系
									if(Util.imManager == null) {
										Util.imManager = new com.net.yuesejiaoyou.redirect.ResolverB.interface4.im.IMManager();
										Util.imManager.initIMManager(jsonObject, getApplicationContext());
									}

//									VideoMessageManager.userLogin(Util.userid,share.getString("password",""), new VideoMessageManager.LoginCallback() {
//
//										@Override
//										public void loginSuccess() {
//											runOnUiThread(new Runnable() {
//												@Override
//												public void run() {
//													Toast.makeText(PhoneLoginActivity.this, "网易云信登录成功", Toast.LENGTH_SHORT).show();
//												}
//											});
//										}
//
//										@Override
//										public void loginError(int code, String reason) {
//											runOnUiThread(new Runnable() {
//												@Override
//												public void run() {
//													Toast.makeText(PhoneLoginActivity.this, "网易云信登录失败", Toast.LENGTH_SHORT).show();
//												}
//											});
//										}
//									});

									/////////////////////////////////
									LogDetect.send(LogDetect.DataType.basicType, "01107", "--before 绑定极光别名： "+Util.userid);
									JPushInterface.setAlias(getApplicationContext(), 1, Util.userid);
									LogDetect.send(LogDetect.DataType.basicType, "01107", "--after 绑定极光别名： "+Util.userid);


									String mode55="update_platform";
									String params55[]={Util.userid,"android"};
									UsersThread_01162A b55=new UsersThread_01162A(mode55,params55,handler);
									Thread thread55=new Thread(b55.runnable);
									thread55.start();


									Intent intent = new Intent();
									intent.setClass(PhoneLoginActivity.this, MainActivity.class);
									startActivity(intent);
									finish();
								} else {
									Toast.makeText(PhoneLoginActivity.this, "请检查手机号或密码", Toast.LENGTH_SHORT).show();
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} else {
							Toast.makeText(PhoneLoginActivity.this, "请检查手机号或密码", Toast.LENGTH_SHORT).show();
						}
					}else{
						Toast.makeText(PhoneLoginActivity.this, "请检查手机号或密码", Toast.LENGTH_SHORT).show();
					}
					break;
				////////////////////
				case 20000:
					Map<Object, Object> maps=(Map<Object, Object>) msg.obj;
					///////////////////////////////
					LogDetect.send("微信登录返回的值maps ", maps);
					//Toast.makeText(PhoneLoginActivity.this, "微信maps:"+maps.toString(), Toast.LENGTH_LONG).show();
					Log.v("11", maps.toString());
					 openid=(String) maps.get("openid");
					 nickname=(String) maps.get("nickname");
					 headimgurl=(String) maps.get("headimgurl");
					String sex=Integer.parseInt(maps.get("sex").toString())+"";
					if(sex.equals("2")){
						gender="女";
					}else if(sex.equals("1")){
						gender="男";
					}
					/*String mode="wxlogin";
					String []params={openid,nickname,headimgurl,sex};
					UsersThread_01158A b=new UsersThread_01158A(mode,params,requestHandler);
					Thread t=new Thread(b.runnable);
					t.start();*/
					//showPopupspWindow_tuichu(wc);
//					String mode="wxlogin_1";
//					String []params={"",openid,nickname,headimgurl,sex};
//					UsersThread_01162A b=new UsersThread_01162A(mode,params,handler);
//					Thread t=new Thread(b.runnable);
//					t.start();
					Log.v("TT","yqcode: "+Util.yqcode);
					if(Util.yqcode.equals("0")) {
						Util.yqcode = "";
					}
					////////////////////
					Log.v("TT","before wxlogin openid:"+openid+",nickname:"+nickname+",headimgurl:"+headimgurl+",gender:"+gender+",yqcode:"+Util.yqcode);

					//LogDetect.send(LogDetect.DataType.specialType, "LoginActivity: ", "before wxlogin openid:"+openid+",nickname:"+nickname+",headimgurl:"+headimgurl+",gender:"+gender+",yqcode:"+Util.yqcode);
					String mode="wxlogin";
					String []params={"",openid,nickname,headimgurl,gender,Util.yqcode};
					UsersThread_01162A b=new UsersThread_01162A(mode,params,handler);
					Thread t=new Thread(b.runnable);
					t.start();
					// 判断是否第一次微信登陆
					/*String []params={"",openid};
					UsersThread_01162A b=new UsersThread_01162A("wxlogin_pd",params,handler);
					Thread t=new Thread(b.runnable);
					t.start();*/

					break;
				////////////////////
				case 501:
					String json01 = (String) msg.obj;
					LogDetect.send(LogDetect.DataType.specialType, "LoginActivity: ", json01);
					try {
						JSONObject jsonObject = new JSONObject(json01);
						String a = jsonObject.getString("result");
						if(a.equals("0")){
							showPopupspWindow_tuichu(wc);
						}else{
							//Toast.makeText(LoginActivity.this, "绑定手机号已存在", Toast.LENGTH_SHORT).show();
							String mode01="wxlogin_1";
							String []params01={"",openid,nickname,headimgurl,gender,Util.yqcode};
							UsersThread_01162A b01=new UsersThread_01162A(mode01,params01,handler);
							Thread t01=new Thread(b01.runnable);
							t01.start();
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Log.e("TT","exception: "+e);
					}
					break;
				//////////////////////////
				case 502:
					String json02 = (String) msg.obj;
					LogDetect.send(LogDetect.DataType.specialType, "LoginActivity: ", json02);
					if (!json02.equals("")) {
						if (json02.contains("id")) {
							try {
								JSONArray jsonArray = new JSONArray(json02);
								//JSONObject jsonObject = new JSONObject(json);
								JSONObject jsonObject = jsonArray.getJSONObject(0);
								String a = jsonObject.getString("id");
								if (!a.equals("")) {
									String name = jsonObject.getString("nickname");
									String headpic = jsonObject.getString("photo");
									String gender = jsonObject.getString("gender");
									String zhubo = jsonObject.getString("is_v");
									String zh = jsonObject.getString("username");
									String pwd = jsonObject.getString("password");
									String wxopenid=jsonObject.getString("openid");
									String invite_num=jsonObject.getString("invite_num");
									//share = getSharedPreferences("Acitivity", Activity.MODE_PRIVATE);
									////////////////////
									Log.v("TT","name:"+name+",headpic:"+headpic+",wxopenid:"+wxopenid);

									share.edit().putString("logintype","wx").commit();
									share.edit().putString("userid", a).commit();
									share.edit().putString("nickname", name).commit();
									share.edit().putString("headpic", headpic).commit();
									share.edit().putString("sex", gender).commit();
									share.edit().putString("zhubo_bk", zhubo).commit();
									share.edit().putBoolean("FIRST",false).commit();
									share.edit().putString("openid",wxopenid).commit();
									///////////////////////////
									/////////////
									Util.userid = a;
									Util.headpic = headpic;
									Util.nickname = name;
									Util.is_agent = jsonObject.getString("is_agent");
									Util.iszhubo = zhubo.equals("0")?"0":"1";
									if(Util.imManager == null) {
										Util.imManager = new com.net.yuesejiaoyou.redirect.ResolverB.interface4.im.IMManager();
										Util.imManager.initIMManager(jsonObject, getApplicationContext());
									}
									Util.invite_num=invite_num;
									//////////////////////////////////////

									JPushInterface.setAlias(getApplicationContext(), 1, Util.userid);	// 设置极光别名
									Intent intent = new Intent();
									intent.setClass(PhoneLoginActivity.this, MainActivity.class);
									startActivity(intent);
									finish();
								}


							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								Log.e("TT", "exception: " + e);
							}
						}else{
							Toast.makeText(PhoneLoginActivity.this, "绑定手机号已存在", Toast.LENGTH_SHORT).show();
						}
					}else{
						Toast.makeText(PhoneLoginActivity.this, "请检查网络连接", Toast.LENGTH_SHORT).show();
					}
					break;
				//////////////////////////

				//////////////////////
				case 204:
					String json1 = (String) msg.obj;
					//LogDetect.send(LogDetect.DataType.specialType, "LoginActivity: ", json);
					if (!json1.equals("")) {
						if (json1.contains("nickname")) {

							try {
					 			// id,nickname,photo,gender
								JSONArray jsonArray = new JSONArray(json1);
								//JSONObject jsonObject = new JSONObject(json);
								JSONObject jsonObject = jsonArray.getJSONObject(0);
								String a = jsonObject.getString("id");
								if (!a.equals("")) {

									String name = jsonObject.getString("nickname");
									String headpic = jsonObject.getString("photo");
									String gender = jsonObject.getString("gender");
									String zhubo = jsonObject.getString("is_v");
									String zh = jsonObject.getString("username");
									String pwd = jsonObject.getString("password");
									String wxopenid=jsonObject.getString("openid");
									String isreged=jsonObject.getString("isreged");
									if(isreged.equals("1")){
										OpenInstall.reportRegister();
									}
									share = getSharedPreferences("Acitivity", Activity.MODE_PRIVATE);
									share.edit().putString("userid", a).commit();
									share.edit().putString("nickname", name).commit();
									share.edit().putString("headpic", headpic).commit();
									share.edit().putString("sex", gender).commit();
									share.edit().putString("zhubo_bk", zhubo).commit();
									share.edit().putBoolean("FIRST",false).commit();
									share.edit().putString("openid",wxopenid);
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
									/////////////////////////////////

//									VideoMessageManager.userLogin(Util.userid,share.getString("password",""), new VideoMessageManager.LoginCallback() {
//
//										@Override
//										public void loginSuccess() {
//											runOnUiThread(new Runnable() {
//												@Override
//												public void run() {
//													Toast.makeText(PhoneLoginActivity.this, "网易云信登录成功", Toast.LENGTH_SHORT).show();
//												}
//											});
//										}
//
//										@Override
//										public void loginError(int code, String reason) {
//											runOnUiThread(new Runnable() {
//												@Override
//												public void run() {
//													Toast.makeText(PhoneLoginActivity.this, "网易云信登录失败", Toast.LENGTH_SHORT).show();
//												}
//											});
//										}
//									});

									JPushInterface.setAlias(getApplicationContext(), 1, Util.userid);	// 设置极光别名


									String mode55="update_platform";
									String params55[]={Util.userid,"android"};
									UsersThread_01162A b55=new UsersThread_01162A(mode55,params55,handler);
									Thread thread55=new Thread(b55.runnable);
									thread55.start();

									Intent intent = new Intent();
									intent.setClass(PhoneLoginActivity.this, MainActivity.class);
									startActivity(intent);
									finish();
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}else {
							LogDetect.send(LogDetect.DataType.specialType, "PhoneLoginActivity: ", "邀请码不正确");
							Toast.makeText(PhoneLoginActivity.this, "邀请码不正确", Toast.LENGTH_SHORT).show();
						}
					}else{
						Toast.makeText(PhoneLoginActivity.this, "请检查网络连接", Toast.LENGTH_SHORT).show();
					}
					break;
				////////////////////
				case 205:
					String json2 = (String) msg.obj;
					////////////////////////////
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
										Toast.makeText(PhoneLoginActivity.this,"您已被封禁",Toast.LENGTH_SHORT).show();
										return;
									}
									String name = jsonObject.getString("nickname");
									String headpic = jsonObject.getString("photo");
									String gender = jsonObject.getString("gender");
									String zhubo = jsonObject.getString("is_v");
									String zh = jsonObject.getString("username");
									String pwd = jsonObject.getString("password");
									String wxopenid=jsonObject.getString("openid");
									String invite_num=jsonObject.getString("invite_num");
									share = getSharedPreferences("Acitivity", Activity.MODE_PRIVATE);

									share.edit().putString("userid", a).commit();
									share.edit().putString("nickname", name).commit();
									share.edit().putString("headpic", headpic).commit();
									share.edit().putString("sex", gender).commit();
									share.edit().putString("zhubo_bk", zhubo).commit();
									share.edit().putBoolean("FIRST",false).commit();
									share.edit().putString("openid",wxopenid).commit();
									////////////////////
									Log.v("TT","Vchat_login wx");
									Util.userid = a;
									Util.headpic = headpic;
									Util.nickname = name;
									Util.is_agent = jsonObject.getString("is_agent");
									Util.iszhubo = zhubo.equals("0")?"0":"1";
									Util.invite_num=invite_num;
									////////建立跟openfire的关系
									if(Util.imManager == null) {
										Util.imManager = new com.net.yuesejiaoyou.redirect.ResolverB.interface4.im.IMManager();
										Util.imManager.initIMManager(jsonObject, getApplicationContext());
									}
									/////////////////////////////////

									JPushInterface.setAlias(getApplicationContext(), 1, Util.userid);	// 设置极光别名

									String mode55="update_platform";
									String params55[]={Util.userid,"android"};
									UsersThread_01162A b55=new UsersThread_01162A(mode55,params55,handler);
									Thread thread55=new Thread(b55.runnable);
									thread55.start();


									Intent intent = new Intent();
									intent.setClass(PhoneLoginActivity.this, MainActivity.class);
									startActivity(intent);
									finish();
								}/* else {
									Toast.makeText(LoginActivity.this, "邀请码不正确", Toast.LENGTH_SHORT).show();
								}*/
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}else{
							showPopupspWindow_tuichu(wc);
						}
					}
					break;

			}
		}
	};

	/***********************************************************************************************
	 * 添加点击事件
	 *
	 *************************************************************************************************/
	@Override
	public void onClick(View view) {
		int id = view.getId();
		switch (id) {
			////////////////////
			case R.id.sign:
				boolean isPhoneNum = isMobileNO(edit1.getText().toString());
				/*if (guojianum.getText().toString().equals("")) {  //country code
					Toast.makeText(PhoneLoginActivity.this, "please enter your country code", Toast.LENGTH_LONG).show();
				} else*/
				if (TextUtils.isEmpty(edit1.getText().toString()) || !isPhoneNum) {  //country code
					Toast.makeText(PhoneLoginActivity.this, "请输入有效的手机号", Toast.LENGTH_LONG).show();
				} else if (TextUtils.isEmpty(edit2.getText().toString())) {
					Toast.makeText(PhoneLoginActivity.this, "请输入密码", Toast.LENGTH_LONG).show();
				} else {
					String[] paramsMap = {"1",/* guojianum.getText().toString().substring(1) +*/ edit1.getText().toString(), edit2.getText().toString()};
					UsersThread_01066A b = new UsersThread_01066A("login", paramsMap, handler);//200
					Thread thread = new Thread(b.runnable);
					thread.start();
				}
				break;
			////////////////////
			case R.id.register:
				Intent intent = new Intent();
				intent.setClass(PhoneLoginActivity.this, RegisterActivity.class);
				startActivity(intent);
				break;
		/*	case R.id.face_book:
				loginButton.performClick();
				break;*/
			////////////////////
			case R.id.forget:
				Intent intent1 = new Intent();
				intent1.setClass(PhoneLoginActivity.this, ForgetPasswordActivity.class);
				startActivity(intent1);
				break;
			////////////////////
			case R.id.back1:
				finish();
				break;
			////////////////////
			case R.id.wx:
				ShareHelp shareHelp=new ShareHelp();
				shareHelp.wx_login(handler,"0");
				Toast.makeText(this,"微信授权登陆",Toast.LENGTH_SHORT).show();
				break;
			////////////////////
			case R.id.qq:
				Toast.makeText(this,"qq授权登陆",Toast.LENGTH_LONG).show();
				break;
			case R.id.xieyi:
				Intent intent2=new Intent();
				intent2.setClass(PhoneLoginActivity.this,AgreementActivity.class);
				startActivity(intent2);
				break;

		}
	}

	/*************************************************************************************************
	 *
	 *
	 ************************************************************************************************/


	TextWatcher mTextWatcher = new TextWatcher() {
		////////////////////
		@Override
		public void beforeTextChanged(CharSequence s, int arg1, int arg2,
									  int arg3) {
			if(TextUtils.isEmpty(s)){
				sign.setBackgroundResource(R.drawable.shape_login1_01160);
			}

		}
		////////////////////
		@Override
		public void onTextChanged(CharSequence s, int arg1, int arg2,
								  int arg3) {
			if(TextUtils.isEmpty(s)){
				sign.setBackgroundResource(R.drawable.shape_login1_01160);
			}else{
				sign.setBackgroundResource(R.drawable.shape_login2_01160);
			}

		}
		////////////////////
		@Override
		public void afterTextChanged(Editable s) {

		}

	};

	/************************************************************************************************
	 * 弹出框----
	 * @param arg0
	 ************************************************************************************************/
	private void showPopupspWindow_tuichu(View arg0) {
		LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View convertView = LayoutInflater.from(PhoneLoginActivity.this).inflate(R.layout.fb_loginpop_01162, null);
		ImageView fb_close= (ImageView) convertView.findViewById(R.id.fb_close);
		final EditText fb_invitecode= (EditText) convertView.findViewById(R.id.fb_invitecode);
		fb_invitecode.setFocusable(true);
		////////////////////
		fb_close.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				popupWindow.dismiss();
			}
		});
		TextView fb_sure= (TextView) convertView.findViewById(R.id.fb_sure);
		////////////////////
		fb_sure.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if(fb_invitecode.getText().toString().equals("")){
					Toast.makeText(PhoneLoginActivity.this,"请输入邀请码",Toast.LENGTH_LONG).show();
				}else{
					/*String mode="wxlogin_tel";
					String []params={"",openid,nickname,headimgurl,gender,"",fb_invitecode.getText().toString()};
					UsersThread_01162A b=new UsersThread_01162A(mode,params,handler);
					Thread t=new Thread(b.runnable);
					t.start();*/
					String mode="wxlogin";
					String []params={"",openid,nickname,headimgurl,gender,fb_invitecode.getText().toString()};
					UsersThread_01162A b=new UsersThread_01162A(mode,params,handler);
					Thread t=new Thread(b.runnable);
					t.start();
				}
			}
		});
		popupWindow = new PopupWindow(convertView, ActionBar.LayoutParams.WRAP_CONTENT,
				ActionBar.LayoutParams.WRAP_CONTENT, true);
//		popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
//		popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
//
//		popupWindow = new PopupWindow(convertView, ActionBar.LayoutParams.MATCH_PARENT,
//				ActionBar.LayoutParams.WRAP_CONTENT, true);
//		popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
//		popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);

		//是否响应touch事件
    //    mPopWindow.setTouchable(false);
		//是否具有获取焦点的能力
		popupWindow.setFocusable(true);
		popupWindow.showAtLocation(arg0, Gravity.CENTER, 0, 0);
		//外部是否可以点击
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.setOutsideTouchable(true);
		//mPopWindow.showAsDropDown(tj2);
		////////////////////
		convertView.setOnTouchListener(new View.OnTouchListener()// 需要设置，点击之后取消popupview，即使点击外面，也可以捕获事件
		{
			@Override
			public boolean onTouch(View v, android.view.MotionEvent event) {
				// TODO Auto-generated method stub
				if (popupWindow.isShowing()) {
					popupWindow.dismiss();
				}
				return false;
			}
		});
		////////////////////
		popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
			@Override
			public void onDismiss() {
				String mode="wxlogin";
				String []params={"",openid,nickname,headimgurl,gender,""};
				UsersThread_01162A b=new UsersThread_01162A(mode,params,handler);
				Thread t=new Thread(b.runnable);
				t.start();
			}
		});

	}
}
