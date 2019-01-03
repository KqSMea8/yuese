package com.net.yuesejiaoyou.redirect.ResolverC.interface4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/*import com.net.yuesejiaoyou.R;
import com.example.vliao.getset.BillBean;*/

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.BillBean;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class Vliao_tixianAdapter_01168 extends BaseAdapter {
List<BillBean> list1;
Context context;
HolderView holderview;
	public Vliao_tixianAdapter_01168(List<BillBean> list1, Context context) {
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
			arg1= LayoutInflater.from(context).inflate(R.layout.vliao_tixianlist_01168, null);
		  //初始化控件
			holderview=new HolderView();
			holderview.tixian=(TextView) arg1.findViewById(R.id.tixian);
			holderview.jine=(TextView) arg1.findViewById(R.id.jine);
			holderview.time1=(TextView) arg1.findViewById(R.id.time1);
			holderview.time2=(TextView) arg1.findViewById(R.id.time2);
			holderview.zhuangtai=(ImageView) arg1.findViewById(R.id.zhuangtai);
			//status
			arg1.setTag(holderview);
		}
		//如果该view存在 则加载 不存在则添加
		else{
			if(arg1.getTag() instanceof HolderView){
				holderview=(HolderView) arg1.getTag();
			}else{
				holderview=new HolderView();
				holderview.tixian=(TextView) arg1.findViewById(R.id.tixian);
				holderview.jine=(TextView) arg1.findViewById(R.id.jine);
				holderview.time1=(TextView) arg1.findViewById(R.id.time1);
				holderview.time2=(TextView) arg1.findViewById(R.id.time2);
				holderview.zhuangtai=(ImageView) arg1.findViewById(R.id.zhuangtai);
				arg1.setTag(holderview);
			}
		}
	    //String []a=list1.get(arg0).getTime().split("\\s");
		String[] time2 = {};
		if(!list1.get(arg0).getTime().equals("")){
			time2 = list1.get(arg0).getTime().split(" ");
			holderview.time1.setText(Vliao_tixianAdapter_01168.dateToWeek(time2[0]));
			holderview.time2.setText(time2[0]);
			//holderview.time2.setText(time2[1]);//Vliao_tixianAdapter_01168
		}
		//holderview.tixian.setText(list1.get(arg0).getStatus());
		//holderview.jine.setText("$"+list1.get(arg0).getMoney());
		holderview.jine.setText(""+list1.get(arg0).getCash());
		//holderview.time1.setText(a[0]);
		//holderview.time2.setText(a[1]);
		String str = list1.get(arg0).getStatus().toString();
		if(str.equals("已提现")){
			holderview.zhuangtai.setBackgroundResource(R.drawable.enchashment_status_success_icon);
		}else if(str.equals("不通过")){
			holderview.zhuangtai.setBackgroundResource(R.drawable.enchashment_status_refuse_icon);
		}else{
			holderview.zhuangtai.setBackgroundResource(R.drawable.enchashment_status_wait_icon);
		}
		return arg1;
	}
	//写一个类 用来初始化adapter想要初始化内容
    class HolderView {
    	//年月日、时分秒、提现状态、金额
		private TextView time1,time2,tixian,jine;
		private ImageView zhuangtai;
    }
    
    public static String dateToWeek(String datetime){
    	SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
    	String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
    	Calendar cal = Calendar.getInstance();//获得一个日历
    	Date datet = null;

    	int w = cal.get(Calendar.DAY_OF_WEEK) - 1;//指示一周中的某天
    	if(w < 0){
    		w = 0;
    	}
    	return weekDays[w];
    }
     
}
