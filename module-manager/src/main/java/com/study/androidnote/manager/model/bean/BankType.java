package com.study.androidnote.manager.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zhao.qingyue on 2019/9/2.
 * 银行类型
 */
public class BankType implements Parcelable {

    private int logoId;      // logo
    private String bankName; // 银行名称

    public BankType() {

    }

    public BankType(int logoId, String bankName) {
        this.logoId = logoId;
        this.bankName = bankName;
    }

    public int getLogoId() {
        return logoId;
    }

    public void setLogoId(int logoId) {
        this.logoId = logoId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    @Override
    public String toString() {
        return "BankType{" + "logoId=" + logoId + ", bankName='" + bankName + '\'' + '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.logoId);
        dest.writeString(this.bankName);
    }

    protected BankType(Parcel in) {
        this.logoId = in.readInt();
        this.bankName = in.readString();
    }

    public static final Creator<BankType> CREATOR = new Creator<BankType>() {
        @Override
        public BankType createFromParcel(Parcel source) {
            return new BankType(source);
        }

        @Override
        public BankType[] newArray(int size) {
            return new BankType[size];
        }
    };
}
