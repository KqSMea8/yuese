package com.net.yuesejiaoyou.activity;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect.DataType;
import com.net.yuesejiaoyou.classroot.interface4.openfire.core.Utils;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.bean.Msg;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.bean.Session;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.db.ChatMsgDao;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.db.Const;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.db.SessionDao;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa1.smack.XMPPException;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa1.smack.XMPPTCPConnection;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack.Chat;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack.ChatManager;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack.SmackException.NotConnectedException;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.view.DropdownListView;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.view.DropdownListView.OnRefreshListenerHeader;
import com.net.yuesejiaoyou.classroot.interface4.openfire.interface4.ChatAdapter;
import com.net.yuesejiaoyou.classroot.interface4.openfire.interface4.ExpressionUtil;
import com.net.yuesejiaoyou.classroot.interface4.openfire.interface4.FaceVPAdapter;
import com.net.yuesejiaoyou.classroot.interface4.openfire.interface4.Util;
import com.net.yuesejiaoyou.redirect.ResolverB.interface3.UsersThread_01158B;
import com.net.yuesejiaoyou.redirect.ResolverB.interface3.UsersThread_01160B;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.agora.P2PVideoConst;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.URL;
import com.net.yuesejiaoyou.bean.ZhuboInfo;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.agora.zhubo.GukeInfo;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.agora.zhubo.ZhuboActivity;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.im.IMManager;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.util.AudioRecoderUtils;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.BaseActivity;
import com.net.yuesejiaoyou.utils.Tools;
import com.net.yuesejiaoyou.widget.GiftDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.DialogCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.OnClick;
import okhttp3.Call;


