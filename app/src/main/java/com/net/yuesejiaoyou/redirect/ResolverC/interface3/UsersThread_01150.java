package com.net.yuesejiaoyou.redirect.ResolverC.interface3;

import android.os.Handler;


import com.net.yuesejiaoyou.redirect.ResolverC.interface1.UsersManageInOut_01150;

import java.io.IOException;

public class UsersThread_01150 {
	private Handler handler;
	private UsersManageInOut_01150 usersManageInOut;
	private String state, id,name,pwd,yzm,tele,sort,userid;
	private int page;
	private String[] params;
		
	public UsersThread_01150(String mode, String[] params, Handler handler) {
		this.usersManageInOut = new UsersManageInOut_01150();
		this.state = mode;
		this.params = params;
		this.handler = handler;
	}	

	public Runnable runnable = new Runnable() {
		public void run() {
			try {
			switch (state) {
				case "renzheng":
					usersManageInOut.renzheng(params, handler);
				break;

				case "check_pwd":
					usersManageInOut.check_pwd(params, handler);
					break;
				case "my_info":
					usersManageInOut.my_info(params, handler);
					break;
				case "mod_info":
					usersManageInOut.mod_info(params, handler);
					break;

				}
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
	};
}
