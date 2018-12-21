package com.net.yuesejiaoyou.redirect.ResolverD.uiface;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;


import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.redirect.ResolverD.getset.vliaofans_01168;
import com.net.yuesejiaoyou.redirect.ResolverD.interface3.PayThreadA;
import com.net.yuesejiaoyou.redirect.ResolverD.interface3.UsersThread_vliao_01178;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.wxapi.Constants;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.wxapi.PayResult;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.wxapi.WXUtil;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class Chongzhi_01178 extends Activity implements OnClickListener {
	private RelativeLayout buy100, buy200, buy500, buy1000, buy2000, buy5000,buy10000;
	private TextView vbnum1, vbnum2, vbnum3, vbnum4, vbnum5, vbnum6,vbnum7;
	private TextView money1, money2, money3, money4, money5, money6,money7;
	ImageView back;
	String money = "";
	String vnum = "";
	
	//弹出框
	private TextView money_txt,money_num;
	private RelativeLayout zhifu,wechat;
	private ImageView back1;
	private PopupWindow mPopWindow;

	private IWXAPI api;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pay_01178);

		api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
		vbnum1 = (TextView) findViewById(R.id.vbnum1);
		vbnum2 = (TextView) findViewById(R.id.vbnum2);
		vbnum3 = (TextView) findViewById(R.id.vbnum3);
		vbnum4 = (TextView) findViewById(R.id.vbnum4);
		vbnum5 = (TextView) findViewById(R.id.vbnum5);
		vbnum6 = (TextView) findViewById(R.id.vbnum6);
		vbnum7= (TextView) findViewById(R.id.vbnum7);
		money1 = (TextView) findViewById(R.id.money1);
		money2 = (TextView) findViewById(R.id.money2);
		money3 = (TextView) findViewById(R.id.money3);
		money4 = (TextView) findViewById(R.id.money4);
		money5 = (TextView) findViewById(R.id.money5);
		money6 = (TextView) findViewById(R.id.money6);
		money7= (TextView) findViewById(R.id.money7);
		buy100 = (RelativeLayout) findViewById(R.id.buy100);
		buy100.setOnClickListener(this);
		buy200 = (RelativeLayout) findViewById(R.id.buy200);
		buy200.setOnClickListener(this);
		buy500 = (RelativeLayout) findViewById(R.id.buy500);
		buy500.setOnClickListener(this);
		buy1000 = (RelativeLayout) findViewById(R.id.buy1000);
		buy1000.setOnClickListener(this);
		buy2000 = (RelativeLayout) findViewById(R.id.buy2000);
		buy2000.setOnClickListener(this);
		buy5000 = (RelativeLayout) findViewById(R.id.buy5000);
		buy5000.setOnClickListener(this);
		buy10000= (RelativeLayout) findViewById(R.id.buy10000);
		buy10000.setOnClickListener(this);
		back = (ImageView) findViewById(R.id.back);
		back.setOnClickListener(this);

		//点击金额发请求：
		String mode = "payprice";
		String[] params = {};
		UsersThread_vliao_01178 usersThread = new UsersThread_vliao_01178(mode, params, handler);
		Thread t = new Thread(usersThread.runnable);
		t.start();
		reciever reviever = new reciever();
		IntentFilter filter1 = new IntentFilter("2");
		registerReceiver(reviever, filter1);
	}

	private class reciever extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			Toast.makeText(Chongzhi_01178.this, "充值成功", Toast.LENGTH_LONG).show();
			//myYuanbao();
			finish();
		}
	}

	@Override
	protected void onActivityResult (int requestCode, int resultCode, Intent data){
		if (resultCode == Activity.RESULT_OK) {

		}
	}

	private Handler handler=new Handler(){
		//处理服务器返回的数据
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch(msg.what){

				case 119:
					LogDetect.send(LogDetect.DataType.specialType, "---case 119——1165:--01178--", "119");
					String json3=(String) msg.obj;
					if(json3.isEmpty()) {
						break;
					}
					JSONObject jsonObject2;
					String jsonmodinvite="";
					try {
						jsonObject2 = new JSONObject(json3);
						jsonmodinvite=jsonObject2.getString("orderInfo");
						LogDetect.send(LogDetect.DataType.specialType, "---jsonmodinvite——1165:--01178--", jsonmodinvite);
					} catch (JSONException e) {
						e.printStackTrace();
					}
					if("1".equals(jsonmodinvite)){
						Toast.makeText(Chongzhi_01178.this, "成功将订单插入数据库", Toast.LENGTH_SHORT).show();
					}
					break;

				case 200:

					LogDetect.send(LogDetect.DataType.specialType, "支付01178：","返回数据");
					@SuppressWarnings("unchecked")
					ArrayList<vliaofans_01168> list11=(ArrayList<vliaofans_01168>) msg.obj;
					if(list11 == null || list11.size() <= 0) {
						LogDetect.send(LogDetect.DataType.specialType,"01178","list11 error: "+list11);
						break;
					}
					LogDetect.send(LogDetect.DataType.specialType, "余额_01178_yue_list11:",list11);
					vbnum1.setText(list11.get(0).getV_num().toString());
					money1.setText(list11.get(0).getPrice().toString()+"元");
					vbnum2.setText(list11.get(1).getV_num().toString());
					money2.setText(list11.get(1).getPrice().toString()+"元");
					vbnum3.setText(list11.get(2).getV_num().toString());
					money3.setText(list11.get(2).getPrice().toString()+"元");
					vbnum4.setText(list11.get(3).getV_num().toString());
					money4.setText(list11.get(3).getPrice().toString()+"元");
					vbnum5.setText(list11.get(4).getV_num().toString());
					money5.setText(list11.get(4).getPrice().toString()+"元");
					vbnum6.setText(list11.get(5).getV_num().toString());
					money6.setText(list11.get(5).getPrice().toString()+"元");
					vbnum7.setText(list11.get(6).getV_num().toString());
					money7.setText(list11.get(6).getPrice().toString()+"元");
					break;

			}
		}
	};

	@Override
	public void onClick(View arg0) {
		int id = arg0.getId();
		switch (id) {
			case R.id.back:
				finish();
				break;
			case R.id.buy100:
				vnum = vbnum1.getText().toString();
				money = money1.getText().toString();
				//money="10";
				
				//onBuyPressed();
				showPopupspWindow2(buy100,money,vnum);

				break;
			case R.id.buy200:
				vnum = vbnum2.getText().toString();
				money = money2.getText().toString();
				//money="20";
				showPopupspWindow2(buy200,money,vnum);
				//onBuyPressed();

				break;
			case R.id.buy500:
				//money="50";
				vnum = vbnum3.getText().toString();
				money = money3.getText().toString();
				//onBuyPressed();
				showPopupspWindow2(buy500,money,vnum);
				break;
			case R.id.buy1000:
				//money="100";
				vnum = vbnum4.getText().toString();
				money = money4.getText().toString();
				//onBuyPressed();
				showPopupspWindow2(buy1000,money,vnum);
				break;
			case R.id.buy2000:
				//money="200";
				//onBuyPressed();
				vnum = vbnum5.getText().toString();
				money = money5.getText().toString();
				showPopupspWindow2(buy2000,money,vnum);
				break;
			case R.id.buy5000:
				//money="500";
				vnum = vbnum6.getText().toString();
				money = money6.getText().toString();
				showPopupspWindow2(buy5000,money,vnum);
				//onBuyPressed();
				break;
			case R.id.buy10000:
				//money="1000";
				vnum = vbnum7.getText().toString();
				money = money7.getText().toString();
				showPopupspWindow2(buy10000,money,vnum);
				//onBuyPressed();
				break;
		}
	}



	@Override
	protected void onDestroy() {
		//stopService(new Intent(this, PayPalService.class));
		super.onDestroy();
	}

	private Handler payhandler=new Handler(){
		//处理服务器返回的数据
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch(msg.what){

				case 1000:
					String content=(String) msg.obj;
					Log.e("get server pay params:",content);
					if(!content.equals("")){
						JSONObject json;
						try {
							json = new JSONObject(content);

							if(null != json && !json.has("retcode")){
								PayReq req = new PayReq();
								//req.appId = "wxf8b4f85f3a794e77";  // 测试用appId
								req.appId			= json.getString("appId");
								req.partnerId		= json.getString("partnerid");
								req.prepayId		= json.getString("prepayid");
								req.nonceStr		= json.getString("nonceStr");
								req.timeStamp		= json.getString("timeStamp");
								req.packageValue	= "Sign=WXPay";
								req.sign			= json.getString("sign");
					            req.extData			= "app data"; // optional
			/*		request.appId = "wxd930ea5d5a258f4f";
					request.partnerId = "1900000109";
					request.prepayId= "1101000000140415649af9fc314aa427",;
					request.packageValue = "Sign=WXPay";
					request.nonceStr= "1101000000140429eb40476f8896f4c9";
					request.timeStamp= "1398746574";
					request.sign= "7FFECB600D7157C5AA49810D2D8F28BC2811827B";*/

								Toast.makeText(Chongzhi_01178.this, "正常调起支付", Toast.LENGTH_SHORT).show();
								// 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
								api.sendReq(req);
							}else{
								LogDetect.send(LogDetect.DataType.specialType,"onResp","返回错误"+json.getString("retmsg"));
								Log.d("PAY_GET", "返回错误"+json.getString("retmsg"));
								//Toast.makeText(ZWW_pay.this, "返回错误"+json.getString("retmsg"), Toast.LENGTH_SHORT).show();
							}
						} catch (JSONException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						break;
					}else{
						Toast.makeText(Chongzhi_01178.this,"网络异常,请稍后重试", Toast.LENGTH_SHORT).show();
					}

				case 300:
					String json1=(String) msg.obj;
					if(!json1.equals("")){
						JSONObject jsonObject;
						String orderInfo1="";
						try {
							jsonObject = new JSONObject(json1);
							orderInfo1=jsonObject.getString("orderInfo");
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						final String orderInfo=orderInfo1;

						Runnable payRunnable = new Runnable() {

							@Override
							public void run() {
								PayTask alipay = new PayTask(Chongzhi_01178.this);
								Map<String, String> result = alipay.payV2(orderInfo, true);
								Log.i("msp", result.toString());
								Message msg = new Message();
								msg.what = SDK_PAY_FLAG;
								msg.obj = result;
								mHandler.sendMessage(msg);
							}
						};
						Thread payThread = new Thread(payRunnable);
						payThread.start();
					}else{
						Toast.makeText(Chongzhi_01178.this,"网络异常,请稍后重试", Toast.LENGTH_SHORT).show();
					}

					break;
			}
		}
	};

	private static final int SDK_PAY_FLAG = 1;
	private static final int SDK_AUTH_FLAG = 2;
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		@SuppressWarnings("unused")
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case SDK_PAY_FLAG: {
					@SuppressWarnings("unchecked")
					PayResult payResult = new PayResult((Map<String, String>) msg.obj);
					/**
					 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
					 */
					String resultInfo = payResult.getResult();// 同步返回需要验证的信息
					LogDetect.send(LogDetect.DataType.basicType,"参数resultInfo",resultInfo);
					String resultStatus = payResult.getResultStatus();
					// 判断resultStatus 为9000则代表支付成功
					if (TextUtils.equals(resultStatus, "9000")) {
						// 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
						Toast.makeText(Chongzhi_01178.this, "支付成功", Toast.LENGTH_SHORT).show();
						//myYuanbao();
						finish();
					} else {
						// 该笔订单真实的支付结果，需要依赖服务端的异步通知。
						Toast.makeText(Chongzhi_01178.this, "支付失败", Toast.LENGTH_SHORT).show();
					}
					break;
				}
				case SDK_AUTH_FLAG: {
//				@SuppressWarnings("unchecked")
//				AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
//				String resultStatus = authResult.getResultStatus();
//
//				// 判断resultStatus 为“9000”且result_code
//				// 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
//				if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
//					// 获取alipay_open_id，调支付时作为参数extern_token 的value
//					// 传入，则支付账户为该授权账户
//					Toast.makeText(PayDemoActivity.this,
//							"授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
//							.show();
//				} else {
//					// 其他状态值则为授权失败
//					Toast.makeText(PayDemoActivity.this,
//							"授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();
//
//				}
					break;
				}
				default:
					break;
			}
		};
	};





	public void showPopupspWindow2(View parent, String mon,String vnums) {

		mon = mon.replaceAll("元","");

		final String realMoney = mon;

		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LogDetect.send(LogDetect.DataType.specialType, "showPopupspWindow2——01168：","弹出框2布局开始");
		View layout = inflater.inflate(R.layout.pay_style, null);
		
		
		money_txt = (TextView) layout.findViewById(R.id.money_txt);
		money_num = (TextView) layout.findViewById(R.id.money_num);
		//支付宝支付
		zhifu = (RelativeLayout) layout.findViewById(R.id.zhifu);
		//zhifu.setOnClickListener(this);
		//微信支付
		wechat = (RelativeLayout) layout.findViewById(R.id.wechat);
		//wechat.setOnClickListener(this);
		
		money_txt.setText("充值"+vnums+"悦币,需要支付"+mon+"元");
		money_num.setText(mon+"元");
		//退出弹出框
		back1 = (ImageView)layout.findViewById(R.id.back1);
		//back1.setOnClickListener(this);
		back1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				//取消
				mPopWindow.dismiss();
			}
		});

		final String subMon = vnums;
		zhifu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Toast.makeText(Chongzhi_01178.this, "支付宝充值",
						Toast.LENGTH_SHORT).show();

				String[]params1={Util.userid,"充值",realMoney,"支付宝",subMon+""};
