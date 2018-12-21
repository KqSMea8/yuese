package com.net.yuesejiaoyou.redirect.ResolverB.core;

import java.util.ArrayList;

import com.net.yuesejiaoyou.redirect.ResolverB.getset.Page;
import com.net.yuesejiaoyou.redirect.ResolverB.getset.Tag;
import com.net.yuesejiaoyou.classroot.interface2.OkHttp;




public class UsersManage_01162B {
	com.net.yuesejiaoyou.redirect.ResolverB.interface4.HelpManager_01162B helpmanager = null;
	OkHttp okhttp=null;
	public UsersManage_01162B() {
		//httpclient = new HttpClientManage();
		okhttp=new OkHttp();
		helpmanager = new com.net.yuesejiaoyou.redirect.ResolverB.interface4.HelpManager_01162B();

	}

	public Page my_phone_record(String[] params) {
		String a = "rp?mode=A-user-search&mode2=my_phone_record";
		String json = okhttp.requestPostBySyn(a, params);
		Page page=helpmanager.my_phone_record(json);
		return page;
	}
	public ArrayList<Tag> my_impression(String[] params) {
		String a = "rp?mode=A-user-search&mode2=my_impression";
		String json = okhttp.requestPostBySyn(a, params);
		ArrayList<Tag> list=helpmanager.my_impression(json);
		return list;
}
	public String evalue_search(String[] params) {
		String a = "memberB?mode=A-user-search&mode2=evalue_search";
		String json = okhttp.requestPostBySyn(a, params);
		//LogDetect.send(LogDetect.DataType.basicType,"01162---json返回",json);
		return json;
	}
	public String evalue_search2(String[] params) {
		String a = "rp?mode=A-user-search&mode2=evalue_search2";
		String json = okhttp.requestPostBySyn(a, params);
		//LogDetect.send(LogDetect.DataType.basicType,"01162---json返回",json);
		return json;
	}
	public String save_evalue(String[] params) {
		String a = "memberB?mode=A-user-add&mode2=save_evalue";
		String json = okhttp.requestPostBySyn(a, params);
		//LogDetect.send(LogDetect.DataType.basicType,"01162---json返回",json);
		return json;
	}
	public String wxlogin(String[] params) {
		String a = "xunyuan?mode=A-user-search&mode2=wxlogin";
		String json = okhttp.requestPostBySyn(a, params);
		return json;
	}
	public String wxlogin_1(String[] params) {
		String a = "xunyuan?mode=A-user-search&mode2=wxlogin_1";
		String json = okhttp.requestPostBySyn(a, params);
		return json;
	}
}
