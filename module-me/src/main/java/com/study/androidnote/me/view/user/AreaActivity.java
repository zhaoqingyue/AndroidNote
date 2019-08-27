package com.study.androidnote.me.view.user;

import android.os.Bundle;
import android.view.View;

import com.study.androidnote.me.R;
import com.study.androidnote.me.R2;
import com.study.commonlib.base.activity.BaseTopBarActivity;

import butterknife.OnClick;

/**
 * 地区
 */
public class AreaActivity extends BaseTopBarActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.me_activity_area;
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
