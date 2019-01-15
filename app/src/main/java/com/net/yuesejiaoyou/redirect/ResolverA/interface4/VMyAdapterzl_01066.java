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
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.net.yuesejiaoyou.R;

import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.redirect.ResolverA.core.UsersManage_01066A;
import com.net.yuesejiaoyou.redirect.ResolverA.getset.User_data;
import com.net.yuesejiaoyou.redirect.ResolverC.uiface.Mytag_01162;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.utils.ImageUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.Date;
import java.util.List;


/**
 * Created by lijianchang@yy.com on 2017/4/12.
 */

public class VMyAdapterzl_01066 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private int normalType = 0;
    private int footType = 1;
    private int touType = 2;
    private boolean fadeTips = false;
    private List<User_data> articles;
    private User_data useinfo;
    private Typeface tf;

    public VMyAdapterzl_01066(Context context, List<User_data> articles, User_data useinfo) {
        this.context = context;
        this.articles = articles;
        this.useinfo = useinfo;
        if (useinfo == null) {
            return;
        }
        AssetManager mgr = context.getAssets();

        //根据路径得到Typeface
        tf = Typeface.createFromAsset(mgr, "fonts/arialbd.ttf");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == normalType) {
            return new NormalHolder(LayoutInflater.from(context).inflate(R.layout.item_comment, null));
        } else if (viewType == touType) {
            return new touHolder(LayoutInflater.from(context).inflate(R.layout.information_heard, null));
        } else {
            return new FootHolder(LayoutInflater.from(context).inflate(R.layout.footview_01066, null));
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof NormalHolder) {
            ((NormalHolder) holder).username.setTypeface(tf);
            ((NormalHolder) holder).username.setText(articles.get(position - 1).getNickname());

            if (position - 1 < 0 || TextUtils.isEmpty(articles.get(position - 1).getYhping())) {

            } else {
                String[] ziping = articles.get(position - 1).getYhping().split("卍");
                for (int i = 0; i < ziping.length; i++) {
                    if (i == 0) {
                        String[] b = ziping[i].split("@");
                        ((NormalHolder) holder).pj1.setVisibility(View.VISIBLE);
                        GradientDrawable drawable = (GradientDrawable) ((NormalHolder) holder).pj1.getBackground();
                        //drawable.setStroke(2, getResources().getColor(R.color.mall_red_text));
                        //改变drawable的背景填充色
                        int color = Color.parseColor(b[1]);
                        drawable.setColor(color);
                        ((NormalHolder) holder).pj1.setText(b[0]);
                    } else if (i == 1) {
                        String[] b = ziping[i].split("@");
                        ((NormalHolder) holder).pj2.setVisibility(View.VISIBLE);
                        GradientDrawable drawable = (GradientDrawable) ((NormalHolder) holder).pj2.getBackground();
                        //drawable.setStroke(2, getResources().getColor(R.color.mall_red_text));
                        //改变drawable的背景填充色
                        int color = Color.parseColor(b[1]);
                        drawable.setColor(color);
                        ((NormalHolder) holder).pj2.setText(b[0]);
                    }
                }
            }


            ImageUtils.loadImage(articles.get(position - 1).getPhoto(), ((NormalHolder) holder).image_view_comment);

        } else if (holder instanceof touHolder) {

            if (useinfo == null) {
                return;
            }

            ((touHolder) holder).tv_topic.setText(useinfo.getSignature());

            ((touHolder) holder).relativeUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setClass(context, Mytag_01162.class);
                    Bundle b = new Bundle();
                    b.putString("zhubo_id", useinfo.getId() + "");
                    intent.putExtras(b);
                    context.startActivity(intent);
                }
            });
            if (TextUtils.isEmpty(useinfo.getImage_label())) {


            } else {
                String[] ziping = useinfo.getImage_label().split("卍");
                LogDetect.send(LogDetect.DataType.basicType, "01162---标签数据", ziping);
                for (int i = 0; i < ziping.length; i++) {
                    if (i == 0) {
                        String[] b = ziping[i].split("@");
                        ((touHolder) holder).pj1.setVisibility(View.VISIBLE);
                        GradientDrawable drawable = (GradientDrawable) ((touHolder) holder).pj1.getBackground();
                        //drawable.setStroke(2, getResources().getColor(R.color.mall_red_text));
                        //改变drawable的背景填充色
                        int color = Color.parseColor(b[1]);
                        drawable.setColor(color);
                        ((touHolder) holder).pj1.setText(b[0]);
                    } else if (i == 1) {
                        String[] b = ziping[i].split("@");
                        ((touHolder) holder).pj2.setVisibility(View.VISIBLE);
                        GradientDrawable drawable = (GradientDrawable) ((touHolder) holder).pj2.getBackground();
                        //drawable.setStroke(2, getResources().getColor(R.color.mall_red_text));
                        //改变drawable的背景填充色
                        int color = Color.parseColor(b[1]);
                        drawable.setColor(color);
                        ((touHolder) holder).pj2.setText(b[0]);
                    }
                }
            }
            LogDetect.send(LogDetect.DataType.basicType, "01162---标签数据", useinfo.getYhping());
            if (TextUtils.isEmpty(useinfo.getYhping()) || useinfo.getYhping().equals("null")) {
                ((touHolder) holder).text_user_impression.setVisibility(View.VISIBLE);
            } else {
                String[] ziping = useinfo.getYhping().split("卍");
                LogDetect.send(LogDetect.DataType.basicType, "01162---标签数据", ziping);
                for (int i = 0; i < ziping.length; i++) {
                    if (i == 0) {
                        String[] b = ziping[i].split("@");
                        ((touHolder) holder).yhpj1.setVisibility(View.VISIBLE);
                        GradientDrawable drawable = (GradientDrawable) ((touHolder) holder).yhpj1.getBackground();
                        //drawable.setStroke(2, getResources().getColor(R.color.mall_red_text));
                        //改变drawable的背景填充色
                        int color = Color.parseColor(b[1]);
                        drawable.setColor(color);
                        ((touHolder) holder).yhpj1.setText(b[0]);
                    } else if (i == 1) {
                        String[] b = ziping[i].split("@");
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
            if (useinfo.getOnline() == 1) {
                ((touHolder) holder).tv_ussr_data_state.setText("在线");
            } else if (useinfo.getOnline() == 0) {
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

        }*/ else {
            ((FootHolder) holder).tips.setVisibility(View.VISIBLE);
            if (fadeTips) {
                ((FootHolder) holder).tips.setTypeface(tf);
                ((FootHolder) holder).tips.setText("——我也是有底线的——");
            }
        }
    }


    @Override
    public int getItemCount() {
        return articles.size() + 2;
    }


    class NormalHolder extends RecyclerView.ViewHolder {
        private TextView username, pj1, pj2;
        private ImageView image_view_comment;

        public NormalHolder(View convertView) {
            super(convertView);

            image_view_comment = convertView.findViewById(R.id.image_view_comment);
            username = (TextView) convertView.findViewById(R.id.tv_nick);
            pj1 = (TextView) convertView.findViewById(R.id.pj1);//自评
            pj2 = (TextView) convertView.findViewById(R.id.pj2);//自评
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
        private TextView tv_topic, text_user_impression, pj1, pj2, pj3, yhpj1, yhpj2, yhpj3, tv_ussr_data_state, jieting, shengao, tizhong, xingzuo, text_view_evaluate_good, address, text_view_evaluate_difference;
        private RelativeLayout relativeUser;

        public touHolder(View itemView) {
            super(itemView);

            tv_topic = (TextView) itemView.findViewById(R.id.tv_topic);//个性签名
            pj1 = (TextView) itemView.findViewById(R.id.pj1);//自评
            pj2 = (TextView) itemView.findViewById(R.id.pj2);//自评
            pj3 = (TextView) itemView.findViewById(R.id.pj3);//自评


            yhpj1 = (TextView) itemView.findViewById(R.id.yhpj1);//用户评价
            yhpj2 = (TextView) itemView.findViewById(R.id.yhpj2);//用户评价
            yhpj3 = (TextView) itemView.findViewById(R.id.yhpj3);//用户评价

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

    public boolean isFadeTips() {
        return fadeTips;
    }

    public void setFadeTips(boolean di) {
        fadeTips = di;
    }


    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return footType;
        } else if (position == 0) {
            return touType;
        } /*else if(position ==0){
            return kongType;
        }*/ else {
            return normalType;
        }
    }
}
