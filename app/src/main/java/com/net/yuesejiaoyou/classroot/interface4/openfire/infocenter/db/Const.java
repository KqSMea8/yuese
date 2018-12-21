package com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.db;

public class Const {
	
	//public static final String XMPP_HOST = "192.168.0.3";
	//public static final String XMPP_HOST = "47.105.33.230";
	public static final String XMPP_HOST = "116.62.220.67";		//"120.27.98.128";
	public static final int XMPP_PORT = 9100;	//5450;
	
	
	/**
	 * 登录状态广播
	 */
	public static final String ACTION_IS_LOGIN_SUCCESS = "com.android.qq.is_login_success";
	/**
	 * 消息记录操作广播
	 */
	public static final String ACTION_MSG_OPER= "com.android.qq.msgoper";


	public static final String ACTION_MSG_PAY= "com.android.gk.pay";
	public static final String ACTION_MSG_ONEVIDEO= "com.android.one.video";
	public static final String ACTION_MSG_ONECHAT= "com.android.one.chat";
	public static final String ACTION_MSG_ONEINVITE= "com.android.one.invite";
	public static final String ACTION_MSG_ZB_RESERVE = "com.android.zhubo_bk.yuyue";

	public static final String ACTION_SYSTEM_NOTICE= "com.android.action.system.notice";
	/**
	 * 新动态广播
	 */
	public static final String ACTION_DYNAMIC= "com.android.one.dynamic";

	/**
	 * 对方在输入广播
	 */
	public static final String ACTION_INPUT= "com.android.one.input";
	
	/**
	 * 添加好友请求广播
	 */
	public static final String ACTION_ADDFRIEND= "com.android.qq.addfriend";
	
	//静态地图API
	public static  final String LOCATION_URL_S = "http://api.map.baidu.com/staticimage?width=320&height=240&zoom=17&center=";
	public static  final String LOCATION_URL_L = "http://api.map.baidu.com/staticimage?width=480&height=800&zoom=17&center=";
	public static final String MSG_TYPE_URL="msg_type_goodsInformation";//文本消息
	public static final String MSG_TYPE_TEXT="chat_text";//文本消息
	public static final String MSG_TYPE_DYN="dyn_rel";//文本消息
	public static final String MSG_TYPE_DAZHAOHU="msg_type_dazhaohu";//文本消息


	public static final String MSG_TYPE_IMG="msg_type_img";//图片
	public static final String MSG_TYPE_VOICE="msg_type_voice";//语音
	public static final String MSG_TYPE_LOCATION="msg_type_location";//位置
	public static final String MSG_TYPE_VIDEO="msg_type_video";	//视频
	public static final String REWARD_ANCHOR="reward_anchor";	//视频
	
	public static final String MSG_TYPE_ADD_FRIEND="msg_type_add_friend";//添加好友
	public static final String MSG_TYPE_ADD_FRIEND_SUCCESS="msg_type_add_friend_success";//同意添加好友
	
	public static final String SPLIT="卍";
	
	public static final int NOTIFY_ID=0x90;
	public static final String MSG_TYPE_ORDER="chat_order";
	public static final String MSG_TYPE_SYSTEM="chat_systemInfo";

}
