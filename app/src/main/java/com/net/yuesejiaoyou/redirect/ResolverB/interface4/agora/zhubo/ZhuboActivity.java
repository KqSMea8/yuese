package com.net.yuesejiaoyou.redirect.ResolverB.interface4.agora.zhubo;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.Toast;

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.classroot.interface4.openfire.core.Utils;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.db.Const;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.agora.IAgoraVideoEventListener;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.agora.ICmdListener;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.agora.IUserInfoHandler;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.agora.IVideoHandler;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.agora.P2PVideoConst;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.agora.VideoMessageManager;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.BaseActivity;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.URL;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.fragment.CallingFragment;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.fragment.GukeFromCallingFragment;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.utils.LogUtil;
import com.xiaojigou.luo.camfilter.widget.LuoGLCameraView;
import com.xiaojigou.luo.xjgarsdk.XJGArSdkApi;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;

import javax.microedition.khronos.egl.EGLContext;

import io.agora.rtc.Constants;
import io.agora.rtc.IRtcEngineEventHandler;
import io.agora.rtc.RtcEngine;
import io.agora.rtc.video.AgoraVideoFrame;
import io.agora.rtc.video.VideoCanvas;
import okhttp3.Call;

/**
 * Created by Administrator on 2018\8\17 0017.
 * Activity管理： 命令消息、声网一对一视频操作
 */

public class ZhuboActivity extends BaseActivity implements IUserInfoHandler, IVideoHandler {

    private GukeInfo gukeInfo;
    private ICmdListener gukeCmdListener;
    private IAgoraVideoEventListener videoEventListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |               //这个在锁屏状态下
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                        | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // 获取顾客信息
        Bundle bundle = getIntent().getExtras();
        gukeInfo = bundle.getParcelable("guke");

        LogUtil.i("ttt", "---" + gukeInfo.toString());
        // 初始化IM消息
        initIM();

        // 如果是主播回拨则先初始化声网
        if (gukeInfo.getDirect() == P2PVideoConst.ZHUBO_CALL_GUKE) {
            initVideo();
        }

        // 显示默认fragment
        if (gukeInfo.getDirect() == P2PVideoConst.GUKE_CALL_ZHUBO) {
            startFragment(new GukeFromCallingFragment());
        } else if (gukeInfo.getDirect() == P2PVideoConst.ZHUBO_CALL_GUKE) {
            startFragment(new CallingFragment());//被叫
        } else {
            Toast.makeText(this, "没有适合的接听页面类型", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_zhubo;
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

        gukeCmdListener = (ICmdListener) fragment;
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                showToast("正在通话中...");
                break;
            default:
                break;
        }

        return false;
    }

    private void initIM() {
        VideoMessageManager.initIM(gukeInfo.getGukeId());
        // 注册消息监听,并发送一对一视频邀请消息
        VideoMessageManager.setCmdMessageListener(cmdMsgListener);
    }

