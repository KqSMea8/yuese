package com.net.yuesejiaoyou.redirect.ResolverB.interface1;

import java.io.IOException;
import java.util.ArrayList;
import android.os.Handler;

import com.net.yuesejiaoyou.redirect.ResolverB.core.UsersManage_01107B;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;



/**
 * Created by Administrator on 2017/11/14.
 */

public class UsersManageInOut_01107B {
    UsersManage_01107B usersManage = null;
    private LogDetect logDbg;

    public UsersManageInOut_01107B() {
        usersManage = new UsersManage_01107B();

    }

    public void addvideo(String[] param, Handler handler) throws IOException {
        String result = usersManage.addvideo(param);
        handler.sendMessage(handler.obtainMessage(6,result));
    }

    public void addvideo_ff(String[] param, Handler handler){
        String result = usersManage.addvideo_ff(param);
        handler.sendMessage(handler.obtainMessage(6,result));
    }

    public void getvideolist(String[] param, Handler handler) {
        ArrayList<com.net.yuesejiaoyou.redirect.ResolverB.getset.Video_01107B> list = usersManage.getvideolist(param);
        handler.sendMessage(handler.obtainMessage(1,list));
    }

    public void getfocusdetail(String[] param, Handler handler) {
        ArrayList<com.net.yuesejiaoyou.redirect.ResolverB.getset.Focus_01107B> list = usersManage.getfocusdetail(param);
        handler.sendMessage(handler.obtainMessage(200,list));
    }
}
