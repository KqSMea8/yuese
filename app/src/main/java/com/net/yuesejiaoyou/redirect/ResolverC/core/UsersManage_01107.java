package com.net.yuesejiaoyou.redirect.ResolverC.core;

/*import com.example.vliao.getset.Focus_01107;
import com.example.vliao.getset.Video_01107;
import com.example.vliao.interface2.OkHttp;
import com.example.vliao.interface4.HelpManager_01107;*/
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect.DataType;
import com.net.yuesejiaoyou.classroot.interface2.OkHttp;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.Focus_01107;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.Video_01107;
import com.net.yuesejiaoyou.redirect.ResolverC.interface4.HelpManager_01107;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/11/14.
 */

public class UsersManage_01107 {
    HelpManager_01107 helpmanager = null;
    OkHttp okhttp=null;
    public UsersManage_01107() {
        //httpclient = new HttpClientManage();
        okhttp=new OkHttp();
        helpmanager = new HelpManager_01107();

    }

    public ArrayList<Focus_01107> getfocusdetail(String[] params) {
        String a = "memberC01178?mode=A-user-search&mode2=getfocusdetail";
        ///////////////////////////
        LogDetect.send(DataType.specialType, "关注:--------",a+"-----------"+params[0]);//参数没有那么多
        ///////////////////////////
        String json = okhttp.requestPostBySyn(a, params);
        ArrayList<Focus_01107> list = helpmanager.getfocusdetail(json);
        return list;
    }




    public String addvideo(String[] params) throws IOException {
        String a = "memberC01178?mode=A-user-add&mode2=addvideo";
        String json = okhttp.requestPostBySyn(a, params);
        String result = helpmanager.getResult(json);
        return result;
    }

    public String addvideo_ff(String[] params){
        String a = "memberC01178?mode=A-user-add&mode2=addvideo_ff";
        String json = okhttp.requestPostBySyn(a, params);
        String result = helpmanager.getResult(json);
        return result;
    }


    public ArrayList<Video_01107> getvideolist(String[] params) {
        String a = "memberC01178?mode=A-user-search&mode2=getvideolist";
        String json = okhttp.requestPostBySyn(a, params);
        ArrayList<Video_01107> list = helpmanager.getvideolist(json);
        return list;
    }



}
