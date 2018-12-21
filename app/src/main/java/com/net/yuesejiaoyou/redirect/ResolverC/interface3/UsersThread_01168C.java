package com.net.yuesejiaoyou.redirect.ResolverC.interface3;

import android.os.Handler;


import com.net.yuesejiaoyou.redirect.ResolverC.interface1.UsersManageInOut_01168C;

import java.io.IOException;

public class UsersThread_01168C {
	private Handler handler;
	private UsersManageInOut_01168C usersManageInOut;
	private String state, id,name,pwd,yzm,tele,sort,userid;
	private int page;
	private String[] params;
		
	public UsersThread_01168C(String mode, String[] params, Handler handler) {
		this.usersManageInOut = new UsersManageInOut_01168C();
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
				case "Vliao_agmentup":
					usersManageInOut.Vliao_agmentup(params,handler);
					break;


				//总金额
				case "vliao_zongjine":
					usersManageInOut.vliao_zongjine(params,handler);
					break;
				//收入明细
				case "shourumingxi":
					usersManageInOut.shourumingxi(params, handler);
				    break;
				//支出明细
				case "zhichumingxi":
					usersManageInOut.zhichumingxi(params, handler);
				    break;
				//提现明细
				case "tixianmingxi":
					usersManageInOut.tixianmingxi(params, handler);
				    break;
				//黑名单
				case "heimingdan":
					usersManageInOut.heimingdan(params,handler);
					break;
					//取消黑名单
				case "quxiao":
					usersManageInOut.quxiao(params,handler);
					break;
				case "shouruzhubo":
					usersManageInOut.shouruzhubo(params,handler);
					break;
				//zhichuzhubo
				case "zhichuzhubo":
					usersManageInOut.zhichuzhubo(params,handler);
					break;
				//tixianzhubo
				case "tixianzhubo":
					usersManageInOut.tixianzhubo(params,handler);
					break;
					
					case "daili_mes":
					usersManageInOut.daili_mes(params,handler);
					break;
				case "daili_num":
					usersManageInOut.daili_num(params,handler);
					break;	
					//推荐人获取信息
				case "tjrwithdraw":
					usersManageInOut.tjrwithdraw(params,handler);
					break;	
					//tjrwithdraw_up
				case "tjrwithdraw_up":
					usersManageInOut.tjrwithdraw_up(params,handler);
					break;
				//tixianzhubo_tjr	提现明细-推荐人
				case "tixianzhubo_tjr":
					usersManageInOut.tixianzhubo_tjr(params,handler);
					break;	
				//收益主播-推荐人里面的明细
				case "shouruzhubo_tjr":
					usersManageInOut.shouruzhubo_tjr(params,handler);
					break;
					
				case "fenxiao1_request":
					usersManageInOut.fenxiao1_request(params,handler);
					break;
					
				case "fenxiao2_request":
					usersManageInOut.fenxiao2_request(params,handler);
					break;
				//tuiguangrenshu
				case "tuiguangrenshu":
					usersManageInOut.tuiguangrenshu(params,handler);
					break;
				//pepele_num
				case "pepele_num":
					usersManageInOut.pepele_num(params,handler);
					break;
					
				/*	//黑名单
				case "heimingdan":
					usersManageInOut.heimingdan(params,handler);
					break;*/
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
			
			
			
			
			
			
			
			
		}
	};
}
