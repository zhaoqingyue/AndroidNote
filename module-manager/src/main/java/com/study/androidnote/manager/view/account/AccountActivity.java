package com.study.androidnote.manager.view.account;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.study.androidnote.manager.R;
import com.study.androidnote.manager.R2;
import com.study.androidnote.manager.view.account.adapter.AccountAdapter;
import com.study.biz.constant.ArouterPath;
import com.study.biz.db.bean.AccountBean;
import com.study.biz.db.dao.AccountBeanDao;
import com.study.biz.db.dao.DaoSession;
import com.study.biz.db.manager.AccountDBManager;
import com.study.biz.db.manager.DaoSessionGenerator;
import com.study.commonlib.base.activity.BaseTopBarActivity;
import com.study.commonlib.ui.recycleradapter.BaseQuickAdapter;
import com.study.commonlib.util.utilcode.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 账号
 */
@Route(path = ArouterPath.PATH_MANAGER_ACCOUNT)
public class AccountActivity extends BaseTopBarActivity implements BaseQuickAdapter.OnItemChildClickListener, BaseQuickAdapter.OnItemChildLongClickListener {

    @BindView(R2.id.rv_account)
    RecyclerView mRecyclerView;

    private List<AccountBean> mAccountList;
    private AccountAdapter mAdapter;
    private AccountDBManager<AccountBean, AccountBeanDao> mDbManager;
    
    @Override
    protected int getLayoutId() {
        return R.layout.manager_activity_account;
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        initDB();
        initAdapter();
    }

    private void initDB() {
        DaoSession daoSession = DaoSessionGenerator.getInstance().getDaoSession();
        mDbManager = new AccountDBManager<>(daoSession.getAccountBeanDao());
    }

    private void initAdapter() {
        updateAccountList(true);
        mAdapter = new AccountAdapter(this, mAccountList);
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
            AccountBean accountBean = mAccountList.get(position);
            String account = accountBean.getAccount();
            Bundle extras = new Bundle();
            extras.putString("Account", account);
            goToActivity(AccountDetailActivity.class, extras);
        } else if (id == R.id.ll_content_add || id == R.id.iv_add) {
            // add
            goToActivityForResult(AccountAddActivity.class, 0);
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
            updateAccountList(false);
        }
    }

    private void updateAccountList(boolean init) {
        if (mAccountList == null) {
            mAccountList = new ArrayList<>();
        } else {
            mAccountList.clear();
        }
        mAccountList.addAll(mDbManager.queryByOrderAsc());
        AccountBean accountBean = new AccountBean();
        accountBean.setItemType(AccountAdapter.ACCOUNT_TYPE_ADD);
        mAccountList.add(accountBean);
        if (!init) {
            mAdapter.notifyDataSetChanged();
        }
    }
}
