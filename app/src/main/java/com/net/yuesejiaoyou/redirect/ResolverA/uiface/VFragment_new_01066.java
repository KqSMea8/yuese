package com.net.yuesejiaoyou.redirect.ResolverA.uiface;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.net.yuesejiaoyou.R;

import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.redirect.ResolverA.getset.Page;
import com.net.yuesejiaoyou.redirect.ResolverA.getset.User_data;
import com.net.yuesejiaoyou.redirect.ResolverA.getset.photo_01162;
import com.net.yuesejiaoyou.redirect.ResolverA.interface3.UsersThread_01066A;
import com.net.yuesejiaoyou.redirect.ResolverA.interface3.UsersThread_01158A;
import com.net.yuesejiaoyou.redirect.ResolverA.interface4.VMyAdapter_01066;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.Recycle_item;
import com.net.yuesejiaoyou.redirect.ResolverC.uiface.vliao_tuiguang_01152;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;


@SuppressLint("ValidFragment")
public class VFragment_new_01066 extends Fragment implements OnClickListener, SwipeRefreshLayout.OnRefreshListener {

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
	private DisplayImageOptions options=null;
	VMyAdapter_01066 adapter;
	SwipeRefreshLayout refreshLayout;
	boolean a=false;
	
	private Handler mHandler = new Handler(Looper.getMainLooper());
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mContext=getActivity();
		view = inflater.inflate(R.layout.fragment1_01066, null);
		LogDetect.send(LogDetect.DataType.specialType, "this0","??");
		refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refreshLayout);
		options = new DisplayImageOptions.Builder().cacheInMemory(true)
				.cacheOnDisc(true).bitmapConfig(Bitmap.Config.RGB_565).build();
		refreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
				android.R.color.holo_orange_light, android.R.color.holo_green_light);
		refreshLayout.setOnRefreshListener(this);
		gridView=(RecyclerView)view.findViewById(R.id.theme_grre);
		gridView.addItemDecoration(new Recycle_item(10));
		a=true;
		pageno=1;
		
//		String mode = "new_01066";
//		//userid，页数，男女
//		String[] params = {"1", pageno+"",gender};
//		UsersThread_01066 b = new UsersThread_01066(mode, params, handler);
//		Thread thread = new Thread(b.runnable);
//		thread.start();

		String mode1="activity_search";
		String []params1={""};
		UsersThread_01158A b1=new UsersThread_01158A(mode1,params1,handler);
		Thread thread1=new Thread(b1.runnable);
		thread1.start();

		return view;
	}


	public void initdata() {
		String mode = "new_01066";

//		if (Util.gender.equals("男")){
//			gender="女";
//		}else{
//			gender="男";
//		}

		//userid，页数，男女
		String[] params = {"1", pageno+"",gender};
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
		//initdata();
		String mode1="activity_search";
		String []params1={""};
		UsersThread_01158A b1=new UsersThread_01158A(mode1,params1,handler);
		Thread thread1=new Thread(b1.runnable);
		thread1.start();
	}
	

	private GridLayoutManager mLayoutManager;
	private int lastVisibleItem = 0;
	private View headview=null;
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
				case 0:
				    break;
				case 202:
					final List<photo_01162> list1= (List<photo_01162>) msg.obj;
					ArrayList<String> mListImage=new ArrayList<String>();
					if(list1.size()!=0||list1!=null){
						LogDetect.send(LogDetect.DataType.basicType,"01160 lunbo ",list1.size());
						headview=LayoutInflater.from(mContext).inflate(R.layout.activity_banner, null);
						Banner banner=(Banner)headview.findViewById(R.id.header);
						for (int i=0;i<list1.size();i++){
							mListImage.add(list1.get(i).getPhoto()) ;
							//mListTitle.add("");
						}
						//设置Banner图片集合
						banner.setImages(mListImage);
						banner.setImageLoader(new GlideImageLaoder());
						//设置Banner动画效果
						banner.setBannerAnimation(Transformer.Tablet);
						//设置Banner标题集合（当banner样式有显示title时）
						//banner.setBannerTitles(mListTitle);
						//设置轮播时间
						banner.setDelayTime(3000);
						banner.setBackgroundColor(mContext.getResources().getColor(R.color.black));
						//设置指示器位置（当banner模式中有指示器时）
						banner.setIndicatorGravity(BannerConfig.CENTER);
						//Banner设置方法全部调用完毕时最后调用
						banner.start();

						banner.setOnBannerListener(new OnBannerListener() {
							@Override
							public void OnBannerClick(int position) {
                                if (position==2){
                                    Intent intent = new Intent();
                                    intent.setClass(getActivity(), vliao_tuiguang_01152.class);
                                    getActivity().startActivity(intent);
                                }else{
                                    Intent intent=new Intent();
                                    Bundle bundle=new Bundle();
                                    bundle.putString("photo_item",list1.get(position).getPhoto_item());
                                    LogDetect.send(LogDetect.DataType.basicType,"01162---第几个图",list1.get(position).getPhoto_item());
                                    intent.putExtras(bundle);
                                    intent.setClass(getActivity(),Activity_web_01162.class);
                                    startActivity(intent);
                                }
							}
						});
					}else{
						Toast.makeText(getActivity(),"数据解析异常",Toast.LENGTH_LONG).show();
					}

					String mode = "new_01066";
					//userid，页数，男女
					String[] params = {"1", pageno+"",gender};
					UsersThread_01066A b = new UsersThread_01066A(mode, params, handler);
					Thread thread = new Thread(b.runnable);
					thread.start();

					break;
				case 201:
					refreshLayout.setRefreshing(false);
					Page list = (Page) msg.obj;
					if (list==null){
						Toast.makeText(mContext,"网络连接异常",Toast.LENGTH_SHORT).show();
						return;
					}
					pageno = list.getCurrent();
					totlepage = list.getTotlePage();

					if (pageno==1){
						articles = list.getList();
						if (articles==null){
							//网络连接异常--NetWork Connection Exception
							Toast.makeText(mContext,"网络连接异常",Toast.LENGTH_SHORT).show();
							return;
						}
						adapter = new VMyAdapter_01066(null, mContext, false,articles);
						adapter.setHasStableIds(true);
						if (totlepage==1 || totlepage==0){
							adapter.setFadeTips(true);
						}else{
							adapter.setFadeTips(false);
						}
						mLayoutManager = new GridLayoutManager(mContext, 2);
						adapter.setHeaderView(headview);

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
							Toast.makeText(mContext,"网络连接异常",Toast.LENGTH_SHORT).show();
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

	public class GlideImageLaoder extends com.youth.banner.loader.ImageLoader {

		@Override
		public void displayImage(Context context, Object path, ImageView imageView) {
			/**
			 注意：
			 1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
			 2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
			 传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
			 切记不要胡乱强转！
			 */

			//第一种方法：Glide 加载图片简单用法
			//Glide.with(context).load(path).into(imageView);

			ImageLoader.getInstance().displayImage(
					(String) path, imageView,
					options);


			//第二种方法：Picasso 加载图片简单用法
			//Picasso.with(context).load(path).into(imageView);

			//第三种方法：fresco加载图片简单用法，记得要写下面的createImageView方法
			//Uri uri = Uri.parse((String) path);
			//imageView.setImageURI(uri);
		}

		//提供createImageView 方法，如果不用可以不重写这个方法，主要是方便自定义ImageView的创建
		@Override
		public ImageView createImageView(Context context) {

			return null;
		}
	}

}
