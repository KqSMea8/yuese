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
import com.net.yuesejiaoyou.redirect.ResolverC.interface4.Vliao_zhichuAdapter_01168;

/*import com.net.yuesejiaoyou.R;
import com.example.vliao.getset.Vliao2_01168;
import com.example.vliao.interface3.UsersThread_01168;
import com.example.vliao.interface4.Vliao_zhichuAdapter_01168;
import com.example.vliao.util.Util;*/

/***********************************************
 * 本类是主播用户支出明细
 **********************************************/
public class Vliao_zhichumingxi_01168 extends Activity implements OnClickListener {

	private ImageView fanhui;
	private ListView zhichu;
	private Context context;

	private Vliao_zhichuAdapter_01168 adapter1;

	/**************************************************
	 * 初始化界面，并发送查询普通用户支出的线程
	 * @param savedInstanceState
	 *************************************************/
	@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			context=this;
			setContentView(R.layout.vliao_zhichu_01168);
			fanhui=(ImageView) findViewById(R.id.fanhui);
			fanhui.setOnClickListener(this);
			zhichu=(ListView) findViewById(R.id.zhichu);
			String mode="zhichumingxi";
			String []params={Util.userid};
			UsersThread_01168C b=new UsersThread_01168C(mode, params, handler);
			Thread thread=new Thread(b.runnable);
			thread.start();
		}

	/**************************************************
	 * 返回普通用户支出数据，进行操作
	 * @param savedInstanceState
	 *************************************************/
	private Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch(msg.what){
			case 203:
				ArrayList<Vliao2_01168> list2=(ArrayList<Vliao2_01168>) msg.obj;
				adapter1=new Vliao_zhichuAdapter_01168(list2, context);
				zhichu.setAdapter(adapter1);
			}
			
			
		}
		
	};

	/**************************************************
	 * 单击事件
	 * @param arg0
	 *************************************************/
	@Override
	public void onClick(View arg0) {
		int id=arg0.getId();
		switch(id){
		/////////////////////////////////////////////////////
		case R.id.fanhui:
			finish();
			break;
		}
		
	}

}
