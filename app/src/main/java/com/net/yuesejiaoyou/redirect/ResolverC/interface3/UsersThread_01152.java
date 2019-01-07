package com.net.yuesejiaoyou.redirect.ResolverC.interface3;

import android.os.Handler;

//import com.example.vliao.interface1.UsersManageInOut_01152;

import com.net.yuesejiaoyou.redirect.ResolverC.interface1.UsersManageInOut_01152;

import java.io.IOException;

public class UsersThread_01152 {
	private Handler handler;
	private UsersManageInOut_01152 usersManageInOut;
	private String state, id,name,pwd,yzm,tele,sort,userid;
	private int page;
	private String[] params;
		
	public UsersThread_01152(String mode, String[] params, Handler handler) {
		this.usersManageInOut = new UsersManageInOut_01152();
		this.state = mode;
		this.params = params;
		this.handler = handler;
	}	

	public Runnable runnable = new Runnable() {
		public void run() {
			try {
			switch (state) {

				case "personal_information":
					usersManageInOut.personal_information(params, handler);
					break;
				case "save_personal_information":
					usersManageInOut.save_personal_information(params, handler);
					break;

				case "gerenzhongxin":
					usersManageInOut.gerenzhongxin(params, handler);
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
				
				case "tuiguang":
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
					
				break;
				
				}
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
	};
}
