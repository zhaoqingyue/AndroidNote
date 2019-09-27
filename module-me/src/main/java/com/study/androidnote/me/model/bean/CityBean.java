package com.study.androidnote.me.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zhao.qingyue on 2019/9/26.
 * 城市Bean
 */
public class CityBean implements Parcelable {

    private String id;   // 城市id（110101）
    private String name; // 城市名称（东城区）
    
    public String getId() {
        return id == null ? "" : id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name == null ? "" : name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
    }
    
    public CityBean() {
    }
    
    protected CityBean(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
    }
    
    public static final Creator<CityBean> CREATOR = new Creator<CityBean>() {
        @Override
        public CityBean createFromParcel(Parcel source) {
            return new CityBean(source);
        }
        
        @Override
        public CityBean[] newArray(int size) {
            return new CityBean[size];
        }
    };
}
