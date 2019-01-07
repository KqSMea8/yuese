package com.net.yuesejiaoyou.redirect.ResolverD.interface4.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.openfire.core.Utils;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.bean.Msg;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.db.Const;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa1.smack.XMPPException;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa1.smack.XMPPTCPConnection;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack.Chat;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack.ChatManager;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack.SmackException;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.redirect.ResolverB.getset.Tag;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.MyAdapter_01162_1;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.MyLayoutmanager;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.Recycle_item;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.agora.IActivityListener;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.agora.IAgoraVideoEventListener;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.agora.IUserInfoHandler;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.agora.IVideoHandler;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.agora.P2PVideoConst;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.URL;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.activity.RechargeActivity;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.utils.LogUtil;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.utils.Tools;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.widget.GiftDialog;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.DialogCallback;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by Administrator on 2018\8\17 0017.
 */

public class GukeVideoFragment extends BaseFragment implements View.OnTouchListener, View.OnClickListener, IActivityListener, IAgoraVideoEventListener {

    private View fragmentView;
    private Activity baseActivity;
    private IUserInfoHandler userInfoHandler;
    private IVideoHandler videoHandler;

    private static final int PERMISSION_REQ_ID_RECORD_AUDIO = 22;
    private static final int PERMISSION_REQ_ID_CAMERA = PERMISSION_REQ_ID_RECORD_AUDIO + 1;

    private ScheduledExecutorService service;
    private boolean serviceRunning = false;
    private String YOU, headpicture, username;
    private String record_id = "";
    @BindView(R.id.ly1)
    LinearLayout ly1;
    private boolean is_open = true;
    private boolean is_evalue = true;
    private PopupWindow mPopWindow;
    RecyclerView grview;
    TextView like, disilike, txt1, txt2, txt3;
    ImageView like_img, dislike_img, star1;
    TextView besure, nickname1;
    List<Tag> list;
    List<String> list1 = new ArrayList<String>();
    ImageView headpic;
    DisplayImageOptions options;
    LinearLayout like1, dislike1;
    String pp = "";
    int t1 = 0;
    TextView grpChat, t2;
    EditText edtInput;
    RelativeLayout layGrpChat;
    ArrayList<String> grpChatArray = new ArrayList<String>();

    private CountDownTimer c;
    private int num = 0;

    private String yid_guke;

    MsgOperReciver_shouzhubo msgOperReciver;
    private int xDelta;
    private int yDelta;
    private int sWidth;
    private boolean can_go = true;

    // 美颜
    private SeekBar smoothLevel;
    private SeekBar whiteLevel;
    private ImageView meiyan, meibai;

    @BindView(R.id.id_send_red_packet)
    GifImageView id_send_red_packet;
    private SurfaceView remoteSurface, localSurface;
    private FrameLayout remoteContainer, localContainer;

    private CheckedTextView c1, c2, c3, c4, c5, c6;

    private PopupWindow popupWindow;


    @BindView(R.id.lay_remote_mute)
    RelativeLayout layMuteRemoteVideo;
    @BindView(R.id.lay_redpk)
    RelativeLayout layRedpk;
    @BindView(R.id.txt_username)
    TextView redpkUsername;
    @BindView(R.id.txt_rdvalue)
    TextView redpkValue;
    @BindView(R.id.time)
    TextView tvTime;

    private redpkTimer showRedpkThread;

