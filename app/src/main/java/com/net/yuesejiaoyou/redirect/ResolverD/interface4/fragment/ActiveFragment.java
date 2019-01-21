package com.net.yuesejiaoyou.redirect.ResolverD.interface4.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.redirect.ResolverA.getset.User_data;
import com.net.yuesejiaoyou.redirect.ResolverA.getset.photo_01162;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.URL;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.activity.UserActivity;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.activity.WebActivity;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.activity.ShareActivity;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.adapter.Find2Adapter;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.utils.ImageUtils;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.utils.MyLoadMoreView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;


@SuppressLint("ValidFragment")
public class ActiveFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    //activity_v
    private List<User_data> articles = new ArrayList<User_data>();
    private int pageno = 1;

    private RecyclerView recyclerView;
    SwipeRefreshLayout refreshLayout;
    Find2Adapter adapter;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refreshLayout);
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        refreshLayout.setOnRefreshListener(this);
        recyclerView = (RecyclerView) view.findViewById(R.id.theme_grre);
        pageno = 1;


        recyclerView = view.findViewById(R.id.theme_grre);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = new Find2Adapter(getContext(), articles);
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
            public void onItemChildClick(BaseQuickAdapter adapter1, View view, int position) {
                if (view.getId() == R.id.item_onev_list_layout) {
                    Intent intent = new Intent(getContext(), UserActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("id", "" + articles.get(position).getId());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });

        getBanner();
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_focus;
    }

    private void getBanner() {
        OkHttpUtils
                .post(this)
                .url(URL.URL_SEARCH)
                .addParams("param1", "")
                .addParams("param2", pageno)
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        getDatas();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        getDatas();
                        if (TextUtils.isEmpty(response)) {
                            return;
                        }
                        final List<photo_01162> list1 = JSON.parseArray(response, photo_01162.class);
                        if (list1 == null || list1.size() == 0) {
                            return;
                        }
                        ArrayList<String> mListImage = new ArrayList<String>();
                        headview = LayoutInflater.from(getContext()).inflate(R.layout.activity_banner, null);
                        Banner banner = headview.findViewById(R.id.header);
                        for (int i = 0; i < list1.size(); i++) {
                            mListImage.add(list1.get(i).getPhoto());
                        }
                        //设置Banner图片集合
                        banner.setImages(mListImage);
                        banner.setImageLoader(new GlideImageLaoder());
                        banner.setBannerAnimation(Transformer.Tablet);
                        banner.setDelayTime(3000);
                        banner.setBackgroundColor(getContext().getResources().getColor(R.color.black));
                        banner.setIndicatorGravity(BannerConfig.CENTER);
                        banner.start();

                        banner.setOnBannerListener(new OnBannerListener() {
                            @Override
                            public void OnBannerClick(int position) {

                                if (position == 2) {
                                    Intent intent = new Intent();
                                    intent.setClass(getActivity(), ShareActivity.class);
                                    getActivity().startActivity(intent);
                                } else {
                                    Intent intent = new Intent();
                                    Bundle bundle = new Bundle();
                                    bundle.putString("photo_item", list1.get(position).getPhoto_item());
                                    intent.putExtras(bundle);
                                    intent.setClass(getActivity(), WebActivity.class);
                                    startActivity(intent);
                                }
                            }
                        });
                        adapter.addHeaderView(headview);
                    }

                });
    }

    public void getDatas() {
        OkHttpUtils
                .post(this)
                .url(URL.URL_ACTIVE)
                .addParams("param1", "")
                .addParams("param2", pageno)
                .addParams("param3", gender)
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        refreshLayout.setRefreshing(false);
                        adapter.loadMoreFail();
                    }

                    @Override
                    public void onResponse(String response, int id) {
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
                        if (list1.size() <= 1) {
                            adapter.loadMoreEnd();
                            return;
                        }
                        if (pageno == 1) {
                            articles.clear();
                        }
                        articles.addAll(list1.subList(0, list1.size() - 1));
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
        // 设置可见
        refreshLayout.setRefreshing(true);
        pageno = 1;
        getDatas();
    }


    private View headview = null;


    public class GlideImageLaoder extends com.youth.banner.loader.ImageLoader {

        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            ImageUtils.loadImage((String) path, imageView);
        }

    }
}
