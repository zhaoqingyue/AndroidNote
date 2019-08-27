package com.study.androidnote.lock.view;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.study.androidnote.lock.R;
import com.study.androidnote.lock.R2;
import com.study.androidnote.lock.util.LockUtils;
import com.study.androidnote.lock.view.cell.LockCell;
import com.study.androidnote.lock.view.custom.LockView;
import com.study.biz.constant.AppConstant;
import com.study.biz.util.LockCache;
import com.study.commonlib.base.activity.BaseTopBarActivity;
import com.study.commonlib.util.utilcode.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 验证手势密码
 */
public class VerificationActivity extends BaseTopBarActivity {

    @BindView(R2.id.lockView)
    LockView mLockView;

    @BindView(R2.id.tv_message)
    TextView mMessage;

    private LockCache mLockCache;
    private static final long DELAY_TIME = 600l;
    private byte[] mGesturePassword;

    @Override
    protected int getLayoutId() {
        return R.layout.lock_activity_verification;
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        initLock();
    }

    private void initLock() {
        mLockCache = LockCache.get(this);
        // 得到当前用户的手势密码
        mGesturePassword = mLockCache.getAsBinary(AppConstant.GESTURE_PASSWORD);
        mLockView.setOnPatternListener(mPatternListener);
        updateStatus(Status.DEFAULT);
    }

    private LockView.OnPatternListener mPatternListener = new LockView.OnPatternListener() {

        @Override
        public void onPatternStart() {
            mLockView.removePostClearPatternRunnable();
        }

        @Override
        public void onPatternComplete(List<LockCell> pattern) {
            if(pattern != null){
                if(LockUtils.checkPattern(pattern, mGesturePassword)) {
                    updateStatus(Status.CORRECT);
                } else {
                    updateStatus(Status.ERROR);
                }
            }
        }
    };

    @OnClick({R2.id.ll_leftLayout})
    public void onBackPressed(View view) {
        int id = view.getId();
        if (id == R.id.ll_leftLayout) {
            finish();
        }
    }

    /**
     * 验证登录密码
     */
    @OnClick(R2.id.tv_verification_login_pwd)
    public void verificationLoginPwd() {
//        goToActivity(GestureSettingActivity.class);
        ToastUtils.showShortToast("该功能暂未实现");
    }

    /**
     * 更新状态
     */
    private void updateStatus(Status status) {
        mMessage.setText(status.strId);
        mMessage.setTextColor(getResources().getColor(status.colorId));
        switch (status) {
            case DEFAULT:
                mLockView.setPattern(LockView.DisplayMode.DEFAULT);
                break;
            case ERROR:
                mLockView.setPattern(LockView.DisplayMode.ERROR);
                mLockView.postClearPatternRunnable(DELAY_TIME);
                break;
            case CORRECT:
                mLockView.setPattern(LockView.DisplayMode.DEFAULT);
                verificationGestureSuccess();
                break;
        }
    }

    /**
     * 验证原手势密码成功
     */
    private void verificationGestureSuccess() {
        ToastUtils.showShortToast("验证成功");
        goToActivity(CreateGestureActivity.class);
        finish();
    }

    private enum Status {
        DEFAULT(R.string.lock_gesture_input_olo_pwd, R.color.lock_msg_default), // 默认的状态
        ERROR(R.string.lock_gesture_error, R.color.lock_msg_error),   // 密码输入错误
        CORRECT(R.string.lock_gesture_correct, R.color.lock_msg_default);       // 密码输入正确

        Status(int strId, int colorId) {
            this.strId = strId;
            this.colorId = colorId;
        }

        private int strId;
        private int colorId;
    }
}
