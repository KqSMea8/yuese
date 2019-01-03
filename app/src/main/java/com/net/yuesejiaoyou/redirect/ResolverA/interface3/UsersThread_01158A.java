package com.net.yuesejiaoyou.redirect.ResolverA.interface3;

import android.os.Handler;


import com.net.yuesejiaoyou.redirect.ResolverA.interface1.UsersManageInOut_01158A;

import java.io.IOException;

public class UsersThread_01158A {
	private Handler handler;
	private UsersManageInOut_01158A usersManageInOut;
	private String state;
	private String[] params;
	
	//mode是登陆或者是注册
	//params前端获取的用户输入信息username、pwd
	//
	public UsersThread_01158A(String mode, String[] params, Handler handler) {
		this.usersManageInOut = new UsersManageInOut_01158A();
		this.state = mode;
		this.params = params;
		this.handler = handler;
	}	

	public Runnable runnable = new Runnable() {
		public void run() {
			try {
				switch (state) {

					case "activity_search":
						usersManageInOut.activity_search(params, handler);
						break;


					case "xiugai":
						usersManageInOut.xiugai(params,handler);
						break;

					//-------------------------------------------------
					case "setlike":
						usersManageInOut.setlike(params,handler);
						break;


				}

		} catch (IOException e) {
				e.printStackTrace();
			}
		}
	};
}
