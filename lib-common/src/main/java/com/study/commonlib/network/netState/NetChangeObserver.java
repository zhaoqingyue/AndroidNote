package com.study.commonlib.network.netState;

import com.study.commonlib.util.utilcode.NetworkUtils;

/**
 * 网络改变观察者，观察网络改变后回调的方法
 *
 * Created by zhao.qingyue on 2019/10/11.
 */
public interface NetChangeObserver {

    /**
     * 网络连接回调 type为网络类型
     */
     void onNetConnected(NetworkUtils.NetworkType type);

    /**
     * 没有网络
     */
     void onNetDisConnect();
}
