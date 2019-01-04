package com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.listener;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.net.yuesejiaoyou.redirect.ResolverD.interface4.YhApplicationA;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.classroot.interface4.openfire.core.Utils;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.bean.Msg;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.bean.Session;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.db.ChatMsgDao;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.db.Const;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.db.SessionDao;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa1.smack.Base64;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack.Chat;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack.Message;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack.MessageListener;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack.util.FileInOut;
import com.net.yuesejiaoyou.classroot.interface4.openfire.interface4.Util;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.agora.P2PVideoConst;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.agora.VideoMessageManager;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.activity.GukeActivity;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.agora.guke.ZhuboInfo;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.agora.zhubo.GukeInfo;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.agora.zhubo.ZhuboActivity;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.im.IMManager;
import com.net.yuesejiaoyou.redirect.ResolverB.uiface.tuichu;
import com.net.yuesejiaoyou.redirect.ResolverB.uiface.tuichu1;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;


public class managerlistener implements MessageListener {
    Context mContext;


    private ChatMsgDao msgDao;
    private SessionDao sessionDao;
    SoundPool sp;
    HashMap<Integer, Integer> spMap;

    public managerlistener(Context a) {
        this.mContext = a;
        sessionDao = new SessionDao(a);
        msgDao = new ChatMsgDao(a);
    }

    @Override
    public void processMessage(Chat chat, Message message) {
        Log.v("PAOPAO", "收到消息:" + message.getBody());
        new Thread(new messagepj(message)).start();
    }

    class messagepj implements Runnable {
        Message message;
        private ReentrantLock lock = new ReentrantLock();

        public messagepj(Message svrInfo) {
            this.message = svrInfo;
        }

        @Override
        public void run() {
            lock.lock();
            playmessage(message);
            lock.unlock();
        }

    }

    public class MyThreadtc extends Thread {
        public void run() {
            while (true) {
                Intent intent = new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setClass(mContext.getApplicationContext(),
                        tuichu.class);
                mContext.startActivity(intent);
                break;
            }

        }
    }


