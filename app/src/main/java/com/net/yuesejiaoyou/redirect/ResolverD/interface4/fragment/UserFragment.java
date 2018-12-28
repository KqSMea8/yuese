package com.net.yuesejiaoyou.redirect.ResolverD.interface4.fragment;
/**
 * 发现首页
 */


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.activity.SearchActivity;
import com.net.yuesejiaoyou.redirect.ResolverA.uiface.VFragment_attend_01066;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;


@SuppressLint("ResourceAsColor")
public class UserFragment extends Fragment {
    private Context mContext;
    private View mBaseView;
    private GifImageView search;

    private ImageView search_lr;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mContext = getActivity();

        mBaseView = inflater.inflate(R.layout.vfragment_01066, null);
        //得到AssetManager
        AssetManager mgr = mContext.getAssets();

        search = mBaseView.findViewById(R.id.search);
        search.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(mContext, SearchActivity.class);//编辑资料
                startActivity(intent);
            }
        });

        search_lr = mBaseView.findViewById(R.id.search_lr);
        search_lr.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(mContext, SearchActivity.class);//编辑资料
                startActivity(intent);
            }
        });

        //构造适配器
        LogUtil.d("ttt", "---" + Util.iszhubo);
        List<Fragment> fragments = new ArrayList<Fragment>();
        if ("0".equals(Util.iszhubo)) {
            fragments.add(new ActiveFragment());
            fragments.add(new RecommendFragment());
            fragments.add(new NewFragment());
            fragments.add(new VFragment_attend_01066());
        } else {
            fragments.add(new BoyFragment());
            fragments.add(new RecommendFragment());
            fragments.add(new NewFragment());
            fragments.add(new ActiveFragment());
        }

        ViewPager viewpager = mBaseView.findViewById(R.id.viewpager1);
        FragAdapter adapter = new FragAdapter(getActivity().getSupportFragmentManager(), fragments);
        viewpager.setAdapter(adapter);
        TabLayout mTabLayout = mBaseView.findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(viewpager);
        viewpager.setCurrentItem(0);

        return mBaseView;
    }

    public class FragAdapter extends FragmentPagerAdapter {

        private List<Fragment> mFragments;

        public FragAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            mFragments = fragments;
        }

        @Override
        public Fragment getItem(int arg0) {
            return mFragments.get(arg0);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (!"0".equals(Util.iszhubo)) {
                switch (position) {
                    case 0:
                        return "用户";
                    case 1:
                        return "推荐";
                    case 2:
                        return "新人";
                    case 3:
                        return "活跃";
                    default:
                        return "推荐";
                }
            } else {
                switch (position) {
                    case 0:
                        return "活跃";
                    case 1:
                        return "推荐";
                    case 2:
                        return "新人";
                    case 3:
                        return "关注";
                    default:
                        return "推荐";
                }
            }
        }

    }


}
