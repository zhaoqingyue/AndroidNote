package com.study.androidnote.manager.view.account;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.study.androidnote.manager.R;
import com.study.androidnote.manager.R2;
import com.study.androidnote.manager.model.bean.AccountType;
import com.study.androidnote.manager.view.account.adapter.AccountTypeAdapter;
import com.study.commonlib.base.activity.BaseTopBarActivity;
import com.study.commonlib.ui.recycleradapter.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 账号类型
 */
public class AccountTypeActivity extends BaseTopBarActivity implements BaseQuickAdapter.OnItemChildClickListener {

    @BindView( R2.id.rv_account_type)
    RecyclerView mRecyclerView;

    @BindArray(R2.array.manager_account)
    String [] mAccounts ;

    private List<AccountType> mAccountList;
    private AccountTypeAdapter mAdapter;
    private int [] mLogos = { R.mipmap.manager_account_0, R.mipmap.manager_account_1, R.mipmap.manager_account_2, R.mipmap.manager_account_3,
            R.mipmap.manager_account_4, R.mipmap.manager_account_5, R.mipmap.manager_account_6, R.mipmap.manager_account_7, R.mipmap.manager_account_8,
            R.mipmap.manager_account_9, R.mipmap.manager_account_10, R.mipmap.manager_account_11, R.mipmap.manager_account_12, R.mipmap.manager_account_13,
            R.mipmap.manager_account_14, R.mipmap.manager_account_15, R.mipmap.manager_account_16, R.mipmap.manager_account_17, R.mipmap.manager_account_18,
            R.mipmap.manager_account_19, R.mipmap.manager_account_20, R.mipmap.manager_account_21, R.mipmap.manager_account_22, R.mipmap.manager_account_23,
            R.mipmap.manager_account_24, R.mipmap.manager_account_25, R.mipmap.manager_account_26, R.mipmap.manager_account_27};

    @Override
    protected int getLayoutId() {
        return R.layout.manager_activity_account_type;
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        initAdapter();
    }

    private void initAdapter() {
        mAccountList = new ArrayList<>();
        for (int i=0; i<mAccounts.length; i++) {
            AccountType accountType = new AccountType();
            accountType.setLogoId(mLogos[i]);
            accountType.setAccountName(mAccounts[i]);
            mAccountList.add(accountType);
        }
        mAdapter = new AccountTypeAdapter(this, mAccountList);
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
            AccountType accountType = mAccountList.get(position);
            Bundle bundle = new Bundle();
            bundle.putParcelable("AccountType", accountType);
            Intent intent = getIntent();
            intent.putExtras(bundle);
            setResult(0, intent);
            finish();
        }
    }
}
