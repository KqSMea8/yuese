package com.net.yuesejiaoyou.redirect.ResolverB.uiface;

import android.Manifest;
import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.xjg.ClickUtils;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.xjg.FilterRecyclerViewAdapter;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.xjg.FilterTypeHelper;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.xjg.GPUCamImgOperator;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.xjg.LuoGLCameraView;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.xjg.MenuAdapter;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.xjg.MenuBean;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.xjg.ZIP;
import com.xiaojigou.luo.xjgarsdk.XJGArSdkApi;

import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLContext;

import io.agora.rtc.Constants;
import io.agora.rtc.IRtcEngineEventHandler;
import io.agora.rtc.RtcEngine;
import io.agora.rtc.video.AgoraVideoFrame;
import io.agora.rtc.video.VideoCanvas;

//import com.xiaojigou.luo.faceEff.R;

/**
 * Created by jianxin luo on 2017/10/7.
 */
public class MeiyanSet extends Activity{
    private LinearLayout mFilterLayout;
    private LinearLayout mFaceSurgeryLayout;
    protected SeekBar mFaceSurgeryFaceShapeSeek;
    protected SeekBar mFaceSurgeryBigEyeSeek;
    protected SeekBar mSkinSmoothSeek;
    protected SeekBar mSkinWihtenSeek;
    protected SeekBar mRedFaceSeek;



    private ArrayList<MenuBean> mStickerData;
    private RecyclerView mMenuView;
    private MenuAdapter mStickerAdapter;

    private RecyclerView mFilterListView;
    private FilterRecyclerViewAdapter mAdapter;
    private GPUCamImgOperator GPUCamImgOperator;
    private boolean isRecording = false;
    private final int MODE_PIC = 1;
    private final int MODE_VIDEO = 2;
    private int mode = MODE_PIC;

    private ImageView btn_shutter;
    private ImageView btn_mode;

    private ObjectAnimator animator;

    private final com.net.yuesejiaoyou.redirect.ResolverB.interface4.xjg.GPUCamImgOperator.GPUImgFilterType[] types = new GPUCamImgOperator.GPUImgFilterType[]{
            com.net.yuesejiaoyou.redirect.ResolverB.interface4.xjg.GPUCamImgOperator.GPUImgFilterType.NONE,
            com.net.yuesejiaoyou.redirect.ResolverB.interface4.xjg.GPUCamImgOperator.GPUImgFilterType.HEALTHY,
            com.net.yuesejiaoyou.redirect.ResolverB.interface4.xjg.GPUCamImgOperator.GPUImgFilterType.NOSTALGIA,
            com.net.yuesejiaoyou.redirect.ResolverB.interface4.xjg.GPUCamImgOperator.GPUImgFilterType.COOL,
            com.net.yuesejiaoyou.redirect.ResolverB.interface4.xjg.GPUCamImgOperator.GPUImgFilterType.EMERALD,
            com.net.yuesejiaoyou.redirect.ResolverB.interface4.xjg.GPUCamImgOperator.GPUImgFilterType.EVERGREEN,
            com.net.yuesejiaoyou.redirect.ResolverB.interface4.xjg.GPUCamImgOperator.GPUImgFilterType.CRAYON
    };

    // 美颜参数
//    private int hongRun;    // 红润
    private int meiBai;     // 美白
    private int moPi;       // 磨皮
//    private int shouLian;   // 瘦脸
//    private int daYan;      // 大眼
//    private String vFilter;    // 滤镜
//    private String tieZhi;     // 贴纸

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_with_filter);
        GPUCamImgOperator =  new GPUCamImgOperator();
        LuoGLCameraView luoGLCameraView = (LuoGLCameraView)findViewById(R.id.glsurfaceview_camera);
        mCustomizedCameraRenderer = luoGLCameraView;
        GPUCamImgOperator.context = luoGLCameraView.getContext();
        GPUCamImgOperator.luoGLBaseView = luoGLCameraView;
        initView();


        //options
//        //optimization mode for video
//        XJGArSdkApi.XJGARSDKSetOptimizationMode(0);
        //optimization mode for video using asychronized thread
        XJGArSdkApi.XJGARSDKSetOptimizationMode(2);
        //show sticker papers
