package com.net.yuesejiaoyou.redirect.ResolverA.uiface;
/**
 * 发现首页
 */


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.redirect.ResolverA.getset.Update;
import com.net.yuesejiaoyou.redirect.ResolverA.interface3.UsersThread_01107A;
import com.net.yuesejiaoyou.redirect.ResolverC.uiface.vliao_tuiguang_01152;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;


@SuppressLint("ResourceAsColor")
public class VFragment1_01066 extends Fragment implements OnClickListener{
	private Context mContext;
	private View mBaseView;
	private DisplayImageOptions options;
	private TextView tuijain,guanzhu,fujin;
	private View xrxian,hyxian;
	private VFragment_1_01066 f1;
	private VFragment_1_01066 f2;
	private VFragment_1_01066 f3;
	private GifImageView search;

	private LinearLayout search_lr;

	private SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
	private SharedPreferences sharedPreferences;
	private PopupWindow popupWindow;
	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mContext = getActivity();
		
		mBaseView = inflater.inflate(R.layout.vfragment_01066, null);
        //得到AssetManager
		AssetManager mgr=mContext.getAssets();

		sharedPreferences = getActivity().getSharedPreferences("Acitivity",
				Context.MODE_PRIVATE);

		search = (GifImageView) mBaseView.findViewById(R.id.search);
		search.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent();
				intent.setClass(mContext, SearchDv_01160.class);//编辑资料
				startActivity(intent);
			}
		});

		search_lr = (LinearLayout) mBaseView.findViewById(R.id.search_lr);
		search_lr.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent();
				intent.setClass(mContext, SearchDv_01160.class);//编辑资料
				startActivity(intent);
			}
		});
		
		//根据路径得到Typeface
		Typeface tf= Typeface.createFromAsset(mgr, "fonts/arialbd.ttf");
        //构造适配器
		List<Fragment> fragments=new ArrayList<Fragment>();
		/*fragments.add(new VFragment_attend_01066());*/
		if(!"0".equals(Util.iszhubo)) {
			fragments.add(new FragmentMan_1_01066());
			fragments.add(new VFragment_1_01066());
			fragments.add(new VFragment_new_01066());
			fragments.add(new VFragment_activity_01066());
		}else{
			fragments.add(new VFragment_activity_01066());
			fragments.add(new VFragment_1_01066());
			fragments.add(new VFragment_new_01066());
			fragments.add(new VFragment_attend_01066());
		}

		/*fragments.add(new VFragment_3s_01066());
		fragments.add(new VFragment_4s_01066());
		fragments.add(new VFragment_5s_01066());*/
		ViewPager viewpager= (ViewPager) mBaseView.findViewById(R.id.viewpager1);
		FragAdapter adapter = new FragAdapter(getActivity().getSupportFragmentManager(), fragments);
		viewpager.setAdapter(adapter);
		TabLayout mTabLayout= (TabLayout) mBaseView.findViewById(R.id.tabs);
		mTabLayout.setupWithViewPager(viewpager);
		viewpager.setCurrentItem(0);

		LogDetect.send(LogDetect.DataType.specialType, "Fragment_1: ", "here");

		//checkUpdate();

		// 每日一弹
		String getday = sharedPreferences.getString("today","");
		String curday = sd.format(new Date());
		if(!curday.equals(getday)) {
			sharedPreferences.edit().putString("today",curday).commit();
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					showPopupspWindowEveryday(search);
				}
			},1000);
		}
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				String mode = "check_update";
				String[] params = {""};
				UsersThread_01107A b = new UsersThread_01107A(mode, params, requestHandler);
				Thread thread = new Thread(b.runnable);
				thread.start();
			}
		},1200);


		return mBaseView;
	}

	public void showPopupspWindowEveryday(View parent) {
		// 加载布局
		LayoutInflater inflater = LayoutInflater.from(getActivity());
		View layout = inflater.inflate(R.layout.popup_everyday, null);
		//取消
//		TextView quxiao;

//
//		c1 = (CheckedTextView) layout.findViewById(R.id.c1);
//		c2 = (CheckedTextView) layout.findViewById(R.id.c2);
//		c3 = (CheckedTextView) layout.findViewById(R.id.c3);
//		c4 = (CheckedTextView) layout.findViewById(R.id.c4);
//		c5 = (CheckedTextView) layout.findViewById(R.id.c5);
//		c6 = (CheckedTextView) layout.findViewById(R.id.c6);
//
//		c1.setOnClickListener(this);
//		c2.setOnClickListener(this);
//		c3.setOnClickListener(this);
//		c4.setOnClickListener(this);
//		c5.setOnClickListener(this);
//		c6.setOnClickListener(this);

//		quxiao = (TextView)layout.findViewById(R.id.quxiao);
//		quxiao.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				popupWindow.dismiss();
//			}
//		});

		layout.findViewById(R.id.img_ad).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), vliao_tuiguang_01152.class);
				getActivity().startActivity(intent);
				popupWindow.dismiss();
			}
		});


		popupWindow = new PopupWindow(layout, ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT, true);
		// 控制键盘是否可以获得焦点
		popupWindow.setFocusable(true);
		WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
		lp.alpha = 0.5f;
		getActivity().getWindow().setAttributes(lp);
		popupWindow.setBackgroundDrawable(new BitmapDrawable(null, ""));
		popupWindow.setOutsideTouchable(true);
		//popupWindow.showAsDropDown(parent, 0, 0,Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL);
		popupWindow.showAtLocation(parent, Gravity.CENTER_VERTICAL, 0, 0);
		popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
			public void onDismiss() {
				WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
				lp.alpha = 1f;
				getActivity().getWindow().setAttributes(lp);
			}
		});

		ImageView imgClose = layout.findViewById(R.id.img_close);
		imgClose.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				popupWindow.dismiss();
			}
		});
	}

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
		public CharSequence getPageTitle(int position) {

			if(!"0".equals(Util.iszhubo)) {
				switch (position) {
				/*case 0:
					return "关注";*/
					case 0:
						return "用户";
					case 1:
						return "推荐";
					case 2:
						return "新人";
					case 3:
						return "活跃";
				/*case 3:
					return "三星";
				case 4:
					return "四星";
				case 5:
					return "五星";*/
					default:
						return "推荐";
				}
			}else{
				switch (position) {
				case 0:
					return "活跃";
					case 1:
						return "推荐";
					case 2:
						return "新人";
					case 3:
						return "关注";
				/*case 3:
					return "三星";
				case 4:
					return "四星";
				case 5:
					return "五星";*/
					default:
						return "推荐";
				}
			}
		}

	}

	/*@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		LogDetect.send(LogDetect.DataType.basicType, "apk下载链接: ",requestCode);
		if (resultCode == RESULT_OK && requestCode == 321) {
			LogDetect.send(LogDetect.DataType.basicType, "apk下载链接: ",requestCode);
			quanxian();
		}
	}*/


	//设置两个按钮的灰色状态
    public void shezhiyanse(){

		guanzhu.setTextColor(mContext.getResources().getColor(R.color.vheise));
		tuijain.setTextColor(mContext.getResources().getColor(R.color.vheise));
		fujin.setTextColor(mContext.getResources().getColor(R.color.vheise));

    	//xrxian.setVisibility(View.GONE);
    	//hyxian.setVisibility(View.GONE);
    }
  //隐藏所有Fragment
    public void hideAllFragment(FragmentTransaction transaction){
        if(f1!=null){
            transaction.hide(f1);
        }
        if(f2!=null){
            transaction.hide(f2);
        }
		if(f3!=null){
			transaction.hide(f3);
		}
    }
	@Override
	public void onClick(View v) {
		 FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
	        hideAllFragment(transaction);
	        switch(v.getId()){

	        }
	}

	private void checkUpdate() {


		new Thread(new Runnable() {

			@SuppressLint("NewApi")
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String result = "";
				HttpClient httpclient = new DefaultHttpClient();
				String url = Util.url + "/uiface/ar?mode0=A-user-search&mode2=getlatestupdate";
				HttpPost httppost = new HttpPost(url);
				// 添加http头信息
				httppost.setHeader("accept", "application/json");
				httppost.setHeader("Content-Type", "application/json;charset=UTF-8");
				//httppost.setHeader("Cookie",sessionid);
				HttpResponse response;
				try {
					response = httpclient.execute(httppost);
					// 检验状态码，如果成功接收数据
					int code = response.getStatusLine().getStatusCode();
					LogDetect.send("httpclient: ", "code="+code+", url="+url);
					if (code == 200) {
						result = EntityUtils.toString(response.getEntity());// 返回json格式：
						JSONObject jsonObject;
						String apkname = "";
						String apkversion = "";
						String apksize = "";
						String apkurl = "";
						try {
							jsonObject = new JSONObject(result);
							apkversion = jsonObject.getString("apkversion");
							apkname = jsonObject.getString("apkname");
							apksize = jsonObject.getString("apksize");
							apkurl = jsonObject.getString("apkurl");
						} catch (JSONException e) {

							e.printStackTrace();
						}


						//　获取当前版本号
						PackageManager pm = mContext.getPackageManager();
						PackageInfo pi;
						try {
							pi = pm.getPackageInfo(mContext.getPackageName(), 0);
							String curVer = pi.versionName;

							Log.v("TT","curVer: "+curVer+", apkVer: "+apkversion);

							String[] apkArray = apkversion.split("\\.");
							if(apkArray.length < 3) {
								apkArray = Arrays.copyOfRange(apkArray, 0, 3);
								for(int i = 0; i < 3; i++) {
									if(apkArray[i] == null) {
										apkArray[i] = "0";
									}
								}
							}
							String[] curArray = curVer.split("\\.");
							if(curArray.length < 3) {
								curArray = Arrays.copyOfRange(apkArray, 0, 3);
								for(int i = 0; i < 3; i++) {
									//if(curArray[i] == null) {
									curArray[i] = "0";
									//}
								}
							}
							int flag_update=0;
							for(int i = 0; i < 3; i++) {
								//int compareResult = apkArray[i].compareTo(curArray[i]);
								int apknum = Integer.parseInt(apkArray[i]);
								int curnum = Integer.parseInt(curArray[i]);
								if(apknum > curnum) {
									flag_update=1;
									// 判断当前版本是否需要更新
									Message msg = requestHandler.obtainMessage();
									Bundle bundle = new Bundle();
									bundle.putString("apkname", apkname);
									bundle.putString("apkversion", apkversion);
									bundle.putString("apksize", apksize);
									bundle.putString("apkurl", apkurl);
									msg.setData(bundle);
									msg.what=28888;
									msg.sendToTarget();
									LogDetect.send(LogDetect.DataType.specialType, "初始化版本判断28888: ", "当前: "+curVer+", 服务器: "+apkversion);
									//handler.sendMessage(msg);
									break;
								} else if(apknum < curnum) {
									break;
								}
							}
							if(flag_update==0){
								Message msg = requestHandler.obtainMessage();
								msg.what=28889;
								requestHandler.sendMessage(msg);
								// 判断当前版本是否需要更新
//							if(apkversion.compareTo(curVer) > 0) {
//								Message msg = handler.obtainMessage();
//								Bundle bundle = new Bundle();
//								bundle.putString("apkname", apkname);
//								bundle.putString("apkversion", apkversion);
//								bundle.putString("apksize", apksize);
//								bundle.putString("apkurl", apkurl);
//								msg.setData(bundle);
//								msg.sendToTarget();
//							}
                             /* if(YhApplication.DEBUG_FLAG)Log.send(LogDetect.DataType.specialType, "初始化版本判断28889: ", "当前: "+curVer+", 服务器: "+apkversion);*/
							}

						} catch (PackageManager.NameNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				} catch (IOException e) {
					LogDetect.send(LogDetect.DataType.specialType,"",e);
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}).start();

	}

	String apkname="yuese";
	String apkurl;
	String update_what;
	PopupWindow mPopWindow;
	private Handler requestHandler =new Handler(){

		@Override
		public void dispatchMessage(Message msg) {
			super.dispatchMessage(msg);

			switch(msg.what){
				case 28888: {    // 打开书签
					Bundle data = msg.getData();
					apkname = data.getString("apkname");
					apkurl = data.getString("apkurl");
					String apkversion = data.getString("apkversion");
					String apksize = data.getString("apksize");
					PackageManager manager = mContext.getPackageManager();

					try {
						PackageInfo info = manager.getPackageInfo(mContext.getPackageName(), 0);
						String a = info.versionName;
						if (a.equals(apkversion)) {
							//Toast.makeText(mContext,"已是最新版本", Toast.LENGTH_LONG).show();
						} else {
							String mode = "check_update";
							String[] params = {""};
							UsersThread_01107A b = new UsersThread_01107A(mode, params, requestHandler);
							Thread thread = new Thread(b.runnable);
							thread.start();
						}
					} catch (PackageManager.NameNotFoundException e) {
						e.printStackTrace();
					}
					break;
				}
				case 28889:
//					Toast.makeText(mContext, "已经是最新版本了",
//					Toast.LENGTH_SHORT).show();
					break;
				case 52:
					ArrayList<Update> list3=(ArrayList<Update>)msg.obj;
					if (list3==null || list3.size()<1){
						return;
					}
					Util.yuming=list3.get(0).getYuming();
					update_what=list3.get(0).getUpdateWhat();
					Util.updatetest=update_what;
					if(list3.get(0).getIsUpdate().equals("1")){
						String apkversion=list3.get(0).getVsion();
						//　获取当前版本号
						PackageManager pm = mContext.getPackageManager();
						PackageInfo pi;
						try {
							pi = pm.getPackageInfo(mContext.getPackageName(), 0);
							String curVer = pi.versionName;

							Log.v("TT","curVer: "+curVer+", apkVer: "+apkversion);

							String[] apkArray = apkversion.split("\\.");
							if(apkArray.length < 3) {
								apkArray = Arrays.copyOfRange(apkArray, 0, 3);
								for(int i = 0; i < 3; i++) {
									if(apkArray[i] == null) {
										apkArray[i] = "0";
									}
								}
							}
							String[] curArray = curVer.split("\\.");
							if(curArray.length < 3) {
								curArray = Arrays.copyOfRange(apkArray, 0, 3);
								for(int i = 0; i < 3; i++) {
									//if(curArray[i] == null) {
									curArray[i] = "0";
									//}
								}
							}
							int flag_update=0;
							for(int i = 0; i < 3; i++) {
								//int compareResult = apkArray[i].compareTo(curArray[i]);
								int apknum = Integer.parseInt(apkArray[i]);
								int curnum = Integer.parseInt(curArray[i]);
								if(apknum > curnum) {
									apkurl=list3.get(0).getDownurl();
									apkname=apkname+apkversion;
									showPopupspWindow(search, apkname, apkurl);

									if (Build.VERSION.SDK_INT >=23) {
										for (String permission : permissions) {
											// 检查该权限是否已经获取
											int i1 = ContextCompat.checkSelfPermission(getActivity(), permission);
											// 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
											if (i1 != PackageManager.PERMISSION_GRANTED) {
												// 如果没有授予该权限，就去提示用户请求
												ActivityCompat.requestPermissions(getActivity(), permissions, 321);
												return;
											}
										}
									}

									break;
								} else if(apknum < curnum) {
									break;
								}
							}


						} catch (PackageManager.NameNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
					break;
			}
		}
	};
	private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE
	};
	public void showPopupspWindow(final View parent, final String apkname, final String apkurl) {
		// 加载布局
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.zp_banbengengxintanchukuang, null);

		//点击确定兑换
		TextView queding = (TextView)layout.findViewById(R.id.updateon);
		queding.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				quanxian();
				//mPopWindow.dismiss();
			}
		});
		TextView gengxinc = (TextView)layout.findViewById(R.id.gengxinc);
         /* gengxinc.setText(Html.fromHtml(""));*/
		gengxinc.setText(update_what);

		TextView cancleup = (TextView)layout.findViewById(R.id.cancleup);
		cancleup.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mPopWindow.dismiss();
			}
		});
		mPopWindow = new PopupWindow(layout, ActionBar.LayoutParams.MATCH_PARENT,
				ActionBar.LayoutParams.WRAP_CONTENT, true);
		// 控制键盘是否可以获得焦点
		mPopWindow.setFocusable(true);
		// 设置popupWindow弹出窗体的背景
		WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
		lp.alpha = 0.4f;
		getActivity().getWindow().setAttributes(lp);
		mPopWindow.setBackgroundDrawable(new BitmapDrawable(null, ""));
		mPopWindow.setOutsideTouchable(true);
		//设置监听
