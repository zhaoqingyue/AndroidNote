package com.study.androidnote.manager.view.account;

import android.os.Bundle;
import android.view.View;

import com.study.androidnote.manager.R;
import com.study.androidnote.manager.R2;
import com.study.biz.db.bean.AccountBean;
import com.study.biz.db.dao.AccountBeanDao;
import com.study.biz.db.dao.DaoSession;
import com.study.biz.db.manager.AccountDBManager;
import com.study.biz.db.manager.DaoSessionGenerator;
import com.study.commonlib.base.activity.BaseTopBarActivity;
import com.study.commonlib.ui.view.EditItem;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 账号详情
 */
public class AccountDetailActivity extends BaseTopBarActivity {

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

    private AccountDBManager<AccountBean, AccountBeanDao> mDbManager;
    private AccountBean mAccountBean;

    @Override
    protected int getLayoutId() {
        return R.layout.manager_activity_account_detail;
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        initDB();
        updateDetail();
    }

    private void initDB() {
        DaoSession daoSession = DaoSessionGenerator.getInstance().getDaoSession();
        mDbManager = new  AccountDBManager<>(daoSession.getAccountBeanDao());

        Bundle bundle = getIntent().getExtras();
        String account = bundle.getString("Account");
        mAccountBean = mDbManager.queryAccountByAccount(account);
    }

    private void updateDetail() {
        if (mAccountBean == null)
            return;

        mName.setContent(mAccountBean.getUserName());
        mNickName.setContent(mAccountBean.getNickName());
        mAccountType.setContent(mAccountBean.getAccountType());
        mAccount.setContent(mAccountBean.getAccount());
        mPassword.setContent(mAccountBean.getPassword());
        mIdentity.setContent(mAccountBean.getIdentity());
        mMobile.setContent(mAccountBean.getMobile());
        mEmail.setContent(mAccountBean.getEmail());
        mUrl.setContent(mAccountBean.getUrl());
    }

    @OnClick({R2.id.ll_leftLayout})
    public void onBackPressed(View view) {
        int id = view.getId();
        if (id == R.id.ll_leftLayout) {
            finish();
        }
    }
}
