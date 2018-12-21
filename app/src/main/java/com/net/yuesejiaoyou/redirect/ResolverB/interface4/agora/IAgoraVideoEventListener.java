package com.net.yuesejiaoyou.redirect.ResolverB.interface4.agora;

/**
 * Created by Administrator on 2018\8\17 0017.
 * 对声网 IRtcEngineEventHandler 接口的屏蔽封装
 */

public interface IAgoraVideoEventListener {
    public void onFirstRemoteVideoDecoded();
    public void onUserOffline();
    public void onJoinChannelSuccess();
    public void onUserMuteVideo(boolean bMute);
}
