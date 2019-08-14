package com.study.commonlib.util.custom;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;

import com.study.commonlib.util.utilcode.Utils;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by chen.tuxi on 2018/12/6.
 */

public class CommonUtils {

    private static final String APN3_KEY = "net.lte.apn3.real_state";
    private static final String APN3_STATS_DISCONNECTED = "disconnected";
    private static final String APN3_STATS_CONNECT = "connect";

    public static String generateUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }


    /**
     * 读取本地文件信息(该方法可以换段落)
     * add by zhaoqingyue
     */
    public static String getFromAssets(Context context, String fileName) {
        if (context == null || TextUtils.isEmpty(fileName))
            return "";
        String text = "";
        try {
            InputStream in = context.getResources().getAssets().open(fileName);
            // 获取文件的字节数
            int lenght = in.available();
            // 创建byte数组
            byte[] buffer = new byte[lenght];
            // 将文件中的数据读到byte数组中
            in.read(buffer);
            in.close();
            text = new String(buffer, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return text;
    }

    /**
     * 判断网络是否可用（包含APN3通道）
     *
     * @return
     */
    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) Utils.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
//        return activeNetworkInfo != null && activeNetworkInfo.isConnected() || getAPN3Stats();
    }

    /**
     * 智能语音调用的二维码全局弹窗
     */
    public static void showIndependentDialog(View contentView) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Utils.getContext());
        AlertDialog dialog = builder.setView(contentView)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).create();
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        dialog.setCanceledOnTouchOutside(false);//点击屏幕不消失
        if (!dialog.isShowing()) {//此时提示框未显示
            dialog.show();
        }
    }

    public static String deleteHtml(String data) {
        try {
            String regEx_script = "<[^>]+>"; // 定义script的正则表达式
            Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            Matcher m_script = p_script.matcher(data);
            String txt = m_script.replaceAll(""); // 过滤script标签
            String content = txt.replaceAll("&nbsp;", "");
            return content;
        } catch (Exception e) {
            return "";
        }
    }

    /*
     * tangxuankai add
     * 获取对端流水号，时间撮+3位随机数
     */
    public static String getOptSerialId(){
        return getsystemTime() + getRandom(100, 999);
    }

    private static  String getsystemTime() {
        long time = System.currentTimeMillis() / 1000;//获取系统时间的10位的时间戳
        String str = String.valueOf(time);
        return str;
    }

    private static  int getRandom(int startNum, int endNum) {
        if (endNum > startNum) {
            Random random = new Random();
            return random.nextInt(endNum - startNum) + startNum;
        }
        return 0;
    }

    private static boolean getAPN3Stats(){
        boolean connect =false;
        String connectResult = CommonUtils.getSystemPropString(APN3_KEY,APN3_STATS_DISCONNECTED);
        if(!TextUtils.isEmpty(connectResult)){
            if(APN3_STATS_CONNECT.equals(connectResult)){
                connect = true;
            }else{
                connect = false;
            }
        }
        return connect;
    }

    public static String getSystemPropString(final String key, final String defaultValue) {
        try {
            Method getStringPropMethod = Class.forName("android.os.SystemProperties")
                    .getMethod("get", String.class, String.class);
            return (String) getStringPropMethod.invoke(null, key, defaultValue);
        } catch (Exception e) {
            return defaultValue;
        }
    }
}
