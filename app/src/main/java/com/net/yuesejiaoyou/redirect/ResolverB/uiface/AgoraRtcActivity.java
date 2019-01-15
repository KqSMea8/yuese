package com.net.yuesejiaoyou.redirect.ResolverB.uiface;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
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
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnLayoutChangeListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
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

//import com.example.translate.SpeechTranslate;
//import com.example.vliao.R;
//import com.example.vliao.getset.Tag;
//import com.example.vliao.interface3.UsersThread_01158;
//import com.example.vliao.interface3.UsersThread_01160;
//import com.example.vliao.interface3.UsersThread_01162;
//import com.example.vliao.interface3.UsersThread_01165;
//import com.example.vliao.interface4.LogDetect;
//import com.example.vliao.interface4.MyAdapter_01162_1;
//import com.example.vliao.interface4.MyLayoutmanager;
//import com.example.vliao.interface4.Recycle_item;
//import com.example.vliao.util.AgoraVideoManager;
//import com.example.vliao.util.Util;
//import com.lazysellers.sellers.core.Utils;
//import com.lazysellers.sellers.infocenter.bean.Msg;
//import com.lazysellers.sellers.infocenter.db.Const;
//import com.lazysellers.sellers.infocenter.hengexa1.smack.XMPPException;
//import com.lazysellers.sellers.infocenter.hengexa1.smack.XMPPTCPConnection;
//import com.lazysellers.sellers.infocenter.hengexa2.smack.Chat;
//import com.lazysellers.sellers.infocenter.hengexa2.smack.ChatManager;
//import com.lazysellers.sellers.infocenter.hengexa2.smack.SmackException;
import com.alibaba.fastjson.JSON;
import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
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
import com.net.yuesejiaoyou.redirect.ResolverB.interface3.UsersThread_01158B;
import com.net.yuesejiaoyou.redirect.ResolverB.interface3.UsersThread_01160B;
import com.net.yuesejiaoyou.redirect.ResolverB.interface3.UsersThread_01162B;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.MyAdapter_01162_1;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.MyLayoutmanager;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.Recycle_item;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.util.AgoraVideoManager;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.URL;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.activity.RechargeActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

//import io.agora.propeller.preprocessing.VideoPreProcessing;
import okhttp3.Call;
import pl.droidsonroids.gif.GifImageView;

@SuppressLint("NewApi")
public class AgoraRtcActivity extends Activity implements OnLayoutChangeListener, View.OnTouchListener, View.OnClickListener {

    private static final String LOG_TAG = AgoraRtcActivity.class.getSimpleName();
    private static final int PERMISSION_REQ_ID_RECORD_AUDIO = 22;
    private static final int PERMISSION_REQ_ID_CAMERA = PERMISSION_REQ_ID_RECORD_AUDIO + 1;

    private ScheduledExecutorService service;
    private boolean serviceRunning = false;
    private String I, YOU, name, logo, headpicture, username;
    // 表情图标每页6列4行
    private int columns = 6;
    private int rows = 4;
    private String record_id = "";
    private EditText send_sms;
    private SimpleDateFormat sd;
    // 每页显示的表情view
    private List<View> views = new ArrayList<View>();
    // 表情列表
    private List<String> staticFacesList;
    private LinearLayout chat_face_container, chat_add_container, bottom, ly1;
    private ImageView image_face;// 表情图标
    private ViewPager mViewPager;
    private LinearLayout mDotsLayout;
    private EditText input;
    private String duifang = "";
    private boolean is_open = true;
    private boolean is_back = true;
    private boolean is_evalue = true;
    private boolean camera_on = true;
    private PopupWindow mPopWindow;
    RecyclerView grview;
    TextView like, disilike, txt1, txt2, txt3, txt4;
    ImageView like_img, dislike_img, star1;
    TextView besure, nickname1;
    List<Tag> list;
    List<String> list1 = new ArrayList<String>();
    ImageView headpic;
    DisplayImageOptions options;
    LinearLayout like1, dislike1;
    String pp = "";
    int t1 = 0;
    private String zhubo_name;
    private RelativeLayout xufeiliaotian, zengsongliwu;
    // 群聊
    TextView grpChat, btn_chat, t2;
    TextView sendMsg;
    EditText edtInput;
    RelativeLayout layChat;
    RelativeLayout layGrpChat;
    ArrayList<String> grpChatArray = new ArrayList<String>();

    private RelativeLayout activity_video_chat_view;
    private CountDownTimer c;
    private int num = 0;

    private String roomid, yid_guke;
    private RelativeLayout relativeLayout;
    //计时器相关
    private TextView time;
    private Timer timer;
    private String strtime;
    private String b1;
    MsgOperReciver_shouzhubo msgOperReciver;
    private int xDelta;
    private int yDelta;
    private int sWidth;
    private boolean can_go = true;
    //屏幕高度
    private int screenHeight = 0;
    //软件盘弹起后所占高度阀值
    private int keyHeight = 0;
    private ImageView translate;
    private boolean ks = false;
    private RelativeLayout gbsp, xz, relativeLayout2, moshubang;
    private LinearLayout ly2;

    // 美颜
//    private VideoPreProcessing mVideoPreProcessing;
    private SeekBar smoothLevel;
    private SeekBar whiteLevel;
    private ImageView meiyan, meibai;
    private int mopiLevel;
    private int meibaiLevel;

    // private ImageView id_send_red_packet;
    private GifImageView id_send_red_packet;
    private RelativeLayout id_head_layout;
    private LinearLayout translate_select;
    private LinearLayout id_like_video_layout, id_share_video_layout;
    private SurfaceView remoteSurface, localSurface;
    private FrameLayout remoteContainer, localContainer;
    //    private SpeechTranslate spchTranslate;
    private ImageView cn, en;

    private CheckedTextView c1, c2, c3, c4, c5, c6;

    private PopupWindow popupWindow;

    private Runnable e;

    private boolean switchCameraCtrl = false;
    private int switchCameraCnt = 10;

    private boolean isGukeGuaduan = false;
    private boolean isHuibo = false;

