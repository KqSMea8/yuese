package com.net.yuesejiaoyou.redirect.ResolverA.uiface;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.bean.Session;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.db.Const;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.db.SessionDao;
import com.net.yuesejiaoyou.classroot.interface4.openfire.interface4.SessionAdapter;
import com.net.yuesejiaoyou.classroot.interface4.openfire.uiface.ChatActivity;
import com.net.yuesejiaoyou.classroot.interface4.openfire.uiface.ChatActivity_KF;

import com.net.yuesejiaoyou.redirect.ResolverA.interface3.UsersThread_01160A;
//////////////////A区对接C区
import com.net.yuesejiaoyou.redirect.ResolverC.uiface.Myphone_01162;
import com.net.yuesejiaoyou.redirect.ResolverC.uiface.My_pingjia_01165;
import com.net.yuesejiaoyou.redirect.ResolverC.uiface.Yuyue_01165;
import com.net.yuesejiaoyou.redirect.ResolverC.uiface.My_v_01165;
/////////////////////////


/**************************************
 *VLFragment_News_01160类继承Fragment类实现OnClickListener接口
 **************************************/
@SuppressLint("NewApi")
public class MessageFragment extends Fragment implements OnClickListener {
    private String id;
    private boolean isGetData = false;

    private Intent intent;
    private View mBaseView;
    private Context mContext;
    private RelativeLayout rl1, rl, rl11/*,rl4*/, RL, rl111;
    private MsgOperReciver msgOperReciver;
    private SessionDao sessionDao;
    private SessionAdapter adapter;
    private PopupWindow popupWindow;
    private List<Session> sessionList = new ArrayList<Session>();
    private ListView lv;
    private TextView th_note, vb_note, pj_note, yy_note;
    private TextView chattip;

    /**********************************
     *创建视图
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     *********************************/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ////////////////////////
        mContext = getActivity();
        ////////////////////////
        mBaseView = inflater.inflate(R.layout.fragment_mail_01160, null);
        ////////////////////////
        SharedPreferences share = mContext.getSharedPreferences("Acitivity", Activity.MODE_PRIVATE);
        id = share.getString("userid", "");

        RL = (RelativeLayout) mBaseView.findViewById(R.id.RL);


        rl = (RelativeLayout) mBaseView.findViewById(R.id.rl);
        //设置点击监听
        rl.setOnClickListener(this);

        rl1 = (RelativeLayout) mBaseView.findViewById(R.id.rl1);
        //设置点击监听
        rl1.setOnClickListener(this);

        rl11 = (RelativeLayout) mBaseView.findViewById(R.id.rl11);
        //设置点击监听
        rl11.setOnClickListener(this);

        rl111 = (RelativeLayout) mBaseView.findViewById(R.id.rl111);

        if (Util.iszhubo.equals("1")) {
            //设置是否可用gone为
            rl111.setVisibility(View.VISIBLE);
            //设置点击监听
            rl111.setOnClickListener(this);
        } else {
            //设置可用性为gone
            rl111.setVisibility(View.GONE);
        }

		/*rl4 = (RelativeLayout)mBaseView.findViewById(R.id.rl4);
		rl4.setOnClickListener(this);*/
        /////////////////////接收聊天,消息页面下面的适配
        sessionDao = new SessionDao(mContext);
        msgOperReciver = new MsgOperReciver();
        IntentFilter intentFilter1 = new IntentFilter(Const.ACTION_MSG_OPER);
        IntentFilter intentFilter2 = new IntentFilter(Const.ACTION_ADDFRIEND);
        mContext.registerReceiver(msgOperReciver, intentFilter1);
        mContext.registerReceiver(msgOperReciver, intentFilter2);
        //////////////////////////////////////////

        th_note = (TextView) mBaseView.findViewById(R.id.th_note);

        vb_note = (TextView) mBaseView.findViewById(R.id.vb_note);

        pj_note = (TextView) mBaseView.findViewById(R.id.pj_note);

        yy_note = (TextView) mBaseView.findViewById(R.id.yy_note);

