package com.net.yuesejiaoyou.classroot.interface4.openfire.interface4;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect.DataType;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.bean.Session;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.view.CircleImageView;


public class SessionAdapter extends BaseAdapter {
	private Context mContext;
	private List<Session> lists;
	private Activity a;
	private ImageDownloader mDownloader;
	private ListView lv_news;
	private DisplayImageOptions options;
	private static Calendar cal = Calendar.getInstance();
	@SuppressLint("SimpleDateFormat")
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	//private int hour = cal.get(Calendar.HOUR_OF_DAY);
	private String sxw;
	public SessionAdapter(Context context, List<Session> lists,Activity a,ListView lv_news) {
		this.mContext = context;
		this.lists = lists;
		this.a=a;
		this.lv_news=lv_news;
		 options = new DisplayImageOptions.Builder().cacheInMemory(true).imageScaleType(ImageScaleType.EXACTLY).showImageOnLoading(R.drawable.nim_avatar_default)
					.cacheOnDisc(true).bitmapConfig(Bitmap.Config.RGB_565).build();
	
		/* if (hour >= 6 && hour < 11) {
			 sxw = "上午";
			 } else if (hour >= 11 && hour < 13) {
				 sxw = "中午";
			 } else if (hour >= 13 && hour < 18) {
				 sxw = "下午";
			 } else{
				 sxw = "夜晚";
			 }*/
		
	}

	
	
	@Override
	public int getCount() {
		if (lists != null) {
			return lists.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		return lists.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final Holder holder;
		if (convertView == null) {
			convertView = View.inflate(mContext, R.layout.fragment_news_item_01160,null);
			holder = new Holder();
			holder.iv = (CircleImageView) convertView.findViewById(R.id.user_head);
			holder.tv_name = (TextView) convertView.findViewById(R.id.user_name);
			//未读消息
			holder.tv_noread= (TextView) convertView.findViewById(R.id.tv_noread);
			holder.tv_content = (TextView) convertView.findViewById(R.id.content);
			holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		
		Session session = lists.get(position);
		
		ImageLoader.getInstance().displayImage(
				session.getHeadpic().trim(),
				holder.iv, options);
		
	/*	if(session.getType().equals(Const.MSG_TYPE_ADD_FRIEND)){
			holder.tv_tips .setVisibility(View.VISIBLE);
			holder.tv_tips.setText("官方");
		}else{
			holder.tv_tips .setVisibility(View.GONE);
			holder.iv.setImageResource(R.drawable.ic_launcher);
		}

		if(!session.getHeadpic().equals("000000")){
			holder.iv.setTag("http://120.27.98.128:9000/img/imgheadpic/"+session.getHeadpic());
			
			if (mDownloader == null) {
				mDownloader = new ImageDownloader();
			}
			
			mDownloader.imageDownload("http://120.27.98.128:9000/img/imgheadpic/"+session.getHeadpic(), holder.iv, "/yanbin",a, new OnImageDownload() {
				@Override
				public void onDownloadSucc(Bitmap bitmap,
						String c_url,ImageView mimageView) {
					ImageView imageView = (ImageView) lv_news.findViewWithTag(c_url);
					if (imageView != null) {
						imageView.setImageBitmap(bitmap);
						imageView.setTag("");
					} 
				}
			});
		}*/
		
		//Log.v("PAOPAO","["+position+"]: "+session.getNotReadCount());
		if(!session.getNotReadCount().equals("0")){
			holder.tv_noread.setVisibility(View.VISIBLE);
			holder.tv_noread.setText(session.getNotReadCount());
		}else{
			holder.tv_noread.setVisibility(View.GONE);
		}
		  try {
			sxw = showDate(sdf.parse(session.getTime()),"yyyy-MM-dd HH:mm:ss");
		} catch (ParseException e) {
			LogDetect.send(DataType.specialType, "日期",sxw);
			LogDetect.send(DataType.specialType, "日期",session.getTime());
			e.printStackTrace();
		}
		holder.tv_name.setText(session.getName());
		holder.tv_content.setText(ExpressionUtil.prase(mContext, holder.tv_content, session.getContent()==null?"":session.getContent()));
		if(sxw.isEmpty()){
			holder.tv_time.setText(session.getTime().split("\\s+")[0]);
		}else{
			holder.tv_time.setText(sxw+"\n"+(session.getTime().split("\\s+")[1]).substring(0,5));
		}
	
		return convertView;
	}

	class Holder {
		CircleImageView iv;
		TextView tv_name/*,tv_tips*/;
		TextView tv_content;
		TextView tv_time;
		TextView tv_noread;
	}

   
	
	public static String showDate(Date date,String pattern){
        String  dateStr=format(date,pattern);
        String year = dateStr.substring(0,4);
        Long yearNum =Long.parseLong(year);
        int month = Integer.parseInt(dateStr.substring(5,7));  
        int day = Integer.parseInt(dateStr.substring(8,10));  
        //String hour = dateStr.substring(11,13);  
       // String minute = dateStr.substring(14,16);

        int hour1 = cal.get(Calendar.HOUR_OF_DAY);
        long addtime =date.getTime();
        long today = System.currentTimeMillis();//当前时间的毫秒数
        Date now = new Date(today);
        String  nowStr=format(now,pattern);
        int  nowDay = Integer.parseInt(nowStr.substring(8,10));
        String result="";
           long l=today-addtime;//当前时间与给定时间差的毫秒数
           long days=l/(24*60*60*1000);//这个时间相差的天数整数，大于1天为前天的时间了，小于24小时则为昨天和今天的时间
           long hours=(l/(60*60*1000)-days*24);//这个时间相差的减去天数的小时数
         //  long min=((l/(60*1000))-days*24*60-hours*60);//
          // long s=(l/1000-days*24*60*60-hours*60*60-min*60);
           if(days > 0){
                   if(days>0 && days<2){
                       result ="前天";
                   } else {
                       result = ""/*yearNum%100+"年"+month+"月 "+day+"日"*/;
                   }
           }else if(hours > 0 ) {
                    if(day!=nowDay){
                        result = "昨天";
                    }/*else{
                        result=hours+"小时 前";    
                    }*/
           }else if (hour1 >= 6 && hour1 < 11) {
        	   result = "上午";
  			 } else if (hour1 >= 11 && hour1 < 13) {
  				result = "中午";
  			 } else if (hour1 >= 13 && hour1 < 18) {
  				result = "下午";
  			 } else if(hour1 >=18 && hour1 >6){
  				result = "夜晚";
  			 } /*else if(min > 0){
                   if(min>0 && min<15){
                       result="刚刚";
                   } else {
                       result=min+"分 前";
                   }
           }else {
                    result=s+"秒 前";
           }*/
           return result;
	}
	/**
	 * 日期格式化
	 * @param date      需要格式化的日期
	 * @param pattern   时间格式，如：yyyy-MM-dd HH:mm:ss
	 * @return          返回格式化后的时间字符串
	 */
	@SuppressLint("SimpleDateFormat")
	public static String format(Date date, String pattern){
	    SimpleDateFormat sdf = new SimpleDateFormat(pattern);
	    return sdf.format(date);
	}
	
}
