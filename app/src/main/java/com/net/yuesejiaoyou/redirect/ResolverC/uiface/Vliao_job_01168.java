package com.net.yuesejiaoyou.redirect.ResolverC.uiface;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/*import com.net.yuesejiaoyou.R;
import com.example.vliao.cache.ImageResizer;
import com.example.vliao.interface4.LogDetect;*/

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.cache.ImageResizer;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

//import com.example.vliao.cache.ImageResizer;

public class Vliao_job_01168 extends Activity implements OnClickListener {
	
	/*private ImageView fanhui;
	private EditText wenben,shoujihao;
	private TextView queren;
	private String neirong="";
	private String phonenum="";
	String wenbenneirong = "";*/
	//明星、主播、律师、M理咨询师、确认
	private TextView mingxing,zhubo,lvshi,xinli,queren,wenben1,wenben2;
	private ImageView tu1,tu2,tu3,tu4,back;
	private LinearLayout yincang2;
	private ImageResizer m_imageWork = null;
	private static final int TAKE_PICTURE = 0x000001;
	private Uri photoUri;
	private String tupian1 = "", tupian2 = "", tupian3 = "", tupian4 = "";
	private String tu;
	private String M1 = "", M2 = "", M3 = "", M4 = "";
	private String neirong = "";
	Bundle bundle;
	private Intent intent= new Intent();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		m_imageWork = new ImageResizer(this, 200, 200);
		
		setContentView(R.layout.zhiye_01168);
		//改变字体
		//Typeface typeFace =Typeface.createFromAsset(getAssets(),"fonts/Arial_0.ttf");
		
		/*fanhui = (ImageView)findViewById(R.id.fanhui);
		fanhui.setOnClickListener(this);
		//确认
		queren = (TextView)findViewById(R.id.queren);
		queren.setOnClickListener(this);
		*//**
		 * 改变字体？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？
		 *//*
		//queren.setTypeface(typeFace);
		
		//内容
		wenben = (EditText)findViewById(R.id.wenben);
		//手机号
		shoujihao = (EditText)findViewById(R.id.shoujihao);
		*/
		back = (ImageView)findViewById(R.id.back);
		back.setOnClickListener(this);
		tu1 = (ImageView)findViewById(R.id.tu1);
		tu2 = (ImageView)findViewById(R.id.tu2);
		tu3 = (ImageView)findViewById(R.id.tu3);
		tu4 = (ImageView)findViewById(R.id.tu4);
		tu1.setOnClickListener(this);
		tu2.setOnClickListener(this);
		tu3.setOnClickListener(this);
		tu4.setOnClickListener(this);
		queren = (TextView)findViewById(R.id.queren);
		queren.setOnClickListener(this);
		mingxing = (TextView)findViewById(R.id.mingxing);
		mingxing.setOnClickListener(this);
		zhubo = (TextView)findViewById(R.id.zhubo);
		zhubo.setOnClickListener(this);
		lvshi = (TextView)findViewById(R.id.lvshi);
		lvshi.setOnClickListener(this);
		xinli = (TextView)findViewById(R.id.xinli);
		xinli.setOnClickListener(this);
		wenben1 = (TextView)findViewById(R.id.wenben1);
		wenben1.setOnClickListener(this);
		wenben2 = (TextView)findViewById(R.id.wenben2);
		wenben2.setOnClickListener(this);
		yincang2 = (LinearLayout)findViewById(R.id.yincang2);
		mingxing.performClick();
	}
	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		int id = v.getId();
		switch (id) {
		
		case R.id.back://退出操作
			bundle = new Bundle();
			bundle.putString("job", "");
			bundle.putString("photo_job", "");
			intent.putExtras(bundle);
			setResult(206,  intent);
			this.finish();
			break;
		case R.id.queren:
			
			panduan();
			
			/*wenbenneirong = wenben.getText().toString();
			if(wenbenneirong.equals("")){
				Toast.makeText(Vliao_job_01168.this, "反馈内容不能为空!",
						Toast.LENGTH_SHORT).show();
			}else{
				//意见反馈
				neirong = wenben.getText().toString();
				phonenum = shoujihao.getText().toString();
				String[] params={Util.userid,neirong,phonenum};
				UsersThread_01168 a = new UsersThread_01168("Vliao_yijianfankui",params,requestHandler);
				Thread thread = new Thread(a.runnable);
				LogDetect.send(LogDetect.DataType.specialType,"提交意见反馈", thread);
				thread.start();
			}*/
		break;
		case R.id.mingxing:
			neirong = mingxing.getText().toString();
			yanse();
			mingxing.setTextColor(0xffFE0202);
			wenben1.setText("请上传您的身份证照片(正反两张)");
			wenben2.setVisibility(View.GONE);
			yincang2.setVisibility(View.GONE);
			qingkong(1);
			break;
		case R.id.zhubo:
			yanse();
			neirong = zhubo.getText().toString();
			zhubo.setTextColor(0xffFE0202);
			wenben1.setText("请上传您的身份证照片(正反两张)");
			wenben2.setVisibility(View.GONE);
			yincang2.setVisibility(View.GONE);
			qingkong(2);
			break;
		case R.id.lvshi:
			yanse();
			neirong = lvshi.getText().toString();
			lvshi.setTextColor(0xffFE0202);
			wenben1.setText("请上传您的《法律职业资格证书》(正反两张)");
			wenben2.setVisibility(View.VISIBLE);
			yincang2.setVisibility(View.VISIBLE);
			qingkong(3);
			break;
		case R.id.xinli:
			yanse();
			neirong = xinli.getText().toString();
			xinli.setTextColor(0xffFE0202);
			wenben1.setText("请上传您的《M理咨询师资格证书（三级）》(正反两张)");
			wenben2.setVisibility(View.GONE);
			yincang2.setVisibility(View.GONE);
			qingkong(4);
			break;
		case R.id.tu1:
			//if (M1.equals("")) {
				tu = "1";
				xiangce();
			//}
			break;
		case R.id.tu2:
			tu = "2";
			xiangce();
			break;
		case R.id.tu3:
			tu = "3";
			xiangce();
			break;
		case R.id.tu4:
			tu = "4";
			xiangce();
			break;
		}
		
	}
	
	private Handler requestHandler = new Handler() {
		  @Override
		  public void handleMessage(Message msg) {
		    switch (msg.what) {
		      case 0:
		      break;
		     
		      //解析重置密码请求返回的json字符串
		      /*
		      case 200:
		    	  String json = (String)msg.obj;
			    	try { //如果服务端返回1，说明个人信息修改成功了
			    		JSONObject jsonObject = new JSONObject(json);
						if(jsonObject.getString("success").equals("1")){
							//提交成功
							Toast.makeText(Vliao_job_01168.this, "提交成功", Toast.LENGTH_SHORT).show();
							finish();
						}else{
							//提交失败
							Toast.makeText(Vliao_job_01168.this, "提交失败", Toast.LENGTH_SHORT).show();  
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    	 break;
		      */
		     
		    }
		 }
	};
	
	
	private void yanse(){
		mingxing.setTextColor(0xffFFFFFF);
		zhubo.setTextColor(0xffFFFFFF);
		lvshi.setTextColor(0xffFFFFFF);
		xinli.setTextColor(0xffFFFFFF);
	}
	
	
	private void xiangce() {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(intent, 3);
	}
	
	
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
			/*uri = data.getData();
            Cursor cursor = ((FragmentActivity) mContext).getContentResolver().query(uri, proj, null,
                    null, null);
            if (cursor == null) {//出现如小米等手机返回的绝对路径错误时,自己拼出路径
                uri = getUri(mContext, data);
            }*/
					LogDetect.send(LogDetect.DataType.specialType, "mImagePath: ", mImagePath);
					imagePath = data.getData().getEncodedPath();

				} else {// 无数据使用指定的图片路径
					imagePath = mImagePath;
					LogDetect.send(LogDetect.DataType.specialType, "mImagePath: ", mImagePath);
				}
				String a = compressPic(imagePath);

