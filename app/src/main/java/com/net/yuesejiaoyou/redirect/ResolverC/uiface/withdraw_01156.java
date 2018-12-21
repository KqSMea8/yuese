package com.net.yuesejiaoyou.redirect.ResolverC.uiface;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

/*import com.net.yuesejiaoyou.R;
import com.example.vliao.interface3.UsersThread_01156;
import com.example.vliao.interface4.LogDetect;
import com.example.vliao.util.Util;*/
import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.redirect.ResolverC.interface3.UsersThread_01156;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import org.json.JSONException;
import org.json.JSONObject;

//import com.example.youhui.interface3.yaoqingjiluAdapter_01152;


public class withdraw_01156 extends Activity implements OnClickListener {
    //自定义ID

    private PopupWindow mPopWindow;
    private ImageView back;
    private NumberPicker numberpicker;
    private TextView account_num,account_name,phone_num,exchange,total_asset;
    private LinearLayout account_lay;
    private Intent intent;
    private ListView listview;
    private TextView details,submitp,maxmoney,maxmoneygz;
    private DisplayImageOptions options;
    private Context context;
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
        setContentView(R.layout.withdraw_01156);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
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
        submitp= (TextView) findViewById(R.id.submitp);
        maxmoney= (TextView) findViewById(R.id.maxmoney);
        maxmoneygz= (TextView) findViewById(R.id.maxmoneygz);

        submitp.setOnClickListener(this);
        username.setFocusable(false);
        username.setFocusableInTouchMode(true);
		String mode = "withdraw_init";
		String[] paramsMap = {Util.userid};
		UsersThread_01156 b = new UsersThread_01156(mode,paramsMap,requestHandler);
		Thread thread = new Thread(b.runnable);
		thread.start();

    }

    @Override
    protected void onResume() {
        super.onResume();

        String mode = "withdraw_init";
        String[] paramsMap = {Util.userid};
        UsersThread_01156 b = new UsersThread_01156(mode,paramsMap,requestHandler);
        Thread thread = new Thread(b.runnable);
        thread.start();
    }

    private boolean isup=true;
    //获得onclick点击事件
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        int id = v.getId();
        switch (id) {
            case R.id.back:
                finish();
                break;
            case R.id.submitp://金额不应超过可用金额。数量不能为空或零。

                if(bHasTixianAccount == false) {
                    Toast.makeText(withdraw_01156.this,"请先绑定提现账号", Toast.LENGTH_SHORT).show();
                    break;
                }

                if (username.getText().toString().trim().equals("")){
                    Toast.makeText(withdraw_01156.this,"输入金额不能为空或零", Toast.LENGTH_SHORT).show();
                    return;
                }
                
                money_str = username.getText().toString();
    			money_tx = Integer.parseInt(money_str);
    			LogDetect.send(LogDetect.DataType.specialType, "用户提现的值01168",money_tx);
                if (Integer.parseInt(username.getText().toString())>moneyp){
                    Toast.makeText(withdraw_01156.this,"输入金额不应超过可用金额", Toast.LENGTH_SHORT).show();
                    return;
                }else if(username.getText().toString().equals("0")){
					Toast.makeText(withdraw_01156.this,"输入金额不能为空或零", Toast.LENGTH_SHORT).show();
					return;
				}else if(money_tx<cash_lower){
					Toast.makeText(withdraw_01156.this,"提现金额必须在"+cash_lower+"元以上", Toast.LENGTH_SHORT).show();
					return;
				}
                //Toast.makeText(withdraw_01156.this,username.getText().toString().trim(), Toast.LENGTH_SHORT).show();
                if (isup){
                    isup=false;
                    String mode = "withdraw_up";
                    String[] paramsMap = {Util.userid,username.getText().toString().trim()};
                    UsersThread_01156 b = new UsersThread_01156(mode,paramsMap,requestHandler);
                    Thread thread = new Thread(b.runnable);
                    thread.start();
                }
                break;
			case R.id.tixian_details:
				Intent intent=new Intent();
				if(Util.iszhubo.equals("1")){
                        intent.setClass(withdraw_01156.this,Vliao_tixianmingxizhubo_01168.class);
				}else{
                       intent.setClass(withdraw_01156.this,Vliao_tixianmingxi_01168.class);
				}
              startActivity(intent);
				break;
			case R.id.account_lay:
                   Intent intent1=new Intent();
                /**************************
                 * 没有申请，暂时注释
                 *************************/
                   intent1.setClass(withdraw_01156.this,vliao_shenqing_01152.class);
                   startActivity(intent1);
				break;
        }
    }

    private double moneyp=0;

    private Handler requestHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    break;
                case 200:
                    String json = (String)msg.obj;
                    if(json.contains("success")){
                        bHasTixianAccount = true;
                        try {
                            JSONObject jsonobj = new JSONObject(json);
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
                        JSONObject jsonobj2 = null;
                        try {
                            jsonobj2 = new JSONObject(json);
                            total_asset.setText(jsonobj2.getString("vbi"));
                            moneyp= Double.parseDouble(jsonobj2.getString("vbimoney"));
                            exchange.setText(Double.parseDouble(jsonobj2.getString("vbimoney"))+"");
                           // account_lay.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 201:
                    isup=true;
                    String json3 = (String)msg.obj;
                    if(json3.contains("success")){
                        try {
                            JSONObject jsonobj = new JSONObject(json3);
                            total_asset.setText(jsonobj.getString("vbi"));
                            moneyp= Double.parseDouble(jsonobj.getString("vbimoney"));
                            exchange.setText(Double.parseDouble(jsonobj.getString("vbimoney"))+"");
                            Toast.makeText(withdraw_01156.this,"提现请求提交成功,等待后台审核", Toast.LENGTH_SHORT).show();
                            username.setText("");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else{
                        Toast.makeText(withdraw_01156.this,"提现请求提交失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };
}
