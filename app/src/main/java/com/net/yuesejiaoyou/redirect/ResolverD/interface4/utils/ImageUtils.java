package com.net.yuesejiaoyou.redirect.ResolverD.interface4.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.GlideApp;

/**
 * Created by tl on 2018/7/30.
 */

public class ImageUtils {

    public static void loadImage(String url, ImageView imageView) {
        GlideApp.with(imageView).clear(imageView);
        GlideApp.with(imageView)
                .load(url)
                .into(imageView);
    }
}
