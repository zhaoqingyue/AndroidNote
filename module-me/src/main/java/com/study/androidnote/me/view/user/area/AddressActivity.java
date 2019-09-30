package com.study.androidnote.me.view.user.area;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.study.androidnote.me.R;
import com.study.androidnote.me.R2;
import com.study.androidnote.me.view.user.area.adapter.AddressAdapter;
import com.study.biz.db.bean.AccountBean;
import com.study.biz.db.bean.AddressBean;
import com.study.biz.db.dao.AccountBeanDao;
import com.study.biz.db.dao.AddressBeanDao;
import com.study.biz.db.dao.DaoSession;
import com.study.biz.db.manager.AccountDBManager;
import com.study.biz.db.manager.AddressDBManager;
import com.study.biz.db.manager.DaoSessionGenerator;
import com.study.commonlib.base.activity.BaseTopBarActivity;
import com.study.commonlib.ui.recycleradapter.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 收货地址
 */
public class AddressActivity extends BaseTopBarActivity implements BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R2.id.rv_address)
    RecyclerView mRecyclerView;

    @BindView(R2.id.tv_empty)
    TextView mEmptyView;

    private AddressAdapter mAdapter;
    private List<AddressBean> mAddressBeans;
    private AddressDBManager<AddressBean, AddressBeanDao> mDbManager;

    @Override
    protected int getLayoutId() {
        return R.layout.me_activity_address;
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        initDB();
        initAdapter();
    }

    private void initDB() {
        DaoSession daoSession = DaoSessionGenerator.getInstance().getDaoSession();
        mDbManager = new AddressDBManager<>(daoSession.getAddressBeanDao());
    }

    private void initAdapter() {
        updateAddressList(true);
        mAdapter = new AddressAdapter(this, mAddressBeans);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(OrientationHelper.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(this);
    }

    private void updateAddressList(boolean init) {
        if (mAddressBeans == null) {
            mAddressBeans = new ArrayList<>();
        } else {
            mAddressBeans.clear();
        }
        mAddressBeans.addAll(mDbManager.queryByOrderAsc());
        if (!init) {
            mAdapter.notifyDataSetChanged();
        }

        if (mAddressBeans.isEmpty()) {
            mEmptyView.setVisibility(View.VISIBLE);
        } else {
            mEmptyView.setVisibility(View.GONE);
        }
    }

    @OnClick({R2.id.ll_leftLayout})
    public void onBackPressed(View view) {
        int id = view.getId();
        if (id == R.id.ll_leftLayout) {
            finish();
        }
    }

    @OnClick({R2.id.iv_rightImage})
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.iv_rightImage) {
            goToActivityForResult(AddAddressActivity.class, 0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK) {
            updateAddressList(false);
        }
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        AddressBean addressBean = mAddressBeans.get(position);
        Long addressId = addressBean.getAddressId();
        Bundle extras = new Bundle();
        extras.putLong("AddressId", addressId);
        goToActivityForResult(AddressDetailActivity.class, extras, 0);
    }
}
