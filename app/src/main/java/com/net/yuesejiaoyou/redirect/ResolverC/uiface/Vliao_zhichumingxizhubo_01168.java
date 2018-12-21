package com.net.yuesejiaoyou.redirect.ResolverC.uiface;

import java.util.ArrayList;


import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.Page;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.Vliao2_01168;
import com.net.yuesejiaoyou.redirect.ResolverC.interface3.UsersThread_01168C;
import com.net.yuesejiaoyou.redirect.ResolverC.interface4.Vliao_shouruAdapter_01168;
import com.net.yuesejiaoyou.redirect.ResolverC.interface4.Vliao_zhichuzhuboAdapter_01168;




/***********************************************
 * 本类是主播用户支出明细
 **********************************************/
public class Vliao_zhichumingxizhubo_01168 extends Activity implements OnClickListener {

	private ImageView fanhui;
	private TextView shourujine;
	private ListView zhichu;
	private Context context;


	private Vliao_shouruAdapter_01168 adapter1;


	/*********************************************************
	 * 初始化控件，发送线程查询主播用户支出明细
	 * @param savedInstanceState
	 ********************************************************/
	@Override
		protected void onCreate(Bundle savedInstanceState) {
			
			super.onCreate(savedInstanceState);
			context=this;
			setContentView(R.layout.vliao_zhichuzhubo_01168);
			
			fanhui=(ImageView) findViewById(R.id.fanhui);
			fanhui.setOnClickListener(this);
			//shourujine
			shourujine=(TextView) findViewById(R.id.shourujine);
			shourujine.setOnClickListener(this);
			zhichu=(ListView) findViewById(R.id.zhichu);
			String mode="zhichuzhubo";
			String []params={Util.userid};
			UsersThread_01168C b=new UsersThread_01168C(mode, params, handler);
			Thread thread=new Thread(b.runnable);
			thread.start();
			
					
		}

	/**************************************************
	 * 返回主播用户支出数据，进行操作
	 * @param savedInstanceState
	 *************************************************/
	private Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch(msg.what){
			case 208:
				Page page= (Page) msg.obj;
	    	  	//LogDetect.send(LogDetect.DataType.specialType, "Vliao_zhichumingxizhubo_01168","进入支出明细");
	    	  	//LogDetect.send(LogDetect.DataType.specialType, "Vliao_zhichumingxizhubo_01168",page.getTotlePage()+"");
	    	  	shourujine.setText(page.getTotlePage()+"");
	    	  	ArrayList<Vliao2_01168> list2=(ArrayList<Vliao2_01168>) page.getList();
				//LogDetect.send(LogDetect.DataType.specialType, "Vliao_zhichumingxizhubo_01168",list2);
				Vliao_zhichuzhuboAdapter_01168 adapter=new Vliao_zhichuzhuboAdapter_01168(list2, Vliao_zhichumingxizhubo_01168.this,zhichu);
				zhichu.setAdapter(adapter);
			
			}
			
			
		}
		
	};

	/*******************************************
	 * 单击事件
	 * @param arg0
	 ******************************************/
	@Override
	public void onClick(View arg0) {
		int id=arg0.getId();
		switch(id){
		///////////////////////////////////////////////
		case R.id.fanhui:
			finish();
			break;
		}
		
	}

}