        // 如果是普通男号用户，则显示私信每条0.1悦币提示
        chattip = (TextView) mBaseView.findViewById(R.id.txt_tip);
        if ("0".equals(Util.iszhubo)) {
            chattip.setVisibility(View.VISIBLE);
        } else {
            chattip.setVisibility(View.GONE);
        }

        lv = (ListView) mBaseView.findViewById(R.id.lv);
        //长按弹窗,删除一条
        lv.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                showPopupspWindow2(RL, position);
                return true;
            }
        });

        ///////////////////////设置点击监听，A区使用openfire接收聊天信息
        lv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                final Session session = sessionList.get(arg2);
                if (session.getTo().equals("40")) {
                    Intent intent = new Intent(mContext, ChatActivity_KF.class);
                    intent.putExtra("id", session.getFrom());
                    intent.putExtra("name", session.getName());
                    intent.putExtra("headpic", session.getHeadpic());
                    startActivity(intent);
                } else {
                    if (session.getFrom().equals("40")) {
                        Intent intent = new Intent(mContext, ChatActivity_KF.class);
                        intent.putExtra("id", "40");
                        intent.putExtra("name", "小客服");
                        intent.putExtra("headpic", "http://116.62.220.67:8090/img/imgheadpic/launch_photo.png");
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(mContext, ChatActivity.class);
                        intent.putExtra("id", session.getFrom());
                        intent.putExtra("name", session.getName());
                        intent.putExtra("headpic", session.getHeadpic());
                        startActivity(intent);
                    }
                }


            }
        });

        initNetData();

        init();

        return mBaseView;
    }

    /************************
     * 初始化数据并创建线程
     **********************/
    private void initNetData() {
        String mode = "xiaoxi";
        String[] paramsMap = {Util.userid};
        //实例化线程t,传递参数mode,paramsMap,handler;
        UsersThread_01160A t = new UsersThread_01160A(mode, paramsMap, handler);
        Thread b = new Thread(t.runnable);
        b.start();
    }


    /************************
     *点击事件
     * @param v
     ************************/
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            //点击我的通话，跳转
            case R.id.rl:
                Intent intent = new Intent();
                intent.setClass(mContext, Myphone_01162.class);
                startActivity(intent);
                break;
            //点击我的v币，跳转
            case R.id.rl1:
                intent = new Intent();
                intent.setClass(mContext, My_v_01165.class);
                startActivity(intent);
                break;
            //点击预约，跳转
            case R.id.rl11:
                intent = new Intent();
                intent.setClass(mContext, Yuyue_01165.class);
                startActivity(intent);
                break;
            //点击我的评价，跳转
            case R.id.rl111:
                intent = new Intent();
                intent.setClass(mContext, My_pingjia_01165.class);
                startActivity(intent);
                break;

        }

    }

    /*********************
     *返回数据
     ********************/
    private Handler handler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 20:
                    initData(id);
                    break;
                case 206:
                    //消息界面
                    String json = (String) msg.obj;
                    if (!json.isEmpty()) {
                        try {

                            JSONObject jsonObject1 = new JSONObject(json);
                            LogDetect.send(LogDetect.DataType.specialType, "01160:", jsonObject1);

                            String success_ornot = jsonObject1.getString("success");
                            LogDetect.send(LogDetect.DataType.specialType, "01160 通话记录:", success_ornot);

                            String name = jsonObject1.getString("name");

                            String vb = jsonObject1.getString("vb");
                            LogDetect.send(LogDetect.DataType.specialType, "01160 我的V币:", vb);

                            String pj = jsonObject1.getString("pj");
                            LogDetect.send(LogDetect.DataType.specialType, "01160 我的评价:", pj);

                            String yuyue = jsonObject1.getString("yuyue");
                            LogDetect.send(LogDetect.DataType.specialType, "01160 我的预约:", yuyue);

                            if (success_ornot.equals("未接通")) {
                                th_note.setTextColor(Color.parseColor("#e0E61718"));
                            } else {
                                th_note.setTextColor(Color.parseColor("#e047F0AF"));
                            }

                            if (success_ornot != null || success_ornot.length() != 0) {
                                th_note.setText(success_ornot + ":" + name);
                            }


                            if (vb.length() != 0) {
                                vb_note.setText(vb);
                            } else {
                                vb_note.setText("");
                            }
                            if (Util.iszhubo.equals("1")) {
                                if (pj.length() != 0) {
                                    pj_note.setText(pj + " 评论了你");
                                } else {
                                    pj_note.setText("");
                                }

                            }

                            yy_note.setText("我有 " + yuyue + " 个预约");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        //Toast.makeText(mContext, "请检查网络连接", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
            return false;
        }
    });


    /******************************
     * MsgOperReciver继承BroadcastReceiver接口
     *****************************/
    private class MsgOperReciver extends BroadcastReceiver {
        /********************
         * 接收方法
         * @param context
         * @param intent
         *******************/
        @Override
        public void onReceive(Context context, Intent intent) {
            init();
        }
    }

    /******************
     * 初始化方法并创建线程
     ******************/
    protected void init() {
        //实例化线程
        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                handler.sendMessage(handler.obtainMessage(20));
            }
        });
        t.start();
    }

    /*****************
     * 数据初始化
     * @param shopid
     ***************/
    private void initData(String shopid) {
        sessionList = sessionDao.queryAllSessions(shopid);
        LogDetect.send(LogDetect.DataType.specialType, "01160 sessionList：", sessionList);

        if (sessionList != null) {
            adapter = new SessionAdapter(mContext, sessionList, getActivity(), lv);
            lv.setAdapter(adapter);
        } else {
            Toast.makeText(mContext, "没有消息", Toast.LENGTH_SHORT).show();
        }
    }


    //点击拒绝此条邀请
    public void showPopupspWindow2(View parent, final int position) {
        // 加载布局
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View layout = inflater.inflate(R.layout.pop_del_01160, null);

        LogDetect.send(LogDetect.DataType.specialType, "01160 加载弹窗:", layout);

        TextView eat = (TextView) layout.findViewById(R.id.eat);
        eat.setText(sessionList.get(position).getName());

        LogDetect.send(LogDetect.DataType.specialType, "01160 用户名:", eat);


        TextView quxiao = (TextView) layout.findViewById(R.id.quxiao);
        quxiao.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Session s = new Session();
                s = sessionList.get(position);
                LogDetect.send(LogDetect.DataType.specialType, "01160 删除item:", s);
                sessionList.remove(position);
                sessionDao.deleteSession(s);
                sessionDao.queryAllSessions(id);
                lv.setAdapter(adapter);
                popupWindow.dismiss();

            }
        });

        popupWindow = new PopupWindow(layout, 520,
                LayoutParams.WRAP_CONTENT, true);
        // 控制键盘是否可以获得焦点
        popupWindow.setFocusable(true);
        // 设置popupWindow弹出窗体的背景
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = 0.4f;
        getActivity().getWindow().setAttributes(lp);
        popupWindow.setBackgroundDrawable(new BitmapDrawable(null, ""));
        WindowManager manager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        @SuppressWarnings("deprecation")
        // 获取xoff
                int xpos = manager.getDefaultDisplay().getWidth() / 2
                - popupWindow.getWidth() / 2;
        // xoff,yoff基于anchor的左下角进行偏移。
        //popupWindow.showAsDropDown(parent, 0, 0);
        popupWindow.showAtLocation(parent, Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
        // 监听

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            // 在dismiss中恢复透明度
            public void onDismiss() {
                WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                lp.alpha = 1f;
                getActivity().getWindow()
                        .addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                getActivity().getWindow().setAttributes(lp);
            }
        });

    }


    /************************
     *
     * @param transit
     * @param enter
     * @param nextAnim
     * @return
     *********************/
    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        //   进入当前Fragment
        if (enter && !isGetData) {
            isGetData = true;
            initNetData();
        } else {
            isGetData = false;
        }
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

    /***************
     *恢复方法
     ***************/
    @Override
    public void onResume() {
        super.onResume();
        if (!isGetData) {
            initNetData();
            isGetData = true;
        }
    }

    /**************
     * 暂停方法
     **************/
    @Override
    public void onPause() {
        super.onPause();
        isGetData = false;
    }

}