//        XJGArSdkApi.XJGARSDKSetShowStickerPapers(true);
        XJGArSdkApi.XJGARSDKSetShowStickerPapers(false);

        init();

        //=====================================================
        // 美颜初始化
        //=====================================================
        sharedPreferences = getSharedPreferences("Acitivity", Context.MODE_PRIVATE); //私有数据
        // 获取保存在本地的美颜参数
//        hongRun = sharedPreferences.getInt("hongrun",0);
        meiBai = sharedPreferences.getInt("meibai",0);
        moPi = sharedPreferences.getInt("mopi",0);
//        shouLian = sharedPreferences.getInt("shoulian",0);
//        daYan = sharedPreferences.getInt("dayan",0);
//        vFilter = sharedPreferences.getString("filter","");
//        tieZhi = sharedPreferences.getString("tiezhi","");

        // 设置美颜参数
        XJGArSdkApi.XJGARSDKSetRedFaceParam(0);
        XJGArSdkApi.XJGARSDKSetWhiteSkinParam(meiBai);
        XJGArSdkApi.XJGARSDKSetSkinSmoothParam(moPi);
        XJGArSdkApi.XJGARSDKSetThinChinParam(0);
        XJGArSdkApi.XJGARSDKSetBigEyeParam(0);
//        if(vFilter.isEmpty() == false) {
//            XJGArSdkApi.XJGARSDKChangeFilter(vFilter);
//        }
//        if(tieZhi.isEmpty() == false) {
//            String stickerPaperdir = XJGArSdkApi.getPrivateResDataDir(getApplicationContext());
//            stickerPaperdir = stickerPaperdir +"/StickerPapers/"+ tieZhi;
//            ZIP.unzipAStickPaperPackages(stickerPaperdir);
//            XJGArSdkApi.XJGARSDKSetShowStickerPapers(true);
//            XJGArSdkApi.XJGARSDKChangeStickpaper(tieZhi);
//        }

        // 设置美颜控件
//        mRedFaceSeek.setProgress(hongRun);
        mSkinWihtenSeek.setProgress(meiBai);
        mSkinSmoothSeek.setProgress(moPi);
//        mFaceSurgeryFaceShapeSeek.setProgress(shouLian);
//        mFaceSurgeryBigEyeSeek.setProgress(daYan);
    }

    private void initView(){
        mFilterLayout = (LinearLayout)findViewById(R.id.layout_filter);

        mFaceSurgeryLayout = (LinearLayout)findViewById(R.id.layout_facesurgery);
        mFaceSurgeryFaceShapeSeek = (SeekBar)findViewById(R.id.faceShapeValueBar);
        mFaceSurgeryFaceShapeSeek.setProgress(20);
        mFaceSurgeryBigEyeSeek = (SeekBar)findViewById(R.id.bigeyeValueBar);
        mFaceSurgeryBigEyeSeek.setProgress(50);

        mSkinSmoothSeek = (SeekBar)findViewById(R.id.skinSmoothValueBar);
        mSkinSmoothSeek.setProgress(100);
        mSkinWihtenSeek = (SeekBar)findViewById(R.id.skinWhitenValueBar);
        mSkinWihtenSeek.setProgress(20);
        mRedFaceSeek = (SeekBar)findViewById(R.id.redFaceValueBar);
        mRedFaceSeek.setProgress(80);
//        XJGArSdkApi.XJGARSDKSetSkinSmoothParam(100);
//        XJGArSdkApi.XJGARSDKSetWhiteSkinParam(20);
//        XJGArSdkApi.XJGARSDKSetRedFaceParam(80);
        mFaceSurgeryFaceShapeSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public int value;
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                value = i;
//                shouLian = i;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int strength = value;//(int)(value*(float)1.0/100);
                XJGArSdkApi.XJGARSDKSetThinChinParam(strength);
            }
        });
        mFaceSurgeryBigEyeSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public int value;
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                value = i;
//                daYan = i;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int strength = value;//(int)(value*(float)1.0/100);
                XJGArSdkApi.XJGARSDKSetBigEyeParam(strength);
            }
        });
        mSkinSmoothSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public int value;
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                value = i;
                moPi = i;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int level = value;//(int)(value/18);
                XJGArSdkApi.XJGARSDKSetSkinSmoothParam(level);
