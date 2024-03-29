package com.study.biz.manager;

import com.study.biz.constant.AppConstant;
import com.study.biz.util.LockCache;
import com.study.commonlib.util.utilcode.SPUtils;
import com.study.commonlib.util.utilcode.Utils;

/**
 * Created by zhao.qingyue on 2019/8/23.
 * SharedPreferences 管理器
 */
public class SpManager {

    /**
     * 是否夜间模式
     */
    public static boolean isNightMode() {
        return SPUtils.getInstance().getBoolean(AppConstant.NIGHT_MODE, false);
    }

    /**
     * 设置夜间模式
     */
    public static void setNightMode(boolean nightMode) {
        SPUtils.getInstance().put(AppConstant.NIGHT_MODE, nightMode);
    }

    /**
     * 是否开启手势密码
     */
    public static boolean isOpenGesturePwd() {
        return SPUtils.getInstance().getBoolean(AppConstant.OPEN_GESTURE_PWD, false);
    }

    /**
     * 设置开启手势密码状态
     */
    public static void setOpenGesturePwd(boolean firstOpen) {
        SPUtils.getInstance().put(AppConstant.OPEN_GESTURE_PWD, firstOpen);
    }

    /**
     * 是否第一次打开
     */
    public static boolean isFirstOpen() {
        return SPUtils.getInstance().getBoolean(AppConstant.FIRST_OPEN, true);
    }

    /**
     * 设置第一次打开状态
     */
    public static void setFirstOpen(boolean firstOpen) {
        SPUtils.getInstance().put(AppConstant.FIRST_OPEN, firstOpen);
    }

    /**
     * 启动视频页是否打开
     */
    public static boolean isStartVideoOpen() {
        return SPUtils.getInstance().getBoolean(AppConstant.OPEN_START_VIDEO, false);
    }

    /**
     * 设置打开启动视频页
     */
    public static void setStartVideoOpen(boolean startVideoOpen) {
        SPUtils.getInstance().put(AppConstant.OPEN_START_VIDEO, startVideoOpen);
    }

    /**
     * 引导页是否打开
     */
    public static boolean isGuideOpen() {
        return SPUtils.getInstance().getBoolean(AppConstant.OPEN_GUIDE, false);
    }

    /**
     * 设置打开引导页
     */
    public static void setGuideOpen(boolean guideOpen) {
        SPUtils.getInstance().put(AppConstant.OPEN_GUIDE, guideOpen);
    }

    /**
     * 引导视频页是否打开
     */
    public static boolean isGuideVideoOpen() {
        return SPUtils.getInstance().getBoolean(AppConstant.OPEN_GUIDE_VIDEO, false);
    }

    /**
     * 设置打开引导视频页
     */
    public static void setGuideVideoOpen(boolean guideVideoOpen) {
        SPUtils.getInstance().put(AppConstant.OPEN_GUIDE_VIDEO, guideVideoOpen);
    }

    /**
     * 是否已经登录
     */
    public static boolean isLogin() {
        return SPUtils.getInstance().getBoolean(AppConstant.ALREADY_LOGIN, false);
    }

    /**
     * 设置登录状态
     */
    public static void setLoginStatus(boolean loginStatus) {
        SPUtils.getInstance().put(AppConstant.ALREADY_LOGIN, loginStatus);
    }
}
