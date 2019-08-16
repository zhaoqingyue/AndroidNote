package com.study.commonlib.base.activity;

import android.view.View;

import com.study.commonlib.R;
import com.study.commonlib.R2;

import butterknife.OnClick;

/**
 * Created by zhao.qingyue on 2019/8/15.
 * 基类Activity
 * 带自定义TopBar的基类Activity
 */
public abstract class BaseTopBarActivity extends BaseActivity {

    @OnClick({R2.id.ll_leftLayout})
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.ll_leftLayout) {
            finish();
        }
    }
}
