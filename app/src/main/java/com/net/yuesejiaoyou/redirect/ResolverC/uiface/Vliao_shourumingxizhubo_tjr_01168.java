package com.net.yuesejiaoyou.redirect.ResolverC.uiface;

import java.util.ArrayList;





import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;

import com.net.yuesejiaoyou.redirect.ResolverC.getset.Page;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.Vliao1_01168;
import com.net.yuesejiaoyou.redirect.ResolverC.interface3.UsersThread_01168C;
import com.net.yuesejiaoyou.redirect.ResolverC.interface4.Vliao_shourutjrAdapter_01168;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
public class Vliao_shourumingxizhubo_tjr_01168 extends Activity implements OnClickListener {
private ImageView fanhui;
private TextView shourujine,allshouru;
private ListView shouru;
private Context context;

	@Override
		protected void onCreate(Bundle savedInstanceState) {
			
			super.onCreate(savedInstanceState);
			context=this;
			setContentView(R.layout.vliao_shouruzhubo_01168);
			fanhui=(ImageView) findViewById(R.id.fanhui);
			fanhui.setOnClickListener(this);
			//shourujine
			shourujine=(TextView) findViewById(R.id.shourujine);
			shourujine.setOnClickListener(this);
			shouru=(ListView) findViewById(R.id.shouru);
			allshouru=(TextView) findViewById(R.id.allshouru);
			
			allshouru.setText("总收入金额（元）");
			String mode="shouruzhubo_tjr";
			String []params={Util.userid};
			UsersThread_01168C b=new UsersThread_01168C(mode, params, handler);
			Thread thread=new Thread(b.runnable);
			thread.start();
			
					
		}
	private Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch(msg.what){
			case 215:
				Page page= (Page) msg.obj;
	    	  	shourujine.setText(page.getAblenum()+"");
	    	  	ArrayList<Vliao1_01168> list2=(ArrayList<Vliao1_01168>) page.getList();
	    	  	LogDetect.send(LogDetect.DataType.basicType,"01162----list集合",list2);
	    	  	Vliao_shourutjrAdapter_01168 adapter=new Vliao_shourutjrAdapter_01168(list2, Vliao_shourumingxizhubo_tjr_01168.this,shouru);
				shouru.setAdapter(adapter);
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
