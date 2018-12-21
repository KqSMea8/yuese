package com.net.yuesejiaoyou.redirect.ResolverA.core;


import com.net.yuesejiaoyou.classroot.interface2.OkHttp;
import com.net.yuesejiaoyou.redirect.ResolverA.getset.Page;
import com.net.yuesejiaoyou.redirect.ResolverA.getset.User_data;
import com.net.yuesejiaoyou.redirect.ResolverB.getset.Videoinfo;
import com.net.yuesejiaoyou.redirect.ResolverA.interface4.HelpManager_01066A;

import java.util.ArrayList;


public class UsersManage_01066A {
	HelpManager_01066A helpmanager = null;
	OkHttp okhttp=null;
	public UsersManage_01066A() {

		okhttp=new OkHttp();
		helpmanager = new HelpManager_01066A();
	}
    public Page xunyuan_01066(String[] params){
		String a="ar?mode=A-user-search&mode2=xunyuan";
		String json=okhttp.requestPostBySyn(a,params);
		Page mblist = helpmanager.xunyuan(json);
		return  mblist;
	}
	public Page stare_01066(String[] params){
		String a="ar?mode=A-user-search&mode2=stares";
		String json=okhttp.requestPostBySyn(a,params);
		Page mblist = helpmanager.xunyuan(json);
		return  mblist;
	}
	public Page activity_v(String[] params){
		String a="ar?mode=A-user-search&mode2=activity_v";
		String json=okhttp.requestPostBySyn(a,params);
		Page mblist = helpmanager.xunyuan(json);
		return  mblist;
	}
	public Page remenman(String[] params){
		String a="ar?mode=A-user-search&mode2=remenman";
		String json=okhttp.requestPostBySyn(a,params);
		Page mblist = helpmanager.xunyuan(json);
		return  mblist;
	}


	public Page attend_01066(String[] params){
		String a="ar?mode=A-user-search&mode2=attend";
		String json=okhttp.requestPostBySyn(a,params);
		Page mblist = helpmanager.xunyuan(json);
		return  mblist;
	}
	public Page new_01066(String[] params){
		String a="ar?mode=A-user-search&mode2=xinru";
		String json=okhttp.requestPostBySyn(a,params);
		Page mblist = helpmanager.xunyuan(json);
		return  mblist;
	}



	public Page videolist(String[] params){
		String a="ar?mode=A-user-search&mode2=videolist";
		String json=okhttp.requestPostBySyn(a,params);
		Page mblist = helpmanager.videolist(json);
		return  mblist;
	}
	public Page hotvideolist(String[] params){
		String a="ar?mode=A-user-search&mode2=hotvideolist";
		String json=okhttp.requestPostBySyn(a,params);
		Page mblist = helpmanager.videolist(json);
		return  mblist;
	}
	public Page newvideolist(String[] params){
		String a="ar?mode=A-user-search&mode2=newvideolist";
		String json=okhttp.requestPostBySyn(a,params);
		Page mblist = helpmanager.videolist(json);
		return  mblist;
	}

	public ArrayList<User_data> evaluate_list(String[] params){
		String a="ar?mode=A-user-search&mode2=evaluate_list";
		String json=okhttp.requestPostBySyn(a,params);
		ArrayList<User_data> mblist = helpmanager.evaluate_list(json);
		return  mblist;
	}

	//登陆
	public String login(String[] params){
		String a = "ar?mode=A-user-search&mode2=login";//含有两个数据用户名和密码
		String json = okhttp.requestPostBySyn(a, params);
		return json;
	}


	//登陆
	public String getcode(String[] params){
		String a = "ar?mode=A-user-search&mode2=getcode";//含有两个数据用户名和密码
		String json = okhttp.requestPostBySyn(a, params);
		return json;
	}



	public String resetpassword(String[] params){
		String a = "ar?mode=A-user-mod&mode2=resetpassword";//含有两个数据用户名和密码
		String json = okhttp.requestPostBySyn(a, params);
		return json;
	}
	//登陆
	public String register(String[] params){
		String a = "ar?mode=A-user-search&mode2=register";//含有两个数据用户名和密码
		String json = okhttp.requestPostBySyn(a, params);
		return json;
	}


	public String guanzhu(String[] params){
		String a = "ar?mode=A-user-mod&mode2=guanzhu";//含有两个数据用户名和密码
		String json = okhttp.requestPostBySyn(a, params);
		return json;
	}
	public String payvideo(String[] params){
		String a = "ar?mode=A-user-mod&mode2=payvideo";//含有两个数据用户名和密码
		String json = okhttp.requestPostBySyn(a, params);
		return json;
	}


	//登陆
	public Videoinfo video_info(String[] params){
		String a = "ar?mode=A-user-search&mode2=video_info";
		String json = okhttp.requestPostBySyn(a, params);

		Videoinfo mblist = helpmanager.video_info(json);
		return mblist;
	}

	public User_data userinfo(String[] params){
		String a="ar?mode=A-user-search&mode2=userinfo";
		String json=okhttp.requestPostBySyn(a,params);
		User_data mblist = helpmanager.userinfo(json);
		return  mblist;
	}

	public String like(String[] params){
		String a="ar?mode=A-user-add&mode2=like";
		String json=okhttp.requestPostBySyn(a,params);
		return  json;
	}


}
