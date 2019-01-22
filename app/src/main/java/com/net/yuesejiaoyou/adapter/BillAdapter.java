package com.net.yuesejiaoyou.adapter;

import android.text.TextUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.BillBean;
import com.net.yuesejiaoyou.utils.ImageUtils;


public class BillAdapter extends BaseQuickAdapter<BillBean, BaseViewHolder> {


    public BillAdapter() {
        super(R.layout.item_bill);
    }

    @Override
    protected void convert(BaseViewHolder helper, BillBean item) {

        helper.setText(R.id.shijian, item.getTime());
        helper.setText(R.id.jine, TextUtils.isEmpty(item.getNum()) ? item.getMoney() : item.getNum());
        helper.setText(R.id.tujing, item.getType());
        ImageUtils.loadImage(item.getPhoto(), (ImageView) helper.getView(R.id.touxiang));

    }

}
