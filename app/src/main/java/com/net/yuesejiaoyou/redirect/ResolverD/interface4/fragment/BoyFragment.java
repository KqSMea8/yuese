package com.net.yuesejiaoyou.redirect.ResolverD.interface4.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.classroot.interface4.openfire.uiface.ChatActivity;
import com.net.yuesejiaoyou.redirect.ResolverA.getset.User_data;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.agora.P2PVideoConst;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.agora.zhubo.GukeInfo;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.agora.zhubo.ZhuboActivity;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.utils.LogUtil;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.utils.MyLoadMoreView;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.URL;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.adapter.FindAdapter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;


@SuppressLint("ValidFragment")
public class BoyFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private Context mContext;
    private RecyclerView recyclerView;
    private List<User_data> articles = new ArrayList<User_data>();
    private View view;
    private int pageno = 1;
    FindAdapter adapter;
    SwipeRefreshLayout refreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        view = inflater.inflate(R.layout.fragment_focus, null);
        LogDetect.send(LogDetect.DataType.specialType, "this0", "??");
        refreshLayout =  view.findViewById(R.id.refreshLayout);
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        refreshLayout.setOnRefreshListener(this);

        recyclerView = view.findViewById(R.id.theme_grre);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        adapter = new FindAdapter(mContext, articles);
        recyclerView.setAdapter(adapter);
        adapter.setLoadMoreView(new MyLoadMoreView());
        adapter.setPreLoadNumber(1);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                pageno++;
                getDatas();
            }
        }, recyclerView);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.userinfo) {
                    Intent intentthis = new Intent(mContext, ChatActivity.class);
                    intentthis.putExtra("id", articles.get(position).getId() + "");
                    intentthis.putExtra("name", articles.get(position).getNickname());
                    intentthis.putExtra("headpic", articles.get(position).getPhoto());
                    startActivity(intentthis);
                } else if (view.getId() == R.id.zhichi) {
                    String roomid = System.currentTimeMillis() + "";
                    ZhuboActivity.startCallGuke(mContext, new GukeInfo(articles.get(position).getId() + "",
                            articles.get(position).getNickname(), articles.get(position).getPhoto(), roomid,
                            P2PVideoConst.ZHUBO_CALL_GUKE, P2PVideoConst.HAVE_NO_YUYUE));
                }
            }
        });

        pageno = 1;
        getDatas();

        return view;
    }

    public void getDatas() {
        OkHttpUtils
                .post(this)
                .url(URL.URL_REMEMAN)
                .addParams("param1", "")
                .addParams("param2", pageno)
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        refreshLayout.setRefreshing(false);
                        adapter.loadMoreFail();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtil.d("ttt", response);
                        refreshLayout.setRefreshing(false);
                        if (TextUtils.isEmpty(response)) {
                            adapter.loadMoreFail();
                            return;
                        }
                        List<User_data> list1 = JSON.parseArray(response, User_data.class);
                        if (list1 == null) {
                            adapter.loadMoreFail();
                            return;
                        }
                        if (pageno == 1) {
                            articles.clear();
                        }
                        articles.addAll(list1);
                        adapter.notifyDataSetChanged();
                        if (list1.size() == 0) {
                            adapter.loadMoreEnd();
                        } else {
                            adapter.loadMoreComplete();
                        }
                    }

                });
    }


    String gender = "女";

    @Override
    public void onRefresh() {
        pageno = 1;
        getDatas();
    }


}
