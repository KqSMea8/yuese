package com.net.yuesejiaoyou.redirect.ResolverC.uiface;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

/*import com.net.yuesejiaoyou.R;
import com.example.vliao.interface3.UsersThread_01152;
import com.example.vliao.interface4.LogDetect;
import com.example.vliao.util.Util;*/

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.redirect.ResolverC.interface3.UsersThread_01152;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.OnClick;


@SuppressLint("NewApi")
public class vliao_shenqing_01152 extends BaseActivity implements OnClickListener {

    private ImageView back, queding;
    private EditText zhanghu, xingming;
    String wenbenneirong = "";
    String wenbenneirong1 = "";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        queding = (ImageView) findViewById(R.id.queding);
        queding.setOnClickListener(this);

        zhanghu = (EditText) findViewById(R.id.zhanghu);
        xingming = (EditText) findViewById(R.id.xingming);

        zhanghu.setFocusable(true);//给控件个焦点
        xingming.setFocusable(true);

    }

    @Override
    protected int getContentView() {
        return R.layout.guanlizhanghu_01152;
    }

    @Override
    public int statusBarColor() {
        return R.color.transparent;
    }

    @Override
    public boolean statusBarFont() {
        return false;
    }

    @OnClick(R.id.back)
    public void backClick() {
        finish();
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        switch (id) {
            case R.id.queding://发送申请
                wenbenneirong = zhanghu.getText().toString();
                wenbenneirong1 = xingming.getText().toString();
                if (wenbenneirong.equals("") || wenbenneirong1.equals("")) {
                    Toast.makeText(vliao_shenqing_01152.this, "请输入正确的格式",
                            Toast.LENGTH_SHORT).show();
                } else if (wenbenneirong.equals("") && wenbenneirong1.equals("")) {
                    Toast.makeText(vliao_shenqing_01152.this, "请输入正确的格式",
                            Toast.LENGTH_SHORT).show();
                } else {
                    String mode = "shenqing";//输入的说明
                    String[] paramsMap = {Util.userid, zhanghu.getText().toString().trim(), xingming.getText().toString().trim()};
                    UsersThread_01152 b = new UsersThread_01152(mode, paramsMap, requestHandler);
                    Thread t = new Thread(b.runnable);
                    t.start();
                    break;
                }
        }
    }


    /**
     * 从APP服务端获取到的json字符串，用list集合去接收，把获取到的信息嵌入到界面元素
     */
    private Handler requestHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:

                    break;

                case 200:
                    String json = (String) msg.obj;
                    if (json.equals("")) {
                        Toast.makeText(vliao_shenqing_01152.this, "请求异常", Toast.LENGTH_SHORT).show();
                        break;
                    }

                    try { //如果服务端返回1，说明个人信息修改成功了
                        JSONObject jsonObject = new JSONObject(json);
                        if (jsonObject.getString("success").equals("1")) {
                            Toast.makeText(vliao_shenqing_01152.this, "绑定成功", Toast.LENGTH_SHORT).show();//Modify the success
                            finish();
                        } else {
                            Toast.makeText(vliao_shenqing_01152.this, "绑定失败", Toast.LENGTH_SHORT).show();//Modify the failure
                        }
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    break;

            }
        }
    };


}