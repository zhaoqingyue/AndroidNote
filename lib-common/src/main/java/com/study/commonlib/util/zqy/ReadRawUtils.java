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
 * Created by zhao.qingyue on 2019/9/25.
 * 读取raw资源文件夹下的文件
 */
public class ReadRawUtils {

    /**
     * 读取raw目录下文件
     */
    public static String readRawFile(Context cxt, int fileID) {
        String data = "";
        StringBuffer sBuffer = new StringBuffer();
        try {
            //获取文件中的内容，并转化为流
            InputStream inputStream = cxt.getResources().openRawResource(fileID);
            //将文件的字节转换为字符
            InputStreamReader isReader = new InputStreamReader(inputStream, "UTF-8");
            //使用bufferReader去读取字符
            BufferedReader reader = new BufferedReader(isReader);
            try {
                while((data = reader.readLine()) != null) {
                    sBuffer.append(data);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sBuffer.toString();
    }
}
