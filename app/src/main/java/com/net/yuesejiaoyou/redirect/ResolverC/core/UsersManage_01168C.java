package com.net.yuesejiaoyou.redirect.ResolverC.core;


import com.net.yuesejiaoyou.classroot.interface2.OkHttp;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.Page;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.Vliao1_01168;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.Vliao2_01168;
import com.net.yuesejiaoyou.redirect.ResolverC.interface4.HelpManager_01168C;

import java.io.IOException;
import java.util.ArrayList;


public class UsersManage_01168C {
	HelpManager_01168C helpmanager = null;
	OkHttp okhttp=null;
	public UsersManage_01168C() {
		//httpclient = new HttpClientManage();
		okhttp=new OkHttp();
		helpmanager = new HelpManager_01168C();

	}

	//收入明细
	public ArrayList<Vliao2_01168> shourumingxi(String[] params) throws IOException {
		String a = "memberC01178?mode=A-user-search&mode2=shourumingxi";
		String json = okhttp.requestPostBySyn(a, params);
		ArrayList<Vliao2_01168> mblist = helpmanager.shourumingxi(json);
		return mblist;
	}
	//支出明细
	public ArrayList<Vliao2_01168> zhichumingxi(String[] params) throws IOException {
		String a = "memberC01178?mode=A-user-search&mode2=zhichumingxi";
		String json = okhttp.requestPostBySyn(a, params);
		ArrayList<Vliao2_01168> mblist = helpmanager.zhichumingxi(json);
		return mblist;
	}
	//提现明细
	public ArrayList<Vliao2_01168> tixianmingxi(String[] params) throws IOException {
		String a = "memberC01178?mode=A-user-search&mode2=tixianmingxi";
		String json = okhttp.requestPostBySyn(a, params);
		ArrayList<Vliao2_01168> mblist = helpmanager.tixianmingxi(json);
		return mblist;
	}


	public Page shouruzhubo(String[] params) {
		String a = "memberC01178?mode=A-user-search&mode2=shouruzhubo";
		String json = okhttp.requestPostBySyn(a,params);
		Page mblist = helpmanager.shouruzhubo(json);
		return mblist;
	}
	public Page zhichuzhubo(String[] params) {
		String a = "memberC01178?mode=A-user-search&mode2=zhichuzhubo";
		String json = okhttp.requestPostBySyn(a,params);
		Page mblist = helpmanager.zhichuzhubo(json);
		return mblist;
	}
	//tixianzhubo
	public Page tixianzhubo(String[] params) {
		String a = "memberC01178?mode=A-user-search&mode2=tixianzhubo";
		String json = okhttp.requestPostBySyn(a,params);
		Page mblist = helpmanager.tixianzhubo(json);
		return mblist;
	}
	public ArrayList<Vliao1_01168> vliao_zongjine(String[] params) {//需要修改getset、HelpManager
		String a = "memberC01178?mode=A-user-search&mode2=vliao_zongjine";
		String json = okhttp.requestPostBySyn(a, params);
		ArrayList<Vliao1_01168> mblist=helpmanager.vliao_zongjine(json);
		return mblist;
	}

	//黑名单
	public ArrayList<Vliao1_01168> heimingdan(String[] params) {//需要修改getset、HelpManager
		String a = "memberC01178?mode=A-user-search&mode2=heimingdan";
		String json = okhttp.requestPostBySyn(a, params);
		ArrayList<Vliao1_01168> mblist=helpmanager.heimingdan(json);
		return mblist;
	}


	public String quxiao(String[] params) throws IOException {
		String a = "memberC01178?mode=A-user-search&mode2=quxiao";
		String json = okhttp.requestPostBySyn(a, params);
		return json;
	}












