package com.net.yuesejiaoyou.redirect.ResolverB.uiface;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.YhApplicationA;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;

import com.net.yuesejiaoyou.redirect.ResolverB.interface3.UsersThread_01107B;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.util.DianboUtil;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.util.VideoInfoUtil;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.util.VideoUploadUtil;

import net.sf.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

//import com.king.photo.activity.ImageselActivity2;

/**
 * Created by Administrator on 2017/11/14.
 */

public class ActivityUploadVideo_FF extends Activity {

    private RelativeLayout layWait;
    private EditText editTitle;
    private TextView txtFile;
    private TextView txtMsg;
    private Button btnUpload;
    private String file;
    private String uploadAddress;
    private String uploadAuth;
    private String videoId;
    private ProgressBar progressBar;
    private Handler handler;
    private TextView priceNumber;
    private TextView diyi, dier, disan, disi, diwu, diliu;
    private String price = "8悦币";

    private YhApplicationA application;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadvideo_ff);

        application = (YhApplicationA) getApplication();

        file = getIntent().getStringExtra("file");  // 获取上传文件路径

        layWait = (RelativeLayout) this.findViewById(R.id.lay_wait);
        /*editTitle = (EditText)this.findViewById(R.id.edttitle);*/
        txtFile = (TextView) this.findViewById(R.id.txt_file);
        txtMsg = (TextView) this.findViewById(R.id.txt_msg);
        btnUpload = (Button) this.findViewById(R.id.btn_upload);
        progressBar = (ProgressBar) this.findViewById(R.id.pgb_update);
        priceNumber = (TextView) this.findViewById(R.id.price);

        diyi = (TextView) this.findViewById(R.id.diyi);
        diyi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initTextViewBackgroudColor();
                price = diyi.getText().toString();
                diyi.setBackgroundResource(R.drawable.confirmruanjiao_3);
            }
        });
        dier = (TextView) this.findViewById(R.id.dier);
        dier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initTextViewBackgroudColor();
                price = dier.getText().toString();
                dier.setBackgroundResource(R.drawable.confirmruanjiao_3);
            }
        });
        disan = (TextView) this.findViewById(R.id.disan);
        disan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initTextViewBackgroudColor();
                price = disan.getText().toString();
                disan.setBackgroundResource(R.drawable.confirmruanjiao_3);
            }
        });
        disi = (TextView) this.findViewById(R.id.disi);
        disi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initTextViewBackgroudColor();
                price = disi.getText().toString();
                disi.setBackgroundResource(R.drawable.confirmruanjiao_3);
            }
        });
        diwu = (TextView) this.findViewById(R.id.diwu);
        diwu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initTextViewBackgroudColor();
                price = diwu.getText().toString();
                diwu.setBackgroundResource(R.drawable.confirmruanjiao_3);

            }
        });
        diliu = (TextView) this.findViewById(R.id.diliu);
        diliu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initTextViewBackgroudColor();
                price = diliu.getText().toString();
                diliu.setBackgroundResource(R.drawable.confirmruanjiao_3);

            }
        });

        txtFile.setText(file);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {

                switch (msg.what) {
                    case 1: // 显示进度信息
                        txtMsg.setText((String) msg.obj);
                        break;
                    case 2: // 发起上传文件请求
                        VideoUploadUtil.uploadVideo(ActivityUploadVideo_FF.this, this, file, uploadAddress, uploadAuth);
                        break;
                    case 3: // 上传成功

                        VideoInfoUtil.getVideoInfo(videoId, new VideoInfoUtil.CoverUrlCallback() {

                            @Override
                            public void onGetCoverSuccess(String url) {
                                handler.obtainMessage(8, url).sendToTarget();
                            }

                            @Override
                            public void onGetCoverFail(String reason) {
                                handler.obtainMessage(7).sendToTarget();
                            }
                        });

                        break;
                    case 4: // 上传失败
                        txtMsg.setText("上传失败!");
                        break;
                    case 5: // 上传进度
                        progressBar.setVisibility(View.VISIBLE);
                        progressBar.setProgress((int) msg.obj);
                        break;
                    case 6: // 短视频videoid和缩略图信息更新到数据库
                        String result = (String) msg.obj;
                        Log.v("TT", "addvideo_ff: " + result);
                        LogDetect.send(LogDetect.DataType.specialType, "107ActivityUploadVideo: ", result);
                        if ("success".equals(result)) {
                            Toast.makeText(ActivityUploadVideo_FF.this, "上传成功", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent("rewardvideoNumber");
                            sendBroadcast(intent);

                            application.closeManageActivity_ff();

//                            Intent intent = new Intent();
//                            intent.setClass(ActivityUploadVideo.this,ActivityVideo.class);
//                            startActivity(intent);
                            finish();
                            //Fragment_guangchang_01150.freshList();
                        } else if ("fail".equals(result)) {
                            Toast.makeText(ActivityUploadVideo_FF.this, "上传失败", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 7:    // 获取短视频封面失败
                        Toast.makeText(ActivityUploadVideo_FF.this, "获取视频封面失败", Toast.LENGTH_SHORT).show();
                        break;
                    case 8: // 成功获取短视频封面，更新封面信息和videoid到app服务端
//						String coverUrl = (String)msg.obj;
//						txtMsg.setText("上传完成");
//						String mode = "addvideo";
//
//						String[] paramsMap = {"",videoId,coverUrl};	//"http://ppt1.mingweishipin.com/snapshot/"+videoId+"00001.jpg"};
//						LogDetect.send(LogDetect.DataType.specialType, "107ActivityUploadVideo: ", mode+" "+paramsMap);
//						UsersThread_01107B b = new UsersThread_01107B(mode,paramsMap,this);
//						Thread t = new Thread(b.runnable);
//						t.start();

                        String coverUrl = (String) msg.obj;
                        txtMsg.setText("上传完成");
                        if (price.equals("")) {
                            Toast.makeText(ActivityUploadVideo_FF.this, "请输入打赏金额", Toast.LENGTH_SHORT).show();
                        } else {
                            String a = (String) price.subSequence(0, price.length() - 2);
                            String mode = "addvideo_ff";
//												"对方的id，礼物的id,礼物的数量"//Util.userid,
                            String[] paramsMap = {"", videoId, coverUrl, a};    //"http://ppt1.mingweishipin.com/snapshot/"+videoId+"00001.jpg",a};
                            LogDetect.send(LogDetect.DataType.specialType, "107ActivityUploadVideo: ", mode + " " + paramsMap);
                            UsersThread_01107B b = new UsersThread_01107B(mode, paramsMap, this);
                            Thread t = new Thread(b.runnable);
                            t.start();
                        }
                        break;
                }
            }
        };

        btnUpload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                /*String title = editTitle.getText().toString();*/
                Log.e("YT", "开始上传");
                /*if(title.isEmpty()) {
                    Toast.makeText(ActivityUploadVideo.this,"请输入标题",Toast.LENGTH_SHORT).show();
                    return;
                }*/
                layWait.setVisibility(View.VISIBLE);
                Message.obtain(handler, 1, "获取上传凭证...").sendToTarget();
                DianboUtil.uploadVideo(file, new Callback() {

                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String json = response.body().string();
                        Log.v("TT", "json: " + json);
                        JSONObject jsonObj = JSONObject.fromObject(json);
                        uploadAddress = jsonObj.getString("UploadAddress");
                        videoId = jsonObj.getString("VideoId");
                        uploadAuth = jsonObj.getString("UploadAuth");

                        Message.obtain(handler, 1, "开始上传").sendToTarget();
                        Message.obtain(handler, 2).sendToTarget();
                    }
                });
            }
        });


    }

    public void initTextViewBackgroudColor() {
        diyi.setBackgroundResource(R.drawable.confirmruanjiao_2);
        dier.setBackgroundResource(R.drawable.confirmruanjiao_2);
        disan.setBackgroundResource(R.drawable.confirmruanjiao_2);
        disi.setBackgroundResource(R.drawable.confirmruanjiao_2);
        diwu.setBackgroundResource(R.drawable.confirmruanjiao_2);
        diliu.setBackgroundResource(R.drawable.confirmruanjiao_2);
    }

}
