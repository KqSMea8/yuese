package com.net.yuesejiaoyou.redirect.ResolverA.interface3;



import android.os.Handler;

import com.net.yuesejiaoyou.redirect.ResolverA.interface1.UsersManageInOut_01160A;

public class UsersThread_01160A {
	private Handler handler;
	private UsersManageInOut_01160A usersManageInOut;
	private String state, id,name,pwd,yzm,tele,sort,userid;
	private int page;
	private String[] params;
		
	public UsersThread_01160A(String mode, String[] params, Handler handler) {
		this.usersManageInOut = new UsersManageInOut_01160A();
		this.state = mode;
		this.params = params;
		this.handler = handler;
	}	

	public Runnable runnable = new Runnable() {
		public void run() {
			switch (state) {
			/*case "see_five_money":
				usersManageInOut.see_five_money(params,handler);
				break;
			case "see_money":
				usersManageInOut.see_money(params,handler);
				break;
			case "report":
				usersManageInOut.report(params,handler);
				break;*/
			case "black":
				usersManageInOut.black(params,handler);
				break;
			/*case "masschat":
				usersManageInOut.masschat(params,handler);
				break;*/
			case "xiaoxi":
				usersManageInOut.xiaoxi(params,handler);
				break;
			/*case "zhubo_yuyue":
				usersManageInOut.zhubo_yuyue(params,handler);
				break;*/
			case "search_dv":
				usersManageInOut.search_dv(params,handler);
				break;
			case "attention_videolist":
				usersManageInOut.attention_videolist(params,handler);
				break;
			/*case "i_like":
				usersManageInOut.i_like(params,handler);
				break;
			case "i_buy":
				usersManageInOut.i_buy(params,handler);
				break;*/
			case "insert_reservation":
				usersManageInOut.insert_reservation(params,handler);
				break;
			/*
			case "del_video":
				usersManageInOut.del_video(params,handler);
				break;*/
			}
		}
	};
}
