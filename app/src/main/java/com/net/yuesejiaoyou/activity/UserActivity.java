package com.net.yuesejiaoyou.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
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
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack.SmackException;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.redirect.ResolverA.getset.User_data;
import com.net.yuesejiaoyou.redirect.ResolverA.interface4.VMyAdapterqm_01066;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.URL;
import com.net.yuesejiaoyou.fragment.UserInfoFragment;
import com.net.yuesejiaoyou.fragment.VideoFragment;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.agora.P2PVideoConst;
import com.net.yuesejiaoyou.bean.ZhuboInfo;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.BaseActivity;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.ShareHelp;
import com.net.yuesejiaoyou.utils.ImageUtils;
import com.net.yuesejiaoyou.utils.LogUtil;
import com.net.yuesejiaoyou.utils.Tools;
import com.net.yuesejiaoyou.widget.GiftDialog;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.DialogCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.OnClick;
import okhttp3.Call;

public class UserActivity extends BaseActivity {
    public String yid, msgbody;
    private ArrayList mListImage;
    private int isguanzhu = 0, fannum = 0;
    private String nicheng, headpic, userid = "";

    private GridLayoutManager mLayoutManager;
    private SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private ImageView photo, fanhui, erweima, iv_zt_img, ib_focus;
    private TextView nickname, wodeqianbao, vhome_tv_status, text_view_price, tv_dv, flow_img, tv_focus_count;
    private RatingBar ratingbar;
    private PopupWindow mPopWindow;
    private PopupWindow popupWindow;
    private Context mContext;
    MsgOperReciver1 msgOperReciver1;
    reciever reviever;
    /**
     * 通用的ToolBar
     */
    private Toolbar commonTitleTb;
    private RelativeLayout relative_layout_intimacy;
    private View.OnClickListener relative_layout_intimacy_listener;

    reciever1 reviever1;

    private User_data userInfo;


    private ChatMsgDao msgDao;
    private List<Msg> listMsg = new ArrayList<Msg>();
    private SessionDao sessionDao;
    private String I, price;
    private TextView tvCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        I = Util.userid;
        msgDao = new ChatMsgDao(this);
        sessionDao = new SessionDao(this);
        mContext = UserActivity.this;

        wodeqianbao = (TextView) findViewById(R.id.wodeqianbao);//昵称
        fanhui = (ImageView) findViewById(R.id.fanhui);//返回
        fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        erweima = (ImageView) findViewById(R.id.erweima);//二维码


        iv_zt_img = (ImageView) findViewById(R.id.iv_zt_img);//状态图片
        vhome_tv_status = (TextView) findViewById(R.id.vhome_tv_status);//状态文字
        text_view_price = (TextView) findViewById(R.id.text_view_price);//价格
        tv_dv = (TextView) findViewById(R.id.tv_dv);//昵称
        flow_img = (TextView) findViewById(R.id.flow_img);//介绍
        ib_focus = (ImageView) findViewById(R.id.ib_focus);//关注图片


