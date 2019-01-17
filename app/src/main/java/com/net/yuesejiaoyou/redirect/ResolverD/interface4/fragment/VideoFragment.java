package com.net.yuesejiaoyou.redirect.ResolverD.interface4.fragment;


import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.redirect.ResolverA.getset.Page;
import com.net.yuesejiaoyou.redirect.ResolverA.getset.User_data;
import com.net.yuesejiaoyou.redirect.ResolverB.getset.Videoinfo;
import com.net.yuesejiaoyou.redirect.ResolverA.interface3.UsersThread_01066A;
import com.net.yuesejiaoyou.redirect.ResolverA.interface4.VideoMyAdapterinfo_01066;


import java.util.ArrayList;
import java.util.List;


@SuppressLint("ValidFragment")
public class VideoFragment extends Fragment implements OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private Context mContext;
    String sort;
    private RecyclerView gridView;
    private List<Videoinfo> articles = new ArrayList<Videoinfo>();
    private View view;
    private int pageno = 1, totlepage = 0;
    private boolean canPull = true;
    private int pos;
    private TextView shaixuan;
    private boolean isinit = false;
    private boolean iskai = false;
    private TextView norecord;
    VideoMyAdapterinfo_01066 adapter;
    SwipeRefreshLayout refreshLayout;
    MsgOperReciver2 msgOperReciver2;
    String zhubo_id;
    private User_data userinfo;
    private MsgOperReciver1 msgOperReciver1;

    public VideoFragment() {
    }

    public VideoFragment(User_data userinfo) {
        this.userinfo = userinfo;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        view = inflater.inflate(R.layout.videofragment1_01066, null);
        LogDetect.send(LogDetect.DataType.specialType, "this0", "??");
        gridView = (RecyclerView) view.findViewById(R.id.theme_grre);
        norecord = (TextView) view.findViewById(R.id.no_record);
        if (userinfo == null) {
            zhubo_id = "0";
        } else {
            zhubo_id = userinfo.getId() + "";
        }
        pageno = 1;

        String mode = "videolist";
        //userid，页数，男女
        String[] params = {"13", pageno + "", zhubo_id};
        UsersThread_01066A b = new UsersThread_01066A(mode, params, handler);
        Thread thread = new Thread(b.runnable);
        thread.start();
        msgOperReciver1 = new MsgOperReciver1();
        IntentFilter intentFilter = new IntentFilter("payinfo");
        getActivity().registerReceiver(msgOperReciver1, intentFilter);
        msgOperReciver2 = new MsgOperReciver2();
        IntentFilter intentFilter2 = new IntentFilter("dianzan");
        getActivity().registerReceiver(msgOperReciver2, intentFilter2);
        return view;
    }

    private class MsgOperReciver2 extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int msgBody = intent.getIntExtra("dianzan", 0);
            int iszan = intent.getIntExtra("iszan", 0);
            LogDetect.send(LogDetect.DataType.specialType, "downorup:", msgBody);

            if (msgBody < articles.size()) {
                //articles.get(msgBody).setIspay(2);
                int num = Integer.parseInt(articles.get(msgBody).getLike_num());
                if (iszan == 0) {
                    articles.get(msgBody).setLike_num((num - 1) + "");
                } else {
                    articles.get(msgBody).setLike_num((num + 1) + "");
                }
                handler.sendMessage(handler.obtainMessage(0, (Object) msgBody));
            }
        }
    }

    private class MsgOperReciver1 extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int msgBody = intent.getIntExtra("paysuccess", 0);
            LogDetect.send(LogDetect.DataType.specialType, "downorup:", msgBody);
            if (msgBody < articles.size()) {
                articles.get(msgBody).setIspay(2);
                handler.sendMessage(handler.obtainMessage(0, (Object) msgBody));
            }
        }
    }

    public void initdata() {
        String mode = "videolist";
        //userid，页数，男女
        String[] params = {"13", pageno + "", zhubo_id};
        UsersThread_01066A b = new UsersThread_01066A(mode, params, handler);
        Thread thread = new Thread(b.runnable);
        thread.start();
    }


    @Override
    public void onRefresh() {
        // 设置可见
        refreshLayout.setRefreshing(true);
        pageno = 1;
        initdata();

    }

    private GridLayoutManager mLayoutManager;
    private int lastVisibleItem = 0;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    String pos = msg.obj.toString();
                    adapter.notifyItemChanged(Integer.valueOf(pos));
                    break;
                case 201:
                    Page list = (Page) msg.obj;
                    pageno = list.getCurrent();
                    totlepage = list.getTotlePage();
                    if (totlepage == 0) {
                        norecord.setVisibility(View.VISIBLE);
                        gridView.setVisibility(View.GONE);
                    }
                    if (pageno == 1) {
                        articles = list.getList();
                        adapter = new VideoMyAdapterinfo_01066(null, mContext, false, articles);
                        if (totlepage == 1) {
                            adapter.setFadeTips(true);
                        } else {
                            adapter.setFadeTips(false);
                        }
                        mLayoutManager = new GridLayoutManager(mContext, 2);
                        gridView.setLayoutManager(mLayoutManager);
                        gridView.setAdapter(adapter);
                        gridView.setItemAnimator(new DefaultItemAnimator());

                        gridView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                            @Override
                            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                                super.onScrollStateChanged(recyclerView, newState);
                                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                                    if (lastVisibleItem + 1 == adapter.getItemCount()) {
                                        if (pageno == totlepage) {

                                        } else {
                                            if (canPull) {
                                                canPull = false;
                                                pageno++;
                                                initdata();
                                            }
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                                super.onScrolled(recyclerView, dx, dy);
                                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
                            }
                        });
                    } else {
                        List<Videoinfo> a = new ArrayList<Videoinfo>();
                        a = list.getList();
                        for (int i = 0; i < a.size(); i++) {
                            articles.add(a.get(i));
                        }
                        adapter.notifyDataSetChanged();
                        canPull = true;
                        if (totlepage == pageno) {
                            adapter.setFadeTips(true);
                        } else {
                            adapter.setFadeTips(false);
                        }
                    }


                    break;
            }
        }
    };

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(msgOperReciver1);
        getActivity().unregisterReceiver(msgOperReciver2);

    }
}
