package com.net.yuesejiaoyou.redirect.ResolverB.interface4.agora;

import android.view.SurfaceView;

/**
 * Created by Administrator on 2018\8\17 0017.
 */

public interface IVideoHandler {
    public void startVideo();   // 开始一对一视频
    public SurfaceView getLocalSurfaceView();
    public SurfaceView getRemoteSurfaceView();

    public void enableAudio();
    public void muteLocalVideoStream(boolean mute);
    public void muteLocalAudioStream(boolean mute);
    public void switchCamera();
    public void leaveChannel();
}