    @SuppressLint("CommitPrefEdits")
    public void playmessage(Message message) {
        final String msgBody = message.getBody();
        if (TextUtils.isEmpty(msgBody))
            return;
        Log.v("ttt", "playmessage-: " + msgBody);
        LogDetect.send(LogDetect.DataType.specialType, "playmessage: ", msgBody);
        // 普通用户向主播发送一对一视频请求
        if (msgBody.contains(Const.ACTION_MSG_ONEINVITE + "卍")) {
            Log.e("jj", msgBody);
            String from = message.getFrom();
            final String[] froms = from.split("@");
            String to = message.getTo();
            String[] tos = to.split("@");
            String[] msgs = msgBody.split(Const.SPLIT);

            if (msgBody.contains("邀0请1视2频")) {
                Log.v("TTT", "收到顾客邀请: " + msgBody);

                YhApplicationA application = (YhApplicationA) mContext.getApplicationContext();
                Activity curActivity = application.getCurrentActivity();
                if (application.isCurrentP2PZhuboActivity() == false) {
                    ZhuboActivity.callFromGuke(curActivity, new GukeInfo(froms[0], msgs[3], msgs[4], msgs[2], P2PVideoConst.GUKE_CALL_ZHUBO, P2PVideoConst.NONE_YUYUE));
                }

            } else if (msgBody.contains("收0到1邀2请")) {


            } else if (msgBody.contains("拒0绝1邀2请")) {    // 顾客收到主播拒绝邀请信息
                Log.v("TTT", "收到主播拒绝: " + msgBody);
                YhApplicationA application = (YhApplicationA) mContext.getApplicationContext();
                if (application.isCurrentP2PGukeActivity()) {
                    VideoMessageManager.getCmdMessageListener().onCmdMessageListener(froms[0], msgs[3], msgs[4], VideoMessageManager.VIDEO_U2A_ANCHOR_HANGUP, msgs[2]);
                }
            } else {

                // 挂断邀请则关闭声网
                if (msgBody.contains("挂0断1邀2请")) {
                    Log.v("TTT", "收到顾客挂断: " + msgBody);

                    YhApplicationA application = (YhApplicationA) mContext.getApplicationContext();
                    if (application.isCurrentP2PZhuboActivity()) {
                        VideoMessageManager.getCmdMessageListener().onCmdMessageListener(froms[0], msgs[3], msgs[4], VideoMessageManager.VIDEO_U2A_USER_HANGUP, msgs[2]);
                    }
                }
            }


            return;
        }

        //主播预约
        if (msgBody.contains(Const.ACTION_MSG_ZB_RESERVE + "卍")) {
            Log.e("jj", msgBody);
            String from = message.getFrom();
            final String[] froms = from.split("@");
            String to = message.getTo();
            String[] tos = to.split("@");

            if (msgBody.contains("邀0请1视2频")) {
                Log.v("TTT", "收到主播邀请: " + msgBody);
                String[] msgs = msgBody.split(Const.SPLIT);
                YhApplicationA application = (YhApplicationA) mContext.getApplicationContext();
                Activity curActivity = application.getCurrentActivity();
                GukeActivity.callFromZhubo(curActivity, new ZhuboInfo(froms[0], msgs[3], msgs[4], msgs[2], P2PVideoConst.ZHUBO_CALL_GUKE, P2PVideoConst.NONE_YUYUE));
            } else if (msgBody.contains("拒0绝1邀2请")) {
                Log.v("TTT", "收到顾客挂断: " + msgBody);
//				MyThreadZB myThread1 = new MyThreadZB(froms[0], msgBody);
//				myThread1.start();

                String[] msgs = msgBody.split(Const.SPLIT);
                YhApplicationA application = (YhApplicationA) mContext.getApplicationContext();
                if (application.isCurrentP2PZhuboActivity()) {
                    LogDetect.writeFile("msgBody: " + msgBody);
                    VideoMessageManager.getCmdMessageListener().onCmdMessageListener(froms[0], msgs[3], msgs[4], VideoMessageManager.VIDEO_A2U_USER_HANGUP, msgs[2]);
                }
            } else {

                // 挂断邀请则关闭声网
                if (msgBody.contains("挂0断1邀2请")) {
                    Log.v("TTT", "收到主播挂断: " + msgBody);
                    String[] msgs = msgBody.split(Const.SPLIT);
//					AgoraVideoManager.close();
                    // 判断一下音乐播放页面是否已经开启，如果开启则给关闭掉
                    YhApplicationA application = (YhApplicationA) mContext.getApplicationContext();

                    if (application.isCurrentP2PGukeActivity()) {
                        VideoMessageManager.getCmdMessageListener().onCmdMessageListener(froms[0], msgs[3], msgs[4], VideoMessageManager.VIDEO_A2U_ANCHOR_HANGUP, msgs[2]);
                    }
                }

            }

            return;
        }


        if (msgBody.contains(Const.ACTION_MSG_ONECHAT + "卍")) {
            Intent intent = new Intent(Const.ACTION_MSG_ONECHAT);// 一对一聊天
            Bundle bundle = new Bundle();
            bundle.putString("oneuponechat", msgBody);
            LogDetect.send(LogDetect.DataType.specialType, "01160", msgBody);
            intent.putExtras(bundle);
            mContext.sendBroadcast(intent);
            return;
        }


        if (msgBody.contains(Const.ACTION_MSG_PAY + "卍")) {

            Intent intent = new Intent(Const.ACTION_MSG_PAY);// 充值提醒
            Bundle bundle = new Bundle();
            bundle.putString("chongzhitixing", msgBody);
            intent.putExtras(bundle);

            mContext.sendBroadcast(intent);

            return;
        }

        /**
         * 动态广播
         */
        if (msgBody.contains(Const.ACTION_DYNAMIC)) {
            LogDetect.send(LogDetect.DataType.specialType, "01160 广播", msgBody);
            if (com.net.yuesejiaoyou.classroot.interface4.util.Util.dongtai.equals("1")) {
                //MusicUtil.playSound(1,0);
                Intent intent = new Intent(Const.ACTION_DYNAMIC);// 动态广播
                Bundle bundle = new Bundle();
                bundle.putString("dongtai", "1");
                intent.putExtras(bundle);
                LogDetect.send(LogDetect.DataType.specialType, "01160 dongtai", msgBody);
                mContext.sendBroadcast(intent);
            } else {
                Intent intent = new Intent(Const.ACTION_DYNAMIC);// 动态广播
                Bundle bundle = new Bundle();
                bundle.putString("dongtai", "0");
                intent.putExtras(bundle);

                mContext.sendBroadcast(intent);

            }


            return;
        }

        /**
         * 正在输入广播
         */
        if (msgBody.contains(Const.ACTION_INPUT)) {
            LogDetect.send(LogDetect.DataType.specialType, "01160 广播", msgBody);
            String[] body = msgBody.split("卍");
            String status = body[0];
            if (status.equals("1")) {
                Intent intent = new Intent(Const.ACTION_INPUT);// 动态广播
                Bundle bundle = new Bundle();
                bundle.putString("shuru", "1");
                intent.putExtras(bundle);
                LogDetect.send(LogDetect.DataType.specialType, "01160 正在输入", msgBody);
                mContext.sendBroadcast(intent);
            } else if (status.equals("0")) {
                Intent intent = new Intent(Const.ACTION_INPUT);// 动态广播
                Bundle bundle = new Bundle();
                bundle.putString("shuru", "0");
                intent.putExtras(bundle);
                LogDetect.send(LogDetect.DataType.specialType, "01160 正在输入", msgBody);
                mContext.sendBroadcast(intent);
            }
            return;
        }

        if (msgBody.contains(Const.ACTION_SYSTEM_NOTICE + "卍")) {
            String from = message.getFrom();
            final String[] froms = from.split("@");

            String to = message.getTo();
            String[] tos = to.split("@");

            String[] system = msgBody.split("卍");

            Session session = new Session();
            session.setFrom(froms[0]);
            session.setTo(tos[0]);
            session.setNotReadCount("");// 未读消息数量
            session.setTime(system[2]);


            session.setName("系统消息");
            session.setHeadpic("http://116.62.220.67:8090/img/imgheadpic/launch_photo.png");

            Msg msg = new Msg();
            msg.setToUser(tos[0]);
            msg.setFromUser(froms[0]);
            msg.setIsComing(0);
            msg.setContent(system[0]);
            msg.setDate(system[2]);
            if (Util.currentfrom.equals(froms[0])) {
                msg.setIsReaded("1");// 暂且默认为已读
                Log.v("PAOPAO", "已读");
            } else {
                msg.setIsReaded("0");// 暂且默认为未读
                Log.v("PAOPAO", "未读");
            }

            msg.setType(Const.MSG_TYPE_TEXT);
            msgDao.insert(msg);
            session.setType(Const.MSG_TYPE_TEXT);
            session.setContent(system[0]);
            Log.v("PAOPAO", "from:" + froms[0] + ",to:" + tos[0]);
            if (sessionDao.isContent(froms[0], tos[0])) {// 判断最近联系人列表是否已存在记录
                sessionDao.updateSession(session);
                Log.v("PAOPAO", "update " + session.getFrom() + "," + session.getTo());
            } else {
                sessionDao.insertSession(session);
                Log.v("PAOPAO", "insert");
            }
            //playSound(1, 0);
            //MusicUtil.playSound(5, 0);

            Intent intent = new Intent(Const.ACTION_ADDFRIEND);// 发送广播，通知消息界面更新
            mContext.sendBroadcast(intent);

            if (system[0].startsWith("欢迎入驻悦色平台")) {
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent.setClass(mContext.getApplicationContext(), tuichu1.class).putExtra("status", "1"));
                return;
            }

            if (system[0].equals("封禁")) {
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent.setClass(mContext.getApplicationContext(), tuichu1.class).putExtra("status", "0"));
                return;
            }


