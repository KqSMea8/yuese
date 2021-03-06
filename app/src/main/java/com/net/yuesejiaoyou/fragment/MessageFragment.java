package com.net.yuesejiaoyou.fragment;

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
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.activity.CoustomerActivity;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.bean.Session;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.db.Const;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.db.SessionDao;
import com.net.yuesejiaoyou.activity.ChatActivity;

//////////////////A区对接C区
import com.net.yuesejiaoyou.activity.CallHistoryActivity;
import com.net.yuesejiaoyou.activity.AppariseActivity;
import com.net.yuesejiaoyou.activity.UserActivity;
import com.net.yuesejiaoyou.activity.YuyueActivity;
import com.net.yuesejiaoyou.activity.RecomeActivity;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.URL;
import com.net.yuesejiaoyou.adapter.MessageAdapter;
import com.net.yuesejiaoyou.utils.LogUtil;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.DialogCallback;

import okhttp3.Call;

@SuppressLint("NewApi")
public class MessageFragment extends Fragment implements OnClickListener {
    private String id;
    private boolean isGetData = false;

    private View mBaseView;
    private LinearLayout rl1, rl, rl11, rl111;
    private MsgOperReciver msgOperReciver;
    private SessionDao sessionDao;
    private PopupWindow popupWindow;
    private List<Session> sessionList = new ArrayList<Session>();
    private TextView th_note, vb_note, pj_note, yy_note;
    private TextView chattip;
    private RecyclerView recyclerView;
    private MessageAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBaseView = inflater.inflate(R.layout.fragment_message, null);
        SharedPreferences share = getActivity().getSharedPreferences("Acitivity", Activity.MODE_PRIVATE);
        id = share.getString("userid", "");

        recyclerView = mBaseView.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext())
                .color(Color.parseColor("#F2F2F2"))
                .build());
        adapter = new MessageAdapter(getContext(), sessionList);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                showPopupspWindow2(mBaseView, position);
                return true;
            }
        });
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter1, View view, int position) {
                final Session session = adapter.getData().get(position);
                LogUtil.i("ttt", session.toString());
                if (session.getTo().equals("40")) {
                    Intent intent = new Intent(getContext(), CoustomerActivity.class);
                    intent.putExtra("id", session.getFrom());
                    intent.putExtra("name", session.getName());
                    intent.putExtra("headpic", session.getHeadpic());
                    startActivity(intent);
                } else {
                    if (session.getFrom().equals("40")) {
                        Intent intent = new Intent(getContext(), CoustomerActivity.class);
                        intent.putExtra("id", "40");
                        intent.putExtra("name", "小客服");
                        intent.putExtra("headpic", "http://116.62.220.67:8090/img/imgheadpic/launch_photo.png");
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(getContext(), ChatActivity.class);
                        intent.putExtra("id", session.getFrom());
                        intent.putExtra("name", session.getName());
                        intent.putExtra("headpic", session.getHeadpic());
                        startActivity(intent);
                    }
                }
            }
        });
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter1, View view, int position) {
                Intent intent = new Intent(getContext(), UserActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", "" + adapter.getData().get(position).getFrom());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        rl = mBaseView.findViewById(R.id.rl);
        //设置点击监听
        rl.setOnClickListener(this);

        rl1 = mBaseView.findViewById(R.id.rl1);
        //设置点击监听
        rl1.setOnClickListener(this);

        rl11 = mBaseView.findViewById(R.id.rl11);
        //设置点击监听
        rl11.setOnClickListener(this);

        rl111 = mBaseView.findViewById(R.id.rl111);

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
        sessionDao = new SessionDao(getContext());
        msgOperReciver = new MsgOperReciver();
        IntentFilter intentFilter1 = new IntentFilter(Const.ACTION_MSG_OPER);
        IntentFilter intentFilter2 = new IntentFilter(Const.ACTION_ADDFRIEND);
        getContext().registerReceiver(msgOperReciver, intentFilter1);
        getContext().registerReceiver(msgOperReciver, intentFilter2);
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

        initNetData();

        initData(id);

        return mBaseView;
    }

    private void initNetData() {
        OkHttpUtils.post(this)
                .url(URL.URL_XIAOXI)
                .addParams("param1", Util.userid)
                .build()
                .execute(new DialogCallback(getActivity(), false) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String resultBean, int id) {
                        if (TextUtils.isEmpty(resultBean)) {
                            return;
                        }
                        try {

                            JSONObject jsonObject1 = new JSONObject(resultBean);
                            String success_ornot = jsonObject1.getString("success");
                            String name = jsonObject1.getString("name");
                            String vb = jsonObject1.getString("vb");
                            String pj = jsonObject1.getString("pj");
                            String yuyue = jsonObject1.getString("yuyue");

                            if (!TextUtils.isEmpty(success_ornot)) {
                                th_note.setText(success_ornot + ":" + name);
                                if (success_ornot.equals("未接通")) {
                                    th_note.setTextColor(Color.parseColor("#e0E61718"));
                                } else {
                                    th_note.setTextColor(Color.parseColor("#e047F0AF"));
                                }
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

                    }
                });
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            //点击我的通话，跳转
            case R.id.rl:
                Intent intent = new Intent();
                intent.setClass(getActivity(), CallHistoryActivity.class);
                startActivity(intent);
                break;
            //点击我的v币，跳转
            case R.id.rl1:
                intent = new Intent();
                intent.setClass(getActivity(), RecomeActivity.class);
                startActivity(intent);
                break;
            //点击预约，跳转
            case R.id.rl11:
                intent = new Intent();
                intent.setClass(getActivity(), YuyueActivity.class);
                startActivity(intent);
                break;
            //点击我的评价，跳转
            case R.id.rl111:
                intent = new Intent();
                intent.setClass(getActivity(), AppariseActivity.class);
                startActivity(intent);
                break;
        }

    }


    private void initData(String shopid) {
        sessionList = sessionDao.queryAllSessions(shopid);
        sessionList = removeDuplicate(sessionList);
        adapter.setNewData(sessionList);
        LogUtil.i("tttt", "-------" + sessionList.size());
    }

    public List<Session> removeDuplicate(List<Session> list) {
        List<Session> datas = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            boolean flag = true;
            for (int j = i + 1; j < list.size(); j++) {
                if (list.get(j).getTo().equals(list.get(i).getTo()) && list.get(j).getFrom().equals(list.get(i).getFrom())) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                datas.add(list.get(i));
            }
        }
        return datas;
    }


    //点击拒绝此条邀请
    public void showPopupspWindow2(View parent, final int position) {
        // 加载布局
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View layout = inflater.inflate(R.layout.pop_del_01160, null);


        TextView eat = (TextView) layout.findViewById(R.id.eat);
        eat.setText(sessionList.get(position).getName());


        TextView quxiao = (TextView) layout.findViewById(R.id.quxiao);
        quxiao.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Session s = new Session();
                s = sessionList.get(position);
                sessionList.remove(position);
                sessionDao.deleteSession(s);
                sessionDao.queryAllSessions(id);
                //lv.setAdapter(adapter);
                popupWindow.dismiss();
                adapter.notifyDataSetChanged();

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
        WindowManager manager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
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


    @Override
    public void onResume() {
        super.onResume();
        if (!isGetData) {
            initNetData();
            isGetData = true;
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        isGetData = false;
    }

    private class MsgOperReciver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    initData(id);
                }
            });
        }
    }
}
