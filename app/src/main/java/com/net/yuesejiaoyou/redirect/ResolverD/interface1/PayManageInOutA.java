package com.net.yuesejiaoyou.redirect.ResolverD.interface1;

import android.os.Handler;


import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.redirect.ResolverD.core.PayManageA;

import java.io.IOException;


public class PayManageInOutA {

	PayManageA usersManage = null;
	private LogDetect logDbg;
	
	public PayManageInOutA() {
		usersManage = new PayManageA();
		
	}
	
	public void zfbpay(String[] params, Handler handler)throws IOException {
		String getdata=usersManage.zfbpay(params);
		handler.sendMessage(handler.obtainMessage(300,(Object)getdata));
	}


}
