package com.net.yuesejiaoyou.redirect.ResolverB.uiface;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

//import com.aliyun.common.utils.StorageUtils;
//import com.aliyun.struct.common.CropKey;
//import com.aliyun.struct.common.ScaleMode;
//import com.aliyun.struct.common.VideoQuality;
//import com.aliyun.struct.encoder.VideoCodecs;
//import com.aliyun.struct.recorder.CameraType;
//import com.aliyun.struct.recorder.FlashType;
//import com.aliyun.struct.snap.AliyunSnapVideoParam;
import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.redirect.ResolverB.interface3.UsersThread_01158B;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.BaseActivity;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.activity.UploadVideoActivity;
//import com.net.yuesejiaoyou.redirect.ResolverB.interface4.videoeditor.Common;
//import com.net.yuesejiaoyou.redirect.ResolverB.interface4.videoimport.MediaActivity;
//import com.net.yuesejiaoyou.redirect.ResolverB.interface4.videoimport.MediaActivity_FF;
//import com.net.yuesejiaoyou.redirect.ResolverB.interface4.videorecorder.AliyunVideoRecorder;
//import com.net.yuesejiaoyou.redirect.ResolverB.interface4.videorecorder.AliyunVideoRecorder_FF;
//import com.example.vliao.R;
//import com.example.vliao.interface3.UsersThread_01158;
//import com.example.vliao.util.Util;
//import com.example.vliao.videoeditor.Common;
//import com.example.vliao.videoimport.MediaActivity;
//import com.example.vliao.videoimport.MediaActivity_FF;
//import com.example.vliao.videorecorder.AliyunVideoRecorder;
//import com.example.vliao.videorecorder.AliyunVideoRecorder_FF;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by Administrator on 2018/3/13.
 */

public class UploadVideo_01158 extends BaseActivity implements View.OnClickListener {
    private ImageView back;
    private ImageView freevideo;
    private ImageView rewardvideo;
    private TextView freevideonumber;
    private TextView rewardvideonumber_1;
    private String[] permissions = {Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE};
    private String freevideoNumber = "";
    private String rewardvideoNumber = "";
    private PopupWindow popupWindow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
        freevideo = (ImageView)findViewById(R.id.freevideo);
        freevideo.setOnClickListener(this);
        rewardvideo = (ImageView)findViewById(R.id.rewardvideo);
        rewardvideo.setOnClickListener(this);
        freevideonumber = (TextView)findViewById(R.id.freevideonumber);
        rewardvideonumber_1 = (TextView) findViewById(R.id.rewardvideonumber);
        getFreeVideo();
        getRewardVideo();

        //接收到广播，从新加载数据
        reciever reviever = new reciever();
        IntentFilter filter1 = new IntentFilter("freevideoNumber");
        registerReceiver(reviever, filter1);

        //接收到广播，从新加载数据
        reciever reviever2 = new reciever();
        IntentFilter filter2 = new IntentFilter("rewardvideoNumber");
        registerReceiver(reviever2, filter2);

    }

    @Override
    protected int getContentView() {
        return R.layout.uploadvideo;
    }

    //获取免费视频数量
    public void getFreeVideo(){
        String mode = "getfreevideo";
        String [] params = {"", Util.userid};
        UsersThread_01158B u = new UsersThread_01158B(mode,params,requestHandler);
        Thread t = new Thread(u.runnable);
        t.start();
    }

    //获取付费视频数量
    public void getRewardVideo(){
        String mode = "getrewardvideo";
        String [] params = {"", Util.userid};
        UsersThread_01158B u = new UsersThread_01158B(mode,params,requestHandler);
        Thread t = new Thread(u.runnable);
        t.start();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.back:
                finish();
                break;
            case R.id.freevideo:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                for(String permission: permissions) {
                    // 检查该权限是否已经获取
                    int i = ContextCompat.checkSelfPermission(UploadVideo_01158.this, permission);
                    // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
                    if (i != PackageManager.PERMISSION_GRANTED) {
                        // 如果没有授予该权限，就去提示用户请求
                        ActivityCompat.requestPermissions(UploadVideo_01158.this, permissions, 321);
                        return;
                    }
                }
            }
				showPopupspWindow_shipin(freevideo,0);

               // MediaActivity.startImport(UploadVideo_01158.this);
                break;
            case R.id.rewardvideo:
