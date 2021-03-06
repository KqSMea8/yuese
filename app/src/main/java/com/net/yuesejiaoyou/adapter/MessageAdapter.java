package com.net.yuesejiaoyou.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.bean.Session;
import com.net.yuesejiaoyou.classroot.interface4.openfire.interface4.ExpressionUtil;
import com.net.yuesejiaoyou.utils.ImageUtils;
import com.net.yuesejiaoyou.utils.Tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;


public class MessageAdapter extends BaseQuickAdapter<Session, com.chad.library.adapter.base.BaseViewHolder> {


    Context context;
    private final SimpleDateFormat simpleDateFormat;

    public MessageAdapter(Context context, List<Session> datas) {
        super(R.layout.item_message);
        this.context = context;
        setNewData(datas);
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    @Override
    protected void convert(com.chad.library.adapter.base.BaseViewHolder helper, Session item) {

        if (TextUtils.isEmpty(item.getHeadpic())) {
            helper.setImageResource(R.id.user_head, R.mipmap.ic_launcher11);
        } else {
            ImageUtils.loadImage(item.getHeadpic(), (ImageView) helper.getView(R.id.user_head));
        }


        if (!item.getNotReadCount().equals("0")) {
            helper.setVisible(R.id.tv_noread, true);
            helper.setText(R.id.tv_noread, item.getNotReadCount());
        } else {
            helper.setVisible(R.id.tv_noread, false);
        }
        try {
            String sxw = Tools.showDate(simpleDateFormat.parse(item.getTime()), "yyyy-MM-dd HH:mm:ss");
            helper.setText(R.id.tv_time, sxw + "\n" + (item.getTime().split("\\s+")[1]).substring(0, 5));
        } catch (ParseException e) {
            e.printStackTrace();
            helper.setText(R.id.tv_time, item.getTime().split("\\s+")[0]);

        }
        helper.setText(R.id.user_name, item.getName());
        helper.setText(R.id.content, ExpressionUtil.prase(mContext, (TextView) helper.getView(R.id.content), item.getContent() == null ? "" : item.getContent()));

        helper.addOnClickListener(R.id.user_head);
    }

}
