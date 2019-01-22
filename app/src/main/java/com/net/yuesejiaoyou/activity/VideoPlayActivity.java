package com.net.yuesejiaoyou.activity;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.alibaba.fastjson.JSON;
import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.redirect.ResolverB.getset.Videoinfo;
import com.net.yuesejiaoyou.fragment.VideoPlayFragment;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.BaseActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import fr.castorflex.android.verticalviewpager.VerticalViewPager;

import java.util.ArrayList;
import java.util.List;

import me.dkzwm.widget.srl.RefreshingListenerAdapter;
import me.dkzwm.widget.srl.SmoothRefreshLayout;
import me.dkzwm.widget.srl.extra.footer.ClassicFooter;
import me.dkzwm.widget.srl.extra.header.ClassicHeader;
import okhttp3.Call;


public class VideoPlayActivity extends BaseActivity {

    @BindView(R.id.refresh_layout)
    SmoothRefreshLayout refreshLayout;
    @BindView(R.id.classicFooter_with_listView)
    ClassicFooter mClassicFooter;
    @BindView(R.id.classicHeader_with_listView)
    ClassicHeader mClassicHeader;
    @BindView(R.id.view_pager)
    VerticalViewPager viewPager;


    private List<Videoinfo> videoinfolist;
    private List<Fragment> fragments;
    private FragAdapter adapter;
    private String url;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mClassicHeader.setLastUpdateTimeKey("header_last_update_time");
        mClassicFooter.setLastUpdateTimeKey("footer_last_update_time");
        mClassicHeader.setTitleTextColor(Color.WHITE);
        mClassicHeader.setLastUpdateTextColor(Color.GRAY);

        refreshLayout.setEnableKeepRefreshView(true);
        refreshLayout.setDisableLoadMore(true);
        refreshLayout.setDisableRefresh(true);
        refreshLayout.setEnableScrollToBottomAutoLoadMore(true);
        refreshLayout.setOnRefreshListener(new RefreshingListenerAdapter() {
            @Override
            public void onRefreshBegin(boolean isRefresh) {
                if (!isRefresh && canPull) {
                    canPull = false;
                    loadMore();
                } else {
                    refreshLayout.refreshComplete(2000);
                }
            }
        });

        Bundle bundle = getIntent().getExtras();
        int pos = bundle.getInt("id");
        videoinfolist = (List<Videoinfo>) bundle.getSerializable("vlist");
        url = bundle.getString("url");

        //构造适配器
        fragments = new ArrayList<>();
        for (int i = 0; i < videoinfolist.size(); i++) {
            if(videoinfolist.get(i).getStatus() == 0){

            }else {
                fragments.add(new VideoPlayFragment(videoinfolist.get(i), i));
            }
        }


        adapter = new FragAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(pos);

        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == fragments.size() - 2) {
                    loadMore();
                }
            }
        });

        MsgOperReciver msgOperReciver = new MsgOperReciver();
        IntentFilter intentFilter = new IntentFilter("videoinfo");
        registerReceiver(msgOperReciver, intentFilter);
    }

    private void loadMore() {
        OkHttpUtils
                .post(this)
                .url(url)
                .addParams("param1", Util.userid)
                .addParams("param2", videoinfolist.size() / 11 + 1)
                .addParams("param3", videoinfolist.get(0).getId())
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        refreshLayout.setEnableLoadMoreNoMoreData(false);
                        refreshLayout.refreshComplete(2000);
                        refreshLayout.setDisableLoadMore(true);
                        refreshLayout.setDisableRefresh(true);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        List<Videoinfo> list1 = JSON.parseArray(response, Videoinfo.class);
                        if (list1 == null || list1.size() == 0) {
                            refreshLayout.setEnableLoadMoreNoMoreData(false);
                            refreshLayout.refreshComplete(2000);
                            refreshLayout.setDisableLoadMore(true);
                            refreshLayout.setDisableRefresh(true);
                            return;
                        }
                        for (int i = 0; i < list1.size(); i++) {
                            if(videoinfolist.get(i).getStatus() == 0){

                            }else {
                                fragments.add(new VideoPlayFragment(list1.get(i), videoinfolist.size() + i));
                            }
                        }

                        adapter.notifyDataSetChanged();
                        videoinfolist.addAll(list1);
                        refreshLayout.setEnableLoadMoreNoMoreData(true);
                        refreshLayout.refreshComplete(2000);
                    }

                });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_videoplay;
    }

    @Override
    public int statusBarColor() {
        return R.color.transparent;
    }

    @Override
    public boolean statusBarFont() {
        return false;
    }

    private boolean canPull = true;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 260:
                    refreshLayout.setDisableLoadMore(false);
                    refreshLayout.setDisableRefresh(true);
                    break;
                case 200:
                    refreshLayout.setDisableLoadMore(true);
                    refreshLayout.setDisableRefresh(false);
                    break;
                case 230:
                    refreshLayout.setDisableLoadMore(true);
                    refreshLayout.setDisableRefresh(true);
                    break;
            }
        }
    };

    private class MsgOperReciver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int msgBody = intent.getIntExtra("downorup", 0);
            if (msgBody == 0) {
                handler.sendMessage(handler.obtainMessage(200, (Object) msgBody));
            } else if (msgBody == (videoinfolist.size() - 1)) {
                handler.sendMessage(handler.obtainMessage(260, (Object) msgBody));
            } else {
                handler.sendMessage(handler.obtainMessage(230, (Object) msgBody));
            }
        }
    }


    public class FragAdapter extends FragmentPagerAdapter {

        private List<Fragment> mFragments;

        public FragAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            mFragments = fragments;
        }

        @Override
        public Fragment getItem(int arg0) {
            return mFragments.get(arg0);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public int getItemPosition(Object object) {
            return PagerAdapter.POSITION_NONE;
        }
    }
}
