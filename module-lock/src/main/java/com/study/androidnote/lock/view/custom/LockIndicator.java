package com.study.androidnote.lock.view.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.study.androidnote.lock.R;
import com.study.androidnote.lock.util.LockUtils;
import com.study.androidnote.lock.view.cell.IndicatorCell;
import com.study.androidnote.lock.view.cell.LockCell;

import java.util.List;

/**
 * Created by zhao.qingyue on 2019/8/27.
 * 锁模式指示器
 */
public class LockIndicator extends View {

    private int width, height;
    private int cellBoxWidth, cellBoxHeight;
    private int radius;
    private int offset = 2;
    private Paint defaultPaint, selectPaint;
    private IndicatorCell[][] mIndicatorCells = new IndicatorCell[3][3];

    public LockIndicator(Context context) {
        this(context, null);
    }

    public LockIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LockIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init();
    }

    private void init() {
        //initViewSize(context, attrs);
        initRadius();
        initPaint();
        init9IndicatorCells();
    }

    /**
     * init view size
     */
    @Deprecated
    private void initViewSize(Context context, AttributeSet attrs) {
        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            String name = attrs.getAttributeName(i);
            if ("layout_width".equals(name)) {
                String value = attrs.getAttributeValue(i);
                this.width = LockUtils.changeSize(context, value);
                //Log.e(TAG, "layout_width:" + value);
            }
            if ("layout_height".equals(attrs.getAttributeName(i))) {
                String value = attrs.getAttributeValue(i);
                this.height = LockUtils.changeSize(context, value);
            }
        }
        //check the width is or not equals height
        //if not throw exception
        if (this.width != this.height) {
            throw new IllegalArgumentException("the width must be equals height");
        }
    }

    private void initRadius() {
        this.radius = (this.width - offset * 2) / 4 / 2;
        this.cellBoxHeight = (this.height - offset * 2) / 3;
        this.cellBoxWidth = (this.width - offset * 2) / 3;
    }

    private void initPaint() {
        defaultPaint = new Paint();
        defaultPaint.setColor(getResources().getColor(R.color.lock_cell_default_indicator));
        defaultPaint.setStrokeWidth(3.0f);
        defaultPaint.setStyle(Paint.Style.STROKE);
        defaultPaint.setAntiAlias(true);

        selectPaint = new Paint();
        selectPaint.setColor(getResources().getColor(R.color.lock_cell_select));
        selectPaint.setStrokeWidth(3.0f);
        selectPaint.setStyle(Paint.Style.FILL);
        selectPaint.setAntiAlias(true);
    }

    /**
     * initialize nine cells
     */
    private void init9IndicatorCells() {
        int distance = this.cellBoxWidth + this.cellBoxWidth / 2 - this.radius;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                mIndicatorCells[i][j] = new IndicatorCell(distance * j + radius + offset, distance * i + radius + offset, 3 * i + j + 1);
            }
        }
    }

    /**
     * set nine indicator cells size
     */
    private void set9IndicatorCellsSize() {
        int distance = this.cellBoxWidth + this.cellBoxWidth / 2 - this.radius;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                mIndicatorCells[i][j].setX(distance * j + radius + offset);
                mIndicatorCells[i][j].setY(distance * i + radius + offset);
            }
        }
    }

    /**
     * set indicator
     */
    public void setIndicator(List<LockCell> cells) {
        for (LockCell cell : cells) {
            for (int i = 0; i < mIndicatorCells.length; i++) {
                for (int j = 0; j < mIndicatorCells[i].length; j++) {
                    if (cell.getIndex() == mIndicatorCells[i][j].getIndex()) {
                        mIndicatorCells[i][j].setStatus(IndicatorCell.STATE_CHECK);
                    }
                }
            }
        }
        this.postInvalidate();
    }

    /**
     * set default indicator
     */
    public void setDefaultIndicator() {
        for (int i = 0; i < mIndicatorCells.length; i++) {
            for (int j = 0; j < mIndicatorCells[i].length; j++) {
                mIndicatorCells[i][j].setStatus(IndicatorCell.STATE_NORMAL);
            }
        }
        this.postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawToCanvas(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.width = getMeasuredWidth();
        this.height = getMeasuredHeight();
        if (this.width != this.height) {
            throw new IllegalArgumentException("the width must be equals height");
        }
        this.initRadius();
        this.set9IndicatorCellsSize();
        this.invalidate();
    }

    /**
     * draw the view to canvas
     */
    private void drawToCanvas(Canvas canvas) {
        for (int i = 0; i < mIndicatorCells.length; i++) {
            for (int j = 0; j < mIndicatorCells[i].length; j++) {
                if (mIndicatorCells[i][j].getStatus() == IndicatorCell.STATE_NORMAL) {
                    canvas.drawCircle(mIndicatorCells[i][j].getX(), mIndicatorCells[i][j].getY(), radius, defaultPaint);
                } else if (mIndicatorCells[i][j].getStatus() == IndicatorCell.STATE_CHECK) {
                    canvas.drawCircle(mIndicatorCells[i][j].getX(), mIndicatorCells[i][j].getY(), radius, selectPaint);
                }
            }
        }
    }
}
