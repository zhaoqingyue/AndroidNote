package com.study.commonlib.util.zqy;

import android.location.Location;

import java.util.Arrays;
import java.util.List;

/**
 * Created by zhao.qingyue on 2019/9/25.
 * 比较工具类
 */
public class EqualsUtils {

    /**
     * 返回对象哈希码
     */
    public static <T> int hashCodeCheckNull(T object) {
        return object == null ? 0 : object.hashCode();
    }

    /**
     * 比较两个对象是否相等
     */
    public static <T> boolean equalsCheckNull(T lhs, T rhs) {
        return lhs == null ? rhs == null : lhs.equals(rhs);
    }

    /**
     * 返回项目列表的哈希码
     */
    public static int itemsHashCode(List<?> items) {
        int result = 17; 
        if (items != null) {
            for (int i = 0; i < items.size(); i++) {
                result = 31 * result + (items.get(i) == null ? 0 : items.get(i).hashCode());
            }
        }
        return result;
    }

    /**
     * 返回整数数组的哈希码
     */
    public static int itemsHashCode(int[] items) {
        int result = 17;
        if (items != null) {
            for (int i = 0; i < items.length; i++) {
                result = 31 * result + items[i];
            }
        }
        return result;
    }

    /**
     * 比较两个列表是否相等
     */
    public static boolean itemsEqual(List<?> lhs, List<?> rhs) {
        if (lhs == rhs)
            return true;

        if (lhs == null || rhs == null)
            return false;

        if (lhs.size() != rhs.size())
            return false;
                
        for (int i = 0; i < lhs.size(); i++) {
            if (lhs.get(i) == null) {
                if (rhs.get(i) != null)
                    return false;
            } else if (!lhs.get(i).equals(rhs.get(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 比较两个整数数组是否相等
     */
    public static boolean itemsEqual(int[] lhs, int[] rhs) {
        if (lhs == rhs)
            return true;

        if (lhs == null || rhs == null || lhs.length != rhs.length)
            return false;

        for (int i = 0; i < lhs.length; i++) {
            if (lhs[i] != rhs[i])
                return false;
        }
        return true;
    }

    /**
     * 比较两个字符串是否相等
     */
    public static boolean stringEquality(String s1, String s2)
    {
        if (s1 == null && s2 == null)
            return true;

        if (s1 == null)
            return false;

        if (s2 == null)
            return false;

        return s1.equals(s2);
    }

    /**
     * 比较两个整数是否相等
     */
    public static boolean integerEquality(Integer i1, Integer i2) {
        if (i1 == null && i2 == null)
            return true;

        if (i1 == null)
            return false;

        if (i2 == null)
            return false;

        return i1.intValue() == i2.intValue();
    }

    /**
     * 比较两个long型是否相等
     */
    public static boolean longEquality(Long l1, Long l2) {
        if (l1 == null && l2 == null)
            return true;

        if (l1 == null)
            return false;

        if (l2 == null)
            return false;

        return l1.longValue() == l2.longValue();
    }

    /**
     * 比较两个浮点数是否相等
     */
    public static boolean floatEquality(Float f1, Float f2) {
        if (f1 == null && f2 == null)
            return true;

        if (f1 == null)
            return false;

        if (f2 == null)
            return false;

        return f1.floatValue() == f2.floatValue();
    }

    /**
     * 比较两个整数列表是否相等
     */
    public static boolean integesrEquality(Integer[] is1, Integer[] is2) {
        if (is1 == null && is2 == null)
            return true;

        if (is1 == null)
            return false;

        if (is2 == null)
            return false;

        return Arrays.equals(is1, is2);
    }

    /**
     * 比较两个位置是否相等
     */
    public static boolean locationEquality(Location loc1, Location loc2) {
        if (loc1 == loc2)
            return true;

        if (loc1 == null)
            return false;

        if (loc2 == null)
            return false;

        return loc1.getLatitude() == loc2.getLatitude() && loc1.getLongitude() == loc2.getLongitude();
    }
}

