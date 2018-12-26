package com.net.yuesejiaoyou.redirect.ResolverA.uiface;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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

import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.redirect.ResolverA.getset.User_data;
import com.net.yuesejiaoyou.redirect.ResolverA.getset.photo_01162;
import com.net.yuesejiaoyou.redirect.ResolverC.uiface.ShareActivity;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.URL;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.adapter.Find2Adapter;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.utils.ImageUtils;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.utils.LogUtil;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.utils.MyLoadMoreView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
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
public class VFragment_1_01066 extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private Context mContext;
    String sort;
    private RecyclerView recyclerView;
    private List<User_data> articles = new ArrayList<User_data>();
    private View view;
    private int pageno = 1;
    Find2Adapter adapter;
    SwipeRefreshLayout refreshLayout;
    private DisplayImageOptions options = null;
    private boolean a = false;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        view = inflater.inflate(R.layout.fragment1_01066, null);
        LogDetect.send(LogDetect.DataType.specialType, "this0", "??");
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refreshLayout);
        options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisc(true).bitmapConfig(Bitmap.Config.RGB_565).build();
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        refreshLayout.setOnRefreshListener(this);

        recyclerView = view.findViewById(R.id.theme_grre);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = new Find2Adapter(mContext, articles);
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
                if (view.getId() == R.id.item_onev_list_layout) {
                    Intent intent = new Intent(getContext(), Userinfo.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("id", "" + articles.get(position).getId());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });

        pageno = 1;

        getBannerDatas();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void getBannerDatas() {
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
                        headview = LayoutInflater.from(mContext).inflate(R.layout.activity_banner, null);
                        Banner banner = headview.findViewById(R.id.header);
                        for (int i = 0; i < list1.size(); i++) {
                            mListImage.add(list1.get(i).getPhoto());
                        }
                        //设置Banner图片集合
                        banner.setImages(mListImage);
                        banner.setImageLoader(new GlideImageLaoder());
                        banner.setBannerAnimation(Transformer.Tablet);
                        banner.setDelayTime(3000);
                        banner.setBackgroundColor(mContext.getResources().getColor(R.color.black));
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
                                    intent.setClass(getActivity(), Activity_web_01162.class);
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
                .url(URL.URL_XUNYAN)
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


    private View headview = null;


    public class GlideImageLaoder extends com.youth.banner.loader.ImageLoader {

        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            ImageUtils.loadImage((String) path, imageView);
        }

    }
}
