package com.net.yuesejiaoyou.redirect.ResolverC.interface3;

import android.os.Handler;

/*import com.example.vliao.interface1.UsersManageInOut_01158;
import com.example.vliao.interface4.LogDetect;*/

import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.redirect.ResolverC.interface1.UsersManageInOut_01158;

import java.io.IOException;

public class UsersThread_01158 {
	private Handler handler;
	private UsersManageInOut_01158 usersManageInOut;
	private String state;
	private String[] params;
	
	//mode是登陆或者是注册
	//params前端获取的用户输入信息username、pwd
	//
	public UsersThread_01158(String mode, String[] params, Handler handler) {
		this.usersManageInOut = new UsersManageInOut_01158();
		this.state = mode;
		this.params = params;
		this.handler = handler;
	}	

	public Runnable runnable = new Runnable() {
		public void run() {
			try {
				switch (state) {
					case "guke_online":
						usersManageInOut.guke_online(params,handler);
						break;








					// 登录
					case "login":
						usersManageInOut.login(params, handler);
						break;
					case "activity_search":
						usersManageInOut.activity_search(params, handler);
						break;


					case "zhubo_online":
						usersManageInOut.zhubo_online(params,handler);
						break;
					case "mod_online":
						usersManageInOut.mod_online(params,handler);
						break;

					case "kou_frist":
						usersManageInOut.kou_frist(params,handler);
						LogDetect.send(LogDetect.DataType.specialType,"01160 发请求:", "");
						break;

					case "kou_zhubo":
						usersManageInOut.kou_zhubo(params,handler);
						break;

					case "xfliaotian":
						usersManageInOut.xfliaotian(params,handler);
						break;

					case "xiugai":
						usersManageInOut.xiugai(params,handler);
						break;

					//-------------------------------------------------
					case "setlike":
						usersManageInOut.setlike(params,handler);
						break;

					case "getfreevideo":
						usersManageInOut.getfreevideo(params,handler);
						break;

					case "getrewardvideo":
						usersManageInOut.getrewardvideo(params,handler);
						break;

					case "getmyvideo":
						usersManageInOut.getmyvideo(params,handler);
						break;
					case "mod_mang":
						usersManageInOut.mod_mang(params,handler);
						break;
					//-------------------------------------------------预约

					case "mod_return":
						usersManageInOut.mod_return(params,handler);
						break;
				}

		} catch (IOException e) {
				e.printStackTrace();
			}
		}
	};
}
