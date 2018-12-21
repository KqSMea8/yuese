package com.net.yuesejiaoyou.redirect.ResolverA.uiface;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.redirect.ResolverA.interface3.UsersThread_01066A;


/**
 * Created by Administrator on 2018/3/22.
 */

public class Vchat_forget2_01162 extends Activity implements View.OnClickListener {
	private TextView  get_code,xieyi,sign;
	private EditText new_phonenum,edit1,edit2,guojianum;
	private ImageView back1;
	String zhanghao="";
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vchat_resetpassword);

		back1= (ImageView) findViewById(R.id.back1);
		back1.setOnClickListener(this);
		edit1= (EditText) findViewById(R.id.phonenum);
		new_phonenum= (EditText) findViewById(R.id.new_phonenum);
		sign= (TextView) findViewById(R.id.sign);
		sign.setOnClickListener(this);

		zhanghao=getIntent().getStringExtra("zhanghao");

	}

	@Override
	public void onClick(View view) {
		int id = view.getId();
		switch (id) {

			case R.id.sign:
				if (TextUtils.isEmpty(new_phonenum.getText().toString()) || TextUtils.isEmpty(edit1.getText().toString())){
					Toast.makeText(Vchat_forget2_01162.this,"密码不能为空",Toast.LENGTH_LONG).show();
					return;
				}
				if (!new_phonenum.getText().toString().trim().equals(edit1.getText().toString().trim())){
					Toast.makeText(Vchat_forget2_01162.this,"两个密码不一致",Toast.LENGTH_LONG).show();
					return;
				}else{
					String[] paramsMap = {"1", new_phonenum.getText().toString(),zhanghao};
					UsersThread_01066A b = new UsersThread_01066A("resetpassword", paramsMap, requestHandler);
					Thread thread = new Thread(b.runnable);
					thread.start();

				}


				break;
			case R.id.back1:
				finish();
				break;

		}




	}

	String code="0";
	/**
	 * 返回json字符串，并转化为JSONObject对象
	 * 解析JSONObject对象，获取相应的数据，保存到后台服务器。
	 */
	private Handler requestHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 201:
					break;
				case 200:
					String json1 = (String) msg.obj;
					if(json1.contains("success")){
						Toast.makeText(Vchat_forget2_01162.this,"更改密码成功",Toast.LENGTH_LONG).show();

						SharedPreferences share = getSharedPreferences("Acitivity", Activity.MODE_PRIVATE);
						share.edit().putString("password", new_phonenum.getText().toString().trim()).commit();

						finish();
					}else{
						Toast.makeText(Vchat_forget2_01162.this,"更改密码失败",Toast.LENGTH_LONG).show();
					}
					break;
			}
		}
	};


}
