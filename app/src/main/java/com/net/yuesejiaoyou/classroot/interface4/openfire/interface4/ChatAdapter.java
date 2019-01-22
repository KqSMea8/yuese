package com.net.yuesejiaoyou.classroot.interface4.openfire.interface4;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.bean.Msg;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.db.Const;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.view.CircleImageView;
import com.net.yuesejiaoyou.classroot.interface4.openfire.uiface.ImgPageActivity;
import com.net.yuesejiaoyou.activity.UserActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalHttp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * from为收到的消息，to为自己的消息
 * 
 * @author baiyuliang
 *
 */
@SuppressLint("NewApi")
public class ChatAdapter extends BaseAdapter {
	private Context mContext;
	private List<Msg> list;
	private int  Timing;
	private FinalBitmap finalImageLoader ;
	private FinalHttp fh;
	AnimationDrawable anim;
	String mheadpath, xgzheadpath;// 个人头像路径
	boolean ismHeadExsits = false;
	boolean isxgzHeadExsits = false;
	private String photoPath,logo,username,headpicture;
	private Activity activity;
	private ListView  mListView;
	ImageDownloader mDownloader;
	private int mMinItemWidth,width;
	private int mMaxItemWidth;
	private LayoutInflater mInflater;
	private    boolean isleft;  
 	private DisplayImageOptions options;
 	
	//记录语音动画图片  
	int index=1;  
	private Handler mHandler;
	
	private static Map<String, SoftReference<Bitmap>> imageCaches = new HashMap<String, SoftReference<Bitmap>>();
	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public ChatAdapter(Context mContext, List<Msg> list,String logo,Activity activity,ListView mListView,Handler mHandler) {
		super();
		this.mContext = mContext;
		this.list = list;
		this.mHandler = mHandler;
		this.logo=logo;
		/*this.Timing = Timing;*/
		this.activity=activity;
		this.mListView=mListView;
//		 finalImageLoader=FinalBitmap.create(mContext);
//		 finalImageLoader.configLoadingImage(R.drawable.location_default);
//		 fh=new FinalHttp();
		 
		 WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
	        DisplayMetrics outMetrics = new DisplayMetrics();
	        wm.getDefaultDisplay().getMetrics(outMetrics);
	        width = outMetrics.widthPixels;
		 SharedPreferences share = mContext.getSharedPreferences("Acitivity",
					Activity.MODE_PRIVATE);
		 username = share.getString("nickname", "");
		 options = new DisplayImageOptions.Builder().cacheInMemory(true).imageScaleType(ImageScaleType.EXACTLY)
					.cacheOnDisc(true).bitmapConfig(Bitmap.Config.RGB_565).build();
		 
		 if(share.getString("headpic", "").contains("http")){
			 	headpicture= share.getString("headpic", "");
			 //	LogDetect.send(LogDetect.DataType.noType,"微信头像 head:",headpicture);
		}else{
				headpicture= "http://120.27.98.128:9112/img/imgheadpic/"+share.getString("headpic", "");
			//	LogDetect.send(LogDetect.DataType.noType,"本地 head:",headpicture);
			
			} 
		 if(logo.contains("http")){
			 this.logo = logo;
			 //	LogDetect.send(LogDetect.DataType.noType,"微信头像 logo:",logo);
		 }
	}

