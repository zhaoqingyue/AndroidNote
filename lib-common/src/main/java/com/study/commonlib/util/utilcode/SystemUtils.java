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

    /**
     * 随机生成一个账号
     */
    public static String generateAccount() {
        String machineId = "note_"; //最大支持1-9个集群机器部署
        int hashCode = UUID.randomUUID().toString().hashCode();
        if (hashCode < 0) {
            //有可能是负数
            hashCode = -hashCode;
        }
        LogUtils.e("ZQY", "generateAccount: " + hashCode);
        // 保持11位
        String hashCodeStr = String.format("%011d", hashCode);
        return machineId + hashCodeStr;
    }
}
