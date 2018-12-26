package com.net.yuesejiaoyou.redirect.ResolverA.interface4;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import jp.wasabeef.glide.transformations.BlurTransformation;

import com.net.yuesejiaoyou.redirect.ResolverD.interface4.GlideApp;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.redirect.ResolverA.core.UsersManage_01066A;

import com.net.yuesejiaoyou.redirect.ResolverB.getset.Videoinfo;//调用B区页面
import com.net.yuesejiaoyou.redirect.ResolverB.uiface.VideoPlay_01066;


/**
 * Created by lijianchang@yy.com on 2017/4/12.
 */

public class VideoMyAdapter_01066 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<String> datas;
    private Context context;
    private int normalType = 0;
    private int footType = 1;
    private int pos = 0;
    private boolean hasMore = true;
    private boolean fadeTips = false;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private List<Videoinfo> articles;
    private DisplayImageOptions options;
    private Typeface tf;
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

    public VideoMyAdapter_01066(List<String> datas, Context context, boolean hasMore, List<Videoinfo> articles) {
        this.datas = datas;
        this.context = context;
        this.hasMore = hasMore;
        this.articles = articles;
        options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisc(true).bitmapConfig(Bitmap.Config.RGB_565).build();

        //得到AssetManager
        AssetManager mgr=context.getAssets();

        //根据路径得到Typeface
        tf= Typeface.createFromAsset(mgr, "fonts/arialbd.ttf");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == normalType) {
            return new NormalHolder(LayoutInflater.from(context).inflate(R.layout.disovery_list_item, null));
        } else {
            return new FootHolder(LayoutInflater.from(context).inflate(R.layout.footview_01066, null));
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                int pos =holder.getLayoutPosition();
                mOnItemClickListener.onItemLongClick(holder.itemView,pos);
                return true;
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos =holder.getLayoutPosition();
                mOnItemClickListener.onItemClick(holder.itemView,pos);
            }
        });

        if (holder instanceof NormalHolder) {


            /////////////////////////////
            LogDetect.send(LogDetect.DataType.specialType,"A区个人详情页点击视频 转2调 B区","00000");
            //////////////////////////////
            ((NormalHolder) holder).xyitem.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {

                    Intent intent = new Intent(context, VideoPlay_01066.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", position);
                    bundle.putSerializable("vlist",(Serializable)articles);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
            /////////////////

            if (articles.get(position).getIspay()==0){
                ((NormalHolder) holder).iv_lock.setVisibility(View.GONE);


                ImageLoader.getInstance().displayImage(
                        articles.get(position).getVideo_photo(), ((NormalHolder) holder).iv_cover,
                        options);
            }else if (articles.get(position).getIspay()==1){
                ((NormalHolder) holder).iv_lock.setVisibility(View.VISIBLE);
                ((NormalHolder) holder).iv_lock.setBackgroundResource(R.drawable.videopay_lock);

                GlideApp.with(context)
                        .load(articles.get(position).getVideo_photo())
                        .placeholder(R.drawable.moren)
                        .error(R.drawable.moren)
                        .optionalTransform(new BlurTransformation(context,6,4))  // “23”：设置模糊度(在0.0到25.0之间)，默认”25";"4":图片缩放比例,默认“1”。
                        .into(((NormalHolder) holder).iv_cover);

            }else{
                ((NormalHolder) holder).iv_lock.setVisibility(View.VISIBLE);
                ((NormalHolder) holder).iv_lock.setBackgroundResource(R.drawable.videopay_unlock);
                ImageLoader.getInstance().displayImage(
                        articles.get(position).getVideo_photo(), ((NormalHolder) holder).iv_cover,
                        options);
//                String imgPath=articles.get(position).getVideo_photo();
//                String tag= (String)((NormalHolder) holder).iv_cover.getTag();
//                if (!imgPath.equals(tag)){
//                    ((NormalHolder) holder).iv_cover.setTag(imgPath);
//                    //设置图片
//                    ImageLoader.getInstance().displayImage(
//                            articles.get(position).getVideo_photo(), ((NormalHolder) holder).iv_cover,
//                            options);
//                }
            }

//            if (articles.get(position).getIszan()==0){
//                ((NormalHolder) holder).iszan.setImageResource(R.drawable.video_like);
//            }else{
//                ((NormalHolder) holder).iszan.setImageResource(R.drawable.video_like_on);
//            }
            if (articles.get(position).getStatus()==0){
                ((NormalHolder) holder).shenhe.setVisibility(View.VISIBLE);
            }else{
                ((NormalHolder) holder).shenhe.setVisibility(View.GONE);
            }

            ImageLoader.getInstance().displayImage(
                    articles.get(position).getPhoto(), ((NormalHolder) holder).iv_headIcon,
                    options);
            ((NormalHolder) holder).title_text.setTypeface(tf);
            ((NormalHolder) holder).title_text.setText(articles.get(position).getExplain());
            ((NormalHolder) holder).username.setTypeface(tf);
            ((NormalHolder) holder).username.setText(articles.get(position).getNickname());
            ((NormalHolder) holder).follow_tv.setTypeface(tf);
            ((NormalHolder) holder).follow_tv.setText(articles.get(position).getLike_num());


        } else {
            ((FootHolder) holder).tips.setVisibility(View.VISIBLE);
            if (fadeTips){
                ((FootHolder) holder).tips.setTypeface(tf);
                ((FootHolder) holder).tips.setText("——我是有底线的——");
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
                    if (position==getItemCount()-1){
                        return 2;
                    }else{
                        return 1;
                    }

                }
            });
        }
    }



    @Override
    public int getItemCount() {
        return articles.size() + 1;
    }

    public void updateList(int i) {

        notifyItemChanged(i);
        //notifyDataSetChanged();
    }

    private class GetDataTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {

            UsersManage_01066A goodsmanage = new UsersManage_01066A();
            String page="";
            Date date = new Date();
            String[] paramsMap={Util.userid,articles.get(pos).getId()+""};
            page = goodsmanage.like(paramsMap);
            LogDetect.send(LogDetect.DataType.specialType, "Faxian_ActAdapter_01150 ——（）返回数据 page: ", page);
            return page;
        }

        @Override
        protected void onPostExecute(String result) {
            if(result.contains("OK")){
                updateList(pos);
            }
            super.onPostExecute(result);
        }
    }


    class NormalHolder extends RecyclerView.ViewHolder {
        private TextView username,title_text,follow_tv;
        private ImageView iv_cover,iv_lock,iv_headIcon,iszan;
        private RelativeLayout xyitem,shenhe;
        public NormalHolder(View convertView) {
            super(convertView);
            xyitem= (RelativeLayout) convertView.findViewById(R.id.item_v_list_layout);
            iv_cover = (ImageView) convertView.findViewById(R.id.iv_cover);
            iv_lock= (ImageView) convertView.findViewById(R.id.iv_lock);
            iszan= (ImageView) convertView.findViewById(R.id.iszan);
            iv_headIcon= (ImageView) convertView.findViewById(R.id.iv_headIcon);
            title_text= (TextView) convertView.findViewById(R.id.title_text);
            username = (TextView) convertView.findViewById(R.id.tv_v_name);
            follow_tv= (TextView) convertView.findViewById(R.id.follow_tv);
            shenhe = (RelativeLayout) convertView.findViewById(R.id.findpage_list_item_examine_rl);
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
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return footType;
        } else {
            return normalType;
        }
    }

    public void removeItem(int pos){
        articles.remove(pos);
        notifyItemRemoved(pos);
    }

}
