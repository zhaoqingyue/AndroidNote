package com.study.commonlib.ui.view.wheel;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.OverScroller;

import com.study.commonlib.R;
import com.study.commonlib.util.utilcode.ScreenUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by zhao.qingyue on 2019/4/3.
 * 自定义日期轮视图
 */
public class WheelViewDate<T> extends View implements Runnable {

    private static final String TAG = "WheelView";

    private static final float DEFAULT_LINE_SPACING = ScreenUtils.dp2px(2f);
    private static final float DEFAULT_TEXT_SIZE = ScreenUtils.sp2px(15f);
    private static final float DEFAULT_TEXT_BOUNDARY_MARGIN = ScreenUtils.dp2px(2);
    private static final float DEFAULT_DIVIDER_HEIGHT = ScreenUtils.dp2px(1);
    private static final int DEFAULT_NORMAL_TEXT_COLOR = Color.DKGRAY;
    private static final int DEFAULT_SELECTED_TEXT_COLOR = Color.BLACK;

    private static final int DEFAULT_VISIBLE_ITEM = 5;
    private static final int DEFAULT_SCROLL_DURATION = 250;
    private static final long DEFAULT_CLICK_CONFIRM = 120;
    private static final String DEFAULT_INTEGER_FORMAT = "%02d";

    //文字对齐方式
    public static final int TEXT_ALIGN_LEFT = 0;
    public static final int TEXT_ALIGN_CENTER = 1;
    public static final int TEXT_ALIGN_RIGHT = 2;

    //滚动状态
    public static final int SCROLL_STATE_IDLE = 0;
    public static final int SCROLL_STATE_DRAGGING = 1;
    public static final int SCROLL_STATE_SCROLLING = 2;

    //分割线填充类型
    public static final int DIVIDER_TYPE_FILL = 0;
    public static final int DIVIDER_TYPE_WRAP = 1;

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    //字体大小
    private float mTextSize;
    private float mSelectedItemTextSize;

    private Paint.FontMetrics mFontMetrics;
    //每个item的高度
    private int mItemHeight;
    //文字的最大宽度
    private int mMaxTextWidth;
    //文字中心距离baseline的距离
    private int mCenterToBaselineY;
    //可见的item条数
    private int mVisibleItems;
    //每个item之间的空间，行间距
    private float mLineSpacing;
    //是否循环滚动
    private boolean isCyclic;
    //文字对齐方式
    @TextAlign
    private int mTextAlign;
    //文字颜色
    private int mTextColor;
    //选中item文字颜色
    private int mSelectedItemTextColor;

    //是否显示分割线
    private boolean isShowDivider;
    //分割线的颜色
    private int mDividerColor;
    //分割线高度
    private float mDividerSize;
    //分割线填充类型
    @DividerType
    private int mDividerType;
    //分割线类型为DIVIDER_TYPE_WRAP时 分割线左右两端距离文字的间距
    private float mDividerPaddingForWrap;
    //分割线两端形状，默认圆头
    private Paint.Cap mDividerCap = Paint.Cap.ROUND;


    private boolean mShowLabel = false;
    private String mLabelText = "";
    private int mLabelColor;
    private float mLabelSize;
    private int maxLenth;

    /* ----------since version 1.0.1 ---------- */
    // 是否绘制选中区域
    private boolean isDrawSelectedRect;
    // 选中区域颜色
    private int mSelectedRectColor;
    /* ----------since version 1.0.1 ---------- */

    //文字起始X
    private int mStartX;
    //X轴中心点
    private int mCenterX;
    //Y轴中心点
    private int mCenterY;
    //选中边界的上下限制
    private int mSelectedItemTopLimit;
    private int mSelectedItemBottomLimit;
    //裁剪边界
    private int mClipLeft;
    private int mClipTop;
    private int mClipRight;
    private int mClipBottom;
    //绘制区域
    private Rect mDrawRect;
    //字体外边距，目的是留有边距
    private float mTextBoundaryMargin;
    //数据为Integer类型时，是否需要格式转换
    private boolean isIntegerNeedFormat;
    //数据为Integer类型时，转换格式，默认转换为两位数
    private String mIntegerFormat;

    //数据列表
    @NonNull
    private List<T> mDataList = new ArrayList<>(1);
    //数据变化时，是否重置选中下标到第一个位置
    private boolean isResetSelectedPosition = false;

    private VelocityTracker mVelocityTracker;
    private int mMaxFlingVelocity;
    private int mMinFlingVelocity;
    private OverScroller mOverScroller;

    //最小滚动距离，上边界
    private int mMinScrollY;
    //最大滚动距离，下边界
    private int mMaxScrollY;

    //Y轴滚动偏移
    private int mScrollOffsetY;
    //Y轴已滚动偏移，控制重绘次数
    private int mScrolledY = 0;
    //手指最后触摸的位置
    private float mLastTouchY;
    //手指按下时间，根据按下抬起时间差处理点击滚动
    private long mDownStartTime;
    //是否强制停止滚动
    private boolean isForceFinishScroll = false;
    //是否是快速滚动，快速滚动结束后跳转位置
    private boolean isFlingScroll;
    //当前选中的下标
    private int mSelectedItemPosition;
    //当前滚动经过的下标
    private int mCurrentScrollPosition;

    //监听器
    private OnItemSelectedListener<T> mOnItemSelectedListener;
    private OnWheelChangedListener mOnWheelChangedListener;

    public WheelViewDate(Context context) {
        this(context, null);
    }

