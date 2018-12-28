package com.net.yuesejiaoyou.redirect.ResolverC.uiface;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

//import com.net.yuesejiaoyou.R;
//import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.activity.WalletActivity;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.activity.YuyueActivity;
import com.net.yuesejiaoyou.redirect.ResolverD.uiface.Chongzhi_01178;
/*import com.example.vliao.interface3.UsersThread_01168;
import com.example.vliao.interface4.LogDetect;
import com.example.vliao.util.Util;*/


public class main extends Activity implements OnClickListener{

	/*private ImageView fanhui;
	private EditText wenben,shoujihao;
	private TextView queren;
	private String neirong="";
	private String phonenum="";
	String wenbenneirong = "";*/

	private TextView a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,a15,a16,a17,a18,a19,a20;
	private TextView a21,a22,a23,a24,a25,a26,a27,a28;
	Intent intent;
	private Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.testc);
		a1 = (TextView) findViewById(R.id.a1);
		a2 = (TextView) findViewById(R.id.a2);
		a3 = (TextView) findViewById(R.id.a3);
		a4 = (TextView) findViewById(R.id.a4);

		a5 = (TextView) findViewById(R.id.a5);
		a6 = (TextView) findViewById(R.id.a6);
		a7 = (TextView) findViewById(R.id.a7);
		a8 = (TextView) findViewById(R.id.a8);

		a9 = (TextView) findViewById(R.id.a9);
		a10 = (TextView) findViewById(R.id.a10);
		a11 = (TextView) findViewById(R.id.a11);
		a12 = (TextView) findViewById(R.id.a12);

		a13 = (TextView) findViewById(R.id.a13);
		a14 = (TextView) findViewById(R.id.a14);
		a15 = (TextView) findViewById(R.id.a15);
		a16 = (TextView) findViewById(R.id.a16);

		a17 = (TextView) findViewById(R.id.a17);
		a18 = (TextView) findViewById(R.id.a18);
		a19 = (TextView) findViewById(R.id.a19);
		a20 = (TextView) findViewById(R.id.a20);

		a21 = (TextView) findViewById(R.id.a21);
		a22 = (TextView) findViewById(R.id.a22);
		a23 = (TextView) findViewById(R.id.a23);
		a24 = (TextView) findViewById(R.id.a24);

		a25 = (TextView) findViewById(R.id.a25);
		a26 = (TextView) findViewById(R.id.a26);
		a27 = (TextView) findViewById(R.id.a27);
		a28 = (TextView) findViewById(R.id.a28);

		a1.setOnClickListener(this);
		a2.setOnClickListener(this);
		a3.setOnClickListener(this);
		a4.setOnClickListener(this);

		a5.setOnClickListener(this);
		a6.setOnClickListener(this);
		a7.setOnClickListener(this);
		a8.setOnClickListener(this);

		a9.setOnClickListener(this);
		a10.setOnClickListener(this);
		a11.setOnClickListener(this);
		a12.setOnClickListener(this);

		a13.setOnClickListener(this);
		a14.setOnClickListener(this);
		a15.setOnClickListener(this);
		a16.setOnClickListener(this);

		a17.setOnClickListener(this);
		a18.setOnClickListener(this);
		a19.setOnClickListener(this);
		a20.setOnClickListener(this);

		a21.setOnClickListener(this);
		a22.setOnClickListener(this);
		a23.setOnClickListener(this);
		a24.setOnClickListener(this);

		a25.setOnClickListener(this);
		a26.setOnClickListener(this);
		a27.setOnClickListener(this);
		a28.setOnClickListener(this);

	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		int id = v.getId();
		switch (id) {

			//我的亲密榜
			case R.id.a1:
				intent=new Intent();
				intent.setClass(this, Vliao_wodeqinmibang_01178.class);
				startActivity(intent);
				break;
			//他人亲密榜
			case R.id.a2:
				//userid=String.valueOf(userinfo.getId());
				Intent intent = new Intent();
				intent.setClass(this, Vliao_hisqinmibang_01178.class);
				Bundle bundle = new Bundle();
				/****************
				 * 原本在userinfo页面获取值，现在直接获取Util.userid
				 *************/
				//bundle.putString("id", userid);
				bundle.putString("id", Util.userid);
				intent.putExtras(bundle);
				startActivity(intent);

				break;
			//收入明细(普通用户)
			case R.id.a3:
				intent = new Intent();
				intent.setClass(this, Vliao_shourumingxi_01168.class);
				startActivity(intent);
				break;
			//支出明细(普通用户)
			case R.id.a4:
				intent = new Intent();
				intent.setClass(this, Vliao_zhichumingxi_01168.class);
				startActivity(intent);
				break;
			//提现明细(普通用户)
			case R.id.a5:
				intent = new Intent();
				intent.setClass(this, Vliao_tixianmingxi_01168.class);
				startActivity(intent);
				break;
			//收入明细(主播)
			case R.id.a6:
				intent = new Intent();
				intent.setClass(this, Vliao_shourumingxizhubo_01168.class);
				startActivity(intent);
				break;
			//支出明细(主播)
			case R.id.a7:
				intent = new Intent();
				intent.setClass(this, Vliao_zhichumingxizhubo_01168.class);
				startActivity(intent);
				break;
			//提现明细(主播)
			case R.id.a8:
				intent = new Intent();
				intent.setClass(this, Vliao_tixianmingxizhubo_01168.class);
				startActivity(intent);
				break;
			//我的粉丝
			case R.id.a9:
				intent = new Intent();
				intent.setClass(this, Vliao_wodefensi_01168.class);
				startActivity(intent);
				break;
			//我的评价(消息页面)
			case R.id.a10:
				intent = new Intent();
				intent.setClass(this, AppariseActivity.class);
				startActivity(intent);
				break;
			//我的V币(消息页面)
			case R.id.a11:
				intent = new Intent();
				intent.setClass(this, RecomeActivity.class);
				startActivity(intent);
				break;
			//我的通话(消息页面)
			case R.id.a12:
				intent = new Intent();
				intent.setClass(this, CallHistoryActivity.class);
				startActivity(intent);
				break;
				//主播设置价格
			case R.id.a13:
				intent = new Intent();
				intent.setClass(this, Vliao_mytimeprice_01178.class);
				startActivity(intent);
				break;
			//我的钱包
			case R.id.a14:
				intent = new Intent();
				intent.setClass(this, WalletActivity.class);
				startActivity(intent);
				break;
			//我的私藏
			case R.id.a15:
				intent = new Intent();
				intent.setClass(this, MyTreasure_01160.class);
				startActivity(intent);
				break;
				//BlackNameActivity
			//黑名单
			case R.id.a16:
				intent = new Intent();
				intent.setClass(this, BlackNameActivity.class);
				startActivity(intent);
				break;
			/**
			 * 认证资料下的页面，需要注册的页面
			 * Authen_nicheng_01150		昵称
			 * Authen_phonenum_01150	手机号码
			 *Authen_intro_01150		个人介绍
			 * Vliao_job_01168			我的职业
			 * Authen_signature_01150	个性签名
			 * Authen_label_01150		形象标签
			 */
			case R.id.a17:
				intent = new Intent();
				intent.setClass(this, EditActivity.class);
				startActivity(intent);
				break;
			case R.id.a18:
				intent = new Intent();
				intent.setClass(this, Authentication_01150.class);
				startActivity(intent);
				break;
			//主播印象
			case R.id.a19:
				/***********************
				 * 原本是上一页面传递过来，现在先用user_id
				 * 上一页面是VMyAdapterzl_01066在原悦色交友interface4中
				 **********************/
				/*************
				 * fragment可解开下面注释
				 ************/
				/*intent = new Intent();
				intent.setClass(context, Mytag_01162.class);
				Bundle b = new Bundle();
				b.putString("zhubo_id",Util.userid);
				intent.putExtras(b);
				context.startActivity(intent);*/
				/*************
				 * fragment可删除下面注释
				 ************/
				intent = new Intent();
				intent.setClass(this, Mytag_01162.class);
				Bundle b = new Bundle();
				b.putString("zhubo_id",Util.userid);
				intent.putExtras(b);
				startActivity(intent);
				break;
			//关注
			case R.id.a20:
				intent = new Intent();
				intent.setClass(this, FocusDetail.class);
				startActivity(intent);
				break;
			//普通用户修改资料
			case R.id.a21:
				intent = new Intent();
				intent.setClass(this, ModifyAvaterActivity.class);
				startActivity(intent);
				break;
			//withdraw_01156
			//提现
			case R.id.a22:
				intent = new Intent();
				intent.setClass(this, withdraw_01156.class);
				startActivity(intent);
				break;
			//充值
			case R.id.a23:
				intent = new Intent();
				intent.setClass(this, Chongzhi_01178.class);
				startActivity(intent);
				break;
			//预约(消息页面)
			case R.id.a24:
				intent = new Intent();
				intent.setClass(this, YuyueActivity.class);
				startActivity(intent);
				break;
				//申请(提交支付宝申请[插入数据])
			case R.id.a25:
				intent = new Intent();
				intent.setClass(this, vliao_shenqing_01152.class);
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
		    /*  case 200:
		    	  String json = (String)msg.obj;
			    	try { //如果服务端返回1，说明个人信息修改成功了
			    		JSONObject jsonObject = new JSONObject(json);
						if(jsonObject.getString("success").equals("1")){
							//提交成功
							Toast.makeText(AA_ceshirukou_01168.this, "提交成功", Toast.LENGTH_SHORT).show();
							finish();
						}else{
							//提交失败
							Toast.makeText(AA_ceshirukou_01168.this, "提交失败", Toast.LENGTH_SHORT).show();
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    	 break;*/


			}
		}
	};









}
