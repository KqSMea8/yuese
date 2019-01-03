package com.net.yuesejiaoyou.redirect.ResolverA.core;

import java.io.IOException;
import java.util.ArrayList;


import com.net.yuesejiaoyou.classroot.interface2.OkHttp;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.redirect.ResolverA.getset.Member_01152;
import com.net.yuesejiaoyou.redirect.ResolverA.interface4.HelpManager_01152A;


public class UsersManage_01152A {
	HelpManager_01152A helpmanager = null;
	OkHttp okhttp=null;
	public UsersManage_01152A() {
//		httpclient = new HttpClientManage();
		okhttp=new OkHttp();
		helpmanager = new HelpManager_01152A();

	}
    
	public String lat_lonmod(String[] params) throws IOException {
		String a = "member?mode=A-user-mod&mode2=lat_lonmod";
		String json = okhttp.requestPostBySyn(a, params);
		return json;
	}

	public ArrayList<Member_01152> gerenzhongxin(String[] params) {
		String a = "ar?mode=A-user-search&mode2=gerenzhongxin";
		String json = okhttp.requestPostBySyn(a, params);
		LogDetect.send(LogDetect.DataType.specialType, "UsersManage_01152A:json", json);
		ArrayList<Member_01152> list=helpmanager.Json_gukeinfo(json);
		LogDetect.send(LogDetect.DataType.specialType, "UsersManage_01152A:集合", list);
		return list;
	}

	public ArrayList<Member_01152> personal_information(String[] params) {
		String a = "ar?mode=A-user-search&mode2=gerenzhongxin";
		String json = okhttp.requestPostBySyn(a, params);
		LogDetect.send(LogDetect.DataType.specialType, "UsersManage_01152A:json", json);
		ArrayList<Member_01152> list=helpmanager.Json_zhuboinfo(json);
		LogDetect.send(LogDetect.DataType.specialType, "UsersManage_01152A:集合", list);
		return list;
	} 

	public String save_personal_information(String[] params) {
		String a = "ar?mode=A-user-mod&mode2=xiugaiziliao";
		LogDetect.send(LogDetect.DataType.specialType, "UsersManage_01152A:a==", a);
		String json = okhttp.requestPostBySyn(a, params);
		LogDetect.send(LogDetect.DataType.specialType, "UsersManage_01152A:json==", json);
		return json;
	}

	public ArrayList<Member_01152> zhubozhongxin(String[] params) {
		String a = "ar?mode=A-user-search&mode2=zhubozhongxin";
		String json = okhttp.requestPostBySyn(a, params);
		LogDetect.send(LogDetect.DataType.specialType, "UsersManage_01152A:json", json);
		ArrayList<Member_01152> list=helpmanager.Json_zhuboinfo(json);
		LogDetect.send(LogDetect.DataType.specialType, "UsersManage_01152A:集合", list);
		return list;
	}

	public String getwudarao(String[] params) {
		String a = "ar?mode=A-user-search&mode2=getwudarao";
		LogDetect.send(LogDetect.DataType.specialType, "UsersManage_01152A:a==", a);
		String json = okhttp.requestPostBySyn(a, params);
		LogDetect.send(LogDetect.DataType.specialType, "UsersManage_01152A:json==", json);
		return json;
	}

	public String xiugai(String[] params) {
		String a = "ar?mode=A-user-search&mode2=xiugai";
		LogDetect.send(LogDetect.DataType.specialType, "UsersManage_01152A:a==", a);
		String json = okhttp.requestPostBySyn(a, params);
		LogDetect.send(LogDetect.DataType.specialType, "UsersManage_01152A:json==", json);
		return json;
	}

	
	
	
}
