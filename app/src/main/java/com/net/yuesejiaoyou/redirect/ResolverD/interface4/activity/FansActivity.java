package com.net.yuesejiaoyou.redirect.ResolverD.interface4.activity;

import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.FansBean;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.BaseActivity;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.URL;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.adapter.FansAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;


public class FansActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    FansAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .color(Color.parseColor("#F2F2F2"))
                .build());
        adapter = new FansAdapter();
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter1, View view, int position) {
                Intent intent = new Intent(FansActivity.this, UserActivity.class);
                intent.putExtra("id", adapter.getData().get(position).getUser_id());
                startActivity(intent);
            }
        });
        getData();
    }

    private void getData() {
        OkHttpUtils.post(this)
                .url(URL.URL_FANS)
                .addParams("param1", Util.userid)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        showToast("网络异常");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        List<FansBean> datas = JSON.parseArray(response, FansBean.class);
                        if (datas != null && datas.size() > 0) {
                            adapter.setNewData(datas);
                        }
                    }
                });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_fans;
    }


    @OnClick(R.id.fanhui)
    public void backClick() {
        finish();
    }

}
