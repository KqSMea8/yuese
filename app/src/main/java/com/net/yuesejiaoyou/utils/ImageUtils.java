package com.net.yuesejiaoyou.utils;

import android.widget.ImageView;

import com.net.yuesejiaoyou.redirect.ResolverD.interface4.GlideApp;

/**
 * Created by tl on 2018/7/30.
 */

public class ImageUtils {

    public static void loadImage(String url, ImageView imageView) {
        try {
            GlideApp.with(imageView).clear(imageView);
            GlideApp.with(imageView)
                    .load(url)
                    .into(imageView);
        } catch (Exception e) {

        }

    }
}
