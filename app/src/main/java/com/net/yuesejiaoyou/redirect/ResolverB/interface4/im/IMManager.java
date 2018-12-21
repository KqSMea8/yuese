package com.net.yuesejiaoyou.redirect.ResolverB.interface4.im;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Looper;
import android.util.Log;

import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.classroot.interface4.openfire.core.Utils;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.bean.Msg;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.bean.Session;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.db.ChatMsgDao;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.db.Const;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.db.SessionDao;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa1.smack.ConnectionConfiguration;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa1.smack.ConnectionListener;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa1.smack.XMPPException;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa1.smack.XMPPTCPConnection;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack.Chat;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack.ChatManager;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack.ChatManagerListener;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack.Message;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack.MessageListener;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack.SmackException;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack.XMPPConnection;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack.SmackException.NotConnectedException;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.listener.managerlistener;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
//import com.net.yuesejiaoyou.redirect.ResolverB.interface4.tencent.liveroom.LiveRoom;

public class IMManager {

	private static IMManager instance;

	private XMPPTCPConnection connection;
	private Context mContext;
	private ChatMsgDao msgDao;
	private SessionDao sessionDao;
	private String I, YOU,name,logo,headpicture,username;// 为了好区分，I就是自己，YOU就是对方
	private SimpleDateFormat sd;
	private List<Msg> listMsg;
	private boolean connected = false;
	private boolean isKickoff;	// true：被踢下线	false：没有被踢下线
	private boolean isResponse = false;
	private boolean checkConnection = false;
	private Object lock = new Object();

	public IMManager(){
		instance = this;
	}

	public static IMManager getInstance() {
		return instance;
	}

	public void resetIMManager(String id,String name,String headpic, final Context ctxt) {
		mContext = ctxt;
		xmppConnect(id, name, headpic);
	}

