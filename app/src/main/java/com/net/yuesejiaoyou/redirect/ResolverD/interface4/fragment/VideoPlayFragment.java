package com.net.yuesejiaoyou.redirect.ResolverD.interface4.fragment;


import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aliyun.vodplayer.media.AliyunLocalSource;
import com.aliyun.vodplayer.media.AliyunPlayAuth;
import com.aliyun.vodplayer.media.AliyunVidSource;
import com.aliyun.vodplayer.media.AliyunVodPlayer;
import com.aliyun.vodplayer.media.IAliyunVodPlayer;
import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.R.id;
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
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack.SmackException;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.activity.UserActivity;
import com.net.yuesejiaoyou.redirect.ResolverB.getset.Videoinfo;
import com.net.yuesejiaoyou.redirect.ResolverB.interface3.UsersThread_01066B;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.agora.P2PVideoConst;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.activity.GukeActivity;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.bean.ZhuboInfo;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.util.AliVideoPlayer;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.util.VideoPlayListener;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.GlideApp;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.ShareHelp;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.utils.Tools;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.activity.RechargeActivity;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.widget.GiftDialog;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.wasabeef.glide.transformations.BlurTransformation;
import pl.droidsonroids.gif.GifImageView;


@SuppressLint("ValidFragment")
public class VideoPlayFragment extends Fragment implements OnClickListener {
    private int pos;
    private boolean bSelected = false;
    private boolean bCreated = false;
    private boolean bPlayed = false;
    private boolean bVideoReady = false;
    private boolean isDestroy = false;
    boolean isloading = true;
    boolean isdel = false;
    private int likenum = 0;
    private boolean likeable = true;
    private static final String TAG = VideoPlayFragment.class.getSimpleName();

    public boolean mAutoPlay = false;
    private List<Button> qualityIds = new ArrayList<>();
    private boolean replay = false;
    private String mVid = null;
    private Map<String, String> qualityList = new HashMap<>();


    private View view;
    private Videoinfo info;
    private ImageView viderimg, id_head_icon, id_follow_v, id_call_v, mohuimg;  //, surimg;
    private SurfaceView videoView;
    private TextView id_like_video_quantity, id_video_income, id_play_video_quantity, id_share_video_quantity, v_name, id_video_description;
    Videoinfo videoinfo1;
    private RelativeLayout id_head_layout;
    private PopupWindow popupWindow;
    private GifImageView id_send_red_packet;
    private ImageView id_video_activity_back;
    /**
     * 160
     */
    private ImageView id_report_or_delete;
    private LinearLayout pop, ll;
    private CheckedTextView c1, c2, c3, c4, c5, c6;
    RelativeLayout container1;
    ImageView ivStop;
    private Context mContext;
    DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
            .cacheOnDisc(true).bitmapConfig(Bitmap.Config.RGB_565).build();
    private SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //送红包
    private ChatMsgDao msgDao;
    private List<Msg> listMsg = new ArrayList<Msg>();
    private SessionDao sessionDao;
    private String I, price, userid;

    private AliyunPlayAuth mPlayAuth = null;
    private AliyunVidSource mVidSource = null;
    private AliyunLocalSource mLocalSource = null;
    private AliyunVodPlayer aliyunVodPlayer;
    private MsgOperReciver1 msgOperReciver1;

    public VideoPlayFragment() {

    }

    public VideoPlayFragment(Videoinfo info, int pos) {
        this.info = info;
        this.pos = pos;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        I = Util.userid;
        msgDao = new ChatMsgDao(getActivity());
        sessionDao = new SessionDao(getActivity());
        userid = info.getUser_id();
        mContext = getActivity();

        view = inflater.inflate(R.layout.activity_video_play_layout, null);
        viderimg = (ImageView) view.findViewById(id.viderimg);
        viderimg.setVisibility(View.VISIBLE);
        id_video_activity_back = (ImageView) view.findViewById(id.id_video_activity_back);
        id_video_activity_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        id_call_v = (ImageView) view.findViewById(id.id_call_v);
        id_video_income = (TextView) view.findViewById(id.id_video_income);
        id_send_red_packet = (GifImageView) view.findViewById(id.id_send_red_packet);

        if (TextUtils.isEmpty(info.getUser_id()) || info.getUser_id().equals(Util.userid)) {
            id_call_v.setVisibility(View.GONE);
            id_video_income.setVisibility(View.VISIBLE);
            id_send_red_packet.setVisibility(View.GONE);
            info.setIspay(0);
        } else {
            id_video_income.setVisibility(View.INVISIBLE);
            id_send_red_packet.setVisibility(View.VISIBLE);
        }
        id_call_v.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(info.getUser_id()) || !"0".equals(Util.iszhubo)) {
                    Toast.makeText(getActivity(), "主播不能跟主播通话", Toast.LENGTH_SHORT).show();
                    return;
                }

