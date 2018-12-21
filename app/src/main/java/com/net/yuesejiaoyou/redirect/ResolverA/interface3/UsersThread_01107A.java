package com.net.yuesejiaoyou.redirect.ResolverA.interface3;

import android.os.Handler;

//import com.example.vliao.interface1.UsersManageInOut_01107;
import com.net.yuesejiaoyou.redirect.ResolverA.interface1.UsersManageInOut_01107A;

/**
 * Created by Administrator on 2017/11/14.
 */

public class UsersThread_01107A {
    private Handler handler;
    private UsersManageInOut_01107A usersManageInOut;
    private String state, id,name,pwd,yzm,tele,sort,userid;
    private int page;
    private String[] params;

    public UsersThread_01107A(String mode, String[] params, Handler handler) {
        this.usersManageInOut = new UsersManageInOut_01107A();
        this.state = mode;
        this.params = params;
        this.handler = handler;
    }

    public Runnable runnable = new Runnable() {
        public void run() {
//            try {
                switch (state) {
                    case "check_update":
                        usersManageInOut.check_update(params,handler);
                        break;


                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
    };
}
