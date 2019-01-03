package com.net.yuesejiaoyou.redirect.ResolverC.core;


import com.net.yuesejiaoyou.classroot.interface2.OkHttp;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.FansBean;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.Page;
import com.net.yuesejiaoyou.redirect.ResolverC.interface4.HelpManager_vliao_01178C;

import java.util.ArrayList;


public class UsersManage_vliao_01178C {
	HelpManager_vliao_01178C helpmanager = null;
	OkHttp okhttp=null;
	public UsersManage_vliao_01178C() {
		//httpclient = new HttpClientManage();
		okhttp=new OkHttp();
		helpmanager = new HelpManager_vliao_01178C();

	}


	public String pay(String[] params) {
		LogDetect.send(LogDetect.DataType.specialType, "--UsersManage_vliao_01178--:", "01178");
		String a = "vliaopay?mode=A-user-add&mode2=pay";
		LogDetect.send(LogDetect.DataType.specialType, "--UsersManage_vliao_01178--a:--", a);
		String json = okhttp.requestPostBySyn(a, params);
		return json;
	}


	public Page wodeqimibang(String[] params) {
		String a = "memberC01178?mode=A-user-search&mode2=wodeqimibang";
		//memberC01178?mode=A-user-search&mode2=wodeqimibang&mode3=5&mode4=2
		///////////////////////////
		LogDetect.send(LogDetect.DataType.specialType, "他的亲密榜:--------",a+"-----------"+params[0]+"----"+params[1]+"----"+params[2]);
		///////////////////////////
		String json = okhttp.requestPostBySyn(a, params);
		Page mblist = helpmanager.wodeqimibang(json);

		return mblist;
	}


	public String vcurrencyconfirm(String[] params) {
		String a = "memberC01178?mode=A-user-add&mode2=vcurrencyconfirm";
		String json = okhttp.requestPostBySyn(a, params);
		return json;
	}
	public ArrayList<FansBean> payprice(String[] params) {
		String a = "memberC01178?mode=A-user-search&mode2=payprice";
		String json = okhttp.requestPostBySyn(a, params);
		ArrayList<FansBean> mblist = helpmanager.payprice(json);
		return mblist;
	}






	public ArrayList<FansBean> vcurrency(String[] params) {
		String a = "vliaopay?mode=A-user-search&mode2=vcurrency";
		String json = okhttp.requestPostBySyn(a, params);
		ArrayList<FansBean> mblist = helpmanager.vcurrency(json);
		return mblist;
	}



	
	
}
