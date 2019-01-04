package com.net.yuesejiaoyou.classroot.interface4.openfire.core;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.util.Log;

import org.apache.http.client.HttpClient;

import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect.DataType;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.db.Const;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa1.smack.XMPPException;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa1.smack.XMPPTCPConnection;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack.Chat;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack.ChatManager;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack.SmackException;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack.SmackException.NotConnectedException;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.utils.LogUtil;


public class Utils {

    public static XMPPTCPConnection xmppConnection;
    public static ChatManager xmppchatmanager;
    public static HttpClient httpclient = null;
    public static double longtitude = 0;
    public static double latitude = 0;
    public static String sessionid = "0";
    public static String seller_id = "0";
    public static String android = "0";
    public static String phoneimei;
    public static String downtime;
    public static String currentfrom = "";


    /**
     * 发送邀请
     *
     * @param content
     * @param touser
     * @throws XMPPException
     * @throws SmackException.NotConnectedException
     */
    public static void sendmessage(XMPPTCPConnection mXMPPConnection, String content, String touser) throws XMPPException, SmackException.NotConnectedException {
        if (mXMPPConnection == null || !mXMPPConnection.isConnected()) {
            // throw new XMPPException();
        }
        ChatManager chatmanager = Utils.xmppchatmanager;
        if (chatmanager == null) {
            return;
        }

        Chat chat = chatmanager.createChat(touser + "@" + Const.XMPP_HOST, null);
        if (chat != null) {
            chat.sendMessage(content, Util.userid + "@" + Const.XMPP_HOST);
            LogUtil.i("ttt","---发送成功");
        } else {
            LogUtil.i("ttt","---发送失败");
        }
    }


    /**
     * 发送消息
     *
     * @param touser
     * @throws XMPPException
     * @throws NotConnectedException
     */
    public static void sendMessage(String fromuser,
                                   String touser) throws XMPPException, NotConnectedException {
        if (Utils.xmppConnection == null || !Utils.xmppConnection.isConnected()) {
            // throw new XMPPException();
            return;
        }
        // ChatManager chatmanager = mXMPPConnection.getChatManager();
        // hatManager chatmanager = new ChatManager(mXMPPConnection);
        ChatManager chatmanager = Utils.xmppchatmanager;
        LogDetect.send(DataType.noType, Utils.seller_id + "=phone=" + Utils.android, "chatmanager: " + chatmanager);
        // Chat chat =chatmanager.createChat(touser + "@" + Const.XMPP_HOST,
        // null);
        Chat chat = chatmanager.createChat("user$" + touser + "@" + Const.XMPP_HOST, null);
        if (chat != null) {
            // chat.sendMessage(content);
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String message = "动态" + Const.SPLIT + Const.MSG_TYPE_DYN
                    + Const.SPLIT + sd.format(new Date()) + Const.SPLIT + "商家" + Const.SPLIT + "商家";
            chat.sendMessage(message, "seller$" + fromuser + "@" + Const.XMPP_HOST);
            //Log.e("jj", "发送成功");
            LogDetect.send(DataType.noType, Utils.seller_id + "=phone=" + Utils.android, "send success");
        } else {
            LogDetect.send(DataType.noType, Utils.seller_id + "=phone=" + Utils.android, "send fail:chat is null");
        }
    }


    public static String encode(String noutf8) {
        StringBuffer sb = new StringBuffer();
        sb.append(noutf8);

        //String a=new  String(sb,"UTF-8");
        String xmString = "";
        String xmlUTF8 = "";
        try {
            xmString = new String(sb.toString().getBytes("UTF-8"));
            noutf8 = URLEncoder.encode(xmString, "UTF-8");


        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return noutf8;

    }

    public static String decode(String isutf8) {
        String output;
        String xmString = "";
        try {
            output = URLDecoder.decode(isutf8, "UTF-8");
            xmString = new String(output.toString().getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return xmString;
    }


}

