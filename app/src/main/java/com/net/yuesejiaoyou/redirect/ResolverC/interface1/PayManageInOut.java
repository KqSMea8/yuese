package com.net.yuesejiaoyou.redirect.ResolverC.interface1;

import android.os.Handler;

/*import com.example.vliao.core.PayManage;
import com.example.vliao.interface4.LogDetect;*/

import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.redirect.ResolverC.core.PayManage;

import java.io.IOException;


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
