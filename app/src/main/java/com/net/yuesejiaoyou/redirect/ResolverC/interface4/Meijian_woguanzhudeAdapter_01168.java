package com.net.yuesejiaoyou.redirect.ResolverC.interface4;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.vliaofans_01168;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;


@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
@SuppressLint("NewApi")
public class Meijian_woguanzhudeAdapter_01168 extends BaseAdapter {
List<vliaofans_01168> list1;
Context context;
HolderView holderview;
Resources resources;
Drawable drawable;

private DisplayImageOptions options;
	public Meijian_woguanzhudeAdapter_01168(List<vliaofans_01168> list1, Context context) {
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
			arg1= LayoutInflater.from(context).inflate(R.layout.meijian_woguanzhudelist_01168, null);
		  //初始化控件

			holderview=new HolderView();
			holderview.touxiang=(RoundImageView) arg1.findViewById(R.id.touxiang);
			holderview.userstatus=(ImageView) arg1.findViewById(R.id.userstatus);
			holderview.name=(TextView) arg1.findViewById(R.id.name);            //name
			holderview.qinmivalue=(TextView) arg1.findViewById(R.id.qinmivalue);//qinmivalue
			arg1.setTag(holderview);
		}
		//如果该view存在 则加载 不存在则添加
		else{
			if(arg1.getTag() instanceof HolderView){
				holderview=(HolderView) arg1.getTag();
			}else{
				holderview=new HolderView();
				LogDetect.send(LogDetect.DataType.specialType, "Vliao_woguanzhudeAdapter_01168","适配器b");
				holderview.touxiang=(RoundImageView) arg1.findViewById(R.id.touxiang);
				holderview.userstatus=(ImageView) arg1.findViewById(R.id.userstatus);
				holderview.name=(TextView) arg1.findViewById(R.id.name);
				holderview.qinmivalue=(TextView) arg1.findViewById(R.id.qinmivalue);
				arg1.setTag(holderview);
			}
		}
		LogDetect.send(LogDetect.DataType.specialType, "Vliao_woguanzhudeAdapter_01168","适配器c");
		holderview.name.setText(list1.get(arg0).getNickname());
		if(!"-1".equals(list1.get(arg0).getQinmvalue())){
			holderview.qinmivalue.setText(list1.get(arg0).getQinmvalue());
		}else{
			holderview.qinmivalue.setText("");
		}
		
		//用户头像
		/*ImageLoader.getInstance().displayImage(
		"http://139.129.38.194:9000/img/imgheadpic/" + list1.get(arg0).getPhoto(), holderview.touxiang,
		options);
		*/
		if(list1.get(arg0).getPhoto().contains("http://")){
			ImageLoader.getInstance().displayImage(
	                list1.get(arg0).getPhoto(), holderview.touxiang,
	                options);
		}
		
		if("在线".equals(list1.get(arg0).getStatus())){
			drawable = resources.getDrawable(R.drawable.online);
			holderview.userstatus.setBackground(drawable);
		}else if("离线".equals(list1.get(arg0).getStatus())){
			drawable = resources.getDrawable(R.drawable.offline);
			holderview.userstatus.setBackground(drawable);
		}else if("活跃的".equals(list1.get(arg0).getStatus())){
			drawable = resources.getDrawable(R.drawable.active);
			holderview.userstatus.setBackground(drawable);
		}else{
			drawable = resources.getDrawable(R.drawable.offline);
			holderview.userstatus.setBackground(drawable);
		}
		
		return arg1;
	}
	//写一个类 用来初始化adapter想要初始化内容
    class HolderView {
		private TextView name,qinmivalue;
		private RoundImageView touxiang;
		ImageView userstatus;
    }
     
}
