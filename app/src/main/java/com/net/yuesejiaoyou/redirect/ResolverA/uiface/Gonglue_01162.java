package com.net.yuesejiaoyou.redirect.ResolverA.uiface;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.net.yuesejiaoyou.R;

/**
 * Created by Administrator on 2018/6/8.
 */

public class Gonglue_01162 extends Activity implements View.OnClickListener {
private ImageView back;
private TextView xieyi;
private String text="";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agreement_01162);
        back= (ImageView) findViewById(R.id.back);

        TextView titlename= (TextView) findViewById(R.id.titlename);
        titlename.setText("赚钱攻略");
        text="一、消费提成\n" +
                "\n" +
                "1、分销一级用户"+getIntent().getStringExtra("cash_onefee")+"%提成。\n" +
                "\n" +
                "2、［你邀请的人］一级用户每完成一次充值，你将获得其中"+getIntent().getStringExtra("cash_onefee")+"%的提成。\n" +
                "\n" +
                "二、收入提成\n" +
                "\n" +
                "1、分销一级用户"+getIntent().getStringExtra("dvcash_onefee")+"%提成。\n" +
                "\n" +
                "2、［你邀请的女生］一级用户每完成一次提现，你将获得其中"+getIntent().getStringExtra("dvcash_onefee")+"%的提成。\n";
                xieyi= (TextView) findViewById(R.id.xieyi);
        back.setOnClickListener(this);
        xieyi.setText(text);

    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.back:
                finish();
                break;


        }


    }
}
