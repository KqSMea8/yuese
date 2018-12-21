package com.net.yuesejiaoyou.redirect.ResolverA.uiface;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import android.view.Window;
import android.view.WindowManager;
import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.redirect.ResolverA.interface3.UsersThread_01066A;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Administrator on 2018/3/21.
 */

public class Vchat_splash extends Activity implements SurfaceHolder.Callback {

	private MediaPlayer player = new MediaPlayer();
	//private MediaPlayer player;
	private SurfaceView surfaceView;
	SharedPreferences sharedPreferences;
	String a1;
	String b1;
	Boolean user_first;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0){
			//LogDetect.send(LogDetect.DataType.basicType,"01162","防止重复启动");
			finish();
			return;
		}

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.vchat_splash);
		surfaceView = (SurfaceView) this.findViewById(R.id.surfaceView);
		sharedPreferences = getSharedPreferences("Acitivity",
				Context.MODE_PRIVATE);
		a1 = sharedPreferences.getString("username", "");
		b1 = sharedPreferences.getString("password", "");
		user_first = sharedPreferences.getBoolean("FIRST", true);

		if(!a1.equals("")&&!b1.equals("")){
			String[] paramsMap = {"1", a1,b1};
			UsersThread_01066A b = new UsersThread_01066A("login", paramsMap, handler);
			Thread thread = new Thread(b.runnable);
			thread.start();
		}


		surfaceView.getHolder().addCallback(this);
		player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mediaPlayer) {
				Log.v("TT", "player finished");
				//mediaPlayer.start();
				// 私有数据
				if (user_first) {
					Intent intent = new Intent(Vchat_splash.this, WelcomeGuideActivity.class);
					startActivity(intent);
					//LogDetect.send(LogDetect.DataType.basicType,"01162",Util.userid);
					player.stop();
					player.release();
					finish();
				}else  if(!a1.equals("")&&!b1.equals("")&&!user_first){
					Intent intent = new Intent(Vchat_splash.this, MainActivity.class);
					startActivity(intent);
					LogDetect.send(LogDetect.DataType.basicType,"01162", Util.userid);
					player.stop();
					player.release();
					finish();
				}
				else {
					Intent intent = new Intent();
					intent.setClass(Vchat_splash.this, LoginActivity.class);
					startActivity(intent);
					LogDetect.send(LogDetect.DataType.basicType,"01162",Util.userid);
					player.stop();
					player.release();
					finish();
				}

			}
		});


	}





	// ---------------------------------------
	// surfaceview回调
	@Override
	public void surfaceCreated(SurfaceHolder surfaceHolder) {
		try {
			Log.e("TT", "before play");
			play();
			Log.e("TT", "after play");
		} catch (IOException e) {
			Log.e("TT", "exception: " + e);
			e.printStackTrace();
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

	}

	private void play() throws IOException {
		player.reset();
		player.setAudioStreamType(AudioManager.STREAM_MUSIC);
		Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.qidongvideo);
		player.setDataSource(this, uri);
		// 把视频输出到SurfaceView上
		player.setDisplay(surfaceView.getHolder());
		player.prepare();
		player.start();
	}
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
				case 200:
					String json = (String) msg.obj;
					if (json.contains("id")) {
						try {
//					  id,nickname,photo,gender
							JSONArray jsonArray = new JSONArray(json);
							//JSONObject jsonObject = new JSONObject(json);
							JSONObject jsonObject = jsonArray.getJSONObject(0);
							String a = jsonObject.getString("id");

							if(!a.equals("")){
								String name = jsonObject.getString("nickname");
								String headpic = jsonObject.getString("photo");
								String gender = jsonObject.getString("gender");
								String zhubo = jsonObject.getString("is_v");
								SharedPreferences share = getSharedPreferences("Acitivity", Activity.MODE_PRIVATE);
								share.edit().putString("username",a1).commit();
								share.edit().putString("password", b1).commit();
								share.edit().putString("userid", a).commit();
								share.edit().putString("nickname", name).commit();
								share.edit().putString("headpic", headpic).commit();
								share.edit().putString("sex", gender).commit();
								share.edit().putString("zhubo_bk", zhubo).commit();
								a1="facebook";
								b1="facebook";

								/////////A区给B区的一对一通话，进行初始化实时通讯
								///////////////
								Util.userid = a;
								LogDetect.send(LogDetect.DataType.basicType,"01162----启动页用户id",a);
								Util.headpic = headpic;
								Util.nickname = name;
								Util.iszhubo = zhubo;
								Util.imManager = new com.net.yuesejiaoyou.redirect.ResolverB.interface4.im.IMManager();
								Util.imManager.initIMManager(jsonObject, getApplicationContext());
								///////////////////////////////////////////////////////
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					break;
			}

		}
	};


}
