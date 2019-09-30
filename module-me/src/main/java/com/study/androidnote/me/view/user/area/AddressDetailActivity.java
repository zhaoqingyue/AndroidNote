package com.study.androidnote.me.view.user.area;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.study.androidnote.me.R;
import com.study.androidnote.me.R2;
import com.study.biz.db.bean.AddressBean;
import com.study.biz.db.dao.AddressBeanDao;
import com.study.biz.db.dao.DaoSession;
import com.study.biz.db.manager.AddressDBManager;
import com.study.biz.db.manager.DaoSessionGenerator;
import com.study.commonlib.base.activity.BaseTopBarActivity;
import com.study.commonlib.ui.view.EditItem;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * 地址详情
 */
public class AddressDetailActivity extends BaseTopBarActivity {

    @BindView( R2.id.edit_address_name)
    EditItem mName;

    @BindView(R2.id.edit_address_phone)
    EditItem mPhone;

    @BindView(R2.id.edit_address_area)
    EditItem mArea;

    @BindView(R2.id.edit_address_detail)
    EditItem mDetail;

    @BindView(R2.id.edit_address_post_code)
    EditItem mPostCode;

    @BindView(R2.id.switch_address_set_default)
    Switch mSwitch;

    private AddressDBManager<AddressBean, AddressBeanDao> mDbManager;
    private AddressBean mAddressBean;

    @Override
    protected int getLayoutId() {
        return R.layout.me_activity_address_detail;
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        initDB();
        updateDetail();
    }

    private void initDB() {
        DaoSession daoSession = DaoSessionGenerator.getInstance().getDaoSession();
        mDbManager = new AddressDBManager<>(daoSession.getAddressBeanDao());

        Bundle bundle = getIntent().getExtras();
        Long addressId = bundle.getLong("AddressId");
        mAddressBean = mDbManager.queryById(addressId);
    }

    private void updateDetail() {
        if (mAddressBean == null)
            return;

        mName.setContent(mAddressBean.getName());
        mPhone.setContent(mAddressBean.getPhone());
        mArea.setContent(mAddressBean.getArea());
        mDetail.setContent(mAddressBean.getAddress());
        mPostCode.setContent(mAddressBean.getPostCode());
        mSwitch.setChecked(mAddressBean.getDefaultAddress());
    }

    @OnClick({R2.id.ll_leftLayout})
    public void onBackPressed(View view) {
        int id = view.getId();
        if (id == R.id.ll_leftLayout) {
            finish();
        }
    }

    @OnCheckedChanged({R2.id.switch_address_set_default})
    public void onCheckChanged(CompoundButton view, boolean isChecked) {
        int id = view.getId();
        if (id == R.id.switch_address_set_default) {
            mAddressBean.setDefaultAddress(isChecked);
            mDbManager.updateEntity(mAddressBean);
        }
    }
}
