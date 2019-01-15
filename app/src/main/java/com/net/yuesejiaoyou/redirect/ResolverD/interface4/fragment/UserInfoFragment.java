package com.net.yuesejiaoyou.redirect.ResolverD.interface4.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.redirect.ResolverA.getset.User_data;
import com.net.yuesejiaoyou.redirect.ResolverA.interface4.VMyAdapterzl_01066;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.URL;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.BindView;
import okhttp3.Call;


@SuppressLint("ValidFragment")
public class UserInfoFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    VMyAdapterzl_01066 adapter;
    private User_data userinfo;
    String zhubo_id;

    public UserInfoFragment(User_data userinfo) {
        this.userinfo = userinfo;
    }

    public UserInfoFragment() {
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (userinfo == null) {
            zhubo_id = "0";
        } else {
            zhubo_id = userinfo.getId() + "";
        }

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext())
                .color(getResources().getColor(R.color.v_infromation_new))
                .build());

        evaluate();
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_userinfo;
    }

    public void evaluate() {
        OkHttpUtils.post(this)
                .url(URL.URL_VIDEO_FOCUS)
                .addParams("param1", "13")
                .addParams("param2", zhubo_id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (TextUtils.isEmpty(response)) {
                            return;
                        }
                        List<User_data> articles = JSON.parseArray(response, User_data.class);
                        adapter = new VMyAdapterzl_01066(getContext(), articles, userinfo);
                        adapter.setFadeTips(true);
                        recyclerView.setAdapter(adapter);
                    }
                });
    }


}
