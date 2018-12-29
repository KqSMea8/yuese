package com.net.yuesejiaoyou.redirect.ResolverA.interface1;

import android.os.Handler;


import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.redirect.ResolverA.core.UsersManage_01066A;
import com.net.yuesejiaoyou.redirect.ResolverA.getset.Page;
import com.net.yuesejiaoyou.redirect.ResolverA.getset.User_data;
import com.net.yuesejiaoyou.redirect.ResolverB.getset.Videoinfo;

import java.util.ArrayList;


public class UsersManageInOut_01066A {

	UsersManage_01066A usersManage = null;
	private LogDetect logDbg;

	public UsersManageInOut_01066A() {
		usersManage = new UsersManage_01066A();
	}
	public void xunyuan_01066(String []params,Handler handler){
		Page getdata=usersManage.xunyuan_01066(params);
		handler.sendMessage(handler.obtainMessage(201,(Object) getdata));
	}

	public void stare_01066(String []params,Handler handler){
		Page getdata=usersManage.stare_01066(params);
		handler.sendMessage(handler.obtainMessage(201,(Object) getdata));
	}
	public void activity_v(String []params,Handler handler){
		Page getdata=usersManage.activity_v(params);
		handler.sendMessage(handler.obtainMessage(201,(Object) getdata));
	}
	public void remenman(String []params,Handler handler){
		Page getdata=usersManage.remenman(params);
		handler.sendMessage(handler.obtainMessage(201,(Object) getdata));
	}


	public void attend_01066(String []params,Handler handler){
		Page getdata=usersManage.attend_01066(params);
		handler.sendMessage(handler.obtainMessage(201,(Object) getdata));
	}
	public void new_01066(String []params,Handler handler){
		Page getdata=usersManage.new_01066(params);
		handler.sendMessage(handler.obtainMessage(201,(Object) getdata));
	}


	public void videolist(String []params,Handler handler){
		Page getdata=usersManage.videolist(params);
		handler.sendMessage(handler.obtainMessage(201,(Object) getdata));
	}

	public void hotvideolist(String []params,Handler handler){
		Page getdata=usersManage.hotvideolist(params);
		handler.sendMessage(handler.obtainMessage(201,(Object) getdata));
	}
	public void newvideolist(String []params,Handler handler){
		Page getdata=usersManage.newvideolist(params);
		handler.sendMessage(handler.obtainMessage(201,(Object) getdata));
	}


	public void evaluate_list(String []params,Handler handler){
		ArrayList<User_data> getdata=usersManage.evaluate_list(params);
		handler.sendMessage(handler.obtainMessage(201,(Object) getdata));
	}

	public void login(String[] params, Handler handler){
		String getdate = usersManage.login(params);
		handler.sendMessage(handler.obtainMessage(200, (Object) getdate));
	}

	public void getcode(String[] params, Handler handler){
		String getdate = usersManage.getcode(params);
		handler.sendMessage(handler.obtainMessage(201, (Object) getdate));
	}



	public void resetpassword(String[] params, Handler handler){
		String getdate = usersManage.resetpassword(params);
		handler.sendMessage(handler.obtainMessage(200, (Object) getdate));
	}

	public void register(String[] params, Handler handler){
		String getdate = usersManage.register(params);
		handler.sendMessage(handler.obtainMessage(200, (Object) getdate));
	}



	public void video_info(String[] params, Handler handler){
		Videoinfo getdate = usersManage.video_info(params);
		handler.sendMessage(handler.obtainMessage(200, (Object) getdate));
	}

	public void guanzhu(String[] params, Handler handler){
		String getdate = usersManage.guanzhu(params);
		handler.sendMessage(handler.obtainMessage(500, (Object) getdate));
	}
	public void payvideo(String[] params, Handler handler){
		String getdate = usersManage.payvideo(params);
		handler.sendMessage(handler.obtainMessage(550, (Object) getdate));
	}


	public void userinfo(String []params,Handler handler){
		User_data getdata=usersManage.userinfo(params);
		handler.sendMessage(handler.obtainMessage(201,(Object) getdata));
	}


}
