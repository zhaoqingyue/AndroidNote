package com.study.commonlib.ui.recycleradapter.divider;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.study.commonlib.util.utilcode.SizeUtils;

/**
 * Created by zhao.qingyue on 2019/7/2.
 * 没有背景，只有间距的分割线
 */
public class SpaceDecoration extends RecyclerView.ItemDecoration {

    private  int mFirstTop,mLeft,mTop,mRight,mBottom;

    public SpaceDecoration(int firstTop, int left, int top, int right, int bottom) {
        this.mFirstTop = firstTop;
        this.mLeft = left;
        this.mTop = top;
        this.mRight = right;
        this.mBottom = bottom;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State
            state) {
        int position = parent.getChildLayoutPosition(view);
        if (position == 0) {
            outRect.set(0, SizeUtils.dp2px(mFirstTop), 0, SizeUtils.dp2px(mBottom));
        } else {
            outRect.set(0, 0, 0, SizeUtils.dp2px(mBottom));
        }
    }
}
