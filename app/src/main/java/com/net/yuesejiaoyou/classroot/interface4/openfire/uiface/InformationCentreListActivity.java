package com.net.yuesejiaoyou.classroot.interface4.openfire.uiface;

import java.util.ArrayList;
import java.util.List;

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.bean.Session;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.db.Const;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.db.SessionDao;
import com.net.yuesejiaoyou.classroot.interface4.openfire.interface4.SessionAdapter;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class InformationCentreListActivity extends Activity {
	private Context mContext;
	private View mBaseView;
	private SessionDao sessionDao;
	private MsgOperReciver msgOperReciver;
	private List<Session> sessionList = new ArrayList<Session>();
	private String shopid;
	private SessionAdapter adapter;
	private ListView lv_news;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		setContentView(R.layout.infocenter_list_01160);
		sessionDao=new SessionDao(mContext);
		msgOperReciver = new MsgOperReciver();
		IntentFilter intentFilter1 = new IntentFilter(Const.ACTION_MSG_OPER);
		IntentFilter intentFilter2 = new IntentFilter(Const.ACTION_ADDFRIEND);
		registerReceiver(msgOperReciver, intentFilter1);
		registerReceiver(msgOperReciver, intentFilter2);
		
		lv_news = (ListView) this.findViewById(R.id.lv_news);
		lv_news.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
				final Session session=sessionList.get(arg2);
				Intent intent = new Intent(InformationCentreListActivity.this, ChatActivity.class);

				intent.putExtra("id", session.getFrom());
				intent.putExtra("name", session.getName());
				intent.putExtra("headpic", session.getHeadpic());
				startActivity(intent);
			}
		});
		
		//SharedPreferences share=this.getSharedPreferences("Acitivity",Activity.MODE_PRIVATE);
		//shopid=share.getString("shopid","");
		init();
    }
	
	protected void init() {
		// TODO Auto-generated method stub
		SharedPreferences share=this.getSharedPreferences("Acitivity",Activity.MODE_PRIVATE);
		String id = share.getString("id","");
		//LogDetect.send(DataType.basicType,Utils.seller_id+"=phone="+Utils.android,shopid);
		initData(id);
	}
	
	private class MsgOperReciver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			init();
		}
	}
	private void initData(String shopid) {
		//注意，当数据量较多时，此处要开线程了，否则阻塞主线程
		//LogDetect.send(DataType.basicType,Utils.seller_id+"=phone="+Utils.android,shopid);
		/*if(shopid.equals("0")){
			LogDetect.send(DataType.basicType,Utils.seller_id+"=phone="+Utils.android,shopid);
			Toast.makeText(getActivity(), "您没有登录！",
					Toast.LENGTH_SHORT).show();
			sessionList=sessionDao.queryAllSessions("服务");
		}else{*/
			sessionList=sessionDao.queryAllSessions(shopid);
			Log.v("PAOPAO","sessionList:"+sessionList.size());
		//}
		adapter = new SessionAdapter(this, sessionList,this,lv_news);
		lv_news.setAdapter(adapter);
	}
	
	@Override
	public void onDestroy() {
		//handler1.removeCallbacksAndMessages(null);
		//timer.cancel();
		//LogDetect.send(DataType.specialType, "Memory: ",
		//		LogDetect.getRunningAppProcessInfo() + "KB");
		//msgOperReciver.
		//this.msgOperReciver.clearAbortBroadcast();
		super.onDestroy();

		//LogDetect.send(DataType.specialType, Utils.seller_id + "=phone="
		//		+ Utils.android, "onDestroy");
	}
	
	
}
