package com.study.commonlib.util.zqy;

import android.Manifest;
import android.app.ActivityManager;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.nfc.NfcAdapter;
import android.os.BatteryManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import static android.content.Context.BATTERY_SERVICE;

/**
 * Created by zhao.qingyue on 2019/9/25.
 * 设备相关工具类
 */
public class DeviceUtils {

    /**
     * 检查WiFi模块是否打开
     */
    public static boolean checkWifiEnabled(Context context) {
        if (checkNull(context)) {
            return false;
        }
        WifiManager wifi = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        return wifi.isWifiEnabled();
    }

    /**
     * 检查设备是否连接到互联网
     */
    public static boolean checkInternetConnection(Context context) {
        if (checkNull(context)) {
            return false;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * 检查移动数据连接是否已启用
     */
    public static boolean checkMobileDataConnectionEnabled(Context context) {
        if (checkNull(context)) {
            return false;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected() && (activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE);
    }

    /**
     * 检查蓝牙是否已启用
     */
    public static boolean checkBluetoothEnabled(Context context) {
        if (checkNull(context)) {
            return false;
        }
        if ((ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED)) {
            return false;
        }
        final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        return (bluetoothAdapter != null && bluetoothAdapter.isEnabled());
    }

    /**
     * 检查定位模块是否已打开
     */
    public static boolean checkLocationEnabled(Context context) {
        if (checkNull(context)) {
            return false;
        }

        int locationMode;
        String locationProviders;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);
            } catch (Settings.SettingNotFoundException e) {
                return false;
            }
            return locationMode != Settings.Secure.LOCATION_MODE_OFF;
        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }

    /**
     * 检查设备是否有后置摄像头
     */
    public static boolean checkDeviceHasBackCamera(Context context) {
        if (checkNull(context)) {
            return false;
        }

        PackageManager pm = context.getPackageManager();
        if (pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            return true;
        }
        return false;
    }

    /**
     * 检查设备是否有相机
     */
    public static boolean checkDeviceHasAnyCamera(Context context) {
        if (checkNull(context)) {
            return false;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
        } else {
            return Camera.getNumberOfCameras() > 0;
        }
    }

    /**
     * 检查设备是否具有软件导航栏
     */
    public static boolean checkDeviceHasSoftwareNavBar(Context context) {
        if (checkNull(context)) {
            return false;
        }

        int resId = context.getResources().getIdentifier("config_showNavigationBar", "bool", "android");
        if (resId > 0) {
            return resId > 0 && context.getResources().getBoolean(resId);
        }
        return false;
    }

    /**
     * 检查设备是否支持NFC
     */
    public static boolean checkNfcSupported(Context context) {
        if (checkNull(context)) {
            return false;
        }

        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(context);
        if (nfcAdapter != null) {
            return true;
        }
        return false;
    }

    /**
     * 检查NFC是否已打开
     */
    public static boolean checkNfcEnabled(Context context) {
        if (checkNull(context)) {
            return false;
        }

        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(context);
        boolean enabled = false;
        if (nfcAdapter != null) {
            enabled = nfcAdapter.isEnabled();
        }
        return enabled;
    }

    /**
     * 检查pdf文件是否可以在外部应用程序中显示
     */
    public static boolean checkCanDisplayPdf(Context context) {
        if (context == null) {
            return false;
        }
        PackageManager packageManager = context.getPackageManager();
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("application/pdf");
        return (packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY).size() > 0);
    }

    /**
     * 检查图片是否可以在外部应用程序中显示
     */
    public static boolean checkCanDisplayImage(Context context) {
        if (checkNull(context)) {
            return false;
        }
        PackageManager packageManager = context.getPackageManager();
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("image/*");
        return packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY).size() > 0;
    }

    /**
     * 检查应用程序是否已安装在设备中
     */
    public static boolean checkAppIsInstalled(Context context, String packageName) {
        if (checkNull(context) || checkNull(packageName) || packageName.isEmpty())
            return false;

        PackageManager packageManager = context.getPackageManager();
        try {
            packageManager.getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {

        }
        return false;
    }

    /**
     * 检查设备电池电量是否不足(至少需要API级别21)
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static boolean checkBatteryIsLow(Context context, int lowBatteryPercentageBound) {
        if (checkNull(context))
            return false;

        BatteryManager batteryManager = (BatteryManager) context.getSystemService(BATTERY_SERVICE);
        if (batteryManager == null)
            return false;

        int batLevel = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
        boolean charging = false;
        lowBatteryPercentageBound = (lowBatteryPercentageBound < 0 || lowBatteryPercentageBound > 100) ? 15 : lowBatteryPercentageBound;
        return !charging && (batLevel <= lowBatteryPercentageBound);
    }

    /**
     * 检查设备内存是否不足
     */
    public static boolean checkMemoryIsLow(Context context) {
        if (checkNull(context))
            return false;

        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        if (activityManager == null)
            return false;

        activityManager.getMemoryInfo(mi);
        return mi.lowMemory;
    }

    /**
     * 检查设备是否是平板电脑
     *
     * @return true/false
     */
    public static boolean isTablet(Context context)
    {
        if (checkNull(context)) {
            return false;
        }
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * 检查设备是否是智能手机
     */
    public static boolean isSmartphone(Context context) {
        if (checkNull(context)) {
            return false;
        }
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) < Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    static boolean checkNull(Object o) {
        return (o == null);
    }
}
