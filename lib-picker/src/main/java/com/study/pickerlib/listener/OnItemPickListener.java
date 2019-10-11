package com.study.pickerlib.listener;

/**
 * 点击确认按钮选中item的回调
 */

public interface OnItemPickListener<T> {

    void onItemPicked(int index, T item);
}
