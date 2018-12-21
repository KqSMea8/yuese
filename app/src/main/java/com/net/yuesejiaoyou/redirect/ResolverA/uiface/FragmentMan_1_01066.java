package com.net.yuesejiaoyou.redirect.ResolverA.uiface;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.redirect.ResolverA.getset.Page;
import com.net.yuesejiaoyou.redirect.ResolverA.getset.User_data;
import com.net.yuesejiaoyou.redirect.ResolverA.interface3.UsersThread_01066A;
import com.net.yuesejiaoyou.redirect.ResolverA.interface4.MyAdapterMan_01066;

import java.util.ArrayList;
import java.util.List;


@SuppressLint("ValidFragment")
public class FragmentMan_1_01066 extends Fragment implements OnClickListener, SwipeRefreshLayout.OnRefreshListener {

	private Context mContext;
	String sort;
	private RecyclerView gridView;
	private List<User_data> articles = new ArrayList<User_data>();
	private View view;
	private int pageno = 1, totlepage = 0;
	private boolean canPull = true;
	private int pos;
	private TextView shaixuan;
	private boolean isinit=false;
	private boolean iskai=false;
	MyAdapterMan_01066 adapter;
	SwipeRefreshLayout refreshLayout;
	
	private Handler mHandler = new Handler(Looper.getMainLooper());
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mContext=getActivity();
		view = inflater.inflate(R.layout.fragment1_01066, null);
		LogDetect.send(LogDetect.DataType.specialType, "this0","??");
		refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refreshLayout);

		refreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
				android.R.color.holo_orange_light, android.R.color.holo_green_light);
		refreshLayout.setOnRefreshListener(this);

		gridView=(RecyclerView)view.findViewById(R.id.theme_grre);


		pageno=1;
		
		String mode1 = "remenman";
		String []params1={"",pageno+""};
		UsersThread_01066A b = new UsersThread_01066A(mode1, params1, handler);
		Thread thread = new Thread(b.runnable);
		thread.start();
		

		return view;
	}


	public void initdata() {
		String mode1 = "remenman";
		//userid，页数，男女
		String []params1={"",pageno+""};
		UsersThread_01066A b = new UsersThread_01066A(mode1, params1, handler);
		Thread thread = new Thread(b.runnable);
		thread.start();
	}


	String gender="女";
    @Override
	public void onRefresh() {
		// 设置可见
		refreshLayout.setRefreshing(true);
		pageno=1;
		initdata();

	}
	

	private GridLayoutManager mLayoutManager;
	private int lastVisibleItem = 0;
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
				case 0:
				    break;
				
				case 201:
					refreshLayout.setRefreshing(false);
					Page list = (Page) msg.obj;
					if (list==null){
						Toast.makeText(mContext,"网络请求失败", Toast.LENGTH_SHORT).show();
						return;
					}
					pageno = list.getCurrent();
					totlepage = list.getTotlePage();

					if (pageno==1){
						articles = list.getList();
						if (articles==null){
							Toast.makeText(mContext,"网络请求失败", Toast.LENGTH_SHORT).show();
							return;
						}
						adapter = new MyAdapterMan_01066(null, mContext, false,articles);
						adapter.setHasStableIds(true);
						if (totlepage==1){
							adapter.setFadeTips(true);
						}else{
							adapter.setFadeTips(false);
						}
						mLayoutManager = new GridLayoutManager(mContext, 4);

						gridView.setLayoutManager(mLayoutManager);
						gridView.setAdapter(adapter);
						gridView.setItemAnimator(new DefaultItemAnimator());

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
					}else{

						List<User_data> a = new ArrayList<User_data>();
						a = list.getList();
						if (a==null){
							canPull=true;
							pageno--;
							Toast.makeText(mContext,"网络请求失败", Toast.LENGTH_SHORT).show();
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
					}
					break;
			}
		}
	};
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}


}