//		mPopWindow.setTouchInterceptor(new View.OnTouchListener() {
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//
//				return true;
//			}
//		});
		//WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		//@SuppressWarnings("deprecation")
		// 获取xoff
		// int xpos = manager.getDefaultDisplay().getWidth() / 2
		//		- mPopWindow.getWidth() / 2;
		// xoff,yoff基于anchor的左下角进行偏移。
		// popupWindow.showAsDropDown(parent, 0, 0);
		mPopWindow.showAtLocation(parent, Gravity.CENTER | Gravity.CENTER,252, 0);
		// 监听

		mPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
			// 在dismiss中恢复透明度
			public void onDismiss() {
				WindowManager.LayoutParams lp =  getActivity().getWindow().getAttributes();
				lp.alpha = 1f;
				getActivity().getWindow()
						.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
				getActivity().getWindow().setAttributes(lp);

				showPopupspWindow(parent,apkname,apkurl);
			}
		});

	}

    public  void quanxian(){
		if (Build.VERSION.SDK_INT >=23) {
			for (String permission : permissions) {
				// 检查该权限是否已经获取
				int i = ContextCompat.checkSelfPermission(getActivity(), permission);
				// 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
				if (i != PackageManager.PERMISSION_GRANTED) {
					// 如果没有授予该权限，就去提示用户请求
					ActivityCompat.requestPermissions(getActivity(), permissions, 321);
					return;
				}
			}
		}

		Intent intent = new Intent();
		intent.setClass(mContext, DownloadActivityApk.class);
		Bundle bundle = new Bundle();
		bundle.putString("url", apkurl);
		bundle.putString("name", apkname);
		intent.putExtras(bundle);
		startActivity(intent);
	}

}
