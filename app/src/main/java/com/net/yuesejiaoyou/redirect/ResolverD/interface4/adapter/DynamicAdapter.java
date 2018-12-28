package com.net.yuesejiaoyou.redirect.ResolverD.interface4.adapter;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.redirect.ResolverB.getset.Videoinfo;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.GlideApp;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.utils.ImageUtils;

import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;


public class DynamicAdapter extends BaseQuickAdapter<Videoinfo, com.chad.library.adapter.base.BaseViewHolder> {


    Context context;
    private final Typeface tf;

    public DynamicAdapter(Context context, List<Videoinfo> datas) {
        super(R.layout.item_dynamic);
        this.context = context;
        setNewData(datas);
        AssetManager mgr = context.getAssets();
        tf = Typeface.createFromAsset(mgr, "fonts/arialbd.ttf");
    }

    @Override
    protected void convert(com.chad.library.adapter.base.BaseViewHolder helper, Videoinfo item) {
        if (item.getIspay() == 0) {
            helper.setGone(R.id.iv_lock, false);
            ImageUtils.loadImage(item.getVideo_photo(), (ImageView) helper.getView(R.id.iv_cover));
        } else if (item.getIspay() == 1) {
            helper.setGone(R.id.iv_lock, true);
            helper.setImageResource(R.id.iv_lock, R.drawable.videopay_lock);

            GlideApp.with(context)
                    .load(item.getVideo_photo())
                    .placeholder(R.drawable.moren)
                    .error(R.drawable.moren)
                    .optionalTransform(new BlurTransformation(context, 6, 4))
                    .into((ImageView) helper.getView(R.id.iv_cover));

        } else {
            helper.setGone(R.id.iv_lock, true);
            helper.setImageResource(R.id.iv_lock, R.drawable.videopay_unlock);
            ImageUtils.loadImage(item.getVideo_photo(), (ImageView) helper.getView(R.id.iv_cover));
        }

        if(TextUtils.isEmpty(item.getVideo_photo())){
            GlideApp.with(context)
                    .load(item.getPhoto())
                    .placeholder(R.drawable.moren)
                    .error(R.drawable.moren)
                    .optionalTransform(new BlurTransformation(context, 6, 4))
                    .into((ImageView) helper.getView(R.id.iv_cover));
        }

        if (item.getStatus() == 0) {
            helper.setVisible(R.id.findpage_list_item_examine_rl, true);
        } else {
            helper.setVisible(R.id.findpage_list_item_examine_rl, false);
        }

        ImageUtils.loadImage(item.getPhoto(), (ImageView) helper.getView(R.id.iv_headIcon));

        helper.setTypeface(R.id.title_text, tf);
        helper.setTypeface(R.id.tv_v_name, tf);
        helper.setTypeface(R.id.follow_tv, tf);
        helper.setText(R.id.title_text, item.getExplain());
        helper.setText(R.id.tv_v_name, item.getNickname());
        helper.setText(R.id.follow_tv, item.getLike_num());
    }

}
