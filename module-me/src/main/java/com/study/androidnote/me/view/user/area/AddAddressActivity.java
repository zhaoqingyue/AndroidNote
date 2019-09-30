package com.study.androidnote.me.view.user.area;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;

import com.study.androidnote.me.R;
import com.study.androidnote.me.R2;
import com.study.androidnote.me.model.UserInfoAPI;
import com.study.androidnote.me.model.bean.CityBean;
import com.study.androidnote.me.util.Constant;
import com.study.biz.bean.event.AddAddressEvent;
import com.study.biz.constant.AppConstant;
import com.study.biz.db.bean.AddressBean;
import com.study.biz.db.dao.AddressBeanDao;
import com.study.biz.db.dao.DaoSession;
import com.study.biz.db.manager.AddressDBManager;
import com.study.biz.db.manager.DaoSessionGenerator;
import com.study.commonlib.base.activity.BaseTopBarActivity;
import com.study.commonlib.ui.dialog.LoadingDialog;
import com.study.commonlib.ui.view.EditItem;
import com.study.commonlib.util.utilcode.ToastUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * 添加地址
 */
public class AddAddressActivity extends BaseTopBarActivity {

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

    private AddressDBManager<AddressBean, AddressBeanDao> mDbManager;
    private boolean mDefaultAddress;

    @Override
    protected int getLayoutId() {
        return R.layout.me_activity_add_address;
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        initDB();
        initEditItem();
    }

    @Override
    protected boolean receiveEventBus() {
        return true;
    }

    private void initDB() {
        DaoSession daoSession = DaoSessionGenerator.getInstance().getDaoSession();
        mDbManager = new AddressDBManager<>(daoSession.getAddressBeanDao());
    }

    private void initEditItem() {
        mPhone.getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);
        mPhone.getEditText().setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});

        mPostCode.getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);
        mPostCode.getEditText().setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
    }

    @OnClick({R2.id.ll_leftLayout})
    public void onBackPressed(View view) {
        int id = view.getId();
        if (id == R.id.ll_leftLayout) {
            finish();
        }
    }

    @OnClick({R2.id.iv_rightImage, R2.id.edit_address_area})
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.iv_rightImage) {
            onConfirm();
        } else if (id == R.id.edit_address_area) {
            goToActivityForResult(ProvinceActivity.class, Constant.RESULT_DATA_TO_PROVINCE);
        }
    }

    private void onConfirm() {
        String name = mName.getContent();
        String phone = mPhone.getContent();
        String area = mArea.getContent();
        String detail = mDetail.getContent();
        String postCode = mPostCode.getContent();

        if (TextUtils.isEmpty(name)) {
            ToastUtils.showShortToast(getString(R.string.me_input_address_name));
            return;
        }

        if (TextUtils.isEmpty(phone)) {
            ToastUtils.showShortToast(getString(R.string.me_input_address_phone));
            return;
        }

        if (TextUtils.isEmpty(area)) {
            ToastUtils.showShortToast(getString(R.string.me_input_address_area));
            return;
        }

        if (TextUtils.isEmpty(detail)) {
            ToastUtils.showShortToast(getString(R.string.me_input_address_detail));
            return;
        }

        LoadingDialog.showProgress(this, getString(R.string.tip_sending));
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                UserInfoAPI.getInstance().addAddress(name, phone, area, detail, postCode);
            }
        }, 1500);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(AddAddressEvent event) {
        if (event.errCode != 0) {
            ToastUtils.showShortToast("添加失败");
            return;
        }
        ToastUtils.showShortToast("添加成功");
        switch (event.msgId) {
            case AppConstant.EDIT_INFO_ADD_ADDRESS: {
                AddressBean addressBean = new AddressBean();
                addressBean.setName(mName.getContent());
                addressBean.setPhone(mPhone.getContent());
                addressBean.setArea(mArea.getContent());
                addressBean.setAddress(mDetail.getContent());
                addressBean.setPostCode(mPostCode.getContent());
                addressBean.setDefaultAddress(mDefaultAddress);
                mDbManager.insertEntity(addressBean);
                setResult(RESULT_OK);
                finish();
                break;
            }
        }
    }

    @OnCheckedChanged({R2.id.switch_address_set_default})
    public void onCheckChanged(CompoundButton view, boolean isChecked) {
        int id = view.getId();
        if (id == R.id.switch_address_set_default) {
            mDefaultAddress = isChecked;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.RESULT_DATA_TO_PROVINCE && resultCode == RESULT_OK && data != null) {
            CityBean area = data.getParcelableExtra("area");
            CityBean city = data.getParcelableExtra("city");
            CityBean province = data.getParcelableExtra("province");
            String areaStr;
            if (city.getName().equals("省直辖县级行政单位")) {
                areaStr = "中国 " + province.getName() + " " + area.getName();
            } else {
                areaStr = "中国 " + province.getName() + " " + city.getName() + " " + area.getName();
            }
            mArea.setContent(areaStr);
        }
    }
}
