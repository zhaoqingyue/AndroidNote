package com.study.androidnote.manager.view.card;

import android.os.Bundle;
import android.view.View;

import com.study.androidnote.manager.R;
import com.study.androidnote.manager.R2;
import com.study.biz.db.bean.CardBean;
import com.study.biz.db.dao.CardBeanDao;
import com.study.biz.db.dao.DaoSession;
import com.study.biz.db.manager.CardDBManager;
import com.study.biz.db.manager.DaoSessionGenerator;
import com.study.commonlib.base.activity.BaseTopBarActivity;
import com.study.commonlib.ui.view.EditItem;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 卡详情
 */
public class CardDetailActivity extends BaseTopBarActivity {

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

    private CardDBManager<CardBean, CardBeanDao> mDbManager;
    private CardBean mCardBean;

    @Override
    protected int getLayoutId() {
        return R.layout.manager_activity_card_detail;
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        initDB();
        updateDetail();
    }

    private void initDB() {
        DaoSession daoSession = DaoSessionGenerator.getInstance().getDaoSession();
        mDbManager = new CardDBManager<>(daoSession.getCardBeanDao());

        Bundle bundle = getIntent().getExtras();
        String certificateNumber = bundle.getString("CertificateNumber");
        mCardBean = mDbManager.queryCardByCertificateNumber(certificateNumber);
    }

    private void updateDetail() {
        if (mCardBean == null)
            return;

        mName.setContent(mCardBean.getUserName());
        mCertificateType.setContent(mCardBean.getCertificateType());
        mCertificateNumber.setContent(mCardBean.getCertificateNumber());
        mComputerNumber.setContent(mCardBean.getComputerNumber());
        mCardNumber.setContent(mCardBean.getCardNumber());
        mBankName.setContent(mCardBean.getBankName());
        mBankNumber.setContent(mCardBean.getBankNumber());
        mMobile.setContent(mCardBean.getMobile());
    }

    @OnClick({R2.id.ll_leftLayout})
    public void onBackPressed(View view) {
        int id = view.getId();
        if (id == R.id.ll_leftLayout) {
            finish();
        }
    }
}
