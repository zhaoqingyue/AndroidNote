package com.study.androidnote.lock.view;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.study.androidnote.lock.R;
import com.study.androidnote.lock.R2;
import com.study.biz.manager.SpManager;
import com.study.commonlib.base.activity.BaseTopBarActivity;
import com.study.commonlib.ui.view.MultiCard;
import com.study.commonlib.util.utilcode.CleanUtils;
import com.study.commonlib.util.utilcode.ToastUtils;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * 手势设置
 */
public class GestureSettingActivity extends BaseTopBarActivity {

    @BindView(R2.id.switch_show_gesture_locus)
    Switch mShowGestureLocus;

    @Override
    protected int getLayoutId() {
        return R.layout.lock_activity_gesture_setting;
    }

    @Override
    protected void initData(Bundle saveInstanceState) {

    }

    @OnClick({R2.id.ll_leftLayout})
    public void onBackPressed(View view) {
        int id = view.getId();
        if (id == R.id.ll_leftLayout) {
            finish();
        }
    }

    @OnClick({R2.id.mc_modify_gesture_pwd, R2.id.mc_forget_gesture_pwd})
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.mc_modify_gesture_pwd) {
            // 修改手势密码
            goToActivity(VerificationActivity.class);
        } else if (id == R.id.mc_forget_gesture_pwd) {
            // 忘记手势密码
            ToastUtils.showShortToast("该功能暂未实现");
        }
    }

    @OnCheckedChanged({R2.id.switch_show_gesture_locus})
    public void onCheckChanged(CompoundButton view, boolean isChecked) {
        int id = view.getId();
        if (id == R.id.switch_show_gesture_locus) {
            if (isChecked) {
                ToastUtils.showShortToast("该功能暂未实现");
            }
        }
    }
}
