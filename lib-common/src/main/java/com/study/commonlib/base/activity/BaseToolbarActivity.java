package com.study.commonlib.base.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import com.gyf.barlibrary.ImmersionBar;
import com.study.commonlib.R;

/**
 * Created by zhao.qingyue on 2019/4/2.
 * 基类Activity
 * 带Toolbar的基类Activity，可设置沉浸式
 */
public abstract class BaseToolbarActivity extends BaseActivity {

    private ImmersionBar mImmersionBar;
    private Toolbar toolbar;
    private TextView tv_title;
    private TextView tv_right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar();
        initImmersionBar();
    }

    protected abstract String getTitleName();

    /**
     * 是否可以使用沉浸式
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }

    /**
     * 是否有Toolbar
     */
    protected boolean hasToolbar() {
        return true;
    }

    /**
     * 是否有导航图标
     */
    protected boolean isHasNaviIcon() {
        return true;
    }

    /**
     * 是否有导航图标
     */
    protected String getRightText() {
        return "";
    }

    /**
     * 初始化mImmersionBar
     */
    protected void initImmersionBar() {
        if (isImmersionBarEnabled()) {
            mImmersionBar = ImmersionBar.with(this);
            if (ImmersionBar.isSupportStatusBarDarkFont()) {
                // 状态栏字体颜色为深色(android6.0以上或者miuiv6以上或者flymeOS4+)
                mImmersionBar.statusBarDarkFont(true);
            }
            mImmersionBar.init();
            if (hasToolbar()) {
                if (toolbar != null) {
                    mImmersionBar.titleBar(toolbar).init();
                }
            }
        }
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_right = (TextView) findViewById(R.id.tv_right);

        if (hasToolbar()) {
            if (toolbar != null) {
                toolbar.setTitle("");
                tv_title.setText(getTitleName());

                String rightText = getRightText();
                tv_right.setText(rightText);

                if (isHasNaviIcon()) {
                    // 设置导航按钮图标
                    toolbar.setNavigationIcon(R.mipmap.icon_arrow_back);
                }

                setSupportActionBar(toolbar);
                ActionBar actionBar = getSupportActionBar();
                if (actionBar != null && isHasNaviIcon()) {
                    // 设置导航按钮enable
                    actionBar.setDisplayHomeAsUpEnabled(true);
                    actionBar.setHomeButtonEnabled(true);
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Toolbar导航按钮的按键事件
            case android.R.id.home: {
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null) {
            // 销毁mImmersionBar
            mImmersionBar.destroy();
        }
    }
}