    public WheelViewDate(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WheelViewDate(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrsAndDefault(context, attrs);
        initValue(context);
    }

    /**
     * 初始化自定义属性及默认值
     */
    private void initAttrsAndDefault(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WheelViewDate);

        mShowLabel = typedArray.getBoolean(R.styleable.WheelViewDate_wvd_showLabel, false);
        mLabelText = typedArray.getString(R.styleable.WheelViewDate_wvd_labelText);
        mLabelColor = typedArray.getColor(R.styleable.WheelViewDate_wvd_labelTextColor, DEFAULT_SELECTED_TEXT_COLOR);
        mLabelSize = typedArray.getDimension(R.styleable.WheelViewDate_wvd_labelTextSize, DEFAULT_TEXT_SIZE);
        maxLenth = typedArray.getInt(R.styleable.WheelViewDate_wvd_maxLength, 2);
        mTextSize = typedArray.getDimension(R.styleable.WheelViewDate_wvd_textSize, DEFAULT_TEXT_SIZE);
        mSelectedItemTextSize = typedArray.getDimension(R.styleable.WheelViewDate_wvd_selectedItemTextSize, DEFAULT_TEXT_SIZE);

        mTextAlign = typedArray.getInt(R.styleable.WheelViewDate_wvd_textAlign, TEXT_ALIGN_CENTER);
        mTextBoundaryMargin = typedArray.getDimension(R.styleable.WheelViewDate_wvd_textBoundaryMargin, DEFAULT_TEXT_BOUNDARY_MARGIN);
        mTextColor = typedArray.getColor(R.styleable.WheelViewDate_wvd_normalItemTextColor, DEFAULT_NORMAL_TEXT_COLOR);
        mSelectedItemTextColor = typedArray.getColor(R.styleable.WheelViewDate_wvd_selectedItemTextColor, DEFAULT_SELECTED_TEXT_COLOR);
        mLineSpacing = typedArray.getDimension(R.styleable.WheelViewDate_wvd_lineSpacing, DEFAULT_LINE_SPACING);
        isIntegerNeedFormat = typedArray.getBoolean(R.styleable.WheelViewDate_wvd_integerNeedFormat, false);
        mIntegerFormat = typedArray.getString(R.styleable.WheelViewDate_wvd_integerFormat);
        if (TextUtils.isEmpty(mIntegerFormat)) {
            mIntegerFormat = DEFAULT_INTEGER_FORMAT;
        }

        mVisibleItems = typedArray.getInt(R.styleable.WheelViewDate_wvd_visibleItems, DEFAULT_VISIBLE_ITEM);
        //跳转可见item为奇数
        mVisibleItems = adjustVisibleItems(mVisibleItems);
        mSelectedItemPosition = typedArray.getInt(R.styleable.WheelViewDate_wvd_selectedItemPosition, 0);
        //初始化滚动下标
        mCurrentScrollPosition = mSelectedItemPosition;
        isCyclic = typedArray.getBoolean(R.styleable.WheelViewDate_wvd_cyclic, false);

        isShowDivider = typedArray.getBoolean(R.styleable.WheelViewDate_wvd_showDivider, false);
        mDividerType = typedArray.getInt(R.styleable.WheelViewDate_wvd_dividerType, DIVIDER_TYPE_FILL);
        mDividerSize = typedArray.getDimension(R.styleable.WheelViewDate_wvd_dividerHeight, DEFAULT_DIVIDER_HEIGHT);
        mDividerColor = typedArray.getColor(R.styleable.WheelViewDate_wvd_dividerColor, DEFAULT_SELECTED_TEXT_COLOR);
        mDividerPaddingForWrap = typedArray.getDimension(R.styleable.WheelViewDate_wvd_dividerPaddingForWrap, DEFAULT_TEXT_BOUNDARY_MARGIN);

        isDrawSelectedRect = typedArray.getBoolean(R.styleable.WheelViewDate_wvd_drawSelectedRect, false);
        mSelectedRectColor = typedArray.getColor(R.styleable.WheelViewDate_wvd_selectedRectColor, Color.TRANSPARENT);
        typedArray.recycle();
    }

