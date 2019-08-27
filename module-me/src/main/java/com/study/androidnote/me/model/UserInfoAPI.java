package com.study.androidnote.me.model;

import com.study.biz.bean.event.UserInfoUpdateEvent;
import com.study.biz.constant.AppConstant;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zhao.qingyue on 2019/8/26.
 * 用户信息API
 */
public class UserInfoAPI {

    private static UserInfoAPI mUserInfoAPI = null;

    public static UserInfoAPI getInstance() {
        if (mUserInfoAPI == null) {
            synchronized (UserInfoAPI.class) {
                if (mUserInfoAPI == null) {
                    mUserInfoAPI = new UserInfoAPI();
                }
            }
        }
        return mUserInfoAPI;
    }

    /**
     * 更新用户名
     */
    public void updateUserName(String userName) {
        UserInfoUpdateEvent event = new UserInfoUpdateEvent();
        event.msgId = AppConstant.EDIT_INFO_USERNAME;
        event.errCode = 0;
        event.msg = userName;
        EventBus.getDefault().post(event);
    }

    /**
     * 更新头像
     */
    public void updateAvatar(String avatar) {
        UserInfoUpdateEvent event = new UserInfoUpdateEvent();
        event.msgId = AppConstant.EDIT_INFO_AVATAR;
        event.errCode = 0;
        event.msg = avatar;
        EventBus.getDefault().post(event);
    }

    /**
     * 更新昵称
     */
    public void updateNickName(String nickName) {
        UserInfoUpdateEvent event = new UserInfoUpdateEvent();
        event.msgId = AppConstant.EDIT_INFO_NICKNAME;
        event.errCode = 0;
        event.msg = nickName;
        EventBus.getDefault().post(event);
    }

    /**
     * 更新账号
     */
    public void updateAccount(String account) {
        UserInfoUpdateEvent event = new UserInfoUpdateEvent();
        event.msgId = AppConstant.EDIT_INFO_ACCOUNT;
        event.errCode = 0;
        event.msg = account;
        EventBus.getDefault().post(event);
    }

    /**
     * 更新手机号
     */
    public void updatePhone(String phone) {
        UserInfoUpdateEvent event = new UserInfoUpdateEvent();
        event.msgId = AppConstant.EDIT_INFO_PHONE;
        event.errCode = 0;
        event.msg = phone;
        EventBus.getDefault().post(event);
    }

    /**
     * 性别
     */
    public void updateSex(String sex) {
        UserInfoUpdateEvent event = new UserInfoUpdateEvent();
        event.msgId = AppConstant.EDIT_INFO_SEX;
        event.errCode = 0;
        event.msg = sex;
        EventBus.getDefault().post(event);
    }

    /**
     * 更新出生日期
     */
    public void updateBirthday(String birthday) {
        UserInfoUpdateEvent event = new UserInfoUpdateEvent();
        event.msgId = AppConstant.EDIT_INFO_BIRTHDAY;
        event.errCode = 0;
        event.msg = birthday;
        EventBus.getDefault().post(event);
    }

    /**
     * 更新邮箱
     */
    public void updateEmail(String email) {
        UserInfoUpdateEvent event = new UserInfoUpdateEvent();
        event.msgId = AppConstant.EDIT_INFO_EMAIL;
        event.errCode = 0;
        event.msg = email;
        EventBus.getDefault().post(event);
    }

    /**
     * 更新地区
     */
    public void updateArea(String area) {
        UserInfoUpdateEvent event = new UserInfoUpdateEvent();
        event.msgId = AppConstant.EDIT_INFO_AREA;
        event.errCode = 0;
        event.msg = area;
        EventBus.getDefault().post(event);
    }

    /**
     * 更新签名
     */
    public void updateSignature(String signature) {
        UserInfoUpdateEvent event = new UserInfoUpdateEvent();
        event.msgId = AppConstant.EDIT_INFO_SIGNATURE;
        event.errCode = 0;
        event.msg = signature;
        EventBus.getDefault().post(event);
    }

    /**
     * 更新收货地址
     */
    public void updateShipAddress(String shipAddress) {
        UserInfoUpdateEvent event = new UserInfoUpdateEvent();
        event.msgId = AppConstant.EDIT_INFO_SHOP_ADDRESS;
        event.errCode = 0;
        event.msg = shipAddress;
        EventBus.getDefault().post(event);
    }
}
