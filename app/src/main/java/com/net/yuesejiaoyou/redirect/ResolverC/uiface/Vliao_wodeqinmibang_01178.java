package com.net.yuesejiaoyou.redirect.ResolverC.uiface;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.vliaofans_01168;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.Page;
import com.net.yuesejiaoyou.redirect.ResolverC.interface3.UsersThread_vliao_01178C;
import com.net.yuesejiaoyou.redirect.ResolverC.interface4.vliao_wodeqinmibangAdapter_01178;


/***********************
 * 本类是查询我的亲密榜
 **********************/
public class Vliao_wodeqinmibang_01178 extends Activity implements OnClickListener {

	ArrayList<vliaofans_01168> list1 = new ArrayList<vliaofans_01168>();
	private int pageno = 1, totlepage = 0;
	private boolean canPull = true;
	private int lastVisibleItem = 0;


	private ImageView fanhui;
	private RelativeLayout fanhuizong;
	private ListView l1;
	private vliao_wodeqinmibangAdapter_01178 adapter1;
	private TextView norecord;
	private Context context;

	/*********************
	 *
	 * @param savedInstanceState
	 ********************/
	@Override
		protected void onCreate(Bundle savedInstanceState) {
			
			super.onCreate(savedInstanceState);
			context=this;
			setContentView(R.layout.vliao_qinmibanfi_01178);
			////////////////////////
			LogDetect.send(LogDetect.DataType.specialType, "vliao_qinmibanfi_01178","初始化");
			///////////////////////
			fanhui=(ImageView) findViewById(R.id.fanhui);
			fanhui.setOnClickListener(this);
			fanhuizong = (RelativeLayout) findViewById(R.id.fanhuizong);
			fanhuizong.setOnClickListener(this);
			norecord= (TextView) findViewById(R.id.no_record);
			l1=(ListView) findViewById(R.id.l1);
			//点击一条信息可跳转该条信息用户id的主页
		initdata();
		}

	/********************
	 *发送线程
	 *******************/
	public void initdata(){
		String mode="wodeqimibang";
		String[]params={"", Util.userid,pageno+""};
		UsersThread_vliao_01178C b=new UsersThread_vliao_01178C(mode, params, handler);
		Thread thread=new Thread(b.runnable);
		thread.start();
	}

	/************************
	 * 返回数据
	 ***********************/
	private Handler handler=new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
				////////////////////////////
				case 202:

					Page page = (Page) msg.obj;
					totlepage = page.getTotlePage();
					pageno = page.getPageNo();
					if (totlepage == 0||list1==null) {
						norecord.setVisibility(View.VISIBLE);
						return;
					}
					if (pageno == 1) {

						list1 = (ArrayList<vliaofans_01168>) page.getList();
						adapter1 = new vliao_wodeqinmibangAdapter_01178(list1, Vliao_wodeqinmibang_01178.this);
						l1.setAdapter(adapter1);

						l1.setOnScrollListener(new AbsListView.OnScrollListener() {
							/******
							 *
							 *****/
							@Override
							public void onScrollStateChanged(AbsListView absListView, int i) {
								if (i == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
									if (lastVisibleItem + 1 == adapter1.getCount()) {
										if (pageno == totlepage) {
										} else {
											if (canPull) {
												canPull = false;
												pageno++;
												initdata();
											}
										}
									}
								}
							}

							/*********
							 *
							 *********/
							@Override
							public void onScroll(AbsListView absListView, int i, int i1, int i2) {

								lastVisibleItem = absListView.getLastVisiblePosition();

							}
						});
					} else {
						List<vliaofans_01168> list2 = new ArrayList<vliaofans_01168>();
						list2 = page.getList();
						for (int i = 0; i < list2.size(); i++) {
							list1.add(list2.get(i));
						}
						adapter1.notifyDataSetChanged();
						canPull = true;
					}
					break;
			}
		}
	};

	/********************
	 * 单击事件
	 * @param arg0
	 *******************/
	@Override
	public void onClick(View arg0) {
		int id=arg0.getId();
		switch(id){
		case R.id.fanhuizong:
			finish();
			break;
		case R.id.fanhui:
			finish();
			break;
		}
		
	}

}
