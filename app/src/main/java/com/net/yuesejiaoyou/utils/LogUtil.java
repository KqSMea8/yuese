package com.net.yuesejiaoyou.utils;

import android.util.Log;

/**
 * 所有的日志必须由LogUtil处理
 *
 * @author pjy
 */
public class LogUtil {
    static boolean isOutLog = true;

    public static void i(String tag, String msg) {
        if (isOutLog) {
            Log.i(tag, msg + "");
        }

    }

    public static void println(String msg) {
        System.out.println(msg);
    }

    public static void e(String msg) {
        if (isOutLog) {
            e("ttt", msg);
        }
    }

    public static void d(String tag, String msg) {
        if (isOutLog) {
            Log.d(tag, msg);
        }

    }

    public static void e(String tag, String msg) {
        if (isOutLog) {
            Log.e(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (isOutLog) {
            Log.w(tag, msg);
        }
    }

    public static void i(String tag, boolean msg) {
        i(tag, String.valueOf(msg));
    }

    public static void i(String tag, int msg) {
        i(tag, String.valueOf(msg));
    }

    public static void i(String tag, long msg) {
        i(tag, String.valueOf(msg));
    }

}
