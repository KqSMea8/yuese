package com.net.yuesejiaoyou.activity;
/**
 * 发现首页
 */


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

/*import com.net.yuesejiaoyou.R;
import com.example.vliao.interface4.LogDetect;*/
import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.redirect.ResolverC.uiface.MyTeasure_buy_01160;
import com.net.yuesejiaoyou.redirect.ResolverC.uiface.MyTeasure_like_01160;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;


@SuppressLint("ResourceAsColor")
public class CollectActivity extends BaseActivity {


	private FragAdapter adapter;
	private ViewPager vp;

	private TextView ilike,ibuy;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ilike = (TextView) findViewById(R.id.ilike);
		ibuy = (TextView) findViewById(R.id.ibuy);

		List<Fragment> fragments=new ArrayList<Fragment>();
		fragments.add(new MyTeasure_like_01160());
		fragments.add(new MyTeasure_buy_01160());

		adapter = new FragAdapter(getSupportFragmentManager(), fragments);

		vp = (ViewPager)findViewById(R.id.viewpager);
		vp.setAdapter(adapter);
		vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			}

			@Override
			public void onPageSelected(int position) {
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


			}
		});

	}

	@Override
	protected int getContentView() {
		return R.layout.activity_collect;
	}

	@OnClick(R.id.ilike)
	public void likeClick(){
		ilike.setTextColor(getResources().getColor(R.color.vhongse));
		ibuy.setTextColor(getResources().getColor(R.color.vheise));
		vp.setCurrentItem(0,true);
	}

	@OnClick(R.id.ibuy)
	public void buyClick(){
		ilike.setTextColor(getResources().getColor(R.color.vheise));
		ibuy.setTextColor(getResources().getColor(R.color.vhongse));
		vp.setCurrentItem(1,true);
	}

	@OnClick(R.id.button_more_moremodify)
	public void backClick(){
		finish();
	}

	public class FragAdapter extends FragmentPagerAdapter {

		private List<Fragment> mFragments;

		public FragAdapter(FragmentManager fm, List<Fragment> fragments) {
			super(fm);
			mFragments=fragments;
		}

		@Override
		public Fragment getItem(int arg0) {
			return mFragments.get(arg0);
		}

		@Override
		public int getCount() {
			return mFragments.size();
		}

	}

}
