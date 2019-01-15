package com.net.yuesejiaoyou.redirect.ResolverC.interface4;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap.Config;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/*import com.net.yuesejiaoyou.R;
import com.example.vliao.getset.Bills_01165;
import com.example.vliao.interface4.LogDetect;
import com.example.vliao.uiface.Userinfo;*/
import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.Bills_01165;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class Yuyue_ListAdapter_01165 extends BaseAdapter {
	
	private Context context;
	private List<Bills_01165> articles;
	private DisplayImageOptions options;
	private Activity activity;
	private ListView listview;
	private HolderView holderView = null;
	private int m;
	protected Handler requesetHandler;
	public Yuyue_ListAdapter_01165(Context context, ListView mlistview,
                                   Activity mactivity, List<Bills_01165> articles, Handler requesetHandler) {
        LogDetect.send(LogDetect.DataType.specialType, "yuyue_ListAdapter适配器: ","Bills_ListAdapter_01165()");

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
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			//在listView内初始化
			holderView = new HolderView();
			convertView= LayoutInflater.from(context).inflate(R.layout.yuyue_yangshi_01165,null);
			holderView.touxiang = (ImageView)convertView.findViewById(R.id.touxiang);
			holderView.nickname = (TextView)convertView.findViewById(R.id.nickname);
			holderView.line = (TextView)convertView.findViewById(R.id.line);
			holderView.no_line = (TextView)convertView.findViewById(R.id.no_line);
			holderView.time = (TextView)convertView.findViewById(R.id.time);
			holderView.status = (TextView)convertView.findViewById(R.id.status);
			holderView.zhubo = (RelativeLayout)convertView.findViewById(R.id.zhubo);
			holderView.shuzhi = (TextView)convertView.findViewById(R.id.shuzhi);
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
		ImageLoader.getInstance().displayImage(articles.get(position).getTouxiang(), holderView.touxiang, options);
		holderView.nickname.setText(articles.get(position).getNickname());
		holderView.time.setText(articles.get(position).getTime());
		//主播在线状态
		if(articles.get(position).getOnline().equals("1")){
			holderView.line.setVisibility(View.VISIBLE);
			holderView.no_line.setVisibility(View.GONE);
		}else{
			holderView.line.setVisibility(View.GONE);
			holderView.no_line.setVisibility(View.VISIBLE);
		}
		//用户与主播的通话情况
        LogDetect.send(LogDetect.DataType.specialType, "用户与主播的通话情况: ", articles.get(position).getBooking_status());

		/*if(articles.get(position).getBooking_status().equals("未接通")){
			holderView.status.setText("Reservation Failure");
		}else{
			holderView.status.setText("Callback Success");

		}*/
		holderView.zhubo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                LogDetect.send(LogDetect.DataType.specialType, "点击跳转至主播个人页面: ","----------------------");
				/**************************
				 * 跳转到userinfo，暂时注释
				 *************************/
				/*
            	Intent intent = new Intent();
                intent.setClass(context, Userinfo.class);
				Bundle bundle = new Bundle();
				bundle.putString("id", ""+articles.get(position).getUser_id());
				intent.putExtras(bundle);
                context.startActivity(intent);*/
            }
//							
        });
		holderView.shuzhi.setText(articles.get(position).getIntimacy());
		LogDetect.send(LogDetect.DataType.specialType, "Yuyue_01165_shuzhi: ",articles.get(position).getIntimacy());

return convertView;
	}
	class HolderView {	//样式列表信息：昵称，在线状态，日期，通话是否成功。
		private ImageView touxiang;
		private TextView nickname,time,status,line,no_line,shuzhi;
		private RelativeLayout zhubo;
		
		
	}
}
