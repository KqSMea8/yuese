package com.net.yuesejiaoyou.redirect.ResolverB.interface4.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.widget.FrameLayout;


import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.YhApplicationA;
import com.net.yuesejiaoyou.redirect.ResolverB.uiface.AgoraRtcActivity;
import com.net.yuesejiaoyou.redirect.ResolverB.uiface.AgoraRtcActivity_zhubo;
import com.net.yuesejiaoyou.redirect.ResolverB.uiface.guke_01160;
import com.net.yuesejiaoyou.redirect.ResolverB.uiface.zhubo;
import com.xiaojigou.luo.camfilter.widget.LuoGLCameraView;
import com.xiaojigou.luo.xjgarsdk.XJGArSdkApi;

import java.io.File;
import java.io.IOException;

import javax.microedition.khronos.egl.EGLContext;

import io.agora.rtc.Constants;
import io.agora.rtc.IRtcEngineEventHandler;
import io.agora.rtc.RtcEngine;
import io.agora.rtc.video.AgoraVideoFrame;
import io.agora.rtc.video.VideoCanvas;

import static io.agora.rtc.Constants.RAW_AUDIO_FRAME_OP_MODE_READ_ONLY;

/**
 * Created by Administrator on 2018\6\27 0027.
 */

public class AgoraVideoManager {
    private static AgoraVideoManager instance;
    private static Context mContext;
    private static boolean isZhubo;
    private static boolean isHuibo;
    private static String userid;
    private static String msgbody;
    private static Thread agoraThread;
    private static Handler handler;
    private static boolean isStart = false;
    private static String curRoomid = "";

    //private static boolean is

    private LuoGLCameraView mCustomizedCameraRenderer;
    private Activity curActivity;

    private AgoraVideoManager(Context ctxt) {
        mContext = ctxt;
    }

    public static String getCurRoomid() {
        return curRoomid;
    }

