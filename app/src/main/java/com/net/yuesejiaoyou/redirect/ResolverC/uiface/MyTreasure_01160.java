package com.net.yuesejiaoyou.redirect.ResolverC.uiface;
/**
 * 发现首页
 */


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/*import com.net.yuesejiaoyou.R;
import com.example.vliao.interface4.LogDetect;*/
import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.ArrayList;
import java.util.List;


@SuppressLint("ResourceAsColor")
public class MyTreasure_01160 extends FragmentActivity implements OnClickListener {

	private DisplayImageOptions options;

	private FragAdapter adapter;
	private ViewPager vp;

	private TextView ilike,ibuy;
	private Button button_more_moremodify;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_treasure_01160);

		/*mContext = getActivity();
		
		mBaseView = inflater.inflate(R.layout.vfragment2_01066, null);
       */
		button_more_moremodify = (Button) findViewById(R.id.button_more_moremodify);
		button_more_moremodify.setOnClickListener(this);

		ilike = (TextView) findViewById(R.id.ilike);
		ibuy = (TextView) findViewById(R.id.ibuy);

		ilike.setOnClickListener(this);
		ibuy.setOnClickListener(this);

		//构造适配器
		List<Fragment> fragments=new ArrayList<Fragment>();
		fragments.add(new MyTeasure_like_01160());
		fragments.add(new MyTeasure_buy_01160());

		adapter = new FragAdapter(getSupportFragmentManager(), fragments);

		//设定适配器
		vp = (ViewPager)findViewById(R.id.viewpager);
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
					ilike.setTextColor(getResources().getColor(R.color.vhongse));
					ibuy.setTextColor(getResources().getColor(R.color.vheise));
				}else if(position==1){
					ilike.setTextColor(getResources().getColor(R.color.vheise));
					ibuy.setTextColor(getResources().getColor(R.color.vhongse));
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
		LogDetect.send(LogDetect.DataType.specialType, "Fragment_1: ", "here");

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
	            case R.id.ilike://发现热门
					ilike.setTextColor(getResources().getColor(R.color.vhongse));
					ibuy.setTextColor(getResources().getColor(R.color.vheise));
					vp.setCurrentItem(0,true);
	                break;
	            case R.id.ibuy://
					ilike.setTextColor(getResources().getColor(R.color.vheise));
					ibuy.setTextColor(getResources().getColor(R.color.vhongse));
					vp.setCurrentItem(1,true);
					break;
				case R.id.button_more_moremodify:
					finish();
					break;
	        }
	}
}
