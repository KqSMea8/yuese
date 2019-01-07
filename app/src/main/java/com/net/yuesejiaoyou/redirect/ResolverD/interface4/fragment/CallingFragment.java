package com.net.yuesejiaoyou.redirect.ResolverD.interface4.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
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


import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Administrator on 2018\8\17 0017.
 */

public class CallingFragment extends BaseFragment implements ICmdListener, IAgoraVideoEventListener {


    private IUserInfoHandler userInfoHandler;
    private IVideoHandler videoHandler;

    private Activity baseActivity;

    @BindView(R.id.photo)
    ImageView photo;
    @BindView(R.id.nickname)
    TextView nickname;
    @BindView(R.id.remote_video)
    FrameLayout remoteContainer;

    GeneralTimer callingTimer;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        baseActivity =  getActivity();

        userInfoHandler = (IUserInfoHandler) baseActivity;
        videoHandler = (IVideoHandler) baseActivity;


        ImageUtils.loadImage(userInfoHandler.getFromUserHeadpic(), photo);
        nickname.setText(userInfoHandler.getFromUserName());

        openCalling();
    }

    @Override
    protected int getContentView() {
        return R.layout.frag_calling;
    }

    @OnClick(R.id.exit_tuichu)
    public void huangUpClick() {
        // 发送挂断消息
        String strCmd;
        if (userInfoHandler.getDirect() == P2PVideoConst.GUKE_CALL_ZHUBO) {
            strCmd = VideoMessageManager.VIDEO_U2A_USER_HANGUP;
        } else if (userInfoHandler.getDirect() == P2PVideoConst.ZHUBO_CALL_GUKE) {
            strCmd = VideoMessageManager.VIDEO_A2U_ANCHOR_HANGUP;
        } else {
            strCmd = VideoMessageManager.VIDEO_NONE;
        }

        sendPushCmd(strCmd);

        final String message1 = "挂0断1邀2请" + Const.SPLIT + Const.ACTION_MSG_ONEINVITE
                + Const.SPLIT + userInfoHandler.getRoomid() + Const.SPLIT + Util.nickname + Const.SPLIT + Util.headpic;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Utils.sendmessage(Utils.xmppConnection, message1, userInfoHandler.getFromUserId());
                } catch (XMPPException | SmackException.NotConnectedException e) {
                    e.printStackTrace();

                }
            }
        }).start();

        cancelCalling();
    }


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

        // 初始化计时器
        callingTimer = new GeneralTimer(60);    // 60秒后自动挂断
        callingTimer.start(new GeneralTimer.TimerCallback() {
            @Override
            public void onTimerEnd() {
                //huangUpClick();
                // 发送超时消息
                String strCmd;
                if(userInfoHandler.getDirect() == P2PVideoConst.GUKE_CALL_ZHUBO) {
                    strCmd = VideoMessageManager.VIDEO_U2A_USER_TIMEUP;
                } else if(userInfoHandler.getDirect() == P2PVideoConst.ZHUBO_CALL_GUKE) {
                    strCmd = VideoMessageManager.VIDEO_A2U_ANCHOR_TIMEUP;
                } else {
                    strCmd = VideoMessageManager.VIDEO_NONE;
                }
                sendPushCmd(strCmd);

               cancelCalling();
            }
        });
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


    private void cancelCalling() {
        // 关闭音乐
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

        OkHttpUtils.post(this)
                .url(URL.URL_ONLINE)
                .addParams("param1", Util.userid)
                .addParams("param2", userInfoHandler.getFromUserId())
                .addParams("param3", "0")
                .addParams("param4", "1")
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

    //----------------------------------------------------------------------------------------------
    // 一对一视频命令消息接口 start
    @Override
    public void onCmdHangup() {
        cancelCalling();
    }

    @Override
    public void onCmdTimeup() {
        cancelCalling();
    }

    @Override
    public void onCmdAccept() {

    }

    @Override
    public void onFirstRemoteVideoDecoded() { // Tutorial Step 5
        SurfaceView remoteSurface = videoHandler.getRemoteSurfaceView();
        if (remoteSurface != null) {
            remoteContainer.addView(remoteSurface);
        }

        acceptCalling();
        // 预通0.5s然后再跳转页面
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                videoHandler.startVideo();
            }
        }, 500);
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
