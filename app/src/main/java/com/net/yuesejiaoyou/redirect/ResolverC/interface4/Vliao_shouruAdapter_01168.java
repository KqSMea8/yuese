package com.net.yuesejiaoyou.redirect.ResolverC.interface4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/*import com.net.yuesejiaoyou.R;
import com.example.vliao.getset.Vliao2_01168;*/

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.Vliao2_01168;

import java.text.DecimalFormat;
import java.util.List;


public class Vliao_shouruAdapter_01168 extends BaseAdapter {
List<Vliao2_01168> list1;
Context context;
HolderView holderview;
	public Vliao_shouruAdapter_01168(List<Vliao2_01168> list1, Context context) {
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
			arg1= LayoutInflater.from(context).inflate(R.layout.vliao_shourulist_01168, null);
		  //初始化控件
			holderview=new HolderView();
			holderview.tujing=(TextView) arg1.findViewById(R.id.tujing);
			holderview.jine=(TextView) arg1.findViewById(R.id.jine);
			//holderview.time1=(TextView) arg1.findViewById(R.id.time1);
			holderview.time2=(TextView) arg1.findViewById(R.id.time2);
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
				//holderview.time1=(TextView) arg1.findViewById(R.id.time1);
				holderview.time2=(TextView) arg1.findViewById(R.id.time2);
				arg1.setTag(holderview);
			}
		}
		/*String[] time2 = {};
		if(!list1.get(arg0).getTime().equals("")){
			//time2 = list1.get(arg0).getTime().split("\\s");
			//holderview.time1.setText(time2[0]);
			holderview.time2.setText(list1.get(arg0).getTime());
		}*/
		holderview.time2.setText(list1.get(arg0).getTime());
	    //String []a=list1.get(arg0).getTime().split("\\s");
		holderview.tujing.setText(list1.get(arg0).getType());
		DecimalFormat df = new DecimalFormat("#");
		//int a= Integer.parseInt(list1.get(arg0).getMoney().toString());
		Double a= Double.parseDouble(list1.get(arg0).getMoney().toString());
		//LogDetect.send(LogDetect.DataType.basicType,"01162---欠钱",list1.get(arg0).getMoney().toString());
		String ab=df.format(a);
		holderview.jine.setText(""+ab);
		return arg1;
	}
	//写一个类 用来初始化adapter想要初始化内容
    class HolderView {
		private TextView time2,tujing,jine;
    }
     
}
