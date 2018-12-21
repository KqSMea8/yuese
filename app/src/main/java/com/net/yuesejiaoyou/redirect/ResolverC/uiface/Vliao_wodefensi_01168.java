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
import android.widget.RelativeLayout;

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.vliaofans_01168;
import com.net.yuesejiaoyou.redirect.ResolverC.interface3.UsersThread_vliao_01168C;
import com.net.yuesejiaoyou.redirect.ResolverC.interface4.Meijian_woguanzhudeAdapter_01168;

/*import com.net.yuesejiaoyou.R;
import com.example.vliao.getset.vliaofans_01168;
import com.example.vliao.interface3.UsersThread_vliao_01168;
import com.example.vliao.interface4.LogDetect;
import com.example.vliao.interface4.Meijian_woguanzhudeAdapter_01168;
import com.example.vliao.util.Util;*/

/****************************************************************
 * 本类是显示我的粉丝明细
 ****************************************************************/
public class Vliao_wodefensi_01168 extends Activity implements OnClickListener {
	ArrayList<vliaofans_01168> list1 = new ArrayList<vliaofans_01168>();

	private ImageView fanhui;
	private RelativeLayout fanhuizong;
	private ListView l1;
	private Context context;

	private Meijian_woguanzhudeAdapter_01168 adapter1;

	/****************************************************************
	 * 初始化方法
	 * @
	 *
	 ****************************************************************/
	@Override
		protected void onCreate(Bundle savedInstanceState) {
			
			super.onCreate(savedInstanceState);
			context=this;
			setContentView(R.layout.meijian_wodefensi_01168);
			//LogDetect.send(LogDetect.DataType.specialType, "Meijian_wodefensi_01168","初始化我喜欢的");
			fanhui=(ImageView) findViewById(R.id.fanhui);
			fanhui.setOnClickListener(this);
			fanhuizong = (RelativeLayout) findViewById(R.id.fanhuizong);
			fanhuizong.setOnClickListener(this);
			l1=(ListView) findViewById(R.id.l1);
			//点击一条信息可跳转该条信息用户id的主页
			String mode="wodefensi";
			String []params={Util.userid};
			UsersThread_vliao_01168C b=new UsersThread_vliao_01168C(mode, params, handler);
			Thread thread=new Thread(b.runnable);
			thread.start();
		}

	/****************************************************************
	 * 对返回的数据进行解析
	 * @
	 *
	 ****************************************************************/
	private Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch(msg.what){
				case 202:
					/////////////////////////////
					LogDetect.send(LogDetect.DataType.specialType, "Aiba_wodefensi_01168","返回数据");
					list1=(ArrayList<vliaofans_01168>) msg.obj;
					if(list1==null){
						//Toast.makeText(Meijian_wodefensi_01168.this, "集合为空", Toast.LENGTH_SHORT).show();
						break;
					}
					//////////////////////////////
					LogDetect.send(LogDetect.DataType.specialType, "Aiba_wodefensi_01168",list1);
					//////////////////////////////
					//adapter1=new Meijian_woguanzhudeAdapter_01168(list1, context);
					adapter1=new Meijian_woguanzhudeAdapter_01168(list1, Vliao_wodefensi_01168.this);
					l1.setAdapter(adapter1);
			}
		}
		
	};

	/*****************************************************
	 * 单击事件
	 * @param arg0
	 *
	 *****************************************************/
	@Override
	public void onClick(View arg0) {
		int id=arg0.getId();
		switch(id){
			////////////////////////////////////
			case R.id.fanhuizong:
				finish();
				break;
			////////////////////////////////////
			case R.id.fanhui:
				finish();
				break;
		}
	}

}
