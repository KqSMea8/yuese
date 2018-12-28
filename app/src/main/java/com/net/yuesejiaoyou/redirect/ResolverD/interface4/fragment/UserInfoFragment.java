package com.net.yuesejiaoyou.redirect.ResolverD.interface4.fragment;


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

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.redirect.ResolverA.getset.User_data;
import com.net.yuesejiaoyou.redirect.ResolverA.interface3.UsersThread_01066A;
import com.net.yuesejiaoyou.redirect.ResolverA.interface4.VMyAdapterzl_01066;

import java.util.ArrayList;
import java.util.List;


@SuppressLint("ValidFragment")
public class UserInfoFragment extends Fragment implements OnClickListener, SwipeRefreshLayout.OnRefreshListener {

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
	VMyAdapterzl_01066 adapter;
	SwipeRefreshLayout refreshLayout;
	private User_data userinfo;
	String zhubo_id;
	public UserInfoFragment(User_data userinfo){
		this.userinfo=userinfo;
	}
	public UserInfoFragment(){
	}
	private Handler mHandler = new Handler(Looper.getMainLooper());
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mContext=getActivity();
		view = inflater.inflate(R.layout.fragment_userinfo, null);
		LogDetect.send(LogDetect.DataType.specialType, "this0","??");
		gridView=(RecyclerView)view.findViewById(R.id.theme_grre);
		pageno=1;
		///////////////////////////
		LogDetect.send(LogDetect.DataType.specialType, "用户信息:","22223235654646565");
		///////////////////////////
		if(userinfo == null) {
			zhubo_id="0";
		} else {
			zhubo_id = userinfo.getId() + "";
		}
		//zhubo_id= (String) getArguments().get("user_id");
		String mode = "evaluate_list";
		//userid，页数，男女
		String[] params = {"13",zhubo_id};
		UsersThread_01066A b = new UsersThread_01066A(mode, params, handler);
		Thread thread = new Thread(b.runnable);
		thread.start();
		int length=2;
		return view;
	}


	public void initdata() {
		String mode = "xunyuan_01066";
		String[] params = {zhubo_id, pageno+"",gender};
		UsersThread_01066A b = new UsersThread_01066A(mode, params, handler);
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
					//refreshLayout.setRefreshing(false);
					ArrayList<User_data> articles = (ArrayList<User_data>) msg.obj;
						adapter = new VMyAdapterzl_01066(null, mContext, true,articles,userinfo);
					    adapter.setFadeTips(true);
						mLayoutManager = new GridLayoutManager(mContext, 1);
						gridView.setLayoutManager(mLayoutManager);
						gridView.setAdapter(adapter);
						gridView.setItemAnimator(new DefaultItemAnimator());
						gridView.addOnScrollListener(new RecyclerView.OnScrollListener() {
							@Override
							public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
								super.onScrollStateChanged(recyclerView, newState);
							}

							@Override
							public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
								super.onScrolled(recyclerView, dx, dy);
								lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
							}
						});

					break;
			}
		}
	};
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}


}
