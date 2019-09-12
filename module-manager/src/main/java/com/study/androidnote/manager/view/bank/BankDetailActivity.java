package com.study.androidnote.manager.view.bank;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.study.androidnote.manager.R;
import com.study.androidnote.manager.R2;
import com.study.androidnote.manager.model.manager.ComplexManager;
import com.study.biz.db.bean.BankBean;
import com.study.biz.db.dao.BankBeanDao;
import com.study.biz.db.dao.DaoSession;
import com.study.biz.db.manager.BankDBManager;
import com.study.biz.db.manager.DaoSessionGenerator;
import com.study.commonlib.base.activity.BaseTopBarActivity;
import com.study.commonlib.ui.view.EditItem;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 银行卡详情
 */
public class BankDetailActivity extends BaseTopBarActivity {

    @BindView(R2.id.iv_logo)
    ImageView mLogo;

    @BindView(R2.id.et_userName)
    EditItem mUserName;

    @BindView(R2.id.et_bankName)
    EditItem mBankName;

    @BindView(R2.id.et_bankType)
    EditItem mBankType;

    @BindView(R2.id.et_bankNumber)
    EditItem mBankNumber;

    @BindView(R2.id.et_bankPwd)
    EditItem mBankPwd;

    @BindView(R2.id.et_identity)
    EditItem mIdentity;

    @BindView(R2.id.et_mobile)
    EditItem mMobile;

    private BankDBManager<BankBean, BankBeanDao> mDbManager;
    private BankBean mBankBean;

    @Override
    protected int getLayoutId() {
        return R.layout.manager_activity_bank_detail;
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        initDB();
        updateDetail();
    }

    private void initDB() {
        DaoSession daoSession = DaoSessionGenerator.getInstance().getDaoSession();
        mDbManager = new BankDBManager<>(daoSession.getBankBeanDao());

        Bundle bundle = getIntent().getExtras();
        String account = bundle.getString("Account");
        mBankBean = mDbManager.queryBankByAccount(account);
    }

    private void updateDetail() {
        if (mBankBean == null)
            return;

        mLogo.setImageResource(mBankBean.getLogoId());
        mUserName.setContent(mBankBean.getUserName());
        mBankName.setContent(mBankBean.getBankTypeName());
        mBankType.setContent(ComplexManager.getInstance().getBankTypeByPos(mBankBean.getBankType()));
        mBankNumber.setContent(mBankBean.getAccount());
        mBankPwd.setContent(mBankBean.getPassword());
        mIdentity.setContent(mBankBean.getIdentity());
        mMobile.setContent(mBankBean.getMobile());
    }

    @OnClick({R2.id.ll_leftLayout})
    public void onBackPressed(View view) {
        int id = view.getId();
        if (id == R.id.ll_leftLayout) {
            finish();
        }
    }
}
