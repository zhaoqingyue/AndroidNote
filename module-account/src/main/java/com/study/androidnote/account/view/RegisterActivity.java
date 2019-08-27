package com.study.androidnote.account.view;

import android.annotation.SuppressLint;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.study.androidnote.account.R;
import com.study.androidnote.account.R2;
import com.study.androidnote.account.model.AccountAPI;
import com.study.androidnote.account.model.LoginEvent;
import com.study.androidnote.account.model.RegisterEvent;
import com.study.androidnote.account.util.MsgId;
import com.study.androidnote.account.util.UserUtil;
import com.study.biz.constant.ArouterPath;
import com.study.biz.db.bean.UserInfo;
import com.study.biz.db.manager.UserInfoManager;
import com.study.biz.manager.JumpManager;
import com.study.commonlib.base.activity.BaseActivity;
import com.study.commonlib.ui.dialog.LoadingDialog;
import com.study.commonlib.util.utilcode.LogUtils;
import com.study.commonlib.util.utilcode.NetworkUtils;
import com.study.commonlib.util.utilcode.SystemUtils;
import com.study.commonlib.util.utilcode.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * 注册
 */
public class RegisterActivity extends BaseActivity {

    @BindView(R2.id.register_edit_mobile)
    EditText mMobileEdit;

    @BindView(R2.id.register_edit_vericode)
    EditText mVericodeEdit;

    @BindView(R2.id.register_text_vericode)
    TextView mVericodeText;

    @BindView(R2.id.register_edit_pwd)
    EditText mPwdEdit;

    @BindView(R2.id.register_edit_confirm_pwd)
    EditText mConfirmPwdEdit;

    @BindView(R2.id.cb_agree)
    CheckBox mCbAgree;

    @BindView(R2.id.iv_pwd_visible)
    ImageView mIvPwdVisible;

    @BindView(R2.id.iv_confirm_pwd_visible)
    ImageView mIvConfirmPwdVisible;


    @BindView(R2.id.tv_user_agreement)
    TextView mUserAgreement;

    @BindView(R2.id.btn_register)
    Button mRegister;

    private boolean mPwdVisible = false;
    private boolean mConfirmPwdVisible = false;

    @Override
    protected int getLayoutId() {
        return R.layout.account_activity_register;
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        String agreement = getString(R.string.biz_app_name) + " " + getString(R.string.account_user_agreement);
        mUserAgreement.setText("《" + agreement + "》");
        setTextChangedListener();
    }

    @Override
    protected boolean receiveEventBus() {
        return true;
    }

    @OnClick({R2.id.ll_leftLayout})
    public void onBackPressed(View view) {
        int id = view.getId();
        if (id == R.id.ll_leftLayout) {
            finish();
        }
    }