    // 消息监听
    private VideoMessageManager.CmdMsgListener cmdMsgListener = new VideoMessageManager.CmdMsgListener() {
        @Override
        public void onCmdMessageListener(String fromId, String fromNickname, String fromHeadpic, String cmd, final String roomid) {
            Log.v("jj", "zhubo_bk-onCmdMessageListener(): " + fromId + "," + fromNickname + "," + fromHeadpic + "," + cmd);

            if (gukeInfo.getRoomid().equals(roomid) == false) {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(ZhuboActivity.this, "roomid不匹配", Toast.LENGTH_SHORT).show();
                        LogDetect.send("01107", "********** gukeRoomid=" + gukeInfo.getRoomid() + ",roomid=" + roomid + " **********");
                    }
                });
                return;
            }
            switch (cmd) {
                case VideoMessageManager.VIDEO_U2A_USER_CALL:    // 用户邀请

                    break;
                case VideoMessageManager.VIDEO_U2A_USER_HANGUP:    // 用户邀请-用户挂断
                    gukeCmdListener.onCmdHangup();
                    break;
                case VideoMessageManager.VIDEO_U2A_USER_TIMEUP:    // 用户邀请-用户超时
                    gukeCmdListener.onCmdTimeup();
                    break;
                case VideoMessageManager.VIDEO_A2U_USER_ACCEPT: // 主播邀请-用户接受

                    gukeCmdListener.onCmdAccept();
                    break;
                case VideoMessageManager.VIDEO_A2U_USER_HANGUP: // 主播邀请-用户挂断

                    gukeCmdListener.onCmdHangup();
                    break;
                case VideoMessageManager.VIDEO_A2U_USER_TIMEUP: // 主播邀请-用户超时

                    gukeCmdListener.onCmdTimeup();
                    break;
            }
        }
    };
    // 控制消息(命令)相关 end
    //----------------------------------------------------------------------------------------------


    //----------------------------------------------------------------------------------------------
    // 声网视频相关 start
    private static final int PERMISSION_REQ_ID_RECORD_AUDIO = 22;
    private static final int PERMISSION_REQ_ID_CAMERA = PERMISSION_REQ_ID_RECORD_AUDIO + 1;
    private RtcEngine mRtcEngine;
    private LuoGLCameraView mCustomizedCameraRenderer;
    private SurfaceView remoteSurface;
    private volatile boolean mJoined = false;

    private void initVideo() {
        LogUtil.i("ttt", "-----initVideo---------");
        initAgoraEngineAndJoinChannel();
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
        //joinChannel();               // Tutorial Step 4
    }

    private final IRtcEngineEventHandler mRtcEventHandler = new IRtcEngineEventHandler() { // Tutorial Step 1
        @Override
        public void onFirstRemoteVideoDecoded(final int uid, int width, int height, int elapsed) { // Tutorial Step 5
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    Log.v("TTT", "zhubo-setupRemoteVideo: ," + Thread.currentThread().getId());
                    SurfaceView surfaceView = RtcEngine.CreateRendererView(ZhuboActivity.this);
                    Log.v("TTT", "zhubo-setupRemoteVideo: 2");
                    mRtcEngine.setupRemoteVideo(new VideoCanvas(surfaceView, VideoCanvas.RENDER_MODE_HIDDEN, uid));
                    Log.v("TTT", "zhubo-setupRemoteVideo: 3");
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
            mRtcEngine.setChannelProfile(Constants.CHANNEL_PROFILE_LIVE_BROADCASTING);
            mRtcEngine.setParameters("{\"rtc.log_filter\":65535}");
            mRtcEngine.setLogFile(Environment.getExternalStorageState() + File.separator + "rtc_sdk.log");
        } catch (Exception e) {
            //Log.e(LOG_TAG, Log.getStackTraceString(e));
            throw new RuntimeException("NEED TO check rtc sdk init fatal error\n" + Log.getStackTraceString(e));
        }
    }

    private void setupVideoProfile() {
        mRtcEngine.enableVideo();

        if (mRtcEngine.isTextureEncodeSupported()) {
            Log.d("TTT", "before setExternalVideoSource");
            mRtcEngine.setExternalVideoSource(true, true, true);
        } else {
            throw new RuntimeException("Can not work on device do not supporting texture" + mRtcEngine.isTextureEncodeSupported());
        }
        mRtcEngine.setVideoProfile(Constants.VIDEO_PROFILE_720P, true);
//        mRtcEngine.setDefaultAudioRoutetoSpeakerphone(true);
//        mRtcEngine.setSpeakerphoneVolume(100);
//        mRtcEngine.setRecordingAudioFrameParameters(16000,1,RAW_AUDIO_FRAME_OP_MODE_READ_ONLY ,1024);
    }

    // Tutorial Step 3
    private void setupLocalVideo() {
        if (mCustomizedCameraRenderer == null) {
            mCustomizedCameraRenderer = new LuoGLCameraView(this);
        }
        mCustomizedCameraRenderer.setOnFrameAvailableHandler(new LuoGLCameraView.OnFrameAvailableListener() {
            @Override
            public void onFrameAvailable(int texture, EGLContext eglContext, int rotation) {
                AgoraVideoFrame vf = new AgoraVideoFrame();
                vf.format = AgoraVideoFrame.FORMAT_TEXTURE_2D;
                vf.timeStamp = System.currentTimeMillis();
                vf.stride = 480;
                vf.height = 640;
                vf.textureID = texture;
                vf.syncMode = true;
                vf.eglContext11 = eglContext;
                vf.transform = new float[]{
                        1.0f, 0.0f, 0.0f, 0.0f,
                        0.0f, 1.0f, 0.0f, 0.0f,
                        0.0f, 0.0f, 1.0f, 0.0f,
                        0.0f, 0.0f, 0.0f, 1.0f
                };

                boolean result = false;
                if (mRtcEngine != null) {
                    result = mRtcEngine.pushExternalVideoFrame(vf);
                }

            }
        });

        mCustomizedCameraRenderer.setOnEGLContextHandler(new LuoGLCameraView.OnEGLContextListener() {
            @Override
            public void onEGLContextReady(EGLContext eglContext) {
                if (!mJoined) {
                    Log.v("TTT", "before joinChannel(" + gukeInfo.getRoomid() + ")");
                    joinChannel();
                    mJoined = true;
                }
            }
        });
        XJGArSdkApi.XJGARSDKSetOptimizationMode(2);
        XJGArSdkApi.XJGARSDKSetShowStickerPapers(false);
    }

    private void joinChannel() {
        mRtcEngine.setClientRole(Constants.CLIENT_ROLE_BROADCASTER);    //01107 , null);
        mRtcEngine.joinChannel(null, gukeInfo.getRoomid(), "Extra Optional Data", 0); // if you do not specify the uid, we will generate the uid for you
    }

    @Override
    public void startVideo() {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                ZhuboVideoFragment fragment = new ZhuboVideoFragment();
                videoEventListener = fragment;

                if (gukeInfo.getDirect() == P2PVideoConst.GUKE_CALL_ZHUBO) {
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
        return mCustomizedCameraRenderer;
//        if (mCustomizedCameraRenderer != null) {
//            ViewGroup parent = (ViewGroup) mCustomizedCameraRenderer.getParent();
//            if (parent != null) {
//                parent.removeView(mCustomizedCameraRenderer);
//            }
//            return mCustomizedCameraRenderer;
//        } else {
//            return null;
//        }
    }

    @Override
    public SurfaceView getRemoteSurfaceView() {
//        if(remoteSurface != null) {
//            ViewGroup parent = (ViewGroup)remoteSurface.getParent();
//            if(parent != null) {
//                parent.removeView(remoteSurface);
//            }
//            return remoteSurface;
//        } else {
//            return null;
//        }
        return remoteSurface;
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
        return gukeInfo == null ? null : gukeInfo.getGukeId();
    }

    @Override
    public String getFromUserName() {
        return gukeInfo == null ? null : gukeInfo.getGukeName();
    }

    @Override
    public String getFromUserHeadpic() {
        return gukeInfo == null ? null : gukeInfo.getGukeHeadpic();
    }

    @Override
    public String getRoomid() {
        return gukeInfo == null ? null : gukeInfo.getRoomid();
    }

    @Override
    public int getDirect() {
        return gukeInfo == null ? null : gukeInfo.getDirect();
    }

    @Override
    public String getYuyue() {
        return gukeInfo.getYuyue();
    }
    // 不管是顾客还是主播，获取fromuser(对方)信息的接口 start
    //----------------------------------------------------------------------------------------------


    //----------------------------------------------------------------------------------------------
    // 页面跳转相关 start
    private static void startActivity(Context context, GukeInfo guke, int direct) {
        Intent intent = new Intent(context.getApplicationContext(), ZhuboActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("guke", guke);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static void startCallGuke(final Context context, final GukeInfo guke) {
        OkHttpUtils
                .post()
                .url(URL.URL_PUSHVIDEO)
                .addParams("param1", "")
                .addParams("param2", Util.userid)
                .addParams("param3", Util.nickname)
                .addParams("param4", Util.headpic)
                .addParams("param5", guke.getGukeId())
                .addParams("param6", guke.getRoomid())
                .addParams("param7", VideoMessageManager.VIDEO_A2U_ANCHOR_CALL)
                .addParams("param8", guke.getYuyue())
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(context, "拨打失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            final String message = "邀0请1视2频" + Const.SPLIT + Const.ACTION_MSG_ZB_RESERVE
                                    + Const.SPLIT + guke.getRoomid() + Const.SPLIT + Util.nickname + Const.SPLIT + Util.headpic;
                            Utils.sendmessage(Utils.xmppConnection, message, guke.getGukeId());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        startActivity(context, guke, P2PVideoConst.ZHUBO_CALL_GUKE);
                    }

                });

    }

    public static void callFromGuke(Context context, GukeInfo guke) {
        startActivity(context, guke, P2PVideoConst.GUKE_CALL_ZHUBO);
    }

}
