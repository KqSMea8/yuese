package com.net.yuesejiaoyou.redirect.ResolverB.interface4;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2018/3/13.
 */

public class Recycle_item extends RecyclerView.ItemDecoration {
	int mSpace;

	public Recycle_item(int space) {
		super();
		this.mSpace=space;
	}

	@Override
	public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
		super.getItemOffsets(outRect, view, parent, state);
		outRect.bottom=mSpace;
		;outRect.top=mSpace;
		outRect.left=mSpace;
		outRect.right=mSpace;

	}
}
