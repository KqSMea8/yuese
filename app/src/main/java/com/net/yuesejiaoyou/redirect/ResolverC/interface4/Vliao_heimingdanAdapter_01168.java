package com.net.yuesejiaoyou.redirect.ResolverC.interface4;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/*import com.net.yuesejiaoyou.R;
import com.example.vliao.getset.Vliao1_01168;
import com.example.vliao.interface3.UsersThread_01168;
import com.example.vliao.util.Util;*/
import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.Vliao1_01168;
import com.net.yuesejiaoyou.redirect.ResolverC.interface3.UsersThread_01168C;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;


public class Vliao_heimingdanAdapter_01168 extends BaseAdapter {
	List<Vliao1_01168> list1;
	Context context;
	HolderView holderview;
	private Handler handler;
	private DisplayImageOptions options;
	public Vliao_heimingdanAdapter_01168(List<Vliao1_01168> list1, Context context, Handler handler) {
		super();
		this.list1 = list1;
		this.context = context;
		this.handler=handler;
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
	public View getView(final int arg0, View arg1, ViewGroup arg2) {
		if(arg1==null){
			arg1= LayoutInflater.from(context).inflate(R.layout.vliao_heimingdanlist_01168, null);
		  //初始化控件
			holderview=new HolderView();
			holderview.name=(TextView) arg1.findViewById(R.id.name);
			holderview.quxiao=(TextView) arg1.findViewById(R.id.quxiao);
			holderview.touxiang=(RoundImageView) arg1.findViewById(R.id.touxiang);
			//holderview.time2=(TextView) arg1.findViewById(R.id.time2);
			arg1.setTag(holderview);
		}
		//如果该view存在 则加载 不存在则添加
		else{
			if(arg1.getTag() instanceof HolderView){
				holderview=(HolderView) arg1.getTag();
			}else{
				holderview=new HolderView();
				holderview.name=(TextView) arg1.findViewById(R.id.name);
				holderview.quxiao=(TextView) arg1.findViewById(R.id.quxiao);
				holderview.touxiang=(RoundImageView) arg1.findViewById(R.id.touxiang);
				arg1.setTag(holderview);
			}
		}
		//holderview.name.setText(""+list1.get(arg0).getUsername());
		holderview.name.setText(""+list1.get(arg0).getNickname());
		//holderview.name.setText(""+list1.get(arg0).getUsername());
		/*ImageLoader.getInstance().displayImage(
				"http://139.129.38.194:9000/img/imgheadpic/" + list1.get(arg0).getPhoto(), holderview.touxiang,
				options);
		*///用户头像
		
		/*ImageLoader.getInstance().displayImage(
                "http://120.27.98.128:9112/img/imgheadpic/" + list1.get(arg0).getPhoto(), holderview.touxiang,
                options);*/
		ImageLoader.getInstance().displayImage(
               list1.get(arg0).getPhoto(), holderview.touxiang,
                options);
		/*holderView.dianzan.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				
				iscomment=false;
				pos=position;
				new GetDataTask().execute();
				updataView(pos,listview);
				handler.sendMessage(handler.obtainMessage(10, position));
			}
		});*/
		holderview.quxiao.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg1) {
				// TODO Auto-generated method stub	
				//开启线程，把该选项的id删除掉，然后重新取出黑名单信息。
				//开启线程，把该选项的id删除掉，然后重新取出黑名单信息。
				String mode="quxiao";
				String[]params={Util.userid,list1.get(arg0).getTarget_id()+""};
				LogDetect.send(LogDetect.DataType.specialType, "Aiba_tixianAdapter_01168","删除过程======");
				UsersThread_01168C b=new UsersThread_01168C(mode, params, handler);
				Thread thread=new Thread(b.runnable);
				//LogDetect.send(LogDetect.DataType.specialType, "Aiba_tixianAdapter_01168","删除========");
				thread.start();
				//LogDetect.send(LogDetect.DataType.specialType, "Aiba_tixianAdapter_01168","删除结束=====");
//				
			}
		});
		
		
		
		
		
	    /*String []a=list1.get(arg0).getTime().split("\\s");
		holderview.tujing.setText(list1.get(arg0).getType());
		holderview.jine.setText(""+list1.get(arg0).getMoney()+"V");
		holderview.time1.setText(a[0]);
		holderview.time2.setText(a[1]);*/
		
		return arg1;
	}
	//写一个类 用来初始化adapter想要初始化内容
    class HolderView {
		private TextView name,quxiao;
		private RoundImageView touxiang;
    }
     
}
