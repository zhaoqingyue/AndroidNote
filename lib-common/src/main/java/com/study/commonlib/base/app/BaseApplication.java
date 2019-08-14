package com.study.commonlib.base.app;

import android.app.Application;
import com.study.commonlib.util.utilcode.ClassUtils;
import com.study.commonlib.util.utilcode.Utils;

import java.util.List;

/**
 * Created by zhao.qingyue on 2019/6/18.
 * 注意事项：
 * 1. 要想使用BaseApplication，必须在组件中实现自己的Application，并且继承BaseApplication；
 * 2. 组件中实现的Application必须在debug包中的AndroidManifest.xml中注册，否则无法使用；
 * 3. 组件的Application需置于java/debug文件夹中，不得放于主代码；
 * 4. 组件中获取Context的方法必须为:Utils.getContext()，不允许其他写法；
 */

public class BaseApplication extends Application {

    private static BaseApplication sInstance;

    private List<IApplicationDelegate> mAppDelegateList;

    public static BaseApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        Utils.init(this);
        mAppDelegateList = ClassUtils.getObjectsWithInterface(this, IApplicationDelegate.class, Utils.getContext().getPackageName());
        for (IApplicationDelegate delegate : mAppDelegateList) {
            delegate.onCreate();
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        for (IApplicationDelegate delegate : mAppDelegateList) {
            delegate.onTerminate();
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        for (IApplicationDelegate delegate : mAppDelegateList) {
            delegate.onLowMemory();
        }
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        for (IApplicationDelegate delegate : mAppDelegateList) {
            delegate.onTrimMemory(level);
        }
    }
}
