package com.net.yuesejiaoyou.redirect.ResolverD.interface4.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.redirect.ResolverA.getset.User_data;
import com.net.yuesejiaoyou.redirect.ResolverA.interface4.VMyAdapter_01066;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.utils.ImageUtils;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.utils.LogUtil;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.utils.Tools;

import java.util.List;


public class Find2Adapter extends BaseQuickAdapter<User_data, com.chad.library.adapter.base.BaseViewHolder> {


    Context context;

    public Find2Adapter(Context context, List<User_data> datas) {
        super(R.layout.listvie_item_01066);
        this.context = context;
        setNewData(datas);
    }

    @Override
    protected void convert(com.chad.library.adapter.base.BaseViewHolder helper, User_data item) {

        LogUtil.i("ttt","---"+item.getNative_place()+"-------"+item.getPictures());
        helper.addOnClickListener(R.id.item_onev_list_layout);
        helper.addOnClickListener(R.id.zhichi);

        if (item.getOnline() == 1) {
            Tools.setDrawableLeft((TextView) helper.getView(R.id.tv_home_status),R.drawable.zt_zaixian);
            helper.setText(R.id.tv_home_status, "在线");
        } else if (item.getOnline() == 2) {
            Tools.setDrawableLeft((TextView) helper.getView(R.id.tv_home_status),R.drawable.zt_huoyue);
            helper.setText(R.id.tv_home_status, "在聊");
        } else if (item.getOnline() == 3) {
            Tools.setDrawableLeft((TextView) helper.getView(R.id.tv_home_status),R.drawable.zt_wurao);
            helper.setText(R.id.tv_home_status, "勿扰");
        } else {
            Tools.setDrawableLeft((TextView) helper.getView(R.id.tv_home_status),R.drawable.zt_lixian);
            helper.setText(R.id.tv_home_status, "离线");
        }
        helper.setText(R.id.tv_home_nickname, item.getNickname());
        helper.setText(R.id.tv_price, "接通率：" + item.getJieting());
        helper.setText(R.id.tv_topic, item.getSignature());
        if (TextUtils.isEmpty(item.getPictures())) {
            ImageUtils.loadImage(item.getPhoto(), (ImageView) helper.getView(R.id.sd_home_avatar_img));
        } else {
            ImageUtils.loadImage(item.getPictures().split(",")[0], (ImageView) helper.getView(R.id.sd_home_avatar_img));
        }

        helper.setRating(R.id.rb_home_leave, (float) item.getStar());
    }

}
