package com.study.androidnote.lock.view.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.View;

import com.study.androidnote.lock.R;
import com.study.androidnote.lock.util.LockUtils;
import com.study.androidnote.lock.view.cell.LockCell;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhao.qingyue on 2019/8/27.
 * 自定义锁模式View
 */
public class LockView extends View {

    private float movingX, movingY;
    private boolean isActionMove = false;
    private boolean isActionDown = false;//default action down is false
    private boolean isActionUp = true;//default action up is true

    private int width, height;
    private int cellRadius, cellInnerRadius;
    private int cellBoxWidth, cellBoxHeight;
    //in stealth mode (default is false)
    private boolean mInStealthMode = false;
    //haptic feed back (default is false)
    private boolean mEnableHapticFeedback = false;
    //set delay time
    private long delayTime = 600L;
    //set offset to the boundary
    private int offset = 10;
    //draw view used paint
    private Paint defaultPaint, selectPaint, errorPaint;
    private Path trianglePath;
    private Matrix triangleMatrix;

    private LockCell[][] mCells = new LockCell[3][3];
    private List<LockCell> sCells = new ArrayList<LockCell>();
    private OnPatternListener patterListener;

    private static final String TAG = "LockPatternView";
    private static final double CONSTANT_COS_30 = Math.cos(Math.toRadians(30));

    public LockView(Context context) {
        this(context, null);
    }

    public LockView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * initialize
     */
    private void init() {
        initCellSize();
        init9Cells();
        initPaints();
        initPaths();
        initMatrixs();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawToCanvas(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        getMeasuredHeight();
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        // Log.e(TAG, "(width: " + width + "  ,  height" + height + ")");
        if (width != height) {
            throw new IllegalArgumentException("the width must be equals height");
        }
        initCellSize();
        set9CellsSize();
        invalidate();
    }

    /**
     * draw the view to canvas
     */
    private void drawToCanvas(Canvas canvas) {
        for (int i = 0; i < mCells.length; i++) {
            for (int j = 0; j < mCells[i].length; j++) {
                if (mCells[i][j].getStatus() == LockCell.STATE_CHECK) {
                    selectPaint.setStyle(Paint.Style.STROKE);
                    canvas.drawCircle(mCells[i][j].getX(), mCells[i][j].getY(), cellRadius, selectPaint);
                    selectPaint.setStyle(Paint.Style.FILL);
                    canvas.drawCircle(mCells[i][j].getX(), mCells[i][j].getY(), cellInnerRadius, selectPaint);
                } else if (mCells[i][j].getStatus() == LockCell.STATE_NORMAL) {
                    canvas.drawCircle(mCells[i][j].getX(), mCells[i][j].getY(), cellRadius, defaultPaint);
                } else if (mCells[i][j].getStatus() == LockCell.STATE_CHECK_ERROR) {
                    errorPaint.setStyle(Paint.Style.STROKE);
                    canvas.drawCircle(mCells[i][j].getX(), mCells[i][j].getY(), cellRadius, errorPaint);
                    errorPaint.setStyle(Paint.Style.FILL);
                    canvas.drawCircle(mCells[i][j].getX(), mCells[i][j].getY(), cellInnerRadius, errorPaint);
                }
            }
        }

        if (sCells.size() > 0) {
            //temporary cell: at the beginning the cell is the first of sCells
            LockCell tempCell = sCells.get(0);

            for (int i = 1; i < sCells.size(); i++) {
                LockCell cell = sCells.get(i);
                if (cell.getStatus() == LockCell.STATE_CHECK) {
                    drawLine(tempCell, cell, canvas, selectPaint);
                    drawNewTriangle(tempCell, cell, canvas, selectPaint);
                } else if (cell.getStatus() == LockCell.STATE_CHECK_ERROR) {
                    drawLine(tempCell, cell, canvas, errorPaint);
                    drawNewTriangle(tempCell, cell, canvas, errorPaint);
                }
                tempCell = cell;
            }

            if (isActionMove && !isActionUp) {
                drawLineFollowFinger(tempCell, canvas, selectPaint);
            }
        }
    }

    /**
     * initialize the view size (include the view width and the view height fro the AttributeSet)
     */
    @Deprecated
    private void initViewSize(Context context, AttributeSet attrs) {
        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            String name = attrs.getAttributeName(i);
            if ("layout_width".equals(name)) {
                String value = attrs.getAttributeValue(i);
                width = LockUtils.changeSize(context, value);
            }
            if ("layout_height".equals(attrs.getAttributeName(i))) {
                String value = attrs.getAttributeValue(i);
                height = LockUtils.changeSize(context, value);
            }
        }
        //check the width is or not equals height.
        //if not throw exception
        if (width != height) {
            throw new IllegalArgumentException("the width must be equals height");
        }
    }

