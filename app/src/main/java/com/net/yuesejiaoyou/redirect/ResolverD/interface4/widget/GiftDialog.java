package com.net.yuesejiaoyou.redirect.ResolverD.interface4.widget;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.redirect.ResolverA.interface3.UsersThread_01162A;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.URL;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.activity.UserActivity;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.utils.Tools;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.DialogCallback;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class GiftDialog extends PopupWindow {

    public static final int CHARGE_RESULT = 551;

    Activity context;

    List<GiftBean> datas = new ArrayList<>();
    private View rootView;
    private GiftBean selectPresent;
    RecyclerView recyclerView;

    private SharedPreferences sp;

    private Gift2Adapter giftAdapter;

    OnGiftLishener lishener;
    private TextView tvYes;
    String userid;

    public GiftDialog(final Activity context, String userid) {
        super(context);
        this.context = context;
        setWidth(-1);
        setHeight(-2);

        this.userid = userid;

        sp = PreferenceManager.getDefaultSharedPreferences(context);

        initGiftData();
        initView();
    }


    private void initView() {
        rootView = View.inflate(context, R.layout.pop_gift, null);

        recyclerView = rootView.findViewById(R.id.recyclerView);
        tvYes = rootView.findViewById(R.id.songli_yes);

        recyclerView.setLayoutManager(new GridLayoutManager(context, 4));
        giftAdapter = new Gift2Adapter(datas);
        recyclerView.setAdapter(giftAdapter);

        giftAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                for (int i = 0; i < datas.size(); i++) {
                    datas.get(i).setSelect(false);
                }
                datas.get(position).setSelect(true);
                giftAdapter.notifyDataSetChanged();
                selectPresent = datas.get(position);
            }
        });


        setContentView(rootView);

        tvYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectPresent != null) {
                    sendgiftHttp(selectPresent.getGid());
                } else {
                    Toast.makeText(context, "请选择礼物", Toast.LENGTH_SHORT).show();
                }
            }
        });

        setOutsideTouchable(true);
        setFocusable(true);
        setBackgroundDrawable(context.getResources().getDrawable(R.drawable.gift_pop_bg2));
        rootView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                switch (keyCode) {
                    case KeyEvent.KEYCODE_BACK:
                        dismiss();
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void dismiss() {
        super.dismiss();
        Tools.backgroundAlpha(context, 1f);
        selectPresent = null;
        for (int i = 0; i < datas.size(); i++) {
            datas.get(i).setSelect(false);
        }
        giftAdapter.notifyDataSetChanged();
    }

    public void initGiftData() {
        datas.add(new GiftBean(1));
        datas.add(new GiftBean(6));
        datas.add(new GiftBean(7));
        datas.add(new GiftBean(8));
        datas.add(new GiftBean(9));
        datas.add(new GiftBean(10));
        datas.add(new GiftBean(11));
        datas.add(new GiftBean(12));
    }


    public void sendgiftHttp(final int gid) {
        OkHttpUtils
                .post(context)
                .url(URL.URL_SENDGIFT)
                .addParams("param1", Util.userid)
                .addParams("param2", userid)
                .addParams("param3", Tools.getGiftCount(gid))
                .build()
                .execute(new DialogCallback(context) {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(context, "赠送失败请重试", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        super.onResponse(response, id);
                        if (TextUtils.isEmpty(response)) {
                            Toast.makeText(context, "赠送失败请重试", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        try {
                            org.json.JSONObject jsonObject = new org.json.JSONObject(response);
                            if (jsonObject.getString("success").equals("1")) {
                                Toast.makeText(context, "打赏成功 ", Toast.LENGTH_SHORT).show();
                                if (lishener != null) {
                                    lishener.onSuccess(gid, 1);
                                } else {
                                    lishener.onFail();
                                }
                            } else {
                                Toast.makeText(context, "余额不足 ", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }

    public GiftDialog setLishener(OnGiftLishener lishener) {
        this.lishener = lishener;
        return this;
    }

    public void show() {
        showAtLocation(context.getWindow().getDecorView(), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        Tools.backgroundAlpha(context, 0.5f);
    }

    public interface OnGiftLishener {
        void onSuccess(int gid, int num);

        void onFail();
    }

    public class GiftBean {
        boolean select;
        int gid;//礼物ID

        public GiftBean() {

        }

        public GiftBean(int id) {
            this.gid = id;
        }

        public boolean isSelect() {
            return select;
        }

        public void setSelect(boolean select) {
            this.select = select;
        }

        public int getGid() {
            return gid;
        }

        public void setGid(int gid) {
            this.gid = gid;
        }
    }

    public class Gift2Adapter extends BaseQuickAdapter<GiftBean, BaseViewHolder> {


        public Gift2Adapter(List<GiftBean> beans) {
            super(R.layout.item_gift);
            addData(beans);
        }

        @Override
        protected void convert(com.chad.library.adapter.base.BaseViewHolder helper, GiftBean item) {
            helper.setImageResource(R.id.image, Tools.getGift2Drwable(item.getGid()));
            helper.setText(R.id.songli_text, Tools.getGiftCount(item.getGid()) + "");
            helper.setText(R.id.songli_name, Tools.getGiftName(item.getGid()));
            if (item.isSelect()) {
                helper.setBackgroundRes(R.id.item_border, R.drawable.gift_item_selected);
            } else {
                helper.setBackgroundColor(R.id.item_border, Color.TRANSPARENT);
            }
        }

    }

}
