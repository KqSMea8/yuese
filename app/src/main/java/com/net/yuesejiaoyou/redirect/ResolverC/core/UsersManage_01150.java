package com.net.yuesejiaoyou.redirect.ResolverC.core;

/*import com.example.vliao.interface2.OkHttp;
import com.example.vliao.interface4.HelpManager_01150;
import com.example.vliao.interface4.LogDetect;*/

import com.net.yuesejiaoyou.classroot.interface2.OkHttp;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.redirect.ResolverC.interface4.HelpManager_01150;

import java.io.IOException;


public class UsersManage_01150 {
	HelpManager_01150 helpmanager = null;
	OkHttp okhttp=null;
	public UsersManage_01150() {
		//httpclient = new HttpClientManage();
		okhttp=new OkHttp();
		helpmanager = new HelpManager_01150();

	}

	//提交认证申请
	public String renzheng(String[] params) throws IOException {
		String a = "memberC01178?mode=A-user-add&mode2=renzheng";
		LogDetect.send(LogDetect.DataType.specialType, "UsersManage_01150——提交认证申请（）请求链接: ", a+params);
		String json = okhttp.requestPostBySyn(a, params);
		LogDetect.send(LogDetect.DataType.specialType, "UsersManage_01150——提交认证申请（）json: ", json);
		return json;
	}
	//查询用户信息
	public String my_info(String[] params) throws IOException {
		String a = "memberC01178?mode=A-user-search&mode2=my_info";
		String json = okhttp.requestPostBySyn(a, params);
		LogDetect.send(LogDetect.DataType.specialType, "UsersManage_01150——查询用户信息（）json: ", json);
		return json;
	}
	//修改用户信息
	public String mod_info(String[] params) throws IOException {
		String a = "memberC01178?mode=A-user-mod&mode2=mod_info";
		String json = okhttp.requestPostBySyn(a, params);
		LogDetect.send(LogDetect.DataType.specialType, "UsersManage_01150——修改用户信息（）json: ", json);
		return json;
	}




	public String lat_lonmod(String[] params) throws IOException {
		String a = "member?mode=A-user-mod&mode2=lat_lonmod";
		String json = okhttp.requestPostBySyn(a, params);
		return json;
	}

	//查询密码
	public String check_pwd(String[] params) throws IOException {
		String a = "ar?mode=A-user-search&mode2=check_pwd";
		LogDetect.send(LogDetect.DataType.specialType, "UsersManage_01150——查询密码（）请求链接: ", a+params);
		String json = okhttp.requestPostBySyn(a, params);
		LogDetect.send(LogDetect.DataType.specialType, "UsersManage_01150——查询密码（）json: ", json);
		return json;
	}

	
	
	
	
	
	
}
