package com.net.yuesejiaoyou.redirect.ResolverA.core;


import com.net.yuesejiaoyou.classroot.interface2.OkHttp;
import com.net.yuesejiaoyou.redirect.ResolverA.getset.Update;
import com.net.yuesejiaoyou.redirect.ResolverA.interface4.HelpManager_01107A;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/11/14.
 */

public class UsersManage_01107A {
    HelpManager_01107A helpmanager = null;
    OkHttp okhttp=null;
    public UsersManage_01107A() {
        //httpclient = new HttpClientManage();
        okhttp=new OkHttp();
        helpmanager = new HelpManager_01107A();

    }

    public ArrayList<Update> check_update(String[] params)  {

        String a = "ar?mode=A-user-search&mode2=check_update";
        String json = okhttp.requestPostBySyn(a, params);
        ArrayList<Update> mblist = helpmanager.check_update(json);


        return mblist;
    }
}
