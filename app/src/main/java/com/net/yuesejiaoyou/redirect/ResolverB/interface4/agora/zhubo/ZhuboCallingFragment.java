package com.net.yuesejiaoyou.redirect.ResolverB.interface4.agora.zhubo;

/**
 * Created by Administrator on 2018\8\20 0020.
 */

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.openfire.core.Utils;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.db.Const;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa1.smack.XMPPException;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack.SmackException;
import com.net.yuesejiaoyou.classroot.interface4.util.GeneralTimer;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.redirect.ResolverB.interface3.UsersThread_01158B;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.agora.IActivityListener;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.agora.IAgoraVideoEventListener;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.agora.ICmdListener;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.agora.IUserInfoHandler;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.agora.IVideoHandler;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.agora.P2PVideoConst;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.agora.VideoMessageManager;
import com.net.yuesejiaoyou.redirect.ResolverB.uiface.MusicUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by Administrator on 2018\8\17 0017.
 */

public class ZhuboCallingFragment extends Fragment implements ICmdListener, IActivityListener, IAgoraVideoEventListener {

    private IUserInfoHandler userInfoHandler;
    private IVideoHandler videoHandler;

    private Activity baseActivity;
    private View fragmentView;

    // 控件相关
    private LinearLayout exit_del;
    private DisplayImageOptions options=null;
    private ImageView photo;
    private TextView nickname;
    private FrameLayout remoteContainer;

    // 计时相关
    private GeneralTimer callingTimer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // 获取上层获取用户信息与操作一对一视频的接口
        baseActivity = getActivity();
        userInfoHandler = (IUserInfoHandler)baseActivity;
        videoHandler = (IVideoHandler)baseActivity;

        fragmentView = inflater.inflate(R.layout.frag_calling, null);

        // 初始化界面和事件
        initUIandEvent();

        // 进入拨打或被拨打状态
        openCalling();