@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("SimpleDateFormat")
public class ChatActivity extends BaseActivity implements OnClickListener,
        OnRefreshListenerHeader {
    private ViewPager mViewPager;
    private LinearLayout mDotsLayout;
    private EditText input;
    private TextView send;        //,sendFile;
    private DropdownListView mListView;
    private ChatAdapter mLvAdapter;
    private ChatMsgDao msgDao;
    private SessionDao sessionDao;
    private LinearLayout chat_face_container, /*chat_add_container,*/
            bottom, l1;
    private ImageView image_face, dv, jb;// 表情图标
    private TextView tv_title, /*tv_pic,// 图片
*/
            tv_voc;    // 语音
    /*	private Chat chat;*/
    // 表情图标每页6列4行
    private int columns = 6;
    private int rows = 4;
    // 每页显示的表情view
    private List<View> views = new ArrayList<View>();
    // 表情列表
    private List<String> staticFacesList;
    private PopupWindow popupWindow;
    // 消息
    private List<Msg> listMsg;
    private SimpleDateFormat sd;
    private MsgOperReciver msgOperReciver;
    /*	private MsgOperReciver1 msgOperReciver1;*/
    private LayoutInflater inflater;
    private int offset;
    private String I, YOU, name, logo, headpicture, username, gender, is_v;// 为了好区分，I就是自己，YOU就是对方
    private Button fanhui;
    /*private TextView yuyin,yuyin1,shuru;*/

    private Button button_more_moremodify;

    private RelativeLayout chat_main, base_header;

    private CheckedTextView c1, c2, c3, c4, c5, c6;

    private MyReceiver_Home receiver;

    private LinearLayout layBottom;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1000:
                    callClick();
//                    if (!"0".equals(com.net.yuesejiaoyou.classroot.interface4.util.Util.iszhubo)) {
//                        ZhuboActivity.startCallGuke(ChatActivity.this, new GukeInfo(YOU, name, logo, System.currentTimeMillis() + "",
//                                P2PVideoConst.ZHUBO_CALL_GUKE, P2PVideoConst.HAVE_NO_YUYUE));
//                        break;
//                    }
//
//                    String mode1 = "zhubo_online";
//                    String[] paramsMap1 = {"", I, YOU};
//                    UsersThread_01158B a = new UsersThread_01158B(mode1, paramsMap1, mHandler);
//                    Thread c = new Thread(a.runnable);
//                    c.start();
                    break;
                case 201:
                    String json1 = (String) msg.obj;
                    ////////////////////------------------>>>>>>>>
                    LogDetect.send(DataType.specialType, "01160:", msg);
                    ////////////////////------------------>>>>>>>>
                    if (!json1.isEmpty()) {
                        try {
                            JSONObject jsonObject1 = new JSONObject(json1);
                            ////////////////////------------------>>>>>>>>
                            LogDetect.send(DataType.specialType, "01160:", jsonObject1);
                            ////////////////////------------------>>>>>>>>
                            //如果返回0，他钱不够，弹窗让他充值
                            String success_ornot = jsonObject1.getString("success");
                            ////////////////////------------------>>>>>>>>
                            LogDetect.send(DataType.specialType, "01160 success_ornot:", success_ornot);
                            ////////////////////------------------>>>>>>>>
                            if (success_ornot.equals("0")) {
                                hideSoftInputView();
                                showPopupspWindow(chat_main);
                            } else if (success_ornot.equals("1")) {
                                sendMsgText(input.getText().toString());
                                //	Toast.makeText(ChatActivity.this, "您花费了2悦币", Toast.LENGTH_SHORT).show();
                            } else if (success_ornot.equals("4")) {
                                Toast.makeText(ChatActivity.this, "您被主播拉黑了", Toast.LENGTH_SHORT).show();
                            }/*else if(success_ornot.equals("2")){
                                Toast.makeText(ChatActivity.this, "聊天失败，请查看网络连接", Toast.LENGTH_SHORT).show();
							}else if(success_ornot.equals("3")){
								sendMsgText(input.getText().toString());
							}else if(success_ornot.equals("5")){
								Toast.makeText(ChatActivity.this, "您已被封禁。", Toast.LENGTH_SHORT).show();
							}*/
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(ChatActivity.this, "聊天失败，请检查网络连接", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 230:
                    String json = (String) msg.obj;
                    LogDetect.send(DataType.specialType, "01160:", msg);
                    if (!json.isEmpty()) {
                        try {
                            JSONObject jsonObject = new JSONObject(json);
                            LogDetect.send(DataType.specialType, "01160:", jsonObject);
                            //如果返回1，说明成功了
                            String success_ornot = jsonObject.getString("success");
                            LogDetect.send(DataType.specialType, "01160:", success_ornot);
                            if (success_ornot.equals("1")) {

                                SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                final String timestamp = new Date().getTime() + "";

                            } else if (success_ornot.equals("2")) {
                                showPopupspWindow_reservation(mListView, 2);
                                Toast.makeText(ChatActivity.this, "主播忙碌，请稍后再试", Toast.LENGTH_SHORT).show();
                            } else if (success_ornot.equals("3")) {
                                showPopupspWindow_reservation(mListView, 3);
                                Toast.makeText(ChatActivity.this, "主播设置勿打扰，请稍后再试", Toast.LENGTH_SHORT).show();
                            } else if (success_ornot.equals("4")) {
                                showPopupspWindow_reservation(mListView, 4);
                                Toast.makeText(ChatActivity.this, "主播不在线", Toast.LENGTH_SHORT).show();
                            } else if (success_ornot.equals("0")) {
                                Toast.makeText(ChatActivity.this, "您的余额不足", Toast.LENGTH_SHORT).show();
                            } else if (success_ornot.equals("5")) {
                                Toast.makeText(ChatActivity.this, "主播被封禁", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(ChatActivity.this, "请检查网络连接", Toast.LENGTH_SHORT).show();
                    }
                    tv_voc.setClickable(true);
                    break;
                //举报
                case 203:
                    String json_report = (String) (msg).obj;
                    LogDetect.send(DataType.specialType, "01160:", msg);
                    if (!json_report.isEmpty()) {
                        try {
                            JSONObject jsonObject1 = new JSONObject(json_report);
                            LogDetect.send(DataType.specialType, "01160:", jsonObject1);
                            //拉黑
                            String success_ornot = jsonObject1.getString("success");
                            LogDetect.send(DataType.specialType, "01160 success_ornot:", success_ornot);
                            if (success_ornot.equals("1")) {
                                popupWindow.dismiss();
                                Toast.makeText(ChatActivity.this, "举报成功", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(ChatActivity.this, "举报失败，请检查网络连接", Toast.LENGTH_SHORT).show();
                    }
                    break;
                //拉黑
                case 204:
                    String json_hei = (String) (msg).obj;
                    LogDetect.send(DataType.specialType, "01160:", msg);
                    if (!json_hei.isEmpty()) {
                        try {
                            JSONObject jsonObject1 = new JSONObject(json_hei);
                            LogDetect.send(DataType.specialType, "01160:", jsonObject1);
                            //拉黑
                            String success_ornot = jsonObject1.getString("success");
                            LogDetect.send(DataType.specialType, "01160 success_ornot:", success_ornot);
                            if (success_ornot.equals("1")) {
                                popupWindow.dismiss();
                                Toast.makeText(ChatActivity.this, "拉黑成功", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(ChatActivity.this, "拉黑失败，请检查网络连接", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 210:
                    String json_reservation = (String) (msg).obj;
                    LogDetect.send(DataType.specialType, "01160:", msg);
                    if (!json_reservation.isEmpty()) {
                        try {
                            JSONObject jsonObject1 = new JSONObject(json_reservation);
                            LogDetect.send(DataType.specialType, "01160:", jsonObject1);
                            //预约
                            String success_ornot = jsonObject1.getString("success");
                            LogDetect.send(DataType.specialType, "01160 success_ornot:", success_ornot);
                            if (success_ornot.equals("-2")) {
                                Toast.makeText(ChatActivity.this, "余额不足，无法预约", Toast.LENGTH_SHORT).show();
                            } else if (success_ornot.equals("-1")) {
                                com.net.yuesejiaoyou.classroot.interface4.util.Util.sendMsgText("『" + com.net.yuesejiaoyou.classroot.interface4.util.Util.nickname + "』 Appointment is successful", YOU);
                                Toast.makeText(ChatActivity.this, "已预约成功，无法再次预约", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ChatActivity.this, "预约成功,消费" + success_ornot + "悦币", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(ChatActivity.this, "预约失败，请检查网络连接", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }

        }
    };

    private AudioRecoderUtils recorder = new AudioRecoderUtils();

    @SuppressLint("ShowToast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        layBottom = findViewById(R.id.lay_bottom);

        SharedPreferences sharedPreferences = getSharedPreferences("Acitivity", Context.MODE_PRIVATE); //私有数据


        I = sharedPreferences.getString("userid", "");

        username = sharedPreferences.getString("nickname", "");
        headpicture = sharedPreferences.getString("headpic", "");

        gender = sharedPreferences.getString("sex", "");

        //I = "147852";/* PreferencesUtils.getSharePreStr(this, "username"); */
        YOU = getIntent().getStringExtra("id");


        name = getIntent().getStringExtra("name");

        logo = getIntent().getStringExtra("headpic");


        l1 = (LinearLayout) findViewById(R.id.l1);


        inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText(name);
        sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        /*sdName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");*/
        msgDao = new ChatMsgDao(this);
        sessionDao = new SessionDao(this);
        msgOperReciver = new MsgOperReciver();
        IntentFilter intentFilter = new IntentFilter(Const.ACTION_MSG_OPER);
        registerReceiver(msgOperReciver, intentFilter);

        receiver = new MyReceiver_Home();
        IntentFilter homeFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        registerReceiver(receiver, homeFilter);
        /*msgOperReciver1 = new MsgOperReciver1();
        IntentFilter intentFilter1 = new IntentFilter(Const.ACTION_INPUT);
		registerReceiver(msgOperReciver1, intentFilter1);*/

        if (logo.equals("000000")) {
            mListView = (DropdownListView) findViewById(R.id.message_chat_listview);
            mListView.setOnRefreshListenerHead(this);

            bottom = (LinearLayout) findViewById(R.id.bottom);
            bottom.setVisibility(View.GONE);

        } else {
            staticFacesList = ExpressionUtil.initStaticFaces(this);
            // 初始化控件
            initViews();
            // 初始化表情
            initViewPager();
            // 初始化更多选项（即表情图标右侧"+"号内容）
            initAdd();
        }
        init_Data(YOU, I);
        // 初始化数据
        initData();
        Util.currentfrom = YOU;
        //Intent intent = new Intent();


    }

    @Override
    protected int getContentView() {
        return R.layout.activity_chat;
    }

    private void init_Data(String from, String to) {

        msgDao.toberead(from, to);
        Intent intent = new Intent(Const.ACTION_MSG_OPER);// 发送广播，通知消息界面更新
        sendBroadcast(intent);
    }

    public void callClick() {
        if (!"0".equals(com.net.yuesejiaoyou.classroot.interface4.util.Util.iszhubo)) {
            Toast.makeText(this, "主播不能跟主播通话", Toast.LENGTH_SHORT).show();
            return;
        }


        OkHttpUtils.post(this)
                .url(URL.URL_CALL)
                .addParams("param1", "")
                .addParams("param2", com.net.yuesejiaoyou.classroot.interface4.util.Util.userid)
                .addParams("param3", YOU)
                .build()
                .execute(new DialogCallback(this) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        showToast("网络异常");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        super.onResponse(response, id);
                        if (TextUtils.isEmpty(response)) {
                            showToast("拨打失败");
                            return;
                        }
                        com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(response);
                        String success_ornot = jsonObject.getString("success");
                        if (success_ornot.equals("1")) {
                            final String timestamp = new Date().getTime() + "";
                            ZhuboInfo zhuboInfo = new ZhuboInfo(YOU, username, headpicture, timestamp, P2PVideoConst.GUKE_CALL_ZHUBO, P2PVideoConst.NONE_YUYUE);
                            GukeActivity.startCallZhubo(ChatActivity.this, zhuboInfo);
                        } else if (success_ornot.equals("2")) {
                            showPopupspWindow_reservation(getWindow().getDecorView(), 2);
                            showToast("主播忙碌，请稍后再试");

                        } else if (success_ornot.equals("3")) {
                            showPopupspWindow_reservation(getWindow().getDecorView(), 3);
                            showToast("主播设置勿打扰，请稍后再试");
                        } else if (success_ornot.equals("4")) {
                            showPopupspWindow_reservation(getWindow().getDecorView(), 4);
                            showToast("主播不在线");
                        } else if (success_ornot.equals("0")) {
                            showPopupspWindow_chongzhi();
                        } else if (success_ornot.equals("5")) {
                            showToast("主播被封禁");
                        } else if (success_ornot.equals("6")) {
                            showToast("您已被对方拉黑");
                        }

                    }
                });
    }

    /**
     * 初始化控件
     */
    private void initViews() {
        mListView = (DropdownListView) findViewById(R.id.message_chat_listview);

//		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//				Toast.makeText(ChatActivity.this,"item: "+i,Toast.LENGTH_SHORT).show();
//			}
//		});
        chat_main = (RelativeLayout) findViewById(R.id.chat_main);
        base_header = (RelativeLayout) findViewById(R.id.base_header);

        button_more_moremodify = (Button) findViewById(R.id.button_more_moremodify);
        button_more_moremodify.setOnClickListener(this);

/*		shuru = (TextView)findViewById(R.id.shuru);*/

        // 表情图标
        image_face = (ImageView) findViewById(R.id.image_face);
		/*// 更多图标
		image_add = (ImageView) findViewById(R.id.image_add);*/
        // 表情布局
        chat_face_container = (LinearLayout) findViewById(R.id.chat_face_container);
        // 更多
        //chat_add_container = (LinearLayout) findViewById(R.id.chat_add_container);
        dv = (ImageView) findViewById(R.id.dv);
        jb = (ImageView) findViewById(R.id.jb);

        if (com.net.yuesejiaoyou.classroot.interface4.util.Util.iszhubo.equals("0")) {
            dv.setVisibility(View.VISIBLE);
            jb.setVisibility(View.VISIBLE);
            jb.setOnClickListener(this);
        } else {
            dv.setVisibility(View.VISIBLE);
            jb.setVisibility(View.VISIBLE);
            jb.setOnClickListener(this);
        }

        mViewPager = (ViewPager) findViewById(R.id.face_viewpager);
        mViewPager.setOnPageChangeListener(new PageChange());
        // 表情下小圆点
        mDotsLayout = (LinearLayout) findViewById(R.id.face_dots_container);
        input = (EditText) findViewById(R.id.input_sms);

        input.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                chat_face_container.setVisibility(View.GONE);
            }
        });


        send = (TextView) findViewById(R.id.send_sms);
        send.setOnClickListener(this);

        //Typeface typeFace =Typeface.createFromAsset(getAssets(),"fonts/arialbd.ttf");

        input.setOnClickListener(this);

        input.addTextChangedListener(watcher);

        // 表情按钮
        image_face.setOnClickListener(this);


        mListView.setOnRefreshListenerHead(this);
        mListView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
                    if (chat_face_container.getVisibility() == View.VISIBLE) {
                        chat_face_container.setVisibility(View.GONE);
                    }
					/*if (chat_add_container.getVisibility() == View.VISIBLE) {
						chat_add_container.setVisibility(View.GONE);
					}*/
                    hideSoftInputView();
                }
                return false;
            }
        });
    }

    public void initAdd() {
        tv_voc = (TextView) findViewById(R.id.tv_voc);
        tv_voc.setOnClickListener(this);
        if (com.net.yuesejiaoyou.classroot.interface4.util.Util.iszhubo.equals("0")) {
            tv_voc.setVisibility(View.VISIBLE);
        } else {
            tv_voc.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.btn_gift)
    public void giftClick() {
        new GiftDialog(this, YOU).setLishener(new GiftDialog.OnGiftLishener() {
            @Override
            public void onSuccess(int gid, int num) {
                sendMsgText("[" + "☆" + com.net.yuesejiaoyou.classroot.interface4.util.Util.nickname + "给" + username + "赠送了" + num + "个" + Tools.getGiftName(gid) + "☆" + "]");
            }
        }).show();
    }

    public void initData() {
        offset = 0;
        listMsg = msgDao.queryMsg(YOU, I, offset);
        offset = listMsg.size();
        mLvAdapter = new ChatAdapter(this, listMsg, logo, ChatActivity.this, mListView, mHandler/*,Timing*/);
        mListView.setAdapter(mLvAdapter);
        mListView.setSelection(listMsg.size());

		/*mLvAdapter.setmItemOnClickListener(new ChatAdapter.ItemOnClickListener() {  
		  
         *//**
         *  点击的view子控件
         * @param view view子控件
         *//*
         @Override  
         public void itemOnClickListener(View view) {  
             yuyin = (TextView)view;  
             yuyin1 = (TextView)view;
         }  
     });  */

    }


    private void initViewPager() {
        int pagesize = ExpressionUtil.getPagerCount(staticFacesList.size(),
                columns, rows);

        // 获取页数
        for (int i = 0; i < pagesize; i++) {
            views.add(ExpressionUtil.viewPagerItem(this, i, staticFacesList,
                    columns, rows, input));
            LayoutParams params = new LayoutParams(16, 16);
            mDotsLayout.addView(dotsItem(i), params);
        }
        FaceVPAdapter mVpAdapter = new FaceVPAdapter(views);
        mViewPager.setAdapter(mVpAdapter);
        mDotsLayout.getChildAt(0).setSelected(true);
    }


    private ImageView dotsItem(int position) {
        View layout = inflater.inflate(R.layout.dot_image_01160, null);
        ImageView iv = (ImageView) layout.findViewById(R.id.face_dot);
        iv.setId(position);
        return iv;
    }

    /**
     */
    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.send_sms:
                String content = input.getText().toString();
                if (TextUtils.isEmpty(content)) {
                    return;
                }
                if (YOU.equals("40")) {
                    sendMsgText(content);
                } else if (!com.net.yuesejiaoyou.classroot.interface4.util.Util.iszhubo.equals("0")) {
                    sendMsgText(content);
                } else {
                    //看余额是否够看5分钟的钱
                    String mode = "see_five_money";
                    String[] paramsMap = {com.net.yuesejiaoyou.classroot.interface4.util.Util.userid, YOU};
                    UsersThread_01160B a = new UsersThread_01160B(mode, paramsMap, mHandler);
                    Thread t = new Thread(a.runnable);
                    t.start();
                    //	LogDetect.send(DataType.specialType,"01160 开线程看余额是否够看5分钟的钱:","1");
                }
                //sendMsgText(content);
                break;
            case R.id.input_sms:
                if (chat_face_container.getVisibility() == View.VISIBLE) {
                    chat_face_container.setVisibility(View.GONE);
                }
                break;
            case R.id.image_face:
                hideSoftInputView();
                input.setVisibility(View.VISIBLE);
                //tv_voc1.setVisibility(View.GONE);
                //shuohua.setVisibility(View.GONE);
			/*if (chat_add_container.getVisibility() == View.VISIBLE) {
				chat_add_container.setVisibility(View.GONE);
			}*/
                if (chat_face_container.getVisibility() == View.GONE) {
                    chat_face_container.setVisibility(View.VISIBLE);
                } else {
                    chat_face_container.setVisibility(View.GONE);
                }
                break;

            case R.id.tv_voc:
                //点击通话,主播不能点

                // 主播不能发起一对一视频
                if (!"0".equals(com.net.yuesejiaoyou.classroot.interface4.util.Util.iszhubo)) {
//				Toast.makeText(this, "主播不能主动发起视频，请从预约列表发起视频", Toast.LENGTH_SHORT).show();
                    ZhuboActivity.startCallGuke(this, new GukeInfo(YOU, name, logo, System.currentTimeMillis() + "",
                            P2PVideoConst.ZHUBO_CALL_GUKE, P2PVideoConst.HAVE_NO_YUYUE));
                    break;
                }

                String mode1 = "zhubo_online";
                String[] paramsMap1 = {"", I, YOU};
                UsersThread_01158B a = new UsersThread_01158B(mode1, paramsMap1, mHandler);
                Thread c = new Thread(a.runnable);
                c.start();

                break;
				
		/*case R.id.tv_voc1:
			hideSoftInputView();// 隐藏软键盘
			tv_voc.setVisibility(View.VISIBLE);
			input.setVisibility(View.VISIBLE);
			tv_voc1.setVisibility(View.GONE);
			//shuohua.setVisibility(View.GONE);
			break;*/
            case R.id.button_more_moremodify:
                if (logo.equals("000000")) {
                    Util.currentfrom = "";
                    AudioRecoderUtils.stopMusic();
                    finish();
                } else {
                    hideSoftInputView();
                    if (chat_face_container.getVisibility() == View.VISIBLE) {
                        chat_face_container.setVisibility(View.GONE);
                    } /*else if (chat_add_container.getVisibility() == View.VISIBLE) {
					chat_add_container.setVisibility(View.GONE);
				}*/ else {
                        Util.currentfrom = "";
                        AudioRecoderUtils.stopMusic();
                        finish();
                    }
                }
                break;
            case R.id.jb:
                showPopupspWindow_jb(base_header);
                break;
            case R.id.c1:
                String mode = "report";
                String[] paramsMap = {com.net.yuesejiaoyou.classroot.interface4.util.Util.userid, YOU, c1.getText().toString()};
                UsersThread_01160B az = new UsersThread_01160B(mode, paramsMap, mHandler);
                Thread t = new Thread(az.runnable);
                t.start();
                break;
            case R.id.c2:
                String mode112 = "report";
                String[] paramsMap112 = {com.net.yuesejiaoyou.classroot.interface4.util.Util.userid, YOU, c2.getText().toString()};
                UsersThread_01160B a1 = new UsersThread_01160B(mode112, paramsMap112, mHandler);
                Thread t1 = new Thread(a1.runnable);
                t1.start();
                break;
            case R.id.c3:
                String mode2 = "report";
                String[] paramsMap2 = {com.net.yuesejiaoyou.classroot.interface4.util.Util.userid, YOU, c3.getText().toString()};
                UsersThread_01160B a2 = new UsersThread_01160B(mode2, paramsMap2, mHandler);
                Thread t2 = new Thread(a2.runnable);
                t2.start();
                break;
            case R.id.c4:
                String mode3 = "report";
                String[] paramsMap3 = {com.net.yuesejiaoyou.classroot.interface4.util.Util.userid, YOU, c4.getText().toString()};
                UsersThread_01160B a3 = new UsersThread_01160B(mode3, paramsMap3, mHandler);
                Thread t3 = new Thread(a3.runnable);
                t3.start();
                break;
            case R.id.c5:
                String mode4 = "report";
                String[] paramsMap4 = {com.net.yuesejiaoyou.classroot.interface4.util.Util.userid, YOU, c5.getText().toString()};
                UsersThread_01160B a4 = new UsersThread_01160B(mode4, paramsMap4, mHandler);
                Thread t4 = new Thread(a4.runnable);
                t4.start();
                break;
            case R.id.c6:
                String mode5 = "report";
                String[] paramsMap5 = {com.net.yuesejiaoyou.classroot.interface4.util.Util.userid, YOU, c6.getText().toString()};
                UsersThread_01160B a5 = new UsersThread_01160B(mode5, paramsMap5, mHandler);
                Thread t5 = new Thread(a5.runnable);
                t5.start();
                break;
        }
    }

    /**
     * 执行发送消息 文本类型
     *
     * @param content
     */
    void sendMsgText(String content) {
        Msg msg = getChatInfoTo(content, Const.MSG_TYPE_TEXT);
        msg.setMsgId(msgDao.insert(msg));
        listMsg.add(msg);
        offset = listMsg.size();
        mLvAdapter.notifyDataSetChanged();
        input.setText("");
        final String message = content + Const.SPLIT + Const.MSG_TYPE_TEXT
                + Const.SPLIT + sd.format(new Date()) + Const.SPLIT + username + Const.SPLIT + headpicture;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sendMessage(Utils.xmppConnection, message, YOU);
                } catch (Exception e) {
                    e.printStackTrace();
                    Looper.prepare();
                    Looper.loop();
                }
            }
        }).start();
        updateSession1(Const.MSG_TYPE_TEXT, content, name, logo);
    }


    /**
     * 发送的信息 from为收到的消息，to为自己发送的消息
     *
     * @param message => 接收者卍发送者卍消息类型卍消息内容卍发送时间
     * @return
     */
    private Msg getChatInfoTo(String message, String msgtype) {
        String time = sd.format(new Date());
        Msg msg = new Msg();
        msg.setFromUser(YOU);
        msg.setToUser(I);
        msg.setType(msgtype);
        msg.setIsComing(1);
        msg.setContent(message);
        msg.setDate(time);
        return msg;
    }

    void updateSession1(String type, String content, String name, String logo) {
        Session session = new Session();
        session.setFrom(YOU);
        session.setTo(I);
        session.setNotReadCount("");// 未读消息数量
        session.setContent(content);
        session.setTime(Tools.currentTime());
        session.setType(type);
        session.setName(name);
        session.setHeadpic(logo);

        if (sessionDao.isContent(YOU, I)) {
            sessionDao.updateSession(session);
        } else {
            sessionDao.insertSession(session);
        }
        Intent intent = new Intent(Const.ACTION_ADDFRIEND);// 发送广播，通知消息界面更新
        sendBroadcast(intent);
    }


    /**
     * 表情页改变时，dots效果也要跟着改变
     */
    class PageChange implements OnPageChangeListener {
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

    /**
     * 下拉加载更多
     */
    @Override
    public void onRefresh() {
        List<Msg> list = msgDao.queryMsg(YOU, I, offset);
        if (list.size() <= 0) {
            mListView.setSelection(0);
            mListView.onRefreshCompleteHeader();
            return;
        }
        listMsg.addAll(0, list);
        offset = listMsg.size();
        mListView.onRefreshCompleteHeader();
        mLvAdapter.notifyDataSetChanged();
        mListView.setSelection(list.size());
    }

    /**
     * 弹出输入法窗口
     */
    @SuppressWarnings("unused")
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

    /**
     * 接收消息记录操作广播：删除复制
     *
     * @author baiyuliang
     */
    private class MsgOperReciver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            initData();

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(msgOperReciver);
        unregisterReceiver(receiver);
    }

    @Override
    protected void onResume() {
        super.onResume();

//		IMManager.getInstance().checkConnectStatusAndReconnect();
        IMManager.clientHeartbeat();
        input.setFocusable(false);
        input.setFocusableInTouchMode(true);
        chat_face_container.setVisibility(View.GONE);
    }

    ;

    private class MyReceiver_Home extends BroadcastReceiver {

        private final String SYSTEM_DIALOG_REASON_KEY = "reason";
        private final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";
        //	private final String SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps";

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
                String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);

                if (reason == null)
                    return;

                // Home键
                if (reason.equals(SYSTEM_DIALOG_REASON_HOME_KEY)) {
                    chat_face_container.setVisibility(View.GONE);
                }

			/*	// 最近任务列表键
				if (reason.equals(SYSTEM_DIALOG_REASON_RECENT_APPS)) {
					Toast.makeText(getApplicationContext(), "按了最近任务列表", Toast.LENGTH_SHORT).show();
				}*/
            }
        }
    }


    /**
     * 监听返回键
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Log.v("PAOPAO", "back key pressed");
            if (logo.equals("000000")) {
                Util.currentfrom = "";
                AudioRecoderUtils.stopMusic();
                finish();
            } else {
                hideSoftInputView();
                if (chat_face_container.getVisibility() == View.VISIBLE) {
                    chat_face_container.setVisibility(View.GONE);
                } /*else if (chat_add_container.getVisibility() == View.VISIBLE) {
					chat_add_container.setVisibility(View.GONE);
				} */ else {
                    AudioRecoderUtils.stopMusic();
                    Util.currentfrom = "";
                    //recorder.stopRecord();
                    finish();
                }
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 发送消息
     *
     * @param content
     * @param touser
     * @throws XMPPException
     * @throws NotConnectedException
     */
    public void sendMessage(XMPPTCPConnection mXMPPConnection, String content,
                            String touser) throws XMPPException, NotConnectedException {
        if (mXMPPConnection == null || !mXMPPConnection.isConnected()) {
            // throw new XMPPException();
        }

        ChatManager chatmanager = Utils.xmppchatmanager;

        ////////////////////------------------>>>>>>>>
        Chat chat = chatmanager.createChat(YOU + "@" + Const.XMPP_HOST, null);
        ////////////////////------------------>>>>>>>>
        LogDetect.send(DataType.specialType, "xmpp表面:", "4");
        ////////////////////------------------>>>>>>>>
        if (chat != null) {
            // chat.sendMessage(content);
            ////////////////////------------------>>>>>>>>
            LogDetect.send(DataType.specialType, "xmpp表面:", "5");
            ////////////////////------------------>>>>>>>>
            chat.sendMessage(content, I + "@" + Const.XMPP_HOST);
            Log.e("jj", "发送成功");
            ////////////////////------------------>>>>>>>>
            LogDetect.send(DataType.specialType, "xmpp表面:", "3");
            ////////////////////------------------>>>>>>>>
            //LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"send success");
        } else {
            //LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"send fail:chat is null");
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    }


    public void showPopupspWindow(View parent) {
        // 加载布局
        LayoutInflater inflater = LayoutInflater.from(ChatActivity.this);
        View layout = inflater.inflate(R.layout.pop_5_recharge_01160, null);

        TextView queding, quxiao;

        queding = (TextView) layout.findViewById(R.id.queding);
        queding.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                //点击确定，跳转到充币页面
                Intent intent = new Intent();
                intent.setClass(ChatActivity.this, RechargeActivity.class);
                startActivity(intent);

                LogDetect.send(DataType.specialType, "01160 跳转到充币页面:", intent);
            }
        });

        quxiao = (TextView) layout.findViewById(R.id.quxiao);
        quxiao.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                popupWindow.dismiss();
            }
        });


        popupWindow = new PopupWindow(layout, LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT, true);
        // 控制键盘是否可以获得焦点
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable(null, ""));
        //popupWindow.showAsDropDown(parent, 0, 0);
        popupWindow.showAtLocation(parent, Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            public void onDismiss() {
            }
        });
    }

    @SuppressLint({"RtlHardcoded", "NewApi"})
    public void showPopupspWindow_jb(View parent) {
        Log.v("TT", "showPopupspWindow_jb");
        // 加载布局
        LayoutInflater inflater = LayoutInflater.from(ChatActivity.this);
        View layout = inflater.inflate(R.layout.pop_jb_01160, null);
        //拉黑      //举报
        TextView quxiao, eat;

        eat = (TextView) layout.findViewById(R.id.eat);
        eat.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                popupWindow.dismiss();
                showPopupspWindow_rp(chat_main);
            }
        });

        quxiao = (TextView) layout.findViewById(R.id.quxiao);
        quxiao.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                String mode = "black";
                String[] paramsMap = {com.net.yuesejiaoyou.classroot.interface4.util.Util.userid, YOU};
                UsersThread_01160B a = new UsersThread_01160B(mode, paramsMap, mHandler);
                Thread t = new Thread(a.runnable);
                t.start();
                LogDetect.send(DataType.specialType, "01160 开线程拉黑:", "4");
            }
        });


        popupWindow = new PopupWindow(layout, 260,
                LayoutParams.WRAP_CONTENT, true);
        // 控制键盘是否可以获得焦点
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable(null, ""));
        popupWindow.showAsDropDown(parent, 0, 0, Gravity.RIGHT | Gravity.TOP);
        //popupWindow.showAtLocation(parent, Gravity.RIGHT|Gravity.TOP, 0, 45);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            public void onDismiss() {
            }
        });
    }

    //举报
    @SuppressLint({"RtlHardcoded", "NewApi"})
    public void showPopupspWindow_rp(View parent) {
        // 加载布局
        LayoutInflater inflater = LayoutInflater.from(ChatActivity.this);
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
        quxiao.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                popupWindow.dismiss();
            }
        });


        popupWindow = new PopupWindow(layout, LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT, true);
        // 控制键盘是否可以获得焦点
        popupWindow.setFocusable(true);
        WindowManager.LayoutParams lp = ChatActivity.this.getWindow().getAttributes();
        lp.alpha = 0.5f;
        ChatActivity.this.getWindow().setAttributes(lp);
        popupWindow.setBackgroundDrawable(new BitmapDrawable(null, ""));
        //popupWindow.showAsDropDown(parent, 0, 0,Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL);
        popupWindow.showAtLocation(parent, Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            public void onDismiss() {
                WindowManager.LayoutParams lp = ChatActivity.this.getWindow().getAttributes();
                lp.alpha = 1f;
                ChatActivity.this.getWindow().setAttributes(lp);
            }
        });
    }


    //预约
    @SuppressLint({"RtlHardcoded", "NewApi"})
    public void showPopupspWindow_reservation(View parent, int online) {
        // 加载布局
        LayoutInflater inflater = LayoutInflater.from(ChatActivity.this);
        View layout = inflater.inflate(R.layout.pop_reservation_01160, null);
        //取消		 确定				标题
        TextView exit_tuichu, exit_login, user_exit;

        user_exit = (TextView) layout.findViewById(R.id.user_exit);
        if (online == 2) {
            user_exit.setText("主播正忙，是否预约");
        } else if (online == 3) {
            user_exit.setText("主播设置勿打扰，是否预约");
        } else if (online == 4) {
            user_exit.setText("主播离线，是否预约");
        }

        exit_tuichu = (TextView) layout.findViewById(R.id.exit_tuichu);
        exit_tuichu.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                popupWindow.dismiss();
            }
        });

        exit_login = (TextView) layout.findViewById(R.id.exit_login);
        exit_login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String befortime = sdf.format(now.getTimeInMillis());

                now.add(Calendar.HOUR, 12);
                String dateStr = sdf.format(now.getTimeInMillis());


