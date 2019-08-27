package com.study.androidnote.account.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.study.androidnote.account.R;
import com.study.androidnote.account.R2;
import com.study.androidnote.account.model.AccountAPI;
import com.study.androidnote.account.model.LoginEvent;
import com.study.androidnote.account.util.MsgId;
import com.study.androidnote.account.util.UserUtil;
import com.study.biz.constant.ArouterPath;
import com.study.biz.db.bean.UserInfo;
import com.study.biz.db.manager.UserInfoManager;
import com.study.biz.manager.JumpManager;
import com.study.commonlib.base.activity.BaseActivity;
import com.study.commonlib.ui.dialog.LoadingDialog;
import com.study.commonlib.util.utilcode.NetworkUtils;
import com.study.commonlib.util.utilcode.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 登录
 */
@Route(path = ArouterPath.PATH_ACCOUNT_LOGIN)
public class LoginActivity extends BaseActivity {

    @BindView( R2.id.layout_login_mobile)
    LinearLayout mMobileLayout;

    @BindView( R2.id.layout_login_account)
    LinearLayout mAccountLayout;

    @BindView(R2.id.login_edit_mobile)
    EditText mMobileEdit;

    @BindView(R2.id.login_edit_vericode)
    EditText mVericodeEdit;

    @BindView(R2.id.login_text_vericode)
    TextView mVericodeText;

    @BindView(R2.id.login_btn_mobile)
    Button mMobileBtn;

    @BindView(R2.id.login_edit_account)
    EditText mAccountEdit;

    @BindView(R2.id.login_edit_pwd)
    EditText mPwdEdit;

    @BindView(R2.id.login_btn_account)
    Button mAccountBtn;

    @BindView(R2.id.iv_pwd_visible)
    ImageView mIvPwdVisible;

    private boolean mPwdVisible = false;

    @Override
    protected int getLayoutId() {
        return R.layout.account_activity_login;
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        setTextChangedListener();
    }

    @Override
    protected boolean receiveEventBus() {
        return true;
    }

