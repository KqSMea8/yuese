package com.net.yuesejiaoyou.redirect.ResolverD.interface4.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.redirect.ResolverC.uiface.Authentication_01150;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.BaseActivity;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.activity.XieyiActivity;

import butterknife.OnClick;

public class AnchorGuideActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_anchorguide;
    }


    @OnClick(R.id.back1)
    public void backClick() {
        finish();
    }


    @OnClick(R.id.agree)
    public void startClick() {
        startActivity(Authentication_01150.class);
        finish();
    }

    @OnClick(R.id.xieyi1)
    public void xieyiClick() {
        startActivity(XieyiActivity.class);
    }

}
