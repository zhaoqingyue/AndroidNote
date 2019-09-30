package com.study.androidnote.manager.view.account;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;

import com.study.androidnote.manager.R;
import com.study.androidnote.manager.R2;
import com.study.androidnote.manager.model.ManagerAPI;
import com.study.androidnote.manager.model.bean.AccountType;
import com.study.androidnote.manager.model.event.AccountEvent;
import com.study.biz.db.bean.AccountBean;
import com.study.biz.db.dao.AccountBeanDao;
import com.study.biz.db.dao.DaoSession;
import com.study.biz.db.manager.AccountDBManager;
import com.study.biz.db.manager.DaoSessionGenerator;
import com.study.commonlib.base.activity.BaseTopBarActivity;
import com.study.commonlib.ui.dialog.LoadingDialog;
import com.study.commonlib.ui.view.EditItem;
import com.study.commonlib.util.utilcode.PhoneUtils;
import com.study.commonlib.util.utilcode.ToastUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 创建账号
 */
public class AccountAddActivity extends BaseTopBarActivity {

    @BindView(R2.id.edit_name)
    EditItem mName;

    @BindView(R2.id.edit_nickName)
    EditItem mNickName;

    @BindView(R2.id.edit_accountType)
    EditItem mAccountType;

    @BindView(R2.id.edit_account)
    EditItem mAccount;

    @BindView(R2.id.edit_pwd)
    EditItem mPassword;

    @BindView(R2.id.edit_identity)
    EditItem mIdentity;

    @BindView(R2.id.edit_mobile)
    EditItem mMobile;

    @BindView(R2.id.edit_email)
    EditItem mEmail;

    @BindView(R2.id.edit_url)
    EditItem mUrl;

    @BindView(R2.id.btn_save)
    Button mSave;

    private AccountDBManager< AccountBean, AccountBeanDao> mDbManager;
    private int mLogoId;

    @Override
    protected int getLayoutId() {
        return R.layout.manager_activity_account_add;
    }

    @Override
    protected boolean receiveEventBus() {
        return true;
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        initDB();
        initEditItem();
        setTextChangedListener();
    }

    private void initDB() {
        DaoSession daoSession = DaoSessionGenerator.getInstance().getDaoSession();
        mDbManager = new  AccountDBManager<>(daoSession.getAccountBeanDao());
    }

    private void initEditItem() {
        mName.getEditText().setInputType(InputType.TYPE_CLASS_TEXT);
        mName.getEditText().setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});

        mNickName.getEditText().setInputType(InputType.TYPE_CLASS_TEXT);
        mNickName.getEditText().setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});

        mAccount.getEditText().setInputType(InputType.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_NORMAL);
        mAccount.getEditText().setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});

        mPassword.getEditText().setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        mPassword.getEditText().setFilters(new InputFilter[]{new InputFilter.LengthFilter(16)});

        mIdentity.getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);
        mIdentity.getEditText().setFilters(new InputFilter[]{new InputFilter.LengthFilter(18)});

        mMobile.getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);
        mMobile.getEditText().setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});

        mEmail.getEditText().setInputType(InputType.TYPE_CLASS_TEXT);
        mEmail.getEditText().setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});

        mUrl.getEditText().setInputType(InputType.TYPE_CLASS_TEXT);
        mUrl.getEditText().setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
    }

    @OnClick({R2.id.ll_leftLayout})
    public void onBackPressed(View view) {
        int id = view.getId();
        if (id == R.id.ll_leftLayout) {
            finish();
        }
    }

    @OnClick({R2.id.edit_accountType,R2.id.btn_save})
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.edit_accountType) {
            goToActivityForResult(AccountTypeActivity.class, 0);
        } else if (id == R.id.btn_save) {
            // 保存
            save();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null)
            return;

        if (requestCode == 0 && resultCode == 0) {
            AccountType accountType = data.getParcelableExtra("AccountType");
            if (accountType != null) {
                mLogoId = accountType.getLogoId();
                mAccountType.setContent(accountType.getAccountName());
            }
        }
    }

    private void save() {
        String name = mName.getContent();
        String nickName = mNickName.getContent();
        String accountType = mAccountType.getContent();
        String account = mAccount.getContent();
        String password = mPassword.getContent();
        String identity = mIdentity.getContent();
        String mobile = mMobile.getContent();
        String email = mEmail.getContent();
        String url = mUrl.getContent();

        if (!PhoneUtils.isApprovedMobile(mobile)) {
            ToastUtils.showShortToast("请输入有效的手机号");
            return;
        }

        LoadingDialog.showProgress(this, getString(R.string.tip_sending));
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                ManagerAPI.getInstance().addAccount(name, nickName, accountType, account, password, identity, mobile, email, url);
            }
        }, 1500);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(AccountEvent event) {
        if (LoadingDialog.isShowProgress()) {
            LoadingDialog.cancelProgress();
        }
        switch (event.msgId) {
            case 0: {
                String name = mName.getContent();
                String nickName = mNickName.getContent();
                String accountType = mAccountType.getContent();
                String account = mAccount.getContent();
                String password = mPassword.getContent();
                String identity = mIdentity.getContent();
                String mobile = mMobile.getContent();
                String email = mEmail.getContent();
                String url = mUrl.getContent();

                AccountBean accountBean = new AccountBean();
                accountBean.setLogoId(mLogoId);
                accountBean.setUserName(name);
                accountBean.setNickName(nickName);
                accountBean.setAccountType(accountType);
                accountBean.setAccount(account);
                accountBean.setPassword(password);
                accountBean.setIdentity(identity);
                accountBean.setMobile(mobile);
                accountBean.setEmail(email);
                accountBean.setUrl(url);
                mDbManager.insertEntity(accountBean);

                setResult(0);
                finish();
                break;
            }
            case 1: {
                ToastUtils.showShortToast("提交失败");
                break;
            }
        }
    }

    private void listenButtonEnable() {
        String name = mName.getContent();
        String nickName = mNickName.getContent();
        String accountType = mAccountType.getContent();
        String account = mAccount.getContent();
        String password = mPassword.getContent();
        String identity = mIdentity.getContent();
        String mobile = mMobile.getContent();
        String email = mEmail.getContent();
        String url = mUrl.getContent();

        if (!TextUtils.isEmpty(nickName) &&
                !TextUtils.isEmpty(accountType) &&
                !TextUtils.isEmpty(account) &&
                !TextUtils.isEmpty(password) &&
                identity.length() == 18 &&
                mobile.length() == 11 &&
                !TextUtils.isEmpty(email)) {
            mSave.setEnabled(true);
        } else {
            mSave.setEnabled(false);
        }
    }

    private void setTextChangedListener() {
        mNickName.getEditText().addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                listenButtonEnable();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mAccount.getEditText().addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                listenButtonEnable();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mPassword.getEditText().addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                listenButtonEnable();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mIdentity.getEditText().addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String identity = mIdentity.getContent();
                if (!TextUtils.isEmpty(identity) && identity.length() == 18) {
                    listenButtonEnable();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mMobile.getEditText().addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String mobile = mMobile.getContent();
                if (!TextUtils.isEmpty(mobile) && mobile.length() == 1) {
                    listenButtonEnable();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEmail.getEditText().addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                listenButtonEnable();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
