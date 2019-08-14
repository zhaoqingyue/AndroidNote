package com.study.commonlib.base.activity;

import android.os.Bundle;

/**
 * Created by chen.tuxi on 2018/10/16.
 * 具有网络功能的Activity继承该基类
 */

public abstract class BaseMvpActivity<T> extends BaseActivity {

    protected T mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPresenter();
    }

    protected abstract void initPresenter();

    protected boolean isNetEnble() {
        return false;
    }
}
