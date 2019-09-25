package com.study.androidnote.me.view.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.study.androidnote.me.R;
import com.study.androidnote.me.R2;
import com.study.androidnote.me.view.setting.about.AboutActivity;
import com.study.androidnote.me.view.setting.safe.AvatarTypeActivity;
import com.study.androidnote.me.view.setting.safe.SafeActivity;
import com.study.biz.constant.ArouterPath;
import com.study.biz.manager.SpManager;
import com.study.commonlib.base.activity.BaseTopBarActivity;
import com.study.commonlib.ui.view.MultiCard;
import com.study.commonlib.util.utilcode.CleanUtils;
import com.study.commonlib.util.utilcode.FileUtils;
import com.study.commonlib.util.utilcode.ToastUtils;
import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * 设置
 */
@Route(path = ArouterPath.PATH_ME_SETTING)
public class SettingActivity extends BaseTopBarActivity {

    @BindView(R2.id.switch_open_gesture_pwd)
    Switch mOpenGesturePwd;

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
        mOpenGesturePwd.setChecked(SpManager.isOpenGesturePwd());
        mOpenGuide.setChecked(SpManager.isFirstOpen());
        mNightMode.setChecked(SpManager.isNightMode());
        mClearCache.setContent(FileUtils.getTotalCacheSize(this));
    }

    @OnClick({R2.id.ll_leftLayout})
    public void onBackPressed(View view) {
        int id = view.getId();
        if (id == R.id.ll_leftLayout) {
            finish();
        }
    }

    @OnClick({R2.id.mc_account, R2.id.mc_avatar_type, R2.id.mc_clear_cache, R2.id.mc_help, R2.id.mc_feedback, R2.id.mc_about, R2.id.btn_logout})
    public void onClick(View view) {
        int id = view.getId();
       if (id == R.id.mc_account) {
           // 账号与安全
           goToActivity(SafeActivity.class);
        } else if (id == R.id.mc_avatar_type) {
           // 头像类型
           goToActivity(AvatarTypeActivity.class);
       } else if (id == R.id.mc_clear_cache) {
           // 清除缓存
           CleanUtils.cleanInternalCache();
           CleanUtils.cleanExternalCache();
           CleanUtils.cleanInternalFiles();
           mClearCache.setContent(FileUtils.getTotalCacheSize(this));
        } else if (id == R.id.mc_help) {
           // 帮助
           ToastUtils.showShortToast("该功能暂未实现");
        } else if (id == R.id.mc_feedback) {
           // 意见反馈
           ToastUtils.showShortToast("该功能暂未实现");
        } else if (id == R.id.mc_about) {
           // 关于
            goToActivity(AboutActivity.class);
        } else if (id == R.id.btn_logout) {
           // 退出登录
//           SpManager.setLoginStatus(false);
//           UserInfoManager.exit();
        }
    }

    @OnCheckedChanged({R2.id.switch_open_gesture_pwd, R2.id.switch_open_guide, R2.id.switch_night_mode})
    public void onCheckChanged(CompoundButton view, boolean isChecked) {
        int id = view.getId();
        if (id == R.id.switch_open_gesture_pwd) {
            SpManager.setOpenGesturePwd(isChecked);
        } else if (id == R.id.switch_open_guide) {
            SpManager.setFirstOpen(isChecked);
        } else if (id == R.id.switch_night_mode) {
            SpManager.setNightMode(isChecked);
        }
    }
}
