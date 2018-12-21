package com.net.yuesejiaoyou.redirect.ResolverD.interface3;

import android.os.Handler;


import com.net.yuesejiaoyou.redirect.ResolverD.interface1.PayManageInOutA;

import java.io.IOException;

public class PayThreadA {
	private Handler handler;
	private PayManageInOutA usersManageInOut;
	private String state;
	private String[] params;
	
	//mode是登陆或者是注册
	//params前端获取的用户输入信息username、pwd
	//
	public PayThreadA(String mode, String[] params, Handler handler) {
		this.usersManageInOut = new PayManageInOutA();
		this.state = mode;
		this.params = params;
		this.handler = handler;
	}



	public Runnable runnable = new Runnable() {
		public void run() {
			try {
				switch (state) {//state
			  
					case "zfbpay":
						usersManageInOut.zfbpay(params, handler);
						break;

				}
					
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	};
}
