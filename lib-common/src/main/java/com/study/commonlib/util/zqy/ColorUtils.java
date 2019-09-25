package com.study.commonlib.util.zqy;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import java.util.Random;

/**
 * Created by zhao.qingyue on 2019/9/25.
 * 颜色Color工具类
 */
public class ColorUtils {

    /**
     * 获取color.xml的颜色资源
     * @param colorResId
     * @return int型颜色
     */
    public static int getColor(Context context, int colorResId) {
        // 如：int color = getResources().getColor(R.color.red);
        return context.getResources().getColor(colorResId);
    }

    /**
     * Color的16进制颜色值 转 Color的Int整型
     * colorHex - Color的16进制颜色值——#3FE2C5
     * return colorInt - -12590395
     */
    public static int hex2Int(String colorHex){
        int colorInt = 0;
        colorInt = Color.parseColor(colorHex);
        return colorInt;
    }

    /**
     * Color的Int整型转Color的16进制颜色值【方案一】
     * colorInt - -12590395
     * return Color的16进制颜色值——#3FE2C5
     */
    public static String int2Hex(int colorInt){
        String hexCode = "";
        hexCode = String.format("#%06X", Integer.valueOf(16777215 & colorInt));
        return hexCode;
    }

    /**
     * Color的Int整型转Color的16进制颜色值【方案二】
     * colorInt - -12590395
     * return Color的16进制颜色值——#3FE2C5
     */
    public static String int2Hex2(int colorInt){
        String hexCode = "";
        int[] rgb = int2Rgb(colorInt);
        hexCode = rgb2Hex(rgb);
        return hexCode;
    }

    /**
     * Color的Int整型转Color的rgb数组
     * colorInt - -12590395
     * return Color的rgb数组 —— [63,226,197]
     */
    public static int[] int2Rgb(int colorInt) {
        int[] rgb = new int[]{0,0,0};
        int red = Color.red(colorInt);
        int green = Color.green(colorInt);
        int blue = Color.blue(colorInt);
        rgb[0] = red;
        rgb[1] = green;
        rgb[2] = blue;

        return rgb;
    }

    /**
     * Color的rgb数组转Color的Int整型
     * rgb - Color的rgb数组 —— [63,226,197]
     * return colorInt - -12590395
     */
    public static int rgb2Int(int[] rgb){
        int colorInt = 0;
        colorInt = Color.rgb(rgb[0],rgb[1],rgb[2]);
        return colorInt;
    }

    /**
     * rgb数组转Color的16进制颜色值
     * rgb - rgb数组——[63,226,197]
     * return Color的16进制颜色值——#3FE2C5
     */
    public static String rgb2Hex(int[] rgb){
        String hexCode="#";
        for(int i=0;i<rgb.length;i++){
            int rgbItem = rgb[i];
            if(rgbItem < 0){
                rgbItem = 0;
            }else if(rgbItem > 255){
                rgbItem = 255;
            }
            String[] code = {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};
            int lCode = rgbItem / 16;//先获取商，例如，255 / 16 == 15
            int rCode = rgbItem % 16;//再获取余数，例如，255 % 16 == 15
            hexCode += code[lCode] + code[rCode];//FF
        }
        return hexCode;
    }

    /**
     * Color的16进制颜色值 转 rgb数组
     * colorHex - Color的16进制颜色值——#3FE2C5
     * return Color的rgb数组 —— [63,226,197]
     */
    public static int[] hex2Rgb(String colorHex){
        int colorInt = hex2Int(colorHex);
        return int2Rgb(colorInt);
    }

