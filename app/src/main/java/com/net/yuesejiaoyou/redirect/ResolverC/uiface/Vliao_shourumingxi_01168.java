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

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.Vliao2_01168;
import com.net.yuesejiaoyou.redirect.ResolverC.interface3.UsersThread_01168C;
import com.net.yuesejiaoyou.redirect.ResolverC.interface4.Vliao_shouruAdapter_01168;

public class Vliao_shourumingxi_01168 extends Activity implements OnClickListener {
	private ListView shouru;

	private ImageView fanhui;
	private Context context;

	private Vliao_shouruAdapter_01168 adapter1;

	/***********************************************************************************************
	 * 初始化页面，加载控件
	 * fanhui----返回
	 * shouru---明细列表
	 *
	*************************************************************************************************/

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		context=this;
		setContentView(R.layout.vliao_shouru_01168);

		fanhui=(ImageView) findViewById(R.id.fanhui);
		fanhui.setOnClickListener(this);
		shouru=(ListView) findViewById(R.id.shouru);
		String mode="shourumingxi";
		String []params={Util.userid};
		UsersThread_01168C b=new UsersThread_01168C(mode, params, handler);
		Thread thread=new Thread(b.runnable);
		thread.start();
	}

	/***********************************************************************************************
	 * 作为消息分发对象，进行发送和处理消息，并且其 Runnable 对象与一个线程的 MessageQueue 关联。
	 *
	 *************************************************************************************************/

	private Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch(msg.what){
				//////////////
				case 202:
					ArrayList<Vliao2_01168> list2=(ArrayList<Vliao2_01168>) msg.obj;
					adapter1=new Vliao_shouruAdapter_01168(list2, Vliao_shourumingxi_01168.this);
					shouru.setAdapter(adapter1);
			}
		}

	};
	/***********************************************************************************************
	 * 添加点击事件
	 * fanhui----返回上一页面
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
