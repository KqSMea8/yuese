package com.net.yuesejiaoyou.redirect.ResolverC.interface4;

import java.text.DecimalFormat;

/**
 * Created by Administrator on 2018/4/8.
 */

public class Format_01162 {

    public static String formatString(int data) {
        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(data);
    }

    public static String formatString(String data) {
        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(data);
    }
}
