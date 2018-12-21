package com.net.yuesejiaoyou.redirect.ResolverB.uiface;


import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import org.json.JSONException;
import org.json.JSONObject;

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
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
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

import io.agora.rtc.Constants;
import io.agora.rtc.IRtcEngineEventHandler;
import io.agora.rtc.RtcEngine;
import io.agora.rtc.video.VideoCanvas;
import static io.agora.rtc.Constants.RAW_AUDIO_FRAME_OP_MODE_READ_ONLY;

import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.classroot.interface4.openfire.core.Utils;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.bean.Msg;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.db.Const;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa1.smack.XMPPException;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa1.smack.XMPPTCPConnection;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack.Chat;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack.ChatManager;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack.SmackException;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.view.DropdownListView;

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.translate.SpeechTranslate;
//import com.net.yuesejiaoyou.redirect.ResolverB.interface4.io.agora.propeller.preprocessing.JHResultListener;
//import com.net.yuesejiaoyou.redirect.ResolverB.interface4.io.agora.propeller.preprocessing.VideoPreProcessing;
import com.net.yuesejiaoyou.redirect.ResolverB.interface3.UsersThread_01158B;
import com.net.yuesejiaoyou.redirect.ResolverB.interface3.UsersThread_01160B;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;


public class VideoChatViewActivity_zhubo extends Activity implements OnLayoutChangeListener, View.OnTouchListener,View.OnClickListener{

    private static final String LOG_TAG = VideoChatViewActivity_zhubo.class.getSimpleName();
    private String I, YOU,name,logo,headpicture,username,duifang="";
    private String strtime;
    private String guke_name;
    private String roomid,yid_zhubo,yid_guke;

    //屏幕高度
    private int screenHeight = 0;
    private int columns = 6;
    private int rows = 4;
    private int num =1;
    private int keyHeight = 0;
    private int xDelta;
    private int yDelta;
    private int sWidth;
    private int switchCameraCnt = 10;
    private static final int PERMISSION_REQ_ID_RECORD_AUDIO = 22;
    private static final int PERMISSION_REQ_ID_CAMERA = PERMISSION_REQ_ID_RECORD_AUDIO + 1;

    private boolean ks =  false;
    private boolean switchCameraCtrl = false;
    private boolean bJHThread = true;

    ArrayList<String> grpChatArray = new ArrayList<String>();
    // 每页显示的表情view
    private List<View> views = new ArrayList<View>();
    private List<String> staticFacesList;

    // 表情图标每页6列4行
//	private VideoPreProcessing  mvideoPreProcessing;
    private EditText send_sms;
    private SimpleDateFormat sd;
    private LayoutInflater inflater;

    // 表情列表
    private LinearLayout chat_face_container, chat_add_container,bottom;
    private ImageView image_face;// 表情图标
    private ViewPager mViewPager;
    private LinearLayout mDotsLayout;
    private EditText input;
    private TextView send;
    private DropdownListView mListView;
    private ScheduledExecutorService service;
    private LinearLayout ly1;
    private TextView t2;
    // private GiftItemView giftView ;
    // 美颜
//    private VideoPreProcessing mVideoPreProcessing;
    private SeekBar smoothLevel;
    private SeekBar whiteLevel;
    private ImageView meiyan,meibai;

    private int mopiLevel;
    private int meibaiLevel;
    // 群聊
    TextView grpChat,btn_chat;
    TextView sendMsg;
    EditText edtInput;
    RelativeLayout layChat;
    RelativeLayout another1;
    RelativeLayout layGrpChat;

    // 计时器相关
    private TextView time;
    private Timer timer;
	Reciever reciever;
    //private List<Shang_01160> shangList;
    private RtcEngine mRtcEngine;// Tutorial Step 1
    private RelativeLayout relativeLayout;
    private RelativeLayout activity_video_chat_view;

    // 软件盘弹起后所占高度阀值
    private RelativeLayout gbsp,xz,moshubang,xx;
    private ImageView imageView7;
    private LinearLayout ly2;
	MsgOperReciver_zhubo msgOperReciver;
    private SurfaceView remoteSurface,localSurface;
    private FrameLayout remoteContainer,localContainer;
    private CheckedTextView c1,c2,c3,c4,c5,c6;
    private PopupWindow popupWindow;
    private ImageView translate;
    private LinearLayout translate_select;
    private ImageView cn,en;
    private SpeechTranslate spchTranslate;
    private  boolean canhang_up=false;

