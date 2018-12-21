package com.net.yuesejiaoyou.redirect.ResolverD.interface1;


import android.os.Handler;


import com.net.yuesejiaoyou.redirect.ResolverD.core.UsersManage_vliao_01178;
import com.net.yuesejiaoyou.redirect.ResolverD.getset.vliaofans_01168;

import java.util.ArrayList;


public class UsersManageInOut_vliao_01178 {

	UsersManage_vliao_01178 usersManage;
	
	public UsersManageInOut_vliao_01178() {
		usersManage = new UsersManage_vliao_01178();
	}
	public void pay(String[] params, Handler handler) {
		String getdate = usersManage.pay(params);
		handler.sendMessage(handler.obtainMessage(119, (Object) getdate));
	}

/*	public void wodeqimibang(String[] params, Handler handler) {
		Page list=usersManage.wodeqimibang(params);
		handler.sendMessage(handler.obtainMessage(202,(Object)list));
		
	}

	public void vcurrencyconfirm(String[] params, Handler handler) {

		String getdate = usersManage.vcurrencyconfirm(params);
		handler.sendMessage(handler.obtainMessage(120, (Object) getdate));
		
	}

	public void vcurrency(String[] params, Handler handler) {

		ArrayList<vliaofans_01168> list1=usersManage.vcurrency(params);

		handler.sendMessage(handler.obtainMessage(121,(Object)list1));
		
	}*/

	public void payprice(String[] params, Handler handler) {
		ArrayList<vliaofans_01168> list2=usersManage.payprice(params);
		handler.sendMessage(handler.obtainMessage(200,(Object)list2));
	}
	
	
}
