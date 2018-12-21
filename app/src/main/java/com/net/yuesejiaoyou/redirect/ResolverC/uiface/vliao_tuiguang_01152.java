package com.net.yuesejiaoyou.redirect.ResolverC.uiface;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.redirect.ResolverA.uiface.Gonglue_01162;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.Member_01152;
import com.net.yuesejiaoyou.redirect.ResolverC.interface3.UsersThread_01152;
import com.net.yuesejiaoyou.redirect.ResolverC.interface4.RoundImageView;
import com.net.yuesejiaoyou.redirect.ResolverC.interface4.tuiguang_Adapter_01152;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.ShareHelp;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

//import com.example.aiba.interface4.Aiba_wodejiazuAdapter_01152;


public class vliao_tuiguang_01152 extends Activity implements OnClickListener {
	//自定义ID
	
	private PopupWindow mPopWindow;
	private NumberPicker numberpicker;
	private TextView zhanghu,xingming,shouji,num,money,daili;
	private Button tuiguang,gonglue;
	private RoundImageView touxiang;
	private Intent intent;
	private ImageView back;
	private List<Member_01152> articles;
    private ListView listview;
    private tuiguang_Adapter_01152 adapter;
    private LinearLayout quyu1,quyu2;
    private TextView biaoti;

	private ArrayList<Member_01152> list;
	DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
			.cacheOnDisc(true).bitmapConfig(Bitmap.Config.RGB_565).build();
	@Override
	/**
	 * 对页面进行初始化
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LogDetect.send(LogDetect.DataType.specialType, "vliao_tuiguang_01152:", "布局开始");
		setContentView(R.layout.activity_tuiguang_01152);
		LogDetect.send(LogDetect.DataType.specialType, "vliao_tuiguang_01152:", "开始=====");
		back = (ImageView) findViewById(R.id.back);
		back.setOnClickListener(this);
		
		tuiguang = (Button) findViewById(R.id.tuiguang);//去推广
		tuiguang.setOnClickListener(this);
		gonglue = (Button) findViewById(R.id.gonglue);//去攻略
		gonglue.setOnClickListener(this);


		touxiang = (RoundImageView) findViewById(R.id.touxiang);//头像
		zhanghu = (TextView) findViewById(R.id.zhanghu);//提现账户
		xingming = (TextView) findViewById(R.id.xingming);//提现姓名
		shouji = (TextView) findViewById(R.id.shouji);//手机号
		num = (TextView) findViewById(R.id.num);//累计邀请
		//num.setOnClickListener(this);
		money = (TextView) findViewById(R.id.money);//累计奖励
		//money.setOnClickListener(this);
		
		//biaoti
		biaoti = (TextView) findViewById(R.id.biaoti);//累计奖励
		
		daili = (TextView) findViewById(R.id.daili);//累计奖励
		daili.setOnClickListener(this);
		
		quyu1 = (LinearLayout) findViewById(R.id.quyu1);//去推广
		quyu1.setOnClickListener(this);
		
		quyu2 = (LinearLayout) findViewById(R.id.quyu2);//去推广
		quyu2.setOnClickListener(this);
		
        //listview = (ListView) findViewById(R.id.listview);
		LogDetect.send(LogDetect.DataType.specialType, "vliao_tuiguang_01152:", "结束=====");
		//直接开线程，不需要请求事件
		String mode = "tuiguang";
		String[] paramsMap = {Util.userid};
		LogDetect.send(LogDetect.DataType.specialType, "vliao_tuiguang_01152:", "参数=====");
		UsersThread_01152 b = new UsersThread_01152(mode,paramsMap,requestHandler);
		Thread thread = new Thread(b.runnable);
		thread.start();
		
//		String mode1 = "xiaofei";
//		String[] paramsMap1 = {Util.userid};
//		LogDetect.send(LogDetect.DataType.specialType, "vliao_tuiguang_01152:", "参数结束=====");
//		UsersThread_01152 b1 = new UsersThread_01152(mode1,paramsMap1,requestHandler);
//		Thread thread1 = new Thread(b1.runnable);
//		thread1.start();

		// 判断是否是代理
		if("1".equals(Util.is_agent)) {
			daili.setVisibility(View.GONE);
			biaoti.setText("分成计划(代理)");
		}
	}
	
//	//返回页面后重新刷新一次
//	@Override
//	public void onResume() {
//		
//		super.onResume();
//		
//		String mode = "tuiguang";
//		String[] paramsMap = {Util.userid};
//		LogDetect.send(LogDetect.DataType.specialType, "meijian_tuiguang_01152:", "参数=====");
//		UsersThread_01152 b = new UsersThread_01152(mode,paramsMap,requestHandler);
//		Thread thread = new Thread(b.runnable);
//		thread.start();
//		
//	}

	//获得onclick点击事件
	@Override
	public void onClick(View v) {
		
		int id = v.getId();
		switch (id) {
		case R.id.back: //返回页面
			this.finish();
			break;
		case R.id.daili: //申请代理
			intent=new Intent();
			intent.setClass(vliao_tuiguang_01152.this, Vliao_agmentup_01066.class);
			startActivity(intent);
			break;
		case R.id.tuiguang: //点击提取现金
//			intent=new Intent();
//			intent.setClass(vliao_tuiguang_01152.this, Wwjhuodongxiangqing_1152.class);
//			startActivity(intent);

			ShareHelp shareHelp1=new ShareHelp();
			shareHelp1.showShare(vliao_tuiguang_01152.this,Util.invite_num);
		  break;

			case R.id.gonglue:
			Intent intent1=new Intent();
				intent1.putExtra("cash_onefee",list.get(0).getCash_onefee());
				intent1.putExtra("cash_twofee",list.get(0).getCash_twofee());
				intent1.putExtra("dvcash_onefee",list.get(0).getDvcash_onefee());
				intent1.putExtra("dvcash_twofee",list.get(0).getDvcash_twofee());
			intent1.setClass(vliao_tuiguang_01152.this,Gonglue_01162.class);
			startActivity(intent1);
				break;
        case R.id.quyu1: //点击提取现金
            intent=new Intent();
            intent.setClass(vliao_tuiguang_01152.this, tuiguangrenshu_01152.class);
            startActivity(intent);
//			Toast.makeText(vliao_tuiguang_01152.this,"该功能正在实现ing",Toast.LENGTH_SHORT).show();
            break;
        case R.id.quyu2: //点击提取现金
            intent=new Intent();
            intent.setClass(vliao_tuiguang_01152.this, Vliao_tjrwithdraw_01168.class);
            startActivity(intent);
//			Toast.makeText(vliao_tuiguang_01152.this,"该功能正在实现ing",Toast.LENGTH_SHORT).show();
            break;
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		String mode = "tuiguang";
		String[] paramsMap = {Util.userid};
		LogDetect.send(LogDetect.DataType.specialType, "vliao_tuiguang_01152:", "参数=====");
		UsersThread_01152 b = new UsersThread_01152(mode,paramsMap,requestHandler);
		Thread thread = new Thread(b.runnable);
		thread.start();
	}

    /**
     * 从APP服务端获取到的json字符串，用list集合去接收，把获取到的信息嵌入到界面元素
     */
	private Handler requestHandler = new Handler() {
		  @Override
		  public void handleMessage(Message msg) {
		    switch (msg.what) {
		      case 0:
		        
		    	  break;
		        
		      case 200:
		    		LogDetect.send(LogDetect.DataType.specialType, "vliao_tuiguang_01152:", "集合======");
		    	    list=(ArrayList<Member_01152>) msg.obj;
		    	    
			    	  if(list==null || list.size()==0){
			    		  Toast.makeText(vliao_tuiguang_01152.this, "集合为空", Toast.LENGTH_SHORT).show();
			    		  break;
			    	  }

			    	  if(list.get(0).getPhoto().contains("http")){
						ImageLoader.getInstance().displayImage(list.get(0).getPhoto(),touxiang,options);
						}else{
						ImageLoader.getInstance().displayImage(Util.url+"/img/imgheadpic/"+list.get(0).getPhoto(),touxiang,options);
						}
			    	  
			    	zhanghu.setText(list.get(0).getTixian_account());
			    	xingming.setText(list.get(0).getAccount_name());
			    	shouji.setText(list.get(0).getPhonenum());
		    	    num.setText(list.get(0).getXiaji_num()+"人");
		    	    LogDetect.send(LogDetect.DataType.specialType, "vliao_tuiguang_01152:", list.get(0).getXiaji_num());
                    money.setText(list.get(0).getAbleinvite_price()+"元");

				  TextView gongluetext = (TextView) findViewById(R.id.gongluetext);
				  gongluetext.setText(Html.fromHtml("1.所有用户都可以参与分成计划；<br><br>2.通过自己分享的专属推广链接下载注册的用户，才算“我”推荐的用户；<br><br>3.推荐的用户充值即可获得充值金额"+list.get(0).getCash_onefee()+"%的分成奖励；<br><br>4.推荐的用户提现可以获得提现金额"+list.get(0).getDvcash_onefee()+"%的分成奖励；<br><br>5.活动的最终解释权归平台所有。"));


                    break;
		      case 210:
		    	  LogDetect.send(LogDetect.DataType.specialType, "vliao_tuiguang_01152:", "适配器集合======");
					ArrayList<Member_01152> list1=(ArrayList<Member_01152>) msg.obj;

					if(list1==null){
						  Toast.makeText(vliao_tuiguang_01152.this, "集合为空", Toast.LENGTH_SHORT).show();
						  break;
					}
					LogDetect.send(LogDetect.DataType.specialType, "vliao_tuiguang_01152:", list1);
					adapter = new tuiguang_Adapter_01152(vliao_tuiguang_01152.this, listview, list1);
					listview.setAdapter(adapter);
					LogDetect.send(LogDetect.DataType.specialType, "vliao_tuiguang_01152:", list1);
					break;
					
		    }
		  }
	};
}
