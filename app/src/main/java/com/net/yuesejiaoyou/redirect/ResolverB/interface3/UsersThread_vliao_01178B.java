package com.net.yuesejiaoyou.redirect.ResolverB.interface3;


import com.net.yuesejiaoyou.redirect.ResolverB.interface1.UsersManageInOut_vliao_01178B;

import android.os.Handler;

public class UsersThread_vliao_01178B {
	private Handler handler;
	private UsersManageInOut_vliao_01178B usersManageInOut;
	private String state;
	private String[] params;
		
	public UsersThread_vliao_01178B(String mode, String[] params, Handler handler) {
		this.usersManageInOut = new UsersManageInOut_vliao_01178B();
		this.state = mode;
		this.params = params;
		this.handler = handler;
	}	

	public Runnable runnable = new Runnable() {
		public void run() {
			switch (state) {
				case "money100":
					usersManageInOut.pay(params, handler);
				break;
				case "money200":
					usersManageInOut.pay(params, handler);
				break;
				case "money500":
					usersManageInOut.pay(params, handler);
				break;
				case "money1000":
					usersManageInOut.pay(params, handler);
				break;
				case "money2000":
					usersManageInOut.pay(params, handler);
				break;
				case "money5000":
					usersManageInOut.pay(params, handler);
				break;
				case "wodeqimibang":
					usersManageInOut.wodeqimibang(params,handler);
				break;
				case "vcurrency":
					usersManageInOut.vcurrency(params,handler);
				break;
				case "vcurrencyconfirm":
					usersManageInOut.vcurrencyconfirm(params,handler);
				break;
				case "payprice":
					usersManageInOut.payprice(params,handler);
					break;
				}
		}
	};
}
