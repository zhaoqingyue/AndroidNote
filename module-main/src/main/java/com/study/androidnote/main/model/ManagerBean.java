package com.study.androidnote.main.model;

/**
 * Created by zhao.qingyue on 2019/8/29.
 * 管理Bean
 */
public class ManagerBean {

    private int resId;     // icon
    private String name;   // 名称

    public ManagerBean() {

    }

    public ManagerBean(int resId, String name) {
        this.resId = resId;
        this.name = name;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ManagerBean{" + "resId=" + resId + ", name='" + name + '\'' + '}';
    }
}
