package com.net.yuesejiaoyou.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.text.TextUtils;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.BaseActivity;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.URL;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.DialogCallback;

import butterknife.OnClick;
import okhttp3.Call;

public class WalletActivity extends BaseActivity {


    private TextView vbi;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        vbi = (TextView) findViewById(R.id.vbi);

        getData();
    }

    @ColorRes
    public int statusBarColor(){
        return R.color.wallet;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_wallet;
    }

    private void getData() {
        OkHttpUtils.post(this)
                .url(URL.URL_ZONGJINE)
                .addParams("param1",Util.userid)
                .build()
                .execute(new DialogCallback(this) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        showToast("网络异常");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (TextUtils.isEmpty(response)) {
                            return;
                        }
                        String money = JSON.parseArray(response).getJSONObject(0).getString("money");
                        vbi.setText(money);
                    }
                });
    }

    @OnClick(R.id.fanhui)
    public void backClick() {
        finish();
    }

    @OnClick(R.id.recharge)
    public void rechargeClick() {
        if (Util.iszhubo.equals("0")) {
            startActivity(RechargeActivity.class);
        } else {
            showToast("主播暂无此功能");
        }
    }

    @OnClick(R.id.withdrawal)
    public void withdrawClick() {
        if (Util.iszhubo.equals("1")) {
            startActivity(WithdrawActivity.class);
        } else {
            showToast("只有主播可以提现");
        }
    }

    @OnClick(R.id.incomedetails)
    public void incomeClick(){
        if (Util.iszhubo.equals("0")) {
            startActivity(IncomeActivity.class);
        } else {
            startActivity(ZhuboIncomeActivity.class);
        }
    }

    @OnClick(R.id.spendingdetails)
    public void spendClick(){
        if (Util.iszhubo.equals("0")) {
            startActivity(ZhuboBillActivity.class);
        } else {
            startActivity(BillActivity.class);
        }
    }

    @OnClick(R.id.withdrawaldetails)
    public void withdrawalClick(){
        if (Util.iszhubo.equals("0")) {
            startActivity(TixianDetailActivity.class);
        } else {
            startActivity(ZhuboTixianActivity.class);
        }
    }

}
