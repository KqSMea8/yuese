package com.net.yuesejiaoyou.classroot.interface4.util;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;

import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.classroot.interface4.openfire.core.Utils;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.bean.Msg;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.bean.Session;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.db.ChatMsgDao;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.db.Const;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.db.SessionDao;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa1.smack.XMPPException;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa1.smack.XMPPTCPConnection;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack.Chat;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack.ChatManager;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack.SmackException;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.im.IMManager;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
	private static Util util;
	public static int flag = 0;
	public static String userid = "0";
	public static String yqcode = "";
	public static String channelcode = "";
	public static String city = "0";
	public static String vip = "0";
	public static double latitude = 0.0;
	public static double lontitude = 0.0;
	public static String nickname = "0";
	public static String headpic = "0";
	public static String iszhubo = "0";
	public static String is_chongzhi = "0";
	public static String bang = "0";
	public static String dongtai = "0";
	public static String invite_num = "0";
	public  static  String nationLocalCode=null;
	public static String is_agent = "0";
	public static IMManager imManager=null;
	public static String url="http://116.62.220.67:8090";
	public static String projectname = "mliao";
	public static String zhuboRoomId = "";
	public static String updatetest = "";
	public static String yuming = "";

	private Util(){
		
	}
	
	public static Util getInstance(){
		if(util == null){
			util = new Util();
		}
		return util;
	}



	/**
	 * 判断是否有sdcard
	 * @return
	 */
	public boolean hasSDCard(){
		boolean b = false;
		if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
			b = true;
		}
		return b;
	}
	
	/**
	 * 得到sdcard路径
	 * @return
	 */
	public String getExtPath(){
		String path = "";
		if(hasSDCard()){
			path = Environment.getExternalStorageDirectory().getPath();
		}
		return path;
	}
	
	/**
	 * 得到/data/data/yanbin.imagedownload目录
	 * @param mActivity
	 * @return
	 */
	public String getPackagePath(Activity mActivity){
		return mActivity.getFilesDir().toString();
	}

	/**
	 * 根据url得到图片名
	 * @param url
	 * @return
	 */
	public String getImageName(String url) {
		String imageName = "";
		if(url != null){
			imageName = url.substring(url.lastIndexOf("/") + 1);
		}
		return imageName;
	}

	public static void sendMsgText(String content,final String touser) {
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		content="『"+Util.nickname+"』 在 "+sd.format(new Date())+" 预约了您，请在预约列表中回拨！";

		final String message = content + Const.SPLIT + Const.MSG_TYPE_TEXT
				+ Const.SPLIT + sd.format(new Date())+ Const.SPLIT+"系统消息"+Const.SPLIT+Util.url+"/img/imgheadpic/launch_photo.png";
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					//LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"before sendMessage()");
					sendMessage(Utils.xmppConnection, message, touser);
				} catch (XMPPException | SmackException.NotConnectedException e) {
					e.printStackTrace();
					//LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"chatmanager: "+e.toString());
					Looper.prepare();
					// ToastUtil.showShortToast(ChatActivity.this, "发送失败");
					Looper.loop();
				}
			}
		}).start();
	}
	public static void sendMessage(XMPPTCPConnection mXMPPConnection, String content,
								   String touser) throws XMPPException, SmackException.NotConnectedException {
		if (mXMPPConnection == null || !mXMPPConnection.isConnected()) {
			// throw new XMPPException();
		}
		// ChatManager chatmanager = mXMPPConnection.getChatManager();
		// hatManager chatmanager = new ChatManager(mXMPPConnection);
		ChatManager chatmanager = com.net.yuesejiaoyou.classroot.interface4.openfire.core.Utils.xmppchatmanager;
		//LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"chatmanager: "+chatmanager);
		// Chat chat =chatmanager.createChat(touser + "@" + Const.XMPP_HOST,
		// null);
		Chat chat = chatmanager.createChat(touser + "@" + Const.XMPP_HOST, null);
		if (chat != null) {
			// chat.sendMessage(content);
			chat.sendMessage(content, 80+ "@" + Const.XMPP_HOST);
			Log.e("jj", "发送成功");
			//LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"send success");
		}else{
			//LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"send fail:chat is null");
		}
	}

	public static void sendMsgText1(Context context,String content, final String touser,final String nickname,final String headpic) {

		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		getChatInfoTo(context,sd,Const.MSG_TYPE_DAZHAOHU,touser);

		content=Util.nickname+"给你发来一条视频请求";

		final String message = content + Const.SPLIT + Const.MSG_TYPE_DAZHAOHU
				+ Const.SPLIT + sd.format(new Date())+ Const.SPLIT+Util.nickname+Const.SPLIT+Util.headpic;
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					//LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"before sendMessage()");
					sendMessage1(Utils.xmppConnection, message, touser);
				} catch (XMPPException | SmackException.NotConnectedException e) {
					e.printStackTrace();
					//LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"chatmanager: "+e.toString());
					Looper.prepare();
					// ToastUtil.showShortToast(ChatActivity.this, "发送失败");
					Looper.loop();
				}
			}
		}).start();

		updateSession1(Const.MSG_TYPE_DAZHAOHU,sd,  context,touser,nickname,headpic) ;

	}

	private static Msg getChatInfoTo(Context context,SimpleDateFormat sd, String msgtype,String you ) {
		String time = sd.format(new Date());
		Msg msg = new Msg();
		msg.setFromUser(you);
		msg.setToUser(Util.userid);
		msg.setType(msgtype);
		msg.setIsComing(1);
		msg.setContent("");
		msg.setDate(time);
		LogDetect.send(LogDetect.DataType.nonbasicType, "01160 time:", time);
		ChatMsgDao msgDao = new ChatMsgDao(context);
		msg.setMsgId(msgDao.insert(msg));

		return msg;
	}
	private static void  updateSession1(String type,SimpleDateFormat sd,Context context,String touser,String nickname,String headpic) {
		Session session = new Session();
		session.setFrom(touser);
		session.setTo(Util.userid);
		session.setNotReadCount("");// 未读消息数量
		session.setContent("[视频请求]");
		session.setTime(sd.format(new Date()));
		session.setType(type);
		session.setName(nickname);
		session.setHeadpic(headpic);
		SessionDao sessionDao = new SessionDao(context);
		if (sessionDao.isContent(touser, Util.userid)) {
			sessionDao.updateSession(session);
		} else {
			sessionDao.insertSession(session);
		}
		Intent intent = new Intent(Const.ACTION_ADDFRIEND);// 发送广播，通知消息界面更新
		context.sendBroadcast(intent);
	}


	public static void sendMessage1(XMPPTCPConnection mXMPPConnection, String content,
								   String touser) throws XMPPException, SmackException.NotConnectedException {
		if (mXMPPConnection == null || !mXMPPConnection.isConnected()) {
			// throw new XMPPException();
		}
		// ChatManager chatmanager = mXMPPConnection.getChatManager();
		// hatManager chatmanager = new ChatManager(mXMPPConnection);
		ChatManager chatmanager = com.net.yuesejiaoyou.classroot.interface4.openfire.core.Utils.xmppchatmanager;
		//LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"chatmanager: "+chatmanager);
		// Chat chat =chatmanager.createChat(touser + "@" + Const.XMPP_HOST,
		// null);
		Chat chat = chatmanager.createChat(touser + "@" + Const.XMPP_HOST, null);
		if (chat != null) {
			// chat.sendMessage(content);
			chat.sendMessage(content, Util.userid+ "@" + Const.XMPP_HOST);
			Log.e("jj", "发送成功");
			//LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"send success");
		}else{
			//LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"send fail:chat is null");
		}
	}



}
