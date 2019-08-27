package com.study.biz.db.bean;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by zhao.qingyue on 2019/8/23.
 * 用户信息
 */
@Entity
public class UserInfo {
    @Id
    private Long userId;

    @Property(nameInDb = "token")
    private String token;     // 登录成功时的token

    @Property(nameInDb = "userName")
    private String userName;  // 用户真实姓名

    @Property(nameInDb = "avatar")
    private String avatar;   // 头像URL

    @Property(nameInDb = "nickName")
    private String nickName; // 昵称

    @Property(nameInDb = "account")
    private String account;   // 账号

    @Property(nameInDb = "password")
    private String password;   // 密码

    @Property(nameInDb = "phone")
    private String phone;     // 手机号

    @Property(nameInDb = "sex")
    private String sex;       // 性别

    @Property(nameInDb = "birthday")
    private String birthday;  // 出生日期

    @Property(nameInDb = "email")
    private String email;     // 邮箱

    @Property(nameInDb = "area")
    private String area;      // 地区

    @Property(nameInDb = "signature")
    private String signature; // 签名

    @Property(nameInDb = "shipAddress")
    private String shipAddress;// 收货地址

    @Generated(hash = 1492997409)
    public UserInfo(Long userId, String token, String userName, String avatar,
            String nickName, String account, String password, String phone,
            String sex, String birthday, String email, String area,
            String signature, String shipAddress) {
        this.userId = userId;
        this.token = token;
        this.userName = userName;
        this.avatar = avatar;
        this.nickName = nickName;
        this.account = account;
        this.password = password;
        this.phone = phone;
        this.sex = sex;
        this.birthday = birthday;
        this.email = email;
        this.area = area;
        this.signature = signature;
        this.shipAddress = shipAddress;
    }

    @Generated(hash = 1279772520)
    public UserInfo() {
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickName() {
        return this.nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAccount() {
        return this.account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return this.sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return this.birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getArea() {
        return this.area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getSignature() {
        return this.signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getShipAddress() {
        return this.shipAddress;
    }

    public void setShipAddress(String shipAddress) {
        this.shipAddress = shipAddress;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
