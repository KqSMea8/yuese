package com.net.yuesejiaoyou.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.redirect.ResolverB.getset.Bills_01165;
import com.net.yuesejiaoyou.redirect.ResolverB.getset.Page;

import com.net.yuesejiaoyou.redirect.ResolverD.interface4.BaseActivity;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.URL;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;


/**
 * Created by Administrator on 2018/4/26.
 */

public class LevelActivity extends BaseActivity implements View.OnClickListener{

    private ImageView touxiang,back;
    private TextView level,level1,level2;
    private Map<String,String> map = new HashMap<String, String>();
    DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
            .cacheOnDisc(true).bitmapConfig(Bitmap.Config.RGB_565).build();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        map.put("1","0");
        map.put("2", "100");
        map.put("3","500");
        map.put("4","1000");
        map.put("5","2000");
        map.put("6","5000");
        map.put("7","8000");
        map.put("8","20000");
        map.put("9","30000");
        map.put("10","50000");
        map.put("11","80000");
        touxiang =(ImageView)findViewById(R.id.touxiang);
        back = (ImageView)findViewById(R.id.back);
        back.setOnClickListener(this);
        level = (TextView)findViewById(R.id.level);
        level1 = (TextView)findViewById(R.id.level1);
        level2 = (TextView)findViewById(R.id.level2);


        OkHttpUtils.post(this)
                .url(URL.URL_LEVEL)
                .addParams("param1", Util.userid)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {

                        Page page = level_search(response);
                        //获取V币消耗数量
                        int pay_sum = page.getTotlePage();
                        ArrayList<Bills_01165> list = (ArrayList<Bills_01165>)page.getList();
                        LogDetect.send(LogDetect.DataType.specialType, "list",list);

                        if(list.get(0).getTouxiang().contains("http")){
                            ImageLoader.getInstance().displayImage(list.get(0).getTouxiang(),touxiang,options);
                        }

                        ImageLoader.getInstance().displayImage(list.get(0).getTouxiang(),touxiang);
                        level.setText(("LV"+list.get(0).getLevel()));
                        String now_pay = map.get(list.get(0).getLevel());
                        String[] now_pays = extractAmountMsg(now_pay);
                        int now_pay_num = Integer.parseInt(now_pays[0]);
                        level1.setText(pay_sum - now_pay_num + "");
                        int nex_pay_level = Integer.parseInt(list.get(0).getLevel())+1;
                        String next_pay_num = map.get(nex_pay_level+"");
                        int next_num = Integer.parseInt(next_pay_num);
                        level2.setText(next_num-pay_sum +"");
                    }
                });
    }

    @Override
    protected int getContentView() {
        return R.layout.uer_level_01165;
    }

    public Page level_search(String json) {
        ArrayList<Bills_01165> list = new ArrayList<>();
        Page page = new Page();
        JSONArray jsonArray;
        try {
            jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                //最后一条数据是返回的抓中次数
                if(i==jsonArray.length()-1){
                    page.setTotlePage(item.getInt("totlePage"));
                }
                else{
                    Bills_01165 bean = new Bills_01165();
                    bean.setTouxiang(item.getString("photo"));
                    bean.setLevel(item.getString("dengji"));
                    list.add(bean);
                }
            }
            page.setList(list);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return page;
    }

    private Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    break;
                case 200:
                    @SuppressWarnings("unchecked")

                    Page page = (Page) msg.obj;
                    LogDetect.send(LogDetect.DataType.specialType, "page",page);
                    //获取V币消耗数量
                    int pay_sum = page.getTotlePage();

                    ArrayList<Bills_01165> list = (ArrayList<Bills_01165>)page.getList();
                    LogDetect.send(LogDetect.DataType.specialType, "list",list);

                    if(list.get(0).getTouxiang().contains("http")){
                        ImageLoader.getInstance().displayImage(list.get(0).getTouxiang(),touxiang,options);
                    }


                    ImageLoader.getInstance().displayImage(list.get(0).getTouxiang(),touxiang);
                    level.setText(("LV"+list.get(0).getLevel()));
                    String now_pay = map.get(list.get(0).getLevel());
                    String[] now_pays = extractAmountMsg(now_pay);
                    int now_pay_num = Integer.parseInt(now_pays[0]);
                    level1.setText(pay_sum - now_pay_num + "");
                    LogDetect.send(LogDetect.DataType.specialType, "当前经验值",level1.toString());
                    //得到下一级,
                    int nex_pay_level = Integer.parseInt(list.get(0).getLevel())+1;
                    LogDetect.send(LogDetect.DataType.specialType, "得到下一级的等级:",nex_pay_level);
                    //得到下一级的V币总消耗额
//                    String next_pay = map.get(nex_pay_level+"");
//                    LogDetect.send(LogDetect.DataType.specialType, "得到下一级的V币总消耗_next_pay:",next_pay);
                    String next_pay_num = map.get(nex_pay_level+"");
                    LogDetect.send(LogDetect.DataType.specialType, "得到下一级的V币总消耗_next_pay_num:",next_pay_num);
                    int next_num = Integer.parseInt(next_pay_num);
                    LogDetect.send(LogDetect.DataType.specialType, "得到下一级的V币总消耗_next_num:",next_num);
                    level2.setText(next_num-pay_sum +"");
                    LogDetect.send(LogDetect.DataType.specialType, "升级还需经验值",level2.toString());
                    break;
            }

        }
    };
    @Override
    public void onClick(View view) {
		switch (view.getId()){
            case R.id.back:
                finish();
                break;
        }
    }
    public static String[] extractAmountMsg(String ptCasinoMsg){
        String returnAmounts [] = new String [4];
        ptCasinoMsg = ptCasinoMsg.replace(" | ", " ");
        String [] amounts = ptCasinoMsg.split(" ");
        for(int i=0;i<amounts.length;i++){
            Pattern p=Pattern.compile("(\\d+\\.\\d+)");
            Matcher m=p.matcher(amounts[i]);
            if(m.find()){
                returnAmounts[i]=m.group(1);
            }else{
                p= Pattern.compile("(\\d+)");
                m=p.matcher(amounts[i]);
                if(m.find()){
                    returnAmounts[i]=m.group(1);
                }
            }
        }
        return returnAmounts;
    }



}
