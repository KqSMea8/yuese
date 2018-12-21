package com.net.yuesejiaoyou.redirect.ResolverC.uiface;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.vliaofans_01168;
import com.net.yuesejiaoyou.redirect.ResolverC.interface3.UsersThread_vliao_01178C;
import org.json.JSONException;
import org.json.JSONObject;


/************************
 * 本类是设置主播价格
 ***********************/
public class Vliao_mytimeprice_01178 extends Activity implements OnClickListener {

	String etcontent;
	ArrayList<vliaofans_01168> list1 = new ArrayList<vliaofans_01168>();

	private EditText vcurrencynum;
	private TextView confirm;
	private ImageView fanhui;
	private RelativeLayout fanhuizong;

	/**************************
	 *
	 * @param savedInstanceState
	 *************************/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vliao_mytimeprice_01178);
		vcurrencynum = (EditText) findViewById(R.id.vcurrencynum);
		confirm = (TextView) findViewById(R.id.confirm);
		confirm.setOnClickListener(this);
		fanhui=(ImageView) findViewById(R.id.fanhui);
		fanhui.setOnClickListener(this);
		fanhuizong = (RelativeLayout) findViewById(R.id.fanhuizong);
		fanhuizong.setOnClickListener(this);
		vcurrencynum.setText(getIntent().getStringExtra("ybprice"));
		TextView reward_percent = (TextView) findViewById(R.id.reward_percent);

		reward_percent.setText("           其中"+getIntent().getStringExtra("reward_percent")+"%归我所有，可在我的钱包中提现");
	}

	/**********************
	 * 返回数据
	 *********************/
	private Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch(msg.what){
			////////////////////////////////
			case 120:
				String json=(String) msg.obj;
				////////////////////////
				LogDetect.send(LogDetect.DataType.specialType, "---json--01178--", json);
				///////////////////////
				JSONObject jsonObject;
				String jsonmod="";
				try {
					jsonObject = new JSONObject(json);
					jsonmod=jsonObject.getString("orderInfo");
					////////////////////////
					LogDetect.send(LogDetect.DataType.specialType, "---jsonmod--01178--", jsonmod);
					///////////////////////
				} catch (JSONException e) {
					e.printStackTrace();
				}
				if("1".equals(jsonmod)){
					//Update the data successfully---价格提交成功
					Toast.makeText(Vliao_mytimeprice_01178.this, "价格提交成功", Toast.LENGTH_SHORT).show();
					finish();
				}
				break;
			}
			
		}
		
	};

	/**************************
	 * 单击事件
	 * @param arg0
	 *************************/
	@Override
	public void onClick(View arg0) {
		int id=arg0.getId();
		switch(id){
		////////////////////////////////
		case R.id.fanhuizong:
			finish();
			break;
		///////////////////////////////
		case R.id.fanhui:
			finish();
			break;
		//////////////////////////////
		case R.id.confirm:
			etcontent = vcurrencynum.getText().toString();
			if(etcontent.equals("")){
				Toast.makeText(Vliao_mytimeprice_01178.this,"不能为空",Toast.LENGTH_SHORT).show();
				return;
			}
			int t= Integer.parseInt(etcontent);
			if(t>=1&&t<=10){
				String mode = "vcurrencyconfirm";
				String[] params = {Util.userid, etcontent};
				////////////////////////
				LogDetect.send(LogDetect.DataType.specialType, "vliao_mytimeprice_01178--params---:",params[0] + "," + params[1]);
				///////////////////////
				UsersThread_vliao_01178C b=new UsersThread_vliao_01178C(mode, params, handler);
				Thread thread=new Thread(b.runnable);
				thread.start();

			}else{
				//Please enter a number between 10 and 60.---输入的价格必须在10到60悦币之间。
				Toast.makeText(Vliao_mytimeprice_01178.this,"输入的价格必须在1到10悦币之间。 ", Toast.LENGTH_LONG).show();
			}
			break;
		}
		
	}
	
}
