package com.study.androidnote.manager.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zhao.qingyue on 2019/8/30.
 * 账户类型
 */
public class AccountType implements Parcelable {

    private int logoId;         // logo
    private String accountName; // 账户名称

    public AccountType() {
    }

    public AccountType(int logoId, String accountName) {
        this.logoId = logoId;
        this.accountName = accountName;
    }

    public int getLogoId() {
        return logoId;
    }

    public void setLogoId(int logoId) {
        this.logoId = logoId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.logoId);
        dest.writeString(this.accountName);
    }

    protected AccountType(Parcel in) {
        this.logoId = in.readInt();
        this.accountName = in.readString();
    }

    public static final Creator<AccountType> CREATOR = new Creator<AccountType>() {
        @Override
        public AccountType createFromParcel(Parcel source) {
            return new AccountType(source);
        }

        @Override
        public AccountType[] newArray(int size) {
            return new AccountType[size];
        }
    };
}
