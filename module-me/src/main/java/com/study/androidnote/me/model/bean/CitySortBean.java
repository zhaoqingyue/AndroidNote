package com.study.androidnote.me.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by zhao.qingyue on 2019/9/26.
 * 城市排序Bean
 */
public class CitySortBean implements Serializable {

    private String id;          // 城市id（110101）
    private String name;        // 城市名称（东城区）
    private String sortLetter;  // 首字母
    private String namePinyin;  // 地区名称拼音

    public CitySortBean() {
    }

    public CitySortBean(String id, String name, String sortLetter, String namePinyin) {
        this.id = id;
        this.name = name;
        this.sortLetter = sortLetter;
        this.namePinyin = namePinyin;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSortLetter() {
        return sortLetter;
    }

    public void setSortLetter(String sortLetter) {
        this.sortLetter = sortLetter;
    }

    public String getNamePinyin() {
        return namePinyin;
    }

    public void setNamePinyin(String namePinyin) {
        this.namePinyin = namePinyin;
    }

    /**
     * 按拼音进行排序
     */
    public static class ComparatorPY implements Comparator<CitySortBean> {
        @Override
        public int compare(CitySortBean lhs, CitySortBean rhs) {
            String str1 = lhs.namePinyin;
            String str2 = rhs.namePinyin;
            return str1.compareToIgnoreCase(str2);
        }
    }
}
