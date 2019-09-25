package com.study.commonlib.ui.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.study.commonlib.R;
import com.study.commonlib.util.utilcode.SizeUtils;

/**
 * Created by zhao.qingyue on 2019/7/3.
 * 自定义加载进度
 */
public class ProgressBarLoading extends View {

    private Context mContext;
    private int mMax;//进度条最大的进度
    private int mDefaultHeight;//高度
    private int mCurProgress;//当前的进度
    private int mWidth;
    private int mHeight;
    private Paint mPaint;
    private int mColor;

    public ProgressBarLoading(Context context) {
        this(context, null);
    }

    public ProgressBarLoading(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressBarLoading(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initAttr(attrs);
    }

    private void initAttr(AttributeSet attrs) {
        TypedArray array = mContext.obtainStyledAttributes(attrs, R.styleable.ProgressBarLoading);
        mMax = array.getInt(R.styleable.ProgressBarLoading_pbl_max, 10);
        mCurProgress = array.getInt(R.styleable.ProgressBarLoading_pbl_progress, 0);
        mDefaultHeight = array.getInt(R.styleable.ProgressBarLoading_pbl_progressHeight, 200);
//        int color = getResources().getColor(R.color.usercenter_green_45c01a);
        mColor = array.getColor(R.styleable.ProgressBarLoading_pbl_progressColor, Color.parseColor("#45c01a"));
        array.recycle();

        mPaint = new Paint();
        mPaint.setColor(mColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        //矩形宽度为view的80%
        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            //mDefaultWidth 为你自定义设置的属性
            mWidth = SizeUtils.dp2px(300);
        } else if (widthMode == MeasureSpec.UNSPECIFIED) {
            mWidth = widthSize;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            //mDefaultHeight 为你自定义设置的属性
            mHeight = SizeUtils.dp2px(mDefaultHeight);
        }
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float result = mWidth * ((float) mCurProgress / (float) 100);
        canvas.drawRect(0, 0, result, mDefaultHeight, mPaint);
    }

    public int getMax() {
        return mMax;
    }

    public void setMax(int max) {
        mMax = max;
    }

    public int getCurProgress() {
        return mCurProgress;
    }

    public interface OnEndListener {
        void onEnd();//动画结束的回调
    }
    public void setCurProgress(int curProgress, long time, final OnEndListener listener) {
        if (mCurProgress == 100) {//重置mCurProgress为0
            mCurProgress = 0;
        }
        //注意是从 mCurProgress->curProgress 来动画来实现
        ValueAnimator animator = ValueAnimator.ofInt(mCurProgress, curProgress);
        animator.setDuration(time);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurProgress = (int) animation.getAnimatedValue();
                postInvalidate();//通知刷新
            }
        });
        animator.start();
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (listener != null) {
                    listener.onEnd();
                }
            }
            @Override
            public void onAnimationStart(Animator animation) {
            }
            @Override
            public void onAnimationCancel(Animator animation) {
            }
            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
    }

    public void setNormalProgress(int newProgress) {
        mCurProgress = 0;
        mCurProgress = newProgress;
        postInvalidate();
    }
}
