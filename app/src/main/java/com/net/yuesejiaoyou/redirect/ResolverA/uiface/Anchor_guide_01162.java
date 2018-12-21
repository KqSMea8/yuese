package com.net.yuesejiaoyou.redirect.ResolverA.uiface;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.net.yuesejiaoyou.R;
///////////////A区转入到C区
import com.net.yuesejiaoyou.redirect.ResolverC.uiface.Authentication_01150;
/**
 * Created by Administrator on 2018/4/6.
 */

public class Anchor_guide_01162 extends Activity implements View.OnClickListener {
private ImageView back1;
private TextView agree,xieyi1;
private	LinearLayout note1;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.anchor_page);
		back1= (ImageView) findViewById(R.id.back1);
		agree= (TextView) findViewById(R.id.agree);
		//note1= (LinearLayout) findViewById(R.id.note1);
		back1.setOnClickListener(this);
		agree.setOnClickListener(this);
		xieyi1= (TextView) findViewById(R.id.xieyi1);
		xieyi1.setOnClickListener(this);

//		note1.setText(Html.fromHtml("<b>NOTE:</b> VVchat is a preminum video dating platform. To \n " +
//				"join as VV anchor, you have to follow the Platform rules \n and local laws."));

	}

	@Override
	public void onClick(View view) {
		int id=view.getId();
		switch (id){
			case R.id.back1:
				finish();
				break;
			case R.id.agree:
				Intent intent=new Intent();
				intent.setClass(Anchor_guide_01162.this,Authentication_01150.class);
				startActivity(intent);
				finish();
				break;
			case R.id.xieyi1:
				Intent intent1=new Intent();
				intent1.setClass(Anchor_guide_01162.this,Videochat_agreement_01162.class);
				startActivity(intent1);

				break;
		}
	}
}
