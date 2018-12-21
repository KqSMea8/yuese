package com.net.yuesejiaoyou.redirect.ResolverA.interface1;

import java.io.IOException;
import java.util.ArrayList;


import android.os.Handler;

import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.redirect.ResolverA.core.UsersManage_01168A;
import com.net.yuesejiaoyou.redirect.ResolverA.getset.Vliao1_01168;


public class UsersManageInOut_01168A {

	UsersManage_01168A usersManage = null;
	private LogDetect logDbg;
	
	public UsersManageInOut_01168A() {
		usersManage = new UsersManage_01168A();
		
	}
	/*public void getip(String mode, Handler handler) throws IOException {

		String getdate = HelpManager_01168A.getNetIp();
		handler.sendMessage(handler.obtainMessage(200, (Object) getdate));
	}
	*/
	public void Vliao_yijianfankui(String[] params, Handler handler) throws IOException {
		String getdate = usersManage.Vliao_yijianfankui(params);
		handler.sendMessage(handler.obtainMessage(200, (Object) getdate));
	}


	//黑名单
	public void heimingdan(String[] params, Handler handler) {//需要修改getset
		ArrayList<Vliao1_01168> getdate = usersManage.heimingdan(params);
		handler.sendMessage(handler.obtainMessage(205, (Object) getdate));
	}
	
	public void quxiao(String[] params, Handler handler) throws IOException {
		String getdate = usersManage.quxiao(params);
		handler.sendMessage(handler.obtainMessage(206, (Object) getdate));
	}

	
}
