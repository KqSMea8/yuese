package com.net.yuesejiaoyou.redirect.ResolverC.interface3;

import android.os.Handler;

//import com.example.vliao.interface1.PayManageInOut;

import com.net.yuesejiaoyou.redirect.ResolverC.interface1.PayManageInOut;

import java.io.IOException;

public class PayThread {
	private Handler handler;
	private PayManageInOut usersManageInOut;
	private String state;
	private String[] params;
	
	//mode是登陆或者是注册
	//params前端获取的用户输入信息username、pwd
	//
	public PayThread(String mode, String[] params, Handler handler) {
		this.usersManageInOut = new PayManageInOut();
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
