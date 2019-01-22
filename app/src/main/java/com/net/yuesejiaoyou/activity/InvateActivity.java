package com.net.yuesejiaoyou.activity;


import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.net.yuesejiaoyou.redirect.ResolverD.interface4.ShareHelp;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;


import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.redirect.ResolverA.getset.Member_01152;
import com.net.yuesejiaoyou.redirect.ResolverA.interface3.UsersThread_01152A;


public class InvateActivity extends Activity implements OnClickListener {
	//自定义ID
	
	private ImageView back,qr_code,share;
	private TextView yaoqingma;
	
	private ArrayList<Member_01152> list;
	DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
			.cacheOnDisc(true).bitmapConfig(Bitmap.Config.RGB_565).build();
	@Override
	/**
	 * 对页面进行初始化
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LogDetect.send(LogDetect.DataType.specialType, "InvateActivity:", "布局开始");
		setContentView(R.layout.activity_invate);
		LogDetect.send(LogDetect.DataType.specialType, "InvateActivity:", "开始=====");
		back = (ImageView) findViewById(R.id.back);
		back.setOnClickListener(this);
		yaoqingma = (TextView) findViewById(R.id.yaoqingma);

		share = (ImageView)findViewById(R.id.share);
		share.setOnClickListener(this);

		qr_code = (ImageView)findViewById(R.id.qr_code);
		options = new DisplayImageOptions.Builder().cacheInMemory(true).imageScaleType(ImageScaleType.EXACTLY)
				.cacheOnDisc(true).bitmapConfig(Bitmap.Config.RGB_565).build();

		LogDetect.send(LogDetect.DataType.specialType, "InvateActivity:", "结束=====");
	
		//发请求
			String mode = "gerenzhongxin";
			String[] paramMap = {Util.userid};
			LogDetect.send(LogDetect.DataType.specialType, "InvateActivity:", "参数=======");
			UsersThread_01152A b = new UsersThread_01152A(mode, paramMap, handler);
			Thread th= new Thread(b.runnable);
			th.start();
	}
	

	//获得onclick点击事件
	@Override
	public void onClick(View v) {
		
		int id = v.getId();
		switch (id) {		
		case R.id.back: //返回页面
			this.finish();
			break;
		case R.id.share:
			ShareHelp shareHelp1=new ShareHelp();
			shareHelp1.showShare(InvateActivity.this,Util.invite_num);
			break;
		}
	}
	
	
	private Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 200:
				ArrayList<Member_01152> list = (ArrayList<Member_01152>)msg.obj;
				if(list==null || list.size()==0){
		    		  Toast.makeText(InvateActivity.this, "网络出错", Toast.LENGTH_SHORT).show();
		    		  break;
		    	  }
				ImageLoader.getInstance().displayImage(
						list.get(0).getQrcodeimg(),
						qr_code, options);
				yaoqingma.setText(list.get(0).getInvite_num());
				LogDetect.send(LogDetect.DataType.basicType,"152+++++++++",list.get(0).getInvite_num());
				
				break;
				
			}
		}
	};

}
