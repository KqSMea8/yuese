package com.net.yuesejiaoyou.redirect.ResolverD.interface4.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.activity.RankActivity;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.activity.SearchActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


@SuppressLint("ResourceAsColor")
public class UserFragment extends BaseFragment {

    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.tabs)
    TabLayout tabLayout;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<Fragment> fragments = new ArrayList<Fragment>();
        if ("0".equals(Util.iszhubo)) {
            fragments.add(new ActiveFragment());
            fragments.add(new RecommendFragment());
            fragments.add(new NewFragment());
            fragments.add(new FocusFragment());
        } else {
            fragments.add(new BoyFragment());
            fragments.add(new RecommendFragment());
            fragments.add(new NewFragment());
            fragments.add(new ActiveFragment());
        }

        FragAdapter adapter = new FragAdapter(getActivity().getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(4);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(0);
    }

    @Override
    protected int getContentView() {
        return R.layout.vfragment_01066;
    }

    @OnClick(R.id.search_lr)
    public void searchClick() {
        startActivity(SearchActivity.class);
    }


    @OnClick(R.id.tv_rank)
    public void rankClick() {
        startActivity(RankActivity.class);
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
