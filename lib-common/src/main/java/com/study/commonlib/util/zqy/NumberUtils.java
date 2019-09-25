package com.study.commonlib.util.zqy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Created by zhao.qingyue on 2019/9/25.
 * 数字工具类
 */
public class NumberUtils {


    /**
     * 从浮点值中删除不必要的十进制零
     */
    public static String removeUnnecessaryDecimalZeros(float value) {
        DecimalFormat format = new DecimalFormat("#.#");
        return format.format(value);
    }

    /**
     * 四舍五入到所需的小数位数
     */
    public static double roundDouble(double value, int places) {
        if (places < 0) {
            return 0d;
        }

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    /**
     * 四舍五入到所需的小数位数
     */
    public static float roundDouble(float value, int places) {
        if (places < 0) {
            return 0f;
        }

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.floatValue();
    }

    /**
     * 将十进制转换为数字的十六进制格式
     */
    public static String decimalToHex(int number) {
        return Integer.toString(number, 16);
    }
}
