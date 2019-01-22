package com.net.yuesejiaoyou.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.redirect.ResolverA.getset.User_data;
import com.net.yuesejiaoyou.utils.ImageUtils;
import com.net.yuesejiaoyou.utils.LogUtil;
import com.net.yuesejiaoyou.utils.Tools;

import java.util.List;


public class Find3Adapter extends BaseQuickAdapter<User_data, com.chad.library.adapter.base.BaseViewHolder> {


    Context context;

    public Find3Adapter(Context context, List<User_data> datas) {
        super(R.layout.item_find3);
        this.context = context;
        setNewData(datas);
    }

    @Override
    protected void convert(com.chad.library.adapter.base.BaseViewHolder helper, User_data item) {

        if (item.getOnline() == 1) {
            Tools.setDrawableRight((TextView) helper.getView(R.id.item_name),R.mipmap.icon_ckzl_zx);
        } else if (item.getOnline() == 2) {
            Tools.setDrawableRight((TextView) helper.getView(R.id.item_name),R.mipmap.icon_ckzl_ml);
        } else if (item.getOnline() == 3) {
            Tools.setDrawableRight((TextView) helper.getView(R.id.item_name),R.mipmap.icon_ckzl_wr);
        } else {
            Tools.setDrawableRight((TextView) helper.getView(R.id.item_name),R.mipmap.icon_ckzl_lx);
        }
        helper.setText(R.id.item_name, item.getNickname());
        helper.setText(R.id.item_rating, "接通率：" + item.getJieting());
        if (TextUtils.isEmpty(item.getPictures())) {
            ImageUtils.loadImage(item.getPhoto(), (ImageView) helper.getView(R.id.touxiang));
        } else {
            ImageUtils.loadImage(item.getPictures().split(",")[0], (ImageView) helper.getView(R.id.touxiang));
        }

        helper.setRating(R.id.rb_home_leave, (float) item.getStar());

    }

}
