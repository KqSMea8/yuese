package com.net.yuesejiaoyou.redirect.ResolverD.interface4.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.openfire.core.Utils;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.db.Const;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa1.smack.XMPPException;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack.SmackException;
import com.net.yuesejiaoyou.classroot.interface4.util.GeneralTimer;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.agora.IActivityListener;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.agora.IAgoraVideoEventListener;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.agora.ICmdListener;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.agora.IUserInfoHandler;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.agora.IVideoHandler;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.agora.P2PVideoConst;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.agora.VideoMessageManager;
import com.net.yuesejiaoyou.redirect.ResolverB.uiface.MusicUtil;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.URL;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.activity.GukeActivity;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.utils.ImageUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import net.sf.json.JSONObject;


import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Administrator on 2018\8\17 0017.
 */

public class GukeFromCallingFragment extends BaseFragment implements ICmdListener, IActivityListener, IAgoraVideoEventListener {


    private IUserInfoHandler userInfoHandler;
    private IVideoHandler videoHandler;

    private Activity baseActivity;

    @BindView(R.id.lay_progress)
    RelativeLayout layWait;
    private GeneralTimer callingTimer;
    @BindView(R.id.photo)
    ImageView photo;
    @BindView(R.id.nickname)
    TextView nickname;
    @BindView(R.id.remote_video)
    FrameLayout remoteContainer;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        baseActivity = getActivity();

        userInfoHandler = (IUserInfoHandler) baseActivity;
        videoHandler = (IVideoHandler) baseActivity;

        ImageUtils.loadImage(userInfoHandler.getFromUserHeadpic(), photo);
        nickname.setText(userInfoHandler.getFromUserName());

