package com.net.yuesejiaoyou.adapter;

import android.text.TextUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.FansBean;
import com.net.yuesejiaoyou.utils.ImageUtils;

import java.text.DecimalFormat;


public class IntimateAdapter extends BaseQuickAdapter<FansBean, BaseViewHolder> {


    public IntimateAdapter() {
        super(R.layout.item_intimate);
    }

    @Override
    protected void convert(BaseViewHolder helper, FansBean item) {

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
        if (TextUtils.isEmpty(item.getQinmvalue()) || "-1".equals(item.getQinmvalue())) {

        }else {
            DecimalFormat df = new DecimalFormat("#");
            String a = df.format(Double.parseDouble(item.getQinmvalue()));
            helper.setText(R.id.qinmivalue, a);
        }
        ImageUtils.loadImage(item.getPhoto(), (ImageView) helper.getView(R.id.touxiang));

    }

}