    @OnClick({R2.id.login_text_vericode, R2.id.login_btn_mobile, R2.id.login_text_account, R2.id.login_btn_account, R2.id.login_text_mobile, R2.id.login_lost_pwd, R2.id.login_text_register,
            R2.id.iv_pwd_visible, R2.id.rl_weixin, R2.id.rl_qq, R2.id.rl_weibo})
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.login_text_vericode) {
            // 获取验证码
            getVerCode();
        } else if (id == R.id.login_btn_mobile) {
            // 手机号登录
            mobileLogin();
        } else if (id == R.id.login_text_account) {
            // 切换到账号登录
            mMobileLayout.setVisibility(View.GONE);
            mAccountLayout.setVisibility(View.VISIBLE);
            String account = mAccountEdit.getText().toString();
            mAccountEdit.setSelection(account.length());
            mAccountEdit.requestFocus();
        } else if (id == R.id.login_btn_account) {
            // 账号登录
            accountLogin();
        } else if (id == R.id.login_text_mobile) {
            // 切换到手机登录
            String mobile = mMobileEdit.getText().toString();
            String verCode = mVericodeEdit.getText().toString();
            if (!TextUtils.isEmpty(mobile) && !TextUtils.isEmpty(verCode)) {
                mMobileBtn.setEnabled(true);
            } else {
                mMobileBtn.setEnabled(false);
            }
            mMobileLayout.setVisibility(View.VISIBLE);
            mAccountLayout.setVisibility(View.GONE);
        } else if (id == R.id.login_lost_pwd) {
            // 忘记密码
            goToActivity(LostPwdActivity.class);
        } else if (id == R.id.login_text_register) {
            // 注册
            goToActivity(RegisterActivity.class);
        } else if (id == R.id.iv_pwd_visible) {
            // 密码可见 & 隐藏
            mPwdVisible = !mPwdVisible;
            if (mPwdVisible) {
                // 设置EditText的密码为可见的
                mPwdEdit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                mPwdEdit.setSelection(mPwdEdit.getText().toString().length());
                mIvPwdVisible.setImageResource(R.mipmap.account_pwd_visible);
            } else {
                // 设置密码为隐藏的
                mPwdEdit.setTransformationMethod(PasswordTransformationMethod.getInstance());
                mPwdEdit.setSelection(mPwdEdit.getText().toString().length());
                mIvPwdVisible.setImageResource(R.mipmap.account_pwd_invisible);
            }
        } else if (id == R.id.rl_weixin) {
            // 微信登录
            ToastUtils.showShortToast("该功能暂未实现");
        } else if (id == R.id.rl_qq) {
            // QQ登录
            ToastUtils.showShortToast("该功能暂未实现");
        } else if (id == R.id.rl_weibo) {
            // 微博登录
            ToastUtils.showShortToast("该功能暂未实现");
        }
    }

    /**
     * 获取验证码
     */
    private void getVerCode() {
        String mobile = mMobileEdit.getText().toString();
        if (!AccountAPI.getInstance().isPhoneNum(mobile)) {
            ToastUtils.showShortToast( R.string.account_login_mobile_err);
        } else {
            if (!NetworkUtils.isConnected()) {
                ToastUtils.showShortToast( R.string.common_network_abnormal);
            } else {
                mCodeTime = GET_CODE_TIMEOUT;
                mVericodeText.setEnabled(false);
                mCodeTimePhone = mobile;
                startCodeTask();
                // 获取验证码
                AccountAPI.getInstance().getLoginVerifyCode(mobile);
            }
        }
    }

    /**
     * 手机号登录
     */
    private void mobileLogin() {
        final String mobile = mMobileEdit.getText().toString();
        final String verCode = mVericodeEdit.getText().toString();
        if (!TextUtils.isEmpty(mobile) && !TextUtils.isEmpty(verCode)) {
            if (!AccountAPI.getInstance().isPhoneNum(mobile)) {
                ToastUtils.showShortToast(R.string.account_login_mobile_err);
            } else {
                if (!NetworkUtils.isConnected()) {
                    ToastUtils.showShortToast(R.string.common_network_abnormal);
                } else {
                    LoadingDialog.showProgress(this, getString(R.string.tip_login));
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            // 手机号登录
                            AccountAPI.getInstance().mobileLogin(mobile, verCode);
                        }
                    }, 1500);
                }
            }
        }
    }

    /**
     * 账号登录
     */
    private void accountLogin() {
        final String account = mAccountEdit.getText().toString();
        final String pwd = mPwdEdit.getText().toString();
        UserUtil.InputError errorCode = UserUtil.checkInputIsValid(account, pwd);
        switch (errorCode) {
            case eERROR_ACCOUNT_EMPTY: {
                ToastUtils.showShortToast(R.string.account_account_empty);
                break;
            }
            case eERROR_PASSWORD_EMPTY: {
                ToastUtils.showShortToast(R.string.account_password_empty);
                break;
            }
            case eERROR_ACCOUNT_INPUT: {
                ToastUtils.showShortToast(R.string.account_account_error);
                break;
            }
            case eERROR_PASSWORD_INPUT: {
                ToastUtils.showShortToast(R.string.account_password_error);
                break;
            }
            case eERROR_EMAIL_INPUT: {
                ToastUtils.showShortToast(R.string.account_email_error);
                break;
            }
            case eERROR_NONE: {
                if (!NetworkUtils.isConnected()) {
                    ToastUtils.showShortToast(R.string.common_network_abnormal);
                } else {
                    LoadingDialog.showProgress(this, getString(R.string.tip_login));
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            // 账号登录
                            AccountAPI.getInstance().login(account, pwd);
                        }
                    }, 1500);
                }
                break;
            }
            default:
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(LoginEvent event) {
        switch (event.msgId) {
            case MsgId.MSGID_GET_LOGIN_VERICODE_SUCCESS: {
                ToastUtils.showShortToast(R.string.account_get_vericode_success);
                break;
            }
            case MsgId.MSGID_GET_LOGIN_VERICODE_FAILED: {
                resetGetCodeTimer();
                ToastUtils.showShortToast(R.string.account_get_vericode_failed);
                break;
            }
            case MsgId.MSGID_LOGIN_MOBILE_LOGIN_SUCCESS:
            case MsgId.MSGID_LOGIN_ACCOUNT_LOGIN_SUCCESS: {
                if (LoadingDialog.isShowProgress()) {
                    LoadingDialog.cancelProgress();
                }
                cancelCodeTask();

                ToastUtils.showShortToast(R.string.account_login_success);
                JumpManager.loginSuccess();
                finish();
                break;
            }
            case MsgId.MSGID_LOGIN_MOBILE_LOGIN_FAILED:
            case MsgId.MSGID_LOGIN_ACCOUNT_LOGIN_FAILED: {
                if (LoadingDialog.isShowProgress()) {
                    LoadingDialog.cancelProgress();
                }
                ToastUtils.showShortToast(R.string.account_login_failed);
                break;
            }
        }
    }

    private static final int GET_CODE_TIMEOUT = 60; // 验证码等待超时
    private int mCodeTime = GET_CODE_TIMEOUT;       // 验证码即时时间
    private String mCodeTimePhone = "";            // 发送验证码对应的电话号码
    private Timer mTimer = new Timer();            // 获取验证码定时器
    private TimerTask mCodeTask = null;            // 获取验证码任务

    private void startCodeTask() {
        cancelCodeTask();
        mCodeTask = new TimerTask() {

            @Override
            public void run() {
                if (null != mCodeHandler) {
                    mCodeHandler.sendEmptyMessage(0);
                }
            }
        };
        mTimer.schedule(mCodeTask, 0, 1000);
    }

    private void cancelCodeTask() {
        if (mCodeTask != null) {
            mCodeTask.cancel();
            mCodeTask = null;
        }
    }

    private void resetGetCodeTimer() {
        cancelCodeTask();
        mCodeTime = GET_CODE_TIMEOUT;
        if (mVericodeText != null) {
            mVericodeText.setEnabled(true);
            @SuppressWarnings("deprecation")
            int color = getResources().getColor(R.color.colorVerCodeNormal);
            mVericodeText.setTextColor(color);

            if (mCodeTimePhone.equals(mMobileEdit.getText().toString())) {
                mVericodeText.setText(getResources().getString(R.string.account_resend));
            } else {
                mVericodeText.setText(getResources().getString(R.string.account_get_verCode));
            }
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler mCodeHandler = new Handler() {

        @SuppressLint("DefaultLocale")
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0: {
                    // 验证码倒计时
                    if (mCodeTime <= 0) {
                        resetGetCodeTimer();
                    } else {
                        String verCodeHint = getResources().getString(R.string.account_send_verCode);
                        String verCode = String.format(verCodeHint, mCodeTime--);
                        @SuppressWarnings("deprecation")
                        int color = getResources().getColor(R.color.colorVerCode);
                        mVericodeText.setTextColor(color);
                        mVericodeText.setText(verCode);
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

    private void setTextChangedListener() {
        mMobileEdit.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String mobile = mMobileEdit.getText().toString();
                String verCode = mVericodeEdit.getText().toString();
                if (!TextUtils.isEmpty(mobile) && mobile.length() == 11) {
                    if (mCodeTime > 0 && mCodeTime < 60 || mCodeTime <= 0) {
                        mVericodeText.setEnabled(false);
                    } else
                        mVericodeText.setEnabled(true);
                } else {
                    mVericodeText.setEnabled(false);
                }

                if (!TextUtils.isEmpty(mobile) && !TextUtils.isEmpty(verCode)) {
                    mMobileBtn.setEnabled(true);
                } else {
                    mMobileBtn.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mVericodeEdit.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String mobile = mMobileEdit.getText().toString();
                String verCode = mVericodeEdit.getText().toString();
                if (!TextUtils.isEmpty(mobile) && !TextUtils.isEmpty(verCode)) {
                    mMobileBtn.setEnabled(true);
                } else {
                    mMobileBtn.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mAccountEdit.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String account = mAccountEdit.getText().toString();
                String pwd = mPwdEdit.getText().toString();
                if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(pwd)) {
                    mAccountBtn.setEnabled(true);
                } else {
                    mAccountBtn.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mPwdEdit.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String account = mAccountEdit.getText().toString();
                String pwd = mPwdEdit.getText().toString();
                if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(pwd)) {
                    mAccountBtn.setEnabled(true);
                } else {
                    mAccountBtn.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
