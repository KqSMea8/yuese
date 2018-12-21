package com.net.yuesejiaoyou.redirect.ResolverC.uiface;

import java.util.ArrayList;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.Page;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.Vliao2_01168;
import com.net.yuesejiaoyou.redirect.ResolverC.interface3.UsersThread_01168C;
import com.net.yuesejiaoyou.redirect.ResolverC.interface4.Vliao_shouruAdapter_01168;
import com.net.yuesejiaoyou.redirect.ResolverC.interface4.Vliao_tixianzhuboAdapter_01168;

/*
import com.net.yuesejiaoyou.R;
import com.example.vliao.getset.Page;
import com.example.vliao.getset.Vliao2_01168;
import com.example.vliao.interface3.UsersThread_01168;
import com.example.vliao.interface4.LogDetect;
import com.example.vliao.interface4.Vliao_shouruAdapter_01168;
import com.example.vliao.interface4.Vliao_shouruzhuboAdapter_01168;
import com.example.vliao.interface4.Vliao_tixianzhuboAdapter_01168;
import com.example.vliao.util.Util;*/

/******************************
 * 本类是查询主播提现明细
 *****************************/
public class Vliao_tixianmingxizhubo_01168 extends Activity implements OnClickListener {
private ImageView fanhui;
private TextView shourujine, time;
private ListView tixian;
private Context context;
private Vliao_shouruAdapter_01168 adapter1;

	/**************************
	 * V聊主播提现明细继承Activity类并实现OnClickListener接口
	 ***********************/
	@Override
		protected void onCreate(Bundle savedInstanceState) {
			
			super.onCreate(savedInstanceState);
			context=this;
			setContentView(R.layout.vliao_tixianzhubo_01168);
			
			fanhui=(ImageView) findViewById(R.id.fanhui);
			fanhui.setOnClickListener(this);
			//shourujine
			shourujine=(TextView) findViewById(R.id.shourujine);
			shourujine.setOnClickListener(this);
			tixian=(ListView) findViewById(R.id.tixian);
			time = (TextView) findViewById(R.id.time);
			String mode="tixianzhubo";
			String []params={Util.userid};
			UsersThread_01168C b=new UsersThread_01168C(mode, params, handler);
			Thread thread=new Thread(b.runnable);
			thread.start();
		}

	/*********************
	 *返回数据
	 ********************/
	private Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch(msg.what){
			//////////////////
			case 209:
				Page page= (Page) msg.obj;
	    		 // LogDetect.send(LogDetect.DataType.specialType, "Vliao_zhichumingxizhubo_01168","进入支出明细");
	    	 	// LogDetect.send(LogDetect.DataType.specialType, "Vliao_zhichumingxizhubo_01168",page.getTotlePage()+"");
	    	  	shourujine.setText(page.getTotlePage()+"");
	    	  	ArrayList<Vliao2_01168> list2=(ArrayList<Vliao2_01168>) page.getList();
				//LogDetect.send(LogDetect.DataType.specialType, "Vliao_zhichumingxizhubo_01168",list2);
	    	  	
	    	  	if(!list2.isEmpty()){
	    	  		time.setVisibility(View.VISIBLE);
	    	  	}
	    	  	
				Vliao_tixianzhuboAdapter_01168 adapter=new Vliao_tixianzhuboAdapter_01168(list2, Vliao_tixianmingxizhubo_01168.this,tixian);
				tixian.setAdapter(adapter);
			}
		}
		
	};
	/****************
	 * 单击事件
	 * @param arg0
	 ****************/
	@Override
	public void onClick(View arg0) {
		int id=arg0.getId();
		switch(id){
		case R.id.fanhui:
			finish();
			break;
		}
		
	}

}
