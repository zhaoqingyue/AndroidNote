package com.study.commonlib.base.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.study.commonlib.util.utilcode.ActivityUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;

/**
 * Created by zhao.qingyue on 2019/6/18.
 * 最基础的基类Activity
 * 所有Activity基类，修改之前务必通知大家
 */
public abstract class BaseActivity extends AppCompatActivity {

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
        if (receiveEventBus()) {
            EventBus.getDefault().register(this);
        }
        initData(savedInstanceState);
    }

    /**
     * 是否有WebView
     */
    protected boolean isHasWebView() {
        return false;
    }

    /**
     * 是否接收EventBus事件
     */
    protected boolean receiveEventBus() {
        return false;
    }

    protected abstract int getLayoutId();

    protected abstract void initData(Bundle saveInstanceState);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (receiveEventBus()) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public Resources getResources() {
        // 不跟随系统改变字体大小
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.fontScale = 1.0f;
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    public void goToActivity(Class<? extends Activity> activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

    public void goToActivity(Class<? extends Activity> activity, Bundle extras) {
        Intent intent = new Intent(this, activity);
        intent.putExtras(extras);
        startActivity(intent);
    }

    public void goToActivityForResult(Class<? extends Activity> activity, Bundle extras, int code) {
        Intent intent = new Intent(this, activity);
        intent.putExtras(extras);
        startActivityForResult(intent, code);
    }
    public void goToActivityForResult(Class<? extends Activity> activity, int code) {
        Intent intent = new Intent(this, activity);
        startActivityForResult(intent, code);
    }
}

