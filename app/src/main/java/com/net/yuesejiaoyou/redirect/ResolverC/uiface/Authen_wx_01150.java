package com.net.yuesejiaoyou.redirect.ResolverC.uiface;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;

/*import com.net.yuesejiaoyou.R;
import com.example.vliao.interface4.LogDetect;*/

public class Authen_wx_01150 extends Activity implements OnClickListener {

	private ImageView back;
	private EditText xinnicheng;
	private String neirong;
	private TextView ascertain;
	Bundle bundle;
	private Intent intent= new Intent();
	String a = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.renzheng_wx_01150);
		back = (ImageView) findViewById(R.id.back);
		back.setOnClickListener(this);
		xinnicheng = (EditText)findViewById(R.id.xinnicheng);
		ascertain = (TextView) findViewById(R.id.ascertain);
		ascertain.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		
		switch (id) {
		case R.id.back:
			finish();
			break;
		case R.id.ascertain:
			if(xinnicheng.getText().toString().equals("")){
				Toast.makeText(Authen_wx_01150.this, "请输入微信号!", Toast.LENGTH_SHORT).show();
			}else{
				a = xinnicheng.getText().toString()+"";
				LogDetect.send(LogDetect.DataType.specialType, "获取昵称a：",a);
				bundle = new Bundle();
				bundle.putString("wxnum", a);
				intent.putExtras(bundle);
				setResult(251,  intent);
				this.finish();
			}
			break;
	}
	}
	
	private Handler requestHandler = new Handler() {
		  @Override
		  public void handleMessage(Message msg) {
		    switch (msg.what) {

		    }
		 }
	};
	
	
	
	
	
	
	
	
	
	
	
	
	

}
