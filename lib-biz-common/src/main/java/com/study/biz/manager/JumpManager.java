package com.study.biz.manager;

import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.study.biz.constant.AppConstant;
import com.study.biz.constant.ArouterPath;
import com.study.biz.util.LockCache;
import com.study.commonlib.util.utilcode.Utils;

/**
 * Created by zhao.qingyue on 2019/8/23.
 * 跳转管理器
 */
public class JumpManager {
    /**
     * 结束启动页——跳转到主页或登录页
     */
    public static void endOfWelcome() {
        // 判断是否登录，如果没有，则进入登录界面去；否则进入主页
        if (SpManager.isLogin()) {
            ARouter.getInstance().build(ArouterPath.PATH_MAIN).navigation();
        } else {
            ARouter.getInstance().build(ArouterPath.PATH_ACCOUNT_LOGIN).navigation();
        }
    }

    /**
     * 登录成功——跳转到主页
     */
    public static void loginSuccess() {
        SpManager.setLoginStatus(true);
        ARouter.getInstance().build(ArouterPath.PATH_MAIN).navigation();
    }

    /**
     * 注册成功——跳转到主页
     */
    public static void registerSuccess() {
        SpManager.setLoginStatus(true);
        ARouter.getInstance().build(ArouterPath.PATH_MAIN).navigation();
    }

    /**
     * 跳转至手势密码
     */
    public static void gotoGesturePwd() {
        String gesturePwd = LockCache.get(Utils.getContext()).getAsString(AppConstant.GESTURE_PASSWORD);
        if (TextUtils.isEmpty(gesturePwd)) {
            ARouter.getInstance().build(ArouterPath.PATH_GESTURE_CREATE).navigation();
        } else {
            ARouter.getInstance().build(ArouterPath.PATH_GESTURE_LOGIN).navigation();
        }
    }
}
