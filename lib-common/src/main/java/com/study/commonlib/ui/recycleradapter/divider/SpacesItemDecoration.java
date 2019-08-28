package com.study.commonlib.ui.recycleradapter.divider;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.study.commonlib.util.utilcode.SizeUtils;

/**
 * Created by zhao.qingyue on 2019/7/2.
 * RecyclerView间距 & 分割线
 */
public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int left;
    private int top;
    private int right;
    private int bottom;
    private int firstTop;
    private int spanCount;

    public SpacesItemDecoration(int space) {
        this.left = space;
        this.top = space;
        this.right = space;
        this.bottom = space;
    }

    public SpacesItemDecoration(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    public SpacesItemDecoration(int left, int top, int right, int bottom, int firstTop, int spanCount) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.firstTop = firstTop;
        this.spanCount = spanCount;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = SizeUtils.dp2px(this.left);
        outRect.right = SizeUtils.dp2px(this.right);
        outRect.bottom = SizeUtils.dp2px(this.bottom);

        int position = parent.getChildLayoutPosition(view);
        if (this.spanCount > 0 && position / this.spanCount  == 0 && this.firstTop > 0) {
            outRect.top = SizeUtils.dp2px(this.firstTop);
        } else {
            outRect.top = SizeUtils.dp2px(this.top);
        }
    }
}