    int timeCount = 0;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 119) {
                startReduceTimer();
            } else if (msg.what == 199) {
                timeCount++;
                tvTime.setText(Tools.getTimerStr(timeCount));
                if (timeCount % 60 == 0) {
                    charge();
                }
                if (timeCount == 3) {
                    charge();
                }
                handler.sendEmptyMessageDelayed(199, 1000);
            }
        }
    };

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        baseActivity = getActivity();
        userInfoHandler = (IUserInfoHandler) baseActivity;
        videoHandler = (IVideoHandler) baseActivity;

        fragmentView = view;


        // 获取远程surfaceView
        SurfaceView surfaceView = videoHandler.getRemoteSurfaceView();
        if (surfaceView != null) {
            setupRemoteVideo();
            remoteSurface = surfaceView;    //videoHandler.getRemoteSurfaceView();
            if (remoteSurface != null) {
                remoteContainer.addView(remoteSurface);
            } else {
                LogUtil.i("ttt", "-----------------remoteSurface is null");
            }
        }


        sWidth = Tools.getScreenWidth(getContext());

        yid_guke = userInfoHandler.getFromUserId(); //getIntent().getStringExtra("yid_guke");

        YOU = yid_guke;


        username = sharedPreferences.getString("name", "");
        headpicture = sharedPreferences.getString("headpic", "");


        ly1.setVisibility(View.GONE);
        t2 = (TextView) ly1.findViewById(R.id.t2);

        layGrpChat = (RelativeLayout) fragmentView.findViewById(R.id.layGrpChat1);
        grpChat = (TextView) fragmentView.findViewById(R.id.grpChat);
        edtInput = (EditText) fragmentView.findViewById(R.id.input_sms);


        id_send_red_packet.setImageResource(R.drawable.giftoff);

        if (userInfoHandler.getDirect() == P2PVideoConst.ZHUBO_CALL_GUKE && userInfoHandler.getYuyue().equals(P2PVideoConst.HAVE_ALREADY_YUYUE)) {  //if(!TextUtils.isEmpty(baseActivity.getIntent().getStringExtra("status"))){
            showToast("预约流程");

            OkHttpUtils.post(this)
                    .url(URL.URL_MOD_RETURN)
                    .addParams("param1", Util.userid)
                    .addParams("param2", YOU)
                    .addParams("param3", "")
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                        }

                        @Override
                        public void onResponse(String response, int id) {

                        }
                    });

            num = 1;
            handler.sendEmptyMessageDelayed(119, 60000);
        } else {
            showToast("正常流程");
            startReduceTimer();
        }

        msgOperReciver = new MsgOperReciver_shouzhubo();
        IntentFilter intentFilter = new IntentFilter(Const.ACTION_MSG_ONECHAT);
        baseActivity.registerReceiver(msgOperReciver, intentFilter);

        if (checkSelfPermission(Manifest.permission.RECORD_AUDIO, PERMISSION_REQ_ID_RECORD_AUDIO) && checkSelfPermission(Manifest.permission.CAMERA, PERMISSION_REQ_ID_CAMERA)) {
            initAgoraEngineAndJoinChannel();
        }


        smoothLevel = (SeekBar) fragmentView.findViewById(R.id.seek_smooth);
        whiteLevel = (SeekBar) fragmentView.findViewById(R.id.seek_white);

        meibai = (ImageView) fragmentView.findViewById(R.id.meibai);
        meiyan = (ImageView) fragmentView.findViewById(R.id.meiyan);


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

        startAwake();

        startTime();
    }

    private void startTime() {
        handler.sendEmptyMessageDelayed(199, 1000);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_agora_rtc;
    }

    private void charge() {
        num++;
        OkHttpUtils
                .post(this)
                .url(URL.URL_CHARGE)
                .addParams("param1", Util.userid)
                .addParams("param2", yid_guke)
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        onEncCallClicked();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (TextUtils.isEmpty(response)) {
                            onEncCallClicked();
                            return;
                        }
                        JSONObject jsonObject = JSON.parseObject(response);
                        String success = jsonObject.getString("success");
                        if (success.equals("1")) {
                            ly1.setVisibility(View.GONE);
                        } else if (success.equals("0")) {
                            OkHttpUtils.post(this)
                                    .url(URL.URL_ONLINE)
                                    .addParams("param1", Util.userid)
                                    .addParams("param2", userInfoHandler.getFromUserId())
                                    .addParams("param3", "1")
                                    .addParams("param4", num)
                                    .addParams("param5", tvTime.getText().toString())
                                    .build()
                                    .execute(new StringCallback() {
                                        @Override
                                        public void onError(Call call, Exception e, int id) {
                                        }

                                        @Override
                                        public void onResponse(String response, int id) {
                                            JSONObject object = JSON.parseObject(response);
                                            record_id = object.getString("success");
                                            leaveChannel();
                                            getLabelData();
                                        }
                                    });
                        } else if (success.equals("2") || success.equals("3")) {
                            num++;
                            int counter;
                            if (success.equals("2")) {        // 2分钟倒计时
                                showToast("余额不足");
                                counter = 60 * 2000;
                            } else if (success.equals("3")) {        // 1分钟倒计时
                                showToast("余额不足");
                                counter = 60 * 1000;
                            } else {
                                counter = 60 * 1000;
                            }

                            ly1.setVisibility(View.VISIBLE);
                            sendMsgText1("开0倒1计2时" + success);
                            if (c != null) {
                                c.cancel();
                            }
                            c = new CountDownTimer(counter, 1000) {
                                @Override
                                public void onTick(long l) {
                                    t2.setText(String.format("%d", l / 1000) + "秒");
                                }

                                @Override
                                public void onFinish() {
                                }
                            }.start();
                        } else {
                            onEncCallClicked();
                        }
                    }

                });

    }

    @OnClick(R.id.btn_endcall)
    public void huangupClick() {
        onEncCallClicked();
    }

    @OnClick(R.id.cafont)
    public void menuClick() {
        showPopupspWindow_rp();
    }

    @OnClick(R.id.guanbi)
    public void closeVideo(View view) {
        onLocalVideoMuteClicked(view);
    }


    @OnClick(R.id.qiehuan)
    public void changeCamere() {
        onSwitchCameraClicked();
    }


    @OnClick(R.id.ly1)
    public void rechargeClick() {
        startActivity(RechargeActivity.class);
    }

    @OnClick(R.id.id_send_red_packet)
    public void giftClick() {
        is_open = false;

        new GiftDialog(getActivity(), yid_guke).setLishener(new GiftDialog.OnGiftLishener() {
            @Override
            public void onSuccess(int gid, int num) {
                sendMsgText1("[" + "☆" + Util.nickname + "给" +
                        userInfoHandler.getFromUserName() + "赠送了" + num + "个" + Tools.getGiftName(gid) + "☆" + "]");
            }

            @Override
            public void onFail() {
                showPopupspWindow_chongzhi();
            }
        }).show();
        showPopupspWindow_sendred(id_send_red_packet);
    }

    @Override
    public void onFirstRemoteVideoDecoded() { // Tutorial Step 5
        setupRemoteVideo();
        remoteSurface = videoHandler.getRemoteSurfaceView();
        if (remoteSurface != null) {
            remoteContainer.addView(remoteSurface);
        }
    }

    @Override
    public void onUserOffline() { // Tutorial Step 7
        baseActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                onRemoteUserLeft();
            }
        });
    }

    @Override
    public void onJoinChannelSuccess() {

    }

    @Override
    public void onUserMuteVideo(final boolean muted) { // Tutorial Step 10
        baseActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                onRemoteUserVideoMuted(muted);
            }
        });
    }

    PowerManager.WakeLock wakeLock;

    private void startAwake() {
        PowerManager pm = (PowerManager) baseActivity.getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "TAG");
        wakeLock.acquire();
    }

    private void stopAwake() {
        if (wakeLock != null) {
            wakeLock.release();
        }
    }

    private void startReduceTimer() {
        service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(new Runnable() {

            @Override
            public void run() {
                //kouqian();
            }
        }, 0, 1, TimeUnit.MINUTES);
    }


    @Override
    public boolean onTouch(View view, MotionEvent event) {
        final int x = (int) event.getRawX();
        final int y = (int) event.getRawY();
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
                xDelta = sWidth - x - params.rightMargin;
                yDelta = y - params.topMargin;
                break;
            case MotionEvent.ACTION_MOVE:
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view
                        .getLayoutParams();
                int xDistance = sWidth - x - xDelta;
                int yDistance = y - yDelta;
                layoutParams.rightMargin = xDistance;
                layoutParams.topMargin = yDistance;
                view.setLayoutParams(layoutParams);
                break;
        }
        return false;
    }

    public void onMeiyanClicked(View view) {

        ImageView iv = (ImageView) view;
        Object showing = view.getTag();
        if (showing != null && (Boolean) showing) {
            //mVideoPreProcessing.enablePreProcessing(false);
            iv.setTag(null);
            iv.clearColorFilter();
            ((ImageView) view).setImageResource(R.drawable.beautyon);
            smoothLevel.setVisibility(View.GONE);
            whiteLevel.setVisibility(View.GONE);
            meibai.setVisibility(View.GONE);
            meiyan.setVisibility(View.GONE);
        } else {
            //mVideoPreProcessing.enablePreProcessing(true);
            iv.setTag(true);
            iv.setColorFilter(getResources().getColor(R.color.agora_blue), PorterDuff.Mode.MULTIPLY);
            ((ImageView) view).setImageResource(R.drawable.meiyan);
            smoothLevel.setVisibility(View.VISIBLE);
            whiteLevel.setVisibility(View.VISIBLE);
            meibai.setVisibility(View.VISIBLE);
            meiyan.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        report(((TextView) v).getText().toString());
    }

    private void report(String str) {
        OkHttpUtils.post(this)
                .url(URL.URL_REPORT)
                .addParams("param1", Util.userid)
                .addParams("param2", YOU)
                .addParams("param3", str)
                .build()
                .execute(new DialogCallback(getContext()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        showToast("网络异常");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (TextUtils.isEmpty(response)) {
                            showToast("网络异常");
                            return;
                        }

                        JSONObject jsonObject = JSON.parseObject(response);
                        String success = jsonObject.getString("success");
                        if (success.equals("1")) {
                            popupWindow.dismiss();
                            showToast("举报成功");
                        }
                    }
                });
    }

    private class MsgOperReciver_shouzhubo extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, final Intent intent) {

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String msgBody = intent.getStringExtra("oneuponechat");
                    Msg m = new Msg();
                    List<Msg> listMsg = new ArrayList<Msg>();
                    String[] msgs = msgBody.split("卍");
                    addGrpChat(msgs[0]);
                    freshGrpChat();
                }
            });

        }
    }


    private void initAgoraEngineAndJoinChannel() {
        setupLocalVideo();           // Tutorial Step 3
        localSurface = videoHandler.getLocalSurfaceView();
        if (localSurface != null) {
            localContainer.addView(localSurface);
        }
        videoHandler.enableAudio();
    }

    public boolean checkSelfPermission(String permission, int requestCode) {
        //Log.i(LOG_TAG, "checkSelfPermission " + permission + " " + requestCode);
        if (ContextCompat.checkSelfPermission(baseActivity, permission)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(baseActivity,
                    new String[]{permission},
                    requestCode);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        //Log.i(LOG_TAG, "onRequestPermissionsResult " + grantResults[0] + " " + requestCode);

        switch (requestCode) {
            case PERMISSION_REQ_ID_RECORD_AUDIO: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkSelfPermission(Manifest.permission.CAMERA, PERMISSION_REQ_ID_CAMERA);
                } else {
                    showLongToast("没有权限 " + Manifest.permission.RECORD_AUDIO);
                    //finish();
                }
                break;
            }
            case PERMISSION_REQ_ID_CAMERA: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initAgoraEngineAndJoinChannel();
                } else {
                    showLongToast("没有权限 " + Manifest.permission.CAMERA);
                    //finish();
                }
                break;
            }
        }
    }

    public final void showLongToast(final String msg) {
        baseActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(baseActivity.getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        handler.removeMessages(199);
        handler.removeMessages(119);

        stopAwake();    // 解除屏幕常亮

        leaveChannel();

        if (service != null) {
            service.shutdown();
        }

        baseActivity.unregisterReceiver(msgOperReciver);
    }


    // Tutorial Step 10
    public void onLocalVideoMuteClicked(View view) {
        ImageView iv = (ImageView) view;
        if (iv.isSelected()) {
            iv.setSelected(false);
            iv.clearColorFilter();
        } else {
            iv.setSelected(true);
        }
        videoHandler.muteLocalVideoStream(iv.isSelected());    //mRtcEngine.muteLocalVideoStream(iv.isSelected());
        FrameLayout container = (FrameLayout) fragmentView.findViewById(R.id.local_video_view_container);
        SurfaceView surfaceView = (SurfaceView) container.getChildAt(0);
        surfaceView.setZOrderMediaOverlay(!iv.isSelected());
        surfaceView.setVisibility(iv.isSelected() ? View.GONE : View.VISIBLE);
    }

    // Tutorial Step 9
    public void onLocalAudioMuteClicked(View view) {
        ImageView iv = (ImageView) view;
        if (iv.isSelected()) {
            iv.setSelected(false);
            iv.clearColorFilter();
        } else {
            iv.setSelected(true);
            //iv.setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
        }
        videoHandler.muteLocalAudioStream(iv.isSelected());   //mRtcEngine.muteLocalAudioStream(iv.isSelected());
    }


    // Tutorial Step 8
    public void onSwitchCameraClicked() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                Log.v("TT", "before switchcamera");
                videoHandler.switchCamera();   //mRtcEngine.switchCamera();
                Log.v("TT", "after switchcamera");
            }
        }).start();

    }

    // Tutorial Step 6
    public void onEncCallClicked() {
        if (can_go) {
            can_go = false;

            stopVideoCall();

            OkHttpUtils.post(this)
                    .url(URL.URL_ONLINE)
                    .addParams("param1", Util.userid)
                    .addParams("param2", userInfoHandler.getFromUserId())
                    .addParams("param3", "1")
                    .addParams("param4", num)
                    .addParams("param5", tvTime.getText().toString())
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            if (TextUtils.isEmpty(response)) {
                                leaveChannel();
                                getLabelData();
                                return;
                            }
                            JSONObject jsonObject = JSON.parseObject(response);
                            record_id = jsonObject.getString("success");
                            leaveChannel();
                            getLabelData();
                        }
                    });
        }
    }


    // Tutorial Step 3
    private void setupLocalVideo() {
        FrameLayout container = (FrameLayout) fragmentView.findViewById(R.id.local_video_view_container);
        localContainer = container;
        container.setOnTouchListener(this);
        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (localSurface == null || remoteSurface == null) {
                } else {
                    if (remoteContainer == null || localContainer == null) {
                    } else {
                        localContainer.removeAllViews();
                        remoteContainer.removeAllViews();
                        String tag = (String) remoteContainer.getTag();
                        if ("remote".equals(tag)) {
                            remoteContainer.addView(localSurface);
                            localContainer.addView(remoteSurface);
                            localSurface.setZOrderMediaOverlay(false);
                            remoteSurface.setZOrderMediaOverlay(true);
                            remoteContainer.setTag("local");
                        } else if ("local".equals(tag)) {
                            remoteContainer.addView(remoteSurface);
                            localContainer.addView(localSurface);
                            localSurface.setZOrderMediaOverlay(true);
                            remoteSurface.setZOrderMediaOverlay(false);
                            remoteContainer.setTag("remote");
                        }
                    }
                }
            }
        });
    }


    // Tutorial Step 5
    private void setupRemoteVideo() {
        FrameLayout container = (FrameLayout) fragmentView.findViewById(R.id.remote_video_view_container);
        remoteContainer = container;
        remoteContainer.setTag("remote");
    }

    // Tutorial Step 6
    private void leaveChannel() {
        videoHandler.leaveChannel();   //mRtcEngine.leaveChannel();
    }

    // Tutorial Step 7
    private void onRemoteUserLeft() {
        FrameLayout container = (FrameLayout) fragmentView.findViewById(R.id.remote_video_view_container);
        container.removeAllViews();
        View tipMsg = fragmentView.findViewById(R.id.quick_tips_when_use_agora_sdk); // optional UI
        tipMsg.setVisibility(View.VISIBLE);
        zhuboStopVideoCall();
    }

    private void zhuboStopVideoCall() {
        if (service != null) {
            service.shutdown();
        }

        OkHttpUtils.post(this)
                .url(URL.URL_ONLINE)
                .addParams("param1", Util.userid)
                .addParams("param2", userInfoHandler.getFromUserId())
                .addParams("param3", "1")
                .addParams("param4", num)
                .addParams("param5", tvTime.getText().toString())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        JSONObject jsonObject = JSON.parseObject(response);
                        record_id = jsonObject.getString("success");
                        leaveChannel();
                        getLabelData();
                    }
                });
    }

    private void getLabelData() {
        OkHttpUtils.post(this)
                .url(URL.URL_EVELUE_SEARCH)
                .addParams("param1", "")
                .addParams("param2", YOU)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        showPopupspWindow4();
                        list = new ArrayList<Tag>();
                        JSONArray jsonArray = JSON.parseArray(response);
                        JSONObject object = jsonArray.getJSONObject(0);
                        String aa = object.getString("headpic");
                        String price = object.getString("price");
                        String star = object.getString("star");
                        String nickname = object.getString("nickname");
                        //int c1=Integer.parseInt(num);
                        if (!TextUtils.isEmpty(price)) {
                            int c2 = Integer.parseInt(price);
                            if (num == 1) {
                                txt1.setText("通话: " + num + " 分钟");
                                txt2.setText("花费: " + num * c2 + "悦币");
                                txt3.setText("亲密度: " + num * c2);
                            } else {
                                txt1.setText("通话: " + num + " 分钟");
                                txt2.setText("花费: " + num * c2 + "悦币");
                                txt3.setText("亲密度: " + num * c2);
                            }
                        }

                        nickname1.setText(nickname);
                        if (star.equals("1")) {
                            star1.setImageResource(R.drawable.star1);
                        } else if (star.equals("2")) {
                            star1.setImageResource(R.drawable.star2);
                        } else if (star.equals("3")) {
                            star1.setImageResource(R.drawable.star3);
                        } else if (star.equals("4")) {
                            star1.setImageResource(R.drawable.star4);
                        } else if (star.equals("5")) {
                            star1.setImageResource(R.drawable.star5);
                        }
                        options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true).bitmapConfig(Bitmap.Config.RGB_565).build();
                        ImageLoader.getInstance().displayImage(aa, headpic, options);
                        for (int i = 0; i < jsonArray.size(); i++) {
                            JSONObject object1 = jsonArray.getJSONObject(i);
                            Tag bean = new Tag();
                            bean.setRecord(object1.getString("lab_name"));
                            bean.setColor(object1.getString("labcolor"));
                            list.add(bean);
                        }
                        MyLayoutmanager layoutmanager = new MyLayoutmanager();
                        grview.setLayoutManager(layoutmanager);
                        final MyAdapter_01162_1 adapter1 = new MyAdapter_01162_1(null, baseActivity, false, list);
                        grview.addItemDecoration(new Recycle_item(20));
                        //LogDetect.send(LogDetect.DataType.basicType,"01162---json返回","准备进入适配器");
                        grview.setAdapter(adapter1);
                        adapter1.setOnItemClickLitsener(new MyAdapter_01162_1.onItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                for (int i = 0; i < list.size(); i++) {
                                    if (list.get(i).isIs_check() && t1 < 3) {
                                        t1++;
                                    }
                                }
                                if (!list.get(position).isIs_check()) {
                                    GradientDrawable drawable = (GradientDrawable) view.getBackground();
                                    list1.add(position + "");
                                    //LogDetect.send(LogDetect.DataType.basicType,"01162-----list1长度和内容","list1长度"+list1.size()+"--list1内容"+list);
                                    if (list1.size() > 4) {
                                        //drawable.setColor(Color.parseColor(list.get(Integer.parseInt(list1.get(0))).getColor()));
                                        GradientDrawable drawable1 = (GradientDrawable) grview.getChildAt(Integer.parseInt(list1.get(0))).getBackground();
                                        drawable1.setColor(Color.parseColor("#CCD7DB"));
                                        drawable.setColor(Color.parseColor(list.get(position).getColor()));
                                        list.get(Integer.parseInt(list1.get(0))).setIs_check(false);
                                        list.get(position).setIs_check(true);
                                        list1.remove(0);
                                    } else {
                                        drawable.setColor(Color.parseColor(list.get(position).getColor()));
                                        list.get(position).setIs_check(true);
                                    }
                                } else {
                                    for (int i1 = 0; i1 < list1.size(); i1++) {
                                        if (list1.get(i1).equals(String.valueOf(position))) {
                                            list1.remove(String.valueOf(position));
                                        }
                                    }
                                    GradientDrawable drawable = (GradientDrawable) view.getBackground();
                                    drawable.setColor(Color.parseColor("#CCD7DB"));
                                    list.get(position).setIs_check(false);
                                }
                            }

                            @Override
                            public void onItemLongClick(View view, int position) {

                            }
                        });

                    }
                });
    }

    // Tutorial Step 10
    private void onRemoteUserVideoMuted(boolean muted) {
        if (muted) {
            layMuteRemoteVideo.setVisibility(View.VISIBLE);
        } else {
            layMuteRemoteVideo.setVisibility(View.GONE);
        }
    }


    private void addGrpChat(String txt) {
        int size = grpChatArray.size();
        if (size >= 4) {
            grpChatArray.remove(0);
        }
        grpChatArray.add(txt);
    }

    private void freshGrpChat() {

        if (grpChatArray.size() <= 0) {
            layGrpChat.setVisibility(View.GONE);
            return;
        } else {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < grpChatArray.size(); i++) {
                sb.append(grpChatArray.get(i) + "\r\n");
            }
            grpChat.setText(/*zhubo_name+":"+*/sb.toString().substring(0, sb.length() - 2));
            layGrpChat.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 发送消息
     *
     * @param content
     * @param touser
     * @throws XMPPException
     * @throws SmackException.NotConnectedException
     */
    public void sendMessage(XMPPTCPConnection mXMPPConnection, String content,
                            String touser) throws XMPPException, SmackException.NotConnectedException {
        if (mXMPPConnection == null || !mXMPPConnection.isConnected()) {

        }
        ChatManager chatmanager = Utils.xmppchatmanager;

        Chat chat = chatmanager.createChat(yid_guke + "@" + Const.XMPP_HOST, null);
        if (chat != null) {
            chat.sendMessage(content, Util.userid + "@" + Const.XMPP_HOST);
        } else {
            //LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"send fail:chat is null");
        }
    }

    //发送倒计时消息
    void sendMsgText1(String content) {
        final String message = content + Const.SPLIT + Const.ACTION_MSG_ONECHAT
                + Const.SPLIT + Tools.currentTime() + Const.SPLIT + username + Const.SPLIT + num;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sendMessage(Utils.xmppConnection, message, yid_guke);
                } catch (XMPPException | SmackException.NotConnectedException e) {
                    e.printStackTrace();
                    Looper.prepare();
                    Looper.loop();
                }
            }
        }).start();
    }

    void sendMsgText2(String content) {
        final String message = content + Const.SPLIT + Const.REWARD_ANCHOR;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sendMessage(Utils.xmppConnection, message, yid_guke);
                } catch (XMPPException | SmackException.NotConnectedException e) {
                    e.printStackTrace();
                    Looper.prepare();
                    Looper.loop();
                }
            }
        }).start();

    }

    private void stopVideoCall() {

        // 如果正在扣钱就不主动触发扣钱动作了
        if (serviceRunning == false) {
            if (service != null) {
                service.shutdown();
            }
            //kouqian();
        } else {
            if (service != null) {
                service.shutdown();
            }
        }
    }


    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                showToast("正在通话...");
                return true;
        }
        return false;
    }

    //红包打赏
    public void showPopupspWindow_sendred(View parent) {
        LayoutInflater inflater = (LayoutInflater) baseActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.red_choose_01165, null);

        TextView cancel = (TextView) layout.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                popupWindow.dismiss();
            }
        });
        //开线程，添加V币
        TextView coin1 = (TextView) layout.findViewById(R.id.coin1);
        coin1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                songhongbao(9);
                popupWindow.dismiss();

            }
        });
        TextView coin2 = (TextView) layout.findViewById(R.id.coin2);
        coin2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                songhongbao(18);
                popupWindow.dismiss();
                //			songhongbao(18);
            }
        });
        TextView coin3 = (TextView) layout.findViewById(R.id.coin3);
        coin3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                songhongbao(66);
                popupWindow.dismiss();
                //			songhongbao(66);
            }
        });
        TextView coin4 = (TextView) layout.findViewById(R.id.coin4);
        coin4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                songhongbao(99);
                popupWindow.dismiss();
                //			songhongbao(99);
            }
        });
        TextView coin5 = (TextView) layout.findViewById(R.id.coin5);
        coin5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                songhongbao(188);
                popupWindow.dismiss();
                //			songhongbao(188);
            }
        });
        TextView coin6 = (TextView) layout.findViewById(R.id.coin6);
        coin6.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                songhongbao(520);
                popupWindow.dismiss();
                //			songhongbao(520);
            }
        });
        TextView coin7 = (TextView) layout.findViewById(R.id.coin7);
        coin7.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                songhongbao(888);
                popupWindow.dismiss();
                //			songhongbao(888);
            }
        });
        TextView coin8 = (TextView) layout.findViewById(R.id.coin8);
        coin8.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                songhongbao(1314);
                popupWindow.dismiss();
                //		songhongbao(1314);
            }
        });
        popupWindow = new PopupWindow(layout, ViewPager.LayoutParams.MATCH_PARENT,
                ViewPager.LayoutParams.WRAP_CONTENT, true);
        // 控制键盘是否可以获得焦点
        popupWindow.setFocusable(true);
        // 设置popupWindow弹出窗体的背景
        //WindowManager.LayoutParams lp = getWindow().getAttributes();
        //lp.alpha = 0.4f;
        //getWindow().setAttributes(lp);
        popupWindow.setBackgroundDrawable(new BitmapDrawable(null, ""));
        WindowManager manager = (WindowManager) baseActivity.getSystemService(Context.WINDOW_SERVICE);
        @SuppressWarnings("deprecation")
        // 获取xoff
                int xpos = manager.getDefaultDisplay().getWidth() / 2
                - popupWindow.getWidth() / 2;
        // xoff,yoff基于anchor的左下角进行偏移。
        // popupWindow.showAsDropDown(parent, 0, 0);
        popupWindow.showAtLocation(parent, Gravity.CENTER | Gravity.CENTER, 252, 0);
        // 监听
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            // 在dismiss中恢复透明度
            public void onDismiss() {
                if (!is_open) {
                    id_send_red_packet.setImageResource(R.drawable.giftoff);
                }
            }
        });
    }

    //开线程，添加红包
    public void songhongbao(int coin) {
        OkHttpUtils.post(this)
                .url(URL.URL_HONGBAO)
                .addParams("param1", Util.userid)
                .addParams("param2", yid_guke)
                .addParams("param3", coin)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        JSONObject jsonObject = JSON.parseObject(response);
                        if ("1".equals(jsonObject.getString("success"))) {
                            String value = jsonObject.getString("value");
                            sendMsgText2(value);
                            showRedpkLayout(Util.nickname, value);
                        } else if ("0".equals(jsonObject.getString("success"))) {
                            Toast.makeText(getContext(), "余额不足", Toast.LENGTH_LONG).show();
                            showPopupspWindow_chongzhi();
                        }
                    }
                });
    }

    public void showPopupspWindow4() {
        // 加载布局
        LayoutInflater inflater = (LayoutInflater) baseActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.evaluate_01162, null);
        grview = (RecyclerView) layout.findViewById(R.id.grview);
        like = (TextView) layout.findViewById(R.id.like);
        headpic = (ImageView) layout.findViewById(R.id.headpic);
        disilike = (TextView) layout.findViewById(R.id.dislike);
        like_img = (ImageView) layout.findViewById(R.id.like_img);
        dislike_img = (ImageView) layout.findViewById(R.id.dislike_img);
        nickname1 = (TextView) layout.findViewById(R.id.nickname);
        besure = (TextView) layout.findViewById(R.id.besure);
        like1 = (LinearLayout) layout.findViewById(R.id.like1);
        dislike1 = (LinearLayout) layout.findViewById(R.id.dislike1);
        star1 = (ImageView) layout.findViewById(R.id.star1);
        txt1 = (TextView) layout.findViewById(R.id.txt1);
        txt2 = (TextView) layout.findViewById(R.id.txt2);
        txt3 = (TextView) layout.findViewById(R.id.txt3);

        like_img.setImageResource(R.drawable.love_selected);
        ImageView close11 = (ImageView) layout.findViewById(R.id.close11);
        close11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                is_evalue = false;
                mPopWindow.dismiss();
            }
        });
        like.setTextColor(Color.parseColor("#FF2E79"));
        dislike_img.setImageResource(R.drawable.un_love);
        disilike.setTextColor(Color.parseColor("#000000"));
        pp = "like";
        besure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos1 = 0;
                int pos2 = 0;
                int pos3 = 0;
                int pos4 = 0;
                String evalue = "";
                String evalues = "";
                int a = list1.size();

                if (a == 0) {
                    mPopWindow.dismiss();
                } else if (a == 1) {
                    pos1 = Integer.parseInt(list1.get(0));
                    evalue = list.get(pos1).getRecord() + "@" + list.get(pos1).getColor();
                    evalues = "“" + list.get(pos1).getRecord() + "”";

                    saveEvalue(evalue);

                } else if (a == 2) {
                    pos1 = Integer.parseInt(list1.get(0));
                    pos2 = Integer.parseInt(list1.get(1));
                    evalue = list.get(pos1).getRecord() + "@" + list.get(pos1).getColor() + "卍" + list.get(pos2).getRecord() + "@" + list.get(pos2).getColor();
                    evalues = "“" + list.get(pos1).getRecord() + "”，“" + list.get(pos2).getRecord() + "”";

                    saveEvalue(evalue);

                } else if (a == 3) {
                    pos1 = Integer.parseInt(list1.get(0));
                    pos2 = Integer.parseInt(list1.get(1));
                    pos3 = Integer.parseInt(list1.get(2));
                    // pos4=Integer.parseInt(list1.get(3));
                    evalue = list.get(pos1).getRecord() + "@" + list.get(pos1).getColor() + "卍" + list.get(pos2).getRecord() + "@" + list.get(pos2).getColor() + "卍" + list.get(pos3).getRecord() + "@" + list.get(pos3).getColor();
                    evalues = "“" + list.get(pos1).getRecord() + "”，“" + list.get(pos2).getRecord() + "”，“" + list.get(pos3).getRecord() + "”";


                    saveEvalue(evalue);
                } else {
                    pos1 = Integer.parseInt(list1.get(0));
                    pos2 = Integer.parseInt(list1.get(1));
                    pos3 = Integer.parseInt(list1.get(2));
                    pos4 = Integer.parseInt(list1.get(3));
                    evalue = list.get(pos1).getRecord() + "@" + list.get(pos1).getColor() + "卍" + list.get(pos2).getRecord() + "@" + list.get(pos2).getColor() + "卍" + list.get(pos3).getRecord() + "@" + list.get(pos3).getColor() + "卍" + list.get(pos4).getRecord() + "@" + list.get(pos4).getColor();
                    evalues = "“" + list.get(pos1).getRecord() + "”，“" + list.get(pos2).getRecord() + "”，“" + list.get(pos3).getRecord() + "”，“" + list.get(pos4).getRecord() + "”";


                    saveEvalue(evalue);
                }

                if (pp.equals("dislike")) {
                    evalues = "视频评价：不喜欢，" + evalues;
                } else {
                    evalues = "视频评价：喜欢，" + evalues;
                }
                sendPtMsgText(evalues);

            }
        });
        dislike_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dislike_img.setImageResource(R.drawable.un_love_selected);
                disilike.setTextColor(Color.parseColor("#0063F2"));
                like_img.setImageResource(R.drawable.love);
                like.setTextColor(Color.parseColor("#000000"));
                pp = "dislike";
            }
        });

        like_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                like_img.setImageResource(R.drawable.love_selected);
                like.setTextColor(Color.parseColor("#FF2E79"));
                dislike_img.setImageResource(R.drawable.un_love);
                disilike.setTextColor(Color.parseColor("#000000"));
                pp = "like";
            }
        });


        mPopWindow = new PopupWindow(layout, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        // 控制键盘是否可以获得焦点
        mPopWindow.setFocusable(true);
        // 设置popupWindow弹出窗体的背景
        WindowManager.LayoutParams lp = baseActivity.getWindow().getAttributes();
        lp.alpha = 0.4f;
        baseActivity.getWindow().setAttributes(lp);
        mPopWindow.setBackgroundDrawable(new BitmapDrawable(null, ""));
        mPopWindow.setOutsideTouchable(true);

        mPopWindow.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.CENTER | Gravity.CENTER, 0, 0);
        // 监听

        mPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            // 在dismiss中恢复透明度
            public void onDismiss() {
                if (!is_evalue) {
                    baseActivity.finish();
                } else {

                    Intent intent = new Intent("5");
                    baseActivity.sendBroadcast(intent);
                    baseActivity.finish();
                }


            }
        });
    }

    private void saveEvalue(String evalue) {
        OkHttpUtils.post(this)
                .url(URL.URL_SAVE_EVALUE)
                .addParams("param1", "1")
                .addParams("param2", YOU)
                .addParams("param3", evalue)
                .addParams("param4", pp)
                .addParams("param5", Util.userid)
                .addParams("param6", record_id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        mPopWindow.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        mPopWindow.dismiss();
                    }
                });
    }

    void sendPtMsgText(String content) {
        final String message = content + Const.SPLIT + Const.MSG_TYPE_TEXT
                + Const.SPLIT + Tools.currentTime() + Const.SPLIT + username + Const.SPLIT + headpicture;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sendMessage(Utils.xmppConnection, message, yid_guke);
                } catch (XMPPException | SmackException.NotConnectedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    //举报
    @SuppressLint({"RtlHardcoded", "NewApi"})
    public void showPopupspWindow_rp() {
        // 加载布局
        LayoutInflater inflater = LayoutInflater.from(baseActivity);
        View layout = inflater.inflate(R.layout.pop_report_01160, null);
        //取消
        TextView quxiao;
        c1 = (CheckedTextView) layout.findViewById(R.id.c1);
        c2 = (CheckedTextView) layout.findViewById(R.id.c2);
        c3 = (CheckedTextView) layout.findViewById(R.id.c3);
        c4 = (CheckedTextView) layout.findViewById(R.id.c4);
        c5 = (CheckedTextView) layout.findViewById(R.id.c5);
        c6 = (CheckedTextView) layout.findViewById(R.id.c6);

        c1.setOnClickListener(this);
        c2.setOnClickListener(this);
        c3.setOnClickListener(this);
        c4.setOnClickListener(this);
        c5.setOnClickListener(this);
        c6.setOnClickListener(this);

        quxiao = (TextView) layout.findViewById(R.id.quxiao);
        quxiao.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                popupWindow.dismiss();
            }
        });


        popupWindow = new PopupWindow(layout, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        // 控制键盘是否可以获得焦点
        popupWindow.setFocusable(true);
        WindowManager.LayoutParams lp = baseActivity.getWindow().getAttributes();
        lp.alpha = 0.5f;
        baseActivity.getWindow().setAttributes(lp);
        popupWindow.setBackgroundDrawable(new BitmapDrawable(null, ""));
        //popupWindow.showAsDropDown(parent, 0, 0,Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL);
        popupWindow.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            public void onDismiss() {
                WindowManager.LayoutParams lp = baseActivity.getWindow().getAttributes();
                lp.alpha = 1f;
                baseActivity.getWindow().setAttributes(lp);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 返回充值结果
        if (resultCode == 100) {
            String result = data.getStringExtra("rlt");
            if ("success".equals(result)) {
                sendMsgText1("停0倒1计2时");
                if (c != null) {
                    c.cancel();
                }
                ly1.setVisibility(View.GONE);
            }
        }
    }

    /////////////////////////////////////////////////////////////////////////
    //是否充值，
    public void showPopupspWindow_chongzhi() {
        LayoutInflater inflater = (LayoutInflater) baseActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.is_chongzhi_01165, null);

        TextView cancel = (TextView) layout.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                popupWindow.dismiss();
            }
        });


        TextView confirm = (TextView) layout.findViewById(R.id.confirm);//获取小窗口上的TextView，以便显示现在操作的功能。
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent1 = new Intent();
                intent1.setClass(baseActivity, RechargeActivity.class);//充值页面
