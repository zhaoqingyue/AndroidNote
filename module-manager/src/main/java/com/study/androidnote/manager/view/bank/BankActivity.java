package com.study.androidnote.manager.view.bank;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.study.androidnote.manager.R;
import com.study.androidnote.manager.R2;
import com.study.androidnote.manager.view.bank.adapter.BankAdapter;
import com.study.biz.constant.ArouterPath;
import com.study.biz.db.bean.BankBean;
import com.study.biz.db.dao.BankBeanDao;
import com.study.biz.db.dao.DaoSession;
import com.study.biz.db.manager.BankDBManager;
import com.study.biz.db.manager.DaoSessionGenerator;
import com.study.commonlib.base.activity.BaseTopBarActivity;
import com.study.commonlib.ui.recycleradapter.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 银行卡
 */
@Route(path = ArouterPath.PATH_MANAGER_BANK)
public class BankActivity extends BaseTopBarActivity implements BaseQuickAdapter.OnItemChildClickListener, BaseQuickAdapter.OnItemChildLongClickListener {

    @BindView(R2.id.rv_bank)
    RecyclerView mRecyclerView;

    private List<BankBean> mBankList;
    private BankAdapter mAdapter;
    private BankDBManager<BankBean, BankBeanDao> mDbManager;

    @Override
    protected int getLayoutId() {
        return R.layout.manager_activity_bank;
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        initDB();
        initAdapter();
    }

    private void initDB() {
        DaoSession daoSession = DaoSessionGenerator.getInstance().getDaoSession();
        mDbManager = new BankDBManager<>(daoSession.getBankBeanDao());
    }

    private void initAdapter() {
        updateBankList(true);
        mAdapter = new BankAdapter(this, mBankList);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(OrientationHelper.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(this);
    }

    @OnClick({R2.id.ll_leftLayout})
    public void onBackPressed(View view) {
        int id = view.getId();
        if (id == R.id.ll_leftLayout) {
            finish();
        }
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        int id = view.getId();
        if (id == R.id.ll_content) {
            BankBean bankBean = mBankList.get(position);
            String account = bankBean.getAccount();
            Bundle extras = new Bundle();
            extras.putString("Account", account);
            goToActivity(BankDetailActivity.class, extras);
        } else if (id == R.id.ll_content_add || id == R.id.iv_add) {
            // add
            goToActivityForResult(BankAddActivity.class, 0);
        }
    }

    @Override
    public boolean onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == 0) {
            updateBankList(false);
        }
    }

    private void updateBankList(boolean init) {
        if (mBankList == null) {
            mBankList = new ArrayList<>();
        } else {
            mBankList.clear();
        }
        mBankList.addAll(mDbManager.queryByOrderAsc());
        BankBean bankBean = new BankBean();
        bankBean.setItemType(BankAdapter.BANK_TYPE_ADD);
        mBankList.add(bankBean);
        if (!init) {
            mAdapter.notifyDataSetChanged();
        }
    }
}
