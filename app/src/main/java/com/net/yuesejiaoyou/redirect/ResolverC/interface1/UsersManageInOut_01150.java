package com.net.yuesejiaoyou.redirect.ResolverC.interface1;

import android.os.Handler;



import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.redirect.ResolverC.core.UsersManage_01150;

import java.io.IOException;


public class UsersManageInOut_01150 {

	UsersManage_01150 usersManage = null;
	private LogDetect logDbg;
	
	public UsersManageInOut_01150() {
		usersManage = new UsersManage_01150();
		
	}
	public void renzheng(String[] params, Handler handler)throws IOException {
		String getdate = usersManage.renzheng(params);
		handler.sendMessage(handler.obtainMessage(202, (Object) getdate));
	}

	public void check_pwd(String[] params, Handler handler)throws IOException {
		String getdate = usersManage.check_pwd(params);
		handler.sendMessage(handler.obtainMessage(202, (Object) getdate));
	}
	public void my_info(String[] params, Handler handler)throws IOException {
		String getdate = usersManage.my_info(params);
		handler.sendMessage(handler.obtainMessage(203, (Object) getdate));
	}
	public void mod_info(String[] params, Handler handler)throws IOException {
		String getdate = usersManage.mod_info(params);
		handler.sendMessage(handler.obtainMessage(204, (Object) getdate));
	}
	
	
}
