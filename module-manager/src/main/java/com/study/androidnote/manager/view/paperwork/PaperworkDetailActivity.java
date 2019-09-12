package com.study.androidnote.manager.view.paperwork;

import android.os.Bundle;
import android.view.View;

import com.study.androidnote.manager.R;
import com.study.androidnote.manager.R2;
import com.study.biz.db.bean.PaperworkBean;
import com.study.biz.db.dao.DaoSession;
import com.study.biz.db.dao.PaperworkBeanDao;
import com.study.biz.db.manager.DaoSessionGenerator;
import com.study.biz.db.manager.PaperworkDBManager;
import com.study.commonlib.base.activity.BaseTopBarActivity;
import com.study.commonlib.ui.view.EditItem;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 证件详情
 */
public class PaperworkDetailActivity extends BaseTopBarActivity {

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

    private PaperworkDBManager<PaperworkBean, PaperworkBeanDao> mDbManager;
    private PaperworkBean mPaperworkBean;

    @Override
    protected int getLayoutId() {
        return R.layout.manager_activity_paperwork_detail;
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        initDB();
        updateDetail();
    }

    private void initDB() {
        DaoSession daoSession = DaoSessionGenerator.getInstance().getDaoSession();
        mDbManager = new PaperworkDBManager<>(daoSession.getPaperworkBeanDao());

        Bundle bundle = getIntent().getExtras();
        String account = bundle.getString("Account");
        mPaperworkBean = mDbManager.queryPaperworkByAccount(account);
    }

    private void updateDetail() {
        if (mPaperworkBean == null)
            return;

        mName.setContent(mPaperworkBean.getUserName());
        mCertificateType.setContent(mPaperworkBean.getPaperworkTypeName());
        mCertificateNumber.setContent(mPaperworkBean.getAccount());
        mIdentity.setContent(mPaperworkBean.getIdentity());
        mMobile.setContent(mPaperworkBean.getMobile());
    }

    @OnClick({R2.id.ll_leftLayout})
    public void onBackPressed(View view) {
        int id = view.getId();
        if (id == R.id.ll_leftLayout) {
            finish();
        }
    }
}
