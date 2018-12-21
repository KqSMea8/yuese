package com.net.yuesejiaoyou.redirect.ResolverC.interface4;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/*import com.net.yuesejiaoyou.R;
import com.example.vliao.getset.vliaofans_01168;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;*/
//import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.vliaofans_01168;
import java.text.DecimalFormat;
import java.util.List;


public class vliao_wodeqinmibangAdapter_01178 extends BaseAdapter {
List<vliaofans_01168> list1;
Context context;
HolderView holderview;
private DisplayImageOptions options;
Resources resources;
Drawable drawable;

	public vliao_wodeqinmibangAdapter_01178(List<vliaofans_01168> list1, Context context) {
	super();
	this.list1 = list1;
	this.context = context;
	resources = this.context.getResources();
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

	@SuppressLint("NewApi")
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		if(arg1==null){
			arg1= LayoutInflater.from(context).inflate(R.layout.vliao_myqinmilist_01178, null);
		  //初始化控件
		//	LogDetect.send(LogDetect.DataType.specialType, "vliao_myqinmilist_01178","适配器a");
			holderview=new HolderView();
			holderview.touxiang=(RoundImageView) arg1.findViewById(R.id.touxiang);
			holderview.name=(TextView) arg1.findViewById(R.id.name);
			holderview.qinmivalue=(TextView) arg1.findViewById(R.id.qinmivalue);
			holderview.numbertext=(TextView) arg1.findViewById(R.id.numbertext);
			holderview.numberimg=(ImageView)arg1.findViewById(R.id.numberimg);
			
			arg1.setTag(holderview);
		}
		//如果该view存在 则加载 不存在则添加
		else{
			if(arg1.getTag() instanceof HolderView){
				holderview=(HolderView) arg1.getTag();
			}else{
				holderview=new HolderView();
				LogDetect.send(LogDetect.DataType.specialType, "vliao_myqinmilist_01178","适配器b");
				holderview.touxiang=(RoundImageView) arg1.findViewById(R.id.touxiang);
				holderview.name=(TextView) arg1.findViewById(R.id.name);
				holderview.qinmivalue=(TextView) arg1.findViewById(R.id.qinmivalue);
				holderview.numbertext=(TextView) arg1.findViewById(R.id.numbertext);
				holderview.numberimg=(ImageView)arg1.findViewById(R.id.numberimg);
				
				arg1.setTag(holderview);
			}
		}
		LogDetect.send(LogDetect.DataType.specialType, "vliao_myqinmilist_01178","适配器c");
		LogDetect.send(LogDetect.DataType.specialType, "vliao_myqinmilist_01178--arg0:--",arg0);
		if(arg0 == 0){
			holderview.numbertext.setVisibility(View.GONE);
			holderview.numberimg.setVisibility(View.VISIBLE);
			
			drawable = resources.getDrawable(R.drawable.qinmidu_no1);
			holderview.numberimg.setBackground(drawable);
			
			/*if(list1.get(arg0).getPhoto().contains("http://")){
				ImageLoader.getInstance().displayImage(
		                list1.get(arg0).getPhoto(), holderview.numberimg,
		                options);
			}else{
				ImageLoader.getInstance().displayImage(
		                "http://120.27.98.128:9110/img/imgheadpic/" + list1.get(arg0).getPhoto(), holderview.numberimg,
		                options);
			}*/
		}else if(arg0 == 1){
			holderview.numbertext.setVisibility(View.GONE);
			holderview.numberimg.setVisibility(View.VISIBLE);
			
			drawable = resources.getDrawable(R.drawable.qinmidu_no2);
			holderview.numberimg.setBackground(drawable);
			
			/*if(list1.get(arg0).getPhoto().contains("http://")){
				ImageLoader.getInstance().displayImage(
		                list1.get(arg0).getPhoto(), holderview.numberimg,
		                options);
			}else{
				ImageLoader.getInstance().displayImage(
		                "http://120.27.98.128:9110/img/imgheadpic/" + list1.get(arg0).getPhoto(), holderview.numberimg,
		                options);
			}*/
		}else if(arg0 == 2){
			holderview.numbertext.setVisibility(View.GONE);
			holderview.numberimg.setVisibility(View.VISIBLE);
			
			drawable = resources.getDrawable(R.drawable.qinmidu_no3);
			holderview.numberimg.setBackground(drawable);
			
			/*if(list1.get(arg0).getPhoto().contains("http://")){
				ImageLoader.getInstance().displayImage(
		                list1.get(arg0).getPhoto(), holderview.numberimg,
		                options);
			}else{
				ImageLoader.getInstance().displayImage(
		                "http://120.27.98.128:9110/img/imgheadpic/" + list1.get(arg0).getPhoto(), holderview.numberimg,
		                options);
			}*/
		}else if(arg0 > 2){
			holderview.numbertext.setVisibility(View.VISIBLE);
			holderview.numberimg.setVisibility(View.GONE);
			holderview.numbertext.setText("NO." + (arg0 + 1));
		}
		holderview.name.setText(list1.get(arg0).getNickname());
		if(!"-1".equals(list1.get(arg0).getQinmvalue())){
			DecimalFormat df = new DecimalFormat("#");
			String a=df.format(Double.parseDouble(list1.get(arg0).getQinmvalue().toString()));
			holderview.qinmivalue.setText(a);
		}
		if(list1.get(arg0).getPhoto().contains("http://")){
			ImageLoader.getInstance().displayImage(
	                list1.get(arg0).getPhoto(), holderview.touxiang,
	                options);
		}
		return arg1;
	}
	//写一个类 用来初始化adapter想要初始化内容
    class HolderView {
		private TextView name,numbertext,qinmivalue;
		private RoundImageView touxiang;
		private ImageView numberimg;
    }
     
}