    /**
     * initialize cell size (include circle radius, inner circle radius, cell box width, cell box height)
     */
    private void initCellSize() {
        cellRadius = (width - offset * 2) / 4 / 2;
        cellInnerRadius = cellRadius / 3;
        cellBoxWidth = (width - offset * 2) / 3;
        cellBoxHeight = (height - offset * 2) / 3;
    }

    /**
     * initialize nine cells
     */
    private void init9Cells() {
        //the distance between the center of two circles
        int distance = cellBoxWidth + cellBoxWidth / 2 - cellRadius;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                mCells[i][j] = new LockCell(distance * j + cellRadius + offset, distance * i + cellRadius + offset, i, j, 3 * i + j + 1);
            }
        }
    }

    /**
     * set nine cells size
     */
    private void set9CellsSize() {
        int distance = cellBoxWidth + cellBoxWidth / 2 - cellRadius;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                mCells[i][j].setX(distance * j + cellRadius + offset);
                mCells[i][j].setY(distance * i + cellRadius + offset);
            }
        }
    }

    /**
     * initialize paints
     */
    private void initPaints() {
        defaultPaint = new Paint();
        defaultPaint.setColor(getResources().getColor(R.color.lock_cell_default));
        defaultPaint.setStrokeWidth(2.0f);
        defaultPaint.setStyle(Paint.Style.STROKE);
        defaultPaint.setAntiAlias(true);

        selectPaint = new Paint();
        selectPaint.setColor(getResources().getColor(R.color.lock_cell_select));
        selectPaint.setStrokeWidth(3.0f);
        //selectPaint.setStyle(Style.STROKE);
        selectPaint.setAntiAlias(true);

        errorPaint = new Paint();
        errorPaint.setColor(getResources().getColor(R.color.lock_cell_error));
        errorPaint.setStrokeWidth(3.0f);
        //errorPaint.setStyle(Style.STROKE);
        errorPaint.setAntiAlias(true);
    }

    /**
     * initialize paths
     */
    private void initPaths() {
        trianglePath = new Path();
    }

    /**
     * initialize matrixs
     */
    private void initMatrixs() {
        triangleMatrix = new Matrix();
    }

    /**
     * draw line include circle(the line include inside the circle, the method is deprecated)
     */
    @Deprecated
    private void drawLineIncludeCircle(LockCell preCell, LockCell nextCell, Canvas canvas, Paint paint) {
        canvas.drawLine(preCell.getX(), preCell.getY(), nextCell.getX(), nextCell.getY(), paint);
    }

    /**
     * draw line not include circle (check whether the cell between two cells )
     */
    private void drawLine(LockCell preCell, LockCell nextCell, Canvas canvas, Paint paint) {
        LockCell centerCell = getCellBetweenTwoCells(preCell, nextCell);
        if (centerCell != null && sCells.contains(centerCell)) {
            drawLineNotIncludeCircle(centerCell, preCell, canvas, paint);
            drawLineNotIncludeCircle(centerCell, nextCell, canvas, paint);
        } else {
            drawLineNotIncludeCircle(preCell, nextCell, canvas, paint);
        }
    }

    /**
     * draw line not include circle (the line do not show inside the circle)
     */
    private void drawLineNotIncludeCircle(LockCell preCell, LockCell nextCell, Canvas canvas, Paint paint) {
        float distance = LockUtils.getDistanceBetweenTwoPoints(preCell.getX(), preCell.getY(), nextCell.getX(), nextCell.getY());
        float x1 = cellRadius / distance * (nextCell.getX() - preCell.getX()) + preCell.getX();
        float y1 = cellRadius / distance * (nextCell.getY() - preCell.getY()) + preCell.getY();
        float x2 = (distance - cellRadius) / distance * (nextCell.getX() - preCell.getX()) + preCell.getX();
        float y2 = (distance - cellRadius) / distance * (nextCell.getY() - preCell.getY()) + preCell.getY();
        canvas.drawLine(x1, y1, x2, y2, paint);
    }

    /**
     * get the cell between two cells (it has the limitation: the pattern must be 3x3)
     */
    private LockCell getCellBetweenTwoCells(LockCell preCell, LockCell nextCell) {
        //two cells are in the same row
        if (preCell.getRow() == nextCell.getRow()) {
            if (Math.abs(nextCell.getColumn() - preCell.getColumn()) > 1) {
                return mCells[preCell.getRow()][1];
            } else {
                return null;
            }
        }
        //two cells are in the same column
        else if (preCell.getColumn() == nextCell.getColumn()) {
            if (Math.abs(nextCell.getRow() - preCell.getRow()) > 1) {
                return mCells[1][preCell.getColumn()];
            } else {
                return null;
            }
        }
        //opposite angles
        else if (Math.abs(nextCell.getColumn() - preCell.getColumn()) > 1 && Math.abs(nextCell.getRow() - preCell.getRow()) > 1) {
            return mCells[1][1];
        } else {
            return null;
        }
    }

    /**
     * draw line follow finger(do not draw line inside the selected cell,
     * but it is only the starting cell not the other's cell)
     */
    private void drawLineFollowFinger(LockCell preCell, Canvas canvas, Paint paint) {
        float distance = LockUtils.getDistanceBetweenTwoPoints(preCell.getX(), preCell.getY(), movingX, movingY);
        if (distance > cellRadius) {
            float x1 = cellRadius / distance * (movingX - preCell.getX()) + preCell.getX();
            float y1 = cellRadius / distance * (movingY - preCell.getY()) + preCell.getY();
            canvas.drawLine(x1, y1, movingX, movingY, paint);
        }
    }

    /**
     * draw triangle
     */
    @Deprecated
    private void drawTriangle(LockCell preCell, LockCell nextCell, Canvas canvas, Paint paint) {
        float distance = LockUtils.getDistanceBetweenTwoPoints(preCell.getX(), preCell.getY(), nextCell.getX(), nextCell.getY());
        float x = cellInnerRadius * 2 / distance * (nextCell.getX() - preCell.getX()) + preCell.getX();
        float y = cellInnerRadius * 2 / distance * (nextCell.getY() - preCell.getY()) + preCell.getY();

        float angleX = LockUtils.getAngleLineIntersectX(preCell.getX(), preCell.getY(), nextCell.getX(), nextCell.getY(), distance);
        float angleY = LockUtils.getAngleLineIntersectY(preCell.getX(), preCell.getY(), nextCell.getX(), nextCell.getY(), distance);
        float x1, y1, x2, y2;
        //slide right down
        if (angleX >= 0 && angleX <= 90 && angleY >= 0 && angleY <= 90) {
            x1 = x - (float) (cellInnerRadius * Math.cos(Math.toRadians(angleX - 30)));
            y1 = y - (float) (cellInnerRadius * Math.sin(Math.toRadians(angleX - 30)));
            x2 = x - (float) (cellInnerRadius * Math.sin(Math.toRadians(angleY - 30)));
            y2 = y - (float) (cellInnerRadius * Math.cos(Math.toRadians(angleY - 30)));
        }
        //slide right up
        else if (angleX >= 0 && angleX <= 90 && angleY > 90 && angleY <= 180) {
            x1 = x - (float) (cellInnerRadius * Math.cos(Math.toRadians(angleX + 30)));
            y1 = y + (float) (cellInnerRadius * Math.sin(Math.toRadians(angleX + 30)));
            x2 = x - (float) (cellInnerRadius * Math.sin(Math.toRadians(180 - angleY + 30)));
            y2 = y + (float) (cellInnerRadius * Math.cos(Math.toRadians(180 - angleY + 30)));
        }
        //slide left up
        else if (angleX > 90 && angleX <= 180 && angleY >= 90 && angleY < 180) {
            x1 = x + (float) (cellInnerRadius * Math.cos(Math.toRadians(180 - angleX - 30)));
            y1 = y + (float) (cellInnerRadius * Math.sin(Math.toRadians(180 - angleX - 30)));
            x2 = x + (float) (cellInnerRadius * Math.sin(Math.toRadians(180 - angleY - 30)));
            y2 = y + (float) (cellInnerRadius * Math.cos(Math.toRadians(180 - angleY - 30)));
        }
        //slide left down
        else {
            x1 = x + (float) (cellInnerRadius * Math.cos(Math.toRadians(180 - angleX + 30)));
            y1 = y - (float) (cellInnerRadius * Math.sin(Math.toRadians(180 - angleX + 30)));
            x2 = x + (float) (cellInnerRadius * Math.sin(Math.toRadians(angleY + 30)));
            y2 = y - (float) (cellInnerRadius * Math.cos(Math.toRadians(angleY + 30)));
        }
        trianglePath.reset();
        trianglePath.moveTo(x, y);
        trianglePath.lineTo(x1, y1);
        trianglePath.lineTo(x2, y2);
        trianglePath.close();
        canvas.drawPath(trianglePath, paint);
    }

    /**
     * draw new triangle
     */
    private void drawNewTriangle(LockCell preCell, LockCell nextCell, Canvas canvas, Paint paint) {
        float distance = LockUtils.getDistanceBetweenTwoPoints(preCell.getX(), preCell.getY(), nextCell.getX(), nextCell.getY());
        float x = preCell.getX();
        float y = preCell.getY() - cellInnerRadius * 2;

        float x1 = x - cellInnerRadius / 2;
        float y1 = y + (float) (cellInnerRadius * CONSTANT_COS_30);
        float x2 = x + cellInnerRadius / 2;
        float y2 = y1;

        float angleX = LockUtils.getAngleLineIntersectX(preCell.getX(), preCell.getY(), nextCell.getX(), nextCell.getY(), distance);
        float angleY = LockUtils.getAngleLineIntersectY(preCell.getX(), preCell.getY(), nextCell.getX(), nextCell.getY(), distance);

        trianglePath.reset();
        trianglePath.moveTo(x, y);
        trianglePath.lineTo(x1, y1);
        trianglePath.lineTo(x2, y2);
        trianglePath.close();
        //slide right down and right up
        if (angleX >= 0 && angleX <= 90) {
            triangleMatrix.setRotate(180 - angleY, preCell.getX(), preCell.getY());
        }
        //slide left up and left down
        else {
            triangleMatrix.setRotate(angleY - 180, preCell.getX(), preCell.getY());
        }
        trianglePath.transform(triangleMatrix);
        canvas.drawPath(trianglePath, paint);
    }

    @SuppressLint( "ClickableViewAccessibility" )
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float ex = event.getX();
        float ey = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                handleActionDown(ex, ey);
                break;
            case MotionEvent.ACTION_MOVE:
                handleActionMove(ex, ey);
                break;
            case MotionEvent.ACTION_UP:
                handleActionUp();
                break;
        }
        return true;
    }

    /**
     * handle action down
     */
    private void handleActionDown(float ex, float ey) {
        isActionMove = false;
        isActionDown = true;
        isActionUp = false;

        setPattern(DisplayMode.DEFAULT);

        if (patterListener != null) {
            patterListener.onPatternStart();
        }

        LockCell cell = checkSelectCell(ex, ey);
        if (cell != null) {
            addSelectedCell(cell);
        }
    }

    /**
     * handle action move
     */
    private void handleActionMove(float ex, float ey) {
        isActionMove = true;
        movingX = ex;
        movingY = ey;
        LockCell cell = checkSelectCell(ex, ey);
        if (cell != null) {
            addSelectedCell(cell);
        }
        setPattern(DisplayMode.NORMAL);
    }

    /**
     * handle action up
     */
    private void handleActionUp() {
        isActionMove = false;
        isActionUp = true;
        isActionDown = false;

        setPattern(DisplayMode.NORMAL);

        if (patterListener != null) {
            patterListener.onPatternComplete(sCells);
        }
    }

    /**
     * check user's touch moving is or not in the area of cells
     */
    private LockCell checkSelectCell(float x, float y) {
        for (int i = 0; i < mCells.length; i++) {
            for (int j = 0; j < mCells[i].length; j++) {
                LockCell cell = mCells[i][j];
                if (LockUtils.checkInRound(cell.getX(), cell.getY(), 80, x, y, cellRadius / 4)) {
                    return cell;
                }
            }
        }
        return null;
    }

    /**
     * add selected cell
     */
    private void addSelectedCell(LockCell cell) {
        if (!sCells.contains(cell)) {
            cell.setStatus(LockCell.STATE_CHECK);
            handleHapticFeedback();
            sCells.add(cell);
        }
        setPattern(DisplayMode.NORMAL);
    }

    /**
     * handle haptic feedback(if mEnableHapticFeedback true: has haptic else not have haptic)
     */
    private void handleHapticFeedback() {
        if (mEnableHapticFeedback) {
            performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY, HapticFeedbackConstants.FLAG_IGNORE_VIEW_SETTING | HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
        }
    }

    /**
     * set default selected cell (the method is deprecated)
     *
     * @param value (ex: 2,1,3,6,4,5)
     * @return the selected cell
     */
    @Deprecated
    public List<LockCell> setDefaultSelectedCell(String value) {
        String[] str = value.split(",");
        for (int i = 0; i < str.length; i++) {
            int val = Integer.valueOf(str[i]);
            if (val <= 3) {
                addSelectedCell(mCells[0][val - 1]);
            } else if (val <= 6) {
                addSelectedCell(mCells[1][val - 4]);
            } else {
                addSelectedCell(mCells[2][val - 7]);
            }
        }
        return sCells;
    }

    /**
     * the display mode of the pattern
     */
    public enum DisplayMode {
        //show default pattern (the default pattern is initialize status)
        DEFAULT, //show selected pattern normal
        NORMAL, //show selected pattern error
        ERROR
    }

    /**
     * set pattern
     */
    public void setPattern(DisplayMode mode) {
        switch (mode) {
            case DEFAULT:
                for (LockCell cell : sCells) {
                    cell.setStatus(LockCell.STATE_NORMAL);
                }
                sCells.clear();
                break;
            case NORMAL:
                break;
            case ERROR:
                for (LockCell cell : sCells) {
                    cell.setStatus(LockCell.STATE_CHECK_ERROR);
                }
                break;
        }
        handleStealthMode();
    }

    /**
     * handle the stealth mode (if true: do not post invalidate; false: post invalidate)
     */
    private void handleStealthMode() {
        if (!mInStealthMode) {
            postInvalidate();
        }
    }

    /**
     * remove the post delay clear pattern
     */
    public void removePostClearPatternRunnable() {
        removeCallbacks(mClearPatternRunnable);
    }

    /**
     * delay clear pattern
     *
     * @param delay the delay time (if delay less than 0, it will be 600L)
     */
    public void postClearPatternRunnable(long delay) {
        if (delay >= 0L) {
            delayTime = delay;
        }
        removeCallbacks(mClearPatternRunnable);
        postDelayed(mClearPatternRunnable, delayTime);
    }

    private Runnable mClearPatternRunnable = new Runnable() {
        public void run() {
            setPattern(DisplayMode.DEFAULT);
        }
    };

    /**
     * @return Whether the view is in stealth mode.
     */
    public boolean isInStealthMode() {
        return mInStealthMode;
    }

    /**
     * Set whether the view is in stealth mode.  If true, there will be no visible feedback as the user enters the pattern.
     */
    public void setInStealthMode(boolean inStealthMode) {
        mInStealthMode = inStealthMode;
    }

    /**
     * @return Whether the view has tactile feedback enabled.
     */
    public boolean isTactileFeedbackEnabled() {
        return mEnableHapticFeedback;
    }

    /**
     * Set whether the view will use tactile feedback.  If true, there will be
     * tactile feedback as the user enters the pattern.
     *
     * @param tactileFeedbackEnabled Whether tactile feedback is enabled
     */
    public void setTactileFeedbackEnabled(boolean tactileFeedbackEnabled) {
        mEnableHapticFeedback = tactileFeedbackEnabled;
    }

    public void setOnPatternListener(OnPatternListener patternListener) {
        patterListener = patternListener;
    }

    /**
     * callback interface
     */
    public static interface OnPatternListener {
        public void onPatternStart();

        public void onPatternComplete(List<LockCell> cells);
    }
}
