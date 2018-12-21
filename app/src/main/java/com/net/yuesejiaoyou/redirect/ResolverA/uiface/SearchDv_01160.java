package com.net.yuesejiaoyou.redirect.ResolverA.uiface;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.redirect.ResolverA.getset.ZBYuyueJB_01160;
import com.net.yuesejiaoyou.redirect.ResolverA.interface3.UsersThread_01160A;
import com.net.yuesejiaoyou.redirect.ResolverA.interface4.SearchDvAdapter_01160;


import java.util.ArrayList;
import java.util.List;




/**
 * 聊天界面
 * 
 * @author 白玉梁
 * @blog http://blog.csdn.net/baiyuliang2013
 * @weibo http://weibo.com/274433520
 * 
 * */
@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("SimpleDateFormat")
public class SearchDv_01160 extends Activity implements OnClickListener {
	private EditText input;
	private TextView cancel;
	private SearchDvAdapter_01160 adapter_01160;
	private List<ZBYuyueJB_01160> list = new ArrayList<ZBYuyueJB_01160>();
	private RecyclerView rv;
	private  String key;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//全屏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
				WindowManager.LayoutParams. FLAG_FULLSCREEN);
		setContentView(R.layout.search_dv_01160);
		input = (EditText) findViewById(R.id.input);
		input.addTextChangedListener(watcher);
		cancel = (TextView) findViewById(R.id.quxiao);
		cancel.setOnClickListener(this);
		rv = (RecyclerView) findViewById(R.id.rv);
		RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
		rv.setLayoutManager(layoutManager);


	}




	@SuppressLint("HandlerLeak")
	private Handler requestHandler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 208:
					list = (ArrayList<ZBYuyueJB_01160>) msg.obj;
					LogDetect.send(LogDetect.DataType.specialType, "查询用户名： ",list.toString());

					if (list.size() != 0) {

						adapter_01160 = new SearchDvAdapter_01160(
								SearchDv_01160.this, rv, list,
								requestHandler,key);
						rv.setAdapter(adapter_01160);
						adapter_01160.setOnItemClickLitsener(new SearchDvAdapter_01160.onItemClickListener() {
							@Override
							public void onItemClick(View view, int position) {
								Intent intent = new Intent(SearchDv_01160.this,Userinfo.class);
								intent.putExtra("id",list.get(position).getUser_id());
								startActivity(intent);
							}
						});
					}
					break;
			}
		}
	};
	private TextWatcher watcher = new TextWatcher(){
		@Override
		public void afterTextChanged(Editable s) {
		if(TextUtils.isEmpty(s)){

		}else {
			key = s.toString();
			String mode = "search_dv";
			String[] paramsMap = {Util.userid, s.toString().trim()};
			UsersThread_01160A b = new UsersThread_01160A(mode, paramsMap, requestHandler);
			Thread t = new Thread(b.runnable);
			t.start();
			}
		}
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
									  int after) {

		}
		@Override
		public void onTextChanged(CharSequence s, int start, int before,
								  int count) {



		}
	};

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.quxiao){
			finish();
		}
	}
}