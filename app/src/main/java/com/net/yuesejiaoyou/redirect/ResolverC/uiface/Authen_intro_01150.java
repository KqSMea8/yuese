package com.net.yuesejiaoyou.redirect.ResolverC.uiface;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.BaseActivity;

/*import com.net.yuesejiaoyou.R;
import com.example.vliao.interface4.LogDetect;*/

public class Authen_intro_01150 extends BaseActivity implements OnClickListener {

	private ImageView back;
	private EditText intro;
	private TextView ascertain,num;
	Bundle bundle;
	private Intent intent= new Intent();
	String a = "";
	private int start,end;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		back = (ImageView) findViewById(R.id.back);
		back.setOnClickListener(this);
		intro = (EditText)findViewById(R.id.intro);
		num = (TextView) findViewById(R.id.num);
		ascertain = (TextView) findViewById(R.id.ascertain);
		ascertain.setOnClickListener(this);
		intro.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
				a=charSequence+"";
			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
				num.setText(charSequence.length()+"");
			}

			@Override
			public void afterTextChanged(Editable editable) {
				start=intro.getSelectionStart();
				end=intro.getSelectionEnd();
				if(a.length()>15){
					Toast.makeText(Authen_intro_01150.this, "最多输入15个字.", Toast.LENGTH_SHORT).show();
					editable.delete(start-1,end);
					intro.setText(editable);
					intro.setSelection(a.length());
				}

			}
		});
	}

	@Override
	protected int getContentView() {
		return R.layout.renzheng_intro_01150;
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
			
			if(intro.getText().toString().equals("")){
				Toast.makeText(Authen_intro_01150.this, "请输入个人简介！", Toast.LENGTH_SHORT).show();
			}else{
				a = intro.getText().toString()+"";
				LogDetect.send(LogDetect.DataType.specialType, "获取个人介绍a：",a);
				bundle = new Bundle();
				bundle.putString("introduction", a);
				intent.putExtras(bundle);
				setResult(203,  intent);
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
