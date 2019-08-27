package com.study.androidnote.me.view.user;

import android.os.Bundle;
import android.view.View;

import com.study.androidnote.me.R;
import com.study.androidnote.me.R2;
import com.study.commonlib.base.activity.BaseTopBarActivity;

import butterknife.OnClick;

/**
 * 收货地址
 */
public class ShipAddressActivity extends BaseTopBarActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.me_activity_ship_address;
    }

    @Override
    protected void initData(Bundle saveInstanceState) {

    }

    @OnClick({R2.id.ll_leftLayout})
    public void onBackPressed(View view) {
        int id = view.getId();
        if (id == R.id.ll_leftLayout) {
            finish();
        }
    }
}
