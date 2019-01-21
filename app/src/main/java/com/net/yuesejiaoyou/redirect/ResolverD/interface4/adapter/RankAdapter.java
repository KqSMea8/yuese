package com.net.yuesejiaoyou.redirect.ResolverD.interface4.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.redirect.ResolverA.getset.User_data;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.utils.ImageUtils;



public class RankAdapter extends BaseQuickAdapter<User_data, BaseViewHolder> {


    public RankAdapter() {
        super(R.layout.item_rank);
    }

    @Override
    protected void convert(BaseViewHolder helper, User_data item) {

        int index = getData().indexOf(item);
        if (index == 0) {
            helper.setGone(R.id.numbertext,false);
            helper.setGone(R.id.numberimg,true);
            helper.setImageResource(R.id.numberimg,R.drawable.qinmidu_no1);
        } else if (index == 1) {
            helper.setGone(R.id.numbertext,false);
            helper.setGone(R.id.numberimg,true);
            helper.setImageResource(R.id.numberimg,R.drawable.qinmidu_no2);
        } else if (index == 2) {

            helper.setGone(R.id.numbertext,false);
            helper.setGone(R.id.numberimg,true);
            helper.setImageResource(R.id.numberimg,R.drawable.qinmidu_no3);
        } else {
            helper.setGone(R.id.numbertext,true);
            helper.setGone(R.id.numberimg,false);
            helper.setText(R.id.numbertext,"NO." + (index + 1));
        }
        helper.setText(R.id.name, item.getNickname());
        helper.setText(R.id.tv_count, "魅力值："+item.getMoney());
        ImageUtils.loadImage(item.getPhoto(), (ImageView) helper.getView(R.id.touxiang));

        helper.addOnClickListener(R.id.touxiang);

    }

}
