package com.net.yuesejiaoyou.redirect.ResolverD.interface4.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.net.yuesejiaoyou.classroot.interface4.openfire.uiface.CoustomerActivity;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.URL;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.activity.AnchorGuideActivity;
import com.net.yuesejiaoyou.redirect.ResolverB.interface3.UsersThread_01158B;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.ShareHelp;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.activity.HelpActivity;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.activity.LoginActivity;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.activity.RechargeActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;


import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.redirect.ResolverA.getset.Member_01152;
import com.net.yuesejiaoyou.redirect.ResolverA.interface3.UsersThread_01152A;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.utils.CleanCacheManager;
import com.net.yuesejiaoyou.redirect.ResolverA.interface4.RoundImageView;

///////////////////////A区调用C区的相关文件类引入
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.activity.ModifyAvaterActivity;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.activity.FocusActivity;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.activity.CollectActivity;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.activity.WalletActivity;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.activity.ShareActivity;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.activity.DailiActivity;
///////////////////////A区调用B区的相关文件类引入
import com.net.yuesejiaoyou.redirect.ResolverB.uiface.MeiyanSet;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.activity.LevelActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import cn.jpush.android.api.JPushInterface;
import okhttp3.Call;


/****************************************************************
 * 本类是查询个人中M普通用户的数据
 ****************************************************************/
public class MineFragment extends Fragment implements OnClickListener{
	private RelativeLayout bianji1;
	private RoundImageView touxiang;
	private ImageView wurao1;
	private LinearLayout user_my_like_layout,chongzhi,qianbao,fencheng,renzheng,meiyan,kefu,sicang,fankui,gengxin,tuichu,qingchu;
	private TextView user_id,dengji,dailishang,my_guanzhu,money,wurao_zhuangtai;
	private View mBaseView;
	private Intent intent;
	//弹出框
	private PopupWindow popupWindow;
	private Context mContext;
	DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
			.cacheOnDisc(true).bitmapConfig(Bitmap.Config.RGB_565).build();

	/****************************************************************
	 * 初始化方法
	 * @
	 *
	 ****************************************************************/
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mBaseView = inflater.inflate(R.layout.gerenzhongxin_1152, null);

		mContext = getActivity();

		user_my_like_layout = (LinearLayout)mBaseView.findViewById(R.id.user_my_like_layout);
		user_my_like_layout.setOnClickListener(this);
		
		chongzhi = (LinearLayout)mBaseView.findViewById(R.id.chongzhi);
		chongzhi.setOnClickListener(this);
		
		kefu = (LinearLayout)mBaseView.findViewById(R.id.kefu);
		kefu.setOnClickListener(this);
		
		wurao1 = (ImageView)mBaseView.findViewById(R.id.wurao1);
		wurao1.setOnClickListener(this);
		wurao_zhuangtai = (TextView)mBaseView.findViewById(R.id.wurao_zhuangtai);
		
		bianji1 = (RelativeLayout)mBaseView.findViewById(R.id.bianji1);
		bianji1.setOnClickListener(this);
		touxiang = (RoundImageView)mBaseView.findViewById(R.id.touxiang);
		touxiang.setOnClickListener(this);
		qianbao = (LinearLayout)mBaseView.findViewById(R.id.qianbao);
		qianbao.setOnClickListener(this);
		fencheng = (LinearLayout)mBaseView.findViewById(R.id.fencheng);
		fencheng.setOnClickListener(this);
		renzheng = (LinearLayout)mBaseView.findViewById(R.id.renzheng);
		renzheng.setOnClickListener(this);
		kefu = (LinearLayout)mBaseView.findViewById(R.id.kefu);
		kefu.setOnClickListener(this);
		fankui = (LinearLayout)mBaseView.findViewById(R.id.fankui);
		fankui.setOnClickListener(this);
		//gengxin = (LinearLayout)mBaseView.findViewById(R.id.gengxin);
		//gengxin.setOnClickListener(this);
		tuichu = (LinearLayout)mBaseView.findViewById(R.id.tuichu);
		tuichu.setOnClickListener(this);
		sicang= (LinearLayout) mBaseView.findViewById(R.id.sicang);
		sicang.setOnClickListener(this);
		user_id = (TextView)mBaseView.findViewById(R.id.user_id);
		money = (TextView)mBaseView.findViewById(R.id.money);
		
		my_guanzhu = (TextView)mBaseView.findViewById(R.id.my_guanzhu);
		
		dengji = (TextView)mBaseView.findViewById(R.id.dengji);
		dengji.setOnClickListener(this);

		dailishang = (TextView)mBaseView.findViewById(R.id.dailishang);
		dailishang.setOnClickListener(this);
		dailishang.setVisibility(View.GONE);

