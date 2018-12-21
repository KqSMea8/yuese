package com.net.yuesejiaoyou.redirect.ResolverA.interface4;

import java.util.List;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.net.yuesejiaoyou.R;


import com.net.yuesejiaoyou.redirect.ResolverA.getset.ZBYuyueJB_01160;
import com.net.yuesejiaoyou.redirect.ResolverA.uiface.SearchDv_01160;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

/**
 * 	通知列表
 */
@SuppressLint("InflateParams")
public class SearchDvAdapter_01160 extends RecyclerView.Adapter{

	onItemClickListener mOnItemClickListener;

	public interface onItemClickListener
	{
		void onItemClick(View view, int position);
		//void onItemLongClick(View view ,int position);
	}

	public void setOnItemClickLitsener(onItemClickListener mOnItemClickLitsener)
	{
		mOnItemClickListener= mOnItemClickLitsener;
	}
	private int normalType = 0;
	private int footType = 1;
	private SearchDv_01160 ab;
	private RecyclerView listview;
	private List<ZBYuyueJB_01160> articles;
	private int m;
	private String key;
	private Handler requestHandler;
	private DisplayImageOptions options;

	
	public SearchDvAdapter_01160(SearchDv_01160 ab, RecyclerView listview, List<ZBYuyueJB_01160> articles, Handler requestHandler,String key){
		this.ab = ab;
		this.listview = listview;
		this.articles = articles;
		this.requestHandler = requestHandler;
		this.key = key;
		options = new DisplayImageOptions.Builder().cacheInMemory(true).imageScaleType(ImageScaleType.EXACTLY).showImageOnLoading(R.drawable.nim_avatar_default)
				.cacheOnDisc(true).bitmapConfig(Bitmap.Config.RGB_565).build();
	}


	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_dv_item_01160, parent, false);
		NormalHolder vh = new NormalHolder(view);
		//将创建的View注册点击事件

		return vh;
			//return new SearchDvAdapter_01160.NormalHolder(LayoutInflater.from(ab).inflate(R.layout.search_dv_item_01160, null));

	}

	@Override
	public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
		if (holder instanceof NormalHolder) {

			holder.itemView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					int pos =holder.getLayoutPosition();
					mOnItemClickListener.onItemClick(holder.itemView,pos);

				}
			});


			((NormalHolder) holder).content.setText(articles.get(position).getIntimacy());
			((NormalHolder) holder).user_name.setText(KeywordUtil.matcherSearchTitle(Color.parseColor("#FF2F77"),articles.get(position).getName(),key));

			ImageLoader.getInstance().displayImage(articles.get(position).getPhoto(),((NormalHolder) holder).user_head,options);

		}
	}

	class NormalHolder extends RecyclerView.ViewHolder {


		private RoundImageView user_head;
		private TextView user_name;
		private TextView content;

		public NormalHolder(View convertView) {
			super(convertView);

			user_head = (RoundImageView) convertView.findViewById(R.id.user_head);
			user_name= (TextView) convertView.findViewById(R.id.user_name);
			content = (TextView) convertView.findViewById(R.id.content);

		}
	}

	@Override
	public int getItemCount() {
		return articles.size();
	}




}
