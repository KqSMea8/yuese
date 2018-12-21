package com.net.yuesejiaoyou.redirect.ResolverD.core;





import com.net.yuesejiaoyou.classroot.interface2.OkHttp;

import java.io.IOException;


public class PayManageA {


	//HelpManager helpmanager = null;
	OkHttp okhttp=null;
	public PayManageA() {
		//httpclient = new HttpClientManage();
		okhttp=new OkHttp();
		//helpmanager = new HelpManager();
	}
	
	public String zfbpay(String[] params) throws IOException {
		String a = "rp?mode=A-user-add&mode2=zfbpay";
		String json = okhttp.requestPostBySyn(a, params);
		//ArrayList<PAY> mblist = helpmanager.pay(json);
		return json;
	}

}
