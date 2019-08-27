package com.study.biz.manager;

/**
 * Created by zhao.qingyue on 2019/8/23.
 * 用户信息管理器
 */
public class UserManager {

    private static UserManager instance;

    public static UserManager getInstance() {
        if (instance == null) {
            synchronized(UserManager.class) {
                if (instance == null) {
                    instance = new UserManager();
                }
            }
        }
        return instance;
    }

    /**
     * 根据checkedPosition获取性别
     */
    public String getSexByPos(int checkedPosition) {
        String sex = "";
        switch (checkedPosition) {
            case 0:{
                sex = "男";
                break;
            }
            case 1:{
                sex = "女";
                break;
            }
        }
        return sex;
    }

    /**
     * 根据pos获取性别
     */
    public int getSexPosByName(String sex) {
        int pos = 0;
        if (sex.equals("男")) {
            pos = 0;
        } else if (sex.equals("女")) {
            pos = 1;
        }
        return pos;
    }
}