    /***************************************************************
     *
     *
     ***************************************************************/
    private final IRtcEngineEventHandler mRtcEventHandler = new IRtcEngineEventHandler() { // Tutorial Step 1
        @Override
        public void onFirstRemoteVideoDecoded(final int uid, int width, int height, int elapsed) { // Tutorial Step 5
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setupRemoteVideo(uid);
                }
            });
        }

        @Override
        public void onJoinChannelSuccess(String channel, int uid, int elapsed) {
            SurfaceHolder holder = localSurface.getHolder();
            holder.addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder surfaceHolder) {

                }

                @Override
                public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

                }

                @Override
                public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

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
        public void onUserMuteVideo(final int uid, final boolean muted) { // Tutorial Step 10
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    onRemoteUserVideoMuted(uid, muted);
                }
            });
        }
    };


    /***************************************************************
     *
     *
     ***************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_video_chat_view);
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        sWidth = metric.widthPixels;
        time = (TextView) findViewById(R.id.time);
        roomid = getIntent().getStringExtra("roomid");
        yid_zhubo = getIntent().getStringExtra("yid_zhubo");
        guke_name = getIntent().getStringExtra("guke_name");
        SharedPreferences sharedPreferences = getSharedPreferences("Acitivity", Context.MODE_PRIVATE); //私有数据
        I = sharedPreferences.getString("id","");
        sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        username = sharedPreferences.getString("name","");

        // 读取美颜设置
        mopiLevel = sharedPreferences.getInt("mopi",1);
        meibaiLevel = sharedPreferences.getInt("meibai",1);
       //headpicture= sharedPreferences.getString("headpic","");

        layGrpChat = (RelativeLayout)this.findViewById(R.id.layGrpChat);
        grpChat = (TextView)this.findViewById(R.id.grpChat);

        activity_video_chat_view = (RelativeLayout) findViewById(R.id.activity_video_chat_view);


        findViewById(R.id.cafont).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupspWindow_rp(findViewById(R.id.activity_video_chat_view));
            }
        });

        //获取屏幕高度
        screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();
        //阀值设置为屏幕高度的1/3
        keyHeight = screenHeight/3;
        
        layChat = (RelativeLayout)findViewById(R.id.rl);
        edtInput = (EditText)this.findViewById(R.id.input_sms);
        sendMsg = (TextView)this.findViewById(R.id.send_sms);
        sendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sendCtxt = edtInput.getText().toString().trim();
                edtInput.setText("");
                hideSoftInputView();
                layChat.setVisibility(View.GONE);
                if(!sendCtxt.isEmpty()) {
                    sendMsgText(username+":"+sendCtxt);
                    LogDetect.send(LogDetect.DataType.specialType,"01160 主播发:",username+":"+sendCtxt);
                }
            }
        });
        ////////////////////
        btn_chat = (TextView)this.findViewById(R.id.btn_chat);
        btn_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layChat.setVisibility(View.VISIBLE);
                edtInput.requestFocus();
                showSoftInputView(layChat);
            }
        });
        ////////////////////

        final ImageView guanbi = (ImageView)this.findViewById(R.id.guanbi);
        guanbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLocalVideoMuteClicked(guanbi);
            }
        });

        final ImageView qiehuan = (ImageView)this.findViewById(R.id.qiehuan);
        ////////////////////
        qiehuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(switchCameraCtrl == false) {
                    switchCameraCtrl = true;
                    onSwitchCameraClicked(qiehuan);
                    startSwitchCameraCtrl();
                } else {
                    Toast.makeText(VideoChatViewActivity_zhubo.this, "请 "+switchCameraCnt+" 秒后再切换摄像头", Toast.LENGTH_SHORT).show();
                }
            }
        });
        ////////////////////


        ly1 = (LinearLayout)findViewById(R.id.ly1);
        ly1.setVisibility(View.GONE);
        t2 = (TextView)ly1.findViewById(R.id.t2);

        LogDetect.send(LogDetect.DataType.specialType,"01160 顾客id:",yid_zhubo);
		 reciever=new Reciever();
		IntentFilter intentFilter1 = new IntentFilter(Const.REWARD_ANCHOR);
		registerReceiver(reciever, intentFilter1);


       /* String mode1 = "kan_qian";
        String[] paramsMap1 = {Util.userid,yid_zhubo};
        UsersThread_01160 a = new UsersThread_01160(mode1,paramsMap1,handler);
        Thread t = new Thread(a.runnable);
        t.start();*/

        msgOperReciver = new MsgOperReciver_zhubo();
        IntentFilter intentFilter = new IntentFilter(Const.ACTION_MSG_ONECHAT);
        registerReceiver(msgOperReciver, intentFilter);

        if (checkSelfPermission(Manifest.permission.RECORD_AUDIO, PERMISSION_REQ_ID_RECORD_AUDIO) && checkSelfPermission(Manifest.permission.CAMERA, PERMISSION_REQ_ID_CAMERA)) {
            initAgoraEngineAndJoinChannel();
        }

        smoothLevel = (SeekBar)findViewById(R.id.seek_smooth);
        whiteLevel = (SeekBar)findViewById(R.id.seek_white);
        //smoothLevel.setThumb(change_thumb.getNewDrawable(VideoChatViewActivity_zhubo.this,R.drawable.huadong1,30,30));
       // whiteLevel.setThumb(change_thumb.getNewDrawable(VideoChatViewActivity_zhubo.this,R.drawable.huadong1,30,30));
        meibai = (ImageView) findViewById(R.id.meibai);
        meiyan = (ImageView) findViewById(R.id.meiyan);