//                recordVideo();
                if(Integer.parseInt(rewardvideoNumber)<Integer.parseInt(freevideoNumber)){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                        for(String permission: permissions) {
                            // 检查该权限是否已经获取
                            int i = ContextCompat.checkSelfPermission(UploadVideo_01158.this, permission);
                            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
                            if (i != PackageManager.PERMISSION_GRANTED) {
                                //如果没有授予该权限，就去提示用户请求
                                ActivityCompat.requestPermissions(UploadVideo_01158.this, permissions, 321);
                                return;
                            }
                        }
                    }
                    showPopupspWindow_shipin(rewardvideo,1);
                   // MediaActivity_FF.startImport(UploadVideo_01158.this);
                }else{
                //Please upload a new free video--请先上传一个免费视频
                    Toast.makeText(UploadVideo_01158.this,"请先上传一个免费视频",Toast.LENGTH_SHORT).show(); }
                break;
        }
    }

    private Handler requestHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    break;
                case 100:
                    freevideoNumber = (String)msg.obj;
                    if(freevideoNumber != null || !freevideoNumber.equals("")){
                        freevideonumber.setText(freevideoNumber);
                    }else{

                    }
                    break;
                case 110:
                    rewardvideoNumber = (String)msg.obj;
                    if(rewardvideoNumber !=null || !rewardvideoNumber.equals("")){
                        rewardvideonumber_1.setText(rewardvideoNumber);
                    }else{

                    }
                    break;
            }
        }
    };

    //-------------------------------------
    // 视频录制相关
    private String[] permissions2 = {Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE};
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 321) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                } else {

                }
            }
        }
    }


    private class reciever extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            getFreeVideo();
            getRewardVideo();
        }
    }

    private void recordVideo() {
        // 动态申请摄像头权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            for(String permission: permissions2) {
                // 检查该权限是否已经获取
                int i = ContextCompat.checkSelfPermission(this, permission);
                // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
                if (i != PackageManager.PERMISSION_GRANTED) {
                    // 如果没有授予该权限，就去提示用户请求
                    ActivityCompat.requestPermissions(this, permissions2, 321);
                    return;
                }
            }
        }
//        String path = StorageUtils.getCacheDirectory(this).getAbsolutePath() + File.separator + "AliyunEditorDemo"+File.separator;
//        Log.e("TT","path: "+path);
//        String[] eff_dirs = new String[]{
//                null,
//                path + "filter/chihuang",
//                path + "filter/fentao",
//                path + "filter/hailan",
//                path + "filter/hongrun",
//                path + "filter/huibai",
//                path + "filter/jingdian",
//                path + "filter/maicha",
//                path + "filter/nonglie",
//                path + "filter/rourou",
//                path + "filter/shanyao",
//                path + "filter/xianguo",
//                path + "filter/xueli",
//                path + "filter/yangguang",
//                path + "filter/youya",
//                path + "filter/zhaoyang"
//        };
//
//        AliyunSnapVideoParam recordParam = new AliyunSnapVideoParam.Builder()
//                .setResulutionMode(AliyunSnapVideoParam.RESOLUTION_720P)
//                .setRatioMode(AliyunSnapVideoParam.RATIO_MODE_9_16)
//                .setRecordMode(AliyunSnapVideoParam.RECORD_MODE_AUTO)
//                .setFilterList(eff_dirs)
//                .setBeautyLevel(80)
//                .setBeautyStatus(true)
//                .setCameraType(CameraType.FRONT)
//                .setFlashType(FlashType.ON)
//                .setNeedClip(true)
//                .setMaxDuration(30000)
//                .setMinDuration(2000)
//                .setVideQuality(VideoQuality.SSD)
//                .setGop(5)
//                .setVideoBitrate(0)
//                /**
//                 * 裁剪参数
//                 */
//                .setMinVideoDuration(4000)
//                .setMaxVideoDuration(29 * 1000)
//                .setMinCropDuration(3000)
//                .setFrameRate(25)
//                .setCropMode(ScaleMode.PS)
//                .build();
//        AliyunVideoRecorder.startRecord(this,recordParam);
    }
	private void recordVideo1() {
		// 动态申请摄像头权限
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

			for(String permission: permissions2) {
				// 检查该权限是否已经获取
				int i = ContextCompat.checkSelfPermission(this, permission);
				// 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
				if (i != PackageManager.PERMISSION_GRANTED) {
					// 如果没有授予该权限，就去提示用户请求
					ActivityCompat.requestPermissions(this, permissions2, 321);
					return;
				}
			}
		}
