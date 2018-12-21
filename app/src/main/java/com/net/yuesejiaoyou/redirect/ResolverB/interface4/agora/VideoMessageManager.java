package com.net.yuesejiaoyou.redirect.ResolverB.interface4.agora;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.openfire.core.Utils;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.db.Const;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack.Chat;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack.ChatManagerListener;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack.Message;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack.MessageListener;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack.SmackException;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.listener.managerlistener;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
//import com.netease.nimlib.sdk.NIMClient;
//import com.netease.nimlib.sdk.Observer;
//import com.netease.nimlib.sdk.RequestCallback;
//import com.netease.nimlib.sdk.SDKOptions;
//import com.netease.nimlib.sdk.StatusBarNotificationConfig;
//import com.netease.nimlib.sdk.StatusCode;
//import com.netease.nimlib.sdk.auth.AuthService;
//import com.netease.nimlib.sdk.auth.AuthServiceObserver;
//import com.netease.nimlib.sdk.auth.LoginInfo;
//import com.netease.nimlib.sdk.msg.MsgService;
//import com.netease.nimlib.sdk.msg.MsgServiceObserve;
//import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
//import com.netease.nimlib.sdk.msg.model.CustomNotification;
//import com.netease.nimlib.sdk.uinfo.UserInfoProvider;
//import com.netease.nimlib.sdk.uinfo.model.UserInfo;
//import com.netease.nimlib.sdk.util.NIMUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2018\8\15 0015.
 */

public class VideoMessageManager {

    public final static String VIDEO_NONE = "0";
    // 用户发起邀请
    public final static String VIDEO_U2A_USER_CALL = "1";         //"用户邀请"; fromdata字段带roomid参数
    public final static String VIDEO_U2A_USER_HANGUP = "2";       //"用户挂断";
    public final static String VIDEO_U2A_USER_TIMEUP = "3";       //"用户超时";
    public final static String VIDEO_U2A_ANCHOR_ACCEPT = "4";     //"主播接听";
    public final static String VIDEO_U2A_ANCHOR_HANGUP = "5";     //"主播挂断";
    public final static String VIDEO_U2A_ANCHOR_TIMEUP = "6";    //"主播超时";

    // 主播发起邀请(回拨)
    public final static String VIDEO_A2U_ANCHOR_CALL = "11";      //"主播邀请"; fromdata字段带roomid参数
    public final static String VIDEO_A2U_ANCHOR_HANGUP = "12";    //"主播挂断";
    public final static String VIDEO_A2U_ANCHOR_TIMEUP = "13";    //"主播超时";
    public final static String VIDEO_A2U_USER_ACCEPT = "14";      //"用户接听";
    public final static String VIDEO_A2U_USER_HANGUP = "15";      //"用户挂断";
    public final static String VIDEO_A2U_USER_TIMEUP = "16";      //"用户超时";

    private static VideoMessageManager instance = new VideoMessageManager();
    private static CmdMsgListener cmdMsgListener;
    private static SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static Context appContext;

    private VideoMessageManager(){}

