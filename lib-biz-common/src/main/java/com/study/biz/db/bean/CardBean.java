package com.study.biz.db.bean;

import com.study.commonlib.ui.recycleradapter.entity.MultiItemEntity;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

/**
 * Created by zhao.qingyue on 2019/8/30.
 * 卡bean
 */
@Entity
public class CardBean implements MultiItemEntity {

    @Id
    private Long cardId;

    @Property(nameInDb = "userName")
    private String userName; // 用户名

    @Property(nameInDb = "logoId")
    private int logoId; // logo

    @Property(nameInDb = "cardTypeName")
    private String cardTypeName; // 卡类型名称

    @Property(nameInDb = "certificateType")
    private String certificateType; // 证件类型

    @Property(nameInDb = "certificateNumber")
    private String certificateNumber;// 证件号

    @Property(nameInDb = "computerNumber")
    private String computerNumber; // 电脑号

    @Property(nameInDb = "cardNumber")
    private String cardNumber; // 卡号

    @Property(nameInDb = "bankName")
    private String bankName; // 银行名称

    @Property(nameInDb = "bankNumber")
    private String bankNumber;  // 银行卡号

    @Property(nameInDb = "identity")
    private String identity; // 身份证号

    @Property(nameInDb = "mobile")
    private String mobile;   // 手机号

    @Property(nameInDb = "account")
    private String account;   // 账号

    @Property(nameInDb = "password")
    private String password; // 密码

    private int itemType;    // Item类型
    
    public CardBean() {
    }

    @Generated(hash = 495799044)
    public CardBean(Long cardId, String userName, int logoId, String cardTypeName, String certificateType, String certificateNumber,
            String computerNumber, String cardNumber, String bankName, String bankNumber, String identity, String mobile, String account,
            String password, int itemType) {
        this.cardId = cardId;
        this.userName = userName;
        this.logoId = logoId;
        this.cardTypeName = cardTypeName;
        this.certificateType = certificateType;
        this.certificateNumber = certificateNumber;
        this.computerNumber = computerNumber;
        this.cardNumber = cardNumber;
        this.bankName = bankName;
        this.bankNumber = bankNumber;
        this.identity = identity;
        this.mobile = mobile;
        this.account = account;
        this.password = password;
        this.itemType = itemType;
    }



    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCardTypeName() {
        return cardTypeName;
    }

    public void setCardTypeName(String cardTypeName) {
        this.cardTypeName = cardTypeName;
    }

    public String getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(String certificateType) {
        this.certificateType = certificateType;
    }

    public String getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    public String getComputerNumber() {
        return computerNumber;
    }

    public void setComputerNumber(String computerNumber) {
        this.computerNumber = computerNumber;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
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

    public int getLogoId() {
        return this.logoId;
    }

    public void setLogoId(int logoId) {
        this.logoId = logoId;
    }
}