                String mode1 = "zhubo_online";
                String[] paramsMap1 = {"", Util.userid, videoinfo1.getUser_id() + ""};
                com.net.yuesejiaoyou.redirect.ResolverB.interface3.UsersThread_01158B a = new com.net.yuesejiaoyou.redirect.ResolverB.interface3.UsersThread_01158B(mode1, paramsMap1, handler);
                Thread c = new Thread(a.runnable);
                c.start();
                id_call_v.setClickable(false);
            }
        });
        id_head_layout = (RelativeLayout) view.findViewById(id.id_head_layout);
        id_head_layout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(info.getUser_id())) {
                    return;
                }

                Intent intent = new Intent(mContext, UserActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", "" + info.getUser_id());
                intent.putExtras(bundle);


                if (bPlayed) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ivStop.setVisibility(View.VISIBLE);
                            Log.v("TTTTTTT", "暂停" + info.getId() + "," + this);
                            aliyunVodPlayer.pause();

                        }
                    });
                }

                getActivity().startActivity(intent);
            }
        });

        id_send_red_packet.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                id_send_red_packet.setImageResource(R.drawable.gifton);
                new GiftDialog(getActivity(), userid).setLishener(new GiftDialog.OnGiftLishener() {
                    @Override
                    public void onSuccess(int gid, int num) {
                        sendSongLi("[" + "☆" + Util.nickname + "给" + videoinfo1.getNickname() + "赠送了" + num + "个" + Tools.getGiftName(gid) + "☆" + "]");
                    }


                }).show();
            }
        });


        id_head_icon = (ImageView) view.findViewById(id.id_head_icon);
        id_follow_v = (ImageView) view.findViewById(id.id_follow_v);
        id_follow_v.setOnClickListener(this);
        id_like_video_quantity = (TextView) view.findViewById(id.id_like_video_quantity);
        id_play_video_quantity = (TextView) view.findViewById(id.id_play_video_quantity);
        id_share_video_quantity = (TextView) view.findViewById(id.id_share_video_quantity);

        id_play_video_quantity.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                if (bPlayed) {

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (ivStop.getVisibility() == View.GONE) {
                                ivStop.setVisibility(View.VISIBLE);
                                Log.v("TTTTTTT", " 暂停," + info.getId() + "," + this);
                                aliyunVodPlayer.pause();
                            } else {
                                ivStop.setVisibility(View.GONE);
                                aliyunVodPlayer.resume();
                                Log.v("TTTTTTT", "播放" + info.getId() + "," + this);
                            }
                        }

                    });
                }
            }
        });

        id_like_video_quantity.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                setLike();
            }
        });
        v_name = (TextView) view.findViewById(id.v_name);
        id_video_description = (TextView) view.findViewById(id.id_video_description);
        id_call_v = (ImageView) view.findViewById(id.id_call_v);

        /**
         * 160
         */
        id_report_or_delete = (ImageView) view.findViewById(id.id_report_or_delete);
        id_report_or_delete.setOnClickListener(this);

        LogDetect.send(DataType.nonbasicType, "onCreateView", info.getId() + "++++++");
        //isinitview=true;
        isloading = true;
        isdel = false;


        container1 = (RelativeLayout) view.findViewById(id.ceshi);
        /////////////////////////////////////////////////////////////////
        container1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bPlayed) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (aliyunVodPlayer.isPlaying()) {
                                ivStop.setVisibility(View.VISIBLE);
                                aliyunVodPlayer.pause();
                            }
                        }
                    });
                }
            }
        });


        ivStop = view.findViewById(R.id.iv_stop);
        /////////////////////////////////////////////////////////////////
        ivStop.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //paysuccess();
                if (info.getIspay() == 1) {
                    showPopupspWindow_payvideo(id_head_layout);
                } else {

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                            ivStop.setVisibility(View.GONE);
                            if (bPlayed) {
                                //if (aliyunVodPlayer.) {
                                Log.v("TTTTTTT", " 播放," + info.getId() + "," + this);
                                aliyunVodPlayer.resume();
                                //} else {
                                //    Log.v("TTTTTTT", "setUserVisibleHint-isVisibleToUser=false 自然停播," + info.getId() + "," + this);
                                //}
                            }
                        }
                    });
                }

            }
        });

        if (info.getIspay() == 1) {

            ivStop.setVisibility(View.VISIBLE);

            mohuimg = (ImageView) view.findViewById(id.mohuimg);
            mohuimg.setVisibility(View.VISIBLE);
            GlideApp.with(mContext)
                    .load(info.getVideo_photo())
                    .placeholder(R.drawable.moren)
                    .error(R.drawable.moren)
                    .optionalTransform(new BlurTransformation(mContext, 6, 4))  // “23”：设置模糊度(在0.0到25.0之间)，默认”25";"4":图片缩放比例,默认“1”。
                    .into(mohuimg);
            GlideApp.with(mContext)
                    .load(info.getVideo_photo())
                    .placeholder(R.drawable.moren)
                    .error(R.drawable.moren)
                    //.crossFade(1000)
                    .optionalTransform(new BlurTransformation(mContext, 6, 4))  // “23”：设置模糊度(在0.0到25.0之间)，默认”25";"4":图片缩放比例,默认“1”。
                    .into(viderimg);

        } else {
            container1.setVisibility(View.VISIBLE);
            videoView = new SurfaceView(mContext);
            videoView.setZOrderMediaOverlay(true);
            container1.addView(videoView);
            ImageLoader.getInstance().displayImage(
                    info.getVideo_photo(), viderimg,
                    options);
            startPlay();
        }


        bCreated = true;
        likeable = true;


        msgOperReciver1 = new MsgOperReciver1();
        IntentFilter intentFilter = new IntentFilter("userinfoguanzhu");
        getActivity().registerReceiver(msgOperReciver1, intentFilter);

        return view;
    }

    private class MsgOperReciver1 extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String msgBody = intent.getStringExtra("guanzhu");
            int isguanzhu = intent.getIntExtra("isguanzhu", 0);
            ///////////////////////////////
            LogDetect.send(LogDetect.DataType.specialType, "downorup:", msgBody);
            if (msgBody.equals(info.getUser_id())) {
                if (isguanzhu == 0) {
                    handler.sendMessage(handler.obtainMessage(1000, (Object) msgBody));
                } else {
                    handler.sendMessage(handler.obtainMessage(1001, (Object) msgBody));
                }

            }
        }
    }


    private void paysuccess() {
        container1.setVisibility(View.VISIBLE);
        videoView = new SurfaceView(mContext);
        videoView.setZOrderMediaOverlay(true);
        container1.addView(videoView);
        ImageLoader.getInstance().displayImage(
                info.getVideo_photo(), viderimg,
                options);
        startPlay();
        info.setIspay(2);
        ivStop.setVisibility(View.GONE);
        mohuimg.setVisibility(View.GONE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent("payinfo");//
                Bundle bundle = new Bundle();
                bundle.putInt("paysuccess", pos);
                intent.putExtras(bundle);
                getActivity().sendBroadcast(intent);

            }
        }, 200);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            LogDetect.send(DataType.nonbasicType, "视频id", info.getId() + "111111");
            bSelected = true;
            if (bCreated) {
                ivStop.setVisibility(View.GONE);
                if (info.getIspay() == 0 || info.getIspay() == 2) {
                    if (bPlayed) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //viderimg.setVisibility(View.GONE);
                                videoView.setVisibility(View.VISIBLE);
                                aliyunVodPlayer.replay();
                            }
                        });
                    } else {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (bVideoReady && !aliyunVodPlayer.isPlaying()) {
                                    //viderimg.setVisibility(View.GONE);
                                    videoView.setVisibility(View.VISIBLE);
                                    aliyunVodPlayer.start();
                                    bPlayed = true;
                                }
                            }
                        });
                    }
                }
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent("videoinfo");//
                    Bundle bundle = new Bundle();
                    bundle.putInt("downorup", pos);
                    intent.putExtras(bundle);
                    getActivity().sendBroadcast(intent);
                }
            }, 200);

            if (isloading) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isloading = false;
                        String mode = "video_info";
                        //userid，页数，男女
                        String[] params = {mode, info.getId() + "", info.getUser_id()};
                        UsersThread_01066B b = new UsersThread_01066B(mode, params, handler);
                        Thread thread = new Thread(b.runnable);
                        thread.start();
                    }
                }, 500);
            }

            /*if(bCreated) {
                startPlay();
            }*/
        } else {
            //Log.v("TT","setUserVisibleHint-isVisibleToUser=false "+info.getId()+","+this);
            bSelected = false;
            if (bCreated) {
                if (info.getIspay() == 0 || info.getIspay() == 2) {
                    if (aliyunVodPlayer.isPlaying()) {
                        Log.v("TTTTTTT", "setUserVisibleHint-isVisibleToUser=false 停播," + info.getId() + "," + this);
                        aliyunVodPlayer.stop();
                    } else {
                        Log.v("TTTTTTT", "setUserVisibleHint-isVisibleToUser=false 自然停播," + info.getId() + "," + this);
                    }
                }
            } else {
                Log.e("TTTTTTT", "setUserVisibleHint-isVisibleToUser=false 其他," + info.getId() + "," + this);
            }
            /*if(bCreated) {
                stopPlay();
            }*/
        }
    }


    public void setLike() {
        if (likeable) {
            likeable = false;
            String mode = "setlike";
            //Toast.makeText(mContext, Util.userid+","+info.getId(), Toast.LENGTH_SHORT).show();
            String[] params = {Util.userid, info.getId() + "", Util.userid};
            com.net.yuesejiaoyou.redirect.ResolverB.interface3.UsersThread_01158B b2 = new com.net.yuesejiaoyou.redirect.ResolverB.interface3.UsersThread_01158B(mode, params, handler);
            Thread t2 = new Thread(b2.runnable);
            t2.start();
        }

    }



    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1000:
                    id_follow_v.setImageResource(R.drawable.video_guanzhu);
                    break;
                case 1001:
                    id_follow_v.setImageResource(R.drawable.video_guanzhu_on);
                    break;
                case 550:
                    String json121 = (String) msg.obj;
                    try { //如果服务端返回1，说明个人信息修改成功了
                        JSONObject jsonObject = new JSONObject(json121);
                        int isguanzhu = Integer.parseInt(jsonObject.getString("success"));
                        LogDetect.send(DataType.specialType, "yue_____： ", isguanzhu);
                        if (isguanzhu == 0) {
                            showPopupspWindow_chongzhi();
                        } else {
                            Toast.makeText(mContext, "支付成功", Toast.LENGTH_SHORT).show();
                            paysuccess();
                        }
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    break;

                case 500:
                    String json11 = (String) msg.obj;
                    try { //如果服务端返回1，说明个人信息修改成功了
                        JSONObject jsonObject = new JSONObject(json11);
                        int isguanzhu = Integer.parseInt(jsonObject.getString("success"));
                        LogDetect.send(DataType.specialType, "yue_____： ", isguanzhu);
                        if (isguanzhu == 0) {
                            //ib_focus.setBackgroundResource(R.drawable.guanzhu);
                        } else {
                            id_follow_v.setImageResource(R.drawable.video_guanzhu_on);

                            Intent intent = new Intent("userguanzhu");//
                            Bundle bundle = new Bundle();
                            bundle.putString("guanzhu", info.getUser_id());
                            intent.putExtras(bundle);
                            getActivity().sendBroadcast(intent);
                        }
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    break;

                case 200:
                    //  LogDetect.send(DataType.nonbasicType, "视频id", info.getId() + "+++3333333");

                    videoinfo1 = (Videoinfo) msg.obj;

                    if (videoinfo1 == null) {
                        //Toast.makeText(getActivity(),"加载失败",Toast.LENGTH_SHORT).show();
                    } else {
                        ImageLoader.getInstance().displayImage(
                                videoinfo1.getPhoto(), id_head_icon,
                                options);
                        if (videoinfo1.getIsguanzhu() == 1) {
                            id_follow_v.setImageResource(R.drawable.video_guanzhu_on);
                        } else {
                            id_follow_v.setImageResource(R.drawable.video_guanzhu);
                        }

                        if (!isdel) {
                            if (videoinfo1.getIszan() == 1) {
                                Tools.setDrawableTop(id_like_video_quantity, R.drawable.video_like_on);
                            } else {
                                Tools.setDrawableTop(id_like_video_quantity, R.drawable.video_like);
                            }

                            id_like_video_quantity.setText(videoinfo1.getLike_num());
                            ///////////////////////////
                            LogDetect.send(LogDetect.DataType.specialType, "videoinfo1.getLike_num():", videoinfo1.getLike_num());
                            /////////////////////////////////////////////
                            ///////////////置空判断
                            if (videoinfo1.getLike_num() == null) {
                                likenum = 0;
                            } else {
                                likenum = Integer.parseInt(videoinfo1.getLike_num());
                            }
                            ///////////////////////
                            id_play_video_quantity.setText(videoinfo1.getLiulan_num() + "");
                            id_share_video_quantity.setText(videoinfo1.getShare_num() + "");
                            v_name.setText(videoinfo1.getNickname() + "");
                            id_video_description.setText(videoinfo1.getExplain() + "");
                            if (videoinfo1.getOnline() == 1) {
                                Tools.setDrawableRight(v_name,R.mipmap.icon_ckzl_zx);

                            } else if (videoinfo1.getOnline() == 2) {
                                Tools.setDrawableRight(v_name,R.mipmap.icon_ckzl_ml);

                            } else if (videoinfo1.getOnline() == 3) {
                                Tools.setDrawableRight(v_name,R.mipmap.icon_ckzl_wr);

                            } else {
                                Tools.setDrawableRight(v_name,R.mipmap.icon_ckzl_lx);

                            }
                        }
                        //viderimg.setVisibility(View.GONE);
                        //surimg.setVisibility(View.VISIBLE);
                        if (info.getIspay() == 0 || info.getIspay() == 2) {
                            videoView.setVisibility(View.VISIBLE);
                        }

                        id_video_income.setText(videoinfo1.getPay_num() + "");


                    }
                    //isloading = true;
                    break;
                case 111:
                    likeable = true;
                    String a = (String) msg.obj;
                    try { //如果服务端返回1，说明个人信息修改成功了
                        JSONObject jsonObject = new JSONObject(a);
                        int isguanzhu = Integer.parseInt(jsonObject.getString("success"));
                        LogDetect.send(DataType.specialType, "yue_____： ", isguanzhu);
                        if (isguanzhu == 0) {//取消点赞
                            Tools.setDrawableTop(id_like_video_quantity, R.drawable.video_like);
                            likenum = likenum - 1;
                            id_like_video_quantity.setText(likenum + "");
                            //Intent intent=new Intent("111");
                            //getActivity().sendBroadcast(intent);

                        } else {//点赞
                            Tools.setDrawableTop(id_like_video_quantity, R.drawable.video_like_on);
                            likenum = likenum + 1;
                            id_like_video_quantity.setText(likenum + "");
                        }

                        Intent intent = new Intent("dianzan");//
                        Bundle bundle = new Bundle();
                        bundle.putInt("dianzan", pos);
                        bundle.putInt("iszan", isguanzhu);
                        intent.putExtras(bundle);
                        getActivity().sendBroadcast(intent);


                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
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

                                GukeActivity.startCallZhubo(getActivity(), new ZhuboInfo(videoinfo1.getUser_id(), videoinfo1.getNickname(), videoinfo1.getPhoto(), timestamp, P2PVideoConst.GUKE_CALL_ZHUBO, P2PVideoConst.NONE_YUYUE));

                            }//否则失败了
                            else if (success_ornot.equals("2")) {
                                showPopupspWindow_reservation(viderimg, 2);
                                Toast.makeText(mContext, "主播忙碌，请稍后再试", Toast.LENGTH_SHORT).show();
                                //tv_voc.setClickable(true);
                            } else if (success_ornot.equals("3")) {
                                showPopupspWindow_reservation(viderimg, 3);
                                Toast.makeText(mContext, "主播设置勿打扰，请稍后再试", Toast.LENGTH_SHORT).show();
                            } else if (success_ornot.equals("4")) {
                                showPopupspWindow_reservation(viderimg, 4);
                                Toast.makeText(mContext, "主播不在线", Toast.LENGTH_SHORT).show();
                            } else if (success_ornot.equals("0")) {
                                Toast.makeText(mContext, "您的余额不足", Toast.LENGTH_SHORT).show();
                            } else if (success_ornot.equals("5")) {
                                Toast.makeText(mContext, "主播被封禁", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(mContext, "请检查网络连接", Toast.LENGTH_SHORT).show();
                    }
                    id_call_v.setClickable(true);
                    break;
                case 101://红包打赏
                    String json2 = (String) msg.obj;
                    try { //如果服务端返回1，说明个人信息修改成功了
                        JSONObject jsonObject = new JSONObject(json2);
                        LogDetect.send(DataType.specialType, "yue_____： ", jsonObject.getString("success"));
                        if (jsonObject.getString("success").equals("1")) {
                            sendSongLi("[" + "☆" + Util.nickname + "给" + info.getNickname() + "赠送了" + price + "元红包☆" + "]");
                            Toast.makeText(mContext, "支付成功", Toast.LENGTH_SHORT).show();
                        } else {
                            showPopupspWindow_chongzhi();
                        }
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
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
                                Toast.makeText(mContext, "举报成功", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(mContext, "举报失败，请检查网络连接", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(mContext, "预约失败，请检查网络连接", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    /****************************************************
     * 弹出框提示用户是否付款
     * @param parent
     ****************************************************/
    public void showPopupspWindow_payvideo(View parent) {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.is_payvideo_01165, null);

        TextView content = (TextView) layout.findViewById(id.content);
        content.setText("观看视频需求 " + info.getPrice() + " 悦币.是否确认付款?");
        TextView cancel = (TextView) layout.findViewById(id.cancel);
        cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                popupWindow.dismiss();

            }
        });
        TextView confirm = (TextView) layout.findViewById(id.confirm);//获取小窗口上的TextView，以便显示现在操作的功能。
        confirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String mode1 = "payvideo";
                //userid，页数，男女
                String[] params = {Util.userid, info.getId() + ""};
                UsersThread_01066B b = new UsersThread_01066B(mode1, params, handler);
                Thread thread = new Thread(b.runnable);
                thread.start();
                popupWindow.dismiss();
            }
        });


        popupWindow = new PopupWindow(layout, ViewPager.LayoutParams.MATCH_PARENT,//
                ViewPager.LayoutParams.WRAP_CONTENT, true);
        // 控制键盘是否可以获得焦点
        popupWindow.setFocusable(true);
        // 设置popupWindow弹出窗体的背景
        //WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        //lp.alpha = 0.4f;
        //getActivity().getWindow().setAttributes(lp);
        popupWindow.setBackgroundDrawable(new BitmapDrawable(null, ""));
        WindowManager manager = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
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
                // WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                //  lp.alpha = 1f;
                // getActivity().getWindow()
                // .addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                //  getActivity().getWindow().setAttributes(lp);
            }
        });
    }


    /////////////////////////////////////////////////////////////////////////

    /****************************************************
     * 弹出框:是否充值
     * @
     ****************************************************/
    public void showPopupspWindow_chongzhi() {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.is_chongzhi_01165, null);

        TextView cancel = (TextView) layout.findViewById(id.cancel);
        /////////////////////////////////////////////////////////////////
        cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                popupWindow.dismiss();

            }
        });


        TextView confirm = (TextView) layout.findViewById(id.confirm);//获取小窗口上的TextView，以便显示现在操作的功能。
        /////////////////////////////////////////////////////////////////
        confirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent1 = new Intent();
                intent1.setClass(mContext, RechargeActivity.class);//充值页面
                startActivity(intent1);
                getActivity().finish();
            }
        });


        popupWindow = new PopupWindow(layout, ViewPager.LayoutParams.MATCH_PARENT,//？？？？？？？？？？？？？？
                ViewPager.LayoutParams.WRAP_CONTENT, true);
        // 控制键盘是否可以获得焦点
        popupWindow.setFocusable(true);
        // 设置popupWindow弹出窗体的背景
        //WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        //lp.alpha = 0.4f;
        //getActivity().getWindow().setAttributes(lp);
        popupWindow.setBackgroundDrawable(new BitmapDrawable(null, ""));
        WindowManager manager = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
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
                //WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                //lp.alpha = 1f;
                //getActivity().getWindow()
                //        .addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                //getActivity().getWindow().setAttributes(lp);
            }
        });
    }


    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            ////////////////////////////////////////////////////////////////////////
            case id.id_report_or_delete:
                showPopupspWindow_rp(view);
                break;
            ////////////////////////////////////////////////////////////////////////
            case id.id_share_video_quantity:
                ShareHelp shareHelp = new ShareHelp();
                shareHelp.showSharevideo(getActivity(), Util.invite_num, info.getId() + "");

                break;


            ////////////////////////////////////////////////////////////////////////
            case id.id_follow_v:
                String mode1 = "guanzhu";
                //userid，页数，男女
                String[] params = {"13", info.getUser_id(), 0 + ""};
                UsersThread_01066B b = new UsersThread_01066B(mode1, params, handler);
                Thread thread = new Thread(b.runnable);
                thread.start();
                break;
            ////////////////////////////////////////////////////////////////////////
            case id.c1:
                String mode = "report";
                String[] paramsMap = {Util.userid, videoinfo1.getUser_id() + "", c1.getText().toString()};
                com.net.yuesejiaoyou.redirect.ResolverB.interface3.UsersThread_01160B az = new com.net.yuesejiaoyou.redirect.ResolverB.interface3.UsersThread_01160B(mode, paramsMap, handler);
                Thread t = new Thread(az.runnable);
                t.start();
                LogDetect.send(DataType.specialType, "01160 开线程举报:", "3");
                break;
            ////////////////////////////////////////////////////////////////////////
            case id.c2:
                String mode112 = "report";
                String[] paramsMap112 = {Util.userid, videoinfo1.getUser_id() + "", c2.getText().toString()};
                com.net.yuesejiaoyou.redirect.ResolverB.interface3.UsersThread_01160B a1 = new com.net.yuesejiaoyou.redirect.ResolverB.interface3.UsersThread_01160B(mode112, paramsMap112, handler);
                Thread t1 = new Thread(a1.runnable);
                t1.start();
                LogDetect.send(DataType.specialType, "01160 开线程举报:", "3");
                break;
            ////////////////////////////////////////////////////////////////////////
            case id.c3:
                String mode2 = "report";
                String[] paramsMap2 = {Util.userid, videoinfo1.getUser_id() + "", c3.getText().toString()};
                com.net.yuesejiaoyou.redirect.ResolverB.interface3.UsersThread_01160B a2 = new com.net.yuesejiaoyou.redirect.ResolverB.interface3.UsersThread_01160B(mode2, paramsMap2, handler);
                Thread t2 = new Thread(a2.runnable);
                t2.start();
                LogDetect.send(DataType.specialType, "01160 开线程举报:", "3");
                break;
            ////////////////////////////////////////////////////////////////////////
            case id.c4:
                String mode3 = "report";
                String[] paramsMap3 = {Util.userid, videoinfo1.getUser_id() + "", c4.getText().toString()};
                com.net.yuesejiaoyou.redirect.ResolverB.interface3.UsersThread_01160B a3 = new com.net.yuesejiaoyou.redirect.ResolverB.interface3.UsersThread_01160B(mode3, paramsMap3, handler);
                Thread t3 = new Thread(a3.runnable);
                t3.start();
                LogDetect.send(DataType.specialType, "01160 开线程举报:", "3");
                break;
            ////////////////////////////////////////////////////////////////////////
            case id.c5:
                String mode4 = "report";
                String[] paramsMap4 = {Util.userid, videoinfo1.getUser_id() + "", c5.getText().toString()};
                com.net.yuesejiaoyou.redirect.ResolverB.interface3.UsersThread_01160B a4 = new com.net.yuesejiaoyou.redirect.ResolverB.interface3.UsersThread_01160B(mode4, paramsMap4, handler);
                Thread t4 = new Thread(a4.runnable);
                t4.start();
                LogDetect.send(DataType.specialType, "01160 开线程举报:", "3");
                break;
            ////////////////////////////////////////////////////////////////////////
            case id.c6:
                String mode5 = "report";
                String[] paramsMap5 = {Util.userid, videoinfo1.getUser_id() + "", c6.getText().toString()};
                com.net.yuesejiaoyou.redirect.ResolverB.interface3.UsersThread_01160B a5 = new com.net.yuesejiaoyou.redirect.ResolverB.interface3.UsersThread_01160B(mode5, paramsMap5, handler);
                Thread t5 = new Thread(a5.runnable);
                t5.start();
                LogDetect.send(DataType.specialType, "01160 开线程举报:", "3");
                break;
        }

    }

    /************************************************************************
     *
     * @
     ************************************************************************/
    @Override
    public void onDestroy() {
        Log.v("TT", "VideoPlayFragment-onDestroy: " + info.getId());
        isDestroy = true;
        getActivity().unregisterReceiver(msgOperReciver1);
        handler.removeCallbacksAndMessages(null);
        if (info.getIspay() == 0 || info.getIspay() == 2) {
            // 阿里短视频销毁
            aliOnDestroy();
        }
        isdel = true;
        LogDetect.send(DataType.nonbasicType, "视频id", info.getId() + "33333");
        super.onDestroy();

    }


    /********************************************************
     *
     *@
     ********************************************************/
    public void startPlay() {
        Log.v("TTTTTTT", "startPlay: id=" + info.getId() + ",aliyunVodPlayer=" + aliyunVodPlayer + ",bSelected=" + bSelected);
        videoView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (isDestroy) {
                    return;
                }
                Log.d(TAG, "surfaceCreated");

                aliyunVodPlayer.setDisplay(holder);
            }

            ///////////////////////////////////////////////////////////////////
            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                if (isDestroy) {
                    return;
                }
                Log.d(TAG, "surfaceChanged");
                aliyunVodPlayer.surfaceChanged();
            }

            ///////////////////////////////////////////////////////////////////
            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

                if (isDestroy) {
                    return;
                }
                Log.d(TAG, "surfaceDestroyed");
            }
        });

        initVodPlayer();
        /////////////////////////////////////////////////////////////////
        AliVideoPlayer.playVideoToListener(info.getVideo_id(), new VideoPlayListener() {
            @Override
            public void vpOnSuccess(String type, String vid, String authInfo) {
                if (isDestroy) {
                    return;
                }
                Log.v("TT", "vpOnSuccess: type=" + type + ",vid=" + vid + ",authIinfo=" + authInfo);
                setPlaySource(type, vid, authInfo);

                if (mVidSource != null) {
                    aliyunVodPlayer.prepareAsync(mVidSource);
                } else if (mPlayAuth != null) {
                    aliyunVodPlayer.prepareAsync(mPlayAuth);
                } else if (mLocalSource != null) {
                    aliyunVodPlayer.prepareAsync(mLocalSource);
                }


                qualityList.put(IAliyunVodPlayer.QualityValue.QUALITY_FLUENT, getString(R.string.alivc_fd_definition));
                qualityList.put(IAliyunVodPlayer.QualityValue.QUALITY_LOW, getString(R.string.alivc_ld_definition));
                qualityList.put(IAliyunVodPlayer.QualityValue.QUALITY_STAND, getString(R.string.alivc_sd_definition));
                qualityList.put(IAliyunVodPlayer.QualityValue.QUALITY_HIGH, getString(R.string.alivc_hd_definition));
                qualityList.put(IAliyunVodPlayer.QualityValue.QUALITY_2K, getString(R.string.alivc_k2_definition));
                qualityList.put(IAliyunVodPlayer.QualityValue.QUALITY_4K, getString(R.string.alivc_k4_definition));
                qualityList.put(IAliyunVodPlayer.QualityValue.QUALITY_ORIGINAL, getString(R.string.alivc_od_definition));
            }

            @Override
            public void vpOnFail() {

            }
        });