    private RelativeLayout layMuteRemoteVideo;

    private RelativeLayout layRedpk;
    private TextView redpkUsername;
    private TextView redpkValue;
    private redpkTimer showRedpkThread;

    private final AgoraVideoManager.AgoraRtcListener mRtcEventHandler = new AgoraVideoManager.AgoraRtcListener() { // Tutorial Step 1
        @Override
        public void onFirstRemoteVideoDecoded(final int uid, int width, int height, int elapsed) { // Tutorial Step 5
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //setupRemoteVideo(uid);
                }
            });
        }

        @Override
        public void onUserOffline(int uid, int reason) { // Tutorial Step 7
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    onRemoteUserLeft();
                }
            });
        }

        @Override
        public void onJoinChannelSuccess(String channel, int uid, int elapsed) {

        }

        @Override
        public void onUserMuteVideo(final int uid, final boolean muted) { // Tutorial Step 10
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    onRemoteUserVideoMuted(uid, muted);
                }
            });
        }
    };

    public void updateRemoteSurfaceView(final SurfaceView surface) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                remoteSurface = surface;
                if (remoteContainer != null) {
                    remoteContainer.removeAllViews();
                    remoteContainer.addView(remoteSurface);
                }
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agora_rtc);

        layMuteRemoteVideo = (RelativeLayout) findViewById(R.id.lay_remote_mute);
        layRedpk = (RelativeLayout) findViewById(R.id.lay_redpk);
        redpkUsername = (TextView) findViewById(R.id.txt_username);
        redpkValue = (TextView) findViewById(R.id.txt_rdvalue);

        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        sWidth = metric.widthPixels;
        time = (TextView) findViewById(R.id.time);
        roomid = getIntent().getStringExtra("roomid");
        yid_guke = getIntent().getStringExtra("yid_guke");

        sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        zhubo_name = getIntent().getStringExtra("zhubo_name");
        YOU = yid_guke;

        id_head_layout = (RelativeLayout) findViewById(R.id.id_head_layout);
        id_head_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        findViewById(R.id.cafont).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupspWindow_rp(findViewById(R.id.activity_video_chat_view));
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("Acitivity", Context.MODE_PRIVATE); //私有数据
        I = sharedPreferences.getString("id", "");
        username = sharedPreferences.getString("name", "");
        headpicture = sharedPreferences.getString("headpic", "");

        // 读取美颜设置
        mopiLevel = sharedPreferences.getInt("mopi", 1);
        meibaiLevel = sharedPreferences.getInt("meibai", 1);

        activity_video_chat_view = (RelativeLayout) findViewById(R.id.activity_video_chat_view);

        //获取屏幕高度
        screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();
        //阀值设置为屏幕高度的1/3
        keyHeight = screenHeight / 3;

        ly1 = (LinearLayout) findViewById(R.id.ly1);
        ly1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(AgoraRtcActivity.this, RechargeActivity.class);
                startActivity(intent);

            }
        });
        ly1.setVisibility(View.GONE);
        t2 = (TextView) ly1.findViewById(R.id.t2);

        layGrpChat = (RelativeLayout) this.findViewById(R.id.layGrpChat1);
        LogDetect.send(LogDetect.DataType.specialType, "01160 layGrpChat:", layGrpChat);
        grpChat = (TextView) this.findViewById(R.id.grpChat);
        layChat = (RelativeLayout) findViewById(R.id.rl);
        edtInput = (EditText) this.findViewById(R.id.input_sms);
        sendMsg = (TextView) this.findViewById(R.id.send_sms);
        sendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sendCtxt = edtInput.getText().toString().trim();
                edtInput.setText("");
                hideSoftInputView();
                layChat.setVisibility(View.GONE);
                if (!sendCtxt.isEmpty()) {
                    sendMsgText(username + ":" + sendCtxt);
                    LogDetect.send(LogDetect.DataType.specialType, "01160 用户发:", username + ":" + sendCtxt);
                }
            }
        });
        btn_chat = (TextView) this.findViewById(R.id.btn_chat);
        btn_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layChat.setVisibility(View.VISIBLE);
                edtInput.requestFocus();
                showSoftInputView(layChat);
            }
        });

        final ImageView guanbi = (ImageView) findViewById(R.id.guanbi);
        guanbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLocalVideoMuteClicked(guanbi);
            }
        });
        final ImageView qiehuan = (ImageView) findViewById(R.id.qiehuan);
        qiehuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//				if(switchCameraCtrl == false) {
//					switchCameraCtrl = true;
                onSwitchCameraClicked(qiehuan);
//					startSwitchCameraCtrl();
//				} else {
//					Toast.makeText(VideoChatViewActivity.this, "请 "+switchCameraCnt+" 秒后再切换摄像头", Toast.LENGTH_SHORT).show();
//				}
            }
        });

        cn = (ImageView) findViewById(R.id.cn);
        en = (ImageView) findViewById(R.id.en);


//        spchTranslate = SpeechTranslate.getInstance();
        //ImageView daxiao = (ImageView)findViewById(R.id.daxiao1);

        id_send_red_packet = (GifImageView) findViewById(R.id.id_send_red_packet);


        id_send_red_packet.setImageResource(R.drawable.giftoff);
        id_send_red_packet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                is_open = false;
                //id_send_red_packet.setImageResource(R.drawable.gifton);
                showPopupspWindow_sendred(id_send_red_packet);
            }
        });

        LogDetect.send(LogDetect.DataType.specialType, "01160 主播id:", yid_guke);

        if (!TextUtils.isEmpty(getIntent().getStringExtra("status"))) {
            isHuibo = true;
            String[] paramsMap = {Util.userid, YOU, ""};
            num = 1;
            new Thread(new UsersThread_01158B("mod_return", paramsMap, handler).runnable).start();

            handler.postDelayed(e = new Runnable() {
                @Override
                public void run() {
//					kouqian();
                    startReduceTimer();
                }
            }, 60000);
        } else {
            //kouqian();
            startReduceTimer();
        }


        msgOperReciver = new MsgOperReciver_shouzhubo();
        IntentFilter intentFilter = new IntentFilter(Const.ACTION_MSG_ONECHAT);
        registerReceiver(msgOperReciver, intentFilter);

        if (checkSelfPermission(Manifest.permission.RECORD_AUDIO, PERMISSION_REQ_ID_RECORD_AUDIO) && checkSelfPermission(Manifest.permission.CAMERA, PERMISSION_REQ_ID_CAMERA)) {
            initAgoraEngineAndJoinChannel();
        }

        timer = new Timer();
        timer.schedule(new TimerTask() {
            SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
            long t = System.currentTimeMillis();
            long tl = t / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset();

            @Override
            public void run() {
                Timestamp tt = new Timestamp(tl);
                String starttime = sdf.format(tt);
                handler.sendMessage(handler.obtainMessage(1, (Object) starttime));
                tl = tl + 1000;
            }
        }, 0, 1000);

