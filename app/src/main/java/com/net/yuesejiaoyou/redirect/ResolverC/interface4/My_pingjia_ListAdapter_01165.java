package com.net.yuesejiaoyou.redirect.ResolverC.interface4;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap.Config;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/*import com.net.yuesejiaoyou.R;
import com.example.vliao.getset.Bills_01165;
import com.example.vliao.interface4.LogDetect;*/
import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.Bills_01165;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.List;

public class My_pingjia_ListAdapter_01165 extends BaseAdapter {
	
	private Context context;
	private List<Bills_01165> articles;
	private DisplayImageOptions options;
	private Activity activity;
	private ListView listview;
	private HolderView holderView = null;

	private int m;
	protected Handler requesetHandler;
	public My_pingjia_ListAdapter_01165(Context context, ListView mlistview,
                                        Activity mactivity, List<Bills_01165> articles, Handler requesetHandler) {
        LogDetect.send(LogDetect.DataType.specialType, "My_V__ListAdapter适配器: ","My_V__ListAdapter_01165()");
        
		this.context = context;
		this.activity = mactivity;
		this.listview = mlistview;
		this.articles = articles;
		this.requesetHandler=requesetHandler;
		options = new DisplayImageOptions.Builder().cacheInMemory(true)
				.cacheOnDisc(true).bitmapConfig(Config.RGB_565).build();
        
	}
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return articles.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return articles.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView==null){
			//在listView内初始化
			holderView = new HolderView();
			convertView= LayoutInflater.from(context).inflate(R.layout.my_pingjia_yanshi_01165,null);
			//Details d = new Details();
			holderView.touxiang = (ImageView)convertView.findViewById(R.id.touxiang);
			holderView.love_img = (ImageView)convertView.findViewById(R.id.love_img);
			holderView.no_love_img = (ImageView)convertView.findViewById(R.id.no_love_img);
			
			holderView.time = (TextView)convertView.findViewById(R.id.time);
			holderView.nickname = (TextView)convertView.findViewById(R.id.nickname);
			holderView.duration = (TextView)convertView.findViewById(R.id.duration);
			holderView.love = (TextView)convertView.findViewById(R.id.love);
			holderView.no_love = (TextView)convertView.findViewById(R.id.no_love);
			holderView.lable1 = (TextView)convertView.findViewById(R.id.lable1);
			holderView.lable2 = (TextView)convertView.findViewById(R.id.lable2);
			holderView.lable3 = (TextView)convertView.findViewById(R.id.lable3);
			holderView.lable4 = (TextView)convertView.findViewById(R.id.lable4);
			convertView.setTag(holderView);
			//return convertView;
		}else{
			//使用这句内存消耗小
			if(convertView.getTag() instanceof HolderView){
				holderView=(HolderView) convertView.getTag();
			}
		}
		//在listView里赋值
		//ImageLoader.getInstance().displayImage(articles.get(position).getGood_img(), holderView.view_photo, options);
		//头像
		//ImageLoader.getInstance().displayImage(articles.get(position).getTouxiang(), holderView.touxiang, options);
		//昵称
		holderView.nickname.setText(articles.get(position).getNickname());
		//日期+时间
		holderView.time.setText(articles.get(position).getTime());		
		LogDetect.send(LogDetect.DataType.specialType, "1165_My_pingjia_time: ",articles.get(position).getTime());		 
		//通话时长
		holderView.duration.setText(articles.get(position).getBooking_status());
		//是否喜欢
		LogDetect.send(LogDetect.DataType.specialType, "1165_My_pingjia_is_like: ",articles.get(position).getType());		 

		if(articles.get(position).getType().equals("dislike")){
			holderView.love_img.setVisibility(View.GONE);
			holderView.love.setVisibility(View.GONE);
			holderView.no_love_img.setVisibility(View.VISIBLE);
			holderView.no_love.setVisibility(View.VISIBLE);
		}else{
			holderView.love_img.setVisibility(View.VISIBLE);
			holderView.love.setVisibility(View.VISIBLE);
			holderView.no_love_img.setVisibility(View.GONE);
			holderView.no_love.setVisibility(View.GONE);
		}
		//articles.get(position).getLabel(
		String str = articles.get(position).getLabel();
		LogDetect.send(LogDetect.DataType.specialType, "1165_My_pingjia_is_标签: ",str);
		String[] strs = str.split("卍");
		LogDetect.send(LogDetect.DataType.specialType, "1165_My_pingjia_is_标签1: ",strs[0]);