//				startActivity(intent1);
                startActivityForResult(intent1, 100);
                popupWindow.dismiss();
            }
        });


        popupWindow = new PopupWindow(layout, ViewPager.LayoutParams.MATCH_PARENT,//？？？？？？？？？？？？？？
                ViewPager.LayoutParams.WRAP_CONTENT, true);
        // 控制键盘是否可以获得焦点
        popupWindow.setFocusable(true);
        // 设置popupWindow弹出窗体的背景
        WindowManager.LayoutParams lp = baseActivity.getWindow().getAttributes();
        lp.alpha = 0.4f;
        baseActivity.getWindow().setAttributes(lp);
        popupWindow.setBackgroundDrawable(new BitmapDrawable(null, ""));
        WindowManager manager = (WindowManager) baseActivity.getSystemService(Context.WINDOW_SERVICE);
        @SuppressWarnings("deprecation")
        // 获取xoff
                int xpos = manager.getDefaultDisplay().getWidth() / 2
                - popupWindow.getWidth() / 2;
        // xoff,yoff基于anchor的左下角进行偏移。
        // popupWindow.showAsDropDown(parent, 0, 0);
        popupWindow.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.CENTER | Gravity.CENTER, 0, 0);
        // 监听

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            // 在dismiss中恢复透明度
            public void onDismiss() {
                WindowManager.LayoutParams lp = baseActivity.getWindow().getAttributes();
                lp.alpha = 1f;
                baseActivity.getWindow()
                        .addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                baseActivity.getWindow().setAttributes(lp);
            }
        });
    }

    //--------------------------------------------------------------------
//  送红包
    abstract class redpkTimer implements Runnable {
        protected int curCnt = 0;
        protected int maxCnt = 3;

        public void clearCnt() {
            curCnt = 0;
        }
    }

    private void showRedpkLayout(String username, String value) {

        redpkUsername.setText(username + "用户");
        redpkValue.setText(value);

        if (layRedpk.getVisibility() == View.GONE) {

            layRedpk.setVisibility(View.VISIBLE);

            showRedpkThread = new redpkTimer() {

                @Override
                public void run() {

                    while (curCnt < maxCnt) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                        curCnt++;
                    }
                    // 3秒后隐藏红包图片
                    baseActivity.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            layRedpk.setVisibility(View.GONE);
                        }
                    });
                }

                public void clearCnt() {
                    curCnt = 0;
                }
            };

            new Thread(showRedpkThread).start();
        } else {
            if (showRedpkThread != null) {
                showRedpkThread.clearCnt();
            }
        }
    }
}
