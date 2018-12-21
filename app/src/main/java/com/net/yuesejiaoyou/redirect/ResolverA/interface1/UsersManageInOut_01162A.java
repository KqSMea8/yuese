package com.net.yuesejiaoyou.redirect.ResolverA.interface1;

import android.os.Handler;


import com.net.yuesejiaoyou.classroot.interface2.OkHttp;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.redirect.ResolverA.core.UsersManage_01162A;
import com.net.yuesejiaoyou.redirect.ResolverA.getset.Page;
import com.net.yuesejiaoyou.redirect.ResolverA.getset.Tag;
import com.net.yuesejiaoyou.redirect.ResolverA.interface4.HelpManager_01162A;

import net.sf.json.JSONObject;

import java.util.ArrayList;


public class UsersManageInOut_01162A {
	HelpManager_01162A helpmanager = null;
	UsersManage_01162A usersManage = null;
	OkHttp okhttp=null;
	private LogDetect logDbg;

	public UsersManageInOut_01162A() {
		usersManage = new UsersManage_01162A();
		
	}

	public void my_phone_record(String[] params, Handler handler) {
		Page getdate = usersManage.my_phone_record(params);
		handler.sendMessage(handler.obtainMessage(200,(Object)getdate));
	}
	public void my_impression(String[] params, Handler handler) {
		ArrayList<Tag> getdate = usersManage.my_impression(params);
		handler.sendMessage(handler.obtainMessage(201,(Object)getdate));
	}
	/*public void evalue_search(String[] params, Handler handler) {
		String getdate = usersManage.evalue_search(params);
		handler.sendMessage(handler.obtainMessage(202,(Object)getdate));
	}
	public void evalue_search2(String[] params, Handler handler) {
		String getdate = usersManage.evalue_search2(params);
		handler.sendMessage(handler.obtainMessage(204,(Object)getdate));
	}
	public void save_evalue(String[] params, Handler handler) {
		String getdate = usersManage.save_evalue(params);
		handler.sendMessage(handler.obtainMessage(204,(Object)getdate));
	}*/
	public void wxlogin(String[] params, Handler handler) {
		String getdate = usersManage.wxlogin(params);
		handler.sendMessage(handler.obtainMessage(204,(Object)getdate));
		LogDetect.send(LogDetect.DataType.basicType,"01162","发送请求");
	}
	public void wxlogin_1(String[] params, Handler handler) {
		String getdate = usersManage.wxlogin_1(params);
		handler.sendMessage(handler.obtainMessage(205,(Object)getdate));
	}
	//打赏红包
	public void red_envelope(String[] params, Handler handler) {
		// TODO Auto-generated method stub
		String getdate = usersManage.red_envelope(params);
		//	LogDetect.send(LogDetect.DataType.specialType, "打赏红包_getdate： ",getdate.toString());

		JSONObject jsonObj = JSONObject.fromObject(getdate);
		jsonObj.put("value",params[2]);

		handler.sendMessage(handler.obtainMessage(101,(Object)jsonObj.toString()));

	}

	//判断第一次微信登陆
	public void wxlogin_pd(String[] params, Handler handler) {
		// TODO Auto-generated method stub
		String getdate = usersManage.wxlogin_pd(params);
		handler.sendMessage(handler.obtainMessage(501,(Object)getdate));
	}
	// 第一次绑定手机号 微信登陆
	public void wxlogin_tel(String[] params, Handler handler) {
		// TODO Auto-generated method stub
		String getdate = usersManage.wxlogin_tel(params);
		handler.sendMessage(handler.obtainMessage(502,(Object)getdate));
	}
	public void update_platform(String[] params, Handler handler) {
		String getdate = usersManage.update_platform(params);
		handler.sendMessage(handler.obtainMessage(210,(Object)getdate));
	}





	
	
}