//		holderView.lable1.setText(strs[1]);

		holderView.lable1.setVisibility(View.GONE);
		holderView.lable2.setVisibility(View.GONE);
		holderView.lable3.setVisibility(View.GONE);
		holderView.lable4.setVisibility(View.GONE);
		for (int m=0;m<strs.length;m++){
            if(m==0){
				holderView.lable1.setVisibility(View.VISIBLE);
				String[] label1 = strs[m].split("@");
				LogDetect.send(LogDetect.DataType.specialType, "1165_My_pingjia_is_字体1: ",label1[0]);
				LogDetect.send(LogDetect.DataType.specialType, "1165_My_pingjia_is_颜色1: ",label1[1]);
				holderView.lable1.setText(label1[0]);

//			holderView.lable1.setBackgroundColor(Color.parseColor(label1[1]));
				GradientDrawable drawable = (GradientDrawable) holderView.lable1.getBackground();
				drawable.setColor(Color.parseColor(label1[1]));
			}else if(m==1){
				holderView.lable2.setVisibility(View.VISIBLE);
				String[] label2 = strs[m].split("@");
				LogDetect.send(LogDetect.DataType.specialType, "1165_My_pingjia_is_字体2: ",label2[0]);
				LogDetect.send(LogDetect.DataType.specialType, "1165_My_pingjia_is_颜色2: ",label2[1]);
				holderView.lable2.setText(label2[0]);
//////////////////////////////////////////////////////////////////////////////////
//			holderView.lable2.setBackgroundColor(Color.parseColor(label2[1]));
				GradientDrawable drawable2 = (GradientDrawable) holderView.lable2.getBackground();
				drawable2.setColor(Color.parseColor(label2[1]));
			}else if(m==2){
				holderView.lable3.setVisibility(View.VISIBLE);
				String[] label2 = strs[m].split("@");
				LogDetect.send(LogDetect.DataType.specialType, "1165_My_pingjia_is_字体2: ",label2[0]);
				LogDetect.send(LogDetect.DataType.specialType, "1165_My_pingjia_is_颜色2: ",label2[1]);
				holderView.lable3.setText(label2[0]);
//////////////////////////////////////////////////////////////////////////////////
//			holderView.lable2.setBackgroundColor(Color.parseColor(label2[1]));
				GradientDrawable drawable2 = (GradientDrawable) holderView.lable3.getBackground();
				drawable2.setColor(Color.parseColor(label2[1]));
			}else if(m==3){
				holderView.lable4.setVisibility(View.VISIBLE);
				String[] label2 = strs[m].split("@");
				LogDetect.send(LogDetect.DataType.specialType, "1165_My_pingjia_is_字体2: ",label2[0]);
				LogDetect.send(LogDetect.DataType.specialType, "1165_My_pingjia_is_颜色2: ",label2[1]);
				holderView.lable4.setText(label2[0]);
//////////////////////////////////////////////////////////////////////////////////
//			holderView.lable2.setBackgroundColor(Color.parseColor(label2[1]));
				GradientDrawable drawable2 = (GradientDrawable) holderView.lable4.getBackground();
				drawable2.setColor(Color.parseColor(label2[1]));
			}

		}

		return convertView;
		
	}
	class HolderView {	//样式列表信息：昵称，在线状态，日期，通话是否成功。
		private ImageView touxiang,love_img,no_love_img;
		private TextView nickname,time,duration,love,no_love,lable1,lable2,lable3,lable4;
	}
}
