package com.net.yuesejiaoyou.redirect.ResolverD.uiface;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.ShareHelp;

import java.util.Map;

/**
 * Created by Administrator on 2018/5/18.
 */

public class TestActivity extends Activity implements View.OnClickListener {
private TextView wxlogin,wxshare,wxpay;
ShareHelp shareHelp=new ShareHelp();

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test1);
        wxlogin=(TextView) findViewById(R.id.wxlogin);
        wxshare=(TextView) findViewById(R.id.wxshare);
        wxpay=(TextView) findViewById(R.id.wxpay);
        wxlogin.setOnClickListener(this);
        wxshare.setOnClickListener(this);
        wxpay.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		int id=view.getId();
		switch (id){
			case R.id.wxlogin:
                    shareHelp.wx_login(handler,"0");

				break;
			case R.id.wxshare:
                   shareHelp.showShare(TestActivity.this,"");

				break;
			case R.id.wxpay:
				Intent intent=new Intent();
				intent.setClass(TestActivity.this,Chongzhi_01178.class);
				startActivity(intent);

				break;
		}


	}

	private Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what){
				case 20000:

					Map<Object, Object> maps=(Map<Object, Object>) msg.obj;
					LogDetect.send("微信登录返回的值maps ", maps);
				 Toast.makeText(TestActivity.this, "微信maps:"+maps.toString(), Toast.LENGTH_LONG).show();

					break;


			}


		}
	};




}
