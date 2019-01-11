package com.net.yuesejiaoyou.redirect.ResolverD.interface4;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;


import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.redirect.ResolverB.interface3.UsersThread_01066B;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.cn.sharesdk.onekeyshare.OnekeyShare;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;

import cn.sharesdk.wechat.friends.Wechat;


public class ShareHelp {
    // 一键分享
    public void showShare(final Context context, final String pn) {


        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
            @Override
            public void onShare(Platform platform, Platform.ShareParams paramsToShare) {
                if ("Wechat".equals(platform.getName())) {
                    paramsToShare.setTitle("悦色 - 高颜值一对一视频交友");
                    paramsToShare.setUrl("http://www.yuese518.top?code=" + pn + "&fa=" + (Math.random() * 9 + 1) * 100000);
                    // paramsToShare.setUrl("http://xinliao.mingweishipin.cn/uiface/vl?a=A-user-search&b=getinvitcode&c=&d="+ Util.userid+"&e="+(int)((Math.random()*9+1)*100000));
                    paramsToShare.setText("长夜漫漫无心睡眠，悦色小妹陪你聊天！");
                    paramsToShare.setImageUrl("http://116.62.220.67:8090/img/imgheadpic/launch_photo.png");
                    paramsToShare.setShareType(Platform.SHARE_WEBPAGE);
                }
                if ("WechatMoments".equals(platform.getName())) {
                    paramsToShare.setTitle("悦色 - 高颜值一对一视频交友");
                    int a = (int) ((Math.random() * 9 + 1) * 100000);
                    paramsToShare.setUrl("http://www.yuese518.top?code=" + pn + "&fa=" + (Math.random() * 9 + 1) * 100000);
                    // paramsToShare.setUrl("http://xinliao.mingweishipin.cn/uiface/vl?a=A-user-search&b=getinvitcode&c=&d="+ Util.userid+"&e="+(int)((Math.random()*9+1)*100000));
                    paramsToShare.setText("长夜漫漫无心睡眠，悦色小妹陪你聊天！");
                    paramsToShare.setImageUrl("http://116.62.220.67:8090/img/imgheadpic/launch_photo.png");
                    paramsToShare.setShareType(Platform.SHARE_WEBPAGE);
                }
            }
        });

        oks.setCallback(new PlatformActionListener() {

            @Override
            public void onCancel(Platform arg0, int arg1) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
                // TODO Auto-generated method stub
                //开线程，向服务端奖励娃娃币


            }

            @Override
            public void onError(Platform arg0, int arg1, Throwable arg2) {
                // TODO Auto-generated method stub
                LogDetect.send(LogDetect.DataType.basicType, "01162", arg2);
                Log.v("TTT", "arg0=" + arg0 + ",arg1=" + arg1 + ",Exception=" + getStackInfo(arg2));
            }

        });
        // 启动分享GUI
        oks.show(context);

    }

    private String getStackInfo(Throwable e) {
        String info;
        Writer writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        e.printStackTrace(pw);
        info = writer.toString();
        pw.close();
        try {
            writer.close();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return info;
    }

    // 一键分享
    public void showSharevideo(final Context context, final String pn, final String videoid) {


        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
            @Override
            public void onShare(Platform platform, Platform.ShareParams paramsToShare) {
                if ("Wechat".equals(platform.getName())) {
                    paramsToShare.setTitle("悦色 - 高颜值一对一视频交友");
                    paramsToShare.setUrl("http://" + Util.yuming + "?code=" + pn + "&fa=" + (Math.random() * 9 + 1) * 100000);
                    // paramsToShare.setUrl("http://xinliao.mingweishipin.cn/uiface/vl?a=A-user-search&b=getinvitcode&c=&d="+ Util.userid+"&e="+(int)((Math.random()*9+1)*100000));
                    paramsToShare.setText("长夜漫漫无心睡眠，悦色小妹陪你聊天！");
                    paramsToShare.setImageUrl("http://116.62.220.67:8090/img/imgheadpic/launch_photo.png");
                    paramsToShare.setShareType(Platform.SHARE_WEBPAGE);
                }
                if ("WechatMoments".equals(platform.getName())) {
                    paramsToShare.setTitle("悦色 - 高颜值一对一视频交友");
                    int a = (int) ((Math.random() * 9 + 1) * 100000);
                    paramsToShare.setUrl("http://" + Util.yuming + "?code=" + pn + "&fa=" + (Math.random() * 9 + 1) * 100000);
                    // paramsToShare.setUrl("http://xinliao.mingweishipin.cn/uiface/vl?a=A-user-search&b=getinvitcode&c=&d="+ Util.userid+"&e="+(int)((Math.random()*9+1)*100000));
                    paramsToShare.setText("长夜漫漫无心睡眠，悦色小妹陪你聊天！");
                    paramsToShare.setImageUrl("http://116.62.220.67:8090/img/imgheadpic/launch_photo.png");
                    paramsToShare.setShareType(Platform.SHARE_WEBPAGE);
                }
            }
        });

        oks.setCallback(new PlatformActionListener() {

            @Override
            public void onCancel(Platform arg0, int arg1) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
                // TODO Auto-generated method stub
                //开线程，向服务端奖励娃娃币


                String mode1 = "sharevideo";
                //userid，页数，男女
                String[] params = {"13", videoid};
                UsersThread_01066B b = new UsersThread_01066B(mode1, params, null);
                Thread thread = new Thread(b.runnable);
                thread.start();
            }

            @Override
            public void onError(Platform arg0, int arg1, Throwable arg2) {
                // TODO Auto-generated method stub
                LogDetect.send(LogDetect.DataType.basicType, "01162", arg2);
            }

        });
        // 启动分享GUI
        oks.show(context);

    }


    // 微信好友   arr[0]:标题， arr[1]:url，arr[2]:图片
    /*public void ShareWechat(final Context context,String[] arr){
		ShareParams sp = new ShareParams();
	    sp.setShareType(Platform.SHARE_WEBPAGE);
	    sp.setText(context.getString(R.string.app_name));
	    sp.setUrl(Utils.url+arr[1]);
	    sp.setTitle(arr[0]);
	    sp.setText(arr[0]);
	    if(arr[2].equals("1")){
	    *//**//*Bitmap thumb = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
	    sp.setImageData(thumb);*//**//*
	    sp.setImageUrl("http://www.yudaobing.cn/app_icon.png");
	    }else{
	    sp.setImageUrl(Utils.url+arr[2]);
	    }
	    
		Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
		//wechat.setPlatformActionListener(paListener); // 设置分享事件回调
		wechat.setPlatformActionListener(new PlatformActionListener() {
	        @Override
	        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
	            Log.e("分享----------ok","ok");
	            Toast.makeText(context, "分享成功",Toast.LENGTH_LONG ).show();
	        }

	        @Override
	        public void onError(Platform platform, int i, Throwable throwable) {
	            Log.e("分享----------no","no");
	            Toast.makeText(context, "分享失败",Toast.LENGTH_LONG ).show();
	        }

	        @Override
	        public void onCancel(Platform platform, int i) {
	            Log.e("分享----------no","no");
	            Toast.makeText(context, "分享取消",Toast.LENGTH_LONG ).show();
	        }
	    });
		// 执行图文分享
		wechat.share(sp);
	}
	// 微信朋友圈
	public void ShareWechatMom(final Context context,String[] arr){
	    Platform.ShareParams sp = new Platform.ShareParams();
	    sp.setShareType(Platform.SHARE_WEBPAGE);
	    sp.setText(context.getString(R.string.app_name));
	    sp.setUrl(Utils.url+arr[1]);
	    sp.setTitle(arr[0]);
	    sp.setText(arr[0]);
	    if(arr[2].equals("1")){
	    sp.setImageUrl("http://www.yudaobing.cn/app_icon.png");
	    }else{
	    sp.setImageUrl(Utils.url+arr[2]);
	    }
	    Platform wechat = ShareSDK.getPlatform(WechatMoments.NAME);
	    wechat.setPlatformActionListener(new PlatformActionListener() {
	        @Override
	        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
	            Log.e("分享----------ok","ok");
	            Toast.makeText(context, "分享成功",Toast.LENGTH_LONG ).show();
	        }

	        @Override
	        public void onError(Platform platform, int i, Throwable throwable) {
	            Log.e("分享----------no","no");
	            Toast.makeText(context, "分享失败",Toast.LENGTH_LONG ).show();
	        }

	        @Override
	        public void onCancel(Platform platform, int i) {
	            Log.e("分享----------no","no");
	            Toast.makeText(context, "分享取消",Toast.LENGTH_LONG ).show();
	        }
	    });
	    wechat.share(sp);
	}
	// QQ分享
	*//**//*public void ShareQQ(final Context context,String[] arr){
		ShareParams sp = new ShareParams();
	    sp.setShareType(Platform.SHARE_WEBPAGE);
	    sp.setText(context.getString(R.string.app_name));
	    sp.setSiteUrl(Utils.url+arr[1]);
	    sp.setTitleUrl(Utils.url+arr[1]);
	    sp.setTitle(arr[0]);
	    sp.setComment(arr[0]);
	    sp.setText(arr[0]);
	    if(arr[2].equals("1")){
	    sp.setImageUrl("http://www.yudaobing.cn/app_icon.png");
	    }else{
	    sp.setImageUrl(Utils.url+arr[2]);
	    }
	    
		Platform qq = ShareSDK.getPlatform(QQ.NAME);
		//qq.setPlatformActionListener(paListener); // 设置分享事件回调
		qq.setPlatformActionListener(new PlatformActionListener() {
	        @Override
	        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
	            Log.e("分享----------ok","ok");
	            Toast.makeText(context, "分享成功",Toast.LENGTH_LONG ).show();
	        }

	        @Override
	        public void onError(Platform platform, int i, Throwable throwable) {
	            Log.e("分享----------no","no");
	            Toast.makeText(context, "分享失败",Toast.LENGTH_LONG ).show();
	        }

	        @Override
	        public void onCancel(Platform platform, int i) {
	            Log.e("分享----------no","no");
	            Toast.makeText(context, "分享取消",Toast.LENGTH_LONG ).show();
	        }
	    });
		// 执行图文分享
		qq.share(sp);
	}*//**//*
    *//**/

    /**
     * 分享到短信
     *//**//*
	*//**//*public void share_ShortMessage(String arg) {
    	ShareParams sp = new ShareParams();
    	sp.setText(arg);
    	Platform shortMessage = ShareSDK.getPlatform(ShortMessage.NAME);
    	shortMessage.share(sp);
    }*//**//**/
    public void showShare2(final Context context, final String pn, final String user_id, final Handler handler) {

        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {

            @Override
            public void onShare(Platform platform, Platform.ShareParams paramsToShare) {
                if ("Wechat".equals(platform.getName())) {
                    //paramsToShare.setTitle(context.getString(R.string.app_name)+"【邀请码:"+pn+"】");

                    //paramsToShare.setUrl("http://code1.mingweishipin.com/qrcode?mode0=A-user-search&mode1=getarticle&mode3=73&from=groupmessage");
                    // paramsToShare.setUrl("http://wawa88.mingweishipin.com/wawa888.html");
                    //paramsToShare.setText("贵族抓娃娃，一款在家也能体验到实时操作娃娃机感觉的APP");
                    paramsToShare.setImageUrl("http://wawa88.mingweishipin.com/fenxiang.png");
                    // paramsToShare.setShareType(Platform.SHARE_WEBPAGE);
                    paramsToShare.setShareType(Platform.SHARE_IMAGE);
                }
                if ("WechatMoments".equals(platform.getName())) {
                    //paramsToShare.setTitle(context.getString(R.string.app_name)+"我在贵族抓娃娃抓中了一只"+pn);
                    //  paramsToShare.setUrl("http://139.129.38.194:9005/uiface/boss/wawa.html?pn="+pn);
                    //paramsToShare.setUrl("http://wawa88.mingweishipin.com/wawa888.html");
                    //paramsToShare.setText("贵族抓娃娃，一款在家也能体验到实时操作娃娃机感觉的APP");
                    // paramsToShare.setImageUrl("http://139.129.38.194:9005/logos.png");
                    paramsToShare.setImageUrl("http://wawa88.mingweishipin.com/fenxiang.png");
                    //paramsToShare.setShareType(Platform.SHARE_WEBPAGE);
                    paramsToShare.setShareType(Platform.SHARE_IMAGE);
                }
            }
        });


        oks.setCallback(new PlatformActionListener() {

            @Override
            public void onCancel(Platform arg0, int arg1) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
                // TODO Auto-generated method stub
                //开线程，向服务端奖励娃娃币

            }

            @Override
            public void onError(Platform arg0, int arg1, Throwable arg2) {
                // TODO Auto-generated method stub

            }

        });
        // 启动分享GUI
        oks.show(context);
    }

    // 微信登录
    public void wx_login(final Handler handler, final String flag) {
        LogDetect.send(LogDetect.DataType.specialType, "wx_login: ", "wx_login");
        Log.v("TTT", "wx_login");
        Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
        //回调信息，可以在这里获取基本的授权返回的信息，但是注意如果做提示和UI操作要传到主线程handler里去执行
        wechat.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onError(Platform arg0, int arg1, Throwable arg2) {
                // TODO Auto-generated method stub
                arg2.printStackTrace();
                LogDetect.send(LogDetect.DataType.specialType, "onError: ", arg2);
            }

            @Override
            public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
                // TODO Auto-generated method stub
                //输出所有授权信息
                LogDetect.send(LogDetect.DataType.specialType, "onSuccess: ", arg2);
                Log.v("TTT", "WXLOGIN: " + arg2);
                arg0.getDb().exportData();
                //LogDetect.send(Utils.android, arg0.getDb().exportData());
                Map<Object, Object> maps = new HashMap<Object, Object>();
                Iterator ite = arg2.entrySet().iterator();
                while (ite.hasNext()) {
                    Map.Entry entry = (Map.Entry) ite.next();
                    Object key = entry.getKey();
                    Object value = entry.getValue();
                    maps.put(key, value);
                    Log.v("TTT", "WXLOGIN: key=" + key + ",value=" + value);
                }
                LogDetect.send("数据存入maps", maps);
                Message message = new Message();
                if (flag.equals("0")) {
                    // 登录页面
                    message.what = 20000;
                }
                if (flag.equals("1")) {
                    // 注册页面
                    message.what = 20001;
                }
                message.obj = maps;
                handler.sendMessage(message);
            }

            @Override
            public void onCancel(Platform arg0, int arg1) {
                LogDetect.send("onCancel", "取消");
            }
        });
        //authorize与showUser单独调用一个即可
        wechat.authorize();//单独授权,OnComplete返回的hashmap是空的
        LogDetect.send(LogDetect.DataType.specialType, "showUser: ", "showUser");
        wechat.showUser(null);//授权并获取用户信息
        //移除授权
        //wechat.removeAccount(true);
        LogDetect.send(LogDetect.DataType.specialType, "showUser: ", "showUser2");
    }

    // 删除授权
    public void wx_delete() {
        Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
        //移除授权
        wechat.removeAccount(true);
        //UMWXHandler.setRefreshTokenAvailable(false);
        LogDetect.send("删除微信授权", "删除微信授权");
    }
}