package com.study.commonlib.util.utilcode;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by tang.xuankai on 2018/12/28.
 */

public class ByteToStringFormatUtils {

    public static double pers  = 1048576; //1024*1024
    public static double cubic = 1073741824; //1024*1024*1024

    //long==> 616.19KB,3.73M
    public static String sizeFormatNum2String(long size) {
        String s = "";
        if(size>1024*1024)
            s=String.format("%.2f", (double)size/pers)+"M";
        else
            s=String.format("%.2f", (double)size/(1024))+"KB";
        return s;
    }

    //616.19KB,3.73M ==> long
    public static long sizeFormatString2Num(String str){
        long size = 0;
        if(EmptyUtils.isNotEmpty(str)){
            if(str.endsWith("KB")){
                size = (long)(Double.parseDouble(str.substring(0,str.length()-2))*1024);
            }else if(str.endsWith("MB")){
                size = (long)(Double.parseDouble(str.substring(0,str.length()-2))*pers);
            }else if(str.endsWith("GB")){
                size = (long)(Double.parseDouble(str.substring(0,str.length()-2))*cubic);
            }else if (str.endsWith("B")){
                size = (long)(Double.parseDouble(str.substring(0,str.length()-1)));
            }
        }
        return size;
    }

    public static float byte2Mb(long b){
        double result = 0.0f;
        NumberFormat nf =new DecimalFormat( "0.0 ");
        result = b / pers;
        result = Double.parseDouble(nf.format(result));
        return (float)result;
    }

    public static int sizeFormatString2MB(String str) {
        double size = 0;
        if(EmptyUtils.isNotEmpty(str)){
            if(str.endsWith("KB")){
                size = (Double.parseDouble(str.substring(0,str.length()-2))/1024);
            } else if(str.endsWith("MB")){
                size = (Double.parseDouble(str.substring(0,str.length()-2)));
            } else if(str.endsWith("GB")){
                size = (Double.parseDouble(str.substring(0,str.length()-2)) * 1024);
            } else if(str.endsWith("TB")) {
                size = (Double.parseDouble(str.substring(0, str.length() - 2)) * pers);
            } else if(str.endsWith("B")) {
                size = (Double.parseDouble(str.substring(0, str.length() - 1)) / pers);
            }
        }
        return getInt(size);
    }

    public static int getInt(double number){
        BigDecimal bd=new BigDecimal(number).setScale(0, BigDecimal.ROUND_HALF_UP);
        return Integer.parseInt(bd.toString());
    }

    public static int valueFormatString2Num(String str){
        int size = 0;
        if (EmptyUtils.isNotEmpty(str)){
            if(str.endsWith("MB") || str.endsWith("mb") || str.endsWith("Mb")){
                size = (int)(Double.parseDouble(str.substring(0, str.length() - 2)));
            }
        }
        return size;
    }

    public static String getNumberFromString(String str) {
        if (EmptyUtils.isEmpty(str)){
            return null;
        }
        if (str.endsWith("KB") || str.endsWith("MB") || str.endsWith("GB") || str.endsWith("TB")) {
            str = str.substring(0, str.length() - 2);
        } else if (str.endsWith("B")) {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    public static String getLetterFromString(String str) {
        if (EmptyUtils.isNotEmpty(str)){
            str = str.replaceAll("[^a-z^A-Z]", "");
        }
        return str;
    }

    public static String getNum(String str) {
        if (EmptyUtils.isNotEmpty(str)) {
            str = str.replaceAll("[^0-9]","");
        }
        return str;
    }
}
