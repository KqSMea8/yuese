package com.net.yuesejiaoyou.redirect.ResolverC.uiface;


import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;



import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.activity.VideoPlayActivity;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.Page;
import com.net.yuesejiaoyou.redirect.ResolverB.getset.Videoinfo;
import com.net.yuesejiaoyou.redirect.ResolverC.interface3.UsersThread_01160;
import com.net.yuesejiaoyou.redirect.ResolverC.interface4.VideoMyAdapter_01066;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@SuppressLint("ValidFragment")
public class MyTeasure_like_01160 extends Fragment implements OnClickListener, SwipeRefreshLayout.OnRefreshListener {

	private Context mContext;
	String sort;
	private RecyclerView gridView;
	private List<Videoinfo> articles = new ArrayList<Videoinfo>();
	private View view;
	private int pageno = 1, totlepage = 0;
	private boolean canPull = true;
	private int pos;
	private TextView shaixuan;
	private boolean isinit=false;
	private boolean iskai=false;
	VideoMyAdapter_01066 adapter;
	SwipeRefreshLayout refreshLayout;
	private int maxid=1000000000;
	private TextView empty;

	private Handler mHandler = new Handler(Looper.getMainLooper());
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mContext=getActivity();
		view = inflater.inflate(R.layout.videofragment2_01066, null);
		LogDetect.send(LogDetect.DataType.specialType, "this0","??");
		refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refreshLayout);

		refreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
				android.R.color.holo_orange_light, android.R.color.holo_green_light);
		refreshLayout.setOnRefreshListener(this);

		gridView=(RecyclerView)view.findViewById(R.id.theme_grre);

		empty = (TextView) view.findViewById(R.id.empty);

		pageno=1;

		//我喜欢的
		initdata();


		msgOperReciver2 = new MsgOperReciver2();
		IntentFilter intentFilter2 = new IntentFilter("dianzan");
		getActivity().registerReceiver(msgOperReciver2, intentFilter2);


		return view;
	}

	private MsgOperReciver2 msgOperReciver2;

	@Override
	public void onDestroy() {
		getActivity().unregisterReceiver(msgOperReciver2);
		super.onDestroy();
	}

	private boolean isshow=false;
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		isshow=isVisibleToUser;
	};


	private class MsgOperReciver2 extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			int msgBody=intent.getIntExtra("dianzan",0);
			int iszan=intent.getIntExtra("iszan",0);
			LogDetect.send(LogDetect.DataType.specialType,"downorup:",msgBody);

			if (msgBody<articles.size() && isshow){
				//articles.get(msgBody).setIspay(2);
				int num= Integer.parseInt(articles.get(msgBody).getLike_num());
				if (iszan==0){
					articles.get(msgBody).setLike_num((num-1)+"");
				}else{
					articles.get(msgBody).setLike_num((num+1)+"");
				}
				handler.sendMessage(handler.obtainMessage(0,(Object)msgBody));
			}
		}
	}


	private void initdata() {
		String mode = "i_like";
		//userid，页数，男女
		String[] params = {Util.userid, pageno+"",maxid+""};
		UsersThread_01160 b = new UsersThread_01160(mode, params, handler);
		Thread thread = new Thread(b.runnable);
		thread.start();
	}


	@Override
	public void onRefresh() {
		// 设置可见
		refreshLayout.setRefreshing(true);
		pageno=1;
		maxid=1000000000;
		initdata();

	}


	private GridLayoutManager mLayoutManager;
	private int lastVisibleItem = 0;
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
				case 0:
					String pos= msg.obj.toString();
					adapter.notifyItemChanged(Integer.valueOf(pos));
					break;

				case 201:
					refreshLayout.setRefreshing(false);
					Page list = (Page) msg.obj;
					if (list==null){
						//Toast.makeText(mContext,"请检查网络连接", Toast.LENGTH_SHORT).show();
						return;
					}
					pageno = list.getCurrent();
					totlepage = list.getTotlePage();

					if (pageno==1){

						articles = list.getList();
						/*if (articles==null){
							Toast.makeText(mContext,"请检查网络连接",Toast.LENGTH_SHORT).show();
							return;
						}*/
						if(articles != null){
							if(articles.size() != 0){
								gridView.setVisibility(View.VISIBLE);
								empty.setVisibility(View.GONE);
								maxid=articles.get(0).getId();
								adapter = new VideoMyAdapter_01066(null, mContext, false,articles);
								adapter.setHasStableIds(true);
								if (totlepage==1){
									adapter.setFadeTips(true);
								}else{
									adapter.setFadeTips(false);
								}
								mLayoutManager = new GridLayoutManager(mContext, 2);

								gridView.setLayoutManager(mLayoutManager);
								gridView.setAdapter(adapter);
								gridView.setItemAnimator(new DefaultItemAnimator());
								((SimpleItemAnimator)gridView.getItemAnimator()).setSupportsChangeAnimations(false);
								gridView.getItemAnimator().setChangeDuration(0);
								//gridView.addItemDecoration(new RecycleViewDivider(
								//		mContext, LinearLayoutManager.VERTICAL, 10, getResources().getColor(R.color.divide_gray_color)));
								gridView.addOnScrollListener(new RecyclerView.OnScrollListener() {
									@Override
									public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
										super.onScrollStateChanged(recyclerView, newState);
										if (newState == RecyclerView.SCROLL_STATE_IDLE) {
											if (lastVisibleItem + 1 == adapter.getItemCount()) {
//									mHandler.postDelayed(new Runnable() {
//										@Override
//										public void run() {
//											//updateRecyclerView(adapter.getRealLastPosition(), adapter.getRealLastPosition() + PAGE_COUNT);
//										}
//									}, 500);

												if (pageno==totlepage) {

												}else{
													if (canPull){
														canPull=false;
														pageno++;
														initdata();
													}
												}
											}
										}
									}

									@Override
									public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
										super.onScrolled(recyclerView, dx, dy);
										lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
									}
								});

								adapter.setOnItemClickLitsener(new VideoMyAdapter_01066.onItemClickListener() {
									@Override
									public void onItemClick(View view, int position) {
										/***********
										 * 取消下面的注释可删除243行,296行还有一处需要修改
										 **********/
										//Intent intent = new Intent();
										/***************************
										 * 播放视频暂注释
										 **************************/
										Intent intent = new Intent(mContext, VideoPlayActivity.class);
										Bundle bundle = new Bundle();
										bundle.putInt("id", position);
										bundle.putSerializable("vlist",(Serializable)articles);
										intent.putExtras(bundle);
										startActivity(intent);
										LogDetect.send(LogDetect.DataType.specialType,"01160 video:","短时间按");
									}

									@Override
									public void onItemLongClick(View view, int position) {

									}
								}) ;

							}else {
								empty.setVisibility(View.VISIBLE);
								gridView.setVisibility(View.GONE);
								empty.setText("您还没有喜欢的短视频");
							}
						}else{
							//Toast.makeText(mContext,"请检查网络连接", Toast.LENGTH_SHORT).show();
						}

					}else{

						List<Videoinfo> a = new ArrayList<Videoinfo>();
						a = list.getList();
						if (a==null){
							canPull=true;
							pageno--;
							//Toast.makeText(mContext,"网络请求失败", Toast.LENGTH_SHORT).show();
							return;
						}
						for (int i=0;i<a.size();i++){
							articles.add(a.get(i));
						}
						adapter.notifyDataSetChanged();

						canPull=true;
						if (totlepage==pageno){
							adapter.setFadeTips(true);
						}else{
							adapter.setFadeTips(false);
						}

						adapter.setOnItemClickLitsener(new VideoMyAdapter_01066.onItemClickListener() {
							@Override
							public void onItemClick(View view, int position) {
								/***********
								 * 取消下面的注释可删除299行
								 **********/
								//Intent intent = new Intent();
								/***************************
								 * 播放视频暂注释
								 **************************/
								Intent intent = new Intent(mContext, VideoPlayActivity.class);
								Bundle bundle = new Bundle();
								bundle.putInt("id", position);
								bundle.putSerializable("vlist",(Serializable)articles);
								intent.putExtras(bundle);
								startActivity(intent);
								LogDetect.send(LogDetect.DataType.specialType,"01160 video:","短时间按");
							}

							@Override
							public void onItemLongClick(View view, int position) {

							}
						}) ;

					}


					break;
			}
		}
	};
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onResume() {
		super.onResume();
		initdata();
	}

}
