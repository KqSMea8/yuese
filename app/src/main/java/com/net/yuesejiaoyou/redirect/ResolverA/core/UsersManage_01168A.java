package com.net.yuesejiaoyou.redirect.ResolverA.core;

import com.net.yuesejiaoyou.classroot.interface2.OkHttp;
import com.net.yuesejiaoyou.redirect.ResolverA.getset.Vliao1_01168;
import com.net.yuesejiaoyou.redirect.ResolverA.interface4.HelpManager_01168A;

import java.io.IOException;
import java.util.ArrayList;



public class UsersManage_01168A {
	HelpManager_01168A helpmanager = null;
	OkHttp okhttp=null;
	public UsersManage_01168A() {
		//httpclient = new HttpClientManage();
		okhttp=new OkHttp();
		helpmanager = new HelpManager_01168A();

	}
    
	
	//V聊意见反馈
	public String Vliao_yijianfankui(String[] params) throws IOException {
		String a = "ar?mode=A-user-search&mode2=Vliao_yijianfankui";
		String json = okhttp.requestPostBySyn(a, params);
		return json;
	}


	//黑名单
	public ArrayList<Vliao1_01168> heimingdan(String[] params) {//需要修改getset、HelpManager
		String a = "ar?mode=A-user-search&mode2=heimingdan";
		String json = okhttp.requestPostBySyn(a, params);
		ArrayList<Vliao1_01168> mblist=helpmanager.heimingdan(json);
		return mblist;
	}
	
	
	public String quxiao(String[] params) throws IOException {
		String a = "ar?mode=A-user-search&mode2=quxiao";
		String json = okhttp.requestPostBySyn(a, params);
		return json;
	}
	
	
	
	
	public String lat_lonmod(String[] params) throws IOException {
		String a = "member?mode=A-user-mod&mode2=lat_lonmod";
		String json = okhttp.requestPostBySyn(a, params);
		return json;
	}
	


	
}
