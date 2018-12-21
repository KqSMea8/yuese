package com.net.yuesejiaoyou.redirect.ResolverC.interface4;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/*import com.net.yuesejiaoyou.R;
import com.example.vliao.getset.Phone_01162;*/
import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.Phone_01162;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;
import java.util.regex.Pattern;


/**
 * Created by lijianchang@yy.com on 2017/4/12.
 */

public class MyAdapter_01162 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<String> datas;
    private Context context;
    private int normalType = 0;
    private int footType = 1;
    private int pos = 0;
    private boolean hasMore = true;
    private boolean fadeTips = false;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private List<Phone_01162> articles;
    private DisplayImageOptions options;
	onItemClickListener mOnItemClickListener;
	public interface onItemClickListener
	{
		void onItemClick(View view, int position);
		void onItemLongClick(View view, int position);
	}

	public void setOnItemClickLitsener(onItemClickListener mOnItemClickLitsener)
	{
		mOnItemClickListener= mOnItemClickLitsener;
	}

    public MyAdapter_01162(List<String> datas, Context context, boolean hasMore, List<Phone_01162> articles) {
        this.datas = datas;
        this.context = context;
        this.hasMore = hasMore;
        this.articles = articles;
        options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisc(true).bitmapConfig(Bitmap.Config.RGB_565).build();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == normalType) {
            return new NormalHolder(LayoutInflater.from(context).inflate(R.layout.activity_phonerecord_item_01162, null));
        } else {
            return new FootHolder(LayoutInflater.from(context).inflate(R.layout.footview_01066, null));
        }
    }
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
		holder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				int pos =holder.getLayoutPosition();
				mOnItemClickListener.onItemClick(holder.itemView,pos);

			}
		});
		holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View view) {
				int pos =holder.getLayoutPosition();
				mOnItemClickListener.onItemLongClick(holder.itemView,pos);
				return true;
			}
		});
        if (holder instanceof NormalHolder) {
			String tt[]=articles.get(position).getTime().split("\\s");
               ((NormalHolder) holder).time1.setText(tt[0]);
               ((NormalHolder) holder).time2.setText(tt[1]);
               ((NormalHolder) holder).user_name.setText(articles.get(position).getNickname());
               options=new DisplayImageOptions.Builder().cacheOnDisc(true).cacheInMemory(true).bitmapConfig(Bitmap.Config.RGB_565).build();
			   ImageLoader.getInstance().displayImage(articles.get(position).getPhoto(),((NormalHolder) holder).touxiang,options);
			   if(articles.get(position).getStatus().equals("未接通")){//Not connected---未接通
//                   ((NormalHolder) holder).callback.setVisibility(View.INVISIBLE);
//                   ((NormalHolder) holder).tips2.setImageResource(R.drawable.chat_in);
//                   ((NormalHolder) holder).tips.setImageResource(R.drawable.video_conversation_fail_icon);
                   ((NormalHolder) holder).callback.setVisibility(View.INVISIBLE);
                   ((NormalHolder) holder).tips2.setVisibility(View.VISIBLE);
                   ((NormalHolder) holder).tips2.setImageResource(R.drawable.chat_in);
                   ((NormalHolder) holder).tips.setImageResource(R.drawable.video_conversation_fail_icon);

                   ((NormalHolder) holder).content1.setTextColor(Color.parseColor("#CB372D"));
				   ((NormalHolder) holder).content1.setText(articles.get(position).getStatus());
			   }else{
                   ((NormalHolder) holder).tips2.setVisibility(View.INVISIBLE);
                   ((NormalHolder) holder).tips.setImageResource(R.drawable.dui);
                   ((NormalHolder) holder).callback.setVisibility(View.VISIBLE);
				   ((NormalHolder) holder).content1.setTextColor(Color.parseColor("#000000"));
				   LogDetect.send(LogDetect.DataType.specialType, "1165_price: ",articles.get(position).getPrice());
				   LogDetect.send(LogDetect.DataType.specialType, "1165_num: ",articles.get(position).getNum());
				   String sz = articles.get(position).getPrice();
				   String sz1= articles.get(position).getNum();
				   int a,b;
				   if(isNumeric(sz) && !sz.equals("")){
					   a= Integer.parseInt(articles.get(position).getPrice());
				   }else{
					   a = 0;
				   }
				   if(isNumeric(sz1) && !sz1.equals("")){
					   b= Integer.parseInt(articles.get(position).getNum());
				   }else{
					   b = 0;
				   }

					   ((NormalHolder) holder).content1.setText(articles.get(position).getStatus()+" ("+a*b+" 悦币)");
			   }
            ((NormalHolder) holder).content.setText("通话时间："+articles.get(position).getTtime());
        } else {
            ((FootHolder) holder).tips.setVisibility(View.VISIBLE);
            if (fadeTips){
                ((FootHolder) holder).tips.setText("——我也是有底线的——");
            }

        }
    }

//    @Override
//    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
//        super.onAttachedToRecyclerView(recyclerView);
//        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
//        if(manager instanceof GridLayoutManager) {
//            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
//            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//                @Override
//                public int getSpanSize(int position) {
//                    if (position==getItemCount()-1){
//                        return 1;
//                    }else{
//                        return 1;
//                    }
//                }
//            });
//        }
//    }


    @Override
    public int getItemCount() {
        return articles.size() + 1;
    }




    class NormalHolder extends RecyclerView.ViewHolder {
        private TextView user_name;
        private TextView time1,time2,content1,content;
        private ImageView touxiang,tips,tips2;
        private ImageView callback;


        public NormalHolder(View convertView) {
            super(convertView);
            touxiang= (ImageView) convertView.findViewById(R.id.user_head);
               time1= (TextView) convertView.findViewById(R.id.tv_time);
			 time2= (TextView) convertView.findViewById(R.id.tv_time1);
              tips= (ImageView) convertView.findViewById(R.id.tips);
              tips2= (ImageView) convertView.findViewById(R.id.tips2);
              user_name= (TextView) convertView.findViewById(R.id.user_name);
              callback= (ImageView) convertView.findViewById(R.id.callback);
              content= (TextView) convertView.findViewById(R.id.content);
            content1= (TextView) convertView.findViewById(R.id.content1);
        }
    }

    class FootHolder extends RecyclerView.ViewHolder {
        private TextView tips;
        public FootHolder(View itemView) {
            super(itemView);
            tips = (TextView) itemView.findViewById(R.id.tips);
        }
    }

    public boolean isFadeTips() {
        return fadeTips;
    }
    public void setFadeTips(boolean di) {
        fadeTips=di;
    }


    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return footType;
        } else {
            return normalType;
        }
    }
	public static boolean isNumeric(String str){
	    Pattern pattern = Pattern.compile("[0-9]*");
	    return pattern.matcher(str).matches();   
	}
}
