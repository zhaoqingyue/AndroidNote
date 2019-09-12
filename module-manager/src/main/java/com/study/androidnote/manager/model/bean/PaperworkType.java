package com.study.androidnote.manager.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zhao.qingyue on 2019/9/4.
 * 证件类型
 */

public class PaperworkType implements Parcelable {

    private int logoId;           // logo
    private String paperworkName; // 证件名称

    public PaperworkType() {
    }

    public PaperworkType(int logoId, String paperworkName) {
        this.logoId = logoId;
        this.paperworkName = paperworkName;
    }

    public int getLogoId() {
        return logoId;
    }

    public void setLogoId(int logoId) {
        this.logoId = logoId;
    }

    public String getPaperworkName() {
        return paperworkName;
    }

    public void setPaperworkName(String paperworkName) {
        this.paperworkName = paperworkName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.logoId);
        dest.writeString(this.paperworkName);
    }

    protected PaperworkType(Parcel in) {
        this.logoId = in.readInt();
        this.paperworkName = in.readString();
    }

    public static final Parcelable.Creator<PaperworkType> CREATOR = new Parcelable.Creator<PaperworkType>() {
        @Override
        public PaperworkType createFromParcel(Parcel source) {
            return new PaperworkType(source);
        }

        @Override
        public PaperworkType[] newArray(int size) {
            return new PaperworkType[size];
        }
    };
}
