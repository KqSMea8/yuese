package com.net.yuesejiaoyou.redirect.ResolverA.interface1;

import java.io.IOException;
import java.util.ArrayList;


import android.os.Handler;

import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.redirect.ResolverA.core.UsersManage_01160A;
import com.net.yuesejiaoyou.redirect.ResolverA.getset.Page;
import com.net.yuesejiaoyou.redirect.ResolverA.getset.UserBean;
import com.net.yuesejiaoyou.redirect.ResolverA.interface4.HelpManager_01160A;


public class UsersManageInOut_01160A {

	UsersManage_01160A usersManage = null;
	private LogDetect logDbg;
	
	public UsersManageInOut_01160A() {
		usersManage = new UsersManage_01160A();
		
	}
	public void getip(String mode, Handler handler) throws IOException {

		String getdate = HelpManager_01160A.getNetIp();
		handler.sendMessage(handler.obtainMessage(200, (Object) getdate));
	}
	/*public void see_five_money(String[] params, Handler handler) {
		String getdate =  usersManage.see_five_money(params);
		handler.sendMessage(handler.obtainMessage(201, (Object) getdate));
	}
	public void see_money(String[] params, Handler handler) {
		String getdate =  usersManage.see_money(params);
		handler.sendMessage(handler.obtainMessage(202, (Object) getdate));
		
	}
	public void report(String[] params, Handler handler) {
		String getdate =  usersManage.report(params);
		handler.sendMessage(handler.obtainMessage(203, (Object) getdate));
		
	}*/
	public void black(String[] params, Handler handler) {
		String getdate =  usersManage.black(params);
		handler.sendMessage(handler.obtainMessage(204, (Object) getdate));
		
	}
/*	public void masschat(String[] params, Handler handler) {
		String getdate =  usersManage.masschat(params);
		handler.sendMessage(handler.obtainMessage(205, (Object) getdate));
		
	}*/
	public void xiaoxi(String[] params, Handler handler) {
		String getdate =  usersManage.xiaoxi(params);
		handler.sendMessage(handler.obtainMessage(206, (Object) getdate));
		
	}
/*	public void zhubo_yuyue(String[] params, Handler handler) {
		ArrayList<UserBean> getdate = usersManage.zhubo_yuyue(params);
		handler.sendMessage(handler.obtainMessage(207, (Object) getdate));
	}*/


	public void search_dv(String[] params, Handler handler) {
		ArrayList<UserBean> getdate = usersManage.search_dv(params);
		handler.sendMessage(handler.obtainMessage(208, (Object) getdate));
	}

    public void attention_videolist(String[] params, Handler handler) {
		Page getdata=usersManage.attention_videolist(params);
		handler.sendMessage(handler.obtainMessage(209,(Object) getdata));
    }

    /*public void i_like(String[] params, Handler handler) {
		Page getdata=usersManage.i_like(params);
		handler.sendMessage(handler.obtainMessage(201,(Object) getdata));
    }

	public void i_buy(String[] params, Handler handler) {
		Page getdata=usersManage.i_buy(params);
		handler.sendMessage(handler.obtainMessage(201,(Object) getdata));
	}
*/
    public void insert_reservation(String[] params, Handler handler) {
		String getdate =  usersManage.insert_reservation(params);
		handler.sendMessage(handler.obtainMessage(210, (Object) getdate));
    }
/*
    public void del_video(String[] params, Handler handler) {
		String getdate =  usersManage.del_video(params);
		handler.sendMessage(handler.obtainMessage(211, (Object) getdate));
    }*/
}
