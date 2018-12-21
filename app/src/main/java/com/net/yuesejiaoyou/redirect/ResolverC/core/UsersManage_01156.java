package com.net.yuesejiaoyou.redirect.ResolverC.core;




/*import com.example.vliao.interface2.OkHttp;
import com.example.vliao.interface4.HelpManager_01160;*/


import com.net.yuesejiaoyou.classroot.interface2.OkHttp;
import com.net.yuesejiaoyou.redirect.ResolverC.interface4.HelpManager_01160;

public class UsersManage_01156 {


    HelpManager_01160 helpmanager;
    OkHttp okhttp=null;
    public UsersManage_01156() {
        //httpclient = new HttpClientManage();
        okhttp=new OkHttp();
        helpmanager = new HelpManager_01160();
    }
    public String withdraw_init(String[] params) {
        String a = "memberC01178?mode=A-user-search&mode1=withdraw_init";
        String json = okhttp.requestPostBySyn(a, params);

        return json;
    }
    public String withdraw_up(String[] params) {
        String a = "memberC01178?mode=A-user-search&mode1=withdraw_up";
        String json = okhttp.requestPostBySyn(a, params);

        return json;
    }





    public String tgwithdraw_init(String[] params) {
        String a = "withdraw?mode=A-user-search&mode1=tgwithdraw_init";
        String json = okhttp.requestPostBySyn(a, params);

        return json;
    }

    public String tgwithdraw_up(String[] params) {
        String a = "withdraw?mode=A-user-search&mode1=tgwithdraw_up";
        String json = okhttp.requestPostBySyn(a, params);
        return json;
    }


}
