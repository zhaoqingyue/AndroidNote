package com.study.androidnote;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.study.biz.BuildConfig;
import com.study.commonlib.base.app.BaseApplication;
import com.study.commonlib.network.netState.NetStateReceiver;

/**
 * Created by zhao.qingyue on 2019/8/13.
 * 程序入口
 */
public class NoteApp extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.IS_TEST) {
            //开启InstantRun之后，一定要在ARouter.init之前调用openDebug
            ARouter.openDebug();
            ARouter.openLog();
        }
        ARouter.init(this);

        // 动态注册网络变化广播
        NetStateReceiver.registerNetworkStateReceiver(this);

        // 网络请求初始化
//        String BASE_URL = BuildConfig.HOST;
//        RetrofitManager.getInstance().init(this, BASE_URL, Constans.KEY);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        NetStateReceiver.unRegisterNetworkStateReceiver(this);
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
