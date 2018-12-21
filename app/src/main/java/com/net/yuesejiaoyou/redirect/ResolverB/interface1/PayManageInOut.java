package com.net.yuesejiaoyou.redirect.ResolverB.interface1;

import java.io.IOException;

import android.os.Handler;

import com.net.yuesejiaoyou.classroot.interface4.LogDetect;

import com.net.yuesejiaoyou.redirect.ResolverB.core.PayManage;





public class PayManageInOut {

	PayManage usersManage = null;
	private LogDetect logDbg;
	
	public PayManageInOut() {
		usersManage = new PayManage();
		
	}
	
	public void zfbpay(String[] params, Handler handler)throws IOException {
		String getdata=usersManage.zfbpay(params);
		handler.sendMessage(handler.obtainMessage(300,(Object)getdata));
	}


}
