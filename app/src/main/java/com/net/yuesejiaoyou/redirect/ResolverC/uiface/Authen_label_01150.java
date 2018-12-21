package com.net.yuesejiaoyou.redirect.ResolverC.uiface;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/*import com.net.yuesejiaoyou.R;
import com.example.vliao.getset.Tag;
import com.example.vliao.interface3.UsersThread_01162;
import com.example.vliao.interface4.LogDetect;
import com.example.vliao.interface4.MyAdapter_01162_1;
import com.example.vliao.interface4.MyGridview;
import com.example.vliao.interface4.MyLayoutmanager;
import com.example.vliao.interface4.Recycle_item;*/

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.Tag;
import com.net.yuesejiaoyou.redirect.ResolverC.interface3.UsersThread_01162C;
import com.net.yuesejiaoyou.redirect.ResolverC.interface4.MyAdapter_01162_1;
import com.net.yuesejiaoyou.redirect.ResolverC.interface4.MyGridview;
import com.net.yuesejiaoyou.redirect.ResolverC.interface4.MyLayoutmanager;
import com.net.yuesejiaoyou.redirect.ResolverC.interface4.Recycle_item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Authen_label_01150 extends Activity implements OnClickListener {

	private ImageView back;
	private EditText xinnicheng;
	private TextView confirm_tm;
	Bundle bundle;
	private MyGridview gridView;
	private Intent intent= new Intent();
	String a = "";
	private List<Tag> list=new ArrayList<Tag>();
	private List<String> list1=new ArrayList<String>();
	private RecyclerView grview;
	private int t1=0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.renzheng_label_01150);
		grview = (RecyclerView) findViewById(R.id.grview);
		back= (ImageView) findViewById(R.id.back);
		back.setOnClickListener(this);
		confirm_tm= (TextView) findViewById(R.id.confirm_tm);
		confirm_tm.setOnClickListener(this);
		String mode="evalue_search2";
		String params[]={"1"};
		UsersThread_01162C b=new UsersThread_01162C(mode,params,requestHandler);
		Thread thread=new Thread(b.runnable);
		thread.start();
	}
	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		
		switch (id) {
		case R.id.back:
			finish();
			break;
		case R.id.confirm_tm:
			/*LogDetect.send(LogDetect.DataType.specialType,"01150 ====================:","点击确定");
			if(label_1.size() == 0){
				Toast.makeText(Authen_label_01150.this, "Please select image label !", Toast.LENGTH_SHORT).show();
			}else if(label_1.size() >= 2){
				String a = label_1.get(label_1.size()-1).getName();
				String b = label_1.get(label_1.size()-2).getName();
				LogDetect.send(LogDetect.DataType.specialType, "获取标签a：",a);
				LogDetect.send(LogDetect.DataType.specialType, "获取标签b：",b);
				bundle = new Bundle();
				bundle.putString("biaoqian1", a);
				bundle.putString("biaoqian2", b);
				intent.putExtras(bundle);
				setResult(204,  intent);
				this.finish();
			}else if(label_1.size() == 1){
                String a = label_1.get(label_1.size()-1).getName();
                String b = "";
                LogDetect.send(LogDetect.DataType.specialType, "获取标签a：",a);
                LogDetect.send(LogDetect.DataType.specialType, "获取标签b：",b);
                bundle = new Bundle();
                bundle.putString("biaoqian1", a);
                bundle.putString("biaoqian2", b);
                intent.putExtras(bundle);
                setResult(204,  intent);
                this.finish();
            }*/

			int pos1=0;
			int pos2=0;
			int pos3=0;
			int pos4=0;
			int a=list1.size();
			String evalue="";
			if(a==0){
				Toast.makeText(Authen_label_01150.this,"请选择标签", Toast.LENGTH_LONG).show();
				return;
			}else if(a==1){
				pos1= Integer.parseInt(list1.get(0));
				evalue=list.get(pos1).getRecord()+"@"+list.get(pos1).getColor();
			}else if(a==2){
				pos1= Integer.parseInt(list1.get(0));
				pos2= Integer.parseInt(list1.get(1));
				evalue=list.get(pos1).getRecord()+"@"+list.get(pos1).getColor()+"卍"+list.get(pos2).getRecord()+"@"+list.get(pos2).getColor();
			}
			bundle = new Bundle();
			bundle.putString("biaoqian", evalue);
			intent.putExtras(bundle);
			setResult(204,  intent);
			finish();
			break;
		
		
		
		
	}
	}



	private Handler requestHandler = new Handler() {
		  @Override
		  public void handleMessage(Message msg) {
		    switch (msg.what) {
				case 204:
					String json = (String) msg.obj;
					JSONArray jsonArray = null;
					LogDetect.send(LogDetect.DataType.basicType,"01162---json返回",json);
					try {
						jsonArray = new JSONArray(json);
						for(int i=0;i<jsonArray.length();i++){
							JSONObject object1 = jsonArray.getJSONObject(i);
							Tag bean = new Tag();
							bean.setRecord(object1.getString("lab_name"));
							bean.setColor(object1.getString("labcolor"));
							list.add(bean);
						}
						MyLayoutmanager layoutmanager = new MyLayoutmanager();
						grview.setLayoutManager(layoutmanager);
						final MyAdapter_01162_1 adapter1 = new MyAdapter_01162_1(null, Authen_label_01150.this, false, list);
						grview.addItemDecoration(new Recycle_item(20));
						//LogDetect.send(LogDetect.DataType.basicType,"01162---json返回","准备进入适配器");
						grview.setAdapter(adapter1);
						LogDetect.send(LogDetect.DataType.basicType,"01162---json返回","准备进入适配器");
						adapter1.setOnItemClickLitsener(new MyAdapter_01162_1.onItemClickListener() {
							@Override
							public void onItemClick(View view, int position) {

								for(int i=0;i<list.size();i++){
									if(list.get(i).isIs_check()&&t1<3){
										t1++;
									}
								}
								if(!list.get(position).isIs_check()){
									GradientDrawable drawable = (GradientDrawable) view.getBackground();
									list1.add(position+"");
									//LogDetect.send(LogDetect.DataType.basicType,"01162-----list1长度和内容","list1长度"+list1.size()+"--list1内容"+list);
									if(list1.size()>2){
										//drawable.setColor(Color.parseColor(list.get(Integer.parseInt(list1.get(0))).getColor()));
										GradientDrawable drawable1 = (GradientDrawable) grview.getChildAt(Integer.parseInt(list1.get(0))).getBackground();
										drawable1.setColor(Color.parseColor("#CCD7DB"));
										drawable.setColor(Color.parseColor(list.get(position).getColor()));
										list.get(Integer.parseInt(list1.get(0))).setIs_check(false);
										list.get(position).setIs_check(true);
										list1.remove(0);
									}else{
										drawable.setColor(Color.parseColor(list.get(position).getColor()));
										list.get(position).setIs_check(true);
										//LogDetect.send(LogDetect.DataType.basicType,"01162-----已选中--变颜色","当前position"+position+"---当前对应颜色"+list.get(position).getColor());
									}
								}else {
									for (int i1=0;i1<list1.size();i1++){
										if(list1.get(i1).equals(String.valueOf(position))){
											list1.remove(String.valueOf(position));
										}
									}
									GradientDrawable drawable = (GradientDrawable) view.getBackground();
									drawable.setColor(Color.parseColor("#CCD7DB"));
									//LogDetect.send(LogDetect.DataType.basicType,"01162-----取消选中变回本来颜色",position);
									list.get(position).setIs_check(false);
								}
							}

							@Override
							public void onItemLongClick(View view, int position) {

							}
						});

					} catch (JSONException e) {
						e.printStackTrace();
					}
					break;

		      
		      
		    
		    }
		 }
	};












}
