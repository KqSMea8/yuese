package com.net.yuesejiaoyou.redirect.ResolverA.uiface;

import java.util.ArrayList;


import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.app.Dialog;
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
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.classroot.interface4.openfire.uiface.ChatActivity_KF;
import com.net.yuesejiaoyou.redirect.ResolverA.getset.Member_01152;
import com.net.yuesejiaoyou.redirect.ResolverA.interface3.UsersThread_01152A;
import com.net.yuesejiaoyou.redirect.ResolverA.interface4.CleanCacheManager;
import com.net.yuesejiaoyou.redirect.ResolverA.interface4.RoundImageView;
import com.net.yuesejiaoyou.redirect.ResolverB.interface3.UsersThread_01158B;
import com.net.yuesejiaoyou.redirect.ResolverB.uiface.MeiyanSet_zhubo;
import com.net.yuesejiaoyou.redirect.ResolverC.uiface.ModifyInformation_01152;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.ShareHelp;
import com.net.yuesejiaoyou.redirect.ResolverD.uiface.Chongzhi_01178;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

///////////////////////A区调用C区的相关文件类引入
import com.net.yuesejiaoyou.redirect.ResolverC.uiface.Vliao_MyWallet_01168;
import com.net.yuesejiaoyou.redirect.ResolverC.uiface.edit_info_01150;
import com.net.yuesejiaoyou.redirect.ResolverC.uiface.Vliao_mytimeprice_01178;
import com.net.yuesejiaoyou.redirect.ResolverC.uiface.Vliao_wodeqinmibang_01178;
import com.net.yuesejiaoyou.redirect.ResolverC.uiface.Vliao_wodefensi_01168;
import com.net.yuesejiaoyou.redirect.ResolverC.uiface.Vliao_heimingdan_01168;
import com.net.yuesejiaoyou.redirect.ResolverC.uiface.vliao_tuiguang_01152;
import com.net.yuesejiaoyou.redirect.ResolverC.uiface.Vliao_agmentup_01066;
///////////////////////A区调用B区的相关文件类引入
import com.net.yuesejiaoyou.redirect.ResolverB.uiface.MyVideo_158;
import com.net.yuesejiaoyou.redirect.ResolverB.uiface.MassChat_01160;

import cn.jpush.android.api.JPushInterface;


/****************************************************************
 * 本类是查询个人中M主播的数据
 ****************************************************************/
public class Fragment_zhubozhongxin_1152 extends Fragment implements OnClickListener{

	private RelativeLayout bianji1;
	private RoundImageView touxiang;
	private ImageView wurao1,xingji;
	private LinearLayout qingchu,meiyan,qianbao,kefu,fankui,gengxin,tuichu,
	user_my_like_layout,chongzhi,fencheng,miyou,qunfa,invite,duanshipin,heimingdan,wurao,vmin;
	private TextView user_name,my_fans,dailishang,price,v_curreny,wurao_zhuangtai;
	private Context mContext;
	private View mBaseView;
	private Intent intent;
	private CleanCacheManager cleanCacheManager;
	private PopupWindow popupWindow;
	DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
			.cacheOnDisc(true).bitmapConfig(Bitmap.Config.RGB_565).build();

	private Dialog wrDialog;	// 勿扰弹窗
	private String ybprice;
	private String reward_percent;
	/****************************************************************
	 * 初始化方法
	 * @
	 *
	 ****************************************************************/
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		//////////////////////////////
		LogDetect.send(LogDetect.DataType.specialType, "Fragment_zhubozhongxin_1152:", "布局开始");
		//////////////////////////////
		mBaseView = inflater.inflate(R.layout.zhubozhongxin_1152, null);
		mContext = getActivity();
		bianji1 = (RelativeLayout)mBaseView.findViewById(R.id.bianji1);
		bianji1.setOnClickListener(this);
		meiyan = (LinearLayout)mBaseView.findViewById(R.id.meiyan);
		meiyan.setOnClickListener(this);
		qingchu = (LinearLayout)mBaseView.findViewById(R.id.qingchu);
		qingchu.setOnClickListener(this);
		vmin = (LinearLayout) mBaseView.findViewById(R.id.vmin);
		vmin.setOnClickListener(this);
		touxiang = (RoundImageView)mBaseView.findViewById(R.id.touxiang);
		touxiang.setOnClickListener(this);
		user_name = (TextView)mBaseView.findViewById(R.id.user_name);
		xingji = (ImageView)mBaseView.findViewById(R.id.xingji);

