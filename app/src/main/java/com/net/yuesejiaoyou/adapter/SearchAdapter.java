package com.net.yuesejiaoyou.adapter;

import android.graphics.Color;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.redirect.ResolverA.getset.UserBean;
import com.net.yuesejiaoyou.utils.KeywordUtil;
import com.net.yuesejiaoyou.utils.ImageUtils;

import java.util.List;


public class SearchAdapter extends BaseQuickAdapter<UserBean, com.chad.library.adapter.base.BaseViewHolder> {


    String key = "";

    public SearchAdapter() {
        super(R.layout.search_dv_item_01160);
    }

    @Override
    protected void convert(com.chad.library.adapter.base.BaseViewHolder helper, UserBean item) {
        ImageUtils.loadImage(item.getPhoto(), (ImageView) helper.getView(R.id.user_head));
        helper.setText(R.id.user_name, KeywordUtil.matcherSearchTitle(Color.parseColor("#FF2F77"), item.getNickname(), key));
        helper.setText(R.id.content, item.getIntimacy());
    }

    public void setDatas(List<UserBean> datas, String key) {
        setNewData(datas);
        this.key = key;
    }

}
