package com.net.yuesejiaoyou.redirect.ResolverD.interface4.activity;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.openfire.uiface.ChatActivity;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.redirect.ResolverA.getset.UserBean;
import com.net.yuesejiaoyou.redirect.ResolverA.getset.User_data;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.BaseActivity;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.URL;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.UrlUtils;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.adapter.SearchAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;


import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;


public class SearchActivity extends BaseActivity implements TextWatcher {

    @BindView(R.id.edittext)
    EditText editText;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    SearchAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        editText.addTextChangedListener(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .color(Color.parseColor("#F2F2F2"))
                .build());
        adapter = new SearchAdapter();
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter1, View view, int position) {
                if(Util.iszhubo.equals("0")){
                    Intent intent = new Intent(SearchActivity.this, UserActivity.class);
                    intent.putExtra("id", adapter.getData().get(position).getId());
                    startActivity(intent);
                }else {
                    UserBean user_data = adapter.getData().get(position);
                    Intent intentthis = new Intent(SearchActivity.this, ChatActivity.class);
                    intentthis.putExtra("id", user_data.getId()+"");
                    intentthis.putExtra("name", user_data.getNickname());
                    intentthis.putExtra("headpic", user_data.getPhoto());
                    startActivity(intentthis);
                }
            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_search;
    }

    @OnClick(R.id.tv_cancel)
    public void cancelClick() {
        finish();
    }


    private void getData(final String key) {
        OkHttpUtils
                .post(this)
                .url(UrlUtils.URL_SEARCH)
                .addParams("user_id", Util.userid)
                .addParams("name", key)
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        showToast("搜索失败");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            List<UserBean> list = JSON.parseArray(response, UserBean.class);
                            if (list != null && list.size() != 0) {
                                adapter.setDatas(list, key);
                            } else {
                                showToast("搜索失败");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!TextUtils.isEmpty(s)) {
            getData(s.toString());
        }
    }
}