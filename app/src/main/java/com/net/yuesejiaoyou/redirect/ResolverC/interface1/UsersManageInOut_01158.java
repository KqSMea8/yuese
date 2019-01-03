package com.net.yuesejiaoyou.redirect.ResolverC.interface1;

import android.os.Handler;


import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.redirect.ResolverC.core.UsersManage_01158;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.Page;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.photo_01162;

import java.io.IOException;
import java.util.ArrayList;


public class UsersManageInOut_01158 {

	UsersManage_01158 usersManage = null;
	private LogDetect logDbg;
	
	public UsersManageInOut_01158() {
		usersManage = new UsersManage_01158();
		
	}

	public void guke_online(String[] params, Handler handler) {
		String getdate = usersManage.guke_online(params);
		handler.sendMessage(handler.obtainMessage(240,(Object)getdate));
	}







	public void login(String[] params, Handler handler) throws IOException {
		String getdate = usersManage.login(params);
		handler.sendMessage(handler.obtainMessage(200, (Object) getdate));
	}
	public void activity_search(String[] params, Handler handler) throws IOException {
		ArrayList<photo_01162> getdate = usersManage.activity_search(params);
		handler.sendMessage(handler.obtainMessage(202, (Object) getdate));
	}


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
	}

	public void xiugai(String[] params, Handler handler)throws IOException {
		String getdate = usersManage.xiugai(params);
		handler.sendMessage(handler.obtainMessage(113,(Object)getdate));
	}

	public void setlike(String[] params, Handler handler)throws IOException {
		String getdate = usersManage.setlike(params);
		handler.sendMessage(handler.obtainMessage(111,(Object)getdate));
	}

	public void getfreevideo(String[] params, Handler handler){
		String getdate = usersManage.getfreevideo(params);
		handler.sendMessage(handler.obtainMessage(100,(Object)getdate));
	}

	public void getrewardvideo(String[] params, Handler handler){
		String getdate = usersManage.getrewardvideo(params);
		handler.sendMessage(handler.obtainMessage(110,(Object)getdate));
	}

	public void getmyvideo(String[] params, Handler handler){
        Page getdata = usersManage.getmyvideo(params);
		handler.sendMessage(handler.obtainMessage(201,(Object)getdata));
	}

	public void mod_mang(String[] params, Handler handler) {
		String getdate = usersManage.mod_mang(params);
		handler.sendMessage(handler.obtainMessage(202,(Object)getdate));
	}



    public void mod_return(String[] params, Handler handler) {
		String getdate = usersManage.mod_return(params);
		handler.sendMessage(handler.obtainMessage(300,(Object)getdate));
    }
}
