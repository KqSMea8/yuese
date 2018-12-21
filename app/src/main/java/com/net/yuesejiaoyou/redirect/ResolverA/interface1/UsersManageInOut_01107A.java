package com.net.yuesejiaoyou.redirect.ResolverA.interface1;

import android.os.Handler;


import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.redirect.ResolverA.core.UsersManage_01107A;
import com.net.yuesejiaoyou.redirect.ResolverA.getset.Update;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/11/14.
 */

public class UsersManageInOut_01107A {
    UsersManage_01107A usersManage = null;
    private LogDetect logDbg;

    public UsersManageInOut_01107A() {
        usersManage = new UsersManage_01107A();

    }


    public void check_update(String[] param, Handler handler) {
        ArrayList<Update> list = usersManage.check_update(param);
        handler.sendMessage(handler.obtainMessage(52,list));
    }

}
