package com.net.yuesejiaoyou.redirect.ResolverB.interface4.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.SurfaceView;

//import com.example.vliao.R;
//import com.example.vliao.uiface.guke_01160;
//import com.example.vliao.uiface.zhubo_bk;

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.YhApplicationA;
import com.net.yuesejiaoyou.redirect.ResolverB.uiface.AgoraRtcActivity;
import com.net.yuesejiaoyou.redirect.ResolverB.uiface.AgoraRtcActivity_zhubo;
import com.net.yuesejiaoyou.redirect.ResolverB.uiface.guke_01160;
import com.net.yuesejiaoyou.redirect.ResolverB.uiface.zhubo;

import io.agora.rtc.Constants;
import io.agora.rtc.IRtcEngineEventHandler;
import io.agora.rtc.RtcEngine;
import io.agora.rtc.video.VideoCanvas;

import static io.agora.rtc.Constants.RAW_AUDIO_FRAME_OP_MODE_READ_ONLY;

/**
 * Created by Administrator on 2018\6\27 0027.
 */

public class AgoraVideoManager_back {
    private static AgoraVideoManager_back instance;
    private static Context mContext;
    private static boolean isZhubo;
    private static boolean isHuibo;
    private static String userid;
    private static String msgbody;
    private static Thread agoraThread;
    private static Handler handler;
    private static boolean isStart = false;
    private static String curRoomid = "";

    private AgoraVideoManager_back(Context ctxt) {
        mContext = ctxt;
    }

    public static String getCurRoomid() {
        return curRoomid;
    }

    public static void init(Context ctxt, boolean isV) {
        instance = new AgoraVideoManager_back(ctxt);
        isZhubo = isV;
    }

    public static boolean isAgoraStart() {
        return isStart;
    }

    public static void close() {
        isStart = false;
        if(handler == null || instance == null || instance.mRtcEngine == null) {
            return;
        }
        handler.post(new Runnable() {

            @Override
            public void run() {
                synchronized (AgoraVideoManager.class) {
                    if (instance != null) {
                        instance.agoraListener = null;
                        RtcEngine engine = instance.mRtcEngine;
                        if (engine != null) {
                            engine.leaveChannel();
                        }
                        RtcEngine.destroy();
                    }
                    if (instance != null) {
                        instance.mRtcEngine = null;
                    }
                    handler = null;
                    instance = null;
                    mContext = null;
                    curRoomid = "";
                }
            }
        });
    }

    public static void startVideo(final String roomid) {

        curRoomid = roomid;
        if(instance == null) {
            return;
        }
        Log.v("TT","curRoomid-: "+curRoomid);
        agoraThread = new Thread(new Runnable() {

            @Override
            public void run() {
                synchronized (AgoraVideoManager.class) {
                    if(instance == null) {
                        Log.e("TT","AgoraVideoManager instance has been destroy");
                        return;
                    }
                    Looper.prepare();
                    Log.v("TT", "init-start: " + Thread.currentThread().getId());

                    instance.initializeAgoraEngine();     // Tutorial Step 1
                    instance.setupVideoProfile();         // Tutorial Step 2
                    instance.setupLocalVideo();           // Tutorial Step 3
                    instance.joinChannel(roomid);         // Tutorial Step 4
                    handler = new Handler();

                    Log.v("TT", "init-end: " + Thread.currentThread().getId());
                }
                // 消息循环是死循环，不能放到同步块里面，否则无法释放同步锁
                Looper.loop();
            }
        });
        agoraThread.start();
        isStart = true;
    }

    public static void startVideo(Context ctxt, String roomid, boolean isV) {
        init(ctxt, isV);
        startVideo(roomid);
    }

    public static void startVideo(Context ctxt, String roomid, boolean isV, String user_id, String msg_body, boolean bHuibo) {

        userid = user_id;
        msgbody = msg_body;
        isHuibo = bHuibo;
        startVideo(ctxt, roomid, isV);
    }

    public static SurfaceView getLocalSurfaceView() {
        return instance == null?null:instance.localSurface;
    }

    public static SurfaceView getRemoteSurfaceView() {
        return instance == null?null:instance.remoteSurface;
    }

    public static void switchCamera() {

        if(handler == null || instance == null || instance.mRtcEngine == null) {
            return;
        }
        handler.post(new Runnable() {

            @Override
            public void run() {
                instance.mRtcEngine.switchCamera();
            }
        });
    }

    public static void muteLocalAudioStream(final boolean mute) {
        if(handler == null || instance == null || instance.mRtcEngine == null) {
            return;
        }
        handler.post(new Runnable() {

            @Override
            public void run() {
                instance.mRtcEngine.muteLocalAudioStream(mute);
            }
        });
    }

    public static void muteLocalVideoStream(final boolean mute) {
        if(handler == null || instance == null || instance.mRtcEngine == null) {
            return;
        }
        handler.post(new Runnable() {

            @Override
            public void run() {
                instance.mRtcEngine.muteLocalVideoStream(mute);
            }
        });
    }

