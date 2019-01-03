package com.net.yuesejiaoyou.redirect.ResolverA.interface1;

import android.os.Handler;


import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.redirect.ResolverA.core.UsersManage_01158A;
import com.net.yuesejiaoyou.redirect.ResolverA.getset.photo_01162;

import java.io.IOException;
import java.util.ArrayList;


public class UsersManageInOut_01158A {

	UsersManage_01158A usersManage = null;
	private LogDetect logDbg;
	
	public UsersManageInOut_01158A() {
		usersManage = new UsersManage_01158A();
		
	}
	/*public void login(String[] params, Handler handler) throws IOException {
		String getdate = usersManage.login(params);
		handler.sendMessage(handler.obtainMessage(200, (Object) getdate));
	}*/
	public void activity_search(String[] params, Handler handler) throws IOException {
		ArrayList<photo_01162> getdate = usersManage.activity_search(params);
		handler.sendMessage(handler.obtainMessage(202, (Object) getdate));
	}

/*
	public void zhubo_online(String[] params, Handler handler)throws IOException {
		String getdate = usersManage.zhubo_online(params);
		handler.sendMessage(handler.obtainMessage(230,(Object)getdate));
	}
	public void mod_online(String[] params, Handler handler)throws IOException {
		String getdate = usersManage.mod_online(params);
		handler.sendMessage(handler.obtainMessage(260,(Object)getdate));
	}
	public void kou_frist(String[] params, Handler handler)throws IOException {
		String getdate = usersManage.kou_frist(params);
		LogDetect.send(LogDetect.DataType.specialType,"01160 发请求:", "");
		handler.sendMessage(handler.obtainMessage(270,(Object)getdate));
	}

	public void kou_zhubo(String[] params, Handler handler)throws IOException {
		String getdate = usersManage.kou_zhubo(params);
		handler.sendMessage(handler.obtainMessage(280,(Object)getdate));
	}

	public void xfliaotian(String[] params, Handler handler)throws IOException {
		String getdate = usersManage.xfliaotian(params);
		handler.sendMessage(handler.obtainMessage(290,(Object)getdate));
	}*/

	public void xiugai(String[] params, Handler handler)throws IOException {
		String getdate = usersManage.xiugai(params);
		handler.sendMessage(handler.obtainMessage(113,(Object)getdate));
	}

	public void setlike(String[] params,Handler handler)throws IOException{
		String getdate = usersManage.setlike(params);
		handler.sendMessage(handler.obtainMessage(111,(Object)getdate));
	}


}
