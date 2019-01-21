package com.net.yuesejiaoyou.redirect.ResolverD.interface4.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.classroot.interface4.openfire.uiface.ChatActivity;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.redirect.ResolverA.getset.User_data;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.URL;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.UrlUtils;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.activity.UserActivity;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.adapter.FindAdapter;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.adapter.RankAdapter;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.utils.LogUtil;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.utils.MyCallBack;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.utils.MyLoadMoreView;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.DialogCallback;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;


@SuppressLint("ValidFragment")
public class RankFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;


    RankAdapter adapter;

    int type = 1;

    public RankFragment(int type) {
        this.type = type;
    }

    public RankFragment() {

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext())
                .color(Color.parseColor("#F2F2F2"))
                .build());
        adapter = new RankAdapter();
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter1, View view, int position) {
                Intent intent = new Intent(getContext(), UserActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", "" + adapter.getData().get(position).getUser_id());
                intent.putExtras(bundle);
                startActivity(intent);

//                User_data user_data = adapter.getData().get(position);
//                Intent intentthis = new Intent(getContext(), ChatActivity.class);
//                intentthis.putExtra("id", user_data.getUser_id()+"");
//                intentthis.putExtra("name", user_data.getNickname());
//                intentthis.putExtra("headpic", user_data.getPhoto());
//                startActivity(intentthis);
            }
        });

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter1, View view, int position) {
                Intent intent = new Intent(getContext(), UserActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", "" + adapter.getData().get(position).getUser_id());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        getDatas();
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_rank;
    }



    public void getDatas() {
        OkHttpUtils
                .post(this)
                .url(UrlUtils.URL_RANK)
                .addParams("type", 1)
                .addParams("mold  ", type)
                .build()
                .execute(new DialogCallback(getContext()) {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        showToast("网络异常");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (TextUtils.isEmpty(response)) {
                            return;
                        }
                        List<User_data> list1 = JSON.parseArray(response, User_data.class);
                        adapter.setNewData(list1);
                    }

                });
    }


}
