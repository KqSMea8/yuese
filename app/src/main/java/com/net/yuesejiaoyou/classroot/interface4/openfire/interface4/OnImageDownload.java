package com.net.yuesejiaoyou.classroot.interface4.openfire.interface4;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * 图片异步下载完成后回调
 * @author yanbin
 *
 */
public interface OnImageDownload {
	void onDownloadSucc(Bitmap bitmap,String c_url,ImageView imageView);
}
