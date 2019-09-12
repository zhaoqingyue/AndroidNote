package com.study.androidnote.manager.view.bank;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.study.androidnote.manager.R;
import com.study.androidnote.manager.R2;
import com.study.androidnote.manager.model.ManagerAPI;
import com.study.androidnote.manager.model.bean.BankType;
import com.study.androidnote.manager.model.event.BankEvent;
import com.study.androidnote.manager.model.manager.ComplexManager;
import com.study.biz.db.bean.BankBean;
import com.study.biz.db.dao.BankBeanDao;
import com.study.biz.db.dao.DaoSession;
import com.study.biz.db.manager.BankDBManager;
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
 * 创建银行卡账号
 */
public class BankAddActivity extends BaseTopBarActivity {

    @BindView(R2.id.edit_name)
    EditItem mName;

    @BindView(R2.id.edit_bank_name)
    EditItem mBankName;

    @BindView(R2.id.edit_bank_type)
    EditItem mBankType;

    @BindView(R2.id.edit_bank_number)
    EditItem mBankNumber;

    @BindView(R2.id.edit_bank_pwd)
    EditItem mBankPwd;

    @BindView(R2.id.edit_identity)
    EditItem mIdentity;

    @BindView(R2.id.edit_mobile)
    EditItem mMobile;

    @BindView(R2.id.btn_save)
    Button mSave;

    private BankDBManager<BankBean, BankBeanDao> mDbManager;
    private int mLogoId;

    @Override
    protected int getLayoutId() {
        return R.layout.manager_activity_bank_add;
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
        mDbManager = new BankDBManager<>(daoSession.getBankBeanDao());
    }

    private void initEditItem() {
        mName.getEditText().setInputType(InputType.TYPE_CLASS_TEXT);
        mName.getEditText().setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});

        mBankNumber.getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);
        mBankNumber.getEditText().setFilters(new InputFilter[]{new InputFilter.LengthFilter(19)});

        mBankPwd.getEditText().setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        mBankPwd.getEditText().setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});

        mIdentity.getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);
        mIdentity.getEditText().setFilters(new InputFilter[]{new InputFilter.LengthFilter(18)});

        mMobile.getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);
        mMobile.getEditText().setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
    }

    @OnClick({R2.id.ll_leftLayout})
    public void onBackPressed(View view) {
        int id = view.getId();
        if (id == R.id.ll_leftLayout) {
            finish();
        }
    }

    @OnClick({R2.id.edit_bank_name, R2.id.edit_bank_type, R2.id.btn_save})
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.edit_bank_name) {
            goToActivityForResult(BankSelectActivity.class, 0);
        } else if (id == R.id.edit_bank_type) {
            goToActivityForResult(BankTypeActivity.class, 1);
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
            BankType bankType = data.getParcelableExtra("BankType");
            if (bankType != null) {
                mLogoId = bankType.getLogoId();
                mBankName.setContent(bankType.getBankName());
            }
        } else if (requestCode == 1 && resultCode == 1) {
            int bankType = data.getIntExtra("BankType", 0);
            mBankType.setContent(ComplexManager.getInstance().getBankTypeByPos(bankType));
        }
    }

    private void save() {
        String name = mName.getContent();
        String bankName = mBankName.getContent();
        String bankType = mBankType.getContent();
        String bankNumber = mBankNumber.getContent();
        String bankPwd = mBankPwd.getContent();
        String identity = mIdentity.getContent();
        String mobile = mMobile.getContent();

        if (!PhoneUtils.isApprovedMobile(mobile)) {
            ToastUtils.showShortToast("请输入有效的手机号");
            return;
        }

        LoadingDialog.showProgress(this, getString(R.string.tip_sending));
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                ManagerAPI.getInstance().addBank(name, bankName, bankType, bankNumber, bankPwd, identity, mobile);
            }
        }, 1500);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(BankEvent event) {
        if (LoadingDialog.isShowProgress()) {
            LoadingDialog.cancelProgress();
        }
        switch (event.msgId) {
            case 0: {
                String name = mName.getContent();
                String bankName = mBankName.getContent();
                String bankType = mBankType.getContent();
                String bankNumber = mBankNumber.getContent();
                String bankPwd = mBankPwd.getContent();
                String identity = mIdentity.getContent();
                String mobile = mMobile.getContent();

                BankBean bankBean = new BankBean();
                bankBean.setUserName(name);
                bankBean.setLogoId(mLogoId);
                bankBean.setBankTypeName(bankName);
                bankBean.setBankType(ComplexManager.getInstance().getBankTypeByName(bankType));
                bankBean.setAccount(bankNumber);
                bankBean.setPassword(bankPwd);
                bankBean.setIdentity(identity);
                bankBean.setMobile(mobile);
                mDbManager.insertEntity(bankBean);

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
        /**
         * 1. 建行：储蓄卡卡号16位或19位，信用卡16位
         * 2. 农行：储蓄卡卡号19位，信用卡16位
         * 3. 工行：储蓄卡卡号19位，信用卡16位
         * 4. 交通：储蓄卡卡号17位，信用卡16位
         * 5. 民生：储蓄卡卡号16位，信用卡16位
         */
        String name = mName.getContent();
        String bankName = mBankName.getContent();
        String bankType = mBankType.getContent();
        String bankNumber = mBankNumber.getContent();
        String bankPwd = mBankPwd.getContent();
        String identity = mIdentity.getContent();
        String mobile = mMobile.getContent();
        if (!TextUtils.isEmpty(name) &&
                !TextUtils.isEmpty(bankName) &&
                !TextUtils.isEmpty(bankType) &&
                (!TextUtils.isEmpty(bankNumber) && (bankNumber.length() == 16 || bankNumber.length() == 17 || bankNumber.length() == 19)) &&
                !TextUtils.isEmpty(bankPwd) &&
                !TextUtils.isEmpty(identity) && identity.length() == 18 &&
                !TextUtils.isEmpty(mobile) && mobile.length() == 11) {
            mSave.setEnabled(true);
        } else {
            mSave.setEnabled(false);
        }
    }

    private void setTextChangedListener() {
        mName.getEditText().addTextChangedListener(new TextWatcher() {

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

        mBankNumber.getEditText().addTextChangedListener(new TextWatcher() {

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

        mBankPwd.getEditText().addTextChangedListener(new TextWatcher() {

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
                listenButtonEnable();
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
                listenButtonEnable();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
