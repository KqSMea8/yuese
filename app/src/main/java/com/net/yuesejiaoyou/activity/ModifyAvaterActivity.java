package com.net.yuesejiaoyou.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/*import com.net.yuesejiaoyou.R;
import com.example.vliao.getset.Member_01152;
import com.example.vliao.interface3.UploadFileTask;
import com.example.vliao.interface3.UsersThread_01152;
import com.example.vliao.interface4.LogDetect;
import com.example.vliao.interface4.LogDetect.DataType;
import com.example.vliao.interface4.RoundImageView;
import com.example.vliao.util.Util;*/
import com.alibaba.fastjson.JSON;
import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.Member_01152;
import com.net.yuesejiaoyou.redirect.ResolverC.interface3.UploadFileTask;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.BaseActivity;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.URL;
import com.net.yuesejiaoyou.utils.ImageUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.DialogCallback;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.OnClick;
import okhttp3.Call;


public class ModifyAvaterActivity extends BaseActivity {
    ImageView back;
    TextView nicheng;
    ImageView touxiang;

    private static final int TAKE_PICTURE = 0x000001;
    RelativeLayout t2;
    LinearLayout xiangji, xiangce;
    String path = Util.url + "/img/imgheadpic/";
    //初始化相册
    private Uri photoUri;
    private String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE
    };
    //初始化异步加载图片
    private DisplayImageOptions options = null;

    //弹出框
    private PopupWindow popupWindow;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化头像
        touxiang = findViewById(R.id.touxiang);
        //初始化昵称
        nicheng = (TextView) findViewById(R.id.nicheng);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String permission : permissions) {
                // 检查该权限是否已经获取
                int i = ContextCompat.checkSelfPermission(ModifyAvaterActivity.this, permission);
                // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
                if (i != PackageManager.PERMISSION_GRANTED) {
                    // 如果没有授予该权限，就去提示用户请求
                    ActivityCompat.requestPermissions(ModifyAvaterActivity.this, permissions, 321);
                    return;
                }
            }
        }

        getDatas();
    }

    private void getDatas() {
        OkHttpUtils
                .post(this)
                .url(URL.URL_USERDETAILE)
                .addParams("param1", Util.userid)
                .build()
                .execute(new DialogCallback(this) {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        showToast("网络异常");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (TextUtils.isEmpty(response)) {
                            showToast("网络异常");
                            return;
                        }

                        List<Member_01152> list1 = JSON.parseArray(response, Member_01152.class);
                        if (list1 == null || list1.size() <= 0) {
                            return;
                        }
                        nicheng.setText(list1.get(0).getNickname());

                        if (list1.get(0).getPhoto().contains("http")) {
                            ImageUtils.loadImage(list1.get(0).getPhoto(), touxiang);
                        }
                    }

                });
    }

    @Override
    protected int getContentView() {
        return R.layout.xiugaiziliao_1152;
    }


    @OnClick(R.id.ll_avater)
    public void avaterClick() {
        showPopupspWindow();
    }

    @OnClick(R.id.ll_name)
    public void nameClick() {
        Intent intent = new Intent();
        intent.setClass(ModifyAvaterActivity.this, NickEditActivity.class);
        intent.putExtra("old_name", nicheng.getText());
        startActivityForResult(intent, 162);
    }

    @OnClick(R.id.queding)
    public void commitClick() {
//        String mode = "save_personal_information";//输入的昵称                                                    //头像图片
//        String[] paramsMap = {Util.userid, nicheng.getText().toString(), path};
//        UsersThread_01152 b = new UsersThread_01152(mode, paramsMap, requestHandler);
//        Thread t = new Thread(b.runnable);
//        t.start();

        final String nick = nicheng.getText().toString();
        if (TextUtils.isEmpty(nick)) {
            showToast("请输入昵称");
            return;
        }
        OkHttpUtils
                .post(this)
                .url(URL.URL_UPDATEUSER)
                .addParams("param1", Util.userid)
                .addParams("param2", nick)
                .addParams("param3", path)
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        showToast("修改失败");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (TextUtils.isEmpty(response)) {
                            showToast("修改失败");
                            return;
                        }

                        try { //如果服务端返回1，说明个人信息修改成功了
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("success").equals("1")) {
                                Toast.makeText(ModifyAvaterActivity.this, "修改成功", Toast.LENGTH_SHORT).show();//Modify the success
                                sp.edit().putString("nickname", nick).apply();
                                finish();
                            } else {
                                Toast.makeText(ModifyAvaterActivity.this, "修改失败", Toast.LENGTH_SHORT).show();//Modify the failure
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                });
    }

    @OnClick(R.id.back)
    public void backClick() {
        finish();
    }


    private Handler requestHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                //上传头像
                case 101:
                    @SuppressWarnings("unchecked")
                    String path = (String) msg.obj;
                    sp.edit().putString("photo", path).commit();
                    touxiang.setTag(Util.url + path);
                    getDatas();
                    break;

            }
        }
    };

    //弹出的更改图片界面
    public void showPopupspWindow() {
        // 加载布局
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.modify_photo_01152, null);
        xiangji = (LinearLayout) layout.findViewById(R.id.xiangji);
        xiangji.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                takePhoto();
                popupWindow.dismiss();

            }
        });


        xiangce = (LinearLayout) layout.findViewById(R.id.xiangce);
        xiangce.setOnClickListener(new OnClickListener() {
            //点击相册，从相册中选择
            @Override
            public void onClick(View arg0) {
                xiangce();
                popupWindow.dismiss();
            }

            //打开系统相册
            private void xiangce() {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 3);

            }
        });
        t2 = (RelativeLayout) layout.findViewById(R.id.t2);
        t2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });


        popupWindow = new PopupWindow(layout, LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT, true);
        // 控制键盘是否可以获得焦点
        popupWindow.setFocusable(true);
        // 设置popupWindow弹出窗体的背景
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.4f;
        getWindow().setAttributes(lp);
        popupWindow.setBackgroundDrawable(new BitmapDrawable(null, ""));
        WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        @SuppressWarnings("deprecation")
        // 获取xoff
                int xpos = manager.getDefaultDisplay().getWidth() / 2
                - popupWindow.getWidth() / 2;
        // xoff,yoff基于anchor的左下角进行偏移。
        // popupWindow.showAsDropDown(parent, 0, 0);
        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER | Gravity.CENTER, 252, 0);
        // 监听

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            // 在dismiss中恢复透明度
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow()
                        .addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                getWindow().setAttributes(lp);
            }
        });

    }


    //判断图片的状态
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3) {
            upimg(data);
        }
        switch (requestCode) {

            case TAKE_PICTURE:
                String imagePath = "";
                if (data != null && data.getData() != null) {// 有数据返回直接使用返回的图片地址

                    LogDetect.send(LogDetect.DataType.specialType, "mImagePath: ", mImagePath);
                    imagePath = data.getData().getEncodedPath();

                } else {// 无数据使用指定的图片路径
                    imagePath = mImagePath;
                    LogDetect.send(LogDetect.DataType.specialType, "mImagePath: ", mImagePath);
                }
                String a = compressPic(imagePath);
                UploadFileTask uploadFileTask = new UploadFileTask(ModifyAvaterActivity.this, requestHandler);
                uploadFileTask.execute(a, Util.userid);
                break;

            case 162:
                if (resultCode == RESULT_OK) {
                    String t1 = data.getStringExtra("new_name");
                    nicheng.setText(t1);
                }
                break;
        }

    }


    //不同手机的处理情况
    @SuppressLint("NewApi")
    protected void upimg(Intent data) {
        int colunm_index = 0;
        String path;
        if (data == null) {
            Toast.makeText(ModifyAvaterActivity.this, "未选择图片", Toast.LENGTH_LONG).show();
            return;
        }

        photoUri = data.getData();
        if (photoUri == null) {
            Toast.makeText(ModifyAvaterActivity.this, "选择文件格式错误", Toast.LENGTH_LONG).show();
            return;
        }
        String[] pojo = {MediaStore.Images.Media.DATA};
        @SuppressWarnings("deprecation")
        Cursor cursor = managedQuery(photoUri, pojo, null, null, null);
        if (cursor != null) {
            colunm_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            path = cursor.getString(colunm_index);

            // 华为手机方案
            if (path == null) {
                final String docId = DocumentsContract.getDocumentId(photoUri);
                final String[] split = docId.split(":");
                final String[] selectionArgs = new String[]{split[1]};
                cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, pojo, "_id=?", selectionArgs, null);
                cursor.moveToFirst();
                final int index = cursor.getColumnIndexOrThrow("_data");
                path = cursor.getString(index);
            }


            path = compressPic(path);    //压缩图片并保存，返回小图片path


            //ContentResolver cr = getContentResolver();
        } else {
            // 小米手机方案
            path = data.getData().getEncodedPath();
            path = compressPic(path);
        }
        /*try {
            bitmap = BitmapFactory.decodeStream(new FileInputStream(new File(path)));

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
        UploadFileTask uploadFileTask = new UploadFileTask(ModifyAvaterActivity.this, requestHandler);
        uploadFileTask.execute(path, Util.userid);
    }

    //压缩图片的方法
    private String compressPic(String path) {
        //01107m add start
        File fileTemp = new File(path);
        long length = fileTemp.length();
        if (length > 1024 * 200) {    //文件大于1MB就把图片长宽各压缩1/4，图片质量降低为原来的80%
            FileOutputStream fos;
            BitmapFactory.Options newOpts = new BitmapFactory.Options();
            newOpts.inJustDecodeBounds = true;
            Bitmap bitmap = BitmapFactory.decodeFile(path, newOpts);
            newOpts.inJustDecodeBounds = false;
            newOpts.inSampleSize = 4;
            bitmap = BitmapFactory.decodeFile(path, newOpts);
            try {
                File newFile = new File(Environment.getExternalStorageDirectory() + "/" + fileTemp.getName());
                if (newFile.exists()) {
                    fos = new FileOutputStream(fileTemp);
                } else {    //如果是从相册发送的图片且体积过大，压缩后存放入SD根目录
                    path = Environment.getExternalStorageDirectory() + "/" + fileTemp.getName();
                    fos = new FileOutputStream(new File(path));
                }

                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos);
                try {
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                // 释放局部变量bitmap资源,防止OOM异常
                if (bitmap != null && !bitmap.isRecycled()) {
                    bitmap.recycle();
                    bitmap = null;
                    System.gc();
                }
            }
        }
        return path;
    }

    String mImagePath = "";

    private void takePhoto() {
        // 执行拍照前，应该先判断SD卡是否存在
        String SDState = Environment.getExternalStorageState();
        if (SDState.equals(Environment.MEDIA_MOUNTED)) {
            /**
             * 通过指定图片存储路径，解决部分机型onActivityResult回调 data返回为null的情况
             */
            //获取与应用相关联的路径
            String imageFilePath = getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
            //String imageFilePath =getSDPath();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
            //根据当前时间生成图片的名称
            String timestamp = "/" + formatter.format(new Date()) + ".jpg";
            File imageFile = new File(imageFilePath, timestamp);// 通过路径创建保存文件

            Uri imageFileUri;

			/*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//如果是7.0android系统
                ContentValues contentValues = new ContentValues(1);
				contentValues.put(MediaStore.Images.Media.DATA, imageFile.getAbsolutePath());
				imageFileUri= getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues);
			}else{
				imageFileUri = Uri.fromFile(imageFile);
			}*/
            imageFileUri = Uri.fromFile(imageFile);
            LogDetect.send(LogDetect.DataType.basicType, "01152-照片", imageFileUri);
            mImagePath = imageFile.getAbsolutePath();
            LogDetect.send(LogDetect.DataType.specialType, "mImagePath: ", mImagePath);
            //Uri imageFileUri = Uri.fromFile(imageFile);// 获取文件的Uri
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);// 告诉相机拍摄完毕输出图片到指定的Uri
            startActivityForResult(intent, TAKE_PICTURE);
        } else {
            //Toast.makeText(this, "内存卡不存在！", Toast.LENGTH_LONG).show();
        }
    }


}