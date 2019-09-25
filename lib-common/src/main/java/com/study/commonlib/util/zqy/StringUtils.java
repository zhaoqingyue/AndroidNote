package com.study.commonlib.util.zqy;

import android.content.Context;

import java.text.Normalizer;
import java.util.List;

/**
 * Created by zhao.qingyue on 2019/9/25.
 * 字符串工具类
 */
public class StringUtils {

    /**
     * 检查字符串是否为空和空
     */
    public static boolean checkEmptyString(String value) {
        return (value == null || value.isEmpty());
    }

    /**
     * 使字符串的首字母大写
     */
    public static String getStringFirstUpper(String value) {
        if (checkEmptyString(value)) {
            return "";
        } else if (value.length() == 1) {
            return value.toUpperCase();
        } else {
            return value.substring(0, 1).toUpperCase() + value.substring(1);
        }
    }

    /**
     * 使字符串的所有字母为大写
     */
    public static String getStringAllUpper(String value) {
        if (checkEmptyString(value)) {
            return "";
        } else if (value.length() == 1) {
            return value.toUpperCase();
        } else {
            return value.toUpperCase();
        }
    }

    /**
     * 使字符串的所有字母变小
     */
    public static String getStringAllLower(String value) {
        if (checkEmptyString(value)) {
            return "";
        } else if (value.length() == 1) {
            return value.toUpperCase();
        } else {
            return value.toLowerCase();
        }
    }

    /**
     * 将字符串中的所有单词大写
     * 警告：此方法将任何空格更改为常规空格" "
     */
    private static String capitalize(String value) {
        if (checkEmptyString(value)) {
            return "";
        }
        String[] wordsArray = value.split("\\s+");
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < wordsArray.length; i++) {
            String s = wordsArray[i];
            if (!checkEmptyString(s)) {
                String cap = s.substring(0, 1).toUpperCase() + s.substring(1);
                builder.append(cap);
                if (i < wordsArray.length) {
                    builder.append(" ");
                }
            }
        }
        return builder.toString();
    }

    /**
     * 去重
     */
    public static String removeAccents(String value) {
        if (checkEmptyString(value)) {
            return "";
        }
        value = Normalizer.normalize(value, Normalizer.Form.NFD);
        value = value.replaceAll("[^\\p{ASCII}]", "");
        return value;
    }

    /**
     * 从字符串中删除开头和结尾的空格
     */
    public static String removeLeadingAndTrailingWhiteSpaces(String value) {
        if (checkEmptyString(value)) {
            return "";
        }
        return value.trim();
    }

    /**
     * 将列表中的项目连接到由定界符分隔的字符串
     */
    public static String joinToStringWithDelimiters(String delimiter, List<Object> items) {
        if (delimiter == null) {
            return "";
        }
        if (items == null || items.isEmpty()) {
            return "";
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            builder.append(items.get(i).toString());
            if (i < items.size()) {
                builder.append(delimiter);
            }
        }
        return builder.toString();
    }

    /**
     * 用定界符分割字符串
     */
    public static String[] getStringSplitedByAnyWhiteSpace(String value, String delimiter) {
        if (checkEmptyString(value) || delimiter == null) {
            return new String[]{};
        }
        return value.split(delimiter);
    }

    /**
     * 通过资源名称返回字符串的值
     */
    public static String getStringValueByItsResourceName(Context context, String resName) {
        if (checkEmptyString(resName)) {
            return resName;
        }

        int resId = 0;
        try {
            String packageName = context.getPackageName();
            resId = context.getResources().getIdentifier(resName, "string", packageName);
        } catch (NullPointerException npe) {
            // nothing to do
        }

        if (resId == 0) {
            return resName;
        } else {
            return context.getResources().getString(resId);
        }
    }
}