    public static void init(Context ctxt, boolean isV) {
        instance = new AgoraVideoManager(ctxt);
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

                        final Object lock = new Object();

                        if(isZhubo && instance.curActivity != null && !instance.curActivity.getClass().getSimpleName().equals("AgoraRtcActivity_zhubo") && instance.mCustomizedCameraRenderer != null) {
                            instance.curActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ViewGroup vg = (ViewGroup) instance.mCustomizedCameraRenderer.getParent();
                                    if(vg != null) {
                                        Log.v("TTT","########## (移除LuoGLCameraView) ##########");
                                        vg.removeView(instance.mCustomizedCameraRenderer);
                                        instance.mCustomizedCameraRenderer = null;
                                        synchronized (lock) {
                                            lock.notifyAll();
                                        }
                                    }
                                }
                            });

                            synchronized (lock) {
                                try {
                                    lock.wait();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

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
                    if(isZhubo == false) {
                        instance.joinChannel(roomid);         // Tutorial Step 4
                    }
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
        Log.v("TTT","AgoraVideoManager-startVideo(): roomid="+roomid+",isV="+isV+",user_id="+user_id+",msg_body="+msg_body+",bHuibo="+bHuibo);
        userid = user_id;
        msgbody = msg_body;
        isHuibo = bHuibo;
        startVideo(ctxt, roomid, isV);
    }

    public static SurfaceView getLocalSurfaceView() {
        Log.v("TTT","getLocalSurfaceView()");
        // 主线程中调用
        ViewGroup vg = (ViewGroup) instance.localSurface.getParent();
        if(vg != null) {
            Log.v("TTT","getLocalSurfaceView()-remove");
            vg.removeView(instance.localSurface);
        }
        return instance == null?null:instance.localSurface;
    }

    public static SurfaceView getRemoteSurfaceView() {
        Log.v("TTT","getRemoteSurfaceView()");
        // 主线程中调用
        ViewGroup vg = (ViewGroup) instance.remoteSurface.getParent();
        if(vg != null) {
            Log.v("TTT","getRemoteSurfaceView()-remove");
            vg.removeView(instance.remoteSurface);
        }
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
            if(isZhubo) {
                mRtcEngine.setChannelProfile(Constants.CHANNEL_PROFILE_LIVE_BROADCASTING);  // 01107
            }
            mRtcEngine.setParameters("{\"rtc.log_filter\":65535}");
            //mRtcEngine.setLogFile("/sdcard/filename.log");
            //setupLogFile();
            mRtcEngine.setLogFile(Environment.getExternalStorageState() + File.separator + "rtc_sdk.log");
        } catch (Exception e) {
            //Log.e(LOG_TAG, Log.getStackTraceString(e));
            throw new RuntimeException("NEED TO check rtc sdk init fatal error\n" + Log.getStackTraceString(e));
        }
    }

    private void setupLogFile() {
        File cacheDir;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheDir = mContext.getExternalCacheDir();
        } else {
            cacheDir = mContext.getCacheDir();
        }

        File logFile = new File(cacheDir.toString() + File.separator + "logFile.txt");

        if (logFile.exists()) {
            mRtcEngine.setLogFile(logFile.toString());
            return;
        }
        try {
            logFile.createNewFile();
            mRtcEngine.setLogFile(logFile.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Tutorial Step 2
    private void setupVideoProfile() {
        mRtcEngine.enableVideo();

        if(isZhubo) {
            // 01107
            if (mRtcEngine.isTextureEncodeSupported()) {
                Log.d("TTT", "before setExternalVideoSource");
                mRtcEngine.setExternalVideoSource(true, true, true);
            } else {
                throw new RuntimeException("Can not work on device do not supporting texture" + mRtcEngine.isTextureEncodeSupported());
            }
        }

        mRtcEngine.setVideoProfile(Constants.VIDEO_PROFILE_480P, true);


        mRtcEngine.setDefaultAudioRoutetoSpeakerphone(true);
        mRtcEngine.setSpeakerphoneVolume(100);
        mRtcEngine.setRecordingAudioFrameParameters(16000,1,RAW_AUDIO_FRAME_OP_MODE_READ_ONLY ,1024);
        mRtcEngine.muteAllRemoteAudioStreams(true);
    }
    
    private volatile boolean mJoined = false;
    // Tutorial Step 3
    private void setupLocalVideo() {
        if(isZhubo) {
            if(mCustomizedCameraRenderer == null) {
                // 代码生成LuoGLCameraView控件
                //@SuppressLint("ResourceType") XmlPullParser parser = mContext.getResources().getXml(R.layout.lay_luoglcameraview);
                //AttributeSet attributes = Xml.asAttributeSet(parser);
                //mCustomizedCameraRenderer = new LuoGLCameraView(mContext, attributes);

                final Object lock = new Object();

                YhApplicationA application = (YhApplicationA) mContext.getApplicationContext();
                curActivity = application.getCurrentActivity();

                curActivity.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Log.v("TTT","new -- LuoGLCameraView: "+Thread.currentThread().getName());
                        mCustomizedCameraRenderer = new LuoGLCameraView(mContext);
                        curActivity.addContentView(mCustomizedCameraRenderer, new FrameLayout.LayoutParams(1,1));
                        //((ViewGroup)activity.findViewById(R.id.content)).addView(mCustomizedCameraRenderer);
                        synchronized (lock) {
                            lock.notifyAll();
                        }
                    }
                });
                synchronized (lock) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            localSurface = mCustomizedCameraRenderer;
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
                    if(mRtcEngine != null) {
                        result = mRtcEngine.pushExternalVideoFrame(vf);
                    }


                    Log.d("TTT", "onFrameAvailable " + eglContext + " " + rotation + " " + texture + " " + result);

                }
            });

            mCustomizedCameraRenderer.setOnEGLContextHandler(new LuoGLCameraView.OnEGLContextListener() {
                @Override
                public void onEGLContextReady(EGLContext eglContext) {

                    if (!mJoined) {
                        Log.v("TTT","before joinChannel("+curRoomid+")");
                        joinChannel(curRoomid); // Tutorial Step 4
                        mJoined = true;
                    }
                }
            });
            XJGArSdkApi.XJGARSDKSetOptimizationMode(2);
            //show sticker papers
//        XJGArSdkApi.XJGARSDKSetShowStickerPapers(true);
            XJGArSdkApi.XJGARSDKSetShowStickerPapers(false);
        } else {
            SurfaceView surfaceView = RtcEngine.CreateRendererView(instance.mContext);
            surfaceView.setZOrderMediaOverlay(true);
            localSurface = surfaceView;
            mRtcEngine.setupLocalVideo(new VideoCanvas(surfaceView, VideoCanvas.RENDER_MODE_HIDDEN, 0));
        }
    }

    // Tutorial Step 4
    private void joinChannel(String roomid) {
        Log.v("TT","++joinChannel: "+roomid);
        if(isZhubo) {
            mRtcEngine.setClientRole(Constants.CLIENT_ROLE_BROADCASTER);    //01107, null);
        } else {
            mRtcEngine.setClientRole(Constants.CLIENT_ROLE_AUDIENCE);   //01107, null);
        }
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
                            intent.setClass(mContext.getApplicationContext(),
                                    guke_01160.class);

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
