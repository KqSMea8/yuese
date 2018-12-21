package com.net.yuesejiaoyou.redirect.ResolverA.uiface;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.fm.openinstall.OpenInstall;
import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.redirect.ResolverA.interface3.UsersThread_01066A;
import com.net.yuesejiaoyou.redirect.ResolverA.interface3.UsersThread_01162A;
import com.net.yuesejiaoyou.redirect.ResolverA.interface4.CountDownTimerUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2018/3/22.
 */

public class Vchat_register_01162 extends Activity implements View.OnClickListener {
	private TextView  get_code,xieyi,sign;
	private EditText code2,edit1,edit2,invite_code;
	private ImageView back1;
	private boolean isPhoneNum ;

	private RadioButton maleButton, femalButton;

	private CountDownTimerUtils countDownTimer;
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vchat_register_01162);
		get_code= (TextView) findViewById(R.id.get_code);
		code2= (EditText) findViewById(R.id.code);
		edit1= (EditText) findViewById(R.id.phonenum);
		edit2= (EditText) findViewById(R.id.password);

		maleButton = (RadioButton) findViewById(R.id.radioMale);
		femalButton = (RadioButton) findViewById(R.id.radioFemale);

		invite_code= (EditText) findViewById(R.id.invite_code);

		sign= (TextView) findViewById(R.id.sign);
		sign.setOnClickListener(this);
		back1= (ImageView) findViewById(R.id.back1);
		back1.setOnClickListener(this);
		xieyi= (TextView) findViewById(R.id.xieyi);
		get_code.setOnClickListener(this);

		SpannableStringBuilder style=new SpannableStringBuilder(xieyi.getText().toString());
		style.setSpan(new ForegroundColorSpan(Color.parseColor("#FD2F7A")),7,15, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		xieyi.setText(style);
		xieyi.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		int id = view.getId();
		switch (id) {

			case R.id.get_code:
				isPhoneNum = isMobileNO(edit1.getText().toString());
				if(TextUtils.isEmpty(edit1.getText().toString()) || !isPhoneNum ){  //country code
					Toast.makeText(Vchat_register_01162.this,"请输入有效的手机号码",Toast.LENGTH_LONG).show();
				} else {
					countDownTimer = new CountDownTimerUtils(get_code, 60000, 1000);
					countDownTimer.start();

					String[] paramsMap = {"1",edit1.getText().toString()};
					UsersThread_01066A b = new UsersThread_01066A("getcode", paramsMap, requestHandler);
					Thread thread = new Thread(b.runnable);
					thread.start();
					code2.setClickable(false);
				}
				break;
			case R.id.sign:

				/*if(TextUtils.isEmpty(edit1.getText().toString()) || !isPhoneNum ){
						edit1.setText("");
					 Toast.makeText(Vchat_register_01162.this,"请输入有效的手机号码",Toast.LENGTH_LONG).show();
				} else */
				if(TextUtils.isEmpty(edit2.getText().toString())){
					 Toast.makeText(Vchat_register_01162.this,"请输入密码",Toast.LENGTH_LONG).show();
				} else if (!code.equals(code2.getText().toString())){
					Toast.makeText(Vchat_register_01162.this,"验证码错误",Toast.LENGTH_LONG).show();
				} else if(!maleButton.isChecked() && !femalButton.isChecked()) {
					 Toast.makeText(Vchat_register_01162.this, "请选择性别", Toast.LENGTH_SHORT).show();
				} else {
					String gender = "";
					if(maleButton.isChecked()) {
						gender = "男";
					} else if(femalButton.isChecked()) {
						gender = "女";
					} else {
						Toast.makeText(Vchat_register_01162.this, "请选择性别", Toast.LENGTH_SHORT).show();
						break;
					}
					String[] paramsMap = {"1", edit1.getText().toString(), edit2.getText().toString(),Util.yqcode,Util.channelcode,"","","",gender};//invite_code.getText().toString()
						UsersThread_01066A b = new UsersThread_01066A("register", paramsMap, requestHandler);
						Thread thread = new Thread(b.runnable);
						thread.start();
					}
				break;
			case R.id.back1:
				finish();
				break;
			case R.id.xieyi:
				Intent intent=new Intent();
				intent.setClass(Vchat_register_01162.this,Agreement_01162.class);
				startActivity(intent);
				break;
		}



	}

    String code="0";
	/**
	 * 返回json字符串，并转化为JSONObject对象
	 * 解析JSONObject对象，获取相应的数据，保存到后台服务器。
	 */
	@SuppressLint("HandlerLeak")
	private Handler requestHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 201:
					String json1 = (String) msg.obj;
					if(!json1.isEmpty()){
						try {
							JSONObject jsonObject1 = new JSONObject(json1);
							if(jsonObject1.has("random")) {
								code = jsonObject1.getString("random");
								LogDetect.send(LogDetect.DataType.basicType, "01162", "服务器返回的验证码" + code);
								if (!TextUtils.isEmpty(code)) {

								} else {
									if(countDownTimer != null) {
										countDownTimer.stop();
									}
									Toast.makeText(Vchat_register_01162.this, "获取验证码失败！", Toast.LENGTH_SHORT).show();
								}
							} else if(jsonObject1.has("result")) {
								String reason = jsonObject1.getString("reason");
								if("limit".equals(reason)) {
									Toast.makeText(Vchat_register_01162.this, "您今天接收短信次数已用完!", Toast.LENGTH_SHORT).show();
								}
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							if(countDownTimer != null) {
								countDownTimer.stop();
							}
							Toast.makeText(Vchat_register_01162.this, "获取验证码失败！", Toast.LENGTH_SHORT).show();
							e.printStackTrace();
							LogDetect.send(LogDetect.DataType.basicType,"01162","JSON解析异常"+e);
						}
					}else {
						if(countDownTimer != null) {
							countDownTimer.stop();
						}
						Toast.makeText(Vchat_register_01162.this, "请检查网络连接！", Toast.LENGTH_SHORT).show();
					}

					break;

				case 200:
					String json = (String) msg.obj;
					LogDetect.send(LogDetect.DataType.specialType, "LoginActivity: ", json);
					if (!json.equals("")) {
						if (json.contains("id")) {

							OpenInstall.reportRegister();

							try {
//					  id,nickname,photo,gender
								JSONArray jsonArray = new JSONArray(json);
								//JSONObject jsonObject = new JSONObject(json);
								JSONObject jsonObject = jsonArray.getJSONObject(0);
								String a = jsonObject.getString("id");
								String name = jsonObject.getString("nickname");
								String headpic = jsonObject.getString("photo");
								String gender = jsonObject.getString("gender");
								String zhubo = jsonObject.getString("is_v");
								String invite_num=jsonObject.getString("invite_num");
								Util.invite_num = invite_num;
								//String zh = login_username.getText().toString();
								//String pwd = login_password.getText().toString();
								SharedPreferences share = getSharedPreferences("Acitivity", Activity.MODE_PRIVATE);
								share.edit().putString("username",/* guojianum.getText().toString()+*/edit1.getText().toString()).commit();
								share.edit().putString("password", edit2.getText().toString()).commit();
								share.edit().putString("userid", a).commit();
								share.edit().putString("nickname", name).commit();
								share.edit().putString("headpic", headpic).commit();
								share.edit().putString("sex", gender).commit();
								share.edit().putString("zhubo_bk", zhubo).commit();

								/////////A区给B区的一对一通话，进行初始化实时通讯
								///////////////
								Util.userid = a;
								Util.headpic = headpic;
								Util.nickname = name;
								Util.iszhubo = zhubo;
								Util.is_agent = jsonObject.getString("is_agent");
								if(Util.imManager == null) {
									Util.imManager = new com.net.yuesejiaoyou.redirect.ResolverB.interface4.im.IMManager();
									Util.imManager.initIMManager(jsonObject, getApplicationContext());
								}

								JPushInterface.setAlias(getApplicationContext(), 1, Util.userid);	// 设置极光别名



								String mode55="update_platform";
								String params55[]={Util.userid,"android"};
								UsersThread_01162A b55=new UsersThread_01162A(mode55,params55,requestHandler);
								Thread thread55=new Thread(b55.runnable);
								thread55.start();

								////////////////////////////////////////////////////
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Intent intent = new Intent();
							intent.setClass(Vchat_register_01162.this, MainActivity.class);
							startActivity(intent);
							finish();
						} else {
							Toast.makeText(Vchat_register_01162.this, "电话号码已注册.", Toast.LENGTH_SHORT).show();
						}
					} else {
					  Toast.makeText(Vchat_register_01162.this, "网络异常,请稍后重试", Toast.LENGTH_SHORT).show();
					}
					break;
			}
		}
	};

	/**
	 * 验证手机格式
	 */
	public static boolean isMobileNO(String mobiles) {
    /*
     * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
     * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
     * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
     */
		String telRegex = "[1][3456789]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
		if (TextUtils.isEmpty(mobiles))
			return false;
		else
			return mobiles.matches(telRegex);
	}


}
