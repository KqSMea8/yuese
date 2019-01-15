package com.net.yuesejiaoyou.redirect.ResolverD.interface4.activity;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;


import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.FansBean;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.BaseActivity;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.URL;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.adapter.IntimateAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.DialogCallback;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;


public class IntimateActivity extends BaseActivity {

    ArrayList<FansBean> list = new ArrayList<FansBean>();
    String userid = "";
    private int pageno = 1;


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private IntimateAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .color(Color.parseColor("#F2F2F2"))
                .build());
        adapter = new IntimateAdapter();
        recyclerView.setAdapter(adapter);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                pageno++;
                initdata();
            }
        }, recyclerView);

        Bundle bundle = this.getIntent().getExtras();
        userid = bundle.getString("id");
        initdata();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_intimate;
    }

    public void initdata() {
        OkHttpUtils.post(this)
                .url(URL.URL_INTIMATE)
                .addParams("param1", "")
                .addParams("param2", userid)
                .addParams("param3", pageno)
                .build()
                .execute(new DialogCallback(this, pageno == 1) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        showToast("网络异常,请重试！");
                        adapter.loadMoreFail();
                    }

                    @Override
                    public void onResponse(String resultBean, int id) {
                        if (TextUtils.isEmpty(resultBean)) {
                            return;
                        }
                        List<FansBean> fansBeans = JSON.parseArray(resultBean, FansBean.class);
                        if (fansBeans != null && fansBeans.size() > 1) {
                            if (pageno == 1) {
                                list.clear();
                            }
                            list.addAll(fansBeans.subList(0, fansBeans.size() - 1));
                            adapter.setNewData(list);
                            adapter.loadMoreComplete();
                        } else {
                            adapter.loadMoreEnd();
                        }
                    }
                });
    }


    @OnClick(R.id.fanhui)
    public void backClick() {
        finish();
    }

}
