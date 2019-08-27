package com.study.biz.manager;

import android.support.v7.app.AppCompatDelegate;

/**
 * Created by zhao.qingyue on 2019/8/23.
 * 设置管理器
 */
public class SettingManager {

    /**
     * 设置日间-夜间模式
     */
    public static void updateDayNightMode() {
        if (SpManager.isNightMode()) {
            // 夜间模式
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            // 日间模式
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
}