            return;
        }


        if (msgBody.equals("AnotherLogin卍over_login")) {


            IMManager.getInstance().setKickoff(true);    // 被踢下线

            MyThreadtc myThread1 = new MyThreadtc();
            myThread1.start();
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String a = sd.format(new Date());
            Utils.downtime = a;
            return;
        }

        // 客户端心跳应答
        if (msgBody.equals("heart卍beat")) {
            Log.v("TTT", "managerlistener: heartbeat");
            return;
        }

        if (msgBody.contains("Chafeng卍Seller")) {

            //MyThread1 myThread2 = new MyThread1();
            //myThread2.start();
            return;
        }

        Log.e("jj", "收到" + msgBody);
        //LogDetect.send(DataType.basicType,Utils.seller_id+"=phone="+Utils.android,msgBody);
        String from = message.getFrom();
        String[] froms = from.split("@");
        String to = message.getTo();
        String[] tos = to.split("@");

        String[] msgs = msgBody.split("卍");
        String msgtype = msgs[1];
        if (msgtype.equals("chat_text") || msgtype.equals("msg_type_goodsInformation")) { // 文本类型

            Session session = new Session();
            session.setFrom(froms[0]);
            session.setTo(tos[0]);
            session.setNotReadCount("");// 未读消息数量
            session.setTime(msgs[2]);

            session.setName(msgs[3]);
            session.setHeadpic(msgs[4]);


            Msg msg = new Msg();
            msg.setToUser(tos[0]);
            msg.setFromUser(froms[0]);
            msg.setIsComing(0);
            msg.setContent(msgs[0]);
            msg.setDate(msgs[2]);
            if (Util.currentfrom.equals(froms[0])) {
                msg.setIsReaded("1");// 暂且默认为已读
                Log.v("PAOPAO", "已读");
            } else {
                msg.setIsReaded("0");// 暂且默认为未读
                Log.v("PAOPAO", "未读");
            }

            msg.setType(msgtype);
            msgDao.insert(msg);
            session.setType(msgtype);
            session.setContent(msgs[0]);
            Log.v("PAOPAO", "from:" + froms[0] + ",to:" + tos[0]);
            if (sessionDao.isContent(froms[0], tos[0])) {// 判断最近联系人列表是否已存在记录
                sessionDao.updateSession(session);
                Log.v("PAOPAO", "update " + session.getFrom() + "," + session.getTo());
            } else {
                sessionDao.insertSession(session);
                Log.v("PAOPAO", "insert");
            }
            //playSound(1, 0);

            String title = null, content = null;
            if (msgtype.equals("chat_text")) {
                title = "消息";
                content = msgs[0];
            } else {
                title = "消息";
                String[] a = msg.getContent().split("\\$");
                content = a[2];
            }

        }

        if (msgBody.contains("com.android.one.dvcertified卍")) {

            String from1 = message.getFrom();
            final String[] froms1 = from1.split("@");
            LogDetect.send(LogDetect.DataType.specialType, "01160 认证通知:", froms1);
            String to1 = message.getTo();
            String[] tos1 = to1.split("@");

            String[] system = msgBody.split("卍");

            Session session = new Session();
            session.setFrom(froms1[0]);
            session.setTo(tos1[0]);
            session.setNotReadCount("");// 未读消息数量
            session.setTime(system[2]);

            session.setName("小客服");
            session.setHeadpic("http://116.62.220.67:8090/img/imgheadpic/launch_photo.png");


            Msg msg = new Msg();
            msg.setToUser(tos1[0]);
            msg.setFromUser(froms1[0]);
            msg.setIsComing(0);
            msg.setContent(system[0]);
            msg.setDate(system[2]);
            if (Util.currentfrom.equals(froms[0])) {
                msg.setIsReaded("1");// 暂且默认为已读
            } else {
                msg.setIsReaded("0");// 暂且默认为未读
            }
            LogDetect.send(LogDetect.DataType.specialType, "01160 认证通知 dv id:", tos1[0]);
            msg.setType(Const.MSG_TYPE_TEXT);
            msgDao.insert(msg);
            session.setType(Const.MSG_TYPE_TEXT);
            session.setContent(system[0]);
            if (sessionDao.isContent(froms1[0], tos1[0])) {// 判断最近联系人列表是否已存在记录
                sessionDao.updateSession(session);
            } else {
                sessionDao.insertSession(session);
            }

            Intent intent = new Intent(Const.ACTION_ADDFRIEND);// 发送广播，通知消息界面更新
            mContext.sendBroadcast(intent);

            if (system[0].startsWith("恭喜您认证主播成功")) {
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent.setClass(mContext.getApplicationContext(), tuichu1.class).putExtra("status", "1"));
                return;
            }

            if (system[0].equals("封禁")) {
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent.setClass(mContext.getApplicationContext(), tuichu1.class).putExtra("status", "0"));
                return;
            }

            return;
        }


        if (msgtype.equals("msg_type_img")) {
            FileInOut.writeFile(Base64.decode(msgs[0]),
                    new File(Environment.getExternalStorageDirectory() + "/"
                            + msgs[3]));
            Session session = new Session();
            session.setFrom(froms[0]);
            session.setTo(tos[0]);
            session.setNotReadCount("");// 未读消息数量
            session.setTime(msgs[2]);
            session.setName(msgs[4]);
            session.setHeadpic(msgs[5]);
            Msg msg = new Msg();
            msg.setToUser(tos[0]);
            msg.setFromUser(froms[0]);
            msg.setIsComing(0);
            // msg.setContent(msgs[0]);
            msg.setDate(msgs[2]);
            if (Util.currentfrom.equals(froms[0])) {
                msg.setIsReaded("1");// 暂且默认为已读

            } else {
                msg.setIsReaded("0");// 暂且默认为未读

            }
            msg.setType(msgtype);

            msg.setContent(Environment.getExternalStorageDirectory() + "/"
                    + msgs[3]);
            msgDao.insert(msg);
            session.setType("msg_type_img");
            session.setContent("[图片]");
            if (sessionDao.isContent(froms[0], tos[0])) {// 判断最近联系人列表是否已存在记录
                sessionDao.updateSession(session);
            } else {
                sessionDao.insertSession(session);
            }
            //playSound(1, 0);

            String title = null, content = null;

            title = "消息";
            content = "[图片]";
            //MyThreadnot myThreadmot = new MyThreadnot(title,content);
            //myThreadmot.start();


        }

        if (msgtype.equals("msg_type_voice")) {
            FileInOut.writeFile(Base64.decode(msgs[0]),
                    new File(Environment.getExternalStorageDirectory() + "/"
                            + msgs[3].split(":")[0]));
            Log.v("TT", "msg_type_voice: " + new File(Environment.getExternalStorageDirectory() + "/"
                    + msgs[3]).getAbsolutePath());
            Session session = new Session();
            session.setFrom(froms[0]);
            session.setTo(tos[0]);
            session.setNotReadCount("");// 未读消息数量
            session.setTime(msgs[2]);
            session.setName(msgs[4]);
            session.setHeadpic(msgs[5]);
            Msg msg = new Msg();
            msg.setToUser(tos[0]);
            msg.setFromUser(froms[0]);
            msg.setIsComing(0);
            // msg.setContent(msgs[0]);
            msg.setDate(msgs[2]);
            if (Util.currentfrom.equals(froms[0])) {
                msg.setIsReaded("1");// 暂且默认为已读

            } else {
                msg.setIsReaded("0");// 暂且默认为未读

            }
            msg.setType(msgtype);

            msg.setContent(Environment.getExternalStorageDirectory() + "/"
                    + msgs[3]);
            msgDao.insert(msg);
            session.setType("msg_type_voice");
            session.setContent("[语音]");
            if (sessionDao.isContent(froms[0], tos[0])) {// 判断最近联系人列表是否已存在记录
                sessionDao.updateSession(session);
            } else {
                sessionDao.insertSession(session);
            }
            //playSound(1, 0);

            String title = null, content = null;

            title = "消息";
            content = "[语音]";
            //MyThreadnot myThreadmot = new MyThreadnot(title,content);
            //myThreadmot.start();


        }

        if (msgtype.equals("msg_type_video")) {
            Session session = new Session();
            session.setFrom(froms[0]);
            session.setTo(tos[0]);
            session.setNotReadCount("");// 未读消息数量
            session.setTime(msgs[2]);

            session.setName(msgs[3]);
            session.setHeadpic(msgs[4]);


            Msg msg = new Msg();
            msg.setToUser(tos[0]);
            msg.setFromUser(froms[0]);
            msg.setIsComing(0);
            msg.setContent(msgs[0]);
            msg.setDate(msgs[2]);
            if (Util.currentfrom.equals(froms[0])) {
                msg.setIsReaded("1");// 暂且默认为已读
                Log.v("PAOPAO", "已读");
            } else {
                msg.setIsReaded("0");// 暂且默认为未读
                Log.v("PAOPAO", "未读");
            }

            msg.setType(msgtype);
            msgDao.insert(msg);
            session.setType(msgtype);
            session.setContent("[视频 消息]");
            Log.v("PAOPAO", "from:" + froms[0] + ",to:" + tos[0]);
            if (sessionDao.isContent(froms[0], tos[0])) {// 判断最近联系人列表是否已存在记录
                sessionDao.updateSession(session);
                Log.v("PAOPAO", "update " + session.getFrom() + "," + session.getTo());
            } else {
                sessionDao.insertSession(session);
                Log.v("PAOPAO", "insert");
            }

        }


        if (msgtype.equals("msg_type_location")) {

            Session session = new Session();
            session.setFrom(froms[0]);
            session.setTo(tos[0]);
            session.setNotReadCount("");// 未读消息数量
            session.setTime(msgs[2]);

            session.setName(msgs[3]);
            session.setHeadpic(msgs[4]);

            Msg msg = new Msg();
            msg.setToUser(tos[0]);
            msg.setFromUser(froms[0]);
            msg.setIsComing(0);
            msg.setContent(msgs[0]);
            msg.setDate(msgs[2]);
            if (Util.currentfrom.equals(froms[0])) {
                msg.setIsReaded("1");// 暂且默认为已读
            } else {
                msg.setIsReaded("0");// 暂且默认为未读
            }
            msg.setType(msgtype);
            msgDao.insert(msg);
            session.setType("msg_type_location");
            session.setContent("[位置]");
            if (sessionDao.isContent(froms[0], tos[0])) {// 判断最近联系人列表是否已存在记录
                sessionDao.updateSession(session);
            } else {
                sessionDao.insertSession(session);
            }
            //playSound(1, 0);
            String title = null, content = null;
            title = "消息";
            content = "[位置]";
            //MyThreadnot myThreadmot = new MyThreadnot(title,content);
            //myThreadmot.start();

        }

        if (msgtype.equals(Const.MSG_TYPE_DAZHAOHU)) {

            Session session = new Session();
            session.setFrom(froms[0]);
            session.setTo(tos[0]);
            session.setNotReadCount("");// 未读消息数量
            session.setTime(msgs[2]);

            session.setName(msgs[3]);
            session.setHeadpic(msgs[4]);

            Msg msg = new Msg();
            msg.setToUser(tos[0]);
            msg.setFromUser(froms[0]);
            msg.setIsComing(0);
            msg.setContent(msgs[0]);
            msg.setDate(msgs[2]);
            if (Util.currentfrom.equals(froms[0])) {
                msg.setIsReaded("1");// 暂且默认为已读
            } else {
                msg.setIsReaded("0");// 暂且默认为未读
            }
            msg.setType(msgtype);
            msgDao.insert(msg);
            session.setType(Const.MSG_TYPE_DAZHAOHU);
            session.setContent("[视频请求]");
            if (sessionDao.isContent(froms[0], tos[0])) {// 判断最近联系人列表是否已存在记录
                sessionDao.updateSession(session);
            } else {
                sessionDao.insertSession(session);
            }
        }


        if (msgtype.equals("chat_order")) {// 订单

            Session session = new Session();
            session.setFrom("订单提醒");
            session.setTo(tos[0]/*Utils.seller_id*/);
            session.setNotReadCount("");// 未读消息数量
            session.setTime(msgs[2]);
            session.setName("订单提醒");
            session.setHeadpic("000000");
            Msg msg = new Msg();
            msg.setToUser(tos[0]/*Utils.seller_id*/);
            msg.setFromUser("订单提醒");
            msg.setIsComing(0);
            msg.setContent(msgs[0]);
            msg.setDate(msgs[2]);
            if (Utils.currentfrom.equals(/*froms[0]*/"订单提醒")) {
                msg.setIsReaded("1");// 暂且默认为已读
            } else {
                msg.setIsReaded("0");// 暂且默认为未读
                //infohong = true;
            }
            msg.setType(msgtype);
            msgDao.insert(msg);
            session.setType("chat_order");
            session.setContent(msgs[0]);
            if (sessionDao.isContent("订单提醒", /*tos[0]*/Utils.seller_id)) {// 判断最近联系人列表是否已存在记录
                sessionDao.updateSession(session);
            } else {
                sessionDao.insertSession(session);
            }
            //playSound(1, 0);

            //playSound(1, 0);
            String title = null, content = null;
            title = "订单提醒";
            String[] content1 = msg.getContent().split("\\$");
            content = "订单编号：" + content1[3];
            //MyThreadnot myThreadmot = new MyThreadnot(title,content);
            //myThreadmot.start();
        }
        if (msgtype.equals("chat_systemInfo")) {// 订单

            Session session = new Session();
            session.setFrom("系统通知");
            session.setTo(tos[0]/*Utils.seller_id*/);
            session.setNotReadCount("");// 未读消息数量
            session.setTime(msgs[2]);
            session.setName("系统通知");
            session.setHeadpic("000000");
            Msg msg = new Msg();
            msg.setToUser(tos[0]/*Utils.seller_id*/);
            msg.setFromUser("系统通知");
            msg.setIsComing(0);
            msg.setContent(msgs[0]);
            msg.setDate(msgs[2]);
            if (Utils.currentfrom.equals(/*froms[0]*/"系统通知")) {
                msg.setIsReaded("1");// 暂且默认为已读
            } else {
                msg.setIsReaded("0");// 暂且默认为未读
                //infohong = true;
            }
            msg.setType(msgtype);
            msgDao.insert(msg);
            session.setType("chat_systemInfo");
            session.setContent(msgs[0]);
            if (sessionDao.isContent("系统通知", tos[0]/*Utils.seller_id*/)) {// 判断最近联系人列表是否已存在记录
                sessionDao.updateSession(session);
            } else {
                sessionDao.insertSession(session);
            }


        }

        if (msgtype.equals("dyn_rel")) {
            //playSound(1, 0);
            Intent intent = new Intent(Const.MSG_TYPE_DYN);// 发送广播，通知消息界面更新
            mContext.sendBroadcast(intent);
        } else {
            Intent intent = new Intent(Const.ACTION_MSG_OPER);// 发送广播，通知消息界面更新
            mContext.sendBroadcast(intent);
            KeyguardManager km = (KeyguardManager) mContext.getSystemService(Context.KEYGUARD_SERVICE);
//	        if (km.inKeyguardRestrictedInputMode()) {  
//	            Intent alarmIntent = new Intent(mContext, KeyguardActivity.class);  
//	            alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
//	            mContext.startActivity(alarmIntent);  
//	        }  
        }
        if (msgtype.equals(Const.REWARD_ANCHOR)) {
            String[] tp1 = msgBody.split(Const.SPLIT);
            Intent intent = new Intent(Const.REWARD_ANCHOR);
            intent.putExtra("reward_num", tp1[0]);
            mContext.sendBroadcast(intent);
        }


    }

    public void playSound(int sound, int number) {
        AudioManager am = (AudioManager) mContext
                .getSystemService(Context.AUDIO_SERVICE);
        float audioMaxVolumn = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        float volumnCurrent = am.getStreamVolume(AudioManager.STREAM_MUSIC);
        float volumnRatio = volumnCurrent / audioMaxVolumn;

        sp.play(spMap.get(sound), volumnRatio, volumnRatio, 1, number, 1f);
    }
}
