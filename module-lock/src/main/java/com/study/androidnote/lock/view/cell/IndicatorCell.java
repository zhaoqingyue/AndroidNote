package com.study.androidnote.lock.view.cell;

/**
 * Created by zhao.qingyue on 2019/8/27.
 * 指示器元素
 */
public class IndicatorCell {
    private int x;          // the center x of circle
    private int y;          // the center y of circle
    private int status = 0; // default
    private int index;      // the cell value

    public static final int STATE_NORMAL = 0;
    public static final int STATE_CHECK = 1;

    public IndicatorCell() {

    }

    public IndicatorCell(int x, int y, int index) {
        this.x = x;
        this.y = y;
        this.index = index;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
