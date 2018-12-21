package com.net.yuesejiaoyou.redirect.ResolverA.uiface;


import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.net.yuesejiaoyou.R;

import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.redirect.ResolverA.getset.Member_01152;
import com.nostra13.universalimageloader.core.DisplayImageOptions;


public class bengkui_01152 extends Activity implements OnClickListener {
	//自定义ID
	
	private ImageView back;
	private TextView tongdao;
	private Intent intent;

	private PopupWindow popupWindow;
	private ArrayList<Member_01152> list;
	DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
			.cacheOnDisc(true).bitmapConfig(Bitmap.Config.RGB_565).build();
	@Override
	/**
	 * 对页面进行初始化
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LogDetect.send(LogDetect.DataType.specialType, "help_center_01152:", "布局开始");
		setContentView(R.layout.bengkui_01152);
		LogDetect.send(LogDetect.DataType.specialType, "help_center_01152:", "开始=====");
		back = (ImageView) findViewById(R.id.back);
		back.setOnClickListener(this);
		
		tongdao = (TextView) findViewById(R.id.tongdao);
		tongdao.setOnClickListener(this);
		
		
		LogDetect.send(LogDetect.DataType.specialType, "help_center_01152:", "结束=====");		
	
	}
	

	//获得onclick点击事件
	@Override
	public void onClick(View v) {
		
		int id = v.getId();
		switch (id) {		
		case R.id.back: //返回页面
			this.finish();
			break;
			
		case R.id.tongdao: //权限通道
			showPopupspWindow_reservation(findViewById(R.id.scrollview));
			break;
		
			
		}
	}


	//预约
	@SuppressLint({ "RtlHardcoded", "NewApi" })
	public void showPopupspWindow_reservation(View parent) {
		// 加载布局
		LayoutInflater inflater = LayoutInflater.from(bengkui_01152.this);
		View layout = inflater.inflate(R.layout.pop_authority_01160, null);

		Button b1,b2,b3,b4,b5; TextView b6;

		b1 = (Button) layout.findViewById(R.id.b1);
		b2 = (Button) layout.findViewById(R.id.b2);
		b3 = (Button) layout.findViewById(R.id.b3);
		b4 = (Button) layout.findViewById(R.id.b4);
		b5 = (Button) layout.findViewById(R.id.b5);
		b6 = (TextView) layout.findViewById(R.id.b6);

		b1.setOnClickListener(new btnListener(b1));
		b2.setOnClickListener(new btnListener(b2));
		b3.setOnClickListener(new btnListener(b3));
		b4.setOnClickListener(new btnListener(b4));
		b5.setOnClickListener(new btnListener(b5));
		b6.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				popupWindow.dismiss();
			}
		});

		WindowManager m = getWindowManager();
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		//int height = (int)(metrics.heightPixels*0.8);
		int width  = (int)(metrics.widthPixels*0.7);

		popupWindow = new PopupWindow(layout,width,
				ViewGroup.LayoutParams.WRAP_CONTENT, true);
		// 控制键盘是否可以获得焦点

		popupWindow.setFocusable(true);
		WindowManager.LayoutParams lp = bengkui_01152.this.getWindow().getAttributes();
		lp.alpha = 0.5f;
		bengkui_01152.this.getWindow().setAttributes(lp);
		popupWindow.setBackgroundDrawable(new BitmapDrawable(null, ""));
		//popupWindow.showAsDropDown(parent, 0, 0,Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL);
		popupWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);
		popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
			public void onDismiss() {
				WindowManager.LayoutParams lp = bengkui_01152.this.getWindow().getAttributes();
				lp.alpha = 1f;
				bengkui_01152.this.getWindow().setAttributes(lp);
			}
		});
	}


/*
		* 创建一个按钮监听器类, 作用是点击按钮后改变按钮的名字
*/
	class btnListener implements OnClickListener
	{
		//定义一个 Button 类型的变量
		private Button btn;
		/*
        * 一个构造方法, 将Button对象做为参数
        */
		private btnListener(Button btn)
		{
			this.btn = btn;//将引用变量传递给实体变量
		}
		public void onClick(View v)
		{
			Intent intent = new Intent();
			intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
			Uri uri = Uri.fromParts("package", getApplication().getPackageName(), null);
			intent.setData(uri);
			startActivity(intent);
		}
	}


}
