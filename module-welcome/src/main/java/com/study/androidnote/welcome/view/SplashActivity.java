package com.study.androidnote.welcome.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import com.study.androidnote.welcome.R;
import com.study.androidnote.welcome.R2;
import com.study.biz.manager.SettingManager;
import com.study.commonlib.base.activity.BaseActivity;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import java.util.List;

import butterknife.BindView;

/**
 * 闪屏页
 */
public class SplashActivity extends BaseActivity {

    @BindView( R2.id.rl_splash)
    RelativeLayout splash;

    @Override
    protected int getLayoutId() {
        return R.layout.welcome_activity_splash;
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        // 设置日间-夜间模式
        SettingManager.updateDayNightMode();
        startAnimation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestPermissions();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void jumpToNext() {
        if (isPermissionRequestFinish) {
            goToActivity(StartImageActivity.class);
            finish();
        } else {
            mHandler.sendEmptyMessageDelayed(0, 1500);
        }
    }

    /**
     * 设置动画
     */
    private void startAnimation() {
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 1.05f, 1.0f, 1.05f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.8f, 1.0f);
        scaleAnimation.setDuration(500);
        alphaAnimation.setDuration(500);
        AnimationSet animationSet = new AnimationSet(false);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(alphaAnimation);
        animationSet.setFillAfter(true);
        splash.startAnimation(animationSet);

        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                jumpToNext();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void requestPermissions() {
        AndPermission.with(this)
                .requestCode(100)
                .permission(
                        //电话、通讯录、短信
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.WRITE_CONTACTS,
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.READ_SMS,
                        Manifest.permission.GET_ACCOUNTS,
                        Manifest.permission.READ_PHONE_STATE,
                        //相机、麦克风
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.CAMERA,
                        //存储空间
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        //位置(需要定位功能sdk)
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                .callback(listener)
                .start();
    }

    private PermissionListener listener = new PermissionListener() {

        /**
         * 权限申请成功回调
         * @param requestCode 申请时设置的requestCode
         * @param grantPermissions
         */
        @Override
        public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
            isPermissionRequestFinish = true;
            if (requestCode == 100) {
                for (String permission : grantPermissions) {
//                    Log.e("ZQY", "permission: " + permission);
                }
            }
        }

        /**
         * 权限申请失败回调
         * @param requestCode 申请时设置的requestCode
         * @param deniedPermissions
         */
        @Override
        public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
            isPermissionRequestFinish = true;
        }
    };

    private boolean isPermissionRequestFinish = false;

    @SuppressLint( "HandlerLeak" )
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0: {
                    if (isPermissionRequestFinish) {
                        jumpToNext();
                    } else {
                        mHandler.sendEmptyMessageDelayed(0, 1000);
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