		dailishang = (TextView)mBaseView.findViewById(R.id.dailishang);
		dailishang.setOnClickListener(this);
		dailishang.setVisibility(View.GONE);

		my_fans = (TextView)mBaseView.findViewById(R.id.my_fans);
		price = (TextView)mBaseView.findViewById(R.id.price);
		v_curreny = (TextView)mBaseView.findViewById(R.id.v_currency);
		qianbao = (LinearLayout)mBaseView.findViewById(R.id.qianbao);
		qianbao.setOnClickListener(this);
		//money = (TextView)mBaseView.findViewById(R.id.money);
		wurao_zhuangtai = (TextView)mBaseView.findViewById(R.id.wurao_zhuangtai);
		fencheng = (LinearLayout)mBaseView.findViewById(R.id.fencheng);
		fencheng.setOnClickListener(this);
		miyou = (LinearLayout)mBaseView.findViewById(R.id.miyou);
		miyou.setOnClickListener(this);
		qunfa = (LinearLayout)mBaseView.findViewById(R.id.qunfa);
		qunfa.setOnClickListener(this);
//		invite = (LinearLayout)mBaseView.findViewById(R.id.invite);
//		invite.setOnClickListener(this);
		duanshipin = (LinearLayout)mBaseView.findViewById(R.id.duanshipin);
		duanshipin.setOnClickListener(this);
		heimingdan = (LinearLayout)mBaseView.findViewById(R.id.heimingdan);
		heimingdan.setOnClickListener(this);
		//wurao = (LinearLayout)mBaseView.findViewById(R.id.wurao);
		//wurao.setOnClickListener(this);
		kefu = (LinearLayout)mBaseView.findViewById(R.id.kefu);
		kefu.setOnClickListener(this);
		fankui = (LinearLayout)mBaseView.findViewById(R.id.fankui);
		fankui.setOnClickListener(this);
		//gengxin = (LinearLayout)mBaseView.findViewById(R.id.gengxin);
		//gengxin.setOnClickListener(this);
		tuichu = (LinearLayout)mBaseView.findViewById(R.id.tuichu);
		tuichu.setOnClickListener(this);
		//点击进去充值页面
		chongzhi = (LinearLayout)mBaseView.findViewById(R.id.chongzhi);
		chongzhi.setOnClickListener(this);
		//我的粉丝
		user_my_like_layout = (LinearLayout)mBaseView.findViewById(R.id.user_my_like_layout);
		user_my_like_layout.setOnClickListener(this);
        //代理商等级
		dailishang = (TextView)mBaseView.findViewById(R.id.dailishang);
		dailishang.setOnClickListener(this);

		wurao1 = (ImageView)mBaseView.findViewById(R.id.wurao1);
		wurao1.setOnClickListener(this);
		//////////////////////////////
		LogDetect.send(LogDetect.DataType.specialType, "Fragment_zhubozhongxin_1152:", "布局结束");
		//////////////////////////////
		String mode = "zhubozhongxin";
		String[] paramMap = {Util.userid};
		//////////////////////////////
		LogDetect.send(LogDetect.DataType.specialType, "Fragment_zhubozhongxin_1152:", "参数======");
		//////////////////////////////


		//UsersThread_01152A b = new UsersThread_01152A(mode, paramMap, handler);
		//Thread th= new Thread(b.runnable);
		//th.start();
		wdarao();
		return mBaseView;
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
		
