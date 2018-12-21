package com.net.yuesejiaoyou.redirect.ResolverC.core;




import com.net.yuesejiaoyou.classroot.interface2.OkHttp;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.vliaofans_01168;
import com.net.yuesejiaoyou.redirect.ResolverC.interface4.HelpManager_vliao_01168C;

import java.io.IOException;
import java.util.ArrayList;


public class UsersManage_vliao_01168C {
	HelpManager_vliao_01168C helpmanager = null;
	OkHttp okhttp=null;
	public UsersManage_vliao_01168C() {
		//httpclient = new HttpClientManage();
		okhttp=new OkHttp();
		helpmanager = new HelpManager_vliao_01168C();

	}


	public String pay(String[] params) {
		//LogDetect.send(LogDetect.DataType.specialType, "--UsersManage_vliao_01178--:", "01178");
		String a = "vliaopay?mode=A-user-add&mode2=pay";
		//LogDetect.send(LogDetect.DataType.specialType, "--UsersManage_vliao_01178--a:--", a);
		String json = okhttp.requestPostBySyn(a, params);
		return json;
	}
	
	public ArrayList<vliaofans_01168> wodefensi(String[] params) throws IOException {
		String a = "memberC01178?mode=A-user-search&mode2=wodefensi";
		String json = okhttp.requestPostBySyn(a, params);
		//LogDetect.send(LogDetect.DataType.specialType, "Meijian_HelpManager_01168",json);
		ArrayList<vliaofans_01168> mblist = helpmanager.wodefensi(json);
	//	LogDetect.send(LogDetect.DataType.specialType, "Meijian_HelpManager_01168",mblist);
		return mblist;
	}
	
}
