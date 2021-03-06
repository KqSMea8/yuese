package com.net.yuesejiaoyou.activity;

import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.BillBean;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.BaseActivity;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.URL;
import com.net.yuesejiaoyou.adapter.BillAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;


public class ZhuboBillActivity extends BaseActivity {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    BillAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .color(Color.parseColor("#F2F2F2"))
                .build());
        adapter = new BillAdapter();
        recyclerView.setAdapter(adapter);

        getData();
    }

    private void getData() {
        OkHttpUtils.post(this)
                .url(URL.URL_OUTCOME)
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
                        List<BillBean> beans = JSON.parseArray(response, BillBean.class);
                        if (beans != null && beans.size() > 1) {
                            beans.remove(beans.size() - 1);
                            adapter.setNewData(beans);
                        }

                    }
                });
    }

    @Override
    protected int getContentView() {
        return R.layout.vliao_zhichu_01168;
    }


    @OnClick(R.id.fanhui)
    public void backClick() {
        finish();
    }

}
