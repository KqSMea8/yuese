package com.net.yuesejiaoyou.redirect.ResolverD.interface4.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.BaseActivity;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.utils.YDDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import okhttp3.Call;


public class DownloadActivity extends BaseActivity {

    @BindView(R.id.label)
    TextView text;
    @BindView(R.id.apkname)
    TextView apkname;
    @BindView(R.id.load)
    ProgressBar load;


    String dirName = "DCIM/bili/boxing";
    String name;
    String url;
    String vsion;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFinishOnTouchOutside(false);

        dirName = Environment.getExternalStorageDirectory() + File.separator + "DCIM/bili/boxing";

        url = getIntent().getExtras().getString("url");
        name = getIntent().getExtras().getString("name");
        vsion = getIntent().getExtras().getString("vsion");

        text.setText("新版本：" + name);
        apkname.setText("版本号：" + vsion);

        download();
    }

    private void download(){
        OkHttpUtils.get(this)
                .url(url)
                .build()
                .execute(new FileCallBack(dirName, vsion + ".d") {

                    int lastProgress = 0;

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        new YDDialog.Builder(DownloadActivity.this)
                                .setMessage("下载失败，请检查网络设置")
                                .setPositiveButton("重新下载", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                       download();
                                    }
                                })
                                .setNegativeButton("退出", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                })
                                .show();
                    }

                    @Override
                    public void inProgress(float progress, long total, int id) {
                        int pro = (int) (progress * 100);
                        if (pro > lastProgress) {
                            load.setProgress(pro);
                            lastProgress = pro;
                        }
                    }

                    @Override
                    public void onResponse(File response, int id) {
                        installApk();
                    }
                });
    }


    @Override
    protected int getContentView() {
        return R.layout.activity_download;
    }

    private void installApk() {
        File file = new File(dirName, vsion + ".d");
        File newFile = new File(dirName, vsion + ".apk");

        if (file.exists()) {
            file.renameTo(newFile);
        }

        chmod(newFile.getAbsolutePath());

        installProcess(newFile);
    }

    public static void chmod(String path) {
        try {
            String command = "chmod 777 " + path;
            Log.i("update", command);
            Runtime runtime = Runtime.getRuntime();
            runtime.exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    File filename;

    private void installProcess(File file) {
        filename = file;
        boolean haveInstallPermission;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //先获取是否有安装未知来源应用的权限
            haveInstallPermission = getPackageManager().canRequestPackageInstalls();
            if (!haveInstallPermission) {//没有权限
                AlertDialog.Builder builder = new AlertDialog.Builder(DownloadActivity.this);
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
        openFile(file);
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
        if (resultCode == RESULT_OK && requestCode == 10086) {
            installProcess(filename);
        }
    }

    private void openFile(File file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= 24) {
            Uri apkUri = FileProvider.getUriForFile(DownloadActivity.this, "com.net.yuesejiaoyou.redirect.ResolverD.interface4.file.provider", file);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
