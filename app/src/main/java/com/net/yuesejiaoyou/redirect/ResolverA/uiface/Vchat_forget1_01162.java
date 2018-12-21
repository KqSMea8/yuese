package com.net.yuesejiaoyou.redirect.ResolverA.uiface;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.redirect.ResolverA.interface3.UsersThread_01066A;
import com.net.yuesejiaoyou.redirect.ResolverA.interface4.CountDownTimerUtils;

import org.json.JSONException;
import org.json.JSONObject;


import static com.net.yuesejiaoyou.redirect.ResolverA.uiface.Vchat_register_01162.isMobileNO;


/**
 * Created by Administrator on 2018/3/22.
 */

public class Vchat_forget1_01162 extends Activity implements View.OnClickListener {
	private TextView get_code, xieyi, sign;
	private EditText code2, edit1, edit2/* guojianum*/;
	private ImageView back1;
	private PopupWindow mPopWindow;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vchat_forgetpassword);
		get_code = (TextView) findViewById(R.id.get_code);
		code2 = (EditText) findViewById(R.id.code);
	//	guojianum = (EditText) findViewById(R.id.area_num);
		back1 = (ImageView) findViewById(R.id.back1);
		back1.setOnClickListener(this);
		edit1 = (EditText) findViewById(R.id.phonenum);
		sign = (TextView) findViewById(R.id.sign);
		sign.setOnClickListener(this);
		get_code.setOnClickListener(this);


	}

	@Override
	public void onClick(View view) {
		int id = view.getId();
		switch (id) {
			case R.id.get_code:
				boolean isPhoneNum = isMobileNO(edit1.getText().toString());
				/*if (guojianum.getText().toString().equals("")) {  //country code
					Toast.makeText(Vchat_forget1_01162.this, "please enter your country code", Toast.LENGTH_LONG).show();
				} else*/ if (TextUtils.isEmpty(edit1.getText().toString()) || !isPhoneNum) {
					edit1.setText("");
					Toast.makeText(Vchat_forget1_01162.this, "请输入有效的手机号码", Toast.LENGTH_LONG).show();
				}else {
				String[] paramsMap = {"1",edit1.getText().toString()};
				UsersThread_01066A b = new UsersThread_01066A("getcode", paramsMap, requestHandler);
				Thread thread = new Thread(b.runnable);
				thread.start();
					}
				break;
			case R.id.sign:
				if (code.equals("0")) {
					return;
				}
				if (!code.equals(code2.getText().toString())) {
					Toast.makeText(Vchat_forget1_01162.this, "请输入验证码", Toast.LENGTH_LONG).show();
					return;
				}
				Intent intent = new Intent();
				intent.setClass(Vchat_forget1_01162.this, Vchat_forget2_01162.class);
				intent.putExtra("zhanghao", /*guojianum.getText().toString().substring(1) +*/ edit1.getText().toString());
				startActivity(intent);
				finish();
				break;
			case R.id.back1:
				finish();
				break;
			/*case R.id.area_num:

				showPopupspWindow4(guojianum);
				LogDetect.send(LogDetect.DataType.basicType,"01162--","点了一下");

				break;*/
		}


	}

	String code = "0";
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
							code = jsonObject1.getString("random");
							if (TextUtils.isEmpty(code)) {
								Toast.makeText(Vchat_forget1_01162.this, "获取验证码失败！", Toast.LENGTH_SHORT).show();
							} else if (code.equals("-1")) {
								Toast.makeText(Vchat_forget1_01162.this, "此手机号码尚未注册!", Toast.LENGTH_SHORT).show();
							} else {
								Toast.makeText(Vchat_forget1_01162.this, "发送成功,请稍后!", Toast.LENGTH_SHORT).show();
								CountDownTimerUtils countDownTimer = new CountDownTimerUtils(get_code, 60000, 1000);
								countDownTimer.start();
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}else{
						Toast.makeText(Vchat_forget1_01162.this, "请检查网络连接！", Toast.LENGTH_SHORT).show();
					}

					break;

			}
		}
	};



}
