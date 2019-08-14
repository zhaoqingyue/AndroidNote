package com.study.commonlib.bean;

import android.graphics.drawable.Drawable;

/**
 * Created by zhao.qingyue on 2019/8/6.
 * App信息的Bean类
 */
public class AppInfo {

    private String appName;     // 应用名称
    private Drawable appIcon;   // 应用图标
    private String packageName; // 应用包名
    private String packagePath; // 应用包路径
    private String versionName; // 版本名称
    private int versionCode;    // 版本号
    private boolean isSystem;   // 是否系统应用

    public AppInfo() {

    }

    public AppInfo(String appName, Drawable appIcon, String packageName, String packagePath, String versionName, int versionCode, boolean isSystem) {
        this.appName = appName;
        this.appIcon = appIcon;
        this.packageName = packageName;
        this.packagePath = packagePath;
        this.versionName = versionName;
        this.versionCode = versionCode;
        this.isSystem = isSystem;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPackagePath() {
        return packagePath;
    }

    public void setPackagePath(String packagePath) {
        this.packagePath = packagePath;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public boolean isSystem() {
        return isSystem;
    }

    public void setSystem(boolean system) {
        isSystem = system;
    }

    @Override
    public String toString() {
        return "AppInfo{" +
                "appName='" + appName + '\'' +
                ", appIcon=" + appIcon +
                ", packageName='" + packageName + '\'' +
                ", packagePath='" + packagePath + '\'' +
                ", versionName='" + versionName + '\'' +
                ", versionCode=" + versionCode +
                ", isSystem=" + isSystem +
                '}';
    }
}
