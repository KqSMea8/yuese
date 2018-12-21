package com.net.yuesejiaoyou.redirect.ResolverB.core;

import com.net.yuesejiaoyou.redirect.ResolverB.getset.Page;
import com.net.yuesejiaoyou.redirect.ResolverB.getset.User_data;
import com.net.yuesejiaoyou.redirect.ResolverB.getset.Videoinfo;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.HelpManager_01066B;

import com.net.yuesejiaoyou.classroot.interface2.OkHttp;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;

import java.util.ArrayList;


public class UsersManage_01066B {
	HelpManager_01066B helpmanager = null;
	OkHttp okhttp=null;
	public UsersManage_01066B() {
		//httpclient = new HttpClientManage();
		okhttp=new OkHttp();
		helpmanager = new HelpManager_01066B();

	}
    public Page xunyuan_01066(String[] params){
		String a="xunyuan?mode=A-user-search&mode2=xunyuan";
		String json=okhttp.requestPostBySyn(a,params);
		Page mblist = helpmanager.xunyuan(json);
		return  mblist;
	}
	public Page stare_01066(String[] params){
		String a="xunyuan?mode=A-user-search&mode2=stares";
		String json=okhttp.requestPostBySyn(a,params);
		Page mblist = helpmanager.xunyuan(json);
		return  mblist;
	}

	public Page attend_01066(String[] params){
		String a="xunyuan?mode=A-user-search&mode2=attend";
		String json=okhttp.requestPostBySyn(a,params);
		Page mblist = helpmanager.xunyuan(json);
		return  mblist;
	}
	public Page new_01066(String[] params){
		String a="xunyuan?mode=A-user-search&mode2=xinru";
		String json=okhttp.requestPostBySyn(a,params);
		Page mblist = helpmanager.xunyuan(json);
		return  mblist;
	}



	public Page videolist(String[] params){
		String a="xunyuan?mode=A-user-search&mode2=videolist";
		String json=okhttp.requestPostBySyn(a,params);
		Page mblist = helpmanager.videolist(json);
		return  mblist;
	}
	public Page hotvideolist(String[] params){
		String a="xunyuan?mode=A-user-search&mode2=hotvideolist";
		String json=okhttp.requestPostBySyn(a,params);
		Page mblist = helpmanager.videolist(json);
		return  mblist;
	}
	public Page newvideolist(String[] params){
		String a="xunyuan?mode=A-user-search&mode2=newvideolist";
		String json=okhttp.requestPostBySyn(a,params);
		Page mblist = helpmanager.videolist(json);
		return  mblist;
	}

	public ArrayList<User_data> evaluate_list(String[] params){
		String a="xunyuan?mode=A-user-search&mode2=evaluate_list";
		String json=okhttp.requestPostBySyn(a,params);
		ArrayList<User_data> mblist = helpmanager.evaluate_list(json);
		return  mblist;
	}

	//登陆
	public String login(String[] params){
		String a = "xunyuan?mode=A-user-search&mode2=login";//含有两个数据用户名和密码
		String json = okhttp.requestPostBySyn(a, params);
		return json;
	}
	//登陆
	public String fblogin(String[] params){
		String a = "xunyuan?mode=A-user-search&mode2=fblogin";//含有两个数据用户名和密码
		String json = okhttp.requestPostBySyn(a, params);
		return json;
	}

	//登陆
	public String getcode(String[] params){
		String a = "xunyuan?mode=A-user-search&mode2=getcode";//含有两个数据用户名和密码
		String json = okhttp.requestPostBySyn(a, params);
		return json;
	}

	public String fgetcode(String[] params){
		String a = "xunyuan?mode=A-user-search&mode2=fgetcode";//含有两个数据用户名和密码
		String json = okhttp.requestPostBySyn(a, params);
		return json;
	}

	public String resetpassword(String[] params){
		String a = "xunyuan?mode=A-user-mod&mode2=resetpassword";//含有两个数据用户名和密码
		String json = okhttp.requestPostBySyn(a, params);
		return json;
	}
	//登陆
	public String register(String[] params){
		String a = "xunyuan?mode=A-user-search&mode2=register";//含有两个数据用户名和密码
		String json = okhttp.requestPostBySyn(a, params);
		return json;
	}


	public String guanzhu(String[] params){
		String a = "memberB?mode=A-user-mod&mode2=guanzhu";//含有两个数据用户名和密码
		String json = okhttp.requestPostBySyn(a, params);
		return json;
	}
	public String payvideo(String[] params){
		String a = "memberB?mode=A-user-mod&mode2=payvideo";//含有两个数据用户名和密码
		String json = okhttp.requestPostBySyn(a, params);
		return json;
	}


	//登陆
	public Videoinfo video_info(String[] params){
		String a = "memberB?mode=A-user-search&mode2=video_info";
		String json = okhttp.requestPostBySyn(a, params);
		LogDetect.send(LogDetect.DataType.basicType,"01162---短视频播放点击量返回","网址"+a+"---参数");
		LogDetect.send(LogDetect.DataType.basicType,"01162---短视频播放点击量返回",params);
		Videoinfo mblist = helpmanager.video_info(json);
		return mblist;
	}

	public String sharevideo(String[] params){
		String a = "memberB?mode=A-user-search&mode2=sharevideo";
		String json = okhttp.requestPostBySyn(a, params);
		LogDetect.send(LogDetect.DataType.basicType,"01162---短视频播放点击量返回","网址"+a+"---参数");
		LogDetect.send(LogDetect.DataType.basicType,"01162---短视频播放点击量返回",params);
		//Videoinfo mblist = helpmanager.video_info(json);
		return json;
	}



	public User_data userinfo(String[] params){
		String a="xunyuan?mode=A-user-search&mode2=userinfo";
		String json=okhttp.requestPostBySyn(a,params);
		User_data mblist = helpmanager.userinfo(json);
		return  mblist;
	}




	public String like(String[] params){
		String a="xunyuan?mode=A-user-add&mode2=like";
		String json=okhttp.requestPostBySyn(a,params);
		return  json;
	}

	public String fblogin_step2(String[] params){
		String a="xunyuan?mode=A-user-search&mode2=fblogin_step2";
		String json=okhttp.requestPostBySyn(a,params);
		return  json;
	}
}
