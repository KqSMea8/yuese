package com.net.yuesejiaoyou.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.bean.MineBean;

import java.util.List;


public class MineAdapter extends BaseQuickAdapter<MineBean, com.chad.library.adapter.base.BaseViewHolder> {


    public MineAdapter(List<MineBean> datas) {
        super(R.layout.item_mine);
        setNewData(datas);
    }

    @Override
    protected void convert(com.chad.library.adapter.base.BaseViewHolder helper, MineBean item) {
        helper.setImageResource(R.id.item_image, item.getImage());
        helper.setText(R.id.item_name, item.getName());
    }

}
