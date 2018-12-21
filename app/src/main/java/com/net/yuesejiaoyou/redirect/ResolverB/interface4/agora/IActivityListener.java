package com.net.yuesejiaoyou.redirect.ResolverB.interface4.agora;

import android.content.Intent;
import android.view.KeyEvent;

/**
 * Created by Administrator on 2018\8\17 0017.
 */

public interface IActivityListener {
    public void onActivityResult(int requestCode, int resultCode, Intent data);
    public boolean onKeyUp(int keyCode, KeyEvent event);
}
