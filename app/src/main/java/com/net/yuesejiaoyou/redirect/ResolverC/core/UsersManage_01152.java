package com.net.yuesejiaoyou.redirect.ResolverC.core;


import com.net.yuesejiaoyou.classroot.interface2.OkHttp;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.Member_01152;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.Page;
import com.net.yuesejiaoyou.redirect.ResolverC.interface4.HelpManager_01152;

import java.io.IOException;
import java.util.ArrayList;


public class UsersManage_01152 {
	HelpManager_01152 helpmanager = null;
	OkHttp okhttp=null;
	public UsersManage_01152() {
//		httpclient = new HttpClientManage();
		okhttp=new OkHttp();
		helpmanager = new HelpManager_01152();

	}

	public String shenqing(String[] params) {
		String a = "memberC01178?mode=A-user-mod&mode2=shenqing";
		LogDetect.send(LogDetect.DataType.specialType, "UsersManage_01152:a==", a);
		String json = okhttp.requestPostBySyn(a, params);
		LogDetect.send(LogDetect.DataType.specialType, "UsersManage_01152:json==", json);
		return json;
	}

	public ArrayList<Member_01152> personal_information(String[] params) {
		String a = "ar?mode=A-user-search&mode2=gerenzhongxin";
		String json = okhttp.requestPostBySyn(a, params);
		LogDetect.send(LogDetect.DataType.specialType, "UsersManage_01152:json", json);
		ArrayList<Member_01152> list=helpmanager.Json_zhuboinfo(json);
		LogDetect.send(LogDetect.DataType.specialType, "UsersManage_01152:集合", list);
		return list;
	}

	public String save_personal_information(String[] params) {
		String a = "memberC01178?mode=A-user-mod&mode2=xiugaiziliao";
		LogDetect.send(LogDetect.DataType.specialType, "UsersManage_01152:a==", a);
		String json = okhttp.requestPostBySyn(a, params);
		LogDetect.send(LogDetect.DataType.specialType, "UsersManage_01152:json==", json);
		return json;
	}








    
	public String lat_lonmod(String[] params) throws IOException {
		String a = "member?mode=A-user-mod&mode2=lat_lonmod";
		String json = okhttp.requestPostBySyn(a, params);
		return json;
	}

	public ArrayList<Member_01152> gerenzhongxin(String[] params) {
		String a = "vl?mode=A-user-search&mode2=gerenzhongxin";
		String json = okhttp.requestPostBySyn(a, params);
		LogDetect.send(LogDetect.DataType.specialType, "UsersManage_01152:json", json);
		ArrayList<Member_01152> list=helpmanager.Json_zhuboinfo(json);
		LogDetect.send(LogDetect.DataType.specialType, "UsersManage_01152:集合", list);
		return list;
	}



	public ArrayList<Member_01152> zhubozhongxin(String[] params) {
		String a = "vl?mode=A-user-search&mode2=gerenzhongxin";
		String json = okhttp.requestPostBySyn(a, params);
		LogDetect.send(LogDetect.DataType.specialType, "UsersManage_01152:json", json);
		ArrayList<Member_01152> list=helpmanager.Json_zhuboinfo(json);
		LogDetect.send(LogDetect.DataType.specialType, "UsersManage_01152:集合", list);
		return list;
	}

	public String getwudarao(String[] params) {
		String a = "vl?mode=A-user-search&mode2=getwudarao";
		LogDetect.send(LogDetect.DataType.specialType, "UsersManage_01152:a==", a);
		String json = okhttp.requestPostBySyn(a, params);
		LogDetect.send(LogDetect.DataType.specialType, "UsersManage_01152:json==", json);
		return json;
	}

	public String xiugai(String[] params) {
		String a = "vl?mode=A-user-search&mode2=xiugai";
		LogDetect.send(LogDetect.DataType.specialType, "UsersManage_01152:a==", a);
		String json = okhttp.requestPostBySyn(a, params);
		LogDetect.send(LogDetect.DataType.specialType, "UsersManage_01152:json==", json);
		return json;
	}

	public ArrayList<Member_01152> tuiguang(String[] params) {
		String a = "memberC01178?mode=A-user-search&mode2=promote_income";
		String json = okhttp.requestPostBySyn(a, params);
		LogDetect.send(LogDetect.DataType.specialType, "UsersManage_01152:json", json);
		ArrayList<Member_01152> list=helpmanager.Json_info(json);
		LogDetect.send(LogDetect.DataType.specialType, "UsersManage_01152:集合", list);
		return list;
	}

	public Page xiaofei(String[] params) {
		String a = "vl?mode=A-user-search&mode2=xiaofei";
		String json = okhttp.requestPostBySyn(a, params);
		LogDetect.send(LogDetect.DataType.specialType, "UsefrsManage_01152:json", json);
		Page list=helpmanager.Json_mbinfo(json);
		LogDetect.send(LogDetect.DataType.specialType, "UsersManage_01152:集合", list);
		return list;
	}



	public Page tuiguangrenshu(String[] params) {
		String a = "vl?mode=A-user-search&mode2=renshu";
		String json = okhttp.requestPostBySyn(a, params);
		LogDetect.send(LogDetect.DataType.specialType, "UsefrsManage_01152:json", json);
		Page list=helpmanager.Json_fo(json);
		LogDetect.send(LogDetect.DataType.specialType, "UsersManage_01152:集合", list);
		return list;
	}
	
	
	
	
	
}
