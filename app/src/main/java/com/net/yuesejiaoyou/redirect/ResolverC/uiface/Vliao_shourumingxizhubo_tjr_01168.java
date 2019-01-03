package com.net.yuesejiaoyou.redirect.ResolverC.uiface;

import java.util.ArrayList;
import java.util.List;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;

import com.net.yuesejiaoyou.redirect.ResolverC.getset.BillBean;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.Page;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.Vliao1_01168;
import com.net.yuesejiaoyou.redirect.ResolverC.interface3.UsersThread_01168C;
import com.net.yuesejiaoyou.redirect.ResolverC.interface4.Vliao_shourutjrAdapter_01168;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.BaseActivity;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.URL;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.adapter.BillAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class Vliao_shourumingxizhubo_tjr_01168 extends BaseActivity {
    @BindView(R.id.shourujine)
    TextView shourujine;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    BillAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        context = this;
//        fanhui = (ImageView) findViewById(R.id.fanhui);
//        fanhui.setOnClickListener(this);
//        //shourujine
//        shourujine = (TextView) findViewById(R.id.shourujine);
//        shourujine.setOnClickListener(this);
//        shouru = (ListView) findViewById(R.id.shourujine);
//        allshouru = (TextView) findViewById(R.id.allshouru);
//
//        allshouru.setText("总收入金额（元）");
//        String mode = "shouruzhubo_tjr";
//        String[] params = {Util.userid};
//        UsersThread_01168C b = new UsersThread_01168C(mode, params, handler);
//        Thread thread = new Thread(b.runnable);
//        thread.start();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .color(Color.parseColor("#F2F2F2"))
                .build());
        adapter = new BillAdapter();
        recyclerView.setAdapter(adapter);

        getData();

    }

    @Override
    protected int getContentView() {
        return R.layout.vliao_shouruzhubo_01168;
    }

//    private Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what) {
//                case 215:
//                    Page page = (Page) msg.obj;
//                    shourujine.setText(page.getAblenum() + "");
//                    ArrayList<Vliao1_01168> list2 = (ArrayList<Vliao1_01168>) page.getList();
//                    LogDetect.send(LogDetect.DataType.basicType, "01162----list集合", list2);
//                    Vliao_shourutjrAdapter_01168 adapter = new Vliao_shourutjrAdapter_01168(list2, Vliao_shourumingxizhubo_tjr_01168.this, shouru);
//                    shouru.setAdapter(adapter);
//            }
//        }
//
//    };

    private void getData() {
        OkHttpUtils.post(this)
                .url(URL.URL_INCOME_TUIJIAN_ZHUBO)
                .addParams("param1", Util.userid)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        showToast("网络异常");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (TextUtils.isEmpty(response)) {
                            return;
                        }
                        JSONArray objects = JSON.parseArray(response);
                        shourujine.setText(objects.getJSONObject(objects.size() - 1).getString("totlePage"));
                        List<BillBean> beans = JSON.parseArray(response, BillBean.class);
                        if (beans != null && beans.size() > 1) {
                            beans.remove(beans.size() - 1);
                            adapter.setNewData(beans);
                        }

                    }
                });
    }


    @OnClick(R.id.fanhui)
    public void backClick() {
        finish();
    }

}
