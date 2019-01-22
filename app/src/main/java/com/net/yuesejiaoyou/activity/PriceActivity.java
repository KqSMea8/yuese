package com.net.yuesejiaoyou.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.BaseActivity;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.URL;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;


import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;


/************************
 * 本类是设置主播价格
 ***********************/
public class PriceActivity extends BaseActivity {


    @BindView(R.id.vcurrencynum)
    EditText vcurrencynum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        vcurrencynum.setText(getIntent().getStringExtra("ybprice"));


        TextView reward_percent = (TextView) findViewById(R.id.reward_percent);

        reward_percent.setText("           其中" + getIntent().getStringExtra("reward_percent") + "%归我所有，可在我的钱包中提现");
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_price;
    }

    @OnClick(R.id.fanhui)
    public void backClick() {
        finish();
    }

    @OnClick(R.id.confirm)
    public void confirmClick() {
        String string = vcurrencynum.getText().toString().trim();
        if (TextUtils.isEmpty(string)) {
            showToast("请输入价格");
            return;
        }
        try {
            int t = Integer.parseInt(string);
            if (t <= 0 || t > 5) {
                showToast("输入的价格必须在1到5悦币之间");
                return;
            }
            OkHttpUtils.post(this)
                    .url(URL.URL_CHANGE_PRICE)
                    .addParams("param1", Util.userid)
                    .addParams("param2", string)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            showToast("修改失败");
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            if (JSON.parseObject(response).getString("orderInfo").equals("1")) {
                                Toast.makeText(PriceActivity.this, "价格提交成功", Toast.LENGTH_SHORT).show();
                                setResult(RESULT_OK);
                                finish();
                            }


                        }
                    });
        } catch (Exception e) {
            showToast("请输入数字");
        }

    }

}