//      logStrs.add(format.format(new Date())+ getString(R.string.log_start_get_data));

    }



    private void initVodPlayer() {
        aliyunVodPlayer = new AliyunVodPlayer(getActivity());
        String sdDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/test_save_cache";
        aliyunVodPlayer.setPlayingCache(true, sdDir, 60 * 60 /*时长, s */, 300 /*大小，MB*/);
        aliyunVodPlayer.setOnPreparedListener(new IAliyunVodPlayer.OnPreparedListener() {
            @Override
            public void onPrepared() {
                if (isDestroy) {
                    return;
                }
                bVideoReady = true;
                if (bSelected) {
                    //viderimg.setVisibility(View.GONE);
                    //surimg.setVisibility(View.VISIBLE);
                    videoView.setVisibility(View.VISIBLE);
                    Log.v("TT", "自动播： " + info.getId());
                    aliyunVodPlayer.start();    // 自动播放
                    bPlayed = true;
                } else {
                    Log.v("TT", "不自动播： " + info.getId());
                }
                //Toast.makeText(NoSkinActivity.this.getApplicationContext(), R.string.toast_prepare_success, Toast.LENGTH_SHORT).show();
                //logStrs.add(format.format(new Date())  + getString(R.string.log_prepare_success));
                //准备成功之后可以调用start方法开始播放
                showVideoProgressInfo();
                showVideoSizeInfo();

                //qualityLayout.removeAllViews();

                if (mLocalSource == null) {
                    qualityIds.clear();
                    String current = aliyunVodPlayer.getCurrentQuality();

                }
            }
        });
        aliyunVodPlayer.setOnFirstFrameStartListener(new IAliyunVodPlayer.OnFirstFrameStartListener() {
            @Override
            public void onFirstFrameStart() {

                viderimg.setVisibility(View.GONE);

                if (isDestroy) {
                    return;
                }
                Map<String, String> debugInfo = aliyunVodPlayer.getAllDebugInfo();
                long createPts = 0;
                if (debugInfo.get("create_player") != null) {
                    String time = debugInfo.get("create_player");
                    createPts = (long) Double.parseDouble(time);
                    //logStrs.add(format.format(new Date(createPts)) + getString(R.string.log_player_create_success));
                }
                if (debugInfo.get("open-url") != null) {
                    String time = debugInfo.get("open-url");
                    long openPts = (long) Double.parseDouble(time) + createPts;
                    //logStrs.add(format.format(new Date(openPts)) + getString(R.string.log_open_url_success));
                }
                if (debugInfo.get("find-stream") != null) {
                    String time = debugInfo.get("find-stream");
                    //VcPlayerLog.d(TAG + "lfj0914","find-Stream tvTime =" + tvTime + " , createpts = " + createPts);
                    long findPts = (long) Double.parseDouble(time) + createPts;
                    //logStrs.add(format.format(new Date(findPts)) + getString(R.string.log_request_stream_success));
                }
                if (debugInfo.get("open-stream") != null) {
                    String time = debugInfo.get("open-stream");
                    //VcPlayerLog.d(TAG + "lfj0914","open-Stream tvTime =" + tvTime + " , createpts = " + createPts);
                    long openPts = (long) Double.parseDouble(time) + createPts;
                    //logStrs.add(format.format(new Date(openPts)) + getString(R.string.log_start_open_stream));
                }
                //logStrs.add(format.format(new Date()) + getString(R.string.log_first_frame_played));
            }
        });
        aliyunVodPlayer.setOnErrorListener(new IAliyunVodPlayer.OnErrorListener() {
            @Override
            public void onError(int arg0, int arg1, String msg) {
                if (isDestroy) {
                    return;
                }
                if (aliyunVodPlayer != null) {
                    aliyunVodPlayer.stop();
                }

                /* if(arg0 == AliyunErrorCode.ALIVC_ERR_INVALID_INPUTFILE.getCode()){
                当播放本地报错4003的时候，可能是文件地址不对，也有可能是没有权限。
                如果是没有权限导致的，就做一个权限的错误提示。其他还是正常提示：
                    int storagePermissionRet = ContextCompat.checkSelfPermission(NoSkinActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    if(storagePermissionRet != PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(NoSkinActivity.this.getApplicationContext(), getString(R.string.toast_fail_msg) + getString(R.string.toast_no_local_storage_permission), Toast.LENGTH_SHORT).show();
                        return;
                    }
                 }*/

                Toast.makeText(getActivity().getApplicationContext(), "播放失败" + msg, Toast.LENGTH_SHORT).show();
            }
        });
        aliyunVodPlayer.setOnCompletionListener(new IAliyunVodPlayer.OnCompletionListener() {
            @Override
            public void onCompletion() {
                if (isDestroy) {
                    return;
                }
                Log.d(TAG, "onCompletion--- ");

                aliyunVodPlayer.replay();

                //showVideoProgressInfo();
                //stopUpdateTimer();
            }
        });
        aliyunVodPlayer.setOnSeekCompleteListener(new IAliyunVodPlayer.OnSeekCompleteListener() {
            @Override
            public void onSeekComplete() {
                if (isDestroy) {
                    return;
                }
                //logStrs.add(format.format(new Date()) + getString(R.string.log_seek_completed));
            }
        });
        aliyunVodPlayer.setOnStoppedListner(new IAliyunVodPlayer.OnStoppedListener() {
            @Override
            public void onStopped() {
                if (isDestroy) {
                    return;
                }
                //logStrs.add(format.format(new Date()) + getString(R.string.log_play_stopped));
                if (replay) {
                    //logStrs.add(format.format(new Date()) + getString(R.string.log_start_get_data));
                    if (mVidSource != null) {
                        aliyunVodPlayer.prepareAsync(mVidSource);
                    } else if (mPlayAuth != null) {
                        aliyunVodPlayer.prepareAsync(mPlayAuth);
                    } else if (mLocalSource != null) {
                        aliyunVodPlayer.prepareAsync(mLocalSource);
                    }
                    mAutoPlay = true;
                }
                replay = false;
            }
        });
        aliyunVodPlayer.setOnBufferingUpdateListener(new IAliyunVodPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(int percent) {
                if (isDestroy) {
                    return;
                }
                Log.d(TAG, "onBufferingUpdate--- " + percent);
                updateBufferingProgress(percent);
            }
        });
        //aliyunVodPlayer.setDisplay(surfaceView.getHolder());
        ////////////////////////////////////////////////////////////////////////
        aliyunVodPlayer.setOnChangeQualityListener(new IAliyunVodPlayer.OnChangeQualityListener() {
            @Override
            public void onChangeQualitySuccess(String finalQuality) {
                if (isDestroy) {
                    return;
                }
                Log.d(TAG, "onChangeQualitySuccess");
                showVideoProgressInfo();
            }

            ////////////////////////////////////////////////////////////////////////
            @Override
            public void onChangeQualityFail(int code, String msg) {
                if (isDestroy) {
                    return;
                }
                Log.d(TAG, "onChangeQualityFail。。。" + code + " ,  " + msg);
                //logStrs.add(format.format(new Date()) + getString(R.string.log_change_quality_fail));
            }
        });
        aliyunVodPlayer.enableNativeLog();


    }


    private void setPlaySource(String typeparam, String vid, String authinfo) {


        String type = typeparam; //getIntent().getStringExtra("type");
        if ("authInfo".equals(type)) {
            //auth方式

            //NOTE： 注意过期时间。特别是重播的时候，可能已经过期。所以重播的时候最好重新请求一次服务器。
            mVid = vid; //getIntent().getStringExtra("vid").toString();
            String authInfo = authinfo; //getIntent().getStringExtra("authinfo");
            AliyunPlayAuth.AliyunPlayAuthBuilder aliyunPlayAuthBuilder = new AliyunPlayAuth.AliyunPlayAuthBuilder();
            aliyunPlayAuthBuilder.setVid(mVid);
            aliyunPlayAuthBuilder.setPlayAuth(authInfo);
            aliyunPlayAuthBuilder.setQuality(IAliyunVodPlayer.QualityValue.QUALITY_LOW);
            //aliyunVodPlayer.setAuthInfo(aliyunPlayAuthBuilder.build());
            mPlayAuth = aliyunPlayAuthBuilder.build();
        }
    }

    /*************************************************************
     *
     * @
     *************************************************************/
    private void showVideoSizeInfo() {
//        videoWidthTxt.setText("video_width: " + aliyunVodPlayer.getVideoWidth() + " , ");
//        videoHeightTxt.setText("video_height: " + aliyunVodPlayer.getVideoHeight() + "   ");
    }

    /*************************************************************
     *
     * @
     *************************************************************/
    private void updateBufferingProgress(int percent) {
        int duration = (int) aliyunVodPlayer.getDuration();
        int secondaryProgress = (int) (duration * percent * 1.0f / 100);
        Log.d(TAG, "lfj0918 duration = " + duration + " , buffer percent = " + percent + " , secondaryProgress =" + secondaryProgress);
        //progressBar.setSecondaryProgress(secondaryProgress);
    }

    /*************************************************************
     *
     * @
     *************************************************************/
    private void showVideoProgressInfo() {

    }

    @Override
    public void onResume() {
        super.onResume();
        //保存播放器的状态，供resume恢复使用。
        resumePlayerState();
    }

    /*************************************************************
     *
     * @
     *************************************************************/
    @Override
    public void onPause() {
        super.onPause();
        savePlayerState();
    }


    private IAliyunVodPlayer.PlayerState mPlayerState;

    /*************************************************************
     *
     * @
     *************************************************************/
    private void resumePlayerState() {
        if (info.getIspay() == 0 || info.getIspay() == 2) {
            if (mPlayerState == IAliyunVodPlayer.PlayerState.Paused) {
                aliyunVodPlayer.pause();
            } else if (mPlayerState == IAliyunVodPlayer.PlayerState.Started) {
                aliyunVodPlayer.start();
            }
        }
    }

    /*************************************************************
     *
     * @
     *************************************************************/
    private void savePlayerState() {
        if (info.getIspay() == 0 || info.getIspay() == 2) {
            mPlayerState = aliyunVodPlayer.getPlayerState();
            if (aliyunVodPlayer.isPlaying()) {
                //然后再暂停播放器
                aliyunVodPlayer.pause();
            }
        }
    }


    public void aliOnDestroy() {
        aliyunVodPlayer.stop();
        aliyunVodPlayer.release();

    }


    /******************************************************************
     * @
     * 举报
     ******************************************************************/
    @SuppressLint({"RtlHardcoded", "NewApi"})
    public void showPopupspWindow_rp(View parent) {
        // 加载布局
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View layout = inflater.inflate(R.layout.pop_report_video_01160, null);
        //取消
        TextView quxiao;
        ll = (LinearLayout) layout.findViewById(id.ll);
        pop = (LinearLayout) layout.findViewById(id.pop);
        ll.setOnClickListener(this);
        pop.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                pop.setVisibility(View.GONE);
                ll.setVisibility(View.VISIBLE);
            }
        });

        c1 = (CheckedTextView) layout.findViewById(id.c1);
        c2 = (CheckedTextView) layout.findViewById(id.c2);
        c3 = (CheckedTextView) layout.findViewById(id.c3);
        c4 = (CheckedTextView) layout.findViewById(id.c4);
        c5 = (CheckedTextView) layout.findViewById(id.c5);
        c6 = (CheckedTextView) layout.findViewById(id.c6);

        c1.setOnClickListener(this);
        c2.setOnClickListener(this);
        c3.setOnClickListener(this);
        c4.setOnClickListener(this);
        c5.setOnClickListener(this);
        c6.setOnClickListener(this);

        quxiao = (TextView) layout.findViewById(id.quxiao);
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
        //  WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        // lp.alpha = 0.5f;
        //  getActivity().getWindow().setAttributes(lp);
        popupWindow.setBackgroundDrawable(new BitmapDrawable(null, ""));
        //popupWindow.showAsDropDown(parent, 0, 0,Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL);
        popupWindow.showAtLocation(parent, Gravity.CENTER | Gravity.BOTTOM, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            public void onDismiss() {
                // WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                //  lp.alpha = 1f;
                //  getActivity().getWindow().setAttributes(lp);
            }
        });
    }

    /******************************************************************
     *
     * 预约
     * @
     ******************************************************************/
    @SuppressLint({"RtlHardcoded", "NewApi"})
    public void showPopupspWindow_reservation(View parent, int online) {
        // 加载布局
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View layout = inflater.inflate(R.layout.pop_reservation_01160, null);
        //取消		 确定				标题
        TextView exit_tuichu, exit_login, user_exit;

        user_exit = (TextView) layout.findViewById(id.user_exit);
        if (online == 2) {
            user_exit.setText("主播正忙，是否预约");
        } else if (online == 3) {
            user_exit.setText("主播设置勿打扰，是否预约");
        } else if (online == 4) {
            user_exit.setText("主播离线，是否预约");
        }

        exit_tuichu = (TextView) layout.findViewById(id.exit_tuichu);
        exit_tuichu.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                popupWindow.dismiss();
            }
        });

        exit_login = (TextView) layout.findViewById(id.exit_login);
        exit_login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String befortime = sdf.format(now.getTimeInMillis());

                now.add(Calendar.HOUR, 12);
                String dateStr = sdf.format(now.getTimeInMillis());

                //开始时间
                String[] paramsMap = {Util.userid, videoinfo1.getUser_id() + "", befortime, dateStr};
                new Thread(new com.net.yuesejiaoyou.redirect.ResolverB.interface3.UsersThread_01160B("insert_reservation", paramsMap, handler).runnable).start();

                popupWindow.dismiss();
            }
        });

        popupWindow = new PopupWindow(layout, LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT, true);
        // 控制键盘是否可以获得焦点
        popupWindow.setFocusable(true);
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = 0.5f;
        getActivity().getWindow().setAttributes(lp);
        popupWindow.setBackgroundDrawable(new BitmapDrawable(null, ""));
        //popupWindow.showAsDropDown(parent, 0, 0,Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL);
        popupWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            public void onDismiss() {
                WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                lp.alpha = 1f;
                getActivity().getWindow().setAttributes(lp);
            }
        });
    }

    /**
     * 执行发送送礼物 文本类型
     *
     * @param content
     */
    void sendSongLi(String content) {
        LogDetect.send(LogDetect.DataType.specialType, "01160 openfire送礼:", "1");
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
                    LogDetect.send(LogDetect.DataType.noType, "zhuboid", userid);
                } catch (XMPPException | SmackException.NotConnectedException e) {
                    e.printStackTrace();
                    //LogDetect.send(DataType.noType,Utils.seller_id+"=phone="+Utils.android,"chatmanager: "+e.toString());
                    Looper.prepare();
                    // ToastUtil.showShortToast(ChatActivity.this, "发送失败");
                    Looper.loop();
                }
            }
        }).start();
        updateSession1(Const.MSG_TYPE_TEXT, content, info.getNickname(), info.getPhoto());
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
        getActivity().sendBroadcast(intent);
    }


}
