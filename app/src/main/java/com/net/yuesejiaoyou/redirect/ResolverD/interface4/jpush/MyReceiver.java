package com.net.yuesejiaoyou.redirect.ResolverD.interface4.jpush;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.text.TextUtils;
import android.util.Log;

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.YhApplicationA;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.activity.LoginActivity;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.agora.P2PVideoConst;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.agora.VideoMessageManager;
import com.net.yuesejiaoyou.activity.GukeActivity;
import com.net.yuesejiaoyou.bean.ZhuboInfo;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.agora.zhubo.GukeInfo;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.agora.zhubo.ZhuboActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import cn.jpush.android.api.JPushInterface;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.Context.ACTIVITY_SERVICE;
import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * 自定义接收器
 * 
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
	private static final String TAG = "JIGUANG-Example";

	@Override
	public void onReceive(Context context, Intent intent) {
		LogDetect.send("01205", "[MyReceiver] onReceive");
		try {
			Bundle bundle = intent.getExtras();
			Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

			if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
				String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
				Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
				//send the Registration Id to your server...

			} else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
				Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
				//Toast.makeText(context, "[MyReceiver] 接收到推送下来的自定义消息", Toast.LENGTH_LONG);
				LogDetect.send("01205", "[MyReceiver] 接收到推送下来的自定义消息");
			} else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
				Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
				int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
				Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

				String json = (String)bundle.get(JPushInterface.EXTRA_EXTRA);
				net.sf.json.JSONObject jsonObj = net.sf.json.JSONObject.fromObject(json);

				if(jsonObj.has("tag")) {

					String fromId = jsonObj.getString("fromid");
					String fromNickname = jsonObj.getString("fromnickname");
					String fromHeadpic = jsonObj.getString("fromheadpic");
					String roomid = jsonObj.getString("roomid");
					String command = jsonObj.getString("command");
					String yuyue = P2PVideoConst.NONE_YUYUE;
					if(jsonObj.has("yuyue")) {
						yuyue = jsonObj.getString("yuyue");
					}
					LogDetect.send(LogDetect.DataType.basicType, "01107", "******************** 接收到一对一视频命令 ********************");
					LogDetect.send(LogDetect.DataType.basicType, "01107", "userid="+fromId+",usernam="+fromNickname+",headpic="+fromHeadpic+",roomid="+roomid+",cmd="+command);

					final YhApplicationA application = (YhApplicationA) context.getApplicationContext();

					final Activity curActivity = application.getCurrentActivity();

					// 如果是拨打一对一视频
					if(VideoMessageManager.VIDEO_U2A_USER_CALL.equals(command) || VideoMessageManager.VIDEO_A2U_ANCHOR_CALL.equals(command)) {
						// 点亮屏幕
						PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
						if (!pm.isScreenOn()) {
							//点亮屏幕
							PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "bright");
							wl.acquire();
							wl.release();
						}


						//打开自定义的Activity
						if(curActivity == null) {
							//Log.v("TT","LoginActivity-打开自定义的activity");
//                    Intent i = new Intent(context, LoginActivity.class);
//                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    context.startActivity(i);
							//LogDetect.send(LogDetect.DataType.basicType, "01107", "App已死，重新打开app");
							LogDetect.send("01107", "App已死，重新打开app");
						} else {
							LogDetect.send("01107", "呼到前台，curActivity: " + curActivity.getClass().getName());
							//Log.v("TT","呼到前台: "+curActivity.getClass().getSimpleName());
							ActivityManager tasksManager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
							tasksManager.moveTaskToFront(curActivity.getTaskId(), ActivityManager.MOVE_TASK_WITH_HOME);
						}
					}

					Log.v(TAG,"command="+command);
					switch(command) {
						case VideoMessageManager.VIDEO_U2A_USER_CALL:
							ZhuboActivity.callFromGuke(curActivity, new GukeInfo(fromId, fromNickname, fromHeadpic, roomid, P2PVideoConst.GUKE_CALL_ZHUBO, yuyue));
							break;
						case VideoMessageManager.VIDEO_A2U_ANCHOR_CALL:
							GukeActivity.callFromZhubo(curActivity, new ZhuboInfo(fromId, fromNickname, fromHeadpic, roomid, P2PVideoConst.ZHUBO_CALL_GUKE, yuyue));
							break;
						case "200":
							Log.v(TAG,"推送200");
							Context appContext = context.getApplicationContext();
							if(isAppOnForeground(appContext) == false) {
								Log.v(TAG,"APP后台");
								NotificationManager manager = (NotificationManager) appContext.getSystemService(NOTIFICATION_SERVICE);
								//创建通知建设类
								Notification.Builder builder = new Notification.Builder(appContext);
								//设置跳转的页面
								Class targetActivity = curActivity != null ? curActivity.getClass() : LoginActivity.class;

								PendingIntent intent2 = PendingIntent.getActivity(appContext,
										100, new Intent(appContext, targetActivity),
										PendingIntent.FLAG_CANCEL_CURRENT);

								//设置通知栏标题
								builder.setContentTitle("新消息");
								//设置通知栏内容
								builder.setContentText("来自 " + fromNickname + " 的消息");
								//设置跳转
								builder.setContentIntent(intent2);
								//设置图标
								builder.setSmallIcon(R.mipmap.ic_launcher);
								//设置
								builder.setDefaults(Notification.DEFAULT_ALL);
								builder.setAutoCancel(true);
								//创建通知类
								Notification notification = builder.build();
								//显示在通知栏
								manager.notify((int) System.currentTimeMillis(), notification);
							} else {
								Log.v(TAG,"APP前台");
							}
							break;
						default:
							VideoMessageManager.CmdMsgListener listener = VideoMessageManager.getCmdMessageListener();
							if(listener != null) {
								listener.onCmdMessageListener(fromId, fromNickname, fromHeadpic, command, roomid);
							}
							break;
					}
				} else {

					String msgID = bundle.getString("MSG_K_STATE_DETECTE", "");
					LogDetect.send("01205", "[MyReceiver] msgID :  " + msgID);
					int msgValue = -1;
					try {
						msgValue = Integer.parseInt(msgID);
						LogDetect.send("01205", "[MyReceiver] msgValue:  " + msgValue);
					} catch (NumberFormatException e) {
						e.printStackTrace();
						LogDetect.send("01205", "[MyReceiver] 转换失败  " + msgID);
					}
					// 服务器定义的
					if (true) {
						LogDetect.send("01205", "[MyReceiver] 接收到推送下来的通知");
						SendWithOKHttp(Util.userid);
					} else {
						LogDetect.send("01205", "[MyReceiver] 错误的消息值！");
					}
				}

				// Toast.makeText(context, "[MyReceiver] 接收到推送下来的通知", Toast.LENGTH_LONG);
				//jumpActivity(context, bundle.getString(JPushInterface.EXTRA_ALERT));
			} else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
				Log.d(TAG, "[MyReceiver] 用户点击打开了通知");

				Log.v("TT","test: "+intent.getStringExtra("test"));

				Log.v("TT","userid: "+ Util.userid);

				//Log.v("TT", context.getApplicationContext().getClass().getName());
				YhApplicationA application = (YhApplicationA) context.getApplicationContext();
				Activity curActivity = application.getCurrentActivity();
				Log.v("TT","simpleName: "+application.getCurrentActivity());
				//Log.v("TT","simpleName: "+application.getCurrentActivity().getClass().getSimpleName());

					//打开自定义的Activity
				if(curActivity == null) {
					Intent i = new Intent(context, LoginActivity.class);
					i.putExtras(bundle);
					//i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
					context.startActivity(i);
				} else {
//					Intent start = new Intent(application,curActivity.getClass());
//					start.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//					context.startActivity(start);
					ActivityManager tasksManager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
					tasksManager.moveTaskToFront(curActivity.getTaskId(), ActivityManager.MOVE_TASK_WITH_HOME);
				}

			} else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
				Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
				//在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

			} else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
				boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
				Log.w(TAG, "[MyReceiver]" + intent.getAction() +" connected state change to "+connected);
			} else {
				Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
			}
		} catch (Exception e){

		}

	}

	public static boolean isAppOnForeground(Context context) {
		ActivityManager activityManager = (ActivityManager) context.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
		String packageName = context.getApplicationContext().getPackageName();
		List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
				.getRunningAppProcesses();
		if (appProcesses == null) {
			return false;
		}

		for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
			// The name of the process that this object is associated with.
			if (appProcess.processName.equals(packageName)
					&& appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
				return true;
			}
		}

		return false;
	}

	// 打印所有的 intent extra 数据
	private static String printBundle(Bundle bundle) {
		StringBuilder sb = new StringBuilder();
		for (String key : bundle.keySet()) {
			if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
			}else if(key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)){
				sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
			} else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
				if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
					Log.i(TAG, "This message has no Extra data");
					continue;
				}

				try {
					JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
					Iterator<String> it =  json.keys();

					while (it.hasNext()) {
						String myKey = it.next();
						sb.append("\nkey:" + key + ", value: [" +
								myKey + " - " +json.optString(myKey) + "]");
					}
				} catch (JSONException e) {
					Log.e(TAG, "Get message extra JSON error!");
				}

			} else {
				sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
			}
		}
		return sb.toString();
	}



	public void SendWithOKHttp(final String self)
	{
		new Thread(new Runnable() {
			@Override
			public void run() {
				OkHttpClient client = new OkHttpClient();
				Request request = new Request.Builder().get().url(/*Util.url+*/"http://116.62.220.67:8090/uiface/memberB?p0=A-user-search&p1=delayidle&p2="+ self).build();

				try {
					Response response = client.newCall(request).execute();
					int code = response.code();
					if(code == 200) {
						Log.d("HTTP response", "response OK!");
						LogDetect.send("01205", " HTTP response   OK!");
						String jsonStr = response.body().string();
					} else {
						LogDetect.send("01205", " HTTP response   FAILED!");
						// LogDetect.send("01107", "请求一对一视频记录网络请求失败");
					}
				} catch (IOException e) {
					e.printStackTrace();
					// LogDetect.send("01107", "网络请求IOException: "+e);
				}
			}
		}).start();
	}
}
