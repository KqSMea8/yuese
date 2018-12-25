package com.net.yuesejiaoyou.redirect.ResolverD.interface4.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by tl on 2018/7/30.
 */

public class ImageUtils {

    public static void loadImage(String url, ImageView imageView) {
        Glide.clear(imageView);
        Glide.with(imageView.getContext())
                .load(url)
                .into(imageView);
    }
}
