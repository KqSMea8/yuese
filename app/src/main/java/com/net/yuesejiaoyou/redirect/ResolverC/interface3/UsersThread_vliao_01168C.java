package com.net.yuesejiaoyou.redirect.ResolverC.interface3;


import android.os.Handler;


import com.net.yuesejiaoyou.redirect.ResolverC.interface1.UsersManageInOut_vliao_01168C;

import java.io.IOException;

public class UsersThread_vliao_01168C {
	private Handler handler;
	private UsersManageInOut_vliao_01168C usersManageInOut;
	private String state;
	private String[] params;
		
	public UsersThread_vliao_01168C(String mode, String[] params, Handler handler) {
		this.usersManageInOut = new UsersManageInOut_vliao_01168C();
		this.state = mode;
		this.params = params;
		this.handler = handler;
	}	

	public Runnable runnable = new Runnable() {
		public void run() {
			switch (state) {
				
			case "wodefensi":
				try {
					usersManageInOut.wodefensi(params,handler);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
				
				}
		}
	};
}
