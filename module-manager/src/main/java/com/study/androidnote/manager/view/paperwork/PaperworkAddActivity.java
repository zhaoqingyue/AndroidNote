package com.study.androidnote.manager.view.paperwork;

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

import com.study.androidnote.manager.R;
import com.study.androidnote.manager.R2;
import com.study.androidnote.manager.model.ManagerAPI;
import com.study.androidnote.manager.model.bean.BankType;
import com.study.androidnote.manager.model.bean.PaperworkType;
import com.study.androidnote.manager.model.event.BankEvent;
import com.study.androidnote.manager.model.event.PaperworkEvent;
import com.study.androidnote.manager.model.manager.ComplexManager;
import com.study.androidnote.manager.view.bank.BankSelectActivity;
import com.study.androidnote.manager.view.bank.BankTypeActivity;
import com.study.biz.db.bean.BankBean;
import com.study.biz.db.bean.PaperworkBean;
import com.study.biz.db.dao.BankBeanDao;
import com.study.biz.db.dao.DaoSession;
import com.study.biz.db.dao.PaperworkBeanDao;
import com.study.biz.db.manager.BankDBManager;
import com.study.biz.db.manager.DaoSessionGenerator;
import com.study.biz.db.manager.PaperworkDBManager;
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
public class PaperworkAddActivity extends BaseTopBarActivity {

    @BindView(R2.id.edit_name)
    EditItem mName;

    @BindView(R2.id.edit_certificateType)
    EditItem mCertificateType;

    @BindView(R2.id.edit_certificateNumber)
    EditItem mCertificateNumber;

    @BindView(R2.id.edit_identity)
    EditItem mIdentity;

    @BindView(R2.id.edit_mobile)
    EditItem mMobile;

    @BindView(R2.id.btn_save)
    Button mSave;

    private PaperworkDBManager<PaperworkBean, PaperworkBeanDao> mDbManager;
    private int mLogoId;

    @Override
    protected int getLayoutId() {
        return R.layout.manager_activity_paperwork_add;
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
        mDbManager = new PaperworkDBManager<>(daoSession.getPaperworkBeanDao());
    }

    private void initEditItem() {
        mName.getEditText().setInputType(InputType.TYPE_CLASS_TEXT);
        mName.getEditText().setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});

        mCertificateNumber.getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);
        mCertificateNumber.getEditText().setFilters(new InputFilter[]{new InputFilter.LengthFilter(18)});

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

    @OnClick({R2.id.edit_certificateType, R2.id.btn_save})
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.edit_certificateType) {
            goToActivityForResult(PaperworkTypeActivity.class, 0);
        }else if (id == R.id.btn_save) {
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
            PaperworkType paperworkType = data.getParcelableExtra("PaperworkType");
            if (paperworkType != null) {
                mLogoId = paperworkType.getLogoId();
                mCertificateType.setContent(paperworkType.getPaperworkName());
            }
        }
    }

    private void save() {
        String name = mName.getContent();
        String certificateType = mCertificateType.getContent();
        String certificateNumber = mCertificateNumber.getContent();
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
                ManagerAPI.getInstance().addPaperwork(name, certificateType, certificateNumber, identity, mobile);
            }
        }, 1500);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(PaperworkEvent event) {
        if (LoadingDialog.isShowProgress()) {
            LoadingDialog.cancelProgress();
        }
        switch (event.msgId) {
            case 0: {
                String name = mName.getContent();
                String certificateType = mCertificateType.getContent();
                String certificateNumber = mCertificateNumber.getContent();
                String identity = mIdentity.getContent();
                String mobile = mMobile.getContent();

                PaperworkBean paperworkBean = new PaperworkBean();
                paperworkBean.setUserName(name);
                paperworkBean.setLogoId(mLogoId);
                paperworkBean.setPaperworkTypeName(certificateType);
                paperworkBean.setAccount(certificateNumber);
                paperworkBean.setIdentity(identity);
                paperworkBean.setMobile(mobile);
                mDbManager.insertEntity(paperworkBean);

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
        String certificateType = mCertificateType.getContent();
        String certificateNumber = mCertificateNumber.getContent();
        String identity = mIdentity.getContent();
        String mobile = mMobile.getContent();
        if (!TextUtils.isEmpty(name) &&
                !TextUtils.isEmpty(certificateType) &&
                !TextUtils.isEmpty(certificateNumber) &&
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
