package com.net.yuesejiaoyou.redirect.ResolverC.interface1;

import android.os.Handler;

/*import com.example.vliao.core.UsersManage_01152;
import com.example.vliao.getset.Member_01152;
import com.example.vliao.getset.Page;
import com.example.vliao.interface4.HelpManager_01152;
import com.example.vliao.interface4.LogDetect;*/

import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.redirect.ResolverC.core.UsersManage_01152;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.Member_01152;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.Page;
import com.net.yuesejiaoyou.redirect.ResolverC.interface4.HelpManager_01152;

import java.io.IOException;
import java.util.ArrayList;


public class UsersManageInOut_01152 {

	UsersManage_01152 usersManage = null;
	private LogDetect logDbg;
	
	public UsersManageInOut_01152() {
		usersManage = new UsersManage_01152();
		
	}

	public void personal_information(String[] params, Handler handler) {
		ArrayList<Member_01152> getdate = usersManage.personal_information(params);
		LogDetect.send(LogDetect.DataType.specialType, "UsersManageInOut_01152个人中M:", getdate);
		handler.sendMessage(handler.obtainMessage(210, (Object) getdate));

	}
	public void save_personal_information(String[] params, Handler handler) {
		String getdate = usersManage.save_personal_information(params);
		LogDetect.send(LogDetect.DataType.specialType, "UsersManageInOut_01152修改资料====:", getdate);
		handler.sendMessage(handler.obtainMessage(202, (Object) getdate));

	}








	public void getip(String mode, Handler handler) throws IOException {

		String getdate = HelpManager_01152.getNetIp();
		handler.sendMessage(handler.obtainMessage(200, (Object) getdate));
	}
	public void gerenzhongxin(String[] params, Handler handler) throws IOException {
		ArrayList<Member_01152> getdate = usersManage.gerenzhongxin(params);
		LogDetect.send(LogDetect.DataType.specialType, "UsersManageInOut_01152个人中M:", getdate);
		handler.sendMessage(handler.obtainMessage(200, (Object) getdate));
		
	}

	public void zhubozhongxin(String[] params, Handler handler) {
		LogDetect.send(LogDetect.DataType.specialType, "UsersManageInOut_01152个人中M:", "==========");
		ArrayList<Member_01152> getdate = usersManage.zhubozhongxin(params);
		LogDetect.send(LogDetect.DataType.specialType, "UsersManageInOut_01152个人中M:", getdate);
		handler.sendMessage(handler.obtainMessage(100, (Object) getdate));
		
	}
	public void getwudarao(String[] params, Handler handler) {
		String getdate = usersManage.getwudarao(params);
		LogDetect.send(LogDetect.DataType.specialType, "UsersManageInOut_01152修改视频状态====:", getdate);
		handler.sendMessage(handler.obtainMessage(112, (Object) getdate));
		
	}
	public void xiugai(String[] params, Handler handler) {
		String getdate = usersManage.xiugai(params);
		LogDetect.send(LogDetect.DataType.specialType, "UsersManageInOut_01152修改视频状态====:", getdate);
		handler.sendMessage(handler.obtainMessage(113, (Object) getdate));
		
	}
	
	public void tuiguang(String[] params, Handler handler) {
		ArrayList<Member_01152> getdate = usersManage.tuiguang(params);
		LogDetect.send(LogDetect.DataType.specialType, "UsersManageInOut_01152推广:", getdate);
		handler.sendMessage(handler.obtainMessage(200, (Object) getdate));
	}
	public void xiaofei(String[] params, Handler handler) {
		Page getdate = usersManage.xiaofei(params);
		LogDetect.send(LogDetect.DataType.specialType, "UsersManageInOut_01152消费:", getdate);
		handler.sendMessage(handler.obtainMessage(210, (Object) getdate));
		
	}
	public void shenqing(String[] params, Handler handler) {
		String getdate = usersManage.shenqing(params);
		LogDetect.send(LogDetect.DataType.specialType, "UsersManageInOut_01152修改账户====:", getdate);
		handler.sendMessage(handler.obtainMessage(200, (Object) getdate));
		
	}
	public void tuiguangrenshu(String[] params, Handler handler) {
		Page getdate = usersManage.tuiguangrenshu(params);
		LogDetect.send(LogDetect.DataType.specialType, "UsersManageInOut_01152消费:", getdate);
		handler.sendMessage(handler.obtainMessage(210, (Object) getdate));
		
	}
	
	
}