    public static void leaveChannel() {
        if(handler == null || instance == null || instance.mRtcEngine == null) {
            return;
        }
        handler.post(new Runnable() {

            @Override
            public void run() {
                instance.mRtcEngine.leaveChannel();
            }
        });
    }

    public static void enableAudio() {
        if(handler == null || instance == null || instance.mRtcEngine == null) {
            return;
        }
        handler.post(new Runnable() {

            @Override
            public void run() {
                instance.mRtcEngine.muteAllRemoteAudioStreams(false);
            }
        });
    }


    private RtcEngine mRtcEngine;
    private SurfaceView localSurface;
    private SurfaceView remoteSurface;
    // Tutorial Step 1
    private void initializeAgoraEngine() {
        try {
            mRtcEngine = RtcEngine.create(mContext, mContext.getString(R.string.agora_app_id), mRtcEventHandler);
            //mRtcEngine.setLogFile(Environment.getExternalStorageDirectory()+ File.separator+"agora_log.txt");
            mRtcEngine.setParameters("{\"rtc.log_filter\":65535}");
            mRtcEngine.setLogFile("/sdcard/filename.log");
        } catch (Exception e) {
            //Log.e(LOG_TAG, Log.getStackTraceString(e));
            throw new RuntimeException("NEED TO check rtc sdk init fatal error\n" + Log.getStackTraceString(e));
        }
    }

    // Tutorial Step 2
    private void setupVideoProfile() {
        mRtcEngine.enableVideo();
        mRtcEngine.setVideoProfile(Constants.VIDEO_PROFILE_480P, false);

        mRtcEngine.setDefaultAudioRoutetoSpeakerphone(true);
        mRtcEngine.setSpeakerphoneVolume(100);
        mRtcEngine.setRecordingAudioFrameParameters(16000,1,RAW_AUDIO_FRAME_OP_MODE_READ_ONLY ,1024);
        mRtcEngine.muteAllRemoteAudioStreams(true);
    }

    // Tutorial Step 3
    private void setupLocalVideo() {
        SurfaceView surfaceView = RtcEngine.CreateRendererView(instance.mContext);
        surfaceView.setZOrderMediaOverlay(true);
        localSurface = surfaceView;
        mRtcEngine.setupLocalVideo(new VideoCanvas(surfaceView, VideoCanvas.RENDER_MODE_HIDDEN, 0));
    }

    // Tutorial Step 4
    private void joinChannel(String roomid) {
        Log.v("TT","++joinChannel: "+roomid);
        mRtcEngine.joinChannel(null, roomid, "Extra Optional Data", 0); // if you do not specify the uid, we will generate the uid for you
        //Toast.makeText(this,"roomid: "+roomid,Toast.LENGTH_SHORT).show();
        Log.v("TT","--joinChannel: "+roomid);
    }

    private AgoraRtcListener agoraListener;

    private final IRtcEngineEventHandler mRtcEventHandler = new IRtcEngineEventHandler() { // Tutorial Step 1
        @Override
        public void onFirstRemoteVideoDecoded(final int uid, final int width, final int height, final int elapsed) { // Tutorial Step 5
            Log.v("TT","++onFirstRemoteVideoDecoded-: "+uid+","+width+","+height+","+elapsed);

            handler.post(new Runnable() {

                @Override
                public void run() {
                    setupRemoteVideo(uid, width, height, elapsed);
                }
            });

            Log.v("TT","--onFirstRemoteVideoDecoded-");
        }

        @Override
        public void onUserOffline(final int uid, final int reason) { // Tutorial Step 7
            handler.post(new Runnable() {
                @Override
                public void run() {
                    onRemoteUserLeft( uid, reason);
                }
            });
        }

        @Override
        public void onJoinChannelSuccess(final String channel, final int uid, final int elapsed) {
            handler.post(new Runnable() {

                @Override
                public void run() {
                    onJoinSuccess(channel, uid, elapsed);
                }
            });
        }

        @Override
        public void onUserMuteVideo(final int uid, final boolean muted) { // Tutorial Step 10
            handler.post(new Runnable() {

                @Override
                public void run() {
                    onRemoteMuteVideo(uid, muted);
                }
            });
        }
    };

