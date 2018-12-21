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

/*import com.net.yuesejiaoyou.R;
import com.example.vliao.interface4.LogDetect;*/

public class Authen_signature_01150 extends Activity implements OnClickListener {

	private ImageView back;
	private EditText signature;
	private TextView ascertain,num;
	Bundle bundle;
	private Intent intent= new Intent();
	String a = "";
	private int start,end;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.renzheng_signature_01150);

		back = (ImageView) findViewById(R.id.back);
		back.setOnClickListener(this);

		signature = (EditText)findViewById(R.id.signature);

		num = (TextView) findViewById(R.id.num);

		ascertain = (TextView) findViewById(R.id.ascertain);
		ascertain.setOnClickListener(this);



		signature.addTextChangedListener(new TextWatcher() {
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
                 start=signature.getSelectionStart();
                 end=signature.getSelectionEnd();
                 if(a.length()>15){
					 Toast.makeText(Authen_signature_01150.this, "最多输入15个字.", Toast.LENGTH_SHORT).show();
					 editable.delete(start-1,end);
                    signature.setText(editable);
                    signature.setSelection(a.length());
				 }
			}
		});


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
			
			if(signature.getText().toString().equals("")){
				Toast.makeText(Authen_signature_01150.this, "请填写个性签名！", Toast.LENGTH_SHORT).show();
			}else{
				a = signature.getText().toString()+"";
				LogDetect.send(LogDetect.DataType.specialType, "获取个性签名：",a);
				bundle = new Bundle();
				bundle.putString("signature", a);
				intent.putExtras(bundle);
				setResult(205,  intent);
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
