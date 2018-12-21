package com.net.yuesejiaoyou.redirect.ResolverC.interface1;

import android.os.Handler;

/*import com.example.vliao.core.UsersManage_01156;
import com.example.vliao.interface4.LogDetect;*/

import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.redirect.ResolverC.core.UsersManage_01156;

import java.io.IOException;


public class UsersManageInOut_01156 {

    UsersManage_01156 usersManage = null;
    private LogDetect logDbg;

    public UsersManageInOut_01156() {
        usersManage = new UsersManage_01156();

    }

    //赠送礼物
    public void withdraw_init(String[] params, Handler handler)throws IOException {
        String getdate = usersManage.withdraw_init(params);
        handler.sendMessage(handler.obtainMessage(200,(Object)getdate));
    }
    //赠送礼物
    public void tgwithdraw_init(String[] params, Handler handler)throws IOException {
        String getdate = usersManage.tgwithdraw_init(params);
        handler.sendMessage(handler.obtainMessage(200,(Object)getdate));
    }


    public void withdraw_up(String[] params, Handler handler)throws IOException {
        String getdate = usersManage.withdraw_up(params);
        handler.sendMessage(handler.obtainMessage(201,(Object)getdate));
    }
    public void tgwithdraw_up(String[] params, Handler handler)throws IOException {
        String getdate = usersManage.tgwithdraw_up(params);
        handler.sendMessage(handler.obtainMessage(201,(Object)getdate));
    }

}
