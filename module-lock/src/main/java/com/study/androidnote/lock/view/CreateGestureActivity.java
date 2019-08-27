package com.study.androidnote.lock.view;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.study.androidnote.lock.R;
import com.study.androidnote.lock.R2;
import com.study.androidnote.lock.util.LockUtils;
import com.study.androidnote.lock.view.cell.LockCell;
import com.study.androidnote.lock.view.custom.LockIndicator;
import com.study.androidnote.lock.view.custom.LockView;
import com.study.biz.constant.AppConstant;
import com.study.biz.constant.ArouterPath;
import com.study.biz.util.LockCache;
import com.study.commonlib.base.activity.BaseTopBarActivity;
import com.study.commonlib.util.utilcode.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 创建手势密码
 */
@Route(path = ArouterPath.PATH_GESTURE_CREATE)
public class CreateGestureActivity extends BaseTopBarActivity {

    @BindView(R2.id.lockIndicator)
    LockIndicator mLockIndicator;

    @BindView(R2.id.lockView)
    LockView mLockView;

    @BindView(R2.id.tv_message)
    TextView mMessage;

    private List<LockCell> mChosenPattern = null;
    private LockCache mLockCache;
    private static final long DELAY_TIME = 600L;

    @Override
    protected int getLayoutId() {
        return R.layout.lock_activity_create_gesture;
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        initLock();
    }

    private void initLock() {
        mLockCache = LockCache.get(this);
        mLockView.setOnPatternListener(mPatternListener);
    }

    /**
     * 手势监听
     */
    private LockView.OnPatternListener mPatternListener = new LockView.OnPatternListener() {

        @Override
        public void onPatternStart() {
            mLockView.removePostClearPatternRunnable();
            mLockView.setPattern(LockView.DisplayMode.DEFAULT);
        }

        @Override
        public void onPatternComplete(List<LockCell> pattern) {
            if(mChosenPattern == null && pattern.size() >= 4) {
                mChosenPattern = new ArrayList<>(pattern);
                updateStatus(Status.CORRECT, pattern);
            } else if (mChosenPattern == null && pattern.size() < 4) {
                updateStatus(Status.LESS_ERROR, pattern);
            } else if (mChosenPattern != null) {
                if (mChosenPattern.equals(pattern)) {
                    updateStatus(Status.CONFIRM_CORRECT, pattern);
                } else {
                    updateStatus(Status.CONFIRM_ERROR, pattern);
                }
            }
        }
    };

    /**
     * 更新状态
     * @param status
     * @param pattern
     */
    private void updateStatus(Status status, List<LockCell> pattern) {
        mMessage.setTextColor(getResources().getColor(status.colorId));
        mMessage.setText(status.strId);
        switch (status) {
            case DEFAULT:
                mLockView.setPattern(LockView.DisplayMode.DEFAULT);
                break;
            case CORRECT:
                updateLockPatternIndicator();
                mLockView.setPattern(LockView.DisplayMode.DEFAULT);
                break;
            case LESS_ERROR:
                mLockView.setPattern(LockView.DisplayMode.DEFAULT);
                break;
            case CONFIRM_ERROR:
                mLockView.setPattern(LockView.DisplayMode.ERROR);
                mLockView.postClearPatternRunnable(DELAY_TIME);
                break;
            case CONFIRM_CORRECT:
                saveChosenPattern(pattern);
                mLockView.setPattern(LockView.DisplayMode.DEFAULT);
                setLockPatternSuccess();
                break;
        }
    }

    /**
     * 更新 Indicator
     */
    private void updateLockPatternIndicator() {
        if (mChosenPattern == null)
            return;

        mLockIndicator.setIndicator(mChosenPattern);
    }

    @OnClick({R2.id.ll_leftLayout})
    public void onBackPressed(View view) {
        int id = view.getId();
        if (id == R.id.ll_leftLayout) {
            finish();
        }
    }

    /**
     * 重新设置手势
     */
    @OnClick(R2.id.tv_reset)
    public void resetLockPattern() {
        mChosenPattern = null;
        mLockIndicator.setDefaultIndicator();
        updateStatus(Status.DEFAULT, null);
        mLockView.setPattern(LockView.DisplayMode.DEFAULT);
    }

    /**
     * 成功设置了手势密码(跳到首页)
     */
    private void setLockPatternSuccess() {
        ToastUtils.showShortToast("create gesture success");
        finish();
    }

    /**
     * 保存手势密码
     */
    private void saveChosenPattern(List<LockCell> cells) {
        byte[] bytes = LockUtils.patternToHash(cells);
        mLockCache.put(AppConstant.GESTURE_PASSWORD, bytes);
    }

    private enum Status {

        DEFAULT(R.string.lock_create_gesture_default, R.color.lock_msg_default),                        // 默认的状态，刚开始的时候（初始化状态）
        CORRECT(R.string.lock_create_gesture_correct, R.color.lock_msg_default),                    // 第一次记录成功
        LESS_ERROR(R.string.lock_create_gesture_less_error, R.color.lock_msg_error),          // 连接的点数小于4（二次确认的时候就不再提示连接的点数小于4，而是提示确认错误）
        CONFIRM_ERROR(R.string.lock_create_gesture_confirm_error, R.color.lock_msg_error),  // 二次确认错误
        CONFIRM_CORRECT(R.string.lock_create_gesture_confirm_correct, R.color.lock_msg_default);                    // 二次确认正确

        Status(int strId, int colorId) {
            this.strId = strId;
            this.colorId = colorId;
        }

        private int strId;
        private int colorId;
    }
}
