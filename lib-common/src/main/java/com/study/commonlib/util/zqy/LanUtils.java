package com.study.commonlib.util.zqy;

import java.util.Locale;

/**
 * Created by zhao.qingyue on 2019/10/9.
 * 语言工具类
 */
public class LanUtils {

    public static boolean isChinese() {
        return Locale.getDefault().getDisplayLanguage().contains("中文");
    }
}
