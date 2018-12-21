package com.net.yuesejiaoyou.redirect.ResolverB.interface3;

import android.os.Handler;
import com.net.yuesejiaoyou.redirect.ResolverB.interface1.UsersManageInOut_01066B;

public class UsersThread_01066B {
	private Handler handler;
	private UsersManageInOut_01066B usersManageInOut;
	private String state;
	private String[] params;

	public UsersThread_01066B(String mode, String[] params, Handler handler) {
		this.usersManageInOut = new UsersManageInOut_01066B();
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
						usersManageInOut.login(params, handler);
						break;
					case "fblogin":
						usersManageInOut.fblogin(params, handler);
						break;
					case "register":
						usersManageInOut.register(params, handler);
						break;
					case "getcode":
						usersManageInOut.getcode(params, handler);
						break;

					case "fgetcode":
						usersManageInOut.fgetcode(params, handler);
						break;
					case "resetpassword":
						usersManageInOut.resetpassword(params, handler);
						break;

					case "video_info":
						usersManageInOut.video_info(params, handler);
					//	LogDetect.send(LogDetect.DataType.nonbasicType, "查询视频信息", "执行------"+params);
						break;

					case "sharevideo":
						usersManageInOut.sharevideo(params, handler);
						//	LogDetect.send(LogDetect.DataType.nonbasicType, "查询视频信息", "执行------"+params);
						break;


					case "fblogin_step2":
						usersManageInOut.fblogin_step2(params, handler);
						break;

				}
		}
	};
}