    /**
     * Application初始化,一般在Application的onCreate里面调用
     */
//    public static void appInit(Context applicationContext) {
//        appContext = applicationContext;
//
//        // SDK初始化（启动后台服务，若已经存在用户登录信息， SDK 将完成自动登录）
//        NIMClient.init(applicationContext, loginInfo(applicationContext), options(applicationContext));
//
//        if (NIMUtil.isMainProcess(applicationContext)) {
//            // 监听用户在线状态
//            NIMClient.getService(AuthServiceObserver.class).observeOnlineStatus(new Observer<StatusCode>() {
//
//                @Override
//                public void onEvent(StatusCode statusCode) {
//
//                    if (statusCode == StatusCode.CONNECTING) {
//                        Log.v("TTT", "正在登录连接");
//                    } else if (statusCode == StatusCode.FORBIDDEN) {
//                        Log.v("TTT", "被服务器拒绝");
//                    } else if (statusCode == StatusCode.KICK_BY_OTHER_CLIENT) {
//                        Log.v("TTT", "被同时在线的其他端踢掉");
//                    } else if (statusCode == StatusCode.KICKOUT) {
//                        Log.v("TTT", "被其他端的登录踢掉");
//                    } else if (statusCode == StatusCode.LOGINED) {
//                        Log.v("TTT", "成功登录");
//                    } else if (statusCode == StatusCode.NET_BROKEN) {
//                        Log.v("TTT", "网络连接断开");
//                    } else if (statusCode == StatusCode.PWD_ERROR) {
//                        Log.v("TTT", "用户名密码错误");
//                    } else if (statusCode == StatusCode.SYNCING) {
//                        Log.v("TTT", "正在同步数据");
//                    } else if (statusCode == StatusCode.UNLOGIN) {
//                        Log.v("TTT", "未登录或登录失败");
//                    } else if (statusCode == StatusCode.VER_ERROR) {
//                        Log.v("TTT", "客户端版本错误");
//                    }
//
//
//                }
//            }, true);
//
//            // 全局消息接收
//            // 如果有自定义通知是作用于全局的，不依赖某个特定的 Activity，那么这段代码应该在 Application 的 onCreate 中就调用
//            NIMClient.getService(MsgServiceObserve.class).observeCustomNotification(new Observer<CustomNotification>() {
//                @Override
//                public void onEvent(CustomNotification message) {
//                    // 在这里处理自定义通知。
//
//                    String fromId = message.getSessionId();
//
//
//                    try {
//                        JSONObject jsonObj = new JSONObject(message.getContent());
//
//                        String fromNickname = jsonObj.getString("fromnickname");
//                        String fromHeadpic = jsonObj.getString("fromheadpic");
//                        String cmd = jsonObj.getString("cmd");
//
//                        instance.onCmdMessageFilter(fromId, fromNickname, fromHeadpic, cmd);
//
////                        if (instance.cmdMsgListener != null) {
////                            instance.cmdMsgListener.onCmdMessageListener(fromId, fromNickname, fromHeadpic, cmd);
////                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }, true);
//        }
//    }

//    private static LoginInfo loginInfo(Context ctxt) {
//        SharedPreferences share = ctxt.getSharedPreferences("Acitivity", Activity.MODE_PRIVATE);
//        String userid = share.getString("userid","");
//        String password = share.getString("password","");
//        Log.v("TTT","appInit: userid: "+userid+", password:"+password);
//        LoginInfo loginInfo = null;
//        if(!userid.isEmpty() && !password.isEmpty()) {
//            loginInfo = new LoginInfo(userid, password);
//        }
//        return loginInfo;
//    }

//    private static SDKOptions options(Context context) {
//        SDKOptions options = new SDKOptions();
//
//        // 如果将新消息通知提醒托管给 SDK 完成，需要添加以下配置。否则无需设置。
//        StatusBarNotificationConfig config = new StatusBarNotificationConfig();
//        config.notificationEntrance = null; //WelcomeActivity.class; // 点击通知栏跳转到该Activity
//        config.notificationSmallIconId = R.drawable.ic_launcher;
//        // 呼吸灯配置
//        config.ledARGB = Color.GREEN;
//        config.ledOnMs = 1000;
//        config.ledOffMs = 1500;
//        // 通知铃声的uri字符串
//        config.notificationSound = "android.resource://com.netease.nim.demo/raw/msg";
//        options.statusBarNotificationConfig = config;
//
//        // 配置保存图片，文件，log 等数据的目录
//        // 如果 options 中没有设置这个值，SDK 会使用采用默认路径作为 SDK 的数据目录。
//        // 该目录目前包含 log, file, image, audio, video, thumb 这6个目录。
//        String sdkPath = context.getExternalCacheDir() + "/leliao"; // 可以不设置，那么将采用默认路径
//        // 如果第三方 APP 需要缓存清理功能， 清理这个目录下面个子目录的内容即可。
//        options.sdkStorageRootPath = sdkPath;
//
//        // 配置是否需要预下载附件缩略图，默认为 true
//        options.preloadAttach = true;
//
//        // 配置附件缩略图的尺寸大小。表示向服务器请求缩略图文件的大小
//        // 该值一般应根据屏幕尺寸来确定， 默认值为 Screen.width / 2
//        options.thumbnailSize = 200;
//
//        // 用户资料提供者, 目前主要用于提供用户资料，用于新消息通知栏中显示消息来源的头像和昵称
//        options.userInfoProvider = new UserInfoProvider() {
//            @Override
//            public UserInfo getUserInfo(String account) {
//                return null;
//            }
//
//            @Override
//            public String getDisplayNameForMessageNotifier(String account, String sessionId,
//                                                           SessionTypeEnum sessionType) {
//                return null;
//            }
//
//            @Override
//            public Bitmap getAvatarForMessageNotifier(SessionTypeEnum sessionTypeEnum, String s) {
//                return null;
//            }
//        };
//        return options;
//    }

    /**
     * 用户注册，同步方法，失败会抛HyphenateException异常
     * @param userid
     * @param password
     * @return
     */
//    public static boolean userRegister(String userid, String password) {
//
//        return false;
//    }

    /**
     * 用户登录
     * @param userid
     * @param password
     * @param callback
     */
//    public static void userLogin(String userid, String password, final LoginCallback callback) {
//
//        Log.v("TTT","userLogin(): "+userid+","+password);
//        LoginInfo loginInfo = new LoginInfo(userid, password);
//        NIMClient.getService(AuthService.class).login(loginInfo).setCallback(new RequestCallback<LoginInfo>() {
//
//            @Override
//            public void onSuccess(LoginInfo loginInfo) {
//                callback.loginSuccess();
//            }
//
//            @Override
//            public void onFailed(int i) {
//                callback.loginError(i,"onFailed");
//            }
//
//            @Override
//            public void onException(Throwable throwable) {
//                callback.loginError(-1,"onException: "+throwable);
//            }
//        });
//    }

