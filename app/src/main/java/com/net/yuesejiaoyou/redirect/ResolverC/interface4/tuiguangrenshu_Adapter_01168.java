package com.net.yuesejiaoyou.redirect.ResolverC.interface4;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap.Config;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.Vliao1_01168;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class tuiguangrenshu_Adapter_01168 extends BaseAdapter{
	private Context context;
	private HolderView holderView = null;
	private List<Vliao1_01168> articles;
	private DisplayImageOptions options;
	private Activity activity;
	private ListView listview;

	public tuiguangrenshu_Adapter_01168(Context context, ListView mlistview,
                                        List<Vliao1_01168> articles) {
		this.context = context;
		this.listview = mlistview;
		this.articles = articles;
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
	public View getView(int position, View convertView, ViewGroup parent) {
		LogDetect.send(LogDetect.DataType.specialType, "tuiguang_Adapter_01152:", "适配器开始++++++++++");
		
					if(convertView==null){
						holderView = new HolderView();
						convertView=LayoutInflater.from(context).inflate(R.layout.distributepersonitem,null);
						holderView.nicheng = (TextView) convertView.findViewById(R.id.nicheng);
						holderView.touxiang = (RoundImageView) convertView.findViewById(R.id.touxiang);
			            convertView.setTag(holderView);
					}else{
						if(convertView.getTag() instanceof HolderView){
							holderView=(HolderView) convertView.getTag();
						}else{
							holderView = new HolderView();
							convertView=LayoutInflater.from(context).inflate(R.layout.distributepersonitem,null);
							holderView.nicheng = (TextView) convertView.findViewById(R.id.nicheng);
							holderView.touxiang = (RoundImageView) convertView.findViewById(R.id.touxiang);
				            convertView.setTag(holderView);
						}
					}
					
					
					if(articles.get(0).getPhoto().contains("http")){
						ImageLoader.getInstance().displayImage(articles.get(0).getPhoto(),holderView.touxiang,options);
					}
					holderView.nicheng.setText(articles.get(position).getNickname());
					//LogDetect.send(LogDetect.DataType.specialType, "tuiguangrenshu_Adapter_01152:", articles.get(position).getNickname());
					

		return convertView;
	}

	 class HolderView {
		public RoundImageView touxiang;
		public TextView nicheng;
	}
}
