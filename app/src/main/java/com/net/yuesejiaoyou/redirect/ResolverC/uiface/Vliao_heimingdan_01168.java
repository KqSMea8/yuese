package com.net.yuesejiaoyou.redirect.ResolverC.uiface;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

/*import com.net.yuesejiaoyou.R;
import com.example.vliao.getset.Vliao1_01168;
import com.example.vliao.interface3.UsersThread_01168;
import com.example.vliao.interface4.LogDetect;
import com.example.vliao.interface4.Vliao_heimingdanAdapter_01168;
import com.example.vliao.util.Util;*/

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.Vliao1_01168;
import com.net.yuesejiaoyou.redirect.ResolverC.interface3.UsersThread_01168C;
import com.net.yuesejiaoyou.redirect.ResolverC.interface4.Vliao_heimingdanAdapter_01168;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Vliao_heimingdan_01168 extends Activity implements OnClickListener {
private ImageView fanhui;
private ListView l1;
private Context context;
private Vliao_heimingdanAdapter_01168 adapter1;
	@Override
		protected void onCreate(Bundle savedInstanceState) {
			
			super.onCreate(savedInstanceState);
			context=this;
			setContentView(R.layout.vliao_heimingdan_01168);
			
			fanhui=(ImageView) findViewById(R.id.fanhui);
			fanhui.setOnClickListener(this);
			l1=(ListView) findViewById(R.id.l1);
			String mode="heimingdan";
			String[]params={Util.userid};
			UsersThread_01168C b=new UsersThread_01168C(mode, params, handler);
			Thread thread=new Thread(b.runnable);
			thread.start();
		}
	private Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch(msg.what){
			case 205:
				ArrayList<Vliao1_01168> list2=(ArrayList<Vliao1_01168>) msg.obj;
				adapter1=new Vliao_heimingdanAdapter_01168(list2, context,handler);
				l1.setAdapter(adapter1);
				break;
			case 206:
				String json = (String)msg.obj;
		    	  LogDetect.send(LogDetect.DataType.specialType, "Meijian_yijianfankui_01168请求209：a",json);
			    	try { //如果服务端返回1，说明个人信息修改成功了
			    		JSONObject jsonObject = new JSONObject(json);
						if(jsonObject.getString("success").equals("1")){
						Toast.makeText(Vliao_heimingdan_01168.this, "取消成功", Toast.LENGTH_SHORT).show();
							String mode="heimingdan";
							String[]params={Util.userid};
							UsersThread_01168C b=new UsersThread_01168C(mode, params, handler);
							Thread thread=new Thread(b.runnable);
							thread.start();
						}else{
							Toast.makeText(Vliao_heimingdan_01168.this, "取消失败,请稍后充实", Toast.LENGTH_SHORT).show();
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				break;
			}
			
			
		}
		
	};
	
	@Override
	public void onClick(View arg0) {
		int id=arg0.getId();
		switch(id){
		case R.id.fanhui:
			finish();
			break;
		}
		
	}

}
