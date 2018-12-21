package com.net.yuesejiaoyou.redirect.ResolverC.interface4;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/*import com.net.yuesejiaoyou.R;
import com.example.vliao.getset.ZBYuyueJB_01160;
import com.example.vliao.uiface.Yuyue_01165;
import com.lazysellers.sellers.infocenter.view.CircleImageView;*/
import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.ZBYuyueJB_01160;
import com.net.yuesejiaoyou.redirect.ResolverC.uiface.Yuyue_01165;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

/**
 * 	通知列表
 */
@SuppressLint("InflateParams")
public class ZByuyueAdapter_01160 extends BaseAdapter {

	private HolderView holderView = null;
	private Yuyue_01165 ab;
	private ListView listview;
	private List<ZBYuyueJB_01160> articles;
	private int m;
	private Handler handler;
	private DisplayImageOptions options;
	private int s;
	
	public ZByuyueAdapter_01160(Yuyue_01165 ab, ListView listview, List<ZBYuyueJB_01160> articles, Handler handler, int s){
		this.ab = ab;
		this.listview = listview;
		this.articles = articles;
		this.handler = handler;
		this.s = s;
		options = new DisplayImageOptions.Builder().cacheInMemory(true).imageScaleType(ImageScaleType.EXACTLY).showImageOnLoading(R.drawable.nim_avatar_default)
				.cacheOnDisc(true).bitmapConfig(Bitmap.Config.RGB_565).build();
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
	
	
	@SuppressWarnings("null")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent){
		if(convertView == null){
			holderView = new HolderView();
			convertView = View.inflate(ab, R.layout.zb_yuyue_item_01160,null);
			holderView.time = (TextView) convertView.findViewById(R.id.tv_time);
			/****************
			 * 暂时注释CircleImageView，用RoundImageView
			 ***************/
			/*holderView.photo = (CircleImageView) convertView.findViewById(R.id.user_head);*/
			holderView.photo = (RoundImageView) convertView.findViewById(R.id.user_head);
			holderView.is_online = (ImageView) convertView.findViewById(R.id.is_online);
			holderView.content = (TextView) convertView.findViewById(R.id.content);
			holderView.bohao = (ImageView) convertView.findViewById(R.id.bohao);
			holderView.name = (TextView) convertView.findViewById(R.id.user_name);
			convertView.setTag(holderView);
		}else{
			if(convertView.getTag() instanceof HolderView){
				holderView=(HolderView) convertView.getTag();
			}
		}
			ImageLoader.getInstance().displayImage(
					articles.get(position).getPhoto().trim(),
					holderView.photo, options);
			
			if(articles.get(position).getIs_online().equals("1")){
				holderView.is_online.setImageResource(R.drawable.online);
			}else{
				holderView.is_online.setImageResource(R.drawable.offline);
			}
			holderView.time.setText(articles.get(position).getTime().substring(5,16).split("\\s+")[0]+"\n"+articles.get(position).getTime().substring(5,16).split("\\s+")[1]);
			holderView.content.setText("亲密度:"+articles.get(position).getIntimacy());
			holderView.name.setText(articles.get(position).getName());
		/*	if(articles.get(position).getIs_chaoshi().equals("1")){
				holderView.bohao.setImageResource(R.drawable.reservation_icon_phone_gray);
			}else{*/
				holderView.bohao.setImageResource(R.drawable.reservation_icon_phone_red);
			/*	holderView.bohao.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if(articles.get(position).getIs_online().equals("1")){
							String mode1 = "zhubo_online";
														//用户id		 主播id		
							String[] paramsMap1 = {"",articles.get(position).getUser_id(),Util.userid};
							s = position;
							UsersThread_01158 a = new UsersThread_01158(mode1,paramsMap1,handler);
							Thread c = new Thread(a.runnable);
							c.start();	
						}else{
							Toast.makeText(ab, "Customer is not online", Toast.LENGTH_SHORT).show();
						}
						
					}
				});*/
			//}
			
		return convertView;
	}
	
	
	class HolderView {
		/****************
		 * 暂时注释CircleImageView，用RoundImageView
		 ***************/
		/*private CircleImageView photo;*/
		private RoundImageView photo;
		private ImageView is_online,bohao;
		private TextView name,content,time;
	}
}
