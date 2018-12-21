package com.net.yuesejiaoyou.redirect.ResolverC.interface4;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap.Config;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/*import com.net.yuesejiaoyou.R;
import com.example.vliao.getset.Bills_01165;
import com.example.vliao.interface4.LogDetect;*/
import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.Bills_01165;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class My_V_ListAdapter_01165 extends BaseAdapter {

	private Context context;
	private List<Bills_01165> articles;
	private DisplayImageOptions options;
	private Activity activity;
	private ListView listview;
	private HolderView holderView = null;
	private int m;
	protected Handler requesetHandler;
	public My_V_ListAdapter_01165(Context context, ListView mlistview,
                                  Activity mactivity, List<Bills_01165> articles, Handler requesetHandler) {
        LogDetect.send(LogDetect.DataType.specialType, "My_V__ListAdapter适配器: ","My_V__ListAdapter_01165()");
        
		this.context = context;
		this.activity = mactivity;
		this.listview = mlistview;
		this.articles = articles;
		this.requesetHandler=requesetHandler;
		options = new DisplayImageOptions.Builder().cacheInMemory(true)
				.cacheOnDisc(true).bitmapConfig(Config.RGB_565).build();
        
	}
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return articles.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return articles.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView==null){
			//在listView内初始化
			holderView = new HolderView();
			convertView= LayoutInflater.from(context).inflate(R.layout.my_v_yangshi_01165,null);
			//Details d = new Details();
			holderView.touxiang = (ImageView)convertView.findViewById(R.id.touxiang);
			holderView.nickname = (TextView)convertView.findViewById(R.id.nickname);
			holderView.time = (TextView)convertView.findViewById(R.id.time);
			holderView.time2 = (TextView)convertView.findViewById(R.id.time2);
			holderView.pay = (TextView)convertView.findViewById(R.id.pay);
			holderView.pay2 = (TextView)convertView.findViewById(R.id.pay2);
			holderView.shouzhi = (TextView)convertView.findViewById(R.id.shouzhi);
			holderView.shouzhi2 = (TextView)convertView.findViewById(R.id.shouzhi2);
			holderView.type = (TextView)convertView.findViewById(R.id.type);
			convertView.setTag(holderView);
			//return convertView;
		}else{
			//使用这句内存消耗小
			if(convertView.getTag() instanceof HolderView){
				holderView=(HolderView) convertView.getTag();
			}
		}
		//在listView里赋值
		//ImageLoader.getInstance().displayImage(articles.get(position).getGood_img(), holderView.view_photo, options);
		//头像
		ImageLoader.getInstance().displayImage(articles.get(position).getTouxiang(), holderView.touxiang, options);
		//昵称
		holderView.nickname.setText(articles.get(position).getNickname());
		//日期+时间
		holderView.time.setText(articles.get(position).getTime());
		//日期
		
		String str = articles.get(position).getTime();
		String str1 = str.substring(0, 10);
		LogDetect.send(LogDetect.DataType.specialType, "1165_My_V_time2: ",str);
		LogDetect.send(LogDetect.DataType.specialType, "1165_My_V_time2: ",str1);
		holderView.time2.setText(str1);
		
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
//		Date date = new Date();
//			try {
//				date = sdf.parse(str);
//				LogDetect.send(LogDetect.DataType.specialType, "1165_My_V_time2: ",sdf.format(date));
//				String stime = sdf.format(date);
//				holderView.time2.setText(stime); 
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}			
		
		//V币数量
//		holderView.pay.setText(articles.get(position).getPay());
//		holderView.pay2.setText(articles.get(position).getPay()+" 悦币");
		//收入类型
		holderView.type.setText(articles.get(position).getType()+": ");
		//类型
//		if(articles.get(position).getType().equals("聊天")){
//			holderView.type.setText(articles.get(position).getType());
//		}else{
//			holderView.type.setText("Other Service");
//		}
		
		//收入还是支出
		//收入还是支出
		LogDetect.send(LogDetect.DataType.specialType, "1165_My_V_num: ",articles.get(position).getPay());
		LogDetect.send(LogDetect.DataType.specialType, "1165_My_V_money: ",articles.get(position).getMoney());
		if(articles.get(position).getPay().equals("")||articles.get(position).getPay().equals(null)){
			holderView.shouzhi.setText("我的悦币收入");
			holderView.shouzhi2.setText("收入金额");
			holderView.pay.setText(articles.get(position).getMoney());
			holderView.pay2.setText(articles.get(position).getMoney()+"悦币");
		}else{
			holderView.shouzhi.setText("我的悦币支出");
			holderView.shouzhi2.setText("支出金额");
			holderView.pay.setText(articles.get(position).getPay());
			holderView.pay2.setText(articles.get(position).getPay()+" 悦币");
		}
		
//		LogDetect.send(LogDetect.DataType.specialType, "1165_My_V_time2: ",sdf.format(date));
		return convertView;
	}
	class HolderView {	//样式列表信息：昵称，在线状态，日期，通话是否成功。
		private ImageView touxiang;
		private TextView time,time2,pay,pay2,nickname,shouzhi,shouzhi2,type;
		
		
	}
}
