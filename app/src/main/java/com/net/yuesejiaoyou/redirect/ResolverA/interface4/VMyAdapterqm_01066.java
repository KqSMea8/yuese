package com.net.yuesejiaoyou.redirect.ResolverA.interface4;


import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.net.yuesejiaoyou.R;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;


/**
 * Created by lijianchang@yy.com on 2017/4/12.
 */

public class VMyAdapterqm_01066 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<String> datas;
    private Context context;
    private int normalType = 0;
    private int footType = 1;
    private int pos = 0;
    private boolean hasMore = true;
    private boolean fadeTips = false;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private String[] articles;
    private DisplayImageOptions options;
    private Typeface tf;
    private String userid;
    private View.OnClickListener clickListener;
    public VMyAdapterqm_01066(List<String> datas, Context context, boolean hasMore, String[] articles, String userid, View.OnClickListener listener) {
        this.datas = datas;
        this.context = context;
        this.hasMore = hasMore;
        this.articles = articles;
        this.userid=userid;
        this.clickListener = listener;
        options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisc(true).bitmapConfig(Bitmap.Config.RGB_565).build();

        //得到AssetManager
        AssetManager mgr=context.getAssets();

        //根据路径得到Typeface
        tf= Typeface.createFromAsset(mgr, "fonts/arialbd.ttf");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //if (viewType == normalType) {
            return new NormalHolder(LayoutInflater.from(context).inflate(R.layout.imageonly, null));
        //}
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof NormalHolder) {


            ((NormalHolder) holder).xyitem.setOnClickListener(clickListener);

//            if (articles.get(position).getIdentify_check()==0){
//                ((NormalHolder) holder).smrz.setVisibility(View.GONE);
//            }else{
//                ((NormalHolder) holder).smrz.setVisibility(View.VISIBLE);
//            }


            ImageLoader.getInstance().displayImage(
                    articles[position], ((NormalHolder) holder).photo,
                    options);
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
//                        return 2;
//                    }else{
//                        return 1;
//                    }
//
//                }
//            });
//        }
//    }



    @Override
    public int getItemCount() {
        return articles.length;
    }

    public void updateList(int i) {
        notifyItemChanged(i);
        //notifyDataSetChanged();
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
            photo = (ImageView) convertView.findViewById(R.id.image_view_comment);
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

            return normalType;

    }
}
