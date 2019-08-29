package com.study.androidnote.lock.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.study.androidnote.lock.R;
import com.study.androidnote.lock.R2;
import com.study.androidnote.lock.util.LockUtils;
import com.study.androidnote.lock.view.cell.LockCell;
import com.study.androidnote.lock.view.custom.LockView;
import com.study.biz.bean.event.LockEvent;
import com.study.biz.constant.AppConstant;
import com.study.biz.constant.ArouterPath;
import com.study.biz.db.bean.UserInfo;
import com.study.biz.db.manager.UserInfoManager;
import com.study.biz.util.LockCache;
import com.study.commonlib.base.activity.BaseTopBarActivity;
import com.study.commonlib.util.utilcode.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 手势登录
 */
@Route(path = ArouterPath.PATH_GESTURE_LOGIN)
public class GestureLoginActivity extends BaseTopBarActivity {

    @BindView( R2.id.iv_avatar)
    ImageView mAvatar;

    @BindView(R2.id.lockView)
    LockView mLockView;

    @BindView(R2.id.tv_message)
    TextView mMessage;

    private LockCache mLockCache;
    private static final long DELAY_TIME = 600l;
    private byte[] mGesturePassword;

    @Override
    protected int getLayoutId() {
        return R.layout.lock_activity_gesture_login;
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        initLock();
        updateAvatar();
    }

    private void initLock() {
        mLockCache = LockCache.get(this);
        // 得到当前用户的手势密码
        mGesturePassword = mLockCache.getAsBinary(AppConstant.GESTURE_PASSWORD);
        mLockView.setOnPatternListener(mPatternListener);
        updateStatus(Status.DEFAULT);
    }

    private void updateAvatar() {
        UserInfo userInfo = UserInfoManager.getInstance();
        if (userInfo == null)
            return;

        String avatar = userInfo.getAvatar();
        Glide.with(this)
                .load(avatar)
                .error(R.mipmap.biz_avatar)
                .placeholder(R.mipmap.biz_avatar)
                .into(mAvatar);
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

    /**
     * 手势管理
     */
    @OnClick(R2.id.tv_manager_gesture)
    public void managerGesture() {
        goToActivity(ManagerGestureActivity.class);
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
                loginGestureSuccess();
                break;
        }
    }

    /**
     * 手势登录成功
     */
    private void loginGestureSuccess() {
        ToastUtils.showShortToast("检验成功");
        LockEvent event = new LockEvent();
        event.msgId = 0;
        event.errCode = 0;
        EventBus.getDefault().post(event);
        finish();
    }

    @OnClick({R2.id.ll_leftLayout})
    public void onBackPressed(View view) {
        int id = view.getId();
        if (id == R.id.ll_leftLayout) {
            LockEvent event = new LockEvent();
            event.msgId = 1;
            event.errCode = 0;
            EventBus.getDefault().post(event);
            finish();
        }
    }

    private enum Status {
        DEFAULT(R.string.lock_gesture_default, R.color.lock_msg_default), // 默认的状态
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
