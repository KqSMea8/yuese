package com.net.yuesejiaoyou.redirect.ResolverC.interface3;

import android.os.Handler;


import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.redirect.ResolverC.interface1.UsersManageInOut_01165C;

import java.io.IOException;

public class UsersThread_01165C {
	private Handler handler;
	private UsersManageInOut_01165C usersManageInOut;
	private String state, id,name,pwd,yzm,tele,sort,userid;
	private int page;
	private String[] params;

	public UsersThread_01165C(String mode, String[] params, Handler handler) {
		this.usersManageInOut = new UsersManageInOut_01165C();
		this.state = mode;
		this.params = params;
		this.handler = handler;
	}

	public Runnable runnable = new Runnable() {
		public void run() {
	        LogDetect.send(LogDetect.DataType.specialType, "UsersThread_01165_state： ",state);
			switch (state){
				case "yuyue_search":
					try {
						usersManageInOut.yuyue_search(params,handler);
					} catch (IOException e) {
						e.printStackTrace();
					}
					break;
				case "my_v_search":
				try {
					usersManageInOut.my_v_search(params,handler);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					break;
					//my_v_search
				case "my_pingjia_search":
					usersManageInOut.my_pingjia_search(params,handler);
					break;











				//打赏红包	
				case "red_envelope":
					usersManageInOut.red_envelope(params,handler);
					break;
				//V币余额查询
				case "yue_search":
					usersManageInOut.yue_search(params,handler);
					break;
					// 用户等级查询  level_search
				case "level_search":
					try {
						usersManageInOut.level_search(params,handler);
					} catch (IOException e) {
						e.printStackTrace();
					}
					break;
					
			}
		}
	};
}

