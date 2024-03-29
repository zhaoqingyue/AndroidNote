package com.study.androidnote.search.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.study.androidnote.search.R;
import com.study.biz.db.bean.SearchKey;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhao.qingyue on 2019/8/27.
 * 自定义搜索流式布局
 */
public class FlowLayout extends ViewGroup {

    private int limitLineCount = 3; //显示行数

    private boolean isLimitLine; //是否有行限制

    private boolean isOverFlow; //是否溢出

    /**
     * 存储所有子View
     */
    private List<List<View>> mAllChildViews = new ArrayList<>();
    /**
     * 每一行的高度
     */
    private List<Integer> mLineHeight = new ArrayList<>();

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //父控件传进来的宽度和高度以及对应的测量模式
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        // 如果当前ViewGroup的宽高为wrap_content的情况
        // 自己测量的宽度
        int width = 0;
        //自己测量的高度
        int height = 0;
        // 记录每一行的宽度，width不断取最大宽度
        int lineWidth = 0;
        // 每一行的高度，累加至height
        int lineHeight = 0;

        int lineCount = 1;
        isOverFlow = false;

        // 获取子view的个数
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            // 测量子View的宽和高
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            // 得到LayoutParams
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            // 子View占据的宽度
            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            // 子View占据的高度
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
            // 如果加入当前child，则超出最大宽度，则的到目前最大宽度给width，累加height 然后开启新行
            if (lineWidth + childWidth > sizeWidth) {
                if(isLimitLine) {
                    if(lineCount == this.limitLineCount + 1) {
                        setOverFlow(true);
                        break;
                    }
                }

                //对比得到最大的宽度
                width = Math.max(width, lineWidth);
                //重置lineWidth
                lineWidth = childWidth;
                //记录行高
                height += lineHeight;
                lineHeight = childHeight;
                lineCount ++;
            } else {//不换行情况
                //叠加行宽
                lineWidth += childWidth;
                //得到最大行高
                lineHeight = Math.max(lineHeight, childHeight);
            }
            //处理最后一个子View的情况
            if (i == childCount - 1) {
                width = Math.max(width, lineWidth);
                height += lineHeight;
            }
        }
        //wrap_content
        setMeasuredDimension(modeWidth == MeasureSpec.EXACTLY ? sizeWidth : width, modeHeight == MeasureSpec.EXACTLY ? sizeHeight : height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mAllChildViews.clear();
        mLineHeight.clear();
        //获取当前ViewGroup的宽度
        int width = getWidth();

        int lineWidth = 0;
        int lineHeight = 0;
        int lineCount = 1;
        //记录当前行的view
        List<View> lineViews = new ArrayList<View>();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            //如果需要换行
            if (childWidth + lineWidth + lp.leftMargin + lp.rightMargin > width) {
                if(isLimitLine) {
                    if(lineCount == this.limitLineCount + 1) {
                        break;
                    }
                }

                //记录LineHeight
                mLineHeight.add(lineHeight);
                //记录当前行的Views
                mAllChildViews.add(lineViews);
                //重置行的宽高
                lineWidth = 0;
//                lineHeight = childHeight + lp.topMargin + lp.bottomMargin;
                //重置view的集合
                lineViews = new ArrayList();
                lineCount ++;
            }
            lineWidth += childWidth + lp.leftMargin + lp.rightMargin;
            lineHeight = Math.max(lineHeight, childHeight + lp.topMargin + lp.bottomMargin);
            lineViews.add(child);
        }
        //处理最后一行
        mLineHeight.add(lineHeight);
        mAllChildViews.add(lineViews);

        //设置子View的位置
        int left = 0;
        int top = 0;
        //获取行数
        int lineNum  = mAllChildViews.size();
        for (int i = 0; i < lineNum; i++) {
            //当前行的views和高度
            lineViews = mAllChildViews.get(i);
            lineHeight = mLineHeight.get(i);
            for (int j = 0; j < lineViews.size(); j++) {
                View child = lineViews.get(j);
                //判断是否显示
                if (child.getVisibility() == View.GONE) {
                    continue;
                }
                MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
                int cLeft = left + lp.leftMargin;
                int cTop = top + lp.topMargin;
                int cRight = cLeft + child.getMeasuredWidth();
                int cBottom = cTop + child.getMeasuredHeight();

                // 修正rc margin
                if (cRight + lp.rightMargin > getWidth()) {
                    cRight = getWidth() - lp.rightMargin;
                }

                //进行子View进行布局
                child.layout(cLeft, cTop, cRight, cBottom);
                left += child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            }
            left = 0;
            top += lineHeight;
        }
    }

    /**
     * 与当前ViewGroup对应的LayoutParams
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if(isLimitLine) {
            // 这里写一个回调，activity中收到后判断isOverFlow，如果溢出，则显示点击更多样式，否则不显示。
        }
    }

    public void setAdapter(Context context, List<SearchKey> searchKeys) {
        if (context == null || searchKeys == null || searchKeys.isEmpty())
            return;

        LayoutInflater inflater =  LayoutInflater.from(context);
        for (int i=0; i<searchKeys.size(); i++) {
            int position = i;
            TextView tv = (TextView) inflater.inflate(R.layout.search_item_label_tv, this, false);
            tv.setText(searchKeys.get(i).getKeyWod());
            // 点击事件
            tv.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    String key = tv.getText().toString();
                    if (mListener != null) {
                        mListener.onItemClick(position, key);
                    }
                }
            });
            addView(tv);
        }
    }

    public void changeAdapter(Context context, List<SearchKey> searchKeys) {
        removeAllViews();
        setAdapter(context, searchKeys);
    }

    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public interface OnItemClickListener {

        void onItemClick(int position, String key);
    }

    public boolean isOverFlow() {
        return isOverFlow;
    }

    public void setOverFlow(boolean overFlow) {
        isOverFlow = overFlow;
    }

    public void setIsLimitLine(boolean isLimitLine) {
        this.isLimitLine = isLimitLine;
        this.requestLayout();
        this.invalidate();

    }
}
