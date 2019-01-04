package com.net.yuesejiaoyou.redirect.ResolverD.interface4.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/7/25.
 */
public abstract class BaseFragment extends Fragment {

    View rootView;
    SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(getContentView(), container, false);
        if (rootView == null) {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
        ButterKnife.bind(this, rootView);

        //私有数据
        sharedPreferences = getActivity().getSharedPreferences("Acitivity", Context.MODE_PRIVATE);

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(this);
    }

    @ColorInt
    public int getColor(@ColorRes int color){
        return getContext().getResources().getColor(color);
    }

    protected abstract int getContentView();

    protected void startActivity(Class<?> cls) {
        startActivity(new Intent(getContext(), cls));
    }

    protected void startActivityForResult(Class<?> cls, int resultCode) {
        startActivityForResult(new Intent(getContext(), cls), resultCode);
    }

    protected void showToast(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    protected void showToast(int msg) {
        showToast(getString(msg));
    }

}
