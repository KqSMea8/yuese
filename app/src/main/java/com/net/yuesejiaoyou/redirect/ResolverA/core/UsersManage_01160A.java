package com.net.yuesejiaoyou.redirect.ResolverA.core;


import com.net.yuesejiaoyou.classroot.interface2.OkHttp;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.redirect.ResolverA.getset.Page;
import com.net.yuesejiaoyou.redirect.ResolverA.getset.UserBean;
import com.net.yuesejiaoyou.redirect.ResolverA.interface4.HelpManager_01160A;

import java.util.ArrayList;

public class UsersManage_01160A {
	HelpManager_01160A helpmanager = null;
	OkHttp okhttp=null;
	public UsersManage_01160A() {
		//httpclient = new HttpClientManage();
		okhttp=new OkHttp();
		helpmanager = new HelpManager_01160A();

	}
    

	public String black(String[] params) {
		String a = "ar?mode=A-user-mod&mode2=black";
		String json = okhttp.requestPostBySyn(a, params);
		LogDetect.send(LogDetect.DataType.specialType,"01160 json:",json);
		return json;
	}


	public String xiaoxi(String[] params) {
		String a = "ar?mode=A-user-mod&mode2=xiaoxi";
		String json = okhttp.requestPostBySyn(a, params);
		LogDetect.send(LogDetect.DataType.specialType,"01160 json:",json);
		return json;
	}



	public ArrayList<UserBean> search_dv(String[] params) {
		String a = "ar?mode=A-user-mod&mode2=search_dv";
		String json = okhttp.requestPostBySyn(a, params);
		LogDetect.send(LogDetect.DataType.specialType,"01160 json:",json);
		ArrayList<UserBean> list = helpmanager.search_dv(json);
		return list;
	}

    public Page attention_videolist(String[] params) {
		String a="ar?mode=A-user-mod&mode2=attention_videolist";
		String json=okhttp.requestPostBySyn(a,params);
		Page mblist = helpmanager.attention_videolist(json);
		return  mblist;
    }


    public String insert_reservation(String[] params) {
		String a = "memberB?mode=A-user-add&mode2=insert_reservation";
		String json = okhttp.requestPostBySyn(a, params);
		LogDetect.send(LogDetect.DataType.specialType,"01160 json:",json);
		return json;
    }
}
