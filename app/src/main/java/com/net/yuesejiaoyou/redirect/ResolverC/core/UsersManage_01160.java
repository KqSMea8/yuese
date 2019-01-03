package com.net.yuesejiaoyou.redirect.ResolverC.core;



import com.net.yuesejiaoyou.classroot.interface2.OkHttp;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.Page;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.ZBYuyueJB_01160;
import com.net.yuesejiaoyou.redirect.ResolverC.interface4.HelpManager_01160;

import java.util.ArrayList;


public class UsersManage_01160 {
	HelpManager_01160 helpmanager = null;
	OkHttp okhttp=null;
	public UsersManage_01160() {
		//httpclient = new HttpClientManage();
		okhttp=new OkHttp();
		helpmanager = new HelpManager_01160();

	}

	public Page i_like(String[] params) {
		String a="memberC01178?mode=A-user-mod&mode2=i_like";
		String json=okhttp.requestPostBySyn(a,params);
		Page mblist = helpmanager.attention_videolist(json);
		return  mblist;
	}

	public Page i_buy(String[] params) {
		String a="memberC01178?mode=A-user-mod&mode2=i_buy";
		String json=okhttp.requestPostBySyn(a,params);
		Page mblist = helpmanager.attention_videolist(json);
		return  mblist;
	}
	public ArrayList<ZBYuyueJB_01160> zhubo_yuyue(String[] params) {
		String a = "memberC01178?mode=A-user-search&mode2=zhubo_yuyue";
		String json = okhttp.requestPostBySyn(a, params);
		LogDetect.send(LogDetect.DataType.specialType,"01160 json:",json);
		ArrayList<ZBYuyueJB_01160> list = helpmanager.zhubo_yuyue(json);
		return list;
	}
	/*public ArrayList<UserBean> zhubo_yuyue(String[] params) {
		String a = "memberC01178?mode=A-user-mod&mode2=zhubo_yuyue";
		String json = okhttp.requestPostBySyn(a, params);
		LogDetect.send(LogDetect.DataType.specialType,"01160 json:",json);
		ArrayList<UserBean> list = helpmanager.zhubo_yuyue(json);
		return list;
	}*/









	/*public String lat_lonmod(String[] params) throws IOException {
		String a = "member?mode=A-user-mod&mode2=lat_lonmod";
		String json = okhttp.requestPostBySyn(a, params);
		return json;
	}*/

	public String see_five_money(String[] params) {
		String a = "xiaoxi?mode=A-user-add&mode2=see_five_money";
		String json = okhttp.requestPostBySyn(a, params);
		LogDetect.send(LogDetect.DataType.specialType,"01160 json:",json);
		return json;
	}

	public String see_money(String[] params) {
		String a = "xiaoxi?mode=A-user-add&mode2=see_money";
		String json = okhttp.requestPostBySyn(a, params);
		LogDetect.send(LogDetect.DataType.specialType,"01160 json:",json);
		return json;
	}

	public String report(String[] params) {
		String a = "xiaoxi?mode=A-user-mod&mode2=report";
		String json = okhttp.requestPostBySyn(a, params);
		LogDetect.send(LogDetect.DataType.specialType,"01160 json:",json);
		return json;
	}

	public String black(String[] params) {
		String a = "xiaoxi?mode=A-user-mod&mode2=black";
		String json = okhttp.requestPostBySyn(a, params);
		LogDetect.send(LogDetect.DataType.specialType,"01160 json:",json);
		return json;
	}

	public String masschat(String[] params) {
		String a = "xiaoxi?mode=A-user-mod&mode2=masschat";
		String json = okhttp.requestPostBySyn(a, params);
		LogDetect.send(LogDetect.DataType.specialType,"01160 json:",json);
		return json;
	}

	public String xiaoxi(String[] params) {
		String a = "xiaoxi?mode=A-user-mod&mode2=xiaoxi";
		String json = okhttp.requestPostBySyn(a, params);
		LogDetect.send(LogDetect.DataType.specialType,"01160 json:",json);
		return json;
	}




	public ArrayList<ZBYuyueJB_01160> search_dv(String[] params) {
		String a = "xiaoxi?mode=A-user-mod&mode2=search_dv";
		String json = okhttp.requestPostBySyn(a, params);
		LogDetect.send(LogDetect.DataType.specialType,"01160 json:",json);
		ArrayList<ZBYuyueJB_01160> list = helpmanager.search_dv(json);
		return list;
	}

    public Page attention_videolist(String[] params) {
		String a="xiaoxi?mode=A-user-mod&mode2=attention_videolist";
		String json=okhttp.requestPostBySyn(a,params);
		Page mblist = helpmanager.attention_videolist(json);
		return  mblist;
    }



    public String insert_reservation(String[] params) {
		String a = "xiaoxi?mode=A-user-add&mode2=insert_reservation";
		String json = okhttp.requestPostBySyn(a, params);
		LogDetect.send(LogDetect.DataType.specialType,"01160 json:",json);
		return json;
    }

    public String del_video(String[] params) {
		String a = "xiaoxi?mode=A-user-del&mode2=del_video";
		String json = okhttp.requestPostBySyn(a, params);
		LogDetect.send(LogDetect.DataType.specialType,"01160 json:",json);
		return json;
    }
}
