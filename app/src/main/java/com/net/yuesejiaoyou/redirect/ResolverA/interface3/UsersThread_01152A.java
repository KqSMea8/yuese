package com.net.yuesejiaoyou.redirect.ResolverA.interface3;

import java.io.IOException;

import com.net.yuesejiaoyou.redirect.ResolverA.interface1.UsersManageInOut_01152A;

import android.os.Handler;

public class UsersThread_01152A {
	private Handler handler;
	private UsersManageInOut_01152A usersManageInOut;
	private String state, id,name,pwd,yzm,tele,sort,userid;
	private int page;
	private String[] params;
		
	public UsersThread_01152A(String mode, String[] params, Handler handler) {
		this.usersManageInOut = new UsersManageInOut_01152A();
		this.state = mode;
		this.params = params;
		this.handler = handler;
	}	

	public Runnable runnable = new Runnable() {
		public void run() {
			try {
			switch (state) {
				case "gerenzhongxin":
					usersManageInOut.gerenzhongxin(params, handler);
				break;
				case "personal_information":
					usersManageInOut.personal_information(params, handler);
				break;
				case "save_personal_information":
					usersManageInOut.save_personal_information(params, handler);
				break;
				case "zhubozhongxin":
					usersManageInOut.zhubozhongxin(params, handler);
				break;
				case "getwudarao":
					usersManageInOut.getwudarao(params, handler);
				break;
				case "xiugai":
					usersManageInOut.xiugai(params, handler);
				break;
				
			/*	case "tuiguang":
					usersManageInOut.tuiguang(params, handler);
					
				break;
				case "xiaofei":
					usersManageInOut.xiaofei(params, handler);
					
				break;
				
				case "shenqing":
					usersManageInOut.shenqing(params, handler);
					
				break;
				
				case "tuiguangrenshu":
					usersManageInOut.tuiguangrenshu(params, handler);
					
				break;*/
				
				}
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
	};
}
