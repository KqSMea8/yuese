package com.net.yuesejiaoyou.redirect.ResolverD.interface4.fragment;
/**
 * 发现首页
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.net.yuesejiaoyou.redirect.ResolverB.getset.Videoinfo;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.activity.VideoPlayActivity;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.URL;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.adapter.DynamicAdapter;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.utils.MyLoadMoreView;

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;

/////////////////////A区跳转到B区，上传短视频
import com.net.yuesejiaoyou.redirect.ResolverB.uiface.UploadVideo_01158;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;


public class DynamicFragment extends BaseFragment {

    @BindView(R.id.newvideo)
    TextView tvNew;
    @BindView(R.id.hotvideo)
    TextView tvHot;
    @BindView(R.id.attention)
    TextView tvFocus;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.uploadvideoyun)
    ImageView ivSend;

    List<Videoinfo> datas = new ArrayList<>();
    DynamicAdapter adapter;

    int page = 1;
    String url = URL.URL_VIDEO_HOT;

    @Override
    protected int getContentView() {
        return R.layout.fragment_dynamic;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (Util.iszhubo.equals("1")) {
            ivSend.setVisibility(View.VISIBLE);
        }

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        adapter = new DynamicAdapter(getContext(), datas);
        recyclerView.setAdapter(adapter);

        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                page = 1;
                getData();
            }
        });
        adapter.setLoadMoreView(new MyLoadMoreView());
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page++;
                getData();
            }
        }, recyclerView);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getContext(), VideoPlayActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("id", position);
                bundle.putSerializable("vlist", (Serializable) datas);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        refreshLayout.autoRefresh(200);
    }

    private void getData() {
        OkHttpUtils
                .post(this)
                .url(url)
                .addParams("param1", Util.userid)
                .addParams("param2", page)
                .addParams("param3", 1000000000)
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        refreshLayout.finishRefresh();
                        adapter.loadMoreFail();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        refreshLayout.finishRefresh();
                        if (TextUtils.isEmpty(response)) {
                            adapter.loadMoreFail();
                            return;
                        }
                        List<Videoinfo> list1 = JSON.parseArray(response, Videoinfo.class);
                        if (list1 == null) {
                            adapter.loadMoreFail();
                            return;
                        }
                        if (page == 1) {
                            datas.clear();
                        }
                        datas.addAll(list1);
                        adapter.notifyDataSetChanged();
                        if (list1.size() == 0) {
                            adapter.loadMoreEnd();
                        } else {
                            adapter.loadMoreComplete();
                        }
                    }

                });
    }

    @OnClick(R.id.newvideo)
    public void newClick() {
        tvNew.setTextColor(getColor(R.color.vhongse));
        tvHot.setTextColor(getColor(R.color.vheise));
        tvFocus.setTextColor(getColor(R.color.vheise));
        url = URL.URL_VIDEO_NEW;
        refreshLayout.autoRefresh(200);
    }


    @OnClick(R.id.hotvideo)
    public void hotClick() {
        tvHot.setTextColor(getColor(R.color.vhongse));
        tvNew.setTextColor(getColor(R.color.vheise));
        tvFocus.setTextColor(getColor(R.color.vheise));
        url = URL.URL_VIDEO_HOT;
        refreshLayout.autoRefresh(200);
    }

    @OnClick(R.id.attention)
    public void focusClick() {
        tvFocus.setTextColor(getColor(R.color.vhongse));
        tvNew.setTextColor(getColor(R.color.vheise));
        tvHot.setTextColor(getColor(R.color.vheise));
        url = URL.URL_VIDEO_FOCUS;
        refreshLayout.autoRefresh(200);
    }


    @OnClick(R.id.uploadvideoyun)
    public void sendClick() {
        startActivity(UploadVideo_01158.class);
    }

}
