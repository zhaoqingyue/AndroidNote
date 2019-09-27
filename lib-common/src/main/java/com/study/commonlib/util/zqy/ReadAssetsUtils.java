package com.study.commonlib.util.zqy;

import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by zhao.qingyue on 2019/9/26.
 * 读取assets资源文件夹下的文件
 */
public class ReadAssetsUtils {

    /**
     * 将JSON文件读取成JSONObject
     */
    public static JSONArray getJSONArray(Context context, String fileName){
        try {
            return new JSONArray(getJson(context, fileName));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getJson(Context context, String fileName){
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //获取assets资源管理器
            AssetManager assetManager = context.getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    /**
     * 读取本地assets目录下文件信息(该方法可以换段落)
     */
    public static String getFromAssets(Context context, String fileName) {
        if (context == null || TextUtils.isEmpty(fileName))
            return "";
        String text = "";
        try {
            InputStream in = context.getResources().getAssets().open(fileName);
            // 获取文件的字节数
            int length = in.available();
            // 创建byte数组
            byte[] buffer = new byte[length];
            // 将文件中的数据读到byte数组中
            in.read(buffer);
            in.close();
            text = new String(buffer, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return text;
    }
}
