package com.net.yuesejiaoyou.redirect.ResolverB.core;

import com.net.yuesejiaoyou.redirect.ResolverB.getset.Focus_01107B;
import com.net.yuesejiaoyou.redirect.ResolverB.getset.Video_01107B;
import com.net.yuesejiaoyou.classroot.interface2.OkHttp;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.HelpManager_01107B;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/11/14.
 */

public class UsersManage_01107B {
    HelpManager_01107B helpmanager = null;
    OkHttp okhttp=null;
    public UsersManage_01107B() {
        //httpclient = new HttpClientManage();
        okhttp=new OkHttp();
        helpmanager = new HelpManager_01107B();

    }

    public String addvideo(String[] params) throws IOException {
        String a = "memberB?mode=A-user-add&mode2=addvideo";
        String json = okhttp.requestPostBySyn(a, params);
        String result = helpmanager.getResult(json);
        return result;
    }

    public String addvideo_ff(String[] params){
        String a = "memberB?mode=A-user-add&mode2=addvideo_ff";
        String json = okhttp.requestPostBySyn(a, params);
        String result = helpmanager.getResult(json);
        return result;
    }


    public ArrayList<Video_01107B> getvideolist(String[] params) {
        String a = "rp?mode=A-user-search&mode2=getvideolist";
        String json = okhttp.requestPostBySyn(a, params);
        ArrayList<Video_01107B> list = helpmanager.getvideolist(json);
        return list;
    }

    public ArrayList<Focus_01107B> getfocusdetail(String[] params) {
        String a = "v?mode=A-user-search&mode2=getfocusdetail";
        String json = okhttp.requestPostBySyn(a, params);
        ArrayList<Focus_01107B> list = helpmanager.getfocusdetail(json);
        return list;
    }

}
