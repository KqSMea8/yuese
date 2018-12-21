package com.net.yuesejiaoyou.redirect.ResolverB.uiface;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect.DataType;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.classroot.interface4.openfire.core.Utils;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.db.Const;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa1.smack.XMPPException;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa1.smack.XMPPTCPConnection;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack.Chat;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack.ChatManager;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack.SmackException.NotConnectedException;
import com.net.yuesejiaoyou.redirect.ResolverB.getset.Member_01160;
import com.net.yuesejiaoyou.redirect.ResolverB.interface3.UsersThread_01160B;

@SuppressLint("SimpleDateFormat")
public class MassChat_01160 extends Activity  implements OnClickListener{
    
	private View mBaseView;
	private Context mContext;
	private String I,username, headpicture;
	private String msgBody;
    private RelativeLayout button_more_moremodify;
    private EditText input;
    private SimpleDateFormat sd;
    private TextView zhishu,queding;
    private String yonghu_id;
	private List<Member_01160> list = new ArrayList<Member_01160>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mass_chat_01160);
		init();

		SharedPreferences sharedPreferences = getSharedPreferences("Acitivity", Context.MODE_PRIVATE); //私有数据
		I = sharedPreferences.getString("userid","");
		username = sharedPreferences.getString("nickname","");
		headpicture= sharedPreferences.getString("headpic","");
		sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		initData();

	}

	private void initData() {
		String mode = "masschat";
		String[] paramsMap = {Util.userid};
		UsersThread_01160B t = new UsersThread_01160B(mode,paramsMap,requestHandler);
		Thread b = new Thread(t.runnable);
		b.start();
	}


	private void init() {
		button_more_moremodify = (RelativeLayout)findViewById(R.id.button_more_moremodify);
		button_more_moremodify.setOnClickListener(this);

		input = (EditText)findViewById(R.id.input);
		zhishu = (TextView)findViewById(R.id.zhishu);
		
		queding = (TextView)findViewById(R.id.queding);
		queding.setOnClickListener(this);
	
		set_type();
	
	}



	private void set_type() {
		//Typeface typeFace =Typeface.createFromAsset(getAssets(),"fonts/arialbd.ttf");

		//zhishu.setTypeface(typeFace);
		//queding.setTypeface(typeFace);
		//input.setTypeface(typeFace);
		
		input.addTextChangedListener(mTextWatcher);
	}



	private Handler requestHandler = new Handler(new Handler.Callback() {
		  @SuppressLint("NewApi")
		@Override
		  public boolean handleMessage(Message msg) {
		    switch (msg.what) {
		    case 205:
		    	String json = (String)msg.obj;
				LogDetect.send(DataType.specialType, "01162--我的粉丝们", json);
				if(json != null) {
					try {
						JSONArray jsonArray = new JSONArray(json);
						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject item = jsonArray.getJSONObject(i);
							Member_01160 bean = new Member_01160();
							bean.setUsername(item.getString("user_id"));
							list.add(bean);
						}
					} catch (Exception e) {
						e.printStackTrace();
						LogDetect.send(DataType.specialType, "this0", e);
					}
				}else if(json == null){
					Toast.makeText(MassChat_01160.this, "您没有粉丝", Toast.LENGTH_SHORT).show();
				}else if(json.isEmpty()){
					Toast.makeText(MassChat_01160.this, "您没有粉丝", Toast.LENGTH_SHORT).show();
				}
		    	break;
		    case 20:
				LogDetect.send(DataType.exceptionType, "01160",yonghu_id);
				if(list != null && list.size() != 0){
					//String[] ids = yonghu_id.split(",");
					int size = list.size();
					String content = input.getText().toString();
					for(int i = 0 ;i< size;i++){

						sendMsgText(content,list.get(i).getUsername());
					}
					LogDetect.send(DataType.exceptionType, "01160",input.getText().toString());
					Toast.makeText(MassChat_01160.this, "发送成功", Toast.LENGTH_SHORT).show();
					finish();
				}else{
					Toast.makeText(MassChat_01160.this, "您没有粉丝", Toast.LENGTH_SHORT).show();
				}

				break;
		    }
			return false;
		  }
	});


	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.button_more_moremodify:
			finish();
			break;
		case R.id.queding:
			if (TextUtils.isEmpty(input.getText().toString())) {
				Toast.makeText(MassChat_01160.this, "请输入内容", Toast.LENGTH_SHORT).show();
				return;
			}else{
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						requestHandler.sendMessage(requestHandler.obtainMessage(20));
					}
				}).start();;

			}
		
			break;
		}
		
	}

	/**
	 * 执行发送消息 文本类型
	 * 
	 * @param content
	 */
	void sendMsgText(String content,final String ids) {
	/*	
		input.setText("");*/
		final String message = content + Const.SPLIT + Const.MSG_TYPE_TEXT
				+ Const.SPLIT + sd.format(new Date())+ Const.SPLIT+username+Const.SPLIT+headpicture;
		//LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"message: "+message);
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					//LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"before sendMessage()");
					sendMessage(Utils.xmppConnection, message, ids);
				} catch (XMPPException | NotConnectedException e) {
					e.printStackTrace();
					//LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"chatmanager: "+e.toString());
					Looper.prepare();
					// ToastUtil.showShortToast(ChatActivity.this, "发送失败");
					Looper.loop();
				}
			}
		}).start();
		Intent intent = new Intent(Const.ACTION_ADDFRIEND);// 发送广播，通知消息界面更新
		sendBroadcast(intent);
	}
	/**
	 * 发送消息
	 * 

	 * @param content
	 * @param touser
	 * @throws XMPPException
	 * @throws NotConnectedException
	 */
	public void sendMessage(XMPPTCPConnection mXMPPConnection, String content,
			String touser) throws XMPPException, NotConnectedException {
		if (mXMPPConnection == null || !mXMPPConnection.isConnected()) {
			// throw new XMPPException();
		}
		// ChatManager chatmanager = mXMPPConnection.getChatManager();
		// hatManager chatmanager = new ChatManager(mXMPPConnection);
		ChatManager chatmanager = Utils.xmppchatmanager;
		//LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"chatmanager: "+chatmanager);
		// Chat chat =chatmanager.createChat(touser + "@" + Const.XMPP_HOST,
		// null);
		Chat chat = chatmanager.createChat(touser + "@" + Const.XMPP_HOST, null);
		if (chat != null) {
			// chat.sendMessage(content);
			chat.sendMessage(content, I + "@" + Const.XMPP_HOST);
			Log.e("jj", "发送成功");
			//LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"send success");
		}else{
			//LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"send fail:chat is null");
		}
	}
	
	TextWatcher mTextWatcher = new TextWatcher() {
	    private CharSequence temp;
	    private int editStart ;
	    private int editEnd ;
	    @Override
	    public void beforeTextChanged(CharSequence s, int arg1, int arg2,
	        int arg3) {
	      temp = s;
	    }
	     
	    @Override
	    public void onTextChanged(CharSequence s, int arg1, int arg2,
	        int arg3) {
	    	zhishu.setText(temp.length()+"/140");
	    }
	     
	    @Override
	    public void afterTextChanged(Editable s) {
	      editStart = input.getSelectionStart();
	      editEnd = input.getSelectionEnd();
	      if (temp.length() > 140) {
	        Toast.makeText(MassChat_01160.this,
	            "字数超过最大限制", Toast.LENGTH_SHORT)
	            .show();
	        s.delete(editStart-1, editEnd);
	        int tempSelection = editStart;
	        input.setText(s);
	        input.setSelection(tempSelection);
	      }
	    }

	  };
	
}
