package com.study.androidnote.me.view.user;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import com.study.androidnote.me.R;
import com.study.androidnote.me.R2;
import com.study.androidnote.me.view.dialog.WheelViewDateDialog;
import com.study.commonlib.base.activity.BaseTopBarActivity;

import butterknife.OnClick;

/**
 * 出生日期
 */
public class BirthdayActivity extends BaseTopBarActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.me_activity_birthday;
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        WheelViewDateDialog.newInstance(2)
                .setOutCancel(false)
                .setDimAmount(0.3f)
                .setGravity(Gravity.BOTTOM)
                .show(getSupportFragmentManager());
    }

    @OnClick({R2.id.ll_leftLayout})
    public void onBackPressed(View view) {
        int id = view.getId();
        if (id == R.id.ll_leftLayout) {
            finish();
        }
    }
}
