package com.study.androidnote.welcome.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.study.androidnote.welcome.R;
import com.study.androidnote.welcome.R2;
import com.study.biz.manager.JumpManager;
import com.study.biz.manager.SpManager;
import com.study.commonlib.base.activity.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 启动页——图片
 */
public class StartActivity extends BaseActivity {

    private static final int COUNT_DOWN = 3;

    @BindView( R2.id.tv_skip)
    TextView mSkip;

    private int mCountdown;

    @Override
    protected int getLayoutId() {
        return R.layout.welcome_activity_start;
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        mCountdown = COUNT_DOWN;
        updateSkipText(true);
    }

    @OnClick({R2.id.tv_skip })
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tv_skip) {
            onJump();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    protected void onJump() {
        if (SpManager.isFirstOpen()) {
            // 第一次打开
            SpManager.setFirstOpen(false);
            if (SpManager.isGuideVideoOpen()) {
                goToActivity(GuideVideoActivity.class);
            } else {
                goToActivity(GuideActivity.class);
            }
        } else {
            // 非第一次打开
            if (SpManager.isGuideVideoOpen()) {
                goToActivity(GuideVideoActivity.class);
            } else if (SpManager.isGuideOpen()) {
                goToActivity(GuideActivity.class);
            } else {
                JumpManager.endOfWelcome();
            }
        }
        finish();
    }

    private void updateSkipText(boolean firsTime) {
        if (!firsTime) {
            mCountdown--;
        }
        String skipHint = getResources().getString(R.string.welcome_skip);
        String skip = String.format(skipHint, mCountdown);
        mSkip.setText(skip);
        mHandler.sendEmptyMessageDelayed(0, 1000);
    }

    @SuppressLint( "HandlerLeak" )
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0: {
                    if (mCountdown > 0) {
                        updateSkipText(false);
                    } else {
                        onJump();
                    }
                    break;
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
