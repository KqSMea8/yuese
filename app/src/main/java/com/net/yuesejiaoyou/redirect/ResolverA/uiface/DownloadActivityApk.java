package com.net.yuesejiaoyou.redirect.ResolverA.uiface;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.content.FileProvider;
import android.view.KeyEvent;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.redirect.ResolverA.interface4.utils.Download;

import java.io.File;

//
//
//import cn.minbrower.browser.utils.Download;
//import cn.minbrower.browser2.R;

public class DownloadActivityApk extends Activity {

	private TextView text,apkname;
	private ProgressBar load;
	private final String dirName = "DCIM/bili/boxing";
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_download);
		setFinishOnTouchOutside(false);	
		text = (TextView)findViewById(R.id.label);
		apkname = (TextView)findViewById(R.id.apkname);
		load = (ProgressBar)findViewById(R.id.load);
		final String url = getIntent().getExtras().getString("url");
		final String name = getIntent().getExtras().getString("name");
		apkname.setText(name);
		this.setTitle(name);
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				
				int pro = load.getProgress() + msg.arg1;
				load.setProgress(pro);
				// -------------------
				String totalText = "";
				int total = load.getMax();
				if(total > 1024*1024) {
					double dtotal = total;
					totalText = String.format("%.2f", dtotal/(1024*1024))+"MB";
				} else if(total > 1024) {
					double dtotal = total;
					totalText = String.format("%.2f", dtotal/1024)+"KB";
				} else {
					totalText = total+"Byte";
				}
				// -------------------
				String labelText = "";
				if(pro > 1024*1024) {
					double dpro = pro;
					labelText = String.format("%.2f", dpro/(1024*1024))+"MB/"+totalText;
				} else if(pro > 1024) {
					double dpro = pro;
					labelText = String.format("%.2f", dpro/(1024*1024))+"KB/"+totalText;
				} else {
					labelText = pro+"Byte/"+totalText;
				}
				text.setText(labelText);
				if(pro >= load.getMax()) {
					//openFile(new File(Environment.getExternalStorageDirectory() + "/"+dirName+"/"+name));
					installProcess(new File(Environment.getExternalStorageDirectory() + "/"+dirName+"/"+name));
					//finish();
				}
			}
			
		};
		
		new Thread(
				new Runnable() {

					@Override
					public void run() {
						
						Download l = new Download(url);
						load.setMax(l.getLength());
						
						/** 
				         * 下载文件到sd卡，虚拟设备必须要开始设置sd卡容量 
				         * downhandler是Download的内部类，作为回调接口实时显示下载数据 
				         */  
				        int status = l.down2sd(dirName, name, l.new downhandler() {  
				            @Override
				            public void setSize(int size) {
				                Message msg = handler.obtainMessage();
				                msg.arg1 = size;  
				                msg.sendToTarget();  
				                //Log.d("log", Integer.toString(size));  
				            }  
				        });  
				        //log输出  
				       //if(YhApplication.DEBUG_FLAG) Log.d("log", Integer.toString(status));
					}
					
				}
			).start();
	}

    File filename;
	private void installProcess(File file) {
		filename=file;
		boolean haveInstallPermission;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			//先获取是否有安装未知来源应用的权限
			haveInstallPermission = getPackageManager().canRequestPackageInstalls();
			LogDetect.send(LogDetect.DataType.basicType, "apk下载链接: ",haveInstallPermission);
			if (!haveInstallPermission) {//没有权限
				AlertDialog.Builder builder = new AlertDialog.Builder(DownloadActivityApk.this);
				builder.setTitle("要安装应用程序，需要打开未知源权限。请设置开放权限。");
				builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
							startInstallPermissionSettingActivity();
						}
					}
				});
				builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				});
				builder.create().show();
				return;
			}
		}
		openFile(filename);
	}
	private void startInstallPermissionSettingActivity() {
       //注意这个是8.0新API
		Uri packageURI = Uri.parse("package:" + getPackageName());
		//注意这个是8.0新API
		Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
		startActivityForResult(intent, 10086);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		LogDetect.send(LogDetect.DataType.basicType, "apk下载链接: ",requestCode);
		LogDetect.send(LogDetect.DataType.basicType, "apk下载链接: ",resultCode);
		if (resultCode == RESULT_OK && requestCode == 10086) {
			LogDetect.send(LogDetect.DataType.basicType, "apk下载链接: ",requestCode);
			installProcess(filename);
		}
		//installProcess(filename);
	}

	private void openFile(File file) {
		// TODO Auto-generated method stub
		LogDetect.send(LogDetect.DataType.basicType, "apk下载链接: ",file.getPath());
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(Intent.ACTION_VIEW);

		//判读版本是否在7.0以上
		if (Build.VERSION.SDK_INT >= 24) {
			//provider authorities
			Uri apkUri = FileProvider.getUriForFile(DownloadActivityApk.this, "com.net.yuesejiaoyou.redirect.ResolverD.interface4.file.provider", file);
			//Granting Temporary Permissions to a URI
			intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
			intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
		} else {
			intent.setDataAndType(Uri.fromFile(file),
					"application/vnd.android.package-archive");
		}
		startActivity(intent);
		LogDetect.send(LogDetect.DataType.basicType, "apk下载链接: ", file.getPath());
		finish();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	
}
