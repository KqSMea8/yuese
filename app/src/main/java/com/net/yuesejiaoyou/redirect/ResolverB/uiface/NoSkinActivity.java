package com.net.yuesejiaoyou.redirect.ResolverB.uiface;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import com.net.yuesejiaoyou.R;

import com.alivc.player.AliyunErrorCode;
import com.aliyun.vodplayer.downloader.AliyunDownloadManager;
import com.aliyun.vodplayer.downloader.AliyunDownloadMediaInfo;
import com.aliyun.vodplayer.media.AliyunLocalSource;
import com.aliyun.vodplayer.media.AliyunPlayAuth;
import com.aliyun.vodplayer.media.AliyunVidSource;
import com.aliyun.vodplayer.media.AliyunVodPlayer;
import com.aliyun.vodplayer.media.IAliyunVodPlayer;
import com.aliyun.vodplayer.utils.VcPlayerLog;
import com.aliyun.vodplayerview.utils.ScreenUtils;
import static com.aliyun.vodplayer.media.IAliyunVodPlayer.PlayerState.Completed;
import static com.aliyun.vodplayer.media.IAliyunVodPlayer.PlayerState.Idle;
import static com.aliyun.vodplayer.media.IAliyunVodPlayer.PlayerState.Stopped;

import com.net.yuesejiaoyou.redirect.ResolverB.interface4.util.Formatter;

/***********************************************
 * 定义视频播放页面的基本控件
 * @
 **********************************************/
public class NoSkinActivity extends Activity {

    private Map<String,String> qualityList = new HashMap<>();
    private SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss.SS");
    private List<String> mQualities = new ArrayList<>();
    private List<Button> qualityIds = new ArrayList<>();
    private List<String> logStrs = new ArrayList<>();
    public static boolean sAutoPlay = false;
    public boolean mAutoPlay = false;
    private boolean mMute= false;
    private int mScalingMode = 1; //fill
    private float speed = 1.0f;
    private boolean replay = false;
    private boolean inSeek = false;
    private boolean isCompleted = false;
    private String mVid = null;
    private String mAuthinfo = null;

    private SurfaceView surfaceView;
    private static final String TAG = NoSkinActivity.class.getSimpleName();
    /*private Button prepareBtn;*/
    private Button playBtn;
    private Button pauseBtn;
    /*private Button releaseBtn;
    private Button changeQualityBtn;*/
    private Button replayBtn;
    private Button stopBtn;
    /*private Button downBtn;*/
    /*private Button downStopBtn;*/
    private RadioGroup autoPlayGroup;
    private RadioButton autoPlayOnBtn;
    private RadioButton autoPlayOffBtn;
    private RadioGroup muteGroup;
    private RadioButton muteOnBtn;
    private RadioButton muteOffBtn;
    private RadioGroup scaleModeGroup;
    private RadioButton scaleModeFit;
    private RadioButton scaleModeFill;
    private RadioGroup speedGroup;
    private RadioButton speed10;
    private RadioButton speed125;
    private RadioButton speed15;
    private RadioButton speed20;

    private AliyunVodPlayer aliyunVodPlayer;
    private Handler handler = new Handler();
    private AliyunPlayAuth mPlayAuth = null;
    private AliyunVidSource mVidSource = null;
    private AliyunLocalSource mLocalSource = null;


    private RelativeLayout rl;
    private LinearLayout qualityLayout;
    private AliyunDownloadMediaInfo downloadInfo;
    private AliyunDownloadManager aliyunDownloadManager;
    private TextView positionTxt;
    //定义持续播放时间
    private TextView durationTxt;
    private SeekBar progressBar;
    //定义亮度
    private SeekBar brightnessBar;
    private SeekBar volumeBar;
    private TextView videoWidthTxt;
    private TextView videoHeightTxt;


    /* prepareBtn.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           aliyunVodPlayer.prepareAsync();
       }
     });*/

