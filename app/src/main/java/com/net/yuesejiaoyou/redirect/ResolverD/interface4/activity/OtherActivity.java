package com.net.yuesejiaoyou.redirect.ResolverD.interface4.activity;



import org.json.JSONException;
import org.json.JSONObject;

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.redirect.ResolverA.interface3.UsersThread_01168A;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class OtherActivity extends Activity implements OnClickListener{
	
	private ImageView fanhui;
	private EditText wenben,shoujihao;
	private TextView queren;
	private String neirong="";
	private String phonenum="";
	String wenbenneirong = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_other);
		//改变字体
		//Typeface typeFace =Typeface.createFromAsset(getAssets(),"fonts/Arial_0.ttf");
		
		fanhui = (ImageView)findViewById(R.id.fanhui);
		fanhui.setOnClickListener(this);
		//确认
		queren = (TextView)findViewById(R.id.queren);
		queren.setOnClickListener(this);
		/**
		 * 改变字体？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？
		 */
		//queren.setTypeface(typeFace);
		
		//内容
		wenben = (EditText)findViewById(R.id.wenben);
		//手机号
		shoujihao = (EditText)findViewById(R.id.shoujihao);
		
		
	}
	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		int id = v.getId();
		switch (id) {
		
		case R.id.fanhui://退出操作
			this.finish();
			break;
		case R.id.queren:
			wenbenneirong = wenben.getText().toString();
			if(wenbenneirong.equals("")){
				Toast.makeText(OtherActivity.this, "反馈内容不能为空!",
						Toast.LENGTH_SHORT).show();
			}else{
				//意见反馈
				neirong = wenben.getText().toString();
				phonenum = shoujihao.getText().toString();
				String[] params={Util.userid,neirong,phonenum};
				UsersThread_01168A a = new UsersThread_01168A("Vliao_yijianfankui",params,requestHandler);
				Thread thread = new Thread(a.runnable);
				LogDetect.send(LogDetect.DataType.specialType,"提交意见反馈", thread);
				thread.start();
			}
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
		      case 200:
		    	  String json = (String)msg.obj;
			    	try { //如果服务端返回1，说明个人信息修改成功了
			    		JSONObject jsonObject = new JSONObject(json);
						if(jsonObject.getString("success").equals("1")){
							//提交成功
							Toast.makeText(OtherActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
							finish();
						}else{
							//提交失败
							Toast.makeText(OtherActivity.this, "提交失败", Toast.LENGTH_SHORT).show();
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    	 break;
		      
		     
		    }
		 }
	};
	
	
	
	
	
	
	
	

}