//                GPUCamImgOperator.setBeautyLevel(level);
            }
        });

        mSkinWihtenSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public int value;
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                value = i;
                meiBai = i;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int level = value;//(int)(value/18);
                XJGArSdkApi.XJGARSDKSetWhiteSkinParam(level);
//                GPUCamImgOperator.setBeautyLevel(level);
            }
        });
        mRedFaceSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public int value;
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                value = i;
//                hongRun = i;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int level = value;//(int)(value/18);
                XJGArSdkApi.XJGARSDKSetRedFaceParam(level);
//                GPUCamImgOperator.setBeautyLevel(level);
            }
        });

        mFilterListView = (RecyclerView) findViewById(R.id.filter_listView);

        btn_shutter = (ImageView)findViewById(R.id.btn_camera_shutter);
        btn_mode = (ImageView)findViewById(R.id.btn_camera_mode);

        findViewById(R.id.btn_camera_filter).setOnClickListener(btn_listener);
        findViewById(R.id.btn_camera_closefilter).setOnClickListener(btn_listener);
        findViewById(R.id.btn_camera_shutter).setOnClickListener(btn_listener);
        findViewById(R.id.btn_camera_switch).setOnClickListener(btn_listener);
        findViewById(R.id.btn_camera_mode).setOnClickListener(btn_listener);
        findViewById(R.id.btn_camera_beauty).setOnClickListener(btn_listener);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mFilterListView.setLayoutManager(linearLayoutManager);

        mAdapter = new FilterRecyclerViewAdapter(this, types);
        mFilterListView.setAdapter(mAdapter);
        mAdapter.setOnFilterChangeListener(onFilterChangeListener);

        animator = ObjectAnimator.ofFloat(btn_shutter,"rotation",0,360);
        animator.setDuration(500);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        Point screenSize = new Point();
        getWindowManager().getDefaultDisplay().getSize(screenSize);
        LuoGLCameraView cameraView = (LuoGLCameraView)findViewById(R.id.glsurfaceview_camera);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) cameraView.getLayoutParams();
        params.width = screenSize.x;
        params.height = screenSize.y;//screenSize.x * 4 / 3;
        cameraView.setLayoutParams(params);



        mMenuView= (RecyclerView)findViewById(R.id.mMenuView);
        mStickerData=new ArrayList<>();
        mMenuView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        mStickerAdapter=new MenuAdapter(this,mStickerData);
        mStickerAdapter.setOnClickListener(new ClickUtils.OnClickListener() {
            @Override
            public void onClick(View v, int type, int pos, int child) {
                MenuBean m=mStickerData.get(pos);
                String name=m.name;
                String path = m.path;
                if (name.equals("无")) {
                    XJGArSdkApi.XJGARSDKSetShowStickerPapers(false);
//                    tieZhi = "";
                    mStickerAdapter.checkPos=pos;
                    v.setSelected(true);
//                }else if(name.equals("测试2")){
//
//                    mStickerAdapter.checkPos=pos;
//                    v.setSelected(true);
                }else{
                    String stickerPaperdir = XJGArSdkApi.getPrivateResDataDir(getApplicationContext());
                    stickerPaperdir = stickerPaperdir +"/StickerPapers/"+ path;
                    ZIP.unzipAStickPaperPackages(stickerPaperdir);

                    XJGArSdkApi.XJGARSDKSetShowStickerPapers(true);
                    XJGArSdkApi.XJGARSDKChangeStickpaper(path);
//                    tieZhi = path;
                    mStickerAdapter.checkPos=pos;
                    v.setSelected(true);
                }
                mStickerAdapter.notifyDataSetChanged();
            }
        });
        mMenuView.setAdapter(mStickerAdapter);
        initEffectMenu();
    }




    //初始化特效按钮菜单
    protected void initEffectMenu() {
//        "stpaper900224"     ,"草莓猫"
//        "stpaper900639"     ,"米奇老鼠"
//        "angel"             ,"天使"
//        "caishen"           ,"财神爷"
//        "cangou"            ,"罐头狗"
//        "daxiongmao"        ,"大熊猫"
//        "diving"            ,"潜水镜"
//        "flowermustach"     ,"花胡子"
//        "huahuan"           ,"花环"
//        "huangyamotuo"      ,"黄鸭摩托"
//        "hunli"             ,"婚礼"
//        "leisi"             ,"蕾丝"
//        "lufei"             ,"路飞"
//        "lvhua"             ,"鹿花"
//        "mengtu"            ,"梦兔"
//        "rabbit"            ,"大白兔"
//        "shumeng"           ,"萌狗"
//        "strawberry"        ,"草莓"
//        "xuezunv"           ,"血族女"

//

        MenuBean bean=new MenuBean();
        bean.name="无";
        bean.path="";
        mStickerData.add(bean);

        bean=new MenuBean();
        bean.name="天使";
        bean.path="angel";
        mStickerData.add(bean);

        bean=new MenuBean();
        bean.name="财神爷";
        bean.path="caishen";
        mStickerData.add(bean);

        bean=new MenuBean();
        bean.name="罐头狗";
        bean.path="cangou";
        mStickerData.add(bean);

//        bean=new MenuBean();
//        bean.name="大熊猫";
//        bean.path="daxiongmao";
//        mStickerData.add(bean);

        bean=new MenuBean();
        bean.name="潜水镜";
        bean.path="diving";
        mStickerData.add(bean);

//        bean=new MenuBean();
//        bean.name="花胡子";
//        bean.path="flowermustach";
//        mStickerData.add(bean);

        bean=new MenuBean();
        bean.name="花环";
        bean.path="huahuan";
        mStickerData.add(bean);
//
//        bean=new MenuBean();
//        bean.name="黄鸭摩托";
//        bean.path="huangyamotuo";
//        mStickerData.add(bean);
//
//        bean=new MenuBean();
//        bean.name="婚礼";
//        bean.path="hunli";
//        mStickerData.add(bean);

        bean=new MenuBean();
        bean.name="蕾丝";
        bean.path="leisi";
        mStickerData.add(bean);

        bean=new MenuBean();
        bean.name="路飞";
        bean.path="lufei";
        mStickerData.add(bean);

        bean=new MenuBean();
        bean.name="鹿花";
        bean.path="lvhua";
        mStickerData.add(bean);

        bean=new MenuBean();
        bean.name="梦兔";
        bean.path="mengtu";
        mStickerData.add(bean);

//        bean=new MenuBean();
//        bean.name="大白兔";
//        bean.path="rabbit";
//        mStickerData.add(bean);
//
//        bean=new MenuBean();
//        bean.name="萌狗";
//        bean.path="shumeng";
//        mStickerData.add(bean);
//
//        bean=new MenuBean();
//        bean.name="草莓";
//        bean.path="strawberry";
//        mStickerData.add(bean);

        bean=new MenuBean();
        bean.name="血族女";
        bean.path="xuezunv";
        mStickerData.add(bean);


        bean=new MenuBean();
        bean.name=" 西瓜猫";
        bean.path="stpaper900224";
        mStickerData.add(bean);

//        bean=new MenuBean();
//        bean.name="米奇老鼠";
//        bean.path="stpaper900639";
//        mStickerData.add(bean);

        mStickerAdapter.notifyDataSetChanged();
    }

    private FilterRecyclerViewAdapter.onFilterChangeListener onFilterChangeListener = new FilterRecyclerViewAdapter.onFilterChangeListener(){

        @Override
        public void onFilterChanged(com.net.yuesejiaoyou.redirect.ResolverB.interface4.xjg.GPUCamImgOperator.GPUImgFilterType filterType) {
//            GPUCamImgOperator.setFilter(filterType);
            String filterName = FilterTypeHelper.FilterType2FilterName(filterType);
            XJGArSdkApi.XJGARSDKChangeFilter(filterName);
//            vFilter = filterName;
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {

        subPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length != 1 || grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if(mode == MODE_PIC)
                takePhoto();
            else
                takeVideo();
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }


    }

    static boolean bShowFaceSurgery = false;
    static boolean bShowImgFilters = false;
    private View.OnClickListener btn_listener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            int buttonId = v.getId();
            if( buttonId == R.id.btn_camera_mode) {
                switchMode();
            }
            else if (buttonId == R.id.btn_camera_shutter) {
//                if (PermissionChecker.checkSelfPermission(MeiyanActivity.this,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                        == PackageManager.PERMISSION_DENIED) {
//                    ActivityCompat.requestPermissions(MeiyanActivity.this, new String[] {
//                                    Manifest.permission.WRITE_EXTERNAL_STORAGE },
//                            v.getId());
//                } else {
//                    if(mode == MODE_PIC)
//                        takePhoto();
//                    else
//                        takeVideo();
//                }

//                sharedPreferences.edit().putInt("hongrun", hongRun).commit();
                sharedPreferences.edit().putInt("meibai", meiBai).commit();
                sharedPreferences.edit().putInt("mopi", moPi).commit();
//                sharedPreferences.edit().putInt("shoulian", shouLian).commit();
//                sharedPreferences.edit().putInt("dayan", daYan).commit();
//                sharedPreferences.edit().putString("vfilter", vFilter).commit();
//                sharedPreferences.edit().putString("tiezhi", tieZhi).commit();
                finish();
            }
            else if (buttonId == R.id.btn_camera_filter) {
                bShowImgFilters = !bShowImgFilters;
                if(bShowImgFilters)
                    showFilters();
                else
                    hideFilters();
            }
            else if (buttonId == R.id.btn_camera_switch) {
                GPUCamImgOperator.switchCamera();
            }
            else if (buttonId == R.id.btn_camera_beauty) {
                bShowFaceSurgery = ! bShowFaceSurgery;
                if(bShowFaceSurgery)
                    showFaceSurgery();
                else
                    hideFaceSurgery();
            }
            else if (buttonId ==  R.id.btn_camera_closefilter) {
                if(bShowImgFilters) {
                    hideFilters();
                    bShowImgFilters = false;
                }
            }
        }
    };

    private void switchMode(){
        if(mode == MODE_PIC){
            mode = MODE_VIDEO;
            btn_mode.setImageResource(R.drawable.icon_camera);
        }else{
            mode = MODE_PIC;
            btn_mode.setImageResource(R.drawable.icon_video);
        }
    }

    private void takePhoto(){
        GPUCamImgOperator.savePicture();
    }

    private void takeVideo(){
        if(isRecording) {
            animator.end();
            GPUCamImgOperator.stopRecord();
        }else {
            animator.start();
            GPUCamImgOperator.startRecord();
        }
        isRecording = !isRecording;
    }

    //显示面部整形
    private void showFaceSurgery()
    {
        ObjectAnimator animator = ObjectAnimator.ofFloat(mFaceSurgeryLayout, "translationY", mFaceSurgeryLayout.getHeight(), 0);
        animator.setDuration(200);
        animator.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                //findViewById(R.id.btn_camera_shutter).setClickable(false);
                mFaceSurgeryLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }
        });
        animator.start();

    }
    //隐藏面部整形
    private void hideFaceSurgery()
    {
        ObjectAnimator animator = ObjectAnimator.ofFloat(mFaceSurgeryLayout, "translationY", 0 ,  mFaceSurgeryLayout.getHeight());
        animator.setDuration(200);
        animator.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                // TODO Auto-generated method stub
                mFaceSurgeryLayout.setVisibility(View.INVISIBLE);
                //findViewById(R.id.btn_camera_shutter).setClickable(true);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                // TODO Auto-generated method stub
                mFaceSurgeryLayout.setVisibility(View.INVISIBLE);
                //findViewById(R.id.btn_camera_shutter).setClickable(true);
            }
        });
        animator.start();

    }

    private void showFilters(){
        ObjectAnimator animator = ObjectAnimator.ofFloat(mFilterLayout, "translationY", mFilterLayout.getHeight(), 0);
        animator.setDuration(200);
        animator.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                //findViewById(R.id.btn_camera_shutter).setClickable(false);
                mFilterLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }
        });
        animator.start();
    }

    private void hideFilters(){
        ObjectAnimator animator = ObjectAnimator.ofFloat(mFilterLayout, "translationY", 0 ,  mFilterLayout.getHeight());
        animator.setDuration(200);
        animator.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                // TODO Auto-generated method stub
                mFilterLayout.setVisibility(View.INVISIBLE);
                //findViewById(R.id.btn_camera_shutter).setClickable(true);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                // TODO Auto-generated method stub
                mFilterLayout.setVisibility(View.INVISIBLE);
                //findViewById(R.id.btn_camera_shutter).setClickable(true);
            }
        });
        animator.start();
    }

    //==================================================================================================
    //==================================================================================================
    //==================================================================================================
    //==================================================================================================
    //==================================================================================================
    //==================================================================================================
    private static final String LOG_TAG = MeiyanActivity.class.getSimpleName();

    private static final boolean DBG = true;

    private static final int PERMISSION_REQ_ID_RECORD_AUDIO = 22;
    private static final int PERMISSION_REQ_ID_CAMERA = PERMISSION_REQ_ID_RECORD_AUDIO + 1;

    //private CustomizedCameraRenderer mCustomizedCameraRenderer; // Tutorial Step 3
    private LuoGLCameraView mCustomizedCameraRenderer;

    private RtcEngine mRtcEngine;// Tutorial Step 1
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
        public void onUserOffline(int uid, int reason) { // Tutorial Step 7
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    onRemoteUserLeft();
                }
            });
        }
    };

    private void init() {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_video_chat_view);

        if (checkSelfPermission(Manifest.permission.RECORD_AUDIO, PERMISSION_REQ_ID_RECORD_AUDIO) && checkSelfPermission(Manifest.permission.CAMERA, PERMISSION_REQ_ID_CAMERA)) {
            initAgoraEngineAndJoinChannel();
        }
    }

    private void initAgoraEngineAndJoinChannel() {
        initializeAgoraEngine();     // Tutorial Step 1
        setupVideoProfile();         // Tutorial Step 2
        setupLocalVideo(getApplicationContext()); // Tutorial Step 3
        //joinChannel();
    }

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

    public void subPermissionsResult(int requestCode,
                                     @NonNull String permissions[], @NonNull int[] grantResults) {
        Log.i(LOG_TAG, "onRequestPermissionsResult " + grantResults[0] + " " + requestCode);

        switch (requestCode) {
            case PERMISSION_REQ_ID_RECORD_AUDIO: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkSelfPermission(Manifest.permission.CAMERA, PERMISSION_REQ_ID_CAMERA);
                } else {
                    showLongToast("No permission for " + Manifest.permission.RECORD_AUDIO);
                    finish();
                }
                break;
            }
            case PERMISSION_REQ_ID_CAMERA: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initAgoraEngineAndJoinChannel();
                } else {
                    showLongToast("No permission for " + Manifest.permission.CAMERA);
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

        leaveChannel();
        RtcEngine.destroy();

//        if (mCustomizedCameraRenderer != null) {
//            mCustomizedCameraRenderer.onDestroy();
//            mCustomizedCameraRenderer = null;
//        }

        mRtcEngine = null;
    }

    // Tutorial Step 8
    public void onLocalAudioMuteClicked(View view) {
        ImageView iv = (ImageView) view;
        if (iv.isSelected()) {
            iv.setSelected(false);
            iv.clearColorFilter();
        } else {
            iv.setSelected(true);
            iv.setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
        }

        mRtcEngine.muteLocalAudioStream(iv.isSelected());
    }

    // Tutorial Step 6
    public void onEncCallClicked(View view) {
        finish();
    }

    // Tutorial Step 1
    private void initializeAgoraEngine() {
        try {
            mRtcEngine = RtcEngine.create(getBaseContext(), getString(R.string.agora_app_id), mRtcEventHandler);
        } catch (Exception e) {
            Log.e(LOG_TAG, Log.getStackTraceString(e));

            throw new RuntimeException("NEED TO check rtc sdk init fatal error\n" + Log.getStackTraceString(e));
        }
        mRtcEngine.setChannelProfile(Constants.CHANNEL_PROFILE_LIVE_BROADCASTING);
    }

    // Tutorial Step 2
    private void setupVideoProfile() {
        mRtcEngine.enableVideo();

        if (mRtcEngine.isTextureEncodeSupported()) {
            mRtcEngine.setExternalVideoSource(true, true, true);
        } else {
            throw new RuntimeException("Can not work on device do not supporting texture" + mRtcEngine.isTextureEncodeSupported());
        }
        mRtcEngine.setVideoProfile(Constants.VIDEO_PROFILE_480P, true);
    }

    private volatile boolean mJoined = false;

    // Tutorial Step 3
    private LuoGLCameraView setupLocalVideo(Context ctx) {
        //FrameLayout container = (FrameLayout) findViewById(R.id.local_video_view_container);
        //CustomizedCameraRenderer surfaceV = new CustomizedCameraRenderer(ctx);

        //mCustomizedCameraRenderer = GPUCamImgOperator.luoGLBaseView;
        mCustomizedCameraRenderer.setOnFrameAvailableHandler(new LuoGLCameraView.OnFrameAvailableListener() {
            @Override
            public void onFrameAvailable(int texture, EGLContext eglContext, int rotation) {
                AgoraVideoFrame vf = new AgoraVideoFrame();
                vf.format = AgoraVideoFrame.FORMAT_TEXTURE_2D;
                vf.timeStamp = System.currentTimeMillis();
                vf.stride = 1080;
                vf.height = 1920;
                vf.textureID = texture;
                vf.syncMode = true;
                vf.eglContext11 = eglContext;
                vf.transform = new float[]{
                        1.0f, 0.0f, 0.0f, 0.0f,
                        0.0f, 1.0f, 0.0f, 0.0f,
                        0.0f, 0.0f, 1.0f, 0.0f,
                        0.0f, 0.0f, 0.0f, 1.0f
                };

                boolean result = mRtcEngine.pushExternalVideoFrame(vf);
                if (DBG) {
                    Log.d(LOG_TAG, "onFrameAvailable " + eglContext + " " + rotation + " " + texture + " " + result);
                }

            }
        });

        mCustomizedCameraRenderer.setOnEGLContextHandler(new LuoGLCameraView.OnEGLContextListener() {
            @Override
            public void onEGLContextReady(EGLContext eglContext) {
                Log.d(LOG_TAG, "onEGLContextReady " + eglContext + " " + mJoined);

                if (!mJoined) {
                    joinChannel(); // Tutorial Step 4
                    mJoined = true;
                }
            }
        });

//        surfaceV.setZOrderMediaOverlay(true);

        //container.addView(surfaceV);
        return mCustomizedCameraRenderer;
    }

    // Tutorial Step 4
    private void joinChannel() {
        mRtcEngine.setClientRole(Constants.CLIENT_ROLE_BROADCASTER);    //01107, null);
        mRtcEngine.joinChannel(null, "123abc", "Extra Optional Data", 0); // if you do not specify the uid, we will generate the uid for you
    }

    // Tutorial Step 5
    private void setupRemoteVideo(int uid) {
//        FrameLayout container = (FrameLayout) findViewById(R.id.remote_video_view_container);

//        if (container.getChildCount() >= 1) {
//            return;
//        }

        SurfaceView surfaceView = RtcEngine.CreateRendererView(getBaseContext());
//        container.addView(surfaceView);
        mRtcEngine.setupRemoteVideo(new VideoCanvas(surfaceView, VideoCanvas.RENDER_MODE_ADAPTIVE, uid));
        surfaceView.setTag(uid); // for mark purpose
//        View tipMsg = findViewById(R.id.quick_tips_when_use_agora_sdk); // optional UI
//        tipMsg.setVisibility(View.GONE);
    }

    // Tutorial Step 6
    private void leaveChannel() {
        mRtcEngine.leaveChannel();
    }

    // Tutorial Step 7
    private void onRemoteUserLeft() {
//        FrameLayout container = (FrameLayout) findViewById(R.id.remote_video_view_container);
//        container.removeAllViews();

//        View tipMsg = findViewById(R.id.quick_tips_when_use_agora_sdk); // optional UI
//        tipMsg.setVisibility(View.VISIBLE);
    }


    public void onLocalViewHidden(View view) {
//        ImageView iv = (ImageView) view;
//        if (iv.isSelected()) {
//            iv.setSelected(false);
//            iv.clearColorFilter();
//            mCustomizedCameraRenderer.setViewHiddenStatus(false);
//        } else {
//            iv.setSelected(true);
//            iv.setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
//            mCustomizedCameraRenderer.setViewHiddenStatus(true);
//        }
    }
}
