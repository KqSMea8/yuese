package com.net.yuesejiaoyou.redirect.ResolverC.interface4;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/*import com.net.yuesejiaoyou.R;
import com.example.vliao.getset.Focus_01107;*/
import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.Focus_01107;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018\5\5 0005.
 */

public class FocusAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Focus_01107> dataList;
    private DisplayImageOptions options;

    public FocusAdapter(Context ctxt, ArrayList<Focus_01107> list) {
        mContext = ctxt;
        dataList = list;

        options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisc(true).bitmapConfig(Bitmap.Config.RGB_565).build();
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int i) {
        return dataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {

        HolderView holderView;
        if(convertView==null){
            holderView = new HolderView();
            convertView= LayoutInflater.from(mContext).inflate(R.layout.focusitem,null);
            holderView.nicheng = (TextView) convertView.findViewById(R.id.nicheng);
            holderView.touxiang = (RoundImageView) convertView.findViewById(R.id.touxiang);
            convertView.setTag(holderView);
        }else{
            if(convertView.getTag() instanceof HolderView){
                holderView=(HolderView) convertView.getTag();
            }else{
                holderView = new HolderView();
                convertView= LayoutInflater.from(mContext).inflate(R.layout.distributepersonitem,null);
                holderView.nicheng = (TextView) convertView.findViewById(R.id.nicheng);
                holderView.touxiang = (RoundImageView) convertView.findViewById(R.id.touxiang);
                convertView.setTag(holderView);
            }
        }

        ImageLoader.getInstance().displayImage(dataList.get(i).getHeadpic(),holderView.touxiang,options);
        holderView.nicheng.setText(dataList.get(i).getNickname());

        return convertView;
    }

    class HolderView {
        private ImageView touxiang;
        private TextView nicheng;
    }
}