        openCalling();
    }

    @Override
    protected int getContentView() {
        return R.layout.frag_callingfrom;
    }

    @OnClick(R.id.exit_login)
    public void acceptClick() {
        layWait.setVisibility(View.VISIBLE);
        checkRoomInfo();
    }

    @OnClick(R.id.exit_tuichu)
    public void refuseClick() {
        String strCmd;
        if (userInfoHandler.getDirect() == P2PVideoConst.GUKE_CALL_ZHUBO) {
            strCmd = VideoMessageManager.VIDEO_U2A_ANCHOR_HANGUP;
        } else if (userInfoHandler.getDirect() == P2PVideoConst.ZHUBO_CALL_GUKE) {
            strCmd = VideoMessageManager.VIDEO_A2U_USER_HANGUP;
        } else {
            strCmd = VideoMessageManager.VIDEO_NONE;
        }
        sendPushCmd(strCmd);

        final String message1 = "拒0绝1邀2请" + Const.SPLIT + Const.ACTION_MSG_ZB_RESERVE
                + Const.SPLIT + userInfoHandler.getRoomid() + Const.SPLIT + Util.nickname + Const.SPLIT + Util.headpic;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Utils.sendmessage(Utils.xmppConnection, message1, userInfoHandler.getFromUserId());
                } catch (XMPPException | SmackException.NotConnectedException e) {

                }
            }
        }).start();
        cancelCalling(false);
    }

    private void sendPushCmd(String strCmd) {
        OkHttpUtils.post(this)
                .url(URL.URL_PUSHCMD)
                .addParams("param1", "")
                .addParams("param2", Util.userid)
                .addParams("param3", Util.nickname)
                .addParams("param4", Util.headpic)
                .addParams("param5", userInfoHandler.getFromUserId())
                .addParams("param6", userInfoHandler.getRoomid())
                .addParams("param7", strCmd)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {

                    }
                });


    }

    private void checkRoomInfo() {
        OkHttpUtils.post(this)
                .url(URL.BASE_URL + "/memberB?p0=A-user-search&p1=checkroominfo&p2=&p3="
                        + Util.userid + "&p4=" + userInfoHandler.getRoomid() + "&p5=" + userInfoHandler.getFromUserId())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        showToast("网络异常");
                        // 发送挂断消息
                        String strCmd;
                        if (userInfoHandler.getDirect() == P2PVideoConst.GUKE_CALL_ZHUBO) {
                            strCmd = VideoMessageManager.VIDEO_U2A_ANCHOR_HANGUP;
                        } else if (userInfoHandler.getDirect() == P2PVideoConst.ZHUBO_CALL_GUKE) {
                            strCmd = VideoMessageManager.VIDEO_A2U_USER_HANGUP;
                        } else {
                            strCmd = VideoMessageManager.VIDEO_NONE;
                        }
                        sendPushCmd(strCmd);

                        cancelCalling(false);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        JSONObject jsonObj = JSONObject.fromObject(response);
                        if ("yes".equals(jsonObj.getString("result"))) {
                            // 发送接听消息
                            String strCmd;
                            if (userInfoHandler.getDirect() == P2PVideoConst.GUKE_CALL_ZHUBO) {
                                strCmd = VideoMessageManager.VIDEO_U2A_ANCHOR_ACCEPT;
                            } else if (userInfoHandler.getDirect() == P2PVideoConst.ZHUBO_CALL_GUKE) {
                                strCmd = VideoMessageManager.VIDEO_A2U_USER_ACCEPT;
                            } else {
                                strCmd = VideoMessageManager.VIDEO_NONE;
                            }

                            sendPushCmd(strCmd);

                            acceptCalling();

                            videoHandler.startVideo();
                        } else if ("no".equals(jsonObj.getString("result"))) {
                            showToast("对方已挂断");
                            cancelCalling(false);
                            baseActivity.finish();
                        } else if ("lackmoney".equals(jsonObj.getString("result"))) {
                            showToast("余额不足，请充值");
                            // 发送挂断消息
                            String strCmd;
                            if (userInfoHandler.getDirect() == P2PVideoConst.GUKE_CALL_ZHUBO) {
                                strCmd = VideoMessageManager.VIDEO_U2A_ANCHOR_HANGUP;
                            } else if (userInfoHandler.getDirect() == P2PVideoConst.ZHUBO_CALL_GUKE) {
                                strCmd = VideoMessageManager.VIDEO_A2U_USER_HANGUP;
                            } else {
                                strCmd = VideoMessageManager.VIDEO_NONE;
                            }

                            sendPushCmd(strCmd);

                            final String message1 = "拒0绝1邀2请" + Const.SPLIT + Const.ACTION_MSG_ZB_RESERVE
                                    + Const.SPLIT + userInfoHandler.getRoomid() + Const.SPLIT + Util.nickname + Const.SPLIT + Util.headpic;
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        //LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"before sendMessage()");
                                        Utils.sendmessage(Utils.xmppConnection, message1, userInfoHandler.getFromUserId());
                                    } catch (XMPPException | SmackException.NotConnectedException e) {
                                        e.printStackTrace();

                                    }
                                }
                            }).start();

                            baseActivity.finish();
                        }
                    }
                });

    }

    /**
     * 开始进入拨打或被拨打状态
     */
    private void openCalling() {
        // 开始播放音乐
        MusicUtil.playSound(1, 100);

        OkHttpUtils.post(this)
                .url(URL.URL_MOD_MANG)
                .addParams("param1", Util.userid)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {

                    }
                });

        callingTimer = new GeneralTimer(60);
        callingTimer.start(new GeneralTimer.TimerCallback() {

            @Override
            public void onTimerEnd() {
                refuseClick();
            }
        });
    }


    private void cancelCalling(boolean isFromTimer) {
        MusicUtil.stopPlay();
        callingTimer.stop();

        OkHttpUtils.post(this)
                .url(URL.URL_UPDATESTATE)
                .addParams("param1", Util.userid)
                .addParams("param2", userInfoHandler.getFromUserId())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {

                    }
                });

        OkHttpUtils.post(this)
                .url(URL.URL_REMOVECALL)
                .addParams("param1", "")
                .addParams("param2", userInfoHandler.getRoomid())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {

                    }
                });


        baseActivity.finish();
    }

    /**
     * 接受拨打
     */
    private void acceptCalling() {

        // 关闭音乐
        MusicUtil.stopPlay();
        // 关闭定时器
        callingTimer.stop();

        OkHttpUtils.post(this)
                .url(URL.URL_REMOVECALL)
                .addParams("param1", "")
                .addParams("param2", userInfoHandler.getRoomid())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {

                    }
                });
        //finish();
    }


    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                showToast("正在通话...");
                return true;
        }
        return false;
    }


    @Override
    public void onCmdHangup() {
        cancelCalling(false);
    }

    @Override
    public void onCmdTimeup() {
        cancelCalling(false);
    }

    @Override
    public void onCmdAccept() {
        acceptCalling();
    }

    @Override
    public void onFirstRemoteVideoDecoded() { // Tutorial Step 5

    }

    @Override
    public void onUserOffline() { // Tutorial Step 7

    }

    @Override
    public void onJoinChannelSuccess() {

    }

    @Override
    public void onUserMuteVideo(final boolean muted) { // Tutorial Step 10

    }

}
