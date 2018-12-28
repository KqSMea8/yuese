package com.net.yuesejiaoyou.redirect.ResolverD.interface4.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/*
import com.net.yuesejiaoyou.R;
import com.example.vliao.getset.Vliao1_01168;
import com.example.vliao.interface3.UsersThread_01168;
import com.example.vliao.util.Format_01162;
import com.example.vliao.util.Util;
*/

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.Vliao1_01168;
import com.net.yuesejiaoyou.redirect.ResolverC.interface3.UsersThread_01168C;
import com.net.yuesejiaoyou.redirect.ResolverC.interface4.Format_01162;
import com.net.yuesejiaoyou.redirect.ResolverC.uiface.Vliao_shourumingxi_01168;
import com.net.yuesejiaoyou.redirect.ResolverC.uiface.Vliao_shourumingxizhubo_01168;
import com.net.yuesejiaoyou.redirect.ResolverC.uiface.Vliao_tixianmingxi_01168;
import com.net.yuesejiaoyou.redirect.ResolverC.uiface.Vliao_tixianmingxizhubo_01168;
import com.net.yuesejiaoyou.redirect.ResolverC.uiface.Vliao_zhichumingxi_01168;
import com.net.yuesejiaoyou.redirect.ResolverC.uiface.Vliao_zhichumingxizhubo_01168;
import com.net.yuesejiaoyou.redirect.ResolverC.uiface.withdraw_01156;
import com.net.yuesejiaoyou.redirect.ResolverD.uiface.Chongzhi_01178;

import java.util.ArrayList;

public class WalletActivity extends Activity implements OnClickListener {
	
	private ImageView fanhui;
	private RelativeLayout recharge,withdrawal,incomedetails,spendingdetails,withdrawaldetails;
	private RelativeLayout incomedetails1,spendingdetails1,withdrawaldetails1;
	private TextView vbi;
	Intent intent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vliao_mywallet_01168);
		fanhui = (ImageView)findViewById(R.id.fanhui);
		fanhui.setOnClickListener(this);
		//充值
		recharge = (RelativeLayout)findViewById(R.id.recharge);
		recharge.setOnClickListener(this);
		//提现
		withdrawal = (RelativeLayout)findViewById(R.id.withdrawal);
		withdrawal.setOnClickListener(this);
		//收入明细
		incomedetails = (RelativeLayout)findViewById(R.id.incomedetails);
		incomedetails.setOnClickListener(this);
		//支出明细
		spendingdetails = (RelativeLayout)findViewById(R.id.spendingdetails);
		spendingdetails.setOnClickListener(this);
		//提现明细
		withdrawaldetails = (RelativeLayout)findViewById(R.id.withdrawaldetails);
		withdrawaldetails.setOnClickListener(this);
		//主播的
		//收入明细
		incomedetails1 = (RelativeLayout)findViewById(R.id.incomedetails1);
		incomedetails1.setOnClickListener(this);
		//支出明细
		spendingdetails1 = (RelativeLayout)findViewById(R.id.spendingdetails1);
		spendingdetails1.setOnClickListener(this);
		//提现明细
		withdrawaldetails1 = (RelativeLayout)findViewById(R.id.withdrawaldetails1);
		withdrawaldetails1.setOnClickListener(this);
		//提现明细
		vbi = (TextView)findViewById(R.id.vbi);
		String mode="vliao_zongjine";
		String params[]={Util.userid};
		UsersThread_01168C b1=new UsersThread_01168C(mode,params,requestHandler);
		Thread thread1 =new Thread(b1.runnable);
		thread1.start();
	}
	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		int id = v.getId();
		switch (id) {
		
			case R.id.fanhui://退出操作
				this.finish();
				break;
			//充值
			case R.id.recharge:
				if(Util.iszhubo.equals("0")){
					intent = new Intent();
					intent.setClass(WalletActivity.this, Chongzhi_01178.class);//v币
					startActivity(intent);
				}else{
					Toast.makeText(WalletActivity.this,"主播暂无此功能",Toast.LENGTH_SHORT).show();
				}
			break;
			//提现
			case R.id.withdrawal:
				if(Util.iszhubo.equals("1")){
					intent=new Intent();
					intent.setClass(this, withdraw_01156.class);
					startActivity(intent);
				}else{
					Toast.makeText(WalletActivity.this,"只有主播可以提现",Toast.LENGTH_SHORT).show();
				}
			break;
			//收入明细
			case R.id.incomedetails:
				intent=new Intent();
				intent.setClass(this, Vliao_shourumingxi_01168.class);
				startActivity(intent);
			break;
			//支出明细
			case R.id.spendingdetails:
				intent=new Intent();
				intent.setClass(this, Vliao_zhichumingxi_01168.class);
				startActivity(intent);
			break;
			//提现明细
			case R.id.withdrawaldetails:
				intent=new Intent();
				intent.setClass(this, Vliao_tixianmingxi_01168.class);
				startActivity(intent);
			break;
			//收入明细
			case R.id.incomedetails1:
				intent=new Intent();
				intent.setClass(this, Vliao_shourumingxizhubo_01168.class);
				startActivity(intent);
			break;
			//支出明细
			case R.id.spendingdetails1:
				intent=new Intent();
				intent.setClass(this, Vliao_zhichumingxizhubo_01168.class);
				startActivity(intent);
			break;
			//提现明细
			case R.id.withdrawaldetails1:
				intent=new Intent();
				intent.setClass(this, Vliao_tixianmingxizhubo_01168.class);
				startActivity(intent);
			break;
		
		}
		
	}
	
	private Handler requestHandler = new Handler() {
		  @Override
		  public void handleMessage(Message msg) {
		    switch (msg.what) {
		      case 0:
		      break;
		     
		      //解析重置密码请求返回的json字符串
		      case 201:
		    	  @SuppressWarnings("unchecked")
                  ArrayList<Vliao1_01168> list1 = (ArrayList<Vliao1_01168>)msg.obj;
		    	  if(list1.size()!=0 && list1!=null){
					  //V币资产
					  int vbzichan = list1.get(0).getMoney();
					  /**************
					   * 取消下面的注释可删除"123"的内容
 					   *************/
					 // String a = "123";
					  /***************************
					   *暂时注释由个人中M传递过来的钱数
					   *******************/
					 String a= Format_01162.formatString(vbzichan);
					  vbi.setText(a);
					  String aa = list1.get(0).getIs_anchor()+"";
					  if(aa.equals("1")){
						  //该用户是主播
						  incomedetails.setVisibility(View.GONE);
						  spendingdetails.setVisibility(View.GONE);
						  withdrawaldetails.setVisibility(View.GONE);
						  incomedetails1.setVisibility(View.VISIBLE);
						  spendingdetails1.setVisibility(View.VISIBLE);
						  withdrawaldetails1.setVisibility(View.VISIBLE);
					  }
				  }

		    	 break;
		      
		     
		    }
		 }
	};

	@Override
	protected void onResume() {
		super.onResume();
		String mode="vliao_zongjine";
		String params[]={Util.userid};
		UsersThread_01168C b1=new UsersThread_01168C(mode,params,requestHandler);
		Thread thread1 =new Thread(b1.runnable);
		thread1.start();
	}
}
