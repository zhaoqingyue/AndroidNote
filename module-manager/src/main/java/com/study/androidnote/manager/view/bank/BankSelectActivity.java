package com.study.androidnote.manager.view.bank;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.study.androidnote.manager.R;
import com.study.androidnote.manager.R2;
import com.study.androidnote.manager.model.bean.BankType;
import com.study.androidnote.manager.view.bank.adapter.BankSelectAdapter;
import com.study.commonlib.base.activity.BaseTopBarActivity;
import com.study.commonlib.ui.recycleradapter.BaseQuickAdapter;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindArray;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 选择银行
 */
public class BankSelectActivity extends BaseTopBarActivity implements BaseQuickAdapter.OnItemChildClickListener  {

    @BindView( R2.id.rv_bank_select)
    RecyclerView mRecyclerView;

    @BindArray(R2.array.manager_bank_name)
    String [] mBanks ;

    private int [] mLogos = { R.mipmap.manager_bank_0, R.mipmap.manager_bank_1, R.mipmap.manager_bank_2, R.mipmap.manager_bank_3,
        R.mipmap.manager_bank_4, R.mipmap.manager_bank_5, R.mipmap.manager_bank_6, R.mipmap.manager_bank_7,
        R.mipmap.manager_bank_8, R.mipmap.manager_bank_9, R.mipmap.manager_bank_10, R.mipmap.manager_bank_11,
        R.mipmap.manager_bank_12, R.mipmap.manager_bank_13, R.mipmap.manager_bank_14};

    private BankSelectAdapter mAdapter;
    private List<BankType> mBankTypes;

    @Override
    protected int getLayoutId() {
        return R.layout.manager_activity_bank_select;
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        initAdapter();
    }

    private void initAdapter() {
        mBankTypes = new ArrayList<>();
        for (int i=0; i<mBanks.length; i++) {
            BankType bankType = new BankType();
            bankType.setLogoId(mLogos[i]);
            bankType.setBankName(mBanks[i]);
            mBankTypes.add(bankType);
        }
        mAdapter = new BankSelectAdapter(this, mBankTypes);
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
            BankType bankType = mBankTypes.get(position);
            Bundle bundle = new Bundle();
            bundle.putParcelable("BankType", bankType);
            Intent intent = getIntent();
            intent.putExtras(bundle);
            setResult(0, intent);
            finish();
        }
    }
}
