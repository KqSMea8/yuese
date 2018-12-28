package com.net.yuesejiaoyou.redirect.ResolverD.interface4.activity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.redirect.ResolverA.getset.Member_01152;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.BaseActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.ArrayList;

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
