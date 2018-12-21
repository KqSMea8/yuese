package com.net.yuesejiaoyou.redirect.ResolverC.uiface;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

/*import com.net.yuesejiaoyou.R;
import com.example.vliao.getset.Focus_01107;
import com.example.vliao.interface3.UsersThread_01107;
import com.example.vliao.interface4.FocusAdapter;
import com.example.vliao.interface4.LogDetect;
import com.example.vliao.util.Util;
import com.lazysellers.sellers.uiface.ChatActivity;*/

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.Focus_01107;
import com.net.yuesejiaoyou.redirect.ResolverC.interface3.UsersThread_01107;
import com.net.yuesejiaoyou.redirect.ResolverC.interface4.FocusAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018\5\5 0005.
 */

public class FocusDetail extends Activity {

    private ListView listView;
    ArrayList<Focus_01107> list;
    private ImageView back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.focus_detail);

        initUI();
        freshUI();
    }

    private void initUI() {
        listView = (ListView)findViewById(R.id.list_focus);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                /**********************************
                 * 跳转ChatActivity页面，暂无此页面，先注释
                 *********************************/
                /*
                Intent intentthis = new Intent(FocusDetail.this, ChatActivity.class);

                Log.v("TT","onItemClick - id:"+list.get(i).getId()+",name:"+list.get(i).getNickname()+",headpic:"+list.get(i).getHeadpic());

                intentthis.putExtra("id", list.get(i).getId());
                intentthis.putExtra("name",  list.get(i).getNickname());
                intentthis.putExtra("headpic", list.get(i).getHeadpic());
                startActivity(intentthis);*/
            }
        });

        back = (ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void freshUI() {
        String mode="getfocusdetail";
        String[]params={Util.userid};
        UsersThread_01107 b=new UsersThread_01107(mode, params, handler);
        Thread thread=new Thread(b.runnable);
        thread.start();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
//            super.handleMessage(msg);

            int what = msg.what;

            switch(what) {
                case 200:
                    list = (ArrayList<Focus_01107>)msg.obj;
                    LogDetect.send(LogDetect.DataType.noType,"01107", list);
                    FocusAdapter adapter = new FocusAdapter(FocusDetail.this, list);
                    listView.setAdapter(adapter);
                    break;
            }
        }
    };
}
