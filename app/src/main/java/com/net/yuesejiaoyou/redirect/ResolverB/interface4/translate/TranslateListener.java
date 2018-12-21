package com.net.yuesejiaoyou.redirect.ResolverB.interface4.translate;

/**
 * Created by Administrator on 2018/3/27.
 */

public interface TranslateListener {
    public void onResult(int languageType, String txt);
    public void onTransResult(int languageType, String txt);
    public void onError(int errCode, String detail);
}
