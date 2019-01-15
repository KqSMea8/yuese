package com.net.yuesejiaoyou.redirect.ResolverC.uiface;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.Tag;
import com.net.yuesejiaoyou.redirect.ResolverC.interface3.UsersThread_01162C;
import com.net.yuesejiaoyou.redirect.ResolverC.interface4.FlowLayout_01162;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.BaseActivity;

import java.util.ArrayList;


/**
 * Created by Administrator on 2018/3/8.
 */


public class Mytag_01162 extends BaseActivity {
    private FlowLayout_01162 mFlowLayout01162;
    private ArrayList<Tag> list;
    private LayoutInflater mInflater;
    private String zhubo_id;
    private ImageView back2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInflater = LayoutInflater.from(this);
        mFlowLayout01162 = (FlowLayout_01162) findViewById(R.id.mytag);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        back2 = (ImageView) findViewById(R.id.back2);
        back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        zhubo_id = bundle.getString("zhubo_id");

        String mode = "my_impression";
        String params[] = {"", zhubo_id};
        UsersThread_01162C b = new UsersThread_01162C(mode, params, handler);
        Thread thread = new Thread(b.runnable);
        thread.start();
    }

    @Override
    protected int getContentView() {
        return R.layout.mytag_01162;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 201:
                    list = (ArrayList<Tag>) msg.obj;
                    initData(list);
                    break;
            }
        }
    };

    public void initData(ArrayList<Tag> list) {
        /**
         * 找到搜索标签的控件
         */
        for (int i = 0; i < list.size(); i++) {
            TextView tv = (TextView) mInflater.inflate(
                    R.layout.mytag_item_01162, mFlowLayout01162, false);
            tv.setText(list.get(i).getRecord() + " " + list.get(i).getCount());
            GradientDrawable drawable = (GradientDrawable) tv.getBackground();
            drawable.setColor(Color.parseColor(list.get(i).getColor()));
            final String str = tv.getText().toString();
            //点击事件
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            mFlowLayout01162.addView(tv);//添加到父View
        }
    }

}
