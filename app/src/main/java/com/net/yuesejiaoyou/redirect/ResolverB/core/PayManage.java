package com.net.yuesejiaoyou.redirect.ResolverB.core;



import com.net.yuesejiaoyou.classroot.interface2.OkHttp;

import java.io.IOException;


public class PayManage {


	//HelpManager helpmanager = null;
	OkHttp okhttp=null;
	public PayManage() {
		//httpclient = new HttpClientManage();
		okhttp=new OkHttp();
		//helpmanager = new HelpManager();
	}
	
	public String zfbpay(String[] params) throws IOException {
		String a = "pay?mode=A-user-add&mode2=zfbpay";
		String json = okhttp.requestPostBySyn(a, params);
		//ArrayList<PAY> mblist = helpmanager.pay(json);
		return json;
	}

}
