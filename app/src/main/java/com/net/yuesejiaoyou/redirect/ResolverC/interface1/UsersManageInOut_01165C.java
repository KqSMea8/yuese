package com.net.yuesejiaoyou.redirect.ResolverC.interface1;

import android.os.Handler;


import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.redirect.ResolverC.core.UsersManage_01165C;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.Bills_01165;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.Page;

import net.sf.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;


public class UsersManageInOut_01165C {

	UsersManage_01165C usersManage = null;
	private LogDetect logDbg;

	public UsersManageInOut_01165C() {
		usersManage = new UsersManage_01165C();

	}

	//我的V币查询
	public void my_v_search(String[] mode, Handler handler) throws IOException {
		// TODO Auto-generated method stub
		ArrayList<Bills_01165> getdate = usersManage.my_v_search(mode);
		// LogDetect.send(LogDetect.DataType.specialType, "我的V币_getdate： ",getdate.toString());
		handler.sendMessage(handler.obtainMessage(200,(Object)getdate));
	}
	public void my_pingjia_search(String[] mode, Handler handler) {
		// TODO Auto-generated method stub
		ArrayList<Bills_01165> getdate = usersManage.my_pingjia_search(mode);
		// LogDetect.send(LogDetect.DataType.specialType, "我的评价_getdate： ",getdate.toString());
		handler.sendMessage(handler.obtainMessage(200,(Object)getdate));
	}

	//预约信息查询
	public void yuyue_search(String[] mode, Handler handler) throws IOException {
		ArrayList<Bills_01165> getdate = usersManage.yuyue_search(mode);
		// LogDetect.send(LogDetect.DataType.specialType, "所有的预约信息_getdate： ",getdate.toString());
		handler.sendMessage(handler.obtainMessage(200,(Object)getdate));

	}
















	//打赏红包
	public void red_envelope(String[] params, Handler handler) {
		// TODO Auto-generated method stub
		String getdate = usersManage.red_envelope(params);
	//	LogDetect.send(LogDetect.DataType.specialType, "打赏红包_getdate： ",getdate.toString());

		JSONObject jsonObj = JSONObject.fromObject(getdate);
		jsonObj.put("value",params[2]);

		handler.sendMessage(handler.obtainMessage(101,(Object)jsonObj.toString()));
		
	}
	//余额查询
	public void yue_search(String[] params, Handler handler) {
		// TODO Auto-generated method stub
		String getdate = usersManage.yue_search(params);
		//LogDetect.send(LogDetect.DataType.specialType, "余额查询_getdate： ",getdate.toString());
		handler.sendMessage(handler.obtainMessage(100,(Object)getdate));
		
	}
	
	// 用户等级查询  level_search
    public void level_search(String[] params, Handler handler) throws IOException {
		Page getdate = usersManage.level_search(params);
		 LogDetect.send(LogDetect.DataType.specialType, "用户等级_getdate： ",getdate.toString());
		handler.sendMessage(handler.obtainMessage(200,(Object)getdate));
    }
	


}


