package com.net.yuesejiaoyou.redirect.ResolverD.interface4.activity;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alipay.sdk.app.PayTask;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.BaseActivity;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.URL;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.adapter.RechargeAdapter;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.bean.RechargeBean;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.wxapi.Constants;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.wxapi.PayResult;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.DialogCallback;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class RechargeActivity extends BaseActivity {


    //弹出框
    private TextView money_txt, money_num;
    private RelativeLayout zhifu, wechat;
    private ImageView back1;
    private PopupWindow mPopWindow;

    private IWXAPI api;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private RechargeAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RechargeAdapter();
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter1, View view, int position) {
                showPopupspWindow2(adapter.getData().get(position).getPrice() + "", adapter.getData().get(position).getV_num() + "");
            }
        });



        getData();
//		reciever reviever = new reciever();
//		IntentFilter filter1 = new IntentFilter("2");
//		registerReceiver(reviever, filter1);
    }

    private void getData() {
        OkHttpUtils.post(this)
                .url(URL.URL_PAYLIST)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        showToast("网络异常");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (TextUtils.isEmpty(response)) {
                            return;
                        }
                        List<RechargeBean> beans = JSON.parseArray(response, RechargeBean.class);
                        adapter.setNewData(beans);
                    }
                });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_recharge;
    }

    @OnClick(R.id.back)
    public void backClick() {
        finish();
    }

    private void wxPay(String money, String subMoney) {
        showToast("获取订单中...");

        OkHttpUtils.post(this)
                .url(Util.url + "/uiface/rp?mode=A-user-add&mode2=wxpay&userid=" + Util.userid + "&type=充值&price=" + money + "&wz=微信&num=" + subMoney)
                .build()
                .execute(new DialogCallback(this) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        showToast("充值失败");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (TextUtils.isEmpty(response)) {
                            showToast("网络异常,请稍后重试");
                            return;
                        }
                        JSONObject json;
                        try {
                            json = new JSONObject(response);

                            if (!json.has("retcode")) {
                                PayReq req = new PayReq();
                                req.appId = json.getString("appId");
                                req.partnerId = json.getString("partnerid");
                                req.prepayId = json.getString("prepayid");
                                req.nonceStr = json.getString("nonceStr");
                                req.timeStamp = json.getString("timeStamp");
                                req.packageValue = "Sign=WXPay";
                                req.sign = json.getString("sign");
                                req.extData = "app data"; // optional

                                showToast("正常调起支付");
                                api.sendReq(req);
                            } else {
                            }
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }

                    }
                });

    }

//	private class reciever extends BroadcastReceiver {
//		@Override
//		public void onReceive(Context context, Intent intent) {
//			Toast.makeText(RechargeActivity.this, "充值成功", Toast.LENGTH_LONG).show();
//			finish();
//		}
//	}

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    LogDetect.send(LogDetect.DataType.basicType, "参数resultInfo", resultInfo);
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(RechargeActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        //myYuanbao();
                        finish();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(RechargeActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {

                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

    private void alipay(String money, String subMoeny) {
        showToast("支付宝充值");

        OkHttpUtils.post(this)
                .url(URL.URL_ALIPAY)
                .addParams("param1",Util.userid)
                .addParams("param2","充值")
                .addParams("param3",money)
                .addParams("param4","支付宝")
                .addParams("param5",subMoeny)
                .build()
                .execute(new DialogCallback(this) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        showToast("充值失败");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (TextUtils.isEmpty(response)) {
                            showToast("网络异常,请稍后重试");
                            return;
                        }
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            final String orderInfo1 = jsonObject.getString("orderInfo");

                            Runnable payRunnable = new Runnable() {

                                @Override
                                public void run() {
                                    PayTask alipay = new PayTask(RechargeActivity.this);
                                    Map<String, String> result = alipay.payV2(orderInfo1, true);
                                    Message msg = new Message();
                                    msg.what = SDK_PAY_FLAG;
                                    msg.obj = result;
                                    mHandler.sendMessage(msg);
                                }
                            };
                            Thread payThread = new Thread(payRunnable);
                            payThread.start();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

    }

    public void showPopupspWindow2(String mon, String vnums) {
        mon = mon.replaceAll("元", "");

        final String realMoney = mon;

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.pay_style, null);


        money_txt = (TextView) layout.findViewById(R.id.money_txt);
        money_num = (TextView) layout.findViewById(R.id.money_num);
        //支付宝支付
        zhifu = (RelativeLayout) layout.findViewById(R.id.zhifu);
        //zhifu.setOnClickListener(this);
        //微信支付
        wechat = (RelativeLayout) layout.findViewById(R.id.wechat);
        //wechat.setOnClickListener(this);

        money_txt.setText("充值" + vnums + "悦币,需要支付" + mon + "元");
        money_num.setText(mon + "元");
        //退出弹出框
        back1 = (ImageView) layout.findViewById(R.id.back1);
        //back1.setOnClickListener(this);
        back1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //取消
                mPopWindow.dismiss();
            }
        });

        final String subMon = vnums;
        zhifu.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                alipay(realMoney, subMon);

            }
        });
        wechat.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                wxPay(realMoney, subMon);
            }
        });


        mPopWindow = new PopupWindow(layout, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);
        mPopWindow.setFocusable(true);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.4f;
        getWindow().setAttributes(lp);
        mPopWindow.setBackgroundDrawable(new BitmapDrawable(null, ""));
        WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        @SuppressWarnings("deprecation")
        // 获取xoff
                int xpos = manager.getDefaultDisplay().getWidth() / 2
                - mPopWindow.getWidth() / 2;
        // xoff,yoff基于anchor的左下角进行偏移。
        // popupWindow.showAsDropDown(parent, 0, 0);
        mPopWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM | Gravity.BOTTOM, 252, 0);

        mPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            // 在dismiss中恢复透明度
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow()
                        .addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                getWindow().setAttributes(lp);
            }
        });


    }


}
