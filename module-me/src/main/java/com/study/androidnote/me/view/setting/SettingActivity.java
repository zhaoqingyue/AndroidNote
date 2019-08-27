package com.study.androidnote.me.view.setting;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.study.androidnote.me.R;
import com.study.androidnote.me.R2;
import com.study.androidnote.me.view.setting.about.AboutActivity;
import com.study.androidnote.me.view.setting.safe.SafeActivity;
import com.study.biz.constant.ArouterPath;
import com.study.biz.db.manager.UserInfoManager;
import com.study.biz.manager.SpManager;
import com.study.commonlib.base.activity.BaseTopBarActivity;
import com.study.commonlib.ui.dialog.LoadingDialog;
import com.study.commonlib.ui.view.MultiCard;
import com.study.commonlib.util.utilcode.CleanUtils;
import com.study.commonlib.util.utilcode.FileUtils;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * 设置
 */
@Route(path = ArouterPath.PATH_ME_SETTING)
public class SettingActivity extends BaseTopBarActivity {

    @BindView(R2.id.switch_open_guide)
    Switch mOpenGuide;

    @BindView(R2.id.switch_night_mode)
    Switch mNightMode;

    @BindView(R2.id.mc_clear_cache)
    MultiCard mClearCache;

    @Override
    protected int getLayoutId() {
        return R.layout.me_activity_setting;
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        mOpenGuide.setChecked(SpManager.isFirstOpen());
        mNightMode.setChecked(SpManager.isNightMode());
        mClearCache.setContent(getTotalCacheSize());
    }

    @OnClick({R2.id.ll_leftLayout})
    public void onBackPressed(View view) {
        int id = view.getId();
        if (id == R.id.ll_leftLayout) {
            finish();
        }
    }

    @OnClick({R2.id.mc_account, R2.id.mc_clear_cache, R2.id.mc_help, R2.id.mc_feedback, R2.id.mc_about, R2.id.btn_logout})
    public void onClick(View view) {
        int id = view.getId();
       if (id == R.id.mc_account) {
           goToActivity(SafeActivity.class);
        } else if (id == R.id.mc_clear_cache) {
           // 清除缓存
           CleanUtils.cleanInternalCache();
           CleanUtils.cleanExternalCache();
           CleanUtils.cleanInternalFiles();
           mClearCache.setContent(getTotalCacheSize());
        } else if (id == R.id.mc_help) {

        } else if (id == R.id.mc_feedback) {

        } else if (id == R.id.mc_about) {
            goToActivity(AboutActivity.class);
        } else if (id == R.id.btn_logout) {
//           SpManager.setLoginStatus(false);
//           UserInfoManager.exit();
        }
    }

    @OnCheckedChanged({R2.id.switch_open_guide, R2.id.switch_night_mode})
    public void onCheckChanged(CompoundButton view, boolean isChecked) {
        int id = view.getId();
        if (id == R.id.switch_open_guide) {
            SpManager.setFirstOpen(isChecked);
        } else if (id == R.id.switch_night_mode) {
            SpManager.setNightModen(isChecked);
        }
    }

    private String getTotalCacheSize() {
        long externalCacheSize = 0;
        long cacheSize = 0;
        long filesSize = 0;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            try {
                externalCacheSize = FileUtils.getFileSizes(getExternalCacheDir());
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
        }
        try {
            cacheSize = FileUtils.getFileSizes(getCacheDir());
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

        try {
            filesSize = FileUtils.getFileSizes(getFilesDir());
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return FileUtils.formatFileSize(externalCacheSize + cacheSize + filesSize);
    }
}
