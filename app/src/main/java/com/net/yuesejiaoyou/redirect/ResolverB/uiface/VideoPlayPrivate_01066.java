package com.net.yuesejiaoyou.redirect.ResolverB.uiface;

import java.util.ArrayList;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import fr.castorflex.android.verticalviewpager.VerticalViewPager;
import me.dkzwm.widget.srl.RefreshingListenerAdapter;
import me.dkzwm.widget.srl.SmoothRefreshLayout;
import me.dkzwm.widget.srl.extra.footer.ClassicFooter;
import me.dkzwm.widget.srl.extra.header.ClassicHeader;

import com.net.yuesejiaoyou.classroot.interface4.LogDetect;


import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.redirect.ResolverB.getset.Page;
import com.net.yuesejiaoyou.redirect.ResolverB.getset.Videoinfo;
import com.net.yuesejiaoyou.redirect.ResolverB.interface3.UsersThread_01066B;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.fragment.VideoPlayFragment;


public class VideoPlayPrivate_01066 extends FragmentActivity {
	private RelativeLayout layout;
	private SmoothRefreshLayout refreshLayout;
	private ClassicFooter mClassicFooter;
	private ClassicHeader mClassicHeader;
	private List<Videoinfo> videoinfolist;
	private List<Fragment> fragments;
	private FragAdapter adapter;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//无title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//全屏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
				WindowManager.LayoutParams. FLAG_FULLSCREEN);
		//getWindow().setFormat(PixelFormat.TRANSLUCENT);
		setContentView(R.layout.activity_videoplay);

		refreshLayout = (SmoothRefreshLayout)findViewById(R.id.refresh_layout);
		mClassicHeader = (ClassicHeader)findViewById(R.id.classicHeader_with_listView);
		mClassicHeader.setLastUpdateTimeKey("header_last_update_time");
		mClassicFooter = (ClassicFooter)findViewById(R.id.classicFooter_with_listView);
		mClassicFooter.setLastUpdateTimeKey("footer_last_update_time");
		mClassicHeader.setTitleTextColor(Color.WHITE);
		mClassicHeader.setLastUpdateTextColor(Color.GRAY);
		refreshLayout.setEnableKeepRefreshView(true);
		refreshLayout.setDisableLoadMore(true);
		refreshLayout.setDisableRefresh(true);
		refreshLayout.setEnableScrollToBottomAutoLoadMore(true);
		//refreshLayout.setMode(SmoothRefreshLayout.);
		//refreshLayout.setHeaderView(new ClassicHeader(this));
		refreshLayout.setOnRefreshListener(new RefreshingListenerAdapter() {
			@Override
			public void onRefreshBegin(boolean isRefresh) {

				if (!isRefresh && canPull) {
					pageno++;
					canPull=false;
					initdata();
				}else{
					refreshLayout.refreshComplete(2000);
				}
			}
		});

		Bundle bundle = getIntent().getExtras();
		int pos = bundle.getInt("id");
		videoinfolist=(List<Videoinfo>)bundle.getSerializable("vlist");

		pageno=videoinfolist.size()%10+1;
		maxid=videoinfolist.get(0).getId();
		//构造适配器
		fragments=new ArrayList<Fragment>();
		for (int i=0;i<videoinfolist.size();i++){
			fragments.add(new VideoPlayFragment(videoinfolist.get(i),i));
		}

		VerticalViewPager viewpager= (VerticalViewPager) findViewById(R.id.view_pager);

		adapter = new FragAdapter(VideoPlayPrivate_01066.this.getSupportFragmentManager(), fragments);
		viewpager.setAdapter(adapter);
		viewpager.setCurrentItem(pos);

		MsgOperReciver msgOperReciver = new MsgOperReciver();
		IntentFilter intentFilter = new IntentFilter("videoinfo");
		registerReceiver(msgOperReciver, intentFilter);

		MsgOperReciver1 msgOperReciver1 = new MsgOperReciver1();
		IntentFilter intentFilter1 = new IntentFilter("payinfo");
		registerReceiver(msgOperReciver1, intentFilter1);

	}
	private int pageno = 1,maxid,totlepage;
	private boolean canPull = true;
	public void initdata() {
		String mode = "videolist";
		//userid，页数，男女
		String[] params = {"13", pageno+"",videoinfolist.get(0).getUser_id()};
		UsersThread_01066B b = new UsersThread_01066B(mode, params, handler);
		Thread thread = new Thread(b.runnable);
		thread.start();
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
				case 260:
					refreshLayout.setDisableLoadMore(false);
					refreshLayout.setDisableRefresh(true);
					break;
				case 200:
					refreshLayout.setDisableLoadMore(true);
					refreshLayout.setDisableRefresh(false);
					break;
				case 230:
					refreshLayout.setDisableLoadMore(true);
					refreshLayout.setDisableRefresh(true);
					break;
				case 201:
					Page list = (Page) msg.obj;
					if (list==null){
						refreshLayout.refreshComplete();
						return;
					}
					pageno = list.getCurrent();
					totlepage = list.getTotlePage();
					List<Videoinfo> a = new ArrayList<Videoinfo>();
					a = list.getList();
					if (a==null){
						refreshLayout.refreshComplete();
						return;
					}else{
						if (a.size()==0){
							refreshLayout.setEnableLoadMoreNoMoreData(true);
							refreshLayout.refreshComplete(2000);
						}else{
							for (int i=0;i<a.size();i++){
								videoinfolist.add(a.get(i));
							}
							fragments.clear();
							for (int i=0;i<videoinfolist.size();i++){
								fragments.add(new VideoPlayFragment(videoinfolist.get(i),i));
							}
							adapter.notifyDataSetChanged();
							canPull=true;
							refreshLayout.setEnableLoadMoreNoMoreData(false);
							refreshLayout.refreshComplete(2000);
							refreshLayout.setDisableLoadMore(true);
							refreshLayout.setDisableRefresh(true);
						}
					}
					break;
			}
		}
	};

	private class MsgOperReciver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			int msgBody=intent.getIntExtra("downorup",0);
			LogDetect.send(LogDetect.DataType.specialType,"downorup:",msgBody);
			if (msgBody==0) {
				handler.sendMessage(handler.obtainMessage(200, (Object)msgBody));
			} else if (msgBody==(videoinfolist.size()-1)) {
				handler.sendMessage(handler.obtainMessage(260, (Object)msgBody));
			}else{
				handler.sendMessage(handler.obtainMessage(230, (Object)msgBody));
			}
		}
	}

	private class MsgOperReciver1 extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			int msgBody=intent.getIntExtra("paysuccess",0);
			LogDetect.send(LogDetect.DataType.specialType,"downorup:",msgBody);
			videoinfolist.get(msgBody).setIspay(2);
		}
	}



//	private Handler handler = new Handler() {
//		@SuppressWarnings("unchecked")
//		@Override
//		public void handleMessage(Message msg) {
//			super.handleMessage(msg);
//			switch (msg.what) {
//				case 260:
//					refreshLayout.setDisableLoadMore(false);
//					refreshLayout.setDisableRefresh(true);
//					break;
//				case 200:
//					refreshLayout.setDisableLoadMore(true);
//					refreshLayout.setDisableRefresh(false);
//					break;
//				case 230:
//					refreshLayout.setDisableLoadMore(true);
//					refreshLayout.setDisableRefresh(true);
//					break;
//
//			}
//		}
//	};

	public class FragAdapter extends FragmentPagerAdapter {

		private List<Fragment> mFragments;

		public FragAdapter(FragmentManager fm, List<Fragment> fragments) {
			super(fm);
			// TODO Auto-generated constructor stub
			mFragments=fragments;
		}

		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			return mFragments.get(arg0);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mFragments.size();
		}
		@Override
		public int getItemPosition(Object object) {
			return PagerAdapter.POSITION_NONE;
		}
	}
}
