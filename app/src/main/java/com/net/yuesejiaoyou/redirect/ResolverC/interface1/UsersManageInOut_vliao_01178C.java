package com.net.yuesejiaoyou.redirect.ResolverC.interface1;


import android.os.Handler;

import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.redirect.ResolverC.core.UsersManage_vliao_01178C;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.Page;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.FansBean;

import java.util.ArrayList;


public class UsersManageInOut_vliao_01178C {

	UsersManage_vliao_01178C usersManage;
	
	public UsersManageInOut_vliao_01178C() {
		usersManage = new UsersManage_vliao_01178C();
		
	}
	
	public void pay(String[] params, Handler handler) {
		String getdate = usersManage.pay(params);
		handler.sendMessage(handler.obtainMessage(119, (Object) getdate));
	}

	public void wodeqimibang(String[] params, Handler handler) {
		Page list=usersManage.wodeqimibang(params);
		///////////////////////////
		LogDetect.send(LogDetect.DataType.specialType, "他的亲密榜:",params);
		///////////////////////////
		handler.sendMessage(handler.obtainMessage(202,(Object)list));
		
	}

	public void vcurrencyconfirm(String[] params, Handler handler) {

		String getdate = usersManage.vcurrencyconfirm(params);
		handler.sendMessage(handler.obtainMessage(120, (Object) getdate));
		
	}

	public void vcurrency(String[] params, Handler handler) {

		ArrayList<FansBean> list1=usersManage.vcurrency(params);

		handler.sendMessage(handler.obtainMessage(121,(Object)list1));
		
	}

	public void payprice(String[] params, Handler handler) {
		ArrayList<FansBean> list2=usersManage.payprice(params);
		handler.sendMessage(handler.obtainMessage(200,(Object)list2));
	}
	
	
}
