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
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.Page;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.Vliao2_01168;
import com.net.yuesejiaoyou.redirect.ResolverC.interface3.UsersThread_01168C;
import com.net.yuesejiaoyou.redirect.ResolverC.interface4.Vliao_shouruAdapter_01168;
import com.net.yuesejiaoyou.redirect.ResolverC.interface4.Vliao_shouruzhuboAdapter_01168;


/**********************
 * 本类是查询主播收益明细
 *********************/
public class Vliao_shourumingxizhubo_01168 extends Activity implements OnClickListener {
	private ImageView fanhui;
	private TextView shourujine;
	private ListView shouru;
	private Context context;
	private Vliao_shouruAdapter_01168 adapter1;

	/***********************************************************************************************
	 * 初始化页面，加载控件
	 * fanhui----返回
	 * shourujine---明细列表
	 *************************************************************************************************/
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		context=this;
		setContentView(R.layout.vliao_shouruzhubo_01168);
		fanhui=(ImageView) findViewById(R.id.fanhui);
		fanhui.setOnClickListener(this);
		//shourujine
		shourujine=(TextView) findViewById(R.id.shourujine);
		shourujine.setOnClickListener(this);
		shouru=(ListView) findViewById(R.id.shouru);
		String mode="shouruzhubo";
		String []params={Util.userid};
		UsersThread_01168C b=new UsersThread_01168C(mode, params, handler);
		Thread thread=new Thread(b.runnable);
		thread.start();


	}

	/***********************************************************************************************
	 * 作为消息分发对象，进行发送和处理消息，并且其 Runnable 对象与一个线程的 MessageQueue 关联。
	 *
	 *
	 *************************************************************************************************/

	private Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch(msg.what){
				//////////
				case 207:
					Page page= (Page) msg.obj;
					shourujine.setText(page.getTotlePage()+"");
					ArrayList<Vliao2_01168> list2=(ArrayList<Vliao2_01168>) page.getList();
					////////////////
					LogDetect.send(LogDetect.DataType.basicType,"01162----list集合",list2);
					Vliao_shouruzhuboAdapter_01168 adapter=new Vliao_shouruzhuboAdapter_01168(list2, Vliao_shourumingxizhubo_01168.this,shouru);
					shouru.setAdapter(adapter);
			}
		}

	};

	/***********************************************************************************************
	 * 添加点击事件
	 *fanhui---返回上一页面
	 *
	 *************************************************************************************************/

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