    @OnClick({R2.id.register_text_vericode, R2.id.iv_pwd_visible, R2.id.iv_confirm_pwd_visible, R2.id.tv_user_agreement, R2.id.btn_register, R2.id.tv_login_now})
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.register_text_vericode) {
            // 获取验证码
            getVerCode();
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
        } else if (id == R.id.iv_confirm_pwd_visible) {
            // 密码可见 & 隐藏
            mConfirmPwdVisible = !mConfirmPwdVisible;
            if (mConfirmPwdVisible) {
                // 设置EditText的密码为可见的
                mConfirmPwdEdit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                mConfirmPwdEdit.setSelection(mConfirmPwdEdit.getText().toString().length());
                mIvConfirmPwdVisible.setImageResource(R.mipmap.account_pwd_visible);
            } else {
                // 设置密码为隐藏的
                mConfirmPwdEdit.setTransformationMethod(PasswordTransformationMethod.getInstance());
                mConfirmPwdEdit.setSelection(mConfirmPwdEdit.getText().toString().length());
                mIvConfirmPwdVisible.setImageResource(R.mipmap.account_pwd_invisible);
            }

        } else if (id == R.id.tv_user_agreement) {
            // 用户使用协议
            ARouter.getInstance().build(ArouterPath.PATH_ME_USER_AGREEMENT).navigation();

        } else if (id == R.id.btn_register) {
            // 注册
            register();
        } else if (id == R.id.tv_login_now) {
            goToActivity(LostPwdActivity.class);
        }
    }

    @OnCheckedChanged({R2.id.cb_agree})
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            String mobile = mMobileEdit.getText().toString();
            String verCode = mVericodeEdit.getText().toString();
            String pwd = mPwdEdit.getText().toString();
            String confirmPwd = mConfirmPwdEdit.getText().toString();
            if (!TextUtils.isEmpty(mobile) && !TextUtils.isEmpty(verCode) && !TextUtils.isEmpty(pwd) && !TextUtils.isEmpty(confirmPwd)) {
                mRegister.setEnabled(true);
            } else {
                mRegister.setEnabled(false);
            }
        }
    }

    /**
     * 注册
     */
    private void register() {
        final String mobile = mMobileEdit.getText().toString();
        final String verCode = mVericodeEdit.getText().toString();
        final String pwd = mPwdEdit.getText().toString();
        final String confirmPwd = mConfirmPwdEdit.getText().toString();
        if (TextUtils.isEmpty(mobile) || TextUtils.isEmpty(verCode) || TextUtils.isEmpty(pwd) || TextUtils.isEmpty(confirmPwd))
            return;

        if (!AccountAPI.getInstance().isPhoneNum(mobile)) {
            ToastUtils.showShortToast(R.string.account_login_mobile_err);
            return;
        }

        if (!pwd.equals(confirmPwd)) {
            ToastUtils.showShortToast("密码不一致");
            return;
        }

        if (!NetworkUtils.isConnected()) {
            ToastUtils.showShortToast(R.string.common_network_abnormal);
            return;
        }

        LoadingDialog.showProgress(this, getString(R.string.tip_register));
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                // 注册
                AccountAPI.getInstance().register(mobile, verCode, pwd, confirmPwd);
            }
        }, 1500);
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
                AccountAPI.getInstance().getRegisterVerifyCode(mobile);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(RegisterEvent event) {
        switch (event.msgId) {
            case MsgId.MSGID_GET_REGISTER_VERICODE_SUCCESS: {
                ToastUtils.showShortToast(R.string.account_get_vericode_success);
                break;
            }
            case MsgId.MSGID_GET_REGISTER_VERICODE_FAILED: {
                resetGetCodeTimer();
                ToastUtils.showShortToast(R.string.account_get_vericode_failed);
                break;
            }
            case MsgId.MSGID_REGISTER_SUCCESS: {
                if (LoadingDialog.isShowProgress()) {
                    LoadingDialog.cancelProgress();
                }
                cancelCodeTask();
                // 插入注册信息
                String account = SystemUtils.generateAccount();
                LogUtils.e("ZQY", "account: " + account);
                String mobile = mMobileEdit.getText().toString();
                String pwd = mPwdEdit.getText().toString();
                UserInfo userInfo = new UserInfo();
                userInfo.setPhone(mobile);
                userInfo.setAccount(account);
                userInfo.setPassword(pwd);
                UserInfoManager.getUserInfoDbManager().insertEntity(userInfo);

                ToastUtils.showShortToast(R.string.account_register_success);
                JumpManager.registerSuccess();
                finish();
                break;
            }
            case MsgId.MSGID_REGISTER_FAILED: {
                if (LoadingDialog.isShowProgress()) {
                    LoadingDialog.cancelProgress();
                }
                cancelCodeTask();
                ToastUtils.showShortToast(R.string.account_register_failed);
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
                String pwd = mPwdEdit.getText().toString();
                String confirmPwd = mConfirmPwdEdit.getText().toString();
                if (!TextUtils.isEmpty(mobile) && mobile.length() == 11) {
                    if (mCodeTime > 0 && mCodeTime < 60 || mCodeTime <= 0) {
                        mVericodeText.setEnabled(false);
                    } else
                        mVericodeText.setEnabled(true);
                } else {
                    mVericodeText.setEnabled(false);
                }

                if (!TextUtils.isEmpty(mobile) && !TextUtils.isEmpty(verCode) && !TextUtils.isEmpty(pwd) && !TextUtils.isEmpty(confirmPwd) && mCbAgree.isChecked()) {
                    mRegister.setEnabled(true);
                } else {
                    mRegister.setEnabled(false);
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
                String pwd = mPwdEdit.getText().toString();
                String confirmPwd = mConfirmPwdEdit.getText().toString();

                if (!TextUtils.isEmpty(mobile) && !TextUtils.isEmpty(verCode) && !TextUtils.isEmpty(pwd) && !TextUtils.isEmpty(confirmPwd) && mCbAgree.isChecked()) {
                    mRegister.setEnabled(true);
                } else {
                    mRegister.setEnabled(false);
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
                String mobile = mMobileEdit.getText().toString();
                String verCode = mVericodeEdit.getText().toString();
                String pwd = mPwdEdit.getText().toString();
                String confirmPwd = mConfirmPwdEdit.getText().toString();

                if (!TextUtils.isEmpty(mobile) && !TextUtils.isEmpty(verCode) && !TextUtils.isEmpty(pwd) && !TextUtils.isEmpty(confirmPwd) && mCbAgree.isChecked()) {
                    mRegister.setEnabled(true);
                } else {
                    mRegister.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mConfirmPwdEdit.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String mobile = mMobileEdit.getText().toString();
                String verCode = mVericodeEdit.getText().toString();
                String pwd = mPwdEdit.getText().toString();
                String confirmPwd = mConfirmPwdEdit.getText().toString();

                if (!TextUtils.isEmpty(mobile) && !TextUtils.isEmpty(verCode) && !TextUtils.isEmpty(pwd) && !TextUtils.isEmpty(confirmPwd) && mCbAgree.isChecked()) {
                    mRegister.setEnabled(true);
                } else {
                    mRegister.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
