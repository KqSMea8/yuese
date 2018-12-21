package com.net.yuesejiaoyou.redirect.ResolverA.interface3;

import android.os.Handler;

import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.redirect.ResolverA.interface1.UsersManageInOut_01162A;


public class UsersThread_01162A {
	private Handler handler;
	private UsersManageInOut_01162A usersManageInOut;
	private String state, id,name,pwd,yzm,tele,sort,userid;
	private int page;
	private String[] params;

	public UsersThread_01162A(String mode, String[] params, Handler handler) {
		this.usersManageInOut = new UsersManageInOut_01162A();
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
		/*		 case "evalue_search":
				 	usersManageInOut.evalue_search(params,handler);
				 	break;
				 case "evalue_search2":
					 usersManageInOut.evalue_search2(params,handler);
					 break;*/
			/*	 case "save_evalue":
				 	usersManageInOut.save_evalue(params,handler);
				 	break;*/
				 case "wxlogin":
				 	usersManageInOut.wxlogin(params,handler);
					 LogDetect.send(LogDetect.DataType.basicType,"01162","发送请求");
				 	break;
				 case "wxlogin_1":
					 usersManageInOut.wxlogin_1(params,handler);
				 	break;
				 case "red_envelope":
					 usersManageInOut.red_envelope(params,handler);
					 break;
				 case "wxlogin_pd":
					 usersManageInOut.wxlogin_pd(params,handler);
				 	break;
				 case "wxlogin_tel":
					 usersManageInOut.wxlogin_tel(params,handler);
				 	break;
				 case "update_platform":
					 usersManageInOut.update_platform(params,handler);
					 break;

			 }
		}
	};
}