    /**
     * 判断是否为ARGB格式的十六进制颜色，例如：FF990587
     * @param string String
     * @return boolean
     */
    public static boolean judgeColorString(String string) {
        if (string.length() == 8) {
            for (int i = 0; i < string.length(); i++) {
                char cc = string.charAt(i);
                return !(cc != '0' && cc != '1' && cc != '2' && cc != '3' && cc != '4' && cc != '5' && cc != '6' && cc != '7' && cc != '8' && cc != '9' && cc != 'A' && cc != 'B' && cc != 'C' &&
                        cc != 'D' && cc != 'E' && cc != 'F' && cc != 'a' && cc != 'b' && cc != 'c' && cc != 'd' && cc != 'e' && cc != 'f');
            }
        }
        return false;
    }

    /**
     * 获得随机颜色
     */
    public static int getRandomColor() {
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        return color;
    }

    /**
     * 使颜色透明
     * @param color 添加透明度的颜色
     * @param transparency 透明度 0.0 - 1.0
     */
    public static int makeColorTransparent(int color, float transparency) {
        if (transparency >= 0f && transparency <= 1f) {
            int alpha = (int) (transparency * 255);
            return (color & 0x00ffffff) | (alpha << 24);
        }

        return color;
    }

    /**
     * 颜色加深
     * @param argbColor ARGB颜色值
     * @param darkValue 0-255 加深范围
     */
    public static int TranslateDark(String argbColor, int darkValue) {
        int startAlpha = Integer.parseInt(argbColor.substring(0, 2), 16);
        int startRed = Integer.parseInt(argbColor.substring(2, 4), 16);
        int startGreen = Integer.parseInt(argbColor.substring(4, 6), 16);
        int startBlue = Integer.parseInt(argbColor.substring(6), 16);
        startRed -= darkValue;
        startGreen -= darkValue;
        startBlue -= darkValue;
        if (startRed < 0) startRed = 0;
        if (startGreen < 0) startGreen = 0;
        if (startBlue < 0) startBlue = 0;
        return Color.argb(startAlpha, startRed, startGreen, startBlue);
    }

    /**
     * 颜色加深
     * @param colorInt ARGB颜色值
     * @param darkValue 0~255 加深范围
     */
    public static int TranslateDark(int colorInt, int darkValue) {
        String argbColor = intToString(colorInt);
        int startAlpha = Integer.parseInt(argbColor.substring(0, 2), 16);
        int startRed = Integer.parseInt(argbColor.substring(2, 4), 16);
        int startGreen = Integer.parseInt(argbColor.substring(4, 6), 16);
        int startBlue = Integer.parseInt(argbColor.substring(6), 16);
        startRed -= darkValue;
        startGreen -= darkValue;
        startBlue -= darkValue;
        if (startRed < 0) startRed = 0;
        if (startGreen < 0) startGreen = 0;
        if (startBlue < 0) startBlue = 0;
        return Color.argb(startAlpha, startRed, startGreen, startBlue);
    }

    /**
     * 颜色变浅，可调度数：0~255
     */
    public static int TranslateLight(String color, int lightValue) {
        int startAlpha = Integer.parseInt(color.substring(0, 2), 16);
        int startRed = Integer.parseInt(color.substring(2, 4), 16);
        int startGreen = Integer.parseInt(color.substring(4, 6), 16);
        int startBlue = Integer.parseInt(color.substring(6), 16);

        startRed += lightValue;
        startGreen += lightValue;
        startBlue += lightValue;

        if (startRed > 255) startRed = 255;
        if (startGreen > 255) startGreen = 255;
        if (startBlue > 255) startBlue = 255;

        return Color.argb(startAlpha, startRed, startGreen, startBlue);
    }

    /**
     * 颜色变浅，可调度数：0~255
     */
    public static int TranslateLight(int colorInt, int lightValue) {
        String color = intToString(colorInt);
        int startAlpha = Integer.parseInt(color.substring(0, 2), 16);
        int startRed = Integer.parseInt(color.substring(2, 4), 16);
        int startGreen = Integer.parseInt(color.substring(4, 6), 16);
        int startBlue = Integer.parseInt(color.substring(6), 16);

        startRed += lightValue;
        startGreen += lightValue;
        startBlue += lightValue;

        if (startRed > 255) startRed = 255;
        if (startGreen > 255) startGreen = 255;
        if (startBlue > 255) startBlue = 255;

        return Color.argb(startAlpha, startRed, startGreen, startBlue);
    }

