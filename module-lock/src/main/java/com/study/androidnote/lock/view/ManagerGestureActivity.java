package com.study.androidnote.lock.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.study.androidnote.lock.R;
import com.study.androidnote.lock.R2;
import com.study.biz.manager.SpManager;
import com.study.commonlib.base.activity.BaseTopBarActivity;
import com.study.commonlib.util.utilcode.ToastUtils;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * 管理手势密码
 */
public class ManagerGestureActivity extends BaseTopBarActivity {

    @BindView(R2.id.switch_fingerprint)
    Switch mOpenFingerprint;

    @BindView(R2.id.switch_gesture_pwd)
    Switch mOpenGesturePwd;

    @Override
    protected int getLayoutId() {
        return R.layout.lock_activity_manager_gesture;
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        mOpenGesturePwd.setChecked(SpManager.isOpenGesturePwd());
    }

    @OnClick({R2.id.ll_leftLayout})
    public void onBackPressed(View view) {
        int id = view.getId();
        if (id == R.id.ll_leftLayout) {
            finish();
        }
    }

    @OnClick({R2.id.mc_gesture_setting})
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.mc_gesture_setting) {
            // 手势设置
            goToActivity(GestureSettingActivity.class);
        }
    }

    @OnCheckedChanged({R2.id.switch_fingerprint, R2.id.switch_gesture_pwd})
    public void onCheckChanged(CompoundButton view, boolean isChecked) {
        int id = view.getId();
        if (id == R.id.switch_fingerprint) {
            if (isChecked) {
                ToastUtils.showShortToast("该功能暂未实现");
            }
        } else if (id == R.id.switch_gesture_pwd) {
            SpManager.setOpenGesturePwd(isChecked);
        }
    }
}
