package com.net.yuesejiaoyou.redirect.ResolverC.interface4;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/*import com.net.yuesejiaoyou.R;
import com.example.vliao.getset.Vliao2_01168;*/
import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.Vliao2_01168;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;


public class Vliao_zhichuAdapter_01168 extends BaseAdapter {
List<Vliao2_01168> list1;
Context context;
	private DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
			.cacheOnDisc(true).bitmapConfig(Bitmap.Config.RGB_565).build();
HolderView holderview;
	public Vliao_zhichuAdapter_01168(List<Vliao2_01168> list1, Context context) {
	super();
	this.list1 = list1;
	this.context = context;
}

	@Override
	public int getCount() {
		
		return list1.size();
	}

	@Override
	public Object getItem(int arg0) {
		
		return list1.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		if(arg1==null){
			arg1= LayoutInflater.from(context).inflate(R.layout.vliao_zhichulist_01168, null);
		  //初始化控件
			holderview=new HolderView();
			holderview.zhichu=(TextView) arg1.findViewById(R.id.zhichu);
			holderview.jine=(TextView) arg1.findViewById(R.id.jine);
			holderview.time1=(TextView) arg1.findViewById(R.id.time1);
			holderview.time2=(TextView) arg1.findViewById(R.id.time2);
			//touxiang
			holderview.zhuangtai=(TextView) arg1.findViewById(R.id.zhuangtai);
			holderview.touxiang=(RoundImageView) arg1.findViewById(R.id.touxiang);
			arg1.setTag(holderview);
		}
		//如果该view存在 则加载 不存在则添加
		else{
			if(arg1.getTag() instanceof HolderView){
				holderview=(HolderView) arg1.getTag();
			}else{
				holderview=new HolderView();
				holderview.zhichu=(TextView) arg1.findViewById(R.id.zhichu);
				holderview.jine=(TextView) arg1.findViewById(R.id.jine);
				holderview.time1=(TextView) arg1.findViewById(R.id.time1);
				holderview.time2=(TextView) arg1.findViewById(R.id.time2);
				//zhuangtai
				holderview.zhuangtai=(TextView) arg1.findViewById(R.id.zhuangtai);
				holderview.touxiang=(RoundImageView) arg1.findViewById(R.id.touxiang);
				arg1.setTag(holderview);
			}
		}
	    //String []a=list1.get(arg0).getTime().split("\\s");
		String[] time2 = {};
		if(!list1.get(arg0).getTime().equals("")){
			time2 = list1.get(arg0).getTime().split("\\s");
			holderview.time1.setText(time2[0]);
			holderview.time2.setText(time2[1]);
		}
		
		//holderview.zhichu.setText(list1.get(arg0).getType());
		//holderview.jine.setText("$"+list1.get(arg0).getMoney());
		holderview.jine.setText(""+list1.get(arg0).getNum());
		//holderview.time1.setText(a[0]);
		//holderview.time2.setText(a[1]);
		holderview.zhuangtai.setText(""+list1.get(arg0).getType());
		ImageLoader.getInstance().displayImage(
	               list1.get(arg0).getPhoto(), holderview.touxiang,
	                options);
		return arg1;
	}
	//写一个类 用来初始化adapter想要初始化内容
    class HolderView {
    	//年月日、时分秒、支出类型、金额
		private TextView time1,time2,zhichu,jine,zhuangtai;
		private RoundImageView touxiang;
    }
     
}
