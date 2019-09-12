package com.study.biz.db.bean;

import com.study.commonlib.ui.recycleradapter.entity.MultiItemEntity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by zhao.qingyue on 2019/9/2.
 * 银行卡Bean
 */
@Entity
public class BankBean implements MultiItemEntity {

    @Id
    private Long bankId;

    @Property(nameInDb = "logoId")
    private int logoId; // logo

    @Property(nameInDb = "bankTypeName")
    private String bankTypeName; // 卡类型名称

    @Property(nameInDb = "bankType")
    private int bankType; // 卡类型

    @Property(nameInDb = "userName")
    private String userName; // 用户名

    @Property(nameInDb = "identity")
    private String identity; // 身份证号

    @Property(nameInDb = "mobile")
    private String mobile;   // 手机号

    @Property(nameInDb = "account")
    private String account;   // 账号

    @Property(nameInDb = "password")
    private String password; // 密码

    private int itemType;    // Item类型

    public BankBean() {

    }

    public BankBean(Long bankId, int logoId, String bankTypeName, int bankType, String userName, String identity, String mobile, String account, String password) {
        this.bankId = bankId;
        this.logoId = logoId;
        this.bankTypeName = bankTypeName;
        this.bankType = bankType;
        this.userName = userName;
        this.identity = identity;
        this.mobile = mobile;
        this.account = account;
        this.password = password;
    }

    @Generated(hash = 1577936065)
    public BankBean(Long bankId, int logoId, String bankTypeName, int bankType, String userName, String identity, String mobile, String account, String password, int itemType) {
        this.bankId = bankId;
        this.logoId = logoId;
        this.bankTypeName = bankTypeName;
        this.bankType = bankType;
        this.userName = userName;
        this.identity = identity;
        this.mobile = mobile;
        this.account = account;
        this.password = password;
        this.itemType = itemType;
    }

    public Long getBankId() {
        return bankId;
    }

    public void setBankId(Long bankId) {
        this.bankId = bankId;
    }

    public int getLogoId() {
        return logoId;
    }

    public void setLogoId(int logoId) {
        this.logoId = logoId;
    }

    public String getBankTypeName() {
        return bankTypeName;
    }

    public void setBankTypeName(String bankTypeName) {
        this.bankTypeName = bankTypeName;
    }

    public int getBankType() {
        return bankType;
    }

    public void setBankType(int bankType) {
        this.bankType = bankType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
