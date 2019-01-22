package com.net.yuesejiaoyou.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.redirect.ResolverA.getset.User_data;
import com.net.yuesejiaoyou.utils.ImageUtils;

import java.util.List;


public class FindAdapter extends BaseQuickAdapter<User_data, com.chad.library.adapter.base.BaseViewHolder> {


    Context context;

    public FindAdapter(Context context, List<User_data> datas) {
        super(R.layout.item_man_01066);
        this.context = context;
        setNewData(datas);
    }

    @Override
    protected void convert(com.chad.library.adapter.base.BaseViewHolder helper, User_data item) {
        helper.addOnClickListener(R.id.userinfo);
        helper.addOnClickListener(R.id.zhichi);

        if (item.getOnline() == 1) {
            helper.setBackgroundRes(R.id.iv_zt_img, R.drawable.zt_zaixian);
            helper.setText(R.id.tv_home_status, "在线");
        } else if (item.getOnline() == 2) {
            helper.setBackgroundRes(R.id.iv_zt_img, R.drawable.zt_huoyue);
            helper.setText(R.id.tv_home_status, "在聊");
        } else if (item.getOnline() == 3) {
            helper.setBackgroundRes(R.id.iv_zt_img, R.drawable.zt_wurao);
            helper.setText(R.id.tv_home_status, "勿扰");
        } else {
            helper.setBackgroundRes(R.id.iv_zt_img, R.drawable.zt_lixian);
            helper.setText(R.id.tv_home_status, "离线");
        }
        helper.setText(R.id.nickname, item.getNickname());
        helper.setImageResource(R.id.headpic, R.drawable.moren);
        ImageUtils.loadImage(item.getPhoto(), (ImageView) helper.getView(R.id.headpic));
    }

}
