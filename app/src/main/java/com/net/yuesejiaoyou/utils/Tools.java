package com.net.yuesejiaoyou.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.redirect.ResolverA.getset.Page;
import com.net.yuesejiaoyou.redirect.ResolverA.getset.User_data;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by admin on 2018/12/21.
 */

public class Tools {

    public static void setDrawableTop(TextView textView, int resId) {
        Resources resources = textView.getContext().getResources();
        BitmapDrawable drawable = new BitmapDrawable(resources, BitmapFactory.decodeResource(resources, resId));
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        textView.setCompoundDrawables(null, drawable, null, null);
    }

    public static void setDrawableLeft(TextView textView, int resId) {
        Resources resources = textView.getContext().getResources();
        BitmapDrawable drawable = new BitmapDrawable(resources, BitmapFactory.decodeResource(resources, resId));
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        textView.setCompoundDrawables(drawable, null, null, null);
    }

    public static void setDrawableRight(TextView textView, int resId) {
        Resources resources = textView.getContext().getResources();
        BitmapDrawable drawable = new BitmapDrawable(resources, BitmapFactory.decodeResource(resources, resId));
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        textView.setCompoundDrawables(null, null, drawable, null);
    }

    public static void setDrawableBottom(TextView textView, int resId) {
        Resources resources = textView.getContext().getResources();
        BitmapDrawable drawable = new BitmapDrawable(resources, BitmapFactory.decodeResource(resources, resId));
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        textView.setCompoundDrawables(null, null, null, drawable);
    }

    public static void setNullDrawable(TextView textView) {
        textView.setCompoundDrawables(null, null, null, null);
    }

