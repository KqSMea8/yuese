package com.net.yuesejiaoyou.redirect.ResolverA.interface4;


import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.net.yuesejiaoyou.R;

import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.redirect.ResolverA.core.UsersManage_01066A;
import com.net.yuesejiaoyou.redirect.ResolverA.getset.User_data;
import com.net.yuesejiaoyou.redirect.ResolverC.uiface.Mytag_01162;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.Date;
import java.util.List;


/**
 * Created by lijianchang@yy.com on 2017/4/12.
 */

public class VMyAdapterzl_01066 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<String> datas;
    private Context context;
    private int normalType = 0;
    private int footType = 1;
    private int touType = 2;
    private int kongType = 3;
    private int pos = 0;
    private boolean hasMore = true;
    private boolean fadeTips = false;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private List<User_data> articles;
    private DisplayImageOptions options;
    private  User_data useinfo ;
    private Typeface tf;
    public VMyAdapterzl_01066(List<String> datas, Context context, boolean hasMore, List<User_data> articles,User_data useinfo) {
        this.datas = datas;
        this.context = context;
        this.hasMore = hasMore;
        this.articles = articles;
        this.useinfo=useinfo;
        if(useinfo == null) {
            return;
        }
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
            return new NormalHolder(LayoutInflater.from(context).inflate(R.layout.item_comment, null));
        }else if (viewType==touType){
            return new touHolder(LayoutInflater.from(context).inflate(R.layout.information_heard, null));
        }/*else if (viewType==kongType){
            return new touHolder(LayoutInflater.from(context).inflate(R.layout.kongview_01066, null));
        }*/else {
            return new FootHolder(LayoutInflater.from(context).inflate(R.layout.footview_01066, null));
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof NormalHolder) {


//            ((NormalHolder) holder).xyitem.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View view) {
//                    Toast.makeText(context,position+"++0",Toast.LENGTH_SHORT).show();
////                    Intent intent = new Intent(context, Ai8Activity.class);
////                    Bundle bundle = new Bundle();
////                    bundle.putString("id", ""+articles.get(position).getId());
////                    intent.putExtras(bundle);
////                    context.startActivity(intent);
//
//                }
//            });
//
////            if (articles.get(position).getIdentify_check()==0){
////                ((NormalHolder) holder).smrz.setVisibility(View.GONE);
////            }else{
////                ((NormalHolder) holder).smrz.setVisibility(View.VISIBLE);
////            }
//
//            ((NormalHolder) holder).tv_status.setTypeface(tf);
//            if (articles.get(position).getOnline()==1){
//                ((NormalHolder) holder).staus.setBackgroundResource(R.drawable.zt_zaixian);
//                ((NormalHolder) holder).tv_status.setText("On-line");
//
//            }else if (articles.get(position).getOnline()==2){
//                ((NormalHolder) holder).staus.setBackgroundResource(R.drawable.zt_zailiao);
//                ((NormalHolder) holder).tv_status.setText("In-the-chat");
//            }else if (articles.get(position).getOnline()==3){
//            	((NormalHolder) holder).staus.setBackgroundResource(R.drawable.zt_wurao);
//                ((NormalHolder) holder).tv_status.setText("Do't-disturb");
//            }else {
//            	((NormalHolder) holder).staus.setBackgroundResource(R.drawable.zt_lixian);
//                ((NormalHolder) holder).tv_status.setText("Off-line");
//            }
//
//            ((NormalHolder) holder).rb_home_leave.setRating((float) articles.get(position).getStar());
//
//
              ((NormalHolder) holder).username.setTypeface(tf);
              ((NormalHolder) holder).username.setText(articles.get(position-1).getNickname());

            if (articles.get(position-1).getYhping().equals("")){

            }else{
                String[] ziping =articles.get(position-1).getYhping().split("卍");
                for (int i=0;i<ziping.length;i++){
                    if (i==0){
                        String[] b=ziping[i].split("@");
                        ((NormalHolder) holder).pj1.setVisibility(View.VISIBLE);
                        GradientDrawable drawable = (GradientDrawable) ((NormalHolder) holder).pj1.getBackground();
                        //drawable.setStroke(2, getResources().getColor(R.color.mall_red_text));
                        //改变drawable的背景填充色
                        int color = Color.parseColor(b[1]);
                        drawable.setColor(color);
                        ((NormalHolder) holder).pj1.setText(b[0]);
                    }else if (i==1){
                        String[] b=ziping[i].split("@");
                        ((NormalHolder) holder).pj2.setVisibility(View.VISIBLE);
                        GradientDrawable drawable = (GradientDrawable) ((NormalHolder) holder).pj2.getBackground();
                        //drawable.setStroke(2, getResources().getColor(R.color.mall_red_text));
                        //改变drawable的背景填充色
                        int color = Color.parseColor(b[1]);
                        drawable.setColor(color);
                        ((NormalHolder) holder).pj2.setText(b[0]);
                    }/*else if(i==2){
                        String[] b=ziping[i].split("@");
                        ((NormalHolder) holder).pj3.setVisibility(View.VISIBLE);
                        GradientDrawable drawable = (GradientDrawable) ((NormalHolder) holder).pj3.getBackground();
                        //drawable.setStroke(2, getResources().getColor(R.color.mall_red_text));
                        //改变drawable的背景填充色
                        int color = Color.parseColor(b[1]);
                        drawable.setColor(color);
                        ((NormalHolder) holder).pj3.setText(b[0]);
                    }*/
                }
            }


//            ((NormalHolder) holder).tv_price.setTypeface(tf);
//            ((NormalHolder) holder).tv_price.setText(articles.get(position).getPrice());
//            ((NormalHolder) holder).note.setTypeface(tf);
//            ((NormalHolder) holder).note.setText(articles.get(position).getSignature());
            ImageLoader.getInstance().displayImage(
                    articles.get(position-1).getPhoto(), ((NormalHolder) holder).image_view_comment,
                    options);
        }else if (holder instanceof touHolder){

            if(useinfo == null) {
                return;
            }

            ((touHolder) holder).tv_topic.setText(useinfo.getSignature());

            ((touHolder) holder).relativeUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setClass(context, Mytag_01162.class);
                    Bundle b = new Bundle();
                    b.putString("zhubo_id",useinfo.getId()+"");
                    intent.putExtras(b);
                    context.startActivity(intent);
                }
            });
            if (useinfo.getImage_label().equals("")){


            }else{
                String[] ziping =useinfo.getImage_label().split("卍");
                LogDetect.send(LogDetect.DataType.basicType,"01162---标签数据",ziping);
                for (int i=0;i<ziping.length;i++){
                    if (i==0){
                        String[] b=ziping[i].split("@");
                        ((touHolder) holder).pj1.setVisibility(View.VISIBLE);
                        GradientDrawable drawable = (GradientDrawable) ((touHolder) holder).pj1.getBackground();
                        //drawable.setStroke(2, getResources().getColor(R.color.mall_red_text));
                        //改变drawable的背景填充色
                        int color = Color.parseColor(b[1]);
                        drawable.setColor(color);
                        ((touHolder) holder).pj1.setText(b[0]);
                    }else if (i==1){
                        String[] b=ziping[i].split("@");
                        ((touHolder) holder).pj2.setVisibility(View.VISIBLE);
                        GradientDrawable drawable = (GradientDrawable) ((touHolder) holder).pj2.getBackground();
                        //drawable.setStroke(2, getResources().getColor(R.color.mall_red_text));
                        //改变drawable的背景填充色
                        int color = Color.parseColor(b[1]);
                        drawable.setColor(color);
                        ((touHolder) holder).pj2.setText(b[0]);
                    }/*else if(i==2){
                        String[] b=ziping[i].split("@");
                        ((touHolder) holder).pj3.setVisibility(View.VISIBLE);
                        GradientDrawable drawable = (GradientDrawable) ((touHolder) holder).pj3.getBackground();
                        //drawable.setStroke(2, getResources().getColor(R.color.mall_red_text));
                        //改变drawable的背景填充色
                        int color = Color.parseColor(b[1]);
                        drawable.setColor(color);
                        ((touHolder) holder).pj3.setText(b[0]);
                    }*/
                }
            }
			LogDetect.send(LogDetect.DataType.basicType,"01162---标签数据",useinfo.getYhping());
            if (useinfo.getYhping().equals("")||useinfo.getYhping().equals("null")||useinfo.getYhping()==null){
                ((touHolder) holder).text_user_impression.setVisibility(View.VISIBLE);
            }else{
                String[] ziping =useinfo.getYhping().split("卍");
				LogDetect.send(LogDetect.DataType.basicType,"01162---标签数据",ziping);
                for (int i=0;i<ziping.length;i++){
                    if (i==0){
                        String[] b=ziping[i].split("@");
                        ((touHolder) holder).yhpj1.setVisibility(View.VISIBLE);
                        GradientDrawable drawable = (GradientDrawable) ((touHolder) holder).yhpj1.getBackground();
                        //drawable.setStroke(2, getResources().getColor(R.color.mall_red_text));
                        //改变drawable的背景填充色
                        int color = Color.parseColor(b[1]);
                        drawable.setColor(color);
                        ((touHolder) holder).yhpj1.setText(b[0]);
                    }else if (i==1){
                        String[] b=ziping[i].split("@");
                        ((touHolder) holder).yhpj2.setVisibility(View.VISIBLE);
                        GradientDrawable drawable = (GradientDrawable) ((touHolder) holder).yhpj2.getBackground();
                        //drawable.setStroke(2, getResources().getColor(R.color.mall_red_text));
                        //改变drawable的背景填充色
                        int color = Color.parseColor(b[1]);
                        drawable.setColor(color);
                        ((touHolder) holder).yhpj2.setText(b[0]);
                    }
                }
            }

            ((touHolder) holder).jieting.setText(useinfo.getRecieve_percent());
            //((touHolder) holder).tv_ussr_data_state.setText("Online");
			if (useinfo.getOnline()==1){
				((touHolder) holder).tv_ussr_data_state.setText("在线");
			}else if(useinfo.getOnline()==0){
				((touHolder) holder).tv_ussr_data_state.setText("离线");
			}
            ((touHolder) holder).shengao.setText(useinfo.getHeight());
            ((touHolder) holder).tizhong.setText(useinfo.getWeight());
            ((touHolder) holder).xingzuo.setText(useinfo.getConstellation());
            ((touHolder) holder).address.setText(useinfo.getAddress());

            ((touHolder) holder).text_view_evaluate_difference.setText(useinfo.getBad_num());
            //蓝M数
            ((touHolder) holder).text_view_evaluate_good.setText(useinfo.getGood_num());
        }/*else if (holder instanceof kongHolder){

        }*/else {
            ((FootHolder) holder).tips.setVisibility(View.VISIBLE);
            if (fadeTips){
                ((FootHolder) holder).tips.setTypeface(tf);
                ((FootHolder) holder).tips.setText("——我也是有底线的——");
            }
        }
    }
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
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
        return articles.size() + 2;
    }

    public void updateList(int i) {
        articles.get(i).setIslike(1);
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
        private TextView textView;
        private TextView username,note,tv_price,tv_status,pj1,pj2,pj3;
        private RoundImageView image_view_comment,staus,smrz;
        private RelativeLayout xyitem,relativeUser;
        RatingBar rb_home_leave;
        public NormalHolder(View convertView) {
            super(convertView);

            image_view_comment= (RoundImageView) convertView.findViewById(R.id.image_view_comment);
            username= (TextView) convertView.findViewById(R.id.tv_nick);
            pj1= (TextView) convertView.findViewById(R.id.pj1);//自评
            pj2= (TextView) convertView.findViewById(R.id.pj2);//自评
            pj3= (TextView) convertView.findViewById(R.id.pj3);//自评


//            xyitem= (RelativeLayout) convertView.findViewById(R.id.item_onev_list_layout);
//            photo = (ImageView) convertView.findViewById(R.id.sd_home_avatar_img);
//            //smrz= (ImageView) convertView.findViewById(R.id.renzheng);
//            staus= (ImageView) convertView.findViewById(R.id.iv_zt_img);
//            tv_status= (TextView) convertView.findViewById(R.id.tv_home_status);
//            username = (TextView) convertView.findViewById(R.id.tv_home_nickname);
//            tv_price= (TextView) convertView.findViewById(R.id.tv_price);
//            rb_home_leave= (RatingBar) convertView.findViewById(R.id.rb_home_leave);
//            note= (TextView) convertView.findViewById(R.id.tv_topic);
        }
    }


    class FootHolder extends RecyclerView.ViewHolder {
        private TextView tips;

        public FootHolder(View itemView) {
            super(itemView);
            tips = (TextView) itemView.findViewById(R.id.tips);
        }
    }

    class touHolder extends RecyclerView.ViewHolder {
        private TextView tv_topic,text_user_impression,pj1,pj2,pj3,yhpj1,yhpj2,yhpj3,tv_ussr_data_state,jieting,shengao,tizhong,xingzuo,text_view_evaluate_good,address,text_view_evaluate_difference;
        private RelativeLayout relativeUser;
        public touHolder(View itemView) {
            super(itemView);

            tv_topic= (TextView) itemView.findViewById(R.id.tv_topic);//个性签名
            pj1= (TextView) itemView.findViewById(R.id.pj1);//自评
            pj2= (TextView) itemView.findViewById(R.id.pj2);//自评
            pj3= (TextView) itemView.findViewById(R.id.pj3);//自评


            yhpj1= (TextView) itemView.findViewById(R.id.yhpj1);//用户评价
            yhpj2= (TextView) itemView.findViewById(R.id.yhpj2);//用户评价
            yhpj3= (TextView) itemView.findViewById(R.id.yhpj3);//用户评价

            relativeUser = (RelativeLayout) itemView.findViewById(R.id.relativeUser);
            tv_ussr_data_state = (TextView) itemView.findViewById(R.id.tv_ussr_data_state);//最后登陆
            jieting = (TextView) itemView.findViewById(R.id.jieting);//接听率
            shengao = (TextView) itemView.findViewById(R.id.shengao);//身高
            tizhong = (TextView) itemView.findViewById(R.id.tizhong);//体重
            xingzuo = (TextView) itemView.findViewById(R.id.xingzuo);//星座
            address = (TextView) itemView.findViewById(R.id.address);//城市

            text_user_impression = (TextView) itemView.findViewById(R.id.text_user_impression);


            text_view_evaluate_difference = (TextView) itemView.findViewById(R.id.text_view_evaluate_difference);
            //蓝M数
            text_view_evaluate_good = (TextView) itemView.findViewById(R.id.text_view_evaluate_good);
            //红M数
            //tips = (TextView) itemView.findViewById(R.id.tips);
            //flow_layout= (FlowLayout) itemView.findViewById(R.id.flow_layout);
        }
    }
    class kongHolder extends RecyclerView.ViewHolder {
        private TextView tips;

        public kongHolder(View itemView) {
            super(itemView);
            //tips = (TextView) itemView.findViewById(R.id.tips);
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
        } else if(position ==0){
            return touType;
        } /*else if(position ==0){
            return kongType;
        }*/else{
            return normalType;
        }
    }
}
