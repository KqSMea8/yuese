package com.net.yuesejiaoyou.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/*import com.net.yuesejiaoyou.R;
import com.example.vliao.interface3.UsersThread_01156;
import com.example.vliao.interface4.LogDetect;
import com.example.vliao.util.Util;*/
import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.redirect.ResolverC.uiface.vliao_shenqing_01152;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.BaseActivity;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.URL;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.DialogCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.OnClick;
import okhttp3.Call;


public class WithdrawActivity extends BaseActivity implements OnClickListener {
    //自定义ID

    private TextView account_num,account_name,phone_num,exchange,total_asset;
    private LinearLayout account_lay;
    private TextView details,maxmoney,maxmoneygz;
    private EditText username;
    private double cash_lower=0;
    int money_tx = 0;
	String money_str = "";
	private boolean bHasTixianAccount = false;
    
    @Override
    /**
     * 对页面进行初始化
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        account_lay = (LinearLayout) findViewById(R.id.account_lay);
        account_lay.setOnClickListener(this);
        account_num = (TextView) findViewById(R.id.account_num);
        account_name = (TextView) findViewById(R.id.account_name);
        phone_num = (TextView) findViewById(R.id.phone_num);
        exchange = (TextView) findViewById(R.id.exchange);
        total_asset = (TextView) findViewById(R.id.total_asset);
		details= (TextView) findViewById(R.id.tixian_details);
		details.setOnClickListener(this);
        username= (EditText) findViewById(R.id.username);
        maxmoney= (TextView) findViewById(R.id.maxmoney);
        maxmoneygz= (TextView) findViewById(R.id.maxmoneygz);

        username.setFocusable(false);
        username.setFocusableInTouchMode(true);


        getDatas();
    }

    @Override
    public int statusBarColor() {
        return R.color.vhongse;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_withdraw;
    }


    public void getDatas() {
        OkHttpUtils.post(this)
                .url(URL.URL_WITHDRAW)
                .addParams("param1", Util.userid)
                .build()
                .execute(new DialogCallback(this) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        showToast("网络异常");
                    }

                    @Override
                    public void onResponse(String resultBean, int id) {
                        if(resultBean.contains("success")){
                            bHasTixianAccount = true;
                            try {
                                JSONObject jsonobj = new JSONObject(resultBean);
                                total_asset.setText(jsonobj.getString("vbi"));
                                moneyp= Double.parseDouble(jsonobj.getString("vbimoney"));
                                exchange.setText(Double.parseDouble(jsonobj.getString("vbimoney"))+"");
                                account_lay.setVisibility(View.VISIBLE);
                                account_num.setText(jsonobj.getString("success"));
                                account_name.setText(jsonobj.getString("account_name"));
                                phone_num.setText(jsonobj.getString("phone_num"));
                                cash_lower=Double.parseDouble(jsonobj.getString("cash_lower"));
                                maxmoney.setText("每满"+cash_lower+"元即可提现");
                                maxmoneygz.setText("可提现余额大于"+cash_lower+"元即可开始提现；");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }else{
                            bHasTixianAccount = false;
                            try {
                                JSONObject jsonobj2 = new JSONObject(resultBean);
                                total_asset.setText(jsonobj2.getString("vbi"));
                                moneyp= Double.parseDouble(jsonobj2.getString("vbimoney"));
                                exchange.setText(Double.parseDouble(jsonobj2.getString("vbimoney"))+"");
                                // account_lay.setVisibility(View.GONE);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

    @OnClick(R.id.back)
    public void backClick(){
        finish();
    }

    @OnClick(R.id.submitp)
    public void comfireClick(){
        if(bHasTixianAccount == false) {
            showToast("请先绑定提现账号");
            return;
        }

        if (username.getText().toString().trim().equals("")){
            showToast("输入金额不能为空或零");
            return;
        }

        money_str = username.getText().toString();
        money_tx = Integer.parseInt(money_str);
        if (Integer.parseInt(username.getText().toString())>moneyp){
            showToast("输入金额不应超过可用金额");
            return;
        }else if(username.getText().toString().equals("0")){
            showToast("输入金额不能为空或零");
            return;
        }else if(money_tx<cash_lower){
            showToast("提现金额必须在"+cash_lower+"元以上");
            return;
        }
        if (isup){
            isup=false;
            OkHttpUtils.post(this)
                    .url(URL.URL_WITHDRAWUP)
                    .addParams("param1", Util.userid)
                    .addParams("param2", money_str)
                    .build()
                    .execute(new DialogCallback(this) {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            isup=true;
                        }

                        @Override
                        public void onResponse(String resultBean, int id) {
                            isup=true;
                            if(resultBean.contains("success")){
                                try {
                                    JSONObject jsonobj = new JSONObject(resultBean);
                                    total_asset.setText(jsonobj.getString("vbi"));
                                    moneyp= Double.parseDouble(jsonobj.getString("vbimoney"));
                                    exchange.setText(Double.parseDouble(jsonobj.getString("vbimoney"))+"");
                                    showToast("提现请求提交成功,等待后台审核");
                                    username.setText("");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }else{
                                showToast("提现请求提交失败");
                            }
                        }
                    });
        }
    }

    private boolean isup=true;
    //获得onclick点击事件
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        int id = v.getId();
        switch (id) {
			case R.id.tixian_details:
				Intent intent=new Intent();
				if(Util.iszhubo.equals("1")){
                        intent.setClass(WithdrawActivity.this,ZhuboTixianActivity.class);
				}else{
                       intent.setClass(WithdrawActivity.this,TixianDetailActivity.class);
				}
              startActivity(intent);
				break;
			case R.id.account_lay:
                   Intent intent1=new Intent();
                   intent1.setClass(WithdrawActivity.this,vliao_shenqing_01152.class);
                   startActivity(intent1);
				break;
        }
    }

    private double moneyp=0;



}
