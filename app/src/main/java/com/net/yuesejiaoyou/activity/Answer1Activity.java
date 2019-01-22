package com.net.yuesejiaoyou.activity;


import android.os.Bundle;

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.BaseActivity;

import butterknife.OnClick;


public class Answer1Activity extends BaseActivity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected int getContentView() {
		return R.layout.activity_answer1;
	}


	@OnClick(R.id.back)
	public void backClick(){
		finish();
	}


}