		chongzhi = (LinearLayout)mBaseView.findViewById(R.id.chongzhi);
		chongzhi.setOnClickListener(this);
		
		qingchu = (LinearLayout)mBaseView.findViewById(R.id.qingchu);
		qingchu.setOnClickListener(this);

		meiyan = (LinearLayout)mBaseView.findViewById(R.id.meiyan);
		meiyan.setOnClickListener(this);
		//////////////////////////////
		LogDetect.send(LogDetect.DataType.specialType, "MineFragment:", "布局结束");
		//////////////////////////////
		//发请求

		getData();
		wdarao();
		return mBaseView;
	}


	public void getData(){
//		String mode = "gerenzhongxin";
//		String[] paramMap = {Util.userid};
//		//////////////////////////////
//		//////////////////////////////
//		UsersThread_01152A b = new UsersThread_01152A(mode, paramMap, handler);
//		Thread th= new Thread(b.runnable);
//		th.start();

		OkHttpUtils.post(this)
				.url(URL.URL_USERDETAILE)
				.addParams("param1", Util.userid)
				.build()
				.execute(new StringCallback() {
					@Override
					public void onError(Call call, Exception e, int id) {

					}

					@Override
					public void onResponse(String response, int id) {
						List<Member_01152> list = JSON.parseArray(response, Member_01152.class);
						if(list==null || list.size()==0){
							Toast.makeText(mContext, "网络出错", Toast.LENGTH_SHORT).show();
							return;
						}
						if(list.get(0).getPhoto().contains("http")){
							ImageLoader.getInstance().displayImage(list.get(0).getPhoto(),touxiang,options);
						}else{
							ImageLoader.getInstance().displayImage("http://120.27.98.128:9118/img/imgheadpic/"+list.get(0).getPhoto(),touxiang,options);
						}
						user_id.setText(list.get(0).getNickname()+"(ID:"+list.get(0).getId()+")");
						money.setText(list.get(0).getMoney()+"");
						dengji.setText("LV."+list.get(0).getDengji());
						if (list.get(0).getdailishang()!=0){
							dailishang.setVisibility(View.VISIBLE);
							dailishang.setText("DL."+list.get(0).getdailishang());
						}
						my_guanzhu.setText(list.get(0).getSum()+"");
						myrenzheng=list.get(0).getMyrenzheng();
					}
				});
	}
	/****************************************************************
	 * 对页面进行重新刷新
	 * @
	 *
	 ****************************************************************/
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//发请求
		String mode = "gerenzhongxin";
		String[] paramMap = {Util.userid};
		//////////////////////////////
		LogDetect.send(LogDetect.DataType.specialType, "MineFragment:", "参数=======");
		//////////////////////////////
		UsersThread_01152A b = new UsersThread_01152A(mode, paramMap, handler);
		Thread th= new Thread(b.runnable);
		th.start();
	}
	/****************************************************************
	 * 切换到勿扰模式
	 * @
	 *
	 ****************************************************************/
	private void wdarao(){
		String mode = "getwudarao";
		String[] paramMap={Util.userid};
		UsersThread_01152A b = new UsersThread_01152A(mode,paramMap,handler);
		Thread thread = new Thread(b.runnable);
		thread.start();
	}
	/****************************************************************
	 * 修改用户在线状态
	 * @
	 *
	 ****************************************************************/
	private void xiugai(){
		String mode = "xiugai";
		String[] paramMap={Util.userid};
		UsersThread_01152A b = new UsersThread_01152A(mode,paramMap,handler);
		Thread thread = new Thread(b.runnable);
		thread.start();
	}

	/****************************************************************
	 * 对返回的数据进行解析
	 * @
	 *
	 ****************************************************************/
	private int myrenzheng=0;
	private Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 200:
				ArrayList<Member_01152> list = (ArrayList<Member_01152>)msg.obj;
				if(list==null || list.size()==0){
		    		  Toast.makeText(mContext, "网络出错", Toast.LENGTH_SHORT).show();
		    		  break;
		    	  }
				if(list.get(0).getPhoto().contains("http")){
					ImageLoader.getInstance().displayImage(list.get(0).getPhoto(),touxiang,options);
				}else{
				ImageLoader.getInstance().displayImage("http://120.27.98.128:9118/img/imgheadpic/"+list.get(0).getPhoto(),touxiang,options);
				}
				user_id.setText(list.get(0).getNickname()+"(ID:"+list.get(0).getId()+")");
				money.setText(list.get(0).getMoney()+"");
				dengji.setText("LV."+list.get(0).getDengji());
				if (list.get(0).getdailishang()!=0){
					dailishang.setVisibility(View.VISIBLE);
					dailishang.setText("DL."+list.get(0).getdailishang());
				}
				my_guanzhu.setText(list.get(0).getSum()+"");
				myrenzheng=list.get(0).getMyrenzheng();
				break;
				
			case 112:
				String a = (String) msg.obj;
				//////////////////////////////
				LogDetect.send(LogDetect.DataType.basicType,"112查看状态",a);
				if(a.equals("")){

				}else if(a.equals("勿扰")){//勿打扰
					wurao1.setBackgroundResource(R.drawable.wuraoyes);
					wurao_zhuangtai.setText(a);
				}else if(a.equals("在线")){//在线
					wurao1.setBackgroundResource(R.drawable.wuraonoo);
					wurao_zhuangtai.setText(a);
				}
				break;
				
			case 113:
				String c = (String)msg.obj;
				//////////////////////////////
				LogDetect.send(LogDetect.DataType.basicType,"113查看状态",c);
				if(c.equals("")){
				}else if(c.equals("勿扰")){//勿打扰
					wurao1.setBackgroundResource(R.drawable.wuraoyes);
					wurao_zhuangtai.setText(c);
					Toast.makeText(getActivity(), "已修改为勿扰", Toast.LENGTH_SHORT).show();
				}else if(c.equals("在线")){//在线
					wurao1.setBackgroundResource(R.drawable.wuraonoo);
					wurao_zhuangtai.setText(c);
					Toast.makeText(getActivity(), "已修改为在线", Toast.LENGTH_SHORT).show();
				}
				break;

			default:
				break;
			}
		}
	};

	/*****************************************************
	 * 单击事件
	 * @param arg0
	 *****************************************************/
	@Override
	public void onClick(View arg0) {
		int id = arg0.getId();
		switch (id) {
			////////////////////////////////
			case R.id.bianji1:
				intent = new Intent();
				intent.setClass(mContext, ModifyAvaterActivity.class);//编辑资料
				startActivity(intent);
				break;
			////////////////////////////////
			case R.id.touxiang:
				intent = new Intent();
				intent.setClass(mContext, ModifyAvaterActivity.class);//修改头像
				startActivity(intent);
				break;
			////////////////////////////////
			case R.id.dengji://跳转到等级页面
				intent = new Intent();
				intent.setClass(mContext, LevelActivity.class);
				startActivity(intent);
				break;
			////////////////////////////////
			////////////////////////////////
			case R.id.dailishang://跳转到代理商等级页面
				intent = new Intent();
				intent.setClass(mContext, DailiActivity.class);
				startActivity(intent);
				break;
			////////////////////////////////
			case R.id.user_my_like_layout://跳转关注页面
				intent = new Intent();
				intent.setClass(mContext, FocusActivity.class);
				startActivity(intent);//
				break;
			////////////////////////////////
			case R.id.chongzhi://充值页面
				intent = new Intent();
				intent.setClass(mContext, RechargeActivity.class);
				startActivity(intent);//
				break;
			////////////////////////////////
			case R.id.qianbao:
				intent = new Intent();
				intent.setClass(mContext, WalletActivity.class);
				startActivity(intent);//“我的钱包”
				break;
			////////////////////////////////
			case R.id.fencheng:
				intent = new Intent();
				intent.setClass(mContext, ShareActivity.class);//”分成计划“
				startActivity(intent);
				break;
			////////////////////////////////
			case R.id.renzheng:
				if (myrenzheng>0){
					Toast.makeText(mContext,"您已经提交资料，请等待管理员审核！",Toast.LENGTH_LONG).show();
				}else{
					intent = new Intent();
					intent.setClass(mContext, AnchorGuideActivity.class);//“成为主播”
					startActivity(intent);
				}
				break;
			////////////////////////////////
			case R.id.meiyan://美颜设置
				Intent intent = new Intent(getActivity(),MeiyanSet.class);
				startActivity(intent);
				break;
			////////////////////////////////
			case R.id.sicang://我的私藏
		    	intent = new Intent();
				intent.setClass(mContext, CollectActivity.class);
				startActivity(intent);
				break;
			////////////////////////////////
			case R.id.kefu://在线客服
				intent = new Intent();
				intent.setClass(mContext, CoustomerActivity.class);//客服
				intent.putExtra("id", "40");
				intent.putExtra("name", "小客服");
				intent.putExtra("headpic", "http://116.62.220.67:8090/img/imgheadpic/launch_photo.png");
				startActivity(intent);
				break;
			////////////////////////////////
			case R.id.wurao1://修改是否视频
				xiugai();
				break;
			////////////////////////////////
			case R.id.tuichu://退出登陆
				showPopupspWindow_tuichu(arg0);
				break;
			////////////////////////////////
			case R.id.fankui://帮助中心
				intent = new Intent();
				intent.setClass(mContext, HelpActivity.class);
				startActivity(intent);
				break;
			////////////////////////////////
			case R.id.qingchu://清除缓存
				//cleanCacheManager.clearAllCache(getActivity());
				//Toast.makeText(getActivity(), "清除成功", Toast.LENGTH_SHORT).show();
				clearCache();
				Toast.makeText(getActivity(), "清除成功", Toast.LENGTH_SHORT).show();
				break;
		}	
	}

	/*****************************************************
	 * 清除缓存
	 *
	 *****************************************************/
	private void clearCache() {
		String hunc="";
		try {
			hunc =  CleanCacheManager.getTotalCacheSize(getActivity());
		} catch (Exception e) {
			e.printStackTrace();
		}
		//LogDetect.send(LogDetect.DataType.basicType,"01165_获得缓存",hunc);
		CleanCacheManager.clearAllCache(getActivity());
		try {
			hunc =  CleanCacheManager.getTotalCacheSize(getActivity());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private void showPopupspWindow_tuichu(View arg0) {
		//////////////////////////////
		LogDetect.send(LogDetect.DataType.specialType, "MineFragment:", "弹出框======");
		//////////////////////////////
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		//View layout = inflater.inflate(R.layout.tuichudenglu_1152, null);
		View  convertView = LayoutInflater.from(mContext).inflate(R.layout.tuichudenglu_1152, null);

		/////////////////////////////////
		/**
		 * 监听事件
		 */
		TextView cancel = (TextView)convertView.findViewById(R.id.cancel);
		cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				popupWindow.dismiss();
				
			}
		});

		/////////////////////////////////
		/**
		 * 监听事件
		 */
		TextView confirm = (TextView) convertView.findViewById(R.id.confirm);//获取小窗口上的TextView，以便显示现在操作的功能。
		confirm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				
				SharedPreferences share =getActivity().getSharedPreferences("Acitivity",
						Activity.MODE_PRIVATE);
				share.edit().putString("logintype", "").commit();
				share.edit().putString("openid", "").commit();
				share.edit().putString("username", "").commit();
				share.edit().putString("password", "").commit();
