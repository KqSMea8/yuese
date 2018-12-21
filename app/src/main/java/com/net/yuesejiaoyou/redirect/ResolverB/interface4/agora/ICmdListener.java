package com.net.yuesejiaoyou.redirect.ResolverB.interface4.agora;

/**
 * Created by Administrator on 2018\8\17 0017.
 */

public interface ICmdListener {

    public void onCmdHangup(); // 拨打中挂断
    public void onCmdTimeup(); // 拨打中超时
    public void onCmdAccept(); // 拨打中接听
}
