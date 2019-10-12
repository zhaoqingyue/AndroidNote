package com.study.commonlib.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.study.commonlib.network.netState.NetChangeObserver;
import com.study.commonlib.network.netState.NetStateReceiver;
import com.study.commonlib.util.utilcode.NetworkUtils;

/**
 * Created by zhao.qingyue on 2019/10/11.
 * 基类Activity
 * 带监听网络变化的基类Activity
 */
public abstract class BaseNetStateActivity extends BaseActivity {
    /**
     * 网络观察者
     */
    protected NetChangeObserver mNetChangeObserver = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerObserver();
    }

    private void registerObserver() {
        // 网络改变的一个回掉类
        mNetChangeObserver = new NetChangeObserver() {

            @Override
            public void onNetConnected(NetworkUtils.NetworkType type) {
                onNetworkConnected(type);
            }

            @Override
            public void onNetDisConnect() {
                onNetworkDisConnected();
            }
        };
        //开启广播去监听网络改变事件
        NetStateReceiver.registerObserver(mNetChangeObserver);
    }

    /**
     * 网络连接状态
     */
    protected abstract void onNetworkConnected(NetworkUtils.NetworkType type);

    /**
     * 网络断开的时候调用
     */
    protected abstract void onNetworkDisConnected();
}