    /**
     * 用户登录回调接口
     */
    public interface LoginCallback {
        public void loginSuccess();
        public void loginError(int code, String reason);
    }

    /**
     * 用户退出登录
     */
//    public static void userLogout(final LogoutCallback callback) {
//        NIMClient.getService(AuthService.class).logout();
//    }

    /**
     * 用户退出登录回调接口
     */
    public interface LogoutCallback {
        public void logoutSuccess();
        public void logoutError(int code, String reason);
        public void logoutProgress(int progress, String status);
    }



    public static void initIM(String fromUserid) {

    }

    /**
     * 消息接收回调接口
     */
    public interface CmdMsgListener {
        public void onCmdMessageListener(String fromId, String fromNickname, String fromHeadpic, String cmd, String roomid);
    }

    public interface SendMsgCallback {
        public void onSendSuccess();
        public void onSendFail(int code, String reason);
    }

    public static void setCmdMessageListener(CmdMsgListener listener) {
        cmdMsgListener = listener;
    }

    // 发送消息接口
//    public static void sendCmdMessage(final String fromNickname, final String fromHeadpic, String toId, final String cmd, final SendMsgCallback callback) {
//// 构造自定义通知，指定接收者
//        CustomNotification notification = new CustomNotification();
//        notification.setSessionId(toId);
//        notification.setSessionType(SessionTypeEnum.P2P);
//
//        // 构建通知的具体内容。为了可扩展性，这里采用 json 格式，以 "id" 作为类型区分。
//        // 这里以类型 “1” 作为“正在输入”的状态通知。
//        JSONObject json = new JSONObject();
//        try {
//            json.put("cmd", cmd);
//            json.put("fromnickname", fromNickname);
//            json.put("fromheadpic", fromHeadpic);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        notification.setContent(json.toString());
//
////        notification.setSendToOnlineUserOnly(false);
//
//        // 发送自定义通知
//
//        if(callback == null) {
//            NIMClient.getService(MsgService.class).sendCustomNotification(notification);
//        } else {
//            NIMClient.getService(MsgService.class).sendCustomNotification(notification).setCallback(new RequestCallback<Void>() {
//
//                @Override
//                public void onSuccess(Void aVoid) {
//                    callback.onSendSuccess();
//                }
//
//                @Override
//                public void onFailed(int i) {
//                    callback.onSendFail(i, "onFailed");
//                }
//
//                @Override
//                public void onException(Throwable throwable) {
//                    callback.onSendFail(-1, throwable.toString());
//                }
//            });
//        }
//    }

//    public static void sendCmdMessage(String fromNickname, String fromHeadpic, String toId, String cmd) {
//        sendCmdMessage(fromNickname, fromHeadpic, toId, cmd, null);
//    }

    public static CmdMsgListener getCmdMessageListener() {
        return instance.cmdMsgListener;
    }

    /**
     * 提前拦截收到的消息，做预处理操作(一对一视频预通)
     * @param fromId
     * @param fromNickname
     * @param fromHeadpic
     * @param message
     */
    public void onCmdMessageFilter(String fromId, String fromNickname, String fromHeadpic, String message) {

        Log.v("TTT","fromId: "+fromId+", fromNickname: "+fromNickname+", fromHeadpic: "+fromHeadpic+", message: "+message);

        switch(message) {
            case VIDEO_U2A_USER_CALL: //"用户邀请";
                break;
            case VIDEO_U2A_USER_HANGUP: //"用户挂断";
                break;
            case VIDEO_U2A_USER_TIMEUP: //"用户超时";
                break;
            case VIDEO_U2A_ANCHOR_ACCEPT: //"主播接听";
                break;
            case VIDEO_U2A_ANCHOR_HANGUP: //"主播挂断";
                break;
            case VIDEO_U2A_ANCHOR_TIMEUP: //"主播超时";
                break;

            // 主播发起邀请(回拨)
            case VIDEO_A2U_ANCHOR_CALL: //"主播邀请";
                break;
            case VIDEO_A2U_ANCHOR_HANGUP: //"主播挂断";
                break;
            case VIDEO_A2U_ANCHOR_TIMEUP: //"主播超时";
                break;
            case VIDEO_A2U_USER_ACCEPT: //"用户接听";
                break;
            case VIDEO_A2U_USER_HANGUP: //"用户挂断";
                break;
            case VIDEO_A2U_USER_TIMEUP: //"用户超时";
                break;
        }
    }
}