    /**
     * 初始化并设置默认值
     */
    private void initValue(Context context) {
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        mMaxFlingVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        mMinFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        mOverScroller = new OverScroller(context);
        mDrawRect = new Rect();
        calculateTextSize();
        updateTextAlign();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    /**
     * 测量文字最大所占空间
     */
    private void calculateTextSize() {
//        mPaint.setTextSize(mTextSize);
        mPaint.setTextSize(mSelectedItemTextSize);
        for (int i = 0; i < mDataList.size(); i++) {
            int textWidth = (int) mPaint.measureText(getDataText(mDataList.get(i)));
            mMaxTextWidth = Math.max(textWidth, mMaxTextWidth);
        }

        mFontMetrics = mPaint.getFontMetrics();
        // itemHeight实际等于字体高度+一个行间距
        mItemHeight = (int) (mFontMetrics.bottom - mFontMetrics.top + mLineSpacing);
    }

    /**
     * 更新textAlign
     */
    private void updateTextAlign() {
        switch (mTextAlign) {
            case TEXT_ALIGN_LEFT:
                mPaint.setTextAlign(Paint.Align.LEFT);
                break;
            case TEXT_ALIGN_RIGHT:
                mPaint.setTextAlign(Paint.Align.RIGHT);
                break;
            default:
                mPaint.setTextAlign(Paint.Align.CENTER);
                break;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // Line Space算在了mItemHeight中
        int height = mItemHeight * mVisibleItems + getPaddingTop() + getPaddingBottom();
        int width = (int) (mMaxTextWidth + getPaddingLeft() + getPaddingRight() + mTextBoundaryMargin * 2);
        setMeasuredDimension(resolveSizeAndState(width, widthMeasureSpec, 0), resolveSizeAndState(height, heightMeasureSpec, 0));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // 设置内容可绘制区域
        mDrawRect.set(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingRight(), getHeight() - getPaddingBottom());
        mCenterX = mDrawRect.centerX();
        mCenterY = mDrawRect.centerY();
        mSelectedItemTopLimit = mCenterY - mItemHeight / 2;
        mSelectedItemBottomLimit = mCenterY + mItemHeight / 2;

        mClipLeft = getPaddingLeft();
        mClipTop = getPaddingTop();
        mClipRight = getWidth() - getPaddingRight();
        mClipBottom = getHeight() - getPaddingBottom();

        calculateDrawStart();
        // 计算滚动限制
        calculateLimitY();
    }

    /**
     * 起算起始位置
     */
    private void calculateDrawStart() {
        switch (mTextAlign) {
            case TEXT_ALIGN_LEFT:
                mStartX = (int) (getPaddingLeft() + mTextBoundaryMargin);
                break;
            case TEXT_ALIGN_RIGHT:
                mStartX = (int) (getWidth() - getPaddingRight() - mTextBoundaryMargin);
                break;
            case TEXT_ALIGN_CENTER:
            default:
                mStartX = getWidth() / 2;
                break;
        }

        //文字中心距离baseline的距离
        mCenterToBaselineY = (int) (mFontMetrics.ascent + (mFontMetrics.descent - mFontMetrics.ascent) / 2);
    }

    /**
     * 计算滚动限制
     */
    private void calculateLimitY() {
        mMinScrollY = isCyclic ? Integer.MIN_VALUE : 0;
        // 下边界 (dataSize - 1 - mInitPosition) * mItemHeight
        mMaxScrollY = isCyclic ? Integer.MAX_VALUE : (mDataList.size() - 1) * mItemHeight;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 绘制选中区域
        drawSelectedRect(canvas);
        // 绘制分割线
        drawDivider(canvas);

        // 滚动了多少个item，滚动的Y值高度除去每行Item的高度
        int scrolledItem = mScrollOffsetY / mItemHeight;
        // 没有滚动完一个item时的偏移值，平滑滑动
        int scrolledOffset = mScrollOffsetY % mItemHeight;

        // 向上取整
        int halfItem = (mVisibleItems + 1) / 2;
        // 计算的最小index
        int minIndex;
        // 计算的最大index
        int maxIndex;
        if (scrolledOffset < 0) {
            // 小于0
            minIndex = scrolledItem - halfItem - 1;
            maxIndex = scrolledItem + halfItem;
        } else if (scrolledOffset > 0) {
            minIndex = scrolledItem - halfItem;
            maxIndex = scrolledItem + halfItem + 1;
        } else {
            minIndex = scrolledItem - halfItem;
            maxIndex = scrolledItem + halfItem;
        }

        // 绘制item
        for (int i = minIndex; i < maxIndex; i++) {
            drawItem(canvas, i, scrolledOffset);
        }

    }

    /**
     * 绘制选中区域
     */
    private void drawSelectedRect(Canvas canvas) {
        if (isDrawSelectedRect) {
            mPaint.setColor(mSelectedRectColor);
            canvas.drawRect(mClipLeft, mSelectedItemTopLimit, mClipRight, mSelectedItemBottomLimit, mPaint);
        }
    }

    /**
     * 绘制分割线
     */
    private void drawDivider(Canvas canvas) {
        if (isShowDivider) {
            mPaint.setColor(mDividerColor);
            float originStrokeWidth = mPaint.getStrokeWidth();
            mPaint.setStrokeJoin(Paint.Join.ROUND);
            mPaint.setStrokeCap(Paint.Cap.ROUND);
            mPaint.setStrokeWidth(mDividerSize);
            if (mDividerType == DIVIDER_TYPE_FILL) {
                canvas.drawLine(mClipLeft, mSelectedItemTopLimit, mClipRight, mSelectedItemTopLimit, mPaint);
                canvas.drawLine(mClipLeft, mSelectedItemBottomLimit, mClipRight, mSelectedItemBottomLimit, mPaint);
            } else {
                // 边界处理 超过边界直接按照DIVIDER_TYPE_FILL类型处理
                int startX = (int) (mCenterX - mMaxTextWidth / 2 - mDividerPaddingForWrap);
                int stopX = (int) (mCenterX + mMaxTextWidth / 2 + mDividerPaddingForWrap);
                int wrapStartX = startX < mClipLeft ? mClipLeft : startX;
                int wrapStopX = stopX > mClipRight ? mClipRight : stopX;
                canvas.drawLine(wrapStartX, mSelectedItemTopLimit, wrapStopX, mSelectedItemTopLimit, mPaint);
                canvas.drawLine(wrapStartX, mSelectedItemBottomLimit, wrapStopX, mSelectedItemBottomLimit, mPaint);
            }
            mPaint.setStrokeWidth(originStrokeWidth);
        }
    }

    /**
     * 绘制2D效果
     *
     * @param canvas         画布
     * @param index          下标
     * @param scrolledOffset 滚动偏移
     */
    private void drawItem(Canvas canvas, int index, int scrolledOffset) {
        String text = getDataByIndex(index);
        if (text == null) {
            return;
        }

        // index 的 item 距离中间项的偏移
        int item2CenterOffsetY = (index - mScrollOffsetY / mItemHeight) * mItemHeight - scrolledOffset;
        // 重新测量字体宽度和基线偏移
        int centerToBaselineY =  mCenterToBaselineY;

        if (Math.abs(item2CenterOffsetY) <= 0) {
            // 绘制选中的条目
            mPaint.setTextSize(mSelectedItemTextSize);
            mPaint.setColor(mSelectedItemTextColor);
            clipAndDraw2DText(canvas, text, mSelectedItemTopLimit, mSelectedItemBottomLimit, item2CenterOffsetY, centerToBaselineY);
        } else if (item2CenterOffsetY > 0 && item2CenterOffsetY < mItemHeight) {
            // 绘制与下边界交汇的条目
            mPaint.setTextSize(mSelectedItemTextSize);
            mPaint.setColor(mSelectedItemTextColor);
            clipAndDraw2DText(canvas, text, mSelectedItemTopLimit, mSelectedItemBottomLimit, item2CenterOffsetY, centerToBaselineY);

            mPaint.setTextSize(mTextSize);
            mPaint.setColor(mTextColor);
            clipAndDraw2DText(canvas, text, mSelectedItemBottomLimit, mClipBottom, item2CenterOffsetY, centerToBaselineY);

        } else if (item2CenterOffsetY < 0 && item2CenterOffsetY > -mItemHeight) {
            // 绘制与上边界交汇的条目
            mPaint.setTextSize(mSelectedItemTextSize);
            mPaint.setColor(mSelectedItemTextColor);
            clipAndDraw2DText(canvas, text, mSelectedItemTopLimit, mSelectedItemBottomLimit, item2CenterOffsetY, centerToBaselineY);

            mPaint.setTextSize(mTextSize);
            mPaint.setColor(mTextColor);
            clipAndDraw2DText(canvas, text, mClipTop, mSelectedItemTopLimit, item2CenterOffsetY, centerToBaselineY);

        } else {
            // 绘制其他条目
            mPaint.setTextSize(mTextSize);
            mPaint.setColor(mTextColor);
            clipAndDraw2DText(canvas, text, mClipTop, mClipBottom, item2CenterOffsetY, centerToBaselineY/2);
        }
    }

    /**
     * 裁剪并绘制2d text
     *
     * @param canvas             画布
     * @param text               绘制的文字
     * @param clipTop            裁剪的上边界
     * @param clipBottom         裁剪的下边界
     * @param item2CenterOffsetY 距离中间项的偏移
     * @param centerToBaselineY  文字中心距离baseline的距离
     */
    private void clipAndDraw2DText(Canvas canvas, String text, int clipTop, int clipBottom, int item2CenterOffsetY, int centerToBaselineY) {
        canvas.save();
        canvas.clipRect(mClipLeft, clipTop, mClipRight, clipBottom);
        canvas.drawText(text, 0, text.length(), mStartX, mCenterY + item2CenterOffsetY - centerToBaselineY, mPaint);
        canvas.restore();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if(mShowLabel && !TextUtils.isEmpty(mLabelText)) {
            int index = getSelectedItemPosition();
            String text = getDataByIndex(index);
            int centerToBaselineY =  mCenterToBaselineY;
            int scrolledOffset = mScrollOffsetY % mItemHeight;
            int item2CenterOffsetY = (index - mScrollOffsetY / mItemHeight) * mItemHeight - scrolledOffset;

            mPaint.setTextSize(mLabelSize);
            mPaint.setColor(mLabelColor);
            String label = mLabelText;
            int textWidth = (int) mPaint.measureText(text);
            int labelWidth = (int) mPaint.measureText(label);
            int start = textWidth + labelWidth + 5*maxLenth;

            Rect targetRect = new Rect(0, mItemHeight * (mVisibleItems / 2), getWidth(), mItemHeight * (mVisibleItems / 2 + 1));
            mPaint.setTextSize(mLabelSize);
            mPaint.setColor(mLabelColor);
            Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();
            int baseline = (targetRect.bottom + targetRect.top - fontMetrics.bottom - fontMetrics.top) / 2;
            mPaint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(
                    mLabelText,
                    targetRect.centerX() + /*15*maxLenth*/ + start ,
                    baseline,
                    mPaint);
        }

    }

    /**
     * 重新测量字体大小
     *
     * @param contentText 被测量文字内容
     * @return 文字中心距离baseline的距离
     */
    private int remeasureTextSize(String contentText) {
        float textWidth = mPaint.measureText(contentText);
        float drawWidth = getWidth();
        float textMargin = mTextBoundaryMargin * 2;
        //稍微增加了一点文字边距 最大为宽度的1/10
        if (textMargin > (drawWidth / 10f)) {
            drawWidth = drawWidth * 9f / 10f;
            textMargin = drawWidth / 10f;
        } else {
            drawWidth = drawWidth - textMargin;
        }
        if (drawWidth <= 0) {
            return mCenterToBaselineY;
        }
        float textSize = mTextSize;
        while (textWidth > drawWidth) {
            textSize--;
            if (textSize <= 0) {
                break;
            }
            mPaint.setTextSize(textSize);
            textWidth = mPaint.measureText(contentText);
        }
        //重新计算文字起始X
        recalculateStartX(textMargin / 2.0f);
        //高度起点也变了
        return recalculateCenterToBaselineY();
    }

    /**
     * 重新计算字体起始X
     *
     * @param textMargin 文字外边距
     */
    private void recalculateStartX(float textMargin) {
        switch (mTextAlign) {
            case TEXT_ALIGN_LEFT:
                mStartX = (int) textMargin;
                break;
            case TEXT_ALIGN_RIGHT:
                mStartX = (int) (getWidth() - textMargin);
                break;
            default:
                mStartX = getWidth() / 2;
                break;
        }
    }

    /**
     * 字体大小变化后重新计算距离基线的距离
     *
     * @return 文字中心距离baseline的距离
     */
    private int recalculateCenterToBaselineY() {
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        //高度起点也变了
        return (int) (fontMetrics.ascent + (fontMetrics.descent - fontMetrics.ascent) / 2);
    }

    /**
     * 根据下标获取到内容
     *
     * @param index 下标
     * @return 绘制的文字内容
     */
    private String getDataByIndex(int index) {
        int dataSize = mDataList.size();
        if (dataSize == 0) {
            return null;
        }

        String itemText = null;
        if (isCyclic) {
            int i = index % dataSize;
            if (i < 0) {
                i += dataSize;
            }
            itemText = getDataText(mDataList.get(i));
        } else {
            if (index >= 0 && index < dataSize) {
                itemText = getDataText(mDataList.get(index));
            }
        }
        return itemText;
    }

    /**
     * 获取item text
     *
     * @param item item数据
     * @return 文本内容
     */
    protected String getDataText(T item) {
        if (item == null) {
            return "";
        } else if (item instanceof Integer) {
            //如果为整形则最少保留两位数.
            return isIntegerNeedFormat ? String.format(Locale.getDefault(), mIntegerFormat, item)
                    : String.valueOf(item);
        } else if (item instanceof String) {
            return (String) item;
        }
        return item.toString();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        initVelocityTracker();
        mVelocityTracker.addMovement(event);

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                //手指按下
                //处理滑动事件嵌套 拦截事件序列
                if (getParent() != null) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                //如果未滚动完成，强制滚动完成
                if (!mOverScroller.isFinished()) {
                    //强制滚动完成
                    mOverScroller.forceFinished(true);
                    isForceFinishScroll = true;
                }
                mLastTouchY = event.getY();
                //按下时间
                mDownStartTime = System.currentTimeMillis();
                break;
            case MotionEvent.ACTION_MOVE:
                //手指移动
                float moveY = event.getY();
                float deltaY = moveY - mLastTouchY;

                if (mOnWheelChangedListener != null) {
                    mOnWheelChangedListener.onWheelScrollStateChanged(SCROLL_STATE_DRAGGING);
                }
                if (Math.abs(deltaY) < 1) {
                    break;
                }
                //deltaY 上滑为正，下滑为负
                doScroll((int) -deltaY);
                mLastTouchY = moveY;
                invalidateIfYChanged();

                break;
            case MotionEvent.ACTION_UP:
                //手指抬起
                isForceFinishScroll = false;
                mVelocityTracker.computeCurrentVelocity(1000, mMaxFlingVelocity);
                float velocityY = mVelocityTracker.getYVelocity();
                if (Math.abs(velocityY) > mMinFlingVelocity) {
                    //快速滑动
                    mOverScroller.forceFinished(true);
                    isFlingScroll = true;
                    mOverScroller.fling(0, mScrollOffsetY, 0, (int) -velocityY, 0, 0,
                            mMinScrollY, mMaxScrollY);
                } else {
                    int clickToCenterDistance = 0;
                    if (System.currentTimeMillis() - mDownStartTime <= DEFAULT_CLICK_CONFIRM) {
                        //处理点击滚动
                        //手指抬起的位置到中心的距离为滚动差值
                        clickToCenterDistance = (int) (event.getY() - mCenterY);
                    }
                    int scrollRange = clickToCenterDistance +
                            calculateDistanceToEndPoint((mScrollOffsetY + clickToCenterDistance) % mItemHeight);
                    //平稳滑动
                    mOverScroller.startScroll(0, mScrollOffsetY, 0, scrollRange);
                }

                invalidateIfYChanged();
                ViewCompat.postOnAnimation(this, this);

                //回收 VelocityTracker
                recycleVelocityTracker();
                break;
            case MotionEvent.ACTION_CANCEL:
                //事件被终止
                //回收
                recycleVelocityTracker();
                break;
        }
        return true;
    }

    /**
     * 初始化 VelocityTracker
     */
    private void initVelocityTracker() {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
    }

    /**
     * 回收 VelocityTracker
     */
    private void recycleVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    /**
     * 计算滚动偏移
     *
     * @param distance 滚动距离
     */
    private void doScroll(int distance) {
        mScrollOffsetY += distance;
        if (!isCyclic) {
            //修正边界
            if (mScrollOffsetY < mMinScrollY) {
                mScrollOffsetY = mMinScrollY;
            } else if (mScrollOffsetY > mMaxScrollY) {
                mScrollOffsetY = mMaxScrollY;
            }
        }
    }

    /**
     * 当Y轴的偏移值改变时再重绘，减少重回次数
     */
    private void invalidateIfYChanged() {
        if (mScrollOffsetY != mScrolledY) {
            mScrolledY = mScrollOffsetY;
            //滚动偏移发生变化
            if (mOnWheelChangedListener != null) {
                mOnWheelChangedListener.onWheelScroll(mScrollOffsetY);
            }
            //观察item变化
            observeItemChanged();
            invalidate();
        }
    }

    /**
     * 观察item改变
     */
    private void observeItemChanged() {
        //item改变回调
        int oldPosition = mCurrentScrollPosition;
        int newPosition = getCurrentPosition();
        if (oldPosition != newPosition) {
            //改变了
            if (mOnWheelChangedListener != null) {
                mOnWheelChangedListener.onWheelItemChanged(oldPosition, newPosition);
            }
            //更新下标
            mCurrentScrollPosition = newPosition;
        }
    }
    /**
     * 强制滚动完成，直接停止
     */
    public void forceFinishScroll() {
        if (!mOverScroller.isFinished()) {
            mOverScroller.forceFinished(true);
        }
    }

    /**
     * 强制滚动完成，并且直接滚动到最终位置
     */
    public void abortFinishScroll() {
        if (!mOverScroller.isFinished()) {
            mOverScroller.abortAnimation();
        }
    }

    /**
     * 计算距离终点的偏移，修正选中条目
     *
     * @param remainder 余数
     * @return 偏移量
     */
    private int calculateDistanceToEndPoint(int remainder) {
        if (Math.abs(remainder) > mItemHeight / 2) {
            if (mScrollOffsetY < 0) {
                return -mItemHeight - remainder;
            } else {
                return mItemHeight - remainder;
            }
        } else {
            return -remainder;
        }
    }

    /**
     * 使用run方法而不是computeScroll是因为，invalidate也会执行computeScroll导致回调执行不准确
     */
    @Override
    public void run() {
        //停止滚动更新当前下标
        if (mOverScroller.isFinished() && !isForceFinishScroll && !isFlingScroll) {
            if (mItemHeight == 0) return;
            //滚动状态停止
            if (mOnWheelChangedListener != null) {
                mOnWheelChangedListener.onWheelScrollStateChanged(SCROLL_STATE_IDLE);
            }
            int currentItemPosition = getCurrentPosition();
            //当前选中的Position没变时不回调 onItemSelected()
            if (currentItemPosition == mSelectedItemPosition) {
                return;
            }
            mSelectedItemPosition = currentItemPosition;
            //停止后重新赋值
            mCurrentScrollPosition = mSelectedItemPosition;

            //停止滚动，选中条目回调
            if (mOnItemSelectedListener != null) {
                mOnItemSelectedListener.onItemSelected(this, mDataList.get(mSelectedItemPosition), mSelectedItemPosition);
            }
            //滚动状态回调
            if (mOnWheelChangedListener != null) {
                mOnWheelChangedListener.onWheelSelected(mSelectedItemPosition);
            }
        }

        if (mOverScroller.computeScrollOffset()) {
            int oldY = mScrollOffsetY;
            mScrollOffsetY = mOverScroller.getCurrY();

            if (oldY != mScrollOffsetY) {
                if (mOnWheelChangedListener != null) {
                    mOnWheelChangedListener.onWheelScrollStateChanged(SCROLL_STATE_SCROLLING);
                }
            }
            invalidateIfYChanged();
            ViewCompat.postOnAnimation(this, this);
        } else if (isFlingScroll) {
            //滚动完成后，根据是否为快速滚动处理是否需要调整最终位置
            isFlingScroll = false;
            //快速滚动后需要调整滚动完成后的最终位置，重新启动scroll滑动到中心位置
            mOverScroller.startScroll(0, mScrollOffsetY, 0, calculateDistanceToEndPoint(mScrollOffsetY % mItemHeight));
            invalidateIfYChanged();
            ViewCompat.postOnAnimation(this, this);
        }
    }

    /**
     * 根据偏移计算当前位置下标
     *
     * @return 偏移量对应的当前下标
     */
    private int getCurrentPosition() {
        int itemPosition;
        if (mScrollOffsetY < 0) {
            itemPosition = (mScrollOffsetY - mItemHeight / 2) / mItemHeight;
        } else {
            itemPosition = (mScrollOffsetY + mItemHeight / 2) / mItemHeight;
        }
        int currentPosition = itemPosition % mDataList.size();
        if (currentPosition < 0) {
            currentPosition += mDataList.size();
        }

        return currentPosition;
    }

    /**
     * 获取指定 position 的数据
     *
     * @param position 下标
     * @return position 对应的数据 {@link Nullable}
     */
    @Nullable
    public T getItemData(int position) {
        if (isPositionInRange(position)) {
            return mDataList.get(position);
        } else if (mDataList.size() > 0 && position >= mDataList.size()) {
            return mDataList.get(mDataList.size() - 1);
        } else if (mDataList.size() > 0 && position < 0) {
            return mDataList.get(0);
        }
        return null;
    }

    /**
     * 获取当前选中的item数据
     *
     * @return 当前选中的item数据
     */
    public T getSelectedItemData() {
        return getItemData(mSelectedItemPosition);
    }

    /**
     * 获取数据列表
     *
     * @return 数据列表
     */
    public List<T> getData() {
        return mDataList;
    }

    /**
     * 设置数据
     *
     * @param dataList 数据列表
     */
    public void setData(List<T> dataList) {
        if (dataList == null) {
            return;
        }
        mDataList = dataList;
        if (!isResetSelectedPosition && mDataList.size() > 0) {
            //不重置选中下标
            if (mSelectedItemPosition >= mDataList.size()) {
                mSelectedItemPosition = mDataList.size() - 1;
                //重置滚动下标
                mCurrentScrollPosition = mSelectedItemPosition;
            }
        } else {
            //重置选中下标和滚动下标
            mCurrentScrollPosition = mSelectedItemPosition = 0;
        }
        //强制滚动完成
        forceFinishScroll();
        calculateTextSize();
        calculateLimitY();
        //重置滚动偏移
        mScrollOffsetY = mSelectedItemPosition * mItemHeight;
        requestLayout();
        invalidate();
    }

    /**
     * 当数据变化时，是否重置选中下标到第一个
     *
     * @return 是否重置选中下标到第一个
     */
    public boolean isResetSelectedPosition() {
        return isResetSelectedPosition;
    }

    /**
     * 设置当数据变化时，是否重置选中下标到第一个
     *
     * @param isResetSelectedPosition 当数据变化时,是否重置选中下标到第一个
     */
    public void setResetSelectedPosition(boolean isResetSelectedPosition) {
        this.isResetSelectedPosition = isResetSelectedPosition;
    }

    /**
     * 获取字体大小
     *
     * @return 字体大小
     */
    public float getTextSize() {
        return mTextSize;
    }

    /**
     * 设置字体大小
     *
     * @param textSize 字体大小
     */
    public void setTextSize(float textSize) {
        setTextSize(textSize, false);
    }

    /**
     * 设置字体大小
     *
     * @param textSize 字体大小
     * @param isSp     单位是否是 sp
     */
    public void setTextSize(float textSize, boolean isSp) {
        float tempTextSize = mTextSize;
        mTextSize = isSp ? ScreenUtils.sp2px(textSize) : textSize;
        if (tempTextSize == mTextSize) {
            return;
        }
        //强制滚动完成
        forceFinishScroll();
        calculateTextSize();
        calculateDrawStart();
        calculateLimitY();
        //字体大小变化，偏移距离也变化了
        mScrollOffsetY = mSelectedItemPosition * mItemHeight;
        requestLayout();
        invalidate();
    }

    /**
     * 获取当前字体
     *
     * @return 字体
     */
    public Typeface getTypeface() {
        return mPaint.getTypeface();
    }

    /**
     * 设置当前字体
     *
     * @param typeface 字体
     */
    public void setTypeface(Typeface typeface) {
        if (mPaint.getTypeface() == typeface) {
            return;
        }
        //强制滚动完成
        forceFinishScroll();
        mPaint.setTypeface(typeface);
        calculateTextSize();
        calculateDrawStart();
        //字体大小变化，偏移距离也变化了
        mScrollOffsetY = mSelectedItemPosition * mItemHeight;
        calculateLimitY();
        requestLayout();
        invalidate();
    }

    /**
     * 获取文字对齐方式
     *
     * @return 文字对齐
     * {@link #TEXT_ALIGN_LEFT}
     * {@link #TEXT_ALIGN_CENTER}
     * {@link #TEXT_ALIGN_RIGHT}
     */
    public int getTextAlign() {
        return mTextAlign;
    }

    /**
     * 设置文字对齐方式
     *
     * @param textAlign 文字对齐方式
     *                  {@link #TEXT_ALIGN_LEFT}
     *                  {@link #TEXT_ALIGN_CENTER}
     *                  {@link #TEXT_ALIGN_RIGHT}
     */
    public void setTextAlign(@TextAlign int textAlign) {
        if (mTextAlign == textAlign) {
            return;
        }
        mTextAlign = textAlign;
        updateTextAlign();
        calculateDrawStart();
        invalidate();
    }

    /**
     * 获取未选中条目颜色
     *
     * @return 未选中条目颜色 ColorInt
     */
    public int getNormalItemTextColor() {
        return mTextColor;
    }

    /**
     * 设置未选中条目颜色
     *
     * @param textColorRes 未选中条目颜色 {@link ColorRes}
     */
    public void setNormalItemTextColorRes(@ColorRes int textColorRes) {
        setNormalItemTextColor(ContextCompat.getColor(getContext(), textColorRes));
    }

    /**
     * 设置未选中条目颜色
     *
     * @param textColor 未选中条目颜色 {@link ColorInt}
     */
    public void setNormalItemTextColor(@ColorInt int textColor) {
        if (mTextColor == textColor) {
            return;
        }
        mTextColor = textColor;
        invalidate();
    }

    /**
     * 获取选中条目颜色
     *
     * @return 选中条目颜色 ColorInt
     */
    public int getSelectedItemTextColor() {
        return mSelectedItemTextColor;
    }

    /**
     * 设置选中条目颜色
     *
     * @param selectedItemColorRes 选中条目颜色 {@link ColorRes}
     */
    public void setSelectedItemTextColorRes(@ColorRes int selectedItemColorRes) {
        setSelectedItemTextColor(ContextCompat.getColor(getContext(), selectedItemColorRes));
    }

    /**
     * 设置选中条目颜色
     *
     * @param selectedItemTextColor 选中条目颜色 {@link ColorInt}
     */
    public void setSelectedItemTextColor(@ColorInt int selectedItemTextColor) {
        if (mSelectedItemTextColor == selectedItemTextColor) {
            return;
        }
        mSelectedItemTextColor = selectedItemTextColor;
        invalidate();
    }

    /**
     * 获取文字距离边界的外边距
     *
     * @return 外边距值
     */
    public float getTextBoundaryMargin() {
        return mTextBoundaryMargin;
    }

    /**
     * 设置文字距离边界的外边距
     *
     * @param textBoundaryMargin 外边距值
     */
    public void setTextBoundaryMargin(float textBoundaryMargin) {
        setTextBoundaryMargin(textBoundaryMargin, false);
    }

    /**
     * 设置文字距离边界的外边距
     *
     * @param textBoundaryMargin 外边距值
     * @param isDp               单位是否为 dp
     */
    public void setTextBoundaryMargin(float textBoundaryMargin, boolean isDp) {
        float tempTextBoundaryMargin = mTextBoundaryMargin;
        mTextBoundaryMargin = isDp ? ScreenUtils.dp2px(textBoundaryMargin) : textBoundaryMargin;
        if (tempTextBoundaryMargin == mTextBoundaryMargin) {
            return;
        }
        requestLayout();
        invalidate();
    }

    /**
     * 获取item间距
     *
     * @return 行间距值
     */
    public float getLineSpacing() {
        return mLineSpacing;
    }

    /**
     * 设置item间距
     *
     * @param lineSpacing 行间距值
     */
    public void setLineSpacing(float lineSpacing) {
        setLineSpacing(lineSpacing, false);
    }

    /**
     * 设置item间距
     *
     * @param lineSpacing 行间距值
     * @param isDp        lineSpacing 单位是否为 dp
     */
    public void setLineSpacing(float lineSpacing, boolean isDp) {
        float tempLineSpace = mLineSpacing;
        mLineSpacing = isDp ? ScreenUtils.dp2px(lineSpacing) : lineSpacing;
        if (tempLineSpace == mLineSpacing) {
            return;
        }
        mScrollOffsetY = 0;
        calculateTextSize();
        requestLayout();
        invalidate();
    }

    /**
     * 获取数据为Integer类型时是否需要转换
     *
     * @return isIntegerNeedFormat
     */
    public boolean isIntegerNeedFormat() {
        return isIntegerNeedFormat;
    }

    /**
     * 设置数据为Integer类型时是否需要转换
     *
     * @param isIntegerNeedFormat 数据为Integer类型时是否需要转换
     */
    public void setIntegerNeedFormat(boolean isIntegerNeedFormat) {
        if (this.isIntegerNeedFormat == isIntegerNeedFormat) {
            return;
        }
        this.isIntegerNeedFormat = isIntegerNeedFormat;
        calculateTextSize();
        requestLayout();
        invalidate();
    }

    /**
     * 同时设置 isIntegerNeedFormat=true 和 mIntegerFormat=integerFormat
     *
     * @param integerFormat 注意：integerFormat 中必须包含并且只能包含一个格式说明符（format specifier）
     *                      格式说明符请参照 http://java2s.com/Tutorials/Java/Data_Format/Java_Format_Specifier.htm
     *                      <p>
     *                      如果有多个格式说明符会抛出 java.util.MissingFormatArgumentException: Format specifier '%s'(多出来的说明符)
     */
    public void setIntegerNeedFormat(String integerFormat) {
        isIntegerNeedFormat = true;
        mIntegerFormat = integerFormat;
        calculateTextSize();
        requestLayout();
        invalidate();
    }

    /**
     * 获取Integer类型转换格式
     *
     * @return integerFormat
     */
    public String getIntegerFormat() {
        return mIntegerFormat;
    }

    /**
     * 设置Integer类型转换格式
     *
     * @param integerFormat 注意：integerFormat 中必须包含并且只能包含一个格式说明符（format specifier）
     *                      格式说明符请参照 http://java2s.com/Tutorials/Java/Data_Format/Java_Format_Specifier.htm
     *                      <p>
     *                      如果有多个格式说明符会抛出 java.util.MissingFormatArgumentException: Format specifier '%s'(多出来的说明符)
     */
    public void setIntegerFormat(String integerFormat) {
        if (TextUtils.isEmpty(integerFormat) || integerFormat.equals(mIntegerFormat)) {
            return;
        }
        mIntegerFormat = integerFormat;
        calculateTextSize();
        requestLayout();
        invalidate();
    }

    /**
     * 获取可见条目数
     *
     * @return 可见条目数
     */
    public int getVisibleItems() {
        return mVisibleItems;
    }

    /**
     * 设置可见的条目数
     *
     * @param visibleItems 可见条目数
     */
    public void setVisibleItems(int visibleItems) {
        if (mVisibleItems == visibleItems) {
            return;
        }
        mVisibleItems = adjustVisibleItems(visibleItems);
        mScrollOffsetY = 0;
        requestLayout();
        invalidate();
    }

    /**
     * 跳转可见条目数为奇数
     *
     * @param visibleItems 可见条目数
     * @return 调整后的可见条目数
     */
    private int adjustVisibleItems(int visibleItems) {
        return Math.abs(visibleItems / 2 * 2 + 1); // 当传入的值为偶数时,换算成奇数;
    }

    /**
     * 是否是循环滚动
     *
     * @return 是否是循环滚动
     */
    public boolean isCyclic() {
        return isCyclic;
    }

    /**
     * 设置是否循环滚动
     *
     * @param isCyclic 是否是循环滚动
     */
    public void setCyclic(boolean isCyclic) {
        if (this.isCyclic == isCyclic) {
            return;
        }
        this.isCyclic = isCyclic;

        forceFinishScroll();
        calculateLimitY();
        //设置当前选中的偏移值
        mScrollOffsetY = mSelectedItemPosition * mItemHeight;
        invalidate();
    }

    /**
     * 获取当前选中下标
     *
     * @return 当前选中的下标
     */
    public int getSelectedItemPosition() {
        return mSelectedItemPosition;
    }

    /**
     * 设置当前选中下标
     *
     * @param position 下标
     */
    public void setSelectedItemPosition(int position) {
        setSelectedItemPosition(position, false);
    }

    /**
     * 设置当前选中下标
     *
     * @param position       下标
     * @param isSmoothScroll 是否平滑滚动
     */
    public void setSelectedItemPosition(int position, boolean isSmoothScroll) {
        setSelectedItemPosition(position, isSmoothScroll, 0);
    }

    /**
     * 设置当前选中下标
     *
     * @param position       下标
     * @param isSmoothScroll 是否平滑滚动
     * @param smoothDuration 平滑滚动时间
     */
    public void setSelectedItemPosition(int position, boolean isSmoothScroll, int smoothDuration) {
        if (!isPositionInRange(position)) {
            return;
        }

        //item之间差值
        int itemDistance = position * mItemHeight - mScrollOffsetY;
        if (itemDistance == 0) {
            return;
        }
        //如果Scroller滑动未停止，强制结束动画
        abortFinishScroll();

        if (isSmoothScroll) {
            //如果是平滑滚动并且之前的Scroll滚动完成
            mOverScroller.startScroll(0, mScrollOffsetY, 0, itemDistance,
                    smoothDuration > 0 ? smoothDuration : DEFAULT_SCROLL_DURATION);
            invalidateIfYChanged();
            ViewCompat.postOnAnimation(this, this);

        } else {
            doScroll(itemDistance);
            mSelectedItemPosition = position;
            //选中条目回调
            if (mOnItemSelectedListener != null) {
                mOnItemSelectedListener.onItemSelected(this, mDataList.get(mSelectedItemPosition), mSelectedItemPosition);
            }

            if (mOnWheelChangedListener != null) {
                mOnWheelChangedListener.onWheelSelected(mSelectedItemPosition);
            }
            invalidateIfYChanged();
        }

    }

    /**
     * 判断下标是否在数据列表范围内
     *
     * @param position 下标
     * @return 是否在数据列表范围内
     */
    public boolean isPositionInRange(int position) {
        return position >= 0 && position < mDataList.size();
    }

    /**
     * 获取是否显示分割线
     *
     * @return 是否显示分割线
     */
    public boolean isShowDivider() {
        return isShowDivider;
    }

    /**
     * 设置是否显示分割线
     *
     * @param isShowDivider 是否显示分割线
     */
    public void setShowDivider(boolean isShowDivider) {
        if (this.isShowDivider == isShowDivider) {
            return;
        }
        this.isShowDivider = isShowDivider;
        invalidate();
    }

    /**
     * 获取分割线颜色
     *
     * @return 分割线颜色 ColorInt
     */
    public int getDividerColor() {
        return mDividerColor;
    }

    /**
     * 设置分割线颜色
     *
     * @param dividerColorRes 分割线颜色 {@link ColorRes}
     */
    public void setDividerColorRes(@ColorRes int dividerColorRes) {
        setDividerColor(ContextCompat.getColor(getContext(), dividerColorRes));
    }

    /**
     * 设置分割线颜色
     *
     * @param dividerColor 分割线颜色 {@link ColorInt}
     */
    public void setDividerColor(@ColorInt int dividerColor) {
        if (mDividerColor == dividerColor) {
            return;
        }
        mDividerColor = dividerColor;
        invalidate();
    }

    /**
     * 获取分割线高度
     *
     * @return 分割线高度
     */
    public float getDividerHeight() {
        return mDividerSize;
    }

    /**
     * 设置分割线高度
     *
     * @param dividerHeight 分割线高度
     */
    public void setDividerHeight(float dividerHeight) {
        setDividerHeight(dividerHeight, false);
    }

    /**
     * 设置分割线高度
     *
     * @param dividerHeight 分割线高度
     * @param isDp          单位是否是 dp
     */
    public void setDividerHeight(float dividerHeight, boolean isDp) {
        float tempDividerHeight = mDividerSize;
        mDividerSize = isDp ? ScreenUtils.dp2px(dividerHeight) : dividerHeight;
        if (tempDividerHeight == mDividerSize) {
            return;
        }
        invalidate();
    }

    /**
     * 获取分割线填充类型
     *
     * @return 分割线填充类型
     * {@link #DIVIDER_TYPE_FILL}
     * {@link #DIVIDER_TYPE_WRAP}
     */
    public int getDividerType() {
        return mDividerType;
    }

    /**
     * 设置分割线填充类型
     *
     * @param dividerType 分割线填充类型
     *                    {@link #DIVIDER_TYPE_FILL}
     *                    {@link #DIVIDER_TYPE_WRAP}
     */
    public void setDividerType(@DividerType int dividerType) {
        if (mDividerType == dividerType) {
            return;
        }
        mDividerType = dividerType;
        invalidate();
    }

    /**
     * 获取自适应分割线类型时的分割线内边距
     *
     * @return 分割线内边距
     */
    public float getDividerPaddingForWrap() {
        return mDividerPaddingForWrap;
    }

    /**
     * 设置自适应分割线类型时的分割线内边距
     *
     * @param dividerPaddingForWrap 分割线内边距
     */
    public void setDividerPaddingForWrap(float dividerPaddingForWrap) {
        setDividerPaddingForWrap(dividerPaddingForWrap, false);
    }

    /**
     * 设置自适应分割线类型时的分割线内边距
     *
     * @param dividerPaddingForWrap 分割线内边距
     * @param isDp                  单位是否是 dp
     */
    public void setDividerPaddingForWrap(float dividerPaddingForWrap, boolean isDp) {
        float tempDividerPadding = mDividerPaddingForWrap;
        mDividerPaddingForWrap = isDp ? ScreenUtils.dp2px(dividerPaddingForWrap) : dividerPaddingForWrap;
        if (tempDividerPadding == mDividerPaddingForWrap) {
            return;
        }
        invalidate();
    }

    /**
     * 获取分割线两端形状
     *
     * @return 分割线两端形状
     * {@link Paint.Cap#BUTT}
     * {@link Paint.Cap#ROUND}
     * {@link Paint.Cap#SQUARE}
     */
    public Paint.Cap getDividerCap() {
        return mDividerCap;
    }

    /**
     * 设置分割线两端形状
     *
     * @param dividerCap 分割线两端形状
     *                   {@link Paint.Cap#BUTT}
     *                   {@link Paint.Cap#ROUND}
     *                   {@link Paint.Cap#SQUARE}
     */
    public void setDividerCap(Paint.Cap dividerCap) {
        if (mDividerCap == dividerCap) {
            return;
        }
        mDividerCap = dividerCap;
        invalidate();
    }

    /**
     * 获取是否绘制选中区域
     *
     * @return 是否绘制选中区域
     */
    public boolean isDrawSelectedRect() {
        return isDrawSelectedRect;
    }

    /**
     * 设置是否绘制选中区域
     *
     * @param isDrawSelectedRect 是否绘制选中区域
     */
    public void setDrawSelectedRect(boolean isDrawSelectedRect) {
        this.isDrawSelectedRect = isDrawSelectedRect;
        invalidate();
    }

    /**
     * 获取选中区域颜色
     *
     * @return 选中区域颜色 ColorInt
     */
    public int getSelectedRectColor() {
        return mSelectedRectColor;
    }

    /**
     * 设置选中区域颜色
     *
     * @param selectedRectColorRes 选中区域颜色 {@link ColorRes}
     */
    public void setSelectedRectColorRes(@ColorRes int selectedRectColorRes) {
        setSelectedRectColor(ContextCompat.getColor(getContext(), selectedRectColorRes));
    }

    /**
     * 设置选中区域颜色
     *
     * @param selectedRectColor 选中区域颜色 {@link ColorInt}
     */
    public void setSelectedRectColor(@ColorInt int selectedRectColor) {
        mSelectedRectColor = selectedRectColor;
        invalidate();
    }

    /**
     * 获取选中监听
     *
     * @return 选中监听器
     */
    public OnItemSelectedListener<T> getOnItemSelectedListener() {
        return mOnItemSelectedListener;
    }

    /**
     * 设置选中监听
     *
     * @param onItemSelectedListener 选中监听器
     */
    public void setOnItemSelectedListener(OnItemSelectedListener<T> onItemSelectedListener) {
        mOnItemSelectedListener = onItemSelectedListener;
    }

    /**
     * 获取滚动变化监听
     *
     * @return 滚动变化监听器
     */
    public OnWheelChangedListener getOnWheelChangedListener() {
        return mOnWheelChangedListener;
    }

    /**
     * 设置滚动变化监听
     *
     * @param onWheelChangedListener 滚动变化监听器
     */
    public void setOnWheelChangedListener(OnWheelChangedListener onWheelChangedListener) {
        mOnWheelChangedListener = onWheelChangedListener;
    }



    /**
     * 自定义文字对齐方式注解
     * <p>
     * {@link #mTextAlign}
     * {@link #setTextAlign(int)}
     */
    @IntDef({TEXT_ALIGN_LEFT, TEXT_ALIGN_CENTER, TEXT_ALIGN_RIGHT})
    @Retention(value = RetentionPolicy.SOURCE)
    public @interface TextAlign {
    }

    /**
     * 自定义分割线类型注解
     * <p>
     * {@link #mDividerType}
     * {@link #setDividerType(int)}
     */
    @IntDef({DIVIDER_TYPE_FILL, DIVIDER_TYPE_WRAP})
    @Retention(value = RetentionPolicy.SOURCE)
    public @interface DividerType {
    }

    /**
     * 条目选中监听器
     *
     * @param <T>
     */
    public interface OnItemSelectedListener<T> {

        /**
         * 条目选中回调
         *
         * @param wheelViewDate wheelView
         * @param data      选中的数据
         * @param position  选中的下标
         */
        void onItemSelected(WheelViewDate<T> wheelViewDate, T data, int position);
    }

    /**
     * WheelView滚动状态改变监听器
     */
    public interface OnWheelChangedListener {

        /**
         * WheelView 滚动
         *
         * @param scrollOffsetY 滚动偏移
         */
        void onWheelScroll(int scrollOffsetY);

        /**
         * WheelView 条目变化
         *
         * @param oldPosition 旧的下标
         * @param newPosition 新下标
         */
        void onWheelItemChanged(int oldPosition, int newPosition);

        /**
         * WheelView 选中
         *
         * @param position 选中的下标
         */
        void onWheelSelected(int position);

        /**
         * WheelView 滚动状态
         *
         * @param state 滚动状态
         *              {@link WheelViewDate#SCROLL_STATE_IDLE}
         *              {@link WheelViewDate#SCROLL_STATE_DRAGGING}
         *              {@link WheelViewDate#SCROLL_STATE_SCROLLING}
         */
        void onWheelScrollStateChanged(int state);
    }
}
