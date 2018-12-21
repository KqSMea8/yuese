package com.net.yuesejiaoyou.redirect.ResolverA.core;



import com.net.yuesejiaoyou.classroot.interface2.OkHttp;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.redirect.ResolverA.getset.Page;
import com.net.yuesejiaoyou.redirect.ResolverA.getset.Tag;
import com.net.yuesejiaoyou.redirect.ResolverA.interface4.HelpManager_01162A;

import java.util.ArrayList;


public class UsersManage_01162A {
	HelpManager_01162A helpmanager = null;
	OkHttp okhttp=null;
	public UsersManage_01162A() {
		//httpclient = new HttpClientManage();
		okhttp=new OkHttp();
		helpmanager = new HelpManager_01162A();

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
	/*public String evalue_search(String[] params) {
		String a = "rp?mode=A-user-search&mode2=evalue_search";
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
		String a = "rp?mode=A-user-add&mode2=save_evalue";
		String json = okhttp.requestPostBySyn(a, params);
		//LogDetect.send(LogDetect.DataType.basicType,"01162---json返回",json);
		return json;
	}*/
	public String wxlogin(String[] params) {
		String a = "ar?mode=A-user-search&mode2=wxlogin";
		LogDetect.send(LogDetect.DataType.basicType,"01162","发送请求");
		String json = okhttp.requestPostBySyn(a, params);
		return json;
	}
	public String wxlogin_1(String[] params) {
		String a = "ar?mode=A-user-search&mode2=wxlogin_1";
		String json = okhttp.requestPostBySyn(a, params);
		return json;
	}
	//打赏红包
	public String red_envelope(String[] params) {
		// TODO Auto-generated method stub
		String a = "memberB?mode=A-user-add&mode2=red_envelope";
		String json = okhttp.requestPostBySyn(a, params);
		//LogDetect.send(LogDetect.DataType.specialType, "打赏红包__json： ",json);



		return json;
	}
	public String wxlogin_pd(String[] params) {
		String a = "ar?mode=A-user-search&mode2=wxlogin_pd";
		String json = okhttp.requestPostBySyn(a, params);
		return json;
	}
	public String wxlogin_tel(String[] params) {
		String a = "ar?mode=A-user-search&mode2=wxlogin_tel";
		String json = okhttp.requestPostBySyn(a, params);
		return json;
	}
	public String update_platform(String[] params) {
		String a = "ar?mode=A-user-mod&mode2=update_platform";
		String json = okhttp.requestPostBySyn(a, params);
		return json;
	}
}
