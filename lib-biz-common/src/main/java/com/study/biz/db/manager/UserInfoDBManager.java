package com.study.biz.db.manager;

import android.text.TextUtils;
import com.study.biz.db.bean.UserInfo;
import com.study.biz.db.dao.UserInfoDao;
import org.greenrobot.greendao.AbstractDao;

/**
 * Created by zhao.qingyue on 2019/1/10.
 * 用户数据库管理类
 */
public class UserInfoDBManager<T, V extends AbstractDao<T, Long>> extends DBManager<T,V> {

    public UserInfoDBManager(V dao) {
        super(dao);
    }

    // 根据ID更新用户名
    public void updateUserNameByUserId(Long userId, String userName) {
        UserInfo userInfo = (UserInfo) mDao.queryBuilder().where(UserInfoDao.Properties.UserId.eq(userId)).build().unique();
        if (userInfo == null)
            return;

        if (!TextUtils.isEmpty(userName))
            userInfo.setUserName(userName);

        mDao.update(userInfo);
    }

    // 根据ID更新头像
    public void updateAvatarByUserId(Long userId, String avatar) {
        UserInfo userInfo = (UserInfo) mDao.queryBuilder().where(UserInfoDao.Properties.UserId.eq(userId)).build().unique();
        if (userInfo == null)
            return;

        if (!TextUtils.isEmpty(avatar))
            userInfo.setAvatar(avatar);

        mDao.update(userInfo);
    }

    // 根据ID更新昵称
    public void updateNickNameByUserId(Long userId, String nickName) {
        UserInfo userInfo = (UserInfo) mDao.queryBuilder().where(UserInfoDao.Properties.UserId.eq(userId)).build().unique();
        if (userInfo == null)
            return;

        if (!TextUtils.isEmpty(nickName))
            userInfo.setNickName(nickName);

        mDao.update(userInfo);
    }

    // 根据ID更新账号
    public void updateAccountByUserId(Long userId, String account) {
        UserInfo userInfo = (UserInfo) mDao.queryBuilder().where(UserInfoDao.Properties.UserId.eq(userId)).build().unique();
        if (userInfo == null)
            return;

        if (!TextUtils.isEmpty(account))
            userInfo.setAccount(account);

        mDao.update(userInfo);
    }


    // 根据ID更新手机号
    public void updatePhoneByUserId(Long userId, String phone) {
        UserInfo userInfo = (UserInfo) mDao.queryBuilder().where(UserInfoDao.Properties.UserId.eq(userId)).build().unique();
        if (userInfo == null)
            return;

        if (!TextUtils.isEmpty(phone))
            userInfo.setPhone(phone);

        mDao.update(userInfo);
    }

    // 根据ID更新性别
    public void updateSexByUserId(Long userId, String sex) {
        UserInfo userInfo = (UserInfo) mDao.queryBuilder().where(UserInfoDao.Properties.UserId.eq(userId)).build().unique();
        if (userInfo == null)
            return;

        if (!TextUtils.isEmpty(sex))
            userInfo.setSex(sex);

        mDao.update(userInfo);
    }

    // 根据ID更新出生日期
    public void updateBirthdayByUserId(Long userId, String birthday) {
        UserInfo userInfo = (UserInfo) mDao.queryBuilder().where(UserInfoDao.Properties.UserId.eq(userId)).build().unique();
        if (userInfo == null)
            return;

        if (!TextUtils.isEmpty(birthday))
            userInfo.setBirthday(birthday);

        mDao.update(userInfo);
    }

    // 根据ID更新邮箱
    public void updateEmailByUserId(Long userId, String email) {
        UserInfo userInfo = (UserInfo) mDao.queryBuilder().where(UserInfoDao.Properties.UserId.eq(userId)).build().unique();
        if (userInfo == null)
            return;

        if (!TextUtils.isEmpty(email))
            userInfo.setEmail(email);

        mDao.update(userInfo);
    }

    // 根据ID更新地区
    public void updateAreaByUserId(Long userId, String area) {
        UserInfo userInfo = (UserInfo) mDao.queryBuilder().where(UserInfoDao.Properties.UserId.eq(userId)).build().unique();
        if (userInfo == null)
            return;

        if (!TextUtils.isEmpty(area))
            userInfo.setArea(area);

        mDao.update(userInfo);
    }

    // 根据ID更新签名
    public void updateSignatureByUserId(Long userId, String signature) {
        UserInfo userInfo = (UserInfo) mDao.queryBuilder().where(UserInfoDao.Properties.UserId.eq(userId)).build().unique();
        if (userInfo == null)
            return;

        if (!TextUtils.isEmpty(signature))
            userInfo.setSignature(signature);

        mDao.update(userInfo);
    }

    // 根据ID更新收货地址
    public void updateShipAddressByUserId(Long userId, String shipAddress) {
        UserInfo userInfo = (UserInfo) mDao.queryBuilder().where(UserInfoDao.Properties.UserId.eq(userId)).build().unique();
        if (userInfo == null)
            return;

        if (!TextUtils.isEmpty(shipAddress))
            userInfo.setShipAddress(shipAddress);

        mDao.update(userInfo);
    }
}
