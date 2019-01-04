package com.net.yuesejiaoyou.redirect.ResolverD.interface4.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.classroot.interface4.openfire.core.Utils;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.db.Const;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa1.smack.XMPPException;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack.SmackException;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.redirect.ResolverB.interface3.UsersThread_01158B;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.agora.IActivityListener;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.agora.IAgoraVideoEventListener;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.agora.ICmdListener;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.agora.IUserInfoHandler;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.agora.IVideoHandler;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.agora.P2PVideoConst;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.agora.VideoMessageManager;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.URL;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.fragment.CallingFragment;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.fragment.GukeFromCallingFragment;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.fragment.GukeVideoFragment;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.agora.guke.ZhuboInfo;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.BaseActivity;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.utils.LogUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.DialogCallback;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.agora.rtc.Constants;
import io.agora.rtc.IRtcEngineEventHandler;
import io.agora.rtc.RtcEngine;
import io.agora.rtc.video.VideoCanvas;
import okhttp3.Call;

import static io.agora.rtc.Constants.RAW_AUDIO_FRAME_OP_MODE_READ_ONLY;

/**
 * Created by Administrator on 2018\8\17 0017.
 */

public class GukeActivity extends BaseActivity implements IUserInfoHandler, IVideoHandler {

    private ZhuboInfo zhuboInfo;
    private ICmdListener zhuboCmdListener;
    private IActivityListener activityListener;
    private IAgoraVideoEventListener videoEventListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |               //这个在锁屏状态下
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                        | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        Bundle bundle = getIntent().getExtras();
        zhuboInfo = bundle.getParcelable("zhubo");
        LogUtil.i("ttt", "---" + zhuboInfo);

        initIM();

        if (zhuboInfo.getDirect() == P2PVideoConst.GUKE_CALL_ZHUBO) {
            initVideo();
        }

        //主叫
        if (zhuboInfo.getDirect() == P2PVideoConst.GUKE_CALL_ZHUBO) {
            startFragment(new CallingFragment());//主叫
        } else if (zhuboInfo.getDirect() == P2PVideoConst.ZHUBO_CALL_GUKE) {
            startFragment(new GukeFromCallingFragment());//被叫
        } else {
            showToast("没有适合的接听页面类型");
            LogUtil.i("ttt", "没有适合的接听页面类型");
            finish();
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_guke;
    }


    @Override
    public boolean statusBarFont() {
        return false;
    }

    @Override
    public int statusBarColor() {
        return R.color.transparent;
    }

