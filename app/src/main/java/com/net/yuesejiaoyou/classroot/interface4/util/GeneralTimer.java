package com.net.yuesejiaoyou.classroot.interface4.util;

/**
 * Created by Administrator on 2018\7\26 0026.
 */

public class GeneralTimer {

    private int mCurrentCounter = 0;
    private int mMaxCounter;
    private boolean mStopTimer = false;
    private Thread mTimerThread;

    private TimerCallback callback;

    public GeneralTimer(int delayTime) {
        mMaxCounter = delayTime;
    }

    public void start(TimerCallback timerCallback) {

        if(mMaxCounter <= mCurrentCounter) {
            return;
        }

        callback = timerCallback;

        mTimerThread = new Thread(new Runnable() {

            @Override
            public void run() {

                while(mStopTimer == false && mCurrentCounter < mMaxCounter) {

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mCurrentCounter++;
                }

                if(mStopTimer == false && mCurrentCounter >= mMaxCounter) {
                    if(callback != null) {
                        callback.onTimerEnd();
                    }
                }
            }
        });

        mTimerThread.start();
    }

    public void stop() {
        mStopTimer = true;
    }

    public interface TimerCallback {
        public void onTimerEnd();
    }
}
