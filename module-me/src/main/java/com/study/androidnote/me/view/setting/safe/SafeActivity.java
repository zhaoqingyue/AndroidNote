package com.study.androidnote.me.view.setting.safe;

import android.os.Bundle;
import android.view.View;

import com.study.androidnote.me.R;
import com.study.androidnote.me.R2;
import com.study.commonlib.base.activity.BaseTopBarActivity;

import butterknife.OnClick;

public class SafeActivity extends BaseTopBarActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.me_activity_safe;
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

    @OnClick({R2.id.mc_account, R2.id.mc_mobile, R2.id.mc_password, R2.id.mc_emergency_contact})
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.mc_account) {

        } else if (id == R.id.mc_mobile) {

        } else if (id == R.id.mc_password) {

        } else if (id == R.id.mc_emergency_contact) {

        }
    }
}
