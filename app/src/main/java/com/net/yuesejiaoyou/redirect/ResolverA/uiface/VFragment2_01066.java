package com.net.yuesejiaoyou.redirect.ResolverA.uiface;
/**
 * 发现首页
 */
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;

/////////////////////A区跳转到B区，上传短视频
import com.net.yuesejiaoyou.redirect.ResolverB.uiface.UploadVideo_01158;



@SuppressLint("ResourceAsColor")
public class VFragment2_01066 extends Fragment implements OnClickListener{
	private Context mContext;
	private View mBaseView;
	private DisplayImageOptions options;
	private TextView newvideo,hotvideo,fujin;
	private View xrxian,hyxian;
	private FragAdapter adapter;
	private ViewPager vp;
	private TextView uploadvideoyun;
	private TextView attention;


	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mContext = getActivity();
		
		mBaseView = inflater.inflate(R.layout.vfragment2_01066, null);
        //得到AssetManager
		AssetManager mgr=mContext.getAssets();

		//根据路径得到Typeface
		Typeface tf= Typeface.createFromAsset(mgr, "fonts/arialbd.ttf");

		hotvideo = (TextView) mBaseView.findViewById(R.id.hotvideo);//活跃
		hotvideo.setTypeface(tf);
		hotvideo.setOnClickListener(this);

		newvideo = (TextView) mBaseView.findViewById(R.id.newvideo);//新入
		newvideo.setTypeface(tf);
		newvideo.setOnClickListener(this);

		attention = (TextView) mBaseView.findViewById(R.id.attention);
		attention.setOnClickListener(this);
		attention.setTypeface(tf);

		uploadvideoyun = (TextView) mBaseView.findViewById(R.id.uploadvideoyun);//新入
		if(!Util.iszhubo.equals("0")){
			uploadvideoyun.setVisibility(View.VISIBLE);
		}
		uploadvideoyun.setOnClickListener(this);

		//构造适配器
		List<Fragment> fragments=new ArrayList<Fragment>();
		fragments.add(new VFragment2_1_01066());
		fragments.add(new VFragment2_2_01066());
		fragments.add(new VFragment2_3_01160());
		adapter = new FragAdapter(getActivity().getSupportFragmentManager(), fragments);

		//设定适配器
		vp = (ViewPager)mBaseView.findViewById(R.id.viewpager);
		vp.setAdapter(adapter);
		vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				Log.d("测试代码", "onPageScrolled滑动中" + position);
			}

			@Override
			public void onPageSelected(int position) {
				Log.d("测试代码", "onPageSelected选中了" + position);
				if (position==0){
					hotvideo.setTextColor(mContext.getResources().getColor(R.color.vhongse));
					newvideo.setTextColor(mContext.getResources().getColor(R.color.vheise));
					attention.setTextColor(mContext.getResources().getColor(R.color.vheise));
				}else if(position==1){
					hotvideo.setTextColor(mContext.getResources().getColor(R.color.vheise));
					newvideo.setTextColor(mContext.getResources().getColor(R.color.vhongse));
					attention.setTextColor(mContext.getResources().getColor(R.color.vheise));
				}else if(position==2){
					hotvideo.setTextColor(mContext.getResources().getColor(R.color.vheise));
					newvideo.setTextColor(mContext.getResources().getColor(R.color.vheise));
					attention.setTextColor(mContext.getResources().getColor(R.color.vhongse));
				}
			}


			@Override
			public void onPageScrollStateChanged(int state) {

				if (state == ViewPager.SCROLL_STATE_DRAGGING) {
					//正在滑动   pager处于正在拖拽中

					Log.d("测试代码", "onPageScrollStateChanged=======正在滑动" + "SCROLL_STATE_DRAGGING");

				} else if (state == ViewPager.SCROLL_STATE_SETTLING) {
					//pager正在自动沉降，相当于松手后，pager恢复到一个完整pager的过程
					Log.d("测试代码", "onPageScrollStateChanged=======自动沉降" + "SCROLL_STATE_SETTLING");

				} else if (state == ViewPager.SCROLL_STATE_IDLE) {
					//空闲状态  pager处于空闲状态
					Log.d("测试代码", "onPageScrollStateChanged=======空闲状态" + "SCROLL_STATE_IDLE");
				}

			}
		});
		vp.setCurrentItem(1);
		LogDetect.send(LogDetect.DataType.specialType, "Fragment_1: ", "here");
		return mBaseView;
	}


	public class FragAdapter extends FragmentPagerAdapter {

		private List<Fragment> mFragments;

		public FragAdapter(FragmentManager fm, List<Fragment> fragments) {
			super(fm);
			// TODO Auto-generated constructor stub
			mFragments=fragments;
		}

		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			return mFragments.get(arg0);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mFragments.size();
		}

	}

	@Override
	public void onClick(View v) {
	        switch(v.getId()){
	            case R.id.hotvideo://发现热门
					hotvideo.setTextColor(mContext.getResources().getColor(R.color.vhongse));
					newvideo.setTextColor(mContext.getResources().getColor(R.color.vheise));
					attention.setTextColor(mContext.getResources().getColor(R.color.vheise));
					vp.setCurrentItem(0,true);
	                break;
	            case R.id.newvideo://
					hotvideo.setTextColor(mContext.getResources().getColor(R.color.vheise));
					newvideo.setTextColor(mContext.getResources().getColor(R.color.vhongse));
					attention.setTextColor(mContext.getResources().getColor(R.color.vheise));
					vp.setCurrentItem(1,true);
					break;
				case R.id.attention: //关注
					hotvideo.setTextColor(mContext.getResources().getColor(R.color.vheise));
					newvideo.setTextColor(mContext.getResources().getColor(R.color.vheise));
					attention.setTextColor(mContext.getResources().getColor(R.color.vhongse));
					vp.setCurrentItem(2,true);
					break;
				case R.id.uploadvideoyun:
					/////////////////////A区跳转到B区，上传短视频
					Intent intent = new Intent();
					intent.setClass(mContext, UploadVideo_01158.class);
					startActivity(intent);
				//Toast.makeText(mContext,"等待实现跳转到发布视频页面",Toast.LENGTH_SHORT).show();
					break;

	        }
	}
}
