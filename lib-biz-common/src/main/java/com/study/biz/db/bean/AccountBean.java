package com.study.biz.db.bean;

import com.study.commonlib.ui.recycleradapter.entity.MultiItemEntity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by zhao.qingyue on 2019/9/4.
 * 账号Bean
 */
@Entity
public class AccountBean implements MultiItemEntity {

    @Id
    private Long accountId;

    @Property(nameInDb = "logoId")
    private int logoId; // logo

    @Property(nameInDb = "userName")
    private String userName; // 用户名

    @Property(nameInDb = "nickName")
    private String nickName; // 昵称

    @Property(nameInDb = "accountType")
    private String accountType;   // 账号类型

    @Property(nameInDb = "account")
    private String account;   // 账号

    @Property(nameInDb = "password")
    private String password; // 密码

    @Property(nameInDb = "identity")
    private String identity; // 身份证号

    @Property(nameInDb = "mobile")
    private String mobile;   // 手机号

    @Property(nameInDb = "email")
    private String email;   // 邮箱

    @Property(nameInDb = "url")
    private String url;   // 网址

    private int itemType;    // Item类型

    public AccountBean() {
    }

    @Generated(hash = 62568291)
    public AccountBean(Long accountId, int logoId, String userName, String nickName,
            String accountType, String account, String password, String identity,
            String mobile, String email, String url, int itemType) {
        this.accountId = accountId;
        this.logoId = logoId;
        this.userName = userName;
        this.nickName = nickName;
        this.accountType = accountType;
        this.account = account;
        this.password = password;
        this.identity = identity;
        this.mobile = mobile;
        this.email = email;
        this.url = url;
        this.itemType = itemType;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public int getLogoId() {
        return logoId;
    }

    public void setLogoId(int logoId) {
        this.logoId = logoId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