    /************************************************
     *
     * @param item
     * @return
     ************************************************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_log, menu);
        return super.onCreateOptionsMenu(menu);
    }


    /************************************************
     *
     * @param item
     * @return
     ************************************************/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.log) {

            LayoutInflater inflater= LayoutInflater.from(this);
            View view=inflater.inflate(R.layout.view_log, null);

            TextView textview=(TextView)view.findViewById(R.id.log);
            if(aliyunVodPlayer != null) {
                for(String log: logStrs) {
                    textview.append("     "+log+"\n");
                }
            }
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle(R.string.player_log);
            alertDialog.setView(view);
            alertDialog.setPositiveButton(R.string.ok,null);
            AlertDialog alert = alertDialog.create();
            alert.show();
        }
        return true;
    }

    /************************
     *
     * @param savedInstanceState
     ***********************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noskin);

        rl = (RelativeLayout) findViewById(R.id.rl);
        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);

        /*prepareBtn = (Button) findViewById(R.id.prepare);*/
        playBtn = (Button) findViewById(R.id.play);
        stopBtn = (Button) findViewById(R.id.stop);
        pauseBtn = (Button) findViewById(R.id.pause);
        /*releaseBtn = (Button) findViewById(R.id.release);*/
        /*changeQualityBtn = (Button) findViewById(R.id.change_quality);*/
        replayBtn = (Button) findViewById(R.id.replay);
        /*downBtn = (Button) findViewById(R.id.download);*/
        /*downStopBtn = (Button) findViewById(R.id.downloadStop);*/

        qualityLayout = (LinearLayout) findViewById(R.id.quality);

        autoPlayGroup = (RadioGroup) findViewById(R.id.autoPlay);
        autoPlayOnBtn = (RadioButton) findViewById(R.id.autoPlayON);
        autoPlayOffBtn = (RadioButton) findViewById(R.id.autoPlayOff);

        if(sAutoPlay) {
            autoPlayOnBtn.setChecked(true);
        } else {
            autoPlayOffBtn.setChecked(true);
        }

        //切换是否自动播放
        /////////////////////////////////////////////////////
        autoPlayGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
             @Override
             public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                 if(checkedId==autoPlayOnBtn.getId())
                 {
                     sAutoPlay = true;
                 }
                 else if(checkedId==autoPlayOffBtn.getId())
                 {
                     sAutoPlay = false;
                 }
             }
         });

        muteGroup = (RadioGroup) findViewById(R.id.mute);
        muteOnBtn = (RadioButton) findViewById(R.id.muteOn);
        muteOffBtn = (RadioButton) findViewById(R.id.muteOff);

        muteOffBtn.setChecked(true);

        /////////////////////////////////////////////////////
        muteGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if(checkedId==muteOnBtn.getId())
                {
                    mMute = true;
                    if(aliyunVodPlayer != null) {
                        aliyunVodPlayer.setMuteMode(mMute);
                    }
                    volumeBar.setProgress(0);
                }
                else if(checkedId==muteOffBtn.getId())
                {
                    mMute = false;
                    if(aliyunVodPlayer != null) {
                        aliyunVodPlayer.setMuteMode(mMute);
                    }
                    volumeBar.setProgress(aliyunVodPlayer.getVolume());
                }
            }
        });

        scaleModeGroup = (RadioGroup) findViewById(R.id.scalingMode);
        scaleModeFit = (RadioButton) findViewById(R.id.fit);
        scaleModeFill = (RadioButton) findViewById(R.id.fill);

        scaleModeFit.setChecked(true);

        /////////////////////////////////////////////////////
        scaleModeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if(checkedId==scaleModeFit.getId())
                {
                    mScalingMode = 0;
                    if(aliyunVodPlayer != null) {
                        aliyunVodPlayer.setVideoScalingMode(IAliyunVodPlayer.VideoScalingMode.VIDEO_SCALING_MODE_SCALE_TO_FIT);
                    }
                }
                else if(checkedId==scaleModeFill.getId())
                {
                    mScalingMode = 1;
                    if(aliyunVodPlayer != null) {
                        aliyunVodPlayer.setVideoScalingMode(IAliyunVodPlayer.VideoScalingMode.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
                    }
                }
            }
        });


        speedGroup = (RadioGroup) findViewById(R.id.speedgroup);
        speed10 = (RadioButton) findViewById(R.id.speed10);
        speed125 = (RadioButton) findViewById(R.id.speed125);
        speed15 = (RadioButton) findViewById(R.id.speed15);
        speed20 = (RadioButton) findViewById(R.id.speed20);
        speed10.setChecked(true);
        speedGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedId) {
                if (checkedId == R.id.speed125) {
                    speed = 1.25f;
                } else if (checkedId == R.id.speed10) {
                    speed = 1.0f;
                } else if (checkedId == R.id.speed15) {
                    speed = 1.5f;
                } else if (checkedId == R.id.speed20) {
                    speed = 2.0f;
                }

                if (aliyunVodPlayer != null) {
                    aliyunVodPlayer.setPlaySpeed(speed);
                }
            }
        });
        positionTxt = (TextView) findViewById(R.id.currentPosition);
        durationTxt = (TextView) findViewById(R.id.totalDuration);
        progressBar = (SeekBar) findViewById(R.id.progress);
        brightnessBar = (SeekBar) findViewById(R.id.brightnessProgress);
        volumeBar = (SeekBar) findViewById(R.id.volumeProgress);

        videoWidthTxt = (TextView) findViewById(R.id.width);
        videoHeightTxt = (TextView) findViewById(R.id.height);

        /////////////////////////////////////////////////////
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayerState = aliyunVodPlayer.getPlayerState();
                if(mPlayerState == Idle || mPlayerState == Stopped ||
                        mPlayerState == Completed) {
                    if(mVidSource != null) {
                        aliyunVodPlayer.prepareAsync(mVidSource);
                    } else if(mPlayAuth != null) {
                        aliyunVodPlayer.prepareAsync(mPlayAuth);
                    } else if(mLocalSource != null) {
                        aliyunVodPlayer.prepareAsync(mLocalSource);
                    }
                    mAutoPlay = true;
                } else {
                    inSeek = false;
                    aliyunVodPlayer.start();
                    pauseBtn.setText(R.string.pause_button);
                }
                if(mMute) {
                    aliyunVodPlayer.setMuteMode(mMute);
                }
                brightnessBar.setProgress(aliyunVodPlayer.getScreenBrightness());
                logStrs.add(format.format(new Date()) + getString(R.string.log_strart_play));
                volumeBar.setProgress(aliyunVodPlayer.getVolume());
            }
        });
        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(aliyunVodPlayer != null) {
                    aliyunVodPlayer.stop();
                }
            }
        });

        /////////////////////////////////////////////////////
        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayerState = aliyunVodPlayer.getPlayerState();
                if (mPlayerState == IAliyunVodPlayer.PlayerState.Started) {
                    aliyunVodPlayer.pause();
                    pauseBtn.setText(R.string.resume_button);
                } else if (mPlayerState == IAliyunVodPlayer.PlayerState.Paused) {
                    aliyunVodPlayer.resume();
                    pauseBtn.setText(R.string.pause_button);
                }
            }
        });
        /*releaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aliyunVodPlayer.stop();
                aliyunVodPlayer.release();
            }
        });
        changeQualityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aliyunVodPlayer.changeQuality(IAliyunVodPlayer.QualityValue.QUALITY_FLUENT);
            }
        });*/
        replayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isCompleted = false;
                inSeek = false;
                aliyunVodPlayer.replay();
                showVideoProgressInfo();
            }
        });

        ///////////////////////////////////////////////////////
        progressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser
                        && aliyunVodPlayer != null
                       ) {
                    aliyunVodPlayer.seekTo(progress);
                    logStrs.add(format.format(new Date()) + getString(R.string.log_seek_start));
                    if(isCompleted) {
                        //播放完成了，不能seek了
                        inSeek = false;
                    }else{
                        inSeek = true;
                    }
                }
            }

            /******************************************
             *
             * @param seekBar
             *******************************************/
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            /******************************************
             *
             * @param seekBar
             *******************************************/
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //////////////////////////////////////////////////////
        brightnessBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            /*********************************************
             *
             * @param seekBar
             * @param progress
             * @param fromUser
             *********************************************/
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if (fromUser && aliyunVodPlayer != null) {
                    Log.d("lfj1009", "progress = " + progress);
                    aliyunVodPlayer.setScreenBrightness(progress);
                }
            }

            /********************************************
             *
             * @param seekBar
             *********************************************/
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            /********************************************
             *
             * @param seekBar
             **********************************/
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        ///////////////////////////////////////////////
        volumeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            /**********************************************
             *
             * @param seekBar
             * @param progress
             * @param fromUser
             *********************************************/
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if (fromUser && aliyunVodPlayer != null) {
                    aliyunVodPlayer.setVolume(progress);
                    muteOffBtn.setChecked(true);
                }
            }

            /***********************************************
             *
             * @param seekBar
             **********************************************/
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            /*********************************************
             *
             * @param seekBar
             *********************************************/
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        ///////////////////////////////////////////
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            /**************************************
             *
             * @param holder
             ************************************/
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                Log.d(TAG, "surfaceCreated");
                aliyunVodPlayer.setDisplay(holder);
            }

            /**************************************
             *
             * @param holder
             * @param format
             * @param width
             * @param height
             ***************************************/
            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                Log.d(TAG, "surfaceChanged");
                aliyunVodPlayer.surfaceChanged();
            }

            /**************************************
             *
             * @param holder
             ***************************************/
            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                Log.d(TAG, "surfaceDestroyed");
            }
        });

        initVodPlayer();
        setPlaySource();
        logStrs.add(format.format(new Date())+ getString(R.string.log_start_get_data));

        if(mVidSource != null) {
            aliyunVodPlayer.prepareAsync(mVidSource);
        } else if(mPlayAuth != null) {
            aliyunVodPlayer.prepareAsync(mPlayAuth);
        } else if(mLocalSource != null) {
            aliyunVodPlayer.prepareAsync(mLocalSource);
        }


        aliyunDownloadManager = AliyunDownloadManager.getInstance(NoSkinActivity.this.getApplicationContext());

        qualityList.put(IAliyunVodPlayer.QualityValue.QUALITY_FLUENT, getString(R.string.alivc_fd_definition));
        qualityList.put(IAliyunVodPlayer.QualityValue.QUALITY_LOW, getString(R.string.alivc_ld_definition));
        qualityList.put(IAliyunVodPlayer.QualityValue.QUALITY_STAND, getString(R.string.alivc_sd_definition));
        qualityList.put(IAliyunVodPlayer.QualityValue.QUALITY_HIGH, getString(R.string.alivc_hd_definition));
        qualityList.put(IAliyunVodPlayer.QualityValue.QUALITY_2K, getString(R.string.alivc_k2_definition));
        qualityList.put(IAliyunVodPlayer.QualityValue.QUALITY_4K, getString(R.string.alivc_k4_definition));
        qualityList.put(IAliyunVodPlayer.QualityValue.QUALITY_ORIGINAL, getString(R.string.alivc_od_definition));


    }

    /********************************
     * 初始化播放器
     * @
     ********************************/
    private void initVodPlayer() {
        aliyunVodPlayer = new AliyunVodPlayer(this);
        String sdDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/test_save_cache";
        aliyunVodPlayer.setPlayingCache(true,sdDir,60 * 60 /*时长, s */,300 /*大小，MB*/);
        aliyunVodPlayer.setOnPreparedListener(new IAliyunVodPlayer.OnPreparedListener() {
            @Override
            public void onPrepared() {

                    aliyunVodPlayer.start();    // 自动播放
                    //Toast.makeText(NoSkinActivity.this.getApplicationContext(), R.string.toast_prepare_success, Toast.LENGTH_SHORT).show();
                    logStrs.add(format.format(new Date())  + getString(R.string.log_prepare_success));
                    //准备成功之后可以调用start方法开始播放
                    inSeek = false;
                    showVideoProgressInfo();
                    showVideoSizeInfo();

                    qualityLayout.removeAllViews();

                    if(mLocalSource == null) {
                        qualityIds.clear();
                        mQualities = aliyunVodPlayer.getMediaInfo().getQualities();
                        String current = aliyunVodPlayer.getCurrentQuality();
                        //update quality buttons
                        for (String quality : mQualities) {
                            Button btn = new Button(getBaseContext());
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            layoutParams.weight = 1;
                            layoutParams.setMargins(10, 0, 10, 0);
                            btn.setText(qualityList.get(quality));
                            btn.setTextSize(14);
                            btn.setTag(quality);
                            btn.setTextColor(Color.rgb(49, 50, 51));
                            btn.setBackgroundColor(Color.rgb(207, 207, 207));
                            btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    changeQuality((Button) v);
                                }
                            });
                            if (current.equals(quality)) {
                                btn.setTextColor(Color.rgb(255, 255, 255));
                                btn.setBackgroundColor(Color.rgb(3, 106, 150));
                            }
                            qualityIds.add(btn);
                            qualityLayout.addView(btn, layoutParams);

                        }
                    }
                    if(sAutoPlay || mAutoPlay) {
                        aliyunVodPlayer.start();
                        pauseBtn.setText(R.string.pause_button);
                        logStrs.add(format.format(new Date()) + getString(R.string.log_strart_play));
                        if(mMute) {
                            aliyunVodPlayer.setMuteMode(mMute);
                        }
                        brightnessBar.setProgress(aliyunVodPlayer.getScreenBrightness());
                        volumeBar.setProgress(aliyunVodPlayer.getVolume());
                    }
                    mAutoPlay = false;
                }
        });

        //////////////////////////////////////////
        aliyunVodPlayer.setOnFirstFrameStartListener(new IAliyunVodPlayer.OnFirstFrameStartListener() {
            @Override
            public void onFirstFrameStart() {
                Map<String, String> debugInfo = aliyunVodPlayer.getAllDebugInfo();
                long createPts = 0;
                if(debugInfo.get("create_player") != null) {
                    String time = debugInfo.get("create_player");
                    createPts = (long) Double.parseDouble(time);
                    logStrs.add(format.format(new Date(createPts)) + getString(R.string.log_player_create_success));
                }
                if(debugInfo.get("open-url") != null) {
                    String time = debugInfo.get("open-url");
                    long openPts = (long) Double.parseDouble(time) + createPts;
                    logStrs.add(format.format(new Date(openPts)) + getString(R.string.log_open_url_success));
                }
                if(debugInfo.get("find-stream") != null) {
                    String time = debugInfo.get("find-stream");
                    VcPlayerLog.d(TAG + "lfj0914","find-Stream time =" + time + " , createpts = " + createPts);
                    long findPts = (long) Double.parseDouble(time) + createPts;
                    logStrs.add(format.format(new Date(findPts)) + getString(R.string.log_request_stream_success));
                }
                if(debugInfo.get("open-stream") != null) {
                    String time = debugInfo.get("open-stream");
                    VcPlayerLog.d(TAG + "lfj0914","open-Stream time =" + time + " , createpts = " + createPts);
                    long openPts = (long) Double.parseDouble(time) + createPts;
                    logStrs.add(format.format(new Date(openPts)) + getString(R.string.log_start_open_stream));
                }
                logStrs.add(format.format(new Date()) + getString(R.string.log_first_frame_played));
            }
        });

        /////////////////////////////////////////////
        aliyunVodPlayer.setOnErrorListener(new IAliyunVodPlayer.OnErrorListener() {
            @Override
            public void onError(int arg0, int arg1, String msg) {

                if(aliyunVodPlayer != null) {
                    aliyunVodPlayer.stop();
                }

                if(arg0 == AliyunErrorCode.ALIVC_ERR_INVALID_INPUTFILE.getCode()){
                    /*当播放本地报错4003的时候，可能是文件地址不对，也有可能是没有权限。
                    //如果是没有权限导致的，就做一个权限的错误提示。其他还是正常提示：
                    int storagePermissionRet = ContextCompat.checkSelfPermission(NoSkinActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    if(storagePermissionRet != PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(NoSkinActivity.this.getApplicationContext(), getString(R.string.toast_fail_msg) + getString(R.string.toast_no_local_storage_permission), Toast.LENGTH_SHORT).show();
                        return;
                    }*/
                }

                Toast.makeText(NoSkinActivity.this.getApplicationContext(), getString(R.string.toast_fail_msg) + msg, Toast.LENGTH_SHORT).show();
            }
        });
        ///////////////////////////////////////////////
        aliyunVodPlayer.setOnCompletionListener(new IAliyunVodPlayer.OnCompletionListener() {
            @Override
            public void onCompletion() {
                Log.d(TAG, "onCompletion--- ");
                isCompleted = true;
                inSeek = false;
                showVideoProgressInfo();
                stopUpdateTimer();

                //循环播放
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        aliyunVodPlayer.replay();
                    }
                });

            }
        });
        ///////////////////////////////////////////////
        aliyunVodPlayer.setOnSeekCompleteListener(new IAliyunVodPlayer.OnSeekCompleteListener() {
            @Override
            public void onSeekComplete() {
                logStrs.add(format.format(new Date()) + getString(R.string.log_seek_completed));
                inSeek = false;
            }
        });
        ///////////////////////////////////////////////
        aliyunVodPlayer.setOnStoppedListner(new IAliyunVodPlayer.OnStoppedListener() {
            @Override
            public void onStopped() {
                logStrs.add(format.format(new Date()) + getString(R.string.log_play_stopped));
                if(replay) {
                    logStrs.add(format.format(new Date()) + getString(R.string.log_start_get_data));
                    if(mVidSource != null) {
                        aliyunVodPlayer.prepareAsync(mVidSource);
                    } else if(mPlayAuth != null) {
                        aliyunVodPlayer.prepareAsync(mPlayAuth);
                    } else if(mLocalSource != null) {
                        aliyunVodPlayer.prepareAsync(mLocalSource);
                    }
                    mAutoPlay = true;
                }
                replay = false;
                inSeek = false;
            }
        });
        ///////////////////////////////////////////////
        aliyunVodPlayer.setOnBufferingUpdateListener(new IAliyunVodPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(int percent) {
                Log.d(TAG, "onBufferingUpdate--- " + percent);
                updateBufferingProgress(percent);
            }
        });

        ///////////////////////////////////////////////
        // aliyunVodPlayer.setDisplay(surfaceView.getHolder());
        aliyunVodPlayer.setOnChangeQualityListener(new IAliyunVodPlayer.OnChangeQualityListener() {

            /***************************************
             *
             * @param finalQuality
             **********************************/
            @Override
            public void onChangeQualitySuccess(String finalQuality) {
                Log.d(TAG, "onChangeQualitySuccess");
                inSeek = false;
                logStrs.add(format.format(new Date()) + getString(R.string.log_change_quality_success));
                showVideoProgressInfo();
            }

            /************************************
             *
             * @param code
             * @param msg
             **********************************/
            @Override
            public void onChangeQualityFail(int code, String msg) {
                Log.d(TAG, "onChangeQualityFail。。。" + code + " ,  " + msg);
                logStrs.add(format.format(new Date()) + getString(R.string.log_change_quality_fail));
            }
        });
        aliyunVodPlayer.enableNativeLog();


    }

    /*************************************
     *
     * @param v
     **********************************/
    private void changeQuality(Button v) {
        if (aliyunVodPlayer != null &&
                (aliyunVodPlayer.getPlayerState() == IAliyunVodPlayer.PlayerState.Paused
                        || aliyunVodPlayer.getPlayerState() == IAliyunVodPlayer.PlayerState.Started ||
                aliyunVodPlayer.getPlayerState() == IAliyunVodPlayer.PlayerState.Error)) {
            Button fouces = v;
            fouces.setTextColor(Color.rgb(255, 255, 255));
            fouces.setBackgroundColor(Color.rgb(3, 106, 150));
            logStrs.add(format.format(new Date()) + getString(R.string.log_change_quality) + fouces.getText());
            //refreshPlaySource();
            aliyunVodPlayer.changeQuality((String) fouces.getTag());
            for (Button otherBtn : qualityIds) {
                if (!otherBtn.equals(fouces)) {
                    otherBtn.setTextColor(Color.rgb(49, 50, 51));
                    otherBtn.setBackgroundColor(getResources().getColor(R.color.alivc_info_text_duration));
                }
            }

        }
    }


    /*  private void refreshPlaySource()
    {
        mAuthinfo = null;
       new Thread(new Runnable() {

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
           public void run() {
               mAuthinfo = PlayAuthUtil.getPlayAuth(NoSkinActivity.this,mVid);
            }
       }).start();

       int tryCount = 0;
       while (mAuthinfo == null || mAuthinfo.isEmpty()) {
           try {
               Thread.sleep(1000);
                tryCount++;
                if(tryCount > 5) {
                    Toast.makeText(NoSkinActivity.this.getApplicationContext(), R.string.request_authinfo_fail, Toast.LENGTH_LONG).show();
                    break;
                }
           } catch (InterruptedException e) {
                e.printStackTrace();
           }
       }

        AliyunPlayAuth.AliyunPlayAuthBuilder aliyunPlayAuthBuilder = new AliyunPlayAuth.AliyunPlayAuthBuilder();
       aliyunPlayAuthBuilder.setVid(mVid);
       aliyunPlayAuthBuilder.setPlayAuth(mAuthinfo);
       aliyunPlayAuthBuilder.setQuality(aliyunVodPlayer.getCurrentQuality());
       //aliyunVodPlayer.setAuthInfo(aliyunPlayAuthBuilder.build());
       mPlayAuth = aliyunPlayAuthBuilder.build();
   }*/

    /***************************************
     *设置播放源
     ***************************************/
    private void setPlaySource() {

        String type = getIntent().getStringExtra("type");
         if ("authInfo".equals(type)) {
            //auth方式

            //NOTE： 注意过期时间。特别是重播的时候，可能已经过期。所以重播的时候最好重新请求一次服务器。
            mVid = getIntent().getStringExtra("vid").toString();
            String authInfo = getIntent().getStringExtra("authinfo");


            AliyunPlayAuth.AliyunPlayAuthBuilder aliyunPlayAuthBuilder = new AliyunPlayAuth.AliyunPlayAuthBuilder();
            aliyunPlayAuthBuilder.setVid(mVid);
            aliyunPlayAuthBuilder.setPlayAuth(authInfo);
            aliyunPlayAuthBuilder.setQuality(IAliyunVodPlayer.QualityValue.QUALITY_LOW);
            //aliyunVodPlayer.setAuthInfo(aliyunPlayAuthBuilder.build());
            mPlayAuth = aliyunPlayAuthBuilder.build();
        } else if ("localSource".equals(type)) {
            //本地播放
            String url = getIntent().getStringExtra("url");
            AliyunLocalSource.AliyunLocalSourceBuilder asb = new AliyunLocalSource.AliyunLocalSourceBuilder();
            asb.setSource(url);
            //aliyunVodPlayer.setLocalSource(asb.build());
            mLocalSource = asb.build();
        }  else if("vidSource".equals(type)) {
             mVid = getIntent().getStringExtra("vid").toString();
             mVidSource = new AliyunVidSource();
             String authInfo = getIntent().getStringExtra("authinfo");
             String token = getIntent().getStringExtra("token");
             String akid = getIntent().getStringExtra("akid");
             String aks = getIntent().getStringExtra("aks");
             mVidSource.setVid(mVid);
             mVidSource.setAuthInfo(authInfo);
             mVidSource.setStsToken(token);
             mVidSource.setAcKey(aks);
             mVidSource.setAcId(akid);
             mVidSource.setDomainRegion("cn-shanghai");
             mVidSource.setHlsUriToken("hlsuritokenaaaa");
             //mVidSource.setDomain("vod-test5.cn-shanghai.aliyuncs.com");
            // mVidSource.setDomain("jiyoujia.com");
        }
    }

    /******************************
     *设置视频的宽度和高度
     ******************************/
    private void showVideoSizeInfo() {
        videoWidthTxt.setText(getString(R.string.video_width) + aliyunVodPlayer.getVideoWidth() + " , ");
        videoHeightTxt.setText(getString(R.string.video_height) + aliyunVodPlayer.getVideoHeight() + "   ");
    }

    /*******************************
     *更新加载进度
     * @param percent
     ********************************/
    private void updateBufferingProgress(int percent) {
        int duration = (int) aliyunVodPlayer.getDuration();
        int secondaryProgress = (int) (duration * percent * 1.0f / 100);
        Log.d(TAG, "lfj0918 duration = " + duration + " , buffer percent = " +percent + " , secondaryProgress =" + secondaryProgress);
        progressBar.setSecondaryProgress(secondaryProgress);
    }

    /*********************************
     *
     **********************************/
    private void showVideoProgressInfo() {
        if((aliyunVodPlayer.getPlayerState().equals(AliyunVodPlayer.PlayerState.Started)
                || aliyunVodPlayer.getPlayerState().equals(AliyunVodPlayer.PlayerState.Replay) ||
         aliyunVodPlayer.getPlayerState().equals(AliyunVodPlayer.PlayerState.Completed))
                && !inSeek) {
            int curPosition = (int) aliyunVodPlayer.getCurrentPosition();
            positionTxt.setText(Formatter.formatTime(curPosition));
            int duration = (int) aliyunVodPlayer.getDuration();
            durationTxt.setText(Formatter.formatTime(duration));
            Log.d(TAG, "lfj0918 duration = " + duration + " , curPosition = " + curPosition );
            progressBar.setMax(duration);
            progressBar.setProgress(curPosition);
        }

        startUpdateTimer();
    }

    /*******************************
     *
     ********************************/
    private void startUpdateTimer() {
        Log.d(TAG, "startUpdateTimer--- ");
        progressUpdateTimer.removeMessages(0);
        progressUpdateTimer.sendEmptyMessageDelayed(0, 1000);
    }

    /*******************************
     *
     ********************************/
    private void stopUpdateTimer() {
        progressUpdateTimer.removeMessages(0);
    }

    private Handler progressUpdateTimer = new Handler() {
        /*******************************
         *
         * @param msg
         *******************************/
        @Override
        public void handleMessage(Message msg) {

            showVideoProgressInfo();
        }
    };

    /*************************
     *
     *******************************/
    @Override
    protected void onResume() {
        super.onResume();
        //保存播放器的状态，供resume恢复使用。
        resumePlayerState();
    }

    /**************************
     *
     *******************************/
    @Override
    protected void onStop() {
        super.onStop();
        //onStop中记录下来的状态，在这里恢复使用
        savePlayerState();
    }


    //用来记录前后台切换时的状态，以供恢复。
    private IAliyunVodPlayer.PlayerState mPlayerState;

    /******************************
     *
     *******************************/
    private void resumePlayerState() {
        if (mPlayerState == IAliyunVodPlayer.PlayerState.Paused) {
            aliyunVodPlayer.pause();
        } else if (mPlayerState == IAliyunVodPlayer.PlayerState.Started) {
            aliyunVodPlayer.start();
            pauseBtn.setText(R.string.pause_button);
        }
    }

    /********************************
     *
     */
    private void savePlayerState() {
        mPlayerState = aliyunVodPlayer.getPlayerState();
        if (aliyunVodPlayer.isPlaying()) {
            //然后再暂停播放器
            aliyunVodPlayer.pause();
        }
    }

    /*******************************
     *
     ******************************/
    @Override
    protected void onDestroy() {
        aliyunVodPlayer.stop();
        aliyunVodPlayer.release();
        stopUpdateTimer();
        progressUpdateTimer = null;

        //CleanLeakUtils.fixInputMethodManagerLeak(this);
        super.onDestroy();
    }

    /****************************************
     *
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {                //转为竖屏了。
            //显示状态栏
            this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            surfaceView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);

            //设置view的布局，宽高之类
            ViewGroup.LayoutParams surfaceViewLayoutParams = surfaceView.getLayoutParams();
            surfaceViewLayoutParams.height = (int) (ScreenUtils.getWidth(this) * 9.0f / 16);
            surfaceViewLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;

        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {                //转到横屏了。
            //隐藏状态栏
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            surfaceView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN);
            //设置view的布局，宽高
            ViewGroup.LayoutParams surfaceViewLayoutParams = surfaceView.getLayoutParams();
            surfaceViewLayoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
            surfaceViewLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;

        }
    }
}