    public static void backgroundAlpha(Activity context, float bgAlpha) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0-1.0
        context.getWindow().setAttributes(lp);
    }


    public static int dp2px(Context context, float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }

    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static Dialog showProgressDialog(Context context, String msg) {
        return showProgressDialog(context, msg, true);
    }

    public static Dialog showProgressDialog(Context context, String msg, boolean isShow) {
        Dialog dialog = new Dialog(context, R.style.progress_requst_dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_just2, null);
        TextView textView = (TextView) view.findViewById(R.id.msg_tv);
        if (!TextUtils.isEmpty(msg)) {
            textView.setText(msg);
        }

        dialog.setContentView(view);
        Window window = dialog.getWindow();
        dialog.setCancelable(false);
        if (isShow) {
            dialog.show();
        }
        return dialog;
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = conn.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }

    public static Page xunyuan(String json) {

        ArrayList<User_data> list = new ArrayList<User_data>();
        Page page = new Page();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                if (i == jsonArray.length() - 1) {
                    page.setTotlePage(item.getInt("totlePage"));
                    page.setCurrent(item.getInt("pagenum"));
                } else {
                    User_data bean = new User_data();
                    bean.setNickname(item.getString("nickname"));
                    bean.setId(item.getInt("id"));
                    bean.setIdentify_check(item.getInt("is_anchor"));
                    bean.setOnline(item.getInt("online"));
                    bean.setSignature(item.getString("signature"));
                    bean.setPhoto(item.getString("photo"));
                    bean.setPrice(item.getString("price"));
                    bean.setStar(item.getInt("star"));
                    bean.setPictures(item.getString("pictures"));
                    list.add(bean);
                }
            }
            page.setList(list);
        } catch (Exception e) {
            e.printStackTrace();
            LogDetect.send(LogDetect.DataType.specialType, "this0", e);
        }
        return page;
    }

    public static int getScreenWidth(Context context) {
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        return display.getWidth();
    }

    public static String currentVersion(Context context) {
        String versionName = "";
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionName = packageInfo.versionName;
            if (TextUtils.isEmpty(versionName)) {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }

    public static boolean checkVersion(String newVersionName, String oldVersionName) {
        String[] split = newVersionName.split("\\.");
        String[] split1 = oldVersionName.split("\\.");

        if (split.length > split1.length) {
            return true;
        }

        try {
            for (int i = 0; i < split.length; i++) {
                int a = Integer.parseInt(split[i]);
                int b = Integer.parseInt(split1[i]);
                if (a > b) {
                    return true;
                } else if (a < b) {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @SuppressLint("SimpleDateFormat")
    public static String format(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    public static String showDate(Date date, String pattern) {
        String dateStr = format(date, pattern);
        String year = dateStr.substring(0, 4);
        Long yearNum = Long.parseLong(year);
        int month = Integer.parseInt(dateStr.substring(5, 7));
        int day = Integer.parseInt(dateStr.substring(8, 10));


        int hour1 = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        long addtime = date.getTime();
        long today = System.currentTimeMillis();//当前时间的毫秒数
        Date now = new Date(today);
        String nowStr = format(now, pattern);
        int nowDay = Integer.parseInt(nowStr.substring(8, 10));
        String result = "";
        long l = today - addtime;//当前时间与给定时间差的毫秒数
        long days = l / (24 * 60 * 60 * 1000);//这个时间相差的天数整数，大于1天为前天的时间了，小于24小时则为昨天和今天的时间
        long hours = (l / (60 * 60 * 1000) - days * 24);//这个时间相差的减去天数的小时数

        if (days > 0) {
            if (days > 0 && days < 2) {
                result = "前天";
            } else {
                result = ""/*yearNum%100+"年"+month+"月 "+day+"日"*/;
            }
        } else if (hours > 0) {
            if (day != nowDay) {
                result = "昨天";
            }
        } else if (hour1 >= 6 && hour1 < 11) {
            result = "上午";
        } else if (hour1 >= 11 && hour1 < 13) {
            result = "中午";
        } else if (hour1 >= 13 && hour1 < 18) {
            result = "下午";
        } else if (hour1 >= 18 && hour1 > 6) {
            result = "夜晚";
        }
        return result;
    }

    public static String currentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd  HH:mm:ss");
        return sdf.format(new Date());
    }

    public static String checkTime(String time){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd  HH:mm:ss");

        return sdf.format(new Date());
    }

    public static String getTimerStr(int time) {
        if (time / 60 < 10) {
            if (time % 60 < 10) {
                return "0" + time / 60 + ":0" + time % 60;
            } else {
                return "0" + time / 60 + ":" + time % 60;
            }
        } else {
            if (time % 60 < 10) {
                return time / 60 + ":0" + time % 60;
            } else {
                return time / 60 + ":" + time % 60;
            }
        }
    }


    public static int getGift2Drwable(int gid) {
        switch (gid) {
            case 1:
                return R.drawable.present_1;
            case 2:
                return R.drawable.present_2;
            case 3:
                return R.drawable.present_3;
            case 4:
                return R.drawable.present_4;
            case 5:
                return R.drawable.present_5;
            case 6:
                return R.drawable.present_6;
            case 7:
                return R.drawable.present_7;
            case 8:
                return R.drawable.present_8;
            case 9:
                return R.drawable.present_9;
            case 10:
                return R.drawable.present_10;//520
            case 11:
                return R.drawable.present_11;//蛋糕
            case 12:
                return R.drawable.present_12;//钻戒
            case 13:
                return R.drawable.present_13;//劳力士
            case 14:
                return R.drawable.present_14;//保时捷
            case 15:
                return R.drawable.present_15;//兰博
            case 16:
                return R.drawable.present_16;
            case 17:
                return R.drawable.present_17;
            case 18:
                return R.drawable.present_18;


        }
        return R.drawable.present_1;
    }

    public static String getGiftName(int gid) {
        switch (gid) {
            case 1:
                return "鲜花";
            case 2:
                return "黄瓜";
            case 3:
                return "热气球";
            case 4:
                return "熊抱抱";
            case 5:
                return "加个好友";
            case 6:
                return "喜欢你";
            case 7:
                return "飞吻";
            case 8:
                return "红包";
            case 9:
                return "巧克力";
            case 10:
                return "520";//520
            case 11:
                return "玫瑰花";//蛋糕
            case 12:
                return "女神钻戒";//
            case 13:
                return "劳力士";//玫瑰花
            case 14:
                return "保时捷跑车";//妖姬
            case 15:
                return "兰博跑车";//鞋
            case 16:
                return "蓝色妖姬";//飞机
            case 17:
                return "别墅";//
            case 18:
                return "私人飞机";//城堡
            case 26:
                return "邮轮";//城堡

        }
        return "鲜花";
    }

    public static String getGiftNameByPrice(int price) {
        switch (price) {
            case 9:
                return "鲜花";
            case 18:
                return "喜欢你";
            case 66:
                return "飞吻";
            case 99:
                return "红包";
            case 188:
                return "巧克力";
            case 520:
                return "520";//520
            case 888:
                return "玫瑰花";//蛋糕
            case 1314:
                return "女神钻戒";//
            case 1888:
                return "劳力士";//玫瑰花
            case 2888:
                return "保时捷跑车";//妖姬
            case 3999:
                return "兰博跑车";//鞋
            case 8888:
                return "蓝色妖姬";//飞机

        }
        return String.valueOf(price);
    }

    public static int getGiftCount(int gid) {
        switch (gid) {
            case 1:
                return 9;
            case 6:
                return 18;
            case 7:
                return 66;
            case 8:
                return 99;
            case 9:
                return 188;
            case 10:
                return 520;//520
            case 11:
                return 888;//蛋糕
            case 12:
                return 1314;//
            case 13:
                return 1888;//玫瑰花
            case 14:
                return 2888;//妖姬
            case 15:
                return 3999;//鞋
            case 16:
                return 8888;//飞机
            case 17:
                return 12000;//
            case 18:
                return 16000;//城堡
            case 26:
                return 29999;//城堡

        }
        return 1;
    }
}
