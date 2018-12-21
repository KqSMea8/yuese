package com.net.yuesejiaoyou.redirect.ResolverC.interface3;

import android.os.Handler;

//import com.example.vliao.interface1.UsersManageInOut_01156;

import com.net.yuesejiaoyou.redirect.ResolverC.interface1.UsersManageInOut_01156;

import java.io.IOException;

public class UsersThread_01156 {
    private Handler handler;
    private UsersManageInOut_01156 usersManageInOut;
    private String state, id,name,pwd,yzm,tele,sort,userid;
    private int page;
    private String[] params;

    public UsersThread_01156(String mode, String[] params, Handler handler) {
        this.usersManageInOut = new UsersManageInOut_01156();
        this.state = mode;
        this.params = params;
        this.handler = handler;
    }
    public Runnable runnable = new Runnable() {
        public void run() {
            try {
                switch (state) {

                    //初始化主页界面
                    case "withdraw_init":
                        usersManageInOut.withdraw_init(params,handler);
                        break;
                    case "tgwithdraw_init":
                        usersManageInOut.tgwithdraw_init(params,handler);
                        break;

                    case "withdraw_up":
                        usersManageInOut.withdraw_up(params,handler);
                        break;
                    case "tgwithdraw_up":
                        usersManageInOut.tgwithdraw_up(params,handler);
                        break;

                }



            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };
}