//        mVideoPreProcessing = new VideoPreProcessing();

        /**
         * 鉴黄线程，5min一次
         */
//        new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//                while(bJHThread) {
//                    try {
//                        Thread.sleep(20*1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    if(bJHThread) {
////                        mVideoPreProcessing.startJianhuang();
//                    }
//                }
//            }
//        }).start();
//
//        /**
//         * 接入鉴黄监听
//         */
//        mVideoPreProcessing.setJHListener(new JHResultListener() {
//            @Override
//            public void onResult(final String scene, final String rate, final String suggestion, final String label) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        if(scene.equals("porn") && label.equals("porn")) {
//                            Toast.makeText(VideoChatViewActivity_zhubo.this, "检测到您可能有违规行为，请注意", Toast.LENGTH_SHORT).show();
//                        } else {
//                            //Toast.makeText(VideoChatViewActivity_zhubo.this, "正常", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//            }
//        });

//        mVideoPreProcessing.setSkinWhiteLevel(meibaiLevel);
//        mVideoPreProcessing.setSmoothLevel(mopiLevel);

//        smoothLevel.setProgress(mopiLevel);
//        smoothLevel.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
//                if(b) {
//                    seekBar.setTag(i);
//                    mVideoPreProcessing.setSmoothLevel(i);
//                }
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//                int i = (int)seekBar.getTag();
//                Toast.makeText(VideoChatViewActivity_zhubo.this,"磨皮度: "+i, Toast.LENGTH_SHORT).show();
//            }
//        });
//        //
//        whiteLevel.setProgress(meibaiLevel);
//        whiteLevel.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
//                if(b) {
//                    seekBar.setTag(i);
//                    mVideoPreProcessing.setSkinWhiteLevel(i);
//                }
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//                int i = (int)seekBar.getTag();
//                Toast.makeText(VideoChatViewActivity_zhubo.this,"美白度: "+i, Toast.LENGTH_SHORT).show();
//            }
//        });

        timer=new Timer();
        timer.schedule(new TimerTask(){
            SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
            long t = System.currentTimeMillis();
            long tl = t / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset();
            @Override
            public void run() {
                Timestamp tt = new Timestamp(tl);
                String starttime = sdf.format(tt);
                handler.sendMessage(handler.obtainMessage(1,(Object)starttime));
                tl = tl+1000;
            }
        }, 0,1000);

        //giftView = (GiftItemView) findViewById(R.id.gift_item_first);

        gbsp = (RelativeLayout)findViewById(R.id.gbsp);
        moshubang = (RelativeLayout)findViewById(R.id.moshubang);
        xx = (RelativeLayout)findViewById(R.id.xx);
        //xz = (RelativeLayout)findViewById(R.id.xz);
        imageView7 = (ImageView)findViewById(R.id.imageView7);


        spchTranslate=SpeechTranslate.getInstance();


        cn= (ImageView) findViewById(R.id.cn);
        en= (ImageView) findViewById(R.id.en);
        CountDownTimer t=new CountDownTimer(5000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                  canhang_up=true;
            }
        };

    }

    /***************************************************************
     * 一对一视频切换摄像头控制
     *
     *************************************************************/
    private void startSwitchCameraCtrl() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                switchCameraCnt = 10;
                while(switchCameraCnt > 0) {
                    switchCameraCnt--;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                switchCameraCtrl = false;
            }
        }).start();
    }

    /***************************************************************
     *
     *
     *************************************************************/
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


    /***************************************************************
     *
     *
     *************************************************************/
    public void onMeiyanClicked(View view) {
//        if (mVideoPreProcessing == null) {
//            mVideoPreProcessing = new VideoPreProcessing();
//        }

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

    /***************************************************************
     *
     *
     *************************************************************/
    private void addGrpChat(String txt) {
        int size = grpChatArray.size();
        if(size >= 4) {
            grpChatArray.remove(0);
        }
        grpChatArray.add(txt);
    }

    /***************************************************************
     *
     *
     *************************************************************/
    private void freshGrpChat() {

        if(grpChatArray.size() <= 0) {
            layGrpChat.setVisibility(View.GONE);
            return;
        } else {
            StringBuffer sb = new StringBuffer();
            for(int i = 0; i < grpChatArray.size(); i++) {
                sb.append(grpChatArray.get(i)+"\r\n");
            }
            grpChat.setText(/*guke_name+":"+*/sb.toString().substring(0, sb.length()-2));
            /*if(duifang.equals("")){
                grpChat.setText(guke_name+":"+sb.toString().substring(0, sb.length()-2));
            }else{
                grpChat.setText(guke_name+":"+sb.toString().substring(0, sb.length()-2));
            }*/

            layGrpChat.setVisibility(View.VISIBLE);
        }

    }

    /***************************************************************
     *
     *
     *************************************************************/
    String songing="";

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //////////////////
            case R.id.c1:
                String mode ="report";
                String[] paramsMap = {Util.userid,yid_zhubo,c1.getText().toString()};
                UsersThread_01160B az = new UsersThread_01160B(mode, paramsMap, handler);
                Thread t = new Thread(az.runnable);
                t.start();
                LogDetect.send(LogDetect.DataType.specialType,"01160 开线程举报:","3");
                break;
            //////////////////
            case R.id.c2:
                String mode112 ="report";
                String[] paramsMap112 = {Util.userid,yid_zhubo,c2.getText().toString()};
                UsersThread_01160B a1 = new UsersThread_01160B(mode112, paramsMap112, handler);
                Thread t1 = new Thread(a1.runnable);
                t1.start();
                LogDetect.send(LogDetect.DataType.specialType,"01160 开线程举报:","3");
                break;
            //////////////////
            case R.id.c3:
                String mode2 ="report";
                String[] paramsMap2 = {Util.userid,yid_zhubo,c3.getText().toString()};
                UsersThread_01160B a2 = new UsersThread_01160B(mode2, paramsMap2, handler);
                Thread t2 = new Thread(a2.runnable);
                t2.start();
                LogDetect.send(LogDetect.DataType.specialType,"01160 开线程举报:","3");
                break;
            //////////////////
            case R.id.c4:
                String mode3 ="report";
                String[] paramsMap3 = {Util.userid,yid_zhubo,c4.getText().toString()};
                UsersThread_01160B a3 = new UsersThread_01160B(mode3, paramsMap3, handler);
                Thread t3 = new Thread(a3.runnable);
                t3.start();
                LogDetect.send(LogDetect.DataType.specialType,"01160 开线程举报:","3");
                break;
            //////////////////
            case R.id.c5:
                String mode4 ="report";
                String[] paramsMap4 = {Util.userid,yid_zhubo,c5.getText().toString()};
                UsersThread_01160B a4 = new UsersThread_01160B(mode4, paramsMap4, handler);
                Thread t4 = new Thread(a4.runnable);
                t4.start();
                LogDetect.send(LogDetect.DataType.specialType,"01160 开线程举报:","3");
                break;
            //////////////////
            case R.id.c6:
                String mode5 ="report";
                String[] paramsMap5 = {Util.userid,yid_zhubo,c6.getText().toString()};
                UsersThread_01160B a5 = new UsersThread_01160B(mode5, paramsMap5, handler);
                Thread t5 = new Thread(a5.runnable);
                t5.start();
                LogDetect.send(LogDetect.DataType.specialType,"01160 开线程举报:","3");
                break;
        }
    }

    /***************************************************************
     *
     *
     *************************************************************/
    private class MsgOperReciver_zhubo extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            String msgBody=intent.getStringExtra("oneuponechat");

            if(msgBody.contains("开0倒1计2时")) {
                LogDetect.send(LogDetect.DataType.specialType, "01160 主播开倒计时信息 :", msgBody);
                handler.sendMessage(handler.obtainMessage(330, (Object) msgBody));
            }else if(msgBody.startsWith("[☆")){
                LogDetect.send(LogDetect.DataType.specialType, "01160 主播收信息 invite:", msgBody);
                handler.sendMessage(handler.obtainMessage(350, (Object) msgBody));
            }else{
                LogDetect.send(LogDetect.DataType.specialType, "01160 主播收信息 invite:", msgBody);
                handler.sendMessage(handler.obtainMessage(320, (Object) msgBody));
            }
        }
    }

    /***************************************************************
     *
     *
     *************************************************************/
	private class Reciever extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			 Toast.makeText(VideoChatViewActivity_zhubo.this,"得到红包 "+intent.getStringExtra("reward_num")+" 悦币",Toast.LENGTH_LONG).show();
		}
	}

    /***************************************************************
     *
     *
     *************************************************************/
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

    /***************************************************************
     * 执行发送消息 文本类型
     * @param content
     ***************************************************************/
    void sendMsgText(String content) {
        //edtInput.setText("");
        //addGrpChat(content);
        //handler.obtainMessage(19).sendToTarget();
        final String message = content + Const.SPLIT + Const.ACTION_MSG_ONECHAT
                + Const.SPLIT + sd.format(new Date())+ Const.SPLIT+username;
        //LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"message: "+message);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"before sendMessage()");
                    sendMessage(Utils.xmppConnection, message, yid_zhubo);
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

    /***************************************************************
     * 隐藏软键盘
     *
     ***************************************************************/
    public void hideSoftInputView() {
        InputMethodManager manager = ((InputMethodManager) this
                .getSystemService(Activity.INPUT_METHOD_SERVICE));
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null)
                manager.hideSoftInputFromWindow(getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /***************************************************************
     *
     *
     **************************************************************/
    private void initAgoraEngineAndJoinChannel() {
        initializeAgoraEngine();     // Tutorial Step 1
        setupVideoProfile();         // Tutorial Step 2
        setupLocalVideo();           // Tutorial Step 3
        joinChannel();               // Tutorial Step 4
    }

    /***************************************************************
     *
     *
     **************************************************************/
    public boolean checkSelfPermission(String permission, int requestCode) {
        Log.i(LOG_TAG, "checkSelfPermission " + permission + " " + requestCode);
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

    /***************************************************************
     *
     *
     **************************************************************/
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
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

    /***************************************************************
     *
     *
     **************************************************************/
    public final void showLongToast(final String msg) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            }
        });
    }

    /***************************************************************
     *
     *
     **************************************************************/
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v("TT","before disableAudioFrame()");
//        mVideoPreProcessing.disableAudioFrame();
        Log.v("TT","after disableAudioFrame()");
        leaveChannel();
        RtcEngine.destroy();
        mRtcEngine = null;
        timer.cancel();//关闭定时器
		unregisterReceiver(reciever);
		unregisterReceiver(msgOperReciver);
    }

    /***************************************************************
     * Tutorial Step 10
     *
     **************************************************************/
    public void onLocalVideoMuteClicked(View view) {
        ImageView iv = (ImageView) view;
        if (iv.isSelected()) {
            iv.setSelected(false);
            iv.clearColorFilter();
        } else {
            iv.setSelected(true);
            //iv.setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
        }

        mRtcEngine.muteLocalVideoStream(iv.isSelected());

        FrameLayout container = (FrameLayout) findViewById(R.id.local_video_view_container);
        SurfaceView surfaceView = (SurfaceView) container.getChildAt(0);
        surfaceView.setZOrderMediaOverlay(!iv.isSelected());
        surfaceView.setVisibility(iv.isSelected() ? View.GONE : View.VISIBLE);
    }

    /***************************************************************
     * Tutorial Step 9
     *
     **************************************************************/
    public void onLocalAudioMuteClicked(View view) {
        ImageView iv = (ImageView) view;
        if (iv.isSelected()) {
            iv.setSelected(false);
            iv.clearColorFilter();
        } else {
            iv.setSelected(true);
            //iv.setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
        }
        mRtcEngine.muteLocalAudioStream(iv.isSelected());
    }

    // Tutorial Step 8
    public void onSwitchCameraClicked(View view) {
        mRtcEngine.switchCamera();
    }

    /***************************************************************
     * Tutorial Step 6
     *
     **************************************************************/
    public void onEncCallClicked(View view) {
          if(canhang_up=true) {
              //给主播扣钱
              String mode1 = "kou_zhubo";
              String[] paramsMap1 = {Util.userid, Util.userid};
              LogDetect.send(LogDetect.DataType.specialType, "01160 主播挂断 video:", Util.userid);
              UsersThread_01158B a = new UsersThread_01158B(mode1, paramsMap1, handler);
              Thread c = new Thread(a.runnable);
              c.start();
          }else{
              Toast.makeText(VideoChatViewActivity_zhubo.this,"5秒后才可挂断",Toast.LENGTH_SHORT).show();
          }

    }

    /***************************************************************
     * Tutorial Step 1
     *
     **************************************************************/
    private void initializeAgoraEngine() {
        try {
            mRtcEngine = RtcEngine.create(getBaseContext(), getString(R.string.agora_app_id), mRtcEventHandler);
            mRtcEngine.setLogFile(Environment.getExternalStorageDirectory()+ File.separator+"agora_log.txt");
        } catch (Exception e) {
            Log.e(LOG_TAG, Log.getStackTraceString(e));

            throw new RuntimeException("NEED TO check rtc sdk init fatal error\n" + Log.getStackTraceString(e));
        }
    }

    /***************************************************************
     * Tutorial Step 2
     *
     **************************************************************/
    private void setupVideoProfile() {
        mRtcEngine.enableVideo();
        mRtcEngine.setVideoProfile(Constants.VIDEO_PROFILE_720P, false);

        mRtcEngine.setDefaultAudioRoutetoSpeakerphone(true);
        mRtcEngine.setSpeakerphoneVolume(100);
		mRtcEngine.setRecordingAudioFrameParameters(16000,1,RAW_AUDIO_FRAME_OP_MODE_READ_ONLY ,1024);
    }

    /***************************************************************
     * Tutorial Step 3
     *
     **************************************************************/
    private void setupLocalVideo() {
        FrameLayout container = (FrameLayout) findViewById(R.id.local_video_view_container);
        localContainer = container;
        SurfaceView surfaceView = RtcEngine.CreateRendererView(getBaseContext());
        localSurface = surfaceView;
        surfaceView.setZOrderMediaOverlay(true);
        container.addView(surfaceView);
        mRtcEngine.setupLocalVideo(new VideoCanvas(surfaceView, VideoCanvas.RENDER_MODE_HIDDEN, 0));

        ////////////////////
        container.setOnTouchListener(this);
        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(localSurface == null || remoteSurface == null) {
                    //Toast.makeText(VideoChatViewActivity.this, "surface null", Toast.LENGTH_SHORT).show();
                } else {

                    if(remoteContainer == null || localContainer == null) {
                        //Toast.makeText(VideoChatViewActivity.this, "container null", Toast.LENGTH_SHORT).show();
                    } else {
                        //Toast.makeText(VideoChatViewActivity.this, "onClick", Toast.LENGTH_SHORT).show();
                        localContainer.removeAllViews();
                        remoteContainer.removeAllViews();
                        String tag = (String)remoteContainer.getTag();
                        if("remote".equals(tag)) {
                            remoteContainer.addView(localSurface);
                            localContainer.addView(remoteSurface);
                            localSurface.setZOrderMediaOverlay(false);
                            remoteSurface.setZOrderMediaOverlay(true);
                            remoteContainer.setTag("local");
                        } else if("local".equals(tag)) {
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
        ////////////////////
    }

    /***************************************************************
     * Tutorial Step 4
     *
     **************************************************************/
    private void joinChannel() {
        mRtcEngine.joinChannel(null, roomid, "Extra Optional Data", 0); // if you do not specify the uid, we will generate the uid for you
        // Toast.makeText(this,"roomid: "+roomid,Toast.LENGTH_SHORT).show();
    }

    /***************************************************************
     * Tutorial Step 5
     *
     **************************************************************/
    private void setupRemoteVideo(int uid) {
        FrameLayout container = (FrameLayout) findViewById(R.id.remote_video_view_container);
        remoteContainer = container;
        remoteContainer.setTag("remote");
        if (container.getChildCount() >= 1) {
            return;
        }

        SurfaceView surfaceView = RtcEngine.CreateRendererView(getBaseContext());
        remoteSurface = surfaceView;
        container.addView(surfaceView);
        mRtcEngine.setupRemoteVideo(new VideoCanvas(surfaceView, VideoCanvas.RENDER_MODE_HIDDEN, uid));

        surfaceView.setTag(uid); // for mark purpose
        View tipMsg = findViewById(R.id.quick_tips_when_use_agora_sdk); // optional UI
        tipMsg.setVisibility(View.GONE);
    }

    /***************************************************************
     * Tutorial Step 6
     *
     **************************************************************/
    private void leaveChannel() {
        mRtcEngine.leaveChannel();
    }

    /***************************************************************
     * Tutorial Step 7
     *
     **************************************************************/
    private void onRemoteUserLeft() {
        FrameLayout container = (FrameLayout) findViewById(R.id.remote_video_view_container);
        container.removeAllViews();

        View tipMsg = findViewById(R.id.quick_tips_when_use_agora_sdk); // optional UI
        tipMsg.setVisibility(View.VISIBLE);
        finish();
    }

    /***************************************************************
     * Tutorial Step 10
     *
     **************************************************************/
    private void onRemoteUserVideoMuted(int uid, boolean muted) {
        FrameLayout container = (FrameLayout) findViewById(R.id.remote_video_view_container);

        SurfaceView surfaceView = (SurfaceView) container.getChildAt(0);
        LogDetect.send(LogDetect.DataType.specialType, "TyrantsFragment: ", surfaceView);
        Object tag = surfaceView.getTag();
        if (tag != null && (Integer) tag == uid) {
            surfaceView.setVisibility(muted ? View.GONE : View.VISIBLE);
        }
    }

    /***************************************************************
     *
     *
     **************************************************************/
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @SuppressWarnings("unchecked")
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 280:
                    String json1 = (String)msg.obj;
                    if(!json1.equals("")){
                        try {
                            JSONObject jsonObject = new JSONObject(json1);
                            LogDetect.send(LogDetect.DataType.exceptionType,"01160 给主播扣钱:",jsonObject);
                            //如果返回1，说明修改成功了
                            String success_ornot=jsonObject.getString("success");
                            if(success_ornot.equals("1")){
                                LogDetect.send(LogDetect.DataType.specialType,"扣了几次:",num);
                                String qian = num+"";
                               // Toast.makeText(VideoChatViewActivity_zhubo.this, "主播挂断，扣除1分钟的钱.", Toast.LENGTH_SHORT).show();
                                finish();
                            }else{
                                Toast.makeText(VideoChatViewActivity_zhubo.this, "您的余额不足，无法挂断", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else{
                        Toast.makeText(VideoChatViewActivity_zhubo.this,"请检查网络连接", Toast.LENGTH_SHORT).show();
                    }
                    break;

                case 260:
                  // service.shutdown();
                    Toast.makeText(VideoChatViewActivity_zhubo.this, "您的余额不足", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case 210:
             /*     String xiaoxi = (String) msg.obj;
                    LogDetect.send(LogDetect.DataType.specialType,"01160 实时充值 :",xiaoxi);
                    Toast.makeText(VideoChatViewActivity_zhubo.this, xiaoxi, Toast.LENGTH_SHORT).show();
                    break;*/
              /*case 300:
                    String json = (String)msg.obj;
                    LogDetect.send(LogDetect.DataType.exceptionType,"01160 倒计时:",json);
                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        LogDetect.send(LogDetect.DataType.exceptionType,"01160 倒计时:",jsonObject);
                        //如果返回1，说明修改成功了
                        String success_ornot=jsonObject.getString("success");
                        if(success_ornot.equals("0")){
                            ly1.setVisibility(View.GONE);
                            num ++;
                            String mode1 = "kan_qian";
                            String[] paramsMap1 = {Util.userid,yid_zhubo};
                            UsersThread_01160 a = new UsersThread_01160(mode1,paramsMap1,handler);


                            service = Executors
                                    .newSingleThreadScheduledExecutor();
                            service.scheduleAtFixedRate(a.runnable, 1, 1, TimeUnit.MINUTES);
                            LogDetect.send(LogDetect.DataType.specialType,"01160 主播执行1分钟定时看用户钱:",TimeUnit.MINUTES);

                        }//否则弹出提示
                        else if(success_ornot.equals("2")){
                            ly1.setVisibility(View.GONE);
                            String mode1 = "mod_online";
                            String[] paramsMap1 = {Util.userid,Util.userid};
                            UsersThread_01160 a = new UsersThread_01160(mode1,paramsMap1,handler);
                            Thread t = new Thread(a.runnable);
                            t.start();
                        }
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    break;*/
                case 320:
                    String msgBody = (String)msg.obj;
                    if(!msgBody.equals("")){
                        Msg m = new Msg();
                        //List<Msg> listMsg = new ArrayList<Msg>();
                        String[] msgs = msgBody.split("卍");
                        LogDetect.send(LogDetect.DataType.specialType,"主播收到", "msgs:"+msgs);
                       // m.setContent(msgs[0]);
                       // m.setType(msgs[1]);
                       // m.setDate(msgs[2]);
                        duifang = msgs[3];//发送人
                       // m.setBak1(msgs[4]); //发送人头像

                        addGrpChat(msgs[0]);
                        LogDetect.send(LogDetect.DataType.specialType,"01160 顾客发zhubo:",guke_name+":"+msgs[0]);
                        //freshGrpChat();
                        handler.obtainMessage(19).sendToTarget();
                        LogDetect.send(LogDetect.DataType.specialType,"01160", "msgs:"+msgs.toString());
                    }else{
                        Toast.makeText(VideoChatViewActivity_zhubo.this,"网络连接异常，请稍后再试", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 350:

                    break;
                case 330:
                    ly1.setVisibility(View.VISIBLE);
					Toast.makeText(VideoChatViewActivity_zhubo.this,"请提示对方，余额不足",Toast.LENGTH_LONG).show();
                    CountDownTimer c = new CountDownTimer(60*2000,1000){
                        @Override
                        public void onTick(long l) {
                            //String a = String.valueOf(millisUntilFinished);
                            t2.setText(""+ String.format("%d",l/1000)+"秒");
                        }
                        @Override
                        public void onFinish() {
                            String mode1 = "mod_online";
                            String[] paramsMap1 = {Util.userid,yid_guke,"1","2"};
                            UsersThread_01158B a = new UsersThread_01158B(mode1,paramsMap1,handler);
                            Thread t = new Thread(a.runnable);
                            t.start();
                        }
                    }.start();

                           /* new Thread(new MyThread()).start();*/
                break;
                case 19:
                    freshGrpChat();
                    break;
                case 25:
                    String reportcheckjson = (String) msg.obj;
                    if(reportcheckjson.isEmpty()){
                        Toast.makeText(VideoChatViewActivity_zhubo.this, "网络连接异常。请稍后重试", Toast.LENGTH_SHORT).show();
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
                    if("1".equals(reportcheckjson))
                    {
                        Toast.makeText(VideoChatViewActivity_zhubo.this, "成功", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(VideoChatViewActivity_zhubo.this, "失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 1:
                    strtime = (String) msg.obj;
                    if(!strtime.equals("")){
                        time.setText(strtime);
                    }else{
                        time.setText("");
                    }
                    break;
                case 203:
                    String json_report = (String) (msg).obj;
                    LogDetect.send(LogDetect.DataType.specialType,"01160:",msg);
                    if(!json_report.isEmpty()){
                        try {
                            JSONObject jsonObject1 = new JSONObject(json_report);
                            LogDetect.send(LogDetect.DataType.specialType,"01160:",jsonObject1);
                            //拉黑
                            String success_ornot=jsonObject1.getString("success");
                            LogDetect.send(LogDetect.DataType.specialType,"01160 success_ornot:",success_ornot);
                            if(success_ornot.equals("1")){
                                popupWindow.dismiss();
                                Toast.makeText(VideoChatViewActivity_zhubo.this, "举报成功", Toast.LENGTH_SHORT).show();
                            }
                        }catch(JSONException e){
                            e.printStackTrace();
                        }
                    }else{
                        Toast.makeText(VideoChatViewActivity_zhubo.this, "举报失败，请检查网络连接", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };


    /***************************************************************
     * 表情页改变时，dots效果也要跟着改变
     *
     ***************************************************************/
    class PageChange implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int arg0) {
            for (int i = 0; i < mDotsLayout.getChildCount(); i++) {
                mDotsLayout.getChildAt(i).setSelected(false);
            }
            mDotsLayout.getChildAt(arg0).setSelected(true);
        }
    }

    /***************************************************************
     * 发送消息
     * @param content
     * @param touser
     * @throws XMPPException
     * @throws SmackException.NotConnectedException
     ***************************************************************/
    public void sendMessage(XMPPTCPConnection mXMPPConnection, String content,
                            String touser) throws XMPPException, SmackException.NotConnectedException {
        if (mXMPPConnection == null || !mXMPPConnection.isConnected()) {

        }

        ChatManager chatmanager = Utils.xmppchatmanager;

        Chat chat = chatmanager.createChat(yid_zhubo + "@" + Const.XMPP_HOST, null);
        if (chat != null) {

            chat.sendMessage(content, Util.userid + "@" + Const.XMPP_HOST);
            Log.e("jj", "发送成功");
            LogDetect.send(LogDetect.DataType.noType,"01160主播发消息" ,content);
        }else{
            //LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"send fail:chat is null");
        }
    }

    /***************************************************************
     * 连点两次退出
     *
     ***************************************************************/
    private long firstTime = 0;
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) {
                    Toast.makeText(this, "再次点击可以挂断电话", Toast.LENGTH_SHORT).show();
                    firstTime = secondTime;// 更新firstTime
                } else {// 两次按键小于2秒时，退出应用
                    //给主播扣钱
                	//hideSoftInputView();
                    String mode1 = "kou_zhubo";
                    String[] paramsMap1 = {Util.userid,Util.userid};
                    LogDetect.send(LogDetect.DataType.specialType,"01160 主播挂断 video:",Util.userid);
                    UsersThread_01158B a = new UsersThread_01158B(mode1,paramsMap1,handler);
                    Thread c = new Thread(a.runnable);
                    c.start();

                }
                return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    /***************************************************************
     *
     *
     ***************************************************************/
    @Override
	public void onLayoutChange(View v, int left, int top, int right,
                               int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
		if(oldBottom != 0 && bottom != 0 &&(oldBottom - bottom > keyHeight)){
        }else if(oldBottom != 0 && bottom != 0 &&(bottom - oldBottom > keyHeight)){
            //关闭
            layChat.setVisibility(View.GONE);
        }
		
	}

    /***************************************************************
     *
     *
     ***************************************************************/
	 @Override
	    protected void onResume() {
	        super.onResume();
	         
	        //添加layout大小发生改变监听器
	        activity_video_chat_view.addOnLayoutChangeListener(this);
	    }

    /***************************************************************
     * 举报
     *
     ***************************************************************/
    @SuppressLint({ "RtlHardcoded", "NewApi" })
    public void showPopupspWindow_rp(View parent) {
        // 加载布局
        LayoutInflater inflater = LayoutInflater.from(VideoChatViewActivity_zhubo.this);
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

        ////////////////////
        quxiao = (TextView)layout.findViewById(R.id.quxiao);
        quxiao.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                popupWindow.dismiss();
            }
        });
        ////////////////////


        popupWindow = new PopupWindow(layout, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        // 控制键盘是否可以获得焦点
        popupWindow.setFocusable(true);
        WindowManager.LayoutParams lp = VideoChatViewActivity_zhubo.this.getWindow().getAttributes();
        lp.alpha = 0.5f;
        VideoChatViewActivity_zhubo.this.getWindow().setAttributes(lp);
        popupWindow.setBackgroundDrawable(new BitmapDrawable(null, ""));
        //popupWindow.showAsDropDown(parent, 0, 0,Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL);
        popupWindow.showAtLocation(parent, Gravity.CENTER_VERTICAL|Gravity.BOTTOM, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            public void onDismiss() {
                WindowManager.LayoutParams lp = VideoChatViewActivity_zhubo.this.getWindow().getAttributes();
                lp.alpha = 1f;
                VideoChatViewActivity_zhubo.this.getWindow().setAttributes(lp);
            }
        });
    }

}