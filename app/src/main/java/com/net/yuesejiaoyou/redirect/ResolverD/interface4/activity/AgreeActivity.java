package com.net.yuesejiaoyou.redirect.ResolverD.interface4.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/6/8.
 */

public class AgreeActivity extends BaseActivity {


    @BindView(R.id.xieyi)
    TextView xieyi;
    @BindView(R.id.titlename)
    TextView tvTitle;
    private String text = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tvTitle.setText("赚钱攻略");
        text = "一、消费提成\n" +
                "\n" +
                "1、分销一级用户" + getIntent().getStringExtra("cash_onefee") + "%提成。\n" +
                "\n" +
                "2、［你邀请的人］一级用户每完成一次充值，你将获得其中" + getIntent().getStringExtra("cash_onefee") + "%的提成。\n" +
                "\n" +
                "二、收入提成\n" +
                "\n" +
                "1、分销一级用户" + getIntent().getStringExtra("dvcash_onefee") + "%提成。\n" +
                "\n" +
                "2、［你邀请的女生］一级用户每完成一次提现，你将获得其中" + getIntent().getStringExtra("dvcash_onefee") + "%的提成。\n";
        xieyi.setText(text);

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_agree;
    }

    @OnClick(R.id.back)
    public void backClick() {
        finish();
    }

}
