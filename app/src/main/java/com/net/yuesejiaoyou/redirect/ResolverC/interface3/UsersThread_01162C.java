package com.net.yuesejiaoyou.redirect.ResolverC.interface3;

import android.os.Handler;

import com.net.yuesejiaoyou.redirect.ResolverC.interface1.UsersManageInOut_01162C;

/*import com.example.vliao.interface1.UsersManageInOut_01162;*/

public class UsersThread_01162C {
	private Handler handler;
	private UsersManageInOut_01162C usersManageInOut;
	private String state, id,name,pwd,yzm,tele,sort,userid;
	private int page;
	private String[] params;

	public UsersThread_01162C(String mode, String[] params, Handler handler) {
		this.usersManageInOut = new UsersManageInOut_01162C();
		this.state = mode;
		this.params = params;
		this.handler = handler;
	}	

	public Runnable runnable = new Runnable() {
		public void run() {
             switch (state){
				 case "my_phone_record":
                      usersManageInOut.my_phone_record(params,handler);
				 	break;
				 case "my_impression":
					 usersManageInOut.my_impression(params,handler);
					 break;













				 case "evalue_search":
				 	usersManageInOut.evalue_search(params,handler);
				 	break;
				 case "evalue_search2":
					 usersManageInOut.evalue_search2(params,handler);
					 break;
				 case "save_evalue":
				 	usersManageInOut.save_evalue(params,handler);
				 	break;
				 case "wxlogin":
				 	usersManageInOut.wxlogin(params,handler);
				 	break;
				 case "wxlogin_1":
					 usersManageInOut.wxlogin_1(params,handler);
				 	break;
			 }
		}
	};
}