//		String path = StorageUtils.getCacheDirectory(this).getAbsolutePath() + File.separator + "AliyunEditorDemo"+File.separator;
//		Log.e("TT","path: "+path);
//		String[] eff_dirs = new String[]{
//				null,
//				path + "filter/chihuang",
//				path + "filter/fentao",
//				path + "filter/hailan",
//				path + "filter/hongrun",
//				path + "filter/huibai",
//				path + "filter/jingdian",
//				path + "filter/maicha",
//				path + "filter/nonglie",
//				path + "filter/rourou",
//				path + "filter/shanyao",
//				path + "filter/xianguo",
//				path + "filter/xueli",
//				path + "filter/yangguang",
//				path + "filter/youya",
//				path + "filter/zhaoyang"
//		};
//
//		AliyunSnapVideoParam recordParam = new AliyunSnapVideoParam.Builder()
//				.setResulutionMode(AliyunSnapVideoParam.RESOLUTION_720P)
//				.setRatioMode(AliyunSnapVideoParam.RATIO_MODE_9_16)
//				.setRecordMode(AliyunSnapVideoParam.RECORD_MODE_AUTO)
//				.setFilterList(eff_dirs)
//				.setBeautyLevel(80)
//				.setBeautyStatus(true)
//				.setCameraType(CameraType.FRONT)
//				.setFlashType(FlashType.ON)
//				.setNeedClip(true)
//				.setMaxDuration(30000)
//				.setMinDuration(2000)
//				.setVideQuality(VideoQuality.SSD)
//				.setGop(5)
//				.setVideoBitrate(0)
//				/**
//				 * 裁剪参数
//				 */
//				.setMinVideoDuration(4000)
//				.setMaxVideoDuration(29 * 1000)
//				.setMinCropDuration(3000)
//				.setFrameRate(25)
//				.setCropMode(ScaleMode.PS)
//				.build();
		//01107 AliyunVideoRecorder_FF.startRecord(this,recordParam);
	}
	public void showPopupspWindow_shipin(View parent, final int i) {
		// 加载布局
		LayoutInflater inflater = LayoutInflater.from(UploadVideo_01158.this);
		View layout = inflater.inflate(R.layout.pop_video_01160, null);

		TextView xuanze,quxiao;

		xuanze = (TextView)layout.findViewById(R.id.xuanze);
		xuanze.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				//从手机相册选择
				//MediaActivity_01160.startImport(UploadVideo_01158.this);
				if(i==0){
					//01107 MediaActivity.startImport(UploadVideo_01158.this);
                    videoImport();
				}else{
					//01107 MediaActivity_FF.startImport(UploadVideo_01158.this);
                    videoImportPay();
				}
				popupWindow.dismiss();
			}
		});

		quxiao = (TextView)layout.findViewById(R.id.quxiao);
		quxiao.setOnClickListener(new View.OnClickListener() {
			//拍摄
			@Override
			public void onClick(View arg0) {
				if (i == 0) {
					//recordVideo();
                    videoRecord(20);
				}else{
					//recordVideo1();
                    videoRecordPay(20);
				}
				popupWindow.dismiss();
			}
		});


		popupWindow = new PopupWindow(layout, ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT, true);
		// 控制键盘是否可以获得焦点
		popupWindow.setFocusable(true);
		WindowManager.LayoutParams lp = UploadVideo_01158.this.getWindow().getAttributes();
		lp.alpha = 0.5f;
		UploadVideo_01158.this.getWindow().setAttributes(lp);
		popupWindow.setBackgroundDrawable(new BitmapDrawable(null, ""));
		//popupWindow.showAsDropDown(parent, 0, 0);
		popupWindow.showAtLocation(parent, Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
		popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
			public void onDismiss() {
				WindowManager.LayoutParams lp = UploadVideo_01158.this.getWindow().getAttributes();
				lp.alpha = 1f;
				UploadVideo_01158.this.getWindow().setAttributes(lp);
			}
		});
	}

	//==============================================================================================
    //==============================================================================================
    //==============================================================================================
    //==============================================================================================
    private void videoRecord(int limit) {

//        initAssetPath();
//
//        int min = 3000;
//        int max = 30000;
//        int gop = 5;
//        int bitrate = 0;
//        if(minDurationEt.getText() != null && !minDurationEt.getText().toString().isEmpty()){
//            try {
//                min = Integer.parseInt(minDurationEt.getText().toString()) * 1000;
//            }catch (Exception e){
//                Log.e("ERROR","input error");
//            }
//        }
//        if(maxDurationEt.getText() != null && !maxDurationEt.getText().toString().isEmpty()){
//            try {
//                max = Integer.parseInt(maxDurationEt.getText().toString()) * 1000;
//            }catch (Exception e){
//                Log.e("ERROR","input error");
//            }
//        }
//        if(gopEt.getText() != null && !gopEt.getText().toString().isEmpty()){
//            try {
//                gop = Integer.parseInt(gopEt.getText().toString());
//            }catch (Exception e){
//                Log.e("ERROR","input error");
//            }
//        }
//        if(!TextUtils.isEmpty(mBitrateEdit.getText())){
//            try{
//                bitrate = Integer.parseInt(mBitrateEdit.getText().toString());
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }
//        AliyunSnapVideoParam recordParam = new AliyunSnapVideoParam.Builder()
//                .setResolutionMode(AliyunSnapVideoParam.RESOLUTION_720P)    // 分辨率
//                .setRatioMode(AliyunSnapVideoParam.RATIO_MODE_9_16)     // 比例
//                .setRecordMode(AliyunSnapVideoParam.RECORD_MODE_AUTO)
//                .setFilterList(mEffDirs)
//                .setBeautyLevel(80)
//                .setBeautyStatus(true)
//                .setCameraType(CameraType.FRONT)
//                .setFlashType(FlashType.ON)
//                .setNeedClip(true)
//                .setMaxDuration(max)
//                .setMinDuration(min)
//                .setVideoQuality(VideoQuality.SSD)   // 视频质量
//                .setGop(gop)
//                .setVideoBitrate(bitrate)
//                .setVideoCodec(VideoCodecs.H264_HARDWARE)   // 视频编码
//                /**
//                 * 裁剪参数
//                 */
//                .setMinVideoDuration(4000)
//                .setMaxVideoDuration(29 * 1000)
//                .setMinCropDuration(3000)
//                .setFrameRate(25)
//                .setCropMode(ScaleMode.PS)
//                .build();
//        AliyunVideoRecorder.startRecord(this,recordParam);

        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_VIDEO_CAPTURE);
        Uri fileUri = null;
        try {
            fileUri = Uri.fromFile(createMediaFile()); // create a file to save the video
        } catch (IOException e) {
            e.printStackTrace();
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);  // set the image file name
        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, limit);   // 录像限制时长(秒)
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1); // set the video image quality to high
        // start the Video Capture Intent
        startActivityForResult(intent, FREE_REQUEST_RECORD_VIDEO);
    }

    private File createMediaFile() throws IOException {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES), "Zuanshi");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "VID_" + timeStamp;
        String suffix = ".mp4";
        File mediaFile = new File(mediaStorageDir + File.separator + imageFileName + suffix);
        return mediaFile;
    }

    private void videoRecordPay(int limit) {

//        initAssetPath();
//
//        int min = 3000;
//        int max = 30000;
//        int gop = 5;
//        int bitrate = 0;
//        if(minDurationEt.getText() != null && !minDurationEt.getText().toString().isEmpty()){
//            try {
//                min = Integer.parseInt(minDurationEt.getText().toString()) * 1000;
//            }catch (Exception e){
//                Log.e("ERROR","input error");
//            }
//        }
//        if(maxDurationEt.getText() != null && !maxDurationEt.getText().toString().isEmpty()){
//            try {
//                max = Integer.parseInt(maxDurationEt.getText().toString()) * 1000;
//            }catch (Exception e){
//                Log.e("ERROR","input error");
//            }
//        }
//        if(gopEt.getText() != null && !gopEt.getText().toString().isEmpty()){
//            try {
//                gop = Integer.parseInt(gopEt.getText().toString());
//            }catch (Exception e){
//                Log.e("ERROR","input error");
//            }
//        }
//        if(!TextUtils.isEmpty(mBitrateEdit.getText())){
//            try{
//                bitrate = Integer.parseInt(mBitrateEdit.getText().toString());
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }
//        AliyunSnapVideoParam recordParam = new AliyunSnapVideoParam.Builder()
//                .setResolutionMode(AliyunSnapVideoParam.RESOLUTION_720P)    // 分辨率
//                .setRatioMode(AliyunSnapVideoParam.RATIO_MODE_9_16)     // 比例
//                .setRecordMode(AliyunSnapVideoParam.RECORD_MODE_AUTO)
//                .setFilterList(mEffDirs)
//                .setBeautyLevel(80)
//                .setBeautyStatus(true)
//                .setCameraType(CameraType.FRONT)
//                .setFlashType(FlashType.ON)
//                .setNeedClip(true)
//                .setMaxDuration(max)
//                .setMinDuration(min)
//                .setVideoQuality(VideoQuality.SSD)   // 视频质量
//                .setGop(gop)
//                .setVideoBitrate(bitrate)
//                .setVideoCodec(VideoCodecs.H264_HARDWARE)   // 视频编码
//                /**
//                 * 裁剪参数
//                 */
//                .setMinVideoDuration(4000)
//                .setMaxVideoDuration(29 * 1000)
//                .setMinCropDuration(3000)
//                .setFrameRate(25)
//                .setCropMode(ScaleMode.PS)
//                .build();
//        AliyunVideoRecorder_FF.startRecord(this,recordParam);

        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_VIDEO_CAPTURE);
        Uri fileUri = null;
        try {
            fileUri = Uri.fromFile(createMediaFile()); // create a file to save the video
        } catch (IOException e) {
            e.printStackTrace();
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);  // set the image file name
        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, limit);   // 录像限制时长(秒)
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1); // set the video image quality to high
        // start the Video Capture Intent
        startActivityForResult(intent, AWARD_REQUEST_RECORD_VIDEO);
    }

    private String[] mEffDirs;
