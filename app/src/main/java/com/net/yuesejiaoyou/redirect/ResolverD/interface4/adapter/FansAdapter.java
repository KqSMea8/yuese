package com.net.yuesejiaoyou.redirect.ResolverD.interface4.adapter;

import android.text.TextUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.FansBean;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.utils.ImageUtils;


public class FansAdapter extends BaseQuickAdapter<FansBean, BaseViewHolder> {


    public FansAdapter() {
        super(R.layout.item_fans);
    }

    @Override
    protected void convert(BaseViewHolder helper, FansBean item) {
        helper.setText(R.id.name, item.getNickname());
        helper.setText(R.id.qinmivalue, item.getQinmvalue().equals("-1") ? "" : item.getQinmvalue());
        ImageUtils.loadImage(item.getPhoto(), (ImageView) helper.getView(R.id.touxiang));

        if (TextUtils.isEmpty(item.getUser_state())) {
            helper.setImageResource(R.id.userstatus, R.drawable.offline);
        } else {
            if ("在线".equals(item.getUser_state())) {
                helper.setImageResource(R.id.userstatus, R.drawable.online);
            } else if ("离线".equals(item.getUser_state())) {
                helper.setImageResource(R.id.userstatus, R.drawable.offline);
            } else if ("活跃的".equals(item.getUser_state())) {
                helper.setImageResource(R.id.userstatus, R.drawable.active);
            } else {
                helper.setImageResource(R.id.userstatus, R.drawable.offline);
            }
        }


    }

}
