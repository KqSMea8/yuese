package com.net.yuesejiaoyou.redirect.ResolverA.interface4.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.bean.Msg;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.bean.Session;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.db.ChatMsgDao;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.db.Const;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.db.SessionDao;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.utils.Tools;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2018\7\17 0017.
 */

public class AutoMessage {
    private static AutoMessage instance;

    public static void startAutoMessage(Context ctxt) {
        if (instance == null) {
            instance = new AutoMessage(ctxt);
        }
    }

    private Context mContext;
    private Thread bkThread;

    private AutoMessage(Context ctxt) {
        mContext = ctxt;
        bkThread = new Thread(new Runnable() {

            private int curIndex = 0;
            private int maxIndex = 0;  // 最多发15次

            @Override
            public void run() {
                int delayTime = getRandDelayTime();
                try {
                    Thread.sleep(delayTime * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                JSONArray jsonArray = null;
                try {
                    String json = netRequest();
                    jsonArray = new JSONArray(json);
//                    for (int i = 0; i < jsonArray.length(); i++) {
//
//                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    maxIndex = jsonArray.length();
                } catch (Exception e) {

                }
                while (curIndex < maxIndex) {

                    delayTime = getRandDelayTime();
                    try {
                        Thread.sleep(delayTime * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        org.json.JSONObject jsonObj = jsonArray.getJSONObject(curIndex);
                        String zhuboid = jsonObj.getString("zhuboid");
                        String zhuboname = jsonObj.getString("zhuboname");
                        String zhubophoto = jsonObj.getString("zhubophoto");
                        String word = jsonObj.getString("word");
                        insertMessage(zhuboid, zhuboname, zhubophoto, word);
                        Intent intent = new Intent(Const.ACTION_ADDFRIEND);// 发送广播，通知消息界面更新
                        mContext.sendBroadcast(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    curIndex++;
                }
            }
        });
        bkThread.start();
    }

    /**
     * 产生30~60秒的随机值
     *
     * @return
     */
    private int getRandDelayTime() {
//        Random rand = new Random();
//        int randDelayTime = rand.nextInt(30);
//
//        return 30+randDelayTime;
        //------------------------------
        Random rand = new Random();
        int randDelayTime = rand.nextInt(5);

        return randDelayTime;
    }

    private void insertMessage(String zhuboid, String zhuboname, String zhubophoto, String word) {

        ChatMsgDao msgDao = new ChatMsgDao(mContext);
        SessionDao sessionDao = new SessionDao(mContext);
        String time = Tools.currentTime();

        Session session = new Session();
        session.setFrom(zhuboid);
        session.setTo(Util.userid);
        session.setNotReadCount("");// 未读消息数量

        session.setTime(time);

        session.setName(zhuboname);
        session.setHeadpic(zhubophoto);


        Msg msg = new Msg();
        msg.setToUser(Util.userid);
        msg.setFromUser(zhuboid);
        msg.setIsComing(0);
        msg.setContent(word);
        msg.setDate(time);
        //if(com.net.yuesejiaoyou.classroot.interface4.openfire.interface4.Util.currentfrom.equals(froms[0])){
        //	msg.setIsReaded("1");// 暂且默认为已读
        //	Log.v("PAOPAO","已读");
        //}else{
        msg.setIsReaded("0");// 暂且默认为未读
        //	Log.v("PAOPAO","未读");
        //}

        msg.setType("chat_text");
        msgDao.insert(msg);
        session.setType("chat_text");
        session.setContent(word);
        //Log.v("PAOPAO","from:"+froms[0]+",to:"+tos[0]);
        if (sessionDao.isContent(zhuboid, Util.userid)) {// 判断最近联系人列表是否已存在记录
            sessionDao.updateSession(session);
        } else {
            sessionDao.insertSession(session);
        }
    }

    private String netRequest() throws IOException {
        OkHttpClient httpClient = new OkHttpClient();
        Request request = new Request.Builder().get().url(Util.url + "/uiface/ar?p0=A-user-search&p1=getrandommsg").build();
        Call call = httpClient.newCall(request);
        Response response = call.execute();

        int code = response.code();
        if (code == 200) {
            return response.body().string();
        } else {
            return null;
        }
    }
}
