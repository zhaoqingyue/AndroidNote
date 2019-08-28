package com.study.commonlib.ui.recycleradapter.divider;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.study.commonlib.util.utilcode.SizeUtils;


/**
 * Created by zhao.qingyue on 2019/7/2.
 * 自定义分割线
 */
public class OnePxDecoration extends RecyclerView.ItemDecoration {
    private  int mFirstItemTop;
    private int mLeftRightPadding;
    private Drawable mDivider;

    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};

    public OnePxDecoration(Context context, int padding, int firstItemTop) {
        TypedArray ta = context.obtainStyledAttributes(ATTRS);
        this.mDivider = ta.getDrawable(0);
        mLeftRightPadding = SizeUtils.dp2px(padding);
        mFirstItemTop = SizeUtils.dp2px(firstItemTop);
        ta.recycle();
    }

    /**
     * 该方法是用来设置RecyclerView Item之间的间距的，
     * 间距可大于分割线
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State
            state) {
        int position = parent.getChildAdapterPosition(view);
        if(position == 1 && mFirstItemTop != 0){
            outRect.set(0, mFirstItemTop, 0, mDivider.getIntrinsicHeight());
        } else{
            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
        }
    }

    /**
     * 绘制分割线：
     * 原理是在两个Item之间添加矩形，该矩形长度与item一致
     */
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft() + mLeftRightPadding;
        int right = parent.getWidth() - parent.getPaddingRight() - mLeftRightPadding;
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) childView.getLayoutParams();
            int top = childView.getBottom() + layoutParams.bottomMargin;
            int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }
}
