package com.net.yuesejiaoyou.redirect.ResolverC.uiface;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.wang.avi.AVLoadingIndicatorView;

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.Bills_01165;
import com.net.yuesejiaoyou.redirect.ResolverC.interface3.UsersThread_01165C;
import com.net.yuesejiaoyou.redirect.ResolverC.interface4.My_V_ListAdapter_01165;



/***********************************************************************************************
 * 本类是查询我的V币明细内容(消息页面)
 *************************************************************************************************/
public class My_v_01165 extends Activity implements OnClickListener{
	private List<Bills_01165> articels = new  ArrayList<Bills_01165>();

	private ImageView back;
	private TextView no_record;
	private ListView listView;

	private AVLoadingIndicatorView avi;

	/***********************************************************************************************
	 * 初始化页面，加载控件
	 * fanhui----返回
	 * listView---明细列表
	 * avi---
	 *************************************************************************************************/
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_v_01165);
		// TODO Auto-generated method stub
		back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
        no_record = (TextView)findViewById(R.id.no_record);
		listView = (ListView)findViewById(R.id.listview);
		listView.setVisibility(View.GONE);
		avi= (AVLoadingIndicatorView) findViewById(R.id.avi);
		avi.smoothToShow();
		String mode = "my_v_search";
		String[] paramsMap = {Util.userid};
		UsersThread_01165C b = new UsersThread_01165C(mode, paramsMap,
				requestHandler);
		Thread thread = new Thread(b.runnable);
		thread.start();
	}

	/***********************************************************************************************
	 * 作为消息分发对象，进行发送和处理消息，并且其 Runnable 对象与一个线程的 MessageQueue 关联。
	 *
	 *************************************************************************************************/


	private Handler requestHandler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			//////////////
			case 200:
				@SuppressWarnings("unchecked")
				ArrayList<Bills_01165> list = (ArrayList<Bills_01165>) msg.obj;
				avi.smoothToHide();
				if (list.size() != 0) {
					listView.setVisibility(View.VISIBLE); // 本类名
					My_V_ListAdapter_01165 adapter = new My_V_ListAdapter_01165(
							My_v_01165.this,listView, null, list,
							requestHandler);
					listView.setAdapter(adapter);
				}else{
					  no_record.setVisibility(View.VISIBLE);
				};
				break;
			default:
				break;
			}
		};
	};

	/***********************************************************************************************
	 * 添加点击事件
	 * back----返回上一页面
	 *************************************************************************************************/

	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.back:
			finish();
			break;
		default:
			break;
		}
	}
}
