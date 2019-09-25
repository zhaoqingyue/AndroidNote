package com.study.commonlib.util.zqy;

import android.util.Patterns;

/**
 * Created by zhao.qingyue on 2019/9/25.
 * 有效验证工具类
 */
public class ValidationUtils {

    /**
     * 验证电子邮件地址
     */
    public static boolean isEmailAddressValid(String email) {
        if (StringUtils.checkEmptyString(email)) {
            return false;
        }
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    /**
     * 验证网址地址
     */
    public static boolean isWebUrlValid(String webUrl) {
        if (StringUtils.checkEmptyString(webUrl)) {
            return false;
        }
        return Patterns.WEB_URL.matcher(webUrl).matches();
    }

    /**
     * 验证电话号码
     */
    public static boolean isPhoneNumberValid(String phoneNumber) {
        if (StringUtils.checkEmptyString(phoneNumber)) {
            return false;
        }
        return Patterns.PHONE.matcher(phoneNumber).matches();
    }

    /**
     * 验证域名地址
     */
    public static boolean isDomainNameValid(String domainName) {
        if (StringUtils.checkEmptyString(domainName)) {
            return false;
        }
        return Patterns.DOMAIN_NAME.matcher(domainName).matches();
    }

    /**
     * 验证IP地址
     */
    public static boolean isIpAddressValid(String ipAddress) {
        if (StringUtils.checkEmptyString(ipAddress)) {
            return false;
        }
        return Patterns.IP_ADDRESS.matcher(ipAddress).matches();
    }
}
