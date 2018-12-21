package com.net.yuesejiaoyou.redirect.ResolverC.interface4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

/*import com.net.yuesejiaoyou.R;
import com.example.vliao.getset.Vliao2_01168;*/
import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.Vliao2_01168;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;


public class Vliao_zhichuzhuboAdapter_01168 extends BaseAdapter {
List<Vliao2_01168> list1;
Context context;
private DisplayImageOptions options;
HolderView holderview;
private ListView lv;

	public Vliao_zhichuzhuboAdapter_01168(List<Vliao2_01168> list1, Context context, ListView lv) {
	super();
	this.list1 = list1;
	this.context = context;
	this.lv = lv;
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
			arg1= LayoutInflater.from(context).inflate(R.layout.vliao_shourulistzhubo_01168, null);
		  //初始化控件
			holderview=new HolderView();
			holderview.tujing=(TextView) arg1.findViewById(R.id.tujing);
			holderview.jine=(TextView) arg1.findViewById(R.id.jine);
			holderview.shijian=(TextView) arg1.findViewById(R.id.shijian);
			holderview.touxiang=(RoundImageView) arg1.findViewById(R.id.touxiang);
			arg1.setTag(holderview);
		}
		//如果该view存在 则加载 不存在则添加
		else{
			if(arg1.getTag() instanceof HolderView){
				holderview=(HolderView) arg1.getTag();
			}else{
				holderview=new HolderView();
				holderview.tujing=(TextView) arg1.findViewById(R.id.tujing);
				holderview.jine=(TextView) arg1.findViewById(R.id.jine);
				holderview.shijian=(TextView) arg1.findViewById(R.id.shijian);
				holderview.touxiang=(RoundImageView) arg1.findViewById(R.id.touxiang);
				arg1.setTag(holderview);
			}
		}
		
		
	    //String []a=list1.get(arg0).getTime().split("\\s");
		holderview.shijian.setText(list1.get(arg0).getTime());
		//holderview.jine.setText("- "+list1.get(arg0).getMoney());
		holderview.jine.setText("- "+list1.get(arg0).getNum());
		holderview.tujing.setText(list1.get(arg0).getType());
		ImageLoader.getInstance().displayImage(
                list1.get(arg0).getPhoto(), holderview.touxiang,
                options);
		//holderview.time1.setText(a[0]);
		//holderview.time2.setText(a[1]);
		
		return arg1;
	}
	//写一个类 用来初始化adapter想要初始化内容
    class HolderView {
		//private TextView time1,time2,tujing,jine;
    	private TextView tujing,shijian,jine;
		private RoundImageView touxiang;
    }
     
}
