package com.net.yuesejiaoyou.redirect.ResolverC.interface1;

import android.os.Handler;



import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.redirect.ResolverC.core.UsersManage_01168C;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.BillBean;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.Page;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.Vliao1_01168;

import java.io.IOException;
import java.util.ArrayList;


public class UsersManageInOut_01168C {

	UsersManage_01168C usersManage = null;
	private LogDetect logDbg;
	
	public UsersManageInOut_01168C() {
		usersManage = new UsersManage_01168C();
		
	}

	public void Vliao_yijianfankui(String[] params, Handler handler) throws IOException {
		String getdate = usersManage.Vliao_yijianfankui(params);
		handler.sendMessage(handler.obtainMessage(200, (Object) getdate));
	}
	public void Vliao_agmentup(String[] params, Handler handler) throws IOException {
		String getdate = usersManage.Vliao_agmentup(params);
		handler.sendMessage(handler.obtainMessage(200, (Object) getdate));
	}


	
	//总金额
	public void vliao_zongjine(String[] params, Handler handler) {//需要修改getset
		ArrayList<Vliao1_01168> getdate = usersManage.vliao_zongjine(params);
		handler.sendMessage(handler.obtainMessage(201, (Object) getdate));
	}
	//收入明细
	public void shourumingxi(String[] params, Handler handler)throws IOException {
		ArrayList<BillBean> getdata=usersManage.shourumingxi(params);
		handler.sendMessage(handler.obtainMessage(202,(Object)getdata));
	}
	//支出明细
	public void zhichumingxi(String[] params, Handler handler)throws IOException {
		ArrayList<BillBean> getdata=usersManage.zhichumingxi(params);
		handler.sendMessage(handler.obtainMessage(203,(Object)getdata));
	}
	//提现明细
	public void tixianmingxi(String[] params, Handler handler)throws IOException {
		ArrayList<BillBean> getdata=usersManage.tixianmingxi(params);
		handler.sendMessage(handler.obtainMessage(204,(Object)getdata));
	}
	//黑名单
	public void heimingdan(String[] params, Handler handler) {//需要修改getset
		ArrayList<Vliao1_01168> getdate = usersManage.heimingdan(params);
		handler.sendMessage(handler.obtainMessage(205, (Object) getdate));
	}
	
	public void quxiao(String[] params, Handler handler) throws IOException {
		String getdate = usersManage.quxiao(params);
		handler.sendMessage(handler.obtainMessage(206, (Object) getdate));
	}
	//shouruzhubo
	public void shouruzhubo(String[] params, Handler handler) {
		Page getdate = usersManage.shouruzhubo(params);
		handler.sendMessage(handler.obtainMessage(207,(Object)getdate));
	}
	public void zhichuzhubo(String[] params, Handler handler) {
		Page getdate = usersManage.zhichuzhubo(params);
		handler.sendMessage(handler.obtainMessage(208,(Object)getdate));
	}
	//tixianzhubo
	public void tixianzhubo(String[] params, Handler handler) {
		Page getdate = usersManage.tixianzhubo(params);
		handler.sendMessage(handler.obtainMessage(209,(Object)getdate));
	}









	public void daili_mes(String[] params, Handler handler) throws IOException {
		Page getdate = usersManage.daili_mes(params);
		handler.sendMessage(handler.obtainMessage(210, (Object) getdate));
	}
	
	
	public void daili_num(String[] params, Handler handler)throws IOException {
		ArrayList<Vliao1_01168> getdata=usersManage.daili_num(params);
		handler.sendMessage(handler.obtainMessage(211,(Object)getdata));
	}
	public void tjrwithdraw(String[] params, Handler handler)throws IOException {
		ArrayList<Vliao1_01168> getdata=usersManage.tjrwithdraw(params);
		handler.sendMessage(handler.obtainMessage(212,(Object)getdata));
	}
//	/tjrwithdraw_up
	
	public void tjrwithdraw_up(String[] params, Handler handler)throws IOException {
        String getdate = usersManage.tjrwithdraw_up(params);
        handler.sendMessage(handler.obtainMessage(213,(Object)getdate));
    }
	//tixianzhubo_tjr
	public void tixianzhubo_tjr(String[] params, Handler handler) {
		Page getdate = usersManage.tixianzhubo_tjr(params);
		handler.sendMessage(handler.obtainMessage(214,(Object)getdate));
	}
	//shouruzhubo_tjr
	public void shouruzhubo_tjr(String[] params, Handler handler) {
		Page getdate = usersManage.shouruzhubo_tjr(params);
		handler.sendMessage(handler.obtainMessage(215,(Object)getdate));
	}
	
	//fenxiao1_request
	//分销明细-显示昵称、头像	主文件tuiguangrenshu_01152
	public void fenxiao1_request(String[] params, Handler handler) {
		Page getdate = usersManage.fenxiao1_request(params);
		handler.sendMessage(handler.obtainMessage(216,(Object)getdate));
	}
	//fenxiao2_request
	public void fenxiao2_request(String[] params, Handler handler) {
		Page getdate = usersManage.fenxiao2_request(params);
		handler.sendMessage(handler.obtainMessage(216,(Object)getdate));
	}
	
	//tuiguangrenshu
	public void tuiguangrenshu(String[] params, Handler handler) {
		Page getdate = usersManage.tuiguangrenshu(params);
		handler.sendMessage(handler.obtainMessage(216,(Object)getdate));
	}
	
	
	public void pepele_num(String[] params, Handler handler)throws IOException {
		ArrayList<Vliao1_01168> getdata=usersManage.pepele_num(params);
		handler.sendMessage(handler.obtainMessage(217,(Object)getdata));
	}
	
	
}
