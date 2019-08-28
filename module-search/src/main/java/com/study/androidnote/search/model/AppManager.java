package com.study.androidnote.search.model;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import com.study.androidnote.search.model.bean.AppBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhao.qingyue on 2019/8/28.
 * 应用管理类（调试）
 */
public class AppManager {

    /**
     * 获取推荐应用
     */
    public static List<AppBean> getRecdAppList(Context context) {
        List<AppBean> appList = new ArrayList<>();
        PackageManager pm = context.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> resolveInfos = pm.queryIntentActivities(intent, 0);
        for (ResolveInfo resolveInfo : resolveInfos) {
            AppBean appBean = new AppBean();
            appBean.setAppIcon(resolveInfo.activityInfo.loadIcon(pm));
            appBean.setAppName(resolveInfo.activityInfo.loadLabel(pm).toString());
            appBean.setPkgName(resolveInfo.activityInfo.packageName);
            appBean.setClassName(resolveInfo.activityInfo.name);
            appList.add(appBean);
            if (appList.size() >= 4) {
                break;
            }
        }
        return appList;
    }
}
