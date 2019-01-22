package com.net.yuesejiaoyou.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.activity.RankActivity;
import com.net.yuesejiaoyou.activity.SearchActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


@SuppressLint("ResourceAsColor")
public class RankItemFragment extends BaseFragment {

    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.tabs)
    TabLayout tabLayout;


    int type = 1;

    public RankItemFragment() {
        super();
    }

    public RankItemFragment(int type) {
        super();
        this.type = type;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<Fragment> fragments = new ArrayList<Fragment>();

        fragments.add(new RankFragment(type, 1));
        fragments.add(new RankFragment(type, 2));
        fragments.add(new RankFragment(type, 4));

        FragAdapter adapter = new FragAdapter(getChildFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(0);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_rankitem;
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
            switch (position) {
                case 0:
                    return "日榜";
                case 1:
                    return "周榜";
                case 2:
                    return "总榜";
                default:
                    return "总榜";
            }

        }

    }


}
