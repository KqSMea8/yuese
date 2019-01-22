package com.net.yuesejiaoyou.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.bean.RechargeBean;



public class RechargeAdapter extends BaseQuickAdapter<RechargeBean, BaseViewHolder> {


    public RechargeAdapter() {
        super(R.layout.item_recharge);
    }

    @Override
    protected void convert(BaseViewHolder helper, RechargeBean item) {
        helper.setText(R.id.item_coin, item.getV_num() + "");
        helper.setText(R.id.item_money, item.getPrice() + "元");
    }

}