//				UploadFileTask_v_01150 uploadFileTask2 = new UploadFileTask_v_01150(Authentication_01150.this, handler);
//				uploadFileTask2.execute(a, Util.userid);

				break;

		}

		



	}
	
	
	
	
protected void upimg(Intent data) {
	int colunm_index = 0;
	String path;
	LogDetect.send(LogDetect.DataType.basicType, "01162", "进入相册上传图片方法");
	if (data == null) {
		Toast.makeText(Vliao_job_01168.this, "未选择图片", Toast.LENGTH_LONG).show();
		return;
	}

	photoUri = data.getData();
	if (photoUri == null) {
		Toast.makeText(Vliao_job_01168.this, "选择图片文件出错", Toast.LENGTH_LONG).show();
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

	if (tu.equals("1")) {
		tupian1 = path;
		tu1.setImageBitmap(m_imageWork.processBitmapNet(path, 200, 200));
		
	} else if (tu.equals("2")) {
		tupian2 = path;
		tu2.setImageBitmap(m_imageWork.processBitmapNet(path, 200, 200));
		
	} else if (tu.equals("3")) {
		tupian3 = path;
		tu3.setImageBitmap(m_imageWork.processBitmapNet(path, 200, 200));

	} else if (tu.equals("4")) {
		tupian4 = path;
		tu4.setImageBitmap(m_imageWork.processBitmapNet(path, 200, 200));

	} 

	/*try {
		bitmap = BitmapFactory.decodeStream(new FileInputStream(new File(path)));

	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}*/
//	LogDetect.send(LogDetect.DataType.basicType, "01150", "相册选择准备上传");
//	UploadFileTask_v_01150 uploadFileTask2 = new UploadFileTask_v_01150(Authentication_01150.this, handler);
//	uploadFileTask2.execute(path, Util.userid);
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

			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//如果是7.0android系统
				ContentValues contentValues = new ContentValues(1);
				contentValues.put(MediaStore.Images.Media.DATA, imageFile.getAbsolutePath());
				imageFileUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
			} else {
				imageFileUri = Uri.fromFile(imageFile);
			}

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
	
	
	
	
	private void qingkong(int i){
		LogDetect.send(LogDetect.DataType.specialType, "清空数据01168: ", "清空图片"+i);
		tu1.setImageBitmap(m_imageWork.processBitmapNet("", 200, 200));
		tu2.setImageBitmap(m_imageWork.processBitmapNet("", 200, 200));
		tu3.setImageBitmap(m_imageWork.processBitmapNet("", 200, 200));
		tu4.setImageBitmap(m_imageWork.processBitmapNet("", 200, 200));
		tupian1 = "";
		tupian2 = "";
		tupian3 = "";
		tupian4 = "";
	}
	
	private void panduan(){

		LogDetect.send(LogDetect.DataType.specialType, "明星: ", neirong+"++"+tupian1+"++"+tupian2);
		
		if(neirong.equals("")){
			LogDetect.send(LogDetect.DataType.specialType, "选择职业: ", "内容");
			Toast.makeText(Vliao_job_01168.this, "请选择您要认证的职业", Toast.LENGTH_SHORT).show();
		}else if(neirong.equals("明星")){
			if(tupian1.equals("")||tupian2.equals("")){
				LogDetect.send(LogDetect.DataType.specialType, "明星: ", "图片1、2");
				Toast.makeText(Vliao_job_01168.this, "请上传对应图像", Toast.LENGTH_SHORT).show();
			}else{
				LogDetect.send(LogDetect.DataType.specialType, "为空时01168 ", tupian1+","+tupian2);
				bundle = new Bundle();
				bundle.putString("job", neirong);
				bundle.putString("photo_job", tupian1+","+tupian2);
				LogDetect.send(LogDetect.DataType.specialType, "为空时01168 ", tupian1+","+tupian2);
				intent.putExtras(bundle);
				setResult(206,  intent);
				this.finish();
			}
		}else if(neirong.equals("主播")){
			if(tupian1.equals("")||tupian2.equals("")){
				LogDetect.send(LogDetect.DataType.specialType, "主播: ", "图片1、2");
				Toast.makeText(Vliao_job_01168.this, "请上传对应图像", Toast.LENGTH_SHORT).show();
			}else{
				LogDetect.send(LogDetect.DataType.specialType, "为空时01168 ", tupian1+","+tupian2);
				bundle = new Bundle();
				bundle.putString("job", neirong);
				bundle.putString("photo_job", tupian1+","+tupian2);
				LogDetect.send(LogDetect.DataType.specialType, "为空时01168 ", tupian1+","+tupian2);
				intent.putExtras(bundle);
				setResult(206,  intent);
				this.finish();
			}
			
		}else if(neirong.equals("律师")){
			if(tupian1.equals("")||tupian2.equals("")||tupian3.equals("")||tupian4.equals("")){
				LogDetect.send(LogDetect.DataType.specialType, "律师: ", "图片1、2");
				Toast.makeText(Vliao_job_01168.this, "请上传对应图像", Toast.LENGTH_SHORT).show();
			}else{
				LogDetect.send(LogDetect.DataType.specialType, "不为空时01168 ", tupian1+","+tupian2+","+tupian3+","+tupian4);
				bundle = new Bundle();
				bundle.putString("job", neirong);
				bundle.putString("photo_job", tupian1+","+tupian2+","+tupian3+","+tupian4);
				LogDetect.send(LogDetect.DataType.specialType, "不为空时01168 ", tupian1+","+tupian2+","+tupian3+","+tupian4);
				intent.putExtras(bundle);
				setResult(206,  intent);
				this.finish();
			}
			
		}else if(neirong.equals("M理咨询师")){
			if(tupian1.equals("")||tupian2.equals("")){
				LogDetect.send(LogDetect.DataType.specialType, "M理咨询师: ", "图片1、2");
				Toast.makeText(Vliao_job_01168.this, "请上传对应图像", Toast.LENGTH_SHORT).show();
			}else{
				LogDetect.send(LogDetect.DataType.specialType, "为空时01168 ", tupian1+","+tupian2);
				bundle = new Bundle();
				bundle.putString("job", neirong);
				bundle.putString("photo_job", tupian1+","+tupian2);
				LogDetect.send(LogDetect.DataType.specialType, "为空时01168 ", tupian1+","+tupian2);
				intent.putExtras(bundle);
				setResult(206,  intent);
				this.finish();
			}
			
		}
		
		
		
	}
	
	
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			//showPopupspWindow1(back_1);
			
			bundle = new Bundle();
			bundle.putString("job", "");
			bundle.putString("photo_job", "");
			intent.putExtras(bundle);
			setResult(206,  intent);
			this.finish();
			
			return true;
		}
		return super.onKeyUp(keyCode, event);
	}
	

}