	public void initIMManager(JSONObject jsonObj, final Context ctxt) {
		mContext = ctxt;
		if(jsonObj.has("id")) {
			
			try {
				final String id = jsonObj.getString("id");
				//String name = jsonObj.getString("name");
				final String name = jsonObj.has("nickname")?jsonObj.getString("nickname"):"对方";	//名称不存在，使用默认名称
				String photo = jsonObj.has("photo")?jsonObj.getString("photo"):null;
				final String headpic = (photo==null || photo.isEmpty())?"default.png":photo;	// 头像不存在，使用默认头像
				
				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						// 如果是主播则开启直播间
//						if(!"0".equals(Util.iszhubo)) {
//							LiveRoom.zhuboCreateRoom(ctxt.getApplicationContext(), Util.projectname+Util.userid);    // 主播登录openfire后同时创建直播房间,房间名称是“项目名+userid”
//						}

						xmppConnect(id, name, headpic);
					}
					
				}).start();
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			msgDao = new ChatMsgDao(mContext);
			sessionDao = new SessionDao(mContext);
		}
	}
	private Msg getChatInfoTo(String message, String msgtype) {
		String time = sd.format(new Date());
		Msg msg = new Msg();
		msg.setFromUser(YOU);
		msg.setToUser(I);
		msg.setType(msgtype);
		msg.setIsComing(1);
		msg.setContent(message);
		msg.setDate(time);
		return msg;
	}
	void updateSession1(String type, String content,String name,String logo) {
		Session session = new Session();
		session.setFrom(YOU);
		session.setTo(I);
		session.setNotReadCount("");// 未读消息数量
		session.setContent(content);
		session.setTime(sd.format(new Date()));
		session.setType(type);
		session.setName(name);
		session.setHeadpic(logo);
		
		if (sessionDao.isContent(YOU, I)) {
			sessionDao.updateSession(session);
		} else {
			sessionDao.insertSession(session);
		}
		Intent intent = new Intent(Const.ACTION_ADDFRIEND);// 发送广播，通知消息界面更新
		mContext.sendBroadcast(intent);
	}
	public void sendMsgText(String id, String name, String youHeadpic, String content) {
		
		if(connected == false) {
			Log.v("PAOPAO","尚未连接");
			return;
		}
		YOU = id;
		logo = youHeadpic;
		Msg msg = getChatInfoTo(content, Const.MSG_TYPE_TEXT);
		msg.setMsgId(msgDao.insert(msg));

		final String message = content + Const.SPLIT + Const.MSG_TYPE_TEXT
				+ Const.SPLIT + sd.format(new Date())+ Const.SPLIT+username+Const.SPLIT+headpicture;
		//LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"message: "+message);
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					//LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"before sendMessage()");
					sendMessage(Utils.xmppConnection, message, YOU);
				} catch ( NotConnectedException e) {
					e.printStackTrace();
					//LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"chatmanager: "+e.toString());
					Looper.prepare();
					// ToastUtil.showShortToast(ChatActivity.this, "发送失败");
					Looper.loop();
				}
			}
		}).start();
		updateSession1(Const.MSG_TYPE_TEXT, content,name,logo);
	}
	
	public void sendMessage(XMPPTCPConnection mXMPPConnection, String content,
			String touser) throws NotConnectedException {
		if (mXMPPConnection == null || !mXMPPConnection.isConnected()) {
			// throw new XMPPException();
		}
		// ChatManager chatmanager = mXMPPConnection.getChatManager();
		// hatManager chatmanager = new ChatManager(mXMPPConnection);
		ChatManager chatmanager = Utils.xmppchatmanager;
		//LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"chatmanager: "+chatmanager);
		// Chat chat =chatmanager.createChat(touser + "@" + Const.XMPP_HOST,
		// null);
		Chat chat = chatmanager.createChat(YOU + "@" + Const.XMPP_HOST, null);
		if (chat != null) {
			// chat.sendMessage(content);
			chat.sendMessage(content, I + "@" + Const.XMPP_HOST);
			Log.e("jj", "发送成功");
			//LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"send success");
		}else{
			//LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"send fail:chat is null");
		}
	}

	public Chat getChat(String userid) {
//		Chat chat = Utils.xmppchatmanager.getUserChat(userid+"@"+Const.XMPP_HOST);
//		LogDetect.send("01107","IMManager-getChat(): "+userid+","+chat);
//		if(chat == null) {
//			chat = Utils.xmppchatmanager.createChat(userid+"@"+Const.XMPP_HOST, null);
//			LogDetect.send("01107","IMManager-getChat() after create: "+userid+","+chat);
//		}

		return Utils.xmppchatmanager.createChat(userid+"@"+Const.XMPP_HOST, null);
	}
	
	private void xmppConnect(final String id, final String name, final String headpic) {

		setKickoff(false);
		//配置文件
		ConnectionConfiguration connectionConfig = new ConnectionConfiguration(
				Const.XMPP_HOST, Const.XMPP_PORT);
		//LogDetect.send(DataType.basicType,Utils.seller_id+"=phone="+Utils.android,"ConnectionConfigration: "+Const.XMPP_HOST+",5222");
		connection = new XMPPTCPConnection(connectionConfig);
		
		connection.addConnectionListener(new ConnectionListener() {

			@Override
			public void connected(XMPPConnection connection) {
				// TODO Auto-generated method stub
				
				//SharedPreferences share=getSharedPreferences("Acitivity",Activity.MODE_PRIVATE);
				//String str=share.getString("shop_id","");
				//LogDetect.send(Utils.user_id+"=phone="+Utils.android,"str: "+str);
				ChatManager chatmanager = new ChatManager((XMPPTCPConnection)connection);
				//if(!seller_id.equals("0")){
					Chat chat = chatmanager.createChat("000000"+"@" + Const.XMPP_HOST, null);
					
					try {
						chat.sendMessage("phoneime",id+"@" + Const.XMPP_HOST);
						
						SharedPreferences sharedPreferences = mContext.getSharedPreferences("Acitivity", Context.MODE_PRIVATE);
						sharedPreferences.edit().putString("id", id).commit();
						sharedPreferences.edit().putString("name", name).commit();
						sharedPreferences.edit().putString("headpic", headpic).commit();
					} catch (NotConnectedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"after login");
				//}
					
					//-----------------
					// 登陆成功后的初始化
					I = id;
					
					username = name;
					headpicture= headpic;
					connected = true;
			}

			@Override
			public void authenticated(XMPPConnection connection) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void connectionClosed() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void connectionClosedOnError(Exception e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void reconnectingIn(int seconds) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void reconnectionSuccessful() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void reconnectionFailed(Exception e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		try {
			//连接
			//connection.connectUsingConfiguration(connectionConfig);
			Log.v("PAOPAO","before connect");
			connection.connect();
			Log.v("PAOPAO","after connect");
			
			//Utils.xmppConnection=connection;
			// 初始化聊天管理
			ChatManager chatmanager = new ChatManager(connection);
			//LogDetect.send(DataType.nonbasicType,Utils.seller_id+"=phone="+Utils.android,chatmanager);
			Utils.xmppchatmanager=chatmanager;
			// 监听
			chatmanager.addChatListener(new ChatManagerListener() {
				@Override
				public void chatCreated(Chat arg0, boolean arg1) {
					arg0.addMessageListener(new managerlistener(mContext));
				}
			});
//			chatmanager.addChatListener(new ChatManagerListener() {
//				@Override
//				public void chatCreated(Chat chat, boolean arg1) {
//					//arg0.addMessageListener(new managerlistener(MsgService.this));
//					chat.addMessageListener(new MessageListener() {
//
//						@Override
//						public void processMessage(Chat chat, Message message) {
//							// TODO Auto-generated method stub
//							//Toast.makeText(MainActivity.this, message.getBody(), Toast.LENGTH_SHORT).show();
//							Log.v("PAOPAO","收到消息"+message.getBody());
//						}
//						
//					});
//				}
//			});
			//partChat = chatmanager.createChat("user@"+Const.XMPP_HOST,null);
		} catch (IOException | SmackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

	public void xmppDisconnect() {
		if(connection != null) {
			try {
//				LiveRoom.zhuboLogout();
				connection.disconnect();
			} catch (NotConnectedException e) {
				e.printStackTrace();
			}
		}
	}

	public void checkConnection() {
		if(connection != null) {
			connection.checkConnection();
		}
	}

	/**
	 * 更新心跳包计数
	 */
	public void updateOpenfireHeartbeat() {
		Log.v("TT","++updateOpenfireHeartbeat()");
		SharedPreferences sharepre = mContext.getSharedPreferences("Acitivity",
				Context.MODE_PRIVATE);
		// 如果不存在heartbeat1字段
		if(!sharepre.contains("heartbeat1")) {
			sharepre.edit().putLong("heartbeat1",1).commit();	// 初始值为1
			LogDetect.send(LogDetect.DataType.basicType,"PacketReader----heartbeat1-----","1");
		} else {	// 如果已经存在heartbeat1
			long heartbeat1 = sharepre.getLong("heartbeat1",0);	// 读取失败默认会置零
			Log.v("TT","updateOpenfireHeartbeat(): "+heartbeat1);
			sharepre.edit().putLong("heartbeat1",++heartbeat1).commit();	// 每次累加1
			LogDetect.send(LogDetect.DataType.basicType,"PacketReader----heartbeat1-----",heartbeat1);
		}
		LogDetect.send(LogDetect.DataType.basicType,"PacketReader----heartbeat1-----","--updateOpenfireHeartbeat()");
		Log.v("TT","--updateOpenfireHeartbeat()");
	}

	public void setKickoff(boolean kickoff) {
		isKickoff = kickoff;
	}

	public boolean isKickoff() {
		return isKickoff;
	}/**/

	public static void clientHeartbeat() {
		if(instance != null) {
			instance.checkConnectStatusAndReconnect();
		}
	}

	public synchronized void checkConnectStatusAndReconnect() {

		if(checkConnection == true) {
			Log.v("TTT","====== 正在检测连接中 ======");
			return;
		} else {
			checkConnection = true;
		}
		try {
		// 如果被踢下线就不尝试重新连接了
		new Thread(new Runnable() {

			@Override
			public void run() {
				Log.v("TTT", "isKickoff="+isKickoff+",userid="+Util.userid+",chatmanager="+Utils.xmppchatmanager);
				if(!isKickoff && !Util.userid.isEmpty() && !"0".equals(Util.userid) && Utils.xmppchatmanager != null) {

					//if(!seller_id.equals("0")){
					Utils.xmppchatmanager.addChatListener(new ChatManagerListener() {
						@Override
						public void chatCreated(Chat chat, boolean createdLocally) {
							chat.addMessageListener(new MessageListener() {

								@Override
								public void processMessage(Chat chat, Message message) {
									isResponse = true;
									synchronized (lock) {
											lock.notifyAll();
									}
									Log.v("TTT","已收到 heartbeat 消息：from="+message.getFrom()+",to="+message.getTo()+",body="+message.getBody());
								}
							});
						}
					});
					Chat chat = Utils.xmppchatmanager.createChat("heartbeat"+"@" + Const.XMPP_HOST, null);
					try {
						isResponse = false;
						chat.sendMessage("phoneime", Util.userid+"@" + Const.XMPP_HOST);
						Log.v("TTT","已发送 heartbeat 消息");

						synchronized (lock) {
							try {
								lock.wait(5000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						if(isResponse == false) {
							Log.e("TTT","++发送 relogin 消息");
//								Chat chatRelogin = Utils.xmppchatmanager.createChat("freshconnect" + "@" + Const.XMPP_HOST, null);
//								chatRelogin.sendMessage("relogin", Util.userid + "@" + Const.XMPP_HOST);
								reConnectOpenfire();
							Log.e("TTT","--发送 relogin 消息");
							} else {
								Log.e("TTT", "已收到应答");
						}
					} catch (NotConnectedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
					checkConnection = false;
			}
		}).start();
		} catch (Exception e) {
			e.printStackTrace();
			checkConnection = false;
		}

	}

	private void reConnectOpenfire() {
		ConnectionConfiguration connectionConfig = new ConnectionConfiguration(
				Const.XMPP_HOST, Const.XMPP_PORT);

		connection = new XMPPTCPConnection(connectionConfig);

		connection.addConnectionListener(new ConnectionListener() {

			@Override
			public void connected(XMPPConnection connection) {
				// TODO Auto-generated method stub

				//SharedPreferences share=getSharedPreferences("Acitivity",Activity.MODE_PRIVATE);
				//String str=share.getString("shop_id","");
				//LogDetect.send(Utils.user_id+"=phone="+Utils.android,"str: "+str);
				ChatManager chatmanager = new ChatManager((XMPPTCPConnection)connection);
				//if(!seller_id.equals("0")){
				Chat chat = chatmanager.createChat("freshconnect"+"@" + Const.XMPP_HOST, null);

				try {
					chat.sendMessage("relogin",Util.userid+"@" + Const.XMPP_HOST);

				} catch (NotConnectedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				connected = true;
			}

			@Override
			public void authenticated(XMPPConnection connection) {
				// TODO Auto-generated method stub

			}

			@Override
			public void connectionClosed() {
				// TODO Auto-generated method stub

			}

			@Override
			public void connectionClosedOnError(Exception e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void reconnectingIn(int seconds) {
				// TODO Auto-generated method stub

			}

			@Override
			public void reconnectionSuccessful() {
				// TODO Auto-generated method stub

			}

			@Override
			public void reconnectionFailed(Exception e) {
				// TODO Auto-generated method stub

			}

		});

		try {
			//连接
			//connection.connectUsingConfiguration(connectionConfig);
			Log.v("PAOPAO", "before connect");
			connection.connect();
			Log.v("PAOPAO", "after connect");

			//Utils.xmppConnection=connection;
			// 初始化聊天管理
			ChatManager chatmanager = new ChatManager(connection);
			//LogDetect.send(DataType.nonbasicType,Utils.seller_id+"=phone="+Utils.android,chatmanager);
			Utils.xmppchatmanager = chatmanager;
			// 监听
			chatmanager.addChatListener(new ChatManagerListener() {
				@Override
				public void chatCreated(Chat arg0, boolean arg1) {
					arg0.addMessageListener(new managerlistener(mContext));
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