        tv_focus_count = (TextView) findViewById(R.id.tv_focus_count);//粉丝数
        ratingbar = (RatingBar) findViewById(R.id.ratingbar);//星星
        relative_layout_intimacy = (RelativeLayout) findViewById(R.id.relative_layout_intimacy);
        relative_layout_intimacy_listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /////////////////////////////
                LogDetect.send(LogDetect.DataType.specialType, "UserActivity:A区 转2调 C区", "他的亲密榜");
                //////////////////////////////
                Intent intent = new Intent();
                intent.setClass(mContext, IntimateActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", userid);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        };
        relative_layout_intimacy.setOnClickListener(relative_layout_intimacy_listener);


        tvCall = findViewById(R.id.tv_call);


        commonTitleTb = (Toolbar) findViewById(R.id.toolbar);
        AppBarLayout mAppBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int scrollRangle = appBarLayout.getTotalScrollRange();
                //初始verticalOffset为0，不能参与计算。
                Log.d("1111", "scrollRangle: " + scrollRangle);
                if (verticalOffset == 0) {
                    wodeqianbao.setAlpha(0.0f);
                } else {
                    //保留一位小数
                    Log.d("1111", "alpha: " + verticalOffset);
                    if (verticalOffset < -700) {
                        Resources resources = getBaseContext().getResources();
                        fanhui.setImageResource(R.drawable.arrow_left_gray);
                        erweima.setImageResource(R.drawable.big_v_home_business_card_gray);
                        Drawable imageDrawable1 = resources.getDrawable(R.drawable.big_v_home_mask_yb); //图片在drawable目录下
                        commonTitleTb.setBackground(imageDrawable1);
                    } else {
                        Resources resources = getBaseContext().getResources();
                        fanhui.setImageResource(R.drawable.arrow_left_white);
                        erweima.setImageResource(R.drawable.big_v_home_business_card);
                        Drawable imageDrawable1 = resources.getDrawable(R.drawable.big_v_home_mask_s); //图片在drawable目录下
                        commonTitleTb.setBackground(imageDrawable1);
                    }
                    DecimalFormat df = new DecimalFormat("0.00");
                    //Log.d("1111", "Math: " + Math.abs(Float.parseFloat(df.format((float)verticalOffset/scrollRangle))));
                    float alpha = Math.abs(Float.parseFloat(df.format((float) verticalOffset / scrollRangle)));
                    //Log.d("1111", "alpha: " + alpha);
                    wodeqianbao.setAlpha(alpha);
                }
            }
        });
        reviever = new reciever();
        IntentFilter filter1 = new IntentFilter("5");
        registerReceiver(reviever, filter1);

        reviever1 = new reciever1();
        IntentFilter filter2 = new IntentFilter("99");
        registerReceiver(reviever1, filter2);

        Intent intent9 = getIntent();
        Bundle bundle = intent9.getExtras();
        userid = bundle.getString("id");


        msgOperReciver1 = new MsgOperReciver1();
        IntentFilter intentFilter = new IntentFilter("userguanzhu");
        registerReceiver(msgOperReciver1, intentFilter);

        getUserData();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_user;
    }

    @Override
    public int statusBarColor() {
        return R.color.transparent;
    }

    @Override
    public boolean statusBarFont() {
        return false;
    }

    public void getUserData() {
        OkHttpUtils.post(this)
                .url(URL.URL_USERINFO)
                .addParams("param1", "13")
                .addParams("param2", userid)
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
                            return;
                        }

                        User_data userinfo = JSON.parseArray(response, User_data.class).get(0);
                        userInfo = userinfo;

                        wodeqianbao = (TextView) findViewById(R.id.wodeqianbao);//昵称
                        nicheng = userinfo.getNickname();
                        headpic = userinfo.getPhoto();
                        userid = String.valueOf(userinfo.getId());
                        wodeqianbao.setText(userinfo.getNickname());
                        tv_dv.setText(userinfo.getNickname());
                        fanhui = (ImageView) findViewById(R.id.fanhui);//返回
                        erweima = (ImageView) findViewById(R.id.erweima);//二维码
                        //得到AssetManager
                        AssetManager mgr = UserActivity.this.getAssets();

                        //根据路径得到Typeface
                        Typeface tf = Typeface.createFromAsset(mgr, "fonts/arialbd.ttf");

                        vhome_tv_status.setTypeface(tf);
                        if (userinfo.getOnline() == 1) {
                            iv_zt_img.setBackgroundResource(R.drawable.zt_zaixian);
                            vhome_tv_status.setText("在线");

                        } else if (userinfo.getOnline() == 2) {
                            iv_zt_img.setBackgroundResource(R.drawable.zt_zailiao);
                            vhome_tv_status.setText("在聊");
                        } else if (userinfo.getOnline() == 3) {
                            iv_zt_img.setBackgroundResource(R.drawable.zt_wurao);
                            vhome_tv_status.setText("勿扰");
                        } else {
                            iv_zt_img.setBackgroundResource(R.drawable.zt_lixian);
                            vhome_tv_status.setText("离线");
                        }

                        ratingbar.setRating((float) userinfo.getStar());

                        if (userinfo.getIslike() == 0) {
                            ib_focus.setImageResource(R.drawable.guanzhu);
                        } else {
                            ib_focus.setImageResource(R.drawable.guanzhu_on);
                        }
                        isguanzhu = userinfo.getIslike();
                        flow_img.setText(userinfo.getLab_tab());
                        text_view_price.setText(userinfo.getPrice() + " 悦币/分钟");
                        tv_focus_count.setText(userinfo.getFans_num() + " 粉丝");
                        fannum = Integer.parseInt(userinfo.getFans_num());
                        //实例化Banner
                        Banner banner = findViewById(R.id.header);
                        //设置Banner样式
                        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
                        //设置图片加载器

                        mListImage = new ArrayList<>();
                        LogUtil.i("ttt", "---" + userinfo.getPictures());
                        if (!TextUtils.isEmpty(userinfo.getPictures())) {
                            ArrayList mListTitle = new ArrayList<>();
                            if (userinfo.getPictures().contains(",")) {
                                String[] a = userinfo.getPictures().split(",");
                                for (int i = 0; i < a.length; i++) {
                                    mListImage.add(a[i]);
                                    mListTitle.add("");
                                }
                            } else {
                                mListImage.add(userinfo.getPictures());
                                mListTitle.add("");
                            }

                            banner.setImages(mListImage);
                            banner.setImageLoader(new GlideImageLaoder());
                            banner.setBannerAnimation(Transformer.Tablet);
                            //设置Banner标题集合（当banner样式有显示title时）
                            banner.setBannerTitles(mListTitle);
                            banner.setDelayTime(3000);
                            banner.setBackgroundColor(UserActivity.this.getResources().getColor(R.color.white));
                            banner.setIndicatorGravity(BannerConfig.CIRCLE_INDICATOR);
                            banner.start();
                        }
                        //实例化图片集合

                        //实例化Title集合


                        ViewPager viewpager = (ViewPager) findViewById(R.id.viewpager);
                        //构造适配器
                        List<Fragment> fragments = new ArrayList<Fragment>();
                        fragments.add(new UserInfoFragment(userinfo));
                        fragments.add(new VideoFragment(userinfo));
                        FragAdapter adapter = new FragAdapter(getSupportFragmentManager(), fragments);
                        viewpager.setAdapter(adapter);

                        TabLayout mTabLayout = (TabLayout) findViewById(R.id.tabs);
                        mTabLayout.setupWithViewPager(viewpager);

                        LinearLayout linearLayout = (LinearLayout) mTabLayout.getChildAt(0);
                        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);

                        linearLayout.setDividerDrawable(ContextCompat.getDrawable(UserActivity.this,
                                R.drawable.shuxian));
                        linearLayout.setDividerPadding(30);
                        setIndicator(UserActivity.this, mTabLayout, 40, 40);
                        RecyclerView gridView = (RecyclerView) findViewById(R.id.recycler_intimacy);


                        if (userinfo.getLikep().equals("")) {

                        } else {
                            String[] pic = userinfo.getLikep().split(",");
                            VMyAdapterqm_01066 adapter1 = new VMyAdapterqm_01066(null, UserActivity.this, true, pic, userid, relative_layout_intimacy_listener);

                            mLayoutManager = new GridLayoutManager(UserActivity.this, pic.length);
                            gridView.setLayoutManager(mLayoutManager);
                            gridView.setAdapter(adapter1);
                        }
                    }
                });
    }

    @OnClick(R.id.ib_focus)
    public void focusClick() {
        OkHttpUtils.post(this)
                .url(URL.URL_GUANZHU)
                .addParams("param1", "13")
                .addParams("param2", userid)
                .addParams("param3", isguanzhu)
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
                            return;
                        }
                        com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(response);

                        isguanzhu = jsonObject.getIntValue("success");

                        if (isguanzhu == 0) {
                            ib_focus.setImageResource(R.drawable.guanzhu);
                            fannum = fannum - 1;
                            tv_focus_count.setText(fannum + " 粉丝");
                        } else {
                            ib_focus.setImageResource(R.drawable.guanzhu_on);
                            fannum = fannum + 1;
                            tv_focus_count.setText(fannum + " 粉丝");
                        }

                        Intent intent = new Intent("userinfoguanzhu");
                        Bundle bundle = new Bundle();
                        bundle.putString("guanzhu", userid);
                        bundle.putInt("isguanzhu", isguanzhu);
                        intent.putExtras(bundle);
                        UserActivity.this.sendBroadcast(intent);
                    }
                });
    }

    @OnClick(R.id.erweima)
    public void qrcodeClick() {
        ShareHelp shareHelp1 = new ShareHelp();
        shareHelp1.showShare(UserActivity.this, Util.invite_num);
    }

    @OnClick(R.id.ll_vhome_imchat)
    public void chatClick() {
        Intent intentthis = new Intent(UserActivity.this, ChatActivity.class);
        intentthis.putExtra("id", userid);
        intentthis.putExtra("name", nicheng);
        intentthis.putExtra("headpic", headpic);
        startActivity(intentthis);
    }

    private class MsgOperReciver1 extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String msgBody = intent.getStringExtra("guanzhu");
            ///////////////////////////////
            LogDetect.send(LogDetect.DataType.specialType, "downorup:", msgBody);
            if (msgBody.equals(userid)) {
                handler.sendMessage(handler.obtainMessage(1000, (Object) msgBody));
            }
        }
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                ///////////////////////////
                case 1000:
                    isguanzhu = 1;
                    ib_focus.setImageResource(R.drawable.guanzhu_on);
                    fannum = fannum + 1;
                    tv_focus_count.setText(fannum + " 粉丝");
                    break;


                case 201:
                    User_data userinfo = (User_data) msg.obj;
                    userInfo = userinfo;
                    /////////////////////////////
                    LogDetect.send(LogDetect.DataType.specialType, "UserActivity:", "121212212" + userinfo.getAddress());
                    //////////////////////////////
                    wodeqianbao = (TextView) findViewById(R.id.wodeqianbao);//昵称
                    nicheng = userinfo.getNickname();
                    headpic = userinfo.getPhoto();
                    userid = String.valueOf(userinfo.getId());
                    wodeqianbao.setText(userinfo.getNickname());
                    tv_dv.setText(userinfo.getNickname());
                    fanhui = (ImageView) findViewById(R.id.fanhui);//返回
                    erweima = (ImageView) findViewById(R.id.erweima);//二维码
                    //得到AssetManager
                    AssetManager mgr = UserActivity.this.getAssets();

                    //根据路径得到Typeface
                    Typeface tf = Typeface.createFromAsset(mgr, "fonts/arialbd.ttf");


                    vhome_tv_status.setTypeface(tf);
                    if (userinfo.getOnline() == 1) {
                        iv_zt_img.setBackgroundResource(R.drawable.zt_zaixian);
                        vhome_tv_status.setText("在线");

                    } else if (userinfo.getOnline() == 2) {
                        iv_zt_img.setBackgroundResource(R.drawable.zt_zailiao);
                        vhome_tv_status.setText("在聊");
                    } else if (userinfo.getOnline() == 3) {
                        iv_zt_img.setBackgroundResource(R.drawable.zt_wurao);
                        vhome_tv_status.setText("勿扰");
                    } else {
                        iv_zt_img.setBackgroundResource(R.drawable.zt_lixian);
                        vhome_tv_status.setText("离线");
                    }

                    ratingbar.setRating((float) userinfo.getStar());

                    if (userinfo.getIslike() == 0) {
                        ib_focus.setImageResource(R.drawable.guanzhu);
                    } else {
                        ib_focus.setImageResource(R.drawable.guanzhu_on);
                    }
                    isguanzhu = userinfo.getIslike();
                    flow_img.setText(userinfo.getLab_tab());
                    text_view_price.setText(userinfo.getPrice() + " 悦币/分钟");
                    tv_focus_count.setText(userinfo.getFans_num() + " 粉丝");
                    fannum = Integer.parseInt(userinfo.getFans_num());
                    //实例化Banner
                    Banner banner = findViewById(R.id.header);
                    //设置Banner样式
                    banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
                    //设置图片加载器

                    //实例化图片集合
                    mListImage = new ArrayList<>();
                    //实例化Title集合
                    ArrayList mListTitle = new ArrayList<>();
                    String[] a = userinfo.getPictures().split(",");
                    for (int i = 0; i < a.length; i++) {
                        mListImage.add(a[i]);
                        mListTitle.add("");
                    }

                    banner.setImages(mListImage);
                    banner.setImageLoader(new GlideImageLaoder());
                    banner.setBannerAnimation(Transformer.Tablet);
                    //设置Banner标题集合（当banner样式有显示title时）
                    banner.setBannerTitles(mListTitle);
                    banner.setDelayTime(3000);
                    banner.setBackgroundColor(UserActivity.this.getResources().getColor(R.color.white));
                    banner.setIndicatorGravity(BannerConfig.CIRCLE_INDICATOR);
                    banner.start();


                    ViewPager viewpager = (ViewPager) findViewById(R.id.viewpager);
                    //构造适配器
                    List<Fragment> fragments = new ArrayList<Fragment>();
                    fragments.add(new UserInfoFragment(userinfo));
                    fragments.add(new VideoFragment(userinfo));
                    FragAdapter adapter = new FragAdapter(getSupportFragmentManager(), fragments);
                    viewpager.setAdapter(adapter);

                    TabLayout mTabLayout = (TabLayout) findViewById(R.id.tabs);
                    mTabLayout.setupWithViewPager(viewpager);

                    LinearLayout linearLayout = (LinearLayout) mTabLayout.getChildAt(0);
                    linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);

                    linearLayout.setDividerDrawable(ContextCompat.getDrawable(UserActivity.this,
                            R.drawable.shuxian));
                    linearLayout.setDividerPadding(30);
                    setIndicator(UserActivity.this, mTabLayout, 40, 40);
                    RecyclerView gridView = (RecyclerView) findViewById(R.id.recycler_intimacy);


                    if (userinfo.getLikep().equals("")) {

                    } else {
                        String[] pic = userinfo.getLikep().split(",");
                        VMyAdapterqm_01066 adapter1 = new VMyAdapterqm_01066(null, UserActivity.this, true, pic, userid, relative_layout_intimacy_listener);

                        mLayoutManager = new GridLayoutManager(UserActivity.this, pic.length);
                        gridView.setLayoutManager(mLayoutManager);
                        gridView.setAdapter(adapter1);
                    }

                    break;
                case 100:
                    String json1 = (String) msg.obj;
                    try { //如果服务端返回1，说明个人信息修改成功了
                        JSONObject jsonObject = new JSONObject(json1);
                        String str = jsonObject.getString("success");
                        ///////////////////////////////
                        LogDetect.send(LogDetect.DataType.specialType, "yue_____： ", str);
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    break;
                case 210:
                    String json_reservation = (String) (msg).obj;
                    ///////////////////////////////
                    LogDetect.send(LogDetect.DataType.specialType, "01160:", msg);
                    if (!json_reservation.isEmpty()) {
                        try {
                            JSONObject jsonObject1 = new JSONObject(json_reservation);
                            ///////////////////////////////
                            LogDetect.send(LogDetect.DataType.specialType, "01160:", jsonObject1);
                            //预约
                            String success_ornot = jsonObject1.getString("success");
                            ///////////////////////////////
                            LogDetect.send(LogDetect.DataType.specialType, "01160 success_ornot:", success_ornot);
                            if (success_ornot.equals("-2")) {
                                Toast.makeText(mContext, "余额不足，无法预约", Toast.LENGTH_SHORT).show();
                            } else if (success_ornot.equals("-1")) {
                                Toast.makeText(mContext, "已预约成功，无法再次预约", Toast.LENGTH_SHORT).show();
                            } else {

                                Util.sendMsgText("『" + Util.nickname + "』 Appointment is successful", userid);

                                Toast.makeText(mContext, "预约成功,消费" + success_ornot + "悦币", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(UserActivity.this, "预约失败，请检查网络连接", Toast.LENGTH_SHORT).show();
                    }
                    break;

            }
        }
    };


    /*************************************
     *
     ************************************/
    public static void setIndicator(Context context, TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
            tabStrip.setAccessible(true);
            LinearLayout ll_tab = null;
            try {
                ll_tab = (LinearLayout) tabStrip.get(tabs);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            int left = (int) (getDisplayMetrics(context).density * leftDip);
            int right = (int) (getDisplayMetrics(context).density * rightDip);
            for (int i = 0; i < ll_tab.getChildCount(); i++) {
                View child = ll_tab.getChildAt(i);
                child.setPadding(0, 0, 0, 0);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
                params.leftMargin = left;
                params.rightMargin = right;
                child.setLayoutParams(params);
                child.invalidate();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }


    public static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics metric = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric;
    }


    @OnClick(R.id.send_red)
    public void awardClick() {
        new GiftDialog(this, userid).setLishener(new GiftDialog.OnGiftLishener() {
            @Override
            public void onSuccess(int gid, int num) {
                sendSongLi("[" + "☆" + Util.nickname + "给" + nicheng + "赠送了" + num + "个" + Tools.getGiftName(gid) + "☆" + "]");
            }


        }).show();
    }

    @OnClick(R.id.tv_call)
    public void callClick() {
        if (!"0".equals(Util.iszhubo)) {
            Toast.makeText(this, "主播不能跟主播通话", Toast.LENGTH_SHORT).show();
            return;
        }
        if (userid.equals(Util.userid)) {
            showToast("不能跟自己通话");
            return;
        }
        if (userInfo == null) {
            showToast("用户不存在");
            return;
        }

        OkHttpUtils.post(this)
                .url(URL.URL_CALL)
                .addParams("param1", "")
                .addParams("param2", Util.userid)
                .addParams("param3", userid)
                .build()
                .execute(new DialogCallback(this) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        showToast("网络异常");
                        tvCall.setEnabled(true);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        super.onResponse(response, id);
                        tvCall.setEnabled(true);
                        if (TextUtils.isEmpty(response)) {
                            showToast("拨打失败");
                            return;
                        }
                        com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(response);
                        String success_ornot = jsonObject.getString("success");
                        if (success_ornot.equals("1")) {
                            final String timestamp = new Date().getTime() + "";
                            yid = userInfo.getId() + "";
                            ZhuboInfo zhuboInfo = new ZhuboInfo(yid, userInfo.getNickname(), userInfo.getPhoto(), timestamp, P2PVideoConst.GUKE_CALL_ZHUBO, P2PVideoConst.NONE_YUYUE);
                            GukeActivity.startCallZhubo(UserActivity.this, zhuboInfo);
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

        tvCall.setEnabled(false);
    }

    public class FragAdapter extends FragmentPagerAdapter {

        private List<Fragment> mFragments;


        public FragAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            mFragments = fragments;
        }


        @Override
        public Fragment getItem(int arg0) {
            return mFragments.get(arg0);
        }


        @Override
        public int getCount() {
            return mFragments.size();
        }


        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "资料";
                case 1:
                    return "视频";
                default:
                    return "资料";
            }
        }


    }

    public class GlideImageLaoder extends com.youth.banner.loader.ImageLoader {

        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            ImageUtils.loadImage((String) path, imageView);
        }

    }

    public void showPopupspWindow_chongzhi() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
                Intent intent = new Intent(UserActivity.this, RechargeActivity.class);
                startActivity(intent);
                popupWindow.dismiss();
            }
        });

        popupWindow = new PopupWindow(layout, ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        Tools.backgroundAlpha(UserActivity.this, 0.4f);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            // 在dismiss中恢复透明度
            public void onDismiss() {
                Tools.backgroundAlpha(UserActivity.this, 1f);
            }
        });
    }

    public void showPopupspWindow4(View parent) {
        // 加载布局
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.pop_01162, null);
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
        //////////////////////////////////////////////
        mPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
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

    //预约
    @SuppressLint({"RtlHardcoded", "NewApi"})
    public void showPopupspWindow_reservation(View parent, int online) {
        // 加载布局
        LayoutInflater inflater = LayoutInflater.from(UserActivity.this);
        View layout = inflater.inflate(R.layout.pop_reservation_01160, null);
        //取消、确定、标题
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
        exit_tuichu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                popupWindow.dismiss();
            }
        });

        exit_login = (TextView) layout.findViewById(R.id.exit_login);
        /////////////////////////////////////////
        exit_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String befortime = sdf.format(now.getTimeInMillis());

                now.add(Calendar.HOUR, 12);
                String dateStr = sdf.format(now.getTimeInMillis());

                //开始时间
