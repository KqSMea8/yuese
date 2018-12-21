package com.net.yuesejiaoyou.redirect.ResolverC.uiface;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.net.yuesejiaoyou.R;

/**
 * Created by Administrator on 2018/3/25.
 */

public class Vliao_setnickname_01162 extends Activity implements View.OnClickListener {
private EditText edit_nickname;
private TextView besure;
private ImageView back1;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vchat_setnickname_01162);
       back1= (ImageView) findViewById(R.id.back1);
       edit_nickname= (EditText) findViewById(R.id.edit_nickname);
       besure= (TextView) findViewById(R.id.besure);
       back1.setOnClickListener(this);
       besure.setOnClickListener(this);
       Intent intent1=getIntent();
       edit_nickname.setText( intent1.getStringExtra("old_name"));
       edit_nickname.setSelection(intent1.getStringExtra("old_name").length());



	}

	@Override
	public void onClick(View view) {
		int id=view.getId();
		switch (id){
			case R.id.back1:


				if(edit_nickname.getText().toString().equals("")){
					Intent intent = new Intent();
					intent.putExtra("new_name", edit_nickname.getText().toString());
					setResult(162, intent);
					finish();
				}else{
					Intent intent = new Intent();
					intent.putExtra("new_name", edit_nickname.getText().toString());
					setResult(162, intent);
					finish();
				}


				break;
			case R.id.besure:
               if(edit_nickname.getText().toString().equals("")){
				   Toast.makeText(Vliao_setnickname_01162.this,"请输入你的新新昵称", Toast.LENGTH_LONG).show();
			   }else {
				   Intent intent = new Intent();
				   intent.putExtra("new_name", edit_nickname.getText().toString());
				   setResult(162, intent);
				   finish();
			   }
				break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
              if(keyCode== KeyEvent.KEYCODE_BACK){
				  if(edit_nickname.getText().toString().equals("")){
					  Intent intent = new Intent();
					  intent.putExtra("new_name", edit_nickname.getText().toString());
					  setResult(162, intent);
					  finish();
				  }else{
					  Intent intent = new Intent();
					  intent.putExtra("new_name", edit_nickname.getText().toString());
					  setResult(162, intent);
					  finish();
				  }
				  return  true;
			  }else {
				  return super.onKeyDown(keyCode, event);
			  }
	}
}
