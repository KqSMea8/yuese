package com.net.yuesejiaoyou.redirect.ResolverC.interface1;

import android.os.Handler;

import com.net.yuesejiaoyou.classroot.interface4.LogDetect;

import com.net.yuesejiaoyou.redirect.ResolverC.core.UsersManage_01107;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.Focus_01107;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.Video_01107;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/11/14.
 */

public class UsersManageInOut_01107 {
    UsersManage_01107 usersManage = null;
    private LogDetect logDbg;

    public UsersManageInOut_01107() {
        usersManage = new UsersManage_01107();

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
        ArrayList<Video_01107> list = usersManage.getvideolist(param);
        handler.sendMessage(handler.obtainMessage(1,list));
    }

    public void getfocusdetail(String[] param, Handler handler) {
        ArrayList<Focus_01107> list = usersManage.getfocusdetail(param);
        handler.sendMessage(handler.obtainMessage(200,list));
    }
}