		String mode = "zhubozhongxin";
		String[] paramMap = {Util.userid};
		//////////////////////////////
		LogDetect.send(LogDetect.DataType.specialType, "Fragment_zhubozhongxin_1152:", "参数======");
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
	private Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 100:
				ArrayList<Member_01152> list = (ArrayList<Member_01152>)msg.obj;
				 if(list==null || list.size()==0){
		    		  Toast.makeText(mContext, "网络出错", Toast.LENGTH_SHORT).show();
		    		  break;
		    	  }
				if(list.get(0).getPhoto().contains("http")){
					ImageLoader.getInstance().displayImage(list.get(0).getPhoto(),touxiang,options);
				}
				//////////////////////////////
				LogDetect.send(LogDetect.DataType.basicType,"152+++++++++头像",list.get(0).getPhoto());
				//////////////////////////////
				ImageLoader.getInstance().displayImage("http://116.62.220.67:8090/img/star"+list.get(0).getStar_ranking()+".png",xingji,options);
				//////////////////////////////
				if (list.get(0).getdailishang()!=0){
					dailishang.setVisibility(View.VISIBLE);
					dailishang.setText("DL."+list.get(0).getdailishang());
				}
				//////////////////////////////
				LogDetect.send(LogDetect.DataType.basicType,"152+++++++++",list.get(0).getdailishang());
				//////////////////////////////
				LogDetect.send(LogDetect.DataType.basicType,"152+++++++++xingji",list.get(0).getStar_ranking());
				LogDetect.send(LogDetect.DataType.basicType,"152+++++++++nicheng",list.get(0).getNickname());
				//////////////////////////////
				user_name.setText(list.get(0).getNickname()+"(ID:"+list.get(0).getId()+")");
				my_fans.setText(list.get(0).getFans_num()+"");
				//////////////////////////////
				LogDetect.send(LogDetect.DataType.basicType,"152+++++++++粉丝",list.get(0).getFans_num());
				//////////////////////////////
				ybprice=list.get(0).getPrice();
				reward_percent=list.get(0).getReward_percent();
				price.setText(list.get(0).getPrice()+"悦币/分钟");
				//////////////////////////////
				LogDetect.send(LogDetect.DataType.basicType,"152+++++++++价格",list.get(0).getPrice());
				//////////////////////////////
				v_curreny.setText(list.get(0).getMoney()+"");
				//////////////////////////////
				LogDetect.send(LogDetect.DataType.basicType,"152===========V币",list.get(0).getMoney());
				//////////////////////////////
				break;
				
