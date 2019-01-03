package com.net.yuesejiaoyou.redirect.ResolverD.interface4.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.BaseActivity;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.util.DianboUtil;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.util.VideoInfoUtil;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.util.VideoUploadUtil;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.URL;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import net.sf.json.JSONObject;

import java.io.IOException;

import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class UploadVideoActivity extends BaseActivity {

    private RelativeLayout layWait;
    private TextView txtFile;
    private TextView txtMsg;
    private String file;
    private String uploadAddress;
    private String uploadAuth;
    private String videoId;
    private ProgressBar progressBar;
    private Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        file = getIntent().getStringExtra("file");  // 获取上传文件路径

        layWait = (RelativeLayout) this.findViewById(R.id.lay_wait);
        txtFile = (TextView) this.findViewById(R.id.txt_file);
        txtMsg = (TextView) this.findViewById(R.id.txt_msg);
        progressBar = (ProgressBar) this.findViewById(R.id.pgb_update);

        txtFile.setText(file);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {

                switch (msg.what) {
                    case 1: // 显示进度信息
                        txtMsg.setText((String) msg.obj);
                        break;
                    case 2: // 发起上传文件请求
                        VideoUploadUtil.uploadVideo(UploadVideoActivity.this, this, file, uploadAddress, uploadAuth);
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
                    case 7:    // 获取短视频封面失败
                        Toast.makeText(UploadVideoActivity.this, "获取视频封面失败", Toast.LENGTH_SHORT).show();
                        break;
                    case 8: // 成功获取短视频封面，更新封面信息和videoid到app服务端
                        String coverUrl = (String) msg.obj;
                        addVideo(coverUrl);
                        txtMsg.setText("上传完成");

                }
            }
        };

        uploadClick();
    }


    @Override
    protected int getContentView() {
        return R.layout.activity_uploadvideo;
    }

    @OnClick(R.id.btn_upload)
    public void uploadClick() {
        layWait.setVisibility(View.VISIBLE);
        txtMsg.setText("获取上传凭证...");


        DianboUtil.uploadVideo(file, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                JSONObject jsonObj = JSONObject.fromObject(json);
                uploadAddress = jsonObj.getString("UploadAddress");
                videoId = jsonObj.getString("VideoId");
                uploadAuth = jsonObj.getString("UploadAuth");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txtMsg.setText("开始上传");
                        VideoUploadUtil.uploadVideo(UploadVideoActivity.this, handler, file, uploadAddress, uploadAuth);
                    }
                });
            }
        });
    }


    public void addVideo(String coverUrl) {
        OkHttpUtils
                .post(this)
                .url(URL.URL_ADDVIDEO)
                .addParams("param1", Util.userid)
                .addParams("param2", videoId)
                .addParams("param3", coverUrl)
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        String result = JSON.parseObject(response).getString("result");
                        if ("success".equals(result)) {
                            showToast("上传成功");
                            Intent intent = new Intent("freevideoNumber");
                            sendBroadcast(intent);
                            app.closeManageActivity();
                            finish();
                        } else if ("fail".equals(result)) {
                            showToast("上传失败");
                        }
                    }

                });
    }
}