//    private void initAssetPath(){
//        String path = StorageUtils.getCacheDirectory(this).getAbsolutePath() + File.separator+ Common.QU_NAME + File.separator;
//        File filter = new File(new File(path), "filter");
//
//        String[] list = filter.list();
//        if(list == null || list.length == 0){
//            return ;
//        }
//        mEffDirs = new String[list.length + 1];
//        mEffDirs[0] = null;
//        for(int i = 0; i < list.length; i++){
//            mEffDirs[i + 1] = filter.getPath() + "/" + list[i];
//        }
////        mEffDirs = new String[]{
////                null,
////                path + "filter/chihuang",
////                path + "filter/fentao",
////                path + "filter/hailan",
////                path + "filter/hongrun",
////                path + "filter/huibai",
////                path + "filter/jingdian",
////                path + "filter/maicha",
////                path + "filter/nonglie",
////                path + "filter/rourou",
////                path + "filter/shanyao",
////                path + "filter/xianguo",
////                path + "filter/xueli",
////                path + "filter/yangguang",
////                path + "filter/youya",
////                path + "filter/zhaoyang",
////                path + "filter/mosaic",
////                path + "filter/blur",
////                path + "filter/bulge",
////                path + "filter/false",
////                path + "filter/gray",
////                path + "filter/haze",
////                path + "filter/invert",
////                path + "filter/miss",
////                path + "filter/pixellate",
////                path + "filter/rgb",
////                path + "filter/sepiatone",
////                path + "filter/threshold",
////                path + "filter/tone",
////                path + "filter/vignette"
////
////        };
//    }

    private void videoImport() {
//        Intent intent = new Intent(this,MediaActivity.class);
//        intent.putExtra(CropKey.VIDEO_RATIO, CropKey.RATIO_MODE_9_16);  // 比例
//        intent.putExtra(CropKey.VIDEO_SCALE, CropKey.SCALE_FILL);        // 画面填充
//        intent.putExtra(CropKey.VIDEO_QUALITY , VideoQuality.SSD);       // 视频质量
//        //String f = frameRateEdit.getText().toString();
//        int frameRate = 25;
////        if(f == null || f.isEmpty()){
////            frameRate = DEFAULT_FRAMR_RATE;
////        }else{
////            frameRate = Integer.parseInt(frameRateEdit.getText().toString());
////        }
//
//        intent.putExtra(CropKey.VIDEO_FRAMERATE,frameRate);
//        //String g = gopEdit.getText().toString();
//        int gop = 125;
////        if(g == null || g.isEmpty()){
////            gop = DEFAULT_GOP;
////        }else{
////            gop = Integer.parseInt(gopEdit.getText().toString());
////        }
//        intent.putExtra(CropKey.VIDEO_GOP,gop);
//        int bitrate = 0;
////        if(!TextUtils.isEmpty(mBitrateEdit.getText())){
////            bitrate = Integer.parseInt(mBitrateEdit.getText().toString());
////        }
//        intent.putExtra(CropKey.VIDEO_BITRATE, bitrate);
//
////            intent.putExtra("width",etWidth.getText().toString());
////            intent.putExtra("height",etHeight.getText().toString());
//        startActivity(intent);

        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, FREE_REQUEST_LOCAL_VIDEO);
    }

    private void videoImportPay() {
//        Intent intent = new Intent(this,MediaActivity_FF.class);
//        intent.putExtra(CropKey.VIDEO_RATIO, CropKey.RATIO_MODE_9_16);  // 比例
//        intent.putExtra(CropKey.VIDEO_SCALE, CropKey.SCALE_FILL);        // 画面填充
//        intent.putExtra(CropKey.VIDEO_QUALITY , VideoQuality.SSD);       // 视频质量
//        //String f = frameRateEdit.getText().toString();
//        int frameRate = 25;
////        if(f == null || f.isEmpty()){
////            frameRate = DEFAULT_FRAMR_RATE;
////        }else{
////            frameRate = Integer.parseInt(frameRateEdit.getText().toString());
////        }
//
//        intent.putExtra(CropKey.VIDEO_FRAMERATE,frameRate);
//        //String g = gopEdit.getText().toString();
//        int gop = 125;
////        if(g == null || g.isEmpty()){
////            gop = DEFAULT_GOP;
////        }else{
////            gop = Integer.parseInt(gopEdit.getText().toString());
////        }
//        intent.putExtra(CropKey.VIDEO_GOP,gop);
//        int bitrate = 0;
////        if(!TextUtils.isEmpty(mBitrateEdit.getText())){
////            bitrate = Integer.parseInt(mBitrateEdit.getText().toString());
////        }
//        intent.putExtra(CropKey.VIDEO_BITRATE, bitrate);
//
////            intent.putExtra("width",etWidth.getText().toString());
////            intent.putExtra("height",etHeight.getText().toString());
//        startActivity(intent);
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, AWARD_REQUEST_LOCAL_VIDEO);
    }

    private int FREE_REQUEST_LOCAL_VIDEO = 101;
    private int FREE_REQUEST_RECORD_VIDEO = 102;
    private int AWARD_REQUEST_LOCAL_VIDEO = 201;
    private int AWARD_REQUEST_RECORD_VIDEO = 202;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1) {
            if(requestCode == FREE_REQUEST_LOCAL_VIDEO || requestCode == AWARD_REQUEST_LOCAL_VIDEO) {
                Uri selectedVideo = data.getData();
                String[] filePathColumn = { MediaStore.Video.Media.DATA };

                Cursor cursor = getContentResolver().query(selectedVideo ,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String videoPath = cursor.getString(columnIndex);
                //Toast.makeText(this, "data: "+videoPath, Toast.LENGTH_SHORT).show();
                cursor.close();

                if(requestCode == FREE_REQUEST_LOCAL_VIDEO) {
                    Intent intent = new Intent(UploadVideo_01158.this, UploadVideoActivity.class);
                    intent.putExtra("file",videoPath);
                    startActivity(intent);
                } else if(requestCode == AWARD_REQUEST_LOCAL_VIDEO) {
                    Intent intent = new Intent(UploadVideo_01158.this, ActivityUploadVideo_FF.class);
                    intent.putExtra("file",videoPath);
                    startActivity(intent);
                }

            } else if(requestCode == FREE_REQUEST_RECORD_VIDEO || requestCode == AWARD_REQUEST_RECORD_VIDEO) {
                // Video captured and saved to fileUri specified in the Intent
                Uri data1 = data.getData();
                // 获取录制视频路径
                String s = data1.toString();
                String purePath = s.replace("file:","");

                if(requestCode == FREE_REQUEST_RECORD_VIDEO) {
                    Intent intent = new Intent(UploadVideo_01158.this, UploadVideoActivity.class);
                    intent.putExtra("file", purePath);
                    startActivity(intent);
                } else if(requestCode == AWARD_REQUEST_RECORD_VIDEO) {
                    Intent intent = new Intent(UploadVideo_01158.this, ActivityUploadVideo_FF.class);
                    intent.putExtra("file", purePath);
                    startActivity(intent);
                }
            }
        }
    }
}
