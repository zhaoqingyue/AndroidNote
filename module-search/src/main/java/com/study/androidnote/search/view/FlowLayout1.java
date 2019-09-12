package com.study.androidnote.search.view;

import android.content.Context;
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
 * Created by zhao.qingyue on 2019/9/9.
 */

public class FlowLayout1 extends ViewGroup {

    protected List<List<View>> mAllViews = new ArrayList<List<View>>();
    protected List<Integer> mLineHeight = new ArrayList<Integer>();
    protected List<Integer> mLineWidth = new ArrayList<Integer>();
    private List<View> lineViews = new ArrayList<>();

//    private int limitLineCount = 3; //显示行数

    private boolean isLimitLine = false; //是否有行限制

    private int limitLineCount = -1;//最大行数
    private boolean isExceedingMaxLimit; //预设的子View是否超出了最大行数限制

    public void setLimitLineCount(int limitLineCount) {
        this.limitLineCount = limitLineCount;
    }

    public void setIsLimitLine(boolean isLimitLine) {
        this.isLimitLine = isLimitLine;
    }

    public FlowLayout1(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public FlowLayout1(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout1(Context context) {
        this(context, null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mAllViews.clear();//记录所有行的view
        mLineHeight.clear();//记录每一行的高度
        mLineWidth.clear();//记录每一行的宽度
        lineViews.clear();//记录每一行的view

        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        // wrap_content
        int width = 0;
        int height = 0;

        // 当前已用行宽高
        int lineWidth = 0;
        int lineHeight = 0;

        int cCount = getChildCount();
        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == View.GONE) {
                if (i == cCount - 1) {
                    width = Math.max(lineWidth, width);
                    height += lineHeight;
                }
                continue;
            }
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

            if (lineWidth + childWidth > sizeWidth - getPaddingLeft() - getPaddingRight()) {
                if (isLimitLine) {
                    if (limitLineCount > 0 && mAllViews.size() + 1 >= limitLineCount) { //+1是因为后面还有最后一行
                        isExceedingMaxLimit = true;
                        break;//超过最大行数跳出循环
                    }
                }

                //需要换行
                if (i == 0 && width == 0 && lineWidth == 0 && height == 0 && lineHeight == 0) {
                    //如果第一个子View就满足换行条件,那么width和height就是子View的宽高
                    width = lineWidth = childWidth;
                    height = lineHeight = childHeight;
                    lineViews.add(child);
                } else {
                    width = Math.max(width, lineWidth);//记录最大行宽
                    height += lineHeight;//累加包裹内容所需的高度
                }

                //换行前,保存当前行数据
                mLineHeight.add(lineHeight);
                mLineWidth.add(lineWidth);
                mAllViews.add(lineViews);

                //换行,新行数据初始化
                lineWidth = 0;//重新赋值行宽
                lineHeight = 0;//重新赋值行高
                lineViews = new ArrayList<View>();//创建新行

                if (i == 0 && width > 0 && height > 0) {
                    //如果第一个子View就满足换行条件并且数据已经保存,则不需要下面重复添加了
                    continue;
                }
            }

            //新行或者当前行继续添加子View
            lineWidth += childWidth;//累加行宽
            lineHeight = Math.max(lineHeight, childHeight);//取当前行最大高度作为行高
            lineViews.add(child);
        }

        //添加最后一行数据
        width = Math.max(lineWidth, width);//包裹内容所需的最大宽度
        height += lineHeight;//累加高度
        mLineHeight.add(lineHeight);
        mLineWidth.add(lineWidth);
        mAllViews.add(lineViews);

        setMeasuredDimension(
                // 父控件宽高确定则用确定的,否则用测量后的
                modeWidth == MeasureSpec.EXACTLY ? sizeWidth : width + getPaddingLeft() + getPaddingRight(),
                modeHeight == MeasureSpec.EXACTLY ? sizeHeight : height + getPaddingTop() + getPaddingBottom()//
        );
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mAllViews.clear();
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
                mAllViews.add(lineViews);
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
        mAllViews.add(lineViews);

        //设置子View的位置
        int left = 0;
        int top = 0;
        //获取行数
        int lineNum  = mAllViews.size();
        for (int i = 0; i < lineNum; i++) {
            //当前行的views和高度
            lineViews = mAllViews.get(i);
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

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    /**
     * 获取指定行数内的item个数
     *
     * @return 每行的个数之和
     * @lineNum 总行数
     */
    public int getTotalByLine(int lineNum) {
        int count = 0;
        if (lineNum <= mAllViews.size()) {
            for (int i = 0; i < lineNum; i++) {
                List<View> line = mAllViews.get(i);
                count += line.size();
            }
        } else {
            for (int i = 0; i < mAllViews.size(); i++) {
                List<View> line = mAllViews.get(i);
                count += line.size();
            }
        }

        return count;
    }

    /**
     * 返回总行数
     *
     * @return
     */
    public int getTotalLine() {
        return mAllViews.size();
    }

    /**
     * 设置的数据是否超过了最大限制
     *
     * @return
     */
    public boolean isExceedingMaxLimit() {
        return isExceedingMaxLimit;
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

    private FlowLayout.OnItemClickListener mListener;

    public void setOnItemClickListener(FlowLayout.OnItemClickListener listener) {
        mListener = listener;
    }

    public interface OnItemClickListener {

        void onItemClick(int position, String key);
    }
}