    /**
     * 不透明度加强，可调度数：0~255
     */
    public static int DarkAlpha(int colorInt, int addValue) {
        String color = intToString(colorInt);
        int startAlpha = Integer.parseInt(color.substring(0, 2), 16);
        int startRed = Integer.parseInt(color.substring(2, 4), 16);
        int startGreen = Integer.parseInt(color.substring(4, 6), 16);
        int startBlue = Integer.parseInt(color.substring(6), 16);

        startAlpha += addValue;
        if (startAlpha > 255) startAlpha = 255;
        return Color.argb(startAlpha, startRed, startGreen, startBlue);
    }

    /**
     * 不透明度加强，可调度数：0~255
     */
    public static int DarkAlpha(String color, int addValue) {

        int startAlpha = Integer.parseInt(color.substring(0, 2), 16);
        int startRed = Integer.parseInt(color.substring(2, 4), 16);
        int startGreen = Integer.parseInt(color.substring(4, 6), 16);
        int startBlue = Integer.parseInt(color.substring(6), 16);

        startAlpha += addValue;
        if (startAlpha > 255) startAlpha = 255;
        return Color.argb(startAlpha, startRed, startGreen, startBlue);
    }

    /**
     * 透明度加强，可调度数：0~255
     */
    public static int LightAlpha(int colorInt, int darkValue) {
        String argbColor = intToString(colorInt);
        int startAlpha = Integer.parseInt(argbColor.substring(0, 2), 16);
        int startRed = Integer.parseInt(argbColor.substring(2, 4), 16);
        int startGreen = Integer.parseInt(argbColor.substring(4, 6), 16);
        int startBlue = Integer.parseInt(argbColor.substring(6), 16);
        startAlpha -= darkValue;

        if (startAlpha < 0) startAlpha = 0;
        return Color.argb(startAlpha, startRed, startGreen, startBlue);
    }

    /**
     * 透明度加强，可调度数：0~255
     */
    public static int LightAlpha(String argbColor, int darkValue) {

        int startAlpha = Integer.parseInt(argbColor.substring(0, 2), 16);
        int startRed = Integer.parseInt(argbColor.substring(2, 4), 16);
        int startGreen = Integer.parseInt(argbColor.substring(4, 6), 16);
        int startBlue = Integer.parseInt(argbColor.substring(6), 16);
        startAlpha -= darkValue;

        if (startAlpha < 0) startAlpha = 0;
        return Color.argb(startAlpha, startRed, startGreen, startBlue);
    }

    /**
     * 将16进制颜色（String）转化为10进制（Int）
     */
    public static int StringTransInt(String color) {
        int startAlpha = Integer.parseInt(color.substring(0, 2), 16);
        int startRed = Integer.parseInt(color.substring(2, 4), 16);
        int startGreen = Integer.parseInt(color.substring(4, 6), 16);
        int startBlue = Integer.parseInt(color.substring(6), 16);
        return Color.argb(startAlpha, startRed, startGreen, startBlue);
    }

    /**
     * 将10进制颜色（int）值转换成16进制(String)
     */
    public static String intToString(int value) {
        String hexString = Integer.toHexString(value);
        if (hexString.length() == 1) {
            hexString = "0" + hexString;
        }
        return hexString;
    }

    /**
     * 将10进制颜色（Int）转换为Drawable对象
     */
    public static Drawable intToDrawable(int color) {
        return new ColorDrawable(color);
    }

    /**
     * 将16进制颜色（String）转化为Drawable对象
     */
    public static Drawable stringToDrawable(String color) {
        return new ColorDrawable(StringTransInt(color));
    }
}
