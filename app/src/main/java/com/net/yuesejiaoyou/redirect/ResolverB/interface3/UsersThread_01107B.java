package com.net.yuesejiaoyou.redirect.ResolverB.interface3;

import android.os.Handler;

import com.net.yuesejiaoyou.redirect.ResolverB.interface1.UsersManageInOut_01107B;

import java.io.IOException;

/**
 * Created by Administrator on 2017/11/14.
 */

public class UsersThread_01107B {
    private Handler handler;
    private UsersManageInOut_01107B usersManageInOut;
    private String state, id,name,pwd,yzm,tele,sort,userid;
    private int page;
    private String[] params;

    public UsersThread_01107B(String mode, String[] params, Handler handler) {
        this.usersManageInOut = new UsersManageInOut_01107B();
        this.state = mode;
        this.params = params;
        this.handler = handler;
    }

    public Runnable runnable = new Runnable() {
        public void run() {
            try {
                switch (state) {

                    // 上传短视频
                    case "addvideo":
                        usersManageInOut.addvideo(params,handler);
                        break;
                    case "addvideo_ff":
                        usersManageInOut.addvideo_ff(params,handler);
                        break;
                    // 查看短视频列表
                    case "getvideolist":
                        usersManageInOut.getvideolist(params,handler);
                        break;
                    // 查看关注明细列表
                    case "getfocusdetail":
                        usersManageInOut.getfocusdetail(params,handler);
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };
}
