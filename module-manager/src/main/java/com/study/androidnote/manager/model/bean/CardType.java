package com.study.androidnote.manager.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zhao.qingyue on 2019/8/30.
 * 卡类型
 */
public class CardType implements Parcelable {

    private int logoId;      // logo
    private String cardName; // 卡名称

    public CardType() {
    }

    public CardType(int logoId, String cardName) {
        this.logoId = logoId;
        this.cardName = cardName;
    }

    public int getLogoId() {
        return logoId;
    }

    public void setLogoId(int logoId) {
        this.logoId = logoId;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.logoId);
        dest.writeString(this.cardName);
    }

    protected CardType(Parcel in) {
        this.logoId = in.readInt();
        this.cardName = in.readString();
    }

    public static final Parcelable.Creator<CardType> CREATOR = new Parcelable.Creator<CardType>() {
        @Override
        public CardType createFromParcel(Parcel source) {
            return new CardType(source);
        }

        @Override
        public CardType[] newArray(int size) {
            return new CardType[size];
        }
    };
}
