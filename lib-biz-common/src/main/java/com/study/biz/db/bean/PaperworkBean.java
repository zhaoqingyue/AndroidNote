package com.study.biz.db.bean;

import com.study.commonlib.ui.recycleradapter.entity.MultiItemEntity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by zhao.qingyue on 2019/9/4.
 * 证件Bean
 */
@Entity
public class PaperworkBean implements MultiItemEntity {

    @Id
    private Long paperworkId;

    @Property(nameInDb = "logoId")
    private int logoId; // logo

    @Property(nameInDb = "paperworkTypeName")
    private String paperworkTypeName; // 证件类型名称

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

    public PaperworkBean() {

    }

    public PaperworkBean(Long paperworkId, int logoId, String paperworkTypeName, String userName, String identity, String mobile, String account, String password) {
        this.paperworkId = paperworkId;
        this.logoId = logoId;
        this.paperworkTypeName = paperworkTypeName;
        this.userName = userName;
        this.identity = identity;
        this.mobile = mobile;
        this.account = account;
        this.password = password;
    }

    @Generated(hash = 1422124975)
    public PaperworkBean(Long paperworkId, int logoId, String paperworkTypeName, String userName, String identity, String mobile, String account, String password,
            int itemType) {
        this.paperworkId = paperworkId;
        this.logoId = logoId;
        this.paperworkTypeName = paperworkTypeName;
        this.userName = userName;
        this.identity = identity;
        this.mobile = mobile;
        this.account = account;
        this.password = password;
        this.itemType = itemType;
    }

    public Long getPaperworkId() {
        return paperworkId;
    }

    public void setPaperworkId(Long paperworkId) {
        this.paperworkId = paperworkId;
    }

    public int getLogoId() {
        return logoId;
    }

    public void setLogoId(int logoId) {
        this.logoId = logoId;
    }

    public String getPaperworkTypeName() {
        return paperworkTypeName;
    }

    public void setPaperworkTypeName(String paperworkTypeName) {
        this.paperworkTypeName = paperworkTypeName;
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
