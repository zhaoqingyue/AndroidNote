package com.study.androidnote.me.model.bean;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by zhao.qingyue on 2019/9/26.
 * 国家编码Bean
 */
public class NationCodeBean implements Serializable {

    private String name;        // 地区名称
    private String namePinyin;  // 地区名称拼音
    private String firstSpell;  // 地区名称首字母
    private String code;        // 地区代码
    private String locale;      // 地区时区
    private String enName;      // 英文名

    public NationCodeBean() {
    }

    public NationCodeBean(String name, String namePinyin, String firstSpell, String code, String locale, String enName) {
        this.name = name;
        this.namePinyin = namePinyin;
        this.firstSpell = firstSpell;
        this.code = code;
        this.locale = locale;
        this.enName = enName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNamePinyin() {
        return namePinyin;
    }

    public void setNamePinyin(String namePinyin) {
        this.namePinyin = namePinyin;
    }

    public String getFirstSpell() {
        return firstSpell;
    }

    public void setFirstSpell(String firstSpell) {
        this.firstSpell = firstSpell;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    /**
     * 按拼音进行排序
     */
    public static class ComparatorPY implements Comparator<NationCodeBean> {
        @Override
        public int compare(NationCodeBean lhs, NationCodeBean rhs) {
            String str1 = lhs.namePinyin;
            String str2 = rhs.namePinyin;
            return str1.compareToIgnoreCase(str2);
        }
    }
}
