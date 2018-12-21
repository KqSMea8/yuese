package com.net.yuesejiaoyou.redirect.ResolverA.uiface;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;


/**
 * Created by Administrator on 2018/4/24.
 */

public class Activity_web_01162 extends Activity  implements View.OnClickListener {

	 private WebView activity_web;
	 private ImageView fanhui;


	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web_01162);
		activity_web= (WebView) findViewById(R.id.activity_photo);
		fanhui= (ImageView) findViewById(R.id.fanhui);
		fanhui.setOnClickListener(this);
		Intent intent=getIntent();
		Bundle bundle=intent.getExtras();
		String a=	bundle.getString("photo_item");
		LogDetect.send(LogDetect.DataType.basicType,"01162--网址内容",a);
         activity_web.loadUrl(a);
		activity_web.getSettings().setJavaScriptEnabled(true);
		activity_web.getSettings().setUseWideViewPort(true);
		activity_web.getSettings().setLoadWithOverviewMode(true);
		activity_web.setWebViewClient(new WebViewClient(){
		   @Override
		   public boolean shouldOverrideUrlLoading(WebView view, String url) {
		   	view.loadUrl(url);
			   return super.shouldOverrideUrlLoading(view, url);
		   }
	   });
	}

	@Override
	public void onClick(View view) {
		int id=view.getId();
		switch (id){
			case R.id.fanhui:
				finish();
				break;



		}



	}
}