//                String[] paramsMap = {com.net.yuesejiaoyou.classroot.interface4.util.Util.userid, YOU, befortime, dateStr};
//                new Thread(new UsersThread_01160A("insert_reservation", paramsMap, mHandler).runnable).start();

                OkHttpUtils.post(this)
                        .url(URL.URL_INSERT)
                        .addParams("param1", com.net.yuesejiaoyou.classroot.interface4.util.Util.userid)
                        .addParams("param2", YOU)
                        .addParams("param3", befortime)
                        .addParams("param4", dateStr)
                        .build()
                        .execute(new DialogCallback(ChatActivity.this) {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                showToast("网络异常");
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                if (TextUtils.isEmpty(response)) {
                                    showToast("预约失败，请检查网络连接");
                                } else {
                                    try {
                                        JSONObject jsonObject1 = new JSONObject(response);
                                        //预约
                                        String success_ornot = jsonObject1.getString("success");
                                        if (success_ornot.equals("-2")) {
                                            showToast("余额不足，无法预约");
                                        } else if (success_ornot.equals("-1")) {
                                            showToast("已预约成功，无法再次预约");
                                        } else {
                                            com.net.yuesejiaoyou.classroot.interface4.util.Util.sendMsgText("『" + com.net.yuesejiaoyou.classroot.interface4.util.Util.nickname + "』 Appointment is successful", YOU);
                                            showToast("预约成功,消费");
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });

                popupWindow.dismiss();
            }
        });

        popupWindow = new PopupWindow(layout, LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT, true);
        // 控制键盘是否可以获得焦点
        popupWindow.setFocusable(true);
        WindowManager.LayoutParams lp = ChatActivity.this.getWindow().getAttributes();
        lp.alpha = 0.5f;
        ChatActivity.this.getWindow().setAttributes(lp);
        popupWindow.setBackgroundDrawable(new BitmapDrawable(null, ""));
        //popupWindow.showAsDropDown(parent, 0, 0,Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL);
        popupWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            public void onDismiss() {
                WindowManager.LayoutParams lp = ChatActivity.this.getWindow().getAttributes();
                lp.alpha = 1f;
                ChatActivity.this.getWindow().setAttributes(lp);
            }
        });
    }


    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            if (s.length() != 0) {
                send.setEnabled(true);
            } else {
                send.setEnabled(false);
            }

        }
    };


    public void showPopupspWindow_chongzhi() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.is_chongzhi_01165, null);

        TextView cancel = (TextView) layout.findViewById(R.id.cancel);
        cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                popupWindow.dismiss();
            }
        });


        TextView confirm = (TextView) layout.findViewById(R.id.confirm);//获取小窗口上的TextView，以便显示现在操作的功能。
        confirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent1 = new Intent();
                intent1.setClass(ChatActivity.this, RechargeActivity.class);//充值页面
                startActivity(intent1);
                finish();
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
        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER | Gravity.CENTER, 252, 0);
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
}
