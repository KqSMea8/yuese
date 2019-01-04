package com.net.yuesejiaoyou.redirect.ResolverD.interface4;


import com.baidu.mapapi.SDKInitializer;

import com.bilibili.boxing.BoxingCrop;
import com.bilibili.boxing.BoxingMediaLoader;
import com.bilibili.boxing.loader.IBoxingMediaLoader;


import com.fm.openinstall.OpenInstall;
import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.BoxingGlideLoader;
import com.net.yuesejiaoyou.classroot.interface4.BoxingUcrop;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.redirect.ResolverB.uiface.MusicUtil;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.tencent.bugly.crashreport.CrashReport;
import com.xiaojigou.luo.xjgarsdk.XJGArSdkApi;


import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import cn.jpush.android.api.JPushInterface;


/**
 * 主Application，所有百度定位SDK的接口说明请参考线上文档：http://developer.baidu.com/map/loc_refer/index.html
 *
 * 百度定位SDK官方网站：http://developer.baidu.com/map/index.php?title=android-locsdk
 *
 * 直接拷贝com.baidu.location.service包到自己的工程下，简单配置即可获取定位结果，也可以根据demo内容自行封装
 */
public class YhApplicationA extends Application {

	static {
		//设置全局的 Header 构建器
		SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
			@NonNull
			@Override
			public RefreshHeader createRefreshHeader(@NonNull Context context, @NonNull RefreshLayout layout) {
				layout.setPrimaryColorsId(R.color.white, R.color.text_color_grey);
				return new ClassicsHeader(context).setEnableLastTime(false).setTextSizeTitle(14);
			}
		});
		//设置全局的 Footer 构建器
		SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
			@NonNull
			@Override
			public RefreshFooter createRefreshFooter(@NonNull Context context, @NonNull RefreshLayout layout) {
				layout.setPrimaryColorsId(R.color.white, R.color.text_color_grey);
				ClassicsFooter classicsFooter = new ClassicsFooter(context);
				classicsFooter.setTextSizeTitle(14);
				classicsFooter.setAccentColor(Color.parseColor("#b9baba"));
				return classicsFooter.setDrawableSize(20);
			}
		});
	}


	private ArrayList<Activity> ActivityList = new ArrayList<Activity>();
	private ActivityLifecycleCallbacks activityCallback;
	private static YhApplicationA instance;
	public boolean ishave=true;
	public Activity mainthis=null;
	public static YhApplicationA getApplication() {
		return instance;
	}
	@Override
	public void onCreate() {
		super.onCreate();

		instance = this;


		SDKInitializer.initialize(getApplicationContext());
		//初始化图片加载器相关配置
		initImageLoader(getApplicationContext());
		//////初始化B区的拨通音乐
		MusicUtil.init(getApplicationContext());

		StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
		StrictMode.setVmPolicy(builder.build());
		builder.detectFileUriExposure();

		CrashReport.initCrashReport(getApplicationContext(), "78d25f004b", false);

		activityCallback = new ActivityLifecycleCallbacks() {


			@Override
			public void onActivityCreated(Activity activity, Bundle bundle) {
				Log.e("TT","onActivityCreated(): "+activity.getClass().getName());
				//currentActivity = activity;
				ActivityList.add(activity);
			}

			@Override
			public void onActivityDestroyed(Activity activity) {
				//Log.e("TT","onActivityDestroyed(): "+activity.getClass().getName());
				ActivityList.remove(activity);
			}

			@Override
			public void onActivityStarted(Activity activity) {
				//Log.e("TT","onActivityStarted(): "+activity.getClass().getName());
				//currentActivity = activity;	// app当前Activity
			}

			@Override
			public void onActivityResumed(Activity activity) {
				//Log.e("TT","onActivityResumed(): "+activity.getClass().getName());
			}

			@Override
			public void onActivityPaused(Activity activity) {
				//Log.e("TT","onActivityPaused(): "+activity.getClass().getName());
			}

			@Override
			public void onActivityStopped(Activity activity) {
				//Log.e("TT","onActivityStopped(): "+activity.getClass().getName());
			}

			@Override
			public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
				//Log.e("TT","onActivitySaveInstanceState(): "+activity.getClass().getName());
			}
		};

		registerActivityLifecycleCallbacks(activityCallback);

		IBoxingMediaLoader loader = new BoxingGlideLoader();
		BoxingMediaLoader.getInstance().init(loader);
		BoxingCrop.getInstance().init(new BoxingUcrop());

		// 极光推送
		if (isMainProcess()) {
			OpenInstall.init(this);
		}

		jpushInit();

		xiaojigouInit();

	}


	private void xiaojigouInit() {
		String licenseText = "hMPthC0oBIbtMp515TWb9jZvrLAKWIMvA4Dhf03n51QvnJr7jZowVe86d0WwU0NK9QGRFaXQn628fRu941qyr3FtsI5R7Y6v1XEpL6YvQNWQCkFEt1SAb0hyawimOYf1tfG2lIaNE63c5e+OxXssOVUWvw8tOr2glVwWVzh79NmZMahrnS8l69SoeoXLMKCYlvAt/qJFFk4+6Aq3QvOv3o72fq5p90yty+YWg7o0HirZpMSP9P5/DHYPFqR/ud7twTJ+Yo2+ZzYvodqRQbGG0HseZn8Xpt7fZdFuZbc2HGRMVk56vNDMRlcGZZXAjENk7m2UMhi1ohhuSf4WmIgXCZFiJXvYFByaY625gXKtEI7+b7t81nWQYHP9BEbzURwL";
		XJGArSdkApi.XJGARSDKInitialization(this,licenseText,"DoctorLuoInvitedUser:lyhz", "LuoInvitedCompany:hengzhong");
	}

	public Activity getCurrentActivity() {
		int length = ActivityList.size();
		if(length > 0) {
			return ActivityList.get(length -1);
		} else {
			return null;
		}
	}


	public boolean isCurrentP2PGukeActivity() {
		Activity curActivity = getCurrentActivity();
		if(curActivity != null) {
			return curActivity.getClass().getSimpleName().equals("GukeActivity");
		} else {
			return false;
		}
	}


	public boolean isCurrentP2PZhuboActivity() {
		Activity curActivity = getCurrentActivity();
		if(curActivity != null) {
			return curActivity.getClass().getSimpleName().equals("ZhuboActivity");
		} else {
			return false;
		}
	}



	public boolean isMainProcess() {
		int pid = android.os.Process.myPid();
		ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
			if (appProcess.pid == pid) {
				return getApplicationInfo().packageName.equals(appProcess.processName);
			}
		}
		return false;
	}



	public static void initImageLoader(Context context) {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
				.threadPoolSize(3)
				.denyCacheImageMultipleSizesInMemory()
				.threadPriority(Thread.NORM_PRIORITY - 2)// 设置线程的优先级
				.denyCacheImageMultipleSizesInMemory()// 当同一个Uri获取不同大小的图片，缓存到内存时，只缓存一个。默认会缓存多个不同的大小的相同图片
				.discCacheFileNameGenerator(new Md5FileNameGenerator())// 设置缓存文件的名字
				.discCacheFileCount(100)// 缓存文件的最大个数
				.memoryCache(new WeakMemoryCache())
				.tasksProcessingOrder(QueueProcessingType.LIFO)// 设置图片下载和显示的工作队列排序
				.build();
		ImageLoader.getInstance().init(config);
	}



	// 关闭视频录制编辑相关activity
	public void closeManageActivity() {
		for(Activity act : ActivityList) {
			String className = act.getClass().getName();
			if("com.net.yuesejiaoyou.redirect.ResolverB.interface4.videorecorder.AliyunVideoRecorder".equals(className)) {
				Log.e("YB","close AliyunVideoRecorder");
				act.finish();
			} else if("com.net.yuesejiaoyou.redirect.ResolverB.interface4.videoeditor.EditorActivity".equals(className)) {
				Log.e("YB","close EditorActivity");
				act.finish();
			} else if("com.net.yuesejiaoyou.redirect.ResolverB.interface4.videoimport.MediaActivity".equals(className)) {
				Log.e("YB","close MediaActivity");
				act.finish();
			}
		}
	}

	public void close() {
		for(Activity act : ActivityList) {
			act.finish();
		}
	}


	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
		MultiDex.install(this);
	}

	private void jpushInit() {
		JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
		JPushInterface.init(this);     		// 初始化 JPush
	}

}
