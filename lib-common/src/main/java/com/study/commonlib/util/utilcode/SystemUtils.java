package com.study.commonlib.util.utilcode;

import java.util.UUID;

/**
 * <pre>
 *    author : chen.shanhong
 *    e-mail : chen.shanhong@byd.com
 *    time   : 2019/07/05
 *    desc   :
 * </pre>
 */
public class SystemUtils {

    /**
     * 生成UUID
     */
    public static String generateUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}
