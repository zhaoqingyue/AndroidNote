package com.study.androidnote.manager.view.card;

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
import com.study.androidnote.manager.model.bean.BankType;
import com.study.androidnote.manager.model.bean.CardType;
import com.study.androidnote.manager.model.event.CardEvent;
import com.study.androidnote.manager.view.bank.BankSelectActivity;
import com.study.biz.db.bean.CardBean;
import com.study.biz.db.dao.CardBeanDao;
import com.study.biz.db.dao.DaoSession;
import com.study.biz.db.manager.CardDBManager;
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
 * 创建卡
 */
public class CardAddActivity extends BaseTopBarActivity {

    @BindView(R2.id.edit_name)
    EditItem mName;

    @BindView(R2.id.edit_certificateType)
    EditItem mCertificateType;

    @BindView(R2.id.edit_certificateNumber)
    EditItem mCertificateNumber;

    @BindView(R2.id.edit_computerNumber)
    EditItem mComputerNumber;

    @BindView(R2.id.edit_cardNumber)
    EditItem mCardNumber;

    @BindView(R2.id.edit_bank_name)
    EditItem mBankName;

    @BindView(R2.id.edit_bank_number)
    EditItem mBankNumber;

    @BindView(R2.id.edit_mobile)
    EditItem mMobile;

    @BindView(R2.id.btn_save)
    Button mSave;

    private CardDBManager<CardBean, CardBeanDao> mDbManager;
    private int mLogoId;

    @Override
    protected int getLayoutId() {
        return R.layout.manager_activity_card_add;
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        initDB();
        initEditItem();
        setTextChangedListener();
    }

    private void initDB() {
        DaoSession daoSession = DaoSessionGenerator.getInstance().getDaoSession();
        mDbManager = new CardDBManager<>(daoSession.getCardBeanDao());
    }

    private void initEditItem() {
        mName.getEditText().setInputType(InputType.TYPE_CLASS_TEXT);
        mName.getEditText().setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});

        mCertificateNumber.getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);
        mCertificateNumber.getEditText().setFilters(new InputFilter[]{new InputFilter.LengthFilter(18)});

        mComputerNumber.getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);
        mComputerNumber.getEditText().setFilters(new InputFilter[]{new InputFilter.LengthFilter(9)});

        mCardNumber.getEditText().setInputType(InputType.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_NORMAL);
        mCardNumber.getEditText().setFilters(new InputFilter[]{new InputFilter.LengthFilter(9)});

        mBankNumber.getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);
        mBankNumber.getEditText().setFilters(new InputFilter[]{new InputFilter.LengthFilter(19)});

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

    @OnClick({R2.id.edit_certificateType, R2.id.edit_bank_name, R2.id.btn_save})
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.edit_certificateType) {
            goToActivityForResult(CardTypeActivity.class, 0);
        } else if (id == R.id.edit_bank_name) {
            goToActivityForResult(BankSelectActivity.class, 1);
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
            CardType cardType = data.getParcelableExtra("CardType");
            if (cardType != null) {
                mLogoId = cardType.getLogoId();
                mCertificateType.setContent(cardType.getCardName());
            }
        } else if (requestCode == 1 && resultCode == 0) {
            BankType bankType = data.getParcelableExtra("BankType");
            if (bankType != null) {
                mLogoId = bankType.getLogoId();
                mBankName.setContent(bankType.getBankName());
            }
        }
    }

    private void save() {
        String name = mName.getContent();
        String certificateType = mCertificateType.getContent();
        String certificateNumber =  mCertificateNumber.getContent();
        String computerNumber = mComputerNumber.getContent();
        String cardNumber = mCardNumber.getContent();
        String bankName = mBankName.getContent();
        String bankNumber = mBankNumber.getContent();
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
                ManagerAPI.getInstance().addCard(name, certificateType, certificateNumber, computerNumber, cardNumber, bankName, bankNumber,  mobile);
            }
        }, 1500);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(CardEvent event) {
        if (LoadingDialog.isShowProgress()) {
            LoadingDialog.cancelProgress();
        }
        switch (event.msgId) {
            case 0: {
                String name = mName.getContent();
                String certificateType = mCertificateType.getContent();
                String certificateNumber =  mCertificateNumber.getContent();
                String computerNumber = mComputerNumber.getContent();
                String cardNumber = mCardNumber.getContent();
                String bankName = mBankName.getContent();
                String bankNumber = mBankNumber.getContent();
                String mobile = mMobile.getContent();

                CardBean cardBean = new CardBean();
                cardBean.setLogoId(mLogoId);
                cardBean.setUserName(name);
                cardBean.setCertificateType(certificateType);
                cardBean.setCertificateNumber(certificateNumber);
                cardBean.setComputerNumber(computerNumber);
                cardBean.setCardNumber(cardNumber);
                cardBean.setBankName(bankName);
                cardBean.setBankNumber(bankNumber);
                cardBean.setMobile(mobile);
                mDbManager.insertEntity(cardBean);

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
        String certificateType = mCertificateType.getContent();
        String certificateNumber =  mCertificateNumber.getContent();
        String computerNumber = mComputerNumber.getContent();
        String cardNumber = mCardNumber.getContent();
        String bankName = mBankName.getContent();
        String bankNumber = mBankNumber.getContent();
        String mobile = mMobile.getContent();
        if (!TextUtils.isEmpty(name) &&
                !TextUtils.isEmpty(certificateType) &&
                !TextUtils.isEmpty(certificateNumber) &&
                !TextUtils.isEmpty(computerNumber) &&
                !TextUtils.isEmpty(cardNumber) &&
                !TextUtils.isEmpty(bankName) &&
                (!TextUtils.isEmpty(bankNumber) && (bankNumber.length() == 16 || bankNumber.length() == 17 || bankNumber.length() == 19)) &&
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

        mCertificateNumber.getEditText().addTextChangedListener(new TextWatcher() {

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

        mComputerNumber.getEditText().addTextChangedListener(new TextWatcher() {

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

        mCardNumber.getEditText().addTextChangedListener(new TextWatcher() {

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