	//V聊意见反馈
	public String Vliao_yijianfankui(String[] params) throws IOException {
		String a = "ar?mode=A-user-search&mode2=Vliao_yijianfankui";
		String json = okhttp.requestPostBySyn(a, params);
		return json;
	}
	//V聊意见反馈
	public String Vliao_agmentup(String[] params) throws IOException {
		String a = "memberC01178?mode=A-user-search&mode2=Vliao_agmentup";
		String json = okhttp.requestPostBySyn(a, params);
		return json;
	}
	//查找总资产
	/*public String vliao_zongjine(String[] params) throws IOException {
		String a = "ar?mode=A-user-search&mode2=vliao_zongjine";
		String json = okhttp.requestPostBySyn(a, params);
		return json;
	}*/
	



	
	
	
	
	public String lat_lonmod(String[] params) throws IOException {
		String a = "member?mode=A-user-mod&mode2=lat_lonmod";
		String json = okhttp.requestPostBySyn(a, params);
		return json;
	}

	//代理下级成员信息
	public Page daili_mes(String[] params)throws IOException {
		String a = "ar?mode=A-user-search&mode2=daili_mes";
		String json = okhttp.requestPostBySyn(a, params);
		Page list = helpmanager.daili_mes(json);
		return list;
	}
	//代理主页信息
	public ArrayList<Vliao1_01168> daili_num(String[] params) {//需要修改getset、HelpManager
		String a = "ar?mode=A-user-search&mode2=daili_num";
		String json = okhttp.requestPostBySyn(a, params);
		ArrayList<Vliao1_01168> mblist=helpmanager.daili_num(json);
		return mblist;
	}
	
	public ArrayList<Vliao1_01168> tjrwithdraw(String[] params) {//需要修改getset、HelpManager
		String a = "memberC01178?mode=A-user-search&mode2=tjrwithdraw";
		String json = okhttp.requestPostBySyn(a, params);
		ArrayList<Vliao1_01168> mblist=helpmanager.tjrwithdraw(json);
		return mblist;
	}
//	/tjrwithdraw_up	提现页面的内容
	public String tjrwithdraw_up(String[] params) {
        String a = "memberC01178?mode=A-user-search&mode1=tjrwithdraw_up";
        String json = okhttp.requestPostBySyn(a, params);
        return json;
    }
	public Page tixianzhubo_tjr(String[] params) {
		String a = "memberC01178?mode=A-user-search&mode2=tixianzhubo_tjr";
		String json = okhttp.requestPostBySyn(a,params);
		Page mblist = helpmanager.tixianzhubo(json);
		return mblist;
	}
	public Page shouruzhubo_tjr(String[] params) {
		String a = "memberC01178?mode=A-user-search&mode2=shouruzhubo_tjr";
		String json = okhttp.requestPostBySyn(a,params);
		Page mblist = helpmanager.shouruzhubo_tjr(json);
		return mblist;
	}
	//fenxiao1_request
	public Page fenxiao1_request(String[] params){
		String a = "memberC01178?mode=A-user-search&mode2=fenxiao1_request";
		String json = okhttp.requestPostBySyn(a, params);
		Page list = helpmanager.fenxiao1_request(json);
		return list;
	}
	
	public Page fenxiao2_request(String[] params) {
		String a = "memberC01178?mode=A-user-search&mode2=fenxiao2_request";
		String json = okhttp.requestPostBySyn(a, params);
		Page list = helpmanager.fenxiao1_request(json);
		return list;
	}
	//tuiguangrenshu
	public Page tuiguangrenshu(String[] params) {
		String a = "ar?mode=A-user-search&mode2=tuiguangrenshu";
		String json = okhttp.requestPostBySyn(a, params);
		Page list = helpmanager.fenxiao1_request(json);
		return list;
	}
	public ArrayList<Vliao1_01168> pepele_num(String[] params) {//需要修改getset、HelpManager
		String a = "memberC01178?mode=A-user-search&mode2=pepele_num";
		String json = okhttp.requestPostBySyn(a, params);
		ArrayList<Vliao1_01168> mblist=helpmanager.pepele_num(json);
		return mblist;
	}
	
	
}