    // Tutorial Step 5
    private void setupRemoteVideo(int uid, int width, int height, int elapsed) {
        Log.d("UU","setupRemoteVideo(): uid: "+uid);
        Log.v("TT","++setupRemoteVideo-: "+uid);

        if(isStart == false) {
            Log.v("TT","Agora 已经关闭-1");
            return;
        }

        Log.v("TT","setupRemoteVideo: "+isZhubo+","+Thread.currentThread().getId());
        SurfaceView surfaceView = RtcEngine.CreateRendererView(mContext);
        Log.v("TT","setupRemoteVideo: 2");
        mRtcEngine.setupRemoteVideo(new VideoCanvas(surfaceView, VideoCanvas.RENDER_MODE_HIDDEN, uid));
        Log.v("TT","setupRemoteVideo: 3");
        remoteSurface = surfaceView;

        Log.v("TT","setupRemoteVideo-: "+isZhubo);

        if(isZhubo) {
            new Thread(new Runnable() {

                @Override
                public void run() {

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.v("TT","before run-");
                    // 此线程需要延时一秒，所以再次判断一
                    if(isStart == false) {
                        Log.v("TT","Agora 已经关闭，停止下面的操作");
                        return;
                    }
                    // 如果当前页面在音乐播放页面或者一对一视频页面则不跳转页面
                    //String currentActivityName = mContext.getApplicationContext().getClass().getName();
                    YhApplicationA application = (YhApplicationA)mContext.getApplicationContext();
                    //Log.v("TT","Application context: "+currentActivityName);
                    Activity curActivity = application.getCurrentActivity();
                    String activityName = null;
                    if(curActivity != null) {
                        activityName = curActivity.getClass().getName();
                    }
                    Log.e("TT","curActivityName: "+activityName);
                    // 如果主播在音乐播放页面和一对一视频页面就不进行页面跳转
                    if(!"com.net.yuesejiaoyou.redirect.ResolverB.uiface.zhubo_bk".equals(activityName) &&
                            !"com.net.yuesejiaoyou.redirect.ResolverB.uiface.AgoraRtcActivity_zhubo".equals(activityName) &&
                            !"com.net.yuesejiaoyou.redirect.ResolverB.uiface.AgoraRtcActivity".equals(activityName)) {
                        if (isHuibo == false) {
                            Intent intent = new Intent();
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            intent.setClass(mContext.getApplicationContext(), zhubo.class);

                            Bundle bundle = new Bundle();
                            bundle.putString("yid_zhubo", userid);
                            //LogDetect.send(LogDetect.DataType.specialType, "01160 顾客id跳转到主播页
                            bundle.putString("msgbody", msgbody);
                            intent.putExtras(bundle);
                            mContext.startActivity(intent);
                        } else {
                            Intent intent = new Intent();
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            intent.setClass(mContext.getApplicationContext(), guke_01160.class);

                            Bundle bundle = new Bundle();
                            bundle.putString("yid_zhubo", userid);
                            //LogDetect.send(LogDetect.DataType.specialType,"01160 顾客id跳转到主播页
                            bundle.putString("msgbody", msgbody);
                            intent.putExtras(bundle);
                            mContext.startActivity(intent);
                        }
                    } else {
                        Log.e("TT","再次收到推流信息");
                        if("com.net.yuesejiaoyou.redirect.ResolverB.uiface.AgoraRtcActivity_zhubo".equals(activityName)) {
                            AgoraRtcActivity_zhubo zhuboActivity = (AgoraRtcActivity_zhubo)curActivity;
                            zhuboActivity.updateRemoteSurfaceView(remoteSurface);
                        }
                    }
                    Log.v("TT","after run");
                }
            }).start();
        } else {
            YhApplicationA application = (YhApplicationA)mContext.getApplicationContext();
            //Log.v("TT","Application context: "+currentActivityName);
            Activity curActivity = application.getCurrentActivity();
            String activityName = null;
            if(curActivity != null) {
                activityName = curActivity.getClass().getName();
            }
            if("com.net.yuesejiaoyou.redirect.ResolverB.uiface.AgoraRtcActivity".equals(activityName)) {
                AgoraRtcActivity zhuboActivity = (AgoraRtcActivity)curActivity;
                zhuboActivity.updateRemoteSurfaceView(remoteSurface);
            }
        }


        // 如果是主


        if(agoraListener != null) {
            agoraListener.onFirstRemoteVideoDecoded(uid, width, height, elapsed);
        }
    }

    // Tutorial Step 7
    private void onRemoteUserLeft(int uid, int reason) {
        if(agoraListener != null) {
            agoraListener.onUserOffline(uid, reason);
        }
    }

    private void onRemoteMuteVideo(int uid, boolean muted) {
        if(agoraListener != null) {
            agoraListener.onUserMuteVideo(uid, muted);
        }
    }

    private void onJoinSuccess(String channel, int uid, int elapsed) {
        if(agoraListener != null) {
            agoraListener.onJoinChannelSuccess(channel, uid, elapsed);
        }
    }

    public static void setAgoraRtcListener(AgoraRtcListener listener) {
        if(instance != null) {
            instance.agoraListener = listener;
        }
    }

    public interface AgoraRtcListener {
        public void onFirstRemoteVideoDecoded(int uid, int width, int height, int elapsed);
        public void onUserOffline(int uid, int reason);
        public void onJoinChannelSuccess(String channel, int uid, int elapsed);
        public void onUserMuteVideo(int uid, boolean muted);
    }
}