			case 112:
				String a = (String) msg.obj;
				//////////////////////////////
				LogDetect.send(LogDetect.DataType.basicType,"112查看状态",a);
				//////////////////////////////
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
				//////////////////////////////
				if(c.equals("")){

				}else if(c.equals("勿扰")){//勿打扰
					wurao1.setBackgroundResource(R.drawable.wuraoyes);
					wurao_zhuangtai.setText(c);
//					if(wrDialog != null & wrDialog.isShowing()) {
//						wrDialog.dismiss();
//					}
					Toast.makeText(getActivity(), "已修改为勿扰", Toast.LENGTH_SHORT).show();
				}else if(c.equals("在线")){//在线
					wurao1.setBackgroundResource(R.drawable.wuraonoo);
					wurao_zhuangtai.setText(c);
//					if(wrDialog != null & wrDialog.isShowing()) {
//						wrDialog.dismiss();
//					}
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
			intent.setClass(mContext, edit_info_01150.class);//编辑资料
			startActivity(intent);
			break;
			////////////////////////////////
			case R.id.touxiang:
			intent = new Intent();
			intent.setClass(mContext, ModifyInformation_01152.class);//编辑资料
			startActivity(intent);
			break;
			////////////////////////////////
			case R.id.qianbao://我的钱包
	        intent = new Intent();
			intent.setClass(mContext, Vliao_MyWallet_01168.class);
			startActivity(intent);
			break;
			////////////////////////////////
			case R.id.user_my_like_layout:
		   intent = new Intent();
			intent.setClass(mContext, Vliao_wodefensi_01168.class);//”我的粉丝“
			startActivity(intent);
			break;
			////////////////////////////////
			case R.id.vmin:
		   intent = new Intent();
			intent.putExtra("ybprice",ybprice)	;
			intent.putExtra("reward_percent",reward_percent);
			intent.setClass(mContext, Vliao_mytimeprice_01178.class);//价值
			startActivity(intent);
		    break;
			////////////////////////////////
			case R.id.dailishang://跳转到代理商等级页面
				intent = new Intent();
				intent.setClass(mContext, Vliao_agmentup_01066.class);
				startActivity(intent);
				break;
			////////////////////////////////
			case R.id.chongzhi:
				if(Util.iszhubo.equals("0")){
					intent = new Intent();
					intent.setClass(mContext, Chongzhi_01178.class);//v币
					startActivity(intent);
				}else{
					Toast.makeText(getActivity(),"主播暂无此功能",Toast.LENGTH_SHORT).show();
				}

            break;
			////////////////////////////////
			case R.id.fencheng:
			intent = new Intent();
			intent.setClass(mContext, vliao_tuiguang_01152.class);//”分成计划“
			startActivity(intent);
			break;
			////////////////////////////////
			case R.id.miyou://与我亲密的
			intent = new Intent();
			intent.setClass(mContext, Vliao_wodeqinmibang_01178.class);
			startActivity(intent);
			break;
			////////////////////////////////
			case R.id.qunfa:
			intent = new Intent();
			intent.setClass(mContext, MassChat_01160.class);//群发私信
			startActivity(intent);
			break;
			////////////////////////////////
//			case R.id.invite://邀请粉丝
//			intent = new Intent();
//			intent.setClass(mContext, yaoqingfensi_01152.class);
//			startActivity(intent);
//             break;
			////////////////////////////////
			case R.id.duanshipin://小视频
			intent = new Intent();
			intent.setClass(mContext, MyVideo_158.class);//短视频页面
			startActivity(intent);
			break;
			////////////////////////////////
			case R.id.heimingdan://黑名单
			intent = new Intent();
			intent.setClass(mContext, Vliao_heimingdan_01168.class);
			startActivity(intent);
			break;
			////////////////////////////////
			case R.id.fankui://帮助中心
//			intent = new Intent();
//			intent.setClass(mContext, Vliao_yijianfankui_01168.class);
//			startActivity(intent);
			intent = new Intent();
			intent.setClass(mContext, help_center_01152.class);
			startActivity(intent);
			break;
			////////////////////////////////
			case R.id.kefu://在线客服
		   intent = new Intent();
			intent.setClass(mContext, ChatActivity_KF.class);//客服
			intent.putExtra("id", "40");
			intent.putExtra("name", "小客服");
			intent.putExtra("headpic", "http://116.62.220.67:8090/img/imgheadpic/launch_photo.png");
			startActivity(intent);
		    break;
			////////////////////////////////
		   /*case R.id.gengxin:
			if(popupWindow==null){
				checkUpdate();
			}else{
				popupWindow.isShowing();
			}
			break;*/
			case R.id.tuichu://退出登陆
				showPopupspWindow_tuichu(arg0);
			break;
            ////////////////////////////////
			case R.id.wurao1://修改是否视频
				//wuraoDialog(arg0);
				xiugai();
			break;
			////////////////////////////////
			case R.id.meiyan://美颜设置
//			intent = new Intent();
//			intent.setClass(mContext, Vliao_yijianfankui_01168.class);
//			startActivity(intent);
//			Toast.makeText(mContext,"该功能正在实现ing",Toast.LENGTH_SHORT).show();

			Intent intent = new Intent(getActivity(),MeiyanSet_zhubo.class);
			startActivity(intent);

			//jumpActivity(getActivity(),"test");
			break;
			////////////////////////////////
			case R.id.qingchu://清除缓存
				//showPopupspWindow_qingchu(arg0);
				clearCache();
				Toast.makeText(getActivity(), "清除成功", Toast.LENGTH_SHORT).show();
				//{
				//Page page = new Page();
				//page.getList().get(0);
				//}
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
			hunc =  cleanCacheManager.getTotalCacheSize(getActivity());
		} catch (Exception e) {
			e.printStackTrace();
		}
		//LogDetect.send(LogDetect.DataType.basicType,"01165_获得缓存",hunc);
		cleanCacheManager.clearAllCache(getActivity());
		try {
			hunc =  cleanCacheManager.getTotalCacheSize(getActivity());
		} catch (Exception e) {
			e.printStackTrace();
		}
		//LogDetect.send(LogDetect.DataType.basicType,"01165_清除之后的缓存",hunc);
		//Toast.makeText(getActivity(), "清除成功", Toast.LENGTH_SHORT).show();
	}

	/*private void jumpActivity(Context context, String msg) {
		NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
		Intent intent = new Intent(context, Main.class);//将要跳转的界面
		//Intent intent = new Intent();//只显示通知，无页面跳转
		builder.setAutoCancel(true);//点击后消失
		builder.setSmallIcon(R.drawable.ic_launcher);//设置通知栏消息标题的头像
		builder.setDefaults(NotificationCompat.DEFAULT_SOUND);//设置通知铃声
		builder.setTicker("悦色交友");
		builder.setContentTitle("悦色交友");
		builder.setContentText(msg);
		//利用PendingIntent来包装我们的intent对象,使其延迟跳转
		PendingIntent intentPend = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
		builder.setContentIntent(intentPend);
		NotificationManager manager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
		manager.notify(0, builder.build());
	}*/

	/*****************************************************
	 * 清除缓存（目前没用到）
	 *
	 *****************************************************/
	private void showPopupspWindow_qingchu(View arg0) {
		//////////////////////////////
		LogDetect.send(LogDetect.DataType.specialType, "vliao_Gerenzhongxin_1152:", "弹出框======");
		//////////////////////////////
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		//////////////////////////////
		//View layout = inflater.inflate(R.layout.tuichudenglu_1152, null);
		View  convertView = LayoutInflater.from(mContext).inflate(R.layout.qingchuhuancun_1152, null);

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

				cleanCacheManager.clearAllCache(getActivity());;
				Toast.makeText(getActivity(), "清除成功", Toast.LENGTH_SHORT).show();
				
				popupWindow.dismiss();
			}
		});


		popupWindow = new PopupWindow(convertView, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT, true);
		popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
		popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);

		//是否响应touch事件
		//mPopWindow.setTouchable(false);
		//是否具有获取焦点的能力
		popupWindow.setFocusable(true);
		popupWindow.showAtLocation(arg0, Gravity.BOTTOM  | Gravity.CENTER,252, 0);
		//外部是否可以点击
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
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
		/////////////////////////////////
	}



	/*****************************************************
	 * 退出登录
	 *
	 *****************************************************/
	private void showPopupspWindow_tuichu(View arg0) {
		//////////////////////////////
		LogDetect.send(LogDetect.DataType.specialType, "Fragment_zhubozhongxin_1152:", "弹出框======");
		//////////////////////////////
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

				Toast.makeText(getActivity(), "退出成功", Toast.LENGTH_SHORT).show();
				Intent intent1=new Intent();
				intent1.setClass(getActivity(),LoginActivity.class );
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



		/////////////////////////////////
	}

	//------------------------------
	// 勿扰弹窗
	private void wuraoDialog(View view) {

		final Dialog dialog = new Dialog(getActivity());
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		wrDialog = dialog;
		View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.lay_wuraotip, null);

//		TextView jubao = (TextView)inflate.findViewById(R.id.txt_jb);
//		jubao.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				dialog.dismiss();
//
//			}
//		});

		//将布局设置给Dialog
		dialog.setContentView(inflate);

		//获取当前Activity所在的窗体
		Window dialogWindow = dialog.getWindow();
		//设置Dialog从窗体底部弹出
		dialogWindow.setGravity( Gravity.CENTER);
		//获得窗体的属性
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();//显示对话框

		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				if(dialog.isShowing()) {
					dialog.dismiss();
					Toast.makeText(getActivity(), "Net connect Exception", Toast.LENGTH_LONG).show();
				}
			}
		}, 6000);
	}

}
