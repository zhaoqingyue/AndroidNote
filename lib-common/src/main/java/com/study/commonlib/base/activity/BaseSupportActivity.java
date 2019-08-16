package com.study.commonlib.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.study.commonlib.ui.fragmentation.SupportActivity;
import com.study.commonlib.util.utilcode.ActivityUtils;

import butterknife.ButterKnife;

/**
 * Created by zhao.qingyue on 2019/8/15.
 * 基类-继承SupportActivity
 */
public abstract class BaseSupportActivity extends SupportActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * 解决系统App使用WebView时报错
         * hookWebView();必须在setContentView();之前调用
         * 在使用WebView的Activity重写isHasWebView()方法，并返回true
         * add by zhaoqingyue 2019-02-26
         */
        if (isHasWebView()) {
            ActivityUtils.hookWebView();
        }
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        initFragment(savedInstanceState);
        initData(savedInstanceState);
    }

    /**
     * 是否有WebView
     */
    protected boolean isHasWebView() {
        return false;
    }

    protected abstract int getLayoutId();

    protected abstract void initFragment(Bundle saveInstanceState);

    protected abstract void initData(Bundle saveInstanceState);


}
