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
import com.net.yuesejiaoyou.redirect.ResolverC.getset.Member_01152;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.List;

public class tuiguang_Adapter_01152 extends BaseAdapter{
	private Context context;
	private HolderView holderView = null;
	private List<Member_01152> articles;
	private DisplayImageOptions options;
	private Activity activity;
	private ListView listview;

	public tuiguang_Adapter_01152(Context context, ListView mlistview,
                                  List<Member_01152> articles) {
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
						convertView=LayoutInflater.from(context).inflate(R.layout.tuiguang_list,null);
						holderView.nicheng = (TextView) convertView.findViewById(R.id.nicheng);
						holderView.pay = (TextView) convertView.findViewById(R.id.pay);
			            convertView.setTag(holderView);
					}else{
						if(convertView.getTag() instanceof HolderView){
							holderView=(HolderView) convertView.getTag();
						}else{
							holderView = new HolderView();
							convertView=LayoutInflater.from(context).inflate(R.layout.tuiguang_list,null);
							holderView.nicheng = (TextView) convertView.findViewById(R.id.nicheng);
							holderView.pay = (TextView) convertView.findViewById(R.id.pay);
				            convertView.setTag(holderView);
						}
					}
					
					holderView.nicheng.setText(articles.get(position).getNicheng()+":");
					LogDetect.send(LogDetect.DataType.specialType, "tuiguang_Adapter_01152:", articles.get(position).getNickname());
					holderView.pay.setText("Total consumption "+articles.get(position).getIncome()+" dollar");
					LogDetect.send(LogDetect.DataType.specialType, "tuiguang_Adapter_01152:", articles.get(position).getIncome());

		return convertView;
	}

	 class HolderView {
		public TextView pay;
		public TextView nicheng;
	}
}