//        time.setFormat("%s");
//        time.setBase(SystemClock.elapsedRealtime());
//        time.start();

/*        giftView = (GiftItemView) findViewById(R.id.gift_item_first);*/

        gbsp = (RelativeLayout) findViewById(R.id.gbsp);
        //xz = (RelativeLayout)findViewById(R.id.xz);
        relativeLayout2 = (RelativeLayout) findViewById(R.id.relativeLayout2);
        ly2 = (LinearLayout) findViewById(R.id.ly2);

        smoothLevel = (SeekBar) findViewById(R.id.seek_smooth);
        whiteLevel = (SeekBar) findViewById(R.id.seek_white);

        moshubang = (RelativeLayout) findViewById(R.id.moshubang);
        meibai = (ImageView) findViewById(R.id.meibai);
        meiyan = (ImageView) findViewById(R.id.meiyan);


        if (isHuibo) {
            // 进入房间就删除记录
            String mode2 = "removep2pvideo";
            String[] paramsMap2 = {"", AgoraVideoManager.getCurRoomid()};
            UsersThread_01158B a2 = new UsersThread_01158B(mode2, paramsMap2, handler);
            Thread c2 = new Thread(a2.runnable);
            c2.start();
        } else {
            // 进入房间就删除记录
            String mode2 = "removep2pvideo";
            String[] paramsMap2 = {"", AgoraVideoManager.getCurRoomid()};
            UsersThread_01158B a2 = new UsersThread_01158B(mode2, paramsMap2, handler);
            Thread c2 = new Thread(a2.runnable);
            c2.start();
        }
        startAwake();
    }

    PowerManager.WakeLock wakeLock;

    private void startAwake() {
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "TAG");
        wakeLock.acquire();
    }

    private void stopAwake() {
        if (wakeLock != null) {
            wakeLock.release();
        }
    }

    private void startReduceTimer() {
        service = Executors
                .newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(new Runnable() {

            @Override
            public void run() {
                kouqian();
            }
        }, 0, 1, TimeUnit.MINUTES);

//        String mode1 = "checkyue";		// 开始聊天的时候先检查余额(大于两分钟，等于两分钟两分钟倒计时，大于一分钟小于两分钟一分钟倒计时)
//        String[] paramsMap1 = {Util.userid, yid_guke};
//        UsersThread_01158B a = new UsersThread_01158B(mode1, paramsMap1, handler);
//        Thread t = new Thread(a.runnable);
//        t.start();
    }

    /**
     * 扣1分钟钱
     */
    private void kouqian() {
        serviceRunning = true;
        String mode1 = "kou_frist";
        String[] paramsMap1 = {Util.userid, yid_guke};
        UsersThread_01158B a = new UsersThread_01158B(mode1, paramsMap1, handler);
        Thread t = new Thread(a.runnable);
        t.start();
        serviceRunning = false;
    }


    @Override
    public boolean onTouch(View view, MotionEvent event) {

        final int x = (int) event.getRawX();
        final int y = (int) event.getRawY();
        Log.d("TT", "onTouch: x= " + x + "y=" + y);
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view
                        .getLayoutParams();
                xDelta = sWidth - x - params.rightMargin;
                yDelta = y - params.topMargin;
                Log.d("TT", "ACTION_DOWN: xDelta= " + xDelta + "yDelta=" + yDelta);
                break;
            case MotionEvent.ACTION_MOVE:
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view
                        .getLayoutParams();
                int xDistance = sWidth - x - xDelta;
                int yDistance = y - yDelta;
                Log.d("TT", "ACTION_MOVE: xDistance= " + xDistance + "yDistance=" + yDistance);
                layoutParams.rightMargin = xDistance;
                layoutParams.topMargin = yDistance;
                view.setLayoutParams(layoutParams);
                break;
        }
        //localView.invalidate();
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


    private void showSoftInputView(final View v) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ((InputMethodManager) v.getContext().getSystemService(
                        Service.INPUT_METHOD_SERVICE)).toggleSoftInput(0,
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }, 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.c1:
                String mode = "report";
                String[] paramsMap = {Util.userid, YOU, c1.getText().toString()};
                UsersThread_01160B az = new UsersThread_01160B(mode, paramsMap, handler);
                Thread t = new Thread(az.runnable);
                t.start();
                LogDetect.send(LogDetect.DataType.specialType, "01160 开线程举报:", "3");
                break;
            case R.id.c2:
                String mode112 = "report";
                String[] paramsMap112 = {Util.userid, YOU, c2.getText().toString()};
                UsersThread_01160B a1 = new UsersThread_01160B(mode112, paramsMap112, handler);
                Thread t1 = new Thread(a1.runnable);
                t1.start();
                LogDetect.send(LogDetect.DataType.specialType, "01160 开线程举报:", "3");
                break;
            case R.id.c3:
                String mode2 = "report";
                String[] paramsMap2 = {Util.userid, YOU, c3.getText().toString()};
                UsersThread_01160B a2 = new UsersThread_01160B(mode2, paramsMap2, handler);
                Thread t2 = new Thread(a2.runnable);
                t2.start();
                LogDetect.send(LogDetect.DataType.specialType, "01160 开线程举报:", "3");
                break;
            case R.id.c4:
                String mode3 = "report";
                String[] paramsMap3 = {Util.userid, YOU, c4.getText().toString()};
                UsersThread_01160B a3 = new UsersThread_01160B(mode3, paramsMap3, handler);
                Thread t3 = new Thread(a3.runnable);
                t3.start();
                LogDetect.send(LogDetect.DataType.specialType, "01160 开线程举报:", "3");
                break;
            case R.id.c5:
                String mode4 = "report";
                String[] paramsMap4 = {Util.userid, YOU, c5.getText().toString()};
                UsersThread_01160B a4 = new UsersThread_01160B(mode4, paramsMap4, handler);
                Thread t4 = new Thread(a4.runnable);
                t4.start();
                LogDetect.send(LogDetect.DataType.specialType, "01160 开线程举报:", "3");
                break;
            case R.id.c6:
                String mode5 = "report";
                String[] paramsMap5 = {Util.userid, YOU, c6.getText().toString()};
                UsersThread_01160B a5 = new UsersThread_01160B(mode5, paramsMap5, handler);
                Thread t5 = new Thread(a5.runnable);
                t5.start();
                LogDetect.send(LogDetect.DataType.specialType, "01160 开线程举报:", "3");
                break;
        }
    }


    private class MsgOperReciver_shouzhubo extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            String msgBody = intent.getStringExtra("oneuponechat");
            handler.sendMessage(handler.obtainMessage(310, (Object) msgBody));


        }
    }

    /**
     * 执行发送消息 文本类型
     *
     * @param content
     */
    void sendMsgText(String content) {
        //addGrpChat(content);
        //handler.obtainMessage(19).sendToTarget();
        edtInput.setText("");
        final String message = content + Const.SPLIT + Const.ACTION_MSG_ONECHAT
                + Const.SPLIT + sd.format(new Date()) + Const.SPLIT + username;
        //LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"message: "+message);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"before sendMessage()");
                    sendMessage(Utils.xmppConnection, message, yid_guke);
                } catch (XMPPException | SmackException.NotConnectedException e) {
                    e.printStackTrace();
                    //LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"chatmanager: "+e.toString());
                    Looper.prepare();
                    // ToastUtil.showShortToast(ChatActivity.this, "发送失败");
                    Looper.loop();
                }
            }
        }).start();

    }

    /**
     * 隐藏软键盘
     */
    public void hideSoftInputView() {
        InputMethodManager manager = ((InputMethodManager) this
                .getSystemService(Activity.INPUT_METHOD_SERVICE));
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null)
                manager.hideSoftInputFromWindow(getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    private void initAgoraEngineAndJoinChannel() {

        setupLocalVideo();           // Tutorial Step 3

        setupRemoteVideo();

        localSurface = AgoraVideoManager.getLocalSurfaceView();
        remoteSurface = AgoraVideoManager.getRemoteSurfaceView();
        if (localSurface != null) {
            localContainer.addView(localSurface);
        }

        if (remoteSurface != null) {
            remoteContainer.addView(remoteSurface);
        }

        AgoraVideoManager.setAgoraRtcListener(mRtcEventHandler);
        AgoraVideoManager.enableAudio();
    }

    public boolean checkSelfPermission(String permission, int requestCode) {
        Log.i(LOG_TAG, "checkSelfPermission " + permission + " " + requestCode);
        if (ContextCompat.checkSelfPermission(this, permission)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{permission},
                    requestCode);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        Log.i(LOG_TAG, "onRequestPermissionsResult " + grantResults[0] + " " + requestCode);

        switch (requestCode) {
            case PERMISSION_REQ_ID_RECORD_AUDIO: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkSelfPermission(Manifest.permission.CAMERA, PERMISSION_REQ_ID_CAMERA);
                } else {
                    showLongToast("没有权限 " + Manifest.permission.RECORD_AUDIO);
                    finish();
                }
                break;
            }
            case PERMISSION_REQ_ID_CAMERA: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initAgoraEngineAndJoinChannel();
                } else {
                    showLongToast("没有权限 " + Manifest.permission.CAMERA);
                    finish();
                }
                break;
            }
        }
    }

    public final void showLongToast(final String msg) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v("TT", "guke_bk  onDestroy()");

        stopAwake();    // 解除屏幕常亮

        Log.v("TT", "before disableAudioFrame()");

        Log.v("TT", "after disableAudioFrame()");
        leaveChannel();

        timer.cancel();//关闭定时器
        if (service != null) {
            service.shutdown();
        }

        unregisterReceiver(msgOperReciver);
        handler.removeCallbacks(e);

        Log.v("TT", "VideoChatViewActivity-onDestroy()");
    }


    // Tutorial Step 10
    public void onLocalVideoMuteClicked(View view) {
        ImageView iv = (ImageView) view;
        if (iv.isSelected()) {
            iv.setSelected(false);
            iv.clearColorFilter();
        } else {
            iv.setSelected(true);
            //iv.setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
        }
        AgoraVideoManager.muteLocalVideoStream(iv.isSelected());    //mRtcEngine.muteLocalVideoStream(iv.isSelected());
        FrameLayout container = (FrameLayout) findViewById(R.id.local_video_view_container);
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
        AgoraVideoManager.muteLocalAudioStream(iv.isSelected());   //mRtcEngine.muteLocalAudioStream(iv.isSelected());
    }


    // Tutorial Step 8
    public void onSwitchCameraClicked(View view) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                Log.v("TT", "before switchcamera");
                AgoraVideoManager.switchCamera();   //mRtcEngine.switchCamera();
                Log.v("TT", "after switchcamera");
            }
        }).start();

    }

    // Tutorial Step 6
    public void onEncCallClicked(View view) {
        if (can_go) {
            can_go = false;

            isGukeGuaduan = true;

            stopVideoCall();

            String mode1 = "mod_online";
            String[] paramsMap1 = {Util.userid, yid_guke, "1", num + "", time.getText().toString()};
            UsersThread_01158B a = new UsersThread_01158B(mode1, paramsMap1, handler);
            LogDetect.send(LogDetect.DataType.specialType, "01160 用户挂断 video:", yid_guke);
            Thread c = new Thread(a.runnable);
            c.start();
        }
    }


    // Tutorial Step 3
    private void setupLocalVideo() {
        FrameLayout container = (FrameLayout) findViewById(R.id.local_video_view_container);
        localContainer = container;
        container.setOnTouchListener(this);
        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (localSurface == null || remoteSurface == null) {
                    //Toast.makeText(VideoChatViewActivity.this, "surface null", Toast.LENGTH_SHORT).show();
                } else {

                    if (remoteContainer == null || localContainer == null) {
                        //Toast.makeText(VideoChatViewActivity.this, "container null", Toast.LENGTH_SHORT).show();
                    } else {
                        //Toast.makeText(VideoChatViewActivity.this, "onClick", Toast.LENGTH_SHORT).show();
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
        FrameLayout container = (FrameLayout) findViewById(R.id.remote_video_view_container);

        remoteContainer = container;
        remoteContainer.setTag("remote");

    }

    // Tutorial Step 6
    private void leaveChannel() {
        AgoraVideoManager.leaveChannel();   //mRtcEngine.leaveChannel();
    }

    // Tutorial Step 7
    private void onRemoteUserLeft() {
        FrameLayout container = (FrameLayout) findViewById(R.id.remote_video_view_container);
        container.removeAllViews();
        View tipMsg = findViewById(R.id.quick_tips_when_use_agora_sdk); // optional UI
        tipMsg.setVisibility(View.VISIBLE);
        //finish();
//		String mode1 = "mod_online";
//		String[] paramsMap1 = {Util.userid, yid_guke, "1", num + ""};
//		UsersThread_01158 a = new UsersThread_01158(mode1, paramsMap1, handler);
//		LogDetect.send(LogDetect.DataType.specialType, "01160 用户挂断 video:", yid_guke);
//		Thread c = new Thread(a.runnable);
//		c.start();

        zhuboStopVideoCall();
    }

    private void zhuboStopVideoCall() {
        if (service != null) {
            service.shutdown();
        }

        String mode1 = "mod_online";
        String[] paramsMap1 = {Util.userid, yid_guke, "1", num + "", time.getText().toString()};
        UsersThread_01158B a = new UsersThread_01158B(mode1, paramsMap1, handler);
        LogDetect.send(LogDetect.DataType.specialType, "01160 用户挂断 video:", yid_guke);
        Thread c = new Thread(a.runnable);
        c.start();
    }

    // Tutorial Step 10
    private void onRemoteUserVideoMuted(int uid, boolean muted) {
        Log.v("TT", "onRemoteUserVideoMuted():" + uid + "," + muted);
//        FrameLayout container = (FrameLayout) findViewById(R.id.remote_video_view_container);
//
//        SurfaceView surfaceView = (SurfaceView) container.getChildAt(0);
//        LogDetect.send(LogDetect.DataType.specialType, "TyrantsFragment: ", surfaceView);
//        Object tag = surfaceView.getTag();
//        if (tag != null && (Integer) tag == uid) {
//            surfaceView.setVisibility(muted ? View.GONE : View.VISIBLE);
//        }
        if (muted) {
            layMuteRemoteVideo.setVisibility(View.VISIBLE);
        } else {
            layMuteRemoteVideo.setVisibility(View.GONE);
        }
    }

    String songing = "";
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @SuppressWarnings("unchecked")
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 260:        // 用户余额不足
                    // LogDetect.send(LogDetect.DataType.specialType,"扣了几次:",num);
                    String json2 = (String) msg.obj;
                    try {
                        JSONObject object = new JSONObject(json2);
                        record_id = object.getString("success");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String qian = num * 2 + "";

                    leaveChannel();
                    String mode = "evalue_search";
                    String params[] = {"", YOU};
                    UsersThread_01162B b = new UsersThread_01162B(mode, params, handler);
                    Thread thread = new Thread(b.runnable);
                    thread.start();
                    break;
                case 270:
                    String json = (String) msg.obj;
                    LogDetect.send(LogDetect.DataType.specialType, "01160:", json);
                    if (!json.equals("")) {
                        try {
                            JSONObject jsonObject = new JSONObject(json);
                            LogDetect.send(LogDetect.DataType.specialType, "01160:", jsonObject);
                            //如果返回1，说明修改成功了
                            String success_ornot = jsonObject.getString("success");
                            LogDetect.send(LogDetect.DataType.specialType, "01160:", success_ornot);
                            if (success_ornot.equals("1")) {
                                ly1.setVisibility(View.GONE);
                                num++;
                            }//否则失败了
                            else if (success_ornot.equals("0")) {
                                String mode1 = "mod_online";
                                String[] paramsMap1 = {Util.userid, yid_guke, "1", num + "", time.getText().toString()};
                                UsersThread_01158B a = new UsersThread_01158B(mode1, paramsMap1, handler);
                                Thread t = new Thread(a.runnable);
                                t.start();
                            } else if (success_ornot.equals("2") || success_ornot.equals("3")) {
                                num++;
                                int counter;
                                if (success_ornot.equals("2")) {        // 2分钟倒计时
                                    Toast.makeText(AgoraRtcActivity.this, "余额不足", Toast.LENGTH_SHORT).show();
                                    counter = 60 * 2000;
                                } else if (success_ornot.equals("3")) {        // 1分钟倒计时
                                    Toast.makeText(AgoraRtcActivity.this, "余额不足", Toast.LENGTH_SHORT).show();
                                    counter = 60 * 1000;
                                } else {
                                    counter = 60 * 1000;
                                }


                                ly1.setVisibility(View.VISIBLE);
                                sendMsgText1("开0倒1计2时" + success_ornot);
                                // 如果已经存在倒计时则先取消再重新打开(两分钟倒计时切换到一分钟倒计时)
                                if (c != null) {
                                    c.cancel();
                                }
                                c = new CountDownTimer(counter, 1000) {
                                    @Override
                                    public void onTick(long l) {
                                        //String a = String.valueOf(millisUntilFinished);
                                        t2.setText(String.format("%d", l / 1000) + "秒");
                                    }

                                    @Override
                                    public void onFinish() {

//										String mode1 = "mod_online";
//										String[] paramsMap1 = {Util.userid, yid_guke, "1", num+""};
//										UsersThread_01158 a = new UsersThread_01158(mode1, paramsMap1, handler);
//										Thread t = new Thread(a.runnable);
//										t.start();
                                    }
                                }.start();
                            } else {    // 其他 checkyue 网络请求检测余额充足
//                                if(isHuibo) {
//                                    num = 1;
//                                } else {
//                                    num = 0;
//                                    Log.v("TT","after checkyue(num): "+num);
//                                }
//                                ly1.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    } else {
//                        Toast.makeText(VideoChatViewActivity.this,"网络异常.请重试",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 310:
                    String msgBody = (String) msg.obj;
                    Msg m = new Msg();
                    List<Msg> listMsg = new ArrayList<Msg>();
                    String[] msgs = msgBody.split("卍");
                    LogDetect.send(LogDetect.DataType.specialType, "顾客收到", "msgs:" + msgs);
                  /*  m.setContent(msgs[0]);
                    m.setType(msgs[1]);
                    m.setDate(msgs[2]);*/
                    duifang = msgs[3]; //发送人
                    addGrpChat(msgs[0]);
                    LogDetect.send(LogDetect.DataType.specialType, "01160 主播发guke:", zhubo_name + ":" + msgs[0]);
                    //freshGrpChat();
                    handler.obtainMessage(19).sendToTarget();
                    LogDetect.send(LogDetect.DataType.specialType, "01160", "msgs:" + msgs.toString());
                    break;
                case 19:
                    freshGrpChat();
                    break;
                case 203:
                    String json_report = (String) (msg).obj;
                    LogDetect.send(LogDetect.DataType.specialType, "01160:", msg);
                    if (!json_report.isEmpty()) {
                        try {
                            JSONObject jsonObject1 = new JSONObject(json_report);
                            LogDetect.send(LogDetect.DataType.specialType, "01160:", jsonObject1);
                            //拉黑
                            String success_ornot = jsonObject1.getString("success");
                            LogDetect.send(LogDetect.DataType.specialType, "01160 success_ornot:", success_ornot);
                            if (success_ornot.equals("1")) {
                                popupWindow.dismiss();
                                Toast.makeText(AgoraRtcActivity.this, "举报成功", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(AgoraRtcActivity.this, "举报失败，请检查网络连接", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 25:
                    String reportcheckjson = (String) msg.obj;
                    if (reportcheckjson.isEmpty()) {
//                        Toast.makeText(VideoChatViewActivity.this, "网络异常.请重试", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    LogDetect.send(LogDetect.DataType.specialType, "handleMessage01178--reportcheckjson-:", reportcheckjson);
                    JSONObject object3;
                    try {
                        object3 = new JSONObject(reportcheckjson);
                        reportcheckjson = object3.getString("success");
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                    LogDetect.send(LogDetect.DataType.specialType, "handleMessage01178--reportcheckjson-:", reportcheckjson);
                    if ("1".equals(reportcheckjson)) {
                        Toast.makeText(AgoraRtcActivity.this, "成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AgoraRtcActivity.this, "失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 1:
                    strtime = (String) msg.obj;
                    if (!strtime.equals("")) {
                        time.setText(strtime);
                    } else {
                        time.setText("");
                    }
                    break;
                case 290:

                    break;
                case 101: {
                    try {
                        Log.v("TT", "red_envelope-1: " + msg.obj);
                        JSONObject jsonObject = new JSONObject((String) msg.obj);
                        if ("1".equals(jsonObject.getString("success"))) {
                            String value = jsonObject.getString("value");
                            sendMsgText2(value);
                            //Toast.makeText(AgoraRtcActivity.this, "成功赠送 "+value+" 悦币", Toast.LENGTH_LONG).show();
                            showRedpkLayout(Util.nickname, value);
                        } else if ("0".equals(jsonObject.getString("success"))) {
                            Toast.makeText(AgoraRtcActivity.this, "余额不足", Toast.LENGTH_LONG).show();
                            showPopupspWindow_chongzhi(activity_video_chat_view);
                        }
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                        Log.v("TT", "red_envelope-exception: " + e1);
                    }

                }
                break;
                case 202:
                    showPopupspWindow4(cn);
                    String json5 = (String) msg.obj;
                    //LogDetect.send(LogDetect.DataType.basicType, "01162---json返回", json);
                    try {
                        list = new ArrayList<Tag>();
                        JSONArray jsonArray = new JSONArray(json5);
                        JSONObject object = jsonArray.getJSONObject(0);
                        String aa = object.getString("headpic");
                        String price = object.getString("price");
                        String star = object.getString("star");
                        String nickname = object.getString("nickname");
                        //int c1=Integer.parseInt(num);
                        int c2 = Integer.parseInt(price);
                        LogDetect.send(LogDetect.DataType.basicType, "01162---json返回", num);
                        if (num == 1) {
                            txt1.setText("通话: " + num + " 分钟");
                            txt2.setText("花费: " + num * c2 + "悦币");
                            txt3.setText("亲密度: " + num * c2);
                        } else {
//                            if(isGukeGuaduan && num > 1) {
//                                num--;
//                            }
                            txt1.setText("通话: " + num + " 分钟");
                            txt2.setText("花费: " + num * c2 + "悦币");
                            txt3.setText("亲密度: " + num * c2);
                        }

                        nickname1.setText(nickname);
                        //LogDetect.send(LogDetect.DataType.basicType,"01162---女主播星级",star);
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
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object1 = jsonArray.getJSONObject(i);
                            Tag bean = new Tag();
                            bean.setRecord(object1.getString("lab_name"));
                            bean.setColor(object1.getString("labcolor"));
                            list.add(bean);
                        }
                        MyLayoutmanager layoutmanager = new MyLayoutmanager();
                        grview.setLayoutManager(layoutmanager);
                        final MyAdapter_01162_1 adapter1 = new MyAdapter_01162_1(null, AgoraRtcActivity.this, false, list);
                        grview.addItemDecoration(new Recycle_item(20));
                        //LogDetect.send(LogDetect.DataType.basicType,"01162---json返回","准备进入适配器");
                        grview.setAdapter(adapter1);
                        LogDetect.send(LogDetect.DataType.basicType, "01162---json返回", "准备进入适配器");
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
                                        //LogDetect.send(LogDetect.DataType.basicType,"01162-----已选中--变颜色","当前position"+position+"---当前对应颜色"+list.get(position).getColor());
                                    }
                                } else {
                                    for (int i1 = 0; i1 < list1.size(); i1++) {
                                        if (list1.get(i1).equals(String.valueOf(position))) {
                                            list1.remove(String.valueOf(position));
                                        }
                                    }
                                    GradientDrawable drawable = (GradientDrawable) view.getBackground();
                                    drawable.setColor(Color.parseColor("#CCD7DB"));
                                    //LogDetect.send(LogDetect.DataType.basicType,"01162-----取消选中变回本来颜色",position);
                                    list.get(position).setIs_check(false);
                                }
                            }

                            @Override
                            public void onItemLongClick(View view, int position) {

                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();

                    }
                    break;
                case 204:
                    mPopWindow.dismiss();
                    //finish();
                    break;
            }
        }
    };

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
           /* if(duifang.equals("")){
                grpChat.setText(zhubo_name+":"+sb.toString().substring(0, sb.length()-2));
            }else{
                grpChat.setText(duifang+":"+sb.toString().substring(0, sb.length()-2));
            }*/
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
        edtInput.setText("");
        final String message = content + Const.SPLIT + Const.ACTION_MSG_ONECHAT
                + Const.SPLIT + sd.format(new Date()) + Const.SPLIT + username + Const.SPLIT + num;
        //LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"message: "+message);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"before sendMessage()");
                    sendMessage(Utils.xmppConnection, message, yid_guke);
                } catch (XMPPException | SmackException.NotConnectedException e) {
                    e.printStackTrace();
                    //LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"chatmanager: "+e.toString());
                    Looper.prepare();
                    // ToastUtil.showShortToast(ChatActivity.this, "发送失败");
                    Looper.loop();
                }
            }
        }).start();
    }

    void sendMsgText2(String content) {
        //edtInput.setText("");
        final String message = content + Const.SPLIT + Const.REWARD_ANCHOR;
        //LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"message: "+message);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"before sendMessage()");
                    sendMessage(Utils.xmppConnection, message, yid_guke);
                } catch (XMPPException | SmackException.NotConnectedException e) {
                    e.printStackTrace();
                    //LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"chatmanager: "+e.toString());
                    Looper.prepare();
                    // ToastUtil.showShortToast(ChatActivity.this, "发送失败");
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

    //连点两次退出
    private long firstTime = 0;

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) {
                    Toast.makeText(this, "再次点击挂断", Toast.LENGTH_SHORT).show();
                    firstTime = secondTime;// 更新firstTime
                } else {// 两次按键小于2秒时，退出应用
                    hideSoftInputView();

//					stopVideoCall();
//
//					String mode1 = "mod_online";
//					String[] paramsMap1 = {Util.userid, yid_guke, "1", num + ""};
//					UsersThread_01158 a = new UsersThread_01158(mode1, paramsMap1, handler);
//					Thread t = new Thread(a.runnable);
//					t.start();

                    onEncCallClicked(null);
                }
                return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right,
                               int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {
        } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
            //关闭
            layChat.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v("TT", "VideoChatViewActivity-onResume()");
        //添加layout大小发生改变监听器
        activity_video_chat_view.addOnLayoutChangeListener(this);
        if (remoteSurface != null) {
            remoteSurface.invalidate();
            Log.v("TT", "remote()");
        }
        if (localSurface != null) {
            localSurface.invalidate();
            Log.v("TT", "local()");
        }
    }

    //红包打赏
    public void showPopupspWindow_sendred(View parent) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
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
//        String mode = "red_envelope";
//        String[] params = {Util.userid, yid_guke, Integer.toString(coin)};
//        UsersThread_01165B b = new UsersThread_01165B(mode, params, handler);
//        Thread thread = new Thread(b.runnable);
//        thread.start();

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
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if ("1".equals(jsonObject.getString("success"))) {
                                String value = jsonObject.getString("value");
                                sendMsgText2(value);
                                showRedpkLayout(Util.nickname, value);
                            } else if ("0".equals(jsonObject.getString("success"))) {
                                Toast.makeText(AgoraRtcActivity.this, "余额不足", Toast.LENGTH_LONG).show();
                                showPopupspWindow_chongzhi(activity_video_chat_view);
                            }
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                });
    }

    public void showPopupspWindow4(View parent) {
        // 加载布局
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
                //		besure.setClickable(false);
                String mode = "save_evalue";
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
                    String params[] = {"1", YOU, evalue, pp, Util.userid, record_id};
                    UsersThread_01162B b1 = new UsersThread_01162B(mode, params, handler);
                    Thread thread = new Thread(b1.runnable);
                    thread.start();
                } else if (a == 2) {
                    pos1 = Integer.parseInt(list1.get(0));
                    pos2 = Integer.parseInt(list1.get(1));
                    evalue = list.get(pos1).getRecord() + "@" + list.get(pos1).getColor() + "卍" + list.get(pos2).getRecord() + "@" + list.get(pos2).getColor();
                    evalues = "“" + list.get(pos1).getRecord() + "”，“" + list.get(pos2).getRecord() + "”";
                    String params[] = {"1", YOU, evalue, pp, Util.userid, record_id};
                    UsersThread_01162B b1 = new UsersThread_01162B(mode, params, handler);
                    Thread thread = new Thread(b1.runnable);
                    thread.start();
                } else if (a == 3) {
                    pos1 = Integer.parseInt(list1.get(0));
                    pos2 = Integer.parseInt(list1.get(1));
                    pos3 = Integer.parseInt(list1.get(2));
                    // pos4=Integer.parseInt(list1.get(3));
                    evalue = list.get(pos1).getRecord() + "@" + list.get(pos1).getColor() + "卍" + list.get(pos2).getRecord() + "@" + list.get(pos2).getColor() + "卍" + list.get(pos3).getRecord() + "@" + list.get(pos3).getColor();
                    evalues = "“" + list.get(pos1).getRecord() + "”，“" + list.get(pos2).getRecord() + "”，“" + list.get(pos3).getRecord() + "”";
                    String params[] = {"1", YOU, evalue, pp, Util.userid, record_id};
                    UsersThread_01162B b1 = new UsersThread_01162B(mode, params, handler);
                    Thread thread = new Thread(b1.runnable);
                    thread.start();
                } else {
                    pos1 = Integer.parseInt(list1.get(0));
                    pos2 = Integer.parseInt(list1.get(1));
                    pos3 = Integer.parseInt(list1.get(2));
                    pos4 = Integer.parseInt(list1.get(3));
                    evalue = list.get(pos1).getRecord() + "@" + list.get(pos1).getColor() + "卍" + list.get(pos2).getRecord() + "@" + list.get(pos2).getColor() + "卍" + list.get(pos3).getRecord() + "@" + list.get(pos3).getColor() + "卍" + list.get(pos4).getRecord() + "@" + list.get(pos4).getColor();
                    evalues = "“" + list.get(pos1).getRecord() + "”，“" + list.get(pos2).getRecord() + "”，“" + list.get(pos3).getRecord() + "”，“" + list.get(pos4).getRecord() + "”";
                    String params[] = {"1", YOU, evalue, pp, Util.userid, record_id};
                    UsersThread_01162B b1 = new UsersThread_01162B(mode, params, handler);
                    Thread thread = new Thread(b1.runnable);
                    thread.start();
                }

                LogDetect.send(LogDetect.DataType.specialType, "01160 用户发:", username + ":" + evalues);
                if (pp.equals("dislike")) {
                    evalues = "视频评价：不喜欢，" + evalues;
                } else {
                    evalues = "视频评价：喜欢，" + evalues;
                }
                LogDetect.send(LogDetect.DataType.specialType, "01160 用户发:", username + ":" + evalues);
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
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.4f;
        getWindow().setAttributes(lp);
        mPopWindow.setBackgroundDrawable(new BitmapDrawable(null, ""));
        mPopWindow.setOutsideTouchable(true);

        mPopWindow.showAtLocation(parent, Gravity.CENTER | Gravity.CENTER, 0, 0);
        // 监听

        mPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            // 在dismiss中恢复透明度
            public void onDismiss() {


                if (!is_evalue) {
                    finish();
                } else {

                    Intent intent = new Intent("5");
                    sendBroadcast(intent);
                    finish();
                }


            }
        });
    }

    void sendPtMsgText(String content) {
        LogDetect.send(LogDetect.DataType.noType, Utils.seller_id + "=phone=" + Utils.android, "chatmanager: " + content);
        //addGrpChat(content);
        //handler.obtainMessage(19).sendToTarget();
        //edtInput.setText("");
        final String message = content + Const.SPLIT + Const.MSG_TYPE_TEXT
                + Const.SPLIT + sd.format(new Date()) + Const.SPLIT + username + Const.SPLIT + headpicture;
        //LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"message: "+message);
        LogDetect.send(LogDetect.DataType.noType, Utils.seller_id + "=phone=" + Utils.android, "message: " + message);
        ////////////
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"before sendMessage()");
                    sendMessage(Utils.xmppConnection, message, yid_guke);
                } catch (XMPPException | SmackException.NotConnectedException e) {
                    e.printStackTrace();
                    //LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"chatmanager: "+e.toString());
                    Looper.prepare();
                    // ToastUtil.showShortToast(ChatActivity.this, "发送失败");
                    Looper.loop();
                }
            }
        }).start();

    }

    //举报
    @SuppressLint({"RtlHardcoded", "NewApi"})
    public void showPopupspWindow_rp(View parent) {
        // 加载布局
        LayoutInflater inflater = LayoutInflater.from(AgoraRtcActivity.this);
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
        WindowManager.LayoutParams lp = AgoraRtcActivity.this.getWindow().getAttributes();
        lp.alpha = 0.5f;
        AgoraRtcActivity.this.getWindow().setAttributes(lp);
        popupWindow.setBackgroundDrawable(new BitmapDrawable(null, ""));
        //popupWindow.showAsDropDown(parent, 0, 0,Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL);
        popupWindow.showAtLocation(parent, Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            public void onDismiss() {
                WindowManager.LayoutParams lp = AgoraRtcActivity.this.getWindow().getAttributes();
                lp.alpha = 1f;
                AgoraRtcActivity.this.getWindow().setAttributes(lp);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

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
    public void showPopupspWindow_chongzhi(View parent) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.is_chongzhi_01165, null);

        TextView cancel = (TextView) layout.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                popupWindow.dismiss();
            }
        });


        TextView confirm = (TextView) layout.findViewById(R.id.confirm);//获取小窗口上的TextView，以便显示现在操作的功能。
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent1 = new Intent();
                intent1.setClass(AgoraRtcActivity.this, RechargeActivity.class);//充值页面
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
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.4f;
        getWindow().setAttributes(lp);
        popupWindow.setBackgroundDrawable(new BitmapDrawable(null, ""));
        WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        @SuppressWarnings("deprecation")
        // 获取xoff
                int xpos = manager.getDefaultDisplay().getWidth() / 2
                - popupWindow.getWidth() / 2;
        // xoff,yoff基于anchor的左下角进行偏移。
        // popupWindow.showAsDropDown(parent, 0, 0);
        popupWindow.showAtLocation(parent, Gravity.CENTER | Gravity.CENTER, 0, 0);
        // 监听

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            // 在dismiss中恢复透明度
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow()
                        .addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                getWindow().setAttributes(lp);
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
                    runOnUiThread(new Runnable() {

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

