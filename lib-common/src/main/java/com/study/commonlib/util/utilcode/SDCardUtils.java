package com.study.commonlib.util.utilcode;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/8/11
 *     desc  : SD卡相关工具类
 * </pre>
 */
public class SDCardUtils {

    private SDCardUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 判断SD卡是否可用
     *
     * @return true : 可用<br>false : 不可用
     */
    public static boolean isSDCardEnable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * 获取SD卡路径
     * <p>先用shell，shell失败再普通方法获取，一般是/storage/emulated/0/</p>
     *
     * @return SD卡路径
     */
    public static String getSDCardPath() {
        if (!isSDCardEnable()) return "sdcard unable!";
        String cmd = "cat /proc/mounts";
        Runtime run = Runtime.getRuntime();
        BufferedReader bufferedReader = null;
        try {
            Process p = run.exec(cmd);
            bufferedReader = new BufferedReader(new InputStreamReader(new BufferedInputStream(p.getInputStream())));
            String lineStr;
            while ((lineStr = bufferedReader.readLine()) != null) {
                if (lineStr.contains("sdcard") && lineStr.contains(".android_secure")) {
                    String[] strArray = lineStr.split(" ");
                    if (strArray.length >= 5) {
                        return strArray[1].replace("/.android_secure", "") + File.separator;
                    }
                }
                if (p.waitFor() != 0 && p.exitValue() == 1) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseUtils.closeIO(bufferedReader);
        }
        return Environment.getExternalStorageDirectory().getPath() + File.separator;
    }

    /**
     * 获取SD卡data路径
     *
     * @return SD卡data路径
     */
    public static String getDataPath() {
        if (!isSDCardEnable()) return "sdcard unable!";
        return Environment.getExternalStorageDirectory().getPath() + File.separator + "data" + File.separator;
    }

    /**
     * 获取SD卡的总空间
     */
    @SuppressWarnings("deprecation")
    public static long getSDCardTotalSize(Context context) {
        //取得SD卡文件路径
        File path = Environment.getExternalStorageDirectory();
        StatFs statFs = new StatFs(path.getPath());
        //获取单个数据块的大小(Byte)
        long blockSize = statFs.getBlockSize();
        //获取所有数据块数
        long totalBlocks = statFs.getBlockCount();
        //return totalBlocks * blockSize;                   //单位: Byte
        return (totalBlocks * blockSize) / 1024;        //单位: KB
        //return (totalBlocks * blockSize) / 1024 / 1024; //单位: MB
    }

    /**
     * 获取SD卡的可用空间
     */
    @SuppressWarnings("deprecation")
    public static long getSDCardAvailSize() {
        //取得SD卡文件路径
        File path = Environment.getExternalStorageDirectory();
        StatFs statFs = new StatFs(path.getPath());
        //获取单个数据块的大小(Byte)
        long blockSize = statFs.getBlockSize();
        //空闲的数据块的数量
        long availableBlocks = statFs.getAvailableBlocks();
        //return availableBlocks * blockSize;                   //单位: Byte
        return (availableBlocks * blockSize) / 1024;        //单位: KB
        //return (availableBlocks * blockSize) / 1024 / 1024; //单位: MB
    }

    public static boolean isFreeSpaceEnough(int packSize) {
        long availSize = getSDCardAvailSize();
        if (packSize < availSize) {
            return true;
        }
        return false;
    }

    /**
     * 获取SD卡剩余空间
     *
     * @return SD卡剩余空间
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static String getFreeSpace() {
        if (!isSDCardEnable()) return "sdcard unable!";
        StatFs stat = new StatFs(getSDCardPath());
        long blockSize, availableBlocks;
        availableBlocks = stat.getAvailableBlocksLong();
        blockSize = stat.getBlockSizeLong();
        return ConvertUtils.byte2FitMemorySize(availableBlocks * blockSize);
    }

    /**
     * 获取SD卡信息
     *
     * @return SDCardInfo
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static String getSDCardInfo() {
        SDCardInfo sd = new SDCardInfo();
        if (!isSDCardEnable()) return "sdcard unable!";
        sd.isExist = true;
        StatFs sf = new StatFs(Environment.getExternalStorageDirectory().getPath());
        sd.totalBlocks = sf.getBlockCountLong();
        sd.blockByteSize = sf.getBlockSizeLong();
        sd.availableBlocks = sf.getAvailableBlocksLong();
        sd.availableBytes = sf.getAvailableBytes();
        sd.freeBlocks = sf.getFreeBlocksLong();
        sd.freeBytes = sf.getFreeBytes();
        sd.totalBytes = sf.getTotalBytes();
        return sd.toString();
    }

    public static class SDCardInfo {
        boolean isExist;
        long    totalBlocks;
        long    freeBlocks;
        long    availableBlocks;
        long    blockByteSize;
        long    totalBytes;
        long    freeBytes;
        long    availableBytes;

        @Override
        public String toString() {
            return "isExist=" + isExist +
                    "\ntotalBlocks=" + totalBlocks +
                    "\nfreeBlocks=" + freeBlocks +
                    "\navailableBlocks=" + availableBlocks +
                    "\nblockByteSize=" + blockByteSize +
                    "\ntotalBytes=" + totalBytes +
                    "\nfreeBytes=" + freeBytes +
                    "\navailableBytes=" + availableBytes;
        }
    }
}