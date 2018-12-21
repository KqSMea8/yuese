/*
 *
 * MenuAdapter.java
 * 
 * Created by Wuwang on 2016/11/14
 * Copyright © 2016年 深圳哎吖科技. All rights reserved.
 */
package com.net.yuesejiaoyou.redirect.ResolverB.interface4.xjg;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

//import com.xiaojigou.luo.xjgarsdk.xjgarsdkdemoapp.R;

import com.net.yuesejiaoyou.R;

import java.util.ArrayList;

/**
 * Description:
 */
public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuHolder> {

    private Context mContext;
    public ArrayList<MenuBean> data;
    public int checkPos=0;

    public MenuAdapter(Context context, ArrayList<MenuBean> data){
        this.mContext=context;
        this.data=data;
    }

    @Override
    public MenuHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MenuHolder(LayoutInflater.from(mContext).inflate(R.layout.item_small_menu,parent,false));
    }

    @Override
    public void onBindViewHolder(MenuHolder holder, int position) {
        holder.setData(data.get(position),position);

    }

    @Override
    public int getItemCount() {
        return data!=null?data.size():0;
    }

    private View.OnClickListener mListener;
    public void setOnClickListener(View.OnClickListener listener){
        this.mListener=listener;
    }

    public class MenuHolder extends RecyclerView.ViewHolder{

        private TextView tv;
        private RelativeLayout layDownload;

        public MenuHolder(View itemView) {
            super(itemView);
            tv= (TextView)itemView.findViewById(R.id.mMenu);
            layDownload = (RelativeLayout) itemView.findViewById(R.id.laydownload);
            ClickUtils.addClickTo(tv,mListener, R.id.mMenu);
            ClickUtils.addClickTo(layDownload, mListener, R.id.laydownload);
        }

        public void setData(MenuBean bean,int pos){
            tv.setText(bean.name);
            tv.setSelected(pos==checkPos);
            ClickUtils.setPos(tv,pos);
            ClickUtils.setPos(layDownload, pos);
            //--------------------------
            if(pos == 0 || bean.exist) {
                layDownload.setVisibility(View.GONE);
            } else {
                layDownload.setVisibility(View.VISIBLE);
                ImageView imgDownload = layDownload.findViewById(R.id.imgdownload);
                ProgressBar pbDownload = layDownload.findViewById(R.id.pbprogress);
                if(bean.isDownloading) {
                    imgDownload.setVisibility(View.GONE);
                    pbDownload.setVisibility(View.VISIBLE);
                } else {
                    imgDownload.setVisibility(View.VISIBLE);
                    pbDownload.setVisibility(View.GONE);
                }
            }
        }

        public void select(boolean isSelect){
            tv.setSelected(isSelect);
        }
    }

}