//				String[]params1={Util.userid,"充值","0.01","支付宝",subMon+""};
				String mode="zfbpay";
				PayThreadA pay=new PayThreadA(mode,params1,payhandler);
				Thread thread =new Thread(pay.runnable);
				thread.start();
				//取消
				//mPopWindow.dismiss();
			}
		});
		wechat.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				//取消
				Toast.makeText(Chongzhi_01178.this, "微信充值",
						Toast.LENGTH_SHORT).show();

				final String url = Util.url+"/uiface/rp?mode=A-user-add&mode2=wxpay&userid="+Util.userid+"&type=充值&price="+realMoney+"&wz=微信&num="+subMon;
				//String []params1={Util.userid,"充值","1","支付宝","10"};
				// mode="zfbpay";
				Toast.makeText(Chongzhi_01178.this, "获取订单中...", Toast.LENGTH_SHORT).show();
				try{
					Thread thread2=new Thread(new Runnable()
					{
						@Override
						public void run()
						{
							byte[] buf = WXUtil.httpGet(url);
							if (buf != null && buf.length > 0) {
								String content = new String(buf);
								payhandler.sendMessage(payhandler.obtainMessage(1000,(Object)content));

							}else{
								Log.d("PAY_GET", "服务器请求错误");
								//Toast.makeText(ZWW_pay.this, "服务器请求错误", Toast.LENGTH_SHORT).show();
							}
						}
					});
					thread2.start();

				}catch(Exception e){
					Log.e("PAY_GET", "异常："+e.getMessage());
					//Toast.makeText(ZWW_pay.this, "异常："+e.getMessage(), Toast.LENGTH_SHORT).show();
				}


			}
		});
		
		/*
		wenben = (EditText) layout.findViewById(R.id.wenben);
		
		t4 = (TextView)layout.findViewById(R.id.t4);
		t4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				
				
				a = "0";
				a = wenben.getText().toString();
				if(a.equals("")){
					Toast.makeText(Hunyu_yirenyingxiao_01168.this, "输入百分比不能为空",
							Toast.LENGTH_SHORT).show();
				}else{
					//int sr = Integer.valueOf(a).intValue();
					int sr = Integer.parseInt(a);
					
					if(0<=sr&&sr<=100){
						a = sr+"";
						sum = 1;
						xuanze = "淡季券";
						shuju = a;
						baifenbi.setText(a+"%");
						tu4.setBackgroundResource(R.drawable.fangkuang2);
						mPopWindow.dismiss();
					}else{
						Toast.makeText(Hunyu_yirenyingxiao_01168.this, "请输入不超过100以内的数",
								Toast.LENGTH_SHORT).show();
					}
				}
				
			}
		});
		*/
		
		
		mPopWindow = new PopupWindow(layout, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT, true);
		// 控制键盘是否可以获得焦点
		mPopWindow.setFocusable(true);
		// 设置popupWindow弹出窗体的背景
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.alpha = 0.4f;
		getWindow().setAttributes(lp);
		mPopWindow.setBackgroundDrawable(new BitmapDrawable(null, ""));
		WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		@SuppressWarnings("deprecation")
		// 获取xoff
		int xpos = manager.getDefaultDisplay().getWidth() / 2
				- mPopWindow.getWidth() / 2;
		// xoff,yoff基于anchor的左下角进行偏移。
		// popupWindow.showAsDropDown(parent, 0, 0);
		mPopWindow.showAtLocation(parent, Gravity.BOTTOM | Gravity.BOTTOM,252, 0);
		// 监听

		mPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
			// 在dismiss中恢复透明度
			public void onDismiss() {
				WindowManager.LayoutParams lp = getWindow().getAttributes();
				lp.alpha = 1f;
				getWindow()
						.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
				getWindow().setAttributes(lp);
			}
		});
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
