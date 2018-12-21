package com.net.yuesejiaoyou.redirect.ResolverA.interface4;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.Toast;

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.openfire.uiface.ChatActivity;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.redirect.ResolverA.getset.User_data;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.agora.P2PVideoConst;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.agora.zhubo.GukeInfo;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.agora.zhubo.ZhuboActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.List;


/**
 * Created by lijianchang@yy.com on 2017/4/12.
 */

public class MyAdapterMan_01066 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<String> datas;
    private Context context;
    private int normalType = 0;
    private int footType = 1;
    private int pos = 0;
    private boolean hasMore = true;
    private boolean fadeTips = false;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private List<User_data> articles;
    private DisplayImageOptions options;

    public MyAdapterMan_01066(List<String> datas, Context context, boolean hasMore, List<User_data> articles) {
        this.datas = datas;
        this.context = context;
        this.hasMore = hasMore;
        this.articles = articles;
        options = new DisplayImageOptions.Builder().cacheInMemory(true).displayer(new RoundedBitmapDisplayer(20)).showImageOnLoading(R.drawable.moren)
                .cacheOnDisc(true).bitmapConfig(Bitmap.Config.RGB_565).build();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == normalType) {
            return new NormalHolder(LayoutInflater.from(context).inflate(R.layout.item_man_01066, null));
        } else {
            return new FootHolder(LayoutInflater.from(context).inflate(R.layout.footview_01066, null));
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof NormalHolder) {


            ((NormalHolder) holder).xyitem.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Intent intentthis = new Intent(context, ChatActivity.class);
                    intentthis.putExtra("id", articles.get(position).getId()+"");
                    intentthis.putExtra("name", articles.get(position).getNickname());
                    intentthis.putExtra("headpic", articles.get(position).getPhoto());
                    context.startActivity(intentthis);
//                    Toast.makeText(context,position+"++0",Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(context, meijian_profile_01178.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("id", ""+articles.get(position).getId());
//                    intent.putExtras(bundle);
//                    context.startActivity(intent);
                }
            });

            ((NormalHolder) holder).zhichi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

//                    if(Util.iszhubo.equals("0")){
//                        Toast.makeText(context,"只有主播能打招呼",Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//
//                    Util.sendMsgText1(context,"",articles.get(position).getId()+"",articles.get(position).getNickname(), articles.get(position).getPhoto());
//
//                    Toast.makeText(context, "打招呼成功!", Toast.LENGTH_SHORT).show();
                    String roomid = System.currentTimeMillis()+"";
                    ZhuboActivity.startCallGuke(context, new GukeInfo(articles.get(position).getId()+"",
                            articles.get(position).getNickname(), articles.get(position).getPhoto(), roomid,
                            P2PVideoConst.ZHUBO_CALL_GUKE, P2PVideoConst.HAVE_NO_YUYUE));
                }
            });

            if (articles.get(position).getOnline()==1){
                ((NormalHolder) holder).staus.setBackgroundResource(R.drawable.zt_zaixian);
                ((NormalHolder) holder).tv_status.setText("在线");
            }else if (articles.get(position).getOnline()==2){
                ((NormalHolder) holder).staus.setBackgroundResource(R.drawable.zt_huoyue);
                ((NormalHolder) holder).tv_status.setText("在聊");
            }else if (articles.get(position).getOnline()==3){
                ((NormalHolder) holder).staus.setBackgroundResource(R.drawable.zt_wurao);
                ((NormalHolder) holder).tv_status.setText("勿扰");
            }else {
                ((NormalHolder) holder).staus.setBackgroundResource(R.drawable.zt_lixian);
                ((NormalHolder) holder).tv_status.setText("离线");
            }
//            if (articles.get(position).getIdentify_check()==0){
//                ((NormalHolder) holder).smrz.setVisibility(View.GONE);
//            }else{
//                ((NormalHolder) holder).smrz.setVisibility(View.VISIBLE);
//            }
//            if (articles.get(position).getOnline()==1){
//                ((NormalHolder) holder).staus.setBackgroundResource(R.drawable.yk);
//            }else if (articles.get(position).getOnline()==2){
//                ((NormalHolder) holder).staus.setBackgroundResource(R.drawable.zailiao);
//            }else if (articles.get(position).getOnline()==3){
//            	((NormalHolder) holder).staus.setBackgroundResource(R.drawable.wurao);
//            }else {
//            	((NormalHolder) holder).staus.setBackgroundResource(R.drawable.lixian);
//            }
            ((NormalHolder) holder).username.setText(articles.get(position).getNickname());
            //((NormalHolder) holder).age.setText(articles.get(position).getAge());
            //((NormalHolder) holder).note.setText(articles.get(position).getSignature());
            ((NormalHolder) holder).photo.setImageResource(R.drawable.moren);
            ImageLoader.getInstance().displayImage(
                    articles.get(position).getPhoto(), ((NormalHolder) holder).photo,
                    options);
        } else {
            ((FootHolder) holder).tips.setVisibility(View.VISIBLE);
            if (fadeTips){
                ((FootHolder) holder).tips.setText("——我也是有底线的——");
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
                        return 4;
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
        articles.get(i).setIslike(1);
        notifyItemChanged(i);
        //notifyDataSetChanged();
    }

   /* private class GetDataTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {

            UsersManage_01066 goodsmanage = new UsersManage_01066();
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
    }*/


    class NormalHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private TextView username,note,zhichi,tv_status;
        private ImageView photo,staus,smrz;
        private RelativeLayout xyitem;
        public NormalHolder(View convertView) {
            super(convertView);
            xyitem= (RelativeLayout) convertView.findViewById(R.id.userinfo);
            photo = (ImageView) convertView.findViewById(R.id.headpic);
            //smrz= (ImageView) convertView.findViewById(R.id.renzheng);
            //staus= (ImageView) convertView.findViewById(R.id.staus);
            username = (TextView) convertView.findViewById(R.id.nickname);
            staus= (ImageView) convertView.findViewById(R.id.iv_zt_img);
            tv_status= (TextView) convertView.findViewById(R.id.tv_home_status);
            zhichi= (TextView) convertView.findViewById(R.id.zhichi);
            //note= (TextView) convertView.findViewById(R.id.notice);
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
}