    private void startFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.lay_content, fragment);
        transaction.commit();

        zhuboCmdListener = (ICmdListener) fragment;
        activityListener = (IActivityListener) fragment;
        videoEventListener = (IAgoraVideoEventListener) fragment;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 声网相关
        if (mRtcEngine != null) {
            leaveChannel();
            RtcEngine.destroy();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (activityListener != null) {
            activityListener.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (activityListener != null) {
            if (activityListener.onKeyUp(keyCode, event)) {
                return true;
            }
        }
        return super.onKeyUp(keyCode, event);
    }

    private void initIM() {
        VideoMessageManager.initIM(zhuboInfo.getZhuboId());
        // 注册消息监听,并发送一对一视频邀请消息
        VideoMessageManager.setCmdMessageListener(cmdMsgListener);
    }

    // 消息监听
    private VideoMessageManager.CmdMsgListener cmdMsgListener = new VideoMessageManager.CmdMsgListener() {
        @Override
        public void onCmdMessageListener(String fromId, String fromNickname, String fromHeadpic, String cmd, final String roomid) {

            if (zhuboInfo.getRoomid().equals(roomid) == false) {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(GukeActivity.this, "roomid不匹配(" + zhuboInfo.getRoomid() + "," + roomid + ")", Toast.LENGTH_SHORT).show();
                    }
                });
                return;
            }
            LogUtil.i("ttt", "----" + cmd);
            switch (cmd) {
                case VideoMessageManager.VIDEO_A2U_ANCHOR_CALL:    // 主播邀请
                    break;
                case VideoMessageManager.VIDEO_A2U_ANCHOR_HANGUP:    // 主播邀请-主播挂断
                    zhuboCmdListener.onCmdHangup();
                    break;
                case VideoMessageManager.VIDEO_A2U_ANCHOR_TIMEUP:    // 主播邀请-主播超时
                    zhuboCmdListener.onCmdTimeup();
                    break;
                case VideoMessageManager.VIDEO_U2A_ANCHOR_ACCEPT:    // 用户邀请-主播接受
                    zhuboCmdListener.onCmdAccept();
                    break;
                case VideoMessageManager.VIDEO_U2A_ANCHOR_HANGUP:    // 用户邀请-主播挂断
                    zhuboCmdListener.onCmdHangup();
                    break;
                case VideoMessageManager.VIDEO_U2A_ANCHOR_TIMEUP:    // 用户邀请-主播超时
                    zhuboCmdListener.onCmdTimeup();
                    break;
            }
        }
    };

    private static final int PERMISSION_REQ_ID_RECORD_AUDIO = 22;
    private static final int PERMISSION_REQ_ID_CAMERA = PERMISSION_REQ_ID_RECORD_AUDIO + 1;
    private RtcEngine mRtcEngine;
    private SurfaceView localSurface;
    private SurfaceView remoteSurface;
    private volatile boolean mJoined = false;

    private void initVideo() {
        if (checkSelfPermission(Manifest.permission.RECORD_AUDIO, PERMISSION_REQ_ID_RECORD_AUDIO) && checkSelfPermission(Manifest.permission.CAMERA, PERMISSION_REQ_ID_CAMERA)) {
            initAgoraEngineAndJoinChannel();
        }
    }

    public boolean checkSelfPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(this,
                permission)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{permission},
                    requestCode);
            return false;
        }
        return true;
    }

    private void initAgoraEngineAndJoinChannel() {
        initializeAgoraEngine();     // Tutorial Step 1
        setupVideoProfile();         // Tutorial Step 2
        setupLocalVideo();           // Tutorial Step 3
        joinChannel();               // Tutorial Step 4
    }

    private final IRtcEngineEventHandler mRtcEventHandler = new IRtcEngineEventHandler() { // Tutorial Step 1
        @Override
        public void onFirstRemoteVideoDecoded(final int uid, int width, int height, int elapsed) { // Tutorial Step 5

            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    Log.v("TTT", "guke-setupRemoteVideo: ," + Thread.currentThread().getId());
                    SurfaceView surfaceView = RtcEngine.CreateRendererView(GukeActivity.this);
                    Log.v("TTT", "guke-setupRemoteVideo: 2: " + surfaceView);
                    mRtcEngine.setupRemoteVideo(new VideoCanvas(surfaceView, VideoCanvas.RENDER_MODE_HIDDEN, uid));
                    Log.v("TTT", "guke-setupRemoteVideo: 3");
                    remoteSurface = surfaceView;

                    if (videoEventListener != null) {
                        videoEventListener.onFirstRemoteVideoDecoded();
                    }
                }
            });

        }

        @Override
        public void onJoinChannelSuccess(String channel, int uid, int elapsed) {
            if (videoEventListener != null) {
                videoEventListener.onJoinChannelSuccess();
            }
        }

        @Override
        public void onUserOffline(int uid, int reason) { // Tutorial Step 7
            if (videoEventListener != null) {
                videoEventListener.onUserOffline();
            }
        }

        @Override
        public void onUserMuteVideo(final int uid, final boolean muted) { // Tutorial Step 10
            if (videoEventListener != null) {
                videoEventListener.onUserMuteVideo(muted);
            }
        }
    };

    private void initializeAgoraEngine() {
        try {
            mRtcEngine = RtcEngine.create(getBaseContext(), getString(R.string.agora_app_id), mRtcEventHandler);
            mRtcEngine.setParameters("{\"rtc.log_filter\":65535}");
            mRtcEngine.setLogFile(Environment.getExternalStorageState() + File.separator + "rtc_sdk.log");
        } catch (Exception e) {
            //Log.e(LOG_TAG, Log.getStackTraceString(e));

            throw new RuntimeException("NEED TO check rtc sdk init fatal error\n" + Log.getStackTraceString(e));
        }
    }

    private void setupVideoProfile() {
        mRtcEngine.enableVideo();
        mRtcEngine.setVideoProfile(Constants.VIDEO_PROFILE_720P, false);

        mRtcEngine.setDefaultAudioRoutetoSpeakerphone(true);
        mRtcEngine.setSpeakerphoneVolume(100);
        mRtcEngine.setRecordingAudioFrameParameters(16000, 1, RAW_AUDIO_FRAME_OP_MODE_READ_ONLY, 1024);
    }

    // Tutorial Step 3
    private void setupLocalVideo() {
        SurfaceView surfaceView = RtcEngine.CreateRendererView(this);
        surfaceView.setZOrderMediaOverlay(true);
        localSurface = surfaceView;
        mRtcEngine.setupLocalVideo(new VideoCanvas(surfaceView, VideoCanvas.RENDER_MODE_HIDDEN, 0));
    }

    private void joinChannel() {
        mRtcEngine.setClientRole(Constants.CLIENT_ROLE_BROADCASTER);    //01107 , null);
        mRtcEngine.joinChannel(null, zhuboInfo.getRoomid(), "Extra Optional Data", 0); // if you do not specify the uid, we will generate the uid for you
//        mRtcEngine.joinChannel(null, "demoChannel1231111", "Extra Optional Data", 0);
    }

    @Override
    public void startVideo() {

        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                GukeVideoFragment fragment = new GukeVideoFragment();
                videoEventListener = fragment;
                activityListener = fragment;

                if (zhuboInfo.getDirect() == P2PVideoConst.ZHUBO_CALL_GUKE) {
                    initVideo();
                }
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                transaction.replace(R.id.lay_content, fragment);

                transaction.commit();
            }
        });

    }

    @Override
    public SurfaceView getLocalSurfaceView() {
        return localSurface == null ? null : localSurface;
    }

    @Override
    public SurfaceView getRemoteSurfaceView() {
        if (remoteSurface != null) {
            ViewGroup parent = (ViewGroup) remoteSurface.getParent();
            if (parent != null) {
                parent.removeView(remoteSurface);
            }
            return remoteSurface;
        } else {
            return null;
        }
    }

    @Override
    public void enableAudio() {
        mRtcEngine.muteAllRemoteAudioStreams(false);
    }

    @Override
    public void muteLocalVideoStream(boolean bMute) {
        mRtcEngine.muteLocalVideoStream(bMute);
    }

    @Override
    public void muteLocalAudioStream(boolean bMute) {
        mRtcEngine.muteLocalAudioStream(bMute);
    }

    @Override
    public void switchCamera() {
        mRtcEngine.switchCamera();
    }

    @Override
    public void leaveChannel() {
        mRtcEngine.leaveChannel();
    }
    // 声网视频相关 start
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // 不管是顾客还是主播，获取fromuser(对方)信息的接口 start
    @Override
    public String getFromUserId() {
        return zhuboInfo == null ? "" : zhuboInfo.getZhuboId();
    }

    @Override
    public String getFromUserName() {
        return zhuboInfo == null ? null : zhuboInfo.getZhuboName();
    }

    @Override
    public String getFromUserHeadpic() {
        return zhuboInfo == null ? null : zhuboInfo.getZhuboHeadpic();
    }

    @Override
    public String getRoomid() {
        return zhuboInfo == null ? null : zhuboInfo.getRoomid();
    }

    @Override
    public int getDirect() {
        return zhuboInfo == null ? null : zhuboInfo.getDirect();
    }

    @Override
    public String getYuyue() {
        return zhuboInfo.getYuyue();
    }

    private static void startActivity(Context context, ZhuboInfo zhubo) {
        Intent intent = new Intent(context, GukeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("zhubo", zhubo);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static void startCallZhubo(final Context context, final ZhuboInfo zhubo) {
        OkHttpUtils.post()
                .url(URL.URL_CALLVIDEO)
                .addParams("param1", "")
                .addParams("param2", Util.userid)
                .addParams("param3", Util.nickname)
                .addParams("param4", Util.headpic)
                .addParams("param5", zhubo.getZhuboId())
                .addParams("param6", zhubo.getRoomid())
                .addParams("param7", VideoMessageManager.VIDEO_U2A_USER_CALL)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            final String message = "邀0请1视2频" + Const.SPLIT + Const.ACTION_MSG_ONEINVITE
                                    + Const.SPLIT + zhubo.getRoomid() + Const.SPLIT +
                                    Util.nickname + Const.SPLIT + Util.headpic;
                            Utils.sendmessage(Utils.xmppConnection, message, zhubo.getZhuboId());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        startActivity(context, zhubo);
                    }
                });
    }

    public static void callFromZhubo(Context context, ZhuboInfo zhubo) {
        startActivity(context, zhubo);
    }
}
