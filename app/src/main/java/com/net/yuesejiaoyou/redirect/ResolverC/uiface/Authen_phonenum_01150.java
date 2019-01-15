package com.net.yuesejiaoyou.redirect.ResolverC.uiface;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

/*import com.net.yuesejiaoyou.R;
import com.example.vliao.interface3.UsersThread_01066;
import com.example.vliao.interface3.UsersThread_01150;
import com.example.vliao.interface4.CountDownTimerUtils;
import com.example.vliao.interface4.LogDetect;
import com.example.vliao.util.Util;*/

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.redirect.ResolverC.interface3.UsersThread_01066;
import com.net.yuesejiaoyou.redirect.ResolverC.interface3.UsersThread_01150;
import com.net.yuesejiaoyou.redirect.ResolverC.interface4.CountDownTimerUtils;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class Authen_phonenum_01150 extends BaseActivity implements OnClickListener {

	private ImageView back;
	private EditText phnum, pwd, code;
	private TextView ascertain, huoqu;
	private String code1;
	Bundle bundle;
	private PopupWindow mPopWindow;
	private Intent intent = new Intent();
	String a = "";
	private String isright = "", random = "", password = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		back = (ImageView) findViewById(R.id.back);
		back.setOnClickListener(this);
		phnum = (EditText) findViewById(R.id.phnum);
		pwd = (EditText) findViewById(R.id.pwd);
		code = (EditText) findViewById(R.id.code);
		huoqu = (TextView) findViewById(R.id.huoqu);
		huoqu.setOnClickListener(this);
		ascertain = (TextView) findViewById(R.id.ascertain);
		ascertain.setOnClickListener(this);
	}

	@Override
	protected int getContentView() {
		return R.layout.renzheng_phone_01150;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
			case R.id.back:
				finish();
				break;
			case R.id.huoqu:

				if (phnum.getText().toString().equals("")) {
					Toast.makeText(Authen_phonenum_01150.this, "请输入你的手机号", Toast.LENGTH_LONG).show();
				}else if(!isMobileNO(phnum.getText().toString())){
					Toast.makeText(Authen_phonenum_01150.this, "手机号格式不正确", Toast.LENGTH_LONG).show();
				} else {
					CountDownTimerUtils countDownTimer = new CountDownTimerUtils(huoqu, 60000, 1000);
					countDownTimer.start();
					String[] paramsMap = {"1", phnum.getText().toString()};
					UsersThread_01066 b = new UsersThread_01066("getcode", paramsMap, requestHandler);
					Thread thread = new Thread(b.runnable);
					thread.start();
				}
				break;
			case R.id.ascertain:
				Log.v("TT","ascertain: "+code.getText().toString()+","+code1);
				if(!code.getText().toString().equals(code1)){
					Toast.makeText(Authen_phonenum_01150.this, "验证码错误!", Toast.LENGTH_SHORT).show();
				}else if (phnum.getText().toString().equals("")) {
					Toast.makeText(Authen_phonenum_01150.this, "请输入手机号码!", Toast.LENGTH_SHORT).show();
				} else if (code.getText().toString().equals("")) {
					Toast.makeText(Authen_phonenum_01150.this, "请输入验证码!", Toast.LENGTH_SHORT).show();
				} else if (pwd.getText().toString().equals("")) {
					Toast.makeText(Authen_phonenum_01150.this, "请输入密码!", Toast.LENGTH_SHORT).show();
				}

				else {
					String mode1 = "check_pwd";
					String params1[] = {Util.userid, phnum.getText().toString(), pwd.getText().toString()};
					UsersThread_01150 b1 = new UsersThread_01150(mode1, params1, requestHandler);
					Thread thread1 = new Thread(b1.runnable);
					thread1.start();
				}
				break;


		}
	}

	private Handler requestHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {

				case 201:
					String json1 = (String) msg.obj;
					try {
						JSONObject jsonObject1 = new JSONObject(json1);
						code1 = jsonObject1.getString("random");
						if (!code1.equals("")) {
						} else {
							Toast.makeText(Authen_phonenum_01150.this, "获取验证码失败！", Toast.LENGTH_SHORT).show();
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case 202:
					String json = (String) msg.obj;
					try {
						JSONObject json_obj = new JSONObject(json);
						isright = json_obj.getString("pwd");
						if (isright.equals("success")) {
							Toast.makeText(Authen_phonenum_01150.this, "成功!", Toast.LENGTH_SHORT).show();
							bundle = new Bundle();
							bundle.putString("phonenum",  phnum.getText().toString());
							intent.putExtras(bundle);
							setResult(202, intent);
							finish();
						} else if (isright.equals("lose")) {
							Toast.makeText(Authen_phonenum_01150.this, "密码错误!", Toast.LENGTH_SHORT).show();
						} else if (isright.equals("lose1")) {
							Toast.makeText(Authen_phonenum_01150.this, "该号码已被注册!", Toast.LENGTH_SHORT).show();
						}

					} catch (JSONException e) {
						e.printStackTrace();
						LogDetect.send(LogDetect.DataType.basicType, "01150", e);
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