//                String[] paramsMap = {Util.userid, userid, befortime, dateStr};
//                new Thread(new UsersThread_01160A("insert_reservation", paramsMap, handler).runnable).start();

                OkHttpUtils.post(this)
                        .url(URL.URL_INSERT)
                        .addParams("param1", Util.userid)
                        .addParams("param2", userid)
                        .addParams("param3", befortime)
                        .addParams("param4", dateStr)
                        .build()
                        .execute(new DialogCallback(UserActivity.this) {
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
                                            Toast.makeText(mContext, "余额不足，无法预约", Toast.LENGTH_SHORT).show();
                                        } else if (success_ornot.equals("-1")) {
                                            Toast.makeText(mContext, "已预约成功，无法再次预约", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Util.sendMsgText("『" + Util.nickname + "』 Appointment is successful", userid);
                                            Toast.makeText(mContext, "预约成功,消费" + success_ornot + "悦币", Toast.LENGTH_SHORT).show();
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

        popupWindow = new PopupWindow(layout, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        // 控制键盘是否可以获得焦点
        popupWindow.setFocusable(true);
        WindowManager.LayoutParams lp = UserActivity.this.getWindow().getAttributes();
        lp.alpha = 0.5f;
        UserActivity.this.getWindow().setAttributes(lp);
        popupWindow.setBackgroundDrawable(new BitmapDrawable(null, ""));
        //popupWindow.showAsDropDown(parent, 0, 0,Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL);
        popupWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);
        ////////////////////////////////////////
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            public void onDismiss() {
                WindowManager.LayoutParams lp = UserActivity.this.getWindow().getAttributes();
                lp.alpha = 1f;
                UserActivity.this.getWindow().setAttributes(lp);
            }
        });
    }

    /**
     * 执行发送送礼物 文本类型
     *
     * @param content
     */
    void sendSongLi(String content) {
        Msg msg = getChatInfoTo(content, Const.MSG_TYPE_TEXT);
        msg.setMsgId(msgDao.insert(msg));
        listMsg.add(msg);

        final String message = content + Const.SPLIT + Const.MSG_TYPE_TEXT
                + Const.SPLIT + sd.format(new Date()) + Const.SPLIT + Util.nickname + Const.SPLIT + Util.headpic;
        //LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"message: "+message);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"before sendMessage()");
                    sendMessage_hongbao(Utils.xmppConnection, message, userid);
                } catch (XMPPException | SmackException.NotConnectedException e) {
                    e.printStackTrace();
                    Looper.prepare();
                    Looper.loop();
                }
            }
        }).start();
        updateSession1(Const.MSG_TYPE_TEXT, content, nicheng, headpic);
    }

    /**
     * 发送消息
     *
     * @param content
     * @param touser
     * @throws XMPPException
     * @throws SmackException.NotConnectedException
     */
    public void sendMessage_hongbao(XMPPTCPConnection mXMPPConnection, String content,
                                    String touser) throws XMPPException, SmackException.NotConnectedException {
        if (mXMPPConnection == null || !mXMPPConnection.isConnected()) {

        }

        ChatManager chatmanager = Utils.xmppchatmanager;

        Chat chat = chatmanager.createChat(touser + "@" + Const.XMPP_HOST, null);
        if (chat != null) {

            chat.sendMessage(content, Util.userid + "@" + Const.XMPP_HOST);
            Log.e("jj", "发送成功");
            LogDetect.send(LogDetect.DataType.noType, "01160顾客发消息", content);
        } else {
            //LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"send fail:chat is null");
        }
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
        msg.setFromUser(userid);
        msg.setToUser(I);
        msg.setType(msgtype);
        msg.setIsComing(1);
        msg.setContent(message);
        msg.setDate(time);
        return msg;
    }

    void updateSession1(String type, String content, String name, String logo) {
        Session session = new Session();
        session.setFrom(userid);
        session.setTo(I);
        session.setNotReadCount("");// 未读消息数量
        session.setContent(content);
        session.setTime(Tools.currentTime());
        session.setType(type);
        session.setName(name);
        session.setHeadpic(logo);

        if (sessionDao.isContent(userid, I)) {
            sessionDao.updateSession(session);
        } else {
            sessionDao.insertSession(session);
        }
        Intent intent = new Intent(Const.ACTION_ADDFRIEND);// 发送广播，通知消息界面更新
        sendBroadcast(intent);
    }


    /*************************************
     *
     ************************************/
    private class reciever extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            showPopupspWindow4(wodeqianbao);
        }
    }

    /*************************************
     *
     ************************************/
    private class reciever1 extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //showPopupspWindow4(wodeqianbao);
            tvCall.setEnabled(true);
            ///////////////////////////////
            LogDetect.send(LogDetect.DataType.specialType, "准备通话---发送openfire", "允许点击");
        }
    }

    /*************************************
     *
     ************************************/
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(msgOperReciver1);
        unregisterReceiver(reviever);
        unregisterReceiver(reviever1);
    }
}
