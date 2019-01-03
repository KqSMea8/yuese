package com.net.yuesejiaoyou.redirect.ResolverA.interface4;


import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.redirect.ResolverA.getset.User_data;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.activity.UserActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;


/**
 * Created by lijianchang@yy.com on 2017/4/12.
 */

public class VMyAdapter_01066 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<String> datas;
    private Context context;
    private int normalType = 0;
    private int footType = 1;
    private int pos = 0;
    private int headType = 2;
    private View mHeaderView=null;
    private boolean hasMore = true;
    private boolean fadeTips = false;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private List<User_data> articles;
    private DisplayImageOptions options;
    private Typeface tf;
    public VMyAdapter_01066(List<String> datas, Context context, boolean hasMore, List<User_data> articles) {
        this.datas = datas;
        this.context = context;
        this.hasMore = hasMore;
        this.articles = articles;
        options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisc(true).bitmapConfig(Bitmap.Config.RGB_565).build();//.displayer(new RoundedBitmapDisplayer(20))
        //得到AssetManager
        AssetManager mgr=context.getAssets();

        //根据路径得到Typeface
        tf= Typeface.createFromAsset(mgr, "fonts/arialbd.ttf");
    }
    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == normalType) {
            return new NormalHolder(LayoutInflater.from(context).inflate(R.layout.listvie_item_01066, null));
        } else if (viewType == headType){
            return new HeadHolder(mHeaderView);
        }else {
            return new FootHolder(LayoutInflater.from(context).inflate(R.layout.footview_01066, null));
        }
    }
    protected boolean isScrolling = false;
    public void setScrolling(boolean scrolling) {
        isScrolling = scrolling;
    }
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof HeadHolder) return;
        if (holder instanceof NormalHolder) {
            final int pos;
            if (mHeaderView!=null){
                pos=position-1;
                LogDetect.send(LogDetect.DataType.specialType, "mHeaderView ——（11）返回数据 : ", pos);
            }else{
                pos=position;
                LogDetect.send(LogDetect.DataType.specialType, "mHeaderView ——（）返回数据 : ", pos);
            }

            ((NormalHolder) holder).xyitem.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Intent intent = new Intent(context, UserActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("id", ""+articles.get(pos).getId());
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });

//            if (articles.get(position).getIdentify_check()==0){
//                ((NormalHolder) holder).smrz.setVisibility(View.GONE);
//            }else{
//                ((NormalHolder) holder).smrz.setVisibility(View.VISIBLE);
//            }

            //((NormalHolder) holder).tv_status.setTypeface(tf);
            if (articles.get(pos).getOnline()==1){
                ((NormalHolder) holder).staus.setBackgroundResource(R.drawable.zt_zaixian);
                ((NormalHolder) holder).tv_status.setText("在线");

            }else if (articles.get(pos).getOnline()==2){
                ((NormalHolder) holder).staus.setBackgroundResource(R.drawable.zt_huoyue);
                ((NormalHolder) holder).tv_status.setText("在聊");
            }else if (articles.get(pos).getOnline()==3){
            	((NormalHolder) holder).staus.setBackgroundResource(R.drawable.zt_wurao);
                ((NormalHolder) holder).tv_status.setText("勿扰");
            }else {
            	((NormalHolder) holder).staus.setBackgroundResource(R.drawable.zt_lixian);
                ((NormalHolder) holder).tv_status.setText("离线");
            }

            ((NormalHolder) holder).rb_home_leave.setRating((float) articles.get(pos).getStar());


            //((NormalHolder) holder).username.setTypeface(tf);
            ((NormalHolder) holder).username.setText(articles.get(pos).getNickname());
            //((NormalHolder) holder).tv_price.setTypeface(tf);
            ((NormalHolder) holder).tv_price.setText(articles.get(pos).getPrice());
            //((NormalHolder) holder).note.setTypeface(tf);
            ((NormalHolder) holder).note.setText(articles.get(pos).getSignature());
            LogDetect.send(LogDetect.DataType.specialType, "mHeaderView ——（）返回数据 : ", articles.get(pos).getPictures().split(",")[0]);
            ((NormalHolder) holder).photo.setImageResource(R.drawable.moren);
            //if(!isScrolling){
                ImageLoader.getInstance().displayImage(
                        articles.get(pos).getPictures().split(",")[0], ((NormalHolder) holder).photo,
                        options);
            //}

        }else {
            ((FootHolder) holder).tips.setVisibility(View.VISIBLE);
            if (fadeTips){
                //((FootHolder) holder).tips.setTypeface(tf);
                ((FootHolder) holder).tips.setText("——我也是有底线的——");
            }

        }
    }



    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        if (mHeaderView==null){
            return articles.size() + 1;
        }else{
            return articles.size() + 2;
        }

    }

    public void updateList(int i) {
        articles.get(i).setIslike(1);
        notifyItemChanged(i);
        //notifyDataSetChanged();
    }
    class HeadHolder extends RecyclerView.ViewHolder {
        private TextView tips;

        public HeadHolder(View itemView) {
            super(itemView);
            //tips = (TextView) itemView.findViewById(R.id.tips);
        }
    }


    class NormalHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private TextView username,note,tv_price,tv_status;
        private ImageView photo,staus,smrz;
        private RelativeLayout xyitem;
        RatingBar rb_home_leave;
        public NormalHolder(View convertView) {
            super(convertView);
            xyitem= (RelativeLayout) convertView.findViewById(R.id.item_onev_list_layout);
            photo = (ImageView) convertView.findViewById(R.id.sd_home_avatar_img);
            //smrz= (ImageView) convertView.findViewById(R.id.renzheng);
            staus= (ImageView) convertView.findViewById(R.id.iv_zt_img);
            tv_status= (TextView) convertView.findViewById(R.id.tv_home_status);
            username = (TextView) convertView.findViewById(R.id.tv_home_nickname);
            tv_price= (TextView) convertView.findViewById(R.id.tv_price);
            rb_home_leave= (RatingBar) convertView.findViewById(R.id.rb_home_leave);
            note= (TextView) convertView.findViewById(R.id.tv_topic);
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
        if (mHeaderView==null){
            if (position == getItemCount() - 1) {
                return footType;
            } else {
                return normalType;
            }
        }else{
            if (position==0){
                return headType;
            } else if (position == getItemCount() - 1) {
                return footType;
            } else {
                return normalType;
            }
        }

    }

	@Override
	public void onAttachedToRecyclerView(RecyclerView recyclerView) {
		super.onAttachedToRecyclerView(recyclerView);
		RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
		if(manager instanceof GridLayoutManager) {
			final GridLayoutManager gridManager = ((GridLayoutManager) manager);
			gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
				@Override
				public int getSpanSize(int position) {
					if (position==getItemCount()-1 || position==0){
						return 2;
					}else{
						return 1;
					}
				}
			});
		}
	}
}
