package com.net.yuesejiaoyou.classroot.interface4.util;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.net.yuesejiaoyou.redirect.ResolverD.interface4.YhApplicationA;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.redirect.ResolverA.interface4.utils.SystemUtils;
import com.net.yuesejiaoyou.activity.MainActivity;
import com.net.yuesejiaoyou.redirect.ResolverB.uiface.MusicUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Timer;
import java.util.TimerTask;


/**循环播放一段无声音频，以提升进程优先级
 *
 * Created by jianddongguo on 2017/7/11.
 * http://blog.csdn.net/andrexpert
 */

public class PlayerMusicService1 extends Service {
    private final static String TAG = "PlayerMusicService1";
    private MediaPlayer mMediaPlayer;
    private int playcount=0;
    private  int count=0;
    private Timer mRunTimer;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if(Contants.DEBUG)
            Log.d(TAG,TAG+"---->onCreate,启动服务");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                MusicUtil.playSound(1,100);
            }
        }).start();
        return START_STICKY;
    }

    private void startPlayMusic(){
        if(mMediaPlayer != null){
            if(Contants.DEBUG)
                Log.d(TAG,"启动后台播放音乐");
            mMediaPlayer.start();
            startRunTimer();
        }




        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                playcount++;
                LogDetect.send(LogDetect.DataType.specialType, "ActivityLogin_01158:--json2 ", "播放次数："+playcount);
                boolean isalive= SystemUtils.isAPPALive(PlayerMusicService1.this,"com.net.yuesejiaoyou.redirect.ResolverD.interface4");

                LogDetect.send(LogDetect.DataType.specialType, "ActivityLogin_01158:--json2 ", "播放次数："+playcount+"---"+isalive);
                if(!isalive){
                    Intent intent = new Intent();
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setClass(PlayerMusicService1.this, MainActivity.class);
                    startActivity(intent);
                    count++;
                    LogDetect.send(LogDetect.DataType.specialType, "ActivityLogin_01158:--json2 ", "main被强制重启："+count);
                }
            }
        });
    }

    private void stopPlayMusic(){
//        if(mMediaPlayer != null){
//            if(Contants.DEBUG)
//                Log.d(TAG,"关闭后台播放音乐");
//            mMediaPlayer.stop();
//            stopRunTimer();
//        }
        MusicUtil.stopPlay();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopPlayMusic();
        if(Contants.DEBUG)
            Log.d(TAG,TAG+"---->onCreate,停止服务");
        // 重启自己
        LogDetect.send(LogDetect.DataType.specialType, "ActivityLogin_01158:--json2 ", "PlayerMusicService重启");
        //Intent intent = new Intent(getApplicationContext(),PlayerMusicService1.class);
        //startService(intent);
    }

    ////////////////////////
    private void startRunTimer() {
        TimerTask mTask = new TimerTask() {
            @Override
            public void run() {
                playcount++;
                try {
                    writeFile("不休眠测试不休眠测试-------------测试");
                    LogDetect.send(LogDetect.DataType.basicType, "不休眠测试不休眠测试", "-------------测试");
                    boolean isalive = SystemUtils.isAPPALive(PlayerMusicService1.this, "com.net.yuesejiaoyou.redirect.ResolverD.interface4");
                    boolean islive=CheckUtil.isClsRunning(PlayerMusicService1.this,"com.net.yuesejiaoyou.redirect.ResolverD.interface4","com.net.yuesejiaoyou.activity.MainActivity");
                    writeFile("不休眠测试不休眠测试-------------播放次数1：" + playcount + "---" + isalive+"----"+islive+"----"+((YhApplicationA)getApplication()).ishave);
                    LogDetect.send(LogDetect.DataType.specialType, "ActivityLogin_01158:--json2 ", "播放次数1：" + playcount + "---" + isalive+"----"+islive+"----"+((YhApplicationA)getApplication()).ishave);
                    //if (!isalive) {
                    //if(playcount==30){
                    if (((YhApplicationA)getApplication()).mainthis==null){
                        writeFile("不休眠测试不休眠测试-------------播放次数1：" + playcount + "---" + isalive+"----"+islive);
                        LogDetect.send(LogDetect.DataType.specialType, "ActivityLogin_01158:--json2 ", "main被强制重启：" + count);
                        Intent intent = new Intent();
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setClass(PlayerMusicService1.this, MainActivity.class);
                        startActivity(intent);
                        count++;
                        LogDetect.send(LogDetect.DataType.specialType, "ActivityLogin_01158:--json2 ", "main被强制重启：" + count);
                    }else{
                        writeFile("main未被销毁");
                        LogDetect.send(LogDetect.DataType.specialType, "ActivityLogin_01158:--json2 ", "main未被销毁" + count);
                    }
                }catch (Exception e){

                    writeFile(getStackInfo(e));
                    Intent intent = new Intent();
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setClass(PlayerMusicService1.this, MainActivity.class);
                    startActivity(intent);
                    count++;
                    //LogDetect.send(LogDetect.DataType.specialType, "ActivityLogin_01158:--json2 ", "main被强制重启：" + count);
                }
            }
        };
        mRunTimer = new Timer();
        // 每隔1s更新一下时间
        mRunTimer.schedule(mTask,1000,10000);
    }
    private void stopRunTimer(){
        if(mRunTimer != null){
            mRunTimer.cancel();
            mRunTimer = null;
        }

    }

    private String getStackInfo(Throwable e) {
        String info;
        Writer writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        e.printStackTrace(pw);
        info = writer.toString();
        pw.close();
        try {
            writer.close();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return info;
    }


    /**
     * 写文件
     * @param string
     */
    public void writeFile(String string) {
//        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/test/";
//        File dir=new File(path);
//        if(!dir.exists()){
//            dir.mkdirs();
//        }
//        String filename="test.txt";
//        FileOutputStream outputStream;
//        try {
//            File text=new File(path+filename);
//            outputStream = new FileOutputStream(text,true);
//            outputStream.write(string.getBytes());
//            outputStream.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }
}
