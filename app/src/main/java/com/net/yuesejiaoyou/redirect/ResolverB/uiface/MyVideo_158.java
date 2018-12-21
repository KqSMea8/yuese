package com.net.yuesejiaoyou.redirect.ResolverB.uiface;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;


import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.redirect.ResolverB.getset.Page;
import com.net.yuesejiaoyou.redirect.ResolverB.getset.Videoinfo;
import com.net.yuesejiaoyou.redirect.ResolverB.interface3.UsersThread_01158B;
import com.net.yuesejiaoyou.redirect.ResolverB.interface3.UsersThread_01160B;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.VideoMyAdapter_01066;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;


/**
 * Created by Administrator on 2018/3/14.
 */

public class MyVideo_158 extends Activity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private ImageView back;
    private ImageView myvideoupload;
    private Context mContext;
    String sort;
    private RecyclerView gridView;
    private List<Videoinfo> articles = new ArrayList<Videoinfo>();
    private View view;
    private int pageno = 1, totlepage = 0;
    private boolean canPull = true;

    private TextView shaixuan;
    private boolean isinit=false;
    private boolean iskai=false;
    VideoMyAdapter_01066 adapter;
    SwipeRefreshLayout refreshLayout;
    private PopupWindow popupWindow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myvideo_158);
        mContext = MyVideo_158.this;
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        myvideoupload = (ImageView) findViewById(R.id.myvideoupload);
        myvideoupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MyVideo_158.this, UploadVideo_01158.class);//编辑资料
                startActivity(intent);
            }
        });

        gridView=(RecyclerView)findViewById(R.id.mytheme_grre);
        pageno=1;
        initdata();
        Reciever reciever=new Reciever();
		IntentFilter intentFilter=new IntentFilter("111");
		registerReceiver(reciever,intentFilter);
    }

    @Override
    public void onRefresh() {
        // 设置可见
        refreshLayout.setRefreshing(true);
        pageno=1;
        initdata();

    }


    public void initdata() {
//		pageno=1;
//
//		String mode = "videolist";
//		//userid，页数，男女
//		String[] params = {"13", pageno+"","1"};
//		UsersThread_01066 b = new UsersThread_01066(mode, params, handler);
//		Thread thread = new Thread(b.runnable);
//		thread.start();
		String mode = "getmyvideo";
		//userid，页数，男女
		String[] params = {Util.userid, pageno+"",Util.userid};
		UsersThread_01158B b = new UsersThread_01158B(mode, params, handler);
		Thread thread = new Thread(b.runnable);
		thread.start();
    }

    private GridLayoutManager mLayoutManager;
    private int lastVisibleItem = 0;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    break;
                case 201:
                    //refreshLayout.setRefreshing(false);
                    Page list = (Page) msg.obj;
                    pageno = list.getCurrent();
                    totlepage = list.getTotlePage();
                    if (pageno==1){
                        articles = list.getList();
                        adapter = new VideoMyAdapter_01066(null, mContext, false,articles);
                        if (totlepage==1){
                            adapter.setFadeTips(true);
                        }else{
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
                                        if (pageno==totlepage) {

                                        }else{
                                            if (canPull){
                                                canPull=false;
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
                    }else{

                        List<Videoinfo> a = new ArrayList<Videoinfo>();
                        a = list.getList();
                        for (int i=0;i<a.size();i++){
                            articles.add(a.get(i));
                        }
                        adapter.notifyDataSetChanged();
                        canPull=true;
                        if (totlepage==pageno){
                            adapter.setFadeTips(true);
                        }else{
                            adapter.setFadeTips(false);
                        }
                    }
                    adapter.setOnItemClickLitsener(new VideoMyAdapter_01066.onItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Intent intent = new Intent(MyVideo_158.this, VideoPlay_01066.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("id", position);
                            bundle.putSerializable("vlist",(Serializable)articles);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            LogDetect.send(LogDetect.DataType.specialType,"01160 video:","短时间按");
                        }

                        @Override
                        public void onItemLongClick(View view, int position) {
                            showPopupspWindow2(findViewById(R.id.zong),position);
                            LogDetect.send(LogDetect.DataType.specialType,"01160 video:","长时间按");
                        }
                    }) ;

                    break;
                case 211:
                    String json_reservation = (String) (msg).obj;
                    LogDetect.send(LogDetect.DataType.specialType,"01160:",msg);
                    if(!TextUtils.isEmpty(json_reservation)){
                        try {
                            JSONObject jsonObject1 = new JSONObject(json_reservation);
                            LogDetect.send(LogDetect.DataType.specialType,"01160:",jsonObject1);

                            String success_ornot=jsonObject1.getString("success");
                            LogDetect.send(LogDetect.DataType.specialType,"01160 success_ornot:",success_ornot);
                            if(success_ornot.equals("1")){
                                Toast.makeText(mContext, "删除成功", Toast.LENGTH_SHORT).show();
                            }
                        }catch(JSONException e){
                            e.printStackTrace();
                        }
                    }else{
                        Toast.makeText(mContext, "删除失败，请检查网络连接", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    public  class Reciever extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			String mode = "getmyvideo";
			//userid，页数，男女
			String[] params = {Util.userid, pageno+"","1"};
			UsersThread_01158B b = new UsersThread_01158B(mode, params, handler);
			Thread thread = new Thread(b.runnable);
			thread.start();
		}
	}



    //点击拒绝此条邀请
    public void showPopupspWindow2(View parent,final int position) {
        // 加载布局
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View layout = inflater.inflate(R.layout.pop_del_01160, null);

        LogDetect.send(LogDetect.DataType.specialType,"01160 加载弹窗:",layout);

        TextView eat = (TextView) layout.findViewById(R.id.eat);
        eat.setText(articles.get(position).getNickname());

        LogDetect.send(LogDetect.DataType.specialType,"01160 用户名:",eat);


        TextView quxiao = (TextView) layout.findViewById(R.id.quxiao);
        quxiao.setText("删除短视频");
        quxiao.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Videoinfo s = new Videoinfo();
                s = articles.get(position);
                LogDetect.send(LogDetect.DataType.specialType,"01160 删除item:",s);
                String[] paramsMap = {Util.userid,s.getVideo_id()+""};
                new Thread(new UsersThread_01160B("del_video",paramsMap,handler).runnable).start();
                adapter.removeItem(position);
                popupWindow.dismiss();

            }
        });

        popupWindow = new PopupWindow(layout, 520,
                ActionBar.LayoutParams.WRAP_CONTENT, true);
        // 控制键盘是否可以获得焦点
        popupWindow.setFocusable(true);
        // 设置popupWindow弹出窗体的背景
        WindowManager.LayoutParams lp =  MyVideo_158.this.getWindow().getAttributes();
        lp.alpha = 0.4f;
        MyVideo_158.this.getWindow().setAttributes(lp);
        popupWindow.setBackgroundDrawable(new BitmapDrawable(null, ""));
        WindowManager manager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        @SuppressWarnings("deprecation")
        // 获取xoff
                int xpos = manager.getDefaultDisplay().getWidth() / 2
                - popupWindow.getWidth() / 2;
        // xoff,yoff基于anchor的左下角进行偏移。
        //popupWindow.showAsDropDown(parent, 0, 0);
        popupWindow.showAtLocation(parent, Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL,0,0);
        // 监听

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            // 在dismiss中恢复透明度
            public void onDismiss() {
                WindowManager.LayoutParams lp = MyVideo_158.this.getWindow().getAttributes();
                lp.alpha = 1f;
                MyVideo_158.this.getWindow()
                        .addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                MyVideo_158.this.getWindow().setAttributes(lp);
            }
        });

    }



    @Override
    public void onClick(View view) {

    }
}
