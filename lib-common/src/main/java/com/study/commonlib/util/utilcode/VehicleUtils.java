package com.study.commonlib.util.utilcode;

import android.content.Context;
import android.text.TextUtils;
import com.study.commonlib.R;
import java.util.regex.Pattern;

/**
 * 车辆相关的工具类
 */
public class VehicleUtils {

    /**
     * 格式化车牌号
     */
    public static String formatCarPlate(Context context, String carPlate) {
        String str = carPlate;
        if (carPlateIsValid(context, carPlate)) {
            return str;
        }
        return context.getResources().getString(R.string.tmp_plate);
    }

    /**
     * 判断车牌号码是否合法
     */
    public static boolean carPlateIsValid(Context context, String carPlate) {
        if (TextUtils.isEmpty(carPlate)) {
            return false;
        }
        if (carPlate.length() < 7 || carPlate.length() > 8) {
            return false;
        }
        String str1 = carPlate.substring(0, 1);
        String str2 = "";
        if (carPlate.length() == 7) {
            str2 = carPlate.substring(1, 7);
        } else if (carPlate.length() == 8) {
            str2 = carPlate.substring(1, 8);
        }
        String[] array = context.getResources().getStringArray(R.array.provinceCode);
        for (int i = 0; i < array.length; i++) {
            if (array[i].indexOf(str1) != -1) {
                Pattern pattern = Pattern.compile("[A-Z][A-Za-z0-9]{4,5}[A-Za-z0-9警]");
                return pattern.matcher(str2).matches();
            }
        }
        return false;
    }

}
