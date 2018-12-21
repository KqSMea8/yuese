package com.net.yuesejiaoyou.redirect.ResolverA.interface3;

import java.io.IOException;




import android.os.Handler;

import com.net.yuesejiaoyou.redirect.ResolverA.interface1.UsersManageInOut_01168A;

public class UsersThread_01168A {
	private Handler handler;
	private UsersManageInOut_01168A usersManageInOut;
	private String state, id,name,pwd,yzm,tele,sort,userid;
	private int page;
	private String[] params;
		
	public UsersThread_01168A(String mode, String[] params, Handler handler) {
		this.usersManageInOut = new UsersManageInOut_01168A();
		this.state = mode;
		this.params = params;
		this.handler = handler;
	}	

	public Runnable runnable = new Runnable() {
		public void run() {
			try {
			switch (state) {
			
				//意见反馈
				case "Vliao_yijianfankui":
					usersManageInOut.Vliao_yijianfankui(params,handler);
					break;
				//黑名单
				case "heimingdan":
					usersManageInOut.heimingdan(params,handler);
					break;
					//取消黑名单
				case "quxiao":
					usersManageInOut.quxiao(params,handler);
					break;

					

				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
			
			
			
			
			
			
			
			
		}
	};
}
