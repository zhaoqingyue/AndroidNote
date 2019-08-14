package com.study.commonlib.base.app;

import android.support.annotation.Keep;

/**
 * Created by zhao.qingyue on 2019/6/18.
 * @name ApplicationDelegate
 */
@Keep
public interface IApplicationDelegate {

    void onCreate();

    void onTerminate();

    void onLowMemory();

    void onTrimMemory(int level);

}
