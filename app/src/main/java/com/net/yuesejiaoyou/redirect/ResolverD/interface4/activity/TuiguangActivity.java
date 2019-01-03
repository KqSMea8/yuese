package com.net.yuesejiaoyou.redirect.ResolverD.interface4.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.redirect.ResolverC.interface4.RoundImageView;
import com.net.yuesejiaoyou.redirect.ResolverC.interface4.tuiguangrenshu_Adapter_01152;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.Page;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.Vliao1_01168;
import com.net.yuesejiaoyou.redirect.ResolverC.interface3.UsersThread_01168C;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.BaseActivity;

import java.util.ArrayList;


public class TuiguangActivity extends BaseActivity implements OnClickListener {
    private ImageView back;
    private RoundImageView touxiang;

    private RelativeLayout quyu1, quyu2;
    private TextView wenben1, wenben2, wenben3, wenben4;


    /*private RelativeLayout fanhuizong;*/
    private ListView l1;
    private Context context;
    ArrayList<Vliao1_01168> list1 = new ArrayList<Vliao1_01168>();
    ArrayList<Vliao1_01168> list2 = new ArrayList<Vliao1_01168>();

    private tuiguangrenshu_Adapter_01152 adapter1;
    private int pageno = 1;
    private int totlepage;
    private boolean canPull = true;
    private int lastVisibleItem = 0;

    private int status = 0;
    //private String status2 = "0";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        context = this;
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
            /*fanhuizong = (RelativeLayout) findViewById(R.id.fanhuizong);
			fanhuizong.setOnClickListener(this);*/
        l1 = (ListView) findViewById(R.id.l1);

        quyu1 = (RelativeLayout) findViewById(R.id.quyu1);
        quyu1.setOnClickListener(this);
        quyu2 = (RelativeLayout) findViewById(R.id.quyu2);
        quyu2.setOnClickListener(this);
        wenben1 = (TextView) findViewById(R.id.wenben1);
        wenben2 = (TextView) findViewById(R.id.wenben2);
        wenben3 = (TextView) findViewById(R.id.wenben3);
        wenben4 = (TextView) findViewById(R.id.wenben4);

        people_num();


    }

    @Override
    protected int getContentView() {
        return R.layout.activity_tuiguang;
    }

    public void quyu1_request() {
        String mode = "fenxiao1_request";
        String[] params = {Util.userid, pageno + ""};
        UsersThread_01168C b = new UsersThread_01168C(mode, params, handler);
        Thread thread = new Thread(b.runnable);
        thread.start();

    }

    public void quyu2_request() {
        String mode = "fenxiao2_request";
        String[] params = {Util.userid, pageno + ""};
        UsersThread_01168C b = new UsersThread_01168C(mode, params, handler);
        Thread thread = new Thread(b.runnable);
        thread.start();

    }

    public void people_num() {
        String mode = "pepele_num";
        String[] params = {Util.userid};
        UsersThread_01168C b = new UsersThread_01168C(mode, params, handler);
        Thread thread = new Thread(b.runnable);
        thread.start();

    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 216:
                    //LogDetect.send(LogDetect.DataType.specialType, "Aiba_wodefensi_01168","返回数据");
                    //list1=(ArrayList<Meijian1_01168>) msg.obj;
                    Page page = (Page) msg.obj;
                    totlepage = page.getTotlePage();
                    pageno = page.getPageNo();
                    pageno = page.getCurrent();

                    if (list1 == null) {
                        //Toast.makeText(Meijian_wodefensi_01168.this, "集合为空", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (pageno == 1 || pageno == 0) {
                        list1 = (ArrayList<Vliao1_01168>) page.getList();
                        adapter1 = new tuiguangrenshu_Adapter_01152(TuiguangActivity.this, l1, list1);
                        l1.setAdapter(adapter1);

                        l1.setOnScrollListener(new AbsListView.OnScrollListener() {
                            @Override
                            public void onScrollStateChanged(AbsListView absListView, int i) {
                                if (i == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                                    if (lastVisibleItem + 1 == adapter1.getCount()) {
                                        if (pageno == totlepage) {
                                        } else {
                                            if (canPull) {
                                                canPull = false;
                                                pageno++;
                                                if (status == 0) {
                                                    quyu1_request();
                                                } else {
                                                    quyu2_request();
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                                lastVisibleItem = absListView.getLastVisiblePosition();
                            }
                        });

                    } else {
                        ArrayList<Vliao1_01168> list2 = new ArrayList<Vliao1_01168>();
                        list2 = (ArrayList<Vliao1_01168>) page.getList();
                        for (int i1 = 0; i1 < list2.size(); i1++) {
                            list1.add(list2.get(i1));
                        }
                        adapter1.notifyDataSetChanged();
                        canPull = true;

                    }
                    break;
                case 217:

                    list1 = (ArrayList<Vliao1_01168>) msg.obj;

                    wenben2.setText("共" + list1.get(0).getPeople_num().toString() + "人");
                    wenben4.setText("共" + list1.get(0).getAccount_name().toString() + "人");
                    //init();
                    quyu1.performClick();
                    break;


            }
        }

    };

    @Override
    public void onClick(View arg0) {
        int id = arg0.getId();
        switch (id) {
            case R.id.back:
                finish();
                break;
            case R.id.quyu1:
                status = 0;
                pageno = 0;
                //if(status1.equals("0")){
                //显示非主播用户信息
                qy1_visible();
                qy2_gone();
                //status1 = "1";
                quyu1_request();

                //}else if(status1.equals("1")){
                //显示全部
                //	qy1_gone();
                //	qy2_gone();
                //	status1 = "0";
                //init();
                //}
                break;
            case R.id.quyu2:
                status = 1;
                pageno = 0;
                //if(status2.equals("0")){
                //显示非主播用户信息
                qy2_visible();
                qy1_gone();
                //status2 = "1";
                quyu2_request();
                //}else if(status2.equals("1")){
                //显示全部
                //	qy1_gone();
                //	qy2_gone();
                //	status2 = "0";
                //	init();
                //}

                break;

        }

    }


    private void qy1_gone() {
        //dier.setBackgroundResource(R.drawable.qiandao02);
        //fenxiao_bak01	未选中 文本颜色为 蓝色
        //quyu1.setBackgroundColor(Color.GRAY); //.setBackground(R.drawable.fenxiao_bak01);
        wenben1.setTextColor(Color.BLACK);
        wenben2.setTextColor(Color.BLACK);
    }

    private void qy1_visible() {
        //fenxiao_bak02	选中 文本颜色为 白色
        //quyu1.setBackgroundResource(R.drawable.fenxiao_bak02);
        //quyu1.setBackground(R.drawable.fenxiao_bak02);
        //quyu1.setBackgroundColor(Color.BLUE);
        wenben1.setTextColor(Color.BLUE);
        wenben2.setTextColor(Color.BLUE);
    }

    private void qy2_gone() {
        //dier.setBackgroundResource(R.drawable.qiandao02);
        //fenxiao_bak01	未选中 文本颜色为 蓝色
        //quyu2.setBackground(R.drawable.fenxiao_bak01);
        //quyu2.setBackgroundColor(Color.GRAY);
        wenben3.setTextColor(Color.BLACK);
        wenben4.setTextColor(Color.BLACK);
    }

    private void qy2_visible() {
        //fenxiao_bak02	选中 文本颜色为 白色
        //quyu2.setBackground(R.drawable.fenxiao_bak02);
        //quyu2.setBackgroundColor(Color.BLUE);
        wenben3.setTextColor(Color.BLUE);
        wenben4.setTextColor(Color.BLUE);
    }


}
