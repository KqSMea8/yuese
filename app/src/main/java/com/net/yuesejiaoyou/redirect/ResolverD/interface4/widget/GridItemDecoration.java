package com.net.yuesejiaoyou.redirect.ResolverD.interface4.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.net.yuesejiaoyou.redirect.ResolverD.interface4.utils.Tools;


/**
 * Created by admin on 2018/12/20.
 */

public class GridItemDecoration extends RecyclerView.ItemDecoration {
    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};

    int decorationWidth = 0;

    public GridItemDecoration(Context context) {
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        a.recycle();
        decorationWidth = Tools.dp2px(context, 1);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
    }

    private int getSpanCount(RecyclerView parent) {
        // 列数
        int spanCount = -1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        }
        return spanCount;
    }

    private boolean isLastColum(RecyclerView parent, int spanCount, View view) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        int pos = layoutManager.getPosition(view);
        if (layoutManager instanceof GridLayoutManager) {
            if ((pos + 1) % spanCount == 0) {// 如果是最后一列，则不需要绘制右边
                return true;
            }
        }
        return false;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State
            state) {
        super.getItemOffsets(outRect, view, parent, state);
        int spanCount = getSpanCount(parent);
        if (isLastColum(parent, spanCount, view))// 如果是最后一列，则不需要绘制右边
        {
            outRect.set(0, 0, decorationWidth, decorationWidth);
        } else {
            outRect.set(0, 0, decorationWidth, decorationWidth);
        }
    }
}