        return fragmentView;
    }

    private void initUIandEvent() {

        //Toast.makeText(baseActivity, "ZhuboCallingFragment", Toast.LENGTH_SHORT).show();

        // 显示对方头像
        photo = (ImageView) fragmentView.findViewById(R.id.photo);
        options=new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true).bitmapConfig(Bitmap.Config.RGB_565).build();
        ImageLoader.getInstance().displayImage(
                userInfoHandler.getFromUserHeadpic(), photo,
                options);

        // 显示对方昵称
        nickname = (TextView) fragmentView.findViewById(R.id.nickname);
        nickname.setText(userInfoHandler.getFromUserName());
        exit_del = (LinearLayout) fragmentView.findViewById(R.id.exit_tuichu);
        exit_del.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // 发送挂断消息
                String strCmd;
                if(userInfoHandler.getDirect() == P2PVideoConst.GUKE_CALL_ZHUBO) {
                    strCmd = VideoMessageManager.VIDEO_U2A_USER_HANGUP;
                } else if(userInfoHandler.getDirect() == P2PVideoConst.ZHUBO_CALL_GUKE) {
                    strCmd = VideoMessageManager.VIDEO_A2U_ANCHOR_HANGUP;
                } else {
                    strCmd = VideoMessageManager.VIDEO_NONE;
                }


                String mode1 = "pushcmdmsg";
                String[] paramsMap1 = {"", Util.userid, Util.nickname, Util.headpic, userInfoHandler.getFromUserId(), userInfoHandler.getRoomid(), strCmd};
                UsersThread_01158B a = new UsersThread_01158B(mode1,paramsMap1,handler);
                Thread c = new Thread(a.runnable);
                c.start();

                // 发送openfire挂断消息 start
                final String message1 = "挂0断1邀2请" + Const.SPLIT + Const.ACTION_MSG_ZB_RESERVE
                        + Const.SPLIT + userInfoHandler.getRoomid() + Const.SPLIT + Util.nickname + Const.SPLIT + Util.headpic;
                //LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"message: "+message);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"before sendMessage()");
                            Utils.sendmessage(Utils.xmppConnection, message1, userInfoHandler.getFromUserId());
                        } catch (XMPPException | SmackException.NotConnectedException e) {
                            e.printStackTrace();
                            //LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"chatmanager: "+e.toString());
                            Looper.prepare();
                            // ToastUtil.showShortToast(ChatActivity.this, "发送失败");
                            Looper.loop();
                        }
                    }
                }).start();
                // 发送openfire挂断消息 end

                cancelCalling(false);
            }

        });

        remoteContainer = (FrameLayout)fragmentView.findViewById(R.id.remote_video);
        if(videoHandler.getLocalSurfaceView() != null) {
            remoteContainer.addView(videoHandler.getLocalSurfaceView());
        }
    }

    /**
     * 开始进入拨打或被拨打状态
     */
    private void openCalling() {

        // 开始播放音乐
        MusicUtil.playSound(1,100);

        // 修改自己为忙碌状态
        String mode1 = "mod_mang";
        String[] paramsMap1 = {Util.userid};
        UsersThread_01158B a = new UsersThread_01158B(mode1,paramsMap1,handler);
        Thread c = new Thread(a.runnable);
        c.start();

        // 初始化计时器
        callingTimer = new GeneralTimer(60);	// 60秒后自动挂断
        callingTimer.start(new GeneralTimer.TimerCallback() {
            @Override
            public void onTimerEnd() {

                baseActivity.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(baseActivity, "拨打超时，对方无应答", Toast.LENGTH_SHORT).show();
                    }
                });
                // 发送超时消息
                String strCmd;
                if(userInfoHandler.getDirect() == P2PVideoConst.GUKE_CALL_ZHUBO) {
                    strCmd = VideoMessageManager.VIDEO_U2A_USER_TIMEUP;
                } else if(userInfoHandler.getDirect() == P2PVideoConst.ZHUBO_CALL_GUKE) {
                    strCmd = VideoMessageManager.VIDEO_A2U_ANCHOR_TIMEUP;
                } else {
                    strCmd = VideoMessageManager.VIDEO_NONE;
                }

                String mode1 = "pushcmdmsg";
                String[] paramsMap1 = {"",Util.userid, Util.nickname, Util.headpic, userInfoHandler.getFromUserId(), userInfoHandler.getRoomid(), strCmd};
                UsersThread_01158B a = new UsersThread_01158B(mode1,paramsMap1,handler);
                Thread c = new Thread(a.runnable);
                c.start();
                cancelCalling(true);
            }
        });
    }

    /**
     * 取消当前拨打状态，可能是主动挂断也可能是被动挂断
     * isFromTimer: true：从Timer超时调用过来
     */
    private void cancelCalling(boolean isFromTimer) {
        // 关闭音乐
        MusicUtil.stopPlay();

        if(isFromTimer == false) {
            // 关闭定时器
            callingTimer.stop();
        }

        // 修改双方状态为在线
        String mode1 = "modezhubostate";
        String[] paramsMap1 = {Util.userid,userInfoHandler.getFromUserId()};
        UsersThread_01158B a = new UsersThread_01158B(mode1,paramsMap1,handler);
        Thread c = new Thread(a.runnable);
        c.start();
        // 删除一对一通话记录
        String mode2 = "removep2pvideo";
        String[] paramsMap2 = {"",userInfoHandler.getRoomid()};
        UsersThread_01158B a2 = new UsersThread_01158B(mode2,paramsMap2,handler);
        Thread c2 = new Thread(a2.runnable);
        c2.start();
        // 主播改成在线并添加未接通记录，更新接通率
        String mode3 = "mod_online";
        String[] paramsMap3 = {Util.userid,userInfoHandler.getFromUserId(),"0","1"};
        UsersThread_01158B a3 = new UsersThread_01158B(mode3,paramsMap3,handler);
        Thread c3 = new Thread(a3.runnable);
        c3.start();

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

        String mode2 = "removep2pvideo";
        String[] paramsMap2 = {"",userInfoHandler.getRoomid()};
        UsersThread_01158B a2 = new UsersThread_01158B(mode2,paramsMap2,handler);
        Thread c2 = new Thread(a2.runnable);
        c2.start();

        //finish();
    }

    //连点两次退出
    private long firstTime = 0;
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) {
                    Toast.makeText(baseActivity, "再按一次可以挂断", Toast.LENGTH_SHORT).show();
                    firstTime = secondTime;// 更新firstTime
                } else {// 两次按键小于2秒时，退出应用
                    // 发送挂断消息
                    String strCmd;
                    if(userInfoHandler.getDirect() == P2PVideoConst.GUKE_CALL_ZHUBO) {
                        strCmd = VideoMessageManager.VIDEO_U2A_USER_HANGUP;
                    } else if(userInfoHandler.getDirect() == P2PVideoConst.ZHUBO_CALL_GUKE) {
                        strCmd = VideoMessageManager.VIDEO_A2U_ANCHOR_HANGUP;
                    } else {
                        strCmd = VideoMessageManager.VIDEO_NONE;
                    }

                    String mode1 = "pushcmdmsg";
                    String[] paramsMap1 = {"",Util.userid, Util.nickname, Util.headpic, userInfoHandler.getFromUserId(), userInfoHandler.getRoomid(), strCmd};
                    UsersThread_01158B a = new UsersThread_01158B(mode1,paramsMap1,handler);
                    Thread c = new Thread(a.runnable);
                    c.start();

                    // 发送openfire挂断消息 start
                    final String message1 = "挂0断1邀2请" + Const.SPLIT + Const.ACTION_MSG_ZB_RESERVE
                            + Const.SPLIT + userInfoHandler.getRoomid() + Const.SPLIT + Util.nickname + Const.SPLIT + Util.headpic;
                    //LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"message: "+message);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                //LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"before sendMessage()");
                                Utils.sendmessage(Utils.xmppConnection, message1, userInfoHandler.getFromUserId());
                            } catch (XMPPException | SmackException.NotConnectedException e) {
                                e.printStackTrace();
                                //LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"chatmanager: "+e.toString());
                                Looper.prepare();
                                // ToastUtil.showShortToast(ChatActivity.this, "发送失败");
                                Looper.loop();
                            }
                        }
                    }).start();
                    // 发送openfire挂断消息 end

                    cancelCalling(false);
                }
                return true;
        }
        return false;
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @SuppressWarnings("unchecked")
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    //----------------------------------------------------------------------------------------------
    // 一对一视频命令消息接口 start
    @Override
    public void onCmdHangup() {
        baseActivity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(baseActivity, "对方已拒绝通话", Toast.LENGTH_SHORT).show();
            }
        });
        cancelCalling(false);
    }

    @Override
    public void onCmdTimeup() {
        cancelCalling(false);
    }

    @Override
    public void onCmdAccept() {
//        acceptCalling();
//        videoHandler.startVideo();
    }
    // 一对一视频命令消息接口 end
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // 声网一对一视频 start
    @Override
    public void onFirstRemoteVideoDecoded() { // Tutorial Step 5
//        SurfaceView remoteSurface = videoHandler.getRemoteSurfaceView();
//        if(remoteSurface != null) {
//            remoteContainer.addView(remoteSurface);
//        }

        acceptCalling();
        videoHandler.startVideo();
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
    // 声网一对一视频 start
    //----------------------------------------------------------------------------------------------
}