	public void setList(List<Msg> list) {
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHodler hodler;
		Bitmap tmpBmp;
		if (convertView == null) {
			hodler = new ViewHodler();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.chat_lv_item_01160, null);
			hodler.rl_chat = (RelativeLayout) convertView
					.findViewById(R.id.rl_chat);// 聊天布局
			// 接收的消息
			hodler.fromIcon = (CircleImageView) convertView
					.findViewById(R.id.chatfrom_icon);// 他人头像
		//hodler.jishi = (TextView)convertView.findViewById(R.id.jishi);
			hodler.toIcon = (CircleImageView) convertView
					.findViewById(R.id.chatto_icon);// 自己头像
			hodler.fromContainer = (LinearLayout) convertView
					.findViewById(R.id.chart_from_container);
			hodler.fromText = (TextView) convertView
					.findViewById(R.id.chatfrom_content);// 文本
			hodler.fromImg = (ImageView) convertView
					.findViewById(R.id.chatfrom_img);// 图片
			/*hodler.fromLocation= (ImageView)
			convertView.findViewById(R.id.chatfrom_location);//位置
*/			hodler.progress_load = (ProgressBar) convertView
					.findViewById(R.id.progress_load);// ProgressBar
			//语音
			//hodler.yuyin1 = (TextView) convertView.findViewById(R.id.yuyin1);
			// 发送的消息
			hodler.toContainer = (RelativeLayout) convertView
					.findViewById(R.id.chart_to_container);
			//hodler.jishi1 = (TextView)convertView.findViewById(R.id.jishi1);
			hodler.toText = (TextView) convertView
					.findViewById(R.id.chatto_content);// 文本
			hodler.toImg = (ImageView) convertView
					.findViewById(R.id.chatto_img);// 图片
		/*	hodler.toLocation= (ImageView)
			convertView.findViewById(R.id.chatto_location);//位置
*/			// 时间
			hodler.time = (TextView) convertView.findViewById(R.id.chat_time);
			//hodler.yuyin = (TextView) convertView.findViewById(R.id.yuyin);
			convertView.setTag(hodler);
		} else {
			hodler = (ViewHodler) convertView.getTag();
		}

		final Msg msg = list.get(position);

