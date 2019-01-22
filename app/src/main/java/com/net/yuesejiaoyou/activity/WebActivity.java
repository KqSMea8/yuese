package com.net.yuesejiaoyou.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.BaseActivity;

import butterknife.OnClick;


/**
 * Created by Administrator on 2018/4/24.
 */

public class WebActivity extends BaseActivity {

    private WebView activity_web;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity_web = (WebView) findViewById(R.id.activity_photo);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String a = bundle.getString("photo_item");
        activity_web.loadUrl(a);
        activity_web.getSettings().setJavaScriptEnabled(true);
        activity_web.getSettings().setUseWideViewPort(true);
        activity_web.getSettings().setLoadWithOverviewMode(true);
        activity_web.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_web;
    }

    @OnClick(R.id.fanhui)
    public void backClick() {
        finish();
    }


}
