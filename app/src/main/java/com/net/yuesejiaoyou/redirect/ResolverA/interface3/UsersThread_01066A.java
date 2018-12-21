package com.net.yuesejiaoyou.redirect.ResolverA.interface3;

import android.os.Handler;

import com.net.yuesejiaoyou.redirect.ResolverA.interface1.UsersManageInOut_01066A;


public class UsersThread_01066A {
	private Handler handler;
	private UsersManageInOut_01066A usersManageInOut;
	private String state;
	private String[] params;

	public UsersThread_01066A(String mode, String[] params, Handler handler) {
		this.usersManageInOut = new UsersManageInOut_01066A();
		this.state = mode;
		this.params = params;
		this.handler = handler;
	}	

	public Runnable runnable = new Runnable() {
		public void run() {

				switch (state) {
					case "xunyuan_01066":
						usersManageInOut.xunyuan_01066(params, handler);
						break;
					case "stare_01066":
						usersManageInOut.stare_01066(params, handler);
						break;

					case "activity_v":
						usersManageInOut.activity_v(params, handler);
						break;
					case "remenman":
						usersManageInOut.remenman(params, handler);
						break;



					case "attend_01066":
						usersManageInOut.attend_01066(params, handler);
						break;
					case "new_01066":
						usersManageInOut.new_01066(params, handler);
						break;

					case "guanzhu":
						usersManageInOut.guanzhu(params, handler);
						break;
					case "payvideo":
						usersManageInOut.payvideo(params, handler);
						break;
					case "userinfo":
						usersManageInOut.userinfo(params, handler);
						break;
					case "videolist":
						usersManageInOut.videolist(params, handler);
						break;

					case "hotvideolist":
						usersManageInOut.hotvideolist(params, handler);
						break;
					case "newvideolist":
						usersManageInOut.newvideolist(params, handler);
						break;
					case "evaluate_list":
						usersManageInOut.evaluate_list(params, handler);
						break;
					case "login":
						usersManageInOut.login(params, handler);//账号密码登录
						break;

					case "register":
						usersManageInOut.register(params, handler);//注册
						break;
					case "getcode":
						usersManageInOut.getcode(params, handler);//获取短信验证码
						break;


					case "resetpassword":
						usersManageInOut.resetpassword(params, handler);//重设密码
						break;

					case "video_info":
						usersManageInOut.video_info(params, handler);
						break;


				}
		}
	};
}