		if (msg.getIsComing() == 0) {// 收到消息 from显示
			hodler.toContainer.setVisibility(View.GONE);// 隐藏右侧布局
			hodler.fromContainer.setVisibility(View.VISIBLE);
			hodler.time.setText(msg.getDate().substring(0, 16));
			Log.v("TT","msg_type: "+msg.getType());
			if (msg.getType().equals(Const.MSG_TYPE_TEXT)) {// 文本类型
				//hodler.yuyin1.setVisibility(View.GONE);
				//hodler.jishi.setVisibility(View.GONE);
				hodler.fromText.setVisibility(View.VISIBLE);// 文本
				hodler.fromImg.setVisibility(View.GONE);// 图片
			//	hodler.fromLocation.setVisibility(View.GONE);//位置
				hodler.progress_load.setVisibility(View.GONE);
				SpannableStringBuilder sb = ExpressionUtil.prase(mContext,
						hodler.fromText, msg.getContent());// 对内容做处理
				hodler.fromText.setText(sb);
				//Linkify.addLinks(hodler.fromText, Linkify.ALL);// 增加文本链接类型
			}else if (msg.getType().equals(Const.MSG_TYPE_DAZHAOHU)) {// 文本类型
				//hodler.yuyin1.setVisibility(View.GONE);
				//hodler.jishi.setVisibility(View.GONE);
				hodler.fromText.setVisibility(View.VISIBLE);// 文本
				hodler.fromImg.setVisibility(View.GONE);// 图片
				//	hodler.fromLocation.setVisibility(View.GONE);//位置
				hodler.progress_load.setVisibility(View.GONE);
//				SpannableStringBuilder sb = ExpressionUtil.prase(mContext,
//						hodler.fromText, msg.getContent());// 对内容做处理
//				hodler.fromText.setText(sb);

				Bitmap b = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.avchat_left_type_video);
				ImageSpan imgSpan = new ImageSpan(mContext, b);
				SpannableString spanString = new SpannableString("icon"+" 视频请求");
				/*要让图片替代指定的文字就要用ImageSpan
				*第2和第3个参数表示从哪里开始替换到哪里替换结束（start和end）
				*/
				spanString.setSpan(imgSpan, 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				hodler.fromText.setText(spanString);
				hodler.fromText.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Message msg=new Message();
						msg.what=1000;
						mHandler.sendMessage(msg);
					}
				});



				//Linkify.addLinks(hodler.fromText, Linkify.ALL);// 增加文本链接类型
			}else if (msg.getType().equals(Const.MSG_TYPE_URL)) {// 文本类型
			//	hodler.yuyin1.setVisibility(View.GONE);
				hodler.fromText.setVisibility(View.VISIBLE);// 文本
				hodler.fromImg.setVisibility(View.GONE);// 图片
			//	hodler.jishi.setVisibility(View.GONE);
			//	hodler.fromLocation.setVisibility(View.GONE);//位置
				// SpannableStringBuilder sb = ExpressionUtil.prase(mContext,
				// hodler.toText, msg.getContent());// 对内容做处理
				final String[] a = msg.getContent().split("\\$");
				//hodler.toText.setTextColor(R.color.green);
				//hodler.toText.setText(Html.fromHtml("<a href=\"\">"+a[2]+"("+a[3]+")</a> "));
				hodler.fromText.setText(a[2]);
				/*hodler.toText.setOnClickListener(new View.OnClickListener() {
					public void onClick(View view) {
						if(a[0].equals("0")){
			        		int good_id = Integer.valueOf(a[1]);
			     			String mode = "checkonly";
			     			SharedPreferences share=mContext.getSharedPreferences("Acitivity",Activity.MODE_PRIVATE);
			     			String userid=share.getString("userid","");
			     			UsersThread b = new UsersThread(mode, mContext,
			     					good_id, StringUtil.lon, StringUtil.lat,userid);
			     			
			     			Thread thread = new Thread(b.runnable);
			     			thread.start();
			        	 }else{
			        		 int good_id = Integer.valueOf(a[1]);
								Intent intent = new Intent(mContext, Acti_InfoActivity.class);
								Bundle bundle = new Bundle();
								bundle.putString("goodid", String.valueOf(good_id));
								intent.putExtras(bundle);
								mContext.startActivity(intent);
			        	 }
					}
				});*/
			} else if (msg.getType().equals(Const.MSG_TYPE_ORDER)) {// 文本类型
				//hodler.yuyin1.setVisibility(View.GONE);
				hodler.fromText.setVisibility(View.VISIBLE);// 文本
				hodler.fromImg.setVisibility(View.GONE);// 图片
				//hodler.jishi.setVisibility(View.GONE);
			//	hodler.fromLocation.setVisibility(View.GONE);//位置
				hodler.progress_load.setVisibility(View.GONE);
				SpannableStringBuilder sb = ExpressionUtil.prase(mContext,
						hodler.fromText, msg.getContent());// 对内容做处理
				hodler.fromText.setText(sb);
				//Linkify.addLinks(hodler.fromText, Linkify.ALL);// 增加文本链接类型

			} else if (msg.getType().equals(Const.MSG_TYPE_IMG)) {// 图片类型
				//hodler.yuyin1.setVisibility(View.GONE);
				hodler.fromText.setVisibility(View.GONE);// 文本
				//hodler.jishi.setVisibility(View.GONE);
				hodler.fromImg.setVisibility(View.VISIBLE);// 图片
			//	hodler.fromLocation.setVisibility(View.GONE);// 位置
				hodler.progress_load.setVisibility(View.GONE);
				//hodler.fromImg.setImageBitmap(FileInOut.getLoacalBitmap(msg.getContent()));
				SoftReference<Bitmap> sbmp = imageCaches.get(msg.getContent());
				if(sbmp != null) {
					tmpBmp = sbmp.get();
				} else {
					tmpBmp = null;
				}
				if(tmpBmp != null) {
					hodler.fromImg.setImageBitmap(tmpBmp);
				}else {
					new AsyncTaskShowBitmap(msg.getContent(),hodler.fromImg,imageCaches,new AsyncTaskCallback() {
	
						@Override
						public void setBitmap(ImageView v, Bitmap bmp) {
							// TODO Auto-generated method stub
							v.setImageBitmap(bmp);
						}
						
					}).execute();
				}
			} /*else if (msg.getType().equals(Const.MSG_TYPE_LOCATION)) {// 位置类型
				//hodler.yuyin1.setVisibility(View.GONE);
				//hodler.jishi.setVisibility(View.GONE);
				hodler.fromText.setVisibility(View.GONE);// 文本
				hodler.fromImg.setVisibility(View.GONE);// 图片
			//	hodler.fromLocation.setVisibility(View.VISIBLE);// 位置
				hodler.progress_load.setVisibility(View.GONE);
				String lat = msg.getContent();// 经纬度
				if (TextUtils.isEmpty(lat)) {
					lat = "116.404,39.915";// 北京
				}
				getImg(hodler.fromLocation, Const.LOCATION_URL_S + lat
						+ "&markers=|" + lat + "&markerStyles=l,A,0xFF0000");// 加载网络图片
			}*/ else if(msg.getType().equals(Const.MSG_TYPE_VOICE)) {/*	// 语音类型
				hodler.fromText.setVisibility(View.GONE);
				//hodler.yuyin1.setVisibility(View.VISIBLE);// 文本
				hodler.fromImg.setVisibility(View.GONE);// 图片
				hodler.fromLocation.setVisibility(View.GONE);//位置
				hodler.progress_load.setVisibility(View.GONE);
				final SpannableStringBuilder sb = ExpressionUtil.prase(mContext,
						hodler.yuyin1, msg.getContent());// 对内容做处理
				
				LogDetect.send(LogDetect.DataType.specialType,"01160 Timing 接收:",sb.toString().split(":")[1]);
				Timing = (Integer.parseInt(sb.toString().split(":")[1]))/1000;
				//hodler.jishi.setVisibility(View.VISIBLE);
				//hodler.jishi.setText(Timing + "' ");
			
				
				  StringBuffer sf = new StringBuffer();
					for(int i=0;i<=Timing;i++){
						sf.append(" "); 
					}
				RelativeLayout.LayoutParams lp = (android.widget.RelativeLayout.LayoutParams) hodler.fromText.getLayoutParams();
			   lp.width = (int) (mMinItemWidth + (mMaxItemWidth / 60f)* Timing);
			 hodler.fromText.setLayoutParams(lp);
			  // hodler.yuyin1.setText(sf);
				 RelativeLayout.LayoutParams lp= (RelativeLayout.LayoutParams) hodler.fromText.getLayoutParams();
			        lp.width=(int) (Timing*10);
			        hodler.fromText.setLayoutParams(lp);
			        hodler.jishi.setVisibility(View.VISIBLE);
			        hodler.jishi.setText(Timing +"");
				
				ImageSpan imgSpan = new ImageSpan(mContext, R.drawable.yuyin);
				SpannableString spannableString = new SpannableString("012345");
				spannableString.setSpan(imgSpan, 1, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				hodler.fromText.setText(spannableString);
				
				
			   
			   
				hodler.yuyin1.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View view) {
						//适配器通讯activity
						mItemOnClickListener.itemOnClickListener(view);
						final CountDownTimer c = new CountDownTimer(Timing*1000, 1000) {
							
							@Override
							public void onTick(long l) {
								index=(int)(l+3)%3;  
			                    Message msg=new Message();  
			                    msg.what=index;  
			                    mHandler.sendMessage(msg);  
							}
							
							@Override
							public void onFinish() {
								 //当播放完时  
			                    Message msg=new Message();  
			                    msg.what=5;  
			                    mHandler.sendMessage(msg);  
			                   
							}
						}.start();
						Toast.makeText(mContext,"语音from: "+sb,Toast.LENGTH_SHORT).show();
						AudioRecoderUtils.playMusicFile(sb.toString().split(":")[0]);
					}
				});
			*/}
			if(!logo.equals("000000")){
				ImageLoader.getInstance().displayImage(
						logo.trim(),
						hodler.fromIcon, options);
				//hodler.fromIcon.setTag(logo);
				//TODO	点头像跳转
				if(!list.get(position).getFromUser().equals("40") && "0".equals(com.net.yuesejiaoyou.classroot.interface4.util.Util.iszhubo)){
					hodler.fromIcon.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
													//跳转到他的用户资料页面
							Intent intent = new Intent(activity,UserActivity.class);
							intent.putExtra("id", list.get(position).getFromUser());
							activity.startActivity(intent);
						}
					});
				}
				
				/*if (mDownloader == null) {
					mDownloader = new ImageDownloader();
				}*/
				Log.v("TT","headpic: "+logo);
				
				
				
				/*if(logo.contains("thirdwx.qlogo.cn")){
					 LogDetect.send(LogDetect.DataType.noType,"微信头像:",logo);
					mDownloader.imageUrlDownload(logo, hodler.fromIcon, "/yanbin",activity, new OnImageDownload() {
						@Override
						public void onDownloadSucc(Bitmap bitmap,
								String c_url,ImageView mimageView) {

							mimageView.setImageBitmap(bitmap);
						}
					});
				}else{
					 LogDetect.send(LogDetect.DataType.noType,"本地头像:",logo);
					
					mDownloader.imageDownload(logo, hodler.fromIcon, "/yanbin",activity, new OnImageDownload() {
						@Override
						public void onDownloadSucc(Bitmap bitmap,
								String c_url,ImageView mimageView) {

							mimageView.setImageBitmap(bitmap);
						}
					});
				} */
				
			/*	mDownloader.imageDownload(logo, hodler.fromIcon, "/yanbin",activity, new OnImageDownload() {
					@Override
					public void onDownloadSucc(Bitmap bitmap,
							String c_url,ImageView mimageView) {
						ImageView imageView = (ImageView) mListView.findViewWithTag(c_url);
						if (imageView != null) {
							imageView.setImageBitmap(bitmap);
							imageView.setTag("");
						} 
					}
				});*/
			}
			
		} else {// 发送消息 to显示（目前发送消息只能发送文本类型，后期将会增加其它类型）
			hodler.toContainer.setVisibility(View.VISIBLE);
			hodler.fromContainer.setVisibility(View.GONE);
			hodler.time.setText(msg.getDate().substring(0, 16));
			if (msg.getType().equals(Const.MSG_TYPE_TEXT)) {// 文本类型
			//	hodler.jishi1.setVisibility(View.GONE);
			//	hodler.yuyin.setVisibility(View.GONE);
				hodler.toText.setVisibility(View.VISIBLE);// 文本
				hodler.toImg.setVisibility(View.GONE);// 图片
			//	hodler.toLocation.setVisibility(View.GONE);//位置
				SpannableStringBuilder sb = ExpressionUtil.prase(mContext,
						hodler.toText, msg.getContent());// 对内容做处理
				hodler.toText.setText(sb);
				//Linkify.addLinks(hodler.toText, Linkify.ALL);
			}else if (msg.getType().equals(Const.MSG_TYPE_DAZHAOHU)) {// 文本类型
				//hodler.yuyin1.setVisibility(View.GONE);
				//hodler.jishi.setVisibility(View.GONE);
				hodler.toText.setVisibility(View.VISIBLE);// 文本
				hodler.toImg.setVisibility(View.GONE);// 图片
				//	hodler.fromLocation.setVisibility(View.GONE);//位置
				//hodler.progress_load.setVisibility(View.GONE);
//				SpannableStringBuilder sb = ExpressionUtil.prase(mContext,
//						hodler.fromText, msg.getContent());// 对内容做处理
//				hodler.fromText.setText(sb);

				Bitmap b = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.avchat_right_type_video);
				ImageSpan imgSpan = new ImageSpan(mContext, b);
				SpannableString spanString = new SpannableString("icon"+" 视频请求");
				/*要让图片替代指定的文字就要用ImageSpan
				*第2和第3个参数表示从哪里开始替换到哪里替换结束（start和end）
				*/
				spanString.setSpan(imgSpan, 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				hodler.toText.setText(spanString);

				//Linkify.addLinks(hodler.fromText, Linkify.ALL);// 增加文本链接类型
			}else if (msg.getType().equals(Const.MSG_TYPE_URL)) {// 文本类型
				hodler.toText.setVisibility(View.VISIBLE);// 文本
				hodler.toImg.setVisibility(View.GONE);// 图片
			//	hodler.toLocation.setVisibility(View.GONE);// 位置
			//	hodler.jishi1.setVisibility(View.GONE);
			//	hodler.yuyin.setVisibility(View.GONE);
				// SpannableStringBuilder sb = ExpressionUtil.prase(mContext,
				// hodler.toText, msg.getContent());// 对内容做处理
				final String[] a = msg.getContent().split("\\$");
				//hodler.toText.setTextColor(R.color.green);
				//hodler.toText.setText(Html.fromHtml("<a href=\"\">"+a[2]+"("+a[3]+")</a> "));
				hodler.toText.setText(a[2]);
				/*hodler.toText.setOnClickListener(new View.OnClickListener() {
					public void onClick(View view) {
						if(a[0].equals("0")){
			        		int good_id = Integer.valueOf(a[1]);
			     			String mode = "checkonly";
			     			SharedPreferences share=mContext.getSharedPreferences("Acitivity",Activity.MODE_PRIVATE);
			     			String userid=share.getString("userid","");
			     			UsersThread b = new UsersThread(mode, mContext,
			     					good_id, StringUtil.lon, StringUtil.lat,userid);
			     			
			     			Thread thread = new Thread(b.runnable);
			     			thread.start();
			        	 }else{
			        		 int good_id = Integer.valueOf(a[1]);
								Intent intent = new Intent(mContext, Acti_InfoActivity.class);
								Bundle bundle = new Bundle();
								bundle.putString("goodid", String.valueOf(good_id));
								intent.putExtras(bundle);
								mContext.startActivity(intent);
			        	 }
					}
				});*/
			} else if (msg.getType().equals(Const.MSG_TYPE_IMG)) {// 图片类型
				hodler.toText.setVisibility(View.GONE);// 文本
				hodler.toImg.setVisibility(View.VISIBLE);// 图片
			//	hodler.yuyin.setVisibility(View.GONE);
			//	hodler.jishi1.setVisibility(View.GONE);
			//	hodler.toLocation.setVisibility(View.GONE);// 位置
				//hodler.toImg.setImageBitmap(FileInOut.getLoacalBitmap(msg.getContent()));
				SoftReference<Bitmap> sbmp = imageCaches.get(msg.getContent());
				if(sbmp != null) {
					tmpBmp = sbmp.get();
				} else {
					tmpBmp = null;
				}
				if(tmpBmp != null) {
					hodler.toImg.setImageBitmap(tmpBmp);
					//Log.v("asmack","cache: "+msg.getContent());
				}else {
					new AsyncTaskShowBitmap(msg.getContent(),hodler.toImg,imageCaches,new AsyncTaskCallback() {
	
						@Override
						public void setBitmap(ImageView v, Bitmap bmp) {
							// TODO Auto-generated method stub
							v.setImageBitmap(bmp);
						}
						
					}).execute();
					//Log.v("asmack"," new: "+msg.getContent());
				}
			} /*else if (msg.getType().equals(Const.MSG_TYPE_LOCATION)) {// 位置类型
				hodler.toText.setVisibility(View.GONE);// 文本
				hodler.toImg.setVisibility(View.GONE);// 图片
				hodler.toLocation.setVisibility(View.VISIBLE);// 位置
			//	hodler.yuyin.setVisibility(View.GONE);
			//	hodler.jishi1.setVisibility(View.GONE);
				String lat = msg.getContent();// 经纬度
				if (TextUtils.isEmpty(lat)) {
					lat = "116.404,39.915";// 北京
				}
				getImg(hodler.toLocation, Const.LOCATION_URL_S + lat
						+ "&markers=|" + lat + "&markerStyles=l,A,0xFF0000");// 加载网络图片
			} */else if (msg.getType().equals(Const.MSG_TYPE_VOICE)) {/*// 语音类型
				hodler.toText.setVisibility(View.GONE);// 文本
				hodler.toImg.setVisibility(View.GONE);// 图片
				hodler.toLocation.setVisibility(View.GONE);//位置
				hodler.yuyin.setVisibility(View.VISIBLE);
				final SpannableStringBuilder sb = ExpressionUtil.prase(mContext,
						hodler.yuyin, msg.getContent());// 对内容做处理
				
				ImageSpan imgSpan = new ImageSpan(mContext, R.drawable.yuyin);
				SpannableString spannableString = new SpannableString("012345");
				spannableString.setSpan(imgSpan, 1, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				hodler.toText.setText(spannableString);
				
				LogDetect.send(LogDetect.DataType.specialType,"01160 Timing 发送:",sb.toString().split(":")[1]);
				Timing = (Integer.parseInt(sb.toString().split(":")[1]))/1000;
				hodler.jishi1.setVisibility(View.VISIBLE);
				hodler.jishi1.setText(Timing +"' ");
					
				
				StringBuffer sf = new StringBuffer();
				for(int i=0;i<=Timing;i++){
					sf.insert(0 , "  " ); 
				}
				
				StringBuffer st = sf;
				RelativeLayout.LayoutParams lp = (android.widget.RelativeLayout.LayoutParams) hodler.toText.getLayoutParams();
			   lp.width = (int) (mMinItemWidth + (mMaxItemWidth / 60f)* Timing);
			   hodler.toText.setLayoutParams(lp);
			   hodler.yuyin.setText(st);
			RelativeLayout.LayoutParams lp= (RelativeLayout.LayoutParams) hodler.toText.getLayoutParams();
			        lp.width=(int) (Timing*30);
			        hodler.toText.setLayoutParams(lp);
			        hodler.jishi1.setVisibility(View.VISIBLE);
			        hodler.jishi1.setText(Timing +"");
				hodler.yuyin.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View view) {
						mItemOnClickListener.itemOnClickListener(view);
						final CountDownTimer c = new CountDownTimer(Timing*1000, 1000) {
							
							@Override
							public void onTick(long l) {
								index=(int)(l+1)%3;  
			                    Message msg=new Message();  
			                    msg.what=index;  
			                    mHandler.sendMessage(msg);  
							}
							
							@Override
							public void onFinish() {
								 //当播放完时  
			                    Message msg=new Message();  
			                    msg.what=1;  
			                    mHandler.sendMessage(msg);  
			                   
							}
						}.start();
						
						Toast.makeText(mContext,"语音to: "+sb,Toast.LENGTH_SHORT).show();
						AudioRecoderUtils.playMusicFile(sb.toString().split(":")[0]);
						//AudioRecoderUtils.playMusicFile("/storage/emulated/0/2018-02-05-13-22-39.amr");
					}
				});
			*/}
			
			ImageLoader.getInstance().displayImage(
					headpicture.trim(),
					hodler.toIcon, options);
			//hodler.toIcon.setTag(headpicture);
			/*if (mDownloader == null) {
				mDownloader = new ImageDownloader();
			}
			
			if(headpicture.contains("thirdwx.qlogo.cn")){
				 LogDetect.send(LogDetect.DataType.noType,"微信头像:",headpicture);
				mDownloader.imageUrlDownload(headpicture, hodler.toIcon, "/yanbin",activity, new OnImageDownload() {
					@Override
					public void onDownloadSucc(Bitmap bitmap,
							String c_url,ImageView mimageView) {

						mimageView.setImageBitmap(bitmap);
					}
				});
			}else{
				 LogDetect.send(LogDetect.DataType.noType,"本地头像:",headpicture);
				
				mDownloader.imageDownload(headpicture, hodler.toIcon, "/yanbin",activity, new OnImageDownload() {
					@Override
					public void onDownloadSucc(Bitmap bitmap,
							String c_url,ImageView mimageView) {

						mimageView.setImageBitmap(bitmap);
					}
				});
			} */
			
		/*	mDownloader.imageDownload(headpicture, hodler.toIcon, "/yanbin",activity, new OnImageDownload() {
				@Override
				public void onDownloadSucc(Bitmap bitmap,
						String c_url,ImageView mimageView) {
					ImageView imageView = (ImageView) mListView.findViewWithTag(c_url);
					if (imageView != null) {
						imageView.setImageBitmap(bitmap);
						imageView.setTag("");
					} 
				}
			});*/
		}

		
		
		// 文本点击
		//hodler.fromText.setOnClickListener(new onClick(position, msg));
		//hodler.fromText.setOnLongClickListener(new onLongCilck(position));

		//hodler.toText.setOnClickListener(new onClick(position, msg));
		//hodler.toText.setOnLongClickListener(new onLongCilck(position));
		// 图片点击
		hodler.fromImg.setOnClickListener(new onClick(position, msg));
		//hodler.fromImg.setOnLongClickListener(new onLongCilck(position));
		hodler.toImg.setOnClickListener(new onClick(position, msg));
		//hodler.toImg.setOnLongClickListener(new onLongCilck(position));
		// 位置
	//	hodler.fromLocation.setOnClickListener(new onClick(position,msg));
		//hodler.fromLocation.setOnLongClickListener(new onLongCilck(position));
	//	hodler.toLocation.setOnClickListener(new onClick(position,msg));
		//hodler.toLocation.setOnLongClickListener(new onLongCilck(position));
		
		return convertView;
	}

	void getImg(ImageView iv, String path) {
		if (!TextUtils.isEmpty(path)) {
			finalImageLoader.display(iv, path);
		} else {
			iv.setImageResource(R.drawable.ic_launcher);
		}
	}

	class ViewHodler {
		RelativeLayout rl_chat;
		CircleImageView fromIcon, toIcon;
		ImageView fromImg, /*fromLocation,*/ toImg/*, toLocation*/;
		TextView fromText, toText, time/*jishi,jishi1,yuyin,yuyin1*/;
		LinearLayout fromContainer;
		RelativeLayout toContainer;
		ProgressBar progress_load;
	}

	/**
	 * 屏蔽listitem的所有事件
	 * */
	@Override
	public boolean areAllItemsEnabled() {
		return false;
	}

	@Override
	public boolean isEnabled(int position) {
		return false;
	}

	/**
	 * 点击监听
	 * 
	 * @author 白玉梁
	 *
	 */
	class onClick implements OnClickListener {
		int position;
		Msg msg;

		public onClick(int position, Msg msg) {
			this.position = position;
			this.msg = msg;
		}

		@Override
		public void onClick(View arg0) {
			String content = msg.getContent();
			if (msg.getType().equals(Const.MSG_TYPE_IMG)) {// 图片
				
				  Intent intentImg=new Intent(mContext, ImgPageActivity.class);
				  intentImg.putExtra("url", content);
				  mContext.startActivity(intentImg);
				
			} /*else if (msg.getType().equals(Const.MSG_TYPE_LOCATION)) {// 位置
				  
				  if (TextUtils.isEmpty(content)) {
					  content = "116.404,39.915";// 北京
				  }
				  WindowManager wm = (WindowManager) mContext
			                .getSystemService(Context.WINDOW_SERVICE);

			      double width = wm.getDefaultDisplay().getWidth();
			      double height = wm.getDefaultDisplay().getHeight();
			      width=(1024/height)*width;
			      height=1024;
			      String url="http://api.map.baidu.com/staticimage?width="+width+"&height="+height+"&zoom=17&center=";
				  Intent intentMap=new Intent(mContext, ImgPageActivity.class);
				  intentMap.putExtra("url",url+content+"&markers=|"+content+"&markerStyles=l,A,0xFF0000");
				  mContext.startActivity(intentMap);
				 
			} else {

			}*/
		}

	}

	/**
	 * 长按监听
	 * 
	 * @author 白玉梁
	 *
	 */
	class onLongCilck implements OnLongClickListener {
		int position;

		public onLongCilck(int position) {
			this.position = position;
		}

		@Override
		public boolean onLongClick(View arg0) {
			// Intent intent=new Intent(Const.ACTION_MSG_OPER);
			// intent.putExtra("type", 1);
			// intent.putExtra("position", position);
			// mContext.sendBroadcast(intent);
			// ToastUtil.showShortToast(mContext, "长按");
			return true;
		}
	}

	class AsyncTaskShowBitmap extends AsyncTask<String,Void,Bitmap>{

		private String filePath;
		private ImageView iView;
		private Map<String,SoftReference<Bitmap>> imgCache;
		private AsyncTaskCallback callbackFunc;
		
		public AsyncTaskShowBitmap(String path,ImageView view,Map<String, SoftReference<Bitmap>> cache,AsyncTaskCallback callback) {
			this.filePath = path;
			this.iView = view;
			this.imgCache = cache;
			this.callbackFunc = callback;
		}
		@Override
		protected Bitmap doInBackground(String... params) {
			// TODO Auto-generated method stub
			FileInputStream fis;
			Bitmap bmp = null;
			try {
				fis = new FileInputStream(filePath);
				bmp = BitmapFactory.decodeStream(fis);
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				bmp = bmp.createScaledBitmap(bmp, 100, 100, true);
				if(bmp != null) {
					imgCache.put(filePath, new SoftReference<Bitmap>(bmp.createScaledBitmap(bmp, 100, 100, true)));
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
            return bmp; 
			//return FileInOut.getLoacalBitmap(filePath);
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			// TODO Auto-generated method stub
			if(callbackFunc != null) {
				callbackFunc.setBitmap(iView, result);
			}
			super.onPostExecute(result);			
		}
		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}
		
		
	}
	
	interface AsyncTaskCallback {
		public abstract void setBitmap(ImageView v, Bitmap bmp);
	}
	
	
	 private ItemOnClickListener mItemOnClickListener;  
	  
	    public void setmItemOnClickListener(ItemOnClickListener listener){  
	        this.mItemOnClickListener = listener;  
	    }  
	  
	    public interface ItemOnClickListener{  
	        public void itemOnClickListener(View view);  
	    }  	
	
/*	 *//** 
     * 停止 
     *//*  
     private void stopTimer(){    
            if (mTimer != null) {    
                mTimer.cancel();    
                mTimer = null;    
            }    
        
            if (mTimerTask != null) {    
                mTimerTask.cancel();    
                mTimerTask = null;    
            }     
  
        }   */
	
	    
	    
}
