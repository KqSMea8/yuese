package com.net.yuesejiaoyou.redirect.ResolverD.interface4.activity;


import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.redirect.ResolverB.getset.Page;
import com.net.yuesejiaoyou.redirect.ResolverB.getset.Videoinfo;
import com.net.yuesejiaoyou.redirect.ResolverB.interface3.UsersThread_01066B;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.fragment.VideoPlayFragment;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.BaseActivity;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import butterknife.BindView;
import fr.castorflex.android.verticalviewpager.VerticalViewPager;

import java.util.ArrayList;
import java.util.List;

import me.dkzwm.widget.srl.RefreshingListenerAdapter;
import me.dkzwm.widget.srl.SmoothRefreshLayout;
import me.dkzwm.widget.srl.extra.footer.ClassicFooter;
import me.dkzwm.widget.srl.extra.header.ClassicHeader;


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
                    pageno++;
                    canPull = false;
                    initdata();
                } else {
                    refreshLayout.refreshComplete(2000);
                }
            }
        });

        Bundle bundle = getIntent().getExtras();
        int pos = bundle.getInt("id");
        videoinfolist = (List<Videoinfo>) bundle.getSerializable("vlist");

        pageno = videoinfolist.size() % 10 + 1;
        maxid = videoinfolist.get(0).getId();
        //构造适配器
        fragments = new ArrayList<>();
        for (int i = 0; i < videoinfolist.size(); i++) {
            fragments.add(new VideoPlayFragment(videoinfolist.get(i), i));
        }


        adapter = new FragAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(pos);

        MsgOperReciver msgOperReciver = new MsgOperReciver();
        IntentFilter intentFilter = new IntentFilter("videoinfo");
        registerReceiver(msgOperReciver, intentFilter);
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

    private int pageno = 1, maxid, totlepage;
    private boolean canPull = true;

    public void initdata() {
        String mode = "hotvideolist";
        //userid，页数，男女
        String[] params = {"13", pageno + "", maxid + ""};
        UsersThread_01066B b = new UsersThread_01066B(mode, params, handler);
        Thread thread = new Thread(b.runnable);
        thread.start();
    }

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
                case 201:
                    Page list = (Page) msg.obj;
                    if (list == null) {
                        refreshLayout.refreshComplete();
                        return;
                    }
                    pageno = list.getCurrent();
                    totlepage = list.getTotlePage();
                    List<Videoinfo> a = new ArrayList<Videoinfo>();
                    a = list.getList();
                    if (a == null) {
                        refreshLayout.refreshComplete();
                        return;
                    } else {
                        if (a.size() == 0) {
                            refreshLayout.setEnableLoadMoreNoMoreData(true);
                            refreshLayout.refreshComplete(2000);
                        } else {
                            for (int i = 0; i < a.size(); i++) {
                                videoinfolist.add(a.get(i));
                            }
                            fragments.clear();
                            for (int i = 0; i < videoinfolist.size(); i++) {
                                fragments.add(new VideoPlayFragment(videoinfolist.get(i), i));
                            }
                            adapter.notifyDataSetChanged();
                            canPull = true;
                            refreshLayout.setEnableLoadMoreNoMoreData(false);
                            refreshLayout.refreshComplete(2000);
                            refreshLayout.setDisableLoadMore(true);
                            refreshLayout.setDisableRefresh(true);
                        }
                    }
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
