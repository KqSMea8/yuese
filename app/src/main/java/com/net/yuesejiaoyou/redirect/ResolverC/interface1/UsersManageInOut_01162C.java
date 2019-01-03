package com.net.yuesejiaoyou.redirect.ResolverC.interface1;

import android.os.Handler;



import com.net.yuesejiaoyou.classroot.interface2.OkHttp;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.redirect.ResolverC.core.UsersManage_01162C;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.Page;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.Tag;
import com.net.yuesejiaoyou.redirect.ResolverC.interface4.HelpManager_01162C;

import java.util.ArrayList;


public class UsersManageInOut_01162C {
	HelpManager_01162C helpmanager = null;
	UsersManage_01162C usersManage = null;
	OkHttp okhttp=null;
	private LogDetect logDbg;

	public UsersManageInOut_01162C() {
		usersManage = new UsersManage_01162C();
		
	}

	public void my_phone_record(String[] params, Handler handler) {
		Page getdate = usersManage.my_phone_record(params);
		handler.sendMessage(handler.obtainMessage(200,(Object)getdate));
	}

	public void my_impression(String[] params, Handler handler) {
		ArrayList<Tag> getdate = usersManage.my_impression(params);
		handler.sendMessage(handler.obtainMessage(201,(Object)getdate));
	}
















	public void evalue_search(String[] params, Handler handler) {
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
	}
	public void wxlogin(String[] params, Handler handler) {
		String getdate = usersManage.wxlogin(params);
		handler.sendMessage(handler.obtainMessage(204,(Object)getdate));
	}
	public void wxlogin_1(String[] params, Handler handler) {
		String getdate = usersManage.wxlogin_1(params);
		handler.sendMessage(handler.obtainMessage(205,(Object)getdate));
	}







	
	
}