//				share.edit().putString("userid", "0").commit();
//				share.edit().putString("nickname", "0").commit();
//				share.edit().putString("headpic", "0").commit();
//				share.edit().putString("sex", "0").commit();
//				share.edit().putString("zhubo_bk", "0").commit();
				//share.edit().putBoolean("FIRST", true).commit();
				// 状态改成离线
				String mode2 = "statuschange";
				String[] paramsMap2 = {Util.userid,"0"};
				UsersThread_01158B a2 = new UsersThread_01158B(mode2,paramsMap2,handler);
				Thread c2 = new Thread(a2.runnable);
				c2.start();

				JPushInterface.setAlias(mContext.getApplicationContext(), 1, "0");	// 退出登录后，撤销极光别名，就收不到一对一视频推送了
				ShareHelp share1 = new ShareHelp();
				share1.wx_delete();
				LogDetect.send(LogDetect.DataType.specialType, "share: ", share.getString("userid", ""));
				Util.userid="0";

				Util.imManager.xmppDisconnect();

				Toast.makeText(getActivity(), "退出成功", Toast.LENGTH_SHORT).show();//Exit logon
				Intent intent1=new Intent();
				intent1.setClass(getActivity(),LoginActivity.class);
				startActivity(intent1);
				getActivity().finish();
			}
		});


		popupWindow = new PopupWindow(convertView, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT, true);
		popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
		popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
		WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
		lp.alpha = 0.5f;
		getActivity().getWindow().setAttributes(lp);
		//是否响应touch事件
		//mPopWindow.setTouchable(false);
		//是否具有获取焦点的能力
		popupWindow.setFocusable(true);
		popupWindow.showAtLocation(arg0, Gravity.BOTTOM  | Gravity.CENTER,252, 0);
		//外部是否可以点击
		popupWindow.setBackgroundDrawable(new BitmapDrawable(null,""));
		popupWindow.setOutsideTouchable(true);
		//mPopWindow.showAsDropDown(tj2);

		/////////////////////////////////
		convertView.setOnTouchListener(new View.OnTouchListener()// 需要设置，点击之后取消popupview，即使点击外面，也可以捕获事件
		{
			@Override
			public boolean onTouch(View v, android.view.MotionEvent event) {
				// TODO Auto-generated method stub
				if (popupWindow.isShowing())
				{
					popupWindow.dismiss();
				}
				return false;
			}
		});

		popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
			@Override
			public void onDismiss() {
				WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
				lp.alpha = 1f;
				getActivity().getWindow().setAttributes(lp);
			}
		});

	}


}

