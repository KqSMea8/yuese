package com.net.yuesejiaoyou.redirect.ResolverC.interface1;


import android.os.Handler;


import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.redirect.ResolverC.core.UsersManage_vliao_01168C;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.vliaofans_01168;

import java.io.IOException;
import java.util.ArrayList;


public class UsersManageInOut_vliao_01168C {

	UsersManage_vliao_01168C usersManage;
	private LogDetect logDbg;
	
	public UsersManageInOut_vliao_01168C() {
		usersManage = new UsersManage_vliao_01168C();
		
	}
	
	public void wodefensi(String[] params, Handler handler)throws IOException {
		//LogDetect.send(LogDetect.DataType.specialType, "---UsersManageInOut_vliao_01168--", "01168");
		ArrayList<vliaofans_01168> list=usersManage.wodefensi(params);
		//LogDetect.send(LogDetect.DataType.specialType, "---getdate--01178--", list);
		handler.sendMessage(handler.obtainMessage(202,(Object)list));
	}

	public void woguanzhude(String[] params, Handler handler) {
		// TODO Auto-generated method stub
		
	}
	
	
}
