package com.net.yuesejiaoyou.redirect.ResolverC.core;



import com.net.yuesejiaoyou.classroot.interface2.OkHttp;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.Bills_01165;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.Page;
import com.net.yuesejiaoyou.redirect.ResolverC.interface4.HelpManager_01165C;

import java.io.IOException;
import java.util.ArrayList;


public class UsersManage_01165C {
	HelpManager_01165C helpmanager = null;
	OkHttp okhttp = null;

	public UsersManage_01165C() {
		//httpclient = new HttpClientManage();
		okhttp = new OkHttp();
		helpmanager = new HelpManager_01165C();
         
	}

	public ArrayList<Bills_01165> my_v_search(String[] mode) {
		// TODO Auto-generated method stub
		String a = "memberC01178?mode=A-user-search&mode2=my_v_search";
		// LogDetect.send(LogDetect.DataType.specialType, "我的V币__a： ",a);

		String json = okhttp.requestPostBySyn(a, mode);
		// LogDetect.send(LogDetect.DataType.specialType, "我的V币__json： ",json);

		ArrayList<Bills_01165> mblist = helpmanager.my_v_search(json);
		// LogDetect.send(LogDetect.DataType.specialType, "我的V币__mblist： ",mblist);

		return mblist;
	}

	public ArrayList<Bills_01165> my_pingjia_search(String[] mode) {
		// TODO Auto-generated method stub
		String a = "memberC01178?mode=A-user-search&mode2=my_pingjia_search";
		// LogDetect.send(LogDetect.DataType.specialType, "我的评价__a： ",a);

		String json = okhttp.requestPostBySyn(a, mode);
		// LogDetect.send(LogDetect.DataType.specialType, "我的评价__json： ",json);

		ArrayList<Bills_01165> mblist = helpmanager.my_pingjia_search(json);
		// LogDetect.send(LogDetect.DataType.specialType, "我的评价__mblist： ",mblist);

		return mblist;
	}

	// 用户等级查询  level_search
	public Page level_search(String[] params) {
		String a = "memberC01178?mode=A-user-search&mode2=level_search";
		LogDetect.send(LogDetect.DataType.specialType, "用户等级查询__a： ", a);
		String json = okhttp.requestPostBySyn(a, params);
		LogDetect.send(LogDetect.DataType.specialType, "用户等级查询__json： ", json);
		Page mblist = helpmanager.level_search(json);
		LogDetect.send(LogDetect.DataType.specialType, "用户等级查询__mblist： ", mblist);
		return  mblist;
	}


	public ArrayList<Bills_01165> yuyue_search(String[] params) throws IOException {
		String a = "memberC01178?mode=A-user-search&mode2=yuyue_search";
		// LogDetect.send(LogDetect.DataType.specialType, "UsersManage_01165预约信息_a： ",a);

		String json = okhttp.requestPostBySyn(a, params);
		//  LogDetect.send(LogDetect.DataType.specialType, "UsersManage_01165预约信息_json： ",json);

		ArrayList<Bills_01165> mblist = helpmanager.yuyue_search(json);
		// LogDetect.send(LogDetect.DataType.specialType, "UsersManage_01165预约信息_mblist： ",mblist);

		return mblist;
	}
















//	public String delete_address(String[] params) {
//	String a = "rp?mode=A-user-delete&mode2=delete_address";
//	String json = okhttp.requestPostBySyn(a, params);
//	return json;
//}

	//打赏红包
	public String red_envelope(String[] params) {
		// TODO Auto-generated method stub
		String a = "member?mode=A-user-add&mode2=red_envelope";
		String json = okhttp.requestPostBySyn(a, params);
		//LogDetect.send(LogDetect.DataType.specialType, "打赏红包__json： ",json);



		return json;
	}

	//余额查询
	public String yue_search(String[] params) {
		// TODO Auto-generated method stub search
		String a = "member?mode=A-user-search&mode2=yue_search";
		String json = okhttp.requestPostBySyn(a, params);
		//LogDetect.send(LogDetect.DataType.specialType, "余额查询__json： ",json);
		return json;
	}



}