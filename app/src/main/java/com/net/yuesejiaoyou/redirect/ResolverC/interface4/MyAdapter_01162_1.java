package com.net.yuesejiaoyou.redirect.ResolverC.interface4;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/*import com.net.yuesejiaoyou.R;
import com.example.vliao.getset.Tag;*/
import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.Tag;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.List;


/**
 * Created by lijianchang@yy.com on 2017/4/12.
 */

public class MyAdapter_01162_1 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<String> datas;
    private Context context;
    private int normalType = 0;
    private int footType = 1;
    private int pos = 0;
    private boolean hasMore = true;
    private boolean fadeTips = false;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private List<Tag> articles;
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

	public MyAdapter_01162_1(List<String> datas, Context context, boolean hasMore, List<Tag> articles) {
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
            return new NormalHolder(LayoutInflater.from(context).inflate(R.layout.mytag_item_01162_1, null));
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
			((NormalHolder) holder).textView.setText(articles.get(position).getRecord());
		//LogDetect.send(LogDetect.DataType.basicType,"01162---json返回","准备进入适配器");

    }





    @Override
    public int getItemCount() {
        return articles.size();
    }

    public void updateList(int i) {
       /* articles.get(i).setIslike(1);*/
        notifyItemChanged(i);
        //notifyDataSetChanged();
    }



    class NormalHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private TextView username,note,age;
        private ImageView photo,staus,smrz;
        private RelativeLayout xyitem;
        public NormalHolder(View convertView) {
            super(convertView);
            textView= (TextView) convertView.findViewById(R.id.tag_item);

          /*  xyitem= (RelativeLayout) convertView.findViewById(R.id.userinfo);
            photo = (ImageView) convertView.findViewById(R.id.headpic);
            smrz= (ImageView) convertView.findViewById(R.id.renzheng);
            staus= (ImageView) convertView.findViewById(R.id.staus);
            username = (TextView) convertView.findViewById(R.id.nickname);
            age= (TextView) convertView.findViewById(R.id.age);
            note= (TextView) convertView.findViewById(R.id.notice);*/
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
        /*if (position == getItemCount() - 1) {
            return footType;
        } else {

        }*/
		return normalType;
    }
}
