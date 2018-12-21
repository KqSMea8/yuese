package com.net.yuesejiaoyou.redirect.ResolverB.interface1;

import android.os.Handler;

import com.net.yuesejiaoyou.redirect.ResolverB.core.UsersManage_01158B;
import com.net.yuesejiaoyou.redirect.ResolverB.getset.Page;
import com.net.yuesejiaoyou.redirect.ResolverB.getset.photo_01162;

import java.io.IOException;
import java.util.ArrayList;

import com.net.yuesejiaoyou.classroot.interface4.LogDetect;

public class UsersManageInOut_01158B {

	UsersManage_01158B usersManage = null;
	private LogDetect logDbg;
	
	public UsersManageInOut_01158B() {
		usersManage = new UsersManage_01158B();
		
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

	public void mod_online1(String[] params, Handler handler)throws IOException {
		String getdate = usersManage.mod_online1(params);
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

	public void setlike(String[] params,Handler handler)throws IOException{
		String getdate = usersManage.setlike(params);
		handler.sendMessage(handler.obtainMessage(111,(Object)getdate));
	}

	public void getfreevideo(String[] params,Handler handler){
		String getdate = usersManage.getfreevideo(params);
		handler.sendMessage(handler.obtainMessage(100,(Object)getdate));
	}

	public void getrewardvideo(String[] params,Handler handler){
		String getdate = usersManage.getrewardvideo(params);
		handler.sendMessage(handler.obtainMessage(110,(Object)getdate));
	}

	public void getmyvideo(String[] params,Handler handler){
        Page getdata = usersManage.getmyvideo(params);
		handler.sendMessage(handler.obtainMessage(201,(Object)getdata));
	}

	public void mod_mang(String[] params, Handler handler) {
		String getdate = usersManage.mod_mang(params);
		handler.sendMessage(handler.obtainMessage(202,(Object)getdate));
	}

    public void guke_online(String[] params, Handler handler) {
		String getdate = usersManage.guke_online(params);
		handler.sendMessage(handler.obtainMessage(240,(Object)getdate));
    }

    public void mod_return(String[] params, Handler handler) {
		String getdate = usersManage.mod_return(params);
		handler.sendMessage(handler.obtainMessage(300,(Object)getdate));
    }

	public void checkyue(String[] params, Handler handler) throws IOException {
		String getdate = usersManage.checkyue(params);
		LogDetect.send(LogDetect.DataType.specialType,"01160 发请求:", "");
		handler.sendMessage(handler.obtainMessage(270,(Object)getdate));
	}

    public void modezhubostate(String[] params, Handler handler) throws IOException {
        String getdate = usersManage.modezhubostate(params);
        handler.sendMessage(handler.obtainMessage(220,(Object)getdate));
    }
    public void pushp2pvideo(String[] params, Handler handler) throws IOException {
		String getdate = usersManage.pushp2pvideo(params);
		if(handler != null) {
			handler.sendMessage(handler.obtainMessage(500, (Object) getdate));
		}
	}

	public void removep2pvideo(String[] params, Handler handler) throws IOException {
		String getdate = usersManage.removep2pvideo(params);
		handler.sendMessage(handler.obtainMessage(510,(Object)getdate));
	}

	public void statuschange(String[] params, Handler handler) throws IOException {
		String getdate = usersManage.statuschange(params);
		handler.sendMessage(handler.obtainMessage(520,(Object)getdate));
	}

	public void pushcmdmsg(String[] params, Handler handler) throws IOException {
		String getdate = usersManage.pushcmdmsg(params);
		handler.sendMessage(handler.obtainMessage(1000,(Object)getdate));
	}
}
