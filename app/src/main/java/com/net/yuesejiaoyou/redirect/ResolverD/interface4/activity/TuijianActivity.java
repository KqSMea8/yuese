package com.net.yuesejiaoyou.redirect.ResolverD.interface4.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.Vliao1_01168;
import com.net.yuesejiaoyou.redirect.ResolverC.interface3.UsersThread_01156;
import com.net.yuesejiaoyou.redirect.ResolverC.interface3.UsersThread_01168C;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.redirect.ResolverC.uiface.Vliao_shourumingxizhubo_tjr_01168;
import com.net.yuesejiaoyou.redirect.ResolverC.uiface.Vliao_tixianmingxizhubo_tjr_01168;
import com.net.yuesejiaoyou.redirect.ResolverC.uiface.vliao_shenqing_01152;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class TuijianActivity extends BaseActivity implements OnClickListener {

    Intent intent = new Intent();
    ArrayList<Vliao1_01168> list1 = new ArrayList<Vliao1_01168>();
    //输入框
    private EditText username;
    private int cash_lower = 0;
    //确定、剩余贡献值、可提现金额
    private TextView submitp, total_asset, exchange, allshouru, ketixian, mingxi;
    //返回
    private ImageView back;
    //可提现、贡献值
    String moneyp = "0", contribution1 = "0";
    //设置开关???
    private boolean isup = true;

    int money_tx = 0;
    String money_str = "";

    private LinearLayout account_lay;
    private TextView account_num, account_name, phone_num;
    private boolean bHasTixianAccount = false;

    private TextView tuoyuansanjiao;
    private TextView pageTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //确认
        submitp = (TextView) findViewById(R.id.submitp);
        submitp.setOnClickListener(this);
        total_asset = (TextView) findViewById(R.id.total_asset);
        exchange = (TextView) findViewById(R.id.exchange);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
        username = (EditText) findViewById(R.id.username);
        allshouru = (TextView) findViewById(R.id.allshouru);
        allshouru.setOnClickListener(this);
        ketixian = (TextView) findViewById(R.id.ketixian);
        ketixian.setOnClickListener(this);

        mingxi = (TextView) findViewById(R.id.mingxi);
        mingxi.setOnClickListener(this);

        tuoyuansanjiao = (TextView) findViewById(R.id.tuoyuansanjiao);
        pageTitle = (TextView) findViewById(R.id.textView);

        if ("1".equals(Util.is_agent)) {
            pageTitle.setText("推荐人提现(代理)");
            tuoyuansanjiao.setText("分成比例 5%");
        } else {
            pageTitle.setText("推荐人提现(分销)");
            tuoyuansanjiao.setText("分成比例 10%");
        }

        account_lay = (LinearLayout) findViewById(R.id.account_lay);
        account_num = (TextView) findViewById(R.id.account_num);
        account_name = (TextView) findViewById(R.id.account_name);
        phone_num = (TextView) findViewById(R.id.phone_num);
        account_lay.setOnClickListener(this);


        String mode = "withdraw_init";
        String[] paramsMap = {Util.userid};
        UsersThread_01156 b = new UsersThread_01156(mode, paramsMap, requestHandler);
        Thread thread = new Thread(b.runnable);
        thread.start();

        String mode1 = "tjrwithdraw";
        String[] paramsMap1 = {Util.userid};
        UsersThread_01168C b1 = new UsersThread_01168C(mode1, paramsMap1, requestHandler);
        Thread thread1 = new Thread(b1.runnable);
        thread1.start();


    }

    @Override
    protected int getContentView() {
        return R.layout.activity_tuijian;
    }

    @Override
    protected void onResume() {
        super.onResume();

        String mode = "withdraw_init";
        String[] paramsMap = {Util.userid};
        UsersThread_01156 b = new UsersThread_01156(mode, paramsMap, requestHandler);
        Thread thread = new Thread(b.runnable);
        thread.start();

        String mode1 = "tjrwithdraw";
        String[] paramsMap1 = {Util.userid};
        UsersThread_01168C b1 = new UsersThread_01168C(mode1, paramsMap1, requestHandler);
        Thread thread1 = new Thread(b1.runnable);
        thread1.start();
    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

        int id = v.getId();
        switch (id) {

            case R.id.back://退出操作
                this.finish();
                break;
            case R.id.allshouru:
                intent = new Intent();
                intent.setClass(this, Vliao_shourumingxizhubo_tjr_01168.class);
                startActivity(intent);
                break;

            case R.id.mingxi:
                intent = new Intent();
                intent.setClass(this, Vliao_shourumingxizhubo_tjr_01168.class);
                startActivity(intent);
                break;

            case R.id.ketixian:
                intent = new Intent();
                intent.setClass(this, Vliao_tixianmingxizhubo_tjr_01168.class);
                startActivity(intent);
                break;

            case R.id.submitp:

                if (bHasTixianAccount == false) {
                    Toast.makeText(TuijianActivity.this, "请先绑定提现账号", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (username.getText().toString().equals("")) {
                    Toast.makeText(TuijianActivity.this, "请输入提现金额", Toast.LENGTH_SHORT).show();
                    return;
                }
                LogDetect.send(LogDetect.DataType.specialType, "username.getText().toString()第一个值", "Integer.parseInt(moneyp)第二个值");
                //money_tx
                //username.getText().toString()
                money_str = username.getText().toString();
                money_tx = Integer.parseInt(money_str);
                LogDetect.send(LogDetect.DataType.specialType, "用户提现的值01168", money_tx);
                if (Double.parseDouble(username.getText().toString()) > Double.parseDouble(moneyp)) {
                    Toast.makeText(TuijianActivity.this, "提现金额超出！", Toast.LENGTH_SHORT).show();
                    return;
                } else if (username.getText().toString().equals("0")) {
                    Toast.makeText(TuijianActivity.this, "请输入提现金额！", Toast.LENGTH_SHORT).show();
                    return;
                } else if (money_tx < cash_lower) {
                    Toast.makeText(TuijianActivity.this, "提现金额必须在" + cash_lower + "元以上", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (isup) {
                    isup = false;
                    String mode = "tjrwithdraw_up";
                    String[] paramsMap = {Util.userid, username.getText().toString().trim()};
                    UsersThread_01168C b = new UsersThread_01168C(mode, paramsMap, requestHandler);
                    Thread thread = new Thread(b.runnable);
                    thread.start();
                }

                break;

            case R.id.account_lay:
                Intent intent1 = new Intent();
                intent1.setClass(TuijianActivity.this, vliao_shenqing_01152.class);
                startActivity(intent1);
                break;

        }

    }

    private Handler requestHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    break;
                case 200:
                    String json = (String) msg.obj;
                    if (json.contains("success")) {
                        bHasTixianAccount = true;
                        try {
                            JSONObject jsonobj = new JSONObject(json);
                            //total_asset.setText(jsonobj.getString("vbi"));
                            //moneyp=""+(Integer.parseInt(jsonobj.getString("vbi"))/2);
                            //exchange.setText(Double.parseDouble(jsonobj.getString("vbi"))/2+"");
                            account_lay.setVisibility(View.VISIBLE);
                            account_num.setText(jsonobj.getString("success"));
                            account_name.setText(jsonobj.getString("account_name"));
                            phone_num.setText(jsonobj.getString("phone_num"));
                            cash_lower = Integer.parseInt(jsonobj.getString("cash_lower"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        bHasTixianAccount = false;
                        JSONObject jsonobj2 = null;
                        try {
                            jsonobj2 = new JSONObject(json);
                            //total_asset.setText(jsonobj2.getString("vbi"));
                            //moneyp=""+(Integer.parseInt(jsonobj2.getString("vbi"))/2);
                            //exchange.setText(Double.parseDouble(jsonobj2.getString("vbi"))/2+"");
                            // account_lay.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                //解析重置密码请求返回的json字符串
                case 212:
                    LogDetect.send(LogDetect.DataType.specialType, "Aiba_wodefensi_01168", "返回数据");
                    list1 = (ArrayList<Vliao1_01168>) msg.obj;
                    if (list1 == null || list1.size() == 0) {
                        //Toast.makeText(Meijian_wodefensi_01168.this, "集合为空", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    //获取到的数据赋给对应控件
                    moneyp = list1.get(0).getAbleinvite_price().toString();    // .getAbleinvite_price.toString();
                    contribution1 = list1.get(0).getInvite_price().toString();    //getInvite_price.toString();
                    exchange.setText(moneyp);
                    total_asset.setText(contribution1);
                  /*String json = (String)msg.obj;
			    	try { //如果服务端返回1，说明个人信息修改成功了
			    		JSONObject jsonObject = new JSONObject(json);
						if(jsonObject.getString("success").equals("1")){
							//提交成功
							Toast.makeText(TuijianActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
							finish();
						}else{
							//提交失败
							Toast.makeText(TuijianActivity.this, "提交失败", Toast.LENGTH_SHORT).show();
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}*/
                    break;
                case 213:
                    isup = true;
                    String json3 = (String) msg.obj;
                    if (json3.contains("success")) {
                        try {
                            JSONObject jsonobj = new JSONObject(json3);
                            //total_asset.setText(jsonobj.getString("vbi"));
                            moneyp = jsonobj.getString("vbi").toString();
                            exchange.setText(Double.parseDouble(jsonobj.getString("vbi")) + "");
                            Toast.makeText(TuijianActivity.this, "提交成功!", Toast.LENGTH_SHORT).show();
                            username.setText("");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(TuijianActivity.this, "提交失败!", Toast.LENGTH_SHORT).show();
                    }

                    break;

            }
        }
    };


}
