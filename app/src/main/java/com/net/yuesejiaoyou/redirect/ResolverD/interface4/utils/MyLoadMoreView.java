package com.net.yuesejiaoyou.redirect.ResolverD.interface4.utils;

import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.net.yuesejiaoyou.R;

/**
 * Created by admin on 2018/12/3.
 */

public class MyLoadMoreView extends LoadMoreView {
    @Override
    public int getLayoutId() {
        return R.layout.loadmore_load;
    }

    @Override
    protected int getLoadingViewId() {
        return R.id.load_more_loading_view;
    }

    @Override
    protected int getLoadFailViewId() {
        return R.id.load_more_load_fail_view;
    }

    @Override
    protected int getLoadEndViewId() {
        return R.id.load_more_load_end_view;
    }
}
