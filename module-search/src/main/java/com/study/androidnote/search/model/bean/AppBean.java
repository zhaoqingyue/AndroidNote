package com.study.androidnote.search.model.bean;

import android.graphics.drawable.Drawable;

/**
 * Created by zhao.qingyue on 2019/7/8.
 * 应用信息
 */
public class AppBean {

    private Long id;          // 序列ID
    private String appName;   // 应用名称
    private String pkgName;   // 应用包名
    private Drawable appIcon; // 应用图标
    private String className; // 启动类名
    private boolean added;    // 是否已添加到常驻应用列表

    public AppBean() {
    }

    public AppBean(Long id, String appName, String pkgName, Drawable appIcon, String className) {
        this.id = id;
        this.appName = appName;
        this.pkgName = pkgName;
        this.appIcon = appIcon;
        this.className = className;
    }

    public AppBean(Long id, String appName, String pkgName, Drawable appIcon, String className, boolean added) {
        this.id = id;
        this.appName = appName;
        this.pkgName = pkgName;
        this.appIcon = appIcon;
        this.className = className;
        this.added = added;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPkgName() {
        return pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public boolean isAdded() {
        return added;
    }

    public void setAdded(boolean added) {
        this.added = added;
    }

    @Override
    public String toString() {
        return "AppBean{" +
                "id=" + id +
                ", appName='" + appName + '\'' +
                ", pkgName='" + pkgName + '\'' +
                ", appIcon=" + appIcon +
                ", className='" + className + '\'' +
                ", added=" + added +
                '}';
    }
}
